<template>
  <t-config-provider :global-config="globalConfig">
    <router-view />
  </t-config-provider>
</template>
<script setup>
import { computed } from 'vue'
import { useDictStore, useUiStore } from '@/stores';
import { zhCN, tdesignConfig } from '@/config/tdesign'

const dictStore = useDictStore();
try {
  dictStore.getDictList();
} catch (e) {
  console.error('字典初始化失败', e)
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
