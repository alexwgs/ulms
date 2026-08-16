<template>
  <t-config-provider :global-config="globalConfig">
    <router-view />
  </t-config-provider>
</template>
<script setup>
import { computed } from 'vue'
import { useDictStore, useUiStore } from '@/stores';
import { zhCN, tdesignConfig } from '@/config/tdesign'
import { isLogin } from '@/utils/auth'

const dictStore = useDictStore();
// 免登录阅读：字典接口依赖登录，未登录时跳过（否则 401 会被拦截器踢回登录页）
if (isLogin()) {
  try {
    dictStore.getDictList();
  } catch (e) {
    console.error('字典初始化失败', e)
  }
}

const uiStore = useUiStore();
uiStore.init();

const globalConfig = computed(() => ({
  ...zhCN,
  ...tdesignConfig,
  table: {
    ...tdesignConfig.table,
    size: uiStore.controlSize
  }
}))
</script>

<style scoped></style>
