<template>
  <t-card class="box-card">
    <t-row>
      <t-col :span="3">
        <t-date-range-picker v-model="dataRange" @change="daterangeChange" :placeholder="['开始日期', '结束日期']" size="small" style="width: 100%" />
      </t-col>
      <t-col :span="2">
        <t-select
          v-model="queryInfo.status"
          placeholder="操作类型"
          @change="getOperLogListData"
          style="width: 120px"
          size="small"
        >
          <t-option value="" label="全部" />
          <t-option :value="0" label="正常" />
          <t-option :value="1" label="异常" />
        </t-select>
      </t-col>
      <t-col :span="6">
        <t-input
          v-model="queryInfo.query"
          placeholder="请输入内容"
          size="small"
          style="max-width: 450px"
        >
          <template #prepend>
            <t-select
              v-model="queryInfo.queryType"
              placeholder="请选择"
              size="small"
              style="width: 100px"
            >
              <t-option label="模块标题" value="title" />
              <t-option label="触发用户" value="ploNum" />
            </t-select>
          </template>
          <template #append>
            <t-button
              size="small" @change="getOperLogListData"><template #icon><DynamicIcon name="search" /></template></t-button>
          </template>
        </t-input>
      </t-col>
      <t-col :span="1">
        <t-button theme="default" size="small" @click="handleDownload">
          下载
        </t-button>
      </t-col>
    </t-row>

    <CustomTable rowKey="id"
      :data="tableData"
      @sort-change="tableSort"
      stripe
      height="calc(100vh - 325px)"
      size="small">
      <TableColumn
        prop="operTime"
        sortable="custom"
        label="时间"
        width="160"
      />
      <TableColumn
        prop="ploNum"
        sortable="custom"
        label="触发用户"
        width="100"
      />
      <TableColumn
        prop="title"
        sortable="custom"
        label="模块标题"
        width="200"
      />
      <TableColumn
        prop="content"
        sortable="custom"
        label="日志内容"
        width="100"
      />
      <TableColumn
        prop="method"
        sortable="custom"
        label="方法名称"
        width="160"
        ellipsis
      />
      <TableColumn
        prop="requestUrl"
        label="请求URL"
        ellipsis
      />
      <TableColumn
        prop="ip"
        sortable="custom"
        label="IP"
        width="120"
        ellipsis
      />
      <TableColumn
        prop="requestParam"
        label="请求参数"
        ellipsis
      />
      <TableColumn
        prop="responseResult"
        label="方法响应参数"
        ellipsis
      />
      <TableColumn
        prop="takeTime"
        sortable="custom"
        label="耗时"
        width="70"
      />
      <TableColumn colKey="status" label="状态" width="80">
        <template #default="scope">
          <t-popup
            placement="left"
            :title="scope.row.status === 1 ? '异常信息' : '正常'"
            width="600"
            trigger="hover"
            :content="scope.row.errorMsg"
          >
            <template #reference>
              <t-tag
                :theme="scope.row.status === 1 ? 'danger' : 'success'"
                effect="plain"
                size="small"
              >
                {{ scope.row.status === 1 ? '异常' : '正常' }}
              </t-tag>
            </template>
          </t-popup>
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
  </t-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
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
      MessagePlugin.error(res.msg)
      return
    }
    tableData.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    MessagePlugin.error('获取日志列表失败')
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
  if (!data.descending) {
    queryInfo.value.orderType = ' asc '
  } else if (data.descending) {
    queryInfo.value.orderType = ' desc '
  }
  queryInfo.value.order = data.sortBy
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
    MessagePlugin.error('下载失败')
  }
}

// 组件挂载时获取数据
onMounted(() => {
  getOperLogListData()
})
</script>

<style lang="less" scoped>
.t-link {
  font-size: 12px;
}
.box-card {
  height: calc(100vh - 190px);
  overflow: auto;
}
</style>
