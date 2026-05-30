import { httpInstance } from '@/utils/request'

export const teachGroupApi = {
  getTeachGroupList: () =>
    httpInstance({
      url: 'college/group/list',
      method: 'get'
    }),
  getTeachGroupUserList: (params) =>
    httpInstance({
      url: 'college/group/list/user',
      method: 'get',
      params
    }),
  addTeachGroup: (form) =>
    httpInstance({
      url: 'college/group/add',
      method: 'post',
      data: form
    }),
  deleteTeachGroup: (form) =>
    httpInstance({
      url: 'college/group/delete',
      method: 'post',
      data: form
    }),
  uploadTeachGroupExcel: (fileName) =>
    httpInstance({
      url: `college/group/upload/${fileName}`,
      method: 'post'
    })
}