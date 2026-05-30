import { httpInstance } from '../../utils/request.js'
import { downloadExcel } from '@/utils/request'

// 获取RPA工具列表
export const getRpaToolList = (params) => {
  return httpInstance.get('/rpa/tool', { params })
}
// 获取RPA工具名称
export const getRpaToolName = (query) => {
  return httpInstance.get('rpa/tool/get/name', { params: { name: query } })
}
// 新增RPA工具
export const addRpaTool = (data) => {
  return httpInstance.post('/rpa/tool/add', data)
}

export const getAllRpaTool = (params) => {
  return httpInstance.get('/rpa/tool/list', { params })
}

// 更新RPA工具
export const updateRpaTool = (data) => {
  return httpInstance.put('/rpa/tool/update', data)
}

// 删除RPA工具
export const deleteRpaTool = (toolId) => {
  return httpInstance.delete(`/rpa/tool/${toolId}`)
}

// 获取RPA工具模板
export const getRpaToolTemplete = (params) => {
  return httpInstance.get(`/rpa/templete/${params}`)
}

// 新增RPA工具模板字段
export const addRpaToolTemplete = (data) => {
  return httpInstance.post('/rpa/templete/add', data)
}

// 更新RPA工具模板字段
export const updateRpaToolTemplete = (data) => {
  return httpInstance.put('/rpa/templete/update', data)
}

// 删除RPA工具模板
export const deteteRpaToolTemplete = (templeteId) => {
  return httpInstance.delete(`/rpa/templete/${templeteId}`)
}

// 更新RPA工具状态
export const updateRpaToolStatus = (date) => {
  return httpInstance.put('/rpa/tool/update', date)
}

// 获取RPA报告列表
export const getRpaReportList = (params) => {
  return httpInstance.get('/rpa/report/list', { params })
}

// 提交工具运行报告
export const addRpaReport = (data) => {
  return httpInstance.post('/rpa/report/add', data)
}

export const getRpaReportRunList = (params) => {
  return httpInstance.get('/rpa/report/runlist', { params })
}

// 获取工具冷却时间
export const getRpaToolCdTime = (toolId) => {
  return httpInstance.get(`/rpa/report/cd/${toolId}`)
}

// 调用工具API
export const callRpaTool = (url, data, headers) => {
  return httpInstance.post(url, data, { headers })
}
