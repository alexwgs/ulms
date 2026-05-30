<template>
  <div>
    <el-dialog title="复议列表" v-model="dialogTableVisible" fullscreen>
      <el-row :gutter="15">
        <el-col :span="6">
          <el-select v-model="queryInfo.disputeResult" size="small" placeholder="复议状态" @change="listDisputeList">
            <el-option v-for="item in dictStore.dictList.trm_exam_dispute_result" :key="item.code" :label="item.codeval" :value="parseInt(item.code)" />
          </el-select>
        </el-col>
      </el-row>
      <el-table :data="disputeList" size="small">
        <el-table-column property="quesCode" label="题目编号" width="120" />
        <el-table-column property="quesStem" label="题干" show-overflow-tooltip />
        <el-table-column property="options" label="选项" width="200">
          <template #default="scope">
            <div v-if="scope.row.quesType === 2">
              <el-checkbox v-for="option in scope.row.options.split(',')" :key="option" :value="scope.row.answer.split(',')" :label="option">{{option}}</el-checkbox>
            </div>
            <div v-else>
              <el-radio v-for="option in scope.row.options.split(',')" :key="option" :value="scope.row.answer" class="ques-option" :label="option">{{option}}</el-radio>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="复议明细" width="120px">
          <template #default="scope">
            <el-popover placement="right" width="500" trigger="click">
              <template #reference>
                <el-button size="small">复议{{scope.row.dispNum}}人次</el-button>
              </template>
              <el-table :data="scope.row.quesDisputes" size="small">
                <el-table-column width="150" property="userAnswer" label="用户答案" />
                <el-table-column property="disputeMemo" label="复议理由" />
                <el-table-column width="80" property="ploNum" label="复议工号" />
                <el-table-column width="100" property="dataDate" label="复议时间" />
              </el-table>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column property="disputeResult" label="复议回复" width="120px">
          <template #default="scope">
            {{dictStore.getDictLabel('trm_exam_dispute_result', scope.row.disputeResult)}}
          </template>
        </el-table-column>
        <el-table-column property="disputeResult" label="操作">
          <template #default="scope">
            <el-button type="success" size="small" @click="disputeSubmit(scope.row, 1)" plain>复议通过</el-button>
            <el-button type="danger" size="small" @click="disputeSubmit(scope.row, 0)" plain>复议不过</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="pageSizes"
        :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      />
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
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
    if (res.code !== 200) return ElMessage.error(res.msg)
    disputeList.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    ElMessage.error('获取复议列表失败')
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
    if (res.code !== 200) return ElMessage.error(res.msg)
    ElMessage.success(res.msg)
    listDisputeList()
  } catch (error) {
    ElMessage.error('提交复议结果失败')
  }
}
defineExpose({
  show
})
</script>
<style lang="less" scoped>

</style>