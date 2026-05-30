import { httpInstance } from '@/utils/request'

export const menuApi = {
  treeMenu: (queryInfo) =>
    httpInstance({ url: 'menu/menus', method: 'get', params: queryInfo }),
  listMenu: (queryInfo) =>
    httpInstance({ url: 'menu/menu', method: 'get', params: queryInfo }),
  addMenu: (queryInfo) =>
    httpInstance({ url: 'menu/menus', method: 'post', data: queryInfo }),
  updateMenuById: (id, form) =>
    httpInstance({ url: `menu/${id}`, method: 'put', data: form }),
  updateMenu: (form) =>
    httpInstance({ url: 'menu', method: 'post', data: form }),
  deleteMenu: (id, pid) =>
    httpInstance({ url: `menu/${id}/${pid}`, method: 'delete' })
}
