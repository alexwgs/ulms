<template>
  <t-dialog
    header="主任状态RT"
    v-model:visible="dialogVisible"
    width="80%"
    :before-close="handleClose"
  >
    <t-form layout="inline" :data="queryForm" class="demo-form-inline">
      <t-form-item label="日期">
        <t-date-picker
          v-model="queryForm.date"
          mode="date"
          placeholder="选择日期"
         
        ></t-date-picker>
      </t-form-item>
      <t-form-item>
        <t-button theme="primary" @click="handleQuery">查询</t-button>
      </t-form-item>
    </t-form>
    <CustomTable rowKey="id" :data="tableData" border style="width: 100%; margin-top: 20px">
      <TableColumn
        prop="empName"
        label="员工姓名"
        width="120"></TableColumn>
      <TableColumn colKey="status" label="状态" width="150">
        <template #default="scope">
          <t-tag :theme="getStatusType(scope.row.status)" variant="light">
            {{ getStatusText(scope.row.status) }}
          </t-tag>
        </template>
      </TableColumn>
      <TableColumn
        prop="startTime"
        label="开始时间"
        width="180"></TableColumn>
      <TableColumn
        prop="endTime"
        label="结束时间"
        width="180"></TableColumn>
      <TableColumn colKey="duration" label="时长(分钟)"></TableColumn>
    </CustomTable>
    <template #footer>
      <span class="dialog-footer">
        <t-button @click="handleClose">关闭</t-button>
      </span>
    </template>
  </t-dialog>
</template>

<script setup>
import { ref, reactive, watch, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { getStatusJourList } from '@/api/welcome/index.js'

const props = defineProps({
  visiable: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['update:visiable'])

const dialogVisible = ref(false)
const tableData = ref([])
const queryForm = reactive({
  date: new Date().toISOString().slice(0, 10)
})

watch(
  () => props.visiable,
  (newVal) => {
    dialogVisible.value = newVal
    if (newVal) {
      handleQuery()
    }
  }
)

watch(dialogVisible, (newVal) => {
  emit('update:visiable', newVal)
})

const handleClose = () => {
  dialogVisible.value = false
}

const handleQuery = () => {
  if (!queryForm.date) {
    MessagePlugin.warning('请选择日期')
    return
  }
  getStatusJourList(queryForm.date)
    .then((res) => {
      if (res.code !== 200) {
        MessagePlugin.error(res.msg)
        return
      }
      tableData.value = res.data || []
    })
    .catch((err) => {
      console.error('获取状态记录失败', err)
    })
}

const getStatusType = (status) => {
  const typeMap = {
    1: 'success',
    2: 'warning',
    3: 'danger',
    4: 'default'
  }
  return typeMap[status] || 'default'
}

const getStatusText = (status) => {
  const textMap = {
    1: '在岗',
    2: '请假',
    3: '休息',
    4: '其他'
  }
  return textMap[status] || '未知'
}

onMounted(() => {
  if (props.visiable) {
    dialogVisible.value = true
    handleQuery()
  }
})
</script>
