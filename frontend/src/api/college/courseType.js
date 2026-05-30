import { httpInstance } from '@/utils/request'

export const courseTypeApi = {
  getCourseTypeTree: () =>
    httpInstance({
      url: 'college/course/type/tree',
      method: 'get'
    }),
  updateCourseType: (form) =>
    httpInstance({
      url: 'college/course/type',
      method: 'put',
      data: form
    }),
  addCourseType: (form) =>
    httpInstance({
      url: 'college/course/type',
      method: 'post',
      data: form
    }),
  deleteCourseType: (id) =>
    httpInstance({
      url: `college/course/type/${id}`,
      method: 'delete'
    })
}