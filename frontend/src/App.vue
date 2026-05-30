<template>
  <el-config-provider :locale="locale" :dialog="dialogConfig" :message="messageConfig">
    <router-view />
  </el-config-provider>
</template>
<script setup>
import { useDictStore } from '@/stores';
import { elementPlusConfig } from '@/config/element-plus'

const dictStore = useDictStore();
dictStore.getDictList();
// 解构配置
const { locale, dialog: dialogConfig, message: messageConfig } = elementPlusConfig
</script>

<style scoped></style>
