<template>
  <div class="notify-container">
    <div class="notify-card">
      <div class="notify-header">
        <div class="notify-icon">
          <el-icon :size="28"><WarningFilled /></el-icon>
        </div>
        <div class="notify-title-wrap">
          <h2 class="notify-title">{{ notifyData.title || '系统通知' }}</h2>
          <span class="notify-meta">
            {{ notifyData.module }} / {{ notifyData.type }}
            <template v-if="notifyData.timestamp"> / {{ notifyData.timestamp }}</template>
          </span>
        </div>
      </div>
      <div class="notify-body">
        <template v-if="notifyData.content">
          <p v-if="isString(notifyData.content)">{{ notifyData.content }}</p>
          <pre v-else>{{ JSON.stringify(notifyData.content, null, 2) }}</pre>
        </template>
        <p v-else class="empty-content">（无附加内容）</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { WarningFilled } from '@element-plus/icons-vue'

const notifyData = ref({})

function isString(v) {
  return typeof v === 'string'
}

onMounted(() => {
  try {
    const raw = localStorage.getItem('ulms_push_notify')
    if (raw) {
      notifyData.value = JSON.parse(raw)
      // 读完即清，避免刷新后残留
      localStorage.removeItem('ulms_push_notify')
    }
  } catch (e) {
    console.error('读取推送通知数据失败:', e)
  }
})
</script>

<style lang="less" scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.notify-container {
  width: 100%;
  height: 100vh;
  background: linear-gradient(135deg, #1a2332 0%, #2b4b6b 100%);
  display: flex;
  //align-items: center;
  //justify-content: center;
  padding: 16px;
  overflow: hidden;
}

.notify-card {
  width: 460px;
  max-width: 460px;
  max-height: calc(100vh - 32px);
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.25);
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.notify-header {
  flex-shrink: 0;
  display: flex;
  align-items: center;
  gap: 14px;
  padding: 16px 20px 14px;
  background: linear-gradient(135deg, #f0f5ff, #e8f0fe);
  border-bottom: 1px solid #e0e8f2;
}

.notify-icon {
  flex-shrink: 0;
  color: #e6a23c;
}

.notify-title-wrap {
  min-width: 0;
}

.notify-title {
  font-size: 17px;
  font-weight: 600;
  color: #1a2332;
  line-height: 1.3;
  word-break: break-all;
}

.notify-meta {
  display: block;
  margin-top: 4px;
  font-size: 12px;
  color: #909399;
}

.notify-body {
  flex: 1;
  overflow-y: auto;
  padding: 16px 20px 20px;
  font-size: 14px;
  color: #303133;
  line-height: 1.7;
  word-break: break-all;

  p {
    margin: 0;
  }

  pre {
    background: #f5f7fa;
    border-radius: 6px;
    padding: 12px;
    font-size: 13px;
    line-height: 1.6;
    white-space: pre-wrap;
    word-break: break-all;
    margin: 0;
  }

  .empty-content {
    color: #c0c4cc;
    font-style: italic;
  }
}
</style>

<style lang="less">
/* 覆盖主应用最小宽度限制，适配弹窗大小 */
html, body {
  overflow: hidden;
}
#app {
  min-width: auto !important;
}
</style>
