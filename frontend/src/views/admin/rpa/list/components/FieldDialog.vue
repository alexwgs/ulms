<template>
  <el-dialog
    :title="dialogTitle"
    v-model="dialogVisible"
    width="50%"
    :close-on-click-modal="false"
    :before-close="handleClose"
  >
    <el-form
      ref="formRef"
      :model="formData"
      :rules="rules"
      size="small"
      label-width="100px"
    >
      <el-row :gutter="15">
        <el-col :span="24">
          <el-form-item label="显示名称" prop="label">
            <el-input
              v-model="formData.label"
              placeholder="请输入字段显示名称"
              maxlength="50"
              show-word-limit
              clearable
            />
          </el-form-item>
        </el-col>

        <el-col :span="24">
          <el-form-item label="字段参数名称" prop="fieldName">
            <el-input
              v-model="formData.fieldName"
              placeholder="请输入字段参数名称"
              maxlength="50"
              show-word-limit
              clearable
            />
          </el-form-item>
        </el-col>

        <el-col :span="24">
          <el-form-item label="组件类型" prop="type">
            <el-select
              v-model="formData.type"
              placeholder="请选择控件类型"
              @change="handleComponentTypeChange"
            >
              <el-option
                v-for="item in componentTypes"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="24">
          <TemplateComponent
            ref="templateComponentRef"
            :type="formData.type"
            :field="formData"
            @update-options="updateOptions"
          />
        </el-col>

        <el-col :span="12">
          <el-form-item label="必填标志" prop="required">
            <el-select
              v-model="formData.required"
              placeholder="请选择必填标志"
              style="width: 100%"
            >
              <el-option label="必填" :value="1" />
              <el-option label="非必填" :value="0" />
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="排序" prop="orderId">
            <el-input-number
              v-model="formData.orderId"
              :min="-999"
              :max="999"
              :step="1"
              controls-position="right"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitForm" :loading="loading">
        确定
      </el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, computed } from 'vue'
import TemplateComponent from './TemplateComponent.vue'
import { addRpaToolTemplete, updateRpaToolTemplete } from '@/api/rpa/rpa.js'

const props = defineProps({
  refresh: Function
})

const emit = defineEmits(['close'])

// 组件类型选项
const componentTypes = [
  { label: '文本/数字', value: 'input' },
  { label: '下拉选择框', value: 'select' },
  { label: '日期选择器', value: 'dateTime' },
  { label: '日期区间选择', value: 'dateRange' },
  { label: '人员控件', value: 'userSelect' },
  { label: '架构控件', value: 'orgSelect' },
  { label: '批量ID', value: 'batchData' },
  { label: '运行人信息', value: 'userInfo' }
]

// 表单引用
const formRef = ref(null)
const templateComponentRef = ref(null)

// 对话框状态
const dialogVisible = ref(false)
const loading = ref(false)
const opType = ref('add')

// 表单数据
const formData = ref({
  id: '',
  templeteId: '',
  label: '',
  fieldName: '',
  type: '',
  options: {},
  orderId: 1,
  required: 0
})

// 表单验证规则
const rules = {
  label: [{ required: true, message: '请输入显示名称', trigger: 'blur' }],
  fieldName: [
    { required: true, message: '请输入字段参数名称', trigger: 'blur' }
  ],
  type: [{ required: true, message: '请选择组件类型', trigger: 'change' }],
  required: [{ required: true, message: '请选择必填标志', trigger: 'change' }],
  orderId: [{ required: true, message: '请输入排序值', trigger: 'blur' }]
}

// 计算对话框标题
const dialogTitle = computed(() => {
  return opType.value === 'add' ? '新增字段' : '编辑字段'
})

// 打开对话框
const openDialog = (type, data = {}) => {
  opType.value = type

  // 初始化表单数据
  if (type === 'update') {
    formData.value = {
      ...data,
      options:
        typeof data.options === 'string'
          ? JSON.parse(data.options)
          : data.options
    }
  } else {
    formData.value = {
      templeteId: data,
      label: '',
      fieldName: '',
      type: '',
      options: {},
      orderId: 1,
      required: 0
    }
  }

  dialogVisible.value = true
}

// 关闭对话框
const handleClose = () => {
  formRef.value?.resetFields()
  dialogVisible.value = false
  emit('close')
}

// 组件类型变化
const handleComponentTypeChange = () => {
  formData.value.options = {}
}

// 更新选项
const updateOptions = (options) => {
  formData.value.options = options
}

// 提交表单
const submitForm = async () => {
  try {
    // 验证子组件
    const validComponent = await templateComponentRef.value.validate()
    if (!validComponent) return

    // 验证主表单
    const validForm = await formRef.value.validate()
    if (!validForm) return

    loading.value = true
    const payload = {
      ...formData.value,
      options: JSON.stringify(formData.value.options)
    }

    let res = null
    if (opType.value === 'add') {
      res = await addRpaToolTemplete(payload)
    } else {
      res = await updateRpaToolTemplete(payload)
    }

    if (res.code !== 200) {
      throw new Error(res.msg)
    }

    ElMessage.success(res.msg)
    props.refresh?.()
    handleClose()
  } catch (error) {
    ElMessage.error(error.message || '操作失败')
  } finally {
    loading.value = false
  }
}

// 暴露方法供父组件调用
defineExpose({
  openDialog
})
</script>

<style lang="less" scoped>
.el-form-item {
  margin-bottom: 18px;
}

.el-input-number {
  width: 100%;
}
</style>
