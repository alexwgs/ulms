import { httpInstance } from '@/utils/request'

export const evaluateApi = {
  getEvaluateList: (params) =>
    httpInstance({
      url: 'college/evaluate/list',
      method: 'get',
      params
    }),
  addEvaluate: (form) =>
    httpInstance({
      url: 'college/evaluate',
      method: 'post',
      data: form
    }),
  getEvaluateTemplateList: (params) =>
    httpInstance({
      url: 'college/evaluate/templete/list',
      method: 'get',
      params
    }),
  getEvaluateTemplate: (tempId) =>
    httpInstance({
      url: `college/evaluate/templete/${tempId}`,
      method: 'get'
    }),
  updateEvaluateTemplate: (form) =>
    httpInstance({
      url: 'college/evaluate/templete',
      method: 'put',
      data: form
    }),
  addEvaluateTemplate: (form) =>
    httpInstance({
      url: 'college/evaluate/templete',
      method: 'post',
      data: form
    })
}