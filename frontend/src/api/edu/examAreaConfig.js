import { httpInstance } from '@/utils/request'

export const examAreaConfigApi = {
  getAreaConfigList: (params) =>
    httpInstance({
      url: 'edu/area/config/list',
      method: 'get',
      params
    }),
  addAreaConfig: (data) =>
    httpInstance({
      url: 'edu/area/config',
      method: 'post',
      data
    }),
  updateAreaConfig: (data) =>
    httpInstance({
      url: 'edu/area/config',
      method: 'put',
      data
    }),
  getAreaDetailList: (params) =>
    httpInstance({
      url: 'edu/area/detail/list',
      method: 'get',
      params
    }),
  addAreaSeat: (data) =>
    httpInstance({
      url: 'edu/area/detail',
      method: 'post',
      data
    }),
  updateAreaSeat: (data) =>
    httpInstance({
      url: 'edu/area/detail',
      method: 'put',
      data
    }),
  deleteAreaSeat: (journo) =>
    httpInstance({
      url: `edu/area/detail/${journo}`,
      method: 'delete'
    })
}