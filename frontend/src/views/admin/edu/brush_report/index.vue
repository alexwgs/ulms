<template>
  <div>
    <el-card class="main-container">
      <el-row style="padding-bottom: 10px" :gutter="15">
        <el-col :span="10">
          <el-radio-group
            v-model="dataType"
            size="small"
            @change="listBrushConfig"
          >
            <el-radio-button label="BY 个人" :value="0"></el-radio-button>
            <el-radio-button label="BY 小组" :value="1"></el-radio-button>
            <el-radio-button label="BY 科室" :value="2"></el-radio-button>
            <el-radio-button label="BY 业务类型" :value="3"></el-radio-button>
          </el-radio-group>
        </el-col>
        <el-col :span="11">
          <el-date-picker
            size="small"
            v-model="dateRange"
            type="daterange"
            value-format="YYYY-MM-DD"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            @change="listBrushConfig"
          ></el-date-picker>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" size="small" @click="downloadReport"
            >下载</el-button
          >
        </el-col>
      </el-row>
      <el-table
        :data="tableData"
        size="small"
        stripe
        style="width: 100%"
        height="calc(100vh - 300px)"
        @sort-change="tableSort"
      >
        <el-table-column
          v-if="dataType !== 3"
          prop="deptNum"
          label="科室"
          width="120"
        ></el-table-column>
        <el-table-column
          v-if="dataType == 0 || dataType == 1"
          prop="deptGroup"
          label="组别"
          width="100"
        ></el-table-column>
        <el-table-column
          v-if="dataType === 0"
          prop="ploName"
          label="员工姓名"
          width="120"
        ></el-table-column>
        <el-table-column
          v-if="dataType === 0"
          prop="answerDays"
          label="天数"
          sortable="custom"
          width="80"
        ></el-table-column>
        <el-table-column
          v-if="dataType == 3"
          prop="category"
          label="业务类型"
        ></el-table-column>
        <el-table-column
          prop="total"
          label="答题量"
          sortable="custom"
          width="100"
        ></el-table-column>
        <el-table-column
          prop="rightNum"
          label="正确量"
          sortable="custom"
          width="100"
        ></el-table-column>
        <el-table-column
          prop="rightRate"
          label="正确率"
          sortable="custom"
          width="100"
        >
          <template #default="scope"
            >{{ (scope.row.rightRate * 100).toFixed(2) }}%</template
          >
        </el-table-column>
        <el-table-column
          v-if="dataType !== 3"
          prop="weakPoint1"
          label="薄弱业务一"
          sortable="custom"
        ></el-table-column>
        <el-table-column
          v-if="dataType !== 3"
          prop="weakPoint2"
          label="薄弱业务二"
          sortable="custom"
        ></el-table-column>
        <el-table-column
          v-if="dataType !== 3"
          prop="weakPoint3"
          label="薄弱业务三"
          sortable="custom"
        ></el-table-column>
      </el-table>
    </el-card>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
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

    if (res.code !== 200) return ElMessage.error(res.msg)
    tableData.value = res.data
  } catch (error) {
    ElMessage.error('获取数据失败')
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
  if (data.order === 'ascending') {
    orderBy = data.prop + ' asc'
  } else if (data.order === 'descending') {
    orderBy = data.prop + ' desc'
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
