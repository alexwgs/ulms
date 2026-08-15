import { login as Login } from '@/router/modules/login'
import DASHBOARD from '@/router/modules/dashboard'
import COLLEGE from '@/router/modules/college'
import EDU from '@/router/modules/edu'
const Layout = () => import('@/layouts/index.vue')

export const constantRoutes = [
  Login,
  DASHBOARD,
  COLLEGE,
  EDU,
  {
    path: '/article/:type/:id',
    name: 'article-view',
    component: () => import('@/views/font/a6voice/view/index.vue'),
    meta: {
      title: '文章详情',
      hidden: true,
      standalone: true
    }
  },
  {
    path: '/',
    name: 'layout',
    component: Layout,
    children: [
      {
        path: 'admin/dashboard',
        name: 'admin-dashboard',
        component: () => import('@/views/admin/dashboard/index.vue')
      },
      {
        path: 'font/a6voice/my-article',
        name: '帖子管理',
        component: () => import('@/views/font/a6voice/my-article/index.vue')
      },
      {
        path: 'font/a6voice/collect',
        name: '我的收藏',
        component: () => import('@/views/font/a6voice/collect/index.vue')
      },
      {
        path: 'font/a6voice/atMe',
        name: '我的消息',
        component: () => import('@/views/font/a6voice/atMe/index.vue')
      },
      {
        path: 'font/a6voice/my-item',
        name: '项目管理',
        component: () => import('@/views/font/a6voice/my-item/index.vue')
      },
      {
        path: 'font/a6voice/article/:id',
        name: '新建帖子',
        component: () => import('@/views/font/a6voice/new/article/index.vue')
      },
      {
        path: 'font/a6voice/item/:id',
        name: '新建项目',
        component: () => import('@/views/font/a6voice/new/item/index.vue')
      },
      {
        path: 'font/a6voice/survey/:id',
        name: '新建调研',
        component: () => import('@/views/font/a6voice/new/survey/index.vue')
      },
      {
        path: 'user/center',
        name: 'info',
        component: () => import('@/views/user/center/index.vue'),
        meta: {
          title: '用户中心'
        }
      },
      {
        path: 'user/setting',
        name: 'setting',
        component: () => import('@/views/user/setting/index.vue'),
        meta: {
          title: '用户设置'
        }
      }
    ]
  },
  {
    path: '/notification',
    name: '系统消息',
    component: () => import('@/components/Notification.vue')
  },
  {
    path: '/404',
    name: '404',
    component: () => import('@/views/exception/404.vue')
  },
  {
    path: '/500',
    name: '500',
    component: () => import('@/views/exception/500.vue'),
    meta: {
      hidden: true
    }
  },
  {
    path: '/403',
    name: '403',
    component: () => import('@/views/exception/403.vue'),
    meta: {
      hidden: true
    }
  }
]

export const notFound = {
  path: '/:pathMatch(.*)*',
  name: 'notFound',
  component: () => import('@/views/exception/not-found/index.vue')
}

/**
 * 这个路由是为了防止vue-router在一开始匹配不到路由的时候报：
 * No match found for location with xxx 的警告
 */
export const defaultPathRoute = {
  path: window.location.hash.replace('#', '') || window.location.pathname,
  name: 'defaultRouteName',
  component: () => import('@/views/exception/403.vue'),
  meta: {
    hidden: true,
    noShowTabbar: true
  }
}
