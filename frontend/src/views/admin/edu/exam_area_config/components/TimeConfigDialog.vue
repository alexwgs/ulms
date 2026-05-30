<template>
  <el-dialog 
    :title="dialogTitle" 
    v-model="dialogVisible" 
    width="40%" 
    :close-on-click-modal="false"
  >
    <el-form 
      ref="timeConfigRef" 
      :model="timeConfigForm" 
      label-position="right" 
      label-width="100px" 
      size="small" 
      :rules="timeConfigRules"
    >
      <el-form-item label="配置名称" prop="bookName">
        <el-input v-model="timeConfigForm.bookName"></el-input>
      </el-form-item>
      <el-form-item label="配置描述" prop="bookDesc">
        <el-input v-model="timeConfigForm.bookDesc"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="bookStat">
        <el-select v-model="timeConfigForm.bookStat" placeholder="请选择状态">
          <el-option label="生效" :value="1"></el-option>
          <el-option label="失效" :value="0"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">取 消</el-button>
        <el-button size="small" type="primary" @click="submitTimeConfig">确 定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { bookInfoApi } from '@/api/edu/bookInfo'

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
      bookCode: '',
      bookName: '',
      bookDesc: '',
      bookStat: 1
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
const timeConfigRef = ref(null)
const timeConfigForm = reactive({ ...props.formData })

const timeConfigRules = reactive({
  bookName: [{ required: true, min: 3, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur' }],
  bookDesc: [{ required: true, message: '请输入相关描述，该描述会显示于前端', trigger: 'blur' }],
  bookStat: [{ required: true, message: '选择状态', trigger: 'blur' }]
})

// Methods
const submitTimeConfig = async () => {
  const valid = await timeConfigRef.value.validate()
  if (!valid) return

  try {
    let res
    if (timeConfigForm.bookCode) {
      res = await bookInfoApi.updateBookConfig(timeConfigForm)
    } else {
      res = await bookInfoApi.addBookConfig(timeConfigForm)
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
