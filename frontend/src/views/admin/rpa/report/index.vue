<template>
  <el-card class="report-card">
    <!-- 筛选条件区域 -->
    <el-row :gutter="15" class="filter-row">
      <el-col :span="8">
        <el-date-picker
          size="small"
          v-model="dataRange"
          @change="daterangeChange"
          type="daterange"
          value-format="YYYY-MM-DD"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          class="date-picker"
        />
      </el-col>
      <el-col :span="6">
        <el-select
          v-model="queryInfo.query"
          filterable
          remote
          reserve-keyword
          placeholder="请输入工具名称(可搜索)"
          :remote-method="remoteMethod"
          :loading="loading"
          size="small"
          @change="getList"
        >
          <el-option
            v-for="(item, index) in rpaToolNameList"
            :key="index"
            :label="item"
            :value="item"
          />
        </el-select>
      </el-col>
      <el-col :span="6">
        <EmployeeSelect
          v-model="queryInfo.runUser"
          placeholder="选择运行人筛选"
          size="small"
          @update:modelValue="getList"
        />
      </el-col>
      <el-col :span="4">
        <el-button
          type="primary"
          size="small"
          @click="downloadExcelReport"
          :loading="downloadLoading"
        >
          下载报表
        </el-button>
      </el-col>
    </el-row>

    <!-- 操作说明 -->
    <el-alert
      title="操作说明"
      type="info"
      :closable="false"
      description="请正确使用RPA工具配置：1.新建服务时务必完整填写相关信息。2.无需参数的可不配置参数模版，如有请务必准确配置。3.权限，如不配置则全员可查看"
      class="alert-message"
    />

    <!-- 数据表格 -->
    <el-table
      :data="list"
      size="small"
      height="calc(100vh - 450px)"
      stripe
      @sort-change="tableSort"
      class="report-table"
      v-loading="loading"
    >
      <el-table-column
        prop="toolName"
        label="工具名称"
        sortable="custom"
        show-overflow-tooltip
        min-width="120"
      />
      <el-table-column
        prop="runTime"
        label="运行时间"
        sortable="custom"
        show-overflow-tooltip
        min-width="150"
      />
      <el-table-column
        prop="runParam"
        label="运行参数"
        sortable="custom"
        show-overflow-tooltip
        min-width="150"
      />
      <el-table-column
        prop="returnCode"
        label="运行情况"
        sortable="custom"
        show-overflow-tooltip
        min-width="100"
      />
      <el-table-column
        prop="returnMessage"
        label="运行信息"
        sortable="custom"
        show-overflow-tooltip
        min-width="150"
      />
      <el-table-column
        prop="returnTime"
        label="返回时间"
        sortable="custom"
        show-overflow-tooltip
        min-width="150"
      />
      <el-table-column label="运行经办" width="140">
        <template #default="{ row }">
          {{
            row.user?.ploName ? `${row.user.ploName}/${row.user.ploNum}` : '-'
          }}
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="queryInfo.pageNum"
      :page-sizes="[20, 40, 100, 200]"
      :page-size="queryInfo.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      class="pagination"
    />
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { downloadExcel } from '@/utils/request'
import { getRpaReportList, getRpaToolName } from '@/api/rpa/rpa.js'
import EmployeeSelect from '@/components/EmployeeSelect.vue'
// 响应式数据
const dataRange = ref([])
const loading = ref(false)
const downloadLoading = ref(false)
const list = ref([])
const total = ref(0)
const rpaToolNameList = ref([])
// 查询参数
const queryInfo = ref({
  orderType: 'desc',
  order: 'runTime',
  runUser: '',
  queryType: '',
  begDate: '',
  endDate: '',
  query: '',
  pageSize: 20,
  pageNum: 1
})

// 获取报表数据
const getList = async () => {
  try {
    loading.value = true
    const res = await getRpaReportList(queryInfo.value)
    list.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    ElMessage.error(error.message || '获取报表数据失败')
  } finally {
    loading.value = false
  }
}
const remoteMethod = (query) => {
  if (query) {
    loading.value = true
    setTimeout(async () => {
      loading.value = false
      const res = await getRpaToolName(query)
      if (res.code !== 200) {
        ElMessage.error(res.msg || '获取RPA工具名称失败')
        return
      }
      rpaToolNameList.value = res.data
    }, 200)
  } else {
    rpaToolNameList.value = []
  }
}

// 日期范围变化
const daterangeChange = () => {
  if (dataRange.value && dataRange.value.length === 2) {
    queryInfo.value.begDate = dataRange.value[0]
    queryInfo.value.endDate = dataRange.value[1]
  } else {
    queryInfo.value.begDate = ''
    queryInfo.value.endDate = ''
  }
  getList()
}

// 分页大小变化
const handleSizeChange = (pageSize) => {
  queryInfo.value.pageSize = pageSize
  getList()
}

// 当前页码变化
const handleCurrentChange = (page) => {
  queryInfo.value.pageNum = page
  getList()
}

// 表格排序
const tableSort = ({ prop, order }) => {
  queryInfo.value.order = prop
  queryInfo.value.orderType = order === 'ascending' ? 'asc' : 'desc'
  getList()
}

// 下载Excel
const downloadExcelReport = async () => {
  try {
    downloadLoading.value = true
    await downloadExcel(
      'rpa/report/runlist',
      queryInfo.value,
      'RPA工具使用情况报表.xlsx'
    )
  } catch (error) {
    ElMessage.error('下载失败: ' + (error.message || '未知错误'))
  } finally {
    downloadLoading.value = false
  }
}

// 初始化
onMounted(() => {
  getList()
})
</script>

<style lang="less" scoped>
.rpa-report-container {
  height: 100%;
  padding: 16px;
}

.report-card {
  margin-top: 16px;
  height: calc(100% - 50px);
}

.filter-row {
  margin-bottom: 16px;

  .date-picker {
    width: 100%;
  }
}

.alert-message {
  margin-bottom: 16px;
}

.report-table {
  margin-top: 16px;
}

.pagination {
  margin-top: 16px;
  justify-content: flex-end;
}
</style>
