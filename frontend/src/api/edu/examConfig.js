import { httpInstance, downloadExcel } from '@/utils/request'

export const examConfigApi = {
  getExamList: (params) =>
    httpInstance({
      url: 'edu/exam/config/list',
      method: 'get',
      params
    }),
  addExamConfig: (data) =>
    httpInstance({
      url: 'edu/exam/config',
      method: 'post',
      data
    }),
  updateExamConfig: (data) =>
    httpInstance({
      url: 'edu/exam/config/update',
      method: 'put',
      data
    }),
  deleteExamConfig: (examCode) =>
    httpInstance({
      url: `edu/exam/config/${examCode}`,
      method: 'delete'
    }),
  getCurrentExamList: () =>
    httpInstance({
      url: 'edu/exam/current/exam/list',
      method: 'get'
    }),
  getAreaConfigList: (params) =>
    httpInstance({
      url: 'edu/area/config/list',
      method: 'get',
      params
    }),
  readTest: (examCode) =>
    httpInstance({
      url: `edu/exam/score/read/${examCode}`,
      method: 'get'
    }),
  getExamScoreList: (params) =>
    httpInstance({
      url: 'edu/exam/score/user/list',
      method: 'get',
      params
    }),
  addExamUser: (data) =>
    httpInstance({
      url: 'edu/exam/score/add',
      method: 'post',
      data
    }),
  deleteExamUser: (journo) =>
    httpInstance({
      url: `edu/exam/score/user/${journo}`,
      method: 'delete'
    }),
  resetExamStatus: (data) =>
    httpInstance({
      url: 'edu/exam/score/reset',
      method: 'post',
      data
    }),
  getExamTimeConfigList: (examCode) =>
    httpInstance({
      url: `edu/exam/time/config/list/${examCode}`,
      method: 'get'
    }),
  addExamTimeConfig: (data) =>
    httpInstance({
      url: 'edu/exam/time/config',
      method: 'post',
      data
    }),
  updateExamTimeConfig: (data) =>
    httpInstance({
      url: 'edu/exam/time/config',
      method: 'put',
      data
    }),
  deleteExamTimeConfig: (id) =>
    httpInstance({
      url: `edu/exam/time/config/${id}`,
      method: 'delete'
    }),
  getExamQuestionTemp: (examCode) =>
    httpInstance({
      url: `edu/exam/config/ques/temp/${examCode}`,
      method: 'get'
    }),
  saveExamQuestionTemp: (examCode, data) =>
    httpInstance({
      url: `edu/exam/config/ques/temp/${examCode}`,
      method: 'post',
      data
    }),
  getExamDisputeList: (examCode) =>
    httpInstance({
      url: `edu/exam/dispute/list/${examCode}`,
      method: 'get'
    }),
  handleExamDispute: (data) =>
    httpInstance({
      url: 'edu/exam/dispute/handle',
      method: 'put',
      data
    }),
  getExamBookInfo: (examCode) =>
    httpInstance({
      url: `edu/exam/book/info/${examCode}`,
      method: 'get'
    }),
  saveExamBookInfo: (data) =>
    httpInstance({
      url: 'edu/exam/book/info',
      method: 'post',
      data
    }),

  downloadExamScoreReport: (examCode) =>
    downloadExcel(
      `edu/exam/score/report/hum/${examCode}`,
      null,
      '个人考试得分明细.xlsx'
    ),
  downloadExamTestReport: (examCode) =>
    downloadExcel(
      `edu/exam/test/report/detail/${examCode}`,
      null,
      '试卷明细.xlsx'
    )
}
