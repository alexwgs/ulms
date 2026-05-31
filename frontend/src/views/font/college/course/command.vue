<template>
  <t-row style="margin-top: 10px">
    <t-col>
      <t-card class="box-card">
        <template #header>
          <div class="clearfix">
            <span>推荐课程</span>
          </div>
        </template>
        <t-tabs placement="left">
          <t-tab-panel
            v-for="(items, index) in command"
            :key="index"
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
import { ref, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { courseApi } from '@/api/college/course.js'
import CourseCard from './index.vue'

const command = ref([])

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

</style>
