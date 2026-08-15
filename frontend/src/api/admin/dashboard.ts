import type { ApiResponse } from '@/types/api'
import { httpInstance } from '@/utils/request'

/** Dashboard 仪表盘 API */
export const dashboardApi = {
  getTodayStats: (): Promise<ApiResponse<any>> =>
    httpInstance({ url: 'sys/dashboard/todayStats', method: 'get' }),

  getUvPvTrend: (days = 7): Promise<ApiResponse<any[]>> =>
    httpInstance({ url: 'sys/dashboard/uvPvTrend', method: 'get', params: { days } }),

  getMethodStatsTrend: (days = 7): Promise<ApiResponse<any[]>> =>
    httpInstance({ url: 'sys/dashboard/methodStatsTrend', method: 'get', params: { days } }),

  getSubsysVisitTrend: (days = 7): Promise<ApiResponse<any[]>> =>
    httpInstance({ url: 'sys/dashboard/subsysVisitTrend', method: 'get', params: { days } })
}
