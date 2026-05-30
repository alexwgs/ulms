<template>
  <el-card class="box-card">
    <div class="table-filter">
      <el-row :gutter="15">
        <el-col :span="7">
          <el-select size="small" v-model="params.dataType" @change="getStatusType" placeholder="类型选择">
            <el-option label="状态" value="status"></el-option>
            <el-option label="工作安排" value="identity"></el-option>
          </el-select>
        </el-col>
        <el-col :span="7">
          <el-select size="small" v-model="params.levelGrade" @change="getStatusType" placeholder="级别选择">
            <el-option label="全部" value=""></el-option>
            <el-option label="一级菜单" :value="1"></el-option>
            <el-option label="二级菜单" :value="2"></el-option>
            <el-option label="三级菜单" :value="3"></el-option>
          </el-select>
        </el-col>
        <el-col :span="5">
          <el-select size="small" v-model="params.status" @change="getStatusType" placeholder="状态选择">
            <el-option label="生效" :value="1"></el-option>
            <el-option label="失效" :value="0"></el-option>
          </el-select>
        </el-col>
        <el-col :span="5">
          <el-button size="small" type="primary" @click="getStatusType">查询</el-button>
          <el-button size="small" type="primary" @click="dialog(null, 'add')">新增</el-button>
        </el-col>
      </el-row>
    </div>
    <el-table :data="tableData" size="small" stripe height="calc(100vh - 325px)">
      <el-table-column prop="id" label="ID"></el-table-column>
      <el-table-column prop="pid" label="上级ID"></el-table-column>
      <el-table-column prop="statusName" label="名称"></el-table-column>
      <el-table-column prop="levelGrade" label="级别"></el-table-column>
      <el-table-column prop="ohtFlag" label="接单状态">
        <template #default="scope">
          <el-tag :type="scope.row.ohtFlag === 0 ? 'danger' : 'success'" size="small" effect="plain">
            {{ scope.row.ohtFlag === 0 ? '不可派单' : '可派单' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="sort" label="排序"></el-table-column>
      <el-table-column prop="status" label="状态">
        <template #default="scope">
          <el-tag :type="scope.row.status === 0 ? 'danger' : 'success'" size="small" effect="plain">
            {{ scope.row.status === 0 ? '失效' : '有效' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="scope">
          <el-button type="warning" size="small" icon="el-icon-edit" @click="dialog(scope.row, 'update')"
            circle></el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @current-change="handleCurrentChange" :current-page="params.pageNum" :page-size="params.pageSize"
      layout="total, prev, pager, next, jumper" :total="total">
    </el-pagination>
  </el-card>

  <el-dialog title="业务主任状态配置维护" :close-on-click-modal="false" v-model="statusTypeVisible"
    @close="closeStatusTypeDialog">
    <el-form :model="statusTypeForm" ref="statusTypeRef" :rules="statusTypeFormRules">
      <el-form-item label="状态" :label-width="formLabelWidth" prop="dataType">
        <el-select size="small" v-model="statusTypeForm.dataType" placeholder="请选择">
          <el-option label="状态" value="status"></el-option>
          <el-option label="工作安排" value="identity"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="ID" :label-width="formLabelWidth" prop="id">
        <el-input size="small" v-model="statusTypeForm.id" autocomplete="off" placeholder="ID自动生成" disabled></el-input>
      </el-form-item>
      <el-form-item label="上级ID" :label-width="formLabelWidth" prop="pid">
        <el-input size="small" type="number" v-model="statusTypeForm.pid" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="名称" :label-width="formLabelWidth" prop="statusName">
        <el-input size="small" v-model="statusTypeForm.statusName" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="菜单级别" :label-width="formLabelWidth" prop="levelGrade">
        <el-select size="small" v-model="statusTypeForm.levelGrade" placeholder="请选择">
          <el-option label="一级菜单" :value="1"></el-option>
          <el-option label="二级菜单" :value="2"></el-option>
          <el-option label="三级菜单" :value="3"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="接单状态" :label-width="formLabelWidth" prop="ohtFlag">
        <el-select size="small" v-model="statusTypeForm.ohtFlag" placeholder="请选择">
          <el-option label="不派求助" :value="0"></el-option>
          <el-option label="派单求助" :value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="排序" :label-width="formLabelWidth" prop="sort">
        <el-input size="small" type="number" v-model="statusTypeForm.sort" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="状态" :label-width="formLabelWidth" prop="status">
        <el-select size="small" v-model="statusTypeForm.status" placeholder="请选择">
          <el-option label="有效" :value="1"></el-option>
          <el-option label="无效" :value="0"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button size="small" @click="closeStatusTypeDialog">取 消</el-button>
        <el-button size="small" type="primary" @click="submit">确 定</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { httpInstance } from '@/utils/request'

const tableData = ref([])
const statusTypeVisible = ref(false)
const formLabelWidth = ref('80px')
const manageType = ref('')
const statusTypeRef = ref(null)

const params = reactive({
  dataType: '',
  levelGrade: '',
  status: 1,
  querytype: '',
  query: '',
  pageSize: 20,
  pageNum: 1
})

const total = ref(0)

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

const getStatusType = async () => {
  try {
    const res = await httpInstance.get('oht/statusType/list', { params })
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    tableData.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    ElMessage.error('获取数据失败')
    console.error(error)
  }
}

const dialog = (data, type) => {
  manageType.value = type
  if (type === 'update' && data) {
    Object.assign(statusTypeForm, data)
  }
  statusTypeVisible.value = true
}

const handleCurrentChange = (page) => {
  params.pageNum = page
  getStatusType()
}

const closeStatusTypeDialog = () => {
  statusTypeRef.value?.resetFields()
  Object.assign(statusTypeForm, { id: '', pid: '', statusName: '', levelGrade: '', ohtFlag: '', sort: '', status: '' })
  statusTypeVisible.value = false
}

const submit = () => {
  statusTypeRef.value?.validate(async (valid) => {
    if (valid) {
      try {
        let res
        if (manageType.value === 'add') {
          res = await httpInstance.post('oht/statusType/add', statusTypeForm)
        } else if (manageType.value === 'update') {
          res = await httpInstance.put('oht/statusType/update', statusTypeForm)
        }
        if (res.code !== 200) {
          ElMessage.error(res.msg)
          return
        }
        ElMessage.success(res.msg)
        statusTypeVisible.value = false
        getStatusType()
      } catch (error) {
        ElMessage.error('操作失败')
        console.error(error)
      }
    } else {
      ElMessage.error('请正确填写表单!')
      return false
    }
  })
}

onMounted(() => {
  getStatusType()
})
</script>

<style lang="less" scoped>
.table-filter {
  padding: 10px;

  span {
    font-size: 12px;
    margin-left: 20px;
  }

  .el-select {
    width: 70%;
  }
}

.box-card {
  height: calc(100vh - 190px);
  overflow: auto;
}
</style>
