<template>
  <el-tabs type="border-card">
    <div class="identity-font">
      <b>当前身份:{{ getIdentity }}</b>
      <el-button
        v-show="true"
        size="small"
        style="margin-left: 10px"
        type="warning"
        @click="editIdentity()"
        plain
        >修改</el-button
      >
    </div>
    <el-tab-pane v-for="item in statusType" :key="item.id">
      <template #label>
        <span style="color: red; font-weight: 700">
          <el-icon v-if="item.statusName == '我在'"><Aim /></el-icon>
          <el-icon v-else><Position /></el-icon>
          {{ item.statusName }}
        </span>
      </template>
      <div v-for="item1 in item.children" :key="item1.id">
        <el-divider content-position="left">{{ item1.statusName }}</el-divider>
        <el-radio-group
          v-model="userStatus"
          ref="radio"
          @change="statusChange($event, item1.children)"
          size="small"
        >
          <el-radio-button
            v-for="item2 in item1.children"
            :key="item2.id"
            :label="item2.statusName"
          ></el-radio-button>
        </el-radio-group>
      </div>
    </el-tab-pane>
    <div class="online-info">
      当前状态[{{ userStatus }}]持续时长：{{ userStatusMi }} 分钟
    </div>
  </el-tabs>
</template>

<script setup>
import { ref, computed, onMounted, onBeforeUnmount } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Aim, Position } from '@element-plus/icons-vue'
import { getStatusTree } from '@/api/welcome/index.js'

const emit = defineEmits(['editIdentity', 'changeUserStatus'])

const rws = ref({})
const identityUpdateFlag = ref(false)
const userStatus = ref('准备中')
const statusType = ref([])
const userStatusMi = ref(0)
const statusTimer = ref(null)
const statusChangeMsg = ref({
  modal: 'oht',
  type: 'userStatus',
  content: ''
})

const getIdentity = computed(() => {
  try {
    const store = window.__POWERED_BY_QIANKUN__ ? window.$store : null
    return store?.state?.identity || '无身份'
  } catch {
    return '无身份'
  }
})

onMounted(() => {
  userStatusTimer()
  initWebSocket()
  getStatusType()
})

onBeforeUnmount(() => {
  if (statusTimer.value) {
    clearInterval(statusTimer.value)
  }
})

const initWebSocket = () => {
  try {
    rws.value = window.__POWERED_BY_QIANKUN__ ? window.$ws?.ws : null
  } catch (error) {
    console.error('初始化WebSocket失败', error)
  }
}

const editIdentity = () => {
  identityUpdateFlag.value = !identityUpdateFlag.value
  emit('editIdentity', identityUpdateFlag.value)
}

const getStatusType = async () => {
  try {
    const res = await getStatusTree()
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    statusType.value = res.data || []
  } catch (error) {
    console.error('获取状态类型失败', error)
  }
}

const statusChange = (e, data) => {
  const selected = data.find((item) => item.statusName === e)
  statusChangeMsg.value.content = { id: selected.id, memo: '' }
  emit('changeUserStatus', e)

  if (e === '自定义') {
    ElMessageBox.prompt('请输入您的自定义工作内容[2到6个字符]', '提示', {
      confirmButtonText: '确定',
      showClose: false,
      showCancelButton: false,
      closeOnPressEscape: false,
      closeOnClickModal: false,
      inputPattern: /^.{2,6}$/,
      inputErrorMessage: '内容长度需为2到6个字符'
    })
      .then(async ({ value }) => {
        statusChangeMsg.value.content.memo = value
        sendWebSocketMessage(statusChangeMsg.value)
        clearInterval(statusTimer.value)
        userStatusTimer()
      })
      .catch(() => {
        ElMessage.info('取消输入')
      })
  } else {
    sendWebSocketMessage(statusChangeMsg.value)
    clearInterval(statusTimer.value)
    userStatusTimer()
  }
}

const sendWebSocketMessage = (data) => {
  try {
    if (rws.value) {
      rws.value.send(JSON.stringify(data))
    }
  } catch (error) {
    console.error('发送WebSocket消息失败', error)
  }
}

const userStatusTimer = () => {
  userStatusMi.value = 0
  statusTimer.value = setInterval(() => {
    userStatusMi.value++
  }, 60000)
}
</script>

<style lang="less" scoped>
.identity-font {
  width: 100%;
  height: 20px;
  font-size: 14px;
  text-align: center;
  color: black;
  line-height: 20px;
}
.online-info {
  font-size: 14px;
  color: #f56c6c;
  line-height: 28px;
}
.status-divider {
  margin-bottom: 10px;
  margin-top: 10px;
  line-height: 20px;
}
</style>
