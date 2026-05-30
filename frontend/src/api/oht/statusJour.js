import { httpInstance } from '@/utils/request'

export const statusJourApi = {
  /**
   * 获取指定日期的接单状态流水列表
   * @param {string} dataDate - 数据日期
   * @returns {Promise}
   */
  getStatusJourListByDate: (dataDate) =>
    httpInstance({
      url: `oht/statusJour/list/${dataDate}`,
      method: 'get'
    }),

  /**
   * 获取状态管理图表视图
   * @param {Object} params - 查询参数
   * @returns {Promise}
   */
  getStatusJourChart: (params) =>
    httpInstance({
      url: 'oht/statusJour/chart',
      method: 'get',
      params
    }),

  /**
   * 获取接单状态流水详情
   * @param {Object} params - 查询参数
   * @returns {Promise}
   */
  getStatusJourDetail: (params) =>
    httpInstance({
      url: 'oht/statusJour/detail',
      method: 'get',
      params
    }),

  /**
   * 下载接单状态流水汇总报表
   * @param {string} begDate - 开始日期
   * @param {string} endDate - 结束日期
   * @returns {Promise}
   */
  downloadStatusJourTotalReport: (begDate, endDate) =>
    httpInstance({
      url: `oht/statusJour/download/total/${begDate}/${endDate}`,
      method: 'get',
      responseType: 'blob'
    }),

  /**
   * 下载接单状态流水明细报表
   * @param {Object} params - 查询参数
   * @returns {Promise}
   */
  downloadStatusJourDetailReport: (params) =>
    httpInstance({
      url: 'oht/statusJour/download/detail',
      method: 'get',
      params,
      responseType: 'blob'
    })
}
