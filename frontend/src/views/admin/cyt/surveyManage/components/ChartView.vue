<template>
  <div class="chart-view-wrapper">
    <div class="chart-container">
      <t-row v-for="(question, index) in questions" :key="index">
        <t-divider></t-divider>
        <t-col style="margin-bottom: 10px" :span="12"
          >{{ getQuestionType(index + 1, question.questionType)
          }}{{ questions[index].content }}</t-col
        >
        <template v-if="question.questionType != 'textarea'">
          <t-col :span="6">
            <AsyncECharts
              :option="getPieOption(chartDatas[index])"
              height="200px"
            />
          </t-col>
          <t-col :span="6">
            <AsyncECharts
              :option="getBarOption(chartDatas[index])"
              height="300px"
            />
          </t-col>
        </template>
        <t-col :span="12" style="height: 50px" v-else
          >需导出报表查看...</t-col
        >
      </t-row>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import AsyncECharts from '@/components/AsyncECharts.vue'
import { getSurveyChartData } from '@/api/cyt/index.js'

const props = defineProps({
  articleId: {
    type: Number,
    default: 0
  }
})

const questions = ref([])
const chartDatas = ref([])
const loading = ref(false)

const getQuestionList = async (id) => {
  if (!id) return
  try {
    loading.value = true
    const res = await getSurveyChartData(id)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    questions.value = res.data
    chartDatas.value = res.chartData
  } catch (error) {
    console.error('获取调研图表数据失败:', error)
    MessagePlugin.error('获取调研图表数据失败')
  } finally {
    loading.value = false
  }
}

const getQuestionType = (index, type) => {
  if (type === 'radio') return index + '.[单选题]'
  else if (type === 'checkbox') return index + '.[多选题]'
  else if (type === 'textarea') return index + '.[开放题]'
  return index + '.[' + type + ']'
}

// 生成饼图配置
const getPieOption = (chartData) => {
  if (!chartData || !chartData.rows || chartData.rows.length === 0) {
    return {
      title: { text: '暂无数据', left: 'center', top: 'center' }
    }
  }

  // 使用 columns 或 dimensions 作为字段名
  const columns = chartData.columns || chartData.dimensions || []
  if (columns.length < 2) {
    return {
      title: { text: '数据格式错误', left: 'center', top: 'center' }
    }
  }

  // 合并重复数据
  const dataMap = new Map()
  chartData.rows.forEach((row) => {
    const name = row[columns[0]]
    const value = parseInt(row[columns[1]]) || 0
    if (dataMap.has(name)) {
      dataMap.set(name, dataMap.get(name) + value)
    } else {
      dataMap.set(name, value)
    }
  })

  const data = Array.from(dataMap.entries()).map(([name, value]) => ({
    name,
    value
  }))

  return {
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      left: 'left',
      textStyle: { fontSize: 10 }
    },
    series: [
      {
        name: '选项分布',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: 16,
            fontWeight: 'bold'
          }
        },
        labelLine: { show: false },
        data: data
      }
    ]
  }
}

// 生成柱状图配置
const getBarOption = (chartData) => {
  if (!chartData || !chartData.rows || chartData.rows.length === 0) {
    return {
      title: { text: '暂无数据', left: 'center', top: 'center' }
    }
  }

  // 使用 columns 或 dimensions 作为字段名
  const columns = chartData.columns || chartData.dimensions || []
  if (columns.length < 2) {
    return {
      title: { text: '数据格式错误', left: 'center', top: 'center' }
    }
  }

  // 合并重复数据
  const dataMap = new Map()
  chartData.rows.forEach((row) => {
    const name = row[columns[0]]
    const value = parseInt(row[columns[1]]) || 0
    if (dataMap.has(name)) {
      dataMap.set(name, dataMap.get(name) + value)
    } else {
      dataMap.set(name, value)
    }
  })

  const xAxisData = Array.from(dataMap.keys())
  const seriesData = Array.from(dataMap.values())

  return {
    tooltip: {
      trigger: 'axis',
      axisPointer: { type: 'shadow' }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%'
    },
    xAxis: {
      type: 'category',
      data: xAxisData,
      axisTick: { alignWithLabel: true },
      axisLabel: { rotate: 30, fontSize: 10 }
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: '数量',
        type: 'bar',
        barWidth: '60%',
        data: seriesData,
        label: {
          show: true,
          position: 'top'
        }
      }
    ]
  }
}

// 监听 articleId 变化
watch(
  () => props.articleId,
  (newId) => {
    if (newId) {
      getQuestionList(newId)
    }
  },
  { immediate: true }
)
</script>

<style lang="less" scoped>
.chart-view-wrapper {
  max-height: 70vh;
  overflow-y: auto;
}

.chart-container {
  position: relative;
  width: 95%;
  padding: 10px;
}
</style>
