<template>
  <el-dialog title="历史案件查询" v-model="historyCaseDialogVisible" width="70%">
    <div class="block">
      <span>查询时间：</span>
      <el-date-picker size="small" @change="historyCaseDateChangeEvent" value-format="YYYY-MM-DD"
        v-model="historyCaseDateDuration" type="daterange" range-separator="至" start-placeholder="开始日期"
        end-placeholder="结束日期">
      </el-date-picker>

      <el-table :data="historyCaseTable" style="width: 100%" size="small" height="400px">
        <el-table-column type="expand">
          <template #default="props">
            <el-descriptions title="订单详情" size="small" :column="4">
              <el-descriptions-item label="订单状态"><el-tag :type="props.row.caseStatus === 3 ? 'success' : 'danger'">
                  {{ dictStore.getDictName('oht_case_status', props.row.caseStatus) }}</el-tag>
              </el-descriptions-item>
              <el-descriptions-item label="客户信息">{{ props.row.custId ? props.row.custId : '--' }}</el-descriptions-item>
              <el-descriptions-item label="等待时长"><span>{{ props.row.waitTime != null ? Math.floor(props.row.waitTime /
                60) + '分' + (props.row.waitTime % 60) + '秒' : '--' }}</span></el-descriptions-item>

              <el-descriptions-item label="结案时间" :span="2">{{ props.row.pcompTime ? props.row.pcompTime : '--'
              }}</el-descriptions-item>
              <el-descriptions-item label="建案时间">{{ props.row.dataTime ? props.row.dataTime : '--'
              }}</el-descriptions-item>
              <el-descriptions-item label="结案类型">
                {{ dictStore.getDictName('oht_case_bcompType', props.row.bcompType) }}
              </el-descriptions-item>
              <el-descriptions-item label="发起人备注" :span="2">
                {{ props.row.bcompMemo ? props.row.bcompMemo : '--' }}
              </el-descriptions-item>
              <el-descriptions-item label="接起时间">{{ props.row.pickTime ? unixTimeToDateTime(props.row.pickTime) : '--'
              }}</el-descriptions-item>
              <el-descriptions-item label="结案类型">
                {{ dictStore.getDictName('oht_case_pcompType', props.row.pcompType) }}
              </el-descriptions-item>
              <el-descriptions-item label="受理人备注" :span="2">
                {{ props.row.pcompMemo ? props.row.pcompMemo : '--' }}
              </el-descriptions-item>
            </el-descriptions>
          </template>
        </el-table-column>
        <el-table-column label="建案时间" prop="caseId">
          <template #default="scope">{{
            dictStore.getDictName('oht_case_status', scope.row.caseStatus)
          }}</template>
        </el-table-column>
        <el-table-column label="案件ID" prop="caseId"></el-table-column>
        <el-table-column label="案件状态" prop="caseStatus">
          <template #default="scope">
            {{ dictStore.getDictName('oht_case_status', scope.row.caseStatus) }}
          </template>
        </el-table-column>
        <el-table-column label="案件类型" prop="caseType">
          <template #default="scope">
            {{ dictStore.getDictName('oht_case_type', scope.row.caseType) }}
          </template>
        </el-table-column>
        <el-table-column prop="buildUser.ploName" sortable="custom" label="建案工号"></el-table-column>
        <el-table-column prop="pickUser.ploName" sortable="custom" label="受理工号"></el-table-column>
      </el-table>
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
        :page-sizes="pageSizes" :page-size="historyCaseQueryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>
    <template #footer="scope">
      <el-button size="small" @click="historyCaseDialogVisible = false">关 闭</el-button>
    </template>
  </el-dialog>
</template>
<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { httpInstance } from '@/utils/request'
import { useDictStore } from '@/stores'
// 导入 store
const dictStore = useDictStore()

// 响应式数据
const historyCaseDialogVisible = ref(false)
const historyCaseDateDuration = ref([])
const historyCaseTable = ref([])
const currentPage = ref(1)
const pageSizes = ref([20, 100, 500])
const total = ref(0)

// 查询参数
const historyCaseQueryInfo = reactive({
  dates: '',
  queryType: 'user',
  query: '',
  pageSize: 20,
  pageNum: 1
})

// 历史案件相关方法
const viewHistoryCase = () => {
  historyCaseDialogVisible.value = true
  getHistoryCase()
}

// 获取历史案件
const getHistoryCase = async () => {
  try {
    const res = await httpInstance.get('oht/case/list', {
      params: historyCaseQueryInfo
    })
    if (res.code !== 200) return ElMessage.error('拉取历史案件信息失败！')
    historyCaseTable.value = res.data.list
    total.value = res.data.total
    currentPage.value = historyCaseQueryInfo.pageNum
  } catch (error) {
    ElMessage.error('拉取历史案件信息失败！')
  }
}

// 日期变更事件
const historyCaseDateChangeEvent = () => {
  if (
    historyCaseDateDuration.value &&
    historyCaseDateDuration.value.length > 0
  ) {
    historyCaseQueryInfo.dates = historyCaseDateDuration.value.join(',')
  }
  getHistoryCase()
}

// 处理页码大小变更
const handleSizeChange = (pageSize) => {
  historyCaseQueryInfo.pageSize = pageSize
  getHistoryCase()
}

// 处理页码变更
const handleCurrentChange = (page) => {
  historyCaseQueryInfo.pageNum = page
  getHistoryCase()
}

// 时间戳转换为日期时间
const unixTimeToDateTime = (UnixTime) => {
  if (UnixTime === null || UnixTime === undefined) return '--'
  return new Date(UnixTime * 1000).toLocaleString()
}

defineExpose({
  viewHistoryCase
});
</script>
