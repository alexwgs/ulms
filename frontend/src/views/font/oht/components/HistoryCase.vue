<template>
  <t-dialog header="历史案件查询" v-model:visible="historyCaseDialogVisible" width="70%">
    <div class="block">
      <span>查询时间：</span>
      <t-date-range-picker size="small" @change="historyCaseDateChangeEvent" format="YYYY-MM-DD" v-model="historyCaseDateDuration" :placeholder="['开始日期', '结束日期']">
      </t-date-range-picker>

      <CustomTable rowKey="id" :data="historyCaseTable" style="width: 100%" size="small" height="400px">
        <TableColumn type="expand">
          <template #default="props">
            <t-descriptions title="订单详情" size="small" :column="3" colon>
              <t-descriptions-item label="订单状态">
                <t-tag :theme="props.row.caseStatus === 3 ? 'success' : 'danger'" variant="light">
                  {{ dictStore.getDictName('oht_case_status', props.row.caseStatus) }}
                </t-tag>
              </t-descriptions-item>
              <t-descriptions-item label="客户信息">{{ props.row.custId ? props.row.custId : '--' }}</t-descriptions-item>
              <t-descriptions-item label="等待时长">{{ props.row.waitTime != null ? Math.floor(props.row.waitTime / 60) + '分' + (props.row.waitTime % 60) + '秒' : '--' }}</t-descriptions-item>
              <t-descriptions-item label="建案时间">{{ props.row.dataTime ? formatTime(props.row.dataTime) : '--' }}</t-descriptions-item>
              <t-descriptions-item label="接起时间">{{ props.row.pickTime ? unixTimeToDateTime(props.row.pickTime) : '--' }}</t-descriptions-item>
              <t-descriptions-item label="结案时间">{{ props.row.pcompTime ? formatTime(props.row.pcompTime) : '--' }}</t-descriptions-item>
              <t-descriptions-item label="发起人结案类型">
                {{ dictStore.getDictName('oht_case_bcompType', props.row.bcompType) }}
              </t-descriptions-item>
              <t-descriptions-item label="发起人备注" :span="2">
                {{ props.row.bcompMemo ? props.row.bcompMemo : '--' }}
              </t-descriptions-item>
              <t-descriptions-item label="受理人结案类型">
                {{ dictStore.getDictName('oht_case_pcompType', props.row.pcompType) }}
              </t-descriptions-item>
              <t-descriptions-item label="受理人备注" :span="2">
                {{ props.row.pcompMemo ? props.row.pcompMemo : '--' }}
              </t-descriptions-item>
            </t-descriptions>
          </template>
        </TableColumn>
        <TableColumn label="建案时间" colKey="dataTime" width="160">
          <template #default="scope">
            {{ formatTime(scope.row.dataTime) }}
          </template>
        </TableColumn>
        <TableColumn label="案件ID" colKey="caseId" width="110" ellipsis></TableColumn>
        <TableColumn label="案件状态" colKey="caseStatus" width="100">
          <template #default="scope">
            {{ dictStore.getDictName('oht_case_status', scope.row.caseStatus) }}
          </template>
        </TableColumn>
        <TableColumn label="案件类型" colKey="caseType" width="120">
          <template #default="scope">
            {{ dictStore.getDictName('oht_case_type', scope.row.caseType) }}
          </template>
        </TableColumn>
        <TableColumn colKey="buildUser.ploName" sortable="custom" label="建案工号" width="110"></TableColumn>
        <TableColumn colKey="pickUser.ploName" sortable="custom" label="受理工号" width="110"></TableColumn>
      </CustomTable>
      <t-pagination @page-size-change="handleSizeChange" @current-change="handleCurrentChange" :current="currentPage"
        :page-size-options="pageSizes" :page-size="historyCaseQueryInfo.pageSize"
 :total="total">
      </t-pagination>
    </div>
    <template #footer="scope">
      <t-button size="small" @click="historyCaseDialogVisible = false">关 闭</t-button>
    </template>
  </t-dialog>
</template>
<script setup>
import { ref, reactive } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { httpInstance } from '@/utils/request'
import { useDictStore } from '@/stores'
import { usePagination } from '@/hooks/usePagination'
// 导入 store
const dictStore = useDictStore()

// 响应式数据
const historyCaseDialogVisible = ref(false)
const historyCaseDateDuration = ref([])
const historyCaseTable = ref([])
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
    if (res.code !== 200) return MessagePlugin.error('拉取历史案件信息失败！')
    historyCaseTable.value = res.data.list
    total.value = res.data.total
    currentPage.value = historyCaseQueryInfo.pageNum
  } catch (error) {
    MessagePlugin.error('拉取历史案件信息失败！')
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


// 处理页码变更


// 时间戳转换为日期时间（兼容秒级/毫秒级时间戳，统一 yyyy-MM-dd HH:mm:ss 格式）
import { formatTime } from '@/utils/formatTime'
const unixTimeToDateTime = (UnixTime) => {
  return formatTime(UnixTime, 'YYYY-MM-DD HH:mm:ss') || '--'
}

defineExpose({
  viewHistoryCase
});
const { currentPage, pageSizes, handleCurrentChange, handleSizeChange } = usePagination({ query: historyCaseQueryInfo, fetch: getHistoryCase, pageSizes: [20, 100, 500] })
</script>
