import { httpInstance } from '@/utils/request'

export const treeApi = {
  /**
   * 获取路径树数据
   * @returns {Promise} - 路径树数据
   */
  getTree: () =>
    httpInstance({ url: 'helper/tree/tree', method: 'get' }),
  
  /**
   * 添加路径节点
   * @param {Object} form - 路径节点表单数据
   * @returns {Promise} - 添加结果
   */
  addTree: (form) =>
    httpInstance({ url: 'helper/tree', method: 'post', data: form }),
  
  /**
   * 更新路径节点
   * @param {Object} form - 路径节点表单数据
   * @returns {Promise} - 更新结果
   */
  updateTree: (form) =>
    httpInstance({ url: 'helper/tree', method: 'put', data: form }),
  
  /**
   * 删除路径节点及其子节点
   * @param {string} id - 节点ID
   * @returns {Promise} - 删除结果
   */
  deleteTreeWithChildren: (id) =>
    httpInstance({ url: `helper/tree/withchildren/${id}`, method: 'delete' })
}