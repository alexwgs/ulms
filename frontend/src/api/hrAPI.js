import {httpInstance} from '@/utils/request';


// 补贴单元************************************************************
// 获取补贴单元列表 
export function listSubsidyUnit(queryInfo) {
  return httpInstance({url: '/hr/subsidy_unit/list', params: queryInfo})
}

export function getSubsidyUnit(subsidyUnitId) {
  return httpInstance({url: `/hr/subsidy_unit/get/${subsidyUnitId}`})
}

export function addSubsidyUnit(form) {
  return httpInstance({url: '/hr/subsidy_unit/add', method: 'post', data: form})
}

export function editSubsidyUnit(form) {
  return httpInstance({url: '/hr/subsidy_unit/update', method: 'put', data: form})
}

export function deleteSubsidyUnit(subsidyUnitId) {
  return httpInstance({url: `/hr/subsidy_unit/delete/${subsidyUnitId}`, method: 'delete'})
}

// 孕产妇维护功能************************************************************
export function listBirthLeave(queryInfo) {
  return httpInstance({url: '/hr/birth_leave/list', params: queryInfo})
}

export function addBirthLeave(form) {
  return httpInstance({url: '/hr/birth_leave/add', method: 'post', data: form})
}

export function getBirthLeave(ploNum) {
  return httpInstance({url: `/hr/birth_leave/get/${ploNum}`})
}

export function editBirthLeave(form) {
  return httpInstance({url: '/hr/birth_leave/update', method: 'put', data: form})
}

// 名单列表************************************************************
export function initPerfList(perfMonth) {
  return httpInstance({url: `/hr/perf_list/init/${perfMonth}`})
}

export function listPerfList(queryInfo) {
  return httpInstance({url: '/hr/perf_list/list', params: queryInfo})
}

export function listTeam(perfMonth) {
  return httpInstance({url: `/hr/perf_list/team/${perfMonth}`})
}