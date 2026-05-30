<template>
  <div class="fixBox">
    <div id="mesBox" class="anim">
      <div class="item" v-for="(item, index) in options" :key="index">
        {{ item.name }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'

const options = ref([
  { name: '"倾"你我之力，"建"文明平台；' },
  { name: '"乘"自由之声，"诉"你我衷言。' },
  { name: '' }
])

const marginTop = ref(0)
let timer = null

const scroll = () => {
  const lineHeight = 24
  const lineCount = options.value.length
  if (Math.abs(marginTop.value) >= lineHeight * lineCount - lineHeight * 2) {
    marginTop.value = 0
    options.value = [
      { name: '"倾"你我之力，"建"文明平台；' },
      { name: '"乘"自由之声，"诉"你我衷言。' },
      { name: '' }
    ]
  } else {
    marginTop.value -= lineHeight
  }
  const mesBox = document.getElementById('mesBox')
  if (mesBox) {
    mesBox.style.top = marginTop.value + 'px'
  }
}

onMounted(() => {
  timer = setInterval(scroll, 2000)
})

onBeforeUnmount(() => {
  if (timer) {
    clearInterval(timer)
  }
})
</script>

<style scoped>
.fixBox {
  width: 100%;
  height: 50px;
  overflow: hidden;
  position: relative;
  #mesBox {
    position: absolute;
    top: 0;
    width: 400px;
    .item {
      height: 24px;
      line-height: 24px;
    }
  }
  .anim {
    transition: all 1s;
  }
}
</style>
