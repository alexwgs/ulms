<template>
  <div>
    <el-card class="main-container">
      <el-alert
        title="操作说明"
        type="info"
        description="此处仅针对学霸刷题中反馈的题目问题。无论选择复议通过还是复议不过均不对原题做任何联动。如需修改题目请自行于题库配置维护！"
        :closable="false"
      >
      </el-alert>
      <el-row style="padding-bottom: 10px" :gutter="15">
        <el-col :span="4">
          <el-select
            v-model="queryInfo.disputeResult"
            size="small"
            placeholder="复议状态"
            @change="listDisputeList"
          >
            <el-option
              v-for="item in dictStore.dictList.trm_exam_dispute_result"
              :key="item.code"
              :label="item.codeval"
              :value="parseInt(item.code)"
            ></el-option>
          </el-select>
        </el-col>
        <el-col :span="10"> </el-col>
        <el-col :span="4"> </el-col>
        <el-col :span="4"> </el-col>
      </el-row>
      <el-table :data="disputeList" size="small" height="calc(100vh - 400px)">
        <el-table-column
          property="quesCode"
          label="题目编号"
          width="120"
        ></el-table-column>
        <el-table-column
          property="quesStem"
          label="题干"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column property="options" label="原始选项/答案" width="200">
          <template #default="scope">
            <div v-if="scope.row.quesType === 2">
              <el-checkbox-group v-model="scope.row.answer">
                <el-checkbox
                  v-for="(option, index) in scope.row.options"
                  :key="index"
                  :value="option"
                  :label="option"
                ></el-checkbox>
              </el-checkbox-group>
            </div>
            <div v-else>
              <el-radio-group v-model="scope.row.answer" class="ques-option">
                <el-radio
                  v-for="(option, index) in scope.row.options"
                  :key="index"
                  :value="option"
                  class="ques-option"
                  :label="option"
                ></el-radio>
              </el-radio-group>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="复议明细" width="120px">
          <template #default="scope">
            <DisputeDetailPopover
              :dispute-data="scope.row.quesDisputes"
              :dispute-count="scope.row.dispNum"
            />
          </template>
        </el-table-column>
        <el-table-column
          property="disputeResult"
          label="复议回复"
          width="120px"
        >
          <template #default="scope">
            {{
              getDictionaryLabel(
                'trm_exam_dispute_result',
                scope.row.disputeResult
              )
            }}
          </template>
        </el-table-column>
        <el-table-column property="disputeResult" label="操作">
          <template #default="scope">
            <el-button
              type="success"
              size="small"
              @click="disputeSubmit(scope.row, 1)"
              plain
              >复议通过</el-button
            >
            <el-button
              type="danger"
              size="small"
              @click="disputeSubmit(scope.row, 0)"
              plain
              >复议不过</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pageNum"
        :page-sizes="pageSizes"
        :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage } from 'element-plus'
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
    if (res.code !== 200) return ElMessage.error(res.msg)
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
    ElMessage.error('获取数据失败，请重试')
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
    if (res.code !== 200) return ElMessage.error(res.msg)
    ElMessage.success(res.msg)
    listDisputeList()
  } catch (error) {
    ElMessage.error('操作失败，请重试')
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
