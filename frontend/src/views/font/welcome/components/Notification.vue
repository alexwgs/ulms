<template>
  <transition name="el-zoom-in-top">
    <el-card
      ref="notificationCardRef"
      class="container"
      v-show="visiable"
      @click.stop
      @mouseleave="visiable = false"
      type="border-card"
    >
      <el-tabs
        v-model="activeName"
        @tab-click="handleClick"
        style="height: 350px"
      >
        <el-tab-pane label="通知" name="first">
          <div style="height: 300px; overflow: auto">
            <el-collapse
              v-model="activeNames"
              @change="handleChange"
              height="100%"
              accordion
            >
              <el-collapse-item
                v-for="item in noticeMessage"
                :name="item.id"
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
              </el-collapse-item>
            </el-collapse>
          </div>
        </el-tab-pane>
        <el-tab-pane label="消息" name="second">
          <el-empty></el-empty>
        </el-tab-pane>
        <el-tab-pane label="待办事项" name="third">
          <el-empty></el-empty>
        </el-tab-pane>
      </el-tabs>
      <div class="manage-box">
        <div class="left-btn" @click="cleanAll">
          <el-icon><CircleCheck /></el-icon> 清空全部
        </div>
        <div class="right-btn" @click="visiable = false">
          <el-icon><CircleClose /></el-icon> 关闭
        </div>
      </div>
    </el-card>
  </transition>
</template>

<script setup>
import { ref, computed } from 'vue'
import { CircleCheck, CircleClose } from '@element-plus/icons-vue'

const notificationCardRef = ref(null)
const activeName = ref('first')
const visiable = ref(false)
const activeNames = ref([])

const noticeMessage = computed(() => {
  try {
    const store = window.__POWERED_BY_QIANKUN__ ? window.$store : null
    return store?.state?.noticeMessage || []
  } catch {
    return []
  }
})

const show = () => {
  visiable.value = !visiable.value
}

const handleChange = (e) => {
  if (e) {
    commitReadNoticeMessage(e)
  }
}

const commitReadNoticeMessage = (id) => {
  try {
    const store = window.__POWERED_BY_QIANKUN__ ? window.$store : null
    if (store) {
      store.commit('readNoticeMessage', id)
    }
  } catch (error) {
    console.error('标记已读失败', error)
  }
}

const cleanAll = () => {
  try {
    const store = window.__POWERED_BY_QIANKUN__ ? window.$store : null
    if (store) {
      store.commit('setNoticeMessage', [])
    }
  } catch (error) {
    console.error('清空消息失败', error)
  }
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
  :deep(.el-card__body) {
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
