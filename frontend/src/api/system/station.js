import { httpInstance } from '@/utils/request'

export const stationApi = {
  listStation: (queryInfo) =>
    httpInstance({
      url: 'station/list',
      method: 'get',
      params: queryInfo
    }),
  addStation: (form) =>
    httpInstance({
      url: 'station',
      method: 'post',
      data: form
    }),
  updateStation: (form) =>
    httpInstance({
      url: 'station',
      method: 'put',
      data: form
    }),
  updateStationById: (id, form) =>
    httpInstance({
      url: `station/${id}`,
      method: 'put',
      data: form
    }),
  deleteStation: (pcIp) =>
    httpInstance({
      url: `station/${pcIp}`,
      method: 'delete'
    })
}
