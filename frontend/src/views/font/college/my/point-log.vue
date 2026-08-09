<template>
  <div>
    <t-card class="academy-card">
      <h3 class="academy-section-title">金币日志</h3>
      <t-row :gutter="15">
        <t-col :span="2">
          <t-tag theme="default" variant="light">当前金币: {{ currentCoin }}</t-tag>
        </t-col>
        <t-col :span="10">
          <t-date-range-picker v-model="dateRange" :placeholder="['开始日期', '结束日期']" @change="getStudyLog" size="small" />
        </t-col>
      </t-row>
      <t-alert
        title="提示"
        theme="info"
        message="一门课程需按照课程需求通过评价（如需），考试（如需），考试需要通过方可完整完成课程学习，才可增加金币。学时累计不限制考试通过与否。"
        show-icon
        :closable="false"
      />
      <CustomTable rowKey="id"
        :data="records"
        size="small"
        height="calc(100vh - 330px)"
        stripe
        style="width: 100%"
        @sort-change="tableSort">
        <TableColumn
          prop="studyDate"
          label="积分时间"
          sortable="custom"
          width="100px"
          ellipsis
        />
        <TableColumn colKey="course.courseName" label="课程名称" ellipsis />
        <TableColumn colKey="coin" label="说明" sortable="custom">
          <template #default="{ row }">
            完成《{{ row.course.courseName }}》课程学习，学习金币 + {{ row.coin }}
          </template>
        </TableColumn>
      </CustomTable>
      <t-pagination
        v-model="queryInfo.pageNum"
        v-model:page-size="queryInfo.pageSize"
        :page-size-options="[20, 40, 100, 200]"

        :total="total"
        @page-size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </t-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
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
    MessagePlugin.error(res.msg)
    return
  }
  records.value = res.data.list
  total.value = res.data.total
}

// 表格排序
const tableSort = (data) => {
  if (!data.descending) {
    queryInfo.orderType = ' asc '
  } else if (data.descending) {
    queryInfo.orderType = ' desc '
  }
  queryInfo.order = data.sortBy
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
