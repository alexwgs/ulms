<template>
  <t-dialog
    header="人力资源"
    :visible="props.treeVisiable"
    @update:visible="handleVisibleChange"
    :close-on-overlay-click="false"
    :close-on-esc-keydown="false"
    :close-btn="false"
    attach="body"
  >
    <t-input size="small" placeholder="输入关键字进行过滤" v-model="filterText"></t-input>
    <div style="height: 400px; overflow: auto; margin-top: 10px;">
      <t-tree
        :data="data"
        :checkable="mutiselect"
        :keys="{ value: 'id', label: 'name', children: 'children' }"
        :filter="filterNode"
        ref="treeRef"
        activable
        v-model="checkedKeys"
        @active="onActive"
        :expand-on-click-node="mutiselect"
      >
      </t-tree>
    </div>
    <template #footer>
      <div class="dialog-footer">
        <t-button size="small" theme="primary" @click="submitSelected">确定</t-button>
        <t-button size="small" @click="closeDialog">关闭</t-button>
      </div>
    </template>
  </t-dialog>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
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
const checkedKeys = ref([])
const activeValues = ref([])

const handleVisibleChange = (val) => {
  emit('update:treeVisiable', val)
}

const getTreeList = async (type) => {
  if (!type) return
  try {
    const res = await httpInstance.get(`employee/tree/${type}`)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    data.value = res.tree
  } catch (error) {
    MessagePlugin.error('获取树形数据失败')
    console.error(error)
  }
}

const onActive = (value) => {
  activeValues.value = value
}

const filterNode = (node) => {
  if (!filterText.value) return true
  return node.data.label.indexOf(filterText.value) !== -1
}

const submitSelected = () => {
  const checkedNodes = checkedKeys.value
    .map((key) => treeRef.value?.getItem(key))
    .filter(Boolean)
    .map((node) => node.data)

  if (!props.mutiselect && checkedNodes.length > 1) {
    MessagePlugin.error('只能选择一个人员！')
    return
  }

  if (props.mutiselect) {
    emit('getChecked', checkedNodes)
  } else {
    const activeVal = activeValues.value[0]
    const currentNode = activeVal != null
      ? treeRef.value?.getItem(activeVal)?.data
      : null
    emit('getChecked', currentNode)
  }

  closeDialog()
}

const closeDialog = () => {
  emit('update:treeVisiable', false)
}

watch(filterText, (val) => {
  treeRef.value?.refresh()
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
