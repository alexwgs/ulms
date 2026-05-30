import { httpInstance } from '@/utils/request'

export const courseFileApi = {
  updateCourseFile: (form) =>
    httpInstance({
      url: 'college/course/file',
      method: 'put',
      data: form
    }),
  addCourseFile: (form) =>
    httpInstance({
      url: 'college/course/file',
      method: 'post',
      data: form
    }),
  getMyStudyProgress: (courseId) =>
    httpInstance({
      url: `college/course/file/progress/my/${courseId}`,
      method: 'get'
    }),
  updateMyStudyProgress: (form) =>
    httpInstance({
      url: 'college/course/file/progress/my',
      method: 'put',
      data: form
    }),
  getMyStudyFileLogs: (params) =>
    httpInstance({
      url: 'college/course/file/progress/log',
      method: 'get',
      params
    })
}