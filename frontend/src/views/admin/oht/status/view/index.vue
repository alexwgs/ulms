<template>
  <el-card class="box-card">
    <div class="table-filter">
      <el-row :gutter="15">
        <el-col :span="8">
          <el-date-picker size="small" v-model="dateRange" value-format="YYYY-MM-DD" type="daterange" @change="datePick"
            range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
        </el-col>
        <el-col :span="8">
          <el-input placeholder="点击右边按钮选择人员" size="small" v-model="userList">
            <template #append>
              <el-button size="small" @click="treeDialogVisiable = !treeDialogVisiable"
                icon="el-icon-zoom-in"></el-button>
            </template>
          </el-input>
        </el-col>
        <el-col :span="6">
          <el-button size="small" type="primary" @click="getChartData">查询</el-button>
          <el-button size="small" type="primary" @click="handleDownloadExcel">报表</el-button>
        </el-col>
      </el-row>
    </div>
    <AsyncECharts :option="chartOption" :loading="loading" height="550px" width="100%"></AsyncECharts>
  </el-card>
  <EmpTree v-model:treeVisiable="treeDialogVisiable" type="101" :mutiselect="true" @getChecked="getTreeChecked">
  </EmpTree>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { httpInstance, downloadExcel } from '@/utils/request'
import EmpTree from '@/components/EmpTree.vue'
import AsyncECharts from '@/components/AsyncECharts.vue'

const dateRange = ref([])
const userList = ref('')
const treeDialogVisiable = ref(false)
const loading = ref(false)

const params = reactive({
  userList: '',
  begDate: '',
  endDate: ''
})

const colors = ['#c23531', '#2f4554', '#61a0a8',
  '#d48265', '#91c7ae', '#749f83',
  '#ca8622', '#bda29a', '#6e7074',
  '#546570', '#c4ccd3', '#f1c40f', '#6D214F', '#BDC581']

const chartData = ref({
  columns: [],
  rows: []
})

const chartSettings = ref({
  stack: {}
})

// 计算图表配置
const chartOption = computed(() => {
  if (!chartData.value.rows || chartData.value.rows.length === 0) {
    return {}
  }

  const columns = chartData.value.columns || []
  const rows = chartData.value.rows || []

  // 第一列是维度（X轴）
  const dimension = columns[0]
  // 其他列是指标（系列）
  const metrics = columns.slice(1)

  const series = metrics.map((metric, index) => ({
    name: metric,
    type: 'bar',
    stack: chartSettings.value.stack[metric] || undefined,
    data: rows.map(row => row[metric]),
    itemStyle: {
      color: colors[index % colors.length]
    }
  }))

  return {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    legend: {
      data: metrics,
      bottom: 0
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '10%',
      top: '10%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: rows.map(row => row[dimension]),
      axisLabel: {
        rotate: 30
      }
    },
    yAxis: {
      type: 'value'
    },
    series: series,
    color: colors
  }
})

const getTreeChecked = (data) => {
  userList.value = ''
  for (const item of data) {
    userList.value += item.id + ','
  }
  params.userList = userList.value.substring(0, userList.value.length - 1)
}

const datePick = () => {
  params.begDate = dateRange.value[0]
  params.endDate = dateRange.value[1]
}

const getChartData = async () => {
  loading.value = true
  try {
    const res = await httpInstance.get('oht/statusJour/chart', { params })
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    chartSettings.value.stack = res.chartSettings[0]
    chartData.value = res.chartData
  } catch (error) {
    ElMessage.error('获取图表数据失败')
    console.error(error)
  } finally {
    loading.value = false
  }
}

const handleDownloadExcel = () => {
  downloadExcel(`oht/statusJour/download/total/${params.begDate}/${params.endDate}`, params, '业务主任状态明细.xlsx')
}
</script>

<style lang="less" scoped>
.table-filter {
  padding: 10px;

  span {
    font-size: 12px;
    margin-left: 20px;
  }

  .el-select {
    width: 70%;
  }
}

.box-card {
  height: calc(100vh - 190px);
  overflow: auto;
}
</style>
