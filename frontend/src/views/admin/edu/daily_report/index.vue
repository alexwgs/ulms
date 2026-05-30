<template>
  <div style="height: 100%">
    <el-card class="box-card">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-radio-group
            v-model="queryInfo.groupBy"
            size="small"
            @change="getDailyScoreListData"
          >
            <el-radio-button value="hum" label="个人"></el-radio-button>
            <el-radio-button value="group" label="小组"></el-radio-button>
            <el-radio-button value="dept" label="科室"></el-radio-button>
          </el-radio-group>
        </el-col>
        <el-col :span="8">
          <el-date-picker
            v-model="daterange"
            size="small"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            @change="getDailyScoreListData"
            style="width: 100%"
          ></el-date-picker>
        </el-col>
        <el-col :span="8">
          <el-input
            placeholder="模糊搜索"
            size="small"
            v-model="queryInfo.query"
          >
            <el-select
              v-model="queryInfo.queryType"
              slot="prepend"
              placeholder="请选择"
              style="width: 80px"
            >
              <el-option
                v-if="queryInfo.groupBy === 'hum'"
                label="姓名"
                value="ploName"
              ></el-option>
              <el-option
                v-if="queryInfo.groupBy === 'hum'"
                label="工号"
                value="ploNum"
              ></el-option>
              <el-option label="组别" value="group"></el-option>
              <el-option label="科室" value="dept"></el-option>
            </el-select>
            <el-button
              slot="append"
              icon="Search"
              @click="getDailyScoreListData"
            ></el-button>
          </el-input>
        </el-col>
        <el-col :span="2">
          <el-button type="primary" size="small" @click="downloadReport">
            下载
          </el-button>
        </el-col>
      </el-row>
      <el-alert
        title="操作说明"
        type="info"
        description="请正确使用字典配置：1.默认显示7天数据。2.统计天数：配置每日一学的天数；打卡天数：完成每日一学的天数；签到天数：当天完成每日一学天数；"
        :closable="false"
      >
      </el-alert>
      <el-table
        :data="dailyScore"
        size="small"
        height="calc(100vh - 385px)"
        stripe
        @sort-change="tableSort"
        style="width: 100%"
      >
        <el-table-column
          prop="deptNum"
          label="科室"
          sortable="custom"
          width="110px"
        ></el-table-column>
        <el-table-column
          v-if="queryInfo.groupBy !== 'dept'"
          prop="deptGroup"
          label="组别"
          sortable="custom"
          width="110px"
        ></el-table-column>
        <el-table-column
          v-if="queryInfo.groupBy === 'hum'"
          prop="ploNum"
          label="员编"
          sortable="custom"
          width="80px"
        ></el-table-column>
        <el-table-column
          v-if="queryInfo.groupBy === 'hum'"
          prop="ploName"
          label="姓名"
          sortable="custom"
          width="100px"
        ></el-table-column>
        <el-table-column
          prop="totalDay"
          label="统计天数"
          width="100px"
        ></el-table-column>
        <el-table-column
          prop="punchDays"
          label="打卡天数"
          sortable="custom"
          width="100px"
        ></el-table-column>
        <el-table-column prop="punchRate" label="打卡率" width="100px">
          <template #default="scope">
            {{ (scope.row.punchRate * 100).toFixed(2) }}%
          </template>
        </el-table-column>
        <el-table-column
          prop="signDays"
          label="签到天数"
          sortable="custom"
          width="100px"
        ></el-table-column>
        <el-table-column
          prop="firstRightNum"
          label="首对次数"
          sortable="custom"
          width="100px"
        ></el-table-column>
        <el-table-column
          prop="firstRightRate"
          label="首对率"
          sortable="custom"
          width="100px"
        >
          <template #default="scope">
            {{ (scope.row.firstRightRate * 100).toFixed(2) }}%
          </template>
        </el-table-column>
        <el-table-column
          prop="studyNum"
          label="学习次数"
          sortable="custom"
          width="100px"
        ></el-table-column>
        <el-table-column
          prop="studyTime"
          label="学习时长"
          sortable="custom"
          width="100px"
        ></el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pageNum"
        :page-sizes="pageSizes"
        :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
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
    if (res.code !== 200) return ElMessage.error(res.msg)
    dailyScore.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    ElMessage.error('获取数据失败，请重试')
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
  if (data.order === 'ascending') queryInfo.orderType = ' asc '
  else if (data.order === 'descending') queryInfo.orderType = ' desc '
  queryInfo.order = data.prop
  getDailyScoreListData()
}

const downloadReport = () => {
  queryInfo.begDate = daterange.value[0]
  queryInfo.endDate = daterange.value[1]

  dailyReportApi.downloadExcel(queryInfo)
}
</script>

<style lang="less" scoped></style>
