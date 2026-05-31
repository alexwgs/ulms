import { httpInstance } from '../../utils/request.js'

// 字典相关API
// 获取字典列表
export const getDictionaryList = (category) => {
  return httpInstance.get(`/dictionary/list/${category}`)
}

// 更新字典
export const updateDictionary = (data) => {
  return httpInstance.put('/dictionary/update', data)
}

// 添加字典
export const addDictionary = (data) => {
  return httpInstance.post('/dictionary/add', data)
}

// 文章相关API
// 获取文章列表
export const getArticalList = (category, params) => {
  return httpInstance.get(`/cyt/articalList/2/${category}`, { params })
}

// 设置文章置顶/精华/广场状态
export const setArticalStatus = (type, id, val) => {
  return httpInstance.put(`/cyt/artical/on/${type}/${id}/${val}`)
}

// 审核文章
export const examineArtical = (data) => {
  return httpInstance.put('/cyt/artical/on/examine', data)
}

// 移动文章分类
export const updateArticalCategory = (data) => {
  return httpInstance.put('/cyt/artical/category', data)
}

// 项目相关API
// 获取项目列表
export const getItemList = (category, params) => {
  return httpInstance.get(`/cyt/articalList/1/${category}`, { params })
}

// 项目结案
export const completeItem = (data) => {
  return httpInstance.put('/cyt/artical/on/comp', data)
}

// 获取项目成员
export const getItemMembers = (id) => {
  return httpInstance.get(`/cyt/member/${id}`)
}

// 获取项目详情
export const getItemDetail = (id) => {
  return httpInstance.get(`/cyt/artical/manage/${id}`)
}

// 获取项目进度
export const getItemProgress = (id) => {
  return httpInstance.get(`/cyt/progress/${id}`)
}

// 评论相关API
// 获取项目评论
export const getComments = (id, params) => {
  return httpInstance.get(`/cyt/comment/list/${id}`, { params })
}

// 删除评论
export const deleteComment = (id) => {
  return httpInstance.put(`/cyt/comment/delete/${id}`)
}

// 删除回复
export const deleteReply = (id) => {
  return httpInstance.put(`/cyt/reply/delete/${id}`)
}

// 调研相关API
// 获取调研列表
export const getSurveyList = (category, params) => {
  return httpInstance.get(`/cyt/articalList/3/${category}`, { params })
}

// 获取调研图表数据
export const getSurveyChartData = (articalId) => {
  return httpInstance.get(`/cyt/survey/chart/${articalId}`)
}
