<template>
  <el-card class="box-card">
    <el-row>
      <el-col :span="6">
        <el-date-picker
          v-model="dataRange"
          @change="daterangeChange"
          type="daterange"
          value-format="YYYY-MM-DD"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          size="small"
          style="width: 70%"
        />
      </el-col>
      <el-col :span="4">
        <el-select
          v-model="queryInfo.status"
          placeholder="操作类型"
          @change="getOperLogListData"
          style="width: 120px"
          size="small"
        >
          <el-option value="" label="全部" />
          <el-option :value="0" label="正常" />
          <el-option :value="1" label="异常" />
        </el-select>
      </el-col>
      <el-col :span="12">
        <el-input
          v-model="queryInfo.query"
          placeholder="请输入内容"
          size="small"
          style="max-width: 450px"
        >
          <template #prepend>
            <el-select
              v-model="queryInfo.queryType"
              placeholder="请选择"
              size="small"
              style="width: 100px"
            >
              <el-option label="模块标题" value="title" />
              <el-option label="触发用户" value="ploNum" />
            </el-select>
          </template>
          <template #append>
            <el-button
              size="small"
              icon="Search"
              @change="getOperLogListData"
            />
          </template>
        </el-input>
      </el-col>
      <el-col :span="2">
        <el-button type="info" size="small" @click="handleDownload">
          下载
        </el-button>
      </el-col>
    </el-row>

    <el-table
      :data="tableData"
      @sort-change="tableSort"
      stripe
      height="calc(100vh - 325px)"
      size="small"
    >
      <el-table-column
        prop="operTime"
        sortable="custom"
        label="时间"
        width="160"
      />
      <el-table-column
        prop="ploNum"
        sortable="custom"
        label="触发用户"
        width="100"
      />
      <el-table-column
        prop="title"
        sortable="custom"
        label="模块标题"
        width="200"
      />
      <el-table-column
        prop="content"
        sortable="custom"
        label="日志内容"
        width="100"
      />
      <el-table-column
        prop="method"
        sortable="custom"
        label="方法名称"
        width="160"
        show-overflow-tooltip
      />
      <el-table-column
        prop="requestUrl"
        label="请求URL"
        show-overflow-tooltip
      />
      <el-table-column
        prop="ip"
        sortable="custom"
        label="IP"
        width="120"
        show-overflow-tooltip
      />
      <el-table-column
        prop="requestParam"
        label="请求参数"
        show-overflow-tooltip
      />
      <el-table-column
        prop="responseResult"
        label="方法响应参数"
        show-overflow-tooltip
      />
      <el-table-column
        prop="takeTime"
        sortable="custom"
        label="耗时"
        width="70"
      />
      <el-table-column prop="status" label="状态" width="80">
        <template #default="scope">
          <el-popover
            placement="left"
            :title="scope.row.status === 1 ? '异常信息' : '正常'"
            width="600"
            trigger="hover"
            :content="scope.row.errorMsg"
          >
            <template #reference>
              <el-tag
                :type="scope.row.status === 1 ? 'danger' : 'success'"
                effect="plain"
                size="small"
              >
                {{ scope.row.status === 1 ? '异常' : '正常' }}
              </el-tag>
            </template>
          </el-popover>
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
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { operLogApi } from '@/API/admin/operLog'

// 响应式数据
const tableData = ref([])
const dataRange = ref([])
const currentPage = ref(1)
const pageSizes = ref([20, 100, 500])
const total = ref(0)

const queryInfo = ref({
  orderType: ' desc',
  order: ' id ',
  status: 0,
  query: '',
  queryType: 'ploNum',
  begDate: '',
  endDate: '',
  pageSize: 20,
  pageNum: 1
})

// 获取操作日志列表
const getOperLogListData = async () => {
  try {
    const res = await operLogApi.getOperLogList(queryInfo.value)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    tableData.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    ElMessage.error('获取日志列表失败')
  }
}

// 日期范围改变
const daterangeChange = () => {
  if (dataRange.value && dataRange.value.length === 2) {
    queryInfo.value.begDate = dataRange.value[0]
    queryInfo.value.endDate = dataRange.value[1]
  } else {
    queryInfo.value.begDate = ''
    queryInfo.value.endDate = ''
  }
  getOperLogListData()
}

// 处理页码大小改变
const handleSizeChange = (pageSize) => {
  queryInfo.value.pageSize = pageSize
  getOperLogListData()
}

// 处理当前页改变
const handleCurrentChange = (page) => {
  queryInfo.value.pageNum = page
  currentPage.value = page
  getOperLogListData()
}

// 表格排序
const tableSort = (data) => {
  if (data.order === 'ascending') {
    queryInfo.value.orderType = ' asc '
  } else if (data.order === 'descending') {
    queryInfo.value.orderType = ' desc '
  }
  queryInfo.value.order = data.prop
  getOperLogListData()
}

// 下载操作日志
const handleDownload = async () => {
  try {
    const res = await operLogApi.downloadOperLog(queryInfo.value)
    const blob = new Blob([res], {
      type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
    })
    const link = document.createElement('a')
    link.href = window.URL.createObjectURL(blob)
    link.download = '应用操作日志.xlsx'
    link.click()
    window.URL.revokeObjectURL(link.href)
  } catch (error) {
    ElMessage.error('下载失败')
  }
}

// 组件挂载时获取数据
onMounted(() => {
  getOperLogListData()
})
</script>

<style lang="less" scoped>
.el-link {
  font-size: 12px;
}
.box-card {
  height: calc(100vh - 190px);
  overflow: auto;
}
</style>
