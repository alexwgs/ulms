# ULMS (统一登录管理平台) 完整接口文档

> **项目**: A6广场 (Unified Login Management System)  
> **版本**: 1.3.2-dev-SNAPSHOT  
> **基础路径**: `http://localhost:8080/ulms`  
> **文档生成日期**: 2026-05-31  

---

## 目录

1. [概述](#1-概述)
2. [通用响应格式](#2-通用响应格式)
3. [认证与授权](#3-认证与授权)
4. [系统管理模块 (sys)](#4-系统管理模块-sys)
5. [学院培训模块 (college)](#5-学院培训模块-college)
6. [教育培训模块 (edu)](#6-教育培训模块-edu)
7. [工时工具模块 (oht)](#7-工时工具模块-oht)
8. [人力资源模块 (hr)](#8-人力资源模块-hr)
9. [流程审批模块 (flow)](#9-流程审批模块-flow)
10. [青年说论坛模块 (youngTalk)](#10-青年说论坛模块-youngtalk)
11. [RPA工具模块 (rpa)](#11-rpa工具模块-rpa)
12. [助手模块 (helper)](#12-助手模块-helper)
13. [数据库管理模块 (db)](#13-数据库管理模块-db)
14. [WebSocket模块 (ws)](#14-websocket模块-ws)
15. [通用模块 (common)](#15-通用模块-common)

---

## 1. 概述

### 1.1 技术栈

| 层级 | 技术 |
|------|------|
| 框架 | Spring Boot 3.5.7 |
| 语言 | Java 17 |
| 数据库 | Oracle (Druid连接池) |
| ORM | MyBatis 3.0.5 + PageHelper |
| 缓存 | Redis (Lettuce) |
| 认证 | Sa-Token (token-based) |
| 实时通信 | Netty WebSocket |

### 1.2 认证机制

- **Token名称**: `authorization` (通过Header/Body/Cookie传递)
- **Token有效期**: 24小时 (86400秒)
- **Token风格**: UUID
- **并发登录**: 允许同一账号多地登录，共用一个token
- **密码加密**: 前端AES加密传输 → 后端自定义`Util.getsecret()`/`Util.getdesecret()`加密存储

### 1.3 权限控制

使用 `@SaCheckPermission("module:action")` 注解控制接口权限。权限标识命名规则：`模块:资源:操作`。

### 1.4 开发环境配置

```yaml
spring:
  datasource:
    url: jdbc:oracle:thin:@wei6130.top:1521/TRMUSR
    username: trmusr
    password: trmusr
```

**测试账号**: 员工编号 `655012`，密码 `111111`

---

## 2. 通用响应格式

### 2.1 Msg — 统一响应体

`Msg` 类继承自 `HashMap<String, Object>`，所有接口统一返回 `Msg` 对象。

**JSON结构**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": { ... }
}
```

**状态码枚举**:

| 状态 | code值 | 说明 |
|------|--------|------|
| SUCCESS | 200 | 操作成功 |
| WARN | 301 | 警告（如未授权、登录过期） |
| ERROR | 500 | 操作失败 |

**常用静态工厂方法**:

| 方法 | 说明 |
|------|------|
| `Msg.success()` | 成功，msg="操作成功" |
| `Msg.success(String msg)` | 成功，自定义消息 |
| `Msg.success(Object data)` | 成功，带data载荷 |
| `Msg.success(String msg, Object data)` | 成功，自定义消息+data |
| `Msg.warn(String msg)` | 警告（code=301） |
| `Msg.error()` | 失败，msg="操作失败" |
| `Msg.error(String msg)` | 失败，自定义消息 |

**链式调用**: `Msg.put(key, value)` 返回 `this`（Msg对象），支持链式添加自定义字段：
```java
Msg.success("登录成功").put("token", "xxx").put("user", userObj).put("userId", "655012")
```

对应JSON:
```json
{
  "code": 200,
  "msg": "登录成功",
  "token": "xxx",
  "user": { ... },
  "userId": "655012"
}
```

### 2.2 DataPage&lt;T&gt; — 分页响应体

分页查询统一使用 `DataPage<T>` 封装，作为 `Msg` 的 `data` 字段返回。

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "total": 150,
    "list": [ ... ],
    "pageNum": 1,
    "pageSize": 20,
    "pages": 8,
    "size": 20
  }
}
```

| 字段 | 类型 | 说明 |
|------|------|------|
| total | long | 总记录数 |
| list | Array | 当前页数据 |
| pageNum | int | 当前页码（从1开始） |
| pageSize | int | 每页记录数 |
| pages | int | 总页数 |
| size | int | 当前页实际记录数 |

**分页参数**（Query String）:
| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| pageNum | int | 否 | 页码，默认1 |
| pageSize | int | 否 | 每页条数，默认10 |
| order | String | 否 | 排序字段 |
| orderType | String | 否 | 排序方式（asc/desc） |

### 2.3 Tree — 树形节点

```json
{
  "id": "43000100",
  "label": "信息技术部",
  "children": [
    { "id": "43000101", "label": "开发一室", "children": [] }
  ]
}
```

| 字段 | 类型 | 说明 |
|------|------|------|
| id | String | 节点唯一标识 |
| label | String | 显示文本 |
| children | Array&lt;Tree&gt; | 子节点 |

### 2.4 内存缓存 (DataCache)

系统启动时加载以下数据到 JVM 内存，并每小时（7:00-22:00）刷新：

| 缓存 | 类型 | Key | 说明 |
|------|------|-----|------|
| EMPLOYEE | ConcurrentHashMap | ploNum | 所有员工信息 |
| DEPARTMENT | ConcurrentHashMap | deptNum | 所有部门信息 |
| JOBINFO | ConcurrentHashMap | jobLevel | 岗位级别信息 |
| Dict | ArrayList | — | 所有字典数据 |
| JOB | ArrayList | — | 岗位信息列表 |

---

## 3. 认证与授权

### 3.1 登录

**[POST] /ulms/login**

- **权限**: 公开（无需认证）
- **请求体** (`application/json`):
```json
{
  "czyCode": "655012",
  "password": "AES加密后的密码"
}
```
- **成功响应** (code=200):
```json
{
  "code": 200,
  "msg": "登录成功！欢迎 张三 使用A6广场",
  "token": "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
  "userId": "655012",
  "user": {
    "ploNum": "655012",
    "ploName": "张三",
    "deptName": "信息技术部",
    "groupName": "开发一室",
    "avatar": "upload/getFile/avatar/avatar.png",
    "ploStatus": "00",
    "jobLevel": "L04",
    ...
  }
}
```
- **错误响应**:
  - `code=401, msg="用户不存在"`
  - `code=401, msg="账户未设置密码，请联系管理员"`
  - `code=401, msg="密码错误"`

### 3.2 其他认证接口

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/ulms/user/info` | 需登录 | 获取当前用户信息 |
| POST | `/ulms/log` | 公开 | 记录操作日志 |
| PUT | `/ulms/modifyPassword` | 需登录 | 修改密码 |
| GET | `/ulms/logout` | 需登录 | 退出登录 |
| GET | `/ulms/unauth` | 公开 | 登录过期提示 |
| GET | `/ulms/403` | 公开 | 无权访问提示 |
| GET | `/ulms/error/500` | 公开 | 应用错误提示 |

### 3.3 修改密码

**[PUT] /ulms/modifyPassword**

- **请求体**:
```json
{
  "czyCode": "655012",
  "oPassword": "AES加密的旧密码",
  "nPassword": "AES加密的新密码",
  "cPassword": "AES加密的确认密码"
}
```
- **响应**: `Msg.success("密码修改成功！")` 或 `Msg.error("密码验证错误！")`

---

## 4. 系统管理模块 (sys)

### 4.1 员工管理 (EmployeeController)
**基础路径**: `/ulms/employee/`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/employee/list/{ploStatus}` | `employee:list` | 分页获取员工列表 |
| POST | `/employee/disppatch/role/{userId}` | `employee:role` | 分配用户角色 |
| POST | `/employee/avatar` | 需登录 | 更新头像 |
| GET | `/employee/birthday/{month}` | 公开 | 按月份获取生日信息 |
| GET | `/employee/find` | 公开 | 模糊搜索员工 |
| GET | `/employee/find/{jobLevels}` | 公开 | 按岗位级别过滤 |
| GET | `/employee/tree/{type}` | 公开 | 获取部门-组别-人员树 |
| GET | `/employee/report` | 公开 | 导出在职人员Excel |

**GET /employee/list/{ploStatus} 详细参数**:
- `ploStatus` (Path): 人员状态过滤，如 "00"(在职)、"01"(离职)、"all"(全部)
- Query String: `query`(搜索关键词), `queryType`(搜索字段: ploNum/ploName/deptNum/deptGroup/agentNum), 分页参数

**响应**: `DataPage<Employee>` 包含：ploNum, ploName, deptName, groupName, batchGroup, jobLevel, ploStatus, agentNum, roles, avatar等

**POST /employee/disppatch/role/{userId}**:
```json
{
  "roles": ["L03", "L04"]
}
```

### 4.2 部门管理 (DepartmentController)
**基础路径**: `/ulms/department`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/department/tree` | `department:list` | 获取部门树形结构 |
| GET | `/department` | 公开 | 获取部门扁平列表 |
| DELETE | `/department/{deptNum}` | `department:delete` | 删除部门（需4位编号） |
| PUT | `/department/{deptNum}` | `department:update` | 更新部门信息 |
| POST | `/department` | `department:add` | 新增部门 |
| GET | `/department/tree/{type}` | 公开 | 按类型获取部门树 |

### 4.3 菜单管理 (MenuController)
**基础路径**: `/ulms/menu`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/menu/bsAuthMenu` | 需登录 | 后台管理菜单（按权限过滤） |
| GET | `/menu/AuthMenu` | 需登录 | 前端菜单+权限列表 |
| GET | `/menu/menu` | 公开 | 全部菜单（含禁用） |
| GET | `/menu/menus` | `menu:list` | 全部菜单 |
| POST | `/menu` | `menu:add` | 新增菜单 |
| PUT | `/menu/{id}` | `menu:update` | 更新菜单 |
| DELETE | `/menu/{id}/{pid}` | `menu:delete` | 删除菜单（pid=0同时删除子菜单） |
| GET | `/menu/{system}` | 需登录 | 按系统获取用户权限菜单 |

**AuthMenu 响应**: `{ code: 200, menu: [...], permissions: [...] }`

**Menu 对象字段**: id, name, intro, path, sort, pid, icon, status, useage, menuType, resourse, component, system, children, meta

### 4.4 角色管理 (RoleController)
**基础路径**: `/ulms/role`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/role/list` | 公开 | 分页角色列表（含权限树） |
| GET | `/role` | 公开 | 全部角色列表 |
| PUT | `/role/permission/{roleId}` | `role:permission:update` | 更新角色权限 |
| DELETE | `/role/{roleId}` | `role:delete` | 删除角色 |
| PUT | `/role/update` | `role:update` | 更新角色信息 |
| POST | `/role/add` | `role:add` | 新增角色 |

**PUT /role/permission/{roleId}**:
```json
{
  "permissionIds": "1,2,3,4,5"
}
```

### 4.5 字典管理 (DictionaryController)
**基础路径**: `/ulms/dictionary/`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/dictionary/list` | 公开 | 分页字典列表 |
| GET | `/dictionary/json` | 公开 | 全部字典（按名称分组） |
| GET | `/dictionary/list/{name}` | 公开 | 按名称获取字典项 |
| PUT | `/dictionary/update` | `dictionary:update` | 更新字典项 |
| DELETE | `/dictionary/delete/{id}` | `dictionary:delete` | 删除字典项 |
| POST | `/dictionary/add` | `dictionary:add` | 新增字典项 |

### 4.6 值班管理 (DutyController)
**基础路径**: `/ulms/duty`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/duty/my` | 需登录 | 当前月值班安排 |
| GET | `/duty/my/{dateDate}` | 需登录 | 指定月份值班安排（YYYY-MM） |

### 4.7 广告管理 (AdController)
**基础路径**: `/ulms/system/ad/`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/system/ad/index` | 公开 | 首页广告展示 |
| GET | `/system/ad/list` | 公开 | 分页广告管理列表 |
| POST | `/system/ad/record` | 公开 | 新增广告 |
| PUT | `/system/ad/record` | 公开 | 更新广告 |
| POST | `/system/ad/delete/{id}` | 公开 | 删除广告（POST方式） |

### 4.8 日历 (CalendarController)
**基础路径**: `/ulms/calendar`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/calendar/duty/{dateDate}` | 需登录 | 获取月度日历数据 |

**响应**: `{ duty: [...], todolist: [...], task: [...] }`

### 4.9 快捷链接 (QuickUrlController)
**基础路径**: `/ulms/quickUrl`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/quickUrl/list` | `quickUrl:list` | 管理员分页链接列表 |
| GET | `/quickUrl/records/{area}` | 公开 | 按区域获取启用链接 |
| PUT | `/quickUrl/record` | `quickUrl:update` | 更新链接 |
| POST | `/quickUrl/record` | `quickUrl:insert` | 新增链接 |
| DELETE | `/quickUrl/record` | `quickUrl:delete` | 删除链接 |

### 4.10 待办管理 (TodoController)
**基础路径**: `/ulms/sys`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| POST | `/sys/todo/add` | 需登录 | 新增个人待办 |
| GET | `/sys/todo/{dataDate}` | 需登录 | 按日期获取待办 |
| PUT | `/sys/todo` | 需登录 | 更新待办 |
| DELETE | `/sys/todo/{id}` | 需登录 | 删除待办 |
| POST | `/sys/todo/admin/add` | 公开 | 批量创建待办（管理员） |
| GET | `/sys/todo/list` | 公开 | 分页待办管理列表 |

### 4.11 错误日志 (ErrorLogController)
**基础路径**: `/ulms/errorLog`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/errorLog/list` | 公开 | 分页错误日志（支持优先级/类型/日期筛选） |

### 4.12 操作日志 (OperLogController)
**基础路径**: `/ulms/sys/operLog`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/sys/operLog/list` | `sys:operlog:list` | 分页操作日志列表 |
| GET | `/sys/operLog/report` | `sys:operlog:report` | 导出操作日志Excel |

### 4.13 附件管理 (UserFilesController)
**基础路径**: `/ulms/userFiles`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/userFiles/list` | 公开 | 分页附件列表 |
| GET | `/userFiles/suffix` | 公开 | 获取文件类型后缀列表 |

### 4.14 工位管理 (StationController)
**基础路径**: `/ulms/station`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/station/list` | `station:list` | 分页工位列表 |
| DELETE | `/station/{pcIp}` | `station:delete` | 按IP删除工位 |
| PUT | `/station` | `station:update` | 更新工位信息 |
| POST | `/station` | `station:add` | 新增工位 |
| POST | `/station/update` | 公开 | 用户自助更新工位 |
| POST | `/station/clientInfo` | 需登录 | 保存客户端信息 |

### 4.15 岗位管理 (JobInfoController)
**基础路径**: `/ulms/sys/jobInfo`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/sys/jobInfo/list` | 公开 | 分页岗位列表 |
| GET | `/sys/jobInfo` | 公开 | 全部岗位（缓存） |
| PUT | `/sys/jobInfo` | 公开 | 更新岗位 |
| POST | `/sys/jobInfo` | 公开 | 新增岗位 |
| DELETE | `/sys/jobInfo/{id}` | 公开 | 删除岗位 |

### 4.16 RPA接口 (RpaController)
**基础路径**: `/ulms/rpa`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/rpa/employee` | 公开 | 获取RPA用员工数据（支持科室/组别/岗位筛选） |
| GET | `/rpa/department` | 公开 | 获取RPA用部门数据 |

---

## 5. 学院培训模块 (college)

### 5.1 课程管理 (CourseController)
**基础路径**: `/ulms/college/course`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/college/course` | 公开 | 前端课程列表（仅已发布+有效期内） |
| GET | `/college/course/list` | `college:course:list` | 管理端课程列表（含CLOB） |
| GET | `/college/course/{courseId}` | 公开 | 课程详情（含讲师信息） |
| PUT | `/college/course` | `college:course:update` | 修改课程 |
| POST | `/college/course` | `college:course:insert` | 新增课程 |
| GET | `/college/course/recommend` | 公开 | 首页滚动推荐课程 |
| GET | `/college/course/command` | 公开 | 首页分类推荐课程 |

**GET /college/course/{courseId} 响应**: `{ course: {...}, teacher: {...} }`

**参数说明**: 前端列表支持 `courseType`(课程类型), `queryType`(搜索类型), `query`(关键词)

### 5.2 课程考试 (CourseExamController)
**基础路径**: `/ulms/college/course/exam`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/college/course/exam/init/{courseId}` | 公开 | 考试初始化数据 |
| GET | `/college/course/exam/start/{courseId}` | 公开 | 开始考试（生成试卷） |
| POST | `/college/course/exam/check` | 公开 | 提交单题答案 |
| PUT | `/college/course/exam/submit/{examCode}` | 公开 | 提交考试（计算总分） |

**考试初始化响应**: `{ list: [我的考试列表], setting: {考试模板配置} }`

**提交考试响应**: `{ examResult: {得分信息} }`

### 5.3 考试模板 (CourseExamTemplateController)
**基础路径**: `/ulms/college/course/exam`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/college/course/exam/template/{courseId}` | 公开 | 获取考试模板 |
| PUT | `/college/course/exam/template` | 公开 | 更新考试模板 |
| POST | `/college/course/exam/template` | 公开 | 新增考试模板 |
| DELETE | `/college/course/exam/template/{courseId}` | 公开 | 删除考试模板 |

### 5.4 课程附件 (CourseFileController)
**基础路径**: `/ulms/college/course/file`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| POST | `/college/course/file` | 公开 | 新增课程附件 |
| PUT | `/college/course/file` | 公开 | 批量更新课程附件 |
| GET | `/college/course/file/progress/my/{courseId}` | 公开 | 学习进度 |
| PUT | `/college/course/file/progress/my` | 公开 | 更新学习进度 |
| GET | `/college/course/file/progress/log` | 公开 | 学习时长日志 |

### 5.5 讲师管理 (CourseTeacherController)
**基础路径**: `/ulms/college/teacher`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/college/teacher/list` | 公开 | 分页讲师列表 |
| GET | `/college/teacher/all` | 公开 | 全部讲师 |
| GET | `/college/teacher/top` | 公开 | 推荐讲师 |
| PUT | `/college/teacher` | `college:teacher:update` | 更新讲师 |
| POST | `/college/teacher` | `college:teacher:insert` | 新增讲师 |
| DELETE | `/college/teacher/{ploNum}` | `college:teacher:delete` | 删除讲师 |

**参数**: `status`, `query`(工号/姓名), `skillType`, `skillName`

### 5.6 课程类型 (CourseTypeController)
**基础路径**: `/ulms/college/course/type`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/college/course/type/tree` | 公开 | 课程类型树（仅status=1） |
| PUT | `/college/course/type` | `college:coursetype:update` | 更新课程类型 |
| POST | `/college/course/type` | `college:coursetype:insert` | 新增课程类型 |
| DELETE | `/college/course/type/{id}` | `college:coursetype:delete` | 删除课程类型 |

### 5.7 评价管理 (EvaluateController)
**基础路径**: `/ulms/college/evaluate`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/college/evaluate/list` | 公开 | 分页评价列表（按courseId过滤） |
| POST | `/college/evaluate` | 公开 | 新增评价 |

### 5.8 评价模板 (EvaluateTempleteController)
**基础路径**: `/ulms/college/evaluate/templete`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/college/evaluate/templete/list` | 公开 | 评价模板列表 |
| GET | `/college/evaluate/templete/{tempId}` | 公开 | 模板详情 |
| POST | `/college/evaluate/templete` | `college:evaltemp:insert` | 批量新增模板 |
| PUT | `/college/evaluate/templete` | `college:evaltemp:update` | 批量更新模板 |

### 5.9 学习进度 (StudyLogController)
**基础路径**: `/ulms/college/study`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/college/study/{courseId}` | 公开 | 学习进度（报名记录+学习日志） |
| POST | `/college/study/sign` | 公开 | 报名课程 |
| GET | `/college/study/my/record` | 公开 | 学习记录（分页） |
| PUT | `/college/study/file/complete/{courseId}` | 公开 | 完成在线学习 |
| GET | `/college/study/my/point` | 公开 | 学习积分记录 |
| GET | `/college/study/my/hour` | 公开 | 学习时长记录 |

### 5.10 教学组别 (TeachGroupController)
**基础路径**: `/ulms/college/group/`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/college/group/list` | 公开 | 授课组别列表 |
| GET | `/college/group/list/user` | 公开 | 组别用户列表 |
| POST | `/college/group/add` | `college:group:add` | 新增组别 |
| POST | `/college/group/delete` | `college:group:delete` | 删除组别 |
| POST | `/college/group/upload/{fileName}` | `college:group:upload` | Excel导入组别 |

### 5.11 积分管理 (UserPointsController)
**基础路径**: `/ulms/college/point`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/college/point` | 公开 | 个人积分+排行榜Top20 |

### 5.12 报表 (CollegeReportConroller)
**基础路径**: `/ulms/college/report`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/college/report/hum/{courseId}` | `college:report:study:hum` | 人员学习情况Excel |
| GET | `/college/report/course/infomation` | 公开 | 人员学习情况查询 |
| GET | `/college/report/course/dowmload/infomation` | 公开 | 人员学习情况Excel下载 |
| GET | `/college/report/course` | `college:report:course:list` | 课程详情Excel |
| GET | `/college/report/exam/{courseId}` | `college:report:exam:list` | 考试记录Excel |
| GET | `/college/report/eval/{courseId}` | `college:report:eval:list` | 评价明细Excel |
| GET | `/college/report/view/{courseId}` | 公开 | 学习情况by科室图表 |

---

## 6. 教育培训模块 (edu)

### 6.1 考试管理 (ExamInfoController)
**基础路径**: `/ulms/edu/exam`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/edu/exam/config/list` | `edu:exam:list:self` OR `edu:exam:list` | 分页考试列表 |
| PUT | `/edu/exam/config/update` | `edu:exam:update` | 更新考试 |
| POST | `/edu/exam/config` | `edu:exam:add` | 新增考试 |
| DELETE | `/edu/exam/config/{examCode}` | `edu:exam:delete` | 删除考试 |
| GET | `/edu/exam/config/ques/temp/{examCode}` | 公开 | 抽题配置列表 |
| POST | `/edu/exam/config/ques/temp/{examCode}` | 公开 | 保存抽题配置 |
| GET | `/edu/exam/current/exam/list` | 公开 | 当前可参加考试列表 |

### 6.2 考试答题 (QuesTestController)
**基础路径**: `/ulms/edu/exam/test`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/edu/exam/test/my/list/{examCode}` | 公开 | 我的试卷题目 |
| POST | `/edu/exam/test/init` | 公开 | 初始化/生成试卷 |
| POST | `/edu/exam/test/submit/{wrongBreak}` | 公开 | 提交单题答案 |
| GET | `/edu/exam/test/report/detail/{examCode}` | `edu:ques:test:report` | 试卷明细Excel |

**wrongBreak参数**: `0`=答错交卷，`1`=答错继续

### 6.3 考试评分 (QuesScoreController)
**基础路径**: `/ulms/edu/exam/score`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/edu/exam/score/user/list` | 公开 | 考生得分列表 |
| POST | `/edu/exam/score/add` | `edu:score:user:add` | 新增考生记录 |
| DELETE | `/edu/exam/score/user/{journo}` | `edu:score:user:delete` | 删除考生记录 |
| POST | `/edu/exam/score/reset` | `edu:score:user:reset` | 重置考试状态 |
| GET | `/edu/exam/score/my/list` | 公开 | 我的历史考试 |
| POST | `/edu/exam/score/pre/check` | 公开 | 考前检查 |
| PUT | `/edu/exam/score/submit` | 公开 | 交卷 |
| GET | `/edu/exam/score/read/{examCode}` | `edu:score:read` | 阅卷 |
| GET | `/edu/exam/score/report/hum/{examCode}` | `edu:score:report:download` | 得分明细Excel |

**考前检查响应**: `{ areaFlag: Boolean, examScore: {...}, user: {...} }`

### 6.4 题库管理 (QuesBankController)
**基础路径**: `/ulms/edu/quesBank`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/edu/quesBank/list` | `edu:question:list:self` | 题库列表（含敏感信息） |
| GET | `/edu/quesBank/sensitive/list` | `edu:question:list:simple` | 题库列表（不含敏感信息） |
| POST | `/edu/quesBank` | `edu:question:add` | 新增题目 |
| PUT | `/edu/quesBank` | `edu:question:update` | 更新题目 |
| DELETE | `/edu/quesBank/{quesCode}` | `edu:question:delete` | 删除题目 |
| PUT | `/edu/quesBank/changeLib` | 公开 | 更换题目所属题库 |
| POST | `/edu/quesBank/question` | 公开 | 随机抽题 |
| PUT | `/edu/quesBank/transfer/{libCode}` | `edu:question:transfer` | 批量迁移题目 |
| GET | `/edu/quesBank/{quesCode}` | 公开 | 题目详情 |
| GET | `/edu/quesBank/report` | `edu:question:download` | 题库Excel导出 |

### 6.5 题库分类 (QuesLibController)
**基础路径**: `/ulms/edu/quesLib`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/edu/quesLib/{status}` | 公开 | 题库树形结构 |
| PUT | `/edu/quesLib` | `edu:queslib:update` | 更新题库 |
| DELETE | `/edu/quesLib/{quesLib}` | `edu:queslib:delete` | 删除题库 |
| POST | `/edu/quesLib` | `edu:queslib:add` | 新增题库 |

### 6.6 刷题配置 (BrushConfigController)
**基础路径**: `/ulms/edu/brush/config`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/edu/brush/config/list` | 公开 | 分页刷题配置 |
| POST | `/edu/brush/config/add` | `edu:brush:config:add` | 新增配置 |
| PUT | `/edu/brush/config/update` | `edu:brush:config:update` | 更新配置 |
| DELETE | `/edu/brush/config/delete/{brushCode}` | `edu:brush:config:delete` | 删除配置 |
| GET | `/edu/brush/config` | 公开 | 当前用户刷题配置（含日/月刷题数） |

### 6.7 刷题答题 (BrushScoreController)
**基础路径**: `/ulms/edu/brush/score`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| POST | `/edu/brush/score/answer` | 公开 | 提交刷题答案 |
| GET | `/edu/brush/score/wrong` | 公开 | 错题集 |
| GET | `/edu/brush/score/report/board/hum` | 公开 | 个人面板 |
| GET | `/edu/brush/score/report/board/group` | 公开 | 小组面板 |
| GET | `/edu/brush/score/report/board/dept` | 公开 | 科室面板 |
| GET | `/edu/brush/score/report/board/center` | 公开 | 学霸榜 |
| GET | `/edu/brush/score/report/board/category` | 公开 | 业务类型面板 |
| GET | `/edu/brush/score/report/hum` | 公开 | 个人刷题Excel |
| GET | `/edu/brush/score/report/group` | 公开 | 小组刷题Excel |
| GET | `/edu/brush/score/report/dept` | 公开 | 科室刷题Excel |
| GET | `/edu/brush/score/report/category` | 公开 | 业务类型刷题Excel |

### 6.8 每日一学配置 (DailyConifgController)
**基础路径**: `/ulms/edu/daily`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/edu/daily/config/list` | 公开 | 分页每日一学配置 |
| DELETE | `/edu/daily/config/{id}` | `edu:daily:config:delete` | 删除配置 |
| PUT | `/edu/daily/config` | `edu:daily:config:update` | 更新配置 |
| POST | `/edu/daily/config` | `edu:daily:config:add` | 新增配置（支持按组批量） |
| GET | `/edu/daily/question/init` | 公开 | 初始化每日答题 |

**初始化响应**: `{ flag: Boolean, dailyConfig: {...}, dailyScore: {...} }`

### 6.9 每日一学答题 (DailyScoreController)
**基础路径**: `/ulms/edu/daily/score`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| POST | `/edu/daily/score/mood/submit` | 公开 | 提交心情数据 |
| POST | `/edu/daily/score/artical/submit` | 公开 | 提交文章学习 |
| POST | `/edu/daily/score/question/submit` | 公开 | 提交问答答案 |
| GET | `/edu/daily/score/list` | 公开 | 得分统计报表 |
| GET | `/edu/daily/score/report/list` | 公开 | 报表Excel导出 |

### 6.10 考试预约时段 (BookInfoController)
**基础路径**: `/ulms/edu/book`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| POST | `/edu/book/info/list/force/book` | `edu:book:list:user` | 强制预约 |
| GET | `/edu/book/list` | `edu:book:list:list` | 分页预约列表 |
| PUT | `/edu/book/list/delete` | `edu:book:list:delete` | 删除预约 |
| PUT | `/edu/book/list` | 公开 | 更新预约 |
| GET | `/edu/book/info/list/{examCode}` | 公开 | 预约时段列表 |
| POST | `/edu/book/info` | 公开 | 新增时段 |
| DELETE | `/edu/book/info/{infoCode}` | 公开 | 删除时段 |
| GET | `/edu/book/info/list/detail` | 公开 | 预约详情（时段+已约人员） |
| POST | `/edu/book/info/list/detail` | 公开 | 创建个人预约 |

### 6.11 预约配置 (ExamBookConfig)
**基础路径**: `/ulms/edu/book/config`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/edu/book/config` | 公开 | 预约配置列表 |
| PUT | `/edu/book/config` | `edu:book:config:update` | 更新配置 |
| POST | `/edu/book/config` | `edu:book:config:add` | 新增配置 |
| DELETE | `/edu/book/config/{bookCode}` | `edu:book:config:delete` | 删除配置 |
| GET | `/edu/book/config/bookTime` | 公开 | 预约时间段列表 |
| PUT | `/edu/book/config/bookTime` | 公开 | 更新时间段 |
| DELETE | `/edu/book/config/bookTime/{journo}` | 公开 | 删除时间段 |
| POST | `/edu/book/config/bookTime` | 公开 | 新增时间段 |

### 6.12 考场管理 (ExamAreaController)
**基础路径**: `/ulms/edu/area`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/edu/area/config/list` | 公开 | 考场配置列表 |
| POST | `/edu/area/config` | `edu:area:config:add` | 新增考场 |
| PUT | `/edu/area/config` | `edu:area:config:update` | 更新考场 |
| DELETE | `/edu/area/config/{areaCode}` | `edu:area:config:delete` | 删除考场 |
| GET | `/edu/area/detail/list` | 公开 | 考场明细列表 |
| POST | `/edu/area/detail` | 公开 | 新增明细 |
| PUT | `/edu/area/detail` | 公开 | 更新明细 |
| DELETE | `/edu/area/detail/{areaCode}` | 公开 | 删除明细 |

### 6.13 题目收藏 (QuesCollectContorller)
**基础路径**: `/ulms/edu/ques/collect`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/edu/ques/collect/my` | 公开 | 我的收藏列表 |
| POST | `/edu/ques/collect/{quesCode}` | 公开 | 收藏题目 |
| PUT | `/edu/ques/collect/{journo}` | 公开 | 取消收藏 |

### 6.14 题目复议 (QuesDisputeController)
**基础路径**: `/ulms/edu/ques/dispute`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| POST | `/edu/ques/dispute` | 公开 | 创建复议 |
| PUT | `/edu/ques/dispute` | `edu:dispute:update` | 处理复议 |
| GET | `/edu/ques/dispute/{examCode}` | 公开 | 我的复议列表 |
| GET | `/edu/ques/dispute` | `edu:dispute:list` | 复议管理列表 |
| PUT | `/edu/ques/dispute/brush` | 公开 | 刷题问题反馈 |

### 6.15 Excel导入 (ExcelImportController)
**基础路径**: `/ulms/edu/excel/in`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| POST | `/edu/excel/in/daily/group/{fileName}` | 公开 | 导入每日一学分组 |
| POST | `/edu/excel/in/ques/score/{fileName}` | 公开 | 导入考试名单 |

---

## 7. 工时工具模块 (oht)

### 7.1 案件管理 (CaseController)
**基础路径**: `/ulms/oht/case`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/oht/case/list` | `oht:case:list` | 分页案件列表 |
| GET | `/oht/case/dir/list` | `oht:case:dir:list` | 业务主任接单统计 |
| GET | `/oht/case/record` | `oht:case:chat:record` | 获取聊天记录 |
| GET | `/oht/case/dept/list` | `oht:case:dept:list` | 部门时段统计+图表 |
| GET | `/oht/case/rpt/case` | `oht:case:report` | 案件明细Excel |
| GET | `/oht/case/rpt/dir` | `oht:case:dir:report` | 业务主任报表Excel |
| GET | `/oht/case/rpt/dept` | `oht:case:dept:report` | 部门时段报表Excel |

### 7.2 任务管理 (CaseTaskController)
**基础路径**: `/ulms/oht`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/oht/task/{caseId}` | `oht:case:task:push` | 案件关联任务列表 |
| GET | `/oht/task/summary` | `oht:case:task:list` | 任务汇总 |
| GET | `/oht/task/summary/report` | `oht:case:task:report` | 任务汇总Excel |

### 7.3 角色管理 (OhtRoleController)
**基础路径**: `/ulms/oht/role`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/oht/role/list` | `oht:role:list` | 分页角色列表 |
| GET | `/oht/role/get/{roleCode}` | 公开 | 按编号获取角色 |
| POST | `/oht/role/add` | `oht:role:add` | 新增角色 |
| PUT | `/oht/role/update` | `oht:role:update` | 更新角色 |
| DELETE | `/oht/role/delete/{roleCode}` | `oht:role:delete` | 删除角色 |

### 7.4 快捷消息 (QuickMemoController)
**基础路径**: `/ulms/oht/quickMemo`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/oht/quickMemo/list` | `oht:quickMemo:list` | 分页快捷消息 |
| GET | `/oht/quickMemo/list/{roleType}` | 公开 | 按角色获取消息 |
| PUT | `/oht/quickMemo/update` | `oht:quickMemo:update` | 更新消息 |
| DELETE | `/oht/quickMemo/delete/{journo}` | `oht:quickMemo:delete` | 删除消息 |
| POST | `/oht/quickMemo/add` | `oht:quickMemo:add` | 新增消息 |

### 7.5 用户角色 (RoleListController)
**基础路径**: `/ulms/oht/userRole`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/oht/userRole/list` | 公开 | 分页用户角色列表 |
| DELETE | `/oht/userRole/delete/{ploNum}` | 公开 | 删除用户角色 |
| PUT | `/oht/userRole/update` | 公开 | 更新用户角色 |
| POST | `/oht/userRole/insert` | 公开 | 新增用户角色 |

### 7.6 状态流水 (StatusJourController)
**基础路径**: `/ulms/oht`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/oht/statusJour/list/{dataDate}` | 公开 | 当日状态流水 |
| GET | `/oht/statusJour/chart` | 公开 | 状态图表视图 |
| GET | `/oht/statusJour/detail` | 公开 | 状态流水详情 |
| GET | `/oht/statusJour/download/total/{begDate}/{endDate}` | 公开 | 状态汇总Excel |
| GET | `/oht/statusJour/download/detail` | 公开 | 状态明细Excel |

### 7.7 状态类型 (StatusTypeController)
**基础路径**: `/ulms/oht`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/oht/statusTree` | 公开 | 状态类型树 |
| GET | `/oht/identityList` | 公开 | 身份列表 |
| GET | `/oht/statusType/list` | 公开 | 分页状态列表 |
| PUT | `/oht/statusType/update` | 公开 | 更新状态类型 |
| POST | `/oht/statusType/add` | 公开 | 新增状态类型 |

---

## 8. 人力资源模块 (hr)

### 8.1 生育假管理 (BirthLeaveController)
**基础路径**: `/ulms/hr/birth_leave`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/hr/birth_leave/list` | 公开 | 分页生育假列表 |
| GET | `/hr/birth_leave/get/{ploNum}` | 公开 | 按员工获取生育假 |
| POST | `/hr/birth_leave/add` | 公开 | 批量新增 |
| POST | `/hr/birth_leave/update` | 公开 | 批量更新 |

### 8.2 心情打卡 (MoodPunchControlle)
**基础路径**: `/ulms/hr/mood`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/hr/mood/pic` | 公开 | 获取启用的心情图片 |
| POST | `/hr/mood/submit` | 公开 | 提交心情打卡 |

### 8.3 人事名单 (PerfListController)
**基础路径**: `/ulms/hr/perf_list`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/hr/perf_list/init/{perfMonth}` | 公开 | 初始化当月名单 |
| GET | `/hr/perf_list/update/{perfMonth}` | 公开 | 更新单位信息 |
| GET | `/hr/perf_list/list` | 公开 | 分页名单列表 |
| GET | `/hr/perf_list/team/{perfMonth}` | 公开 | 我的团队名单 |
| GET | `/hr/perf_list/report` | 公开 | 名单Excel导出 |

### 8.4 打卡流水 (PunchJourController)
**基础路径**: `/ulms/hr/punch/jour`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/hr/punch/jour/list` | 公开 | 我的打卡流水（默认近3天） |

### 8.5 津贴单位 (SubsidyUnitController)
**基础路径**: `/ulms/hr/subsidy_unit`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/hr/subsidy_unit/list` | 公开 | 分页津贴单位 |
| GET | `/hr/subsidy_unit/get/{id}` | 公开 | 按ID获取 |
| POST | `/hr/subsidy_unit/add` | 公开 | 新增 |
| DELETE | `/hr/subsidy_unit/delete/{id}` | 公开 | 删除 |
| PUT | `/hr/subsidy_unit/update` | 公开 | 更新 |

---

## 9. 流程审批模块 (flow)

### 9.1 审批操作 (FlowApproveController)
**基础路径**: `/ulms/flow/approve`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/flow/approve/list/{id}` | 公开 | 案件审批日志 |
| POST | `/flow/approve/submit` | 公开 | 提交审批决定 |

### 9.2 案件管理 (FlowCaseController)
**基础路径**: `/ulms/flow`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/flow/list` | 公开 | 所有审批流程定义 |
| GET | `/flow/case/my/{type}` | 公开 | 我的案件（all/apply/process/draft/proxy） |
| GET | `/flow/case/list` | 公开 | 案件列表 |
| GET | `/flow/case/{id}` | 公开 | 案件详情（含审批日志） |
| PUT | `/flow/case/cancel/{id}` | `flow:case:cancel` | 撤销案件（30天内） |

### 9.3 流程配置 (FlowConfigController)
**基础路径**: `/ulms/sys/flow/config`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/sys/flow/config/list` | 公开 | 分页流程配置 |
| POST | `/sys/flow/config` | 公开 | 新增流程 |
| PUT | `/sys/flow/config` | 公开 | 更新流程 |
| GET | `/sys/flow/config/templete/{id}` | 公开 | 审批模板列表 |
| POST | `/sys/flow/config/templete/{id}` | 公开 | 新增审批模板 |
| PUT | `/sys/flow/config/templete/{id}` | 公开 | 替换审批模板 |

### 9.4 考勤流程 (FlowGqsqController)
**基础路径**: `/ulms/work-attendance`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| POST | `/work-attendance/flow/new` | 公开 | 新建考勤申请 |
| GET | `/work-attendance/flow/seq/auth` | 公开 | 获取授权序列号 |
| GET | `/work-attendance/batch/process` | 公开 | 解析批量导入Excel |

### 9.5 代理管理 (FlowProxyController)
**基础路径**: `/ulms/flow/proxy`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/flow/proxy/list` | 公开 | 分页代理列表 |
| POST | `/flow/proxy/add` | 公开 | 新增代理 |
| PUT | `/flow/proxy/stop/{id}` | 公开 | 停止代理 |
| DELETE | `/flow/proxy/delete/{id}` | 公开 | 删除代理 |

### 9.6 报表 (FlowReportController)
**基础路径**: `/ulms/flow/report`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/flow/report` | 公开 | 流程案件Excel导出 |

### 9.7 绩效指标 (PerfFlagCountroller)
**基础路径**: `/ulms/perf-flag`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| POST | `/perf-flag/flow/new` | 公开 | 新建绩效指标申请 |
| GET | `/perf-flag/batch/process` | 公开 | 批量导入Excel解析 |

### 9.8 绩效单位 (PerfUnitCountroller)
**基础路径**: `/ulms/perf-unit`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| POST | `/perf-unit/flow/new` | 公开 | 新建绩效单位申请 |
| GET | `/perf-unit/batch/process` | 公开 | 批量导入Excel解析 |

---

## 10. 青年说论坛模块 (youngTalk)

### 10.1 文章管理 (ArticalController)
**基础路径**: `/ulms/cyt`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/cyt/stageList` | 公开 | 广场文章列表 |
| GET | `/cyt/topList/{type}` | 公开 | 置顶文章列表 |
| GET | `/cyt/weekly/rank` | 公开 | 周热榜（近30天Top15） |
| GET | `/cyt/articalList` | 公开 | 全量文章列表（多条件筛选排序） |
| GET | `/cyt/articalList/{type}/{category}` | 公开 | 按类型+分类筛选 |
| GET | `/cyt/articalList/{type}` | 公开 | 按类型获取 |
| GET | `/cyt/artical/{id}` | 公开 | 文章详情 |
| GET | `/cyt/artical/manage/{id}` | `cyt:artical:view` | 管理员查看文章 |
| POST | `/cyt/artical` | 公开 | 发布文章 |
| POST | `/cyt/survey` | 公开 | 发布问卷 |
| GET | `/cyt/myArtical` | 公开 | 我的文章列表 |
| GET | `/cyt/myItem` | 公开 | 我的项目列表 |
| PUT | `/cyt/artical` | 公开 | 更新文章（仅发布者） |
| PUT | `/cyt/artical/take/{id}` | 公开 | 承接项目 |
| PUT | `/cyt/artical/on/{type}/{id}/{status}` | `cyt:artical:set` | 设置置顶/精华/上架 |
| PUT | `/cyt/artical/on/comp` | `cyt:artical:complete` | 设置项目完成 |
| PUT | `/cyt/artical/on/examine` | `cyt:artical:examine` | 审核文章 |
| PUT | `/cyt/artical/category` | `cyt:artical:category:update` | 移动分类 |
| PUT | `/cyt/artical/delete` | 公开 | 软删除文章 |

**文章列表筛选参数**: `sortType`(time/comment/hot), `queryType`, `query`, `compType`, `eliteFlag`, `topFlag`, `onStage`, `status`, `dateYear`, `category`

### 10.2 问卷管理 (AnswerController)
**基础路径**: `/ulms/cyt`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| POST | `/cyt/answer` | 公开 | 提交问卷答案 |
| GET | `/cyt/answer/getUserList/{articalId}` | 公开 | 已答题用户列表 |
| GET | `/cyt/download/{articalId}` | `cyt:artical:survey:report` | 问卷报表Excel |

### 10.3 问卷题目 (QuestionController)
**基础路径**: `/ulms/cyt`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/cyt/questions/{articalId}` | 公开 | 问卷题目+选项+我的答案 |
| GET | `/cyt/survey/chart/{articalId}` | 公开 | 问卷统计图表 |

### 10.4 评论管理 (CommentController)
**基础路径**: `/ulms/cyt/`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/cyt/comment/list/{articalId}` | 公开 | 分页评论列表（含回复和点赞） |
| POST | `/cyt/comment` | 公开 | 发表评论 |
| GET | `/cyt/comment/{id}` | 公开 | 获取单条评论 |
| GET | `/cyt/comment/rank` | 公开 | 评论排行榜Top12 |
| PUT | `/cyt/comment/delete/{id}` | `cyt:comment:delete` | 删除评论 |

### 10.5 回复管理 (ReplyController)
**基础路径**: `/ulms/cyt`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| POST | `/cyt/reply/` | 公开 | 发表回复 |
| PUT | `/cyt/reply/delete/{id}` | `cyt:reply:delete` | 删除回复 |
| GET | `/cyt/reply/{id}` | 公开 | 获取单条回复 |

### 10.6 点赞管理 (LikeController)
**基础路径**: `/ulms/cyt`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| POST | `/cyt/like/{likeType}/{id}` | 公开 | 点赞/取消点赞 |

**likeType参数**: `1`=文章，`2`=评论，`3`=回复

### 10.7 收藏管理 (CollectController)
**基础路径**: `/ulms/cyt`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| POST | `/cyt/collect/{id}/{status}` | 公开 | 收藏/取消收藏 |
| GET | `/cyt/collect/list` | 公开 | 我的收藏列表 |

### 10.8 项目成员 (MemberController)
**基础路径**: `/ulms/cyt`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| POST | `/cyt/member/{articalId}` | 公开 | 设置项目成员 |
| GET | `/cyt/member/{articalId}` | 公开 | 获取项目成员 |

### 10.9 项目进度 (ProgressController)
**基础路径**: `/ulms/cyt`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/cyt/progress/{articalId}` | 公开 | 获取进度列表 |
| POST | `/cyt/progress` | 公开 | 新增进度 |
| PUT | `/cyt/progress` | 公开 | 更新进度 |

### 10.10 消息管理 (MessageController)
**基础路径**: `/ulms/cyt`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/cyt/message/list` | 公开 | 分页消息列表（默认未读） |
| PUT | `/cyt/message/read/{id}` | 公开 | 标记单条已读 |
| GET | `/cyt/message/unread` | 公开 | 未读消息数 |
| PUT | `/cyt/message/read/all` | 公开 | 全部标为已读 |

---

## 11. RPA工具模块 (rpa)

### 11.1 工具管理 (ToolListController)
**基础路径**: `/ulms/rpa/tool`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/rpa/tool/list` | 公开 | 分页RPA工具列表 |
| PUT | `/rpa/tool/update` | `rpa:tool:list:update` | 更新工具 |
| POST | `/rpa/tool/add` | `rpa:tool:list:add` | 新增工具 |
| DELETE | `/rpa/tool/delete/{id}` | `rpa:tool:list:delete` | 删除工具 |
| GET | `/rpa/tool` | 公开 | 用户可用工具列表 |
| GET | `/rpa/tool/get/name` | 公开 | 工具名称自动补全 |

### 11.2 使用报告 (ToolReportController)
**基础路径**: `/ulms/rpa/report`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/rpa/report/list` | `rpa:report:list` | 分页工具使用记录 |
| POST | `/rpa/report/add` | 公开 | 新增运行记录 |
| GET | `/rpa/report/cd/{toolId}` | 公开 | 获取冷却剩余时间 |
| GET | `/rpa/report/runlist` | `rpa:report:download` | 导出使用报告Excel |
| GET | `/rpa/report/chart` | 公开 | 使用统计图表 |

### 11.3 工具模板 (ToolTempleteController)
**基础路径**: `/ulms/rpa/templete`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/rpa/templete/{templeteId}` | 公开 | 模板字段列表 |
| PUT | `/rpa/templete/update` | `rpa:tool:templete:update` | 更新字段 |
| POST | `/rpa/templete/add` | `rpa:tool:templete:add` | 新增字段 |
| DELETE | `/rpa/templete/{id}` | `rpa:tool:templete:delete` | 删除字段 |

---

## 12. 助手模块 (helper)

### 12.1 助手文章 (HelperArticalController)
**基础路径**: `/ulms/helper/artical`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/helper/artical/list` | `helper:artical:list:self` OR `helper:artical:list` | 分页文章列表 |
| GET | `/helper/artical/font/list` | 公开 | 前端文章列表 |
| GET | `/helper/artical/{journo}` | 公开 | 文章详情（增加阅读数） |
| POST | `/helper/artical` | `helper:artical:add` | 新增文章 |
| PUT | `/helper/artical` | `helper:artical:update` | 更新文章（仅作者） |
| DELETE | `/helper/artical/{journo}` | `helper:artical:delete` | 删除文章 |

### 12.2 助手分类 (HelperTreeController)
**基础路径**: `/ulms/helper/tree`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/helper/tree/tree` | 公开 | 分类树形结构 |
| PUT | `/helper/tree` | `helper:tree:update` | 更新节点 |
| POST | `/helper/tree` | `helper:tree:insert` | 新增节点 |
| DELETE | `/helper/tree/{id}` | `helper:tree:delete` | 删除节点 |
| DELETE | `/helper/tree/withchildren/{id}` | `helper:tree:delete` | 删除节点及子节点 |

---

## 13. 数据库管理模块 (db)

### 13.1 表管理 (TableController)
**基础路径**: `/ulms/db/table`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/db/table/{id}` | 公开 | 获取表元数据 |
| GET | `/db/table/sort` | 公开 | 按分类/关键词查询表列表 |
| GET | `/db/table/sync` | 公开 | 一键同步数据库模式 |
| POST | `/db/table/update` | 公开 | 更新表元数据 |

### 13.2 列管理 (ColumnController)
**基础路径**: `/ulms/db/column`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/db/column/{tid}` | 公开 | 获取表列元数据 |
| POST | `/db/column/update` | 公开 | 批量更新列元数据 |

### 13.3 表分类 (TableTreeController)
**基础路径**: `/ulms/db/tree`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/db/tree/{status}` | 公开 | 表分类树（-1查全部） |
| PUT | `/db/tree` | 公开 | 更新节点 |
| DELETE | `/db/tree/{id}` | 公开 | 删除节点 |
| POST | `/db/tree` | 公开 | 新增节点 |
| POST | `/db/tree/batch` | 公开 | 批量新增节点 |

---

## 14. WebSocket模块 (ws)

### 14.1 在线监控 (OnlineMonitorController)
**基础路径**: `/ulms/ws/online`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/ws/online/list` | 公开 | 在线用户列表 |
| POST | `/ws/online/push` | 公开 | 推送消息给在线用户 |

**POST /ws/online/push 请求体**:
```json
{
  "userIds": ["655012", "655013"],
  "module": "chat",
  "type": "message",
  "title": "新消息",
  "content": { ... }
}
```
userIds为空/null时广播给所有在线用户。

---

## 15. 通用模块 (common)

### 15.1 文件上传/下载 (FileUpload)
**基础路径**: `/ulms/upload`

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| POST | `/upload/file/{path}` | 公开 | 上传文件（路径验证） |
| POST | `/upload/{path}` | 公开 | 上传文件（如头像） |
| GET | `/upload/getFile/{filePath}/{fileName}` | 公开 | 下载/查看文件 |
| DELETE | `/upload/getFile/{filePath}/{fileName}` | 公开 | 删除文件（桩方法） |

**POST /upload/file/{path} 请求**: `multipart/form-data`，字段名 `file`

**path参数规则**: `file/{path}` 模式有严格校验：禁止 `..`、`/`、`\`，只能是字母数字和 `_-`。普通 `/{path}` 模式无校验。

**上传成功响应**:
```json
{
  "code": 200,
  "msg": "文件上传成功",
  "file": {
    "path": "upload/getFile/avatar/1234567890.png",
    "fileId": "1234567890",
    "fileSuffix": "png"
  }
}
```

---

## 附录A: 权限标识完整列表

| 权限标识 | 所属模块 | 说明 |
|----------|----------|------|
| `employee:list` | sys | 员工列表查看 |
| `employee:role` | sys | 员工角色分配 |
| `department:list` | sys | 部门列表查看 |
| `department:delete` | sys | 删除部门 |
| `department:update` | sys | 更新部门 |
| `department:add` | sys | 新增部门 |
| `menu:list` | sys | 菜单列表 |
| `menu:add` | sys | 新增菜单 |
| `menu:update` | sys | 更新菜单 |
| `menu:delete` | sys | 删除菜单 |
| `role:permission:update` | sys | 更新角色权限 |
| `role:delete` | sys | 删除角色 |
| `role:update` | sys | 更新角色 |
| `role:add` | sys | 新增角色 |
| `dictionary:update` | sys | 更新字典 |
| `dictionary:delete` | sys | 删除字典 |
| `dictionary:add` | sys | 新增字典 |
| `quickUrl:list` | sys | 快捷链接查看 |
| `quickUrl:update` | sys | 更新快捷链接 |
| `quickUrl:insert` | sys | 新增快捷链接 |
| `quickUrl:delete` | sys | 删除快捷链接 |
| `sys:operlog:list` | sys | 操作日志查看 |
| `sys:operlog:report` | sys | 操作日志导出 |
| `station:list` | sys | 工位列表 |
| `station:delete` | sys | 删除工位 |
| `station:update` | sys | 更新工位 |
| `station:add` | sys | 新增工位 |
| `college:course:list` | college | 课程管理列表 |
| `college:course:update` | college | 更新课程 |
| `college:course:insert` | college | 新增课程 |
| `college:teacher:update` | college | 更新讲师 |
| `college:teacher:insert` | college | 新增讲师 |
| `college:teacher:delete` | college | 删除讲师 |
| `college:coursetype:update` | college | 更新课程类型 |
| `college:coursetype:insert` | college | 新增课程类型 |
| `college:coursetype:delete` | college | 删除课程类型 |
| `college:evaltemp:insert` | college | 新增评价模板 |
| `college:evaltemp:update` | college | 更新评价模板 |
| `college:group:add` | college | 新增教学组别 |
| `college:group:delete` | college | 删除教学组别 |
| `college:group:upload` | college | 导入教学组别 |
| `college:report:study:hum` | college | 学习报表 |
| `college:report:course:list` | college | 课程报表 |
| `college:report:exam:list` | college | 考试报表 |
| `college:report:eval:list` | college | 评价报表 |
| `edu:exam:list:self` | edu | 我的考试列表 |
| `edu:exam:list` | edu | 全部考试列表 |
| `edu:exam:update` | edu | 更新考试 |
| `edu:exam:add` | edu | 新增考试 |
| `edu:exam:delete` | edu | 删除考试 |
| `edu:question:list:self` | edu | 题库（含敏感） |
| `edu:question:list:simple` | edu | 题库（不含敏感） |
| `edu:question:add` | edu | 新增题目 |
| `edu:question:update` | edu | 更新题目 |
| `edu:question:delete` | edu | 删除题目 |
| `edu:question:transfer` | edu | 迁移题目 |
| `edu:question:download` | edu | 题库导出 |
| `edu:queslib:update` | edu | 更新题库 |
| `edu:queslib:delete` | edu | 删除题库 |
| `edu:queslib:add` | edu | 新增题库 |
| `edu:brush:config:add` | edu | 新增刷题配置 |
| `edu:brush:config:update` | edu | 更新刷题配置 |
| `edu:brush:config:delete` | edu | 删除刷题配置 |
| `edu:daily:config:delete` | edu | 删除每日一学 |
| `edu:daily:config:update` | edu | 更新每日一学 |
| `edu:daily:config:add` | edu | 新增每日一学 |
| `edu:score:user:add` | edu | 新增考生 |
| `edu:score:user:delete` | edu | 删除考生 |
| `edu:score:user:reset` | edu | 重置考试 |
| `edu:score:read` | edu | 阅卷 |
| `edu:score:report:download` | edu | 得分导出 |
| `edu:book:list:user` | edu | 强制预约 |
| `edu:book:list:list` | edu | 预约列表 |
| `edu:book:list:delete` | edu | 删除预约 |
| `edu:book:config:update` | edu | 更新预约配置 |
| `edu:book:config:add` | edu | 新增预约配置 |
| `edu:book:config:delete` | edu | 删除预约配置 |
| `edu:area:config:add` | edu | 新增考场 |
| `edu:area:config:update` | edu | 更新考场 |
| `edu:area:config:delete` | edu | 删除考场 |
| `edu:dispute:update` | edu | 处理复议 |
| `edu:dispute:list` | edu | 复议列表 |
| `edu:ques:test:report` | edu | 试卷导出 |
| `oht:case:list` | oht | 案件列表 |
| `oht:case:dir:list` | oht | 主任统计 |
| `oht:case:chat:record` | oht | 聊天记录 |
| `oht:case:dept:list` | oht | 部门统计 |
| `oht:case:report` | oht | 案件报表 |
| `oht:case:dir:report` | oht | 主任报表 |
| `oht:case:dept:report` | oht | 部门报表 |
| `oht:case:task:push` | oht | 任务查看 |
| `oht:case:task:list` | oht | 任务汇总 |
| `oht:case:task:report` | oht | 任务报表 |
| `oht:role:list` | oht | 角色列表 |
| `oht:role:add` | oht | 新增角色 |
| `oht:role:update` | oht | 更新角色 |
| `oht:role:delete` | oht | 删除角色 |
| `oht:quickMemo:list` | oht | 消息列表 |
| `oht:quickMemo:update` | oht | 更新消息 |
| `oht:quickMemo:delete` | oht | 删除消息 |
| `oht:quickMemo:add` | oht | 新增消息 |
| `flow:case:cancel` | flow | 撤销案件 |
| `rpa:tool:list:update` | rpa | 更新RPA工具 |
| `rpa:tool:list:add` | rpa | 新增RPA工具 |
| `rpa:tool:list:delete` | rpa | 删除RPA工具 |
| `rpa:report:list` | rpa | RPA报告查看 |
| `rpa:report:download` | rpa | RPA报告导出 |
| `rpa:tool:templete:update` | rpa | 更新模板字段 |
| `rpa:tool:templete:add` | rpa | 新增模板字段 |
| `rpa:tool:templete:delete` | rpa | 删除模板字段 |
| `helper:artical:list:self` | helper | 我的文章 |
| `helper:artical:list` | helper | 全部文章 |
| `helper:artical:add` | helper | 新增文章 |
| `helper:artical:update` | helper | 更新文章 |
| `helper:artical:delete` | helper | 删除文章 |
| `helper:tree:update` | helper | 更新分类 |
| `helper:tree:insert` | helper | 新增分类 |
| `helper:tree:delete` | helper | 删除分类 |
| `cyt:artical:view` | youngTalk | 管理查看文章 |
| `cyt:artical:set` | youngTalk | 设置置顶/精华/上架 |
| `cyt:artical:complete` | youngTalk | 设置项目完成 |
| `cyt:artical:examine` | youngTalk | 审核文章 |
| `cyt:artical:category:update` | youngTalk | 移动分类 |
| `cyt:artical:survey:report` | youngTalk | 问卷报表 |
| `cyt:comment:delete` | youngTalk | 删除评论 |
| `cyt:reply:delete` | youngTalk | 删除回复 |

---

## 附录B: 接口统计总览

| 模块 | 接口数 | 权限保护 | 公开 |
|------|--------|----------|------|
| sys (系统管理) | 76 | 29 | 47 |
| college (学院培训) | 56 | 21 | 35 |
| edu (教育培训) | 93 | 40 | 53 |
| oht (工时工具) | 34 | 20 | 14 |
| hr (人力资源) | 17 | 0 | 17 |
| flow (流程审批) | 25 | 1 | 24 |
| youngTalk (青年说) | 46 | 8 | 38 |
| rpa (RPA工具) | 15 | 8 | 7 |
| helper (助手) | 11 | 8 | 3 |
| db (数据库管理) | 11 | 0 | 11 |
| ws (WebSocket) | 2 | 0 | 2 |
| common (通用) | 4 | 0 | 4 |
| **总计** | **~390** | **135** | **255** |

---

> 文档由代码自动分析生成，基于 ULMS v1.3.2-dev-SNAPSHOT 后端源码。  
> 生成日期: 2026-05-31
> 开发者: WeiGenSheng
