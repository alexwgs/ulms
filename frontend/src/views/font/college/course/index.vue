<template>
  <el-card :body-style="{ padding: '0px' }" shadow="always" class="course-panel">
    <div @click="gotoCourseView(item.courseId)" :title="item.courseName">
      <img :src="fsURL + 'upload/getFile/college-cover/' + item.coverImg" width="100%" />
      <div class="info">
        <div style="position: absolute; margin-top: -181px; margin-left: -20px">
          <el-tag :type="teachMethod == 2 ? 'danger' : 'primary'" size="small" effect="dark">{{ teachMethod == 2 ?
            '任务课程' : '常规课程' }}</el-tag>
        </div>
        <div class="text-trim">
          {{ item.courseName }}
        </div>
        <div class="course-info">
          <div class="line">
            <div class="line-info left">
              {{ item.lecturer }}
            </div>
            <div class="right line-info">{{ item.studyNum }}人学习</div>
          </div>
        </div>
      </div>
    </div>
  </el-card>
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
</script>

<style lang="less" scoped>
.text-trim {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

:deep(.el-card__body) {
  padding: 0;
}

.course-panel {
  cursor: pointer;
  margin-bottom: 5px;

  img {
    height: 150px;
    width: 100%;
  }

  .info {
    padding: 14px;

    .course-info {
      overflow: hidden;

      .line {
        height: 18px;

        .line-info {
          display: inline-block;
          font-size: 14px;
          width: 50%;
          color: #999;
        }
      }
    }
  }
}
</style>
