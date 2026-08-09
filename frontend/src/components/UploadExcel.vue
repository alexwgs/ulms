<template>
  <t-dialog
    :header="title"
    v-model:visible="dialogVisible"
    width="60%"
    :close-on-overlay-click="false"
  >
    <t-alert :title="info" theme="warning"></t-alert>
    <t-button size="small" theme="primary" @click="downloadExcel"
      >下载在职人员名单</t-button
    >
    <t-card class="upload-box">
      <t-upload
        ref="uploadRef"
        :action="baseURL + 'upload/file/excel/'"
        accept=".xls,.xlsx"
        @preview="handlePreview"
        @remove="handleRemove"
        :file-list="fileList"
        :limit="1"
        :auto-upload="false"
        @success="fileUploadSuccess"
      >
        <t-button theme="primary" size="small" slot="trigger"
          >选取文件</t-button
        >
        <t-button
          style="margin-left: 10px"
          theme="success"
          size="small"
          @click="submitUpload"
          >上传</t-button
        >
        <template #tip>
          <div>{{ info }}</div>
        </template>
      </t-upload>
    </t-card>
    <template #footer>
      <span class="dialog-footer">
        <t-button size="small" @click="dialogVisible = false">关 闭</t-button>
      </span>
    </template>
  </t-dialog>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { httpInstance } from '@/utils/request.js'

// Props
const props = defineProps({
  url: {
    type: String,
    default: ''
  },
  title: {
    type: String,
    default: '上传Excel'
  },
  info: {
    type: String,
    default: '请选择要上传的Excel文件'
  }
})

// 响应式数据
const dialogVisible = ref(false)
const fileList = ref([])
const fileName = ref('')
const baseURL = ref(import.meta.env.VITE_FILE_BASE_URL)
const queryInfo = ref({})
const uploadRef = ref(null)

// 显示对话框
const show = (queryInfoData) => {
  queryInfo.value = queryInfoData || {}
  dialogVisible.value = true
}

// 下载Excel文件
const downloadExcel = () => {
  // 这里需要根据项目实际情况实现下载功能
  // 假设项目中有全局的下载方法
  if (window.$global && window.$global.downloadExcel) {
    window.$global.downloadExcel('employee/report', null, '人员在职名单.xlsx')
  } else {
    MessagePlugin.warning('下载功能暂未实现')
  }
}

// 文件预览
const handlePreview = () => {
  // 暂未实现
}

// 文件移除
const handleRemove = () => {
  // 暂未实现
}

// 提交上传
const submitUpload = () => {
  if (uploadRef.value) {
    uploadRef.value.uploadFiles()
  }
}

// 文件上传成功
const fileUploadSuccess = async (response, file, fileList) => {
  try {
    const fileName = response.file.fileId + '.' + response.file.fileSuffix
    // 获取文件名称后。开始解析数据
    const res = await httpInstance.post(
      `${queryInfo.value.url}/${fileName}`,
      queryInfo.value
    )
    if (res.code !== 200) {
      return MessagePlugin.error(res.msg)
    }
    MessagePlugin.success(res.msg)
    dialogVisible.value = false
    fileList.value = []
  } catch (error) {
    MessagePlugin.error(error.message || '上传失败')
  }
}

// 暴露方法
defineExpose({
  show
})
</script>
<style lang="less" scoped>
.upload-box {
  margin-top: 10px;
}
</style>
