import { httpInstance } from '@/utils/request'
import { downloadExcel } from '@/utils/request'

export const caseApi = {
  /**
   * 获取案件列表
   * @param {Object} params - 查询参数
   * @returns {Promise}
   */
  getCaseList: (params) =>
    httpInstance({
      url: 'oht/case/list',
      method: 'get',
      params
    }),

  /**
   * 按业务主任维度统计任务情况
   * @param {Object} params - 查询参数
   * @returns {Promise}
   */
  getCaseDirList: (params) =>
    httpInstance({
      url: 'oht/case/dir/list',
      method: 'get',
      params
    }),

  /**
   * 获取求助聊天记录
   * @param {Object} params - 查询参数
   * @returns {Promise}
   */
  getCaseChatRecord: (params) =>
    httpInstance({
      url: 'oht/case/record',
      method: 'get',
      params
    }),

  /**
   * 按部门维度统计任务情况
   * @param {Object} params - 查询参数
   * @returns {Promise}
   */
  getCaseDeptList: (params) =>
    httpInstance({
      url: 'oht/case/dept/list',
      method: 'get',
      params
    }),

  /**
   * 案件明细报表下载功能
   * @param {Object} params - 查询参数
   * @returns {Promise}
   */
  downloadCaseReport: (params) =>
    downloadExcel(
      'oht/case/rpt/case',
      params,
      '求助案件报告.xlsx'
    ),

  /**
   * 业务主任接单报表下载功能
   * @param {Object} params - 查询参数
   * @returns {Promise}
   */
  downloadCaseDirReport: (params) =>
    downloadExcel(
      'oht/case/rpt/dir',
      params,
      '业务主任接单报表.xlsx'
    ),

  /**
   * 部门时段接单报表下载功能
   * @param {Object} params - 查询参数
   * @returns {Promise}
   */
  downloadCaseDeptReport: (params) =>
    downloadExcel(
      'oht/case/rpt/dept',
      params,
      '部门时段接单报表.xlsx'
    )
}
