<template>
  <div class="dashboard">
    <!-- Row 1: 统计卡片 -->
    <StatsCardRow
      :today-stats="todayStats"
      :online-count="onlineCount"
    />

    <!-- Row 2: 系统 UV/PV 图表 + 异常列表 -->
    <t-row :gutter="16" class="dashboard-row">
      <t-col :span="8">
        <SystemUvPvChart />
      </t-col>
      <t-col :span="4">
        <ErrorTable :error-list="errorList" />
      </t-col>
    </t-row>

    <!-- Row 3: 子系统访问趋势 + 请求操作统计 -->
    <t-row :gutter="16" class="dashboard-row">
      <t-col :span="6">
        <SubsysVisitChart />
      </t-col>
      <t-col :span="6">
        <RequestOpsChart />
      </t-col>
    </t-row>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { useOhtStore } from '@/stores'
import { dashboardApi } from '@/api/admin/dashboard'
import StatsCardRow from './components/StatsCardRow.vue'
import SystemUvPvChart from './components/SystemUvPvChart.vue'
import SubsysVisitChart from './components/SubsysVisitChart.vue'
import RequestOpsChart from './components/RequestOpsChart.vue'
import ErrorTable from './components/ErrorTable.vue'

const ohtStore = useOhtStore()

const todayStats = ref({ uv: 0, pv: 0, exceptionCount: 0 })
const errorList = ref([])

const onlineCount = computed(() => ohtStore.ohtUserList?.length ?? 0)

const fetchTodayStats = async () => {
  try {
    const res = await dashboardApi.getTodayStats()
    if (res.code === 200) {
      todayStats.value = res.todayStats || { uv: 0, pv: 0, exceptionCount: 0 }
      errorList.value = res.errorList || []
    } else {
      MessagePlugin.error(res.msg || '获取今日数据失败')
    }
  } catch (e) {
    console.error('获取今日统计数据失败:', e)
    MessagePlugin.error('获取今日数据失败')
  }
}

onMounted(() => {
  fetchTodayStats()
})
</script>

<style lang="less" scoped>
.dashboard {
  padding: 20px;

  &-row {
    margin-bottom: var(--td-comp-margin-xxl);

    &:last-child {
      margin-bottom: 0;
    }
  }
}
</style>
