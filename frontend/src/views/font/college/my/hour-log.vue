<template>
  <div>
    <t-card class="academy-card">
      <h3 class="academy-section-title">学时日志</h3>
      <t-row :gutter="15">
        <t-col :span="2">
          <t-tag theme="default" variant="light">学习累计时长: {{ currentHour }}（H）</t-tag>
        </t-col>
        <t-col :span="10">
          <t-date-range-picker v-model="dateRange" :placeholder="['开始日期', '结束日期']" @change="getStudyLog" size="small" />
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
        <TableColumn colKey="studyDate" label="课程时长" sortable="custom" width="100px" ellipsis />
        <TableColumn colKey="course.courseName" label="课程名称" ellipsis />
        <TableColumn colKey="coin" label="说明" sortable="custom">
          <template #default="scope">
            完成《{{ scope.row.course.courseName }}》课程学习，学习时长 + {{ scope.row.hours }}
          </template>
        </TableColumn>
      </CustomTable>
      <t-pagination
        v-model="queryInfo.pageNum"
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
import { MessagePlugin } from 'tdesign-vue-next'
import { httpInstance } from '@/utils/request'

const records = ref([])
const queryInfo = ref({
  orderType: ' desc',
  order: ' compDate',
  dateRange: '',
  pageSize: 20,
  pageNum: 1
})
const dateRange = ref([])
const currentHour = ref(JSON.parse(window.localStorage.getItem('point'))?.hours || 0)
const total = ref(0)

const getStudyLog = async () => {
  if (dateRange.value == null) queryInfo.value.dateRange = ''
  else queryInfo.value.dateRange = dateRange.value.join(',')
  const res = await httpInstance({
    url: 'college/study/my/hour',
    method: 'get',
    params: queryInfo.value
  })
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  records.value = res.data.list
  total.value = res.data.total
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
