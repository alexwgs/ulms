import { httpInstance } from '@/utils/request'

export const errorLogApi = {
  listErrorLog: (queryInfo) =>
    httpInstance({
      url: 'errorLog/list',
      method: 'get',
      params: queryInfo
    }),
  addErrorLog: (form) =>
    httpInstance({
      url: 'errorLog',
      method: 'post',
      data: form
    }),
  updateErrorLog: (form) =>
    httpInstance({
      url: 'errorLog',
      method: 'put',
      data: form
    }),
  deleteErrorLog: (id) =>
    httpInstance({
      url: `errorLog/${id}`,
      method: 'delete'
    })
}
