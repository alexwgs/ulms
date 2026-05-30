import { httpInstance } from '@/utils/request'

export const roleApi = {
  listRole: (queryInfo) =>
    httpInstance({ url: 'role/list', method: 'get', params: queryInfo }),

  listAllRole: () => httpInstance({ url: 'role', method: 'get' }),

  addRole: (form) => httpInstance({ url: 'role/add', method: 'post', data: form }),

  updateRole: (form) =>
    httpInstance({ url: 'role/update', method: 'put', data: form }),

  updateRoleById: (id, form) =>
    httpInstance({ url: `role/permission/${id}`, method: 'put', data: form }),

  deleteRole: (id) => httpInstance({ url: `role/${id}`, method: 'delete' })
}
