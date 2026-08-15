import type { ApiResponse, DataPage, PageQuery } from '@/types/api'
import { httpInstance } from '@/utils/request'

/** 用户附件 */
export interface UserFile {
  fileId: string
  fileName: string
  fileSuffix: string
  filePath: string
  userId: string
  dataDate: string
  [key: string]: any
}

export const userFileApi = {
  getSuffix: (): Promise<ApiResponse<string[]>> =>
    httpInstance({ url: 'userFiles/suffix', method: 'get' }),

  getFileList: (params?: PageQuery): Promise<ApiResponse<DataPage<UserFile>>> =>
    httpInstance({ url: 'userFiles/list', method: 'get', params })
}
