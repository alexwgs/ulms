// stores/oht.js
import { defineStore } from 'pinia'
import { NotifyPlugin } from 'tdesign-vue-next'
import dayjs from 'dayjs'
import { useWsStore } from '@/stores/modules/ws' // 假设你有全局store

export const useOhtStore = defineStore('oht', {
  state: () => ({
    permissions: [],
    ohtUserList: [],
    station: { pcIp: '', extnIp: '', extnNum: '', floorNum: '' },
    waittingCase: [],
    refuseCaseIds: [],
    currentCase: null,
    ohtCountingTimer: null,
    ohtCurrentCaseCountingTimer: null,
    ohtWaitSeconds: 0,
    ohtRole: {},
    orderStatus: 0, // 0-空闲 1-求助等待中 2-处理中 3-待结案
    userStatus: null,
    taskCaseNum: {},
    ohtDirStatus: 0,
    identity: '',
    userInfo: [],
    ohtNewMessageSize: 0,
    ohtStatusInfo: 'error',
    noticeMessage: [],
    todoAlertTimer: null,
    todoList: [],
    ifmsUserStatus: [],
    noticeCaseId: null,
    roleType: null
  }),

  getters: {
    filteredWaitingCase: (state) => {
      return state.waittingCase.filter(
        (caseItem) => !state.refuseCaseIds.includes(caseItem.caseId)
      )
    }
  },

  actions: {
    addNoticeMessage(noticeMessage) {
      noticeMessage.ifRead = false
      noticeMessage.id =
        this.noticeMessage.length + Math.floor(Math.random() * 10000) + 1
      const size = this.noticeMessage.unshift(noticeMessage)
      if (size >= 100) this.noticeMessage.splice(99)
      noticeMessage.dataTime = dayjs().format('YYYY-MM-DD HH:mm:ss')
      window.localStorage.setItem('notice', JSON.stringify(this.noticeMessage))
    },

    readNoticeMessage(id) {
      this.noticeMessage.find((item) => {
        if (item.id === id && item.ifRead === false) {
          item.ifRead = true
          window.localStorage.setItem(
            'notice',
            JSON.stringify(this.noticeMessage)
          )
        }
      })
    },

    setNoticeMessage(noticeMessages) {
      this.noticeMessage = noticeMessages
    },

    setPermissions(permissions) {
      this.permissions = permissions
    },

    setUserList(userList) {
      this.ohtUserList = userList
    },

    addUserList(user) {
      this.ohtUserList.push(user)
    },

    deleteUserList(userId) {
      const index = this.ohtUserList.findIndex(
        (item) => userId === item.user.ploNum
      )
      if (index > -1) this.ohtUserList.splice(index, 1)
    },

    setWaittingCase(cases) {
      const wsStore = useWsStore()
      const user = JSON.parse(window.localStorage.getItem('user') || '{}')

      if (!cases?.length) {
        wsStore.setVoiceNotice('close')
        clearTimeout(this.ohtCurrentCaseCountingTimer)
        this.waittingCase = []
      } else {
        // 过滤拒绝的案件
        this.waittingCase = cases
          .filter((item) => !this.refuseCaseIds.includes(item.caseId))
          .map((item) => {
            if (item.buildGroup === user.deptGroup) {
              // 不需要额外时间
            } else if (item.buildDept === user.deptNum) {
              item.buildTime += 15
            } else {
              item.buildTime += 30
            }
            return item
          })
          .sort((a, b) => a.buildTime - b.buildTime)

        this.ohtCasesToCurrentCase()
      }
    },

    addRefuseCaseId(caseId) {
      this.refuseCaseIds.push(caseId)
    },

    setCurrentCase(currentCase) {
      this.currentCase = currentCase
    },

    setStation(station) {
      this.station = station
    },

    setOrderStatus(status) {
      this.orderStatus = status
    },

    setOhtRole(role) {
      this.ohtRole = role
    },

    setUserStatus(userStatus) {
      this.userStatus = userStatus
    },

    setTaskCaseNum(taskCaseNum) {
      this.taskCaseNum = taskCaseNum
    },

    addTaskCaseNum(taskManager) {
      if (!this.taskCaseNum[taskManager.addTaskType]) {
        this.taskCaseNum[taskManager.addTaskType] = 0
      }

      this.taskCaseNum[taskManager.addTaskType] +=
        taskManager.direction === '+' ? 1 : -1
    },

    setOhtDirStatus(ohtDirStatus) {
      this.ohtDirStatus = ohtDirStatus
      clearTimeout(this.ohtCurrentCaseCountingTimer)
      this.noticeCaseId = null
    },

    setIdentity(identity) {
      this.identity = identity
    },

    setOhtNewMessageSize(type) {
      if (type === 'add') this.ohtNewMessageSize++
      else if (type === 'reset') this.ohtNewMessageSize = 0
    },

    setOhtStatusInfo(statusInfo) {
      const wsStore = useWsStore()
      this.ohtStatusInfo = statusInfo
      if (statusInfo !== 'task') wsStore.setVoiceNotice('close')
    },

    setTodoAlert(alertLists) {
      clearInterval(this.todoAlertTimer)

      if (!alertLists.length) return

      const now = dayjs()
      let currentTime = now.unix()

      this.todoAlertTimer = setInterval(() => {
        for (let i = 0; i < alertLists.length; i++) {
          const item = alertLists[i]
          const [hours, minutes, seconds] = item.alertTime
            .split(':')
            .map(Number)

          const alertTime = dayjs()
            .hour(hours)
            .minute(minutes)
            .second(seconds)
            .unix()

          const countDown = alertTime - currentTime

          if (countDown === 0) {
            const wsStore = useWsStore()
            wsStore.setDesktopNotification(
              `待办提醒：${item.title}`,
              item.content,
              'todo'
            )

            NotifyPlugin.info({
              title: `待办提醒：${item.title}`,
              content: item.content,
              duration: 0,
              placement: 'bottom-right'
            })

            alertLists.splice(i, 1)
            i--
          } else if (countDown < 0) {
            alertLists.splice(i, 1)
            i--
          }
        }

        currentTime++
        if (alertLists.length === 0) clearInterval(this.todoAlertTimer)
      }, 1000)
    },

    ohtTimeCountting(unixTime) {
      clearInterval(this.ohtCountingTimer)
      this.ohtWaitSeconds = 0
      const currentTime = dayjs().unix()
      const startTime = unixTime ?? currentTime
      this.ohtWaitSeconds = currentTime - startTime

      this.ohtCountingTimer = setInterval(() => {
        this.ohtWaitSeconds++
      }, 1000)
    },

    ohtCasesToCurrentCase() {
      if (this.roleType === 0) return

      const currentTime = dayjs().unix()

      if (
        this.waittingCase.length > 0 &&
        this.currentCase === null &&
        this.orderStatus === 0 &&
        this.ohtDirStatus > 0 &&
        this.ohtStatusInfo === 'default1'
      ) {
        const currentCase = this.waittingCase[0]
        const dict = JSON.parse(window.localStorage.getItem('dict') || '{}')
        const caseTypes = dict.oht_case_type || []
        const caseType = caseTypes.find(
          (item) => parseInt(item.code) === currentCase.caseType
        )
        const caseTypeName = caseType?.codeval || '未知'

        if (currentCase.buildTime > currentTime) {
          this.ohtCurrentCaseCountingTimer = setTimeout(
            () => {
              this.executeNewTask(currentCase, caseTypeName)
            },
            (currentCase.buildTime - currentTime) * 1000
          )
        } else {
          clearTimeout(this.ohtCurrentCaseCountingTimer)
          this.executeNewTask(currentCase, caseTypeName)
        }

        this.addIgnoreNum()
      } else if (
        !this.waittingCase?.length &&
        this.currentCase === null &&
        this.ohtRole.roleType === 1 &&
        this.orderStatus < 2
      ) {
        this.orderStatus = 0
        this.ohtStatusInfo = 'default1'
      }
    },

    executeNewTask(currentCase, caseTypeName) {
      this.ohtStatusInfo = 'task'
      this.orderStatus = 1

      // 发送WebSocket消息
      if (window.$ws?.ws) {
        window.$ws.ws.send(
          JSON.stringify({
            modal: 'oht',
            type: 'command',
            content: {
              action: 'newTask',
              caseId: currentCase.caseId,
              buildId: currentCase.buildId
            }
          })
        )
      }

      const wsStore = useWsStore()
      const message = `当前收到[${currentCase.dataTime}]求助类型为[${caseTypeName}]的求助，请尽快接单！`

      wsStore.setDesktopNotification('[举手系统]新的举手订单', message, 'oht')

      NotifyPlugin.info({
        title: '[举手系统]新的举手订单',
        content: message,
        duration: 0,
        placement: 'top-right'
      })
    },

    addIgnoreNum() {
      this.addTaskCaseNum({ addTaskType: 'ignoreNum', direction: '+' })
    },

    // 清理所有定时器
    clearAllTimers() {
      clearInterval(this.ohtCountingTimer)
      clearTimeout(this.ohtCurrentCaseCountingTimer)
      clearInterval(this.todoAlertTimer)

      this.ohtCountingTimer = null
      this.ohtCurrentCaseCountingTimer = null
      this.todoAlertTimer = null
    }
  }
})
// 导出默认
export default useOhtStore
