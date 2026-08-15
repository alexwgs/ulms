<template>
  <div class="navbar">
    <div class="left-side">
      <div class="flex items-center">
        <img alt="logo" src="../../assets/a6.png" class="mr-2" />
        <span class="text-lg font-bold">
          <!-- A6广场-管理后台 -->
        </span>
      </div>
    </div>

    <!-- 一级导航菜单 -->
    <div class="main-nav">
      <t-head-menu :value="activeFirstMenuId?.toString() || ''" theme="light" class="main-nav-menu">
        <t-menu-item v-for="menu in firstLevelMenus" :key="menu.id" :value="menu.id.toString()"
          @click="handleFirstMenuClick(menu)">
          <DynamicIcon :name="mapIcon(menu.icon)" />
          <span>{{ menu.name }}</span>
        </t-menu-item>
      </t-head-menu>
    </div>

    <ul class="right-side">
      <li>
        <userStatus />
      </li>
      <li>
        <t-tooltip :content="theme === 'light' ? '切换至黑暗模式' : '切换至亮色模式'" placement="bottom">
          <t-button class="nav-btn" variant="outline" @click="handleToggleTheme">
            <DynamicIcon :name="theme === 'dark' ? 'moon' : 'sunny'" />
          </t-button>
        </t-tooltip>
      </li>
      <li>
        <t-tooltip content="消息通知" placement="bottom">
          <div class="message-box-trigger">
            <t-badge :count="9" dot>
              <t-button class="nav-btn" variant="outline" @click="setPopoverVisible">
                <DynamicIcon name="notification" />
              </t-button>
            </t-badge>
          </div>
        </t-tooltip>
        <t-popup ref="popover" trigger="click" :overlay-style="{ width: '400px' }">
          <template #content>
            <message-box />
          </template>
          <div ref="refBtn" class="ref-btn"></div>
        </t-popup>
      </li>
      <li>
        <t-dropdown trigger="click">
          <div class="flex items-center">
            <t-avatar size="small" :image="fsBaseURL + userStore.avatar" />
          </div>
          <template #dropdown>
            <t-dropdown-menu>
              <t-dropdown-item @click="$router.push({ name: 'info' })">
                <DynamicIcon name="user" />
                <span class="ml-2">用户中心</span>
              </t-dropdown-item>
              <t-dropdown-item @click="$router.push({ name: 'setting' })">
                <DynamicIcon name="setting" />
                <span class="ml-2">用户设置</span>
              </t-dropdown-item>
              <t-dropdown-item divider @click="logout">
                <DynamicIcon name="swap" />
                <span class="ml-2">登出用户</span>
              </t-dropdown-item>
            </t-dropdown-menu>
          </template>
        </t-dropdown>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useUserStore, useAppStore, useMenuStore, useOhtStore } from '@/stores'
import router from '@/router'
import userStatus from '@/layouts/head/components/userStatus.vue'
import { mapIcon } from '@/utils/iconMap'
import MessageBox from '@/components/MessageBox.vue'

const userStore = useUserStore()
const appStore = useAppStore()
const menuStore = useMenuStore()
const ohtStore = useOhtStore()
const popover = ref()
const refBtn = ref()


const setPopoverVisible = () => {
  const event = new MouseEvent('click', {
    view: window,
    bubbles: true,
    cancelable: true
  })
  refBtn.value.dispatchEvent(event)
}

// 头像为展示类图片，统一走 HTTPS 的文件管理地址，避免混合内容被浏览器拦截
const fsBaseURL = import.meta.env.VITE_FILE_BASE_URL

// 主题相关
const isDark = ref(document.documentElement.classList.contains('dark'))

const theme = computed(() => {
  return isDark.value ? 'dark' : 'light'
})

const handleToggleTheme = () => {
  isDark.value = !isDark.value

  // 切换暗黑/亮色主题
  if (isDark.value) {
    document.documentElement.classList.add('dark')
  } else {
    document.documentElement.classList.remove('dark')
  }
  appStore.toggleTheme(isDark.value)

  // 保存主题设置到本地存储
  localStorage.setItem('theme', isDark.value ? 'dark' : 'light')
}

// 初始化主题
const initTheme = () => {
  const savedTheme = localStorage.getItem('theme')
  if (savedTheme === 'dark') {
    isDark.value = true
    document.documentElement.classList.add('dark')
  }
}

// 调用初始化主题
initTheme()

// 菜单相关
// 获取一级菜单列表
const firstLevelMenus = computed(() => {
  return menuStore.firstLevelMenus
})

// 获取当前选中的一级菜单ID
const activeFirstMenuId = computed(() => {
  return appStore.activeFirstMenuId
})

// 处理一级菜单点击事件
const handleFirstMenuClick = (menu) => {
  const hasChildren = menu.children && menu.children.length > 0
  // 设置当前选中的一级菜单，包括组件类型
  appStore.setActiveFirstMenu(menu.id, hasChildren, menu.component)

  // 如果没有子菜单，直接跳转到对应的路径
  if (!hasChildren && menu.path) {
    // 确保路径以 / 开头
    const path = menu.path.startsWith('/') ? menu.path : `/${menu.path}`
    router.push(path)
  }
  // 如果有子菜单，跳转到第一个子菜单的路径
  else if (hasChildren && menu.children[0].path) {
    // 确保路径以 / 开头
    const path = menu.children[0].path.startsWith('/')
      ? menu.children[0].path
      : `/${menu.children[0].path}`
    router.push(path)
  }
}

const logout = async () => {
  await userStore.logout()
  router.push('/login')
}
</script>

<style scoped lang="less">
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
  background-color: var(--td-bg-color-container);
}

.mr-2 {
  height: 50px;
}

.left-side {
  display: flex;
  align-items: center;
  padding-left: 20px;
}

.right-side {
  display: flex;
  padding-right: 20px;
  list-style: none;

  li {
    display: flex;
    align-items: center;
    padding: 0 10px;
  }

  a {
    color: var(--td-text-color-primary);
    text-decoration: none;
  }

  .nav-btn {
    border-color: var(--td-border-level-1-color);
    color: var(--td-text-color-primary);
    font-size: 16px;
  }

  .ref-btn {
    position: absolute;
    bottom: 14px;
  }
}
</style>

<style lang="less">
.message-popover {
  padding: 0 !important;

  .t-popup__content {
    padding: 0;
  }
}
</style>
