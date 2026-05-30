import { httpInstance } from '@/utils/request'

export const courseApi = {
  getFrontCourseList: (params) =>
    httpInstance({
      url: 'college/course',
      method: 'get',
      params
    }),
  updateCourse: (form) =>
    httpInstance({
      url: 'college/course',
      method: 'put',
      data: form
    }),
  addCourse: (form) =>
    httpInstance({
      url: 'college/course',
      method: 'post',
      data: form
    }),
  getAdminCourseList: (params) =>
    httpInstance({
      url: 'college/course/list',
      method: 'get',
      params
    }),
  getCourseDetail: (courseId) =>
    httpInstance({
      url: `college/course/${courseId}`,
      method: 'get'
    }),
  getRecommendCourseList: () =>
    httpInstance({
      url: 'college/course/recommend',
      method: 'get'
    }),
  getCommandCourseList: () =>
    httpInstance({
      url: 'college/course/command',
      method: 'get'
    })
}