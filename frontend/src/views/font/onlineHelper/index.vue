<template>
  <i-frame :src="url" />
</template>

<script setup>
import { ref } from 'vue'
import iFrame from '@/components/iFrame/index.vue' // 确保添加.vue扩展名

const url = ref('http://10.47.24.45/auxs/kf_helper/search/search1.jsp')
</script>
