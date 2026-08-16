# ULMS 全量架构审计报告

> 审计日期：2026-08-16
> 审计范围：前端（Vue3/Vite/TDesign）+ 后端（Spring Boot 3.5.7/MyBatis/Oracle/Sa-Token）+ 安全 + 测试覆盖
> 审计方式：静态分析 + 本机实测复现 + 高风险抽查验证（前端/安全/测试由审计子代理全量扫描，后端由主代理交叉审计）
> 状态：**完成**

---

## 0. 已验证的独立发现（本机实测复现）

### 🔴 HIGH-1：axios-retry 重试导致 POST/PUT 请求体双重 JSON 编码
- **位置**：`frontend/src/utils/request.js`（拦截器 stringify + axios-retry 配置）
- **机制**（Node 实测复现）：
  1. request.js 请求拦截器对 post data 执行 `JSON.stringify(config.data)`（单层）
  2. 首次请求因**网络错误或后端 5xx** 失败 → axios-retry 重试
  3. 重试重新走请求拦截器 → 对**已 stringify 的字符串**再次 `JSON.stringify` → **双编码**
  4. 后端 Jackson 反序列化失败：`Cannot construct instance of BrushConfig ... from String value ('"{\"monthLimit\":0,...}"')`（后端日志 2026-08-15 22:53 三连报错）
- **影响**：所有 POST/PUT 请求在首次失败（网络抖动/后端 5xx）时，重试请求 100% 双编码 → 400 失败。刷题"开始答题"（extractQuestions）是实际受害接口
- **修复建议**（拦截器幂等化，已实测验证有效）：
  ```js
  if (config.method === 'post') {
    if (config.data instanceof FormData) {
      delete config.headers['Content-Type']
    } else if (config.data && typeof config.data === 'object') {
      config.data = JSON.stringify(config.data)
      config.headers['Content-Type'] = 'application/json'
    }
  }
  ```
- **验证**：`probe_retry.mjs` 实测双编码复现；`probe_fix.mjs` 实测修复后重试单层、FormData 不受影响 ✅

### 🟡 MED-1：后端日志 "Value for ploNum cannot be null"
- MyBatis Example 条件传入 null 触发（`andPloNumEqualTo(null)`）；`QuesTestServiceImpl.listMyExamTest` 的 userId 来自 `Util.userIdByShiro()`，登录态失效返回 null。相关系统性模式见 B-M2（youngTalk 25 处 `userId.equals("0")` NPE 式"拦截"）

---

## 1. 测试覆盖审计（已完成）

### 现状
- 后端 12 个测试类，**仅 6 个有真实断言价值**（PasswordUtilTest/AESUtilTest/UtilTest/StudyLogServiceImplTest/QuesBankServiceImplTest/MenuServiceImplTest）；4 个透传壳、2 个保护分支壳
- **96 个 ServiceImpl 仅 9 个有测试（90.6% 零测试）**；**90 个 Controller 全部零测试**（sys 4/19、college 1/15、edu 1/19、flow 1/9、oht 2/7，youngTalk/hr/rpa/helper/db 全 0）
- 前端：package.json 无 test 脚本，无 Vitest/Jest/@vue/test-utils/cypress，src 下无 spec/test 文件

### 最危险的空缺（无任何测试）
| 模块 | 说明 |
|---|---|
| 登录认证 `LoginController.login` | 核心入口，密码 AES+自定义加密链路（无独立 LoginService，逻辑在 Controller+UserServiceImpl） |
| 权限装配 `UserServiceImpl.getWithMenuList`、`MenuServiceImpl`（12 方法中 11 个） | 菜单树/权限码装配 |
| 审批状态机 `FlowCaseServiceImpl.approve` | 流程状态流转 |
| 成绩计算 `QuesScoreServiceImpl` / `BrushScoreServiceImpl` | 判分/积分/日限额核心逻辑 |
| 考试组卷 `CourseExamServiceImpl` | 抽题组卷 |
| 文件管理 `UserFilesServiceImpl` | 上传/读取 |

### 优点
- 现有测试全部纯 JUnit5+Mockito，无 Spring 上下文、无 Oracle/Redis 依赖 → 快速稳定，补测保持此风格

### 建议
1. 优先补：登录认证、权限装配、成绩判分、审批状态机（各 3-6 个用例，测试要点见 subagent 输出）
2. 前端引入 Vitest，从纯逻辑 hooks 起步：`usePagination`/`useCrudPage`/`useConfirm`/`usePrompt`/`request.js` 拦截器/`CustomTable` 列解析（可测性良好）
3. 杂项：`backend/pom.xml:158-164` 声明了**从未使用的 TestNG 7.8.0**，建议移除

---

## 2. 前端架构审计（已完成）

### 🔴 高严重度
- **HIGH-1**（见 §0）：`request.js` + axios-retry 重试双编码——所有 POST/PUT 首次失败重试即 400
- **F-H1 OHT 身份变更消息永远发不出去**（已抽查验证）：`views/font/oht/components/Identity.vue:87-88` 把 **store 构造函数当实例用**——`useWsStore.connectionStatus` 恒为 undefined → 条件恒 false，且 `useWsStore.send` 若执行会抛 TypeError。影响：值班身份选择后服务端收不到，其他终端不感知
- **F-H2 调研报表下载必然失败**（已抽查验证）：`admin/cyt/surveyManage/index.vue:554-561` 自造下载用 `import.meta.env.VITE_APP_BASE_API`（4 个 .env 均未定义，实际是 `VITE_API_BASE_URL`）→ `undefined/...` URL；且 `window.open` 直链不带 Authorization
- **F-H3 AES 密钥硬编码前端**：`utils/cryptoService.js:3-4` 密钥+IV 明文（与安全 H1 交叉确认）；且 `decryptData`（passphrase KDF）与 `encryptData`（原始字节密钥）派生方式不一致，互解不出
- **F-H4 请求错误处理口径不一**：`utils/request.js:55-68` HTTP 200 时无论 `res.code` 多少都 resolve（含 301/401）→ 各页面判断不一致；5xx/网络错误无用户提示且 prod `esbuild.drop: console` 丢弃唯一日志 → 静默失败
- **F-H5 路由守卫重定向死代码 + 无显式 token 过期检测**：`router/index.js:48-54` 因 catch-all notFound 已注册，`resolvedTarget.matched.length === 0` 恒不成立；过期全靠 `initializeAppData` 抛错兜底
- **F-H6 三层循环依赖**：`request.js:5`→`router/index.js`→`stores`→`menu/index.ts`→`api/auth.js`→`request.js`；`ws/index.ts:4-5` 同时 import `@/router` 与 `@/stores`，oht 又反向 import ws（与 F-M1 合并）

### 🟡 中严重度
- **F-M1 store 循环依赖**：`stores/modules/ws/index.ts:5` ↔ `stores/modules/oht/index.ts:5` 互相 import
- **F-M2 包体积**：`vite.config.js:43` `manualChunks` **强制整包 7MB tdesign/1.1MB echarts**（直接抵消 Resolver 按需引入）；3 处全量 `import * as echarts`；全量 `tdesign.css`；`DynamicIcon.vue` 一次性 import ~70 图标且与 `utils/iconMap.js` 两套映射；死依赖 tinymce/sockjs/stompjs/unplugin-icons 等
- **F-M3 localStorage user 直读模式**：standalone 页面裸 `JSON.parse(localStorage.getItem('user'))` 无 try/catch（`oht/index.ts:91`、`ws/index.ts:333`、`study.vue:130`、`ExamList.vue:154` 等）
- **F-M4 useCrudPage 无 catch**：`hooks/useCrudPage.js:39-49` load() 只有 try/finally，11 个使用页面裸调 → unhandled rejection
- **F-M5 登出失败 token 残留**：`stores/user/index.ts:77-81` logout 失败则 clearToken 不执行；`layouts/head/index.vue:173` 无 try/catch；`localStorage 'user'/'userId'` 登出不清理
- **F-M6 oht 定时器泄漏**：`stores/modules/oht/index.ts:316-324` `clearAllTimers` 全项目零调用 → 登出后幽灵提醒/跨用户污染
- **F-M7 权限体系形同虚设**：`hooks/usePermission.js:14-19` `hasPermission` 恒 `role==='admin'` 而 role 默认即 admin → 恒 true；`a6voice/index.vue:169` 直接 `hasPermission = () => true`
- **F-M8 动态路由中文 name 冲突**：`stores/modules/menu/index.ts:46,65` route name 用中文菜单名，与 `constants.js:34,87` 静态中文名同池，同名 addRoute 静默覆盖
- **F-M9 侧边栏三级菜单渲染空子菜单**：`layouts/sider/index.vue:27-31` 递归第三层硬截断
- **F-M10 downloadExcel 全局 responseType 不还原 + 多数调用 fire-and-forget**（`request.js:127-165`），失败即 unhandled rejection
- **F-M11 `RankBoard2.vue:13` jobLevel 判断可疑**：`jobLevel?.indexOf('100,102,103,104,105') === -1` 单个值与逗号串比较恒真 → 报表按钮恒显示（原业务逻辑，需确认语义）
- **F-M12 通知占位**：`layouts/head/index.vue:37` 角标硬编码 `:count="9"`；MessageBox 空壳；`ws/index.ts:499-503` 每条推送弹新窗口
- **F-M13 dict store 缓存缺陷**：`stores/modules/dict/index.ts` 多处 `this.getDictList()` 不 await → 首渲染空字典；缓存无版本无过期

### 🟢 低严重度
- **F-L1** `admin/cyt/articalManage/` 目录拼写残留（artical→article 改名不彻底）
- **F-L2** 部分页面 `await` 无 try/catch，失败后 loading 不复位（如 `Brush.vue getBrushConfig`）
- **F-L3** TS 形同虚设：`tsconfig.json:8` `"strict": false`；stores `state: any`；`any` 共 87 处
- **F-L4** 死代码：`router/index.js:162-175` redirectToLogin、`constants.js:123-131` defaultPathRoute、`layouts/navigation/index.vue` 旧布局（引用不存在的 store）、`user/index.ts:36-41` switchRoles 等
- **F-L5** Element 残留：`.is-bordered` 死样式 3 处（`DailyTask.vue:511`、`TestView.vue:305`、`QuestionView.vue:157`）；EP 色板；200+ 处 `label-width` 写法
- **F-L6** 生产 console 被 `esbuild.drop: ['console']` 全丢（145 处错误日志失去排查价值）
- **F-L7** `menu/index.ts:20` 组件解析只认 `views/<path>/index.vue`，其余全落 404（菜单与路由解耦脆弱）
- **F-L8** `CustomTable.vue:22` rowKey 假定字符串，函数/嵌套路径失效
- **F-L9** `ws/index.ts:599-608` 通知权限被拒时 `new Notification()` 抛 SecurityError

### ✅ 亮点
CustomTable 桥设计（全项目无一处直接 `<t-table>`，迁移彻底）；路由初始化编排（standalone/防重复/精确清理/菜单联动）；hooks 设计（settled 防重复、显式 destroy）；AsyncECharts 按需注册范本；错误处理意识（try/catch+MessagePlugin 普遍）；v-for 全带 :key、无 console.log/TODO 残留；eslint 基线已建立

---

## 3. 后端架构审计（已完成）

### 🔴 高严重度

**B-H1 未认证写接口（50+，系统性）**：`SaTokenConfigure.java:21-31` 只注册 `new SaInterceptor()`（仅注解式鉴权），**无全局登录检查**——凡未标注 `@SaCheckLogin/@SaCheckPermission/@SaCheckRole` 的接口匿名即可调用。高危案例：
- **oht 角色管理**（匿名可改他人权限）：`RoleListController.java:34/49/64`（删/改/增用户角色）
- **flow**：`FlowConfigController.java:36/44/59/74`（审批模板增改，可写动态表名）、`FlowApproveController.java:30`（审批放行）、`FlowProxyController.java:22/28/33`
- **college**：`CourseExamTemplateController.java:33/45/57`（考试模板）、`CourseExamController.java:72/85`（交卷/刷学习）、`StudyLogController.java:44/79`（报名/刷学分）、`CourseFileController.java:33/45/78`、`EvaluateController.java:38`
- **edu**：`ExamAreaController`/`ExamBookController`（预约增删改）、`ExcelImportController.java:30/41`（名单导入）、`QuesTestController.java:39/50`、`QuesScoreController.java:107/152`、`QuesBankController.java:83`、`ExamInfoController.java:75`
- **sys**：`TodoController.java:79`（给任意用户写日程）、`AdController`/`JobInfoController`（广告/岗位 CRUD）、`LoginController.java:82`（刷日志）
- **hr/rpa/youngTalk**：`BirthLeaveController`/`SubsidyUnitController`/`MoodPunchController`、`ToolReportController.java:39`、youngTalk 全部写接口（Article/Reply/Comment/Like/Collect/Member/Progress/Message）

**B-H2 WebSocket 业务命令 IDOR**：`websocket/service/MessageHandle.java:223-431` 任意登录用户可对**任意 caseId** 执行 cancel/takeOrder/pcompCase/bcompCase/newTask/refuse，未校验发起人/接单人/主任身份（仅 :432-456 admin 通道有校验）

**B-H3 7 个功能性 bug**（已抽查验证其中 2 个）：
- `sys/controller/QuickUrlController.java:76`：DELETE /record 实际调用 `insertQuickUrl`——**删除变新增**
- `edu/controller/BookInfoController.java:89-95`：PUT /list 实际调用 `bookListService.delete`——**"更新预约"实为删除**
- `common/controller/FileUpload.java:279`：下载 `os.write(buff, 0, buff.length)` 应为 `i`——**下载文件尾混脏字节**
- `websocket/service/LocalWsStateService.java:55-56`：`sessionMap.remove(userId)` 后 `put(userId, sessionMap.get(userId))` 必为 null → **NPE**（已验证）；`WebSocketServer.java:166` onOpen 调用，NPE 被 :161 catch 吞掉 → **生产单机模式房间注册失败**（Redis 模式正常）
- `common/controller/FileUpload.java:94-120`：写盘失败只记日志不返回——**上传假成功**
- `college/controller/CollegeReportController.java:144-148`：循环多次 `EasyExcel.write(...).doWrite()` 同一响应流——**导出文件损坏**
- `youngTalk/controller/ArticleController.java:640-642`：删除失败返回值被丢弃——**失败仍返回成功**

**B-H4 破坏性导入（先删后导，无事务）**：`ExcelImportController.java:34/49`（`deleteAll()`/`deleteByExamCode()` 后解析）、`college/TeachGroupController.java:64`——解析抛异常/文件缺失时数据已清空

**B-H5 路径穿越（任意文件读取）**：`college/TeachGroupController.java:62-66`、`edu/ExcelImportController.java:32-36/47-51` 的 `@PathVariable fileName` 直接拼 `uploadPath + "excel" + fileName` 无 `..` 校验（正面：common FileUpload 已封堵）

**B-H6 认证/加密设计缺陷**：`LoginController.java:39-70` 无次数限制/验证码且区分"用户不存在/密码错误"（账号枚举）；`:88-119` modifyPassword 无登录校验、czyCode 来自请求体（**未认证可改任意人密码**）；`RpaController.java:27-68` 匿名返回全员花名册；`CollegeReportController.java:57` 权限被注释、`:74-89` 明细导出无鉴权

**B-H7 循环依赖**：**已核实 `application.yml:8` 为 `allow-circular-references: false`**，且注入图谱核查无环——维持 false（修正早前版本误判；flow 模块是未来最易引入环的地方）

### 🟡 中严重度
- **B-M1 事务在 Controller 层**：`QuesBankController.java:99`、`ExamInfoController.java:76`；多处多写编排无事务（StudyLogController:44-62 手动补偿、ExcelImportController 导入）
- **B-M2 youngTalk 鉴权是坏的 NPE 式"拦截"**：`Util.java:348-354` 未登录返回 null，youngTalk 25 处写 `userId.equals("0")`——匿名请求直接 NPE→500（非 401 非授权），且每次插入 ErrorLog
- **B-M3 N+1 查询普遍**：flow 各报表 per-item 查询、`CourseServiceImpl.java:117-118`、`QuesTestServiceImpl.java:81-83/97-99/104-112`（:111 `subList(0, quesNum)` 题量不足越界）、`CaseServiceImpl.java:144-184`（每员工 5 次）、`EmployeeController.java:87-100`、`StatusJourController.java:149-256`（控制器层双层循环查库）
- **B-M4 控制器承载业务逻辑**：`CourseExamController.java:85-104`（考试提交编排）、`QuesScoreController.java:108-149`（超时判定/自动交卷）、`BookInfoController.java:149-168`、`ArticleController.java:50-162`、`EmployeeController.java:57-103/210-332` 等（查询组装+报表 IO+事务性写编排三类）
- **B-M5 DataCache 非原子刷新**：`DataCache.java:90-115` 先 `EMPLOYEE.clear()` 再逐条 put，无 try/catch——刷新期间全站读到空/半量缓存，失败后保持空态至下个整点（`PerfListController.java:82` 等 NPE 风险）
- **B-M6 权限加载无缓存**：`StpInterfaceImpl.java:36-44` 每次 @SaCheckPermission 查 2 次库
- **B-M7 异常处理隐患**：ErrorLog 插入在 handler 内（DB 故障时 handler 自身再抛，兜底失效）；`ErrorLogServiceImpl.java:71-74` getNewId 为 max+1 并发不唯一；`DashboardController` 用 `e.printStackTrace()`；Excel 监听器 `doAfterAllAnalysed` 无条件覆盖为成功（**导入有错行仍返回成功**）
- **B-M8 配置**：Druid dev 无 IP 白名单；Swagger `enabled` 变量计算后未使用（prod 未禁用 springdoc，/swagger-ui、/v3/api-docs 可能匿名可访问）；上传 max-file-size 500MB 无业务限制；`ws.multi-instance: true` 默认（无 Redis 启动即依赖失败）
- **B-M9 其他 NPE/健壮性**：`ConnectInitController.java:89` `user.getUser().getJobLevel()` 未判空；`BookInfoController.java:159-160` 先 getBookDate 后判空；`QuesScoreController.java:113`；`FlowConfigController.java:65/80`；`ChatRecordFile.java:60-65` 文件创建失败后 `new PrintWriter(null)` NPE；RedisWsStateService 状态 key 无 TTL；`WsStateConfiguration` 监听器未注册 Bean/无 @PreDestroy

### 🟢 低严重度
- **B-L1** `pom.xml:158-164` 未使用 TestNG；`DataPage.java:25-37` 非 Page 时静默 0/null；`Msg.java:13` extends HashMap 类型安全弱
- **B-L2** `Util.getsecret/getdesecret` 可逆弱算法（PasswordUtil 已注明下线期限）；中文文件名导出未 RFC 5987 编码
- **B-L3** 少量 `select *`（`BirthLeaveMapper.xml:276`、`db/TableMapper.xml:288/297`）；`DataCache.java` 放 common.controller 包名误导
- **B-L4** `MenuController.java:85-86` getRoleById 未判空；`EmployeeController.java:68` 无 queryType NPE；`FileUpload.java:87-93` 逐字节 flush；`PerfListController.java:27-40` GET 触发写操作

### ✅ 亮点
分层干净（**60+ service impl 无跨模块 Mapper 注入**，跨模块全走 service 接口）；SQL 注入防护成体系（`Util.buildOrderByClause`/`isValidSqlIdentifier`/BrushScore 排序列白名单/flow 动态表名列白名单，高危 ${} 实际不可注入）；密码 BCrypt 渐进升级；WebSocket 握手鉴权 + admin 通道权限 + 512KB 上限 + 多实例中继设计；BdExceptionHandler 13 类异常+兜底不回传细节；文件上传白名单+魔数校验+nosniff；ThreadLocal SimpleDateFormat；循环依赖已显式关闭且核查无环；prod 显式关闭 Redis 依赖

---

## 4. 安全审计（已完成）

### 🔴 高严重度（可直接利用）

**H1. 硬编码密钥/凭据全面泄露**（已抽查验证）
- `backend/.../common/util/AESUtil.java:14-15`：SECRET_KEY=`cmbccdWH-wei6130`、IV 硬编码且与前端一致（`frontend/src/utils/cryptoService.js:3-4`），已进入构建产物
- `application-{dev,uat,pro}.yml`：SSL keystore 密码 `wqqlxr6130`、admin 默认口令 `111111`、Oracle 密码 `trmusr`/`auxsusr`、Druid 口令 `wei6130/wei6130`、Redis 密码 `wqqlxr6130` 全部带默认值入库
- `backend/src/main/resources/server.p12`、`keystore-uat.p12`：TLS 私钥直接入库
- **影响**：AES 传输加密形同虚设；TLS 私钥随仓库分发；攻击者可解密登录密码密文

**H2. Druid 监控台未受 Sa-Token 保护 + 弱口令**
- `application-pro.yml:48-53` 无 IP 白名单；`/druid/*` 独立 Servlet 不走 MVC 拦截器（`SaTokenConfigure.java:21-31` 只覆盖 MVC `/**`）
- **影响**：未登录可访问，弱口令 `wei6130/wei6130` 登录后可见全部 SQL/连接池信息

**H3. 越权（水平+垂直）：大量接口缺失权限校验 + 请求体 userId 直操他人数据**（已抽查验证 3 处）
- 确认案例：
  - `ExcelImportController.java:30-54`：无权限注解，`deleteByExamCode` 先删后导，fileName 路径变量可含 `..` → 任意登录用户可删任一考试成绩名单
  - `DailyScoreController` + `DailyScoreServiceImpl:135-141,176-184`：请求体 ploNum 覆盖他人每日一学/心情/答题
  - `QuesScoreController.java:152-162`：按请求体 journo 提交他人考试（可提前交卷/写错误分数）
  - `youngTalk/ArticleController.java:401-420`：所有权校验用请求体 pubUser 可绕过；`:628-644` 删除无校验
  - `sys/TodoController.java:52-77,96-123`：查看/删除/篡改他人日程
  - `flow/FlowConfigController.java:36-88`：审批链模板 CRUD 无权限
  - `college/CourseFileController.java:33-55`：课程附件增改无权限
  - `sys/MenuController.java:99`：`@SaCheckPermission("menu:list")` 被注释（已抽查确认）
- **系统性问题**：约 30 个 Controller 零 `@SaCheckPermission`（JobInfo/Ad/Duty/ErrorLog/UserFiles/Dashboard/BrushScore(12映射)/DailyScore/PerfList/SubsidyUnit/StatusJour/RoleList/FlowApprove/FlowGqsq/FlowProxy/FlowReport/PerfFlag 等），仅靠前端菜单隐藏做"伪鉴权"

**H4. 存储型 XSS：富文本 v-html 无净化**（已抽查：全项目 20 处 v-html）
- 前端未净化点：`a6voice/view/components/view.vue:47`、`Comment.vue:26,54`、`SystemNotice.vue:39,87,125`、`Survey.vue:48`、`Item.vue:56`、`admin/cyt/*/Survey.vue:40,126`、`Item.vue:64,174,208`、`rpa/index.vue:42`、`toolDetail.vue:21`、`DailyTask.vue:75`、`college/course/view.vue:68`、`onlineHelper2/ViewDialog.vue:27` 等
- 后端无任何输出过滤（无 Jsoup/HtmlUtils）；仅 2 处用 DOMPurify
- **影响**：发帖/评论即会话劫持（配合 M1 localStorage token）

**H5. 生产明文 HTTP/WS 传输**
- `frontend/.env.prod:2,5`：`http://10.47.81.182/ulms/api`、`ws://wei6130.top:8080/ulms/`；`HttpConnectorConfig.java:28-37` 8080 HTTP 与 8443 HTTPS 并存不跳转（需人工确认生产实际暴露面）

### 🟡 中严重度
- **M1** token 明文存 localStorage（`utils/auth.js:1-17`）+ 员工信息（含手机号）入 localStorage（`stores/user/index.ts:67-68`）
- **M2** 登录无验证码/无限速（`RandomValidateCodeUtil.java` 存在但未引用；`LoginController.login` 可撞库）
- **M3** 会话策略：timeout 86400s、active-timeout:-1、is-concurrent/is-share:true（同账号共 token）、is-read-body/is-read-cookie:true（token 可从 body/cookie 读）
- **M4** CORS 过宽：`CorsConfig.java:37-52` 放行 192.168.*/10.47.81.*/公网 IP + `setAllowCredentials(true)`；`LoginController.java:29`、`EmployeeController.java:34` 裸 `@CrossOrigin` 全放行
- **M5** 文件下载免鉴权：`FileUpload.java:253` `@SaIgnore` GET `/upload/getFile/**`，文件名秒级时间戳+3位随机可暴力枚举
- **M6** 操作日志存敏感数据：`OperLogAspect.java:91-102` 全量请求/响应入库（含密码密文）；`:159` 异常堆栈
- **M7** Swagger：dev/uat 开放；prod 未显式关闭 springdoc（需确认 /v3/api-docs 可达性）
- **M8** 上传 `max-file-size:500MB` + 黑名单策略（未禁 shtml/ps1/csv/json），建议改白名单
- **M9** 越权细节：`ProgressController.java:40`、`MemberController.java:52` 请求体 userId；`QuesDisputeController.java:51` 查他人申诉；`UserFilesController.java:22-32` 列全部附件元数据
- **M10** `ArticleController.java:425-458` takeItem 任意用户认领任意项目成为 PM（需业务确认）
- **M11** `ChatRecordFile.java:59` roomName 来自 caseId 拼路径（需确认 caseId 生成规则）

### 🟢 低严重度
- **L1** `Util.getsecret/getdesecret` 可逆变换；已新增 PasswordUtil（BCrypt 存量透明升级），存量密文仍可逆建议一次性全量重置
- **L2** 前端密码框 `maxlength="10"`（`login/index.vue:31,55-63`）——弱密码策略
- **L3** 项目根 `applog/` 需确认加入 .gitignore
- **L4** `FileUpload.java:279` 写文件用 `buff.length` 而非实际读取长度 `i`（偶发尾字节垃圾）
- **L5** 前端仍调 `/getPw`（`api/auth.js:87`）后端已无此端点——死代码/拦截器残留排除项
- **L6** `LoginController.modifyPassword` 未校验"操作者==目标用户"
- **L7** 各 application-*.yml 默认口令建议全部改为环境变量注入

### ✅ 亮点
BCrypt 迁移+存量升级；上传路径穿越防护（`FileUpload.java:44-48,129-134`）与魔数校验（`:160-165,227-251`）；SQL 动态拼接白名单（`Util.isValidSqlIdentifier`/`buildOrderByClause`/`BrushScoreServiceImpl.java:158-159`）；全局异常不向客户端回传堆栈；文件下载 `nosniff`；`Util.userIdByShiro()` 服务端取用户落地广泛；logout 服务端失效；WebSocket 握手鉴权；Druid wall 过滤

### 📌 需人工确认
① is-read-cookie 是否导致登录写 cookie（影响 M3/M4）；② caseId 生成规则（M11）；③ 生产是否确有公网/明文暴露面（H5）；④ prod 下 springdoc `/v3/api-docs` 可达性（M7）；⑤ 各零权限接口的业务归属（H3）

---

## 5. 汇总与优先级

### 修复优先级建议
| 优先级 | 问题 | 修复成本 |
|---|---|---|
| P0（立即） | ① HIGH-1 axios-retry 双编码（拦截器幂等化，已验证）② **B-H3 后端 7 个功能 bug**（QuickUrl 删除变新增、BookInfo 更新变删除、FileUpload 下载脏字节/上传假成功、LocalWsStateService NPE、报表导出损坏、删除失败返回成功）③ F-H1 Identity.vue store 误用 ④ F-H2 surveyManage env 变量 ⑤ H1 密钥/口令硬编码 ⑥ H2 Druid 加固 | 各 1-3 处改动 |
| P1（尽快） | ⑦ **B-H1 未认证写接口 50+**（SaTokenConfigure 加全局登录拦截 + 写接口补注解）⑧ H3 越权/所有权服务端化 ⑨ B-H5 路径穿越封堵 ⑩ B-H4 导入加事务+校验 ⑪ H4 XSS 净化（20 处 v-html）⑫ B-H2 WebSocket IDOR ⑬ F-H4 拦截器错误处理口径 | 全局拦截器 + 50+ 注解 |
| P2（规划） | ⑭ M1 token HttpOnly Cookie/CSP ⑮ M2 登录验证码/限速 ⑯ M4 CORS 收敛 ⑰ B-M1 事务下沉 ⑱ B-M5 DataCache 原子刷新 ⑲ F-M1/F-H6 去环 ⑳ F-M2 包体积拆包 | 跨模块改造 |
| P3（持续） | ㉑ 测试补覆盖（登录/权限/成绩/审批优先）㉒ 前端 Vitest 起步 ㉓ B-M3 N+1 批量查询 ㉔ B-M2 youngTalk 鉴权重写 ㉕ 清理死代码/死依赖/articalManage/TestNG ㉖ F-M11 jobLevel 逻辑确认 | 迭代式 |

### 风险提示（需人工确认）
- 生产是否确以明文 HTTP/WS 对外（H5）
- prod 下 springdoc `/v3/api-docs` 可达性（M7）
- is-read-cookie 是否导致登录写 cookie（M3/M4）
- caseId 生成规则（M11）
- 零权限接口的业务归属（H3）

### 总结
系统**基础工程扎实**（异常处理、SQL 白名单、上传校验、BCrypt 迁移、CustomTable 桥、hooks 沉淀、测试风格良好），但存在三类系统性短板：
1. **授权模型缺失**（安全 H3 / 后端 B-H1）：30+ 接口零权限注解、多处请求体 userId 越权、两处所有权校验可绕过，覆盖考试/成绩/审批流/日程/帖子等核心数据
2. **密钥与弱口令随仓库分发**（H1/H2/H5）：AES 密钥前端可提取、TLS 私钥入库、Druid/DB/Redis 弱口令，传输加密形同虚设
3. **前端请求管线重试双编码缺陷**（HIGH-1，实测复现）与**富文本 XSS 面**（H4，20 处 v-html 无净化）

测试覆盖 90% 空白是长期质量风险。建议按 P0→P3 优先级迭代修复，每项改动经编译/构建/测试/lint 验证，修复后再做一次回归审计。
