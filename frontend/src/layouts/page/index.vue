<template>
  <div
    class="content-container"
    :style="{
      backgroundColor: 'var(--td-bg-color-page)',
      padding: 'var(--td-comp-paddingTB-xl) var(--td-comp-paddingLR-xl)'
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
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  margin: 0;
}

.box-card {
  flex: 1;
  min-height: 0;
  width: 100%;
  border: none;
  display: flex;
  flex-direction: column;

  :deep(.t-card__header) {
    padding: 12px 20px;
    border-bottom: 1px solid var(--td-border-level-1-color);
  }

  :deep(.t-card__body) {
    flex: 1;
    min-height: 0;
    overflow-y: auto;
  }
}

.card-header {
  font-weight: bold;
  font-size: 16px;
}
</style>
