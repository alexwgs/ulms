import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import './assets/css/global.css'
import TDesign from 'tdesign-vue-next'
import 'tdesign-vue-next/dist/tdesign.css'
import { MessagePlugin } from 'tdesign-vue-next'
import CustomTable from '@/components/CustomTable.vue'
import TableColumn from '@/components/TableColumn.vue'
import DynamicIcon from '@/components/DynamicIcon.vue'

const app = createApp(App)

app.use(TDesign)
app.component('CustomTable', CustomTable)
app.component('TableColumn', TableColumn)
app.component('DynamicIcon', DynamicIcon)

app.config.globalProperties.$message = (message, duration) => {
  MessagePlugin.info({ content: message, duration: duration || 3000 })
}
app.use(createPinia())
app.use(router)
app.mount('#app')
