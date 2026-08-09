<template>
  <t-card :bordered="true" class="chart-card">
    <template #header>
      <span class="card-title">子系统访问趋势</span>
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

/** 子模块颜色调色板 */
const MODULE_COLORS = [
  '#0052d9', '#00a870', '#ed7b2f', '#e34d59', '#7b61ff',
  '#0594fa', '#2ba471', '#f5a623', '#d54941', '#8b5cf6'
]

/** 将 [{datestr, module, uv, pv}] pivot 为多系列格式 */
const pivotData = (rawData, begDate, endDate) => {
  // 生成完整日期列表
  const dates = []
  let current = dayjs(begDate)
  const end = dayjs(endDate)
  while (current.isBefore(end) || current.isSame(end, 'day')) {
    dates.push(current.format('YYYY-MM-DD'))
    current = current.add(1, 'day')
  }

  // 提取唯一模块
  const moduleSet = new Set()
  const dataMap = new Map()
  rawData.forEach(row => {
    const ds = (row.datestr || row.dateStr || (row.DATE_STR || '')).toLowerCase()
    const mod = (row.module || row.MODULE || 'UNKNOWN').toUpperCase()
    const key = ds + '|' + mod
    dataMap.set(key, {
      uv: Number(row.uv || 0),
      pv: Number(row.pv || 0)
    })
    moduleSet.add(mod)
  })
  const modules = [...moduleSet].sort()

  // 构建 series
  const series = modules.map((mod, idx) => {
    const color = MODULE_COLORS[idx % MODULE_COLORS.length]
    const pvValues = dates.map(d => {
      const cell = dataMap.get(d + '|' + mod)
      return cell ? cell.pv : 0
    })
    return {
      name: mod,
      type: 'line',
      data: pvValues,
      itemStyle: { color },
      smooth: true,
      connectNulls: true
    }
  })

  return { dates, series, modules }
}

/** 计算天数 */
const computeDays = (range) => {
  if (!range || range.length !== 2) return 7
  return dayjs(range[1]).diff(dayjs(range[0]), 'day') + 1
}

/** 构建 ECharts option */
const buildOption = (dates, series, modules) => ({
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
    trigger: 'axis'
  },
  legend: {
    data: modules,
    top: 0,
    type: 'scroll'
  },
  grid: {
    left: '3%',
    right: '5%',
    bottom: '3%',
    top: '40px',
    
  },
  xAxis: {
    type: 'category',
    data: dates,
    axisTick: { alignWithLabel: true }
  },
  yAxis: {
    name: 'PV',
    type: 'value',
    minInterval: 1
  },
  series
})

const fetchData = async () => {
  loading.value = true
  error.value = ''
  try {
    const days = computeDays(dateRange.value)
    const res = await dashboardApi.getSubsysVisitTrend(days)
    if (res.code !== 200) {
      error.value = res.msg || '获取数据失败'
      return
    }
    const { dates, series, modules } = pivotData(
      res.data || [],
      dateRange.value[0],
      dateRange.value[1]
    )
    chartOption.value = buildOption(dates, series, modules)
  } catch (e) {
    console.error('获取子系统访问趋势失败:', e)
    error.value = '获取数据失败'
  } finally {
    loading.value = false
  }
}

onMounted(() => { fetchData() })
</script>

<style lang="less" scoped>
</style>
