<template>
  <el-form
    ref="formRef"
    :model="formData"
    size="small"
    label-width="100px"
    :rules="rules"
  >
    <!-- 文本/数字输入框 -->
    <div v-if="type === 'input'">
      <el-row :gutter="15">
        <el-col :span="12">
          <el-form-item label="类型" prop="type">
            <el-select v-model="formData.type" placeholder="请选择控件类型">
              <el-option label="文本" value="text" />
              <el-option label="多行文本" value="textarea" />
              <el-option label="数字" value="number" />
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="输入框行数" prop="rows">
            <el-input-number
              v-model="formData.rows"
              :min="1"
              :disabled="formData.type !== 'textarea'"
            />
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="最大长度" prop="maxlength">
            <el-input-number v-model="formData.maxlength" :min="1" />
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="最小长度" prop="minlength">
            <el-input-number v-model="formData.minlength" :min="0" />
          </el-form-item>
        </el-col>

        <el-col :span="24">
          <el-form-item label="占位文本" prop="placeholder">
            <el-input v-model="formData.placeholder" maxlength="100" />
          </el-form-item>
        </el-col>
      </el-row>
    </div>

    <!-- 下拉选择框 -->
    <div v-else-if="type === 'select'">
      <el-row :gutter="15">
        <el-col :span="12">
          <el-form-item label="是否多选" prop="mutiselect">
            <el-select v-model="formData.mutiselect">
              <el-option label="多选" :value="true" />
              <el-option label="单选" :value="false" />
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="选择项目数" prop="multipleLimit">
            <el-input-number
              v-model="formData.multipleLimit"
              :min="1"
              :disabled="!formData.mutiselect"
            />
          </el-form-item>
        </el-col>

        <el-col :span="24">
          <el-form-item label="选项配置" prop="options">
            <el-input
              type="textarea"
              v-model="formData.options"
              :rows="4"
              placeholder='[{"label": "选项1", "value": "value1"}, {"label": "选项2", "value": "value2"}]'
            />
            <div class="el-form-item__tip">请输入有效的JSON数组格式</div>
          </el-form-item>
        </el-col>
      </el-row>
    </div>

    <!-- 日期选择器 -->
    <div v-else-if="type === 'dateTime'">
      <el-row :gutter="15">
        <el-col :span="12">
          <el-form-item label="日期类型" prop="type">
            <el-select v-model="formData.type">
              <el-option label="年选择" value="year" />
              <el-option label="月选择" value="month" />
              <el-option label="日选择" value="date" />
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="可输入" prop="editable">
            <el-select v-model="formData.editable">
              <el-option label="是" :value="true" />
              <el-option label="否" :value="false" />
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="值格式" prop="valueFormat">
            <el-select v-model="formData.valueFormat">
              <el-option label="YYYY" value="YYYY" />
              <el-option label="YYYY-MM" value="YYYY-MM" />
              <el-option label="YYYY-MM-DD" value="YYYY-MM-DD" />
              <el-option
                label="YYYY-MM-DD HH:mm:ss"
                value="YYYY-MM-DD HH:mm:ss"
              />
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="显示样式" prop="format">
            <el-select v-model="formData.format">
              <el-option label="YYYY" value="YYYY" />
              <el-option label="YYYY-MM" value="YYYY-MM" />
              <el-option label="YYYY-MM-DD" value="YYYY-MM-DD" />
              <el-option
                label="YYYY-MM-DD HH:mm:ss"
                value="YYYY-MM-DD HH:mm:ss"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </div>

    <!-- 日期区间选择 -->
    <div v-else-if="type === 'dateRange'">
      <el-row :gutter="15">
        <el-col :span="12">
          <el-form-item label="日期类型" prop="type">
            <el-select v-model="formData.type">
              <el-option label="日期区间（含时间）" value="datetimerange" />
              <el-option label="日期区间" value="daterange" />
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="可输入" prop="editable">
            <el-select v-model="formData.editable">
              <el-option label="是" :value="true" />
              <el-option label="否" :value="false" />
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="值格式" prop="valueFormat">
            <el-select v-model="formData.valueFormat">
              <el-option label="YYYY" value="YYYY" />
              <el-option label="YYYY-MM" value="YYYY-MM" />
              <el-option label="YYYY-MM-DD" value="YYYY-MM-DD" />
              <el-option
                label="YYYY-MM-DD HH:mm:ss"
                value="YYYY-MM-DD HH:mm:ss"
              />
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="显示样式" prop="format">
            <el-select v-model="formData.format">
              <el-option label="YYYY" value="YYYY" />
              <el-option label="YYYY-MM" value="YYYY-MM" />
              <el-option label="YYYY-MM-DD" value="YYYY-MM-DD" />
              <el-option
                label="YYYY-MM-DD HH:mm:ss"
                value="YYYY-MM-DD HH:mm:ss"
              />
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="分隔符" prop="rangeSeparator">
            <el-input v-model="formData.rangeSeparator" />
          </el-form-item>
        </el-col>
      </el-row>
    </div>

    <!-- 人员选择控件 -->
    <div v-else-if="type === 'userSelect'">
      <el-row>
        <el-col :span="12">
          <el-form-item label="是否多选" prop="mutiselect">
            <el-select v-model="formData.mutiselect">
              <el-option label="可多选" :value="true" />
              <el-option label="不可多选" :value="false" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </div>

    <!-- 架构控件 -->
    <div v-else-if="type === 'orgSelect'">
      <el-row :gutter="15">
        <el-col :span="12">
          <el-form-item label="是否多选" prop="mutiselect">
            <el-select v-model="formData.mutiselect">
              <el-option label="可多选" :value="true" />
              <el-option label="不可多选" :value="false" />
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="选择口径" prop="type">
            <el-select v-model="formData.type">
              <el-option label="仅科室" value="dept" />
              <el-option label="科室及组别" value="all" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </div>

    <!-- 批量ID -->
    <div v-else-if="type === 'batchData'">
      <el-alert
        title="此种类型仅限于批量识别0～9的连续数字，如中间存在任何非数字类型则自动替换英文逗号.不要选择必填"
        type="warning"
        :closable="false"
        class="mb-3"
      />

      <el-row :gutter="15">
        <el-col :span="12">
          <el-form-item label="输入框行数" prop="rows">
            <el-input-number v-model="formData.rows" :min="1" />
          </el-form-item>
        </el-col>

        <el-col :span="24">
          <el-form-item label="占位文本" prop="placeholder">
            <el-input v-model="formData.placeholder" maxlength="100" />
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="最大长度" prop="maxlength">
            <el-input-number v-model="formData.maxlength" :min="1" />
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="最小长度" prop="minlength">
            <el-input-number v-model="formData.minlength" :min="0" />
          </el-form-item>
        </el-col>
      </el-row>
    </div>

    <!-- 使用人信息 -->
    <div v-else-if="type === 'userInfo'">
      <el-alert
        title="默认设置使用人信息，可选择工号、组别、科室；均传递中文字符。该字段均readonly，同时可设置前端是否可见。后端数据处理务必注意‘小组’可能为空值的情况"
        type="warning"
        :closable="false"
        class="mb-3"
      />

      <el-row>
        <el-col :span="12">
          <el-form-item label="选择口径" prop="type">
            <el-select v-model="formData.type">
              <el-option label="使用人科室" value="dept" />
              <el-option label="使用人小组" value="group" />
              <el-option label="使用人工号" value="user" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </div>

    <!-- 未匹配类型 -->
    <div v-else>
      <el-alert
        title="当前尚未配置控件信息或请先选择需要新增的控件类型！"
        type="error"
        :closable="false"
      />
    </div>
  </el-form>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'

const props = defineProps({
  type: {
    type: String,
    default: ''
  },
  field: {
    type: Object,
    default: () => ({})
  }
})

const emit = defineEmits(['updateOptions'])

// 表单引用
const formRef = ref(null)

// 表单数据
const formData = ref({
  // 通用属性
  type: '',
  placeholder: '',

  // 文本/数字输入框
  rows: 1,
  maxlength: 100,
  minlength: 0,

  // 下拉选择框
  mutiselect: false,
  multipleLimit: 1,
  options: '',

  // 日期相关
  editable: true,
  valueFormat: 'yyyy-MM-dd',
  format: 'yyyy-MM-dd',
  rangeSeparator: '至',

  // 架构控件
  selectType: 'dept'
})

// 表单验证规则
const rules = {
  type: [{ required: true, message: '请选择类型', trigger: 'change' }],
  maxlength: [{ required: true, message: '请输入最大长度', trigger: 'blur' }],
  editable: [
    { required: true, message: '请选择是否可输入', trigger: 'change' }
  ],
  valueFormat: [{ required: true, message: '请选择值格式', trigger: 'change' }],
  format: [{ required: true, message: '请选择显示样式', trigger: 'change' }],
  rangeSeparator: [
    { required: true, message: '请输入分隔符', trigger: 'blur' }
  ],
  mutiselect: [{ required: true, message: '请选择是否多选', trigger: 'change' }]
}

// 初始化表单数据
const initFormData = () => {
  if (props.field?.options) {
    formData.value = {
      ...formData.value,
      ...(typeof props.field.options === 'string'
        ? JSON.parse(props.field.options)
        : props.field.options)
    }
  }
}

// 提交表单验证
const validate = async () => {
  try {
    await formRef.value.validate()

    // 如果是下拉选择框，验证选项配置是否为有效JSON
    if (props.type === 'select') {
      try {
        JSON.parse(formData.value.options)
      } catch (e) {
        throw new Error('选项配置必须是有效的JSON格式')
      }
    }

    emit('updateOptions', formData.value)
    return true
  } catch (error) {
    ElMessage.error(error.message || '表单验证失败')
    return false
  }
}

// 监听props变化
watch(() => props.field, initFormData, { deep: true })

// 初始化
onMounted(initFormData)

// 暴露方法给父组件
defineExpose({
  validate
})
</script>

<style lang="less" scoped>
.mb-3 {
  margin-bottom: 12px;
}

.el-form-item {
  margin-bottom: 18px;
}

.el-form-item__tip {
  font-size: 12px;
  color: #909399;
  margin-top: 4px;
}
</style>
