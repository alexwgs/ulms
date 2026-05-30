const EDU = {
  path: '/edu',
  name: 'exam-brush',
  component: () => import('@/views/font/edu/index.vue'),
  redirect: '/edu/brush',
  meta: {
    title: '小招学霸',
    hidden: true,
    standalone: true
  },
  children: [
    {
      path: 'brush',
      name: 'edu-brush',
      component: () => import('@/views/font/edu/Brush.vue'),
      meta: { title: '刷题入口' }
    },
    {
      path: 'myexam',
      name: 'edu-myexam',
      component: () => import('@/views/font/edu/ExamList.vue'),
      meta: { title: '我的考试' }
    },
    {
      path: 'myquestion',
      name: 'edu-myquestion',
      component: () => import('@/views/font/edu/MyQuestion.vue'),
      meta: { title: '我的题库' }
    },
    {
      path: 'competition',
      name: 'edu-competition',
      component: () => import('@/views/font/edu/Competition.vue'),
      meta: { title: '知识竞赛' }
    },
    {
      path: 'rankBoard',
      name: 'edu-rank-board',
      component: () => import('@/views/font/edu/RankBoard.vue'),
      meta: { title: '个人学霸榜' }
    },
    {
      path: 'RankBoard2',
      name: 'edu-rank-board2',
      component: () => import('@/views/font/edu/RankBoard2.vue'),
      meta: { title: '团队学霸榜' }
    },
    {
      path: 'exam/test',
      name: 'edu-exam-test',
      component: () => import('@/views/font/edu/Exam.vue'),
      meta: { title: '考试答题' }
    },
    {
      path: 'exam/test/view/:examCode',
      name: 'edu-exam-test-view',
      component: () => import('@/views/font/edu/TestView.vue'),
      meta: { title: '查看试卷' }
    }
  ]
}
export default EDU
