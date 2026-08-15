<template>
  <t-card class="management-card">
    <t-form :data="params" label-width="80px" colon class="filter-form">
      <t-row :gutter="[24, 24]">
        <t-col :span="3">
          <t-form-item label="类型" name="dataType">
            <t-select size="small" v-model="params.dataType" @change="getStatusType" placeholder="请选择">
              <t-option label="状态" value="status"></t-option>
              <t-option label="工作安排" value="identity"></t-option>
            </t-select>
          </t-form-item>
        </t-col>
        <t-col :span="3">
          <t-form-item label="级别" name="levelGrade">
            <t-select size="small" v-model="params.levelGrade" @change="getStatusType" placeholder="全部">
              <t-option label="全部" value=""></t-option>
              <t-option label="一级菜单" :value="1"></t-option>
              <t-option label="二级菜单" :value="2"></t-option>
              <t-option label="三级菜单" :value="3"></t-option>
            </t-select>
          </t-form-item>
        </t-col>
        <t-col :span="3">
          <t-form-item label="状态" name="status">
            <t-select size="small" v-model="params.status" @change="getStatusType" placeholder="请选择">
              <t-option label="生效" :value="1"></t-option>
              <t-option label="失效" :value="0"></t-option>
            </t-select>
          </t-form-item>
        </t-col>
        <t-col :span="3" class="operation-container">
          <t-button variant="outline" size="small" theme="primary" @click="getStatusType">查询</t-button>
          <t-button variant="outline" size="small" theme="primary" @click="dialog(null, 'add')">新增</t-button>
        </t-col>
      </t-row>
    </t-form>
    <CustomTable rowKey="id" :data="tableData" size="small" stripe height="calc(100vh - 325px)">
      <TableColumn colKey="id" label="ID"></TableColumn>
      <TableColumn colKey="pid" label="上级ID"></TableColumn>
      <TableColumn colKey="statusName" label="名称"></TableColumn>
      <TableColumn colKey="levelGrade" label="级别"></TableColumn>
      <TableColumn colKey="ohtFlag" label="接单状态">
        <template #default="scope">
          <t-tag :theme="scope.row.ohtFlag === 0 ? 'danger' : 'success'" size="small" variant="light">
            {{ scope.row.ohtFlag === 0 ? '不可派单' : '可派单' }}
          </t-tag>
        </template>
      </TableColumn>
      <TableColumn colKey="sort" label="排序"></TableColumn>
      <TableColumn colKey="status" label="状态">
        <template #default="scope">
          <t-tag :theme="scope.row.status === 0 ? 'danger' : 'success'" size="small" variant="light">
            {{ scope.row.status === 0 ? '失效' : '有效' }}
          </t-tag>
        </template>
      </TableColumn>
      <TableColumn label="操作" width="100">
        <template #default="scope">
          <t-button variant="outline" theme="default" size="small" @click="dialog(scope.row, 'update')"
           >编辑</t-button>
        </template>
      </TableColumn>
    </CustomTable>
    <t-pagination @page-size-change="handleSizeChange" @current-change="handleCurrentChange" :current="currentPage"
      :page-size-options="pageSizes" :page-size="params.pageSize" :total="total">
    </t-pagination>
  </t-card>

  <t-dialog header="业务主任状态配置维护" :close-on-overlay-click="false" v-model:visible="statusTypeVisible"
    @close="closeStatusTypeDialog">
    <t-form :data="statusTypeForm" ref="statusTypeRef" :rules="statusTypeFormRules">
      <t-form-item label="状态" :label-width="formLabelWidth" name="dataType">
        <t-select size="small" v-model="statusTypeForm.dataType" placeholder="请选择">
          <t-option label="状态" value="status"></t-option>
          <t-option label="工作安排" value="identity"></t-option>
        </t-select>
      </t-form-item>
      <t-form-item label="ID" :label-width="formLabelWidth" name="id">
        <t-input size="small" v-model="statusTypeForm.id" autocomplete="off" placeholder="ID自动生成" disabled></t-input>
      </t-form-item>
      <t-form-item label="上级ID" :label-width="formLabelWidth" name="pid">
        <t-input size="small" type="number" v-model="statusTypeForm.pid" autocomplete="off"></t-input>
      </t-form-item>
      <t-form-item label="名称" :label-width="formLabelWidth" name="statusName">
        <t-input size="small" v-model="statusTypeForm.statusName" autocomplete="off"></t-input>
      </t-form-item>
      <t-form-item label="菜单级别" :label-width="formLabelWidth" name="levelGrade">
        <t-select size="small" v-model="statusTypeForm.levelGrade" placeholder="请选择">
          <t-option label="一级菜单" :value="1"></t-option>
          <t-option label="二级菜单" :value="2"></t-option>
          <t-option label="三级菜单" :value="3"></t-option>
        </t-select>
      </t-form-item>
      <t-form-item label="接单状态" :label-width="formLabelWidth" name="ohtFlag">
        <t-select size="small" v-model="statusTypeForm.ohtFlag" placeholder="请选择">
          <t-option label="不派求助" :value="0"></t-option>
          <t-option label="派单求助" :value="1"></t-option>
        </t-select>
      </t-form-item>
      <t-form-item label="排序" :label-width="formLabelWidth" name="sort">
        <t-input size="small" type="number" v-model="statusTypeForm.sort" autocomplete="off"></t-input>
      </t-form-item>
      <t-form-item label="状态" :label-width="formLabelWidth" name="status">
        <t-select size="small" v-model="statusTypeForm.status" placeholder="请选择">
          <t-option label="有效" :value="1"></t-option>
          <t-option label="无效" :value="0"></t-option>
        </t-select>
      </t-form-item>
    </t-form>
    <template #footer>
      <t-space>
        <t-button variant="outline" size="small" @click="closeStatusTypeDialog">取 消</t-button>
        <t-button variant="outline" size="small" theme="primary" @click="submit">确 定</t-button>
      </t-space>
    </template>
  </t-dialog>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { httpInstance } from '@/utils/request'
import { useCrudPage } from '@/hooks/useCrudPage'

// 列表 + 分页（useCrudPage 样板）
const {
  list: tableData,
  total,
  query: params,
  currentPage,
  pageSizes,
  handleCurrentChange,
  handleSizeChange,
  load: getStatusType
} = useCrudPage({
  fetchList: (q) => httpInstance.get('oht/statusType/list', { params: q }),
  defaultQuery: {
    dataType: '',
    levelGrade: '',
    status: 1,
    querytype: '',
    query: '',
    pageSize: 20,
    pageNum: 1
  },
  pageSizes: [20, 100, 500]
})

const statusTypeVisible = ref(false)
const formLabelWidth = ref('80px')
const manageType = ref('')
const statusTypeRef = ref(null)

const statusTypeForm = reactive({
  id: '',
  pid: '',
  statusName: '',
  levelGrade: '',
  ohtFlag: '',
  sort: '',
  status: ''
})

const statusTypeFormRules = {
  dataType: [{ required: true, message: '请输入上级ID，无上级则输入0', trigger: 'change' }],
  pid: [{ required: true, message: '请输入上级ID，无上级则输入0', trigger: 'blur' }],
  statusName: [
    { required: true, message: '请输入状态名称', trigger: 'blur' },
    { min: 2, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
  ],
  levelGrade: [{ required: true, message: '该菜单级别不可为空，若为身份则为1级', trigger: 'change' }],
  ohtFlag: [{ required: true, message: '是否派单状态不可为空', trigger: 'change' }],
  sort: [{ required: true, message: '排序不可为空，自小而大', trigger: 'blur' }],
  status: [{ required: true, message: '状态不可为空', trigger: 'change' }]
}

const dialog = (data, type) => {
  manageType.value = type
  if (type === 'update' && data) {
    Object.assign(statusTypeForm, data)
  }
  statusTypeVisible.value = true
}

const closeStatusTypeDialog = () => {
  statusTypeRef.value?.resetFields()
  Object.assign(statusTypeForm, { id: '', pid: '', statusName: '', levelGrade: '', ohtFlag: '', sort: '', status: '' })
  statusTypeVisible.value = false
}

const submit = async () => {
  const valid = await statusTypeRef.value?.validate()
  if (valid === true) {
    try {
      let res
      if (manageType.value === 'add') {
        res = await httpInstance.post('oht/statusType/add', statusTypeForm)
      } else if (manageType.value === 'update') {
        res = await httpInstance.put('oht/statusType/update', statusTypeForm)
      }
      if (res.code !== 200) {
        MessagePlugin.error(res.msg)
        return
      }
      MessagePlugin.success(res.msg)
      statusTypeVisible.value = false
      getStatusType()
    } catch (error) {
      MessagePlugin.error('操作失败')
      console.error(error)
    }
  } else {
    MessagePlugin.error('请正确填写表单!')
  }
}

onMounted(() => {
  getStatusType()
})
</script>

<style lang="less" scoped>
.management-card {
  height: calc(100vh - 190px);
  overflow: auto;
}
</style>
