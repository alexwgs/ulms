import { httpInstance } from '@/utils/request.js'

// 讨论帖相关API
// 获取讨论帖列表
export const getArticalList = (params) => {
  return httpInstance.get('/cyt/articalList', { params })
}

// 获取讨论帖详情
export const getArticalDetail = (id) => {
  return httpInstance.get(`/cyt/artical/${id}`)
}

// 更新讨论帖
export const updateArtical = (data) => {
  return httpInstance.put('/cyt/artical', data)
}

// 新增讨论帖
export const addArtical = (data) => {
  return httpInstance.post('/cyt/artical', data)
}

// 删除讨论帖
export const deleteArtical = (params) => {
  return httpInstance.put('/cyt/artical/delete', { params })
}

// 收藏相关API
// 获取收藏列表
export const getCollectList = (params) => {
  return httpInstance.get('/cyt/collect/list', { params })
}

// 收藏/取消收藏
export const toggleCollect = (id, isCollect) => {
  return httpInstance.post(`/cyt/collect/${id}/${isCollect}`)
}

// 点赞相关API
// 点赞/取消点赞
export const toggleLike = (type, id) => {
  return httpInstance.post(`/cyt/like/${type}/${id}`)
}

// 评论相关API
// 获取评论列表
export const getCommentList = (id, params) => {
  return httpInstance.get(`/cyt/comment/list/${id}/`, { params })
}

// 新增评论
export const addComment = (data) => {
  return httpInstance.post('/cyt/comment', data)
}

// 删除评论
export const deleteComment = (id) => {
  return httpInstance.put(`/cyt/comment/delete/${id}`)
}

// 回复相关API
// 新增回复
export const addReply = (data) => {
  return httpInstance.post('/cyt/reply/', data)
}

// 删除回复
export const deleteReply = (id) => {
  return httpInstance.put(`/cyt/reply/delete/${id}`)
}

// 消息相关API
// 获取未读消息数
export const getUnreadCount = () => {
  return httpInstance.get('/cyt/message/unread')
}

// 获取消息列表
export const getMessageList = (params) => {
  return httpInstance.get('/cyt/message/list', { params })
}

// 获取评论消息
export const getCommentMessage = (id) => {
  return httpInstance.get(`/cyt/comment/${id}`)
}

// 获取回复消息
export const getReplyMessage = (id) => {
  return httpInstance.get(`/cyt/reply/${id}`)
}

// 标记消息已读
export const markMessageRead = (id) => {
  return httpInstance.put(`/cyt/message/read/${id}`)
}

// 标记所有消息已读
export const markAllMessageRead = () => {
  return httpInstance.put('/cyt/message/read/all')
}

// 课题相关API
// 获取课题列表
export const getItemList = (category, params) => {
  return httpInstance.get(`/cyt/articalList/1/${category}`, { params })
}

// 获取课题详情
export const getItemDetail = (id) => {
  return httpInstance.get(`/cyt/artical/${id}`)
}

// 获取课题成员
export const getItemMembers = (id) => {
  return httpInstance.get(`/cyt/member/${id}`)
}

// 获取课题进度
export const getItemProgress = (id) => {
  return httpInstance.get(`/cyt/progress/${id}`)
}

// 更新课题进度
export const updateProgress = (progress) => {
  return httpInstance.put('cyt/progress', progress)
}

// 添加课题成员
export const addItemMember = (id, data) => {
  return httpInstance.post(`/cyt/member/${id}`, data)
}

// 获取我的课题
export const getMyItem = (params) => {
  return httpInstance.get('cyt/myItem', { params })
}

// 承接课题
export const takeItem = (id) => {
  return httpInstance.put(`cyt/artical/take/${id}`)
}

// 调研相关API
// 获取调研列表
export const getSurveyList = (category, params) => {
  return httpInstance.get(`/cyt/articalList/3/${category}`, { params })
}

// 获取调研详情
export const getSurveyDetail = (id) => {
  return httpInstance.get(`/cyt/artical/${id}`)
}

// 获取调研问题
export const getSurveyQuestions = (id) => {
  return httpInstance.get(`/cyt/questions/${id}`)
}

// 提交调研答案
export const submitSurvey = (answers) => {
  return httpInstance.post('/cyt/answer', answers)
}

// 获取调研图表数据
export const getSurveyChart = (id) => {
  return httpInstance.get(`/cyt/survey/chart/${id}`)
}

// 创建调研
export const createSurvey = (data) => {
  return httpInstance.post('/cyt/survey', data)
}

// 排行榜相关API
// 获取评论排行榜
export const getCommentRank = () => {
  return httpInstance.get('/cyt/comment/rank')
}

// 获取周_hot排行榜
export const getWeeklyHot = () => {
  return httpInstance.get('/cyt/weekly/rank')
}

// 阶段列表相关API
// 获取阶段列表
export const getStageList = () => {
  return httpInstance.get('cyt/stageList')
}

// 我的文章相关API
// 获取我的文章列表
export const getMyArtical = (params) => {
  return httpInstance.get('cyt/myArtical', { params })
}
