import { httpInstance } from '@/utils/request'

export const questionCollectApi = {
  // 收藏/取消收藏题目
  collectQuestion: (quesCode) =>
    httpInstance({
      url: `edu/ques/collect/${quesCode}`,
      method: 'post'
    }),
  // 获取我的收藏
  getMyCollect: () =>
    httpInstance({
      url: 'edu/ques/collect/my',
      method: 'get'
    }),
  // 取消收藏（通过journo）
  uncollectQuestion: (journo) =>
    httpInstance({
      url: `edu/ques/collect/${journo}`,
      method: 'put'
    })
}
