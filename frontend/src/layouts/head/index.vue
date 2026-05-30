<template>
  <div class="navbar">
    <div class="left-side">
      <div class="flex items-center">
        <img alt="logo" src="../../assets/a6.png" class="mr-2" />
        <el-text class="text-lg font-bold">
          <!-- A6广场-管理后台 -->
        </el-text>
      </div>
    </div>

    <!-- 一级导航菜单 -->
    <div class="main-nav">
      <el-menu mode="horizontal" :default-active="activeFirstMenuId?.toString() || ''"
        background-color="var(--el-bg-color)" text-color="var(--el-menu-text-color)"
        active-text-color="var(--el-color-primary)" class="main-nav-menu">
        <el-menu-item v-for="menu in firstLevelMenus" :key="menu.id" :index="menu.id.toString()"
          @click="handleFirstMenuClick(menu)">
          <el-icon>
            <component :is="menu.icon" />
          </el-icon>
          <span>{{ menu.name }}</span>
        </el-menu-item>
      </el-menu>
    </div>

    <ul class="right-side">
      <li>
        <userStatus />
      </li>
      <li>
        <el-tooltip :content="theme === 'light' ? '切换至黑暗模式' : '切换至亮色模式'" placement="bottom">
          <el-button class="nav-btn" type="default" circle @click="handleToggleTheme">
            <template #icon>
              <el-icon v-if="theme === 'dark'">
                <moon />
              </el-icon>
              <el-icon v-else>
                <sunny />
              </el-icon>
            </template>
          </el-button>
        </el-tooltip>
      </li>
      <li>
        <el-tooltip content="消息通知" placement="bottom">
          <div class="message-box-trigger">
            <el-badge :value="9" dot>
              <el-button class="nav-btn" type="default" circle @click="setPopoverVisible">
                <el-icon>
                  <bell />
                </el-icon>
              </el-button>
            </el-badge>
          </div>
        </el-tooltip>
        <el-popover ref="popover" trigger="click" :width="400" popper-class="message-popover">
          <template #reference>
            <div ref="refBtn" class="ref-btn"></div>
          </template>
          <message-box />
        </el-popover>
      </li>
      <li>
        <el-dropdown trigger="click">
          <div class="flex items-center">
            <el-avatar :size="32" :src="fsBaseURL + userStore.avatar" />
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="$router.push({ name: 'info' })">
                <el-icon>
                  <user />
                </el-icon>
                <span class="ml-2">用户中心</span>
              </el-dropdown-item>
              <el-dropdown-item @click="$router.push({ name: 'setting' })">
                <el-icon>
                  <setting />
                </el-icon>
                <span class="ml-2">用户设置</span>
              </el-dropdown-item>
              <el-dropdown-item divided @click="logout">
                <el-icon><switch-button /></el-icon>
                <span class="ml-2">登出用户</span>
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useUserStore, useAppStore, useMenuStore, useOhtStore } from '@/stores'
import router from '@/router'
import userStatus from '@/layouts/head/components/userStatus.vue'

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

const fsBaseURL = import.meta.env.VITE_FILE_BASE_URL

// 主题相关
const isDark = ref(document.documentElement.classList.contains('dark'))

const theme = computed(() => {
  return isDark.value ? 'dark' : 'light'
})

const handleToggleTheme = () => {
  isDark.value = !isDark.value

  // 切换Element Plus主题
  if (isDark.value) {
    document.documentElement.classList.add('dark')
    appStore.toggleTheme('light')
  } else {
    document.documentElement.classList.remove('dark')
    appStore.toggleTheme('dark')
  }

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
  background-color: var(--el-bg-color);
  border-bottom: 1px solid var(--el-border-color);
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
    color: var(--el-text-color-primary);
    text-decoration: none;
  }

  .nav-btn {
    border-color: var(--el-border-color);
    color: var(--el-text-color-regular);
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

  .el-popover__title {
    margin-bottom: 0;
  }
}
</style>
