import {httpInstance} from '@/utils/request';


// 获取电子流列表 
export function getFlowList(queryInfo) {
  return httpInstance({url: '/flow/list', params: queryInfo})
}
// 后台获取电子流列表
export function listFlowInfo(queryInfo) {
  return httpInstance({url: '/sys/flow/config/list', params: queryInfo})
}
export function addFlowInfo(form) {
  return httpInstance({url: '/sys/flow/config', method: 'post', data: form})
}
export function editFlowInfo(form) {
  return httpInstance({url: '/sys/flow/config', method: 'put', data: form})
}
export function editFlowInfoTemplete(flowId, form) {
  return httpInstance({url: `sys/flow/config/templete/${flowId}`, method: 'put', data: form})
}
export function addFlowInfoTemplete(flowId, form) {
  return httpInstance({url: `sys/flow/config/templete/${flowId}`, method: 'post', data: form})
}

// ********************************案件信息********************************************************
// 获取电子流列表
export function getFlowListByType(queryInfo, type) {
  return httpInstance({url: `/flow/case/my/${type}`, params: queryInfo})
}

// 获取电子流审批情况
export function listApproveLog(caseId) {
  return httpInstance({url: `/flow/approve/list/${caseId}`})
}


export function getFlowCase(caseId) {
  return httpInstance({url: `/flow/case/${caseId}`})
}

// 获取电子流案件
export function listFlowCase(queryInfo) {
  return httpInstance({url: '/flow/case/list', params: queryInfo})
}

// 提交电子流案件
export function submitFlowCase(form) {
  return httpInstance({url: '/flow/approve/submit', method: 'post', data: form})
}

// 案件撤销
export function cancelFlowCase(caseId) {
  return httpInstance({url: `/flow/case/cancel/${caseId}`, method: 'put'})
}

// ********************************新建电子流********************************************************
// 新建perfUnit
export function newPerfUnit(perfUnits) {
  return httpInstance({url: '/perf-unit/flow/new', method: 'post', data: perfUnits})
}

// 批量解析导入perfUnit
export function processBatchPerfUnit(data) {
  return httpInstance({url: '/perf-unit/batch/process', params: {filePath: data} })
}
// 新建perfFlag
export function newPerfFlag(perfFlags) {
  return httpInstance({url: '/perf-flag/flow/new', method: 'post', data: perfFlags})
}
// 新建WorkAttendance
export function newWorkAttendance(workAttendances) {
  return httpInstance({url: '/work-attendance/flow/new', method: 'post', data: workAttendances})
}
// 获取是否有人才序列的权限
export function listAuthSeq() {
  return httpInstance({url: '/work-attendance/flow/seq/auth', method: 'get'})
}

// 批量解析导入perfFlag
export function processBatchPerfFlag(data) {
  return httpInstance({url: '/perf-flag/batch/process', params: {filePath: data} })
}

// 批量解析导入FlowGqsq
export function processBatchFlowGqsq(data) {
  return httpInstance({url: '/work-attendance/batch/process', params: {filePath: data} })
}

// ********************************代理相关********************************************************
export function listFlowProxy(queryInfo) {
  return httpInstance({url: '/flow/proxy/list', params: queryInfo})
}

export function addFlowProxy(form) {
  return httpInstance({url: '/flow/proxy/add', method: 'post', data: form})
}
// 提前终止代理
export function stopFlowProxy(id) {
  return httpInstance({url: `/flow/proxy/stop/${id}`, method: 'put'})
}
// 删除代理
export function deleteFlowProxy(id) {
  return httpInstance({url: `/flow/proxy/delete/${id}`, method: 'delete'})
}
