<template>
  <el-card class="box-card">
    <el-row :gutter="20">
      <el-col :span="6">
        <span>
          <el-date-picker
            size="small"
            style="width: 70%"
            v-model="dataRange"
            @change="daterangeChange"
            type="daterange"
            value-format="YYYY-MM-DD"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          ></el-date-picker>
        </span>
      </el-col>
      <el-col :span="6">
        <span
          >日志级别
          <el-select
            size="small"
            v-model="queryInfo.priority"
            style="width: 150px"
            @change="getErrorLogList"
            placeholder="请选择,默认不限制"
          >
            <el-option value="" label="全部"></el-option>
            <el-option value="info" label="info"></el-option>
            <el-option value="warning" label="warning"></el-option>
            <el-option value="error" label="error"></el-option>
          </el-select>
        </span>
      </el-col>
      <el-col :span="6">
        <span
          >错误类型
          <el-select
            size="small"
            v-model="queryInfo.dataType"
            style="width: 150px"
            @change="getErrorLogList"
            placeholder="请选择,默认不限制"
          >
            <el-option label="全部" value=""></el-option>
            <el-option label="DataBase" value="DataBase"></el-option>
            <el-option label="Controller" value="Controller"></el-option>
            <el-option label="Class" value="Class"></el-option>
          </el-select>
        </span>
      </el-col>
    </el-row>
    <el-table
      :data="tableData"
      size="small"
      sortable="custom"
      @sort-change="tableSort"
      stripe
      height="calc(100vh - 355px)"
    >
      <el-table-column
        prop="dataTime"
        sortable="custom"
        label="时间"
        width="160"
      ></el-table-column>
      <el-table-column
        prop="userId"
        sortable="custom"
        label="触发用户"
        width="100"
      ></el-table-column>
      <el-table-column
        prop="priority"
        sortable="custom"
        label="日志级别"
        width="100"
      >
        <template #default="{ row }">
          <el-tag :type="getPriorityTagType(row.priority)" size="small">
            {{ row.priority }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="dataType"
        sortable="custom"
        label="日志类型"
        width="100"
      ></el-table-column>
      <el-table-column
        prop="info"
        sortable="custom"
        label="错误信息"
        width="160"
      ></el-table-column>
      <el-table-column
        prop="content"
        label="错误详情"
        show-overflow-tooltip
      ></el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="pageSizes"
      :page-size="queryInfo.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    ></el-pagination>
  </el-card>
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
      return 'info'
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
    ElMessage.error(error.message)
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

const tableSort = ({ order, prop }) => {
  queryInfo.orderType = order === 'ascending' ? ' asc ' : ' desc '
  queryInfo.order = prop
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

    .el-select {
      width: 55%;
      margin-left: 8px;
    }
  }
}

.box-card {
  height: calc(100vh - 240px);
  overflow: auto;

  .el-tag {
    margin-right: 5px;
  }
}
</style>
