/**
 * 统一错误码对照表（审计修复 F-H4）
 *
 * 约定：
 * - HTTP 层错误（状态码非 200）：由 request.js 响应拦截器统一提示，页面无需处理
 * - 业务层错误（HTTP 200 + code）：拦截器按对照表给出兜底文案；
 *   页面在 res.code !== 200 分支可自行提示 res.msg（后端文案优先）
 *
 * 业务错误码对照：
 * | code | 含义                | 建议响应                          |
 * |------|--------------------|-----------------------------------|
 * | 200  | 操作成功            | 正常处理                          |
 * | 301  | 警告（登录状态异常）| 提示后按业务处理                  |
 * | 401  | 未登录 / Token 失效 | 拦截器自动跳转登录页              |
 * | 403  | 无权限              | 拦截器自动跳转 403                |
 * | 400~499 | 业务参数/规则错误 | 提示 res.msg（后端给出的具体原因）|
 * | >=500 | 服务器异常          | 拦截器统一提示，避免内部细节泄露  |
 */

/** 业务状态码常量 */
export const ErrorCode = {
  SUCCESS: 200,
  WARN: 301,
  UNAUTHORIZED: 401,
  FORBIDDEN: 403,
  NOT_FOUND: 404,
  BAD_REQUEST: 400,
  SERVER_ERROR: 500
}

/** HTTP 状态码 → 用户提示文案（拦截器 error 分支使用） */
export const HTTP_ERROR_MESSAGES = {
  400: '请求参数有误，请检查后重试',
  401: '登录已过期，请重新登录',
  403: '您没有权限执行该操作',
  404: '请求的资源不存在',
  405: '请求方式不被支持',
  408: '请求超时，请稍后重试',
  500: '服务器开小差了，请稍后重试',
  502: '网关异常，请稍后重试',
  503: '服务暂不可用，请稍后重试',
  504: '网关超时，请稍后重试'
}

/** 网络层异常提示 */
export const NETWORK_ERROR_MESSAGE = '网络异常，请检查网络连接后重试'
export const TIMEOUT_MESSAGE = '请求超时，请稍后重试'

/** 根据状态码取兜底文案 */
export function getHttpErrorMessage(status, fallback = NETWORK_ERROR_MESSAGE) {
  return HTTP_ERROR_MESSAGES[status] || fallback
}

/** 根据业务 code 取兜底文案（后端 res.msg 优先时传 fallback） */
export function getBusinessErrorMessage(code, fallback = '操作失败，请稍后重试') {
  switch (code) {
    case ErrorCode.UNAUTHORIZED:
      return '登录已过期，请重新登录'
    case ErrorCode.FORBIDDEN:
      return '您没有权限执行该操作'
    case ErrorCode.NOT_FOUND:
      return '请求的资源不存在'
    case ErrorCode.BAD_REQUEST:
      return '请求参数有误'
    case ErrorCode.WARN:
      return '操作未完成'
    default:
      return fallback
  }
}
