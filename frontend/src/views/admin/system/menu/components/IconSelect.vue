<!-- IconSelect.vue -->
<template>
  <t-popup
    v-model:visible="popoverVisible"
    placement="bottom"
    width="400px"
    trigger="click"
  >
    <t-input
      :model-value="modelValue"
      placeholder="点击选择图标"
      class="icon-select-input"
      readonly
    >
      <template #prefix>
        <DynamicIcon v-if="modelValue" :name="modelValue" />
      </template>
    </t-input>

    <!-- 新增图标列表容器 -->
    <template #content>
      <div class="icon-grid">
        <div
          v-for="(icon, index) in icons"
          :key="index"
          class="icon-item"
          @click="selectIcon(icon)"
        >
          <t-icon class="icon">
          <component :is="icon.component" />
        </t-icon>
        <span class="icon-name">{{ icon.name }}</span>
      </div>
    </div>
    </template>
  </t-popup>
</template>

<script setup>
import { ref } from 'vue'
import * as TDesignIcons from 'tdesign-icons-vue-next'

const props = defineProps({
  modelValue: {
    type: String,
    default: ''
  }
})

const emit = defineEmits(['update:modelValue'])
// 名称转换方法
const toKebabCase = (str) => {
  return str.replace(/([a-z])([A-Z])/g, '$1-$2').toLowerCase()
}

// 图标列表数据生成
const icons = Object.entries(TDesignIcons).map(([name, component]) => ({
  name: toKebabCase(name),
  component
}))

// 状态控制
const popoverVisible = ref(false)

// 图标选择处理
const selectIcon = (icon) => {
  emit('update:modelValue', icon.name)
  popoverVisible.value = false
}
</script>

<style scoped>
.icon-grid {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 12px;
  max-height: 400px;
  overflow-y: auto;
}

.icon-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 12px;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.2s;
}

.icon-item:hover {
  background-color: var(--td-brand-color-light);
  transform: scale(1.05);
}

.icon {
  font-size: 24px;
  margin-bottom: 8px;
}

.icon-name {
  font-size: 12px;
  text-align: center;
  word-break: break-all;
}
</style>
