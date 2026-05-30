<template>
  <el-dialog
    :title="dialogTitle"
    v-model="dialogVisible"
    width="60%"
    :close-on-click-modal="false"
    :before-close="handleClose"
    :fullscreen="false"
  >
    <el-form
      ref="toolFormRef"
      :model="formData"
      :rules="rules"
      size="small"
      label-width="120px"
    >
      <el-col :span="24">
        <el-form-item label="服务名称" prop="name">
          <el-input
            v-model="formData.name"
            placeholder="请输入前端显示的RPA名称，建议保持与海螺API借调名称一致"
            :maxlength="50"
            show-word-limit
            clearable
          />
        </el-form-item>
      </el-col>

      <el-col :span="24">
        <el-form-item label="分类" prop="category" required>
          <el-select v-model="formData.category" placeholder="请选择工具类别">
            <el-option
              v-for="item in dictStore.dictList.rpa_tool_list_category"
              :key="item.code"
              :label="item.codeval"
              :value="item.code"
            />
          </el-select>
        </el-form-item>
      </el-col>

      <el-col :span="24">
        <el-form-item label="RPA说明" prop="memo">
          <el-input
            type="textarea"
            v-model="formData.memo"
            placeholder="请输入RPA功能说明"
            :maxlength="1000"
            show-word-limit
            clearable
            :rows="3"
          />
        </el-form-item>
      </el-col>

      <el-col :span="24">
        <el-form-item label="订阅号" prop="subscribeId" required>
          <el-input
            v-model="formData.subscribeId"
            placeholder="请从RPA数字助理门户- API服务借调 中获取"
            :maxlength="100"
            show-word-limit
            clearable
          />
        </el-form-item>
      </el-col>

      <el-col :span="24">
        <el-form-item label="调用密钥" prop="apiSec">
          <el-input
            v-model="formData.apiSec"
            placeholder="请从RPA数字助理门户- API服务借调 中获取"
            :maxlength="100"
            show-word-limit
            clearable
          />
        </el-form-item>
      </el-col>

      <el-col :span="24">
        <el-form-item label="请求地址" prop="url">
          <el-input
            v-model="formData.url"
            placeholder="请求的API服务借调服务地址"
            :maxlength="500"
            show-word-limit
            clearable
          />
        </el-form-item>
      </el-col>

      <el-col :span="24">
        <el-form-item label="Authorization" prop="auth" required>
          <el-input
            v-model="formData.auth"
            placeholder="请输入Authorization"
            :maxlength="1000"
            show-word-limit
            clearable
          />
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="运行时长" prop="runTimes" required>
          <el-input
            v-model.number="formData.runTimes"
            placeholder="请输入预计运行耗时，单位秒"
            type="number"
            min="0"
          />
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="联系人" prop="contacts" required>
          <el-input
            v-model="formData.contacts"
            placeholder="工号/姓名"
            :maxlength="50"
            show-word-limit
            clearable
          />
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="状态" prop="status">
          <el-select
            v-model="formData.status"
            placeholder="请选择状态"
            style="width: 100%"
          >
            <el-option label="有效" :value="1" />
            <el-option label="无效" :value="0" />
          </el-select>
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="排序" prop="sortId">
          <el-input-number
            v-model="formData.sortId"
            :min="1"
            :step="1"
            controls-position="right"
          />
        </el-form-item>
      </el-col>
    </el-form>

    <template #footer>
      <el-button @click="handleClose">取消</el-button>
      <el-button type="primary" @click="submitForm" :loading="loading">
        确定
      </el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, computed } from 'vue'
import { ElMessage } from 'element-plus'
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
    if (!valid) return

    loading.value = true

    let res = {}
    if (opType.value === 'add') {
      res = await addRpaTool(formData.value)
    } else {
      res = await updateRpaTool(formData.value)
    }
    if (res.code !== 200) return

    ElMessage.success(res.msg)
    emit('refresh')
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
