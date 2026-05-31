<template>
  <t-card :bordered="true" class="chart-card">
    <template #header>
      <span class="card-title">请求操作统计</span>
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

const fillZeroDates = (data, begDate, endDate) => {
  const result = []
  const dataMap = new Map(data.map(d => {
    const key = d.datestr ||
      (d.dateStr || (d.DATE_STR ? d.DATE_STR.toLowerCase() : ''))
    return [key, d]
  }))
  let current = dayjs(begDate)
  const end = dayjs(endDate)
  while (current.isBefore(end) || current.isSame(end, 'day')) {
    const key = current.format('YYYY-MM-DD')
    const row = dataMap.get(key)
    result.push({
      datestr: key,
      postCount: row ? Number(row.postcount || row.postCount || 0) : 0,
      putCount: row ? Number(row.putcount || row.putCount || 0) : 0,
      deleteCount: row ? Number(row.deletecount || row.deleteCount || 0) : 0,
      exceptionCount: row ? Number(row.exceptioncount || row.exceptionCount || 0) : 0,
      avgTakeTime: row ? Number(row.avgtaketime || row.avgTakeTime || 0) : 0
    })
    current = current.add(1, 'day')
  }
  return result
}

const computeDays = (range) => {
  if (!range || range.length !== 2) return 7
  return dayjs(range[1]).diff(dayjs(range[0]), 'day') + 1
}

const buildOption = (data) => {
  const dates = data.map(d => d.datestr)
  const postData = data.map(d => d.postCount)
  const putData = data.map(d => d.putCount)
  const deleteData = data.map(d => d.deleteCount)
  const exceptionData = data.map(d => d.exceptionCount)
  const avgTimeData = data.map(d => d.avgTakeTime)

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
      data: ['POST', 'PUT', 'DELETE', '异常数', '平均响应时间(ms)'],
      top: 0,
      type: 'scroll'
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
        name: '请求数',
        type: 'value',
        position: 'left',
        minInterval: 1
      },
      {
        name: '响应时间(ms)',
        type: 'value',
        position: 'right'
      }
    ],
    series: [
      {
        name: 'POST',
        type: 'bar',
        stack: 'ops',
        barWidth: '45%',
        itemStyle: { color: '#0052d9' },
        data: postData
      },
      {
        name: 'PUT',
        type: 'bar',
        stack: 'ops',
        itemStyle: { color: '#00a870' },
        data: putData
      },
      {
        name: 'DELETE',
        type: 'bar',
        stack: 'ops',
        itemStyle: { color: '#ed7b2f' },
        data: deleteData
      },
      {
        name: '异常数',
        type: 'line',
        itemStyle: { color: '#f56c6c' },
        data: exceptionData
      },
      {
        name: '平均响应时间(ms)',
        type: 'line',
        yAxisIndex: 1,
        itemStyle: { color: '#7b61ff' },
        data: avgTimeData,
        markLine: {
          silent: true,
          lineStyle: { type: 'dashed' },
          data: [{ type: 'average', name: 'Avg' }]
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
    const res = await dashboardApi.getMethodStatsTrend(days)
    if (res.code !== 200) {
      error.value = res.msg || '获取数据失败'
      return
    }
    const filled = fillZeroDates(res.data || [], dateRange.value[0], dateRange.value[1])
    chartOption.value = buildOption(filled)
  } catch (e) {
    console.error('获取请求统计失败:', e)
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
