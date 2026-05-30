const Layout = () => import('@/layouts/index.vue')

const DASHBOARD = {
  path: '/',
  name: '工作台',
  component: Layout,
  meta: {
    icon: 'icon-dashboard',
    title: 'A6广场'
  },
  children: [
    {
      path: '/',
      name: 'welcome',
      component: () => import('@/views/font/welcome/index.vue'),
      meta: {
        title: '工作台'
      }
    }
  ]
}

export default DASHBOARD
