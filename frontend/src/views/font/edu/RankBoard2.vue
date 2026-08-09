<template>
  <div class="container">
    <t-row :gutter="10">
      <t-col :span="4">
        <div class="my-rank-area">
          <h4 style="color: #fff; margin: 10px">团队榜单</h4>
          <div class="my-rank-info">
            <t-date-range-picker v-model="dateRange" size="small" @change="dateChange" style="background-color: transparent; width: 100%" :placeholder="['开始日期', '结束日期']" ></t-date-range-picker>
          </div>
          <div
            class="my-rank-info"
            style="text-align: center"
            v-if="global?.user?.jobLevel?.indexOf('100,102,103,104,105') === -1"
          >
            <t-space>
              <t-button
                theme="primary"
                size="small"
                @click="
                  downloadExcel(
                    'edu/brush/score/report/group',
                    queryInfo,
                    '小组刷题情况.xlsx'
                  )
                "
                >小组报表</t-button
              >
              <t-button
                theme="primary"
                size="small"
                @click="
                  downloadExcel(
                    'edu/brush/score/report/dept',
                    queryInfo,
                    '科室刷题情况.xlsx'
                  )
                "
                >科室报表</t-button
              >
              <t-button
                theme="primary"
                size="small"
                @click="
                  downloadExcel(
                    'edu/brush/score/report/category',
                    queryInfo,
                    '各业务类型答题情况.xlsx'
                  )
                "
                >业务统计</t-button
              >
            </t-space>
          </div>
        </div>
        <CustomTable rowKey="id"
          :data="leftTable"
          :loading="loading"
          size="small"
          style="width: 100%"
          height="calc(100vh - 350px)">
          <TableColumn
            prop="deptNum"
            label="科室"
            width="100"
            sortable></TableColumn>
          <TableColumn
            prop="total"
            label="答题量"
            sortable
            width="80"></TableColumn>
          <TableColumn colKey="rightRate" label="正确率" sortable>
            <template #default="scope"
              >{{ (scope.row.rightRate * 100).toFixed(2) }}%</template
            >
          </TableColumn>
        </CustomTable>
      </t-col>
      <t-col :span="9">
        <CustomTable rowKey="id"
          :data="mainTable"
          :loading="loading"
          size="small"
          style="width: 100%"
          height="calc(100vh - 500px)">
          <TableColumn
            prop="deptNum"
            label="科室"
            width="120"
            sortable></TableColumn>
          <TableColumn
            prop="deptGroup"
            label="组别"
            width="100"
            sortable></TableColumn>
          <TableColumn
            prop="total"
            label="答题量"
            sortable
            width="80"></TableColumn>
          <TableColumn colKey="rightRate" label="正确率" sortable width="100">
            <template #default="scope"
              >{{ (scope.row.rightRate * 100).toFixed(2) }}%</template
            >
          </TableColumn>
          <TableColumn
            prop="weakPoint1"
            label="薄弱业务1"
            sortable></TableColumn>
          <TableColumn
            prop="weakPoint2"
            label="薄弱业务2"
            sortable></TableColumn>
          <TableColumn
            prop="weakPoint3"
            label="薄弱业务3"
            sortable></TableColumn>
        </CustomTable>
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
      </t-col>
    </t-row>
    <div></div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, nextTick } from 'vue'
import { brushReportApi } from '@/api/edu/brushReport'
import { downloadExcel } from '@/utils/request'
import { MessagePlugin } from 'tdesign-vue-next'
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
    MessagePlugin.error(res.msg)
    loading.value = false
    return
  }
  mainTable.value = res.data

  const res2 = await brushReportApi.getDeptBrushList(queryInfo)
  if (res2.code !== 200) {
    MessagePlugin.error(res2.msg)
    loading.value = false
    return
  }
  leftTable.value = res2.data
  loading.value = false
}

const drawPie = async (id) => {
  const res = await brushReportApi.getCategoryBrushList(queryInfo)
  if (res.code !== 200) {
    MessagePlugin.error(res.msg)
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
    MessagePlugin.error('最长不可间隔超过90天！')
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
.t-table {
  border-radius: 10px;
}
/*最外层透明*/
:deep(.t-table),
.t-table__expanded-cell {
background-color: rgba(224, 224, 224, 0.25);
}
/* 表格内背景颜色 */
:deep(.t-table th),
:deep(.t-table tr),
:deep(.t-table td) {
background-color: rgba(224, 224, 224, 0.25);
  color: #000;
}
.t-range-editor--mini :deep(.t-range-input) {
background-color: transparent;
  color: var(--td-bg-color-container);
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
