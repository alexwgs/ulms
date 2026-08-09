const Layout = () => import('@/layouts/index.vue')

const COLLEGE = {
  path: '/college',
  name: 'college',
  component: () => import('@/views/font/college/index.vue'),
  meta: {
    title: '学习平台',
    icon: 'book-open'
  },
  children: [
    {
      path: '',
      name: 'college-home',
      component: () => import('@/views/font/college/home/index.vue'),
      meta: { title: '首页' }
    },
    {
      path: 'library',
      name: 'college-library',
      component: () => import('@/views/font/college/library/index.vue'),
      meta: { title: '课程库' }
    },
    {
      path: 'teacher',
      name: 'college-teacher',
      component: () => import('@/views/font/college/teacher/list.vue'),
      meta: { title: '讲师列表' }
    },
    {
      path: 'teacher/:id',
      name: 'college-teacher-detail',
      component: () => import('@/views/font/college/teacher/detail.vue'),
      meta: { title: '讲师详情', hidden: true }
    },
    {
      path: 'course',
      name: 'college-course',
      component: () => import('@/views/font/college/course/command.vue'),
      meta: { title: '课程列表' }
    },
    {
      path: 'course/view/:courseId',
      name: 'college-course-view',
      component: () => import('@/views/font/college/course/view.vue'),
      meta: { title: '课程详情', hidden: true }
    },
    {
      path: 'course/study',
      name: 'college-course-study',
      component: () => import('@/views/font/college/course/study.vue'),
      meta: { title: '课程学习', hidden: true }
    },
    {
      path: 'course/command',
      name: 'college-course-command',
      component: () => import('@/views/font/college/course/command.vue'),
      meta: { title: '课程评价' }
    },
    {
      path: 'course/exam/:courseId',
      name: 'college-exam',
      component: () => import('@/views/font/college/exam/index.vue'),
      meta: { title: '考试' }
    },
    {
      path: 'evaluate',
      name: 'college-evaluate',
      component: () => import('@/views/font/college/evaluate/index.vue'),
      meta: { title: '评价' }
    },
    {
      path: 'user',
      name: 'college-user',
      component: () => import('@/views/font/college/user/index.vue'),
      meta: { title: '用户列表' }
    },
    {
      path: 'view-course-study',
      name: 'college-view-course-study',
      component: () =>
        import('@/views/font/college/view-course-study/index.vue'),
      meta: { title: '课程学习查看' }
    },
    {
      path: 'my',
      name: 'college-center',
      component: () => import('@/views/font/college/center/index.vue'),
      meta: { title: '学习中心' },
      children: [
        {
          path: '',
          name: 'college-my',
          component: () => import('@/views/font/college/my/index.vue'),
          meta: { title: '学习中心' }
        },
        {
          path: 'study-record',
          name: 'college-my-study-record',
          component: () => import('@/views/font/college/my/study-record.vue'),
          meta: { title: '学习记录' }
        },
        {
          path: 'point-log',
          name: 'college-my-point-log',
          component: () => import('@/views/font/college/my/point-log.vue'),
          meta: { title: '积分记录' }
        },
        {
          path: 'hour-log',
          name: 'college-my-hour-log',
          component: () => import('@/views/font/college/my/hour-log.vue'),
          meta: { title: '学时记录' }
        },
        {
          path: 'study-report',
          name: 'college-my-study-report',
          component: () => import('@/views/font/college/my/information.vue'),
          meta: { title: '学习报表' }
        }
      ]
    }
  ]
}
export default COLLEGE
