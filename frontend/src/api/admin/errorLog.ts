import type { ApiResponse, DataPage, PageQuery } from '@/types/api'
import { httpInstance } from '@/utils/request'

/** 错误日志 */
export interface ErrorLog {
  id: number
  dataTime: string
  userId: string
  priority: string
  dataType: string
  info: string
  content: string
  [key: string]: any
}

export const errorLogApi = {
  listErrorLog: (queryInfo?: PageQuery): Promise<ApiResponse<DataPage<ErrorLog>>> =>
    httpInstance({ url: 'errorLog/list', method: 'get', params: queryInfo }),

  addErrorLog: (form: Partial<ErrorLog>): Promise<ApiResponse<any>> =>
    httpInstance({ url: 'errorLog', method: 'post', data: form }),

  updateErrorLog: (form: Partial<ErrorLog>): Promise<ApiResponse<any>> =>
    httpInstance({ url: 'errorLog', method: 'put', data: form }),

  deleteErrorLog: (id: string | number): Promise<ApiResponse<any>> =>
    httpInstance({ url: `errorLog/${id}`, method: 'delete' })
}
