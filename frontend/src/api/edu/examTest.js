import { httpInstance } from '@/utils/request'

export const examTestApi = {
  // 考试预检查
  precheckExam: (data) =>
    httpInstance({
      url: 'edu/exam/score/pre/check',
      method: 'post',
      data
    }),
  // 初始化考试
  initExam: (data) =>
    httpInstance({
      url: 'edu/exam/test/init',
      method: 'post',
      data
    }),
  // 提交答案
  submitAnswer: (wrongBreak, data) =>
    httpInstance({
      url: `edu/exam/test/submit/${wrongBreak}`,
      method: 'post',
      data
    }),
  // 交卷
  handOverTest: (data) =>
    httpInstance({
      url: 'edu/exam/score/submit',
      method: 'put',
      data
    }),
  // 获取历史考试列表
  getHistoryExam: (params) =>
    httpInstance({
      url: 'edu/exam/score/my/list',
      method: 'get',
      params
    }),
  // 获取我的试卷
  getMyTest: (examCode) =>
    httpInstance({
      url: `edu/exam/test/my/list/${examCode}`,
      method: 'get'
    })
}
