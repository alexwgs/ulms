import { httpInstance } from '@/utils/request'

export const quickMemoApi = {
  /**
   * 获取快捷消息列表
   * @param {Object} params - 查询参数
   * @returns {Promise}
   */
  getQuickMemoList: (params) =>
    httpInstance({
      url: 'oht/quickMemo/list',
      method: 'get',
      params
    }),

  /**
   * 根据角色类型获取快捷消息列表
   * @param {string} roleType - 角色类型
   * @returns {Promise}
   */
  getQuickMemoListByRoleType: (roleType) =>
    httpInstance({
      url: `oht/quickMemo/list/${roleType}`,
      method: 'get'
    }),

  /**
   * 新增快捷消息
   * @param {Object} data - 快捷消息数据
   * @returns {Promise}
   */
  addQuickMemo: (data) =>
    httpInstance({
      url: 'oht/quickMemo/add',
      method: 'post',
      data
    }),

  /**
   * 更新快捷消息
   * @param {Object} data - 快捷消息数据
   * @returns {Promise}
   */
  updateQuickMemo: (data) =>
    httpInstance({
      url: 'oht/quickMemo/update',
      method: 'put',
      data
    }),

  /**
   * 删除快捷消息
   * @param {string} journo - 记录编号
   * @returns {Promise}
   */
  deleteQuickMemo: (journo) =>
    httpInstance({
      url: `oht/quickMemo/delete/${journo}`,
      method: 'delete'
    })
}
