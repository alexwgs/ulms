import type { ApiResponse } from '@/types/api'
import { httpInstance } from '@/utils/request'

/** 在线用户 */
export interface OnlineUser {
  [key: string]: any
}

export const onlineMonitorApi = {
  getOnlineUsers: (): Promise<ApiResponse<OnlineUser[]>> =>
    httpInstance({ url: 'ws/online/list', method: 'get' }),

  pushMessage: (data: Record<string, any>): Promise<ApiResponse<any>> =>
    httpInstance({ url: 'ws/online/push', method: 'post', data })
}
