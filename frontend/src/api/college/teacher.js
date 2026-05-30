import { httpInstance } from '@/utils/request'

export const teacherApi = {
  getTeacherList: (params) =>
    httpInstance({
      url: 'college/teacher/list',
      method: 'get',
      params
    }),
  getAllTeachers: () =>
    httpInstance({
      url: 'college/teacher/all',
      method: 'get'
    }),
  getTopTeachers: () =>
    httpInstance({
      url: 'college/teacher/top',
      method: 'get'
    }),
  updateTeacher: (form) =>
    httpInstance({
      url: 'college/teacher',
      method: 'put',
      data: form
    }),
  addTeacher: (form) =>
    httpInstance({
      url: 'college/teacher',
      method: 'post',
      data: form
    }),
  deleteTeacher: (ploNum) =>
    httpInstance({
      url: `college/teacher/${ploNum}`,
      method: 'delete'
    })
}