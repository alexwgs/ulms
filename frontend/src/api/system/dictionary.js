import { httpInstance } from '@/utils/request'

/**
 * 字典管理相关API
 */
export const dictionaryApi = {
  /**
   * 获取字典列表
   * @param {Object} queryInfo - 查询参数
   * @returns {Promise}
   */
  listDictionary: (queryInfo) =>
    httpInstance({
      url: 'dictionary/list',
      method: 'get',
      params: queryInfo
    }),

  /**
   * 获取字典JSON
   * @returns {Promise}
   */
  getDictionaryJson: () =>
    httpInstance({
      url: 'dictionary/json',
      method: 'get'
    }),

  /**
   * 按名称获取字典列表
   * @param {string} name - 字典名称
   * @param {Object} params - 查询参数
   * @returns {Promise}
   */
  getDictionaryListByName: (name, params) =>
    httpInstance({
      url: `dictionary/list/${name}`,
      method: 'get',
      params: params
    }),

  /**
   * 添加字典
   * @param {Object} form - 字典数据
   * @returns {Promise}
   */
  addDictionary: (form) =>
    httpInstance({
      url: 'dictionary/add',
      method: 'post',
      data: form
    }),

  /**
   * 更新字典
   * @param {Object} form - 字典数据
   * @returns {Promise}
   */
  updateDictionary: (form) =>
    httpInstance({
      url: 'dictionary/update',
      method: 'put',
      data: form
    }),

  /**
   * 删除字典
   * @param {string} id - 字典ID
   * @returns {Promise}
   */
  deleteDictionary: (id) =>
    httpInstance({
      url: `dictionary/delete/${id}`,
      method: 'delete'
    })
}
