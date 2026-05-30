import { httpInstance, downloadExcel } from '@/utils/request'

export const questionBankApi = {
  getQuestionList: (params) =>
    httpInstance({
      url: 'edu/quesBank/list',
      method: 'get',
      params
    }),
  getQuestionListSensitive: (params) =>
    httpInstance({
      url: 'edu/quesBank/sensitive/list',
      method: 'get',
      params
    }),
  addQuestion: (data) =>
    httpInstance({
      url: 'edu/quesBank',
      method: 'post',
      data
    }),
  updateQuestion: (data) =>
    httpInstance({
      url: 'edu/quesBank',
      method: 'put',
      data
    }),
  deleteQuestion: (quesCode) =>
    httpInstance({
      url: `edu/quesBank/${quesCode}`,
      method: 'delete'
    }),
  changeQuestionLib: (data) =>
    httpInstance({
      url: 'edu/quesBank/changeLib',
      method: 'put',
      data
    }),
  extractQuestions: (data) =>
    httpInstance({
      url: 'edu/quesBank/question',
      method: 'post',
      data
    }),
  transferQuestions: (libCode, quesCodes) =>
    httpInstance({
      url: `edu/quesBank/transfer/${libCode}`,
      method: 'put',
      data: quesCodes
    }),
  getQuestionDetail: (quesCode) =>
    httpInstance({
      url: `edu/quesBank/${quesCode}`,
      method: 'get'
    }),
  downloadQuestionReport: (params) =>
    downloadExcel('edu/quesBank/report', params, '题库明细.xlsx')
}