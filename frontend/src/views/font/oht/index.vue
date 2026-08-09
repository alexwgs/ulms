<template>
  <div class="oht-chat-container">
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
                  <!-- Emoji按钮 -->
                  <div class="tool-item">
                    <t-popup placement="top-start" :width="320" trigger="click">
                      <t-button text @click="toggleEmoji">
                        <t-icon size="20">
                          <SunnyIcon />
                        </t-icon>
                      </t-button>
                      <template #content>
                        <div class="emoji-picker-container">
                          <emoji-picker v-if="showEmoji" @select="onEmojiSelect" />
                        </div>
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
                          <t-dropdown-item v-for="item in quickMemo" :key="item.journo" :command="item.memo">
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

              <!-- 输入框 -->
              <div class="input-wrapper">
                <t-textarea v-model="chatContent"  :rows="3" placeholder="请输入消息内容..."
                  @keyup.enter="handleMessage" resize="none" />
              </div>

              <!-- 发送按钮 -->
              <div class="send-area">
                <span class="tips">Enter 发送消息</span>
                <t-button theme="success" @click="handleMessage" :disabled="!chatContent.trim()" :loading="sending">
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
import { MessagePlugin } from 'tdesign-vue-next'
import { ChatIcon, InfoCircleFilledIcon, FileIcon, DeleteIcon, NotificationIcon, UserCircleIcon, TimeIcon, SunnyIcon } from 'tdesign-icons-vue-next'
import EmojiPicker from 'vue3-emoji-picker'
import 'vue3-emoji-picker/css'
import CommandBar from './components/CommandBar.vue'
import UserList from './components/UserList.vue'
import Station from './components/Station.vue'
import HistoryCase from './components/HistoryCase.vue'
import { useOhtStore, useDictStore, useUserStore, useWsStore } from '@/stores'
import { quickMemoApi } from '@/api/oht/quickMemo'

const ohtStore = useOhtStore()
const wsStore = useWsStore()
const dictStore = useDictStore()
const userStore = useUserStore()
const historyCaseRef = ref(null)
const stationRef = ref(null)

const messageScrollbar = ref(null)

const chatContent = ref('')
const quickMemo = ref([])
const messageCont = ref([])
const sending = ref(false)
const showEmoji = ref(false)

const fsURL = import.meta.env.VITE_FILE_BASE_URL
const defaultAvatar = new URL('@/assets/img/default_avatar.png', import.meta.url).href

const message = reactive({
  modal: 'oht',
  theme: 'message',
  content: '',
  avatar: ''
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
  }

  setScrollTop()
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
  chatContent.value = data
}

const clearChatMessage = () => {
  wsStore.message = []
  messageCont.value = wsStore.message
  MessagePlugin.success('聊天记录已清空')
}

const toggleEmoji = () => {
  showEmoji.value = !showEmoji.value
}

const onEmojiSelect = (emoji) => {
  chatContent.value += emoji.i
  showEmoji.value = false
}

onMounted(() => {
  messageCont.value = wsStore.message
  getQuickMemo()
  setScrollTop()
})
</script>

<style lang="less" scoped>
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
background: linear-gradient(135deg, var(--td-brand-color) 0%, var(--td-success-color) 100%);
    color: white;

    .chat-title {
      display: flex;
      align-items: center;
      gap: 8px;
      font-size: 16px;
      font-weight: 600;
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

.message-container {
  height: calc(100vh - 580px);
  min-height: 300px;
  overflow: hidden;
  background: var(--td-bg-color-container);
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
background: white;
              border: 1px solid #e8e8e8;

              .bubble-arrow {
                left: -8px;
                border-right-color: white;
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
background: #95ec69;
              border: 1px solid #95ec69;

              .bubble-arrow {
                right: -8px;
                left: auto;
                border-left-colorbackground: #95ec69;
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
      text-align: center;
      margin-bottom: 10px;

      span {
background: #dadada;
        color: white;
        padding: 2px 10px;
        border-radius: 3px;
        font-size: 12px;
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
          border-radius: 4px;
          object-fit: cover;
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
  border-top: 1px solid #e8e8e8;
  background: var(--td-bg-color-container);
  padding: 10px 15px;

  .toolbar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;
    padding-bottom: 10px;
    border-bottom: 1px solid #f0f0f0;

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

.emoji-picker-container {
  :deep(.emoji-picker) {
    --ep-bg-color: white;
    --ep-border-color: #e8e8e8;
    --ep-text-color: #333;
    --ep-secondary-bg-color: #f5f7fa;
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

:deep(.t-textarea__inner) {
  border-radius: 8px;
}
</style>
