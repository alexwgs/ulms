<template>
  <el-card class="box-card">
    <el-row :gutter="15">
      <el-col :span="10">
        <el-date-picker @change="getCaseList" size="small" value-format="YYYY-MM-DD" v-model="dateDuration"
          type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-col>
      <el-col :span="8">
        <el-input placeholder="请输入内容" size="small" v-model="queryInfo.query" @change="getCaseList">
          <template #prepend>
            <el-select v-model="queryInfo.queryType" size="small" style="width: 130px" placeholder="请选择">
              <el-option label="案件ID" value="caseId"></el-option>
              <el-option label="发起人" value="buildId"></el-option>
              <el-option label="接起人" value="pickId"></el-option>
            </el-select>
          </template>
        </el-input>
      </el-col>
      <el-col :span="6" class="text-right">
        <el-button type="primary" size="small" @click="caseApi.downloadCaseReport(queryInfo)">
          下载<i class="el-icon-download el-icon--right"></i>
        </el-button>
      </el-col>
    </el-row>
    <el-table :data="caseTableQuery" size="small" sortable="custom" @sort-change="tableSort" stripe
      height="calc(100vh - 325px)" v-loading="loading">
      <el-table-column label="推送明细" width="120">
        <template #default="{ row }">
          <el-button-group>
            <el-button type="primary" icon="Bell" size="small" @click="pushDetail(row.caseId)"></el-button>
            <el-button type="primary" icon="ChatLineRound" size="small" @click="viewChatRecord(row)"
              :disabled="row.caseStatus <= 1"></el-button>
          </el-button-group>
        </template>
      </el-table-column>
      <el-table-column prop="dataTime" sortable="custom" label="建案日期" width="150"></el-table-column>
      <el-table-column prop="caseStatus" sortable="custom" label="状态">
        <template #default="{ row }">
          {{ getDictLabel('oht_case_status', row.caseStatus) }}
        </template>
      </el-table-column>
      <el-table-column prop="caseType" sortable="custom" label="案件状态">
        <template #default="{ row }">
          {{ getDictLabel('oht_case_type', row.caseType) }}
        </template>
      </el-table-column>
      <el-table-column prop="buildUser.ploName" label="建案工号"></el-table-column>
      <el-table-column prop="buildExtn" sortable="custom" label="建案分机"></el-table-column>
      <el-table-column prop="bcompType" sortable="custom" label="建案人类型">
        <template #default="{ row }">
          {{ getDictLabel('oht_case_bcompType', row.bcompType) }}
        </template>
      </el-table-column>
      <el-table-column prop="bcompMemo" sortable="custom" label="建案备注"></el-table-column>
      <el-table-column prop="bcompTime" sortable="custom" label="建案人处理时间"></el-table-column>
      <el-table-column prop="pickUser.ploName" label="受理工号"></el-table-column>
      <el-table-column prop="pickTime" sortable="custom" label="受理时间">
        <template #default="{ row }">
          {{ formatDateTime(row.pickTime) }}
        </template>
      </el-table-column>
      <el-table-column prop="pickExtn" sortable="custom" label="受理分机"></el-table-column>
      <el-table-column prop="custId" sortable="custom" label="客户信息"></el-table-column>
      <el-table-column prop="pcompType" sortable="custom" label="结案类型">
        <template #default="{ row }">
          {{ getDictLabel('oht_case_pcompType', row.pcompType) }}
        </template>
      </el-table-column>
      <el-table-column prop="pcompMemo" sortable="custom" label="结案备注" show-overflow-tooltip></el-table-column>
      <el-table-column prop="pcompTime" sortable="custom" label="结案时间"></el-table-column>
      <el-table-column prop="waitTime" label="等待时长" sortable="custom"></el-table-column>
    </el-table>
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
      :current-page="queryInfo.pageNum" :page-sizes="pageSizes" :page-size="queryInfo.pageSize"
      layout="total, sizes, prev, pager, next, jumper" :total="total"></el-pagination>
  </el-card>

  <!-- 案件推送明细模态框 -->
  <CasePushDetailDialog v-model:visible="pushDetailModalFlag" :push-list="pushList" />

  <!-- 案件往来记录模态框 -->
  <CaseChatRecordDialog v-model:visible="chatRecordsModalFlag" :chat-records="chatRecords" />
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
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
      ElMessage.error(res.msg)
      return
    }
    caseTableQuery.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    console.error('获取案件列表失败:', error)
    ElMessage.error('获取案件列表失败')
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
const tableSort = ({ prop, order }) => {
  queryInfo.order = prop
  queryInfo.orderType = order === 'ascending' ? 'asc' : 'desc'
  getCaseList()
}

// 推送明细
const pushDetail = async (caseId) => {
  try {
    loading.value = true
    const res = await taskApi.getTaskByCaseId(caseId)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    pushList.value = res.data
    pushDetailModalFlag.value = true
  } catch (error) {
    console.error('获取推送明细失败:', error)
    ElMessage.error('获取推送明细失败')
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
      ElMessage.error(res.msg)
      return
    }
    chatRecords.value = res.data
    chatRecordsModalFlag.value = true
  } catch (error) {
    console.error('获取聊天记录失败:', error)
    ElMessage.error('获取聊天记录失败')
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
.box-card {
  height: calc(100vh - 190px);
  overflow: auto;
}

.text-right {
  display: flex;
  justify-content: flex-end;
}
</style>
