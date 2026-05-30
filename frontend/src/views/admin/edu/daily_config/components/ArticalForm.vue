<template>
  <el-dialog
    title="公布栏文章配置"
    v-model="dialogVisible"
    width="80%"
    :close-on-click-modal="false"
  >
    <el-form
      ref="articalFormRef"
      :model="articalForm"
      size="small"
      label-width="80px"
      :rules="articalFormRules"
    >
      <el-form-item label="标题" prop="title">
        <el-input v-model="articalForm.title"></el-input>
      </el-form-item>
      <el-form-item label="正文" prop="content">
        <WangEditor
          v-model="articalForm.content"
          :height="400"
          :placeholder="'请输入文章内容...'"
        ></WangEditor>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" size="small" @click="handleSubmit">
          {{ articalForm.id ? '修改' : '新增' }}
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, reactive, watch, computed } from 'vue'
import { ElMessage } from 'element-plus'
import WangEditor from '@/components/WangEditor.vue'
import { dailyConfigApi } from '@/api/edu/dailyConfig'

// Props
const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  },
  articalData: {
    type: Object,
    default: () => ({
      id: null,
      articalType: 9,
      category: 2,
      title: '',
      content: '',
      status: 1
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
const articalFormRef = ref(null)
const articalForm = reactive({
  id: null,
  articalType: 9,
  category: 2,
  title: '',
  content: '',
  status: 1
})

const articalFormRules = reactive({
  title: [{ required: true, message: '标题不能为空', trigger: 'blur' }],
  content: [{ required: true, message: '正文不能为空', trigger: 'blur' }]
})

// Watch
watch(
  () => props.articalData,
  (newData) => {
    Object.assign(articalForm, newData)
  },
  { deep: true, immediate: true }
)

// Methods
const handleSubmit = async () => {
  const valid = await articalFormRef.value.validate()
  if (!valid) return

  try {
    let res
    if (articalForm.id) {
      res = await dailyConfigApi.updateArtical(articalForm)
    } else {
      res = await dailyConfigApi.addArtical(articalForm)
    }

    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }

    ElMessage.success(res.msg)
    emit('success', res.data)
    dialogVisible.value = false
  } catch (error) {
    ElMessage.error('操作失败，请重试')
  }
}
</script>

<style scoped></style>
