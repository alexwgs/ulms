<template>
  <div>
    <t-dialog
      header="任务名单管理"
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
                style="width: 80px"
                placeholder="请选择"
                size="small"
              >
                <t-option label="姓名" value="ploName"></t-option>
                <t-option label="工号" value="ploNum"></t-option>
                <t-option label="科室" value="deptNum"></t-option>
                <t-option label="组别" value="deptGroup"></t-option>
              </t-select>
            </template>
            <template #append>
              <t-button size="small"
                @click="getCourseTaskList"><template #icon><DynamicIcon name="search" /></template></t-button>
            </template>
          </t-input>
        </t-col>
        <t-col :span="5">
          <EmployeeSelect
            placeholder="输入工号添加"
            v-model="examScoreForm.ploNum"
            size="small"
          ></EmployeeSelect>
          <t-button
            theme="primary" size="small"
            @click="addNewUser"><template #icon><DynamicIcon name="add" /></template>
            增加
          </t-button>
        </t-col>
        <t-col :span="2">
          <t-button
            size="small"
            theme="primary"
            @click="
              uploadExcelRef.show({
                url: 'edu/excel/in/ques/score',
                courseId: queryInfo.courseId
              })
            "
          >
            批量
          </t-button>
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
        <TableColumn
          prop="ploNum"
          label="员编"
          sortable="custom"></TableColumn>
        <TableColumn colKey="ploName" label="姓名" width="100px">
          <template #default="scope">{{ scope.row.user.ploName }}</template>
        </TableColumn>
        <TableColumn
          label="报名情况"
          prop="compStat"
          width="100px"
          sortable="custom">
          <template #default="scope">
            {{
              scope.row.compStat === 2 ? '任务' : `其他[${scope.row.compStat}]`
            }}
          </template>
        </TableColumn>
      </CustomTable>
      <t-pagination
        @page-size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        v-model:current="currentPage"
        :page-size-options="pageSizes"
        v-model:page-size="queryInfo.pageSize"

        :total="total"
      />
      <template #footer>
        <div class="dialog-footer">
          <t-button size="small" @click="dialogVisible = false"
            >取 消</t-button
          >
          <t-button size="small" theme="primary" @click="dialogVisible = false">
            确 定
          </t-button>
        </div>
      </template>
    </t-dialog>
    <UploadExcel ref="uploadExcelRef"></UploadExcel>
  </div>
</template>
<script setup>
import { ref, reactive } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
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
      MessagePlugin.error(res.msg)
      return
    }
    examScoreeList.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    MessagePlugin.error('获取任务名单失败')
  }
}

// 添加新用户
const addNewUser = async () => {
  try {
    examScoreForm.courseId = queryInfo.courseId
    const res = await examConfigApi.addExamUser(examScoreForm)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    MessagePlugin.success(res.msg)
    examScoreForm.ploNum = ''
    getCourseTaskList()
  } catch (error) {
    MessagePlugin.error('添加用户失败')
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
  if (!data.descending) {
    queryInfo.orderType = ' asc '
  } else if (data.descending) {
    queryInfo.orderType = ' desc '
  }
  queryInfo.order = data.sortBy
  getCourseTaskList()
}

// 暴露方法给父组件
defineExpose({
  show
})
</script>
<style lang="less" scoped></style>
