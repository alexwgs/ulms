<template>
  <el-dialog 
    title="人力资源" 
    :model-value="props.treeVisiable" 
    @update:model-value="handleVisibleChange"
    :close-on-click-modal="false" 
    :close-on-press-escape="false"
    :show-close="false" 
    append-to-body
  >
    <el-input size="small" placeholder="输入关键字进行过滤" v-model="filterText"></el-input>
    <div style="height: 400px; overflow: auto; margin-top: 10px;">
      <el-tree 
        :data="data" 
        :show-checkbox="mutiselect" 
        node-key="id" 
        :filter-node-method="filterNode" 
        ref="treeRef"
        highlight-current
        :check-on-click-node="mutiselect"
      >
      </el-tree>
    </div>
    <template #footer>
      <div class="dialog-footer">
        <el-button size="small" type="primary" @click="submitSelected">确定</el-button>
        <el-button size="small" @click="closeDialog">关闭</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { httpInstance } from '@/utils/request'

const props = defineProps({
  treeVisiable: {
    type: Boolean,
    default: false
  },
  type: {
    type: String,
    default: ''
  },
  mutiselect: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['update:treeVisiable', 'getChecked'])

const data = ref([])
const filterText = ref('')
const treeRef = ref(null)

const handleVisibleChange = (val) => {
  emit('update:treeVisiable', val)
}

const getTreeList = async (type) => {
  if (!type) return
  try {
    const res = await httpInstance.get(`employee/tree/${type}`)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    data.value = res.tree
  } catch (error) {
    ElMessage.error('获取树形数据失败')
    console.error(error)
  }
}

const filterNode = (value, data) => {
  if (!value) return true
  return data.label.indexOf(value) !== -1
}

const submitSelected = () => {
  const checkedNodes = treeRef.value?.getCheckedNodes(true, false) || []

  if (!props.mutiselect && checkedNodes.length > 1) {
    ElMessage.error('只能选择一个人员！')
    return
  }

  if (props.mutiselect) {
    emit('getChecked', checkedNodes)
  } else {
    const currentNode = treeRef.value?.getCurrentNode()
    emit('getChecked', currentNode)
  }

  closeDialog()
}

const closeDialog = () => {
  emit('update:treeVisiable', false)
}

watch(filterText, (val) => {
  treeRef.value?.filter(val)
})

// 监听 treeVisiable 变化，当对话框打开时加载数据
watch(() => props.treeVisiable, (newVal) => {
  if (newVal && props.type) {
    getTreeList(props.type)
  }
})

onMounted(() => {
  if (props.type && props.treeVisiable) {
    getTreeList(props.type)
  }
})
</script>

<style lang="less" scoped>
.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style>
