<template>
  <div>
    <el-card class="box-card">
      <div class="table-filter">
        <el-row :gutter="15">
          <el-col :span="5">
            <el-input size="small" v-model="params.userId" placeholder="请输入查询工号"></el-input>
          </el-col>
          <el-col :span="8">
            <el-date-picker size="small" style="width:200px" v-model="dateRange" value-format="YYYY-MM-DD"
              type="daterange" @change="datePick" range-separator="至" start-placeholder="开始日期"
              end-placeholder="结束日期"></el-date-picker>
          </el-col>
          <el-col :span="5">
            <el-select size="small" v-model="params.status" @change="getTodolist" placeholder="状态选择">
              <el-option label="待办" :value="0"></el-option>
              <el-option label="已完成" :value="1"></el-option>
            </el-select>
          </el-col>
          <el-col :span="6">
            <el-button size="small" type="primary" @click="getTodolist">查询</el-button>
            <el-button size="small" type="primary" @click="openTodoDialog(null, 'add')">新增</el-button>
          </el-col>
        </el-row>
      </div>
      <el-table :data="tableData" size="small" stripe height="calc(100vh - 330px)">
        <el-table-column prop="dataDate" sort label="日期"></el-table-column>
        <el-table-column prop="userId" sort label="用户ID"></el-table-column>
        <el-table-column prop="title" sort label="标题"></el-table-column>
        <el-table-column prop="content" sort label="详情" show-overflow-tooltip></el-table-column>
        <el-table-column prop="priority" sort label="优先级"></el-table-column>
        <el-table-column prop="alert" sort label="提醒">
          <template #default="scope">
            <el-tag :type="scope.row.alertFlag === 0 ? 'danger' : 'success'" size="small" effect="plain">
              {{ scope.row.alertFlag === 0 ? '否' : scope.row.alertTime }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" sort label="状态">
          <template #default="scope">
            <el-tag :type="scope.row.status === 0 ? 'danger' : 'success'" size="small" effect="plain">
              {{ scope.row.status === 0 ? '未完成' : '已完成' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120px">
          <template #default="scope">
            <el-button type="warning" size="small" icon="el-icon-edit" @click="openTodoDialog(scope.row, 'edit')"
              circle></el-button>
            <el-button size="small" type="danger" icon="el-icon-delete" @click="deleteTodo(scope.row)"
              circle></el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination @current-change="handleCurrentChange" :current-page="params.pageNum" :page-size="params.pageSize"
        layout="total, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </el-card>

    <el-dialog width="50%" title="待办事项管理" v-model="todolistVisible">
      <el-form :model="todoForm" :rules="rules" ref="todoFormRef" label-width="100px">
        <el-form-item label="待办日期" prop="dataDate">
          <el-date-picker size="small" v-model="todoForm.dataDate" value-format="yyyy-MM-dd" type="date"
            placeholder="选择日期"></el-date-picker>
        </el-form-item>
        <el-form-item label="待办人员" prop="userId" style="margin-bottom:0">
          <el-col :span="24">
            <el-radio-group size="small" v-model="userType" @change="userTypeChange">
              <el-radio-button :label="true">全体人员</el-radio-button>
              <el-radio-button :label="false">指定人员</el-radio-button>
            </el-radio-group>
          </el-col>
          <el-col :span="24">
            <el-input style="width:100%" placeholder="" size="small" v-model="todoForm.userId" :readonly="userType">
              <template #append>
                <el-button :disabled="userType" @click="treeDialogVisiable = !treeDialogVisiable"
                  icon="el-icon-zoom-in"></el-button>
              </template>
            </el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="待办摘要" prop="title">
          <el-input size="small" v-model="todoForm.title"></el-input>
        </el-form-item>
        <el-form-item label="待办详情" prop="content">
          <el-input type="textarea" size="small" v-model="todoForm.content"></el-input>
        </el-form-item>
        <el-form-item label="优先级" prop="name">
          <el-checkbox-group size="small" v-model="priorityList">
            <el-checkbox label="重要"></el-checkbox>
            <el-checkbox label="紧急"></el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="是否提醒" required>
          <el-col :span="5">
            <el-switch size="small" v-model="alertFlag" @change="alertSwitch"></el-switch>
          </el-col>
          <el-col :span="18">
            <el-form-item prop="time">
              <el-time-picker size="small" placeholder="选择时间" :disabled="!alertFlag" value-format="HH:mm:ss"
                v-model="todoForm.alertTime" style="width: 100%;"></el-time-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="是否完成" prop="status">
          <el-select size="small" v-model="todoForm.status">
            <el-option label="待办" :value="0"></el-option>
            <el-option label="已完成" :value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="small" @click="submit()">{{ todoType === 'add' ? '立即创建' : '提交修改'
          }}</el-button>
          <el-button size="small" @click="closeTodoDialog">取 消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <EmpTree v-model:treeVisiable="treeDialogVisiable" type="all" :mutiselect="true" @getChecked="getTreeChecked"></EmpTree>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { httpInstance } from '@/utils/request'
import EmpTree from '@/components/EmpTree.vue'

const dateRange = ref([])
const priorityList = ref([])
const tableData = ref([])
const todolistVisible = ref(false)
const todoType = ref('')
const total = ref(0)
const userType = ref(true)
const alertFlag = ref(false)
const treeDialogVisiable = ref(false)
const todoFormRef = ref(null)

const params = reactive({
  userId: '',
  begDate: '',
  endDate: '',
  status: '',
  querytype: '',
  query: '',
  pageSize: 20,
  pageNum: 1
})

const todoForm = reactive({
  dataDate: '',
  title: '',
  userId: 'admin',
  content: '',
  priority: '',
  alertFlag: 0,
  alertTime: '',
  status: 0
})

const rules = {
  title: [
    { required: true, message: '请输入待办摘要,尽可能简洁', trigger: 'blur' },
    { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
  ],
  userId: [
    { required: true, message: '请选择待办的用户', trigger: 'blur' }
  ],
  dataDate: [
    { required: true, message: '请选择待办的日期', trigger: 'blur' }
  ]
}

const getTreeChecked = (data) => {
  let userListStr = ''
  let userIdStr = ''
  for (const item of data) {
    userListStr += item.label + ','
    userIdStr += item.id + ','
  }
  todoForm.userId = userIdStr.substring(0, userIdStr.length - 1)
}

const userTypeChange = (e) => {
  if (e) todoForm.userId = 'admin'
  else todoForm.userId = ''
}

const getTodolist = async () => {
  try {
    const res = await httpInstance.get('sys/todo/list', { params })
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    tableData.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    ElMessage.error('获取待办列表失败')
    console.error(error)
  }
}

const alertSwitch = (e) => {
  if (e === true) {
    todoForm.alertFlag = 1
  } else {
    todoForm.alertTime = ''
    todoForm.alertFlag = 0
  }
}

const datePick = () => {
  params.begDate = dateRange.value[0]
  params.endDate = dateRange.value[1]
  getTodolist()
}

const handleCurrentChange = (page) => {
  params.pageNum = page
  getTodolist()
}

const closeTodoDialog = () => {
  todoFormRef.value?.resetFields()
  Object.assign(todoForm, { dataDate: '', title: '', userId: 'admin', content: '', priority: '', alertFlag: 0, alertTime: '', status: 0 })
  todolistVisible.value = false
}

const submit = () => {
  todoFormRef.value?.validate(async (valid) => {
    if (valid) {
      try {
        todoForm.priority = priorityList.value.join(',')
        let res
        if (todoType.value === 'add') {
          res = await httpInstance.post('sys/todo/admin/add', todoForm)
        } else if (todoType.value === 'edit') {
          res = await httpInstance.put('sys/todo', todoForm)
        }
        if (res.code !== 200) {
          ElMessage.error(res.msg)
          return
        }
        ElMessage.success(res.msg)
        todolistVisible.value = false
        getTodolist()
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

const deleteTodo = (row) => {
  ElMessageBox.confirm('你是否确定永久删除此待办，不可恢复', '确认信息', {
    distinguishCancelAndClose: true,
    confirmButtonText: '确认',
    cancelButtonText: '放弃删除'
  })
    .then(async () => {
      try {
        const res = await httpInstance.delete(`sys/todo/${row.id}`)
        if (res.code !== 200) {
          ElMessage.error(res.msg)
          return
        }
        ElMessage.success(res.msg)
        getTodolist()
      } catch (error) {
        ElMessage.error('删除失败')
        console.error(error)
      }
    })
    .catch(() => {
      // 用户取消操作
    })
}

const openTodoDialog = (row, type) => {
  if (type === 'add') {
    todoType.value = 'add'
    Object.assign(todoForm, { dataDate: '', title: '', userId: 'admin', content: '', priority: '', alertFlag: 0, alertTime: '', status: 0 })
    priorityList.value = []
    alertFlag.value = false
  } else if (type === 'edit') {
    todoType.value = 'edit'
    Object.assign(todoForm, { ...row })
    if (row.alertFlag === 0) alertFlag.value = false
    else alertFlag.value = true
  }
  todolistVisible.value = true
}

onMounted(() => {
  getTodolist()
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
</style>
