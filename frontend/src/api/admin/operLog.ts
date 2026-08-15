import type { ApiResponse, DataPage, PageQuery } from '@/types/api'
import { httpInstance } from '@/utils/request'

/** 操作日志 */
export interface OperLog {
  id: string
  operTime: string
  ploNum: string
  title: string
  content: string
  method: string
  requestUrl: string
  ip: string
  requestParam: string
  responseResult: string
  takeTime: number
  status: number
  errorMsg: string
  [key: string]: any
}

export const operLogApi = {
  getOperLogList: (params?: PageQuery): Promise<ApiResponse<DataPage<OperLog>>> =>
    httpInstance({ url: 'sys/operLog/list', method: 'get', params }),

  downloadOperLog: (params?: PageQuery): Promise<any> =>
    httpInstance({ url: 'sys/operLog/report', method: 'get', params, responseType: 'blob' })
}
