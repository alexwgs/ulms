import { httpInstance } from '@/utils/request'

export const articleApi = {
  /**
   * 获取文章列表
   * @param {Object} queryInfo - 查询参数
   * @returns {Promise} - 文章列表数据
   */
  getArticles: (queryInfo) =>
    httpInstance({ url: 'helper/article/list', method: 'get', params: queryInfo }),
  
  /**
   * 删除文章
   * @param {string} journo - 文章ID
   * @returns {Promise} - 删除结果
   */
  deleteArticle: (journo) =>
    httpInstance({ url: `helper/article/${journo}`, method: 'delete' }),
  
  /**
   * 获取文章详情
   * @param {string} journo - 文章ID
   * @returns {Promise} - 文章详情数据
   */
  getArticleById: (journo) =>
    httpInstance({ url: `helper/article/${journo}`, method: 'get' }),
  
  /**
   * 添加文章
   * @param {Object} form - 文章表单数据
   * @returns {Promise} - 添加结果
   */
  addArticle: (form) =>
    httpInstance({ url: 'helper/article', method: 'post', data: form }),
  
  /**
   * 更新文章
   * @param {Object} form - 文章表单数据
   * @returns {Promise} - 更新结果
   */
  updateArticle: (form) =>
    httpInstance({ url: 'helper/article', method: 'put', data: form })
}