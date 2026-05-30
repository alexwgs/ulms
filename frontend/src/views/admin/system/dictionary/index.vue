<template>
  <el-alert title="操作说明" type="info" :closable="false"
    description="请正确使用字典配置：1.字典名称分段命名，以下划线分段。2.第一段代表子系统、第二段代表表名称、第三段以后代表字段含义。3.注意部分字典描述为JSON有特别含义！" />
  <el-card class="box-card">
    <el-row :gutter="20">
      <el-col :span="10">
        <el-input placeholder="模糊搜索" size="small" v-model="queryInfo.query" class="input-with-select">
          <template #append>
            <el-button size="small" icon="Search" @click="getDictionaryList()"></el-button>
          </template>
        </el-input>
      </el-col>
      <el-col :span="4">
        <el-select v-model="queryInfo.status" size="small" placeholder="全部状态" @change="getDictionaryList">
          <el-option label="全部状态" value=""></el-option>
          <el-option v-for="item in dictStore.dictList.sys_dict_status" :key="item.id" :label="item.codeval"
            :value="item.code"></el-option>
        </el-select>
      </el-col>
      <el-col :span="10">
        <el-button type="primary" size="small" @click="addDictionary">添加字典</el-button>
      </el-col>
    </el-row>
    <el-alert title="操作说明" type="info" :closable="false">
      <template #description>
        请正确使用字典配置：1.字典名称分段命名，以下划线分段。2.第一段代表子系统、第二段代表表名称、第三段以后代表字段含义。3.注意部分字典描述为JSON有特别含义！
      </template>
    </el-alert>
    <el-table :data="dictionaryTableList" size="small" height="calc(100vh - 400px)" stripe @sort-change="tableSort"
      style="width: 100%">
      <el-table-column prop="id" label="ID" width="100" sortable="custom"></el-table-column>
      <el-table-column prop="name" label="字典名称" width="150" show-overflow-tooltip sortable="custom"></el-table-column>
      <el-table-column prop="code" label="KEY" width="100" sortable="custom"></el-table-column>
      <el-table-column prop="codeval" label="VALUE" width="150" sortable="custom"></el-table-column>
      <el-table-column prop="description" label="描述" show-overflow-tooltip sortable="custom"></el-table-column>
      <el-table-column prop="status" label="状态" width="100" sortable="custom">
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
          <el-button size="small" icon="Edit" type="warning" @click="editDictionary(scope.row)" circle></el-button>
          <el-button size="small" icon="Delete" type="danger" @click="removeDictionary(scope.row)" circle></el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
      :page-sizes="pageSizes" :page-size="queryInfo.pageSize" layout="total, sizes, prev, pager, next, jumper"
      :total="total"></el-pagination>
  </el-card>

  <el-dialog :title="dialogTitle" v-model="dialogVisible" :close-on-click-modal="false" @close="closeDialog"
    width="600px">
    <el-form :model="dictionaryForm" ref="dictionaryFormRef" :rules="dictionaryRules">
      <el-form-item label="字典名称" prop="name" :label-width="formLabelWidth">
        <el-input size="small" v-model="dictionaryForm.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="KEY" prop="code" :label-width="formLabelWidth">
        <el-input size="small" v-model="dictionaryForm.code" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="VAL" prop="codeval" :label-width="formLabelWidth">
        <el-input size="small" v-model="dictionaryForm.codeval" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="描述" prop="description" :label-width="formLabelWidth">
        <el-input size="small" v-model="dictionaryForm.description" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="status" :label-width="formLabelWidth">
        <el-select size="small" v-model="dictionaryForm.status" placeholder="请选择">
          <el-option v-for="item in dictionaryStatus" :key="item.code" :label="item.codeval"
            :value="parseInt(item.code)"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">取 消</el-button>
        <el-button size="small" type="primary" @click="submitForm">确 定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useDictStore } from '@/stores'
import { dictionaryApi } from '@/api/system/dictionary'

// 数据定义
const dictStore = useDictStore()
const dictionaryTableList = ref([])
const dictionaryStatus = ref([])
const currentPage = ref(1)
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formLabelWidth = '120px'
const dictionaryFormRef = ref(null)

const queryInfo = reactive({
  orderType: ' asc',
  order: ' id ',
  status: '',
  querytype: '',
  query: '',
  pageSize: 20,
  pageNum: 1
})

const dictionaryForm = reactive({
  id: '',
  name: '',
  code: '',
  codeval: '',
  description: '',
  status: 1
})

const pageSizes = [20, 100, 500]
const total = ref(0)

// 表单验证规则
const dictionaryRules = reactive({
  name: [
    { required: true, message: '请输入字典名称', trigger: 'blur' },
    { min: 2, max: 50, message: '长度在2到50个字符', trigger: 'blur' }
  ],
  code: [
    { required: true, message: '请输入KEY', trigger: 'blur' },
    { min: 1, max: 25, message: '长度在1到25个字符', trigger: 'blur' }
  ],
  codeval: [
    { required: true, message: '请输入VALUE', trigger: 'blur' },
    { min: 1, max: 100, message: '长度在1到100个字符', trigger: 'blur' }
  ],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }]
})

// 生命周期
onMounted(() => {
  // 初始化字典状态，实际项目中可能需要从API获取
  dictionaryStatus.value = [
    { code: '0', codeval: '无效' },
    { code: '1', codeval: '有效' }
  ]
  getDictionaryList()
})

// 方法
const getDictionaryList = async () => {
  try {
    const res = await dictionaryApi.listDictionary(queryInfo)
    if (res.code !== 200) throw new Error(res.msg)
    dictionaryTableList.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    ElMessage.error(error.message)
  }
}

const handleSizeChange = (pageSize) => {
  queryInfo.pageSize = pageSize
  getDictionaryList()
}

const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  getDictionaryList()
}

const addDictionary = () => {
  dialogTitle.value = '新增字典信息'
  dialogVisible.value = true
  Object.assign(dictionaryForm, {
    id: '',
    name: '',
    code: '',
    codeval: '',
    description: '',
    status: 1
  })
}

const editDictionary = (data) => {
  dialogTitle.value = '修改字典信息'
  dialogVisible.value = true
  Object.assign(dictionaryForm, {
    id: data.id,
    name: data.name,
    code: data.code,
    codeval: data.codeval,
    description: data.description,
    status: data.status
  })
}

const removeDictionary = async (data) => {
  try {
    await ElMessageBox.confirm('此操作将永久删除该记录, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    const res = await dictionaryApi.deleteDictionary(data.id)
    if (res.code !== 200) throw new Error(res.msg)
    ElMessage.success(res.msg)
    getDictionaryList()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error(error.message || '取消删除')
    }
  }
}

const submitForm = async () => {
  try {
    await dictionaryFormRef.value.validate()

    const isAdd = dialogTitle.value === '新增字典信息'

    let res = null
    if (isAdd) {
      res = await dictionaryApi.addDictionary(dictionaryForm)
    } else {
      res = await dictionaryApi.updateDictionary(dictionaryForm)
    }
    if (res.code !== 200) return
    ElMessage.success(res.msg)
    dialogVisible.value = false
    getDictionaryList()
  } catch (error) {
    if (error.message) {
      ElMessage.error(error.message)
    }
  }
}

const closeDialog = () => {
  dictionaryFormRef.value?.resetFields()
}

const tableSort = ({ order, prop }) => {
  queryInfo.orderType = order === 'ascending' ? ' asc ' : ' desc '
  queryInfo.order = prop
  getDictionaryList()
}
</script>

<style lang="less" scoped>
.box-card {
  height: calc(100vh - 240px);

  .el-tag {
    margin-right: 5px;
  }
}

.dialog-footer {
  text-align: right;
}
</style>
