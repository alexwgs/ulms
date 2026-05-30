<template>
  <div>
    <el-card class="box-card">
      <el-row :gutter="15">
        <el-col :span="24">
          <el-radio-group v-model="queryInfo.coursePass" size="small" @change="getStudyLog">
            <el-radio-button label="">全部</el-radio-button>
            <el-radio-button :label="1">学习通过</el-radio-button>
            <el-radio-button :label="0">学习不过</el-radio-button>
            <el-radio-button :label="2">学习中</el-radio-button>
          </el-radio-group>
        </el-col>
      </el-row>
      <el-alert
        title="提示"
        type="info"
        description="一门课程需按照课程需求通过评价（如需），考试（如需），考试需要通过方可完整完成课程学习，才可增加学分。学时不限制考试通过与否。"
        show-icon
        :closable="false"
      />
      <el-table
        :data="records"
        size="small"
        height="calc(100vh - 330px)"
        stripe
        style="width: 100%"
        @sort-change="tableSort"
      >
        <el-table-column prop="course.courseName" label="课程名称" show-overflow-tooltip />
        <el-table-column prop="coin" label="学分" sortable="custom" width="100px" />
        <el-table-column prop="hours" label="课时" sortable="custom" width="90px" />
        <el-table-column prop="studyDate" label="学习日期" sortable="custom" width="100px" show-overflow-tooltip />
        <el-table-column prop="studyComp" label="课程进度" sortable="custom" width="100px" show-overflow-tooltip>
          <template #default="scope">
            <el-tag effect="plain" size="small" :type="scope.row.studyComp == 1 ? 'success' : 'danger'">
              {{ scope.row.studyComp == 1 ? '已完成' : '未学完' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="evalDate" label="评价" sortable="custom" width="100px" show-overflow-tooltip>
          <template #default="scope">
            <el-tag
              effect="plain"
              size="small"
              :type="scope.row.ifEval == 0 ? 'success' : scope.row.evalComp == 1 ? 'success' : 'danger'"
            >
              {{ scope.row.ifEval == 0 ? '无需评价' : scope.row.evalComp == 1 ? '已评价' : '未评价' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="examComp" label="考试" sortable="custom" width="100px" show-overflow-tooltip>
          <template #default="scope">
            <el-tag
              effect="plain"
              size="small"
              :type="scope.row.ifExam == 0 ? 'success' : scope.row.examComp == 1 ? 'success' : 'danger'"
            >
              {{ scope.row.ifExam == 0 ? '免考' : scope.row.examComp == 1 ? '已完成' : '未完成' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="coursePass" label="是否通过" sortable="custom" width="100px">
          <template #default="scope">
            <el-tag
              effect="plain"
              size="small"
              :type="scope.row.coursePass == 2 ? 'warning' : scope.row.coursePass == 1 ? 'success' : 'danger'"
            >
              {{ scope.row.coursePass == 2 ? '学习中' : scope.row.coursePass == 1 ? '学习通过' : '学习不过' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="查看" width="80px">
          <template #default="scope">
            <el-button size="small" type="primary" @click="gotoCourseView(scope.row.courseId)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        v-model:current-page="queryInfo.pageNum"
        v-model:page-size="queryInfo.pageSize"
        :page-sizes="[20, 40, 100, 200]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { httpInstance } from '@/utils/request'

const router = useRouter()

const records = ref([])
const queryInfo = ref({
  orderType: ' desc',
  order: '',
  coursePass: '',
  pageSize: 20,
  pageNum: 1
})
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
  const routeData = router.resolve({ path: '/college/course/view', query: { courseId: courseId } })
  window.open(routeData.href, '_blank')
}

const tableSort = (data) => {
  if (data.order === 'ascending') queryInfo.value.orderType = ' asc '
  else if (data.order === 'descending') queryInfo.value.orderType = ' desc '
  queryInfo.value.order = data.prop
  getStudyLog()
}

const handleSizeChange = (pageSize) => {
  queryInfo.value.pageSize = pageSize
  getStudyLog()
}

const handleCurrentChange = (page) => {
  queryInfo.value.pageNum = page
  getStudyLog()
}

onMounted(() => {
  getStudyLog()
})
</script>

<style lang="less" scoped>

</style>
