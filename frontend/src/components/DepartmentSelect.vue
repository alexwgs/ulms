<template>
  <t-tree-select
    :size="moduleSize"
    :data="treeData"
    :clearable="allowClear"
    :max-tag-count="maxTag"
    :checkable="mutiple"
    :placeholder="placeholder"
    :check-strictly="false"
    :multiple="mutiple"
    :filterable="allowSearch"
    :filter-method="filterTreeNode"
    v-model="selectedValue"
    :props="{
      value: 'id',
      label: 'label',
      children: 'children'
    }"
    style="width: 300px"
    :expand-on-click-node="true"
    :render-after-expand="false"
    :value-key="'id'"
  />
</template>

<script>
// 在普通 <script> 块中定义默认值
export default {
  props: {
    mutiple: {
      type: Boolean,
      default: false
    },
    treeCheckable: {
      type: Boolean,
      default: true
    },
    allowClear: {
      type: Boolean,
      default: true
    },
    placeholder: {
      type: String,
      default: '请选择组织架构...'
    },
    maxTag: {
      type: Number,
      default: 5
    },
    dataType: {
      type: String,
      default: 'dept'
    },
    modelValue: {
      type: [String, Number, Array],
      default: null
    },
    treeCheckedStrategy: {
      type: String,
      default: 'all'
    },
    allowSearch: {
      type: Boolean,
      default: true
    },
    fieldNames: {
      type: Object,
      default: () => ({
        value: 'id',
        label: 'label',
        children: 'children'
      })
    }
  }
}
</script>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { departmentTree, groupTree, joblevelTree } from '@/api/permissionAPI'
import { useUserStore, useAppStore } from '@/stores'

const { moduleSize } = useAppStore()
const props = defineProps()

const emit = defineEmits(['update:modelValue'])
const sourceData = ref([])
const treeData = ref([])
const selectedValue = ref(
  props.mutiple ? props.modelValue || [] : props.modelValue || null
)

// 处理外部传入的 modelValue 变化
watch(
  () => props.modelValue,
  (newVal) => {
    if (newVal === undefined || newVal === null) {
      selectedValue.value = props.mutiple ? [] : null
    } else if (props.mutiple) {
      selectedValue.value = Array.isArray(newVal)
        ? newVal
        : typeof newVal === 'string' && newVal.indexOf(',') > -1
          ? newVal.split(',')
          : [newVal]
    } else {
      selectedValue.value = newVal
    }
  },
  { immediate: true }
)

// 处理内部值变化
watch(
  selectedValue,
  (newVal) => {
    emit('update:modelValue', newVal)
  },
  { deep: true }
)

// 过滤树节点（仅过滤，不修改原始数据）
const filterTreeNode = (value, data) => {
  // 处理data可能为undefined的情况
  if (!data || !data.label) {
    return false
  }
  return data.label.includes(value)
}

const initData = async () => {
  if (props.dataType) {
    let res = {}
    if (props.dataType === 'dept') {
      res = await departmentTree()
    } else if (props.dataType === 'group') {
      res = await groupTree()
    } else if (props.dataType === 'joblevel') {
      res = await joblevelTree()
    }
    sourceData.value = res.data
    treeData.value = res.data
  }
}

onMounted(() => {
  initData()
})
</script>
