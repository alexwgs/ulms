import { httpInstance, downloadExcel } from '@/utils/request'

export const brushReportApi = {
  // 个人榜单
  getHumBrushList: (params) =>
    httpInstance({
      url: 'edu/brush/score/report/board/hum',
      method: 'get',
      params
    }),
  // 中心错题榜
  getCenterWrongQuestion: () =>
    httpInstance({
      url: 'edu/brush/score/report/board/center',
      method: 'get'
    }),
  // 团队榜单
  getGroupBrushList: (params) =>
    httpInstance({
      url: 'edu/brush/score/report/board/group',
      method: 'get',
      params
    }),
  // 科室榜单
  getDeptBrushList: (params) =>
    httpInstance({
      url: 'edu/brush/score/report/board/dept',
      method: 'get',
      params
    }),
  // 业务类型统计
  getCategoryBrushList: (params) =>
    httpInstance({
      url: 'edu/brush/score/report/board/category',
      method: 'get',
      params
    }),
  // 下载全员刷题情况报表
  getHumBrushReport: (params) =>
    downloadExcel(
      'edu/brush/score/report/hum',
      params,
      '全员刷题情况.xlsx'
    ),
  // 下载小组刷题情况报表
  getGroupBrushReport: (params) =>
    downloadExcel(
      'edu/brush/score/report/group',
      params,
      '小组刷题情况.xlsx'
    ),
  // 下载科室刷题情况报表
  getDeptBrushReport: (params) =>
    downloadExcel(
      'edu/brush/score/report/dept',
      params,
      '科室刷题情况.xlsx'
    ),
  // 下载各业务类型答题情况报表
  getCategoryBrushReport: (params) =>
    downloadExcel(
      'edu/brush/score/report/category',
      params,
      '各业务类型答题情况.xlsx'
    )
}
