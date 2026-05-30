<template>
  <el-dialog
    title="考勤记录"
    v-model="dialogVisible"
    width="80%"
    :before-close="handleClose"
  >
    <el-form :inline="true" :model="queryForm" class="demo-form-inline">
      <el-form-item label="日期">
        <el-date-picker
          v-model="queryForm.date"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="YYYY-MM-DD"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">查询</el-button>
        <el-button @click="handleReset">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="tableData" border style="width: 100%; margin-top: 20px">
      <el-table-column
        prop="punchInTime"
        label="上班时间"
        width="180"
      ></el-table-column>
      <el-table-column
        prop="punchOutTime"
        label="下班时间"
        width="180"
      ></el-table-column>
      <el-table-column
        prop="duration"
        label="时长"
        width="120"
      ></el-table-column>
      <el-table-column
        prop="workDuration"
        label="工作时长"
        width="120"
      ></el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template #default="scope">
          <el-tag v-if="scope.row.status === 1" type="success">正常</el-tag>
          <el-tag v-else-if="scope.row.status === 2" type="warning"
            >迟到</el-tag
          >
          <el-tag v-else-if="scope.row.status === 3" type="danger">早退</el-tag>
          <el-tag v-else type="info">缺卡</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="remark" label="备注"></el-table-column>
    </el-table>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleClose">关闭</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'

const dialogVisible = ref(false)
const tableData = ref([])
const queryForm = reactive({
  date: []
})

const show = () => {
  dialogVisible.value = true
  handleQuery()
}

const handleClose = () => {
  dialogVisible.value = false
}

const handleQuery = () => {
  if (!queryForm.date || queryForm.date.length < 2) {
    ElMessage.warning('请选择日期范围')
    return
  }
  const params = {
    startDate: queryForm.date[0],
    endDate: queryForm.date[1]
  }
  getPunchJourList(params)
}

const handleReset = () => {
  queryForm.date = []
}

const getPunchJourList = async (params) => {
  try {
    const res = await fetch(
      `/api/punch/jour/list?startDate=${params.startDate}&endDate=${params.endDate}`
    )
    const data = await res.json()
    if (data.code !== 200) {
      ElMessage.error(data.msg)
      return
    }
    tableData.value = data.data || []
  } catch (error) {
    console.error('获取考勤记录失败', error)
    ElMessage.error('获取考勤记录失败')
  }
}

onMounted(() => {
  const end = new Date()
  const start = new Date()
  start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
  queryForm.date = [
    start.toISOString().slice(0, 10),
    end.toISOString().slice(0, 10)
  ]
})

defineExpose({
  show
})
</script>
