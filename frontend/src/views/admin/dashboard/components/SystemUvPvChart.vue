<template>
  <t-card :bordered="true" class="chart-card">
    <template #header>
      <span class="card-title">系统访问趋势（UV/PV）</span>
    </template>
    <template #actions>
      <t-date-range-picker
        v-model="dateRange"
        format="YYYY-MM-DD"
        :placeholder="['开始日期', '结束日期']"
        :presets="datePresets"
        size="small"
        @change="fetchData"
      />
    </template>
    <AsyncECharts
      :option="chartOption"
      :loading="loading"
      :error="error"
      height="360px"
    />
  </t-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import dayjs from 'dayjs'
import { MessagePlugin } from 'tdesign-vue-next'
import AsyncECharts from '@/components/AsyncECharts.vue'
import { dashboardApi } from '@/api/admin/dashboard'

const loading = ref(false)
const error = ref('')

const dateRange = ref([
  dayjs().subtract(6, 'day').format('YYYY-MM-DD'),
  dayjs().format('YYYY-MM-DD')
])

const datePresets = {
  '近7天': [dayjs().subtract(6, 'day').format('YYYY-MM-DD'), dayjs().format('YYYY-MM-DD')],
  '近14天': [dayjs().subtract(13, 'day').format('YYYY-MM-DD'), dayjs().format('YYYY-MM-DD')],
  '近30天': [dayjs().subtract(29, 'day').format('YYYY-MM-DD'), dayjs().format('YYYY-MM-DD')]
}

const chartOption = ref({})

/** 填充缺失日期为零值 */
const fillZeroDates = (data, begDate, endDate) => {
  const result = []
  const dataMap = new Map(data.map(d => [d.datestr, d]))
  let current = dayjs(begDate)
  const end = dayjs(endDate)
  while (current.isBefore(end) || current.isSame(end, 'day')) {
    const key = current.format('YYYY-MM-DD')
    const row = dataMap.get(key)
    result.push({ datestr: key, uv: row ? Number(row.uv) || 0 : 0, pv: row ? Number(row.pv) || 0 : 0 })
    current = current.add(1, 'day')
  }
  return result
}

/** 计算天数差 */
const computeDays = (range) => {
  if (!range || range.length !== 2) return 7
  return dayjs(range[1]).diff(dayjs(range[0]), 'day') + 1
}

/** 构建 ECharts option */
const buildOption = (data) => {
  const dates = data.map(d => d.datestr)
  const uvData = data.map(d => d.uv)
  const pvData = data.map(d => d.pv)

  return {
    toolbox: {
      right: 10,
      feature: {
        dataView: { show: true, readOnly: false },
        magicType: { show: true, type: ['line', 'bar'] },
        restore: { show: true },
        saveAsImage: { show: true }
      }
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: { type: 'cross' }
    },
    legend: {
      data: ['UV', 'PV'],
      top: 0
    },
    grid: {
      left: '3%',
      right: '5%',
      bottom: '3%',
      top: '40px',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: dates,
      axisTick: { alignWithLabel: true }
    },
    yAxis: [
      {
        name: 'UV',
        type: 'value',
        position: 'left',
        minInterval: 1
      },
      {
        name: 'PV',
        type: 'value',
        position: 'right'
      }
    ],
    series: [
      {
        name: 'UV',
        type: 'bar',
        barWidth: '35%',
        itemStyle: { color: '#0052d9' },
        data: uvData
      },
      {
        name: 'PV',
        type: 'line',
        yAxisIndex: 1,
        itemStyle: { color: '#ed7b2f' },
        data: pvData,
        markLine: {
          silent: true,
          data: [{ type: 'average', name: 'PV均值' }]
        }
      }
    ]
  }
}

const fetchData = async () => {
  loading.value = true
  error.value = ''
  try {
    const days = computeDays(dateRange.value)
    const res = await dashboardApi.getUvPvTrend(days)
    if (res.code !== 200) {
      error.value = res.msg || '获取数据失败'
      return
    }
    const filled = fillZeroDates(res.data || [], dateRange.value[0], dateRange.value[1])
    chartOption.value = buildOption(filled)
  } catch (e) {
    console.error('获取UV/PV趋势失败:', e)
    error.value = '获取数据失败'
  } finally {
    loading.value = false
  }
}

onMounted(() => { fetchData() })
</script>

<style lang="less" scoped>
.chart-card {
  height: 100%;
}

.card-title {
  font-size: 16px;
  font-weight: 600;
}
</style>
