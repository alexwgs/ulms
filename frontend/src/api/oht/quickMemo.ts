import type { ApiResponse, DataPage, PageQuery } from '@/types/api'
import { httpInstance } from '@/utils/request'

/** 快捷消息 */
export interface QuickMemo {
  journo: string
  roleType: string
  memo: string
  status: number
  [key: string]: any
}

export const quickMemoApi = {
  /** 分页获取快捷消息列表 */
  getQuickMemoList: (params?: PageQuery): Promise<ApiResponse<DataPage<QuickMemo>>> =>
    httpInstance({ url: 'oht/quickMemo/list', method: 'get', params }),

  /** 根据角色类型获取快捷消息列表 */
  getQuickMemoListByRoleType: (roleType: string): Promise<ApiResponse<QuickMemo[]>> =>
    httpInstance({ url: `oht/quickMemo/list/${roleType}`, method: 'get' }),

  addQuickMemo: (data: Partial<QuickMemo>): Promise<ApiResponse<any>> =>
    httpInstance({ url: 'oht/quickMemo/add', method: 'post', data }),

  updateQuickMemo: (data: Partial<QuickMemo>): Promise<ApiResponse<any>> =>
    httpInstance({ url: 'oht/quickMemo/update', method: 'put', data }),

  deleteQuickMemo: (journo: string): Promise<ApiResponse<any>> =>
    httpInstance({ url: `oht/quickMemo/delete/${journo}`, method: 'delete' })
}
