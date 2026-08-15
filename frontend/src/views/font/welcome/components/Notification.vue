<template>
  <transition name="zoom-in-top">
    <t-card
      ref="notificationCardRef"
      class="container"
      v-show="visiable"
      @click.stop
      @mouseleave="visiable = false"
    >
      <t-tabs
        v-model="activeName"
        @change="handleClick"
        style="height: 350px"
      >
        <t-tab-panel label="通知" value="first">
          <div style="height: 300px; overflow: auto">
            <t-collapse
              v-model="activeNames"
              @change="handleChange"
              height="100%"
              expand-mutex
            >
              <t-collapse-panel
                v-for="item in noticeMessage"
                :value="item.id"
                :title="
                  (item.ifRead ? '[已读]' : '[未读]') +
                  '[' +
                  item.dataTime +
                  ']' +
                  item.system
                "
                :key="item.id"
                :class="item.ifRead ? 'read' : 'unread'"
              >
                <div>{{ item.content }}</div>
              </t-collapse-panel>
            </t-collapse>
          </div>
        </t-tab-panel>
        <t-tab-panel label="消息" value="second">
          <t-empty></t-empty>
        </t-tab-panel>
        <t-tab-panel label="待办事项" value="third">
          <t-empty></t-empty>
        </t-tab-panel>
      </t-tabs>
      <div class="manage-box">
        <div class="left-btn" @click="cleanAll">
          <CheckCircleFilledIcon /> 清空全部
        </div>
        <div class="right-btn" @click="visiable = false">
          <CloseCircleFilledIcon /> 关闭
        </div>
      </div>
    </t-card>
  </transition>
</template>

<script setup>
import { ref, computed } from 'vue'
import { CheckCircleFilledIcon, CloseCircleFilledIcon } from 'tdesign-icons-vue-next'
import { useOhtStore } from '@/stores'

const notificationCardRef = ref(null)
const activeName = ref('first')
const visiable = ref(false)
const activeNames = ref([])

const ohtStore = useOhtStore()
const noticeMessage = computed(() => ohtStore.noticeMessage || [])

const show = () => {
  visiable.value = !visiable.value
}

const handleChange = (e) => {
  if (e) {
    commitReadNoticeMessage(e)
  }
}

const commitReadNoticeMessage = (id) => {
  ohtStore.readNoticeMessage(id)
}

const cleanAll = () => {
  ohtStore.setNoticeMessage([])
}

const handleClick = (tab) => {
}

defineExpose({
  show
})
</script>

<style lang="less" scoped>
.container {
  margin-top: -10px;
  padding: 0;
  width: 350px;
  height: 415px;
  position: absolute;
  right: 5%;
  :deep(.t-card__body) {
    padding-top: 0;
  }
  .manage-box {
    width: 100%;
    border-top: 1px solid rgb(184, 182, 182);
    height: 40px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    cursor: pointer;
    .left-btn {
      margin-left: 10px;
      display: flex;
      align-items: center;
    }
    .right-btn {
      margin-right: 10px;
      display: flex;
      align-items: center;
    }
  }
}
.read {
  opacity: 0.6;
}
.unread {
  font-weight: bold;
}
</style>
