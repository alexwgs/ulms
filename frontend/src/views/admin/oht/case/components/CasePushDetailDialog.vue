<template>
  <el-dialog title="推送明细" v-model="dialogVisible" width="80%">
    <el-table
      :data="pushList"
      stripe
      style="width: 100%"
      size="small"
    >
      <el-table-column prop="deptNum" label="科室" width="120"></el-table-column>
      <el-table-column prop="groupNum" label="组别" width="100"></el-table-column>
      <el-table-column prop="userId" label="用户信息" width="130"></el-table-column>
      <el-table-column prop="dataTime" label="推送时间" width="160">
        <template #default="{ row }">
          {{ formatDateTime(row.dataTime) }}
        </template>
      </el-table-column>
      <el-table-column prop="taskLevel" label="级别" width="70"></el-table-column>
      <el-table-column prop="taskStatus" label="处理结果" width="100">
        <template #default="{ row }">
          {{ getDictLabel('oht_case_task_status', row.taskStatus) }}
        </template>
      </el-table-column>
      <el-table-column prop="memo" label="备注"></el-table-column>
    </el-table>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="dialogVisible = false">关闭</el-button>
      </span>
    </template>
  </el-dialog>
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
