<template>
  <div class="message-box">
    <div class="message-box-empty">
      <DynamicIcon name="notification" size="40px" style="color: var(--td-text-color-placeholder)" />
      <p>暂无新消息</p>
    </div>
  </div>
</template>

<script setup>
</script>

<style lang="less" scoped>
.message-box {
  min-height: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.message-box-empty {
  text-align: center;
  color: var(--td-text-color-secondary);
  p {
    margin-top: 8px;
    font-size: 14px;
  }
}
</style>
