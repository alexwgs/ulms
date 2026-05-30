<template>
  <i-frame :src="url" />
</template>

<script setup>
import { ref } from 'vue'
import iFrame from '@/components/iFrame/index.vue' // 确保添加.vue扩展名

const url = ref(import.meta.env.VITE_API_BASE_URL + '/druid')
</script>
