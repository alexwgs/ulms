<template>
  <el-alert title="操作说明" type="info" :closable="false"
    description="请正确使用快链配置：1.快链名称为前端快捷菜单显示名称。2.排序越大，显示越靠前。3.默认区域为集中快链组件，主页区域为单独快链组件。" />
  <el-card class="box-card">
    <el-row :gutter="20">
      <el-col :span="10">
        <el-input placeholder="请输入内容" size="small" v-model="queryInfo.query" clearable class="input-with-select"
          @clear="getQuickUrlList">
          <template #append>
            <el-button size="small" icon="Search" @click="getQuickUrlList"></el-button>
          </template>
        </el-input>
      </el-col>
      <el-col :span="4">
        <el-select v-model="queryInfo.status" size="small" placeholder="全部状态" @change="getQuickUrlList">
          <el-option label="全部状态" value="-1"></el-option>
          <el-option v-for="item in dictStore.dictList.sys_dict_status" :key="item.id" :label="item.codeval"
            :value="item.code"></el-option>
        </el-select>
      </el-col>
      <el-col :span="4">
        <el-select v-model="queryInfo.area" size="small" placeholder="区域" @change="getQuickUrlList">
          <el-option label="全部" value=""></el-option>
          <el-option label="默认区域" value="default"></el-option>
          <el-option label="主页区域" value="main"></el-option>
        </el-select>
      </el-col>
      <el-col :span="6">
        <el-button type="primary" size="small" @click="addStation">添加快链</el-button>
      </el-col>
    </el-row>
    <el-alert title="操作说明" type="info" :closable="false">
      <template #description>
        请正确使用快速链接：1.用户标志指路径上带有userId数据。2.Token表示路径上带有token数据。3.排序是前端由大到小到顺序编号。
      </template>
    </el-alert>
    <el-table :data="list" size="small" @sort-change="tableSort" height="calc(100vh - 400px)" stripe>
      <el-table-column label="ID" prop="id" sortable="custom" width="80"></el-table-column>
      <el-table-column label="区域" prop="area" sortable="custom">
        <template #default="scope">
          <el-tag size="small" effect="plain">{{
            scope.row.area === 'main'
              ? '主页'
              : scope.row.area === 'default'
                ? '默认'
                : '其他'
          }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="名称" prop="name" sortable="custom"></el-table-column>
      <el-table-column label="url" prop="url" sortable="custom" show-overflow-tooltip></el-table-column>
      <el-table-column label="icon" prop="iconUrl" sortable="custom" show-overflow-tooltip></el-table-column>
      <el-table-column label="系统类型" prop="sysType" sortable="custom"></el-table-column>
      <el-table-column label="用户标志" prop="userFlag" sortable="custom" width="100"></el-table-column>
      <el-table-column label="Token标志" prop="tokenFlag" sortable="custom" width="100"></el-table-column>
      <el-table-column label="排序" prop="sort" sortable="custom" width="80"></el-table-column>
      <el-table-column label="状态" prop="status" sortable="custom" width="120">
        <template #default="scope">
          <el-tag size="small" :type="scope.row.status === 0 ? 'danger' : 'success'" effect="dark">
            {{
              dictStore.dictList.sys_dict_status.find(
                (item) => item.code === scope.row.status.toString()
              )?.codeval
            }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" width="120">
        <template #default="scope">
          <el-button size="small" icon="Edit" type="warning" @click="handleEdit(scope.$index, scope.row)"
            circle></el-button>
          <el-button size="small" icon="Delete" type="danger" @click="handleDelete(scope.$index, scope.row)"
            circle></el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
      :page-sizes="pageSizes" :page-size="queryInfo.pageSize" layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
  </el-card>
  <!-- 弹出窗口 -->
  <el-dialog :title="dialogTitle" :close-on-click-modal="false" v-model="dialogFormVisible">
    <el-form :model="quickUrlForm" ref="quickUrlFormRef" :rules="quickUrlRules">
      <el-form-item label="名称" :label-width="formLabelWidth" prop="name">
        <el-input size="small" v-model="quickUrlForm.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="URL" :label-width="formLabelWidth" prop="url">
        <el-input size="small" v-model="quickUrlForm.url" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="图像" :label-width="formLabelWidth" prop="iconUrl">
        <el-input size="small" v-model="quickUrlForm.iconUrl" autocomplete="off"></el-input>
        <el-upload class="upload-demo" :action="fsURL + 'upload/file/icon'" :on-success="handleSuccess"
          :file-list="iconUrlList" :multiple="false" list-type="picture">
          <el-button size="small" type="primary">点击上传</el-button>
          <template #tip>
            <div class="el-upload__tip">只能上传jpg/png文件，且不超过50kb</div>
          </template>
        </el-upload>
      </el-form-item>
      <el-form-item label="系统类型" :label-width="formLabelWidth" prop="sysType">
        <el-input size="small" v-model="quickUrlForm.sysType" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="快链区域" :label-width="formLabelWidth" prop="area">
        <el-select v-model="quickUrlForm.area" size="small" placeholder="区域">
          <el-option label="全部" value=""></el-option>
          <el-option label="默认区域" value="default"></el-option>
          <el-option label="主页区域" value="main"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="用户标志" :label-width="formLabelWidth" prop="userFlag">
        <el-switch v-model="quickUrlForm.userFlag" active-text="带用户信息" inactive-text="不带用户信息" active-value="Y"
          inactive-value="N">
        </el-switch>
      </el-form-item>
      <el-form-item label="Token标志" :label-width="formLabelWidth" prop="tokenFlag">
        <el-switch v-model="quickUrlForm.tokenFlag" active-text="带Token信息" inactive-text="不带Token信息" active-value="Y"
          inactive-value="N">
        </el-switch>
      </el-form-item>
      <el-form-item label="序号" :label-width="formLabelWidth" prop="sort">
        <el-input size="small" type="number" v-model="quickUrlForm.sort" autocomplete="off"
          placeholder="越大越靠前（由大到小）"></el-input>
      </el-form-item>
      <el-form-item label="状态" :label-width="formLabelWidth" prop="status">
        <el-select size="small" v-model="quickUrlForm.status" placeholder="免登陆菜单状态">
          <el-option v-for="item in dictStore.dictList.sys_dict_status" :key="item.id" :label="item.codeval"
            :value="item.code"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button size="small" @click="dialogFormVisible = false">取 消</el-button>
        <el-button size="small" type="primary" @click="dialogFormSubmit">确 定</el-button>
      </span>
    </template>
  </el-dialog>
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
  if (res.code !== 200) return ElMessage.error(res.msg)
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
    await ElMessageBox.confirm('此操作将永久删除该记录, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(async () => {
      const res = await quickUrlApi.deleteQuickUrl(row)
      if (res.code !== 200) return ElMessage.error(res.msg)
      ElMessage.success(res.msg)
      getQuickUrlList()
    })
  } catch (error) {
    ElMessage.error('取消删除')
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
  try {
    await quickUrlFormRef.value.validate()
    const res =
      dialogTitle.value === '新增免登陆快链'
        ? await quickUrlApi.addQuickUrl(quickUrlForm)
        : await quickUrlApi.updateQuickUrl(quickUrlForm)
    if (res.code !== 200) return ElMessage.error(res.msg)
    ElMessage.success(res.msg)
    dialogFormVisible.value = false
    getQuickUrlList()
  } catch (error) {
    ElMessage.error('表单校验失败！请检查表单！')
  }
}

const tableSort = ({ order, prop }) => {
  queryInfo.orderType = order === 'ascending' ? ' asc ' : ' desc '
  queryInfo.order = prop
  getQuickUrlList()
}
</script>

<style lang="less" scoped>
.box-card {
  height: calc(100vh - 240px);
  overflow: auto;
}
</style>
