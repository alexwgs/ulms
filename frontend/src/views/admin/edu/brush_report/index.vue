<template>
  <div>
    <t-card class="main-container">
      <div class="table-toolbar">
        <div class="toolbar-left">
          <t-radio-group
            v-model="dataType"
            size="small"
            @change="listBrushConfig"
          >
            <t-radio-button label="BY 个人" :value="0"></t-radio-button>
            <t-radio-button label="BY 小组" :value="1"></t-radio-button>
            <t-radio-button label="BY 科室" :value="2"></t-radio-button>
            <t-radio-button label="BY 业务类型" :value="3"></t-radio-button>
          </t-radio-group>
        </div>
        <div class="toolbar-right">
          <t-date-range-picker size="small" v-model="dateRange" :placeholder="['开始日期', '结束日期']" @change="listBrushConfig" ></t-date-range-picker>
          <t-button variant="outline" theme="primary" size="small" @click="downloadReport"
            >下载</t-button
          >
        </div>
      </div>
      <CustomTable rowKey="id"
        :data="tableData"
        size="small"
        stripe
        style="width: 100%"
        height="calc(100vh - 300px)"
        @sort-change="tableSort">
        <TableColumn
          v-if="dataType !== 3"
          prop="deptNum"
          label="科室"
          width="120"></TableColumn>
        <TableColumn
          v-if="dataType == 0 || dataType == 1"
          prop="deptGroup"
          label="组别"
          width="100"></TableColumn>
        <TableColumn
          v-if="dataType === 0"
          prop="ploName"
          label="员工姓名"
          width="120"></TableColumn>
        <TableColumn
          v-if="dataType === 0"
          prop="answerDays"
          label="天数"
          sortable="custom"
          width="80"></TableColumn>
        <TableColumn
          v-if="dataType == 3"
          prop="category"
          label="业务类型"></TableColumn>
        <TableColumn
          prop="total"
          label="答题量"
          sortable="custom"
          width="100"></TableColumn>
        <TableColumn
          prop="rightNum"
          label="正确量"
          sortable="custom"
          width="100"></TableColumn>
        <TableColumn
          prop="rightRate"
          label="正确率"
          sortable="custom"
          width="100">
          <template #default="scope"
            >{{ (scope.row.rightRate * 100).toFixed(2) }}%</template
          >
        </TableColumn>
        <TableColumn
          v-if="dataType !== 3"
          prop="weakPoint1"
          label="薄弱业务一"
          sortable="custom"></TableColumn>
        <TableColumn
          v-if="dataType !== 3"
          prop="weakPoint2"
          label="薄弱业务二"
          sortable="custom"></TableColumn>
        <TableColumn
          v-if="dataType !== 3"
          prop="weakPoint3"
          label="薄弱业务三"
          sortable="custom"></TableColumn>
      </CustomTable>
    </t-card>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { brushReportApi } from '@/api/edu/brushReport'

// 响应式数据
const queryInfo = reactive({
  begDate: '',
  endDate: '',
  orderBy: 'right_num desc,right_rate desc,total desc'
})

const tableData = ref([])
const dateRange = ref([])
const dataType = ref(0)

// 生命周期
onMounted(() => {
  // 初始化日期范围为最近7天
  const today = new Date()
  const sevenDaysAgo = new Date(today)
  sevenDaysAgo.setDate(sevenDaysAgo.getDate() - 6)

  const formatDate = (date) => {
    return date.toISOString().split('T')[0]
  }

  dateRange.value = [formatDate(sevenDaysAgo), formatDate(today)]
  listBrushConfig()
})

// 方法
async function listBrushConfig() {
  if (dateRange.value.length > 0) {
    queryInfo.begDate = dateRange.value[0]
    queryInfo.endDate = dateRange.value[1]
  }

  let res
  try {
    switch (dataType.value) {
      case 0:
        res = await brushReportApi.getHumBrushList(queryInfo)
        break
      case 1:
        res = await brushReportApi.getGroupBrushList(queryInfo)
        break
      case 2:
        res = await brushReportApi.getDeptBrushList(queryInfo)
        break
      case 3:
        res = await brushReportApi.getCategoryBrushList(queryInfo)
        break
      default:
        res = await brushReportApi.getHumBrushList(queryInfo)
    }

    if (res.code !== 200) return MessagePlugin.error(res.msg)
    tableData.value = res.data
  } catch (error) {
    MessagePlugin.error('获取数据失败')
  }
}

function downloadReport() {
  switch (dataType.value) {
    case 0:
      brushReportApi.getHumBrushList(queryInfo)
      break
    case 1:
      brushReportApi.getGroupBrushList(queryInfo)
      break
    case 2:
      brushReportApi.getDeptBrushList(queryInfo)
      break
    case 3:
      brushReportApi.getCategoryBrushList(queryInfo)
      break
    default:
      brushReportApi.getHumBrushList(queryInfo)
      break
  }
}

function tableSort(data) {
  let orderBy = ''
  if (!data.descending) {
    orderBy = data.sortBy + ' asc'
  } else if (data.descending) {
    orderBy = data.sortBy + ' desc'
  }
  queryInfo.orderBy = orderBy
  listBrushConfig()
}
</script>
<style lang="less" scoped>
.main-container {
  height: calc(100vh - 185px);
}
.tree-box {
  height: 450px;
  overflow: auto;
}
</style>
