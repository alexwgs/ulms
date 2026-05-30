import zhCn from 'element-plus/dist/locale/zh-cn.mjs'

export const elementPlusConfig = {
  // 语言设置为中文
  locale: zhCn,
  // Dialog 全局配置
  dialog: {
    draggable: true,
    alignCenter: false,
    destroyOnClose: false
  },
  // Message 全局配置
  message: {
    max: 3,
    duration: 3000,
    showClose: true
  },
  // MessageBox 全局配置
  messageBox: {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  },
  // Pagination 全局配置
  pagination: {
    small: false,
    background: true,
    pagerCount: 7,
    layout: 'total, sizes, prev, pager, next, jumper',
    pageSizes: [10, 20, 30, 50, 100]
  },
  // Table 全局配置
  table: {
    border: false,
    stripe: true,
    size: 'default'
  }
}

export default elementPlusConfig
