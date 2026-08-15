<template>
  <t-dialog
    header="公布栏文章配置"
    v-model:visible="dialogVisible"
    width="80%"
    :close-on-overlay-click="false"
  >
    <t-form
      ref="articleFormRef"
      :data="articleForm"
      size="small"
      label-width="80px"
      :rules="articleFormRules"
    >
      <t-form-item label="标题" name="title">
        <t-input v-model="articleForm.title"></t-input>
      </t-form-item>
      <t-form-item label="正文" name="content">
        <WangEditor
          v-model="articleForm.content"
          :height="400"
          :placeholder="'请输入文章内容...'"
        ></WangEditor>
      </t-form-item>
    </t-form>
    <template #footer>
      <span class="dialog-footer">
        <t-button size="small" @click="dialogVisible = false">取 消</t-button>
        <t-button theme="primary" size="small" @click="handleSubmit">
          {{ articleForm.id ? '修改' : '新增' }}
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
  articleData: {
    type: Object,
    default: () => ({
      id: null,
      articleType: 9,
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
const articleFormRef = ref(null)
const articleForm = reactive({
  id: null,
  articleType: 9,
  category: 2,
  title: '',
  content: '',
  status: 1
})

const articleFormRules = reactive({
  title: [{ required: true, message: '标题不能为空', trigger: 'blur' }],
  content: [{ required: true, message: '正文不能为空', trigger: 'blur' }]
})

// Watch
watch(
  () => props.articleData,
  (newData) => {
    Object.assign(articleForm, newData)
  },
  { deep: true, immediate: true }
)

// Methods
const handleSubmit = async () => {
  const valid = await articleFormRef.value.validate()
  if (valid !== true) return

  try {
    let res
    if (articleForm.id) {
      res = await dailyConfigApi.updateArticle(articleForm)
    } else {
      res = await dailyConfigApi.addArticle(articleForm)
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
