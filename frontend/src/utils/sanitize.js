/**
 * 展示文本清洗工具
 *
 * 历史数据中部分简介/描述字段混入了 HTML 属性片段（例如重复的
 * label-width="auto" style="max-width: 600px"）或残缺标签，导致页面展示混乱。
 * 本工具在展示层做防御性清洗，保证界面干净，不影响数据库原始数据。
 */
export function cleanDisplayText(text) {
  if (text == null) return ''
  let str = String(text)
  // 移除 HTML 标签
  str = str.replace(/<[^>]+>/g, '')
  // 移除 HTML 属性片段：属性名="值"（兼容无空格粘连的重复脏数据）
  str = str.replace(/[a-zA-Z][\w-]*="[^"]*"/g, '')
  // 移除被截断、引号未闭合的属性片段（如末尾 style="max-widt）
  str = str.replace(/[a-zA-Z][\w-]*="[^"]*$/g, '')
  // 折叠连续空白
  str = str.replace(/\s+/g, ' ').trim()
  return str
}

export default cleanDisplayText
