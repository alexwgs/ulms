import dayjs from 'dayjs'

/**
 * 统一时间格式化：兼容秒级时间戳（10 位）、毫秒级时间戳（13 位）与日期字符串。
 * 后端 Case 的 buildTime/pickTime 为秒级时间戳（Integer），dataTime/pcompTime/bcompTime
 * 为 "yyyy-MM-dd HH:mm:ss" 字符串。
 * @param {number|string} value 时间戳（秒/毫秒）或日期字符串
 * @param {string} format 输出格式，默认 yyyy-MM-dd HH:mm:ss
 * @returns {string} 格式化结果；空值返回空字符串
 */
export function formatTime(value, format = 'YYYY-MM-DD HH:mm:ss') {
  if (value === null || value === undefined || value === '') return ''
  // 纯数字视为时间戳：> 1e12 为毫秒，否则按秒处理（×1000）
  if (typeof value === 'number' || /^\d+$/.test(String(value).trim())) {
    const num = Number(value)
    const ms = num > 1e12 ? num : num * 1000
    return dayjs(ms).format(format)
  }
  // 日期字符串直接解析
  const d = dayjs(String(value))
  return d.isValid() ? d.format(format) : String(value)
}
