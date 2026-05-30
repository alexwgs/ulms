<template>
  <el-dialog
    title="封面管理"
    v-model="dialogVisible"
    :close-on-click-modal="false"
    width="50%"
    :before-close="handleClose"
  >
    <div class="block">
      <el-image
        :src="fsURL + 'upload/getFile/college-cover/' + courseFrom.coverImg"
      ></el-image>
    </div>
    <el-upload
      ref="fileUploadRef"
      :action="fsURL + 'upload/file/college-cover'"
      :on-success="handleSuccess"
      :on-remove="handleRemove"
      :file-list="fileList"
      :multiple="false"
      :limit="1"
      accept="image/*"
      list-type="picture"
    >
      <el-button size="small" type="primary">点击上传</el-button>
      <template #tip
        ><div class="el-upload__tip">
          只能上传jpg/png文件，且不超过500kb
        </div></template
      >
    </el-upload>
    <template #footer>
      <span class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">关 闭</el-button>
      </span>
    </template>
  </el-dialog>
</template>
<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { courseApi } from '@/api/college/course.js'

const fsURL = import.meta.env.VITE_FILE_BASE_URL
const dialogVisible = ref(false)
const fileUploadRef = ref(null)
const fileList = ref([])
const emit = defineEmits(['refresh'])
const courseFrom = reactive({
  courseId: '',
  coverImg: ''
})

const show = (coverImg, courseId) => {
  courseFrom.courseId = courseId
  courseFrom.coverImg = coverImg
  dialogVisible.value = true
}

const handleSuccess = async (response) => {
  if (response.code !== 200) {
    return ElMessage.error(response.msg)
  }

  const fileName =
    response.data.file.fileId + '.' + response.data.file.fileSuffix
  courseFrom.coverImg = fileName
  fileList.value = []

  if (fileUploadRef.value) {
    fileUploadRef.value.clearFiles()
  }

  await handelConfirm()
}

const handelConfirm = async () => {
  try {
    const res = await courseApi.updateCourse(courseFrom)
    if (res.code !== 200) return ElMessage.error(res.msg)
    ElMessage.success(res.msg)
    emit('refresh')
    dialogVisible.value = false
  } catch (error) {
    ElMessage.error('更新封面失败')
  }
}

const handleRemove = () => {
  courseFrom.coverImg = ''
  fileList.value = []
}

const handleClose = () => {
  dialogVisible.value = false
}

defineExpose({
  show
})
</script>
<style lang="less" scoped>
.block {
  margin-top: -20px;
}
</style>
