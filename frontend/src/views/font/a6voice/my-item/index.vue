<template>
  <t-card class="management-card">
    <template #header>
      <div class="clearfix">
        <div class="my-item-page-header">
          <t-button theme="default" variant="text" @click="router.back()">
            
          返回</t-button>
          <span class="my-item-page-title">我参与的项目</span>
        </div>
      </div>
    </template>
    <div class="text item">
      <t-form :data="{ status, itemYear }" label-width="80px" colon class="filter-form">
        <t-row :gutter="[24, 24]">
          <t-col :span="4">
            <t-form-item label="项目状态" name="status">
              <t-select v-model="status" @change="getItemList" size="small" placeholder="请选择">
                <t-option v-for="item in dictStore.getDictByNames('cyt_artical_status', 1)" :key="item.id"
                  :label="item.codeval" :value="item.code"></t-option>
              </t-select>
            </t-form-item>
          </t-col>
          <t-col :span="4">
            <t-form-item label="项目年份" name="itemYear">
              <t-date-picker v-model="itemYear" size="small" @change="getItemList" mode="year"
                placeholder="选择年"></t-date-picker>
            </t-form-item>
          </t-col>
        </t-row>
      </t-form>
      <CustomTable rowKey="id" :data="items" size="small" stripe style="width: 100%" height="calc(100vh - 280px)">
        <TableColumn colKey="category" label="分类" width="130">
          <template #default="scope">
            <t-tag v-for="item in dictStore.getDictByNames('cyt_item_category', 1)" size="small" :key="item.code"
              :style="scope.row.category == item.code ? '' : 'display:none'" variant="light">{{ item.codeval }}</t-tag>
          </template>
        </TableColumn>
        <TableColumn colKey="title" label="项目名称"></TableColumn>
        <TableColumn colKey="pubDate" label="发布日期" width="180"></TableColumn>
        <TableColumn colKey="status" label="状态" width="140">
          <template #default="scope">
            <t-tag v-for="item in dictStore.getDictByNames('cyt_artical_status', 1)" :key="item.code"
              :theme="scope.row.status == 1 ? 'success' : 'warning'" size="small"
              :style="scope.row.status == item.code ? '' : 'display:none'" variant="light">{{ item.codeval
              }}</t-tag>
          </template>
        </TableColumn>
        <TableColumn label="操作" width="260">
          <template #default="scope">
            <t-button theme="primary" size="small" @click="viewItem(scope.$index, scope.row)">查看</t-button>
            <t-button theme="primary" size="small" @click="memberEdit(scope.$index, scope.row)">项目成员</t-button>
            <t-button theme="warning" size="small" @click="progressEdit(scope.$index, scope.row)">进度管理</t-button>
          </template>
        </TableColumn>
      </CustomTable>
      <t-pagination @current-change="handleCurrentChange" v-model="currentPage"
        :page-size="queryInfo.pageSize" :total="total">
      </t-pagination>
    </div>
    <t-dialog header="成员管理" mode="full-screen" :close-on-overlay-click="false" v-model:visible="memberVisible">
      <t-alert title="只能设置一位项目PM，所有成员的积分分配比例之和为100%" theme="warning" show-icon>
      </t-alert>
      <CustomTable rowKey="id" :data="memberForm" border size="small" style="width: 100%">
        <TableColumn label="员工编号" colKey="userId" width="200">
          <template #default="scope">
            <t-input v-model="scope.row.userId" size="small" style="width: 100px" :disabled="editFlag"
              autocomplete="off" placeholder=""></t-input>
            <span v-if="scope.row.user != undefined">{{
              scope.row.user.ploName
            }}</span>
          </template>
        </TableColumn>
        <TableColumn label="项目角色" colKey="role" width="180">
          <template #default="scope">
            <t-select v-model="scope.row.role" size="small" :disabled="editFlag">
              <t-option v-for="item in dictStore.getDictByNames('cyt_item_member', 1)" :key="item.id"
                :label="item.codeval" :value="parseInt(item.code)"></t-option>
            </t-select>
          </template>
        </TableColumn>
        <TableColumn label="积分分配" colKey="scoreRate" width="120">
          <template #default="scope">
            <t-input v-model="scope.row.scoreRate" size="small" type="number" :disabled="editFlag" autocomplete="off"
              placeholder="">%</t-input>
          </template>
        </TableColumn>
        <TableColumn label="描述" colKey="describe">
          <template #default="scope">
            <t-input v-model="scope.row.describe" size="small" :disabled="editFlag" autocomplete="off"
              placeholder=""></t-input>
          </template>
        </TableColumn>
        <TableColumn align="center" width="180">
          <template #header>
            <t-button variant="outline" theme="primary" size="small" @click="addMember">新增</t-button>
          </template>
          <template #default="scope">
            <t-button theme="danger" :disabled="editFlag" size="small"
              @click="deleteMember(scope.$index, scope.row)">删除</t-button>
          </template>
        </TableColumn>
      </CustomTable>
      <template #footer>
        <div class="dialog-footer">
          <t-button theme="danger" size="small" :disabled="!editFlag" @click="editFlag = !editFlag">编 辑</t-button>
          <t-button theme="primary" size="small" :disabled="editFlag" @click="submitMemberForm">提交修改</t-button>
          <t-button size="small" @click="memberVisible = false">关 闭</t-button>
        </div>
      </template>
    </t-dialog>
    <t-dialog header="进度更新" mode="full-screen" :close-on-overlay-click="false" v-model:visible="progressVisible">
      <CustomTable rowKey="id" :data="progresses" stripe border size="small" style="width: 100%" height="300px">
        <TableColumn colKey="dateTime" label="日期" width="160"></TableColumn>
        <TableColumn colKey="userId" label="操作人" width="100"></TableColumn>
        <TableColumn colKey="type" label="操作类型" width="160">
          <template #default="scope">
            {{ getDictLabel('cyt_item_type', scope.row.type) }}
          </template>
        </TableColumn>
        <TableColumn colKey="content" label="更新内容"></TableColumn>
        <TableColumn colKey="rate" label="进度" width="80"></TableColumn>
        <TableColumn label="操作" width="100">
          <template #default="scope">
            <t-button theme="danger" size="small" :disabled="scope.row.type == 3 || scope.row.type == 4 ? false : true"
              @click="cancelProgress(scope.$index, scope.row)">作废</t-button>
          </template>
        </TableColumn>
      </CustomTable>
      <t-divider content-position="left">新增更新</t-divider>
      <t-form ref="progressFormRef" :rules="progressRules" :data="progressForm" size="small" label-width="80px">
        <t-form-item label="更新类型" name="type">
          <t-select v-model="progressForm.type" placeholder="请选择类型">
            <t-option v-for="type in dictStore.getDictByNames('cyt_item_type', 1)" :key="type.id" :label="type.codeval"
              :value="parseInt(type.code)" :disabled="type.code == 3 || type.code == 4 ? false : true"></t-option>
          </t-select>
        </t-form-item>
        <t-form-item label="当前进度" name="rate">
          <t-input type="number" v-model="progressForm.rate"></t-input>
        </t-form-item>
        <t-form-item label="更新内容" name="content">
          <t-textarea v-model="progressForm.content" :show-limit-number="true" maxlength="500" />
        </t-form-item>
        <t-upload class="upload-demo" :headers="uploadHeaders" :action="fsURL + 'upload/file/cytFile'" @success="uploadFileSuccess"
          @remove="handleRemove" :before-remove="beforeRemove" multiple :limit="3" @exceed="handleExceed"
          :file-list="fileList">
          <t-button size="small" theme="primary">点击上传</t-button>
          <template #tip>
            <div>
              限制最多三个文件。请勿上传与项目无关的附件，且单个文件不超过5MB
            </div>
          </template>
        </t-upload>
      </t-form>
      <template #footer>
        <div class="dialog-footer">
          <t-button size="small" theme="danger" @click="updateProgress">更新进度</t-button>
          <t-button size="small" @click="progressVisible = false">关 闭</t-button>
        </div>
      </template>
    </t-dialog>
  </t-card>
</template>

<script setup>
const uploadHeaders = { Authorization: localStorage.getItem('token') || '' }
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { MessagePlugin, DialogPlugin } from 'tdesign-vue-next'
import { httpInstance } from '@/utils/request'
import { useDictStore } from '@/stores'

const router = useRouter()
const dictStore = useDictStore()

const fsURL = import.meta.env.VITE_FILE_BASE_URL

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
      MessagePlugin.error(res.msg)
      return
    }
    total.value = res.data.total
    items.value = res.data.list
  } catch (error) {
    MessagePlugin.error('获取项目列表失败')
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
      MessagePlugin.error(res.msg)
      return
    }
    memberForm.push(...res.data)
    memberVisible.value = true
  } catch (error) {
    MessagePlugin.error('获取成员列表失败')
    console.error(error)
  }
}

const progressEdit = (index, row) => {
  currentArticalId.value = row.id
  progressVisible.value = true
  getProgress()
}

const cancelProgress = (index, row) => {
  DialogPlugin.confirm('此操作将永久删除该更新内容, 是否继续?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      const progress = { id: row.id, status: 0 }
      const res = await httpInstance.put('cyt/progress', progress)
      if (res.code !== 200) {
        MessagePlugin.error(res.msg)
        return
      }
      MessagePlugin.success(res.msg)
      getProgress()
    } catch (error) {
      MessagePlugin.error('删除失败')
      console.error(error)
    }
  }).catch(() => {
    MessagePlugin.info('取消删除！')
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
    MessagePlugin.error('字段内容不可为空！')
    return
  }
  if (totalRate !== 100) {
    MessagePlugin.error('积分分配之和必须为100！当前为:' + totalRate)
    return
  }
  try {
    const res = await httpInstance.post(`cyt/member/${currentArticalId.value}`, memberForm)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    MessagePlugin.success(res.msg)
    memberVisible.value = false
  } catch (error) {
    MessagePlugin.error('提交失败')
    console.error(error)
  }
}

const getProgress = async () => {
  try {
    const res = await httpInstance.get(`cyt/progress/${currentArticalId.value}`)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    progresses.value = res.data
    progressForm.rate = progresses.value.length > 0 ? progresses.value[0].rate : 0
  } catch (error) {
    MessagePlugin.error('获取进度失败')
    console.error(error)
  }
}

const updateProgress = async () => {
  if (progresses.value.length > 0 && (progressForm.rate <= progresses.value[0].rate || progressForm.rate > 100)) {
    MessagePlugin.error('完成进度必须填写大于前次进度，且小于等于100的值！')
    return
  }
  if (progressForm.type === 3 && progressForm.rate !== 100) {
    MessagePlugin.error('预结案进度必须是100%！')
    return
  }
  if (files.value !== null && files.value.length !== 0) {
    progressForm.files = '[' + files.value.join(',') + ']'
  }
  progressForm.articalId = currentArticalId.value

  const valid = await progressFormRef.value?.validate()
  if (valid === true) {
    try {
      const res = await httpInstance.post('cyt/progress', progressForm)
      if (res.code !== 200) {
        MessagePlugin.error(res.msg)
        return
      }
      MessagePlugin.success(res.msg)
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
      MessagePlugin.error('更新进度失败')
      console.error(error)
    }
  }
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
  MessagePlugin.warning(
    `当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`
  )
}

const beforeRemove = (file, fileList) => {
  return DialogPlugin.confirm(`确定移除 ${file.name} ？`)
}
</script>

<style lang="less" scoped>
.management-card {
  height: calc(100vh - 130px);
}

.my-item-page-header {
  display: flex;
  align-items: center;
  gap: 8px;
}

.my-item-page-title {
  font-size: 18px;
  font-weight: 600;
  color: var(--td-text-color-primary);
}

.table-filter {
  padding: 0;

  span {
    font-size: 14px;
    margin-left: 20px;
  }
}

.t-dialog__body {
  padding-top: 0;
}
</style>
