<template>
  <t-alert
    :title="title"
    :theme="theme"
    :closable="false"
    :message="message"
  />
</template>

<script setup>
defineProps({
  title: {
    type: String,
    default: '操作说明'
  },
  message: {
    type: String,
    required: true
  },
  theme: {
    type: String,
    default: 'info'
  }
})
</script>
