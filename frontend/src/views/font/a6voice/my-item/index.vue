<template>
  <el-card class="box-card">
    <template #header>
      <div class="clearfix">
        <el-page-header @back="() => router.back()" content="我参与的项目">
        </el-page-header>
      </div>
    </template>
    <div class="text item">
      <div class="table-filter">
        <span>项目状态
          <el-select style="width: 200px;" v-model="status" @change="getItemList" size="small" placeholder="请选择">
            <el-option v-for="item in dictStore.getDictByNames('cyt_artical_status', 1)" :key="item.id"
              :label="item.codeval" :value="item.code"></el-option>
          </el-select>
        </span>
        <span class="demonstration">项目年份
          <el-date-picker v-model="itemYear" size="small" @change="getItemList" type="year"
            placeholder="选择年"></el-date-picker>
        </span>
      </div>
      <el-table :data="items" size="small" stripe style="width: 100%" height="calc(100vh - 280px)">
        <el-table-column prop="title" label="分类" width="130">
          <template #default="scope">
            <el-tag v-for="item in dictStore.getDictByNames('cyt_item_category', 1)" size="small" :key="item.code"
              :style="scope.row.category == item.code ? '' : 'display:none'" effect="plain">{{ item.codeval }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="title" label="项目名称"></el-table-column>
        <el-table-column prop="pubDate" label="发布日期" width="180"></el-table-column>
        <el-table-column prop="status" label="状态" width="140">
          <template #default="scope">
            <el-tag v-for="item in dictStore.getDictByNames('cyt_artical_status', 1)" :key="item.code"
              :type="scope.row.status == 1 ? 'success' : 'warning'" size="small"
              :style="scope.row.status == item.code ? '' : 'display:none'" effect="plain">{{ item.codeval
              }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="260">
          <template #default="scope">
            <el-button type="primary" size="small" @click="viewItem(scope.$index, scope.row)">查看</el-button>
            <el-button type="primary" size="small" @click="memberEdit(scope.$index, scope.row)">项目成员</el-button>
            <el-button type="warning" size="small" @click="progressEdit(scope.$index, scope.row)">进度管理</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination @current-change="handleCurrentChange" v-model:current-page="currentPage"
        :page-size="queryInfo.pageSize" layout="total, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>
    <el-dialog title="成员管理" fullscreen :close-on-click-modal="false" v-model="memberVisible">
      <el-alert title="只能设置一位项目PM，所有成员的积分分配比例之和为100%" type="warning" show-icon>
      </el-alert>
      <el-table :data="memberForm" border size="small" style="width: 100%">
        <el-table-column label="员工编号" prop="userId" width="200">
          <template #default="scope">
            <el-input v-model="scope.row.userId" size="small" style="width: 100px" :disabled="editFlag"
              autocomplete="off" placeholder=""></el-input>
            <span v-if="scope.row.user != undefined">{{
              scope.row.user.ploName
            }}</span>
          </template>
        </el-table-column>
        <el-table-column label="项目角色" prop="role" width="180">
          <template #default="scope">
            <el-select v-model="scope.row.role" size="small" :disabled="editFlag">
              <el-option v-for="item in dictStore.getDictByNames('cyt_item_member', 1)" :key="item.id"
                :label="item.codeval" :value="parseInt(item.code)"></el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="积分分配" prop="scoreRate" width="120">
          <template #default="scope">
            <el-input v-model="scope.row.scoreRate" size="small" type="number" :disabled="editFlag" autocomplete="off"
              placeholder="">%</el-input>
          </template>
        </el-table-column>
        <el-table-column label="描述" prop="describe">
          <template #default="scope">
            <el-input v-model="scope.row.describe" size="small" :disabled="editFlag" autocomplete="off"
              placeholder=""></el-input>
          </template>
        </el-table-column>
        <el-table-column align="center" width="180">
          <template #header>
            <el-button type="primary" size="small" icon="plus" @click="addMember"></el-button>
          </template>
          <template #default="scope">
            <el-button type="danger" :disabled="editFlag" size="small"
              @click="deleteMember(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="danger" size="small" :disabled="!editFlag" @click="editFlag = !editFlag">编 辑</el-button>
          <el-button type="primary" size="small" :disabled="editFlag" @click="submitMemberForm">提交修改</el-button>
          <el-button size="small" @click="memberVisible = false">关 闭</el-button>
        </div>
      </template>
    </el-dialog>
    <el-dialog title="进度更新" fullscreen :close-on-click-modal="false" v-model="progressVisible">
      <el-table :data="progresses" stripe border size="small" style="width: 100%" height="300px">
        <el-table-column prop="dateTime" label="日期" width="160"></el-table-column>
        <el-table-column prop="userId" label="操作人" width="100"></el-table-column>
        <el-table-column prop="type" label="操作类型" width="160">
          <template #default="scope">
            {{ getDictLabel('cyt_item_type', scope.row.type) }}
          </template>
        </el-table-column>
        <el-table-column prop="content" label="更新内容"></el-table-column>
        <el-table-column prop="rate" label="进度" width="80"></el-table-column>
        <el-table-column label="操作" width="100">
          <template #default="scope">
            <el-button type="danger" size="small" :disabled="scope.row.type == 3 || scope.row.type == 4 ? false : true"
              @click="cancelProgress(scope.$index, scope.row)">作废</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-divider content-position="left">新增更新</el-divider>
      <el-form ref="progressFormRef" :rules="progressRules" :model="progressForm" size="small" label-width="80px">
        <el-form-item label="更新类型" prop="type">
          <el-select v-model="progressForm.type" placeholder="请选择类型">
            <el-option v-for="type in dictStore.getDictByNames('cyt_item_type', 1)" :key="type.id" :label="type.codeval"
              :value="parseInt(type.code)" :disabled="type.code == 3 || type.code == 4 ? false : true"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="当前进度" prop="rate">
          <el-input type="number" v-model="progressForm.rate"></el-input>
        </el-form-item>
        <el-form-item label="更新内容" prop="content">
          <el-input type="textarea" v-model="progressForm.content" :show-word-limit="true" maxlength="500"></el-input>
        </el-form-item>
        <el-upload class="upload-demo" :action="fsURL + 'upload/file/cytFile'" :on-success="uploadFileSuccess"
          :on-remove="handleRemove" :before-remove="beforeRemove" multiple :limit="3" :on-exceed="handleExceed"
          :file-list="fileList">
          <el-button size="small" type="primary">点击上传</el-button>
          <template #tip>
            <div class="el-upload__tip">
              限制最多三个文件。请勿上传与项目无关的附件，且单个文件不超过5MB
            </div>
          </template>
        </el-upload>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button size="small" type="danger" @click="updateProgress">更新进度</el-button>
          <el-button size="small" @click="progressVisible = false">关 闭</el-button>
        </div>
      </template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { httpInstance } from '@/utils/request'
import { useDictStore } from '@/stores'

const router = useRouter()
const dictStore = useDictStore()

const fsURL = import.meta.env.VITE_FILE_MANAGE_BASE || import.meta.env.VITE_API_BASE_URL

const editFlag = ref(true)
const items = ref([])
const currentArticalId = ref('')
const status = ref('')
const itemYear = ref(new Date())
const currentPage = ref(1)
const total = ref(0)
const memberVisible = ref(false)
const progressVisible = ref(false)
const progresses = ref([])
const fileList = ref([])
const files = ref([])

const queryInfo = reactive({
  sortType: 'hot',
  querytype: '',
  query: '',
  pageSize: 20,
  pageNum: 1
})

const memberForm = reactive([
  {
    userId: '655012',
    role: 1,
    scoreRate: 0,
    describe: '',
    status: 1
  }
])

const progressForm = reactive({
  articalId: '',
  rate: 0,
  content: '',
  files: '',
  type: 4,
  status: 1
})

const progressRules = reactive({
  content: [
    { required: true, message: '请输入更新内容', trigger: 'blur' },
    {
      min: 20,
      max: 500,
      message: '长度在 20 到 500 个字符',
      trigger: 'blur'
    }
  ],
  rate: [
    { required: true, message: '请输入更新进度0~100', trigger: 'blur' }
  ],
  type: [{ required: true, message: '请选择更新类型', trigger: 'change' }]
})

const progressFormRef = ref(null)

const getDictLabel = (dictName, value) => {
  const dictList = dictStore.getDictByNames(dictName, 1)
  const item = dictList.find(d => d.code == value)
  return item ? item.codeval : ''
}

onMounted(() => {
  getItemList()
})

const viewItem = (index, row) => {
  const url = router.resolve({ path: '/artical/item/' + row.id })
  window.open(url.href, '_blank')
}

const getItemList = async () => {
  try {
    const res = await httpInstance.get('cyt/myItem', { params: queryInfo })
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    total.value = res.data.total
    items.value = res.data.list
  } catch (error) {
    ElMessage.error('获取项目列表失败')
    console.error(error)
  }
}

const memberEdit = async (index, row) => {
  editFlag.value = true
  memberForm.length = 0
  currentArticalId.value = row.id
  try {
    const res = await httpInstance.get(`cyt/member/${currentArticalId.value}`)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    memberForm.push(...res.data)
    memberVisible.value = true
  } catch (error) {
    ElMessage.error('获取成员列表失败')
    console.error(error)
  }
}

const progressEdit = (index, row) => {
  currentArticalId.value = row.id
  progressVisible.value = true
  getProgress()
}

const cancelProgress = (index, row) => {
  ElMessageBox.confirm('此操作将永久删除该更新内容, 是否继续?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      const progress = { id: row.id, status: 0 }
      const res = await httpInstance.put('cyt/progress', progress)
      if (res.code !== 200) {
        ElMessage.error(res.msg)
        return
      }
      ElMessage.success(res.msg)
      getProgress()
    } catch (error) {
      ElMessage.error('删除失败')
      console.error(error)
    }
  }).catch(() => {
    ElMessage.info('取消删除！')
  })
}

const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  getItemList()
}

const addMember = () => {
  if (!editFlag.value) {
    memberForm.push({
      userId: '',
      role: 1,
      scoreRate: 0,
      describe: '',
      status: 1
    })
  }
}

const deleteMember = (index, row) => {
  memberForm.splice(index, 1)
}

const submitMemberForm = async () => {
  let totalRate = 0
  let flag = true
  memberForm.forEach((item) => {
    if (item.scoreRate === '' || item.userId === '' || item.scoreRate === '') {
      flag = false
    }
    totalRate = totalRate + parseInt(item.scoreRate)
  })
  if (!flag) {
    ElMessage.error('字段内容不可为空！')
    return
  }
  if (totalRate !== 100) {
    ElMessage.error('积分分配之和必须为100！当前为:' + totalRate)
    return
  }
  try {
    const res = await httpInstance.post(`cyt/member/${currentArticalId.value}`, memberForm)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    ElMessage.success(res.msg)
    memberVisible.value = false
  } catch (error) {
    ElMessage.error('提交失败')
    console.error(error)
  }
}

const getProgress = async () => {
  try {
    const res = await httpInstance.get(`cyt/progress/${currentArticalId.value}`)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    progresses.value = res.data
    progressForm.rate = progresses.value.length > 0 ? progresses.value[0].rate : 0
  } catch (error) {
    ElMessage.error('获取进度失败')
    console.error(error)
  }
}

const updateProgress = () => {
  if (progresses.value.length > 0 && (progressForm.rate <= progresses.value[0].rate || progressForm.rate > 100)) {
    ElMessage.error('完成进度必须填写大于前次进度，且小于等于100的值！')
    return
  }
  if (progressForm.type === 3 && progressForm.rate !== 100) {
    ElMessage.error('预结案进度必须是100%！')
    return
  }
  if (files.value !== null && files.value.length !== 0) {
    progressForm.files = '[' + files.value.join(',') + ']'
  }
  progressForm.articalId = currentArticalId.value

  progressFormRef.value?.validate(async (valid) => {
    if (valid) {
      try {
        const res = await httpInstance.post('cyt/progress', progressForm)
        if (res.code !== 200) {
          ElMessage.error(res.msg)
          return
        }
        ElMessage.success(res.msg)
        getProgress()
        Object.assign(progressForm, {
          articalId: '',
          rate: 0,
          content: '',
          files: '',
          type: 4,
          status: 1
        })
        fileList.value = []
      } catch (error) {
        ElMessage.error('更新进度失败')
        console.error(error)
      }
    } else {
      return false
    }
  })
}

const handleRemove = (file, fileList) => {
  files.value = []
  for (let i = 0; i < fileList.length; i++) {
    files.value.push(JSON.stringify(fileList[i].response.file))
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
  return ElMessageBox.confirm(`确定移除 ${file.name} ？`)
}
</script>

<style lang="less" scoped>
.box-card {
  height: calc(100vh - 130px);
}

.table-filter {
  padding: 10px;

  span {
    font-size: 14px;
    margin-left: 20px;
  }
}

.el-dialog__body {
  padding-top: 0;
}
</style>
