import { httpInstance } from '@/utils/request'

export const studyApi = {
  getStudyProgress: (courseId) =>
    httpInstance({
      url: `college/study/${courseId}`,
      method: 'get'
    }),
  signUpCourse: (form) =>
    httpInstance({
      url: 'college/study/sign',
      method: 'post',
      data: form
    }),
  getMyStudyRecord: (params) =>
    httpInstance({
      url: 'college/study/my/record',
      method: 'get',
      params
    }),
  completeStudyFile: (courseId) =>
    httpInstance({
      url: `college/study/file/complete/${courseId}`,
      method: 'put'
    }),
  getMyStudyPoint: (params) =>
    httpInstance({
      url: 'college/study/my/point',
      method: 'get',
      params
    }),
  getMyStudyHour: (params) =>
    httpInstance({
      url: 'college/study/my/hour',
      method: 'get',
      params
    })
}