import {httpInstance} from '@/utils/request';

/**
 * 通过表面获取表列信息
 * @param {String} tableName 表名
 * @returns 
 */
export function getColumns(tableName) {
  return httpInstance({url: `/db/column/${tableName}`})
}