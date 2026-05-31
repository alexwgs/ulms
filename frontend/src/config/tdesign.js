import zhCN from 'tdesign-vue-next/es/locale/zh_CN'

export const tdesignConfig = {
  // 组件全局配置
  classPrefix: 't',
  // Dialog 全局配置
  dialog: {
    closeOnOverlayClick: false,
    destroyOnClose: false,
    placement: 'center'
  },
  // ChatIcon 全局配置
  message: {
    duration: 3000
  },
  // Pagination 全局配置
  pagination: {
    showJumper: true,
    showPageSize: true,
    pageSizeOptions: [10, 20, 30, 50, 100],
    totalContent: true
  },
  // Table 全局配置
  table: {
    bordered: false,
    stripe: true,
    size: 'medium'
  },
  // 日历周起始日
  calendar: {
    firstDayOfWeek: 1
  }
}

export { zhCN }
export default tdesignConfig
