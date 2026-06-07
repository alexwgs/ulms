<template>
  <div class="content">
    <div class="result-container">
      <t-icon class="result-icon" name="error-circle" size="72px" />
      <div class="result-title">404</div>
      <div class="result-subtitle">页面走丢了！请重新确认访问地址是否正确！</div>
      <div class="operation-row">
        <t-button theme="primary" @click="back">
          没有查找到该页面,返回首页
        </t-button>
      </div>
    </div>
  </div>
</template>

<script>
import { defineComponent } from 'vue';
import { useRouter } from 'vue-router';

export default defineComponent({
  setup() {
    const router = useRouter();
    const back = () => {
      router.push('/');
    };
    return {
      back,
    };
  },
});
</script>

<style scoped lang="less">
.content {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
}

.result-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;
}

.result-icon {
  color: var(--td-error-color);
}

.result-title {
  font-size: 48px;
  font-weight: 700;
  color: var(--td-text-color-primary);
  line-height: 1.2;
}

.result-subtitle {
  font-size: 14px;
  color: var(--td-text-color-secondary);
}

.operation-row {
  margin-top: 16px;
}
</style>
