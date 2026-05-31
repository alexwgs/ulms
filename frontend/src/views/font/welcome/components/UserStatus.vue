<template>
  <div class="logInfo" v-if="user">
    <t-badge
      :value="unreadCount"
      :type="unreadCount > 0 ? 'danger' : 'success'"
      class="badge-box"
      :max="10"
    >
      <t-button
        type="text"
        class="notice-btn"
        @click.stop="notificationRef?.show()"
      >
        <ChatIcon />
      </t-button>
    </t-badge>
    <div class="avatar_box" @click="profile">
      <t-avatar size="medium" :src="fsURL + user.avatar"></t-avatar>
    </div>
    <div
      size="small"
      @mouseover="() => (userStatusPopFlag = !userStatusPopFlag)"
      class="user-status"
    >
      {{ user.ploName }}<br /><b
        :class="ohtDirStatus > 0 ? 'take-case' : 'refuse-case'"
        >{{ userStatus }}</b
      >
    </div>
    <t-collapse-transition>
      <div
        v-show="userStatusPopFlag"
        class="user-status-popup"
        @mouseleave="() => (userStatusPopFlag = !userStatusPopFlag)"
      >
        <DirectorStatus
          v-if="hasPermission('main:directorStatus:manager')"
          @editIdentity="
            (e) => {
              identityUpdateFlag = e
            }
          "
          @changeUserStatus="
            (e) => {
              userStatus = e
            }
          "
        ></DirectorStatus>
        <div class="logout">
          <t-button size="small" theme="danger" @click="logout">登出</t-button>
        </div>
      </div>
    </t-collapse-transition>
    <Identity :updateFlag="identityUpdateFlag"></Identity>
    <Notification ref="notificationRef" style="z-index: 999"></Notification>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ChatIcon } from 'tdesign-icons-vue-next'
import Identity from './Identity.vue'
import DirectorStatus from './DirectorStatus.vue'
import Notification from './Notification.vue'

const router = useRouter()

const fsURL = import.meta.env.VITE_FILE_MANAGE_BASE || ''
const user = ref(null)
const avatar = ref('')
const identityUpdateFlag = ref(false)
const userStatusPopFlag = ref(false)
const userStatus = ref('准备中')
const unreadNum = ref(0)
const notificationRef = ref(null)

const ohtDirStatus = computed(() => {
  try {
    const store = window.__POWERED_BY_QIANKUN__ ? window.$store : null
    return store?.state?.ohtDirStatus || 0
  } catch {
    return 0
  }
})

const unreadCount = computed(() => {
  try {
    const store = window.__POWERED_BY_QIANKUN__ ? window.$store : null
    const messages = store?.state?.noticeMessage || []
    return messages.filter((e) => e.ifRead === false).length
  } catch {
    return 0
  }
})

onMounted(() => {
  const userData = window.localStorage.getItem('user')
  if (userData) {
    user.value = JSON.parse(userData)
  }
})

const logout = () => {
  try {
    const ws = window.__POWERED_BY_QIANKUN__ ? window.$ws?.ws : null
    if (ws) {
      ws.close()
    }
  } catch (error) {
    console.error('关闭WebSocket失败', error)
  }
  window.localStorage.clear()
  router.push('/login')
}

const profile = () => {
  window.localStorage.setItem('activePath', '/profile')
  router.push({ path: '/profile' })
}

const hasPermission = (permission) => {
  try {
    const global = window.__POWERED_BY_QIANKUN__ ? window.$global : null
    if (global && global.hasPermission) {
      return global.hasPermission(permission)
    }
    return true
  } catch (error) {
    return true
  }
}
</script>

<style lang="less" scoped>
.logInfo {
  position: relative;
  display: flex;
  align-items: center;
  padding: 0 10px;
  height: 100%;
}
.badge-box {
  display: inline-block;
  margin-right: 10px;
}
.notice-btn {
  font-size: 20px;
  color: #409eff;
  padding: 0;
}
.avatar_box {
  margin-right: 10px;
  cursor: pointer;
}
.user-status {
  cursor: pointer;
  font-size: 14px;
  line-height: 1.5;
}
.take-case {
  color: #67c23a;
}
.refuse-case {
  color: #909399;
}
.user-status-popup {
  position: absolute;
  top: 50px;
  right: 0;
  width: 300px;
background: white;
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  z-index: 100;
  padding: 10px;
}
.logout {
  margin-top: 10px;
  text-align: center;
}
</style>
