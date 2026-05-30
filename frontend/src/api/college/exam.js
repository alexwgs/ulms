import { httpInstance } from '@/utils/request'

export const examApi = {
  getCourseExamTemplate: (courseId) =>
    httpInstance({
      url: `college/course/exam/template/${courseId}`,
      method: 'get'
    }),
  deleteCourseExamTemplate: (courseId) =>
    httpInstance({
      url: `college/course/exam/template/${courseId}`,
      method: 'delete'
    }),
  updateCourseExamTemplate: (form) =>
    httpInstance({
      url: 'college/course/exam/template',
      method: 'put',
      data: form
    }),
  addCourseExamTemplate: (form) =>
    httpInstance({
      url: 'college/course/exam/template',
      method: 'post',
      data: form
    }),
  initCourseExam: (courseId) =>
    httpInstance({
      url: `college/course/exam/init/${courseId}`,
      method: 'get'
    }),
  startCourseExam: (courseId) =>
    httpInstance({
      url: `college/course/exam/start/${courseId}`,
      method: 'get'
    }),
  submitExamAnswer: (form) =>
    httpInstance({
      url: 'college/course/exam/check',
      method: 'post',
      data: form
    }),
  submitExam: (examCode) =>
    httpInstance({
      url: `college/course/exam/submit/${examCode}`,
      method: 'put'
    })
}