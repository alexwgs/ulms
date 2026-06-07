<template>
  <div class="rpa-chart-container">
    <!-- 筛选条件 -->
    <div class="filter-section">
      <t-card shadow class="filter-card">
        <t-row :gutter="20">
          <t-col :span="4">
            <t-select
              v-model="queryInfo.query"
              filterable
              placeholder="请输入工具名称(可搜索)"
              @search="remoteMethod"
              :loading="loading"
              size="small"
              @change="getList"
            >
              <t-option
                v-for="(item, index) in options"
                :key="index"
                :label="item"
                :value="item"
              />
            </t-select>
          </t-col>
          <t-col :span="4">
            <EmployeeSelect
              size="small"
              @update:modelValue="getList"
              v-model="queryInfo.runUser"
            ></EmployeeSelect>
          </t-col>
          <t-col :span="4">
            <t-date-range-picker v-model="dataRange" :placeholder="['开始日期', '结束日期']" @change="daterangeChange" size="small" class="w-full" />
          </t-col>
        </t-row>
      </t-card>
    </div>

    <!-- 主要内容区域 -->
    <div class="main-content">
      <!-- 左侧区域：7份 -->
      <t-col :span="9">
        <div class="left-section">
          <!-- 按日统计使用情况 -->
          <t-card shadow class="chart-card mb-20">
            <template #header>
              <div class="card-header">
                <span>按日统计使用情况</span>
                <t-tag size="small" theme="primary" effect="light"
                  >趋势分析</t-tag
                >
              </div>
            </template>
            <AsyncECharts
              :option="chartOptions1"
              :loading="loading"
              :error="error"
              :height="'400px'"
              class="chart"
            />
          </t-card>

          <!-- 按产品统计使用情况 -->
          <t-card shadow class="chart-card">
            <template #header>
              <div class="card-header">
                <span>按产品统计使用情况</span>
                <t-tag size="small" theme="success" effect="light"
                  >产品分析</t-tag
                >
              </div>
            </template>
            <AsyncECharts
              :option="chartOptions2"
              :loading="loading"
              :error="error"
              :height="'400px'"
              class="chart"
            />
          </t-card>
        </div>
      </t-col>

      <!-- 右侧区域：3份 -->
      <t-col :span="4">
        <div class="right-section">
          <!-- 总使用情况 -->
          <t-card shadow class="stats-card mb-20">
            <template #header>
              <div class="card-header">
                <span class="card-title">总使用情况</span>
                <t-tag size="small" theme="default" effect="light"
                  >统计概览</t-tag
                >
              </div>
            </template>
            <div class="stats-content">
              <div class="stat-item">
                <div class="stat-icon">
                  <t-icon class="icon"><TimeIcon /></t-icon>
                </div>
                <div class="stat-info">
                  <div class="stat-label">总使用次数</div>
                  <div class="stat-value">{{ totalTimes }}</div>
                </div>
              </div>
              <div class="stat-item">
                <div class="stat-icon">
                  <t-icon class="icon"><User /></t-icon>
                </div>
                <div class="stat-info">
                  <div class="stat-label">总使用人数</div>
                  <div class="stat-value">{{ totalUserSize }}</div>
                </div>
              </div>
            </div>
          </t-card>

          <!-- 按使用人排序TOP 20 -->
          <t-card shadow class="chart-card">
            <template #header>
              <div class="card-header">
                <span class="card-title">按使用人排序TOP 20</span>
                <t-tag size="small" theme="warning" effect="light"
                  >用户分析</t-tag
                >
              </div>
            </template>
            <AsyncECharts
              :option="chartOptions3"
              :loading="loading"
              :error="error"
              :height="'400px'"
              class="chart"
            />
          </t-card>
        </div>
      </t-col>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onBeforeUnmount } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { TimeIcon, UserIcon } from 'tdesign-icons-vue-next'
import DepartmentSelect from '@/components/DepartmentSelect.vue'
import EmployeeSelect from '@/components/EmployeeSelect.vue'
import AsyncECharts from '@/components/AsyncECharts.vue'
import { chartApi } from '@/api/rpa/Chart'
import { getRpaToolName } from '@/api/rpa/rpa'
// 响应式数据
const deptData = ref([])
const empData = ref('')
const totalTimes = ref(0)
const totalUserSize = ref(0)
const options = ref([])
const dataRange = ref([
  new Date(Date.now() - 30 * 24 * 60 * 60 * 1000).toISOString().split('T')[0],
  new Date().toISOString().split('T')[0]
])
const loading = ref(false)
const error = ref('')

// 查询参数
const queryInfo = ref({
  orderType: ' desc',
  order: 'runTime',
  runUser: '',
  queryType: '',
  query: '',
  begDate: new Date(Date.now() - 30 * 24 * 60 * 60 * 1000)
    .toISOString()
    .split('T')[0],
  endDate: new Date().toISOString().split('T')[0],
  pageSize: 20,
  pageNum: 1
})

// 图表数据
const chartDataUse = ref({})
const chartDataProduct = ref({})
const chartDataUser = ref({})

// 图表配置
const chartOptions1 = ref({})
const chartOptions2 = ref({})
const chartOptions3 = ref({})

// 计算属性：排序后的产品数据
const sortedChartData = computed(() => {
  if (!chartDataProduct.value.series1) {
    return { series1: [], series2: [], coloum: [] }
  }

  const indices = chartDataProduct.value.series1
    .map((value, index) => ({ value, index }))
    .sort((a, b) => b.value - a.value)
    .map((sortedItem) => sortedItem.index)

  return {
    series1: indices.map((i) => chartDataProduct.value.series1[i]),
    series2: indices.map((i) => chartDataProduct.value.series2[i]),
    coloum: indices.map((i) => chartDataProduct.value.coloum[i])
  }
})

// 方法：获取数据
const getList = async () => {
  loading.value = true
  error.value = ''

  try {
    const res = await chartApi.getUseTimeByDayChartData(queryInfo.value)

    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      error.value = res.msg
      return
    }

    const data = res.data
    chartDataUse.value = data.chartDataUse
    chartDataProduct.value = data.chartDataProduct
    chartDataUser.value = data.chartDataUser
    totalTimes.value = data.chartDataUse.series1.reduce((a, b) => a + b, 0)
    totalUserSize.value = data.totalUserSize.size

    // 更新图表配置
    updateChartOptions()
  } catch (err) {
    console.error('获取数据失败:', err)
    MessagePlugin.error('获取数据失败')
    error.value = '获取数据失败'
  } finally {
    loading.value = false
  }
}
const remoteMethod = (query) => {
  if (query) {
    loading.value = true
    setTimeout(async () => {
      loading.value = false
      const res = await getRpaToolName(query)
      if (res.code !== 200) {
        MessagePlugin.error(res.msg || '获取RPA工具名称失败')
        return
      }
      options.value = res.data
    }, 200)
  } else {
    options.value = []
  }
}
// 方法：更新图表配置
const updateChartOptions = () => {
  // 按日统计使用情况
  chartOptions1.value = {
    toolbox: {
      feature: {
        dataView: { show: true, readOnly: false },
        magicType: { show: true, type: ['line', 'bar'] },
        restore: { show: true },
        saveAsImage: { show: true }
      }
    },
    title: {
      text: '按日统计使用情况',
      subtext: 'by日总使用数'
    },
    tooltip: {
      trigger: 'axis'
    },
    legend: {
      data: ['使用次数', '使用人数']
    },
    xAxis: [
      {
        type: 'category',
        boundaryGap: false,
        data: chartDataUse.value.coloum || []
      }
    ],
    yAxis: [
      {
        name: '使用次数',
        position: 'left',
        type: 'value'
      },
      {
        name: '使用人数',
        position: 'right',
        type: 'value'
      }
    ],
    series: [
      {
        name: '使用次数',
        type: 'line',
        yAxisIndex: 0,
        data: chartDataUse.value.series1 || [],
        markPoint: {
          data: [
            { type: 'max', name: 'Max' },
            { type: 'min', name: 'Min' }
          ]
        },
        markLine: {
          data: [{ type: 'average', name: 'Avg' }]
        }
      },
      {
        name: '使用人数',
        type: 'line',
        yAxisIndex: 1,
        data: chartDataUse.value.series2 || [],
        markPoint: {
          data: [
            { type: 'max', name: 'Max' },
            { type: 'min', name: 'Min' }
          ]
        },
        markLine: {
          data: [{ type: 'average', name: 'Avg' }]
        }
      }
    ]
  }

  // 按产品统计使用情况
  chartOptions2.value = {
    toolbox: {
      feature: {
        dataView: { show: true, readOnly: false },
        magicType: { show: true, type: ['line', 'bar'] },
        restore: { show: true },
        saveAsImage: { show: true }
      }
    },
    title: {
      text: '按产品统计使用情况',
      subtext: 'by工具总使用数'
    },
    tooltip: {
      trigger: 'axis'
    },
    legend: {
      data: ['使用次数', '使用人数']
    },
    xAxis: [
      {
        type: 'category',
        axisTick: {
          alignWithLabel: true
        },
        data: sortedChartData.value.coloum
      }
    ],
    yAxis: [
      {
        name: '使用次数',
        position: 'left',
        type: 'value'
      },
      {
        name: '使用人数',
        position: 'right',
        type: 'value'
      }
    ],
    series: [
      {
        name: '使用次数',
        type: 'bar',
        yAxisIndex: 0,
        data: sortedChartData.value.series1,
        label: {
          show: true,
          position: 'inside'
        }
      },
      {
        name: '使用人数',
        type: 'line',
        yAxisIndex: 1,
        data: sortedChartData.value.series2
      }
    ]
  }

  // 按使用人排序TOP 20
  chartOptions3.value = {
    title: {
      text: '按使用人排序TOP 20'
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    legend: {},
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%'
    },
    xAxis: {
      type: 'value'
    },
    yAxis: {
      type: 'category',
      data: chartDataUser.value.coloum || []
    },
    series: [
      {
        type: 'bar',
        data: chartDataUser.value.series || [],
        label: {
          show: true,
          position: 'inside'
        }
      }
    ]
  }
}

// 方法：日期范围变化
const daterangeChange = () => {
  if (dataRange.value && dataRange.value.length === 2) {
    queryInfo.value.begDate = dataRange.value[0]
    queryInfo.value.endDate = dataRange.value[1]
    getList()
  }
}

// 生命周期：挂载
onMounted(() => {
  getList()
})

// 生命周期：卸载前
onBeforeUnmount(() => {
  // 清理资源
})

// 调试deptData变化
import { watch } from 'vue'
watch(
  deptData,
  (newVal) => {
  },
  { deep: true }
)
</script>

<style lang="less" scoped>
.rpa-chart-container {
  min-height: 100vh;

  .filter-section {
    margin-bottom: 24px;

    .filter-card {
      border-radius: 8px;
      transition: all 0.3s ease;

      &:hover {
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
      }
    }

    .w-full {
      width: 100%;
    }
  }

  .main-content {
    display: flex;
    gap: 20px;

    .left-section,
    .right-section {
      width: 100%;
    }

    .mb-20 {
      margin-bottom: 20px;
    }
  }

  .chart-card,
  .stats-card {
    border-radius: 8px;
    transition: all 0.3s ease;
    overflow: hidden;

    &:hover {
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    }
  }

  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .chart {
    width: 100%;
    height: 100%;
  }

  .stats-card {
    .stats-content {
      .stat-item {
        display: flex;
        align-items: center;
        padding: 10px 0;
        border-bottom: 1px solid #f0f0f0;

        &:last-child {
          border-bottom: none;
        }

        .stat-icon {
          width: 48px;
          height: 48px;
          border-radius: 50%;
          display: flex;
          align-items: center;
          justify-content: center;
          margin-right: 16px;

          .icon {
            font-size: 24px;
            color: #409eff;
          }
        }

        .stat-info {
          flex: 1;

          .stat-label {
            font-size: 14px;
            color: #606266;
            margin-bottom: 4px;
          }

          .stat-value {
            font-size: 24px;
            font-weight: bold;
            color: #303133;
          }
        }
      }
    }
  }
}

// 响应式布局调整
@media (max-width: 1200px) {
  .rpa-chart-container {
    .main-content {
      flex-direction: column;

      .left-section,
      .right-section {
        width: 100%;
      }
    }
  }
}
</style>
