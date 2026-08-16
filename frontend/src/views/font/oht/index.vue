<template>
  <!-- 免登录访问：未登录时显示登录引导，不发起依赖登录的请求（聊天/WS/个人信息均需登录） -->
  <div v-if="!loggedIn" class="oht-login-guide">
    <t-card class="login-guide-card">
      <t-icon size="48" style="color: var(--td-brand-color)">
        <LockOnIcon />
      </t-icon>
      <h3>值机助手需要登录后使用</h3>
      <p>登录后可在线沟通、查看值班状态、历史案件等信息</p>
      <t-button theme="primary" size="large" @click="goLogin">立即登录</t-button>
    </t-card>
  </div>
  <div v-else class="oht-chat-container">
    <t-row :gutter="15" class="main-row">
      <!-- 左侧聊天区域 -->
      <t-col :span="8">
        <div class="chat-wrapper">
          <!-- 状态栏 - 融入操作bar -->
          <div class="chat-status-bar">
            <CommandBar></CommandBar>
          </div>

          <!-- 聊天窗口 -->
          <t-card class="chat-card">
            <!-- 聊天头部 -->
            <div class="chat-header">
              <div class="chat-title">
                <span>在线沟通</span>
                <span class="conn-badge" :class="connState.class">
                  <i class="conn-dot"></i>{{ connState.text }}
                </span>
              </div>
              <div class="chat-tips">
                <t-icon>
                  <InfoCircleFilledIcon />
                </t-icon>
                <span>消息最多保留100条</span>
              </div>
            </div>

            <!-- 消息区域 -->
            <div class="message-container" ref="messageDiv">
              <div ref="messageScrollbar" height="100%">
                <div class="message-list">
                  <div v-for="(item, index) in messageCont" :key="index" class="message-item"
                    :class="[getMessageClass(item.direction)]">
                    <!-- 时间显示 -->
                    <div class="message-time" v-if="shouldShowTime(index)">
                      <span>{{ item.ctime }}</span>
                    </div>

                    <!-- 接收的消息 -->
                    <div v-if="item.direction === 2" class="message-content send">
                      <div class="avatar">
                        <img :src="(item.avatar ? fsURL + item.avatar : defaultAvatar)" alt="头像"
                          @error="($event.target.src = defaultAvatar)" />
                      </div>
                      <div class="bubble">
                        <div class="sender-name">{{ item.sendName }}</div>
                        <div class="bubble-content">
                          <div class="bubble-arrow"></div>
                          {{ item.content }}
                        </div>
                      </div>
                    </div>

                    <!-- 发送的消息 -->
                    <div v-if="item.direction === 1" class="message-content receive">
                      <div class="bubble">
                        <div class="bubble-content">
                          <div class="bubble-arrow"></div>
                          {{ item.content }}
                        </div>
                      </div>
                      <div class="avatar">
                        <img :src="(item.avatar ? fsURL + item.avatar : defaultAvatar)" alt="头像"
                          @error="($event.target.src = defaultAvatar)" />
                      </div>
                    </div>
                    <!-- 系统消息 -->
                    <div v-if="item.direction === 3" class="message-content system">
                      <t-tag theme="default" variant="light" size="small">
                        {{ item.content }}
                      </t-tag>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- 输入区域 -->
            <div class="input-area">
              <!-- 工具栏 -->
              <div class="toolbar">
                <div class="toolbar-left">
                  <!-- Emoji按钮（离线表情面板：纯本地字符，内网/离线可用） -->
                  <div class="tool-item">
                    <t-popup v-model:visible="emojiVisible" placement="top-start" trigger="click">
                      <t-button text>
                        <t-icon size="20">
                          <SmileIcon />
                        </t-icon>
                      </t-button>
                      <template #content>
                        <OfflineEmojiPicker @select="onEmojiSelect" />
                      </template>
                    </t-popup>
                  </div>

                  <!-- 快捷消息 -->
                  <div class="tool-item">
                    <t-dropdown @click="selectQuickMemo" trigger="click">
                      <t-button text>
                        <t-icon size="20">
                          <FileIcon />
                        </t-icon>
                        <span class="btn-text">快捷消息</span>
                      </t-button>
                      <template #dropdown>
                        <t-dropdown-menu>
                          <t-dropdown-item v-for="item in quickMemo" :key="item.journo" :value="item.memo">
                            {{ item.memo }}
                          </t-dropdown-item>
                        </t-dropdown-menu>
                      </template>
                    </t-dropdown>
                  </div>
                </div>

                <div class="toolbar-right">
                  <t-button text theme="danger" @click="clearChatMessage">
                    <t-icon size="18">
                      <DeleteIcon />
                    </t-icon>
                    <span class="btn-text">清屏</span>
                  </t-button>
                </div>
              </div>

              <!-- 输入框（原生 textarea：支持光标定位插入 emoji、Shift+Enter 换行） -->
              <div class="input-wrapper">
                <textarea ref="inputRef" v-model="chatContent" rows="3" class="chat-textarea"
                  placeholder="请输入消息内容...（Enter 发送，Shift+Enter 换行）"
                  @keydown="handleKeydown"></textarea>
              </div>

              <!-- 发送按钮 -->
              <div class="send-area">
                <span class="tips">Enter 发送 · Shift+Enter 换行</span>
                <t-button theme="primary" @click="handleMessage" :disabled="!chatContent.trim()" :loading="sending">
                  <t-icon>
                    <NotificationIcon />
                  </t-icon>
                  发送
                </t-button>
              </div>
            </div>
          </t-card>
        </div>
      </t-col>

      <!-- 右侧基本信息 -->
      <t-col :span="4">
        <div class="right-panel">
          <!-- 基本信息卡片 -->
          <t-card class="info-card" shadow>
            <template #header>
              <div class="card-header">
                <t-icon>
                  <UserCircleIcon />
                </t-icon>
                <span>基本信息</span>
              </div>
            </template>

            <div class="info-content">
              <div class="info-item">
                <span class="label">角色类型：{{ dictStore.getDictLabel('oht_role_type', ohtStore.ohtRole.roleType) }}</span>
              </div>

              <div class="info-item" v-if="ohtStore.station">
                <span class="label">位置信息：</span>
                <div class="tags">
                  <t-tag theme="primary" variant="light" size="small">
                    EXT: {{ ohtStore.station.extnNum }}
                  </t-tag>
                  <t-tag theme="success" variant="light" size="small">
                    楼层: {{ ohtStore.station.floorNum }}
                  </t-tag>
                </div>
                <t-button theme="primary" link @click="stationRef.openDialog()">
                  修改
                </t-button>
              </div>

              <div class="info-item" v-if="ohtStore.ohtRole.roleType === 1">
                <span class="label">接单数据：</span>
                <div class="tags">
                  <t-tag theme="success" variant="light">接起: {{ ohtStore.taskCaseNum.takeNum }}</t-tag>
                  <t-tag theme="warning" variant="light">忽略: {{ ohtStore.taskCaseNum.ignoreNum }}</t-tag>
                  <t-tag theme="danger" variant="light">拒绝: {{ ohtStore.taskCaseNum.refuseNum }}</t-tag>
                </div>
              </div>

              <div class="info-item">
                <span class="label">快捷操作：</span>
                <t-button theme="primary" size="small" @click="historyCaseRef.viewHistoryCase()">
                  <t-icon>
                    <TimeIcon />
                  </t-icon>
                  历史案件
                </t-button>
              </div>
            </div>
          </t-card>
          <!-- 用户列表 -->
          <UserList />
        </div>
      </t-col>
    </t-row>

    <!-- 弹窗组件 -->
    <Station ref="stationRef" />
    <HistoryCase ref="historyCaseRef" />
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, nextTick, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { MessagePlugin } from 'tdesign-vue-next'
import { InfoCircleFilledIcon, FileIcon, DeleteIcon, NotificationIcon, UserCircleIcon, TimeIcon, SmileIcon, LockOnIcon } from 'tdesign-icons-vue-next'
import OfflineEmojiPicker from './components/OfflineEmojiPicker.vue'
import CommandBar from './components/CommandBar.vue'
import UserList from './components/UserList.vue'
import Station from './components/Station.vue'
import HistoryCase from './components/HistoryCase.vue'
import { useOhtStore, useDictStore, useUserStore, useWsStore } from '@/stores'
import { quickMemoApi } from '@/api/oht/quickMemo'
import { isLogin } from '@/utils/auth'

const route = useRoute()
const router = useRouter()
const loggedIn = isLogin()

const goLogin = () => {
  router.push({ name: 'login', query: { redirect: route.fullPath } })
}

const ohtStore = useOhtStore()
const wsStore = useWsStore()
const dictStore = useDictStore()
const userStore = useUserStore()
const historyCaseRef = ref(null)
const stationRef = ref(null)

const messageScrollbar = ref(null)
const inputRef = ref(null)

const chatContent = ref('')
const quickMemo = ref([])
const messageCont = ref([])
const sending = ref(false)
const emojiVisible = ref(false)

const fsURL = import.meta.env.VITE_FILE_BASE_URL
const defaultAvatar = new URL('@/assets/img/default_avatar.png', import.meta.url).href

const message = reactive({
  modal: 'oht',
  type: 'message',
  content: '',
  avatar: ''
})

// 连接状态胶囊（在线/连接中/离线/异常）
const connState = computed(() => {
  const map = {
    connected: { class: 'on', text: '在线' },
    connecting: { class: 'pending', text: '连接中' },
    disconnected: { class: 'off', text: '离线' },
    error: { class: 'off', text: '连接异常' }
  }
  return map[wsStore.connectionStatus] || { class: 'off', text: wsStore.connectionStatusText || '离线' }
})

const getMessageClass = (direction) => {
  const classes = {
    1: 'send',
    2: 'receive',
    3: 'system'
  }
  return classes[direction] || ''
}

const shouldShowTime = (index) => {
  if (index === 0) return true
  const currentMsg = messageCont.value[index]
  const prevMsg = messageCont.value[index - 1]
  if (!currentMsg || !prevMsg) return true
  return currentMsg.ctime !== prevMsg.ctime
}

const handleMessage = () => {
  if (chatContent.value.trim() === '') {
    return MessagePlugin.warning('请输入要发送的内容！')
  }
  if (wsStore.connectionStatus === 'connected') {
    sending.value = true
    message.avatar = userStore.avatar
    message.content = chatContent.value.trim()
    wsStore.sendMessage(message)
    chatContent.value = ''
    sending.value = false
  } else {
    MessagePlugin.warning('当前未连接，消息发送失败，请检查网络')
  }

  setScrollTop()
  // 发送后回焦输入框
  nextTick(() => inputRef.value?.focus())
}

// Enter 发送、Shift+Enter 换行；中文输入法组合中（isComposing）不触发发送
const handleKeydown = (e) => {
  if (e.key === 'Enter' && !e.shiftKey && !e.isComposing) {
    e.preventDefault()
    handleMessage()
  }
}

const setScrollTop = () => {
  nextTick(() => {
    setTimeout(() => {
      if (messageScrollbar.value && messageScrollbar.value.wrap) {
        messageScrollbar.value.wrap.scrollTop =
          messageScrollbar.value.wrap.scrollHeight
      }
    }, 10)
  })
}

const getQuickMemo = async () => {
  try {
    const res = await quickMemoApi.getQuickMemoList({ type: 1 })
    if (res.code !== 200) return MessagePlugin.error('获取信息失败！')
    quickMemo.value = res.data.list
  } catch (error) {
    MessagePlugin.error('获取快捷消息失败！')
  }
}

const selectQuickMemo = (data) => {
  chatContent.value = data.value
  nextTick(() => inputRef.value?.focus())
}

const clearChatMessage = () => {
  wsStore.message = []
  messageCont.value = wsStore.message
  MessagePlugin.success('聊天记录已清空')
}

// 离线 emoji 面板：点击表情插入光标处（本地 Unicode，无网络依赖）
const onEmojiSelect = (emoji) => {
  const ta = inputRef.value
  const start = ta ? ta.selectionStart : chatContent.value.length
  const end = ta ? ta.selectionEnd : start
  chatContent.value =
    chatContent.value.slice(0, start) + emoji + chatContent.value.slice(end)
  emojiVisible.value = false
  nextTick(() => {
    if (ta) {
      const pos = start + emoji.length
      ta.focus()
      ta.setSelectionRange(pos, pos)
    }
  })
}

onMounted(() => {
  if (!loggedIn) return
  messageCont.value = wsStore.message
  getQuickMemo()
  setScrollTop()
})
</script>

<style lang="less" scoped>
.oht-login-guide {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: calc(100vh - 120px);

  .login-guide-card {
    width: 420px;
    padding: 40px 20px;
    text-align: center;
    border-radius: 12px;

    h3 {
      margin: 16px 0 8px;
      font-size: 18px;
      color: var(--td-text-color-primary);
    }

    p {
      margin: 0 0 24px;
      font-size: 13px;
      color: var(--td-text-color-secondary);
    }
  }
}

.oht-chat-container {
  padding: 0;
  min-height: 50vh;

  .main-row {
    height: calc(100vh - 200px);
  }
}

.chat-wrapper {
  height: calc(100vh - 190px);
  display: flex;
  flex-direction: column;

  .chat-status-bar {
    margin-bottom: 15px;
  }
}

.chat-card {
  flex: 1;
  display: flex;
  flex-direction: column;
  border-radius: 12px;
  overflow: hidden;

  .chat-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 12px 16px;
    background: linear-gradient(135deg, #22355c 0%, #17233b 100%);
    color: white;

    .chat-title {
      display: flex;
      align-items: center;
      gap: 8px;
      font-size: 16px;
      font-weight: 600;
    }

    .conn-badge {
      display: inline-flex;
      align-items: center;
      gap: 5px;
      padding: 2px 9px;
      margin-left: 4px;
      font-size: 12px;
      font-weight: 400;
      border-radius: 10px;
      background: rgba(255, 255, 255, 0.14);

      .conn-dot {
        width: 7px;
        height: 7px;
        border-radius: 50%;
        background: #9aa5b8;
      }

      &.on .conn-dot {
        background: #3ddc84;
        box-shadow: 0 0 0 3px rgba(61, 220, 132, 0.22);
      }

      &.pending .conn-dot {
        background: #f7ba2a;
        box-shadow: 0 0 0 3px rgba(247, 186, 42, 0.22);
        animation: conn-blink 1.2s ease-in-out infinite;
      }

      &.off .conn-dot {
        background: #f76560;
      }
    }

    .chat-tips {
      display: flex;
      align-items: center;
      gap: 4px;
      font-size: 12px;
      opacity: 0.9;
    }
  }
}

@keyframes conn-blink {
  50% {
    opacity: 0.35;
  }
}

.message-container {
  height: calc(100vh - 580px);
  min-height: 300px;
  overflow: hidden;
  background: #f5f7fb;
  padding: 15px;

  .message-list {
    .message-item {
      margin-bottom: 15px;

      &.receive {
        .message-content {
          display: flex;
          justify-content: flex-start;

          .avatar {
            margin-right: 10px;
          }

          .bubble {
            .bubble-content {
              background: #fff;
              border: 1px solid #e3e9f2;
              box-shadow: 0 1px 3px rgba(23, 35, 59, 0.05);

              .bubble-arrow {
                left: -8px;
                border-right-color: #fff;
              }
            }
          }
        }
      }

      &.send {
        .message-content {
          display: flex;
          justify-content: flex-end;

          .avatar {
            margin-left: 10px;
          }

          .bubble {
            .bubble-content {
              background: linear-gradient(135deg, #2f6bff 0%, #0052d9 100%);
              color: #fff;
              border: none;
              box-shadow: 0 2px 6px rgba(0, 82, 217, 0.22);

              .bubble-arrow {
                right: -8px;
                left: auto;
                border-left-color: #0052d9;
                border-right-color: transparent;
              }
            }
          }
        }
      }

      &.system {
        .message-content {
          justify-content: center;
        }
      }
    }

    .message-time {
      display: flex;
      align-items: center;
      gap: 12px;
      margin: 4px 0 12px;

      &::before,
      &::after {
        content: '';
        flex: 1;
        height: 1px;
        background: #e4e9f2;
      }

      span {
        padding: 2px 10px;
        border-radius: 10px;
        background: #e8edf5;
        color: #7a8699;
        font-size: 12px;
        font-variant-numeric: tabular-nums;
      }
    }

    .message-content {
      display: flex;
      align-items: flex-start;

      .avatar {
        flex-shrink: 0;

        img {
          width: 40px;
          height: 40px;
          border-radius: 6px;
          object-fit: cover;
          border: 1px solid #e3e9f2;
        }
      }

      .bubble {
        max-width: 70%;

        .sender-name {
          font-size: 12px;
          color: var(--td-text-color-secondary);
          margin-bottom: 4px;
        }

        .bubble-content {
          position: relative;
          padding: 10px 14px;
          border-radius: 8px;
          line-height: 1.5;
          font-size: 14px;
          word-break: break-all;
          white-space: pre-wrap;

          .bubble-arrow {
            position: absolute;
            top: 10px;
            border: 6px solid transparent;
          }
        }
      }
    }
  }
}

.input-area {
  border-top: 1px solid #e8edf5;
  background: #fff;
  padding: 10px 15px 12px;

  .toolbar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;
    padding-bottom: 10px;
    border-bottom: 1px solid #f0f3f8;

    .toolbar-left,
    .toolbar-right {
      display: flex;
      align-items: center;
      gap: 8px;
    }

    .tool-item {
      .btn-text {
        margin-left: 4px;
        font-size: 13px;
      }
    }
  }

  .input-wrapper {
    margin-bottom: 10px;
  }

  .chat-textarea {
    width: 100%;
    padding: 8px 10px;
    resize: none;
    border: 1px solid #dfe5ee;
    border-radius: 8px;
    font: inherit;
    font-size: 14px;
    line-height: 1.6;
    color: #1e2a3a;
    background: #fafbfd;
    transition: border-color 0.15s ease, box-shadow 0.15s ease;

    &:focus {
      outline: none;
      border-color: var(--td-brand-color);
      box-shadow: 0 0 0 2px rgba(0, 82, 217, 0.12);
      background: #fff;
    }

    &::placeholder {
      color: #a3adbd;
    }
  }

  .send-area {
    display: flex;
    justify-content: space-between;
    align-items: center;

    .tips {
      font-size: 12px;
      color: var(--td-text-color-secondary);
    }
  }
}

.right-panel {
  height: 100%;
  display: flex;
  flex-direction: column;
  gap: 15px;

  .info-card {
    border-radius: 12px;

    .card-header {
      display: flex;
      align-items: center;
      gap: 8px;
      font-weight: 600;
    }

    .info-content {
      .info-item {
        display: flex;
        align-items: center;
        flex-wrap: wrap;
        gap: 8px;
        padding: 10px 0;
        border-bottom: 1px solid #f0f0f0;

        &:last-child {
          border-bottom: none;
        }

        .label {
          font-size: 13px;
          color: #666;
          min-width: 70px;
        }

        .tags {
          display: flex;
          gap: 5px;
          flex-wrap: wrap;
        }
      }
    }
  }
}

:deep(.t-card) {
  border-radius: 12px;
}

:deep(.t-scrollbar__wrap) {
  overflow-x: hidden;
}
</style>
