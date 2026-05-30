<template>
    <el-popover v-if="ohtStore.ohtRole.roleType === 1" placement="top" width="600" trigger="hover">
        <el-tabs type="border-card">
            <div class="identity-font"><b>当前身份:{{ getIdentity }}</b><el-button v-show="true" size="small"
                    style="margin-left:10px" type="warning" @click="editIdentity()" plain>修改</el-button></div>
            <el-tab-pane v-for="item in statusType" :label="item.statusName" :key="item.id">
                <span slot="label" style="color:red;font-weight:700"><i
                        :class="item.statusName == '我在' ? 'el-icon-aim' : 'el-icon-position'"></i> {{ item.statusName
                        }}</span>
                <div v-for="item1 in item.children" :key="item1.id">
                    <el-divider content-position="left">{{ item1.statusName }}</el-divider>
                    <el-radio-group v-model="userStatus" ref="radio" @change="statusChange($event, item1.children)"
                        size="small">
                        <el-radio-button v-for="item2 in item1.children" :key="item2.id" :value="item2.statusName"
                            :label="item2.statusName"></el-radio-button>
                    </el-radio-group>
                </div>
            </el-tab-pane>
            <div class="online-info">当前状态[{{ userStatus }}]持续时长：{{ userStatusMi }} 分钟</div>
        </el-tabs>
        <template #reference>
            <el-badge :hidden="ohtStore.ohtNewMessageSize === 0" :value="ohtStore.ohtNewMessageSize" class="item">
                <el-button plain :type="ohtStore.ohtDirStatus == 1 ? 'success' : 'danger'" size="small"
                    @click="goToOht">{{
                        ohtStore.ohtDirStatus == 1 ? '可接单' : '小休' }} {{ userStatusMi }}分钟</el-button>
            </el-badge>
        </template>
    </el-popover>
    <el-badge v-else :hidden="ohtStore.ohtNewMessageSize === 0" :value="ohtStore.ohtNewMessageSize" class="item">
        <el-tooltip content="当前会话服务器连接状态" placement="bottom">
            <el-button size="small" :type="wsStore.connectionStatus === 'connected' ? 'success' : 'danger'"
                @Click="goToOht">{{ wsStore.connectionStatus === 'connected' ? ohtStore.ohtNewMessageSize === 0 ? '在线' :
                    '待处理' : '离线'
                }}</el-button>
        </el-tooltip>
    </el-badge>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useUserStore, useAppStore, useMenuStore, useWsStore, useOhtStore } from '@/stores'
import { statusTypeApi } from '@/api/oht/statusType'
import { useRouter } from 'vue-router'

const router = useRouter()
const ohtStore = useOhtStore()
const wsStore = useWsStore()
const userStatus = ref('准备中')
const statusType = ref([])
const statusTimer = ref(null)  // 使用 ref 存储定时器
const userStatusMi = ref(0)

onMounted(() => {
    // 检查用户是否已登录
    const token = localStorage.getItem('token')
    const userId = localStorage.getItem('userId')
    if (token && userId) {
        // 初始化WebSocket连接
        wsStore.initWebSocket(userId)
    }
})
onBeforeUnmount(() => {
    wsStore.closeConnection()
})
// 跳转到关于页面
const goToOht = () => {
    router.push('/font/oht')
}

const statusChangeMsg = ref({
    modal: 'oht',
    type: 'userStatus', // message为消息，可为系统消息system，可为init初始化信息
    content: ''
})

// 获取身份类型
const getStatusType = async () => {
    const res = await statusTypeApi.getStatusTree()
    if (res.code !== 200) return ElMessage.error(res.msg || '获取身份失败！')
    statusType.value = res.data
}

// 状态变更处理
const statusChange = async (e, data) => {
    const selected = data.find((item) => { return item.statusName === e })
    statusChangeMsg.value.content = { id: selected.id, memo: '' }
    if (e === '自定义') {
        try {
            const { value } = await ElMessageBox.prompt('请输入您的自定义工作内容[2到6个字符]', '提示', {
                confirmButtonText: '确定',
                showCancelButton: true,
                closeOnPressEscape: false,
                closeOnClickModal: false,
                inputPattern: /^.{2,6}$/,
                inputErrorMessage: '请输入2到6个字符'
            })

            statusChangeMsg.value.content.memo = value
            wsStore.sendMessage(statusChangeMsg.value)

            // 清除旧定时器，启动新定时器
            stopUserStatusTimer()
            startUserStatusTimer()

        } catch (error) {
            ElMessage.info('取消输入')
        }
    } else {
        wsStore.sendMessage(statusChangeMsg.value)

        // 清除旧定时器，启动新定时器
        stopUserStatusTimer()
        startUserStatusTimer()
    }
}

// 计算属性
const getIdentity = computed(() => {
    return ohtStore.identity === '' ? '无身份' : ohtStore.identity
})

// 启动定时器
const startUserStatusTimer = () => {
    // 如果已有定时器，先清除
    stopUserStatusTimer()

    statusTimer.value = setInterval(() => {
        userStatusMi.value++
    }, 60000) // 1分钟
}

// 停止定时器
const stopUserStatusTimer = () => {
    if (statusTimer.value) {
        clearInterval(statusTimer.value)
        statusTimer.value = null
    }
}

// 重置定时器（如果需要）
const resetUserStatusTimer = () => {
    userStatusMi.value = 0
    startUserStatusTimer()
}

// 编辑身份方法（需要实现）
const editIdentity = () => {
}

// 生命周期钩子
onMounted(() => {
    getStatusType()
    startUserStatusTimer()
})

// 组件卸载时清理定时器
onUnmounted(() => {
    stopUserStatusTimer()
})
</script>