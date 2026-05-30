<template>
  <el-dialog 
    :title="dialogTitle" 
    v-model="dialogVisible" 
    width="40%" 
    :close-on-click-modal="false"
  >
    <el-form 
      ref="areaConfigFormRef" 
      :model="areaConfigForm" 
      label-position="right" 
      label-width="100px" 
      size="small" 
      :rules="areaConfigRules"
    >
      <el-form-item label="配置名称" prop="areaName">
        <el-input v-model="areaConfigForm.areaName"></el-input>
      </el-form-item>
      <el-form-item label="配置描述" prop="areaDesc">
        <el-input v-model="areaConfigForm.areaDesc"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="areaStat">
        <el-select v-model="areaConfigForm.areaStat" placeholder="请选择状态">
          <el-option label="生效" :value="1"></el-option>
          <el-option label="失效" :value="0"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">取 消</el-button>
        <el-button size="small" type="primary" @click="submitAreaConfig">确 定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { examAreaConfigApi } from '@/api/edu/examAreaConfig'

// Props
const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  },
  dialogTitle: {
    type: String,
    default: ''
  },
  formData: {
    type: Object,
    default: () => ({
      areaCode: '',
      areaName: '',
      areaDesc: '',
      areaStat: 1
    })
  }
})

// Emits
const emit = defineEmits(['update:visible', 'success'])

// Computed
const dialogVisible = computed({
  get: () => props.visible,
  set: (val) => emit('update:visible', val)
})

// Reactive data
const areaConfigFormRef = ref(null)
const areaConfigForm = reactive({ ...props.formData })

const areaConfigRules = reactive({
  areaName: [{ required: true, min: 3, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur' }],
  areaDesc: [{ required: true, message: '请输入相关描述，该描述会显示于前端', trigger: 'blur' }],
  areaStat: [{ required: true, message: '选择状态', trigger: 'blur' }]
})

// Methods
const submitAreaConfig = async () => {
  const valid = await examAreaConfigApi.areaConfigFormRef.value.validate()
  if (!valid) return

  try {
    let res
    if (areaConfigForm.areaCode) {
      res = await examAreaConfigApi.updateAreaConfig(areaConfigForm)
    } else {
      res = await examAreaConfigApi.addAreaConfig(areaConfigForm)
    }

    if (res.code !== 200) return ElMessage.error(res.msg)
    ElMessage.success(res.msg)
    emit('success')
    dialogVisible.value = false
  } catch (error) {
    ElMessage.error('操作失败，请重试')
  }
}
</script>

<style scoped>

</style>
