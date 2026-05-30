import { httpInstance } from '../../utils/request.js'


/**
 * 获取课程类型树
 * @returns {Promise}
 */
export const getCourseTypeTree = () => {
  return httpInstance.get('/college/course/type/tree')
}

/**
 * 新增课程类型
 * @param {Object} data - 课程类型数据
 * @returns {Promise}
 */
export const addCourseType = (data) => {
  return httpInstance.post('/college/course/type', data)
}

/**
 * 更新课程类型
 * @param {Object} data - 课程类型数据
 * @returns {Promise}
 */
export const updateCourseType = (data) => {
  return httpInstance.put('/college/course/type', data)
}

/**
 * 删除课程类型
 * @param {string} id - 课程类型ID
 * @returns {Promise}
 */
export const deleteCourseType = (id) => {
  return httpInstance.delete(`/college/course/type/${id}`)
}

/**
 * 评价模板相关API
 */

/**
 * 获取评价模板列表
 * @param {Object} params - 查询参数
 * @returns {Promise}
 */
export const getEvaluateTemplateList = (params) => {
  return httpInstance.get('/college/evaluate/templete/list', { params })
}

/**
 * 获取评价模板详情
 * @param {string} tempId - 模板ID
 * @returns {Promise}
 */
export const getEvaluateTemplateDetail = (tempId) => {
  return httpInstance.get(`/college/evaluate/templete/${tempId}`)
}

/**
 * 新增评价模板
 * @param {Object} data - 评价模板数据
 * @returns {Promise}
 */
export const addEvaluateTemplate = (data) => {
  return httpInstance.post('/college/evaluate/templete', data)
}

/**
 * 更新评价模板
 * @param {Object} data - 评价模板数据
 * @returns {Promise}
 */
export const updateEvaluateTemplate = (data) => {
  return httpInstance.put('/college/evaluate/templete', data)
}

/**
 * 评价相关API
 */

/**
 * 获取评价列表
 * @param {Object} params - 查询参数
 * @returns {Promise}
 */
export const getEvaluateList = (params) => {
  return httpInstance.get('/college/evaluate/list', { params })
}

/**
 * 添加评价
 * @param {Object} data - 评价数据
 * @returns {Promise}
 */
export const addEvaluate = (data) => {
  return httpInstance.post('/college/evaluate', data)
}

/**
 * 课程相关API
 */

/**
 * 获取课程列表（普通用户）
 * @param {Object} params - 查询参数
 * @returns {Promise}
 */
export const getCourseListNormal = (params) => {
  return httpInstance.get('/college/course', { params })
}

/**
 * 获取课程列表（管理员）
 * @param {Object} params - 查询参数
 * @returns {Promise}
 */
export const getCourseList = (params) => {
  return httpInstance.get('/college/course/list', { params })
}

/**
 * 获取特定课程详情
 * @param {string} courseId - 课程ID
 * @returns {Promise}
 */
export const getCourseDetail = (courseId) => {
  return httpInstance.get(`/college/course/${courseId}`)
}

/**
 * 新增课程
 * @param {Object} data - 课程数据
 * @returns {Promise}
 */
export const addCourse = (data) => {
  return httpInstance.post('/college/course', data)
}

/**
 * 更新课程
 * @param {Object} data - 课程数据
 * @returns {Promise}
 */
export const updateCourse = (data) => {
  return httpInstance.put('/college/course', data)
}

/**
 * 获取推荐课程
 * @returns {Promise}
 */
export const getRecommendCourses = () => {
  return httpInstance.get('/college/course/recommend')
}

/**
 * 获取首页推荐课程
 * @returns {Promise}
 */
export const getCommandCourses = () => {
  return httpInstance.get('/college/course/command')
}

/**
 * 讲师相关API
 */

/**
 * 获取讲师列表
 * @param {Object} params - 查询参数
 * @returns {Promise}
 */
export const getTeacherList = (params) => {
  return httpInstance.get('/college/teacher/list', { params })
}

/**
 * 获取所有教师列表
 * @returns {Promise}
 */
export const getAllTeacherList = () => {
  return httpInstance.get('/college/teacher/all')
}

/**
 * 获取推荐教师列表
 * @returns {Promise}
 */
export const getTopTeacherList = () => {
  return httpInstance.get('/college/teacher/top')
}

/**
 * 新增教师信息
 * @param {Object} data - 教师信息数据
 * @returns {Promise}
 */
export const addTeacher = (data) => {
  return httpInstance.post('/college/teacher', data)
}

/**
 * 更新教师信息
 * @param {Object} data - 教师信息数据
 * @returns {Promise}
 */
export const updateTeacher = (data) => {
  return httpInstance.put('/college/teacher', data)
}

/**
 * 删除教师信息
 * @param {string} ploNum - 教师编号
 * @returns {Promise}
 */
export const deleteTeacher = (ploNum) => {
  return httpInstance.delete(`/college/teacher/${ploNum}`)
}

/**
 * 课程文件相关API
 */

/**
 * 添加课程文件
 * @param {Object} data - 课程文件数据
 * @returns {Promise}
 */
export const addCourseFile = (data) => {
  return httpInstance.post('/college/course/file', data)
}

/**
 * 更新课程文件列表
 * @param {Object} data - 课程文件数据
 * @returns {Promise}
 */
export const updateCourseFile = (data) => {
  return httpInstance.put('/college/course/file', data)
}

/**
 * 获取我的学习进度
 * @param {string} courseId - 课程ID
 * @returns {Promise}
 */
export const getMyLearningProgress = (courseId) => {
  return httpInstance.get(`/college/course/file/progress/my/${courseId}`)
}

/**
 * 更新我的学习进度
 * @param {Object} data - 学习进度数据
 * @returns {Promise}
 */
export const updateMyLearningProgress = (data) => {
  return httpInstance.put('/college/course/file/progress/my', data)
}

/**
 * 获取我的学习时长日志
 * @returns {Promise}
 */
export const getMyLearningLog = () => {
  return httpInstance.get('/college/course/file/progress/log')
}

/**
 * 课程考试相关API
 */

/**
 * 初始化考试数据
 * @param {string} courseId - 课程ID
 * @returns {Promise}
 */
export const initExamData = (courseId) => {
  return httpInstance.get(`/college/course/exam/init/${courseId}`)
}

/**
 * 开始考试
 * @param {string} courseId - 课程ID
 * @returns {Promise}
 */
export const startExam = (courseId) => {
  return httpInstance.get(`/college/course/exam/start/${courseId}`)
}

/**
 * 提交答案
 * @param {Object} data - 答案数据
 * @returns {Promise}
 */
export const submitAnswer = (data) => {
  return httpInstance.post('/college/course/exam/check', data)
}

/**
 * 提交考试
 * @param {string} examCode - 考试编码
 * @returns {Promise}
 */
export const submitExam = (examCode) => {
  return httpInstance.put(`/college/course/exam/submit/${examCode}`)
}

/**
 * 学习记录相关API
 */

/**
 * 获取学习记录详情
 * @param {string} courseId - 课程ID
 * @returns {Promise}
 */
export const getStudyDetail = (courseId) => {
  return httpInstance.get(`/college/study/${courseId}`)
}

/**
 * 报名课程
 * @param {Object} data - 报名数据
 * @returns {Promise}
 */
export const signUpCourse = (data) => {
  return httpInstance.post('/college/study/sign', data)
}

/**
 * 获取我的学习记录
 * @returns {Promise}
 */
export const getMyStudyRecord = () => {
  return httpInstance.get('/college/study/my/record')
}

/**
 * 完成课程学习
 * @param {string} courseId - 课程ID
 * @returns {Promise}
 */
export const completeCourseStudy = (courseId) => {
  return httpInstance.put(`/college/study/file/complete/${courseId}`)
}

/**
 * 获取我的学习积分记录
 * @returns {Promise}
 */
export const getMyPointRecord = () => {
  return httpInstance.get('/college/study/my/point')
}

/**
 * 获取我的学习时长记录
 * @returns {Promise}
 */
export const getMyHourRecord = () => {
  return httpInstance.get('/college/study/my/hour')
}

/**
 * 教学组相关API
 */

/**
 * 获取教学组列表
 * @returns {Promise}
 */
export const getTeachGroupList = () => {
  return httpInstance.get('/college/group/list')
}

/**
 * 获取教学组用户列表
 * @param {Object} params - 查询参数
 * @returns {Promise}
 */
export const getGroupUserList = (params) => {
  return httpInstance.get('/college/group/list/user', { params })
}

/**
 * 添加教学组
 * @param {Object} data - 教学组数据
 * @returns {Promise}
 */
export const addTeachGroup = (data) => {
  return httpInstance.post('/college/group/add', data)
}

/**
 * 删除教学组
 * @param {Object} data - 教学组数据
 * @returns {Promise}
 */
export const deleteTeachGroup = (data) => {
  return httpInstance.post('/college/group/delete', data)
}

/**
 * 上传教学组文件
 * @param {string} fileName - 文件名
 * @param {Object} data - 文件数据
 * @returns {Promise}
 */
export const uploadTeachGroupFile = (fileName, data) => {
  return httpInstance.post(`/college/group/upload/${fileName}`, data)
}

/**
 * 用户积分相关API
 */

/**
 * 获取用户积分排行和我的积分
 * @returns {Promise}
 */
export const getUserPoints = () => {
  return httpInstance.get('/college/point')
}

/**
 * 考试配置相关API
 */

/**
 * 获取试题库树
 * @returns {Promise}
 */
export const getQuestionLibraryTree = () => {
  return httpInstance.get('/edu/quesLib/1')
}

/**
 * 获取课程考试模板配置
 * @param {string} courseId - 课程ID
 * @returns {Promise}
 */
export const getCourseExamTemplate = (courseId) => {
  return httpInstance.get(`/college/course/exam/template/${courseId}`)
}

/**
 * 添加课程考试模板配置
 * @param {Object} data - 考试模板数据
 * @returns {Promise}
 */
export const addCourseExamTemplate = (data) => {
  return httpInstance.post('/college/course/exam/template', data)
}

/**
 * 更新课程考试模板配置
 * @param {Object} data - 考试模板数据
 * @returns {Promise}
 */
export const updateCourseExamTemplate = (data) => {
  return httpInstance.put('/college/course/exam/template', data)
}

/**
 * 删除课程考试模板
 * @param {string} courseId - 课程ID
 * @returns {Promise}
 */
export const deleteCourseExamTemplate = (courseId) => {
  return httpInstance.delete(`/college/course/exam/template/${courseId}`)
}