import { httpInstance } from '@/utils/request'

export const brushApi = {
  // 获取刷题配置
  getBrushConfig: () =>
    httpInstance({
      url: 'edu/brush/config',
      method: 'get'
    }),
  listBrushConfig: (params) =>
    httpInstance({
      url: 'edu/brush/config/list',
      method: 'get',
      params
    }),
  getTreeData: () =>
    httpInstance({
      url: 'edu/quesLib/1',
      method: 'get'
    }),
  updateBrushConfig: (data) =>
    httpInstance({
      url: 'edu/brush/config/update',
      method: 'put',
      data
    }),
  addBrushConfig: (data) =>
    httpInstance({
      url: 'edu/brush/config/add',
      method: 'post',
      data
    }),
  // 提交答案
  submitAnswer: (data) =>
    httpInstance({
      url: 'edu/brush/score/answer',
      method: 'post',
      data
    }),
  // 获取我的错题
  getWrongQuestion: () =>
    httpInstance({
      url: 'edu/brush/score/wrong',
      method: 'get'
    })
}
