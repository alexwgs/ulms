import { httpInstance } from '@/utils/request'

export const reportApi = {
  getHumReport: (courseId) =>
    httpInstance({
      url: `college/report/hum/${courseId}`,
      method: 'get'
    }),
  getCourseInformationReport: (params) =>
    httpInstance({
      url: 'college/report/course/infomation',
      method: 'get',
      params
    }),
  downloadCourseInformationReport: (params) =>
    httpInstance({
      url: 'college/report/course/dowmload/infomation',
      method: 'get',
      params
    }),
  getCourseListReport: () =>
    httpInstance({
      url: 'college/report/course',
      method: 'get'
    }),
  getCourseExamReport: (courseId) =>
    httpInstance({
      url: `college/report/exam/${courseId}`,
      method: 'get'
    }),
  getCourseEvalReport: (courseId) =>
    httpInstance({
      url: `college/report/eval/${courseId}`,
      method: 'get'
    }),
  getStudyViewReport: (courseId) =>
    httpInstance({
      url: `college/report/view/${courseId}`,
      method: 'get'
    })
}