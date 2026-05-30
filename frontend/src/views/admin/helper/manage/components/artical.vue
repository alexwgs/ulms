<template>
  <!--文章管理对话框-->
  <el-dialog title="文章管理" v-model="articalDialogVisible" width="80%" :before-close="close" :fullscreen="true">
    <el-row :gutter="15">
      <el-form ref="courseForm" :model="articalFormData" :rules="rules" size="small" label-width="100px">
        <el-col :span="12">
          <el-form-item label="地区" prop="area">
            <el-select v-model="articalFormData.area" placeholder="根据网络判断地区（未启用）" filterable clearable
              style="height: 20px; width: 100%">
              <el-option v-for="(item, index) in arealist" :key="index" :label="item.label"
                :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="条线" prop="owner">
            <el-select v-model="articalFormData.owner" filterable clearable style="height: 20px; width: 100%">
              <el-option v-for="(item, index) in ownerlist" :key="index" :label="item.label"
                :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="文章路径" prop="routeId">
            <el-cascader v-model="articalFormData.routeId" @change="routerChange" :options="tree"
              :props="{ value: 'id', label: 'name' }" :style="{ width: '100%' }"></el-cascader>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="允许搜索" prop="routeId">
            <el-select size="small" v-model="articalFormData.search" placeholder="是否可在前端通过搜索获取">
              <el-option v-for="item in yesOrNoList" :key="item.code" :label="item.codeval"
                :value="Number(item.code)"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="文章标题" prop="title">
            <el-input v-model="articalFormData.title" placeholder="请输入文章标题" :maxlength="50" show-word-limit clearable
              :style="{ width: '100%' }"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="关键词" prop="keyWord">
            <el-tag :key="tag" v-for="tag in keywords" closable :disable-transitions="false"
              @close="keywordClose(tag)">{{ tag }}</el-tag>
            <el-input class="input-new-tag" v-if="inputVisible" v-model="inputValue" ref="saveTagInput" size="small"
              @keyup.enter="handleInputConfirm" @blur="handleInputConfirm">
            </el-input>
            <el-button v-else class="button-new-tag" size="small" @click="showInput">+ 关键词</el-button>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态" prop="status">
            <el-select v-model="articalFormData.status" placeholder="请选择文章状态" filterable clearable
              style="height: 20px; width: 100%">
              <el-option label="有效" :value="1"></el-option>
              <el-option label="无效" :value="0"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="排序" prop="sorting">
            <el-input-number v-model="articalFormData.sorting" placeholder="排序" :step="1"></el-input-number>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="文章内容" prop="content">
            <WangEditor v-model="articalFormData.content" :height="400" placeholder="请输入文章内容"></WangEditor>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="其他附件" prop="files">
            <el-upload :action="fsURL + 'upload/file/cytFile'" :on-success="uploadFileSuccess" :on-remove="handleRemove"
              :before-remove="beforeRemove" multiple :limit="10" :on-exceed="handleExceed" :file-list="fileList">
              <el-button size="small" type="primary">点击上传</el-button>
              <template #tip>
                <div class="el-upload__tip">
                  限制最多十个文件。请勿上传与项目无关的附件，且单个文件不超过50MB
                </div>
              </template>
            </el-upload>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>
    <template #footer>
      <el-button size="small" @click="close">取消</el-button>
      <el-button size="small" type="primary" @click="submitForm">确定</el-button>
    </template>
  </el-dialog>
</template>
<script setup>
import { ref, reactive, onMounted, nextTick } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import WangEditor from '@/components/WangEditor.vue'
import { articalApi } from '@/api/helper/artical'
import { treeApi } from '@/api/helper/tree'

// 定义props和emit
const emit = defineEmits(['refreshList'])

// 响应式数据
const fsURL = import.meta.env.VITE_API_BASE_URL
const keywords = ref([])
const inputVisible = ref(false)
const opType = ref('add')
const inputValue = ref('')
const articalDialogVisible = ref(false)
const articalFormData = reactive({
  name: '',
  sort: 1,
  status: 1,
  area: '0',
  owner: '0',
  routeId: '',
  files: null,
  search: 1,
  sorting: 1,
  memo: '',
  keywords: '',
  keyWord: ''
})
const rules = reactive({
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  sort: [{ required: true, message: '由小到大', trigger: 'blur' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }]
})
const tree = ref([])
const arealist = ref([
  { label: '全部', value: '0' },
  { label: '上海', value: '1' },
  { label: '成都', value: '2' },
  { label: '武汉', value: '3' }
])
const ownerlist = ref([
  { label: '全部', value: '0' },
  { label: '客服条线', value: '1' },
  { label: '风管条线', value: '2' }
])
const fileList = ref([])
const files = ref([])

// 定义常量
const yesOrNoList = [
  { code: '0', codeval: '否' },
  { code: '1', codeval: '是' }
]

// 表单引用
const courseForm = ref()
const saveTagInput = ref()

// 生命周期钩子
onMounted(() => {
  getTree()
})

// 获取路径树数据
const getTree = async () => {
  try {
    const res = await treeApi.getTree()
    if (res.code !== 200) return ElMessage.error(res.msg)
    tree.value = res.data
  } catch (error) {
    ElMessage.error('获取路径树失败')
  }
}

// 打开文章管理对话框
const getArtical = async (type, journo) => {
  opType.value = type
  if (type === 'add') {
    keywords.value = []
    Object.assign(articalFormData, {
      name: '',
      sort: 1,
      status: 1,
      area: '0',
      owner: '0',
      routeId: '',
      files: null,
      search: 1,
      sorting: 1,
      memo: '',
      keywords: '',
      keyWord: ''
    })
    fileList.value = []
    files.value = []
  } else if (type === 'update') {
    try {
      const res = await articalApi.getArticalById(journo)
      if (res.code !== 200) return ElMessage.error(res.msg)
      Object.assign(articalFormData, res.data)
      keywords.value = articalFormData.keyWord
        ? articalFormData.keyWord.split('|')
        : []
      fileList.value = []
      files.value = []
      if (articalFormData.files) {
        const fileObj = JSON.parse(articalFormData.files)
        for (let i = 0; i < fileObj.length; i++) {
          fileList.value.push({ name: fileObj[i].name, url: fileObj[i].path })
        }
      }
    } catch (error) {
      ElMessage.error('获取文章详情失败')
    }
  }
  articalDialogVisible.value = true
}

// 提交表单
const submitForm = async () => {
  articalFormData.keyWord = keywords.value.join('|')
  if (files.value && files.value.length !== 0) {
    articalFormData.files = '[' + files.value.join(',') + ']'
  }
  try {
    if (opType.value === 'add') {
      const res = await articalApi.addArtical(articalFormData)
      if (res.code !== 200) return ElMessage.error(res.msg)
      ElMessage.success(res.msg)
    } else if (opType.value === 'update') {
      const res = await articalApi.updateArtical(articalFormData)
      if (res.code !== 200) return ElMessage.error(res.msg)
      ElMessage.success(res.msg)
    }
    emit('refreshList')
    articalDialogVisible.value = false
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

// 关闭对话框
const close = () => {
  Object.assign(articalFormData, {
    name: '',
    sort: 1,
    status: 1,
    area: '0',
    owner: '0',
    routeId: '',
    files: null,
    search: 1,
    sorting: 1,
    memo: '',
    keywords: '',
    keyWord: ''
  })
  keywords.value = []
  fileList.value = []
  files.value = []
  articalDialogVisible.value = false
}

// 关键词管理
const keywordClose = (tag) => {
  const index = keywords.value.indexOf(tag)
  if (index > -1) {
    keywords.value.splice(index, 1)
  }
}

const showInput = () => {
  inputVisible.value = true
  nextTick(() => {
    saveTagInput.value?.$refs?.input?.focus()
  })
}

const handleInputConfirm = () => {
  const value = inputValue.value.trim()
  if (value && keywords.value.indexOf(value) === -1) {
    keywords.value.push(value)
  }
  inputVisible.value = false
  inputValue.value = ''
}

// 路径选择变化处理
const routerChange = () => {
  if (articalFormData.routeId && articalFormData.routeId.length >= 2) {
    articalFormData.routeId =
      articalFormData.routeId[articalFormData.routeId.length - 1]
  } else {
    articalFormData.routeId = ''
  }
}

// 附件上传管理
const handleRemove = (file, fileList) => {
  files.value = []
  for (let i = 0; i < fileList.length; i++) {
    if (fileList[i].response?.file) {
      files.value.push(JSON.stringify(fileList[i].response.file))
    }
  }
}

const uploadFileSuccess = (res, fileList) => {
  if (res.data?.file) {
    files.value.push(JSON.stringify(res.data.file))
  }
}

const handleExceed = (files, fileList) => {
  ElMessage.warning(
    `当前限制选择 10 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`
  )
}

const beforeRemove = (file, fileList) => {
  return ElMessageBox.confirm(`确定移除 ${file.name} ？`, '确认删除', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(() => {
      return true
    })
    .catch(() => {
      return false
    })
}

// 直接暴露方法给父组件
defineExpose({
  getArtical
})
</script>
<style lang="less" scoped>
.el-tag+.el-tag {
  margin-left: 10px;
}

.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}

.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>
