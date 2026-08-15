import type { ApiResponse, DataPage, PageQuery } from '@/types/api'
import { httpInstance } from '@/utils/request'

/** 岗位信息 */
export interface JobInfo {
  jobLevel: string
  jobName: string
  [key: string]: any
}

export const jobinfoApi = {
  listAllJobLevelNoPages: (queryInfo?: PageQuery): Promise<ApiResponse<JobInfo[]>> =>
    httpInstance({ url: 'sys/jobInfo', method: 'get', params: queryInfo }),

  listAllJobLevel: (queryInfo?: PageQuery): Promise<ApiResponse<DataPage<JobInfo>>> =>
    httpInstance({ url: 'sys/jobInfo/list', method: 'get', params: queryInfo }),

  addJobLevel: (form: Partial<JobInfo>): Promise<ApiResponse<any>> =>
    httpInstance({ url: 'sys/jobInfo', method: 'post', data: form }),

  updateJobLevel: (form: Partial<JobInfo>): Promise<ApiResponse<any>> =>
    httpInstance({ url: 'sys/jobInfo', method: 'put', data: form }),

  deleteJobLevel: (id: string | number): Promise<ApiResponse<any>> =>
    httpInstance({ url: `sys/jobInfo/${id}`, method: 'delete' })
}
