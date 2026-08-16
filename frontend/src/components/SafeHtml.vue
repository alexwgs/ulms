<template>
  <div v-html="safeHtml" v-bind="$attrs"></div>
</template>

<script setup>
import { computed } from 'vue'
import DOMPurify from 'dompurify'

/**
 * 安全的富文本渲染组件（审计加固 H4）：
 * 统一对 v-html 内容做 DOMPurify 净化，防止存储型 XSS。
 * 用法：<SafeHtml class="article-text" :html="article.content" />
 */
const props = defineProps({
  html: {
    type: String,
    default: ''
  }
})

const safeHtml = computed(() => DOMPurify.sanitize(props.html || ''))
</script>
