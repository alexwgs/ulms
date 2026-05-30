import { httpInstance, downloadExcel } from '@/utils/request'

export const dailyReportApi = {
  getDailyScoreList: (params) =>
    httpInstance({
      url: 'edu/daily/score/list',
      method: 'get',
      params
    }),
  downloadDailyScoreReport: (params) =>
    downloadExcel('edu/daily/score/report/list', params, '每日一学统计报表.xlsx')
}