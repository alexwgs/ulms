<template>
  <div>
    <el-calendar v-model="calendarTitle">
      <template #date-cell="{ data }">
        <div
          :class="
            getTaskForDate(data.day).length > 0
              ? 'calendar-cell daily-task-' +
                getTaskForDate(data.day)[0].priority
              : 'calendar-cell'
          "
          @click="handleAddEvent(data.day)"
        >
          <div class="calendar-day">
            {{ data.day.split('-').slice(2).join('-') }}
            &emsp;
            <el-tag
              v-if="getDutyForDate(data.day).length > 0"
              :type="getDutyForDate(data.day)[0]?.priority || 'info'"
              size="small"
              >{{ getDutyForDate(data.day)[0]?.event }}</el-tag
            >
          </div>
          <div class="todo-list">
            <div v-for="item in getTodolistForDate(data.day)" :key="item.id">
              <el-tag
                :type="item.priority || 'info'"
                size="small"
                effect="dark"
              >
                {{ item.event }}
              </el-tag>
            </div>
          </div>
        </div>
      </template>
    </el-calendar>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onBeforeUnmount } from 'vue'
import { getCalendarDuty } from '@/api/welcome/index.js'
import { ElMessage } from 'element-plus'

const emit = defineEmits(['open-todolist'])

const calendarTitle = ref(new Date())
const today = ref('')
const selectDay = ref(null)
const todoAlertTimer = ref(null)
const todolistData = ref([])
const dutyData = ref([])
const taskData = ref([])

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
  const year = now.getFullYear()
  const month = String(now.getMonth() + 1).padStart(2, '0')
  const day = String(now.getDate()).padStart(2, '0')
  return `${year}-${month}-${day}`
})

onMounted(() => {
  selectDay.value = getToday.value
  today.value = getToday.value
  getDuty(selectDay.value.substring(0, 7))
  setupCalendarButtons()
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
        ElMessage.error(res.msg)
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

const handleAddEvent = (day) => {
  const hasPermission = checkPermission('main:todolist:manager')
  if (hasPermission) {
    emit('open-todolist', day)
  }
}

const checkPermission = (permission) => {
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

const setupCalendarButtons = () => {
  const prevBtn = document.querySelector(
    '.el-calendar__button-group .el-button-group>button:nth-child(1)'
  )
  if (prevBtn) {
    prevBtn.addEventListener('click', () => {
      let month = ''
      if (calendarTitle.value.getMonth() < 9) {
        month = '0' + (calendarTitle.value.getMonth() + 1)
      } else {
        month = calendarTitle.value.getMonth() + 1 + ''
      }
      const curMonth = calendarTitle.value.getFullYear() + '-' + month
      getDuty(curMonth)
    })
  }

  const curentBtn = document.querySelector(
    '.el-calendar__button-group .el-button-group>button:nth-child(2)'
  )
  if (curentBtn) {
    curentBtn.addEventListener('click', () => {
      getDuty(selectDay.value.substring(0, 7))
    })
  }

  const nextBtn = document.querySelector(
    '.el-calendar__button-group .el-button-group>button:last-child'
  )
  if (nextBtn) {
    nextBtn.addEventListener('click', () => {
      let month = ''
      if (calendarTitle.value.getMonth() < 9) {
        month = '0' + (calendarTitle.value.getMonth() + 1)
      } else {
        month = calendarTitle.value.getMonth() + 1 + ''
      }
      const curMonth = calendarTitle.value.getFullYear() + '-' + month
      getDuty(curMonth)
    })
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

const toTimestamp = (dateTime) => {
  const date = new Date()
  const times = dateTime.split(':')
  return Math.round(
    new Date(
      date.getFullYear(),
      date.getMonth(),
      date.getDate(),
      times[0],
      times[1],
      times[2]
    ).getTime() / 1000
  )
}
</script>

<style lang="less" scoped>
.calendar-cell {
  width: 100%;
  height: 100%;
}
.todo-list {
  height: 86%;
  overflow-y: auto;
  overflow-x: hidden;
}
.past-cell {
  background-color: #e0e0e0;
}
.calendar-day {
  text-align: center;
  color: #202535;
  width: 100%;
  text-align: left;
  line-height: 15px;
  font-size: 12px;
}
.todo-font {
  font-size: 10px;
  background-color: #909399;
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
.el-calendar-table td.is-selected {
  background-color: #fbb7b7 !important;
}
:deep(.el-calendar-table .el-calendar-day) {
  height: 100px;
  padding-right: 0;
}
.daily-task-success {
  background-image: url('../../../assets/img/edu/dailyTask-do.png');
  background-repeat: no-repeat;
  background-size: 45px 45px;
  background-position: bottom right;
}
.daily-task-danger {
  background-image: url('../../../assets/img/edu/dailyTask-undo.png');
  background-repeat: no-repeat;
  background-size: 45px 45px;
  background-position: bottom right;
}
.daily-task-warning {
  background-image: url('../../../assets/img/edu/dailyTask-did.png');
  background-repeat: no-repeat;
  background-size: 45px 45px;
  background-position: bottom right;
}
</style>
