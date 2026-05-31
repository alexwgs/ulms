<template>
  <t-card class="box-card">
    <t-row :gutter="15">
      <t-col :span="9">
        <t-date-range-picker @change="dateChangeEvent" size="small" format="YYYY-MM-DD" v-model="dateDuration" :placeholder="['开始日期', '结束日期']"></t-date-range-picker>
      </t-col>
      <t-col :span="3" class="text-right">
        <t-button theme="primary" size="small" @click="taskApi.downloadTaskSummaryReport(queryInfo)">
          下载
        </t-button>
      </t-col>
    </t-row>
    <CustomTable rowKey="id" :data="caseTaskList" size="small" :default-sort="{ prop: 'take', order: 'descending' }" stripe
      height="calc(100vh - 270px)" :loading="loading">
      <TableColumn colKey="deptNum" sortable label="科室"></TableColumn>
      <TableColumn colKey="groupNum" sortable label="组别"></TableColumn>
      <TableColumn colKey="userId" sortable label="用户编号" width="180"></TableColumn>
      <TableColumn colKey="userName" sortable label="用户姓名"></TableColumn>
      <TableColumn colKey="miss" sortable label="忽略量"></TableColumn>
      <TableColumn colKey="take" sortable label="接起量"></TableColumn>
      <TableColumn colKey="refuse" sortable label="拒绝量"></TableColumn>
    </CustomTable>
  </t-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { taskApi } from '@/api/oht/task'

// 响应式数据
const loading = ref(false)
const caseTaskList = ref([])
const dateDuration = ref([new Date(), new Date()])

// 查询参数
const queryInfo = reactive({
  dates: '',
  orderType: 'desc',
  order: 'buildTime',
  querytype: '',
  query: '',
  pageSize: 20,
  pageNum: 1
})

// 初始化
onMounted(() => {
  getTaskSummary()
})

// 获取案件任务列表
const getTaskSummary = async () => {
  try {
    loading.value = true
    const res = await taskApi.getTaskSummary(queryInfo)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    caseTaskList.value = res.data
  } catch (error) {
    console.error('获取案件任务列表失败:', error)
    MessagePlugin.error('获取案件任务列表失败')
  } finally {
    loading.value = false
  }
}

// 日期范围变化事件
const dateChangeEvent = () => {
  if (dateDuration.value && dateDuration.value.length === 2) {
    queryInfo.dates = dateDuration.value.join(',')
  } else {
    queryInfo.dates = ''
  }
  taskApi.getTaskSummary()
}
</script>

<style lang="less" scoped>
.box-card {
  height: calc(100vh - 190px);
  overflow: auto;
}

.text-right {
  display: flex;
  justify-content: flex-end;
}
</style>
