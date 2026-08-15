<template>
  <div>
    <t-card class="management-card">
      <t-form :data="params" label-width="80px" colon class="filter-form">
        <t-row :gutter="[24, 24]">
          <t-col :span="3">
            <t-form-item label="工号" name="userId">
              <t-input size="small" v-model="params.userId" placeholder="请输入查询工号"></t-input>
            </t-form-item>
          </t-col>
          <t-col :span="4">
            <t-form-item label="日期范围" name="dateRange">
              <t-date-range-picker size="small" v-model="dateRange" format="YYYY-MM-DD" @change="datePick" :placeholder="['开始日期', '结束日期']"></t-date-range-picker>
            </t-form-item>
          </t-col>
          <t-col :span="3">
            <t-form-item label="状态" name="status">
              <t-select size="small" v-model="params.status" @change="getTodolist" placeholder="请选择">
                <t-option label="待办" :value="0"></t-option>
                <t-option label="已完成" :value="1"></t-option>
              </t-select>
            </t-form-item>
          </t-col>
          <t-col :span="3" class="operation-container">
            <t-button variant="outline" size="small" theme="primary" @click="getTodolist">查询</t-button>
            <t-button variant="outline" size="small" theme="primary" @click="openTodoDialog(null, 'add')">新增</t-button>
          </t-col>
        </t-row>
      </t-form>
      <CustomTable rowKey="id" :data="tableData" size="small" stripe height="calc(100vh - 330px)">
        <TableColumn colKey="dataDate" sort label="日期"></TableColumn>
        <TableColumn colKey="userId" sort label="用户ID"></TableColumn>
        <TableColumn colKey="title" sort label="标题"></TableColumn>
        <TableColumn colKey="content" sort label="详情" ellipsis></TableColumn>
        <TableColumn colKey="priority" sort label="优先级"></TableColumn>
        <TableColumn colKey="alert" sort label="提醒">
          <template #default="scope">
            <t-tag :theme="scope.row.alertFlag === 0 ? 'danger' : 'success'" size="small" variant="light">
              {{ scope.row.alertFlag === 0 ? '否' : scope.row.alertTime }}
            </t-tag>
          </template>
        </TableColumn>
        <TableColumn colKey="status" sort label="状态">
          <template #default="scope">
            <t-tag :theme="scope.row.status === 0 ? 'danger' : 'success'" size="small" variant="light">
              {{ scope.row.status === 0 ? '未完成' : '已完成' }}
            </t-tag>
          </template>
        </TableColumn>
        <TableColumn label="操作" width="120px">
          <template #default="scope">
            <t-button variant="outline" theme="default" size="small" @click="openTodoDialog(scope.row, 'edit')"
             >编辑</t-button>
            <t-button variant="outline" size="small" theme="danger" @click="deleteTodo(scope.row)"
             >删除</t-button>
          </template>
        </TableColumn>
      </CustomTable>
      <t-pagination @current-change="handleCurrentChange" :current="params.pageNum" :page-size="params.pageSize"
 :total="total">
      </t-pagination>
    </t-card>

    <t-dialog width="50%" header="待办事项管理" v-model:visible="todolistVisible">
      <t-form :data="todoForm" :rules="rules" ref="todoFormRef" label-width="100px">
        <t-form-item label="待办日期" name="dataDate">
          <t-date-picker size="small" v-model="todoForm.dataDate" format="YYYY-MM-DD" mode="date"
            placeholder="选择日期"></t-date-picker>
        </t-form-item>
        <t-form-item label="待办人员" name="userId" style="margin-bottom:0">
          <t-row>
            <t-col :span="12">
              <t-radio-group size="small" v-model="userType" @change="userTypeChange">
                <t-radio-button :value="true">全体人员</t-radio-button>
                <t-radio-button :value="false">指定人员</t-radio-button>
              </t-radio-group>
            </t-col>
            <t-col :span="12">
              <t-input-adornment style="width: 100%">
                <template #append>
                  <t-button variant="outline" theme="default" :disabled="userType" @click="treeDialogVisiable = !treeDialogVisiable">选择</t-button>
                </template>
                <t-input placeholder="" size="small" v-model="todoForm.userId" :readonly="userType"></t-input>
              </t-input-adornment>
            </t-col>
          </t-row>
        </t-form-item>
        <t-form-item label="待办摘要" name="title">
          <t-input size="small" v-model="todoForm.title"></t-input>
        </t-form-item>
        <t-form-item label="待办详情" name="content">
          <t-textarea size="small" v-model="todoForm.content" />
        </t-form-item>
        <t-form-item label="优先级" name="name">
          <t-checkbox-group size="small" v-model="priorityList">
            <t-checkbox value="重要"></t-checkbox>
            <t-checkbox value="紧急"></t-checkbox>
          </t-checkbox-group>
        </t-form-item>
        <t-form-item label="是否提醒" required>
          <t-row>
            <t-col :span="3">
              <t-switch size="small" v-model="alertFlag" @change="alertSwitch"></t-switch>
            </t-col>
            <t-col :span="9">
              <t-form-item name="time">
                <t-time-picker size="small" placeholder="选择时间" :disabled="!alertFlag"
                  v-model="todoForm.alertTime" style="width: 100%;"></t-time-picker>
              </t-form-item>
            </t-col>
          </t-row>
        </t-form-item>
        <t-form-item label="是否完成" name="status">
          <t-select size="small" v-model="todoForm.status">
            <t-option label="待办" :value="0"></t-option>
            <t-option label="已完成" :value="1"></t-option>
          </t-select>
        </t-form-item>
        <t-form-item>
          <t-button variant="outline" theme="primary" size="small" @click="submit()">{{ todoType === 'add' ? '立即创建' : '提交修改'
          }}</t-button>
          <t-button variant="outline" size="small" @click="closeTodoDialog">取 消</t-button>
        </t-form-item>
      </t-form>
    </t-dialog>

    <EmpTree v-model:treeVisiable="treeDialogVisiable" type="all" :mutiselect="true" @getChecked="getTreeChecked"></EmpTree>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { httpInstance } from '@/utils/request'
import EmpTree from '@/components/EmpTree.vue'
import { useConfirm } from '@/hooks/useConfirm'

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
      MessagePlugin.error(res.msg)
      return
    }
    tableData.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    MessagePlugin.error('获取待办列表失败')
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

const submit = async () => {
  const valid = await todoFormRef.value?.validate()
  if (valid === true) {
    try {
      todoForm.priority = priorityList.value.join(',')
      let res
      if (todoType.value === 'add') {
        res = await httpInstance.post('sys/todo/admin/add', todoForm)
      } else if (todoType.value === 'edit') {
        res = await httpInstance.put('sys/todo', todoForm)
      }
      if (res.code !== 200) {
        MessagePlugin.error(res.msg)
        return
      }
      MessagePlugin.success(res.msg)
      todolistVisible.value = false
      getTodolist()
    } catch (error) {
      MessagePlugin.error('操作失败')
      console.error(error)
    }
  } else {
    MessagePlugin.error('请正确填写表单!')
  }
}

const deleteTodo = async (row) => {
  const { confirm: confirmDialog } = useConfirm()
  const ok = await confirmDialog('你是否确定永久删除此待办，不可恢复', {
    title: '确认信息',
    confirmButtonText: '确认',
    cancelButtonText: '放弃删除'
  })
  if (!ok) return
  try {
    const res = await httpInstance.delete(`sys/todo/${row.id}`)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    MessagePlugin.success(res.msg)
    getTodolist()
  } catch (error) {
    MessagePlugin.error('删除失败')
    console.error(error)
  }
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
</style>
