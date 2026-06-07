<template>
  <div class="async-echarts-container">
    <!-- 图表容器 -->
    <div
      ref="chartRef"
      class="chart-container"
      :style="{
        height: height || '400px',
        width: width || '100%'
      }"
    ></div>

    <!-- 加载状态 -->
    <div v-if="loading" class="loading-overlay">
      <t-icon class="loading-icon"><LoadingIcon /></t-icon>
      <span class="loading-text">加载中...</span>
    </div>

    <!-- 错误提示 -->
    <div v-if="error" class="error-overlay">
      <t-icon class="error-icon"><ErrorCircleIcon /></t-icon>
      <span class="error-text">{{ error }}</span>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, watch, nextTick, computed } from 'vue'
import * as echarts from 'echarts/core'
import {
  GridComponent,
  TooltipComponent,
  DataZoomComponent,
  ToolboxComponent,
  LegendComponent,
  TitleComponent,
  MarkPointComponent,
  MarkLineComponent,
  DatasetComponent,
  TransformComponent
} from 'echarts/components'
import {
  BarChart,
  LineChart,
  PieChart,
  RadarChart,
  ScatterChart
} from 'echarts/charts'
import { CanvasRenderer } from 'echarts/renderers'
import { UniversalTransition } from 'echarts/features'
import { LoadingIcon, ErrorCircleIcon } from 'tdesign-icons-vue-next'
import { useAppStore } from '@/stores'

// 注册 ECharts 组件
echarts.use([
  GridComponent,
  TooltipComponent,
  DataZoomComponent,
  ToolboxComponent,
  LegendComponent,
  TitleComponent,
  MarkPointComponent,
  MarkLineComponent,
  DatasetComponent,
  TransformComponent,
  BarChart,
  LineChart,
  PieChart,
  RadarChart,
  ScatterChart,
  CanvasRenderer,
  UniversalTransition
])

// 定义 Dark 主题配置
const darkTheme = {
    backgroundColor: 'transparent',
  textStyle: {
    color: '#B9B8CE'
  },
  title: {
    textStyle: {
      color: '#EEF1FA'
    },
    subtextStyle: {
      color: '#B9B8CE'
    }
  },
  legend: {
    textStyle: {
      color: '#B9B8CE'
    }
  },
  tooltip: {
    backgroundColor: 'rgba(50,50,50,0.9)',
    borderColor: '#333',
    textStyle: {
      color: '#fff'
    }
  },
  categoryAxis: {
    axisLine: {
      lineStyle: {
        color: '#B9B8CE'
      }
    },
    axisTick: {
      lineStyle: {
        color: '#B9B8CE'
      }
    },
    axisLabel: {
      color: '#B9B8CE'
    },
    splitLine: {
      lineStyle: {
        color: '#484753'
      }
    }
  },
  valueAxis: {
    axisLine: {
      lineStyle: {
        color: '#B9B8CE'
      }
    },
    axisTick: {
      lineStyle: {
        color: '#B9B8CE'
      }
    },
    axisLabel: {
      color: '#B9B8CE'
    },
    splitLine: {
      lineStyle: {
        color: '#484753'
      }
    }
  }
}

// 注册 Dark 主题
echarts.registerTheme('dark', darkTheme)

// 定义 props
const props = defineProps({
  // 图表配置项
  option: {
    type: Object,
    default: () => ({})
  },
  // 加载状态
  loading: {
    type: Boolean,
    default: false
  },
  // 错误信息
  error: {
    type: String,
    default: ''
  },
  // 图表高度
  height: {
    type: String,
    default: '400px'
  },
  // 图表宽度
  width: {
    type: String,
    default: '100%'
  },
  // 是否自动调整大小
  autoResize: {
    type: Boolean,
    default: true
  },
  // 图表主题
  theme: {
    type: String,
    default: ''
  }
})

// 定义响应式数据
const chartRef = ref(null)
let chartInstance = null
const resizeObserver = ref(null)

// 获取应用状态
const appStore = useAppStore()
// 计算当前主题
const currentTheme = computed(() => {
  // 监听 appStore.theme 变化，动态返回主题字符串
  return appStore.theme === 'dark' ? 'dark' : ''
})

// 初始化图表
const initChart = () => {
  if (chartRef.value) {
    try {
      // 销毁已存在的图表实例
      if (chartInstance) {
        chartInstance.dispose()
      }

      // 创建新的图表实例，使用系统主题
      const theme = props.theme || currentTheme.value
      chartInstance = echarts.init(chartRef.value, theme)

      // 设置图表配置
      updateChart()

      // 添加自动调整大小
      if (props.autoResize) {
        setupResizeObserver()
      }
    } catch (error) {
      console.error('初始化图表失败:', error)
    }
  }
}

// 更新图表
const updateChart = () => {
  if (chartInstance && props.option) {
    try {
      // 根据当前主题调整配置
      const isDark = currentTheme.value === 'dark'
      const themeOption = isDark
        ? {
            backgroundColor: 'transparent',
            textStyle: { color: '#B9B8CE' }
          }
        : {}

      const mergedOption = {
        ...themeOption,
        ...props.option
      }

      chartInstance.setOption(mergedOption, true)
    } catch (error) {
      console.error('更新图表失败:', error)
    }
  }
}

// 调整图表大小
const resizeChart = () => {
  if (chartInstance) {
    try {
      requestAnimationFrame(() => {
        chartInstance?.resize()
      })
    } catch (error) {
      console.error('调整图表大小失败:', error)
    }
  }
}

// 设置 resize 观察器
const setupResizeObserver = () => {
  if (chartRef.value && window.ResizeObserver) {
    resizeObserver.value = new ResizeObserver(() => {
      if (chartInstance) {
        // 使用 requestAnimationFrame 延迟 resize 调用，避免 ECharts 6 报错
        requestAnimationFrame(() => {
          chartInstance?.resize()
        })
      }
    })
    resizeObserver.value.observe(chartRef.value)
  }
}
// 销毁图表
const destroyChart = () => {
  if (chartInstance) {
    chartInstance.dispose()
    chartInstance = null
  }

  if (resizeObserver.value) {
    resizeObserver.value.disconnect()
    resizeObserver.value = null
  }
}

// 监听配置项变化
watch(
  () => props.option,
  () => {
    nextTick(() => {
      updateChart()
    })
  },
  { deep: true }
)

// 监听 props 主题变化
watch(
  () => props.theme,
  () => {
    nextTick(() => {
      initChart()
    })
  }
)

// 监听系统主题变化
watch(currentTheme, (newTheme, oldTheme) => {
  if (newTheme !== oldTheme) {
    nextTick(() => {
      initChart()
    })
  }
})

// 监听高度和宽度变化
watch([() => props.height, () => props.width], () => {
  nextTick(() => {
    resizeChart()
  })
})

// 生命周期钩子
onMounted(() => {
  initChart()
})

onUnmounted(() => {
  destroyChart()
})
</script>

<style lang="less" scoped>
.async-echarts-container {
  position: relative;

  .chart-container {
    position: relative;
    border-radius: 4px;
  }

  .loading-overlay,
  .error-overlay {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
-color: rgba(255, 255, 255, 0.8);
    border-radius: 4px;
    z-index: 10;
  }

  .loading-icon,
  .error-icon {
    font-size: 24px;
    margin-bottom: 8px;
  }

  .loading-icon {
    color: #409eff;
  }

  .error-icon {
    color: #f56c6c;
  }

  .loading-text,
  .error-text {
    font-size: 14px;
  }

  .loading-text {
    color: #409eff;
  }

  .error-text {
    color: #f56c6c;
  }
}
</style>
