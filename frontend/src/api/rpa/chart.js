import { httpInstance } from '@/utils/request'

export const chartApi = {
// 按日统计使用情况by日总使用数
  getUseTimeByDayChartData: (params) =>
    httpInstance({
      url: 'rpa/report/chart',
      method: 'get',
      params
    })
}