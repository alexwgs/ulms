import { httpInstance } from '@/utils/request'

/**
 * 员工管理相关API
 */
export const employeeApi = {
  /**
   * 获取员工列表
   * @param {string} ploStatus - 员工状态
   * @param {Object} queryInfo - 查询参数
   * @returns {Promise}
   */
  listEmployeeByStatus: (ploStatus, params) =>
    httpInstance({
      url: `employee/list/${ploStatus}`,
      method: 'get',
      params
    }),

  /**
   * 分配角色
   * @param {string} userId - 用户ID
   * @param {Object} data - 角色数据
   * @returns {Promise}
   */
  dispatchRole: (userId, data) =>
    httpInstance({
      url: `employee/disppatch/role/${userId}`,
      method: 'post',
      data: data
    }),

  /**
   * 更新头像
   * @param {Object} data - 头像数据
   * @returns {Promise}
   */
  updateAvatar: (data) =>
    httpInstance({
      url: 'employee/avatar',
      method: 'post',
      data: data
    }),

  /**
   * 获取生日信息
   * @param {string} month - 月份
   * @returns {Promise}
   */
  getBirthdayInfo: (month) =>
    httpInstance({
      url: `employee/birthday/${month}`,
      method: 'get'
    }),

  /**
   * 模糊查询员工
   * @param {Object} params - 查询参数
   * @returns {Promise}
   */
  findEmployee: (params) =>
    httpInstance({
      url: 'employee/find',
      method: 'get',
      params
    }),

  /**
   * 按职级查询员工
   * @param {string} jobLevels - 职级
   * @param {Object} params - 查询参数
   * @returns {Promise}
   */
  findEmployeeByJobLevel: (jobLevels, params) =>
    httpInstance({
      url: `employee/find/${jobLevels}`,
      method: 'get',
      params
    }),

  /**
   * 获取员工树
   * @param {string} type - 类型
   * @returns {Promise}
   */
  getEmployeeTree: (type) =>
    httpInstance({
      url: `employee/tree/${type}`,
      method: 'get'
    }),

  /**
   * 导出员工报表
   * @returns {Promise}
   */
  exportEmployeeReport: () =>
    httpInstance({
      url: 'employee/report',
      method: 'get'
    })
}
