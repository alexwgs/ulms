<template>
  <el-row style="margin-top: 10px">
    <el-col>
      <el-card class="box-card">
        <template #header>
          <div class="clearfix">
            <span>推荐课程</span>
          </div>
        </template>
        <el-tabs tab-position="left">
          <el-tab-pane
            v-for="(items, index) in command"
            :key="index"
            :label="index"
            style="max-height: 470px"
          >
            <el-row :gutter="15" style="padding: 10px">
              <el-col
                :span="6"
                v-for="item in items"
                :key="item.courseId"
              >
                <CourseCard :item="item" :key="item.courseId"></CourseCard>
              </el-col>
            </el-row>
          </el-tab-pane>
        </el-tabs>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { courseApi } from '@/api/college/course.js'
import CourseCard from './index.vue'

const command = ref([])

const getCourseCommand = async () => {
  const res = await courseApi.getCommandCourseList()
  if (res.code !== 200) return ElMessage.error(res.msg)
  command.value = res.data
}

onMounted(() => {
  getCourseCommand()
})
</script>

<style lang="less" scoped>

</style>
