<template>
  <t-card class="report-card">
    <!-- 筛选条件区域 -->
    <t-row :gutter="15" class="filter-row">
      <t-col :span="4">
        <t-date-range-picker size="small" v-model="dataRange" @change="daterangeChange" :placeholder="['开始日期', '结束日期']" class="date-picker" />
      </t-col>
      <t-col :span="3">
        <t-select
          v-model="queryInfo.query"
          filterable
          placeholder="请输入工具名称(可搜索)"
          @search="remoteMethod"
          :loading="loading"
          size="small"
          @change="getList"
        >
          <t-option
            v-for="(item, index) in rpaToolNameList"
            :key="index"
            :label="item"
            :value="item"
          />
        </t-select>
      </t-col>
      <t-col :span="3">
        <EmployeeSelect
          v-model="queryInfo.runUser"
          placeholder="选择运行人筛选"
          size="small"
          @update:modelValue="getList"
        />
      </t-col>
      <t-col :span="2">
        <t-button
          theme="primary"
          size="small"
          @click="downloadExcelReport"
          :loading="downloadLoading"
        >
          下载报表
        </t-button>
      </t-col>
    </t-row>

    <!-- 操作说明 -->
    <t-alert
      title="操作说明"
      theme="info"
      :closable="false"
      message="请正确使用RPA工具配置：1.新建服务时务必完整填写相关信息。2.无需参数的可不配置参数模版，如有请务必准确配置。3.权限，如不配置则全员可查看"
      class="alert-message"
    />

    <!-- 数据表格 -->
    <CustomTable rowKey="id"
      :data="list"
      size="small"
      height="calc(100vh - 450px)"
      stripe
      @sort-change="tableSort"
      class="report-table"
      :loading="loading">
      <TableColumn
        prop="toolName"
        label="工具名称"
        sortable="custom"
        ellipsis
        min-width="120"
      />
      <TableColumn
        prop="runTime"
        label="运行时间"
        sortable="custom"
        ellipsis
        min-width="150"
      />
      <TableColumn
        prop="runParam"
        label="运行参数"
        sortable="custom"
        ellipsis
        min-width="150"
      />
      <TableColumn
        prop="returnCode"
        label="运行情况"
        sortable="custom"
        ellipsis
        min-width="100"
      />
      <TableColumn
        prop="returnMessage"
        label="运行信息"
        sortable="custom"
        ellipsis
        min-width="150"
      />
      <TableColumn
        prop="returnTime"
        label="返回时间"
        sortable="custom"
        ellipsis
        min-width="150"
      />
      <TableColumn label="运行经办" width="140">
        <template #default="{ row }">
          {{
            row.user?.ploName ? `${row.user.ploName}/${row.user.ploNum}` : '-'
          }}
        </template>
      </TableColumn>
    </CustomTable>

    <!-- 分页组件 -->
    <t-pagination
      @page-size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current="queryInfo.pageNum"
      :page-size-options="[20, 40, 100, 200]"
      :page-size="queryInfo.pageSize"

      :total="total"
      class="pagination"
    />
  </t-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
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
    MessagePlugin.error(error.message || '获取报表数据失败')
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
        MessagePlugin.error(res.msg || '获取RPA工具名称失败')
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
const tableSort = ({ sortBy, descending }) => {
  queryInfo.value.order = sortBy
  queryInfo.value.orderType = !descending ? 'asc' : 'desc'
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
    MessagePlugin.error('下载失败: ' + (error.message || '未知错误'))
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
