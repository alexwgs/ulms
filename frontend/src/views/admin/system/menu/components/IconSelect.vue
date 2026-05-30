<!-- IconSelect.vue -->
<template>
  <el-popover
    v-model:visible="popoverVisible"
    placement="bottom"
    width="400px"
    trigger="click"
  >
    <template #reference>
      <el-input
        :model-value="modelValue"
        placeholder="点击选择图标"
        class="icon-select-input"
        readonly
      >
        <template #prefix>
          <el-icon v-if="modelValue">
            <component :is="modelValue" />
          </el-icon>
        </template>
      </el-input>
    </template>

    <!-- 新增图标列表容器 -->
    <div class="icon-grid">
      <div
        v-for="(icon, index) in icons"
        :key="index"
        class="icon-item"
        @click="selectIcon(icon)"
      >
        <el-icon class="icon">
          <component :is="icon.component" />
        </el-icon>
        <span class="icon-name">{{ icon.name }}</span>
      </div>
    </div>
  </el-popover>
</template>

<script setup>
import { ref, computed } from 'vue'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

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
const icons = Object.entries(ElementPlusIconsVue).map(([name, component]) => ({
  name: toKebabCase(name),
  component
}))

// 状态控制
const popoverVisible = ref(false)

// 当前选中图标
const selectedIconComponent = computed(() => {
  const iconName = props.modelValue?.replace(/(-\w)/g, (m) =>
    m[1].toUpperCase()
  )
  return ElementPlusIconsVue[iconName]
})

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
  background-color: var(--el-color-primary-light-9);
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
