import type { ApiResponse, PageQuery } from '@/types/api'
import { httpInstance } from '@/utils/request'

/** 菜单节点 */
export interface Menu {
  id: number
  pid: number
  name: string
  icon: string
  resourse: string
  menuType: number
  useage: number
  status: number
  sort: number
  children?: Menu[]
  [key: string]: any
}

export const menuApi = {
  treeMenu: (queryInfo?: PageQuery): Promise<ApiResponse<Menu[]>> =>
    httpInstance({ url: 'menu/menus', method: 'get', params: queryInfo }),

  listMenu: (queryInfo?: PageQuery): Promise<ApiResponse<Menu[]>> =>
    httpInstance({ url: 'menu/menu', method: 'get', params: queryInfo }),

  addMenu: (queryInfo: Partial<Menu>): Promise<ApiResponse<any>> =>
    httpInstance({ url: 'menu/menus', method: 'post', data: queryInfo }),

  updateMenuById: (id: number, form: Partial<Menu>): Promise<ApiResponse<any>> =>
    httpInstance({ url: `menu/${id}`, method: 'put', data: form }),

  updateMenu: (form: Partial<Menu>): Promise<ApiResponse<any>> =>
    httpInstance({ url: 'menu', method: 'post', data: form }),

  deleteMenu: (id: number, pid: number): Promise<ApiResponse<any>> =>
    httpInstance({ url: `menu/${id}/${pid}`, method: 'delete' })
}
