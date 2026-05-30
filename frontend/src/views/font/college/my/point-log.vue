<template>
  <div>
    <el-card class="box-card">
      <el-row :gutter="15">
        <el-col :span="4">
          <el-tag type="info">当前金币: {{ currentCoin }}</el-tag>
        </el-col>
        <el-col :span="20">
          <el-date-picker
            v-model="dateRange"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="YYYY-MM-DD"
            @change="getStudyLog"
            size="small"
          />
        </el-col>
      </el-row>
      <el-alert
        title="提示"
        type="info"
        description="一门课程需按照课程需求通过评价（如需），考试（如需），考试需要通过方可完整完成课程学习，才可增加金币。学时累计不限制考试通过与否。"
        show-icon
        :closable="false"
      />
      <el-table
        :data="records"
        size="small"
        height="calc(100vh - 330px)"
        stripe
        style="width: 100%"
        @sort-change="tableSort"
      >
        <el-table-column
          prop="studyDate"
          label="积分时间"
          sortable="custom"
          width="100px"
          show-overflow-tooltip
        />
        <el-table-column prop="course.courseName" label="课程名称" show-overflow-tooltip />
        <el-table-column prop="coin" label="说明" sortable="custom">
          <template #default="{ row }">
            完成《{{ row.course.courseName }}》课程学习，学习金币 + {{ row.coin }}
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        v-model:current-page="queryInfo.pageNum"
        v-model:page-size="queryInfo.pageSize"
        :page-sizes="[20, 40, 100, 200]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { studyApi } from '@/api/college/study.js'

// 响应式数据
const records = ref([])
const total = ref(0)
const dateRange = ref([])
const currentCoin = ref(0)

// 查询参数
const queryInfo = reactive({
  orderType: ' desc',
  order: ' compDate',
  dateRange: '',
  pageSize: 20,
  pageNum: 1
})

// 获取当前金币
const getCurrentCoin = () => {
  const point = localStorage.getItem('point')
  if (point) {
    try {
      currentCoin.value = JSON.parse(point).coin || 0
    } catch {
      currentCoin.value = 0
    }
  }
}

// 获取学习日志
const getStudyLog = async () => {
  if (dateRange.value == null) {
    queryInfo.dateRange = ''
  } else {
    queryInfo.dateRange = dateRange.value.join(',')
  }

  const res = await studyApi.getMyStudyPoint(queryInfo)
  if (res.code !== 200) {
    ElMessage.error(res.msg)
    return
  }
  records.value = res.data.list
  total.value = res.data.total
}

// 表格排序
const tableSort = (data) => {
  if (data.order === 'ascending') {
    queryInfo.orderType = ' asc '
  } else if (data.order === 'descending') {
    queryInfo.orderType = ' desc '
  }
  queryInfo.order = data.prop
  getStudyLog()
}

// 分页大小变化
const handleSizeChange = (pageSize) => {
  queryInfo.pageSize = pageSize
  getStudyLog()
}

// 页码变化
const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  getStudyLog()
}

// 初始化
onMounted(() => {
  getCurrentCoin()
  getStudyLog()
})
</script>

<style lang="less" scoped></style>
