<template>
  <t-alert title="操作说明" theme="info" :closable="false"
    message="请正确使用快链配置：1.快链名称为前端快捷菜单显示名称。2.排序越大，显示越靠前。3.默认区域为集中快链组件，主页区域为单独快链组件。" />
  <t-card class="box-card">
    <t-row :gutter="20">
      <t-col :span="5">
        <t-input placeholder="请输入内容" size="small" v-model="queryInfo.query" clearable class="input-with-select"
          @clear="getQuickUrlList">
          <template #append>
            <t-button size="small" @click="getQuickUrlList"><template #icon><DynamicIcon name="search" /></template></t-button>
          </template>
        </t-input>
      </t-col>
      <t-col :span="2">
        <t-select v-model="queryInfo.status" size="small" placeholder="全部状态" @change="getQuickUrlList">
          <t-option label="全部状态" value="-1"></t-option>
          <t-option v-for="item in dictStore.dictList.sys_dict_status" :key="item.id" :label="item.codeval"
            :value="item.code"></t-option>
        </t-select>
      </t-col>
      <t-col :span="2">
        <t-select v-model="queryInfo.area" size="small" placeholder="区域" @change="getQuickUrlList">
          <t-option label="全部" value=""></t-option>
          <t-option label="默认区域" value="default"></t-option>
          <t-option label="主页区域" value="main"></t-option>
        </t-select>
      </t-col>
      <t-col :span="3">
        <t-button theme="primary" size="small" @click="addStation">添加快链</t-button>
      </t-col>
    </t-row>
    <CustomTable rowKey="id" :data="list" size="small" @sort-change="tableSort" height="calc(100vh - 400px)" stripe>
      <TableColumn label="ID" colKey="id" sortable="custom" width="80"></TableColumn>
      <TableColumn label="区域" colKey="area" sortable="custom">
        <template #default="scope">
          <t-tag size="small" effect="plain">{{
            scope.row.area === 'main'
              ? '主页'
              : scope.row.area === 'default'
                ? '默认'
                : '其他'
          }}</t-tag>
        </template>
      </TableColumn>
      <TableColumn label="名称" colKey="name" sortable="custom"></TableColumn>
      <TableColumn label="url" colKey="url" sortable="custom" ellipsis></TableColumn>
      <TableColumn label="icon" colKey="iconUrl" sortable="custom" ellipsis></TableColumn>
      <TableColumn label="系统类型" colKey="sysType" sortable="custom"></TableColumn>
      <TableColumn label="用户标志" colKey="userFlag" sortable="custom" width="100"></TableColumn>
      <TableColumn label="Token标志" colKey="tokenFlag" sortable="custom" width="100"></TableColumn>
      <TableColumn label="排序" colKey="sort" sortable="custom" width="80"></TableColumn>
      <TableColumn label="状态" colKey="status" sortable="custom" width="120">
        <template #default="scope">
          <t-tag size="small" :theme="scope.row.status === 0 ? 'danger' : 'success'" effect="dark">
            {{
              dictStore.dictList.sys_dict_status.find(
                (item) => item.code === scope.row.status.toString()
              )?.codeval
            }}</t-tag>
        </template>
      </TableColumn>
      <TableColumn label="操作" fixed="right" width="120">
        <template #default="scope">
          <t-button size="small" theme="warning" @click="handleEdit(scope.$index, scope.row)"
            shape="circle"><template #icon><DynamicIcon name="edit" /></template></t-button>
          <t-button size="small" theme="danger" @click="handleDelete(scope.$index, scope.row)"
            shape="circle"><template #icon><DynamicIcon name="delete" /></template></t-button>
        </template>
      </TableColumn>
    </CustomTable>
    <t-pagination @page-size-change="handleSizeChange" @current-change="handleCurrentChange" :current="currentPage"
      :page-size-options="pageSizes" :page-size="queryInfo.pageSize"
      :total="total">
    </t-pagination>
  </t-card>
  <!-- 弹出窗口 -->
  <t-dialog :header="dialogTitle" :close-on-overlay-click="false" v-model:visible="dialogFormVisible">
    <t-form :data="quickUrlForm" ref="quickUrlFormRef" :rules="quickUrlRules">
      <t-form-item label="名称" :label-width="formLabelWidth" name="name">
        <t-input size="small" v-model="quickUrlForm.name" autocomplete="off"></t-input>
      </t-form-item>
      <t-form-item label="URL" :label-width="formLabelWidth" name="url">
        <t-input size="small" v-model="quickUrlForm.url" autocomplete="off"></t-input>
      </t-form-item>
      <t-form-item label="图像" :label-width="formLabelWidth" name="iconUrl">
        <t-input size="small" v-model="quickUrlForm.iconUrl" autocomplete="off"></t-input>
        <t-upload class="upload-demo" :action="fsURL + 'upload/file/icon'" @success="handleSuccess"
          :file-list="iconUrlList" :multiple="false" list-type="picture">
          <t-button size="small" theme="primary">点击上传</t-button>
          <template #tip>
            <div>只能上传jpg/png文件，且不超过50kb</div>
          </template>
        </t-upload>
      </t-form-item>
      <t-form-item label="系统类型" :label-width="formLabelWidth" name="sysType">
        <t-input size="small" v-model="quickUrlForm.sysType" autocomplete="off"></t-input>
      </t-form-item>
      <t-form-item label="快链区域" :label-width="formLabelWidth" name="area">
        <t-select v-model="quickUrlForm.area" size="small" placeholder="区域">
          <t-option label="全部" value=""></t-option>
          <t-option label="默认区域" value="default"></t-option>
          <t-option label="主页区域" value="main"></t-option>
        </t-select>
      </t-form-item>
      <t-form-item label="用户标志" :label-width="formLabelWidth" name="userFlag">
        <t-switch v-model="quickUrlForm.userFlag" active-text="带用户信息" inactive-text="不带用户信息" active-value="Y"
          inactive-value="N">
        </t-switch>
      </t-form-item>
      <t-form-item label="Token标志" :label-width="formLabelWidth" name="tokenFlag">
        <t-switch v-model="quickUrlForm.tokenFlag" active-text="带Token信息" inactive-text="不带Token信息" active-value="Y"
          inactive-value="N">
        </t-switch>
      </t-form-item>
      <t-form-item label="序号" :label-width="formLabelWidth" name="sort">
        <t-input size="small" type="number" v-model="quickUrlForm.sort" autocomplete="off"
          placeholder="越大越靠前（由大到小）"></t-input>
      </t-form-item>
      <t-form-item label="状态" :label-width="formLabelWidth" name="status">
        <t-select size="small" v-model="quickUrlForm.status" placeholder="免登陆菜单状态">
          <t-option v-for="item in dictStore.dictList.sys_dict_status" :key="item.id" :label="item.codeval"
            :value="item.code"></t-option>
        </t-select>
      </t-form-item>
    </t-form>
    <template #footer>
      <span class="dialog-footer">
        <t-button size="small" @click="dialogFormVisible = false">取 消</t-button>
        <t-button size="small" theme="primary" @click="dialogFormSubmit">确 定</t-button>
      </span>
    </template>
  </t-dialog>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { quickUrlApi } from '@/api/system/quickUrl'
import { useDictStore } from '@/stores'

const dictStore = useDictStore()
const list = ref([])
const fsURL = import.meta.env.VITE_FILE_BASE_URL
const queryInfo = reactive({
  orderType: ' desc',
  order: ' sort ',
  querytype: '',
  query: '',
  area: '',
  status: '-1',
  pageSize: 20,
  pageNum: 1
})
const pageSizes = [20, 100, 500]
const currentPage = ref(1)
const total = ref(0)
const quickUrlForm = reactive({
  id: '',
  name: '',
  url: '',
  iconUrl: '',
  sysType: '',
  userFlag: 'N',
  tokenFlag: 'N',
  sort: 0,
  status: '',
  area: 'default'
})
const formLabelWidth = '120px'
const dialogFormVisible = ref(false)
const dialogTitle = ref('')
const quickUrlFormRef = ref(null)
const iconUrlList = ref([])

const quickUrlRules = reactive({
  name: [
    {
      required: true,
      max: 20,
      min: 2,
      message: '请输入2-20位长度的名称！',
      trigger: 'blur'
    }
  ],
  url: [
    {
      required: true,
      max: 200,
      min: 1,
      message: '请正确输入URL地址！',
      trigger: 'blur'
    }
  ]
})

onMounted(() => {
  getQuickUrlList()
})

const getQuickUrlList = async () => {
  const res = await quickUrlApi.listQuickUrl(queryInfo)
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  list.value = res.data.list
  total.value = res.data.total
}

const handleEdit = (index, row) => {
  dialogTitle.value = '修改免登陆快链'
  dialogFormVisible.value = true
  Object.assign(quickUrlForm, {
    id: row.id,
    name: row.name,
    url: row.url,
    iconUrl: row.iconUrl,
    sysType: row.sysType,
    userFlag: row.userFlag,
    tokenFlag: row.tokenFlag,
    sort: row.sort,
    status: row.status,
    area: row.area
  })
  iconUrlList.value = [{ name: 'icon.png', url: fsURL + row.iconUrl }]
}

const handleDelete = async (index, row) => {
  try {
    await DialogPlugin.confirm('此操作将永久删除该记录, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(async () => {
      const res = await quickUrlApi.deleteQuickUrl(row)
      if (res.code !== 200) return MessagePlugin.error(res.msg)
      MessagePlugin.success(res.msg)
      getQuickUrlList()
    })
  } catch (error) {
    MessagePlugin.error('取消删除')
  }
}

const addStation = () => {
  dialogTitle.value = '新增免登陆快链'
  dialogFormVisible.value = true
  iconUrlList.value = []
  Object.assign(quickUrlForm, {
    id: '',
    name: '',
    url: '',
    iconUrl: '',
    sysType: '',
    userFlag: 'N',
    tokenFlag: 'N',
    sort: 0,
    status: '',
    area: 'default'
  })
}

const handleSizeChange = (pageSize) => {
  queryInfo.pageSize = pageSize
  getQuickUrlList()
}

const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  getQuickUrlList()
}

const handleSuccess = (response) => {
  quickUrlForm.iconUrl = response.file.path
}

const dialogFormSubmit = async () => {
  const valid = await quickUrlFormRef.value.validate()
  if (valid !== true) return

  try {
    const res =
      dialogTitle.value === '新增免登陆快链'
        ? await quickUrlApi.addQuickUrl(quickUrlForm)
        : await quickUrlApi.updateQuickUrl(quickUrlForm)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    dialogFormVisible.value = false
    getQuickUrlList()
  } catch (error) {
    MessagePlugin.error('表单校验失败！请检查表单！')
  }
}

const tableSort = ({ sortBy, descending }) => {
  queryInfo.orderType = !descending ? ' asc ' : ' desc '
  queryInfo.order = sortBy
  getQuickUrlList()
}
</script>

<style lang="less" scoped>
.box-card {
  height: calc(100vh - 240px);
  overflow: auto;
}
</style>
