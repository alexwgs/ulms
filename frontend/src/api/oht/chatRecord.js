import { httpInstance } from '@/utils/request'

// 求助系统聊天记录（default.txt 大厅 / 案件房间），按日期查询
export const chatRecordApi = {
  /**
   * 按日期查询会话记录
   * @param {string} date - 日期 yyyy-MM-dd
   * @param {string} room - 房间名，默认 default（大厅）
   * @returns {Promise}
   */
  getRecord: (date, room = 'default') =>
    httpInstance({ url: '/record/list', method: 'get', params: { date, room } })
}
