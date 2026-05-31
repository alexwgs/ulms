<template>
  <div>
    <t-dialog
      header="考试名单"
      v-model:visible="dialogVisible"
      :close-on-overlay-click="false"
      width="70%"
    >
      <t-row :gutter="10">
        <t-col :span="5">
          <t-input
            placeholder="请输入对应的搜索内容"
            v-model="queryInfo.query"
            size="small"
          >
            <template #prepend>
              <t-select
                v-model="queryInfo.queryType"
                size="small"
                placeholder="请选择"
                style="width: 115px"
              >
                <t-option label="姓名" value="ploName" />
                <t-option label="工号" value="ploNum" />
                <t-option label="科室" value="deptNum" />
                <t-option label="组别" value="deptGroup" />
              </t-select>
            </template>
            <template #append>
              <t-button
                size="small" @click="getExamScoreList()"
              ><template #icon><DynamicIcon name="search" /></template></t-button>
            </template>
          </t-input>
        </t-col>
        <t-col :span="5">
          <EmployeeSelect
            v-model="examScoreForm.ploNum"
            placeholder="请选择员工"
            size="small"
          />
          <t-button
            slot="append"
            theme="primary"
            size="small" @click="addNewUser"
          ><template #icon><DynamicIcon name="plus" /></template></t-button>
        </t-col>
        <t-col :span="2">
          <t-button
            size="small"
            theme="primary"
            @click="
              uploadExcelRef.show({
                url: 'edu/excel/in/ques/score',
                examCode: queryInfo.examCode
              })
            "
            >批量导入</t-button
          >
        </t-col>
      </t-row>

      <CustomTable rowKey="id"
        :data="examScoreeList"
        size="small"
        height="400px"
        stripe
        @sort-change="tableSort"
        style="width: 100%">
        <TableColumn
          prop="deptNum"
          label="科室"
          width="110px"
          sortable="custom">
          <template #default="scope">{{ scope.row.user.deptName }}</template>
        </TableColumn>
        <TableColumn
          prop="deptGroup"
          label="组别"
          width="110px"
          sortable="custom">
          <template #default="scope">{{ scope.row.user.groupName }}</template>
        </TableColumn>
        <TableColumn colKey="ploNum" label="员编" sortable="custom" />
        <TableColumn colKey="ploName" label="姓名" width="100px">
          <template #default="scope">{{ scope.row.user.ploName }}</template>
        </TableColumn>
        <TableColumn
          label="考试状态"
          prop="compStat"
          width="100px"
          sortable="custom">
          <template #default="scope">
            {{
              dictStore.getDictLabel('trm_exam_comp_stat', scope.row.compStat)
            }}
          </template>
        </TableColumn>
        <TableColumn
          prop="userScore"
          label="得分"
          width="80px"
          sortable="custom"
        />
        <TableColumn label="操作" width="80px">
          <template #default="scope">
            <!-- <t-button v-if="scope.row.compStat===0" theme="primary" size="small" @click="editBtn(scope.row)" shape="circle"><template #icon><DynamicIcon name="edit" /></template></t-button> -->
            <t-button
              v-if="scope.row.compStat === 0"
              theme="warning" size="small"
              @click="deleteUser(scope.row.journo)"
              circle
            ><template #icon><DynamicIcon name="delete" /></template></t-button>
            <t-button
              v-else
              theme="danger"
              size="small"
              @click="reExam(scope.row)"
              >重考</t-button
            >
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
      <template #footer>
        <span class="dialog-footer">
          <t-button size="small" @click="dialogVisible = false"
            >取 消</t-button
          >
          <t-button size="small" theme="primary" @click="dialogVisible = false"
            >确 定</t-button
          >
        </span>
      </template>
    </t-dialog>
    <UploadExcel
      ref="uploadExcelRef"
      title="考试名单导入"
      info=" 导入【月考】，表头需有“员编”、“考试类型”列！ "
    />
  </div>
</template>
<script setup>
import { ref, reactive } from 'vue'
import { MessagePlugin, DialogPlugin } from 'tdesign-vue-next'
import UploadExcel from '@/components/UploadExcel.vue'
import { examConfigApi } from '@/api/edu/examConfig'
import { useDictStore } from '@/stores'
import EmployeeSelect from '@/components/EmployeeSelect.vue'
const uploadExcelRef = ref(null)
const dialogVisible = ref(false)
const currentPage = ref(1)
const pageSizes = ref([20, 100, 500])
const total = ref(0)
const examScoreeList = ref([])
const dictStore = useDictStore()

const queryInfo = reactive({
  examCode: '',
  orderType: ' desc',
  order: ' deptGroup ',
  queryType: 'ploName',
  query: '',
  status: 1,
  pageSize: 20,
  pageNum: 1
})

const examScoreForm = reactive({
  ploNum: '',
  examCode: '',
  compStat: 0,
  userScore: 0
})

const show = (examCode) => {
  queryInfo.examCode = examCode
  getExamScoreList()
  dialogVisible.value = true
}

const getExamScoreList = async () => {
  try {
    const res = await examConfigApi.getExamScoreList(queryInfo)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    examScoreeList.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    MessagePlugin.error('获取考试成绩列表失败')
  }
}

const deleteUser = async (journo) => {
  try {
    const res = await examConfigApi.deleteExamUser(journo)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    getExamScoreList()
  } catch (error) {
    MessagePlugin.error('删除用户失败')
  }
}

const addNewUser = async () => {
  examScoreForm.examCode = queryInfo.examCode
  try {
    const res = await examConfigApi.addExamUser(examScoreForm)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    examScoreForm.ploNum = ''
    getExamScoreList()
  } catch (error) {
    MessagePlugin.error('添加用户失败')
  }
}

const handleSizeChange = (pageSize) => {
  queryInfo.pageSize = pageSize
  getExamScoreList()
}

const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  getExamScoreList()
}

const tableSort = (data) => {
  if (!data.descending) queryInfo.orderType = ' asc '
  else if (data.descending) queryInfo.orderType = ' desc '
  queryInfo.order = data.sortBy
  getExamScoreList()
}

const reExam = (row) => {
  DialogPlugin.confirm(
    '慎重！该操作将重置该用户的考试状态，是否继续?',
    '提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  )
    .then(async () => {
      try {
        const res = await examConfigApi.resetExamStatus(row)
        if (res.code !== 200) return MessagePlugin.error(res.msg)
        MessagePlugin.success(res.msg)
        getExamScoreList()
      } catch (error) {
        MessagePlugin.error('重置考试状态失败')
      }
    })
    .catch(() => {
      MessagePlugin.info({
        type: 'info',
        message: '已取消操作'
      })
    })
}

defineExpose({
  show
})
</script>
<style lang="less" scoped></style>
