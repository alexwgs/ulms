<template>
  <t-dialog
    header="附件管理"
    v-model:visible="dialogVisible"
    :close-on-overlay-click="false"
    width="50%"
    :before-close="handleClose"
  >
    <PageTips
      title="操作说明"
      theme="info"
      :closable="false"
      message="评价天数设置为0则表示不限制评价天数。"
    />
    <t-row :gutter="15">
      <t-form
        ref="satisfyForm"
        :data="satisfyFormData"
        :rules="rules"
        size="small"
        label-width="100px"
        style="width: 100%"
      >
        <t-col :span="6">
          <t-form-item label="是否评价" name="ifEval">
            <t-select
              v-model="satisfyFormData.ifEval"
              placeholder="请选择是否评价"
              @change="ifEvalChange"
              clearable
              :style="{ width: '100%' }"
            >
              <t-option label="无需评价" :value="0"></t-option>
              <t-option label="需要评价" :value="1"></t-option>
            </t-select>
          </t-form-item>
        </t-col>
        <t-col :span="6">
          <t-form-item label="评价天数" name="evalDate">
            <t-input
              v-model="satisfyFormData.evalDate"
              type="number"
              placeholder="学习后几天内完成，0为不限制"
              :maxlength="3"
              :style="{ width: '100%' }"
              :disabled="satisfyFormData.ifEval == 0"
            ></t-input>
          </t-form-item>
        </t-col>
        <t-col :span="12">
          <t-form-item label="评价模板" name="tempId">
            <t-select
              v-model="satisfyFormData.tempId"
              placeholder="请选择评价模板"
              clearable
              :style="{ width: '100%' }"
              :disabled="satisfyFormData.ifEval == 0"
            >
              <t-option label="无" value=""></t-option>
              <t-option
                v-for="item in tempList"
                :key="item.tempId"
                :label="item.tempName"
                :value="item.tempId"
              ></t-option>
            </t-select>
          </t-form-item>
        </t-col>
      </t-form>
    </t-row>
    <template #footer>
      <span class="dialog-footer">
        <t-button size="small" @click="dialogVisible = false">取 消</t-button>
        <t-button size="small" theme="primary" @click="handelConfirm"
          >更 新</t-button
        >
      </span>
    </template>
  </t-dialog>
</template>
<script setup>
import { ref, reactive } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
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
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    tempList.value = res.data
  } catch (error) {
    MessagePlugin.error('获取评价模板列表失败')
  }
}

const handelConfirm = async () => {
  try {
    const res = await courseApi.updateCourse(satisfyFormData)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    emit('refresh')
    dialogVisible.value = false
  } catch (error) {
    console.error(error)
    MessagePlugin.error('更新评价配置失败')
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
