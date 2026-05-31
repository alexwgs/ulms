<template>
  <t-config-provider :global-config="globalConfig">
    <router-view />
  </t-config-provider>
</template>
<script setup>
import { useDictStore } from '@/stores';
import { zhCN, tdesignConfig } from '@/config/tdesign'

const dictStore = useDictStore();
dictStore.getDictList();

const globalConfig = {
  ...zhCN,
  ...tdesignConfig
}
</script>

<style scoped></style>
