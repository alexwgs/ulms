import { httpInstance } from '@/utils/request'

export const dailyConfigApi = {
  getDailyConfigList: (params) =>
    httpInstance({
      url: 'edu/daily/config/list',
      method: 'get',
      params
    }),
  getBrushConfigList: () =>
    httpInstance({
      url: 'edu/brush/config/list',
      method: 'get'
    }),
  addDailyConfig: (data) =>
    httpInstance({
      url: 'edu/daily/config',
      method: 'post',
      data
    }),
  updateDailyConfig: (data) =>
    httpInstance({
      url: 'edu/daily/config',
      method: 'put',
      data
    }),
  deleteDailyConfig: (id) =>
    httpInstance({
      url: `edu/daily/config/${id}`,
      method: 'delete'
    }),
  getSensitiveQuesList: (params) =>
    httpInstance({
      url: 'edu/quesBank/sensitive/list',
      method: 'get',
      params
    }),
  getArticalDetail: (id) =>
    httpInstance({
      url: `cyt/artical/${id}`,
      method: 'get'
    }),
  addArtical: (data) =>
    httpInstance({
      url: 'cyt/artical',
      method: 'post',
      data
    }),
  updateArtical: (data) =>
    httpInstance({
      url: 'cyt/artical',
      method: 'put',
      data
    })
}