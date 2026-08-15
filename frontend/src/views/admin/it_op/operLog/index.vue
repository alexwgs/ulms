<template>
  <t-card class="management-card">
    <t-form :data="queryInfo" label-width="80px" colon class="filter-form">
      <t-row :gutter="[24, 24]">
        <t-col :span="5">
          <t-form-item label="日期范围" name="dataRange">
            <t-date-range-picker v-model="dataRange" @change="daterangeChange" :placeholder="['开始日期', '结束日期']" size="small" />
          </t-form-item>
        </t-col>
        <t-col :span="3">
          <t-form-item label="操作类型" name="status">
            <t-select
              v-model="queryInfo.status"
              placeholder="全部"
              @change="getOperLogListData"
              size="small"
            >
              <t-option value="" label="全部" />
              <t-option :value="0" label="正常" />
              <t-option :value="1" label="异常" />
            </t-select>
          </t-form-item>
        </t-col>
        <t-col :span="6">
          <t-form-item label="关键字" name="query">
            <t-input-adornment>
              <template #prepend>
                <t-select
                  v-model="queryInfo.queryType"
                  placeholder="请选择"
                  size="small"
                >
                  <t-option label="模块标题" value="title" />
                  <t-option label="触发用户" value="ploNum" />
                </t-select>
              </template>
              <template #append>
                <t-button variant="outline" theme="primary"
                  size="small" @click="getOperLogListData">搜索</t-button>
              </template>
              <t-input v-model="queryInfo.query" placeholder="请输入内容" size="small"></t-input>
            </t-input-adornment>
          </t-form-item>
        </t-col>
        <t-col :span="3" class="operation-container">
          <t-button variant="outline" theme="default" size="small" @click="handleDownload">
            下载
          </t-button>
        </t-col>
      </t-row>
    </t-form>

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
            <t-tag
              :theme="scope.row.status === 1 ? 'danger' : 'success'"
              variant="light"
              size="small"
            >
              {{ scope.row.status === 1 ? '异常' : '正常' }}
            </t-tag>
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
import { operLogApi } from '@/api/admin/operLog'
import { useCrudPage } from '@/hooks/useCrudPage'

// 列表 + 分页（useCrudPage 样板）
const {
  list: tableData,
  total,
  query: queryInfo,
  currentPage,
  pageSizes,
  handleCurrentChange,
  handleSizeChange,
  load: getOperLogListData
} = useCrudPage({
  fetchList: (q) => operLogApi.getOperLogList(q),
  defaultQuery: {
    orderType: ' desc',
    order: ' id ',
    status: 0,
    query: '',
    queryType: 'ploNum',
    begDate: '',
    endDate: '',
    pageSize: 20,
    pageNum: 1
  },
  pageSizes: [20, 100, 500]
})

const dataRange = ref([])

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


// 处理当前页改变


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
.management-card {
  height: calc(100vh - 190px);
  overflow: auto;
}
</style>
