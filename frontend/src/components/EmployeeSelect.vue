<template>
  <t-select
    v-if="conditionType === 'search'"
    :style="{ width: width }"
    :size="moduleSize"
    v-model:model-value="ploNum"
    :loading="loading"
    :disabled="disabled"
    placeholder="请输入姓名、工号、期数、组别查询（可模糊查询）"
    filterable
    :clearable="clearable"
    @search="handleSearch"
    @change="handleChange"
  >
    <t-option
      v-for="item of options"
      :key="item.ploNum"
      :value="item.ploNum"
      :label="
        item.ploNum +
        '/' +
        item.ploName +
        '[' +
        item.deptName +
        ' - ' +
        item.groupName +
        ']'
      "
    ></t-option>
  </t-select>

  <t-select
    v-else-if="conditionType === 'joblevel'"
    :style="{ width: width }"
    :size="moduleSize"
    v-model:model-value="ploNum"
    :loading="loading"
    :disabled="disabled"
    :clearable="clearable"
    placeholder="请选择..."
  >
    <t-option
      v-for="item of options"
      :key="item.ploNum"
      :value="item.ploNum"
      >{{
        item.ploNum +
        '/' +
        item.ploName +
        '[' +
        item.deptName +
        ' - ' +
        item.groupName +
        ']'
      }}</t-option
    >
  </t-select>

  <t-select
    v-else-if="conditionType === 'info'"
    :style="{ width: width }"
    :size="moduleSize"
    v-model:model-value="ploNum"
    :loading="loading"
    :disabled="disabled"
    :clearable="clearable"
    placeholder="请输入姓名、工号、期数、组别查询（可模糊查询）"
    filterable
    @search="handleSearch"
    @change="getUser"
  >
    <t-option
      v-for="item of options"
      :key="item.ploNum"
      :value="item.ploNum"
      >{{
        item.ploNum +
        '/' +
        item.ploName +
        '[' +
        item.deptName +
        ' - ' +
        item.groupName +
        ']'
      }}</t-option
    >
  </t-select>
</template>
<script setup>
import { ref, onMounted, watch } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { findEmployee, findEmployeeByJobLevel } from '@/api/permissionAPI'
import { useUserStore, useAppStore } from '@/stores'
const { moduleSize } = useAppStore()
const userStore = useUserStore()
const props = defineProps({
  conditionType: {
    type: String,
    validator: (value) => {
      return ['search', 'joblevel', 'info'].includes(value)
    },
    default: 'search' // 'jobLevel'
  },
  ploStatus: {
    type: String,
    default: ''
  },
  width: {
    type: String,
    default: '300px'
  },
  params: {
    type: String,
    default: ''
  },
  disabled: {
    type: Boolean,
    default: false
  },
  modelValue: {
    type: String,
    default: ''
  },
  allowClear: {
    type: Boolean,
    default: false
  },
  clearable: {
    type: Boolean,
    default: true
  }
})

const loading = ref(false)
const options = ref([])
const user = ref({})
const ploNum = ref()
const emit = defineEmits(['update:modelValue', 'getUser'])

const queryInfo = ref({
  ploStatus: props.ploStatus,
  value: props.params
})
// 监听props.modelValue变化，更新内部ploNum
watch(
  () => props.modelValue,
  (newVal) => {
    ploNum.value = newVal
  },
  { immediate: true }
)

// 监听内部ploNum变化，通知父组件
watch(
  () => ploNum.value,
  (newValue) => {
    // 单个数据更新
    emit('update:modelValue', newValue)
  }
)
// 处理选择变化
const handleChange = (value) => {
  // 触发自定义事件 'getUser'，并传递当前选中值（或其他数据）
  emit('getUser', value)
  // 注意：update:modelValue 已经通过 watch 自动触发，无需重复
}

const handleSearch = async (e) => {
  if (!e) return
  const key = e.toUpperCase()
  // 优化请求
  if (e.length < 4 && 'A,S,6'.indexOf(key.substring(0, 1)) > -1) {
    options.value = []
    return
  }
  loading.value = true
  queryInfo.value.value = e
  try {
    const res = await findEmployee(queryInfo.value)
    options.value = res.data
  } catch (error) {
    console.error('搜索失败:', error)
    MessagePlugin.error('搜索失败，请重试')
  } finally {
    loading.value = false
  }
}

const getOptions = async () => {
  if (
    props.params === null ||
    props.params === undefined ||
    props.params === ''
  )
    return
  loading.value = true
  try {
    if (props.conditionType === 'joblevel') {
      const res = await findEmployeeByJobLevel(props.params)
      if (res.code !== 200) {
        MessagePlugin.error(res.msg)
        return
      }
      options.value = res.data
      // 如果传递过来的人员信息不为空，为其自动按照科室组别的顺序匹配上级
      options.value.find((item) => {
        if (item.deptGroup === userStore.deptGroup) {
          ploNum.value = item.ploNum
        } else if (item.deptNum === userStore.deptNum) {
          ploNum.value = item.ploNum
        }
      })
    } else if (props.conditionType === 'info') {
      const res = await findEmployee(queryInfo.value)
      if (res.code !== 200) {
        MessagePlugin.error(res.msg)
        return
      }
      options.value = res.data
      options.value.find((item) => {
        ploNum.value = item.ploNum
      })
    }
  } catch (e) {
    console.error('获取选项失败:', e)
    MessagePlugin.error('获取选项失败，请重试')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  getOptions()
})
defineExpose({ ploNum, user, getOptions, handleSearch })
</script>
