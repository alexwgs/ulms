// src/api/artical.js
import { httpInstance } from '@/utils/request'

export const manageNoticeApi = {
  // 获取公告列表
  getArticalList: (url, params) =>
    httpInstance({
      url,
      method: 'get',
      params
    }),
  // 设置置顶、精华、广场状态
  setTopAndSoOn: (url) =>
    httpInstance({
      url,
      method: 'put'
    }),
  // 审核文章
  examineArtical: (url, data) =>
    httpInstance({
      url,
      method: 'put',
      data
    }),
  // 更新分类
  updateCategory: (url, data) =>
    httpInstance({
      url,
      method: 'put',
      data
    })
}
// 获取文章列表
