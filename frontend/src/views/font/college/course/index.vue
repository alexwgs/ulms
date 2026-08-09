<template>
  <div class="academy-course-card">
    <div @click="gotoCourseView(item.courseId)" :title="item.courseName">
      <div class="cover-wrap">
        <img
          :src="fsURL + 'upload/getFile/college-cover/' + item.coverImg"
          width="100%"
          @error="hideBrokenImage"
        />
        <t-tag :theme="teachMethod == 2 ? 'danger' : 'primary'" size="small" variant="light" class="cover-tag">{{
          teachMethod == 2 ? '任务课程' : '常规课程' }}</t-tag>
      </div>
      <div class="info">
        <div class="course-name" :title="item.courseName">{{ item.courseName }}</div>
        <div class="course-line">
          <span>{{ item.lecturer }}</span>
          <span>{{ item.studyNum }}人学习</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'

const props = defineProps({
  item: {
    type: Object,
    required: true
  }
})

const router = useRouter()
// 展示类文件统一走 HTTPS 文件管理地址，避免混合内容被浏览器拦截
const fsURL = import.meta.env.VITE_FILE_BASE_URL

const teachMethod = computed(() => props.item.teachMethod)

const gotoCourseView = (courseId) => {
  // 通过命名路由解析，使用 params 传递 courseId
  const routeData = router.resolve({
    name: 'college-course-view',  // 使用路由名称，避免硬编码路径
    params: { courseId: courseId }
  })
  // 在新窗口打开
  window.open(routeData.href, '_blank')
}

// 封面缺失时隐藏破图图标
const hideBrokenImage = (e) => {
  e.target.style.display = 'none'
}
</script>
