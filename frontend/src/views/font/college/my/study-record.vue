<template>
  <div>
    <t-card class="box-card">
      <t-row :gutter="15">
        <t-col :span="12">
          <t-radio-group v-model="queryInfo.coursePass" size="small" @change="getStudyLog">
            <t-radio-button value="">全部</t-radio-button>
            <t-radio-button :value="1">学习通过</t-radio-button>
            <t-radio-button :value="0">学习不过</t-radio-button>
            <t-radio-button :value="2">学习中</t-radio-button>
          </t-radio-group>
        </t-col>
      </t-row>
      <t-alert
        title="提示"
        theme="info"
        message="一门课程需按照课程需求通过评价（如需），考试（如需），考试需要通过方可完整完成课程学习，才可增加学分。学时不限制考试通过与否。"
        show-icon
        :closable="false"
      />
      <CustomTable rowKey="id"
        :data="records"
        size="small"
        height="calc(100vh - 330px)"
        stripe
        style="width: 100%"
        @sort-change="tableSort">
        <TableColumn colKey="course.courseName" label="课程名称" ellipsis />
        <TableColumn colKey="coin" label="学分" sortable="custom" width="100px" />
        <TableColumn colKey="hours" label="课时" sortable="custom" width="90px" />
        <TableColumn colKey="studyDate" label="学习日期" sortable="custom" width="100px" ellipsis />
        <TableColumn colKey="studyComp" label="课程进度" sortable="custom" width="100px" ellipsis>
          <template #default="scope">
            <t-tag effect="plain" size="small" :theme="scope.row.studyComp == 1 ? 'success' : 'danger'">
              {{ scope.row.studyComp == 1 ? '已完成' : '未学完' }}
            </t-tag>
          </template>
        </TableColumn>
        <TableColumn colKey="evalDate" label="评价" sortable="custom" width="100px" ellipsis>
          <template #default="scope">
            <t-tag
              effect="plain"
              size="small"
              :theme="scope.row.ifEval == 0 ? 'success' : scope.row.evalComp == 1 ? 'success' : 'danger'"
            >
              {{ scope.row.ifEval == 0 ? '无需评价' : scope.row.evalComp == 1 ? '已评价' : '未评价' }}
            </t-tag>
          </template>
        </TableColumn>
        <TableColumn colKey="examComp" label="考试" sortable="custom" width="100px" ellipsis>
          <template #default="scope">
            <t-tag
              effect="plain"
              size="small"
              :theme="scope.row.ifExam == 0 ? 'success' : scope.row.examComp == 1 ? 'success' : 'danger'"
            >
              {{ scope.row.ifExam == 0 ? '免考' : scope.row.examComp == 1 ? '已完成' : '未完成' }}
            </t-tag>
          </template>
        </TableColumn>
        <TableColumn colKey="coursePass" label="是否通过" sortable="custom" width="100px">
          <template #default="scope">
            <t-tag
              effect="plain"
              size="small"
              :theme="scope.row.coursePass == 2 ? 'warning' : scope.row.coursePass == 1 ? 'success' : 'danger'"
            >
              {{ scope.row.coursePass == 2 ? '学习中' : scope.row.coursePass == 1 ? '学习通过' : '学习不过' }}
            </t-tag>
          </template>
        </TableColumn>
        <TableColumn label="查看" width="80px">
          <template #default="scope">
            <t-button size="small" theme="primary" @click="gotoCourseView(scope.row.courseId)">查看</t-button>
          </template>
        </TableColumn>
      </CustomTable>
      <t-pagination
        v-model:current="queryInfo.pageNum"
        v-model:page-size="queryInfo.pageSize"
        :page-size-options="[20, 40, 100, 200]"

        :total="total"
        @page-size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </t-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { MessagePlugin } from 'tdesign-vue-next'
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
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  records.value = res.data.list
  total.value = res.data.total
}

const gotoCourseView = (courseId) => {
  const routeData = router.resolve({ path: '/college/course/view', query: { courseId: courseId } })
  window.open(routeData.href, '_blank')
}

const tableSort = (data) => {
  if (!data.descending) queryInfo.value.orderType = ' asc '
  else if (data.descending) queryInfo.value.orderType = ' desc '
  queryInfo.value.order = data.sortBy
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
