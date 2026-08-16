import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import pinia from '@/stores'
import 'tdesign-vue-next/dist/tdesign.css'
// 图标字体（A6有声等页面的 iconfont 图标依赖）
import './assets/css/iconfont/iconfont.css'
// 全局样式放在 tdesign.css 之后引入，保证覆盖规则（间距、卡片、表格等）真正生效
import './assets/css/global.css'
import { MessagePlugin } from 'tdesign-vue-next'
import CustomTable from '@/components/CustomTable.vue'
import TableColumn from '@/components/TableColumn.vue'
import DynamicIcon from '@/components/DynamicIcon.vue'
import { downloadExcel } from '@/utils/request'

const app = createApp(App)

app.component('CustomTable', CustomTable)
app.component('TableColumn', TableColumn)
app.component('DynamicIcon', DynamicIcon)

app.config.globalProperties.$message = (message, duration) => {
  MessagePlugin.info({ content: message, duration: duration || 3000 })
}

// 全局下载工具（原 qiankun 宿主提供的 $global，独立部署时由前端自身提供）
const $global = {
  downloadExcel,
  downloadFile: (url, fileName) => {
    const a = document.createElement('a')
    a.href = url
    a.download = fileName || ''
    document.body.appendChild(a)
    a.click()
    document.body.removeChild(a)
  }
}
app.config.globalProperties.$global = $global
window.$global = $global

// 全局错误捕获：防止单页面渲染错误再次连锁污染后续导航，并在开发环境中记录问题
app.config.errorHandler = (err, instance, info) => {
  console.error('[ULMS Vue error]', info, err)
}
app.use(pinia)
app.use(router)
app.mount('#app')
