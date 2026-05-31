<template>
  <div
    class="content-container"
    :style="{
      backgroundColor: 'var(--td-bg-color-page)',
      padding: '0 10px 0 10px'
    }"
  >
    <t-card class="box-card" :bordered="false">
      <template #header>
        <div class="card-header">
          <span>{{ breadList.slice(-1)[0] }}</span>
        </div>
      </template>
      <router-view />
    </t-card>
  </div>
</template>

<script setup>
import Breadcrumb from '../breadcrumb/index.vue'
import { useRouter } from 'vue-router'
import { ref, watch } from 'vue'

const router = useRouter()
const breadList = ref([''])

watch(
  router.currentRoute,
  (newValue) => {
    breadList.value = []
    newValue.matched.forEach((item) => {
      if (item.meta.title) {
        breadList.value.push(item.meta.title)
      }
    })
  },
  { immediate: true }
)
</script>

<style scoped lang="less">
.content-container {
  height: 100%;
  margin: 0;
}

.box-card {
  width: 100%;
  border: none;

  :deep(.t-card__header) {
    padding: 12px 20px;
    border-bottom: 1px solid var(--td-border-level-1-color);
  }
}

.card-header {
  font-weight: bold;
  font-size: 16px;
}
</style>
