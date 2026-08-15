<template>
  <!--文章管理对话框-->
  <t-dialog header="文章管理" v-model:visible="articleDialogVisible" width="80%" :before-close="close" mode="full-screen">
    <t-row :gutter="15">
      <t-form ref="courseForm" :data="articleFormData" :rules="rules" size="small" label-width="100px">
        <t-col :span="6">
          <t-form-item label="地区" name="area">
            <t-select v-model="articleFormData.area" placeholder="根据网络判断地区（未启用）" filterable clearable
              style="height: 20px; width: 100%">
              <t-option v-for="(item, index) in arealist" :key="index" :label="item.label"
                :value="item.value"></t-option>
            </t-select>
          </t-form-item>
        </t-col>
        <t-col :span="6">
          <t-form-item label="条线" name="owner">
            <t-select v-model="articleFormData.owner" filterable clearable style="height: 20px; width: 100%">
              <t-option v-for="(item, index) in ownerlist" :key="index" :label="item.label"
                :value="item.value"></t-option>
            </t-select>
          </t-form-item>
        </t-col>
        <t-col :span="6">
          <t-form-item label="文章路径" name="routeId">
            <t-cascader v-model="articleFormData.routeId" @change="routerChange" :options="tree"
              :keys="{ value: 'id', label: 'name' }" :style="{ width: '100%' }"></t-cascader>
          </t-form-item>
        </t-col>
        <t-col :span="6">
          <t-form-item label="允许搜索" name="routeId">
            <t-select size="small" v-model="articleFormData.search" placeholder="是否可在前端通过搜索获取">
              <t-option v-for="item in yesOrNoList" :key="item.code" :label="item.codeval"
                :value="Number(item.code)"></t-option>
            </t-select>
          </t-form-item>
        </t-col>
        <t-col :span="12">
          <t-form-item label="文章标题" name="title">
            <t-input v-model="articleFormData.title" placeholder="请输入文章标题" :maxlength="50" show-limit-number clearable
              :style="{ width: '100%' }"></t-input>
          </t-form-item>
        </t-col>
        <t-col :span="12">
          <t-form-item label="关键词" name="keyWord">
            <t-tag :key="tag" v-for="tag in keywords" closable :disable-transitions="false"
              @close="keywordClose(tag)" variant="light">{{ tag }}</t-tag>
            <t-input class="input-new-tag" v-if="inputVisible" v-model="inputValue" ref="saveTagInput" size="small"
              @keyup.enter="handleInputConfirm" @blur="handleInputConfirm">
            </t-input>
            <t-button v-else class="button-new-tag" size="small" @click="showInput">+ 关键词</t-button>
          </t-form-item>
        </t-col>
        <t-col :span="6">
          <t-form-item label="状态" name="status">
            <t-select v-model="articleFormData.status" placeholder="请选择文章状态" filterable clearable
              style="height: 20px; width: 100%">
              <t-option label="有效" :value="1"></t-option>
              <t-option label="无效" :value="0"></t-option>
            </t-select>
          </t-form-item>
        </t-col>
        <t-col :span="6">
          <t-form-item label="排序" name="sorting">
            <t-input-number v-model="articleFormData.sorting" placeholder="排序" :step="1"></t-input-number>
          </t-form-item>
        </t-col>
        <t-col :span="12">
          <t-form-item label="文章内容" name="content">
            <WangEditor v-model="articleFormData.content" :height="400" placeholder="请输入文章内容"></WangEditor>
          </t-form-item>
        </t-col>
        <t-col :span="12">
          <t-form-item label="其他附件" name="files">
            <t-upload :headers="uploadHeaders" :action="fsURL + 'upload/file/cytFile'" @success="uploadFileSuccess" @remove="handleRemove"
              :before-remove="beforeRemove" multiple :limit="10" @exceed="handleExceed" :file-list="fileList">
              <t-button size="small" theme="primary">点击上传</t-button>
              <template #tip>
                <div>
                  限制最多十个文件。请勿上传与项目无关的附件，且单个文件不超过50MB
                </div>
              </template>
            </t-upload>
          </t-form-item>
        </t-col>
      </t-form>
    </t-row>
    <template #footer>
      <t-button size="small" @click="close">取消</t-button>
      <t-button size="small" theme="primary" @click="submitForm">确定</t-button>
    </template>
  </t-dialog>
</template>
<script setup>
const uploadHeaders = { Authorization: localStorage.getItem('token') || '' }
import { ref, reactive, onMounted, nextTick } from 'vue'
import { MessagePlugin, DialogPlugin } from 'tdesign-vue-next'
import WangEditor from '@/components/WangEditor.vue'
import { articleApi } from '@/api/helper/article'
import { treeApi } from '@/api/helper/tree'

// 定义props和emit
const emit = defineEmits(['refreshList'])

// 响应式数据
const fsURL = import.meta.env.VITE_API_BASE_URL
const keywords = ref([])
const inputVisible = ref(false)
const opType = ref('add')
const inputValue = ref('')
const articleDialogVisible = ref(false)
const articleFormData = reactive({
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
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    tree.value = res.data
  } catch (error) {
    MessagePlugin.error('获取路径树失败')
  }
}

// 打开文章管理对话框
const getArticle = async (type, journo) => {
  opType.value = type
  if (type === 'add') {
    keywords.value = []
    Object.assign(articleFormData, {
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
      const res = await articleApi.getArticleById(journo)
      if (res.code !== 200) return MessagePlugin.error(res.msg)
      Object.assign(articleFormData, res.data)
      keywords.value = articleFormData.keyWord
        ? articleFormData.keyWord.split('|')
        : []
      fileList.value = []
      files.value = []
      if (articleFormData.files) {
        const fileObj = JSON.parse(articleFormData.files)
        for (let i = 0; i < fileObj.length; i++) {
          fileList.value.push({ name: fileObj[i].name, url: fileObj[i].path })
        }
      }
    } catch (error) {
      MessagePlugin.error('获取文章详情失败')
    }
  }
  articleDialogVisible.value = true
}

// 提交表单
const submitForm = async () => {
  articleFormData.keyWord = keywords.value.join('|')
  if (files.value && files.value.length !== 0) {
    articleFormData.files = '[' + files.value.join(',') + ']'
  }
  try {
    if (opType.value === 'add') {
      const res = await articleApi.addArticle(articleFormData)
      if (res.code !== 200) return MessagePlugin.error(res.msg)
      MessagePlugin.success(res.msg)
    } else if (opType.value === 'update') {
      const res = await articleApi.updateArticle(articleFormData)
      if (res.code !== 200) return MessagePlugin.error(res.msg)
      MessagePlugin.success(res.msg)
    }
    emit('refreshList')
    articleDialogVisible.value = false
  } catch (error) {
    MessagePlugin.error('操作失败')
  }
}

// 关闭对话框
const close = () => {
  Object.assign(articleFormData, {
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
  articleDialogVisible.value = false
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
  if (articleFormData.routeId && articleFormData.routeId.length >= 2) {
    articleFormData.routeId =
      articleFormData.routeId[articleFormData.routeId.length - 1]
  } else {
    articleFormData.routeId = ''
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
  MessagePlugin.warning(
    `当前限制选择 10 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`
  )
}

const beforeRemove = (file, fileList) => {
  return DialogPlugin.confirm(`确定移除 ${file.name} ？`, '确认删除', {
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
  getArticle
})
</script>
<style lang="less" scoped>
.t-tag+.t-tag {
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
