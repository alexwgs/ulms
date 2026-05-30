<template>
  <div>
    <el-card class="box-card">
      <el-row :gutter="15">
        <el-col :span="4">
          <el-tag type="info">学习累计时长: {{ currentHour }}（H）</el-tag>
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
        description="一门课程需按照课程需求通过评价（如需），考试（如需），考试需要通过方可完整完成课程学习，才可增加学分。学时不限制考试通过与否。"
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
        <el-table-column prop="studyDate" label="课程时长" sortable="custom" width="100px" show-overflow-tooltip />
        <el-table-column prop="course.courseName" label="课程名称" show-overflow-tooltip />
        <el-table-column prop="coin" label="说明" sortable="custom">
          <template #default="scope">
            完成《{{ scope.row.course.courseName }}》课程学习，学习时长 + {{ scope.row.hours }}
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
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { httpInstance } from '@/utils/request'

const records = ref([])
const queryInfo = ref({
  orderType: ' desc',
  order: ' compDate',
  dateRange: '',
  pageSize: 20,
  pageNum: 1
})
const dateRange = ref([])
const currentHour = ref(JSON.parse(window.localStorage.getItem('point'))?.hours || 0)
const total = ref(0)

const getStudyLog = async () => {
  if (dateRange.value == null) queryInfo.value.dateRange = ''
  else queryInfo.value.dateRange = dateRange.value.join(',')
  const res = await httpInstance({
    url: 'college/study/my/hour',
    method: 'get',
    params: queryInfo.value
  })
  if (res.code !== 200) return ElMessage.error(res.msg)
  records.value = res.data.list
  total.value = res.data.total
}

const tableSort = (data) => {
  if (data.order === 'ascending') queryInfo.value.orderType = ' asc '
  else if (data.order === 'descending') queryInfo.value.orderType = ' desc '
  queryInfo.value.order = data.prop
  getStudyLog()
}

const handleSizeChange = (pageSize) => {
  queryInfo.value.pageSize = pageSize
  getStudyLog()
}

const handleCurrentChange = (page) => {
  queryInfo.value.pageNum = page
  getStudyLog()
}

onMounted(() => {
  getStudyLog()
})
</script>

<style lang="less" scoped>
</style>
