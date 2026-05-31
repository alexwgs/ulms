import { defineStore } from 'pinia'
import { getMenu } from '@/api/auth.js'
import router from '@/router'

// 直接使用组件导入函数，不使用defineAsyncComponent包装
const Layout = () => import('@/layouts/index.vue')
const modules = import.meta.glob('@/views/**/*.vue')

// 辅助函数：检查组件是否存在
function getComponent(componentPath) {
  // componentPath不为空则判断是否以/开头，若不是则添加/
  if (!componentPath || !componentPath.startsWith('/')) {
    componentPath = '/' + componentPath
  }
  const component = modules[`/src/views${componentPath}/index.vue`]
  if (!component) {
    console.warn(`Component not found: ${componentPath}`)
    return () => import('@/views/exception/not-found/index.vue')
  }
  // 直接返回组件导入函数，不使用defineAsyncComponent包装
  return component
}

export function mapMenusToRoutes(menuList, basePath = '') {
  const routes = []

  menuList.forEach((menu) => {
    if (!menu.path && !menu.children) return

    const routePath = basePath + (menu.path || '')

    // 处理有子菜单的情况
    if (menu.children?.length > 0) {
      const childrenRoutes = mapMenusToRoutes(menu.children, '/')
      if (childrenRoutes.length > 0) {
        // 对于有子菜单的项，创建一个路由组
        // 注意：绝对不使用Layout组件，因为Layout已经在根路由中使用了
        // 所有动态路由都将作为根Layout路由的子路由，不应再包含Layout组件
        const route = {
          path: routePath,
          name: menu.name || String(menu.id),
          meta: {
            title: menu.meta?.title || menu.name,
            icon: menu.meta?.icon || menu.icon,
            isDynamic: true
          },
          // 对于有子菜单的项，不设置component，或者使用具体的页面组件
          // 绝对不使用Layout组件，避免嵌套
          component:
            menu.component !== 'LAYOUT' ? getComponent(menu.component) : null,
          children: childrenRoutes
        }
        routes.push(route)
      }
    }
    // 处理没有子菜单的情况
    else if (menu.path) {
      const route = {
        path: routePath,
        name: menu.name || String(menu.id),
        meta: {
          title: menu.meta?.title || menu.name,
          icon: menu.meta?.icon.replace('el-icon-', '') || menu.icon,
          isDynamic: true
        },
        component: getComponent(menu.component)
      }
      routes.push(route)
    }
  })

  return routes
}

const useMenuStore = defineStore('menu', {
  state: () => ({
    menuList: [],
    routeMenus: [],
    routeHasMounted: false
  }),

  getters: {
    routeMenusGet: (state) => state.routeMenus,
    // 获取所有一级菜单
    firstLevelMenus: (state) => state.menuList.filter((menu) => menu.pid === 0),
    // 根据ID获取菜单
    getMenuById: (state) => (menuId) => {
      return state.menuList.find((menu) => menu.id === menuId)
    },
    // 获取当前选中一级菜单的子菜单
    currentSubMenus: (state) => (firstMenuId) => {
      const firstMenu = state.menuList.find((menu) => menu.id === firstMenuId)
      return firstMenu ? firstMenu.children || [] : []
    },
    // 获取当前选中一级菜单的组件类型
    currentFirstMenuComponent: (state) => (firstMenuId) => {
      const firstMenu = state.menuList.find((menu) => menu.id === firstMenuId)
      return firstMenu ? firstMenu.component : null
    }
  },

  actions: {
    setMenuList(partial) {
      this.menuList = partial || []
    },

    setRouteMounted(value) {
      this.routeHasMounted = value
    },

    async getMenuList() {
      try {
        const res = await getMenu()
        // this.setMenuList(res.data)
        this.setMenuList(res.menu)
        await this.addRouteMenus()
        return true
      } catch (error) {
        console.error('获取菜单失败:', error)
        return false
      }
    },

    async addRouteMenus() {
      if (this.routeHasMounted) {
        console.warn('路由已加载，跳过重复添加')
        return
      }

      // 初始化基础布局路由
      if (!router.hasRoute('layout')) {
        router.addRoute({
          path: '/',
          name: 'layout',
          component: Layout,
          children: []
        })
      }

      // 清理现有动态路由
      this.cleanDynamicRoutes()

      if (!this.menuList?.length) return

      // 生成并添加新路由
      const childRoutes = mapMenusToRoutes(this.menuList)

      childRoutes.forEach((route) => {
        if (route.path.substring(0, 1) !== '/') route.path = '/' + route.path
        // 将所有动态路由作为layout路由的子路由添加
        router.addRoute('layout', route)
      })

      this.routeMenus = childRoutes
      this.setRouteMounted(true)
    },

    cleanDynamicRoutes() {
      router.getRoutes().forEach((route) => {
        if (route.name && route.meta?.isDynamic) {
          router.removeRoute(route.name)
        }
      })
    },

    // 获取菜单路径对应的菜单信息
    getMenuByPath(path) {
      const findMenu = (menuList) => {
        for (const menu of menuList) {
          if (menu.path === path) return menu
          if (menu.children && menu.children.length > 0) {
            const found = findMenu(menu.children)
            if (found) return found
          }
        }
        return null
      }
      return findMenu(this.menuList)
    }
  }
})

export default useMenuStore
