<template>
  <t-dialog
    header="封面管理"
    v-model:visible="dialogVisible"
    :close-on-overlay-click="false"
    width="50%"
    :before-close="handleClose"
  >
    <div class="block">
      <t-image
        v-if="courseFrom.coverImg"
        :src="displayURL + 'upload/getFile/college-cover/' + courseFrom.coverImg"
      ></t-image>
      <t-empty v-else description="暂未配置封面"></t-empty>
    </div>
    <t-upload
      ref="fileUploadRef"
      :action="fsURL + 'upload/file/college-cover'"
      @success="handleSuccess"
      @remove="handleRemove"
      :file-list="fileList"
      :multiple="false"
      :limit="1"
      accept="image/*"
      list-type="picture"
    >
      <t-button size="small" theme="primary">点击上传</t-button>
      <template #tip
        ><div>
          只能上传jpg/png文件，且不超过500kb
        </div></template
      >
    </t-upload>
    <template #footer>
      <span class="dialog-footer">
        <t-button size="small" @click="dialogVisible = false">关 闭</t-button>
      </span>
    </template>
  </t-dialog>
</template>
<script setup>
import { ref, reactive } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { courseApi } from '@/api/college/course.js'

const fsURL = import.meta.env.VITE_FILE_BASE_URL
// 展示类图片统一走 HTTPS 文件管理地址，避免混合内容被浏览器拦截
const displayURL = import.meta.env.VITE_FILE_BASE_URL || fsURL
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

const handleSuccess = async ({ response }) => {
  if (response.code !== 200) {
    return MessagePlugin.error(response.msg)
  }

  const fileName =
    response.data.file.fileId + '.' + response.data.file.fileSuffix
  courseFrom.coverImg = fileName
  fileList.value = []

  await handelConfirm()
}

const handelConfirm = async () => {
  try {
    const res = await courseApi.updateCourse(courseFrom)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    emit('refresh')
    dialogVisible.value = false
  } catch (error) {
    MessagePlugin.error('更新封面失败')
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
