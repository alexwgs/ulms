<template>
  <t-dialog
    header="公布栏文章配置"
    v-model:visible="dialogVisible"
    width="80%"
    :close-on-overlay-click="false"
  >
    <t-form
      ref="articalFormRef"
      :data="articalForm"
      size="small"
      label-width="80px"
      :rules="articalFormRules"
    >
      <t-form-item label="标题" name="title">
        <t-input v-model="articalForm.title"></t-input>
      </t-form-item>
      <t-form-item label="正文" name="content">
        <WangEditor
          v-model="articalForm.content"
          :height="400"
          :placeholder="'请输入文章内容...'"
        ></WangEditor>
      </t-form-item>
    </t-form>
    <template #footer>
      <span class="dialog-footer">
        <t-button size="small" @click="dialogVisible = false">取 消</t-button>
        <t-button theme="primary" size="small" @click="handleSubmit">
          {{ articalForm.id ? '修改' : '新增' }}
        </t-button>
      </span>
    </template>
  </t-dialog>
</template>

<script setup>
import { ref, reactive, watch, computed } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
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
  if (valid !== true) return

  try {
    let res
    if (articalForm.id) {
      res = await dailyConfigApi.updateArtical(articalForm)
    } else {
      res = await dailyConfigApi.addArtical(articalForm)
    }

    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }

    MessagePlugin.success(res.msg)
    emit('success', res.data)
    dialogVisible.value = false
  } catch (error) {
    MessagePlugin.error('操作失败，请重试')
  }
}
</script>

<style scoped></style>
