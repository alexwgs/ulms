<template>
  <el-dialog
    :title="title"
    v-model="dialogVisible"
    width="60%"
    :close-on-click-modal="false"
  >
    <el-alert :title="info" type="warning"></el-alert>
    <el-button size="small" type="primary" @click="downloadExcel"
      >下载在职人员名单</el-button
    >
    <el-card class="upload-box">
      <el-upload
        ref="uploadRef"
        :action="baseURL + 'upload/file/excel/'"
        accept=".xls,.xlsx"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :file-list="fileList"
        :limit="1"
        :auto-upload="false"
        :on-success="fileUploadSuccess"
      >
        <el-button type="primary" size="small" slot="trigger"
          >选取文件</el-button
        >
        <el-button
          style="margin-left: 10px"
          type="success"
          size="small"
          @click="submitUpload"
          >上传</el-button
        >
        <template #tip>
          <div class="el-upload__tip">{{ info }}</div>
        </template>
      </el-upload>
    </el-card>
    <template #footer>
      <span class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">关 闭</el-button>
      </span>
    </template>
  </el-dialog>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
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
    ElMessage.warning('下载功能暂未实现')
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
    uploadRef.value.submit()
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
      return ElMessage.error(res.msg)
    }
    ElMessage.success(res.msg)
    dialogVisible.value = false
    fileList.value = []
  } catch (error) {
    ElMessage.error(error.message || '上传失败')
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
