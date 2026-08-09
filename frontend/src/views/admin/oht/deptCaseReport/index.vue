<template>
  <t-card class="management-card">
    <t-form :data="queryInfo" label-width="80px" colon class="filter-form">
      <t-row :gutter="[24, 24]">
        <t-col :span="5">
          <t-form-item label="日期范围" name="dateDuration">
            <t-date-range-picker v-model="dateDuration" @update:model-value="dateChangeEvent" size="small" :placeholder="['开始日期', '结束日期']"></t-date-range-picker>
          </t-form-item>
        </t-col>
        <t-col :span="3" class="operation-container">
          <t-button theme="primary" size="small" @click="caseApi.downloadCaseDeptReport(queryInfo)">
            下载
          </t-button>
        </t-col>
      </t-row>
    </t-form>
    <CustomTable rowKey="id" :data="caseTableQuery" size="small" sortable height="580px" stripe style="width: 100%"
      :loading="loading">
      <TableColumn colKey="dataDate" sortable label="日期"></TableColumn>
      <TableColumn colKey="hour" width="70" sortable label="时段"></TableColumn>
      <TableColumn colKey="type1CaseNum" sortable label="放行发起数"></TableColumn>
      <TableColumn colKey="type1Level1" sortable label="业务放行(1级)"></TableColumn>
      <TableColumn colKey="type1Level2" sortable label="业务放行(2级)"></TableColumn>
      <TableColumn colKey="type1Level3" sortable label="业务放行(3级)"></TableColumn>
      <TableColumn colKey="type1WaitTime" sortable label="放行等候时长(s)"></TableColumn>
      <TableColumn colKey="type2CaseNum" sortable label="投诉发起数"></TableColumn>
      <TableColumn colKey="type2Level1" sortable label="投诉案件(1级)"></TableColumn>
      <TableColumn colKey="type2Level2" sortable label="投诉案件(2级)"></TableColumn>
      <TableColumn colKey="type2Level3" sortable label="投诉案件(3级)"></TableColumn>
      <TableColumn colKey="type2WaitTime" sortable label="投诉等候时长(s)"></TableColumn>
    </CustomTable>
    <Chart :data="caseTableQuery" :settings="chartSettings" :markLine="markLine" height="450px"></Chart>
  </t-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'

import { caseApi } from '@/api/oht/case'
import Chart from './components/Chart.vue'

// 响应式数据
const loading = ref(false)
const caseTableQuery = ref([])
const dateDuration = ref([])

// 查询参数
const queryInfo = reactive({
  dates: '',
  querytype: '',
  query: '',
  pageSize: 20,
  pageNum: 1
})

// 图表设置
const chartSettings = {
  showLine: ['放行接起率', '投诉接起率'],
  axisSite: { right: ['放行接起率', '投诉接起率'] },
  label: { show: true, position: 'top' },
  yAxisType: ['S', 'percent'],
  yAxisName: ['秒', '比率']
}

// 标记线设置
const markLine = {
  data: [{ name: '平均线', type: 'average' }]
}

// 初始化
onMounted(() => {
  getCaseList()
})

// 获取案件列表
const getCaseList = async () => {
  try {
    loading.value = true
    const res = await caseApi.getCaseDeptList(queryInfo)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    caseTableQuery.value = res.data || []
  } catch (error) {
    console.error('获取部门案件报表失败:', error)
    MessagePlugin.error('获取部门案件报表失败')
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
  getCaseList()
}
</script>

<style lang="less" scoped>
.management-card {
  height: calc(100vh - 190px);
  overflow: auto;
}

.text-right {
  display: flex;
  justify-content: flex-end;
}
</style>
