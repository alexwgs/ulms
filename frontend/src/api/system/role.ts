import type { ApiResponse, DataPage, PageQuery } from '@/types/api'
import { httpInstance } from '@/utils/request'

/** 系统角色 */
export interface Role {
  id: number
  roleName: string
  permissions?: string
  [key: string]: any
}

export const roleApi = {
  /** 分页查询角色列表 */
  listRole: (queryInfo?: PageQuery): Promise<ApiResponse<DataPage<Role>>> =>
    httpInstance({ url: 'role/list', method: 'get', params: queryInfo }),

  /** 查询全部角色 */
  listAllRole: (): Promise<ApiResponse<Role[]>> =>
    httpInstance({ url: 'role', method: 'get' }),

  addRole: (form: Partial<Role>): Promise<ApiResponse<any>> =>
    httpInstance({ url: 'role/add', method: 'post', data: form }),

  updateRole: (form: Partial<Role>): Promise<ApiResponse<any>> =>
    httpInstance({ url: 'role/update', method: 'put', data: form }),

  updateRoleById: (
    id: number,
    form: { permissionIds: string }
  ): Promise<ApiResponse<any>> =>
    httpInstance({ url: `role/permission/${id}`, method: 'put', data: form }),

  deleteRole: (id: number): Promise<ApiResponse<any>> =>
    httpInstance({ url: `role/${id}`, method: 'delete' })
}
