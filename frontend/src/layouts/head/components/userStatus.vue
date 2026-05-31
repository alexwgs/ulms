<template>
    <t-popup v-if="ohtStore.ohtRole.roleType === 1" placement="top" :overlay-style="{ width: '600px' }" trigger="hover">
        <t-badge :count="ohtStore.ohtNewMessageSize" :dot="ohtStore.ohtNewMessageSize > 0">
            <t-button variant="outline" :theme="ohtStore.ohtDirStatus == 1 ? 'success' : 'danger'" size="small"
                @click="goToOht">{{
                    ohtStore.ohtDirStatus == 1 ? '可接单' : '小休' }} {{ userStatusMi }}分钟</t-button>
        </t-badge>
        <template #content>
            <t-tabs theme="card">
                <div class="identity-font"><b>当前身份:{{ getIdentity }}</b><t-button v-show="true" size="small"
                        style="margin-left:10px" theme="warning" @click="editIdentity()" variant="outline">修改</t-button></div>
                <t-tab-panel v-for="item in statusType" :value="item.id" :key="item.id" :label="item.statusName">
                    <template #label>
                        <span style="color:red;font-weight:700">{{ item.statusName }}</span>
                    </template>
                    <div v-for="item1 in item.children" :key="item1.id">
                        <t-divider :content="item1.statusName" align="left" />
                        <t-radio-group v-model="userStatus" @change="(val) => statusChange(val, item1.children)"
                            size="small">
                            <t-radio-button v-for="item2 in item1.children" :key="item2.id" :value="item2.statusName">
                                {{ item2.statusName }}
                            </t-radio-button>
                        </t-radio-group>
                    </div>
                </t-tab-panel>
                <div class="online-info">当前状态[{{ userStatus }}]持续时长：{{ userStatusMi }} 分钟</div>
            </t-tabs>
        </template>
    </t-popup>
    <t-badge v-else :count="ohtStore.ohtNewMessageSize" :dot="ohtStore.ohtNewMessageSize > 0">
        <t-tooltip content="当前会话服务器连接状态" placement="bottom">
            <t-button size="small" :theme="wsStore.connectionStatus === 'connected' ? 'success' : 'danger'"
                @click="goToOht">{{ wsStore.connectionStatus === 'connected' ? ohtStore.ohtNewMessageSize === 0 ? '在线' :
                    '待处理' : '离线'
                }}</t-button>
        </t-tooltip>
    </t-badge>
</template>

<script setup>
import { ref, computed, onMounted, onBeforeUnmount, onUnmounted } from 'vue'
import { MessagePlugin, DialogPlugin } from 'tdesign-vue-next'
import { useUserStore, useAppStore, useMenuStore, useWsStore, useOhtStore } from '@/stores'
import { statusTypeApi } from '@/api/oht/statusType'
import { useRouter } from 'vue-router'

const router = useRouter()
const ohtStore = useOhtStore()
const wsStore = useWsStore()
const userStatus = ref('准备中')
const statusType = ref([])
const statusTimer = ref(null)
const userStatusMi = ref(0)

onMounted(() => {
    const token = localStorage.getItem('token')
    const userId = localStorage.getItem('userId')
    if (token && userId) {
        wsStore.initWebSocket(userId)
    }
})
onBeforeUnmount(() => {
    wsStore.closeConnection()
})
const goToOht = () => {
    router.push('/font/oht')
}

const statusChangeMsg = ref({
    modal: 'oht',
    type: 'userStatus',
    content: ''
})

const getStatusType = async () => {
    const res = await statusTypeApi.getStatusTree()
    if (res.code !== 200) return MessagePlugin.error(res.msg || '获取身份失败！')
    statusType.value = res.data
}

const statusChange = async (e, data) => {
    const selected = data.find((item) => { return item.statusName === e })
    statusChangeMsg.value.content = { id: selected.id, memo: '' }
    if (e === '自定义') {
        try {
            const dialog = DialogPlugin({
                header: '提示',
                body: '请输入您的自定义工作内容[2到6个字符]',
                confirmBtn: '确定',
                cancelBtn: '取消',
                onConfirm: ({ e }) => {
                    statusChangeMsg.value.content.memo = '自定义'
                    wsStore.sendMessage(statusChangeMsg.value)
                    stopUserStatusTimer()
                    startUserStatusTimer()
                    dialog.hide()
                },
                onClose: () => {
                    MessagePlugin.info('取消输入')
                }
            })
        } catch (error) {
            MessagePlugin.info('取消输入')
        }
    } else {
        wsStore.sendMessage(statusChangeMsg.value)
        stopUserStatusTimer()
        startUserStatusTimer()
    }
}

const getIdentity = computed(() => {
    return ohtStore.identity === '' ? '无身份' : ohtStore.identity
})

const startUserStatusTimer = () => {
    stopUserStatusTimer()
    statusTimer.value = setInterval(() => {
        userStatusMi.value++
    }, 60000)
}

const stopUserStatusTimer = () => {
    if (statusTimer.value) {
        clearInterval(statusTimer.value)
        statusTimer.value = null
    }
}

const editIdentity = () => {
}

onMounted(() => {
    getStatusType()
    startUserStatusTimer()
})

onUnmounted(() => {
    stopUserStatusTimer()
})
</script>
