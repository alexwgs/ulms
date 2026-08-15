import type { ApiResponse, DataPage, PageQuery } from '@/types/api'
import { httpInstance } from '@/utils/request'

/** 首页轮播图 */
export interface Ad {
  id: number
  name: string
  url: string
  begDate: string
  endDate: string
  loopFlag: string
  sort: number
  status: number
  [key: string]: any
}

export const adApi = {
  listAd: (queryInfo?: PageQuery): Promise<ApiResponse<DataPage<Ad>>> =>
    httpInstance({ url: 'system/ad/list', method: 'get', params: queryInfo }),

  addAd: (form: Partial<Ad>): Promise<ApiResponse<any>> =>
    httpInstance({ url: 'system/ad/record', method: 'post', data: form }),

  updateAd: (form: Partial<Ad>): Promise<ApiResponse<any>> =>
    httpInstance({ url: 'system/ad/record', method: 'put', data: form }),

  updateAdById: (id: number, form: Partial<Ad>): Promise<ApiResponse<any>> =>
    httpInstance({ url: `ad/${id}`, method: 'put', data: form }),

  deleteAd: (id: number): Promise<ApiResponse<any>> =>
    httpInstance({ url: `system/ad/${id}`, method: 'delete' })
}
