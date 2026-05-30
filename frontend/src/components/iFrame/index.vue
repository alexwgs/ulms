<template>
  <div
    v-loading="loading"
    :style="'height:' + height"
    style="margin: -10px -20px 0 -20px"
  >
    <iframe
      :src="src"
      frameborder="no"
      style="width: 100%; height: 100%"
      scrolling="auto"
    />
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'

const props = defineProps({
  src: {
    type: String,
    required: true
  }
})

const loading = ref(true)
const height = ref(document.documentElement.clientHeight - 110 + 'px;')

// 更新iframe高度的函数
const updateHeight = () => {
  height.value = document.documentElement.clientHeight - 110 + 'px;'
}

onMounted(() => {
  setTimeout(() => {
    loading.value = false
  }, 300)

  // 监听窗口大小变化
  window.addEventListener('resize', updateHeight)
})

onBeforeUnmount(() => {
  // 组件卸载前移除事件监听
  window.removeEventListener('resize', updateHeight)
})
</script>
