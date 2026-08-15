<template>
  <t-dialog
    header="模板管理"
    v-model:visible="dialogVisible"
    width="60%"
    style="height: 60%"
    :close-on-overlay-click="false"
    :before-close="handleClose"
  >
    <t-alert
      title="重要！！当控件序号设置为小于0时，前端将不显示该字段!"
      center
      :closable="false"
      theme="error"
      class="alert-message"
    />

    <t-button
      theme="primary"
      size="small"
      @click="openFieldDialog('add')"
      class="mb-3"
    >
      新建字段
    </t-button>

    <CustomTable rowKey="id" :data="list" size="small" :loading="loading">
      <TableColumn colKey="id" label="ID" width="100" ellipsis />
      <TableColumn colKey="label" label="显示名称" width="130" />
      <TableColumn colKey="fieldName" label="参数名称" width="130" />
      <TableColumn colKey="type" label="控件类型" width="100">
        <template #default="{ row }">
          {{ getComponentTypeLabel(row.type) }}
        </template>
      </TableColumn>
      <TableColumn colKey="options" label="选项" ellipsis />
      <TableColumn colKey="required" label="必填" width="100">
        <template #default="{ row }">
          {{ row.required ? '是' : '否' }}
        </template>
      </TableColumn>
      <TableColumn colKey="orderId" label="序号" width="80">
        <template #default="{ row }">
          {{ row.orderId < 0 ? `${row.orderId}（不显示）` : row.orderId }}
        </template>
      </TableColumn>
      <TableColumn label="操作" width="120" fixed="right">
        <template #default="{ row }">
          <t-button variant="outline"
            theme="default"
            size="small" @click="openFieldDialog('update', row)"
           >编辑</t-button>
          <t-button variant="outline"
            theme="danger"
            size="small" @click="deleteField(row.id)"
           >删除</t-button>
        </template>
      </TableColumn>
    </CustomTable>

    <template #footer>
      <t-button size="small" theme="primary" @click="submitForm"
        >确定</t-button
      >
    </template>

    <FieldDialog ref="fieldDialogRef" @refresh="fetchFieldList" />
  </t-dialog>
</template>

<script setup>
import { ref } from 'vue'
import FieldDialog from './FieldDialog.vue'
import {
  getRpaToolTemplete,
  deteteRpaToolTemplete,
  updateRpaTool
} from '@/api/rpa/rpa.js'
import { MessagePlugin } from 'tdesign-vue-next'
import { useConfirm } from '@/hooks/useConfirm'

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
    MessagePlugin.error('获取字段列表失败')
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
  const { confirm: confirmDialog } = useConfirm()
  const ok = await confirmDialog('此操作将永久删除该字段, 是否继续?', {
    title: '提示'
  })
  if (!ok) return

  try {
    await deteteRpaToolTemplete(id)
    MessagePlugin.success('删除成功')
    fetchFieldList()
  } catch (error) {
    MessagePlugin.error('删除失败')
  }
}

// 提交表单
const submitForm = async () => {
  try {
    // 直接关闭对话框，不需要额外的API调用
    emit('refresh')
    dialogVisible.value = false
  } catch (error) {
    MessagePlugin.error('操作失败')
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
