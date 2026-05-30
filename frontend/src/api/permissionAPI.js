import {httpInstance} from '@/utils/request';


// *********************登陆页面************************
export function getDictionary() {
  return httpInstance({url: '/dictionary/json'})
}


export function getUserInfo() {
  return httpInstance({url: '/user/info'})
}

export function getMenu() {
  // return httpInstance({url: '/menu/menu', method: 'get', params: {system: 'bpms', useage: 1}})
  return httpInstance({url: '/menu/bsAuthMenu', method: 'get', params: {system: 'bpms', useage: 1}})
}

export function getDict() {
  return httpInstance({url: '/dictionary/json' })
}

export function login(form) {
  return httpInstance({url: '/login', method: 'post', data: form})
}
export function logout() {
  return httpInstance({url: '/logout'})
}


// *********************公共组件************************
// 通过姓名、工号、科室、组别查询员工
export function findEmployee(queryInfo) { 
  return httpInstance({url: '/employee/find', method: 'get', params:queryInfo})
}
// 通过职级查询员工
export function findEmployeeByJobLevel(jobLevels) { 
  return httpInstance({url: `/employee/find/${jobLevels}`, method: 'get'})
}
// 查询科室组别树
export function departmentTree() {
  return httpInstance({url: '/department/tree/dept', method: 'get'})
}
// 查询科室组别树
export function groupTree() {
  return httpInstance({url: '/department/tree/cust', method: 'get'})
}

// 查询职级
export function joblevelTree() {
  return httpInstance({url: '/sys/jobInfo', method: 'get'})
}