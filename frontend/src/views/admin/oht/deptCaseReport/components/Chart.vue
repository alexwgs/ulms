<template>
  <div ref="chartRef" :style="{ width: '100%', height: height }"></div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, watch, nextTick, computed } from 'vue'
import * as echarts from 'echarts'
import {
  GridComponent,
  TooltipComponent,
  LegendComponent,
  TitleComponent,
  ToolboxComponent,
  DataZoomComponent,
  MarkLineComponent
} from 'echarts/components'
import { BarChart, LineChart } from 'echarts/charts'
import { CanvasRenderer } from 'echarts/renderers'

// 注册 ECharts 组件
echarts.use([
  GridComponent,
  TooltipComponent,
  LegendComponent,
  TitleComponent,
  ToolboxComponent,
  DataZoomComponent,
  MarkLineComponent,
  BarChart,
  LineChart,
  CanvasRenderer
])

const props = defineProps({
  // 图表数据
  data: {
    type: Array,
    default: () => []
  },
  // 图表高度
  height: {
    type: String,
    default: '400px'
  },
  // 图表配置
  settings: {
    type: Object,
    default: () => ({
      showLine: ['放行接起率', '投诉接起率'],
      axisSite: { right: ['放行接起率', '投诉接起率'] },
      label: { show: true, position: 'top' },
      yAxisType: ['S', 'percent'],
      yAxisName: ['秒', '比率']
    })
  },
  // 标记线配置
  markLine: {
    type: Object,
    default: () => ({
      data: [{ name: '平均线', type: 'average' }]
    })
  }
})

const chartRef = ref(null)
let chartInstance = null
const resizeObserver = ref(null)

// 计算图表配置
const chartOption = computed(() => {
  if (!props.data || props.data.length === 0) {
    return {
      title: {
        text: '暂无数据',
        left: 'center',
        top: 'center',
        textStyle: {
          color: '#999',
          fontSize: 16
        }
      }
    }
  }

  // 提取数据
  const dates = props.data.map(item => item.dataDate + ' ' + item.hour + '时')
  
  // 柱状图数据 - 等候时长
  const type1WaitTime = props.data.map(item => item.type1WaitTime || 0)
  const type2WaitTime = props.data.map(item => item.type2WaitTime || 0)
  
  // 计算接起率 (假设有接起数和发起数)
  const type1AnswerRate = props.data.map(item => {
    const total = item.type1CaseNum || 0
    const answered = (item.type1Level1 || 0) + (item.type1Level2 || 0) + (item.type1Level3 || 0)
    return total > 0 ? (answered / total * 100).toFixed(2) : 0
  })
  
  const type2AnswerRate = props.data.map(item => {
    const total = item.type2CaseNum || 0
    const answered = (item.type2Level1 || 0) + (item.type2Level2 || 0) + (item.type2Level3 || 0)
    return total > 0 ? (answered / total * 100).toFixed(2) : 0
  })

  const { settings } = props

  return {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'cross',
        crossStyle: {
          color: '#999999'
        }
      }
    },
    toolbox: {
      feature: {
        dataView: { show: true, readOnly: false },
        magicType: { show: true, type: ['line', 'bar'] },
        restore: { show: true },
        saveAsImage: { show: true }
      }
    },
    legend: {
      data: ['放行等候时长', '投诉等候时长', '放行接起率', '投诉接起率'],
      bottom: 0
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '15%',
      top: '15%',
      
    },
    xAxis: [
      {
        type: 'category',
        data: dates,
        axisPointer: {
          type: 'shadow'
        },
        axisLabel: {
          rotate: 45,
          fontSize: 10
        }
      }
    ],
    yAxis: [
      {
        type: 'value',
        name: settings.yAxisName?.[0] || '秒',
        min: 0,
        axisLabel: {
          formatter: '{value} s'
        }
      },
      {
        type: 'value',
        name: settings.yAxisName?.[1] || '比率',
        min: 0,
        max: 100,
        axisLabel: {
          formatter: '{value} %'
        }
      }
    ],
    dataZoom: [
      {
        type: 'inside',
        start: 0,
        end: 100
      },
      {
        start: 0,
        end: 100
      }
    ],
    series: [
      {
        name: '放行等候时长',
        type: 'bar',
        data: type1WaitTime,
        label: settings.label,
        markLine: props.markLine
      },
      {
        name: '投诉等候时长',
        type: 'bar',
        data: type2WaitTime,
        label: settings.label,
        markLine: props.markLine
      },
      {
        name: '放行接起率',
        type: 'line',
        yAxisIndex: 1,
        data: type1AnswerRate,
        label: {
          show: true,
          position: 'top',
          formatter: '{c}%'
        },
        smooth: true
      },
      {
        name: '投诉接起率',
        type: 'line',
        yAxisIndex: 1,
        data: type2AnswerRate,
        label: {
          show: true,
          position: 'top',
          formatter: '{c}%'
        },
        smooth: true
      }
    ]
  }
})

// 初始化图表
const initChart = () => {
  if (!chartRef.value) return

  // 销毁已存在的实例
  if (chartInstance) {
    chartInstance.dispose()
  }

  chartInstance = echarts.init(chartRef.value)
  chartInstance.setOption(chartOption.value, true)

  // 设置 resize 监听
  setupResizeObserver()
}

// 更新图表
const updateChart = () => {
  if (chartInstance) {
    chartInstance.setOption(chartOption.value, true)
  }
}

// 设置 resize 观察器
const setupResizeObserver = () => {
  if (chartRef.value && window.ResizeObserver) {
    resizeObserver.value = new ResizeObserver(() => {
      if (chartInstance) {
        chartInstance.resize()
      }
    })
    resizeObserver.value.observe(chartRef.value)
  }
}

// 监听数据变化
watch(
  () => props.data,
  () => {
    nextTick(() => {
      updateChart()
    })
  },
  { deep: true }
)

// 监听高度变化
watch(
  () => props.height,
  () => {
    nextTick(() => {
      if (chartInstance) {
        chartInstance.resize()
      }
    })
  }
)

onMounted(() => {
  initChart()
})

onUnmounted(() => {
  if (resizeObserver.value) {
    resizeObserver.value.disconnect()
  }
  if (chartInstance) {
    chartInstance.dispose()
    chartInstance = null
  }
})
</script>

<style lang="less" scoped>
</style>
