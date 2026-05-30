import { httpInstance } from '@/utils/request'

// 获取操作日志列表
export const operLogApi = {
  getOperLogList: (params) =>
    httpInstance({
      url: 'sys/operLog/list',
      method: 'get',
      params
    }),
  downloadOperLog: (params) =>
    httpInstance({
      url: 'sys/operLog/report',
      method: 'get',
      params,
      responseType: 'blob'
    })
}
