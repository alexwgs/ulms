import { httpInstance } from '@/utils/request'
import { downloadExcel } from '@/utils/request'

export const taskApi = {
  /**
   * 根据案件ID获取任务信息
   * @param {string} caseId - 案件ID
   * @returns {Promise}
   */
  getTaskByCaseId: (caseId) =>
    httpInstance({
      url: `oht/task/${caseId}`,
      method: 'get'
    }),

  /**
   * 根据日期范围获取任务汇总信息
   * @param {Object} params - 查询参数
   * @returns {Promise}
   */
  getTaskSummary: (params) =>
    httpInstance({
      url: 'oht/task/summary',
      method: 'get',
      params
    }),

  /**
   * 案件推送操作总览报表下载
   * @param {Object} params - 查询参数
   * @returns {Promise}
   */
  downloadTaskSummaryReport: (params) =>
    downloadExcel(
      'oht/task/summary/report',
      params,
      '案件推送操作总览.xlsx'
    )
}
