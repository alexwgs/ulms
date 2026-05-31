<template>
  <div style="height: 100%">
    <t-card class="box-card">
      <t-row :gutter="20">
        <t-col :span="3">
          <t-radio-group
            v-model="queryInfo.groupBy"
            size="small"
            @change="getDailyScoreListData"
          >
            <t-radio-button value="hum" label="个人"></t-radio-button>
            <t-radio-button value="group" label="小组"></t-radio-button>
            <t-radio-button value="dept" label="科室"></t-radio-button>
          </t-radio-group>
        </t-col>
        <t-col :span="4">
          <t-date-range-picker v-model="daterange" size="small" :placeholder="['开始日期', '结束日期']" @change="getDailyScoreListData" style="width: 100%" ></t-date-range-picker>
        </t-col>
        <t-col :span="4">
          <t-input
            placeholder="模糊搜索"
            size="small"
            v-model="queryInfo.query"
          >
            <t-select
              v-model="queryInfo.queryType"
              slot="prepend"
              placeholder="请选择"
              style="width: 80px"
            >
              <t-option
                v-if="queryInfo.groupBy === 'hum'"
                label="姓名"
                value="ploName"
              ></t-option>
              <t-option
                v-if="queryInfo.groupBy === 'hum'"
                label="工号"
                value="ploNum"
              ></t-option>
              <t-option label="组别" value="group"></t-option>
              <t-option label="科室" value="dept"></t-option>
            </t-select>
            <t-button
              slot="append" @click="getDailyScoreListData"><template #icon><DynamicIcon name="search" /></template></t-button>
          </t-input>
        </t-col>
        <t-col :span="1">
          <t-button theme="primary" size="small" @click="downloadReport">
            下载
          </t-button>
        </t-col>
      </t-row>
      <t-alert
        title="操作说明"
        theme="info"
        message="请正确使用字典配置：1.默认显示7天数据。2.统计天数：配置每日一学的天数；打卡天数：完成每日一学的天数；签到天数：当天完成每日一学天数；"
        :closable="false"
      >
      </t-alert>
      <CustomTable rowKey="id"
        :data="dailyScore"
        size="small"
        height="calc(100vh - 385px)"
        stripe
        @sort-change="tableSort"
        style="width: 100%">
        <TableColumn
          prop="deptNum"
          label="科室"
          sortable="custom"
          width="110px"></TableColumn>
        <TableColumn
          v-if="queryInfo.groupBy !== 'dept'"
          prop="deptGroup"
          label="组别"
          sortable="custom"
          width="110px"></TableColumn>
        <TableColumn
          v-if="queryInfo.groupBy === 'hum'"
          prop="ploNum"
          label="员编"
          sortable="custom"
          width="80px"></TableColumn>
        <TableColumn
          v-if="queryInfo.groupBy === 'hum'"
          prop="ploName"
          label="姓名"
          sortable="custom"
          width="100px"></TableColumn>
        <TableColumn
          prop="totalDay"
          label="统计天数"
          width="100px"></TableColumn>
        <TableColumn
          prop="punchDays"
          label="打卡天数"
          sortable="custom"
          width="100px"></TableColumn>
        <TableColumn colKey="punchRate" label="打卡率" width="100px">
          <template #default="scope">
            {{ (scope.row.punchRate * 100).toFixed(2) }}%
          </template>
        </TableColumn>
        <TableColumn
          prop="signDays"
          label="签到天数"
          sortable="custom"
          width="100px"></TableColumn>
        <TableColumn
          prop="firstRightNum"
          label="首对次数"
          sortable="custom"
          width="100px"></TableColumn>
        <TableColumn
          prop="firstRightRate"
          label="首对率"
          sortable="custom"
          width="100px">
          <template #default="scope">
            {{ (scope.row.firstRightRate * 100).toFixed(2) }}%
          </template>
        </TableColumn>
        <TableColumn
          prop="studyNum"
          label="学习次数"
          sortable="custom"
          width="100px"></TableColumn>
        <TableColumn
          prop="studyTime"
          label="学习时长"
          sortable="custom"
          width="100px"></TableColumn>
      </CustomTable>
      <t-pagination
        @page-size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current="queryInfo.pageNum"
        :page-size-options="pageSizes"
        :page-size="queryInfo.pageSize"

        :total="total"
      ></t-pagination>
    </t-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { dailyReportApi } from '@/api/edu/dailyReport'
import dayjs from 'dayjs'

// Reactive data
const queryInfo = reactive({
  begDate: '',
  endDate: '',
  groupBy: 'hum',
  orderType: ' desc',
  order: ' firstRightRate ',
  queryType: 'ploName',
  query: '',
  pageSize: 20,
  pageNum: 1
})

const daterange = ref([
  dayjs().subtract(7, 'days').format('YYYY-MM-DD'),
  dayjs().format('YYYY-MM-DD')
])

const dailyScore = ref([])
const pageSizes = [20, 100, 500]
const total = ref(0)

// Lifecycle
onMounted(() => {
  getDailyScoreListData()
})

// Methods
const getDailyScoreListData = async () => {
  queryInfo.begDate = daterange.value[0]
  queryInfo.endDate = daterange.value[1]

  try {
    const res = await dailyReportApi.getDailyScoreList(queryInfo)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    dailyScore.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    MessagePlugin.error('获取数据失败，请重试')
  }
}

const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  getDailyScoreListData()
}

const handleSizeChange = (pageSize) => {
  queryInfo.pageSize = pageSize
  getDailyScoreListData()
}

const tableSort = (data) => {
  if (!data.descending) queryInfo.orderType = ' asc '
  else if (data.descending) queryInfo.orderType = ' desc '
  queryInfo.order = data.sortBy
  getDailyScoreListData()
}

const downloadReport = () => {
  queryInfo.begDate = daterange.value[0]
  queryInfo.endDate = daterange.value[1]

  dailyReportApi.downloadExcel(queryInfo)
}
</script>

<style lang="less" scoped></style>
