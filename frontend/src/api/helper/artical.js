import { httpInstance } from '@/utils/request'

export const articalApi = {
  /**
   * 获取文章列表
   * @param {Object} queryInfo - 查询参数
   * @returns {Promise} - 文章列表数据
   */
  getArticals: (queryInfo) =>
    httpInstance({ url: 'helper/artical/list', method: 'get', params: queryInfo }),
  
  /**
   * 删除文章
   * @param {string} journo - 文章ID
   * @returns {Promise} - 删除结果
   */
  deleteArtical: (journo) =>
    httpInstance({ url: `helper/artical/${journo}`, method: 'delete' }),
  
  /**
   * 获取文章详情
   * @param {string} journo - 文章ID
   * @returns {Promise} - 文章详情数据
   */
  getArticalById: (journo) =>
    httpInstance({ url: `helper/artical/${journo}`, method: 'get' }),
  
  /**
   * 添加文章
   * @param {Object} form - 文章表单数据
   * @returns {Promise} - 添加结果
   */
  addArtical: (form) =>
    httpInstance({ url: 'helper/artical', method: 'post', data: form }),
  
  /**
   * 更新文章
   * @param {Object} form - 文章表单数据
   * @returns {Promise} - 更新结果
   */
  updateArtical: (form) =>
    httpInstance({ url: 'helper/artical', method: 'put', data: form })
}