<template>
  <t-card class="management-card">
    <t-form :data="queryInfo" label-width="80px" colon class="filter-form">
      <t-row :gutter="[24, 24]">
        <t-col :span="5">
          <t-form-item label="日期范围" name="dateDuration">
            <t-date-range-picker @change="getCaseList" size="small" format="YYYY-MM-DD" v-model="dateDuration" :placeholder="['开始日期', '结束日期']"></t-date-range-picker>
          </t-form-item>
        </t-col>
        <t-col :span="6">
          <t-form-item label="关键字" name="query">
            <t-input-adornment>
              <template #prepend>
                <t-select v-model="queryInfo.queryType" size="small" placeholder="请选择">
                  <t-option label="案件ID" value="caseId"></t-option>
                  <t-option label="发起人" value="buildId"></t-option>
                  <t-option label="接起人" value="pickId"></t-option>
                </t-select>
              </template>
              <t-input placeholder="请输入内容" size="small" v-model="queryInfo.query" @change="getCaseList"></t-input>
            </t-input-adornment>
          </t-form-item>
        </t-col>
        <t-col :span="3" class="operation-container">
          <t-button variant="outline" theme="primary" size="small" @click="caseApi.downloadCaseReport(queryInfo)">
            下载
          </t-button>
        </t-col>
      </t-row>
    </t-form>
    <CustomTable rowKey="id" :data="caseTableQuery" size="small" sortable="custom" @sort-change="tableSort" stripe
      height="calc(100vh - 325px)" :loading="loading">
      <TableColumn label="推送明细" width="120">
        <template #default="{ row }">
          <t-space>
            <t-button variant="outline" theme="default" size="small" @click="pushDetail(row.caseId)">通知</t-button>
            <t-button variant="outline" theme="primary" size="small" @click="viewChatRecord(row)"
              :disabled="row.caseStatus <= 1"><template #icon><DynamicIcon name="chat-bubble" /></template></t-button>
          </t-space>
        </template>
      </TableColumn>
      <TableColumn colKey="dataTime" sortable="custom" label="建案日期" width="150"></TableColumn>
      <TableColumn colKey="caseStatus" sortable="custom" label="状态">
        <template #default="{ row }">
          {{ getDictLabel('oht_case_status', row.caseStatus) }}
        </template>
      </TableColumn>
      <TableColumn colKey="caseType" sortable="custom" label="案件状态">
        <template #default="{ row }">
          {{ getDictLabel('oht_case_type', row.caseType) }}
        </template>
      </TableColumn>
      <TableColumn colKey="buildUser.ploName" label="建案工号"></TableColumn>
      <TableColumn colKey="buildExtn" sortable="custom" label="建案分机"></TableColumn>
      <TableColumn colKey="bcompType" sortable="custom" label="建案人类型">
        <template #default="{ row }">
          {{ getDictLabel('oht_case_bcompType', row.bcompType) }}
        </template>
      </TableColumn>
      <TableColumn colKey="bcompMemo" sortable="custom" label="建案备注"></TableColumn>
      <TableColumn colKey="bcompTime" sortable="custom" label="建案人处理时间"></TableColumn>
      <TableColumn colKey="pickUser.ploName" label="受理工号"></TableColumn>
      <TableColumn colKey="pickTime" sortable="custom" label="受理时间">
        <template #default="{ row }">
          {{ formatDateTime(row.pickTime) }}
        </template>
      </TableColumn>
      <TableColumn colKey="pickExtn" sortable="custom" label="受理分机"></TableColumn>
      <TableColumn colKey="custId" sortable="custom" label="客户信息"></TableColumn>
      <TableColumn colKey="pcompType" sortable="custom" label="结案类型">
        <template #default="{ row }">
          {{ getDictLabel('oht_case_pcompType', row.pcompType) }}
        </template>
      </TableColumn>
      <TableColumn colKey="pcompMemo" sortable="custom" label="结案备注" ellipsis></TableColumn>
      <TableColumn colKey="pcompTime" sortable="custom" label="结案时间"></TableColumn>
      <TableColumn colKey="waitTime" label="等待时长" sortable="custom"></TableColumn>
    </CustomTable>
    <t-pagination @page-size-change="handleSizeChange" @current-change="handleCurrentChange"
      :current="queryInfo.pageNum" :page-size-options="pageSizes" :page-size="queryInfo.pageSize"
 :total="total"></t-pagination>
  </t-card>

  <!-- 案件推送明细模态框 -->
  <CasePushDetailDialog v-model:visible="pushDetailModalFlag" :push-list="pushList" />

  <!-- 案件往来记录模态框 -->
  <CaseChatRecordDialog v-model:visible="chatRecordsModalFlag" :chat-records="chatRecords" />
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { useDictStore } from '@/stores'
import CasePushDetailDialog from './components/CasePushDetailDialog.vue'
import CaseChatRecordDialog from './components/CaseChatRecordDialog.vue'
import { caseApi } from '@/api/oht/case'
import { taskApi } from '@/api/oht/task'

const dictStore = useDictStore()

// 格式化日期为YYYY-MM-DD
const formatDate = (date) => {
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  return `${year}-${month}-${day}`
}

// 响应式数据
const loading = ref(false)
const caseTableQuery = ref([])
const total = ref(0)
const dateDuration = ref([formatDate(new Date()), formatDate(new Date())])
const pushDetailModalFlag = ref(false)
const pushList = ref([])
const chatRecordsModalFlag = ref(false)
const chatRecords = ref([])

// 查询参数
const queryInfo = reactive({
  dates: '',
  orderType: 'desc',
  order: 'buildTime',
  queryType: 'caseId',
  query: '',
  pageSize: 20,
  pageNum: 1
})

const pageSizes = [20, 100, 500]

// 初始化
onMounted(() => {
  getCaseList()
})

// 获取案件列表
const getCaseList = async () => {
  try {
    loading.value = true
    if (dateDuration.value && dateDuration.value.length === 2) {
      queryInfo.dates = dateDuration.value.join(',')
    } else {
      queryInfo.dates = ''
    }
    const res = await caseApi.getCaseList(queryInfo)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    caseTableQuery.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    console.error('获取案件列表失败:', error)
    MessagePlugin.error('获取案件列表失败')
  } finally {
    loading.value = false
  }
}

// 分页大小变化
const handleSizeChange = (pageSize) => {
  queryInfo.pageSize = pageSize
  getCaseList()
}

// 当前页码变化
const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  getCaseList()
}

// 表格排序
const tableSort = ({ sortBy, descending }) => {
  queryInfo.order = sortBy
  queryInfo.orderType = !descending ? 'asc' : 'desc'
  getCaseList()
}

// 推送明细
const pushDetail = async (caseId) => {
  try {
    loading.value = true
    const res = await taskApi.getTaskByCaseId(caseId)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    pushList.value = res.data
    pushDetailModalFlag.value = true
  } catch (error) {
    console.error('获取推送明细失败:', error)
    MessagePlugin.error('获取推送明细失败')
  } finally {
    loading.value = false
  }
}

// 查看聊天记录
const viewChatRecord = async (row) => {
  try {
    loading.value = true
    const res = await caseApi.getCaseChatRecord({
      month: row.dataTime.substring(0, 7),
      id: row.caseId
    })
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    chatRecords.value = res.data
    chatRecordsModalFlag.value = true
  } catch (error) {
    console.error('获取聊天记录失败:', error)
    MessagePlugin.error('获取聊天记录失败')
  } finally {
    loading.value = false
  }
}

// 格式化日期时间
const formatDateTime = (timestamp) => {
  if (!timestamp) return ''
  const date = new Date(timestamp)
  return date.toLocaleString()
}

// 获取字典标签
const getDictLabel = (dictType, dictValue) => {
  const dictList = dictStore.dictList[dictType] || []
  const dictItem = dictList.find((item) => item.code == dictValue)
  return dictItem ? dictItem.codeval : dictValue
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
