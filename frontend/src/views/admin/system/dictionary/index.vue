<template>
  <PageTips title="操作说明" theme="info" :closable="false"
    message="请正确使用字典配置：1.字典名称分段命名，以下划线分段。2.第一段代表子系统、第二段代表表名称、第三段以后代表字段含义。3.注意部分字典描述为JSON有特别含义！" />
  <t-card class="management-card">
    <t-form :data="queryInfo" label-width="80px" colon class="filter-form">
      <t-row :gutter="[24, 24]">
        <t-col :span="5">
          <t-form-item label="关键字" name="query">
            <t-input-adornment class="input-with-select">
              <template #append>
                <t-button variant="outline" theme="primary" size="small" @click="getDictionaryList()">搜索</t-button>
              </template>
              <t-input placeholder="模糊搜索" size="small" v-model="queryInfo.query"></t-input>
            </t-input-adornment>
          </t-form-item>
        </t-col>
        <t-col :span="3">
          <t-form-item label="状态" name="status">
            <t-select v-model="queryInfo.status" size="small" placeholder="全部状态" @change="getDictionaryList">
              <t-option label="全部状态" value=""></t-option>
              <t-option v-for="item in (dictStore.dictList?.sys_dict_status || [])" :key="item.id" :label="item.codeval"
                :value="item.code"></t-option>
            </t-select>
          </t-form-item>
        </t-col>
        <t-col :span="3" class="operation-container">
          <t-button variant="outline" theme="primary" size="small" @click="addDictionary">添加字典</t-button>
        </t-col>
      </t-row>
    </t-form>
    <CustomTable rowKey="id" :data="dictionaryTableList" size="small" height="calc(100vh - 400px)" stripe @sort-change="tableSort"
      style="width: 100%">
      <TableColumn colKey="id" label="ID" width="100" sortable="custom"></TableColumn>
      <TableColumn colKey="name" label="字典名称" width="150" ellipsis sortable="custom"></TableColumn>
      <TableColumn colKey="code" label="KEY" width="100" sortable="custom"></TableColumn>
      <TableColumn colKey="codeval" label="VALUE" width="150" sortable="custom"></TableColumn>
      <TableColumn colKey="description" label="描述" ellipsis sortable="custom"></TableColumn>
      <TableColumn colKey="status" label="状态" width="100" sortable="custom">
        <template #default="scope">
          <t-tag size="small" :theme="scope.row.status === 0 ? 'danger' : 'success'" variant="light">
            {{
              (dictStore.dictList?.sys_dict_status || []).find(
                (item) => item.code === scope.row.status.toString()
              )?.codeval
            }}</t-tag>
        </template>
      </TableColumn>
      <TableColumn label="操作" fixed="right" width="120">
        <template #default="scope">
          <t-button variant="outline" size="small" theme="default" @click="editDictionary(scope.row)">编辑</t-button>
          <t-button variant="outline" size="small" theme="danger" @click="removeDictionary(scope.row)">删除</t-button>
        </template>
      </TableColumn>
    </CustomTable>
    <t-pagination @page-size-change="handleSizeChange" @current-change="handleCurrentChange" :current="currentPage"
      :page-size-options="pageSizes" :page-size="queryInfo.pageSize"
      :total="total"></t-pagination>
  </t-card>

  <t-dialog :header="dialogTitle" v-model:visible="dialogVisible" :close-on-overlay-click="false" @close="closeDialog"
    width="600px">
    <t-form :data="dictionaryForm" ref="dictionaryFormRef" :rules="dictionaryRules">
      <t-form-item label="字典名称" name="name" :label-width="formLabelWidth">
        <t-input size="small" v-model="dictionaryForm.name" autocomplete="off"></t-input>
      </t-form-item>
      <t-form-item label="KEY" name="code" :label-width="formLabelWidth">
        <t-input size="small" v-model="dictionaryForm.code" autocomplete="off"></t-input>
      </t-form-item>
      <t-form-item label="VAL" name="codeval" :label-width="formLabelWidth">
        <t-input size="small" v-model="dictionaryForm.codeval" autocomplete="off"></t-input>
      </t-form-item>
      <t-form-item label="描述" name="description" :label-width="formLabelWidth">
        <t-input size="small" v-model="dictionaryForm.description" autocomplete="off"></t-input>
      </t-form-item>
      <t-form-item label="状态" name="status" :label-width="formLabelWidth">
        <t-select size="small" v-model="dictionaryForm.status" placeholder="请选择">
          <t-option v-for="item in dictionaryStatus" :key="item.code" :label="item.codeval"
            :value="parseInt(item.code)"></t-option>
        </t-select>
      </t-form-item>
    </t-form>
    <template #footer>
      <t-space>
        <t-button variant="outline" size="small" @click="dialogVisible = false">取 消</t-button>
        <t-button variant="outline" size="small" theme="primary" @click="submitForm">确 定</t-button>
      </t-space>
    </template>
  </t-dialog>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useDictStore } from '@/stores'
import { dictionaryApi } from '@/api/system/dictionary'
import { usePagination } from '@/hooks/usePagination'
import { useConfirm } from '@/hooks/useConfirm'

// 数据定义
const dictStore = useDictStore()
const dictionaryTableList = ref([])
const dictionaryStatus = ref([])
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
    MessagePlugin.error(error.message)
  }
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
  const ok = await confirm('此操作将永久删除该记录, 是否继续?')
  if (!ok) return
  const res = await dictionaryApi.deleteDictionary(data.id)
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  MessagePlugin.success(res.msg)
  getDictionaryList()
}

const submitForm = async () => {
  const valid = await dictionaryFormRef.value.validate()
  if (valid !== true) return

  try {
    const isAdd = dialogTitle.value === '新增字典信息'

    let res = null
    if (isAdd) {
      res = await dictionaryApi.addDictionary(dictionaryForm)
    } else {
      res = await dictionaryApi.updateDictionary(dictionaryForm)
    }
    if (res.code !== 200) return
    MessagePlugin.success(res.msg)
    dialogVisible.value = false
    getDictionaryList()
  } catch (error) {
    if (error.message) {
      MessagePlugin.error(error.message)
    }
  }
}

const closeDialog = () => {
  dictionaryFormRef.value?.resetFields()
}

const tableSort = ({ sortBy, descending }) => {
  queryInfo.orderType = !descending ? ' asc ' : ' desc '
  queryInfo.order = sortBy
  getDictionaryList()
}
const { currentPage, pageSizes, handleCurrentChange, handleSizeChange } = usePagination({ query: queryInfo, fetch: getDictionaryList, pageSizes: [20, 100, 500] })
const { confirm } = useConfirm()
</script>

<style lang="less" scoped>
.management-card {
  height: calc(100vh - 240px);

  .t-tag {
    margin-right: 5px;
  }
}

.dialog-footer {
  text-align: right;
}
</style>
