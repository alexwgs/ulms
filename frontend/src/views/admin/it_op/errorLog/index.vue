<template>
  <t-card class="box-card">
    <t-row :gutter="20">
      <t-col :span="3">
        <span>
          <t-date-range-picker size="small" style="width: 100%" v-model="dataRange" @change="daterangeChange" :placeholder="['开始日期', '结束日期']" ></t-date-range-picker>
        </span>
      </t-col>
      <t-col :span="3">
        <span
          >日志级别
          <t-select
            size="small"
            v-model="queryInfo.priority"
            style="width: 150px"
            @change="getErrorLogList"
            placeholder="请选择,默认不限制"
          >
            <t-option value="" label="全部"></t-option>
            <t-option value="info" label="info"></t-option>
            <t-option value="warning" label="warning"></t-option>
            <t-option value="error" label="error"></t-option>
          </t-select>
        </span>
      </t-col>
      <t-col :span="3">
        <span
          >错误类型
          <t-select
            size="small"
            v-model="queryInfo.dataType"
            style="width: 150px"
            @change="getErrorLogList"
            placeholder="请选择,默认不限制"
          >
            <t-option label="全部" value=""></t-option>
            <t-option label="DataBase" value="DataBase"></t-option>
            <t-option label="Controller" value="Controller"></t-option>
            <t-option label="Class" value="Class"></t-option>
          </t-select>
        </span>
      </t-col>
    </t-row>
    <CustomTable rowKey="id"
      :data="tableData"
      size="small"
      sortable="custom"
      @sort-change="tableSort"
      stripe
      height="calc(100vh - 355px)">
      <TableColumn
        prop="dataTime"
        sortable="custom"
        label="时间"
        width="160"></TableColumn>
      <TableColumn
        prop="userId"
        sortable="custom"
        label="触发用户"
        width="100"></TableColumn>
      <TableColumn
        prop="priority"
        sortable="custom"
        label="日志级别"
        width="100">
        <template #default="{ row }">
          <t-tag :theme="getPriorityTagType(row.priority)" size="small">
            {{ row.priority }}
          </t-tag>
        </template>
      </TableColumn>
      <TableColumn
        prop="dataType"
        sortable="custom"
        label="日志类型"
        width="100"></TableColumn>
      <TableColumn
        prop="info"
        sortable="custom"
        label="错误信息"
        width="160"></TableColumn>
      <TableColumn
        prop="content"
        label="错误详情"
        ellipsis></TableColumn>
    </CustomTable>
    <t-pagination
      @page-size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current="currentPage"
      :page-size-options="pageSizes"
      :page-size="queryInfo.pageSize"

      :total="total"
    ></t-pagination>
  </t-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { errorLogApi } from '@/api/admin/errorLog'

// 数据定义
const tableData = ref([])
const dataRange = ref([])
const currentPage = ref(1)
const total = ref(0)

const queryInfo = reactive({
  orderType: ' desc',
  order: ' id ',
  priority: '',
  dataType: '',
  begDate: '',
  endDate: '',
  querytype: '',
  query: '',
  pageSize: 20,
  pageNum: 1
})

const pageSizes = [20, 100, 500]

// 方法
const getPriorityTagType = (priority) => {
  switch (priority) {
    case 'error':
      return 'danger'
    case 'warning':
      return 'warning'
    case 'info':
      return 'default'
    default:
      return ''
  }
}

const getErrorLogList = async () => {
  try {
    const res = await errorLogApi.listErrorLog(queryInfo)
    if (res.code !== 200) return
    tableData.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    MessagePlugin.error(error.message)
  }
}

const daterangeChange = () => {
  if (dataRange.value && dataRange.value.length === 2) {
    queryInfo.begDate = dataRange.value[0]
    queryInfo.endDate = dataRange.value[1]
    getErrorLogList()
  }
}

const handleSizeChange = (pageSize) => {
  queryInfo.pageSize = pageSize
  getErrorLogList()
}

const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  getErrorLogList()
}

const tableSort = ({ sortBy, descending }) => {
  queryInfo.orderType = !descending ? ' asc ' : ' desc '
  queryInfo.order = sortBy
  getErrorLogList()
}

// 生命周期
onMounted(() => {
  getErrorLogList()
})
</script>

<style lang="less" scoped>
.table-filter {
  padding: 10px;

  span {
    font-size: 12px;
    margin-left: 20px;
    display: inline-flex;
    align-items: center;

    .t-select {
      width: 55%;
      margin-left: 8px;
    }
  }
}

.box-card {
  height: calc(100vh - 240px);
  overflow: auto;

  .t-tag {
    margin-right: 5px;
  }
}
</style>
