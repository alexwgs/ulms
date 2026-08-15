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
    <Transition name="collapse">
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
    </Transition>
    <Identity :updateFlag="identityUpdateFlag"></Identity>
    <Notification ref="notificationRef" style="z-index: 999"></Notification>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useOhtStore } from '@/stores'
import { ChatIcon } from 'tdesign-icons-vue-next'
import Identity from './Identity.vue'
import DirectorStatus from './DirectorStatus.vue'
import Notification from './Notification.vue'

const router = useRouter()

const fsURL = import.meta.env.VITE_FILE_BASE_URL || ''
const user = ref(null)
const avatar = ref('')
const identityUpdateFlag = ref(false)
const userStatusPopFlag = ref(false)
const userStatus = ref('准备中')
const unreadNum = ref(0)
const notificationRef = ref(null)

const ohtStore = useOhtStore()
const ohtDirStatus = computed(() => ohtStore.ohtDirStatus)

const unreadCount = computed(() => {
  const messages = ohtStore.noticeMessage || []
  return messages.filter((e) => e.ifRead === false).length
})

onMounted(() => {
  const userData = window.localStorage.getItem('user')
  if (userData) {
    user.value = JSON.parse(userData)
  }
})

const logout = () => {
  window.localStorage.clear()
  router.push('/login')
}

const profile = () => {
  window.localStorage.setItem('activePath', '/profile')
  router.push({ path: '/profile' })
}

// 前端权限体系尚未建立，暂时保持放行
const hasPermission = () => true
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
  color: var(--td-brand-color);
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
  color: var(--td-success-color);
}
.refuse-case {
  color: var(--td-text-color-placeholder);
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
