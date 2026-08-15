import type { ApiResponse, DataPage, PageQuery } from '@/types/api'
import { httpInstance } from '@/utils/request'

/** 信息点（工位终端） */
export interface Station {
  extnNum: string
  extnIp: string
  pcIp: string
  ploNum: string
  memo: string
  [key: string]: any
}

export const stationApi = {
  listStation: (queryInfo?: PageQuery): Promise<ApiResponse<DataPage<Station>>> =>
    httpInstance({ url: 'station/list', method: 'get', params: queryInfo }),

  addStation: (form: Partial<Station>): Promise<ApiResponse<any>> =>
    httpInstance({ url: 'station', method: 'post', data: form }),

  updateStation: (form: Partial<Station>): Promise<ApiResponse<any>> =>
    httpInstance({ url: 'station', method: 'put', data: form }),

  updateStationById: (id: string | number, form: Partial<Station>): Promise<ApiResponse<any>> =>
    httpInstance({ url: `station/${id}`, method: 'put', data: form }),

  deleteStation: (pcIp: string): Promise<ApiResponse<any>> =>
    httpInstance({ url: `station/${pcIp}`, method: 'delete' })
}
