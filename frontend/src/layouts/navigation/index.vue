<template>
  <t-layout class="layout-container">
    <!-- 顶部导航栏 -->
    <t-header class="header">
      <div class="logo" v-if="appStore.logo">
        <img :src="appStore.logo" alt="Logo" />
      </div>

      <t-head-menu
        :value="activeMenu"
        theme="dark"
        class="menu"
        @change="handleMenuSelect"
      >
        <!-- 一级菜单项 -->
        <t-menu-item
          v-for="item in topLevelMenus"
          :key="item.key"
          :value="item.key"
        >
          <DynamicIcon v-if="item.icon" :name="mapIcon(item.icon)" />
          <span>{{ item.label }}</span>
        </t-menu-item>

        <!-- 带子菜单的项 -->
        <t-submenu
          v-for="subMenu in subMenus"
          :key="subMenu.key"
          :value="subMenu.key"
        >
          <template #title>
            <DynamicIcon v-if="subMenu.icon" :name="mapIcon(subMenu.icon)" />
            <span>{{ subMenu.label }}</span>
          </template>

          <!-- 二级菜单项 -->
          <t-menu-item
            v-for="child in subMenu.children"
            :key="child.key"
            :value="child.key"
          >
            <span>{{ child.label }}</span>
          </t-menu-item>
        </t-submenu>
      </t-head-menu>

      <!-- 用户信息/操作区 -->
      <div class="user-actions">
        <t-dropdown @click="handleCommand">
          <div class="user-info">
            <t-avatar :image="userStore.avatar" />
            <span class="username">{{ userStore.username }}</span>
          </div>
          <template #dropdown>
            <t-dropdown-menu>
              <t-dropdown-item :value="'userCenter'">个人中心</t-dropdown-item>
              <t-dropdown-item :value="'logout'" divider>退出登录</t-dropdown-item>
            </t-dropdown-menu>
          </template>
        </t-dropdown>
      </div>
    </t-header>

    <t-layout>
      <!-- 面包屑导航 -->
      <t-header class="breadcrumb-header">
        <t-breadcrumb separator="/">
          <t-breadcrumb-item
            v-for="item in breadcrumbItems"
            :key="item.key"
          >
            {{ item.label }}
          </t-breadcrumb-item>
        </t-breadcrumb>
      </t-header>

      <!-- 主内容区 -->
      <t-content class="content">
        <router-view />
      </t-content>

      <!-- 页脚 -->
      <t-footer class="footer">
        {{ appStore.footerText }}
      </t-footer>
    </t-layout>
  </t-layout>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { useAppStore } from '@/stores/app'
import { mapIcon } from '@/utils/iconMap'

const router = useRouter()
const userStore = useUserStore()
const appStore = useAppStore()

// 当前激活菜单
const activeMenu = ref('')

// 计算属性
const topLevelMenus = computed(() => {
  return userStore.menuList.filter(menu => !menu.children)
})

const subMenus = computed(() => {
  return userStore.menuList.filter(menu => menu.children)
})

const breadcrumbItems = computed(() => {
  const route = router.currentRoute.value
  return route.matched.map(item => ({
    key: item.name,
    label: item.meta?.title || item.name
  }))
})

// 方法
const handleCommand = (data) => {
  if (data.value === 'userCenter') {
    router.push('/user/center')
  } else if (data.value === 'logout') {
    handleLogout()
  }
}

const handleMenuSelect = (index) => {
  // 根据菜单key跳转路由
  const menu = findMenuByKey(index)
  if (menu && menu.path) {
    router.push(menu.path)
  }
}

const handleLogout = async () => {
  try {
    await userStore.logout()
    router.push('/login')
  } catch (error) {
    console.error('退出登录失败:', error)
  }
}

// 辅助方法：根据key查找菜单
const findMenuByKey = (key) => {
  const allMenus = userStore.menuList.flatMap(menu => 
    menu.children ? [menu, ...menu.children] : [menu]
  )
  return (allMenus || []).find(menu => menu?.key === key)
}

// 设置当前激活菜单
const setActiveMenu = () => {
  const route = router.currentRoute.value
  if (route.matched.length > 0) {
    activeMenu.value = route.matched[route.matched.length - 1].name
  }
}

// 监听路由变化
router.afterEach(() => {
  setActiveMenu()
})

// 初始化
onMounted(() => {
  setActiveMenu()
})
</script>

<style scoped>
.layout-container {
  min-height: 100vh;
}

.header {
  display: flex;
  align-items: center;
  padding: 0 24px;
  background-color: var(--td-bg-color-container);
  color: var(--td-text-color-primary);
  height: 60px;
}

.logo {
  margin-right: 24px;
}

.logo img {
  height: 32px;
}

.menu {
  flex: 1;
  border-bottom: none;
}

.user-actions {
  margin-left: auto;
}

.user-info {
  color: var(--td-text-color-primary);
  display: flex;
  align-items: center;
  cursor: pointer;
}

.username {
  margin-left: 8px;
}

.breadcrumb-header {
  background-color: var(--td-bg-color-container);
  padding: 0 24px;
  height: 48px;
  display: flex;
  align-items: center;
  border-bottom: 1px solid var(--td-border-level-1-color);
}

.content {
  padding: 24px;
  background-color: var(--td-bg-color-page);
  min-height: calc(100vh - 60px - 48px - 60px);
}

.footer {
  text-align: center;
  padding: 16px 24px;
  height: 60px;
  line-height: 28px;
  background-color: var(--td-bg-color-page);
}
</style>