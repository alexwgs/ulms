import { httpInstance } from '@/utils/request'

export const quickUrlApi = {
  listQuickUrl: (queryInfo) =>
    httpInstance({
      url: 'quickUrl/list',
      method: 'get',
      params: queryInfo
    }),
  addQuickUrl: (form) =>
    httpInstance({
      url: 'quickUrl/record',
      method: 'post',
      data: form
    }),
  updateQuickUrl: (form) =>
    httpInstance({
      url: 'quickUrl/record',
      method: 'put',
      data: form
    }),
  updateQuickUrlByArea: (area) =>
    httpInstance({
      url: `quickUrl/${area}`,
      method: 'put',
      data: form
    }),
  deleteQuickUrl: () =>
    httpInstance({
      url: 'quickUrl/record',
      method: 'delete'
    })
}
