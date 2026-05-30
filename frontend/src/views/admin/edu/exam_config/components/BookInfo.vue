<template>
  <div>
    <el-dialog
      title="预约名单"
      v-model="dialogVisible"
      :close-on-click-modal="false"
      width="70%"
    >
      <el-row :gutter="10">
        <el-col :span="10">
          <EmployeeSelect v-model="queryInfo.query" :clearable="true" />
        </el-col>
        <el-col :span="4">
          <el-button size="small" type="primary" @click="getBookList()"
            >查询</el-button
          >
          <el-button
            size="small"
            type="primary"
            @click="forceBook()"
            :disabled="queryInfo.query == ''"
            >强制预约</el-button
          >
        </el-col>
      </el-row>

      <el-table
        :data="bookListData"
        size="small"
        height="400px"
        stripe
        @sort-change="tableSort"
        style="width: 100%"
      >
        <el-table-column prop="deptNum" label="科室" width="110px">
          <template #default="scope">{{ scope.row.user.deptName }}</template>
        </el-table-column>
        <el-table-column prop="deptGroup" label="组别" width="110px">
          <template #default="scope">{{ scope.row.user.groupName }}</template>
        </el-table-column>
        <el-table-column
          prop="ploNum"
          label="员编"
          sortable="custom"
          width="80px"
        />
        <el-table-column prop="ploName" label="姓名" width="100px">
          <template #default="scope">{{ scope.row.user.ploName }}</template>
        </el-table-column>
        <el-table-column
          label="预约状态"
          prop="listStat"
          width="100px"
          sortable="custom"
        >
          <template #default="scope">
            {{ dictStore.getDictLabel('sys_dict_status', scope.row.listStat) }}
          </template>
        </el-table-column>
        <el-table-column
          prop="dateTime"
          label="预约时间"
          width="200px"
          show-overflow-tooltip
          sortable="custom"
        />
        <el-table-column
          prop="bookDate"
          label="操作时间"
          show-overflow-tooltip
          sortable="custom"
        />
        <el-table-column label="操作" width="80px">
          <template #default="scope">
            <el-button
              type="danger"
              icon="delete"
              size="small"
              @click="deleteUser(scope.row)"
              circle
            />
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
    <ExamBook ref="examBootRef" />
  </div>
</template>
<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { useDictStore } from '@/stores'
import { bookInfoApi } from '@/api/edu/bookInfo'
import EmployeeSelect from '@/components/EmployeeSelect.vue'
import ExamBook from './ExamBook.vue'

const examBootRef = ref(null)
const dialogVisible = ref(false)
const currentPage = ref(1)
const pageSizes = ref([20, 100, 500])
const total = ref(0)
const bookListData = ref([])
const dictStore = useDictStore()

const queryInfo = reactive({
  examCode: '',
  orderType: ' desc',
  order: ' ploNum ',
  queryType: 'ploNum',
  query: '',
  listStat: 1,
  pageSize: 20,
  pageNum: 1
})

const show = (examCode) => {
  queryInfo.examCode = examCode
  getBookList()
  dialogVisible.value = true
}

const getBookList = async () => {
  try {
    const res = await bookInfoApi.listBookList(queryInfo)
    if (res.code !== 200) return ElMessage.error(res.msg)
    bookListData.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    ElMessage.error('获取预约列表失败')
  }
}

const deleteUser = async (row) => {
  try {
    const res = await bookInfoApi.deleteBookList(row)
    if (res.code !== 200) return ElMessage.error(res.msg)
    ElMessage.success(res.msg)
    getBookList()
  } catch (error) {
    ElMessage.error('删除用户失败')
  }
}

const handleSizeChange = (pageSize) => {
  queryInfo.pageSize = pageSize
  getBookList()
}

const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  getBookList()
}

const tableSort = (data) => {
  if (data.order === 'ascending') queryInfo.orderType = ' asc '
  else if (data.order === 'descending') queryInfo.orderType = ' desc '
  queryInfo.order = data.prop
  getBookList()
}

const forceBook = () => {
  examBootRef.value?.show(queryInfo.examCode, queryInfo.query)
}
defineExpose({
  show
})
</script>
<style lang="less" scoped></style>
