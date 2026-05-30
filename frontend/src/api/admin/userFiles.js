import { httpInstance } from '@/utils/request'

export const userFileApi = {
  getSuffix: () =>
    httpInstance({
      url: 'userFiles/suffix',
      method: 'get'
    }),
  /**
   * 获取文件列表
   * @param {Object} params - 请求参数对象
   * @returns {Promise} 返回一个Promise对象，包含文件列表数据
   */
  getFileList: (params) =>
    httpInstance({
      url: 'userFiles/list', // 请求的URL地址
      method: 'get', // 请求方法为GET
      params // 请求参数
    })
}
