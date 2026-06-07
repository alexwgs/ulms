<template>
  <t-dialog
    :header="dialogTitle"
    v-model:visible="dialogVisible"
    width="60%"
    :close-on-overlay-click="false"
    @before-close="handleClose"
  >
    <t-form
      ref="toolFormRef"
      :data="formData"
      :rules="rules"
      size="small"
      label-width="120px"
    >
      <t-row :gutter="[16, 16]">
        <t-col :span="12">
          <t-form-item label="服务名称" name="name">
          <t-input
            v-model="formData.name"
            placeholder="请输入前端显示的RPA名称，建议保持与海螺API借调名称一致"
            :maxlength="50"
            show-limit-number
            clearable
          />
        </t-form-item>
      </t-col>

      <t-col :span="12">
        <t-form-item label="分类" name="category" required>
          <t-select v-model="formData.category" placeholder="请选择工具类别">
            <t-option
              v-for="item in dictStore.dictList.rpa_tool_list_category"
              :key="item.code"
              :label="item.codeval"
              :value="item.code"
            />
          </t-select>
        </t-form-item>
      </t-col>

      <t-col :span="12">
        <t-form-item label="RPA说明" name="memo">
          <t-textarea v-model="formData.memo"
            placeholder="请输入RPA功能说明"
            :maxlength="1000"
            show-limit-number
            clearable
            :rows="3" />
        </t-form-item>
      </t-col>

      <t-col :span="12">
        <t-form-item label="订阅号" name="subscribeId" required>
          <t-input
            v-model="formData.subscribeId"
            placeholder="请从RPA数字助理门户- API服务借调 中获取"
            :maxlength="100"
            show-limit-number
            clearable
          />
        </t-form-item>
      </t-col>

      <t-col :span="12">
        <t-form-item label="调用密钥" name="apiSec">
          <t-input
            v-model="formData.apiSec"
            placeholder="请从RPA数字助理门户- API服务借调 中获取"
            :maxlength="100"
            show-limit-number
            clearable
          />
        </t-form-item>
      </t-col>

      <t-col :span="12">
        <t-form-item label="请求地址" name="url">
          <t-input
            v-model="formData.url"
            placeholder="请求的API服务借调服务地址"
            :maxlength="500"
            show-limit-number
            clearable
          />
        </t-form-item>
      </t-col>

      <t-col :span="12">
        <t-form-item label="Authorization" name="auth" required>
          <t-input
            v-model="formData.auth"
            placeholder="请输入Authorization"
            :maxlength="1000"
            show-limit-number
            clearable
          />
        </t-form-item>
      </t-col>

      <t-col :span="6">
        <t-form-item label="运行时长" name="runTimes" required>
          <t-input
            v-model.number="formData.runTimes"
            placeholder="请输入预计运行耗时，单位秒"
            type="number"
            min="0"
          />
        </t-form-item>
      </t-col>

      <t-col :span="6">
        <t-form-item label="联系人" name="contacts" required>
          <t-input
            v-model="formData.contacts"
            placeholder="工号/姓名"
            :maxlength="50"
            show-limit-number
            clearable
          />
        </t-form-item>
      </t-col>

      <t-col :span="6">
        <t-form-item label="状态" name="status">
          <t-select
            v-model="formData.status"
            placeholder="请选择状态"
            style="width: 100%"
          >
            <t-option label="有效" :value="1" />
            <t-option label="无效" :value="0" />
          </t-select>
        </t-form-item>
      </t-col>

      <t-col :span="6">
        <t-form-item label="排序" name="sortId">
          <t-input-number
            v-model="formData.sortId"
            :min="1"
            :step="1"
            controls-position="right"
          />
        </t-form-item>
      </t-col>
      </t-row>
    </t-form>

    <template #footer>
      <t-button @click="handleClose">取消</t-button>
      <t-button theme="primary" @click="submitForm" :loading="loading">
        确定
      </t-button>
    </template>
  </t-dialog>
</template>

<script setup>
import { ref, computed } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { useDictStore } from '@/stores'
import { addRpaTool, updateRpaTool } from '@/api/rpa/rpa.js'

const emit = defineEmits(['refresh'])

const dictStore = useDictStore()

// 对话框状态
const dialogVisible = ref(false)
const loading = ref(false)
const opType = ref('add')

// 表单数据
const formData = ref({
  id: '',
  category: '',
  name: '',
  subscribeId: '',
  memo: '',
  apiSec: '',
  runTimes: 0,
  contacts: '',
  roles: '',
  url: '',
  files: null,
  sortId: 1,
  status: 1,
  auth: ''
})

// 表单验证规则
const rules = {
  name: [{ required: true, message: '请输入服务名称', trigger: 'blur' }],
  category: [{ required: true, message: '请选择分类', trigger: 'change' }],
  subscribeId: [{ required: true, message: '请输入订阅号', trigger: 'blur' }],
  auth: [{ required: true, message: '请输入Authorization', trigger: 'blur' }],
  runTimes: [
    { required: true, message: '请输入运行时长', trigger: 'blur' },
    { type: 'number', min: 0, message: '必须大于等于0', trigger: 'blur' }
  ],
  contacts: [{ required: true, message: '请输入联系人', trigger: 'blur' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }],
  sortId: [{ required: true, message: '请输入排序值', trigger: 'blur' }]
}

// 计算对话框标题
const dialogTitle = computed(() => {
  return opType.value === 'add' ? '新增工具' : '编辑工具'
})

// 表单引用
const toolFormRef = ref(null)

// 打开对话框
const openDialog = (type, data = {}) => {
  opType.value = type
  formData.value = {
    id: '',
    category: '',
    name: '',
    memo: '',
    subscribeId: '',
    apiSec: '',
    runTimes: 0,
    contacts: '',
    roles: '',
    url: '',
    files: null,
    sortId: 1,
    status: 1,
    auth: '',
    ...data
  }
  dialogVisible.value = true
}

// 关闭对话框
const handleClose = () => {
  dialogVisible.value = false
}

// 提交表单
const submitForm = async () => {
  try {
    const valid = await toolFormRef.value.validate()
    if (valid !== true) return

    loading.value = true

    let res = {}
    if (opType.value === 'add') {
      res = await addRpaTool(formData.value)
    } else {
      res = await updateRpaTool(formData.value)
    }
    if (res.code !== 200) return

    MessagePlugin.success(res.msg)
    emit('refresh')
    handleClose()
  } catch (error) {
    MessagePlugin.error(error.message || '操作失败')
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
.t-form-item {
  margin-bottom: 18px;
}

.t-input-number {
  width: 100%;
}
</style>
