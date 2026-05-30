import { httpInstance } from '@/utils/request'

export const questionLibApi = {
  getQuestionLibTree: (id) =>
    httpInstance({
      url: `edu/quesLib/${id}`,
      method: 'get'
    }),
  getTreeData: (id = 1) =>
    httpInstance({
      url: `edu/quesLib/${id}`,
      method: 'get'
    }),
  addQuestionLib: (data) =>
    httpInstance({
      url: 'edu/quesLib',
      method: 'post',
      data
    }),
  updateQuestionLib: (data) =>
    httpInstance({
      url: 'edu/quesLib',
      method: 'put',
      data
    }),
  deleteQuestionLib: (id) =>
    httpInstance({
      url: `edu/quesLib/${id}`,
      method: 'delete'
    })
}