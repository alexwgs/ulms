<template>
  <div>
    <t-dialog
      header="预约名单"
      v-model:visible="dialogVisible"
      :close-on-overlay-click="false"
      width="70%"
    >
      <t-row :gutter="10">
        <t-col :span="5">
          <EmployeeSelect v-model="queryInfo.query" :clearable="true" />
        </t-col>
        <t-col :span="2">
          <t-button variant="outline" size="small" theme="danger" @click="getBookList()"
            >查询</t-button
          >
          <t-button variant="outline"
            size="small"
            theme="danger"
            @click="forceBook()"
            :disabled="queryInfo.query == ''"
            >强制预约</t-button
          >
        </t-col>
      </t-row>

      <CustomTable rowKey="id"
        :data="bookListData"
        size="small"
        height="400px"
        stripe
        @sort-change="tableSort"
        style="width: 100%">
        <TableColumn colKey="deptNum" label="科室" width="110px">
          <template #default="scope">{{ scope.row.user.deptName }}</template>
        </TableColumn>
        <TableColumn colKey="deptGroup" label="组别" width="110px">
          <template #default="scope">{{ scope.row.user.groupName }}</template>
        </TableColumn>
        <TableColumn
          prop="ploNum"
          label="员编"
          sortable="custom"
          width="80px"
        />
        <TableColumn colKey="ploName" label="姓名" width="100px">
          <template #default="scope">{{ scope.row.user.ploName }}</template>
        </TableColumn>
        <TableColumn
          label="预约状态"
          prop="listStat"
          width="100px"
          sortable="custom">
          <template #default="scope">
            {{ dictStore.getDictLabel('sys_dict_status', scope.row.listStat) }}
          </template>
        </TableColumn>
        <TableColumn
          prop="dateTime"
          label="预约时间"
          width="200px"
          ellipsis
          sortable="custom"
        />
        <TableColumn
          prop="bookDate"
          label="操作时间"
          ellipsis
          sortable="custom"
        />
        <TableColumn label="操作" width="80px">
          <template #default="scope">
            <t-button variant="outline"
              theme="danger" size="small"
              @click="deleteUser(scope.row)"
             
            >删除</t-button>
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
    <ExamBook ref="examBootRef" />
  </div>
</template>
<script setup>
import { ref, reactive } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { useDictStore } from '@/stores'
import { bookInfoApi } from '@/api/edu/bookInfo'
import EmployeeSelect from '@/components/EmployeeSelect.vue'
import ExamBook from './ExamBook.vue'
import { usePagination } from '@/hooks/usePagination'

const examBootRef = ref(null)
const dialogVisible = ref(false)
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
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    bookListData.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    MessagePlugin.error('获取预约列表失败')
  }
}

const deleteUser = async (row) => {
  try {
    const res = await bookInfoApi.deleteBookList(row)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    getBookList()
  } catch (error) {
    MessagePlugin.error('删除用户失败')
  }
}





const tableSort = (data) => {
  if (!data.descending) queryInfo.orderType = ' asc '
  else if (data.descending) queryInfo.orderType = ' desc '
  queryInfo.order = data.sortBy
  getBookList()
}

const forceBook = () => {
  examBootRef.value?.show(queryInfo.examCode, queryInfo.query)
}
defineExpose({
  show
})
const { currentPage, pageSizes, handleCurrentChange, handleSizeChange } = usePagination({ query: queryInfo, fetch: getBookList, pageSizes: [20, 100, 500] })
</script>
<style lang="less" scoped></style>
