import { httpInstance } from '@/utils/request'

export const onlineMonitorApi = {
  getOnlineUsers: () =>
    httpInstance({ url: 'ws/online/list', method: 'get' }),

  pushMessage: (data) =>
    httpInstance({ url: 'ws/online/push', method: 'post', data })
}
