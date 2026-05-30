import { httpInstance } from '@/utils/request'

export const departmentApi = {
  treeDepartment: () =>
    httpInstance({
      url: 'department/tree',
      method: 'get'
    }),
  addDepartment: (form) =>
    httpInstance({
      url: 'department',
      method: 'post',
      data: form
    }),
  updateDepartment: (form) =>
    httpInstance({
      url: 'department',
      method: 'put',
      data: form
    }),
  updateDepartmentById: (id, form) =>
    httpInstance({
      url: `department/${id}`,
      method: 'put',
      data: form
    }),
  deleteDepartment: (id) =>
    httpInstance({
      url: `department/${id}`,
      method: 'delete'
    })
}
