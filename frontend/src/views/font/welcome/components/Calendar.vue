<template>
  <div style="width: 100%">
    <t-calendar
      v-model="currentDate"
      @cell-click="handleCellClick"
      @controller-change="handleControllerChange"
    >
      <template #cellAppend="cell" >
        <t-tag
            v-if="getDutyForDate(cell.formattedDate).length > 0"
            :theme="getDutyForDate(cell.formattedDate)[0]?.priority || 'default'"
            size="small"
         variant="light">{{ getDutyForDate(cell.formattedDate)[0]?.event }}</t-tag
        >
        <div class="todo-list">
          <div v-for="item in getTodolistForDate(cell.formattedDate)" :key="item.id">
            <t-tag
                :theme="item.priority || 'default'"
                size="small"
                variant="light"
            >
              {{ item.event }}
            </t-tag>
          </div>
        </div>
      </template>

    </t-calendar>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onBeforeUnmount } from 'vue'
import { getCalendarDuty } from '@/api/welcome/index.js'
import { MessagePlugin } from 'tdesign-vue-next'

const emit = defineEmits(['open-todolist'])

const currentDate = ref(new Date())
const selectDay = ref(null)
const todoAlertTimer = ref(null)
const todolistData = ref([])
const dutyData = ref([])
const taskData = ref([])

const formatDate = (date) => {
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  return `${year}-${month}-${day}`
}

const getMonthStr = (date) => {
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  return `${year}-${month}`
}

const getDutyForDate = (day) => {
  return dutyData.value.filter((item) => item.date === day)
}

const getTaskForDate = (day) => {
  return taskData.value.filter((item) => item.date === day)
}

const getTodolistForDate = (day) => {
  return todolistData.value.filter((item) => item.date === day)
}

const getToday = computed(() => {
  const now = new Date()
  return formatDate(now)
})

// TDesign @cell-click handler — replaces the old @click div + setupCalendarButtons hack
const handleCellClick = ({ cell }) => {
  selectDay.value = cell.formattedDate
  const hasPermission = checkPermission('main:todolist:manager')
  if (hasPermission) {
    emit('open-todolist', cell.formattedDate)
  }
}

// TDesign @controller-change handler — replaces DOM query button listeners
const handleControllerChange = ({ filterDate }) => {
  getDuty(getMonthStr(filterDate))
}

onMounted(() => {
  selectDay.value = getToday.value
  getDuty(getMonthStr(new Date()))
})

onBeforeUnmount(() => {
  if (todoAlertTimer.value) {
    clearInterval(todoAlertTimer.value)
  }
})

const getDuty = (date) => {
  getCalendarDuty(date)
    .then((res) => {
      if (res.code !== 200) {
        MessagePlugin.error(res.msg)
        return
      }
      dutyData.value = res.data?.duty || res.duty || []
      taskData.value = res.data?.task || res.task || []
      todolistData.value = res.data?.todolist || res.todolist || []
    })
    .catch((err) => {
      console.error('获取日历数据失败', err)
    })
}

// 前端权限体系尚未建立，暂时保持放行
const checkPermission = () => true
</script>

<style lang="less" scoped>
.calendar-cell {
  width: 100%;
  height: 100%;
}
//.todo-list {
//  height: 86%;
//  overflow-y: auto;
//  overflow-x: hidden;
//}

.todo-font {
  font-size: 10px;
background-color: var(--td-text-color-placeholder);
  color: #fff;
  margin-top: 1px;
  width: 100%;
  word-break: keep-all;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.calendar-todoLabel {
  color: #dd6161;
  font-size: 10px;
  margin-top: 2px;
}
:deep(.t-calendar__cell--selected) {
background-color: #fbb7b7 !important;
}
:deep(.t-calendar__table-body-cell) {
  height: 100px;
  padding-right: 0;
}
.daily-task-success {
-image: url('../../../assets/img/edu/dailyTask-do.png');
-repeat: no-repeat;
-size: 45px 45px;
-position: bottom right;
}
.daily-task-danger {
-image: url('../../../assets/img/edu/dailyTask-undo.png');
-repeat: no-repeat;
-size: 45px 45px;
-position: bottom right;
}
.daily-task-warning {
-image: url('../../../assets/img/edu/dailyTask-did.png');
-repeat: no-repeat;
-size: 45px 45px;
-position: bottom right;
}
</style>
