<template>
  <el-dialog
    title="主任状态RT"
    v-model="dialogVisible"
    width="80%"
    :before-close="handleClose"
  >
    <el-form :inline="true" :model="queryForm" class="demo-form-inline">
      <el-form-item label="日期">
        <el-date-picker
          v-model="queryForm.date"
          type="date"
          placeholder="选择日期"
          value-format="YYYY-MM-DD"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="tableData" border style="width: 100%; margin-top: 20px">
      <el-table-column
        prop="empName"
        label="员工姓名"
        width="120"
      ></el-table-column>
      <el-table-column prop="status" label="状态" width="150">
        <template #default="scope">
          <el-tag :type="getStatusType(scope.row.status)">
            {{ getStatusText(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="startTime"
        label="开始时间"
        width="180"
      ></el-table-column>
      <el-table-column
        prop="endTime"
        label="结束时间"
        width="180"
      ></el-table-column>
      <el-table-column prop="duration" label="时长(分钟)"></el-table-column>
    </el-table>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleClose">关闭</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, reactive, watch, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
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
    ElMessage.warning('请选择日期')
    return
  }
  getStatusJourList(queryForm.date)
    .then((res) => {
      if (res.code !== 200) {
        ElMessage.error(res.msg)
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
    4: 'info'
  }
  return typeMap[status] || 'info'
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
