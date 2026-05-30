<template>
  <div>
    <el-card class="box-card" style="height: 200px">
      <div style="float: left">
        <el-avatar
          shape="square"
          :size="160"
          fit="fill"
          :src="fsURL + userStore.avatar"
        ></el-avatar>
      </div>
      <div
        style="
          float: left;
          padding-left: 15px;
          min-width: 720px;
          padding-bottom: 20px;
        "
      >
        <el-tag effect="plain"
          >{{ userStore.ploName }} / {{ userStore.ploNum }}</el-tag
        >
        <el-row :gutter="10" style="font-size: 12px; padding-top: 15px">
          <el-col :span="12">科室：{{ userStore.deptName }}</el-col>
          <el-col :span="12">组别：{{ userStore.groupName }}</el-col>
        </el-row>
        <el-divider style="margin: 15px 0 15px 0"></el-divider>
        <el-row
          v-if="myPoint"
          :gutter="10"
          style="font-size: 12px; padding-top: 5px"
        >
          <el-col :span="6"
            >金币：<el-tag type="info">{{ myPoint.coin }}</el-tag></el-col
          >
          <el-col :span="6"
            >培训课程:
            <el-tag type="info">{{ myPoint.signNum }}</el-tag></el-col
          >
          <el-col :span="6"
            >授课课时: <el-tag type="info">{{ myPoint.hours }}</el-tag></el-col
          >
          <el-col :span="6"
            >积分: <el-tag type="info">{{ myPoint.point }}</el-tag></el-col
          >
        </el-row>
      </div>
    </el-card>
    <el-row :gutter="10" style="margin-top: 10px; width: 100%">
      <el-col :span="16">
        <el-card class="box-card" style="height: calc(100vh - 330px)">
          <template #header>
            <div class="clearfix">
              <span>进行中的学习</span>
            </div>
          </template>
          <div>
            <el-empty
              v-if="total == 0"
              :image-size="200"
              description="暂无数据"
            ></el-empty>
            <div v-else>
              <el-table
                :data="records"
                size="small"
                height="calc(100vh - 330px)"
                stripe
                style="width: 100%"
                @sort-change="tableSort($event)"
              >
                <el-table-column
                  prop="course.courseName"
                  label="课程名称"
                  show-overflow-tooltip
                ></el-table-column>
                <el-table-column
                  prop="studyComp"
                  label="课程进度"
                  sortable="custom"
                  width="100px"
                  show-overflow-tooltip
                >
                  <template #default="scope">
                    <el-tag
                      effect="plain"
                      size="small"
                      :type="scope.row.studyComp == 1 ? 'success' : 'danger'"
                    >
                      {{
                        scope.row.studyComp == 1 ? '已完成' : '未学完'
                      }}</el-tag
                    >
                  </template>
                </el-table-column>
                <el-table-column
                  prop="evalDate"
                  label="评价"
                  sortable="custom"
                  width="100px"
                  show-overflow-tooltip
                >
                  <template #default="scope">
                    <el-tag
                      effect="plain"
                      size="small"
                      :type="
                        scope.row.ifEval == 0
                          ? 'success'
                          : scope.row.evalComp == 1
                            ? 'success'
                            : 'danger'
                      "
                    >
                      {{
                        scope.row.ifEval == 0
                          ? '无需评价'
                          : scope.row.evalComp == 1
                            ? '已评价'
                            : '未评价'
                      }}</el-tag
                    >
                  </template>
                </el-table-column>
                <el-table-column
                  prop="examComp"
                  label="考试"
                  sortable="custom"
                  width="100px"
                  show-overflow-tooltip
                >
                  <template #default="scope">
                    <el-tag
                      effect="plain"
                      size="small"
                      :type="
                        scope.row.ifExam == 0
                          ? 'success'
                          : scope.row.examComp == 1
                            ? 'success'
                            : 'danger'
                      "
                    >
                      {{
                        scope.row.ifExam == 0
                          ? '免考'
                          : scope.row.examComp == 1
                            ? '已完成'
                            : '未完成'
                      }}</el-tag
                    >
                  </template>
                </el-table-column>
                <el-table-column label="查看" width="80px">
                  <template #default="scope">
                    <el-button
                      size="small"
                      type="primary"
                      @click="gotoCourseView(scope.row.courseId)"
                      >查看</el-button
                    >
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="box-card" style="height: calc(100vh - 330px)">
          <template #header>
            <div class="clearfix">
              <span>学习计划</span>
            </div>
          </template>
          <el-empty :image-size="200" description="暂未启用"></el-empty>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { httpInstance } from '@/utils/request'
import { useUserStore } from '@/stores'

const router = useRouter()
const userStore = useUserStore()

const fsURL = import.meta.env.VITE_FILE_BASE_URL
const myPoint = ref(null)
const queryInfo = ref({
  orderType: ' desc',
  order: '',
  coursePass: 2,
  pageSize: 100,
  pageNum: 1
})
const records = ref([])
const total = ref(0)

const getStudyLog = async () => {
  const res = await httpInstance({
    url: 'college/study/my/record',
    method: 'get',
    params: queryInfo.value
  })
  if (res.code !== 200) return ElMessage.error(res.msg)
  records.value = res.data.list
  total.value = res.data.total
}

const gotoCourseView = (courseId) => {
  const routeData = router.resolve({
    path: '/college/course/view',
    query: { courseId: courseId }
  })
  window.open(routeData.href, '_blank')
}

const tableSort = (data) => {
  if (data.order === 'ascending') queryInfo.value.orderType = ' asc '
  else if (data.order === 'descending') queryInfo.value.orderType = ' desc '
  queryInfo.value.order = data.prop
  getStudyLog()
}

onMounted(() => {
  myPoint.value = JSON.parse(window.localStorage.getItem('point'))
  getStudyLog()
})
</script>

<style lang="less" scoped></style>
