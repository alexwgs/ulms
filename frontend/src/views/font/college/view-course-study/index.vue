<template>
  <div>
    <el-alert
      title='点击选择"科室"可下钻至小组数据。点击"BACK"可返回"科室"数据！'
      type="success"
      effect="dark"
      :closable="false"
    />
    <div
      id="studyChart1"
      :class="className"
      :style="{ height: height, 'min-width': width }"
    />
    <div
      id="studyChart2"
      :class="className"
      :style="{ height: '800px', 'min-width': width }"
    />
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import * as echarts from 'echarts'
import { httpInstance } from '@/utils/request'
import { ElMessage } from 'element-plus'

const props = defineProps({
  className: {
    type: String,
    default: 'chart'
  },
  width: {
    type: String,
    default: '97%'
  },
  height: {
    type: String,
    default: '600px'
  }
})

const myChart1 = ref(null)
const myChart2 = ref(null)
const data = ref(null)
const column = ref([])
const data1 = ref([])
const data2 = ref([])
const drilldownData = ref([])

const option1 = ref({
  xAxis: {
    axisLabel: {
      show: true,
      interval: 0,
      rotate: 40
    },
    data: []
  },
  tooltip: {
    show: true,
    formatter: '{b0}: {c0}%'
  },
  yAxis: {},
  dataGroupId: '',
  animationDurationUpdate: 500,
  series: {
    type: 'bar',
    id: 'sales',
    data: [],
    universalTransition: {
      enabled: true,
      divideShape: 'clone'
    }
  }
})

const option2 = ref({
  title: {
    text: '各组别学习完成率排序'
  },
  toolbox: {
    feature: {
      dataView: { show: true, readOnly: false },
      magicType: { show: true, type: ['line', 'bar'] },
      restore: { show: true },
      saveAsImage: { show: true }
    }
  },
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'shadow'
    },
    formatter: (params) => {
      return params[0].data.deptGroup + '<br>完成率：' + params[0].data.rate + '%<br>需学习人数：' + params[0].data.totalNum + '<br>完成人数：' + params[0].data.studyNum
    }
  },
  dataset: {
    source: []
  },
  legend: {},
  xAxis: {
    type: 'value'
  },
  yAxis: {
    type: 'category',
    axisLabel: { interval: 0, rotate: 30 }
  },
  series: [
    {
      name: '学习完成率',
      type: 'bar',
      encode: { x: 'rate', y: 'deptGroup' }
    }
  ]
})

const init = async (courseId) => {
  try {
    const res = await httpInstance.get(`college/report/view/${courseId}`)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    data.value = res.data

    column.value = [...new Set(data.value.map(e => e.deptNum))]
    for (const deptNum of column.value) {
      const rowDetails = []
      let deptTotal = 0
      let deptStudy = 0
      data.value.forEach(e => {
        if (e.deptNum === deptNum) {
          deptTotal += parseInt(e.totalNum)
          deptStudy += parseInt(e.studyNum)
          rowDetails.push([
            e.deptGroup,
            ((e.studyNum * 100) / e.totalNum).toFixed(2)
          ])
        }
      })
      data1.value.push({
        groupId: deptNum,
        value: ((deptStudy * 100) / deptTotal).toFixed(2)
      })
      data2.value.push({ dataGroupId: deptNum, data: rowDetails })
    }
    data.value.sort((a, b) => {
      return parseFloat(a.rate) - parseFloat(b.rate)
    })

    drawView()
  } catch (error) {
    console.error(error)
  }
}

const drawView = () => {
  myChart1.value = echarts.init(document.getElementById('studyChart1'))
  myChart2.value = echarts.init(document.getElementById('studyChart2'))

  option1.value.xAxis.data = column.value
  option1.value.series.data = data1.value
  drilldownData.value = data2.value

  myChart1.value.on('click', (event) => {
    if (event.data) {
      const subData = drilldownData.value.find(data => data.dataGroupId === event.data.groupId)
      if (!subData) {
        return
      }
      myChart1.value.setOption({
        xAxis: {
          data: subData.data.map(item => item[0])
        },
        series: {
          type: 'bar',
          id: 'sales',
          dataGroupId: subData.dataGroupId,
          data: subData.data.map(item => item[1]),
          universalTransition: {
            enabled: true,
            divideShape: 'clone'
          }
        },
        graphic: [
          {
            type: 'text',
            left: 50,
            top: 20,
            style: {
              text: 'Back',
              fontSize: 18
            },
            onclick: () => {
              myChart1.value.setOption(option1.value)
            }
          }
        ]
      })
    }
  })

  option1.value && myChart1.value.setOption(option1.value, 'dark')

  option2.value.dataset.source = data.value
  option2.value && myChart2.value.setOption(option2.value, 'dark')

  myChart1.value.resize()
  myChart2.value.resize()
}

const handleResize = () => {
  if (myChart1.value) myChart1.value.resize()
  if (myChart2.value) myChart2.value.resize()
}

onMounted(() => {
  window.addEventListener('resize', handleResize)
})

onBeforeUnmount(() => {
  window.removeEventListener('resize', handleResize)
  if (myChart1.value) {
    myChart1.value.dispose()
    myChart1.value = null
  }
  if (myChart2.value) {
    myChart2.value.dispose()
    myChart2.value = null
  }
})

defineExpose({
  init
})
</script>

<style lang="less" scoped></style>
