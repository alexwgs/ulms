<template>
  <el-card class="box-card">
    <el-row :gutter="15">
      <el-col :span="18">
        <el-date-picker @change="dateChangeEvent" size="small" value-format="YYYY-MM-DD" v-model="dateDuration"
          type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-col>
      <el-col :span="6" class="text-right">
        <el-button type="primary" size="small" @click="taskApi.downloadTaskSummaryReport(queryInfo)">
          下载<i class="el-icon-download el-icon--right"></i>
        </el-button>
      </el-col>
    </el-row>
    <el-table :data="caseTaskList" size="small" :default-sort="{ prop: 'take', order: 'descending' }" stripe
      height="calc(100vh - 270px)" v-loading="loading">
      <el-table-column prop="deptNum" sortable label="科室"></el-table-column>
      <el-table-column prop="groupNum" sortable label="组别"></el-table-column>
      <el-table-column prop="userId" sortable label="用户编号" width="180"></el-table-column>
      <el-table-column prop="userName" sortable label="用户姓名"></el-table-column>
      <el-table-column prop="miss" sortable label="忽略量"></el-table-column>
      <el-table-column prop="take" sortable label="接起量"></el-table-column>
      <el-table-column prop="refuse" sortable label="拒绝量"></el-table-column>
    </el-table>
  </el-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
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
      ElMessage.error(res.msg)
      return
    }
    caseTaskList.value = res.data
  } catch (error) {
    console.error('获取案件任务列表失败:', error)
    ElMessage.error('获取案件任务列表失败')
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
