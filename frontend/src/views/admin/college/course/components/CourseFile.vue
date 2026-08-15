<template>
  <div>
    <t-dialog
      header="附件管理"
      v-model:visible="dialogVisible"
      :close-on-overlay-click="false"
      width="80%"
      :before-close="handleClose"
    >
      <t-alert
        title="1.先选择上传文件类型。2.点击放大镜选择文件。3.可自行修改文件学习时长及文件显示名称！"
        theme="info"
      ></t-alert>
      <t-loading :loading="loading">
        <t-row :gutter="15">
        <t-col :span="3">
          <t-radio-group
            v-model="formData.fileType"
            size="small"
            @change="changeFiletype"
          >
            <t-radio-button :value="1" label="学习附件"></t-radio-button>
            <t-radio-button :value="2" label="学习资料"></t-radio-button>
          </t-radio-group>
        </t-col>
        <t-col :span="2">
          <t-input
            v-model="formData.duration"
            size="small"
            placeholder="学习时长"
          ></t-input>
        </t-col>
        <t-col :span="5">
          <t-upload
            ref="fileUploadRef"
            :headers="uploadHeaders" :action="fsURL + uploadUrl"
            @success="handleSuccess"
            :before-upload="getFileDuration"
            :auto-upload="false"
            :multiple="false"
            :limit="1"
            :show-file-list="true"
            @change="handleChange"
            :file-list="fileList"
          >
          <t-input-adornment style="width: 100%">
            <template #append>
              <t-button variant="outline" theme="primary" size="small">搜索</t-button>
            </template>
            <t-input
              v-model="formData.fileName"
              size="small"
              placeholder="请选择上传文件"
              readonly
            ></t-input>
          </t-input-adornment>
          </t-upload>
        </t-col>
        <t-col :span="2">
          <t-button
            size="small"
            theme="primary"
            @click="submitFile"
            ><template #icon><DynamicIcon name="upload" /></template>上传</t-button
          >
        </t-col>
      </t-row>
      </t-loading>
      <t-row :gutter="15">
        <t-col :span="7">
          <CustomTable rowKey="id"
            :data="video"
            size="small"
            border
            style="width: 100%; margin-top: 10px"
            height="300">
            <TableColumn colKey="fileName" label="文件名">
              <template #default="scope">
                <t-input
                  v-model="scope.row.fileName"
                  size="small"
                  placeholder="请输入文件名"
                ></t-input>
              </template>
            </TableColumn>
            <TableColumn colKey="duration" label="时长(S)" width="80">
              <template #default="scope">
                <t-input
                  v-model="scope.row.duration"
                  size="small"
                  placeholder="请输入时长"
                ></t-input>
              </template>
            </TableColumn>
            <TableColumn
              prop="extenName"
              label="扩展名"
              width="60"></TableColumn>
            <TableColumn colKey="status" label="状态" width="70">
              <template #default="scope">
                <t-tag
                  :theme="scope.row.status ? 'success' : 'danger'"
                  @click="changeStatus(scope.row)"
                   variant="light">{{ scope.row.status ? '有效' : '无效' }}</t-tag
                >
              </template>
            </TableColumn>
            <TableColumn colKey="fileOrder" label="排序" width="80">
              <template #default="scope">
                <t-input
                  type="number"
                  v-model="scope.row.fileOrder"
                  size="small"
                  placeholder="数字越大越靠前"
                ></t-input>
              </template>
            </TableColumn>
          </CustomTable>
        </t-col>
        <t-col :span="5">
          <CustomTable rowKey="id"
            :data="file"
            size="small"
            border
            style="width: 100%; margin-top: 10px"
            height="300">
            <TableColumn colKey="fileName" label="文件名">
              <template #default="scope">
                <t-input
                  v-model="scope.row.fileName"
                  size="small"
                  placeholder="请输入文件名"
                ></t-input>
              </template>
            </TableColumn>
            <TableColumn
              prop="extenName"
              label="扩展名"
              width="60"></TableColumn>
            <TableColumn colKey="status" label="状态" width="70">
              <template #default="scope">
                <t-tag
                  :theme="scope.row.status ? 'success' : 'danger'"
                  @click="changeStatus(scope.row)"
                   variant="light">{{ scope.row.status ? '有效' : '无效' }}</t-tag
                >
              </template>
            </TableColumn>
            <TableColumn colKey="fileOrder" label="排序" width="80">
              <template #default="scope">
                <t-input
                  type="number"
                  v-model="scope.row.fileOrder"
                  size="small"
                  placeholder="数字越大越靠前"
                ></t-input>
              </template>
            </TableColumn>
          </CustomTable>
        </t-col>
      </t-row>
      <template #footer>
        <span class="dialog-footer">
          <t-button size="small" @click="dialogVisible = false"
            >取 消</t-button
          >
          <t-button size="small" theme="primary" @click="batchUpdate()"
            >更 新</t-button
          >
        </span>
      </template>
    </t-dialog>
  </div>
</template>
<script setup>
const uploadHeaders = { Authorization: localStorage.getItem('token') || '' }
import { ref, reactive, watch } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { SearchIcon, UploadIcon } from 'tdesign-icons-vue-next'
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
    fileList.value = []
    return MessagePlugin.error('上传文件过大！最大300MB，当前：' + fileSize + 'MB')
  }

  formData.fileName = fileData.name
  getFileDuration(fileData)
}

const handleSuccess = ({ response }) => {
  loading.value = false
  if (response.code !== 200) {
    clearFileUpload()
    return MessagePlugin.error(response.msg)
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
    fileUploadRef.value.uploadFiles()
  }
  loading.value = true
}

const clearFileUpload = () => {
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
  }
}

const insert = async (record) => {
  try {
    const res = await courseFileApi.addCourseFile(record)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    emit('refresh')
  } catch (error) {
    MessagePlugin.error('添加文件失败')
  }
}

const batchUpdate = async () => {
  try {
    const res = await courseFileApi.updateCourseFile(
      video.value.concat(file.value)
    )
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    emit('refresh')
    dialogVisible.value = false
  } catch (error) {
    MessagePlugin.error('批量更新失败')
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
