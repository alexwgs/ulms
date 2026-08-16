import {
  createRouter,
  createWebHashHistory,
  createWebHistory
} from 'vue-router'
import { constantRoutes, notFound } from '@/router/routers/constants'
import { useUserStore, useMenuStore, useAppStore } from '@/stores'
import { isLogin } from '@/utils/auth'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: constantRoutes,
  scrollBehavior() {
    return { top: 0 }
  }
})

router.beforeEach(async (to, from, next) => {
  NProgress.start()
  const userStore = useUserStore()
  const menuStore = useMenuStore()
  const appStore = useAppStore()

  // 1. 先处理独立页面（不需要登录和菜单的页面）
  if (to.meta.standalone) {
    next()
    return
  }

  try {
    // 2. 检查是否已添加404路由，如果没有则添加
    if (!router.hasRoute('notFound')) {
      router.addRoute('layout', notFound)
    }

    // 3. 处理登录状态
    if (isLogin()) {
      // 4. 初始化菜单数据（如果需要）
      if (!menuStore.routeHasMounted) {
        try {
          await initializeAppData(userStore, menuStore)
          menuStore.setRouteMounted(true)
          setDefaultFirstMenu(menuStore, appStore)

          // 重要：确保当前路由存在，如果不存在则重定向到默认页面
          const resolvedTarget = router.resolve(to.fullPath || to.path)
          if (resolvedTarget.matched.length === 0) {
            const defaultPath =
              menuStore.firstLevelMenus[0]?.path || '/admin/dashboard'
            next({ path: defaultPath, replace: true })
            return
          }

          next({ ...to, replace: true })
          return
        } catch (error) {
          userStore.logout()
          throw error
        }
      }

      // 5. 检查路由是否存在，避免"no match found"错误
      if (to.matched.length === 0) {
        // 尝试获取一个有效的默认路由
        const defaultPath =
          menuStore.firstLevelMenus[0]?.path || '/admin/dashboard'
        next({ path: defaultPath, replace: true })
      } else {
        next()
      }
    } else {
      if (to.name === 'login') {
        next()
      } else {
        next({
          name: 'login',
          query: { redirect: to.fullPath },
          replace: true
        })
      }
    }
  } catch (error) {
    console.error('路由守卫错误:', error)
    next({ name: 'login', replace: true })
  } finally {
    NProgress.done()
  }
})

// 路由变化后，根据当前路径设置选中的一级菜单
router.afterEach((to) => {
  const menuStore = useMenuStore()
  const appStore = useAppStore()

  // 根据当前路径找到对应的菜单
  const menu = menuStore.getMenuByPath(to.path)
  if (menu) {
    // 找到对应的一级菜单
    const firstMenu = findFirstMenu(menuStore.menuList, menu.id)
    if (firstMenu) {
      const hasChildren = firstMenu.children && firstMenu.children.length > 0
      // 设置当前选中的一级菜单，包括组件类型
      appStore.setActiveFirstMenu(
        firstMenu.id,
        hasChildren,
        firstMenu.component
      )
    }
  }
})

// 定义一个异步函数 initializeAppData，用于初始化应用数据
async function initializeAppData(userStore, menuStore) {
  // 使用 Promise.all 并行执行 userStore.info() 和 menuStore.getMenuList() 两个异步操作
  // Promise.all 接收一个包含多个 Promise 的数组，并返回一个新的 Promise
  // 新的 Promise 在所有传入的 Promise 都完成时完成，或者在任何一个 Promise 失败时失败
  await Promise.all([userStore.info(), menuStore.getMenuList()])
  // 调用 router 的 addRoute 方法，添加一个新的路由
  // 'layout' 是路由的名称，notFound 是对应的路由组件或配置
  router.addRoute('layout', notFound)
}

// 设置默认选中的一级菜单
function setDefaultFirstMenu(menuStore, appStore) {
  const firstMenus = menuStore.firstLevelMenus
  if (firstMenus.length > 0) {
    const firstMenu = firstMenus[0]
    const hasChildren = firstMenu.children && firstMenu.children.length > 0
    // 设置默认选中的一级菜单，包括组件类型
    appStore.setActiveFirstMenu(firstMenu.id, hasChildren, firstMenu.component)
  }
}

// 根据菜单ID查找对应的一级菜单
function findFirstMenu(menuList, menuId) {
  // 先查找是否是一级菜单
  const firstMenu = menuList.find((menu) => menu.id === menuId)
  if (firstMenu) return firstMenu

  // 递归查找子菜单
  for (const menu of menuList) {
    if (menu.children && menu.children.length > 0) {
      const found = findFirstMenu(menu.children, menuId)
      if (found) {
        // 如果找到的不是一级菜单，返回它的一级菜单
        const parentFirstMenu = menuList.find(
          (m) => m.id === menu.pid || m.id === menu.id
        )
        return parentFirstMenu.pid === 0
          ? parentFirstMenu
          : menuList.find((m) => m.id === parentFirstMenu.pid)
      }
    }
  }

  return null
}

export default router
