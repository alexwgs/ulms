<template>
  <div>
    <el-dialog
      title="附件管理"
      v-model="dialogVisible"
      :close-on-click-modal="false"
      width="80%"
      :before-close="handleClose"
    >
      <el-alert
        title="1.先选择上传文件类型。2.点击放大镜选择文件。3.可自行修改文件学习时长及文件显示名称！"
        type="info"
      ></el-alert>
      <el-row :gutter="15" v-loading="loading">
        <el-col :span="6">
          <el-radio-group
            v-model="formData.fileType"
            size="small"
            @change="changeFiletype"
          >
            <el-radio-button :value="1" label="学习附件"></el-radio-button>
            <el-radio-button :value="2" label="学习资料"></el-radio-button>
          </el-radio-group>
        </el-col>
        <el-col :span="4">
          <el-input
            v-model="formData.duration"
            size="small"
            placeholder="学习时长"
          ></el-input>
        </el-col>
        <el-col :span="10">
          <el-upload
            ref="fileUploadRef"
            :action="fsURL + uploadUrl"
            :on-success="handleSuccess"
            :before-upload="getFileDuration"
            :auto-upload="false"
            :multiple="false"
            :limit="1"
            :accept="
              formData.fileType == 1
                ? 'video/mp4, audio/mpeg, application/pdf, application/vnd.ms-powerpoint'
                : ''
            "
            :show-file-list="true"
            :on-change="handleChange"
            :file-list="fileList"
          >
            <el-input
              v-model="formData.fileName"
              style="width: 100%"
              size="small"
              placeholder="请选择上传文件"
              readonly
            >
              <template #append>
                <el-button size="small" icon="Search"></el-button>
              </template>
            </el-input>
          </el-upload>
        </el-col>
        <el-col :span="4">
          <el-button
            size="small"
            icon="Upload"
            type="primary"
            @click="submitFile"
            >上传</el-button
          >
        </el-col>
      </el-row>
      <el-row :gutter="15">
        <el-col :span="14">
          <el-table
            :data="video"
            size="small"
            border
            style="width: 100%; margin-top: 10px"
            height="300"
          >
            <el-table-column prop="fileName" label="文件名">
              <template #default="scope">
                <el-input
                  v-model="scope.row.fileName"
                  size="small"
                  placeholder="请输入文件名"
                ></el-input>
              </template>
            </el-table-column>
            <el-table-column prop="duration" label="时长(S)" width="80">
              <template #default="scope">
                <el-input
                  v-model="scope.row.duration"
                  size="small"
                  placeholder="请输入时长"
                ></el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop="extenName"
              label="扩展名"
              width="60"
            ></el-table-column>
            <el-table-column prop="status" label="状态" width="70">
              <template #default="scope">
                <el-tag
                  :type="scope.row.status ? 'success' : 'danger'"
                  @click="changeStatus(scope.row)"
                  >{{ scope.row.status ? '有效' : '无效' }}</el-tag
                >
              </template>
            </el-table-column>
            <el-table-column prop="fileOrder" label="排序" width="80">
              <template #default="scope">
                <el-input
                  type="number"
                  v-model="scope.row.fileOrder"
                  size="small"
                  placeholder="数字越大越靠前"
                ></el-input>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
        <el-col :span="10">
          <el-table
            :data="file"
            size="small"
            border
            style="width: 100%; margin-top: 10px"
            height="300"
          >
            <el-table-column prop="fileName" label="文件名">
              <template #default="scope">
                <el-input
                  v-model="scope.row.fileName"
                  size="small"
                  placeholder="请输入文件名"
                ></el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop="extenName"
              label="扩展名"
              width="60"
            ></el-table-column>
            <el-table-column prop="status" label="状态" width="70">
              <template #default="scope">
                <el-tag
                  :type="scope.row.status ? 'success' : 'danger'"
                  @click="changeStatus(scope.row)"
                  >{{ scope.row.status ? '有效' : '无效' }}</el-tag
                >
              </template>
            </el-table-column>
            <el-table-column prop="fileOrder" label="排序" width="80">
              <template #default="scope">
                <el-input
                  type="number"
                  v-model="scope.row.fileOrder"
                  size="small"
                  placeholder="数字越大越靠前"
                ></el-input>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
      <template #footer>
        <span class="dialog-footer">
          <el-button size="small" @click="dialogVisible = false"
            >取 消</el-button
          >
          <el-button size="small" type="primary" @click="batchUpdate()"
            >更 新</el-button
          >
        </span>
      </template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, reactive, watch } from 'vue'
import { ElMessage } from 'element-plus'
import { Search, Upload } from '@element-plus/icons-vue'
import { courseFileApi } from '@/api/college/courseFile'

// 获取VITE的环境参数
const fsURL = import.meta.env.VITE_FILE_BASE_URL

const dialogVisible = ref(false)
const loading = ref(false)
const fileUploadRef = ref(null)
const courseId = ref('')
const courseFiles = ref([])
const video = ref([])
const file = ref([])
const uploadUrl = ref('upload/file/college-media')
const fileList = ref([])
const emit = defineEmits(['refresh'])

const formData = reactive({
  fileType: 1,
  duration: 0,
  fileName: ''
})

const show = (courseFilesData, courseIdData) => {
  courseId.value = courseIdData
  courseFiles.value = JSON.parse(JSON.stringify(courseFilesData))

  video.value = courseFiles.value
    .filter((e) => e.fileType === 1)
    .sort(function (a, b) {
      if (a.fileOrder > b.fileOrder) return 1
      else return -1
    })

  file.value = courseFiles.value
    .filter((e) => e.fileType === 2)
    .sort(function (a, b) {
      if (a.fileOrder > b.fileOrder) return 1
      else return -1
    })

  dialogVisible.value = true
}

const changeStatus = (row) => {
  row.status = row.status ? 0 : 1
}

const handleChange = (fileData) => {
  const fileSize = (fileData.size / 1024 / 1024).toFixed(2)
  if (parseFloat(fileSize) >= 300) {
    if (fileUploadRef.value) {
      fileUploadRef.value.clearFiles()
    }
    return ElMessage.error('上传文件过大！最大300MB，当前：' + fileSize + 'MB')
  }

  formData.fileName = fileData.name
  getFileDuration(fileData)
}

const handleSuccess = (response, fileData) => {
  loading.value = false
  if (response.code !== 200) {
    clearFileUpload()
    return ElMessage.error(response.msg)
  }

  const upFile = {
    courseId: courseId.value,
    fileId: response.data.file.fileId,
    fileName: response.data.file.name,
    extenName: response.data.file.fileSuffix,
    fileOrder: 0,
    status: 1,
    fileType: formData.fileType,
    duration: formData.duration
  }

  insert(upFile)

  if (upFile.fileType === 1) {
    video.value.push(upFile)
  } else {
    file.value.push(upFile)
  }

  clearFileUpload()
}

const submitFile = () => {
  if (fileUploadRef.value) {
    fileUploadRef.value.submit()
  }
  loading.value = true
}

const clearFileUpload = () => {
  if (fileUploadRef.value) {
    fileUploadRef.value.clearFiles()
  }
  // 上传成功后数据重置
  formData.duration = 0
  formData.fileName = ''
  fileList.value = []
}

const getFileDuration = (fileData) => {
  const fileName = fileData.name || ''
  const ext = fileName.split('.')[fileName.split('.').length - 1].toLowerCase()

  if (ext === 'mp4' || ext === 'mp3') {
    const url = URL.createObjectURL(fileData)
    const audioElement = new Audio(url)
    audioElement.addEventListener('loadedmetadata', function (_event) {
      formData.duration = audioElement.duration.toFixed(0) // 时长为秒，小数，182.36
    })
  } else if (ext === 'pdf') {
    formData.duration = 300
  } else if (formData.fileType === 1) {
    ElMessage.error('您所提交的文件格式不支持！目前仅支持【mp3、mp4】')
    return false
  }
}

const insert = async (record) => {
  try {
    const res = await courseFileApi.addCourseFile(record)
    if (res.code !== 200) return ElMessage.error(res.msg)
    ElMessage.success(res.msg)
    emit('refresh')
  } catch (error) {
    ElMessage.error('添加文件失败')
  }
}

const batchUpdate = async () => {
  try {
    const res = await courseFileApi.updateCourseFile(
      video.value.concat(file.value)
    )
    if (res.code !== 200) return ElMessage.error(res.msg)
    ElMessage.success(res.msg)
    dialogVisible.value = false
  } catch (error) {
    ElMessage.error('批量更新失败')
  }
}

const changeFiletype = () => {
  if (formData.fileType === 1) {
    uploadUrl.value = 'upload/file/college-media'
  } else if (formData.fileType === 2) {
    uploadUrl.value = 'upload/file/college-document'
  }
}

const handleClose = () => {
  dialogVisible.value = false
}

defineExpose({
  show
})
</script>
<style lang="less" scoped></style>
