<template>
  <i-frame :src="url" />
</template>

<script setup>
import { ref } from 'vue'
import iFrame from '@/components/iFrame/index.vue'

const url = ref(import.meta.env.VITE_API_BASE_URL + '/swagger-ui/index.html')
</script>
