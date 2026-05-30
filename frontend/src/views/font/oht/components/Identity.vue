<template>
  <el-dialog title="今日身份选择" v-model="identityVisiable" :close-on-click-modal="false" :close-on-press-escape="false"
    :show-close="false">
    <el-alert style="margin-top: -20px;" title="请注意，值星慎重选择，若选择错误，请在顶部状态栏下拉框中更新状态！" type="warning" show-icon>
    </el-alert>
    <el-form>
      <el-form-item label="今日身份" prop="type">
        <el-checkbox-group v-model="identityArry" size="small" :max="2">
          <el-checkbox label="" @change="noIdentity" border>无身份</el-checkbox>
          <el-checkbox v-for="item in identityList" :key="item.id" :disabled="disabledFlag" :label="item.statusName"
            border></el-checkbox>
        </el-checkbox-group>
      </el-form-item>
    </el-form>
    <template #footer="scope">
      <el-button @click="identityVisible = !updateFlag">取消</el-button>
      <el-button type="primary" @click="sendIdentityChangeMsg">确 定</el-button>
    </template>
  </el-dialog>
</template>
<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useOhtStore, useWsStore } from '@/stores'
// import webSocketService from './websocket'
import { statusTypeApi } from '@/api/oht/statusType'
// 定义 props
const props = defineProps({
  updateFlag: {
    type: Boolean,
    default: false
  }
})

// 导入 store
const ohtStore = useOhtStore()

// 响应式数据
const identityList = ref([])
const identityArry = ref([])
const identityVisible = ref(true)
const disabledFlag = ref(false)

// 身份变更对象
const identityChange = reactive({
  modal: 'oht',
  type: 'identity', // message为消息，可为系统消息system，可为init初始化信息
  content: '',
  userStatus: null
})

// 对话框可见性
const identityVisiable = computed({
  get: () => {
    // 这里可以添加权限检查逻辑
    // const flag = hasPermission('main:directorStatus:manager')
    const flag = true // 暂时默认有权限
    return flag && (identityVisible.value === props.updateFlag)
  },
  set: (value) => {
    // 这里可以添加通知父组件的逻辑
  }
})

// 获取身份列表
const getIdentityList = async () => {
  try {
    const res = await statusTypeApi.getIdentityList()
    if (res.code !== 200) return ElMessage.error(res.msg)
    identityList.value = res.data
  } catch (error) {
    ElMessage.error('获取身份列表失败！')
  }
}

// 无身份处理
const noIdentity = (flag) => {
  disabledFlag.value = flag
  if (flag) identityArry.value = ['']
}

// 发送身份变更消息
const sendIdentityChangeMsg = () => {
  if (identityArry.value.length < 1) return ElMessage.error('请至少选择一个身份！')
  identityChange.userStatus = ohtStore.userStatus === null ? 0 : ohtStore.userStatus
  identityChange.content = identityArry.value.join(',')
  if (useWsStore.connectionStatus === 'connected') {
    useWsStore.send(JSON.stringify(identityChange))
  }
  ohtStore.setIdentity(identityArry.value.join(','))
  identityVisible.value = !props.updateFlag
}

// 生命周期
onMounted(() => {
  getIdentityList()
})
</script>
<style lang="less" scoped></style>
