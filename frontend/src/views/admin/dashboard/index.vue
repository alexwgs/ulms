<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card class="box-card">
          <template #header>
            <span style="font-size: 18px; font-weight: 800"
              >欢迎回来！{{ userStore.ploName }}</span
            >
          </template>
          <div>
            <el-row :gutter="20">
              <el-col :span="6" style="text-align: center">
                <el-statistic
                  :value="
                    chartData.loginTimes.length
                      ? chartData.loginTimes[chartData.loginTimes.length - 1]
                      : 0
                  "
                  title="A6广场访问人数"
                />
              </el-col>
              <el-col :span="6">
                <el-statistic
                  :value="ohtStore.ohtUserList.length"
                  title="在线人数"
                />
              </el-col>
              <el-col :span="6">
                <el-statistic
                  :value="
                    chartData.pv.length
                      ? chartData.pv[chartData.pv.length - 1]
                      : 0
                  "
                  title="PV"
                />
              </el-col>
              <el-col :span="6">
                <el-statistic :value="errorList.length" title="近三天异常数">
                  <template #suffix>
                    <span class="like">
                      <el-icon
                        v-if="errorList.length > 0"
                        :size="20"
                        color="#f56c6c"
                      >
                        <CircleCloseFilled />
                      </el-icon>
                    </span>
                  </template>
                </el-statistic>
              </el-col>
            </el-row>
          </div>
          <div style="width: 100%; padding-top: 40px; height: calc(100vh - 350px)">
            <AsyncECharts
              :option="chartOptions"
              :loading="loading"
              height="400"
            />
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="box-card">
          <template #header>
            <span>异常数据</span>
          </template>
          <el-table
            :data="errorList"
            height="calc(100vh - 255px)"
            style="width: 100%"
          >
            <el-table-column
              prop="operTime"
              label="操作时间"
              width="160"
            />
            <el-table-column
              prop="title"
              label="模块"
              width="100"
              show-overflow-tooltip
            />
            <el-table-column
              prop="content"
              label="操作类型"
              show-overflow-tooltip
            />
            <el-table-column
              prop="errorMsg"
              label="错误说明"
              show-overflow-tooltip
            />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { CircleCloseFilled } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import AsyncECharts from '@/components/AsyncECharts.vue'
import { httpInstance } from '@/utils/request'
import { useUserStore, useOhtStore } from '@/stores'

const userStore = useUserStore()
const ohtStore = useOhtStore()

const loading = ref(false)
const errorList = ref([])
const chartData = ref({
  loginTimes: [],
  quickTimes: [],
  pv: [],
  dataDate: []
})

const chartOptions = ref({})

const fetchData = async () => {
  loading.value = true
  try {
    const res = await httpInstance({
      url: 'sys/operLog/getVisitCount',
      method: 'get'
    })
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    errorList.value = res.errorList || []
    chartData.value = res.visitData || {
      loginTimes: [],
      quickTimes: [],
      pv: [],
      dataDate: []
    }
    chartOptions.value = {
      toolbox: {
        feature: {
          dataView: { show: true, readOnly: false },
          magicType: { show: true, type: ['line', 'bar'] },
          restore: { show: true },
          saveAsImage: { show: true }
        }
      },
      title: {
        text: '近30日站点访问情况'
      },
      tooltip: {
        trigger: 'axis'
      },
      legend: {
        data: ['A6广场访问人次', '快捷访问人次', 'pv']
      },
      xAxis: [
        {
          type: 'category',
          axisTick: {
            alignWithLabel: true
          },
          data: chartData.value.dataDate
        }
      ],
      yAxis: [
        {
          name: '人次',
          position: 'left',
          type: 'value'
        },
        {
          name: 'pv',
          position: 'right',
          type: 'value'
        }
      ],
      series: [
        {
          name: 'A6广场访问人次',
          type: 'bar',
          stack: 'one',
          data: chartData.value.loginTimes
        },
        {
          name: '快捷访问人次',
          type: 'bar',
          stack: 'one',
          data: chartData.value.quickTimes
        },
        {
          name: 'pv',
          type: 'line',
          yAxisIndex: 1,
          stack: 'two',
          data: chartData.value.pv,
          markLine: {
            data: [{ type: 'average', name: 'Avg' }]
          }
        }
      ]
    }
  } catch (error) {
    console.error('获取数据失败:', error)
    ElMessage.error('获取数据失败')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchData()
})
</script>

<style lang="less" scoped>
.dashboard {
  padding: 20px;
}

.like {
  cursor: pointer;
  font-size: 25px;
  display: inline-block;
}
</style>
