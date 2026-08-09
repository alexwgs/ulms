<template>
  <t-card class="management-card">
    <t-form :data="queryInfo" label-width="80px" colon class="filter-form">
      <t-row :gutter="[24, 24]">
        <t-col :span="5">
          <t-form-item label="日期范围" name="dateDuration">
            <t-date-range-picker v-model="dateDuration" @update:model-value="dateChangeEvent" size="small" :placeholder="['开始日期', '结束日期']"></t-date-range-picker>
          </t-form-item>
        </t-col>
        <t-col :span="3" class="operation-container">
          <t-button theme="primary" size="small" @click="caseApi.downloadCaseDirReport(queryInfo)">
            下载
          </t-button>
        </t-col>
      </t-row>
    </t-form>
    <CustomTable rowKey="id" :data="caseTableQuery" size="small" sortable stripe height="calc(100vh - 270px)" :loading="loading">
      <TableColumn colKey="deptName" sortable label="科室"></TableColumn>
      <TableColumn colKey="groupName" sortable label="组别"></TableColumn>
      <TableColumn colKey="userId" sortable label="工号"></TableColumn>
      <TableColumn colKey="userName" sortable label="姓名"></TableColumn>
      <TableColumn colKey="avaliableTime" sortable label="可接单时长(h)"></TableColumn>
      <TableColumn colKey="onCallTime" sortable label="通话中时长(h)"></TableColumn>
      <TableColumn colKey="leaveTime" sortable label="离开时长(h)"></TableColumn>
      <TableColumn colKey="type1Level1" sortable label="业务放行(1级)"></TableColumn>
      <TableColumn colKey="type1Level2" sortable label="业务放行(2级)"></TableColumn>
      <TableColumn colKey="type1Level3" sortable label="业务放行(3级)"></TableColumn>
      <TableColumn colKey="type1ProcessTime" sortable label="业务放行时长(s)"></TableColumn>
      <TableColumn colKey="type2Level1" sortable label="投诉案件(1级)"></TableColumn>
      <TableColumn colKey="type2Level2" sortable label="投诉案件(2级)"></TableColumn>
      <TableColumn colKey="type2Level3" sortable label="投诉案件(3级)"></TableColumn>
      <TableColumn colKey="type2ProcessTime" sortable label="投诉处理时长(s)"></TableColumn>
    </CustomTable>
  </t-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { caseApi } from '@/api/oht/case'

// 响应式数据
const loading = ref(false)
const caseTableQuery = ref([])
const dateDuration = ref([])

// 查询参数
const queryInfo = reactive({
  dates: '',
  querytype: '',
  query: '',
  pageSize: 20,
  pageNum: 1
})

// 初始化
onMounted(() => {
  getCaseList()
})

// 获取案件列表
const getCaseList = async () => {
  try {
    loading.value = true
    const res = await caseApi.getCaseDirList(queryInfo)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    caseTableQuery.value = res.data || []
  } catch (error) {
    console.error('获取业务主任接单报表失败:', error)
    MessagePlugin.error('获取业务主任接单报表失败')
  } finally {
    loading.value = false
  }
}

// 日期范围变化事件
const dateChangeEvent = () => {
  if (dateDuration.value && dateDuration.value.length === 2) {
    queryInfo.dates = dateDuration.value.join(',')
  } else {
    queryInfo.dates = ''
  }
  getCaseList()
}
</script>

<style lang="less" scoped>
.management-card {
  height: calc(100vh - 190px);
  overflow: auto;
}

.text-right {
  display: flex;
  justify-content: flex-end;
}
</style>
