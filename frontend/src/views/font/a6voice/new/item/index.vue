<template>
  <el-card class="box-card">
    <el-page-header @back="() => router.back()" content="创建新项目"></el-page-header>
    <el-divider></el-divider>
    <el-form ref="formRef" :model="form" label-width="80px" size="small" :rules="formRules">
      <el-form-item label="板块" prop="category">
        <el-radio-group v-model="form.category" size="small" @change="getExamin">
          <el-radio-button v-for="item in dictStore.getDictByNames('cyt_item_category', 1).filter(
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
      <el-form-item label="责任部门" prop="restDept">
        <el-select size="small" style="width: 100%" @change="changeRespDept" v-model="respDepts" multiple filterable
          allow-create default-first-option placeholder="请选择责任部门（可多选）">
          <el-option v-for="item in dictStore.getDictByNames('cyt_artical_resp_dept', 1)" :key="item.id"
            :label="item.codeval" :value="item.code"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="封面图片" prop="cover">
        <el-upload class="avatar-upload" :action="fsURL + 'upload/cytCover'" :show-file-list="false"
          :on-success="handleCoverSuccess" :before-upload="beforeCoverUpload">
          <img v-if="form.cover" :src="fsURL + form.cover" class="avatar" />
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
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
        <el-button size="small" type="primary" :disabled="form.content.length < 1 ? true : false"
          @click="onSubmit(3)">立即发布</el-button>
        <el-button size="small" @click="onSubmit(2)">存草稿</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { httpInstance } from '@/utils/request'
import WangEditor from '@/components/WangEditor.vue'
import { useDictStore } from '@/stores'

const router = useRouter()
const route = useRoute()
const dictStore = useDictStore()
const formRef = ref(null)

const fsURL = import.meta.env.VITE_FILE_MANAGE_BASE || import.meta.env.VITE_API_BASE_URL
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
    if (res.code !== 200) return ElMessage.error(res.msg)
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
  
  if (res.code !== 200) return ElMessage.error(res.msg)
  ElMessage.success(res.msg)
  goBack()
}

const handleCoverSuccess = async (res, file) => {
  if (res.code !== 200) return ElMessage.error(res.msg)
  ElMessage.success(res.msg)
  form.cover = res.path
}

const beforeCoverUpload = (file) => {
  const isJPG = file.type === 'image/jpeg'
  const isLimitSize = file.size / 1024 < 512
  if (!isJPG) {
    ElMessage.error('上传封面图片只能是 JPG 格式!')
  }
  if (!isLimitSize) {
    ElMessage.error('上传封面图片大小不能超过 512KB!')
  }
  return isJPG && isLimitSize
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
  files.value.push(JSON.stringify(res.file))
}

const handleExceed = (files, fileList) => {
  ElMessage.warning(
    `当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`
  )
}

const beforeRemove = (file, fileList) => {
  return ElMessageBox.confirm(`确定移除 ${file.name} ？`, {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
}

const getExamin = () => {
  form.anonFlag = 0
  const dictList = dictStore.getDictByNames('cyt_item_category', 1)
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

.avatar-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-upload:hover {
  border-color: #409eff;
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
