<template>
  <t-row style="margin-top: 10px">
    <t-col :span="12">
      <t-card class="academy-card">
        <h3 class="academy-section-title">推荐课程</h3>
        <t-tabs v-model="activeTab" placement="left">
          <t-tab-panel
            v-for="(items, index) in command"
            :key="index"
            :value="index"
            :label="index"
            style="max-height: 470px"
          >
            <t-row :gutter="15" style="padding: 10px">
              <t-col
                :span="3"
                v-for="item in items"
                :key="item.courseId"
              >
                <CourseCard :item="item" :key="item.courseId"></CourseCard>
              </t-col>
            </t-row>
          </t-tab-panel>
        </t-tabs>
      </t-card>
    </t-col>
  </t-row>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { courseApi } from '@/api/college/course.js'
import CourseCard from './index.vue'

const command = ref({})
// 分类数据异步加载，加载完成后自动选中第一个分类，避免 tabs 内容区为空
const activeTab = ref('')
watch(
  command,
  (val) => {
    const keys = Object.keys(val || {})
    if (keys.length && !keys.includes(activeTab.value)) {
      activeTab.value = keys[0]
    }
  },
  { immediate: true }
)

const getCourseCommand = async () => {
  const res = await courseApi.getCommandCourseList()
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  command.value = res.data
}

onMounted(() => {
  getCourseCommand()
})
</script>

<style lang="less" scoped>
.academy-card :deep(.t-tabs__nav) {
  min-width: 120px;
}

.academy-card :deep(.t-tabs__nav-item) {
  border-radius: 6px;
}

.academy-card :deep(.t-tabs__nav-item.t-is-active) {
  background: rgba(20, 50, 78, 0.08);
  color: var(--academy-navy);
}
</style>
