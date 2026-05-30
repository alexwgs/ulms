import { httpInstance } from '@/utils/request'

export const statusTypeApi = {
  /**
   * 获取状态类型树
   * @returns {Promise}
   */
  getStatusTree: () =>
    httpInstance({
      url: 'oht/statusTree',
      method: 'get'
    }),

  /**
   * 获取身份列表
   * @returns {Promise}
   */
  getIdentityList: () =>
    httpInstance({
      url: 'oht/identityList',
      method: 'get'
    }),

  /**
   * 获取状态类型列表
   * @param {Object} params - 查询参数
   * @returns {Promise}
   */
  getStatusTypeList: (params) =>
    httpInstance({
      url: 'oht/statusType/list',
      method: 'get',
      params
    }),

  /**
   * 新增状态类型
   * @param {Object} data - 状态类型数据
   * @returns {Promise}
   */
  addStatusType: (data) =>
    httpInstance({
      url: 'oht/statusType/add',
      method: 'post',
      data
    }),

  /**
   * 更新状态类型
   * @param {Object} data - 状态类型数据
   * @returns {Promise}
   */
  updateStatusType: (data) =>
    httpInstance({
      url: 'oht/statusType/update',
      method: 'put',
      data
    })
}
