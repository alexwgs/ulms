import { httpInstance } from '@/utils/request'

/**
 * Dashboard 仪表盘 API
 */
export const dashboardApi = {
  /**
   * 获取今日统计数据（UV、PV、异常数）+ 异常列表
   */
  getTodayStats: () =>
    httpInstance({ url: 'sys/dashboard/todayStats', method: 'get' }),

  /**
   * 获取系统 UV/PV 趋势
   * @param {number} days - 统计天数，默认 7
   */
  getUvPvTrend: (days = 7) =>
    httpInstance({
      url: 'sys/dashboard/uvPvTrend',
      method: 'get',
      params: { days }
    }),

  /**
   * 获取请求方法统计趋势（POST/PUT/DELETE 次数、异常数、平均响应时间）
   * @param {number} days - 统计天数，默认 7
   */
  getMethodStatsTrend: (days = 7) =>
    httpInstance({
      url: 'sys/dashboard/methodStatsTrend',
      method: 'get',
      params: { days }
    }),

  /**
   * 获取子系统访问趋势
   * @param {number} days - 统计天数，默认 7
   */
  getSubsysVisitTrend: (days = 7) =>
    httpInstance({
      url: 'sys/dashboard/subsysVisitTrend',
      method: 'get',
      params: { days }
    })
}
