import { httpInstance } from '@/utils/request'

export const adApi = {
  listAd: (queryInfo) =>
    httpInstance({
      url: 'system/ad/list',
      method: 'get',
      params: queryInfo
    }),
  addAd: (form) =>
    httpInstance({
      url: 'system/ad/record',
      method: 'post',
      data: form
    }),
  updateAd: (form) =>
    httpInstance({
      url: 'system/ad/record',
      method: 'put',
      data: form
    }),
  updateAdById: (id, form) =>
    httpInstance({
      url: `ad/${id}`,
      method: 'put',
      data: form
    }),
  deleteAd: (id) =>
    httpInstance({
      url: `system/ad/${id}`,
      method: 'delete'
    })
}
