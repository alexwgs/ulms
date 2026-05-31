<template>
  <div>
    <t-dialog header="复议列表" v-model:visible="dialogTableVisible" mode="full-screen">
      <t-row :gutter="15">
        <t-col :span="3">
          <t-select v-model="queryInfo.disputeResult" size="small" placeholder="复议状态" @change="listDisputeList">
            <t-option v-for="item in dictStore.dictList.trm_exam_dispute_result" :key="item.code" :label="item.codeval" :value="parseInt(item.code)" />
          </t-select>
        </t-col>
      </t-row>
      <CustomTable rowKey="id" :data="disputeList" size="small">
        <TableColumn property="quesCode" label="题目编号" width="120" />
        <TableColumn property="quesStem" label="题干" ellipsis />
        <TableColumn property="options" label="选项" width="200">
          <template #default="scope">
            <div v-if="scope.row.quesType === 2">
              <t-checkbox v-for="option in scope.row.options.split(',')" :key="option" :checked="scope.row.answer.split(',').includes(option)" disabled>{{option}}</t-checkbox>
            </div>
            <div v-else>
              <t-radio v-for="option in scope.row.options.split(',')" :key="option" :checked="option === scope.row.answer" class="ques-option" disabled>{{option}}</t-radio>
            </div>
          </template>
        </TableColumn>
        <TableColumn label="复议明细" width="120px">
          <template #default="scope">
            <t-popup placement="right" width="500" trigger="click">
              <template #reference>
                <t-button size="small">复议{{scope.row.dispNum}}人次</t-button>
              </template>
              <CustomTable rowKey="id" :data="scope.row.quesDisputes" size="small">
                <TableColumn width="150" property="userAnswer" label="用户答案" />
                <TableColumn property="disputeMemo" label="复议理由" />
                <TableColumn width="80" property="ploNum" label="复议工号" />
                <TableColumn width="100" property="dataDate" label="复议时间" />
              </CustomTable>
            </t-popup>
          </template>
        </TableColumn>
        <TableColumn property="disputeResult" label="复议回复" width="120px">
          <template #default="scope">
            {{dictStore.getDictLabel('trm_exam_dispute_result', scope.row.disputeResult)}}
          </template>
        </TableColumn>
        <TableColumn property="disputeResult" label="操作">
          <template #default="scope">
            <t-button theme="success" size="small" @click="disputeSubmit(scope.row, 1)" plain>复议通过</t-button>
            <t-button theme="danger" size="small" @click="disputeSubmit(scope.row, 0)" plain>复议不过</t-button>
          </template>
        </TableColumn>
      </CustomTable>
      <t-pagination
        @page-size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current="currentPage"
        :page-size-options="pageSizes"
        :page-size="queryInfo.pageSize"

        :total="total"
      />
    </t-dialog>
  </div>
</template>
<script setup>
import { ref, reactive } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { useDictStore } from '@/stores'
import { questionDisputeApi } from '@/api/edu/questionDispute'

const dialogTableVisible = ref(false)
const currentPage = ref(1)
const pageSizes = ref([20, 100, 500])
const total = ref(0)
const disputeList = ref([])
const dictStore = useDictStore()

const queryInfo = reactive({
  orderType: ' desc',
  order: ' examCode ',
  disputeResult: 2,
  querytype: '',
  query: '',
  examCode: null,
  pageSize: 20,
  pageNum: 1
})


const show = (examCode) => {
  queryInfo.examCode = examCode
  listDisputeList()
  dialogTableVisible.value = true
}

const listDisputeList = async () => {
  try {
    const res = await questionDisputeApi.listDisputeList({ params: queryInfo })
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    disputeList.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    MessagePlugin.error('获取复议列表失败')
  }
}

const handleSizeChange = (pageSize) => {
  queryInfo.pageSize = pageSize
  listDisputeList()
}

const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  listDisputeList()
}

const disputeSubmit = async (row, disputeResult) => {
  row.disputeResult = disputeResult
  row.examCode = queryInfo.examCode
  try {
    const res = await questionDisputeApi.updateDispute(row)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    listDisputeList()
  } catch (error) {
    MessagePlugin.error('提交复议结果失败')
  }
}
defineExpose({
  show
})
</script>
<style lang="less" scoped>

</style>