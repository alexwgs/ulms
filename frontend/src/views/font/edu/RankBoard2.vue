<template>
  <div class="container">
    <el-row :gutter="10">
      <el-col :span="7">
        <div class="my-rank-area">
          <h4 style="color: #fff; margin: 10px">团队榜单</h4>
          <div class="my-rank-info">
            <el-date-picker
              v-model="dateRange"
              size="small"
              type="daterange"
              value-format="YYYY-MM-DD"
              @change="dateChange"
              style="background-color: transparent; width: 100%"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
          </div>
          <div
            class="my-rank-info"
            style="text-align: center"
            v-if="global?.user?.jobLevel?.indexOf('100,102,103,104,105') === -1"
          >
            <el-button-group>
              <el-button
                type="primary"
                size="small"
                @click="
                  downloadExcel(
                    'edu/brush/score/report/group',
                    queryInfo,
                    '小组刷题情况.xlsx'
                  )
                "
                >小组报表</el-button
              >
              <el-button
                type="primary"
                size="small"
                @click="
                  downloadExcel(
                    'edu/brush/score/report/dept',
                    queryInfo,
                    '科室刷题情况.xlsx'
                  )
                "
                >科室报表</el-button
              >
              <el-button
                type="primary"
                size="small"
                @click="
                  downloadExcel(
                    'edu/brush/score/report/category',
                    queryInfo,
                    '各业务类型答题情况.xlsx'
                  )
                "
                >业务统计</el-button
              >
            </el-button-group>
          </div>
        </div>
        <el-table
          :data="leftTable"
          v-loading="loading"
          size="small"
          style="width: 100%"
          height="calc(100vh - 350px)"
        >
          <el-table-column
            prop="deptNum"
            label="科室"
            width="100"
            sortable
          ></el-table-column>
          <el-table-column
            prop="total"
            label="答题量"
            sortable
            width="80"
          ></el-table-column>
          <el-table-column prop="rightRate" label="正确率" sortable>
            <template #default="scope"
              >{{ (scope.row.rightRate * 100).toFixed(2) }}%</template
            >
          </el-table-column>
        </el-table>
      </el-col>
      <el-col :span="17">
        <el-table
          :data="mainTable"
          v-loading="loading"
          size="small"
          style="width: 100%"
          height="calc(100vh - 500px)"
        >
          <el-table-column
            prop="deptNum"
            label="科室"
            width="120"
            sortable
          ></el-table-column>
          <el-table-column
            prop="deptGroup"
            label="组别"
            width="100"
            sortable
          ></el-table-column>
          <el-table-column
            prop="total"
            label="答题量"
            sortable
            width="80"
          ></el-table-column>
          <el-table-column prop="rightRate" label="正确率" sortable width="100">
            <template #default="scope"
              >{{ (scope.row.rightRate * 100).toFixed(2) }}%</template
            >
          </el-table-column>
          <el-table-column
            prop="weakPoint1"
            label="薄弱业务1"
            sortable
          ></el-table-column>
          <el-table-column
            prop="weakPoint2"
            label="薄弱业务2"
            sortable
          ></el-table-column>
          <el-table-column
            prop="weakPoint3"
            label="薄弱业务3"
            sortable
          ></el-table-column>
        </el-table>
        <div
          id="myChart"
          style="
            width: 100%;
            height: 300px;
            background-color: rgba(252, 252, 252, 0.26);
            border-radius: 15px;
            margin-top: 10px;
          "
        ></div>
      </el-col>
    </el-row>
    <div></div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, nextTick } from 'vue'
import { brushReportApi } from '@/api/edu/brushReport'
import { downloadExcel } from '@/utils/request'
import { ElMessage } from 'element-plus'
import * as echarts from 'echarts'

const global = window.__POWERED_BY_QIANKUN__ ? window.$global : null

const formatDate = (date) => {
  return new Date(date).toISOString().split('T')[0]
}

const getDefaultDateRange = () => {
  const end = new Date()
  const start = new Date()
  start.setDate(start.getDate() - 6)
  return [formatDate(start), formatDate(end)]
}

const loading = ref(false)
const dateRange = ref(getDefaultDateRange())
const queryInfo = reactive({
  begDate: '',
  endDate: '',
  orderBy: 'right_num desc,right_rate desc,total desc'
})
const mainTable = ref([])
const leftTable = ref([])

const option = reactive({
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'cross',
      crossStyle: {
        color: '#000'
      }
    }
  },
  toolbox: {
    feature: {
      dataView: { show: true, readOnly: false },
      magicType: { show: true, type: ['bar', 'line'] },
      restore: { show: true },
      saveAsImage: { show: true }
    }
  },
  legend: {
    data: ['答题量', '正确率(%)']
  },
  dataZoom: {
    start: 0,
    end: 30
  },
  xAxis: [
    {
      type: 'category',
      data: [],
      axisPointer: {
        type: 'shadow'
      }
    }
  ],
  yAxis: [
    {
      type: 'value',
      name: '答题量',
      scale: true,
      axisLabel: {
        formatter: '{value}'
      }
    },
    {
      type: 'value',
      name: '正确率(%)',
      min: 0,
      max: 100,
      axisLabel: {
        formatter: '{value} %'
      }
    }
  ],
  series: [
    {
      name: '答题量',
      type: 'bar',
      data: []
    },
    {
      name: '正确率(%)',
      type: 'line',
      yAxisIndex: 1,
      data: []
    }
  ]
})

const listBrushData = async () => {
  if (dateRange.value.length > 0) {
    queryInfo.begDate = dateRange.value[0]
    queryInfo.endDate = dateRange.value[1]
  }
  loading.value = true

  const res = await brushReportApi.getGroupBrushList(queryInfo)
  if (res.code !== 200) {
    ElMessage.error(res.msg)
    loading.value = false
    return
  }
  mainTable.value = res.data

  const res2 = await brushReportApi.getDeptBrushList(queryInfo)
  if (res2.code !== 200) {
    ElMessage.error(res2.msg)
    loading.value = false
    return
  }
  leftTable.value = res2.data
  loading.value = false
}

const drawPie = async (id) => {
  const res = await brushReportApi.getCategoryBrushList(queryInfo)
  if (res.code !== 200) {
    ElMessage.error(res.msg)
    return
  }
  const categorys = res.data
  option.xAxis[0].data = categorys.map((e) => e.category)
  option.series[0].data = categorys.map((e) => e.total)
  option.series[1].data = categorys.map((e) => (e.rightRate * 100).toFixed(2))
  const charts = echarts.init(document.getElementById(id))
  charts.setOption(option)
}

const dateChange = () => {
  queryInfo.begDate = dateRange.value[0]
  queryInfo.endDate = dateRange.value[1]

  const begDate = new Date(queryInfo.begDate)
  const endDate = new Date(queryInfo.endDate)
  const diffDays = Math.floor((endDate - begDate) / (1000 * 60 * 60 * 24))

  if (diffDays > 90) {
    ElMessage.error('最长不可间隔超过90天！')
    return
  }
  listBrushData()
}

onMounted(() => {
  listBrushData()
  nextTick(() => {
    drawPie('myChart')
  })
})
</script>

<style lang="less" scoped>
.el-table {
  border-radius: 10px;
}
/*最外层透明*/
:deep(.el-table),
.el-table__expanded-cell {
  background-color: rgba(224, 224, 224, 0.25);
}
/* 表格内背景颜色 */
:deep(.el-table th),
:deep(.el-table tr),
:deep(.el-table td) {
  background-color: rgba(224, 224, 224, 0.25);
  color: #000;
}
.el-range-editor--mini :deep(.el-range-input) {
  background-color: transparent;
  color: #ffffff;
}
.my-rank-area {
  height: 130px;
  margin-bottom: 10px;
  border-radius: 10px;
  background-color: rgba(233, 233, 233, 0.24);
  padding: 10px;
  overflow-y: auto;
  .my-rank-info {
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    padding: 5px;
    font-size: 14px;
    margin-bottom: 5px;
  }
}
</style>
