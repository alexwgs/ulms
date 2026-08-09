<template>
  <t-dialog :header="'[' + selectDay + ']日程管理'" v-model:visible="calendarDialogVisible" width="80%"
    :close-on-overlay-click="false" :close-on-esc-keydown="false" :close-btn="false">
    <t-tabs v-model="activeName" @change="refreshData" style="margin-top: -30px">
      <t-tab-panel label="当日待办" value="todo">
        <t-button theme="primary" size="small" @click="openTodoDialog('add')"><template #icon><DynamicIcon name="add" /></template>添加待办</t-button>
        <CustomTable rowKey="id" :data="todoList.filter((item) => item.status == 0)" height="250px" style="width: 100%">
          <TableColumn colKey="priority" sortable label="优先级" width="140">
            <template #default="scope">
              <t-tag size="small" :theme="scope.row.priority != null &&
                  scope.row.priority.indexOf('紧急') > -1
                  ? 'danger'
                  : 'primary'
                " variant="light">
                {{
                  scope.row.priority != null &&
                    scope.row.priority.indexOf('紧急') > -1
                    ? '紧急'
                    : '不紧急'
                }}
              </t-tag>
              <t-tag size="small" style="margin-left: 5px" :theme="scope.row.priority != null &&
                  scope.row.priority.indexOf('重要') > -1
                  ? 'warning'
                  : 'primary'
                " variant="light">
                {{
                  scope.row.priority != null &&
                    scope.row.priority.indexOf('重要') > -1
                    ? '重要'
                    : '不重要'
                }}
              </t-tag>
            </template>
          </TableColumn>
          <TableColumn colKey="title" label="摘要（鼠标移入可查看详细）">
            <template #default="scope">
              <t-popup placement="top-start" :title="scope.row.title" width="300" trigger="hover"
                :content="scope.row.content">
                <t-button variant="text" ghost>{{ scope.row.title }}</t-button>
              </t-popup>
            </template>
          </TableColumn>
          <TableColumn colKey="alertFlag" sortable label="提醒" width="120">
            <template #default="scope">
              {{ scope.row.alertFlag === 1 ? scope.row.alertTime : '否' }}
            </template>
          </TableColumn>
          <TableColumn label="操作" width="140">
            <template #default="scope">
              <t-button variant="outline" size="small" theme="default" :disabled="scope.row.userId === 'admin'"
                @click="updateTodo(scope.row)">编辑</t-button>
              <t-button variant="outline" size="small" theme="danger" :disabled="scope.row.userId === 'admin'"
                @click="deleteTodo(scope.row)">删除</t-button>
              <t-button variant="outline" size="small" theme="success" :disabled="scope.row.userId === 'admin'"
                @click="completeTodo(scope.row)">完成</t-button>
            </template>
          </TableColumn>
        </CustomTable>
        <CustomTable rowKey="id" :data="todoList.filter((item) => item.status == 1)" height="150px" style="width: 100%">
          <TableColumn colKey="priority" sortable label="优先级" width="140">
            <template #default="scope">
              <t-tag size="small" :theme="scope.row.priority != null &&
                  scope.row.priority.indexOf('紧急') > -1
                  ? 'danger'
                  : 'default'
                " variant="light">
                {{
                  scope.row.priority != null &&
                    scope.row.priority.indexOf('紧急') > -1
                    ? '紧急'
                    : '不紧急'
                }}
              </t-tag>
              <t-tag size="small" style="margin-left: 5px" :theme="scope.row.priority != null &&
                  scope.row.priority.indexOf('重要') > -1
                  ? 'warning'
                  : 'default'
                " variant="light">
                {{
                  scope.row.priority != null &&
                    scope.row.priority.indexOf('重要') > -1
                    ? '重要'
                    : '不重要'
                }}
              </t-tag>
            </template>
          </TableColumn>
          <TableColumn colKey="title" label="摘要">
            <template #default="scope">
              <t-popup placement="top-start" :title="scope.row.title" width="300" trigger="hover"
                :content="scope.row.content">
                <t-link :underline="false">{{ scope.row.title }}</t-link>
              </t-popup>
            </template>
          </TableColumn>
          <TableColumn colKey="alertFlag" sortable label="提醒" width="120">
            <template #default="scope">
              {{ scope.row.alertFlag === 1 ? scope.row.alertTime : '否' }}
            </template>
          </TableColumn>
          <TableColumn label="操作" width="140">
            <template #default="scope">
              <t-button variant="outline" size="small" theme="default" :disabled="scope.row.userId === 'admin'"
                @click="updateTodo(scope.row)">编辑</t-button>
              <t-button variant="outline" size="small" theme="danger" :disabled="scope.row.userId === 'admin'"
                @click="deleteTodo(scope.row)">删除</t-button>
              <t-button variant="outline" size="small" theme="default" :disabled="scope.row.userId === 'admin'"
                @click="completeTodo(scope.row)">刷新</t-button>
            </template>
          </TableColumn>
        </CustomTable>
      </t-tab-panel>
      <t-tab-panel v-if="hasPermission('main:directorStatus:manager')" label="日程记录" value="schedule">
        <t-row>
          <t-col :span="4">在线时长:{{ durationFormatter(totalTime) }}</t-col>
          <t-col :span="4">我在时长:{{ durationFormatter(onseatTime) }}</t-col>
          <t-col :span="4">离开时长:{{ durationFormatter(offseatTime) }}</t-col>
        </t-row>
        <CustomTable rowKey="id" :data="statusJourList.filter((item) => item.statusId !== 0)" height="400px" style="width: 100%">
          <TableColumn colKey="begTime" sortable label="开始时间" width="120"></TableColumn>
          <TableColumn colKey="endTime" sortable label="结束时间" width="120"></TableColumn>
          <TableColumn colKey="statusName" label="状态">
            <template #default="slot">
              {{
                slot.row.statusName +
                (slot.row.memo == null ? '' : '[' + slot.row.memo + ']')
              }}
            </template>
          </TableColumn>
          <TableColumn colKey="duration" sortable label="持续时间" width="100">
            <template #default="slot">
              {{ durationFormatter(slot.row.duration) }}
            </template>
          </TableColumn>
        </CustomTable>
      </t-tab-panel>
      <t-tab-panel label="每日一学" value="task" v-show="currentDate >= selectDay">
        <DailyTask ref="dailyTaskRef"></DailyTask>
      </t-tab-panel>
    </t-tabs>
    <template #footer>
      <span class="dialog-footer">
        <t-button size="small" @click="onclose">取 消</t-button>
      </span>
    </template>

    <t-dialog width="50%" header="待办事项" v-model:visible="innerVisible" attach="body">
      <t-form :data="todoForm" :rules="rules" ref="todoFormRef" label-width="100px">
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
          <t-tag v-for="(tag, index) in priorityList" :key="index" :theme="tag === '重要' ? 'warning' : 'danger'" variant="light">
            {{ tag }}
          </t-tag>
        </t-form-item>
        <t-form-item label="是否提醒" required>
          <t-row>
            <t-col :span="3">
              <t-switch size="small" v-model="alertFlag" @change="alertSwitch"></t-switch>
            </t-col>
            <t-col :span="9">
              <t-form-item name="time">
                <t-time-picker size="small" placeholder="选择时间" :disabled="!alertFlag"
                  v-model="todoForm.alertTime" style="width: 100%"></t-time-picker>
              </t-form-item>
            </t-col>
          </t-row>
        </t-form-item>
        <t-form-item>
          <t-button theme="primary" size="small" @click="submitTodoForm()">{{
            todoType === 'add' ? '立即创建' : '提交修改'
            }}</t-button>
          <t-button size="small" @click="innerVisible = false">取 消</t-button>
        </t-form-item>
      </t-form>
    </t-dialog>
  </t-dialog>
</template>

<script setup>
import { ref, reactive, computed, onMounted, nextTick } from 'vue'
import { MessagePlugin, DialogPlugin } from 'tdesign-vue-next'
import { AddIcon, EditIcon, DeleteIcon, CheckIcon, RefreshIcon } from 'tdesign-icons-vue-next'
import {
  getTodoList as apiGetTodoList,
  addTodo as apiAddTodo,
  updateTodo as apiUpdateTodo,
  deleteTodo as apiDeleteTodo,
  getStatusJourList
} from '@/api/welcome/index.js'
import DailyTask from './DailyTask.vue'

const selectDay = ref('')
const calendarDialogVisible = ref(false)
const calendarUpdateFlag = ref(0)
const activeName = ref('todo')
const todoList = ref([])
const taskData = ref([])
const todoType = ref('')
const onseatTime = ref(0)
const offseatTime = ref(0)
const totalTime = ref(0)
const innerVisible = ref(false)
const statusJourList = ref([])
const alertFlag = ref(false)
const todoForm = reactive({
  dataDate: '',
  title: '',
  content: '',
  priority: '',
  alertFlag: 0,
  alertTime: '',
  status: 0
})
const priorityList = ref([])
const todoFormRef = ref(null)
const dailyTaskRef = ref(null)

const currentDate = computed(() => {
  const now = new Date()
  const year = now.getFullYear()
  const month = String(now.getMonth() + 1).padStart(2, '0')
  const day = String(now.getDate()).padStart(2, '0')
  return `${year}-${month}-${day}`
})

const rules = {
  title: [
    { required: true, message: '请输入待办摘要,尽可能简洁', trigger: 'blur' },
    { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
  ]
}

onMounted(() => {
  getTodoListData()
})

const getTodoListData = () => {
  const today = currentDate.value
  if (!selectDay.value) selectDay.value = today

  apiGetTodoList(selectDay.value)
    .then((res) => {
      if (res.code !== 200) {
        MessagePlugin.error(res.msg)
        return
      }
      todoList.value = res.data || []

      if (selectDay.value === today) {
        const alertLists = todoList.value.filter(
          (item) => item.alertFlag === 1 && item.status === 0
        )
        alertLists.sort((a, b) => a.alertTime.localeCompare(b.alertTime))
        commitTodoAlert(alertLists)
      }
    })
    .then(() => {
      try {
        const parent = window.__POWERED_BY_QIANKUN__ ? window.$parent : null
        if (
          parent &&
          parent.taskData &&
          parent.taskData.filter((e) => e.event === '').length > 0
        ) {
          calendarDialogVisible.value = true
          if (currentDate.value >= selectDay.value) {
            activeName.value = 'task'
          }
          nextTick(() => {
            if (dailyTaskRef.value) {
              dailyTaskRef.value.init()
            }
          })
        }
      } catch (error) {
        console.error('检查任务数据失败', error)
      }
    })
    .catch((err) => {
      console.error('获取待办列表失败', err)
    })
}

const commitTodoAlert = (alertLists) => {
  try {
    const store = window.__POWERED_BY_QIANKUN__ ? window.$store : null
    if (store) {
      store.commit('setTodoAlert', alertLists)
    }
  } catch (error) {
    console.error('设置待办提醒失败', error)
  }
}

const getStatusJourData = async () => {
  totalTime.value = 0
  onseatTime.value = 0
  offseatTime.value = 0

  try {
    const res = await getStatusJourList(selectDay.value)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    statusJourList.value = res.data || []
    statusJourList.value.forEach((item) => {
      totalTime.value += item.duration
    })
    statusJourList.value.forEach((item) => {
      if (item.statusName.indexOf('我在-') > -1)
        onseatTime.value += item.duration
    })
    statusJourList.value.forEach((item) => {
      if (item.statusName.indexOf('离开-') > -1)
        offseatTime.value += item.duration
    })
  } catch (error) {
    console.error('获取状态记录失败', error)
  }
}

const refreshData = (value) => {
  if (value === 'schedule') {
    getStatusJourData()
  } else if (value === 'task') {
    if (selectDay.value === currentDate.value) {
      if (dailyTaskRef.value) dailyTaskRef.value.init()
    } else {
      if (dailyTaskRef.value) dailyTaskRef.value.init(selectDay.value)
    }
  }
}

const onclose = () => {
  calendarDialogVisible.value = false
  activeName.value = 'todo'
}

const alertSwitch = (e) => {
  if (e === true) {
    todoForm.alertFlag = 1
  } else {
    todoForm.alertTime = ''
    todoForm.alertFlag = 0
  }
}

const submitTodoForm = async () => {
  const valid = await todoFormRef.value?.validate()
  if (valid === true) {
    todoForm.dataDate = selectDay.value
    todoForm.priority = priorityList.value.join(',')

    if (todoForm.alertFlag === 1 && todoForm.alertTime === '') {
      MessagePlugin.error('请填写提醒时间！')
      return
    }

    try {
      let res
      if (todoType.value === 'add') {
        res = await apiAddTodo(todoForm)
      } else {
        res = await apiUpdateTodo(todoForm)
      }

      if (res.code !== 200) {
        MessagePlugin.error(res.msg)
        return
      }
      MessagePlugin.success(res.msg)
    } catch (error) {
      MessagePlugin.error('操作失败')
      return
    }

    calendarUpdateFlag.value = 1
    innerVisible.value = false
    getTodoListData()
  }
}

const openTodoDialog = (type) => {
  if (type === 'add') {
    todoType.value = 'add'
    Object.assign(todoForm, {
      dataDate: '',
      title: '',
      content: '',
      priority: '',
      alertFlag: 0,
      alertTime: '',
      status: 0
    })
    priorityList.value = []
    alertFlag.value = false
  } else if (type === 'edit') {
    todoType.value = 'edit'
  }
  innerVisible.value = true
}

const updateTodo = (row) => {
  Object.assign(todoForm, row)
  if (row.priority != null) priorityList.value = row.priority.split(',')
  alertFlag.value = todoForm.alertFlag === 1
  openTodoDialog('edit')
}

const deleteTodo = (row) => {
  DialogPlugin.confirm('你是否确定永久删除此待办，不可恢复', '确认信息', {
    distinguishCancelAndClose: true,
    confirmButtonText: '确认',
    cancelButtonText: '放弃删除'
  })
    .then(async () => {
      try {
        const res = await apiDeleteTodo(row.id)
        if (res.code !== 200) {
          MessagePlugin.error(res.msg)
          return
        }
        MessagePlugin.success(res.msg)
        getTodoListData()
        calendarUpdateFlag.value = 1
      } catch (error) {
        MessagePlugin.error('删除失败')
      }
    })
    .catch(() => { })
}

const completeTodo = async (row) => {
  const data = { id: row.id, status: row.status === 1 ? 0 : 1 }

  try {
    const res = await apiUpdateTodo(data)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    MessagePlugin.success(res.msg)
    getTodoListData()
    calendarUpdateFlag.value = 1
  } catch (error) {
    MessagePlugin.error('操作失败')
  }
}

const durationFormatter = (second) => {
  return (
    Math.floor(second / 3600) +
    ':' +
    Math.floor((second % 3600) / 60) +
    ':' +
    Math.floor(second % 60)
  )
}

const hasPermission = (permission) => {
  try {
    const global = window.__POWERED_BY_QIANKUN__ ? window.$global : null
    if (global && global.hasPermission) {
      return global.hasPermission(permission)
    }
    return true
  } catch (error) {
    return true
  }
}

defineExpose({
  calendarDialogVisible,
  selectDay,
  getTodoList: getTodoListData
})
</script>
