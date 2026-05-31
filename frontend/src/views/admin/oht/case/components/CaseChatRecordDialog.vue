<template>
  <t-dialog header="案件往来记录" v-model:visible="dialogVisible" width="80%">
    <div style="height: 500px; overflow: auto;">
      <t-timeline>
        <t-timeline-item
          v-for="(item, index) in chatRecords"
          :key="index"
          :timestamp="item.type === 'message' ? item.data.data.ctime : item.data.case.dataTime"
          placement="top"
        >
          <t-alert
            v-if="item.type === 'command' && item.msg === 'newCase'"
            title="【发起新案件】"
            :message="'建案时间：[' + item.data.case.buildTime + ']'"
            theme="info"
            :closable="false"
          ></t-alert>
          <t-alert
            v-else-if="item.type === 'command' && item.msg === 'linked'"
            title="【建立连接】"
            :message="'发起人信息：[' + item.data.case.buildUser.ploName + '/' + item.data.case.buildUser.ploNum + '] ， 分机号：[' + item.data.case.buildExtn + '] ， '
            + '接单人信息：[' + item.data.case.pickUser.ploName + '/' + item.data.case.pickUser.ploNum + '] ， 分机号：[' + item.data.case.pickExtn + '] 发起人等待时长: ' + item.data.case.waitTime"
            theme="success"
            :closable="false"
          ></t-alert>
          <t-card v-else-if="item.type === 'message'">
            <p>{{ item.data.data.sendName }} 发送消息：</p>
            <h4>{{ item.data.data.content }}</h4>
          </t-card>
          <t-alert
            v-else-if="item.type === 'command' && item.msg === 'otherComplete'"
            title="【用户结案】"
            :message="'发起人结案：[' + item.data.case.bcompMemo + '/' + item.data.case.bcompTime + '] ， '
            + '接单人结案：[' + item.data.case.pcompMemo + '/' + item.data.case.pcompTime + '] '"
            theme="warning"
            :closable="false"
          ></t-alert>
        </t-timeline-item>
      </t-timeline>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <t-button theme="primary" @click="dialogVisible = false">关闭</t-button>
      </span>
    </template>
  </t-dialog>
</template>

<script setup>
import { ref, watch } from 'vue'

const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  },
  chatRecords: {
    type: Array,
    default: () => []
  }
})

const emit = defineEmits(['update:visible'])

const dialogVisible = ref(props.visible)

// 监听外部visible变化
watch(() => props.visible, (newVal) => {
  dialogVisible.value = newVal
})

// 监听内部dialogVisible变化
watch(dialogVisible, (newVal) => {
  emit('update:visible', newVal)
})
</script>
