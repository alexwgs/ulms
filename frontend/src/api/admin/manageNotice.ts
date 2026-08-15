import type { ApiResponse } from '@/types/api'
import { httpInstance } from '@/utils/request'

export const manageNoticeApi = {
  /** 获取公告列表 */
  getArticalList: (url: string, params?: Record<string, any>): Promise<ApiResponse<any>> =>
    httpInstance({ url, method: 'get', params }),

  /** 设置置顶、精华、广场状态 */
  setTopAndSoOn: (url: string): Promise<ApiResponse<any>> =>
    httpInstance({ url, method: 'put' }),

  /** 审核文章 */
  examineArtical: (url: string, data?: Record<string, any>): Promise<ApiResponse<any>> =>
    httpInstance({ url, method: 'put', data }),

  /** 更新分类 */
  updateCategory: (url: string, data?: Record<string, any>): Promise<ApiResponse<any>> =>
    httpInstance({ url, method: 'put', data })
}
