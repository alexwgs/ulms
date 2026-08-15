import type { ApiResponse, DataPage, DictionaryItem, PageQuery } from '@/types/api'
import { httpInstance } from '@/utils/request'

/** 字典管理相关API */
export const dictionaryApi = {
  /** 分页获取字典列表 */
  listDictionary: (queryInfo?: PageQuery): Promise<ApiResponse<DataPage<DictionaryItem>>> =>
    httpInstance({ url: 'dictionary/list', method: 'get', params: queryInfo }),

  /** 获取字典JSON */
  getDictionaryJson: (): Promise<ApiResponse<Record<string, DictionaryItem[]>>> =>
    httpInstance({ url: 'dictionary/json', method: 'get' }),

  /** 按名称获取字典列表 */
  getDictionaryListByName: (name: string, params?: PageQuery): Promise<ApiResponse<DictionaryItem[]>> =>
    httpInstance({ url: `dictionary/list/${name}`, method: 'get', params }),

  addDictionary: (form: Partial<DictionaryItem>): Promise<ApiResponse<any>> =>
    httpInstance({ url: 'dictionary/add', method: 'post', data: form }),

  updateDictionary: (form: Partial<DictionaryItem>): Promise<ApiResponse<any>> =>
    httpInstance({ url: 'dictionary/update', method: 'put', data: form }),

  deleteDictionary: (id: string | number): Promise<ApiResponse<any>> =>
    httpInstance({ url: `dictionary/delete/${id}`, method: 'delete' })
}
