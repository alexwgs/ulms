// stores/modules/ws/index.js
import { defineStore } from 'pinia'
import ReconnectingWebSocket from 'reconnecting-websocket'
import router from '@/router'
import { useOhtStore, useUserStore } from '@/stores'
import { clearToken } from '@/utils/auth'
import { NotifyPlugin } from 'tdesign-vue-next'
import todoVoice from '@/assets/voice/todoVoice.mp3'
import ohtVoice from '@/assets/voice/ohtVoice.mp3'
import messageVoice from '@/assets/voice/messageVoice.mp3'
export const useWsStore = defineStore('websocket', {
  state: () => ({
    // WebSocket连接状态: 'disconnected' | 'connecting' | 'connected' | 'error'
    connectionStatus: 'disconnected',
    // 连接错误信息
    connectionError: null,
    // 用户ID
    userId: '',
    // 消息列表
    message: [],
    // OHT消息大小
    ohtMsgSize: 0,
    // 未完成案件
    unfinishCase: null,
    // 用户列表
    userList: [],
    // 时间戳
    times: null,
    // 任务案件数量
    taskCaseNum: {},
    // 重连尝试次数
    reconnectAttempts: 0,
    // 最大重连次数
    maxReconnectAttempts: 5,
    // 最后收到消息的时间
    lastMessageTime: null,
    // 是否手动关闭
    manuallyClosed: false,
    // 消息提示音
    notificationAudio: null,

    noticeWin: null,

    noticeContent: {},

    // 通用推送消息列表
    pushMessages: [],

    // 弹窗管理
    windowCounter: 0,
    openedWindows: {}
  }),

  getters: {
    // 是否已连接
    isConnected: (state) => state.connectionStatus === 'connected',

    // 是否正在连接
    isConnecting: (state) => state.connectionStatus === 'connecting',

    // 是否断开
    isDisconnected: (state) => state.connectionStatus === 'disconnected',

    // 是否有错误
    hasError: (state) => state.connectionStatus === 'error',

    // 获取未读消息数量
    unreadMessageCount: (state) => {
      return state.message.filter((msg) => !msg.read).length
    },

    // 获取最新消息
    latestMessages: (state) => {
      return state.message.slice(-50)
    },

    // 获取连接状态描述
    connectionStatusText: (state) => {
      const statusMap = {
        disconnected: '未连接',
        connecting: '连接中',
        connected: '已连接',
        error: '连接错误'
      }
      return statusMap[state.connectionStatus] || '未知状态'
    }
  },

  actions: {
    // 初始化WebSocket连接
    initWebSocket(userId) {
      if (!userId) {
        console.error('用户ID不能为空')
        return
      }

      this.userId = userId
      this.manuallyClosed = false
      this.connectionError = null
      this.setConnectionStatus('connecting')
      this.connect()
    },

    // 设置连接状态
    setConnectionStatus(status) {
      this.connectionStatus = status
    },

    // 设置错误信息
    setConnectionError(error) {
      this.connectionError = error
      console.error('WebSocket错误:', error)
    },

    // 建立WebSocket连接
    connect() {
      try {
        // 自动适配 HTTP/HTTPS：
        // 开发模式直接采用 env 中配置的协议与 host（例如 wss://localhost:8443），
        // 避免 http://localhost:5173 页面被拼成 ws://localhost:8443 导致 TLS 握手失败；
        // 生产模式与页面同源，按页面协议选择 ws/wss。
        const protocol = import.meta.env.DEV
          ? new URL(import.meta.env.VITE_WS_BASE_URL).protocol + '//'
          : window.location.protocol === 'https:'
            ? 'wss://'
            : 'ws://'
        const host = import.meta.env.DEV
          ? new URL(import.meta.env.VITE_WS_BASE_URL).host
          : window.location.host
        const pathPart = import.meta.env.BASE_URL
        const token = localStorage.getItem('token')
        const wsUrl =
          protocol + host + pathPart + 'ws/' + this.userId + (token ? '?token=' + encodeURIComponent(token) : '')

        // 创建WebSocket实例
        this.ws = new ReconnectingWebSocket(wsUrl, null, {
          debug: false,
          reconnectInterval: 3000,
          maxReconnectAttempts: this.maxReconnectAttempts
        })

        this.setupEventListeners()
      } catch (error) {
        this.setConnectionError(error)
        this.setConnectionStatus('error')
      }
    },

    // 设置事件监听
    setupEventListeners() {
      const ohtStore = useOhtStore()

      // 连接打开
      this.ws.onopen = (event) => {
        this.reconnectAttempts = 0
        this.connectionError = null
        this.setConnectionStatus('connected')
      }

      // 接收消息
      this.ws.addEventListener('message', (event) => {
        this.lastMessageTime = Date.now()

        try {
          const msg = JSON.parse(event.data)
          if (msg.code === 200) {
            this.handleSuccessMessage(msg, ohtStore)
          } else {
            console.error('接收到错误消息！', msg)
          }
        } catch (error) {
          console.error('解析消息失败:', error)
        }
      })

      // 连接关闭
      this.ws.onclose = (event) => {
        if (this.manuallyClosed) {
          this.setConnectionStatus('disconnected')
        } else {
          this.setConnectionStatus('connecting')
        }
      }

      // 连接错误
      this.ws.onerror = (error) => {
        console.error('WebSocket错误:', error)
        this.setConnectionError(error)
        this.setConnectionStatus('error')
        this.handleReconnect()
      }
    },

    // 处理重连
    handleReconnect() {
      this.reconnectAttempts++

      if (this.reconnectAttempts >= this.maxReconnectAttempts) {
        this.setConnectionError(new Error('WebSocket重连失败，请检查网络连接'))
        this.setConnectionStatus('error')
      }
    },

    // 手动重连
    reconnect() {
      if (this.manuallyClosed) {
        this.manuallyClosed = false
      }

      this.closeConnection()
      this.connect()
    },

    // 处理成功消息
    handleSuccessMessage(msg, ohtStore) {
      if (msg.model === 'oht') {
        this.handleOhtMessage(msg, ohtStore)
      } else if (msg.model === 'cyt') {
        this.handleCytMessage(msg)
      } else if (msg.model === 'sys') {
        this.handleSysMessage(msg)
      } else if (msg.model === 'push') {
        this.handlePushMessage(msg)
      } else if (msg.model === 'admin') {
        this.handleAdminMessage(msg)
      }
    },

    // 处理OHT相关消息
    handleOhtMessage(msg, ohtStore) {
      switch (msg.type) {
        case 'init':
          this.handleInitMessage(msg, ohtStore)
          break
        case 'message':
          this.handleChatMessage(msg)
          break
        case 'listInit':
          ohtStore.setUserList(msg.data.data)
          break
        case 'system':
          this.handleSystemMessage(msg, ohtStore)
          break
        case 'command':
          this.handleCommandMessage(msg, ohtStore)
          break
        case 'error':
          this.handleErrorMessage(msg, ohtStore)
          break
        default:
      }
    },

    // 处理初始化消息
    handleInitMessage(msg, ohtStore) {
      const data = msg.data.data
      // 如果当前刷新后，实际有发起的求助，但是未结案，优先切换至案件求助中

      // 设置OHT角色
      ohtStore.setOhtRole(data.ohtRole)

      // 设置OHT状态信息
      this.setOhtStatusByRole(data.ohtRole.roleType, ohtStore)

      // 处理未完成案件
      if (data.unfinishCase !== undefined) {
        this.unfinishCase = data.unfinishCase
        ohtStore.setCurrentCase(data.unfinishCase)

        const status =
          data.unfinishCase.caseStatus === 0 ? 'reconnectWait' : 'unfinish'
        ohtStore.setOhtStatusInfo(status)
        ohtStore.setOrderStatus(3)
      } else {
        ohtStore.setWaittingCase(msg.data.cases)
      }

      // 设置其他数据
      if (data.station !== undefined) {
        ohtStore.setStation(data.station)
      }

      if (data.ohtStatus !== null) {
        ohtStore.setOhtDirStatus(data.ohtStatus)
      }

      if (data.identity !== '') {
        ohtStore.setIdentity(data.identity)
      }

      if (data.data?.taskCaseNum !== undefined) {
        ohtStore.setTaskCaseNum(data.data.taskCaseNum)
      }

      // 遍历求助案件
      ohtStore.ohtCasesToCurrentCase()
    },

    // 根据角色设置OHT状态
    setOhtStatusByRole(roleType, ohtStore) {
      const statusMap = {
        0: 'default0',
        1: 'default1'
      }
      ohtStore.setOhtStatusInfo(statusMap[roleType] || 'error')
    },

    // 处理聊天消息
    handleChatMessage(msg) {
      try {
        this.message.push({
          ...msg.data.data,
          read: false,
          timestamp: Date.now()
        })

        // 当当前页面不在OHT对话界面才推送通知数量
        if (sessionStorage.getItem('activePath') !== '/font/oht') {
          this.incrementUnreadCount()
        }

        // 限制消息数量
        if (this.message.length >= 130) {
          this.message.splice(0, 30)
        }
      } catch (err) {
        // 忽略错误，不阻塞消息处理
      }
    },

    // 处理系统消息
    handleSystemMessage(msg, ohtStore) {
      const currentUserId = JSON.parse(
        window.localStorage.getItem('user')
      )?.ploNum

      switch (msg.msg) {
        case 'join':
          ohtStore.addUserList(msg.data.data)
          break
        case 'leave':
          ohtStore.deleteUserList(msg.data.data)
          break
        case 'update': {
          // 标准化数据格式：确保 roleType 在顶层（兼容轻量列表格式与完整 InitUser 格式）
          const updatedUser = msg.data.data
          if (updatedUser.roleType === undefined && updatedUser.ohtRole) {
            updatedUser.roleType = updatedUser.ohtRole.roleType
          }
          ohtStore.deleteUserList(updatedUser.userId)
          ohtStore.addUserList(updatedUser)

          if (updatedUser.userId === currentUserId) {
            ohtStore.setIdentity(updatedUser.identity)
            ohtStore.setOhtDirStatus(updatedUser.ohtStatus)
            ohtStore.ohtCasesToCurrentCase()
          }
          break
        }
      }
    },

    // 处理命令消息
    handleCommandMessage(msg, ohtStore) {
      const commandHandlers = {
        newCase: () => {
          ohtStore.setCurrentCase(msg.data.case)
          ohtStore.setOhtStatusInfo('helpWait')
        },
        reconnect: () => {
          const roleType = ohtStore.ohtRole?.roleType
          const status = roleType === 0 ? 'reconnect0' : 'reconnect1'
          ohtStore.setOhtStatusInfo(status)
        },
        help: () => {
          ohtStore.setWaittingCase(msg.data.cases)
        },
        cancelSuccess: () => {
          ohtStore.setCurrentCase(null)
          ohtStore.setOhtStatusInfo('default0')
        },
        linked: () => {
          ohtStore.setOrderStatus(2)
          ohtStore.setCurrentCase(msg.data.case)

          const roleType = ohtStore.ohtRole?.roleType
          if (roleType === 0) {
            ohtStore.setOhtStatusInfo('linked0')
          } else if (roleType === 1) {
            ohtStore.setOhtStatusInfo('linked1')
            ohtStore.addTaskCaseNum({ addTaskType: 'takeNum', direction: '+' })
            ohtStore.addTaskCaseNum({
              addTaskType: 'ignoreNum',
              direction: '-'
            })
          }
        },
        disconnect: () => {
          ohtStore.setOhtStatusInfo('disconnect')
        },
        mineComplete: () => {
          ohtStore.setCurrentCase(null)
          ohtStore.setOrderStatus(0)

          const roleType = ohtStore.ohtRole?.roleType
          if (roleType === 0) {
            ohtStore.setOhtStatusInfo('default0')
          } else if (roleType === 1) {
            ohtStore.setOhtStatusInfo('default1')
            ohtStore.setWaittingCase(msg.data.cases)
          }
        },
        otherComplete: () => {
          const roleType = ohtStore.ohtRole?.roleType
          const status = roleType === 0 ? 'otherComplete0' : 'otherComplete1'
          ohtStore.setOhtStatusInfo(status)
        },
        refuseCase: () => {
          ohtStore.addRefuseCaseId(msg.data.caseId)
          ohtStore.setOhtStatusInfo('default1')
          ohtStore.addTaskCaseNum({ addTaskType: 'refuseNum', direction: '+' })
          ohtStore.addTaskCaseNum({ addTaskType: 'ignoreNum', direction: '-' })
          ohtStore.setOrderStatus(0)
          ohtStore.setWaittingCase(msg.data.cases)
        },
        cancel: () => {
          if (ohtStore.orderStatus === 1) {
            ohtStore.setOhtStatusInfo('default1')
            ohtStore.setOrderStatus(0)
            ohtStore.setCurrentCase(null)
          }
        }
      }

      const handler = commandHandlers[msg.msg]
      if (handler) {
        handler()
      }
    },

    // 处理错误消息
    handleErrorMessage(msg, ohtStore) {
      console.error('[系统]消息:', msg.msg)

      ohtStore.setOhtStatusInfo('default1')
      ohtStore.setOrderStatus(0)
      ohtStore.setCurrentCase(null)
      ohtStore.setWaittingCase([])
    },

    // 处理CYT消息
    handleCytMessage(msg) {
      if (msg.type === 'notice') {
        // 通知消息暂未处理
      }
    },

    // 处理系统消息
    handleSysMessage(msg) {
      if (msg.type === 'system' && msg.msg === 'logoff') {
        this.handleLogout('您的账号已在另一处登录，请注意账号安全或正常登出！')
      } else if (msg.type === 'error') {
        console.error('[系统]出错了:', msg.msg)
      }
    },

    // 处理通用推送消息
    handlePushMessage(msg) {
      const { module, title, content, timestamp } = msg.data || {}
      this.pushMessages.unshift({
        module, title, content, timestamp,
        id: Date.now() + Math.random(),
        read: false
      })
      if (this.pushMessages.length > 100) this.pushMessages.splice(99)

      // 语音提醒
      this.setDesktopNotification(title, typeof content === 'string' ? content : '', 'message')

      // 右下角弹窗
      const typeMap = { notice: 'info', warning: 'warning', info: 'info', error: 'error' }
      const messageBody = typeof content === 'string' ? content : (content ? JSON.stringify(content) : '')
      const notifyType = typeMap[msg.data?.type] || 'info'
      const notifyFn = NotifyPlugin[notifyType] || NotifyPlugin.info
      notifyFn({
        title: title || '系统通知',
        content: messageBody || `来自 ${module} 模块的消息`,
        duration: 5000,
        placement: 'bottom-right'
      })

      // 浏览器弹窗 —— 通过 localStorage 传递数据
      localStorage.setItem('ulms_push_notify', JSON.stringify({
        module, title, content, timestamp,
        type: msg.data?.type
      }))
      const baseUrl = import.meta.env.BASE_URL || '/'
      const notifyPath = baseUrl.endsWith('/') ? baseUrl + 'notification' : baseUrl + '/notification'
      window.open(
        window.location.origin + notifyPath,
        'pushNotify',
        'width=480,height=360,left=' + (window.screen.width - 500) + ',top=' + (window.screen.height - 400)
      )
    },

    // 处理管理员消息
    handleAdminMessage(msg) {
      if (msg.type === 'kickOut') {
        this.handleLogout('由于系统维护，您已被强制登出！')
      } else if (msg.type === 'message') {
        // 管理员消息暂未处理
      }
    },

    // 处理登出
    handleLogout(message) {
      if (message) {
        NotifyPlugin.warning({ title: '系统提示', content: message })
      }
      const userStore = useUserStore()
      userStore.resetInfo()
      clearToken()
      router.push('/login')
    },

    // 增加未读计数
    incrementUnreadCount() {
      const ohtStore = useOhtStore()
      ohtStore.setOhtNewMessageSize('add')
    },

    // 发送消息
    sendMessage(message) {
      if (this.ws && this.ws.readyState === 1) {
        this.ws.send(JSON.stringify(message))
        return true
      }
      console.warn(
        'WebSocket未连接，消息发送失败，当前状态:',
        this.connectionStatus
      )
      return false
    },

    // 关闭WebSocket连接
    closeConnection() {
      this.manuallyClosed = true
      if (this.ws) {
        this.ws.close()
        this.ws = null
      }
      this.setConnectionStatus('disconnected')
      this.message = []
    },

    // 重置状态
    resetState() {
      this.closeConnection()
      this.$reset()
    },

    // 标记消息为已读
    markMessagesAsRead() {
      this.message = this.message.map((msg) => ({ ...msg, read: true }))
    },

    // 清空消息
    clearMessages() {
      this.message = []
    },

    // 获取ws实例（谨慎使用）
    getWsInstance() {
      return this.ws
    },

    // 检查连接是否打开
    isWsOpen() {
      return this.ws && this.ws.readyState === 1
    },
    sendNotice(app, type, title, content) {
      this.noticeContent = {
        app: app,
        type: type,
        title: title,
        content: content
      }
      this.openWindow({
        url: '/ulms/notification',
        title: '通知',
        width: 400,
        height: 300
      })
    },
    setDesktopNotification(title, message, voiceType) {
      if (title === null) title = '新的消息'
      if (message === null) message = '您有新的消息,请注意查收！'
      this.setVoiceNotice(voiceType)
      if (window.Notification && Notification.permission !== 'denied') {
        Notification.requestPermission(function (status) {
          var notice_ = new Notification(
            title + '【' + new Date().toLocaleString() + '】',
            { body: message }
          )
          notice_.onclick = function () {
            window.focus()
          }
        })
      } else {
        this.sendNotice(
          'oht',
          'case',
          title + '【' + new Date().toLocaleString() + '】',
          message
        )
      }
    },
    setVoiceNotice(type) {
      // 停止当前播放的音频
      if (this.notificationAudio) {
        this.notificationAudio.pause()
        this.notificationAudio.currentTime = 0
        this.notificationAudio = null
      }
      // 如果type是close，只停止播放不创建新音频
      if (type === 'close') {
        return
      }
      try {
        let audioSrc = null
        let loop = false
        // 根据类型选择音频文件和配置
        switch (type) {
          case 'todo':
            audioSrc = todoVoice // 使用已导入的音频
            loop = false
            break
          case 'oht':
            audioSrc = ohtVoice // 使用已导入的音频
            loop = true
            break
          case 'message':
            audioSrc = messageVoice // 使用已导入的音频
            loop = false
            break
          default:
            return
        }
        // 创建并播放音频
        if (audioSrc) {
          const audio = new Audio(audioSrc)
          audio.loop = loop
          // 添加错误处理
          audio.onerror = (e) => {
            console.error('音频播放失败:', e)
            this.notificationAudio = null
          }
          // 播放
          audio.play().catch((e) => {
            console.error('播放失败:', e)
            this.notificationAudio = null
          })
          this.notificationAudio = audio
        }
      } catch (e) {
        console.error('创建音频失败:', e)
        this.notificationAudio = null
      }
    },
    /**
     * 打开新窗口
     * @param {Object} options 配置选项
     * @param {string} options.url 要打开的URL
     * @param {string} options.title 窗口标题
     * @param {number} options.width 窗口宽度
     * @param {number} options.height 窗口高度
     * @param {boolean} options.center 是否居中
     * @param {Object} options.data 要传递的数据
     */
    openWindow(options = {}) {
      const {
        url = '/ulms/notification',
        title = '新窗口',
        width = 800,
        height = 600,
        center = true
      } = options

      // 计算窗口位置（居中显示）
      const left = center ? (window.screen.width - width) / 2 : 100
      const top = center ? (window.screen.height - height) / 2 : 100

      // 窗口特性
      const features = `
        width=${width},
        height=${height},
        left=${left},
        top=${top},
        toolbar=no,
        menubar=no,
        scrollbars=yes,
        resizable=yes,
        location=no,
        status=no
      `.replace(/\s+/g, '')

      // 生成唯一窗口ID
      const windowId = `window_${Date.now()}_${this.windowCounter++}`

      // 构建完整URL
      const fullUrl = `${window.location.origin}${url}`

      // 打开新窗口
      const newWindow = window.open(fullUrl, title, features)

      if (newWindow) {
        // 存储窗口引用
        this.openedWindows[windowId] = {
          window: newWindow,
          title,
          url: fullUrl,
          openTime: Date.now(),
          options
        }

        // 监听窗口关闭
        const checkClosed = setInterval(() => {
          if (newWindow.closed) {
            clearInterval(checkClosed)
            this.handleWindowClose(windowId)
          }
        }, 500)

        return windowId
      } else {
        console.error('窗口打开失败，可能被浏览器拦截')
        this.showPopupBlockedMessage()
        return null
      }
    }
  }
})

// 导出默认
export default useWsStore
