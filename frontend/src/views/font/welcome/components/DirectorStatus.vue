<template>
  <t-tabs theme="card">
    <div class="identity-font">
      <b>当前身份:{{ getIdentity }}</b>
      <t-button
        v-show="true"
        size="small"
        style="margin-left: 10px"
        theme="warning"
        @click="editIdentity()"
        variant="outline"
        >修改</t-button
      >
    </div>
    <t-tab-panel v-for="item in statusType" :key="item.id">
      <template #label>
        <span style="color: red; font-weight: 700">
          <t-icon v-if="item.statusName == '我在'"><MapAimingIcon /></t-icon>
          <t-icon v-else><LocationIcon /></t-icon>
          {{ item.statusName }}
        </span>
      </template>
      <div v-for="item1 in item.children" :key="item1.id">
        <t-divider content-position="left">{{ item1.statusName }}</t-divider>
        <t-radio-group
          v-model="userStatus"
          ref="radio"
          @change="statusChange($event, item1.children)"
          size="small"
        >
          <t-radio-button
            v-for="item2 in item1.children"
            :key="item2.id"
            :value="item2.statusName"
          ></t-radio-button>
        </t-radio-group>
      </div>
    </t-tab-panel>
    <div class="online-info">
      当前状态[{{ userStatus }}]持续时长：{{ userStatusMi }} 分钟
    </div>
  </t-tabs>
</template>

<script setup>
import { ref, computed, onMounted, onBeforeUnmount } from 'vue'
import { MessagePlugin, DialogPlugin } from 'tdesign-vue-next'
import { useOhtStore, useWsStore } from '@/stores'
import { MapAimingIcon, LocationIcon } from 'tdesign-icons-vue-next'
import { getStatusTree } from '@/api/welcome/index.js'

const emit = defineEmits(['editIdentity', 'changeUserStatus'])

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

const ohtStore = useOhtStore()
const getIdentity = computed(() => ohtStore.identity || '无身份')

onMounted(() => {
  userStatusTimer()
  getStatusType()
})

onBeforeUnmount(() => {
  if (statusTimer.value) {
    clearInterval(statusTimer.value)
  }
})

const editIdentity = () => {
  identityUpdateFlag.value = !identityUpdateFlag.value
  emit('editIdentity', identityUpdateFlag.value)
}

const getStatusType = async () => {
  try {
    const res = await getStatusTree()
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
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
    DialogPlugin.prompt('请输入您的自定义工作内容[2到6个字符]', '提示', {
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
        MessagePlugin.info('取消输入')
      })
  } else {
    sendWebSocketMessage(statusChangeMsg.value)
    clearInterval(statusTimer.value)
    userStatusTimer()
  }
}

const wsStore = useWsStore()
const sendWebSocketMessage = (data) => {
  wsStore.sendMessage(data)
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
  color: var(--td-error-color);
  line-height: 28px;
}
.status-divider {
  margin-bottom: 10px;
  margin-top: 10px;
  line-height: 20px;
}
</style>
