<template>
  <el-card class="box-card">
    <el-page-header @back="() => router.back()" content="创建新帖子">
    </el-page-header>
    <el-divider></el-divider>
    <el-form ref="formRef" :model="form" label-width="80px" :rules="formRules">
      <el-form-item label="板块" prop="category">
        <el-radio-group v-model="form.category" size="small" @change="getExamin">
          <el-radio-button v-for="item in dictStore.getDictByNames('cyt_artical_category', 1).filter(
            (item) => item.status == 1
          )" :key="'2' + item.code" :label="parseInt(item.code)">{{ item.codeval }}</el-radio-button>
        </el-radio-group>
        {{ examineInfo }}
      </el-form-item>
      <el-form-item label="标题" prop="title">
        <el-input size="small" v-model="form.title"></el-input>
      </el-form-item>
      <el-form-item label="正文" prop="content">
        <WangEditor v-model="form.content"></WangEditor>
      </el-form-item>
      <el-form-item label="其他附件" prop="files">
        <el-upload class="upload-demo" :action="fsURL + 'upload/file/cytFile'" :on-success="uploadFileSuccess"
          :on-remove="handleRemove" :before-remove="beforeRemove" multiple :limit="3" :on-exceed="handleExceed"
          :file-list="fileList">
          <el-button size="small" type="primary">点击上传</el-button>
          <div class="el-upload__tip">
            限制最多三个文件。请勿上传与项目无关的附件，且单个文件不超过 5MB
          </div>
        </el-upload>
      </el-form-item>
      <el-form-item v-if="categoryFlags && categoryFlags.anonFlag" label="是否匿名" prop="anonFlag">
        <el-radio-group v-model="form.anonFlag" size="small">
          <el-radio-button label="0">实名</el-radio-button>
          <el-radio-button label="1">匿名</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="small" :disabled="form.content.length < 1 ? true : false && submitStat"
          @click="onSubmit()">{{ submitStat ? '正在发布' : '立即发布' }}</el-button>
        <el-button size="small" @click="onSubmit(2)">存草稿</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { httpInstance } from '@/utils/request'
import WangEditor from '@/components/WangEditor.vue'
import { useDictStore } from '@/stores'

const router = useRouter()
const route = useRoute()
const dictStore = useDictStore()
const formRef = ref(null)

const fsURL = import.meta.env.VITE_FILE_MANAGE_BASE || import.meta.env.VITE_API_BASE_URL
const id = ref('')
const examineInfo = ref('')
const form = reactive({
  category: 1,
  articalType: 2,
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

const initArtical = async () => {
  if (id.value !== 'new') {
    const res = await httpInstance.get(`cyt/artical/${id.value}`)
    if (res.code !== 200) return ElMessage.error(res.msg)
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
  try {
    await formRef.value.validate()
    submitStat.value = true
    if (status != null) form.status = status
    if (files.value !== null && files.value.length !== 0)
      form.files = '[' + files.value.join(',') + ']'
    
    let res
    if (id.value !== 'new') {
      res = await httpInstance.put('cyt/artical', form)
    } else {
      res = await httpInstance.post('cyt/artical', form)
    }
    
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      submitStat.value = false
      return
    }
    
    submitStat.value = false
    goBack()
  } catch (error) {
    submitStat.value = false
    ElMessage.error('提交失败，请完整填写内容！')
  }
}

const getExamin = () => {
  form.anonFlag = 0
  const dictList = dictStore.getDictByNames('cyt_artical_category', 1)
  const category = dictList.find(
    (item) => parseInt(item.code) === form.category && item.status === 1
  )
  categoryFlags.value = JSON.parse(category.description)
  
  if (categoryFlags.value.examine) {
    form.status = 3
    examineInfo.value = '当前板块发帖需要审核！'
  } else {
    form.status = 1
    examineInfo.value = ''
  }
}

const handleRemove = (file, fileList) => {
  files.value = []
  for (let i = 0; i < fileList.length; i++) {
    if (fileList[i].response && fileList[i].response.file) {
      files.value.push(JSON.stringify(fileList[i].response.file))
    }
  }
}

const uploadFileSuccess = (res, fileList) => {
  files.value.push(JSON.stringify(res.data.file))
}

const handleExceed = (files, fileList) => {
  ElMessage.warning(
    `当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`
  )
}

const beforeRemove = (file, fileList) => {
  return ElMessageBox.confirm(`确定移除 ${file.name} ？`, '确认删除', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    for (let i = 0; i < fileList.length; i++) {
      if (file.uid === fileList[i].uid) {
        fileList.splice(i, 1)
        return
      }
    }
  }).catch(() => {
  })
}

onMounted(() => {
  id.value = route.params.id
  initArtical()
})
</script>

<style lang="less" scoped>
.box-card {
  display: flex;
  flex-direction: column;
}
</style>
