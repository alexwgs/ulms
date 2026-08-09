<template>
  <div>
    <t-card class="main-container">
      <PageTips
        title="操作说明"
        theme="info"
        message="此处仅针对学霸刷题中反馈的题目问题。无论选择复议通过还是复议不过均不对原题做任何联动。如需修改题目请自行于题库配置维护！"
        :closable="false"
      >
      </PageTips>
      <t-form :data="queryInfo" label-width="80px" colon class="filter-form">
        <t-row :gutter="[24, 24]">
          <t-col :span="3">
            <t-form-item label="复议状态" name="disputeResult">
              <t-select
                v-model="queryInfo.disputeResult"
                size="small"
                placeholder="全部"
                @change="listDisputeList"
              >
                <t-option
                  v-for="item in (dictStore.dictList?.trm_exam_dispute_result || [])"
                  :key="item.code"
                  :label="item.codeval"
                  :value="parseInt(item.code)"
                ></t-option>
              </t-select>
            </t-form-item>
          </t-col>
        </t-row>
      </t-form>
      <CustomTable rowKey="id" :data="disputeList" size="small" height="calc(100vh - 400px)">
        <TableColumn
          property="quesCode"
          label="题目编号"
          width="120"></TableColumn>
        <TableColumn
          property="quesStem"
          label="题干"
          ellipsis></TableColumn>
        <TableColumn property="options" label="原始选项/答案" width="200">
          <template #default="scope">
            <div v-if="scope.row.quesType === 2">
              <t-checkbox-group v-model="scope.row.answer">
                <t-checkbox
                  v-for="(option, index) in scope.row.options"
                  :key="index"
                  :value="option"
                  :label="option"
                ></t-checkbox>
              </t-checkbox-group>
            </div>
            <div v-else>
              <t-radio-group v-model="scope.row.answer" class="ques-option">
                <t-radio
                  v-for="(option, index) in scope.row.options"
                  :key="index"
                  :value="option"
                  class="ques-option"
                  :label="option"
                ></t-radio>
              </t-radio-group>
            </div>
          </template>
        </TableColumn>
        <TableColumn label="复议明细" width="120px">
          <template #default="scope">
            <DisputeDetailPopover
              :dispute-data="scope.row.quesDisputes"
              :dispute-count="scope.row.dispNum"
            />
          </template>
        </TableColumn>
        <TableColumn
          property="disputeResult"
          label="复议回复"
          width="120px">
          <template #default="scope">
            {{
              getDictionaryLabel(
                'trm_exam_dispute_result',
                scope.row.disputeResult
              )
            }}
          </template>
        </TableColumn>
        <TableColumn property="disputeResult" label="操作">
          <template #default="scope">
            <t-button
              theme="success"
              size="small"
              @click="disputeSubmit(scope.row, 1)"
              variant="outline"
              >复议通过</t-button
            >
            <t-button
              theme="danger"
              size="small"
              @click="disputeSubmit(scope.row, 0)"
              variant="outline"
              >复议不过</t-button
            >
          </template>
        </TableColumn>
      </CustomTable>
      <t-pagination
        @page-size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current="queryInfo.pageNum"
        :page-size-options="pageSizes"
        :page-size="queryInfo.pageSize"

        :total="total"
      ></t-pagination>
    </t-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import DisputeDetailPopover from './components/DisputeDetailPopover.vue'
import { questionDisputeApi } from '@/api/edu/questionDispute'
import { useDictStore } from '@/stores'

// Store
const dictStore = useDictStore()

// Reactive data
const queryInfo = reactive({
  orderType: ' desc',
  order: ' quesCode ',
  disputeResult: 2,
  querytype: '',
  query: '',
  examCode: null,
  pageSize: 20,
  pageNum: 1
})

const pageSizes = [20, 100, 500]
const total = ref(0)
const disputeList = ref([])

// Lifecycle
onMounted(() => {
  listDisputeList()
})

// Methods
const listDisputeList = async () => {
  try {
    const res = await questionDisputeApi.getQuestionDisputeList(queryInfo)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    disputeList.value = res.data.list
    total.value = res.data.total
    disputeList.value.forEach((item) => {
      item.options = item.options?.split(',') || ['【选项失效】']
      item.quesStem = item.quesStem ? item.quesStem : '【题目失效】'
      if (item.quesType === 2) {
        item.answer = item.answer.split(',')
      }
    })
  } catch (error) {
    MessagePlugin.error('获取数据失败，请重试')
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
  try {
    const updatedRow = { ...row, disputeResult, examCode: null }
    const res = await questionDisputeApi.updateDispute(updatedRow)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    listDisputeList()
  } catch (error) {
    MessagePlugin.error('操作失败，请重试')
  }
}

const getDictionaryLabel = (dictType, code) => {
  const dictList = dictStore.dictList[dictType] || []
  const item = dictList.find((item) => item.code === code.toString())
  return item ? item.codeval : code
}
</script>

<style lang="less" scoped>
.main-container {
  height: calc(100vh - 185px);
}

.tree-box {
  height: 450px;
  overflow: auto;
}
</style>
