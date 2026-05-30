<template>
  <div>
    <el-dialog
      title="任务名单管理"
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
                style="width: 80px"
                placeholder="请选择"
                size="small"
              >
                <el-option label="姓名" value="ploName"></el-option>
                <el-option label="工号" value="ploNum"></el-option>
                <el-option label="科室" value="deptNum"></el-option>
                <el-option label="组别" value="deptGroup"></el-option>
              </el-select>
            </template>
            <template #append>
              <el-button
                icon="Search"
                size="small"
                @click="getCourseTaskList"
              ></el-button>
            </template>
          </el-input>
        </el-col>
        <el-col :span="10">
          <EmployeeSelect
            placeholder="输入工号添加"
            v-model="examScoreForm.ploNum"
            size="small"
          ></EmployeeSelect>
          <el-button
            type="primary"
            icon="Plus"
            size="small"
            @click="addNewUser"
          >
            增加
          </el-button>
        </el-col>
        <el-col :span="4">
          <el-button
            size="small"
            type="primary"
            @click="
              uploadExcelRef.show({
                url: 'edu/excel/in/ques/score',
                courseId: queryInfo.courseId
              })
            "
          >
            批量
          </el-button>
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
        <el-table-column
          prop="ploNum"
          label="员编"
          sortable="custom"
        ></el-table-column>
        <el-table-column prop="ploName" label="姓名" width="100px">
          <template #default="scope">{{ scope.row.user.ploName }}</template>
        </el-table-column>
        <el-table-column
          label="报名情况"
          prop="compStat"
          width="100px"
          sortable="custom"
        >
          <template #default="scope">
            {{
              scope.row.compStat === 2 ? '任务' : `其他[${scope.row.compStat}]`
            }}
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        v-model:current-page="currentPage"
        :page-sizes="pageSizes"
        v-model:page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      />
      <template #footer>
        <div class="dialog-footer">
          <el-button size="small" @click="dialogVisible = false"
            >取 消</el-button
          >
          <el-button size="small" type="primary" @click="dialogVisible = false">
            确 定
          </el-button>
        </div>
      </template>
    </el-dialog>
    <UploadExcel ref="uploadExcelRef"></UploadExcel>
  </div>
</template>
<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import UploadExcel from '@/components/UploadExcel.vue'
import { examConfigApi } from '@/api/edu/examConfig'
import EmployeeSelect from '@/components/EmployeeSelect.vue'
// 定义组件属性
const props = defineProps({
  // 可以根据需要添加props
})

// 定义事件
const emit = defineEmits([])

// 响应式数据
const dialogVisible = ref(false)
const uploadExcelRef = ref(null)
const currentPage = ref(1)
const total = ref(0)
const pageSizes = ref([20, 100, 500])

const queryInfo = reactive({
  courseId: '',
  orderType: ' desc',
  order: ' deptGroup ',
  queryType: 'ploName',
  query: '',
  status: 1,
  pageSize: 20,
  pageNum: 1
})

const examScoreeList = ref([])
const examScoreForm = reactive({
  ploNum: '',
  courseId: '',
  compStat: 0,
  userScore: 0
})

// 显示对话框
const show = (courseId) => {
  queryInfo.courseId = courseId
  dialogVisible.value = true
  getCourseTaskList()
}

// 获取任务名单列表
const getCourseTaskList = async () => {
  try {
    const res = await examConfigApi.getExamScoreList(queryInfo)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    examScoreeList.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    ElMessage.error('获取任务名单失败')
  }
}

// 添加新用户
const addNewUser = async () => {
  try {
    examScoreForm.courseId = queryInfo.courseId
    const res = await examConfigApi.addExamUser(examScoreForm)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    ElMessage.success(res.msg)
    examScoreForm.ploNum = ''
    getCourseTaskList()
  } catch (error) {
    ElMessage.error('添加用户失败')
  }
}

// 处理分页大小变化
const handleSizeChange = (pageSize) => {
  queryInfo.pageSize = pageSize
  currentPage.value = 1
  getCourseTaskList()
}

// 处理当前页码变化
const handleCurrentChange = (page) => {
  currentPage.value = page
  queryInfo.pageNum = page
  getCourseTaskList()
}

// 处理表格排序
const tableSort = (data) => {
  if (data.order === 'ascending') {
    queryInfo.orderType = ' asc '
  } else if (data.order === 'descending') {
    queryInfo.orderType = ' desc '
  }
  queryInfo.order = data.prop
  getCourseTaskList()
}

// 暴露方法给父组件
defineExpose({
  show
})
</script>
<style lang="less" scoped></style>
