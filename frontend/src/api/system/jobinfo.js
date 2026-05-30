import { httpInstance } from '@/utils/request'

export const jobinfoApi = {
  listAllJobLevelNoPages: (queryInfo) =>
    httpInstance({ url: 'sys/jobInfo', method: 'get', params: queryInfo }),
  listAllJobLevel: (queryInfo) =>
    httpInstance({ url: 'sys/jobInfo/list', method: 'get', params: queryInfo }),
  addJobLevel: (form) =>
    httpInstance({ url: 'sys/jobInfo', method: 'post', data: form }),
  updateJobLevel: (form) =>
    httpInstance({ url: 'sys/jobInfo', method: 'put', data: form }),
  deleteJobLevel: (id) =>
    httpInstance({ url: `sys/jobInfo/${id}`, method: 'delete' })
}
