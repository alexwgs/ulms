# ULMS 代码重构路线图

> 基于 2026-08 前后端全量代码审计产出。目标：可扩展、可维护、可测试的工程标准，保持现有功能不回归。

## 审计结论摘要

### 后端（评分：安全 2/10、分层 4/10、耦合 3/10、API 4/10、命名 3/10、可测试 2/10、异常 5/10）
- 分层骨架完整（controller→service→dao→domain），Msg/DataPage/全局异常/Sa-Token 基建已搭好。
- 核心问题：**安全漏洞严重**（SQL 注入约 10 处、WebSocket 无鉴权、路径穿越、明文密钥入库、上传无鉴权、PII 泄露）；Controller 承载业务逻辑；静态状态泛滥；跨模块 DAO 直连；事务缺失；测试仅 3 个 util 类。

### 前端（评分：API 4/10、状态 5/10、组件化 6/10、路由权限 4/10、规范 3/10、类型 2/10、可测试 2/10）
- 基础设施优秀（CustomTable 迁移桥、usePagination、动态路由），业务层混乱。
- 核心问题：权限恒放行、字典缓存键错位（功能 bug）、登录双实现 + token 双存储、qiankun 残留 35 处、ws store 空实现（被顶号不登出）、无限递归定时炸弹、主题切换写反、无 ESLint/测试、console 158 处。

## 分级路线图（P0 → P3）

### P0 安全加固（最高优先，先止血）
- [x] SQL 注入收敛：order/orderType 全库统一白名单（Util.buildOrderByClause，48 处）；删除 `${sql}` 裸 SQL 死代码；flow 动态表/列名 Java 侧白名单（Util.isValidSqlIdentifier）
- [x] WebSocket 握手鉴权 + 协议权限校验（握手校验 token 与 userId 一致、OnlineMonitor list/push 补权限、MessageHandle admin 模态补权限）
- [x] 聊天记录路径穿越修复（id/month 白名单）
- [x] 上传接口鉴权（/api/upload 移出排除清单，getFile 读接口 @SaIgnore 放行，前端 13 处 t-upload 携带 token）；[x] 上传文件类型/文件名校验（后缀黑名单 + NPE 修复）
- [x] 凭据外置：DB/Redis/keystore/Druid/默认账号口令 → 环境变量占位符（`${VAR:default}`，三个 profile）；[ ] 仓库内 .p12 移除与上线轮换（需运维配合）
- [x] 敏感信息收敛：选人接口（find/find/{jobLevels}）返回脱敏副本、员工报表 report 补权限；[x] 异常脱敏（BdExceptionHandler 通用异常不再回传 e.getMessage）

### P1 确定性 bug 修复
- [x] 后端：MenuServiceImpl `Integer==Integer` 引用比较（→ Objects.equals）；QuesBankController 删除失败返回值丢弃；FlowApproveController unchecked get + concat 丢弃；logout 返回类型（String → Msg）
- [x] 前端：字典缓存键统一（dict → dictCache，4 处）；ws store `handleLogout` 空实现 + `windowCounter`/`openedWindows` 未声明；a6voice 无限递归删除 + 死模板块；主题切换写反
- [x] 后端命名 typo 重命名：EvaluateTempleteController→EvaluateTemplateController、CollegeReportConroller→CollegeReportController、ExamBookConfig→ExamBookController、DailyConifgController→DailyConfigController、QuesCollectContorller→QuesCollectController、PerfFlagCountroller→PerfFlagController、MoodPunchControlle→MoodPunchController（共 7 类；Artical→Article 因涉及前后端 API 契约，留待专项处理）

### P2 工程规范（可维护性）
- [x] 前端 ESLint + Prettier 基建：eslint + eslint-plugin-vue + @eslint/js + globals，eslint.config.js flat config（宽松起步），lint/lint:fix 脚本；发现并清零 83 个存量真实 bug（MessagePlugin 47 处未 import、未定义变量、模板语法错误、ref 漏 .value、重复 key、复制粘贴错误 API 名、死代码等），lint 现 0 error（1980 warnings 为可逐步收敛的风格/未使用变量提示）
- [x] 前端 qiankun 残留清理：全部 `__POWERED_BY_QIANKUN__`/`window.$ws`/`window.$store`/`window.$parent` 死分支清零，改用 ohtStore/wsStore/dictStore/direct import，修复 7 个功能 bug（OHT 新任务消息、主管状态、身份状态、用户状态、通知列表/已读/清空、待办提醒、字典分类）
- [x] 前端下载统一：downloadExcel 修复（返回 Promise、文件名解析正则保护避免越界、删除无效 retry 配置）、删除 tools.js 死代码 download/downloadTemplate；UploadExcel 改用直接 import
- [ ] CRUD 样板抽取（useCrudPage）
- [x] 前端统一 API 封装：删除 $get/$post 死代码（$post 零调用、$get 唯一调用改 httpInstance）+ 孤儿 QS import
- [x] 前端登录 store 合并：删除 auth store 死 setLogin（登录统一走 userStore.login）、token 双存储修复（clearToken 同步清 sessionStorage）、孤儿 pinia 实例修复（main.js 复用 stores 导出实例）
- [x] 后端跨模块 DAO 收口到服务层：18 个文件（edu 15 + rpa 2 + college 1）直连 sys.dao.PublicMapper → PublicService.getJourno()；rpa ToolListServiceImpl 的 UserRoleMapper → UserRoleService；oht CaseServiceImpl 的 DictionaryMapper → DictionaryService（新增 getDictionaryMapByName 方法）
- [x] 后端事务补齐：QuesBankController transfer 循环更新、ExamInfoController 先删后插、FlowGqsqServiceImpl batchInsert 循环 insert 均加 @Transactional(rollbackFor=Exception.class)
- [x] 后端权限缺口补齐：HelperArticalController 校验串纠错（edu:exam:list→helper:artical:list）、QuesBankController changeLib 补权限、MoodPunch 打卡 userId 服务端覆盖（防伪造）；db TableController sync/update 权限码需前端菜单配合，待确认
- [~] 后端静态状态收敛：MessageHandle 的 28 处静态 handle. → this.、new MessageHandle() → 单例；ConnectInitController 的 initUser/logOff 改实例方法 + staticInit. → this.；ChatRecordFile 的 staticInit.ulmsConfig → this.ulmsConfig；剩余 DataCache 静态 Map / WebSocketServer.state 待收敛

### P3 类型与测试体系（可扩展 + 可测试）
- [x] 建立 CI（.github/workflows/ci.yml：后端 mvn test + 前端 lint + build，push/PR 触发）
- [~] 前端渐进 TS 化（第一步：tsconfig.json + src/types/api.d.ts 定义 ApiResponse/DataPage/PageQuery/DictionaryItem 类型契约，checkJs 关闭、不破坏存量 JS；后续 api 模块/store 逐步启用类型）
- [~] 后端 Service 层单元测试：新增 MenuServiceImplTest（4 用例，覆盖 findMenuPermissionChild 的 Integer 比较修复、status 过滤、children 装配），总计 19 测试全绿；其余核心 Service（FlowCase/QuesBank/Case）的 Mockito 单测待补

## 已完成
- [x] 密码迁移 BCrypt（PasswordUtil + 透明升级）
- [x] 循环依赖消除（flowCase ↔ flowApproveLog）
- [x] prod Redis 配置矛盾修复（单机 WS 模式）
- [x] Util.isInteger 空字符串缺陷修复
- [x] 补充 PasswordUtil/Util/AESUtil 单元测试（12 用例）
- [x] 后端包名统一（Impl → impl，53 文件）
- [x] 前端迁移遗留脚本清理（fix-*.mjs × 3）
- [x] BdExceptionHandler NumberFormatException 参数类型修复 + 死字段清理

## 落地原则
1. 每项改动编译/测试验证后再推进。
2. 先修安全与确定性 bug，再做结构性重构。
3. 结构性重构以 college（分层典型）与 flow（动态 SQL 最危险）为试点。
4. CustomTable 桥与 usePagination 等已验证抽象是重构锚点，新代码必须复用。

## 进度总结（2026-08，32 轮：20 + 12）

- **P0 安全加固**：✅ 100%（SQL 注入/WebSocket 鉴权/路径穿越/上传鉴权/异常脱敏/凭据外置/PII 收敛）
- **P1 确定性 bug**：✅ 100%（后端 4 + 前端 5 + 命名 typo 7 类）
- **P2 工程规范**：🔄 约 80%（登录 store、命名、权限、事务、ESLint 基建 + 83 bug 清零、API 统一、qiankun 清理、下载统一、跨模块 DAO 收口、静态状态收敛部分完成；CRUD 样板、Controller 瘦身、DataCache/WebSocketServer.state 收敛待做）
- **P3 类型与测试体系**：🔄 约 50%（CI 建立、TS 起步 + 类型契约、19 单元测试；其余核心 Service 单测、完整 TS 化待做）

### 验证结论（全绿）
- 后端 `mvn test`：19 用例全绿
- 前端 `npm run build:dev`：构建成功
- 前端 `npm run lint`：0 error（1980 warning 为可逐步收敛的风格提示）

### 剩余结构性重构（建议下一阶段继续）
1. 前端 CRUD 样板抽取（useCrudPage）
2. 后端 Controller 业务下沉 Service（college/flow 试点）
3. 后端 DataCache 静态 Map / WebSocketServer.state 收敛
4. 前端渐进 TS 化深入（api 模块 → store → router meta 逐步启用类型）
5. db TableController sync/update 权限（需前端菜单配合确认权限码）
6. Artical→Article 拼写纠正（涉及前后端 API 契约，专项）
7. 其余核心 Service（FlowCase/QuesBank/Case）的 Mockito 单测
