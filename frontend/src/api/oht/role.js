import { httpInstance } from '@/utils/request'

export const roleApi = {
  /**
   * 获取求助系统角色列表
   * @param {Object} params - 查询参数
   * @returns {Promise}
   */
  getRoleList: (params) =>
    httpInstance({
      url: 'oht/role/list',
      method: 'get',
      params
    }),

  /**
   * 根据角色代码获取角色信息
   * @param {string} roleCode - 角色代码
   * @returns {Promise}
   */
  getRoleByCode: (roleCode) =>
    httpInstance({
      url: `oht/role/get/${roleCode}`,
      method: 'get'
    }),

  /**
   * 新增求助系统角色
   * @param {Object} data - 角色数据
   * @returns {Promise}
   */
  addRole: (data) =>
    httpInstance({
      url: 'oht/role/add',
      method: 'post',
      data
    }),

  /**
   * 更新求助系统角色信息
   * @param {Object} data - 角色数据
   * @returns {Promise}
   */
  updateRole: (data) =>
    httpInstance({
      url: 'oht/role/update',
      method: 'put',
      data
    }),

  /**
   * 删除求助系统角色
   * @param {string} roleCode - 角色代码
   * @returns {Promise}
   */
  deleteRole: (roleCode) =>
    httpInstance({
      url: `oht/role/delete/${roleCode}`,
      method: 'delete'
    })
}
