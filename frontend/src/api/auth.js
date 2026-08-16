import { httpInstance } from '@/utils/request'

/**
 * 认证相关API
 */

/**
 * 登录页面相关API
 */

/**
 * 获取字典数据
 * @returns {Promise}
 */
export function getDictionary() {
  return httpInstance({ url: '/dictionary/json' })
}

/**
 * 获取首页图片
 * @returns {Promise}
 */
export function getIndexImg() {
  return httpInstance({ url: '/system/ad/index' })
}

/**
 * 修改密码
 * @param {Object} form - 密码修改数据
 * @returns {Promise}
 */
export function modifyPassword(form) {
  return httpInstance({ url: '/modifyPassword', method: 'put', data: form })
}

/**
 * 设置客户端信息
 * @param {Object} form - 客户端信息数据
 * @returns {Promise}
 */
export function setClientInfo(form) {
  return httpInstance({
    url: '/station/clientInfo',
    method: 'post',
    data: form
  })
}

/**
 * 获取用户信息
 * @returns {Promise}
 */
export function getUserInfo() {
  return httpInstance({ url: '/user/info' })
}

/**
 * 获取菜单
 * @returns {Promise}
 */
export function getMenu() {
  return httpInstance({ url: '/menu/AuthMenu' })
}

/**
 * 用户登录
 * @param {Object} form - 登录表单数据
 * @returns {Promise}
 */
export function login(form) {
  return httpInstance({ url: '/login', method: 'post', data: form })
}

/**
 * 用户登出
 * @returns {Promise}
 */
export function logout() {
  return httpInstance({ url: '/logout' })
}

/**
 * 新建操作日志
 * @param {Object} data - 操作日志数据
 * @returns {Promise}
 */
export function createOperationLog(data) {
  return httpInstance({ url: '/log', method: 'post', data: data })
}

/**
 * 未授权提示
 * @returns {Promise}
 */
export function getUnauth() {
  return httpInstance({ url: '/unauth' })
}

/**
 * 无权限提示
 * @returns {Promise}
 */
export function get403() {
  return httpInstance({ url: '/403' })
}

/**
 * 应用错误提示
 * @returns {Promise}
 */
export function get500Error() {
  return httpInstance({ url: '/error/500' })
}

/**
 * 公共组件相关API
 */

/**
 * 通过姓名、工号、科室、组别查询员工
 * @param {Object} queryInfo - 查询参数
 * @returns {Promise}
 */
export function findEmployee(queryInfo) {
  return httpInstance({
    url: '/employee/find',
    method: 'get',
    params: queryInfo
  })
}

/**
 * 通过职级查询员工
 * @param {string} jobLevels - 职级
 * @returns {Promise}
 */
export function findEmployeeByJobLevel(jobLevels) {
  return httpInstance({ url: `/employee/find/${jobLevels}`, method: 'get' })
}

/**
 * 查询科室组别树
 * @returns {Promise}
 */
export function departmentTree() {
  return httpInstance({ url: '/department/tree/dept', method: 'get' })
}

/**
 * 查询科室组别树
 * @returns {Promise}
 */
export function groupTree() {
  return httpInstance({ url: '/department/tree/cust', method: 'get' })
}

/**
 * 查询职级
 * @returns {Promise}
 */
export function joblevelTree() {
  return httpInstance({ url: '/sys/jobInfo', method: 'get' })
}

/**
 * 获取角色列表
 * @returns {Promise}
 */
export function getRoleList() {
  return httpInstance({ url: '/role', method: 'get' })
}
