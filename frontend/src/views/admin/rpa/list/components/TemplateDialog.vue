<template>
  <el-dialog
    title="模板管理"
    v-model="dialogVisible"
    width="60%"
    style="height: 60%"
    :close-on-click-modal="false"
    :before-close="handleClose"
    :fullscreen="false"
  >
    <el-alert
      title="重要！！当控件序号设置为小于0时，前端将不显示该字段!"
      center
      :closable="false"
      type="error"
      class="alert-message"
    />

    <el-button
      type="primary"
      size="small"
      @click="openFieldDialog('add')"
      class="mb-3"
    >
      新建字段
    </el-button>

    <el-table :data="list" size="small" v-loading="loading">
      <el-table-column prop="id" label="ID" width="100" show-overflow-tooltip />
      <el-table-column prop="label" label="显示名称" width="130" />
      <el-table-column prop="fieldName" label="参数名称" width="130" />
      <el-table-column prop="type" label="控件类型" width="100">
        <template #default="{ row }">
          {{ getComponentTypeLabel(row.type) }}
        </template>
      </el-table-column>
      <el-table-column prop="options" label="选项" show-overflow-tooltip />
      <el-table-column prop="required" label="必填" width="100">
        <template #default="{ row }">
          {{ row.required ? '是' : '否' }}
        </template>
      </el-table-column>
      <el-table-column prop="orderId" label="序号" width="80">
        <template #default="{ row }">
          {{ row.orderId < 0 ? `${row.orderId}（不显示）` : row.orderId }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="120" fixed="right">
        <template #default="{ row }">
          <el-button
            type="warning"
            size="small"
            icon="Edit"
            @click="openFieldDialog('update', row)"
            circle
          />
          <el-button
            type="danger"
            size="small"
            icon="Delete"
            @click="deleteField(row.id)"
            circle
          />
        </template>
      </el-table-column>
    </el-table>

    <template #footer>
      <el-button size="small" type="primary" @click="submitForm"
        >确定</el-button
      >
    </template>

    <FieldDialog ref="fieldDialogRef" @refresh="fetchFieldList" />
  </el-dialog>
</template>

<script setup>
import { ref } from 'vue'
import FieldDialog from './FieldDialog.vue'
import {
  getRpaToolTemplete,
  deteteRpaToolTemplete,
  updateRpaTool
} from '@/api/rpa/rpa.js'
import { ElMessage, ElMessageBox } from 'element-plus'

const emit = defineEmits(['refresh'])

const list = ref([])
const fieldDialogRef = ref(null)

// 对话框状态
const dialogVisible = ref(false)
const loading = ref(false)
const currentTemplate = ref({})

// 组件类型选项
const componentTypes = [
  { key: 'input', value: '文本/数字' },
  { key: 'select', value: '选择控件' },
  { key: 'dateTime', value: '日期选择器' },
  { key: 'dateRange', value: '日期区间选择' },
  { key: 'userSelect', value: '人员控件' },
  { key: 'orgSelect', value: '架构控件' },
  { key: 'batchData', value: '批量ID' },
  { key: 'userInfo', value: '使用人信息' }
]

// 打开对话框
const openDialog = (template) => {
  currentTemplate.value = template
  dialogVisible.value = true
  fetchFieldList()
}

// 获取组件类型标签
const getComponentTypeLabel = (type) => {
  const found = componentTypes.find((item) => item.key === type)
  return found ? found.value : '未定义'
}

// 获取字段列表
const fetchFieldList = async () => {
  try {
    loading.value = true
    const res = await getRpaToolTemplete(currentTemplate.value.id)
    list.value = res.data
  } catch (error) {
    ElMessage.error('获取字段列表失败')
  } finally {
    loading.value = false
  }
}

// 打开字段对话框
const openFieldDialog = (type, field = {}) => {
  fieldDialogRef.value.openDialog(type, {
    ...field,
    templateId: currentTemplate.value.id
  })
}

// 删除字段
const deleteField = async (id) => {
  try {
    await ElMessageBox.confirm('此操作将永久删除该字段, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })

    await deteteRpaToolTemplete(id)
    ElMessage.success('删除成功')
    fetchFieldList()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

// 提交表单
const submitForm = async () => {
  try {
    // 直接关闭对话框，不需要额外的API调用
    emit('refresh')
    dialogVisible.value = false
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

// 关闭对话框
const handleClose = () => {
  dialogVisible.value = false
  emit('refresh')
}

// 暴露方法供父组件调用
defineExpose({
  openDialog
})
</script>

<style lang="less" scoped>
.alert-message {
  margin-bottom: 16px;
}

.mb-3 {
  margin-bottom: 12px;
}
</style>
