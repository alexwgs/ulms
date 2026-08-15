// 后端统一响应结构（与 com.cmbccd.ulms.sys.domain.Msg 对齐）
// 渐进 TS 化第一步：为 API 返回建立类型契约，供新代码与 IDE 提示使用。
export interface ApiResponse<T = any> {
  code: number
  msg: string
  data: T
  [key: string]: any
}

// 分页响应结构（与 com.cmbccd.ulms.common.util.DataPage 对齐）
export interface DataPage<T = any> {
  list: T[]
  total: number
  pageNum: number
  pageSize: number
}

// 分页查询参数约定
export interface PageQuery {
  pageNum?: number
  pageSize?: number
  [key: string]: any
}

// 字典项
export interface DictionaryItem {
  code: string
  codeval: string
  name: string
  status: number
}
