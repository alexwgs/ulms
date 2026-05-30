<template>
  <el-card class="box-card">
    <el-row :gutter="15">
      <el-col :span="18">
        <el-date-picker v-model="dateDuration" @update:model-value="dateChangeEvent" type="daterange" size="small"
          value-format="YYYY-MM-DD" range-separator="至" start-placeholder="开始日期"
          end-placeholder="结束日期"></el-date-picker>
      </el-col>
      <el-col :span="6" class="text-right">
        <el-button type="primary" size="small" @click="caseApi.downloadCaseDirReport(queryInfo)">
          下载<i class="el-icon-download el-icon--right"></i>
        </el-button>
      </el-col>
    </el-row>
    <el-table :data="caseTableQuery" size="small" sortable stripe height="calc(100vh - 270px)" v-loading="loading">
      <el-table-column prop="deptName" sortable label="科室"></el-table-column>
      <el-table-column prop="groupName" sortable label="组别"></el-table-column>
      <el-table-column prop="userId" sortable label="工号"></el-table-column>
      <el-table-column prop="userName" sortable label="姓名"></el-table-column>
      <el-table-column prop="avaliableTime" sortable label="可接单时长(h)"></el-table-column>
      <el-table-column prop="onCallTime" sortable label="通话中时长(h)"></el-table-column>
      <el-table-column prop="leaveTime" sortable label="离开时长(h)"></el-table-column>
      <el-table-column prop="type1Level1" sortable label="业务放行(1级)"></el-table-column>
      <el-table-column prop="type1Level2" sortable label="业务放行(2级)"></el-table-column>
      <el-table-column prop="type1Level3" sortable label="业务放行(3级)"></el-table-column>
      <el-table-column prop="type1ProcessTime" sortable label="业务放行时长(s)"></el-table-column>
      <el-table-column prop="type2Level1" sortable label="投诉案件(1级)"></el-table-column>
      <el-table-column prop="type2Level2" sortable label="投诉案件(2级)"></el-table-column>
      <el-table-column prop="type2Level3" sortable label="投诉案件(3级)"></el-table-column>
      <el-table-column prop="type2ProcessTime" sortable label="投诉处理时长(s)"></el-table-column>
    </el-table>
  </el-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { caseApi } from '@/api/oht/case'

// 响应式数据
const loading = ref(false)
const caseTableQuery = ref([])
const dateDuration = ref(null)

// 查询参数
const queryInfo = reactive({
  dates: '',
  querytype: '',
  query: '',
  pageSize: 20,
  pageNum: 1
})

// 初始化
onMounted(() => {
  getCaseList()
})

// 获取案件列表
const getCaseList = async () => {
  try {
    loading.value = true
    const res = await caseApi.getCaseDirList(queryInfo)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    caseTableQuery.value = res.data
  } catch (error) {
    console.error('获取业务主任接单报表失败:', error)
    ElMessage.error('获取业务主任接单报表失败')
  } finally {
    loading.value = false
  }
}

// 日期范围变化事件
const dateChangeEvent = () => {
  if (dateDuration.value && dateDuration.value.length === 2) {
    queryInfo.dates = dateDuration.value.join(',')
  } else {
    queryInfo.dates = ''
  }
  getCaseList()
}
</script>

<style lang="less" scoped>
.box-card {
  height: calc(100vh - 190px);
  overflow: auto;
}

.text-right {
  display: flex;
  justify-content: flex-end;
}
</style>
