<template>
  <t-dialog
    header="考勤记录"
    v-model:visible="dialogVisible"
    width="80%"
    @before-close="handleClose"
  >
    <t-form layout="inline" :data="queryForm" class="demo-form-inline">
      <t-form-item label="日期">
        <t-date-range-picker v-model="queryForm.date" :placeholder="['开始日期', '结束日期']" ></t-date-range-picker>
      </t-form-item>
      <t-form-item>
        <t-button theme="primary" @click="handleQuery">查询</t-button>
        <t-button @click="handleReset">重置</t-button>
      </t-form-item>
    </t-form>
    <CustomTable rowKey="id" :data="tableData" border style="width: 100%; margin-top: 20px">
      <TableColumn
        prop="punchInTime"
        label="上班时间"
        width="180"></TableColumn>
      <TableColumn
        prop="punchOutTime"
        label="下班时间"
        width="180"></TableColumn>
      <TableColumn
        prop="duration"
        label="时长"
        width="120"></TableColumn>
      <TableColumn
        prop="workDuration"
        label="工作时长"
        width="120"></TableColumn>
      <TableColumn colKey="status" label="状态" width="100">
        <template #default="scope">
          <t-tag v-if="scope.row.status === 1" theme="success">正常</t-tag>
          <t-tag v-else-if="scope.row.status === 2" theme="warning"
            >迟到</t-tag
          >
          <t-tag v-else-if="scope.row.status === 3" theme="danger">早退</t-tag>
          <t-tag v-else theme="default">缺卡</t-tag>
        </template>
      </TableColumn>
      <TableColumn colKey="remark" label="备注"></TableColumn>
    </CustomTable>
    <template #footer>
      <span class="dialog-footer">
        <t-button @click="handleClose">关闭</t-button>
      </span>
    </template>
  </t-dialog>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'

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
    MessagePlugin.warning('请选择日期范围')
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
      MessagePlugin.error(data.msg)
      return
    }
    tableData.value = data.data || []
  } catch (error) {
    console.error('获取考勤记录失败', error)
    MessagePlugin.error('获取考勤记录失败')
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
