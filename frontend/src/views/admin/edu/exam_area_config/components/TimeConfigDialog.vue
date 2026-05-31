<template>
  <t-dialog 
    :header="dialogTitle" 
    v-model:visible="dialogVisible" 
    width="40%" 
    :close-on-overlay-click="false"
  >
    <t-form 
      ref="timeConfigRef" 
      :data="timeConfigForm" 
      label-align="right" 
      label-width="100px" 
      size="small" 
      :rules="timeConfigRules"
    >
      <t-form-item label="配置名称" name="bookName">
        <t-input v-model="timeConfigForm.bookName"></t-input>
      </t-form-item>
      <t-form-item label="配置描述" name="bookDesc">
        <t-input v-model="timeConfigForm.bookDesc"></t-input>
      </t-form-item>
      <t-form-item label="状态" name="bookStat">
        <t-select v-model="timeConfigForm.bookStat" placeholder="请选择状态">
          <t-option label="生效" :value="1"></t-option>
          <t-option label="失效" :value="0"></t-option>
        </t-select>
      </t-form-item>
    </t-form>
    <template #footer>
      <span class="dialog-footer">
        <t-button size="small" @click="dialogVisible = false">取 消</t-button>
        <t-button size="small" theme="primary" @click="submitTimeConfig">确 定</t-button>
      </span>
    </template>
  </t-dialog>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
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
  if (valid !== true) return

  try {
    let res
    if (timeConfigForm.bookCode) {
      res = await bookInfoApi.updateBookConfig(timeConfigForm)
    } else {
      res = await bookInfoApi.addBookConfig(timeConfigForm)
    }

    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    emit('success')
    dialogVisible.value = false
  } catch (error) {
    MessagePlugin.error('操作失败，请重试')
  }
}
</script>

<style scoped>

</style>
