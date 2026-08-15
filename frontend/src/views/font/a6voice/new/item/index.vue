<template>
  <t-card class="management-card">
    <div class="sub-page-header">
      <t-button theme="default" variant="text" @click="router.back()">
        
      返回</t-button>
      <span class="sub-page-title">创建新项目</span>
    </div>
    <t-divider></t-divider>
    <t-form ref="formRef" :data="form" label-width="80px" size="small" :rules="formRules">
      <t-form-item label="板块" name="category">
        <t-radio-group v-model="form.category" size="small" @change="getExamin">
          <t-radio-button v-for="item in dictStore.getDictByNames('cyt_item_category', 1).filter(
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
      <t-form-item label="责任部门" name="restDept">
        <t-select size="small" style="width: 100%" @change="changeRespDept" v-model="respDepts" multiple filterable
          allow-create default-first-option placeholder="请选择责任部门（可多选）">
          <t-option v-for="item in dictStore.getDictByNames('cyt_artical_resp_dept', 1)" :key="item.id"
            :label="item.codeval" :value="item.code"></t-option>
        </t-select>
      </t-form-item>
      <t-form-item label="封面图片" name="cover">
        <t-upload class="avatar-upload" :headers="uploadHeaders" :action="fsURL + 'upload/cytCover'" :show-file-list="false"
          @success="handleCoverSuccess" :before-upload="beforeCoverUpload">
          <img v-if="form.cover" :src="fsURL + form.cover" class="avatar" />
          <i v-else class="avatar-uploader-icon"></i>
        </t-upload>
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
      <t-form-item>
        <t-button size="small" theme="primary" :disabled="form.content.length < 1 ? true : false"
          @click="onSubmit(3)">立即发布</t-button>
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
const cover = ref([])
const form = reactive({
  category: '',
  respDept: '',
  articalType: 1,
  title: '',
  content: '',
  cover: '',
  files: '',
  status: 0,
  compType: 6,
  anonFlag: 0
})

const respDepts = ref([])

const formRules = {
  category: [
    { required: true, message: '请选择板块', trigger: 'blur' },
    { max: 10, min: 2, message: '请正确选择帖子类别' }
  ],
  title: [
    { required: true, message: '请输入帖子标题', trigger: 'blur' },
    { max: 100, min: 2, message: '请输入 2-100 个字符的帖子标题' }
  ],
  content: [
    { required: true, message: '请输入帖子内容', trigger: 'blur' },
    { min: 20, message: '请输入至少 20 个字符以上到内容！' }
  ]
}

const fileList = ref([])
const files = ref([])
const examineInfo = ref('')
const categoryFlags = ref({})

const initArtical = async () => {
  if (id.value !== 'new') {
    const res = await httpInstance.get(`cyt/artical/${id.value}`)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    Object.assign(form, res.data)
    respDepts.value = form.respDept.split(',')
    if (form.files) {
      const Object = JSON.parse(form.files)
      for (let i = 0; i < Object.length; i++) {
        fileList.value.push({ name: Object[i].name, url: Object[i].path })
      }
    }
  }
}

const goBack = () => {
  router.back()
}

const changeRespDept = () => {
  form.respDept = respDepts.value.join(',')
}

const onSubmit = async (status) => {
  form.status = status
  if (files.value !== null && files.value.length !== 0)
    form.files = '[' + files.value.join(',') + ']'
  
  let res
  if (form.id) {
    res = await httpInstance.put('cyt/artical', form)
  } else {
    res = await httpInstance.post('cyt/artical', form)
  }
  
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  MessagePlugin.success(res.msg)
  goBack()
}

const handleCoverSuccess = ({ response }) => {
  if (response.code !== 200) return MessagePlugin.error(response.msg)
  MessagePlugin.success(response.msg)
  form.cover = response.data.file.path
}

const beforeCoverUpload = (file) => {
  const isJPG = file.type === 'image/jpeg'
  const isLimitSize = file.size / 1024 < 512
  if (!isJPG) {
    MessagePlugin.error('上传封面图片只能是 JPG 格式!')
  }
  if (!isLimitSize) {
    MessagePlugin.error('上传封面图片大小不能超过 512KB!')
  }
  return isJPG && isLimitSize
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

const getExamin = () => {
  form.anonFlag = 0
  const dictList = dictStore.getDictByNames('cyt_item_category', 1)
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

onMounted(() => {
  id.value = route.params.id
  initArtical()
})
</script>

<style lang="less" scoped>
.management-card {
  display: flex;
  flex-direction: column;
}

.avatar-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-upload:hover {
  border-color: var(--td-brand-color);
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 400px;
  height: 300px;
  line-height: 300px;
  text-align: center;
}

.avatar {
  width: 400px;
  height: 300px;
  display: block;
}
</style>
