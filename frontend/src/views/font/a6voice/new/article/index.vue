<template>
  <t-card class="management-card">
    <div class="sub-page-header">
      <t-button theme="default" variant="text" @click="router.back()">
        
      返回</t-button>
      <span class="sub-page-title">创建新帖子</span>
    </div>
    <t-divider></t-divider>
    <t-form ref="formRef" :data="form" label-width="80px" :rules="formRules" class="new-article-form">
      <t-form-item label="板块" name="category">
        <t-radio-group v-model="form.category" size="small" @change="getExamin">
          <t-radio-button v-for="item in dictStore.getDictByNames('cyt_artical_category', 1).filter(
            (item) => item.status == 1
          )" :key="'2' + item.code" :value="parseInt(item.code)">{{ item.codeval }}</t-radio-button>
        </t-radio-group>
        {{ examineInfo }}
      </t-form-item>
      <t-form-item label="标题" name="title">
        <t-input size="small" v-model="form.title"></t-input>
      </t-form-item>
      <t-form-item label="正文" name="content">
        <WangEditor v-model="form.content"></WangEditor>
      </t-form-item>
      <t-form-item label="其他附件" name="files">
        <t-upload class="upload-demo" :headers="uploadHeaders" :action="fsURL + 'upload/file/cytFile'" @success="uploadFileSuccess"
          @remove="handleRemove" :before-remove="beforeRemove" multiple :limit="3" @exceed="handleExceed"
          :file-list="fileList">
          <t-button size="small" theme="primary">点击上传</t-button>
          <div>
            限制最多三个文件。请勿上传与项目无关的附件，且单个文件不超过 5MB
          </div>
        </t-upload>
      </t-form-item>
      <t-form-item v-if="categoryFlags && categoryFlags.anonFlag" label="是否匿名" name="anonFlag">
        <t-radio-group v-model="form.anonFlag" size="small">
          <t-radio-button value="0">实名</t-radio-button>
          <t-radio-button value="1">匿名</t-radio-button>
        </t-radio-group>
      </t-form-item>
      <t-form-item class="form-footer">
        <t-button theme="primary" size="small" :disabled="form.content.length < 1 ? true : false && submitStat"
          @click="onSubmit()">{{ submitStat ? '正在发布' : '立即发布' }}</t-button>
        <t-button size="small" @click="onSubmit(2)">存草稿</t-button>
      </t-form-item>
    </t-form>
  </t-card>
</template>

<script setup>
const uploadHeaders = { Authorization: localStorage.getItem('token') || '' }
import { ref, reactive, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { MessagePlugin, DialogPlugin } from 'tdesign-vue-next'
import { httpInstance } from '@/utils/request'
import WangEditor from '@/components/WangEditor.vue'
import { useDictStore } from '@/stores'

const router = useRouter()
const route = useRoute()
const dictStore = useDictStore()
const formRef = ref(null)

const fsURL = import.meta.env.VITE_FILE_BASE_URL
const id = ref('')
const examineInfo = ref('')
const form = reactive({
  category: 1,
  articleType: 2,
  title: '',
  content: '',
  files: '',
  status: 1,
  anonFlag: 0
})

const formRules = {
  category: [
    { required: true, message: '请选择板块', trigger: 'change' }
  ],
  title: [
    { required: true, message: '请输入帖子标题', trigger: 'blur' },
    { max: 100, min: 2, message: '请输入 2-100 个字符的帖子标题' }
  ],
  content: [
    { required: true, message: '请输入帖子内容', trigger: 'blur' },
    { min: 50, message: '请输入至少 50 个字符以上到内容！' }
  ]
}

const submitStat = ref(false)
const fileList = ref([])
const files = ref([])
const categoryFlags = ref({})

const initArticle = async () => {
  if (id.value !== 'new') {
    const res = await httpInstance.get(`cyt/article/${id.value}`)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    Object.assign(form, res.data)
    getExamin()
    if (form.files) {
      const Object = JSON.parse(form.files)
      for (let i = 0; i < Object.length; i++) {
        fileList.value.push({ name: Object[i].name, url: Object[i].path })
      }
    }
  } else {
    getExamin()
  }
}

const goBack = () => {
  router.back()
}

const onSubmit = async (status) => {
  const valid = await formRef.value.validate()
  if (valid !== true) return

  try {
    submitStat.value = true
    if (status != null) form.status = status
    if (files.value !== null && files.value.length !== 0)
      form.files = '[' + files.value.join(',') + ']'
    
    let res
    if (id.value !== 'new') {
      res = await httpInstance.put('cyt/article', form)
    } else {
      res = await httpInstance.post('cyt/article', form)
    }
    
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      submitStat.value = false
      return
    }
    
    submitStat.value = false
    goBack()
  } catch (error) {
    submitStat.value = false
    MessagePlugin.error('提交失败，请完整填写内容！')
  }
}

const getExamin = () => {
  form.anonFlag = 0
  const dictList = dictStore.getDictByNames('cyt_artical_category', 1)
  const category = dictList.find(
    (item) => parseInt(item.code) === form.category && item.status === 1
  )
  categoryFlags.value = category?.description
    ? JSON.parse(category.description)
    : {}
  
  if (categoryFlags.value.examine) {
    form.status = 3
    examineInfo.value = '当前板块发帖需要审核！'
  } else {
    form.status = 1
    examineInfo.value = ''
  }
}

const handleRemove = ({ file }) => {
  const removed = file?.response?.data?.file
  if (!removed) return
  files.value = files.value.filter((item) => {
    try {
      return JSON.parse(item).fileId !== removed.fileId
    } catch {
      return true
    }
  })
}

const uploadFileSuccess = ({ response }) => {
  if (response.code !== 200) return MessagePlugin.error(response.msg)
  files.value.push(JSON.stringify(response.data.file))
}

const handleExceed = ({ files, currentFiles }) => {
  MessagePlugin.warning(
    `当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + currentFiles.length} 个文件`
  )
}

const beforeRemove = (file) => {
  return new Promise((resolve) => {
    DialogPlugin.confirm({
      header: '确认删除',
      body: `确定移除 ${file.name} 吗？`,
      theme: 'warning',
      confirmBtn: '确定',
      cancelBtn: '取消',
      onConfirm: () => resolve(true),
      onClose: () => resolve(false)
    })
  })
}

onMounted(() => {
  id.value = route.params.id
  initArticle()
})
</script>

<style lang="less" scoped>
.management-card {
  display: flex;
  flex-direction: column;
}

.new-article-form {
  max-width: 900px;
}
</style>
