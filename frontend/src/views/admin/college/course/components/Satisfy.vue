<template>
  <el-dialog
    title="附件管理"
    v-model="dialogVisible"
    :close-on-click-modal="false"
    width="50%"
    :before-close="handleClose"
  >
    <el-alert
      title="操作说明"
      type="info"
      :closable="false"
      description="评价天数设置为0则表示不限制评价天数。"
    />
    <el-row :gutter="15">
      <el-form
        ref="satisfyForm"
        :model="satisfyFormData"
        :rules="rules"
        size="small"
        label-width="100px"
        style="width: 100%"
      >
        <el-col :span="12">
          <el-form-item label="是否评价" prop="ifEval">
            <el-select
              v-model="satisfyFormData.ifEval"
              placeholder="请选择是否评价"
              @change="ifEvalChange"
              clearable
              :style="{ width: '100%' }"
            >
              <el-option label="无需评价" :value="0"></el-option>
              <el-option label="需要评价" :value="1"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="评价天数" prop="evalDate">
            <el-input
              v-model="satisfyFormData.evalDate"
              type="number"
              placeholder="学习后几天内完成，0为不限制"
              :maxlength="3"
              :style="{ width: '100%' }"
              :disabled="satisfyFormData.ifEval == 0"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="评价模板" prop="tempId">
            <el-select
              v-model="satisfyFormData.tempId"
              placeholder="请选择评价模板"
              clearable
              :style="{ width: '100%' }"
              :disabled="satisfyFormData.ifEval == 0"
            >
              <el-option label="无" value=""></el-option>
              <el-option
                v-for="item in tempList"
                :key="item.tempId"
                :label="item.tempName"
                :value="item.tempId"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>
    <template #footer>
      <span class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">取 消</el-button>
        <el-button size="small" type="primary" @click="handelConfirm"
          >更 新</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>
<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { evaluateApi } from '@/api/college/evaluate'
import { courseApi } from '@/api/college/course'

const dialogVisible = ref(false)
const tempList = ref([])
const emit = defineEmits(['refresh'])
const satisfyFormData = reactive({
  courseId: '',
  ifEval: 0,
  evalDate: 0,
  tempId: ''
})

const rules = reactive({
  ifEval: [{ required: true, message: '请选择是否评价', trigger: 'change' }],
  evalDate: [
    { required: true, message: '学习后几天内完成，0为不限制', trigger: 'blur' }
  ],
  tempId: [
    {
      required: true,
      message: '请选择评价模板',
      trigger: 'change',
      validator: (_, value, callback) => {
        if (satisfyFormData.ifEval === 1 && !value) {
          callback(new Error('请选择评价模板'))
        } else {
          callback()
        }
      }
    }
  ]
})
const show = (course) => {
  satisfyFormData.courseId = course.courseId
  satisfyFormData.ifEval = course.ifEval
  satisfyFormData.evalDate = course.evalDate
  satisfyFormData.tempId = course.tempId
  getTempleteList()
  dialogVisible.value = true
}

const getTempleteList = async () => {
  try {
    const res = await evaluateApi.getEvaluateTemplateList({ tempStat: 1 })
    if (res.code !== 200) return ElMessage.error(res.msg)
    tempList.value = res.data
  } catch (error) {
    ElMessage.error('获取评价模板列表失败')
  }
}

const handelConfirm = async () => {
  try {
    const res = await courseApi.updateCourse(satisfyFormData)
    if (res.code !== 200) return ElMessage.error(res.msg)
    ElMessage.success(res.msg)
    emit('refresh')
    dialogVisible.value = false
  } catch (error) {
    console.error(error)
    ElMessage.error('更新评价配置失败')
  }
}

const ifEvalChange = () => {
  if (satisfyFormData.ifEval === 0) {
    satisfyFormData.evalDate = 0
    satisfyFormData.tempId = ''
  }
}

const handleClose = () => {
  dialogVisible.value = false
}

defineExpose({
  show
})
</script>
<style lang="less" scoped></style>
