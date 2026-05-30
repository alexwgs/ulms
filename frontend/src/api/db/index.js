import { httpInstance } from '@/utils/request.js'

// 树结构相关API
// 获取树结构数据
export const getTreeData = (status) => {
  return httpInstance.get(`/db/tree/${status}`)
}

// 新增树节点
export const addTreeNode = (data) => {
  return httpInstance.post('/db/tree', data)
}

// 更新树节点
export const updateTreeNode = (data) => {
  return httpInstance.put('/db/tree', data)
}

// 删除树节点
export const deleteTreeNode = (id) => {
  return httpInstance.delete(`/db/tree/${id}`)
}

// 批量移动表到分类
export const batchMoveTables = (data) => {
  return httpInstance.post('/db/tree/batch', data)
}

// 表相关API
// 获取表列表
export const getTableList = (params) => {
  return httpInstance.get('/db/table/sort', { params })
}

// 同步表信息
export const syncTableInfo = () => {
  return httpInstance.get('/db/table/sync')
}

// 获取表详情
export const getTableDetail = (id) => {
  return httpInstance.get(`/db/table/${id}`)
}

// 更新表信息
export const updateTableInfo = (data) => {
  return httpInstance.post('/db/table/update', data)
}

// 列相关API
// 获取表列信息
export const getColumnInfo = (tableId) => {
  return httpInstance.get(`/db/column/${tableId}`)
}

// 更新表列信息
export const updateColumnInfo = (data) => {
  return httpInstance.post('/db/column/update', data)
}
