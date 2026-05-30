import { httpInstance } from '@/utils/request'

export const questionDisputeApi = {
  getQuestionDisputeList: (params) =>
    httpInstance({
      url: 'edu/ques/dispute',
      method: 'get',
      params
    }),
  // 刷题中的题目问题反馈，不修改得分及题目内容
  updateBrushDispute: (data) =>
    httpInstance({
      url: 'edu/ques/dispute/brush',
      method: 'put',
      data
    }),
  createDisputeByExam: (data) =>
    httpInstance({
      url: 'edu/ques/dispute',
      method: 'post',
      data
    }),
  updateDispute: (data) =>
    httpInstance({
      url: 'edu/ques/dispute',
      method: 'put',
      data
    }),
  getUserDispute: (examCode) =>
    httpInstance({
      url: `edu/ques/dispute/${examCode}`,
      method: 'get'
    }),
  listDispute: (params) =>
    httpInstance({
      url: 'edu/ques/dispute/list',
      method: 'get',
      params
    })
}
