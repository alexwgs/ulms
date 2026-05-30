import { httpInstance } from '@/utils/request'

export const userRoleApi = {
  /**
   * 根据角色类型获取用户角色列表
   * @param {Object} params - 查询参数
   * @returns {Promise}
   */
  getUserRoleList: (params) =>
    httpInstance({
      url: 'oht/userRole/list',
      method: 'get',
      params
    }),

  /**
   * 删除用户角色
   * @param {string} ploNum - 员工编号
   * @returns {Promise}
   */
  deleteUserRole: (ploNum) =>
    httpInstance({
      url: `oht/userRole/delete/${ploNum}`,
      method: 'delete'
    }),

  /**
   * 更新用户角色
   * @param {Object} data - 用户角色数据
   * @returns {Promise}
   */
  updateUserRole: (data) =>
    httpInstance({
      url: 'oht/userRole/update',
      method: 'put',
      data
    }),

  /**
   * 新增用户角色
   * @param {Object} data - 用户角色数据
   * @returns {Promise}
   */
  addUserRole: (data) =>
    httpInstance({
      url: 'oht/userRole/insert',
      method: 'post',
      data
    })
}
