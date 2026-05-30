<template>
  <div>
    <el-dialog
      title="考试名单"
      v-model="dialogVisible"
      :close-on-click-modal="false"
      width="70%"
    >
      <el-row :gutter="10">
        <el-col :span="10">
          <el-input
            placeholder="请输入对应的搜索内容"
            v-model="queryInfo.query"
            size="small"
          >
            <template #prepend>
              <el-select
                v-model="queryInfo.queryType"
                size="small"
                placeholder="请选择"
                style="width: 115px"
              >
                <el-option label="姓名" value="ploName" />
                <el-option label="工号" value="ploNum" />
                <el-option label="科室" value="deptNum" />
                <el-option label="组别" value="deptGroup" />
              </el-select>
            </template>
            <template #append>
              <el-button
                size="small"
                icon="search"
                @click="getExamScoreList()"
              />
            </template>
          </el-input>
        </el-col>
        <el-col :span="10">
          <EmployeeSelect
            v-model="examScoreForm.ploNum"
            placeholder="请选择员工"
            size="small"
          />
          <el-button
            slot="append"
            type="primary"
            size="small"
            icon="plus"
            @click="addNewUser"
          />
        </el-col>
        <el-col :span="4">
          <el-button
            size="small"
            type="primary"
            @click="
              uploadExcelRef.show({
                url: 'edu/excel/in/ques/score',
                examCode: queryInfo.examCode
              })
            "
            >批量导入</el-button
          >
        </el-col>
      </el-row>

      <el-table
        :data="examScoreeList"
        size="small"
        height="400px"
        stripe
        @sort-change="tableSort"
        style="width: 100%"
      >
        <el-table-column
          prop="deptNum"
          label="科室"
          width="110px"
          sortable="custom"
        >
          <template #default="scope">{{ scope.row.user.deptName }}</template>
        </el-table-column>
        <el-table-column
          prop="deptGroup"
          label="组别"
          width="110px"
          sortable="custom"
        >
          <template #default="scope">{{ scope.row.user.groupName }}</template>
        </el-table-column>
        <el-table-column prop="ploNum" label="员编" sortable="custom" />
        <el-table-column prop="ploName" label="姓名" width="100px">
          <template #default="scope">{{ scope.row.user.ploName }}</template>
        </el-table-column>
        <el-table-column
          label="考试状态"
          prop="compStat"
          width="100px"
          sortable="custom"
        >
          <template #default="scope">
            {{
              dictStore.getDictLabel('trm_exam_comp_stat', scope.row.compStat)
            }}
          </template>
        </el-table-column>
        <el-table-column
          prop="userScore"
          label="得分"
          width="80px"
          sortable="custom"
        />
        <el-table-column label="操作" width="80px">
          <template #default="scope">
            <!-- <el-button v-if="scope.row.compStat===0" type="primary" icon="edit" size="small" @click="editBtn(scope.row)" circle></el-button> -->
            <el-button
              v-if="scope.row.compStat === 0"
              type="warning"
              icon="delete"
              size="small"
              @click="deleteUser(scope.row.journo)"
              circle
            />
            <el-button
              v-else
              type="danger"
              size="small"
              @click="reExam(scope.row)"
              >重考</el-button
            >
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
      <template #footer>
        <span class="dialog-footer">
          <el-button size="small" @click="dialogVisible = false"
            >取 消</el-button
          >
          <el-button size="small" type="primary" @click="dialogVisible = false"
            >确 定</el-button
          >
        </span>
      </template>
    </el-dialog>
    <UploadExcel
      ref="uploadExcelRef"
      title="考试名单导入"
      info=" 导入【月考】，表头需有“员编”、“考试类型”列！ "
    />
  </div>
</template>
<script setup>
import { ref, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
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
    if (res.code !== 200) return ElMessage.error(res.msg)
    examScoreeList.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    ElMessage.error('获取考试成绩列表失败')
  }
}

const deleteUser = async (journo) => {
  try {
    const res = await examConfigApi.deleteExamUser(journo)
    if (res.code !== 200) return ElMessage.error(res.msg)
    ElMessage.success(res.msg)
    getExamScoreList()
  } catch (error) {
    ElMessage.error('删除用户失败')
  }
}

const addNewUser = async () => {
  examScoreForm.examCode = queryInfo.examCode
  try {
    const res = await examConfigApi.addExamUser(examScoreForm)
    if (res.code !== 200) return ElMessage.error(res.msg)
    ElMessage.success(res.msg)
    examScoreForm.ploNum = ''
    getExamScoreList()
  } catch (error) {
    ElMessage.error('添加用户失败')
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
  if (data.order === 'ascending') queryInfo.orderType = ' asc '
  else if (data.order === 'descending') queryInfo.orderType = ' desc '
  queryInfo.order = data.prop
  getExamScoreList()
}

const reExam = (row) => {
  ElMessageBox.confirm(
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
        if (res.code !== 200) return ElMessage.error(res.msg)
        ElMessage.success(res.msg)
        getExamScoreList()
      } catch (error) {
        ElMessage.error('重置考试状态失败')
      }
    })
    .catch(() => {
      ElMessage({
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
