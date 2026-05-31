<template>
  <t-dialog header="推送明细" v-model:visible="dialogVisible" width="80%">
    <CustomTable rowKey="id"
      :data="pushList"
      stripe
      style="width: 100%"
      size="small">
      <TableColumn colKey="deptNum" label="科室" width="120"></TableColumn>
      <TableColumn colKey="groupNum" label="组别" width="100"></TableColumn>
      <TableColumn colKey="userId" label="用户信息" width="130"></TableColumn>
      <TableColumn colKey="dataTime" label="推送时间" width="160">
        <template #default="{ row }">
          {{ formatDateTime(row.dataTime) }}
        </template>
      </TableColumn>
      <TableColumn colKey="taskLevel" label="级别" width="70"></TableColumn>
      <TableColumn colKey="taskStatus" label="处理结果" width="100">
        <template #default="{ row }">
          {{ getDictLabel('oht_case_task_status', row.taskStatus) }}
        </template>
      </TableColumn>
      <TableColumn colKey="memo" label="备注"></TableColumn>
    </CustomTable>
    <template #footer>
      <span class="dialog-footer">
        <t-button theme="primary" @click="dialogVisible = false">关闭</t-button>
      </span>
    </template>
  </t-dialog>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useDictStore } from '@/stores'

const dictStore = useDictStore()

const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  },
  pushList: {
    type: Array,
    default: () => []
  }
})

const emit = defineEmits(['update:visible'])

const dialogVisible = ref(props.visible)

// 监听外部visible变化
watch(() => props.visible, (newVal) => {
  dialogVisible.value = newVal
})

// 监听内部dialogVisible变化
watch(dialogVisible, (newVal) => {
  emit('update:visible', newVal)
})

// 格式化日期时间
const formatDateTime = (timestamp) => {
  if (!timestamp) return ''
  const date = new Date(timestamp)
  return date.toLocaleString()
}

// 获取字典标签
const getDictLabel = (dictType, dictValue) => {
  const dictList = dictStore.dictList[dictType] || []
  const dictItem = dictList.find(item => item.code == dictValue)
  return dictItem ? dictItem.codeval : dictValue
}
</script>
