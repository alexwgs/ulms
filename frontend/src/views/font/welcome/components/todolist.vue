<template>
  <el-dialog :title="'[' + selectDay + ']日程管理'" v-model="calendarDialogVisible" width="80%"
    :close-on-click-modal="false" :close-on-press-escape="false" :show-close="false">
    <el-tabs v-model="activeName" @tab-click="refreshData" style="margin-top: -30px">
      <el-tab-pane label="当日待办" name="todo">
        <el-button type="primary" size="small" :icon="Plus" @click="openTodoDialog('add')">添加待办</el-button>
        <el-table :data="todoList.filter((item) => item.status == 0)" height="250px" style="width: 100%">
          <el-table-column prop="priority" sortable label="优先级" width="140">
            <template #default="scope">
              <el-tag size="small" :type="scope.row.priority != null &&
                  scope.row.priority.indexOf('紧急') > -1
                  ? 'danger'
                  : 'info'
                " effect="dark">
                {{
                  scope.row.priority != null &&
                    scope.row.priority.indexOf('紧急') > -1
                    ? '紧急'
                    : '不紧急'
                }}
              </el-tag>
              <el-tag size="small" style="margin-left: 5px" :type="scope.row.priority != null &&
                  scope.row.priority.indexOf('重要') > -1
                  ? 'warning'
                  : 'info'
                " effect="dark">
                {{
                  scope.row.priority != null &&
                    scope.row.priority.indexOf('重要') > -1
                    ? '重要'
                    : '不重要'
                }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="title" label="摘要（鼠标移入可查看详细）">
            <template #default="scope">
              <el-popover placement="top-start" :title="scope.row.title" width="300" trigger="hover"
                :content="scope.row.content">
                <template #reference>
                  <el-link underline="never">{{ scope.row.title }}</el-link>
                </template>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column prop="alertFlag" sortable label="提醒" width="120">
            <template #default="scope">
              {{ scope.row.alertFlag === 1 ? scope.row.alertTime : '否' }}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="140">
            <template #default="scope">
              <el-button size="small" type="primary" :disabled="scope.row.userId === 'admin'"
                @click="updateTodo(scope.row)" :icon="Edit" circle></el-button>
              <el-button size="small" type="danger" :disabled="scope.row.userId === 'admin'"
                @click="deleteTodo(scope.row)" :icon="Delete" circle></el-button>
              <el-button size="small" type="success" :disabled="scope.row.userId === 'admin'"
                @click="completeTodo(scope.row)" :icon="Check" circle></el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-table :data="todoList.filter((item) => item.status == 1)" height="150px" style="width: 100%">
          <el-table-column prop="priority" sortable label="优先级" width="140">
            <template #default="scope">
              <el-tag size="small" :type="scope.row.priority != null &&
                  scope.row.priority.indexOf('紧急') > -1
                  ? 'danger'
                  : 'info'
                " effect="dark">
                {{
                  scope.row.priority != null &&
                    scope.row.priority.indexOf('紧急') > -1
                    ? '紧急'
                    : '不紧急'
                }}
              </el-tag>
              <el-tag size="small" style="margin-left: 5px" :type="scope.row.priority != null &&
                  scope.row.priority.indexOf('重要') > -1
                  ? 'warning'
                  : 'info'
                " effect="dark">
                {{
                  scope.row.priority != null &&
                    scope.row.priority.indexOf('重要') > -1
                    ? '重要'
                    : '不重要'
                }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="title" label="摘要">
            <template #default="scope">
              <el-popover placement="top-start" :title="scope.row.title" width="300" trigger="hover"
                :content="scope.row.content">
                <template #reference>
                  <el-link underline="never">{{ scope.row.title }}</el-link>
                </template>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column prop="alertFlag" sortable label="提醒" width="120">
            <template #default="scope">
              {{ scope.row.alertFlag === 1 ? scope.row.alertTime : '否' }}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="140">
            <template #default="scope">
              <el-button size="small" type="primary" :disabled="scope.row.userId === 'admin'"
                @click="updateTodo(scope.row)" :icon="Edit" circle></el-button>
              <el-button size="small" type="danger" :disabled="scope.row.userId === 'admin'"
                @click="deleteTodo(scope.row)" :icon="Delete" circle></el-button>
              <el-button size="small" type="success" :disabled="scope.row.userId === 'admin'"
                @click="completeTodo(scope.row)" :icon="RefreshRight" circle></el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane v-if="hasPermission('main:directorStatus:manager')" label="日程记录" name="schedule" hight="600px">
        <el-row>
          <el-col :span="8">在线时长:{{ durationFormatter(totalTime) }}</el-col>
          <el-col :span="8">我在时长:{{ durationFormatter(onseatTime) }}</el-col>
          <el-col :span="8">离开时长:{{ durationFormatter(offseatTime) }}</el-col>
        </el-row>
        <el-table :data="statusJourList.filter((item) => item.statusId !== 0)" height="400px" style="width: 100%">
          <el-table-column prop="begTime" sortable label="开始时间" width="120"></el-table-column>
          <el-table-column prop="endTime" sortable label="结束时间" width="120"></el-table-column>
          <el-table-column prop="statusName" label="状态">
            <template #default="slot">
              {{
                slot.row.statusName +
                (slot.row.memo == null ? '' : '[' + slot.row.memo + ']')
              }}
            </template>
          </el-table-column>
          <el-table-column prop="duration" sortable label="持续时间" width="100">
            <template #default="slot">
              {{ durationFormatter(slot.row.duration) }}
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="每日一学" name="task" v-if="currentDate >= selectDay">
        <DailyTask ref="dailyTaskRef"></DailyTask>
      </el-tab-pane>
    </el-tabs>
    <template #footer>
      <span class="dialog-footer">
        <el-button size="small" @click="onclose">取 消</el-button>
      </span>
    </template>

    <el-dialog width="50%" title="待办事项" v-model="innerVisible" append-to-body>
      <el-form :model="todoForm" :rules="rules" ref="todoFormRef" label-width="100px">
        <el-form-item label="待办摘要" prop="title">
          <el-input size="small" v-model="todoForm.title"></el-input>
        </el-form-item>
        <el-form-item label="待办详情" prop="content">
          <el-input size="small" type="textarea" v-model="todoForm.content"></el-input>
        </el-form-item>
        <el-form-item label="优先级" prop="name">
          <el-checkbox-group size="small" v-model="priorityList">
            <el-checkbox label="重要"></el-checkbox>
            <el-checkbox label="紧急"></el-checkbox>
          </el-checkbox-group>
          <el-tag v-for="(tag, index) in priorityList" :key="index" :type="tag === '重要' ? 'warning' : 'danger'">
            {{ tag }}
          </el-tag>
        </el-form-item>
        <el-form-item label="是否提醒" required>
          <el-col :span="5">
            <el-switch size="small" v-model="alertFlag" @change="alertSwitch"></el-switch>
          </el-col>
          <el-col :span="18">
            <el-form-item prop="time">
              <el-time-picker size="small" placeholder="选择时间" :disabled="!alertFlag" value-format="HH:mm:ss"
                v-model="todoForm.alertTime" style="width: 100%"></el-time-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="small" @click="submitTodoForm()">{{
            todoType === 'add' ? '立即创建' : '提交修改'
            }}</el-button>
          <el-button size="small" @click="innerVisible = false">取 消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </el-dialog>
</template>

<script setup>
import { ref, reactive, computed, onMounted, nextTick } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  Plus,
  Edit,
  Delete,
  Check,
  RefreshRight
} from '@element-plus/icons-vue'
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
        ElMessage.error(res.msg)
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
          activeName.value = 'task'
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
      ElMessage.error(res.msg)
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

const refreshData = (e) => {
  if (e.name === 'schedule') {
    getStatusJourData()
  } else if (e.name === 'task') {
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
  todoFormRef.value?.validate(async (valid) => {
    if (valid) {
      todoForm.dataDate = selectDay.value
      todoForm.priority = priorityList.value.join(',')

      if (todoForm.alertFlag === 1 && todoForm.alertTime === '') {
        ElMessage.error('请填写提醒时间！')
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
          ElMessage.error(res.msg)
          return
        }
        ElMessage.success(res.msg)
      } catch (error) {
        ElMessage.error('操作失败')
        return
      }

      calendarUpdateFlag.value = 1
      innerVisible.value = false
      getTodoListData()
    }
  })
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
  ElMessageBox.confirm('你是否确定永久删除此待办，不可恢复', '确认信息', {
    distinguishCancelAndClose: true,
    confirmButtonText: '确认',
    cancelButtonText: '放弃删除'
  })
    .then(async () => {
      try {
        const res = await apiDeleteTodo(row.id)
        if (res.code !== 200) {
          ElMessage.error(res.msg)
          return
        }
        ElMessage.success(res.msg)
        getTodoListData()
        calendarUpdateFlag.value = 1
      } catch (error) {
        ElMessage.error('删除失败')
      }
    })
    .catch(() => { })
}

const completeTodo = async (row) => {
  const data = { id: row.id, status: row.status === 1 ? 0 : 1 }

  try {
    const res = await apiUpdateTodo(data)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    ElMessage.success(res.msg)
    getTodoListData()
    calendarUpdateFlag.value = 1
  } catch (error) {
    ElMessage.error('操作失败')
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
