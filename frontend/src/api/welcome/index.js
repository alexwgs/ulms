import { httpInstance } from '@/utils/request.js'

// 系统公告相关API
// 获取系统公告列表
export const getSystemNotice = (typeId = 9, topFlag = 0) => {
  return httpInstance.get(`/cyt/articalList/${typeId}/${topFlag}`)
}

// 获取文章详情
export const getArticalDetail = (id) => {
  return httpInstance.get(`/cyt/artical/${id}`)
}

// 快捷链接相关API
// 获取快捷链接列表
export const getQuickUrlList = (area) => {
  return httpInstance.get(`/quickUrl/records/${area}`)
}

// 日历相关API
// 获取日历班表数据
export const getCalendarDuty = (date) => {
  return httpInstance.get(`/calendar/duty/${date}`)
}

// 待办事项相关API
// 获取待办列表
export const getTodoList = (date) => {
  return httpInstance.get(`/sys/todo/${date}`)
}

// 添加待办
export const addTodo = (data) => {
  return httpInstance.post('/sys/todo/add', data)
}

// 更新待办
export const updateTodo = (data) => {
  return httpInstance.put('/sys/todo', data)
}

// 删除待办
export const deleteTodo = (id) => {
  return httpInstance.delete(`/sys/todo/${id}`)
}

// OHT身份相关API
// 获取身份列表
export const getIdentityList = () => {
  return httpInstance.get('/oht/identityList')
}

// 获取状态树
export const getStatusTree = () => {
  return httpInstance.get('/oht/statusTree')
}

// 获取状态记录列表
export const getStatusJourList = (date) => {
  return httpInstance.get(`/oht/statusJour/list/${date}`)
}

// 每日一学相关API
// 获取每日问题初始化数据
export const getDailyQuestionInit = (quesDate) => {
  return httpInstance.get('/edu/daily/question/init', { params: { quesDate } })
}

// 获取题库详情
export const getQuestionBank = (quesCode) => {
  return httpInstance.get(`/edu/quesBank/${quesCode}`)
}

// 获取心情图片
export const getMoodPic = () => {
  return httpInstance.get('/hr/mood/pic')
}

// 提交心情打卡
export const submitMood = (data) => {
  return httpInstance.post('/edu/daily/score/mood/submit', data)
}

// 提交文章学习
export const submitArticalStudy = (data) => {
  return httpInstance.post('/edu/daily/score/artical/submit', data)
}

// 提交问题答案
export const submitQuestion = (data) => {
  return httpInstance.post('/edu/daily/score/question/submit', data)
}

// 日志相关API
// 添加操作日志
export const addLog = (data) => {
  return httpInstance.post('/log', data)
}
