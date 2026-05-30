<template>
  <el-row style="margin-top: 10px">
    <el-col>
      <el-card class="box-card">
        <template #header>
          <div class="clearfix">
            <span>明星讲师</span>
            <el-button
              style="float: right; padding: 3px 0"
              link
              @click="router.push('/college/teacher')"
              >更多></el-button
            >
          </div>
        </template>
        <el-row :gutter="15">
          <el-col
            :span="4"
            v-for="(item, index) in teachers"
            :key="index"
            style="margin-top: 10px"
          >
            <el-card :body-style="{ padding: '0px' }" shadow="always">
              <div style="cursor: pointer" @click="teacherView(item)">
                <div
                  v-if="item.avatar == null"
                  style="
                    height: 160px;
                    width: 100%;
                    text-align: center;
                    line-height: 160px;
                  "
                >
                  暂无照片
                </div>
                <img
                  v-else
                  :src="fsURL + 'upload/getFile/college-avatar/' + item.avatar"
                  class="image"
                  style="height: 160px; width: 100%"
                />
                <div style="padding: 14px">
                  <div
                    style="
                      overflow: hidden;
                      text-overflow: ellipsis;
                      white-space: nowrap;
                    "
                  >
                    <span>{{ item.ploName }}</span>
                    <span v-if="item.honor">
                      <el-tag
                        v-for="(honor, hIndex) in item.honor.split('、')"
                        :key="hIndex"
                        size="small"
                        type="danger"
                        >{{ honor }}</el-tag
                      >
                    </span>
                  </div>
                  <div
                    class="bottom clearfix"
                    style="height: 40px; overflow: hidden"
                  >
                    <span style="font-size: 10px; color: #999">{{
                      item.introduce
                    }}</span>
                  </div>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { httpInstance } from '@/utils/request'
import { ElMessage } from 'element-plus'

const router = useRouter()
const fsURL = ref(import.meta.env.VITE_FILE_BASE_URL)
const teachers = ref([])

const getTopTeacher = async () => {
  try {
    const res = await httpInstance.get('college/teacher/top')
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    teachers.value = res.data
  } catch (error) {
    ElMessage.error('获取讲师列表失败')
  }
}

const teacherView = (teacher) => {
  window.localStorage.setItem('teacher', JSON.stringify(teacher))
  router.push({ path: '/college/teacher/view' })
}

onMounted(() => {
  getTopTeacher()
})
</script>

<style lang="less" scoped>
.text-trim {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
