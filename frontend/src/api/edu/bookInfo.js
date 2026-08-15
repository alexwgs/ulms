import { httpInstance, downloadExcel } from '@/utils/request'

export const bookInfoApi = {
  // @SaCheckPermission("edu:book:list:list")
  listBookList: (params) =>
    httpInstance({
      url: 'edu/book/list',
      method: 'get',
      params
    }),
  updateBookList: (data) =>
    httpInstance({
      url: 'edu/book/list',
      method: 'put',
      data
    }),

  deleteBookList: (data) =>
    httpInstance({
      url: 'edu/book/list',
      method: 'delete',
      data
    }),
  forceExamBookList: (data) =>
    httpInstance({
      url: 'edu/book/info/list/force/book',
      method: 'put',
      data
    }),
  getExamBookTimeInfo: (examCode) =>
    httpInstance({
      url: `edu/book/info/list/${examCode}`,
      method: 'get'
    }),
  addNewExamBookTimeInfo: (data) =>
    httpInstance({
      url: 'edu/book/info',
      method: 'post',
      data
    }),
  deleteExamBookInfo: (infoCode) =>
    httpInstance({
      url: `edu/book/info/${infoCode}`,
      method: 'delete'
    }),
  getExamBookDetail: (params) =>
    httpInstance({
      url: 'edu/book/info/list/detail',
      method: 'get',
      params
    }),
  createExamBookList: (data) =>
    httpInstance({
      url: 'edu/book/info/list/detail',
      method: 'post',
      data
    }),

  getBookConfigList: (params) =>
    httpInstance({
      url: 'edu/book/config',
      method: 'get',
      params
    }),
  addBookConfig: (data) =>
    httpInstance({
      url: 'edu/book/config',
      method: 'post',
      data
    }),
  updateBookConfig: (data) =>
    httpInstance({
      url: 'edu/book/config',
      method: 'put',
      data
    }),
  getBookTimeList: (params) =>
    httpInstance({
      url: 'edu/book/config/bookTime',
      method: 'get',
      params
    }),
  addBookTime: (data) =>
    httpInstance({
      url: 'edu/book/config/bookTime',
      method: 'post',
      data
    }),
  updateBookTime: (data) =>
    httpInstance({
      url: 'edu/book/config/bookTime',
      method: 'put',
      data
    }),
  deleteBookTime: (journo) =>
    httpInstance({
      url: `edu/book/config/bookTime/${journo}`,
      method: 'delete'
    })
}
