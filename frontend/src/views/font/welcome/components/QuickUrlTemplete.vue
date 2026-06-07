<template>
  <t-row>
    <t-col
      v-for="item in quickUrlList"
      :key="item.id"
      class="quick-menu-col"
      :span="span"
    >
      <div
        class="avartar-box-small"
        style="display: inline-block"
        @click="goto(item.url, item.userFlag, item.tokenFlag, item.sysType)"
      >
        <img
          class="quick-login-btn"
          :src="
            item.iconUrl === null
              ? fsURL + 'upload/getFile/avatar/avatar.png'
              : fsURL + item.iconUrl
          "
        />
      </div>
      <div class="quick-menu-font">{{ item.name }}</div>
    </t-col>
  </t-row>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { getQuickUrlList, addLog } from '@/api/welcome/index.js'

const props = defineProps({
  area: {
    type: String,
    default: ''
  },
  span: {
    type: Number,
    default: 4
  }
})

const fsURL = import.meta.env.VITE_FILE_MANAGE_BASE || ''
const quickUrlList = ref([])

onMounted(() => {
  getQuickUrlListData()
})

const getQuickUrlListData = async () => {
  try {
    const res = await getQuickUrlList(props.area)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    quickUrlList.value = res.data || []
  } catch (error) {
    console.error('获取快捷链接失败', error)
  }
}

const goto = (url, userFlag, tokenFlag, sysType) => {
  url = url + '?type=' + sysType
  if (userFlag === 'Y') url += '&czy_code=' + localStorage.getItem('userId')
  // token 不再通过 URL 参数传递，避免泄露到浏览器历史和服务端日志
  // 目标系统如需认证 token，应从 localStorage 或 cookie 自行读取
  addLog({ module: sysType, operate: 'quickLink' })
  window.open(url, '_blank')
}
</script>

<style lang="less" scoped>
.quick-menu-col {
  text-align: center;
  padding-top: 15px;
}

.quick-menu-font {
  font-size: 14px;
  color: var(--td-menu-font-color);
  padding-top: 5px;

  &:hover {
    color: #67c23a;
  }
}

.quick-login-btn {
-color: #f0f9eb;
  cursor: pointer;

  &:hover {
-color: #67c23a;
  }
}
</style>
