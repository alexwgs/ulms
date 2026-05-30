<template>
  <el-container class="layout-container">
    <!-- 顶部导航栏 -->
    <el-header class="header">
      <div class="logo" v-if="appStore.logo">
        <img :src="appStore.logo" alt="Logo" />
      </div>
      
      <el-menu
        :default-active="activeMenu"
        mode="horizontal"
        background-color="#001529"
        text-color="#fff"
        active-text-color="#ffd04b"
        class="menu"
        @select="handleMenuSelect"
      >
        <!-- 一级菜单项 -->
        <el-menu-item 
          v-for="item in topLevelMenus" 
          :key="item.key" 
          :index="item.key"
        >
          <el-icon v-if="item.icon">
            <component :is="item.icon" />
          </el-icon>
          <span>{{ item.label }}</span>
        </el-menu-item>

        <!-- 带子菜单的项 -->
        <el-sub-menu 
          v-for="subMenu in subMenus" 
          :key="subMenu.key" 
          :index="subMenu.key"
        >
          <template #title>
            <el-icon v-if="subMenu.icon">
              <component :is="subMenu.icon" />
            </el-icon>
            <span>{{ subMenu.label }}</span>
          </template>
          
          <!-- 二级菜单项 -->
          <el-menu-item 
            v-for="child in subMenu.children" 
            :key="child.key" 
            :index="child.key"
          >
            <span>{{ child.label }}</span>
          </el-menu-item>
        </el-sub-menu>
      </el-menu>

      <!-- 用户信息/操作区 -->
      <div class="user-actions">
        <el-dropdown @command="handleCommand">
          <div class="user-info">
            <el-avatar :src="userStore.avatar" />
            <span class="username">{{ userStore.username }}</span>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="userCenter">个人中心</el-dropdown-item>
              <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </el-header>

    <el-container>
      <!-- 面包屑导航 -->
      <el-header class="breadcrumb-header">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item 
            v-for="item in breadcrumbItems" 
            :key="item.key"
          >
            {{ item.label }}
          </el-breadcrumb-item>
        </el-breadcrumb>
      </el-header>

      <!-- 主内容区 -->
      <el-main class="content">
        <router-view />
      </el-main>

      <!-- 页脚 -->
      <el-footer class="footer">
        {{ appStore.footerText }}
      </el-footer>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { useAppStore } from '@/stores/app'

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
const handleCommand = (command) => {
  if (command === 'userCenter') {
    router.push('/user/center')
  } else if (command === 'logout') {
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
  background: #001529;
  color: white;
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
  color: white;
  display: flex;
  align-items: center;
  cursor: pointer;
}

.username {
  margin-left: 8px;
}

.breadcrumb-header {
  background: #fff;
  padding: 0 24px;
  height: 48px;
  display: flex;
  align-items: center;
  border-bottom: 1px solid #f0f0f0;
}

.content {
  padding: 24px;
  background: #fff;
  min-height: calc(100vh - 60px - 48px - 60px);
}

.footer {
  text-align: center;
  padding: 16px 24px;
  height: 60px;
  line-height: 28px;
  background: #f0f2f5;
}
</style>