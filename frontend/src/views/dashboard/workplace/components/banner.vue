<template>
  <a-col class="banner">
    <a-col :span="4">
      <a-typography-title :heading="5" style="margin-top: 0">
        欢迎！ {{ userInfo.name }}
      </a-typography-title>
    </a-col>
    <a-divider class="panel-border" />
  </a-col>
</template>

<script setup>
  import { computed } from 'vue';
  import { useUserStore } from '@/stores'

  const userStore = useUserStore();
  const userInfo = computed(() => {
    return {
      name: userStore.ploName,
    };
  });
</script>

<style scoped lang="less">
  .banner {
    width: 100%;
    padding: 0 20px 0 20px;
-color: var(--color-bg-2);
    border-radius: 4px 4px 0 0;
  }

  :deep(.arco-icon-home) {
    margin-right: 6px;
  }
</style>
