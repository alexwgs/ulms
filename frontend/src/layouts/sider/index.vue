<template>
  <el-menu
    v-if="currentSubMenus.length > 0"
    :default-active="activeMenuId"
    :collapse="collapsed"
    :collapse-transition="false"
    :unique-opened="true"
    class="el-menu-vertical"
    @collapse="setCollapsed"
  >
    <template v-for="item in currentSubMenus" :key="item.id">
      <el-sub-menu v-if="item.children" :index="item.id.toString()">
        <template #title>
          <el-icon><component :is="item.icon" /></el-icon>
          <span>{{ item.name }}</span>
        </template>
        <!-- 第二级菜单 -->
        <template v-for="i in item.children" :key="i.id">
          <el-sub-menu v-if="i.children" :index="i.id.toString()">
            <template #title>
              <el-icon><component :is="i.icon" /></el-icon>
              <span>{{ i.name }}</span>
            </template>
            <!-- 第三级菜单 -->
            <template v-for="o in i.children" :key="o.id">
              <el-sub-menu v-if="o.children" :index="o.id.toString()">
                <template #title>
                  <span>{{ o.name }}</span>
                </template>
              </el-sub-menu>
              <el-menu-item
                v-else
                :index="o.id.toString()"
                @click="goto(o.path)"
              >
                <el-icon><component :is="o.icon" /></el-icon>
                <span>{{ o.name }}</span>
              </el-menu-item>
            </template>
          </el-sub-menu>
          <el-menu-item v-else :index="i.id.toString()" @click="goto(i.path)">
            <el-icon><component :is="i.icon" /></el-icon>
            <span>{{ i.name }}</span>
          </el-menu-item>
        </template>
      </el-sub-menu>
      <el-menu-item v-else :index="item.id.toString()" @click="goto(item.path)">
        <el-icon><component :is="item.icon" /></el-icon>
        <span>{{ item.name }}</span>
      </el-menu-item>
    </template>
  </el-menu>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useMenuStore, useAppStore } from '@/stores'
import router from '@/router'
import { HomeFilled } from '@element-plus/icons-vue'

const appStore = useAppStore()
const menuStore = useMenuStore()

// 折叠状态
const collapsed = computed({
  get() {
    if (appStore.device === 'desktop') return appStore.menuCollapse
    return false
  },
  set(value) {
    appStore.updateSettings({ menuCollapse: value })
  }
})

// 设置折叠状态
const setCollapsed = (val) => {
  appStore.updateSettings({ menuCollapse: val })
}

// 获取当前选中的一级菜单ID
const activeFirstMenuId = computed(() => {
  return appStore.activeFirstMenuId
})

// 获取当前选中一级菜单的子菜单
const currentSubMenus = computed(() => {
  return menuStore.currentSubMenus(activeFirstMenuId.value)
})

// 动态计算默认激活项
const activeMenuId = computed(() => {
  // 如果有子菜单，默认激活第一个
  if (currentSubMenus.value.length > 0) {
    return currentSubMenus.value[0].id.toString()
  }
  return ''
})

// 菜单跳转
const goto = (path) => {
  if (path && path.substring(0, 1) !== '/') {
    path = '/' + path
  }
  router.push(path || '/')
}
</script>

<style lang="less" scoped>
.el-menu-vertical {
  height: 100%;
  width: 100%;
  border-right: none;
  overflow: hidden;
  background-color: var(--el-bg-color);

  &:not(.el-menu--collapse) {
    width: 100%;
  }

  .el-icon {
    font-size: 18px;
    margin-right: 5px;
  }

  // 修复黑暗模式下菜单折叠时的重叠问题
  &.el-menu--collapse {
    overflow: visible;

    // 确保子菜单在黑暗模式下有正确的背景色
    .el-sub-menu__popper {
      background-color: var(--el-bg-color) !important;
      border-color: var(--el-border-color) !important;

      .el-menu-item {
        background-color: var(--el-bg-color) !important;
      }
    }
  }

  // 修复黑暗模式下菜单hover时的样式
  .el-menu-item:hover,
  .el-sub-menu__title:hover {
    background-color: var(--el-bg-color-overlay) !important;
  }

  // 修复黑暗模式下激活菜单的样式
  .is-active {
    background-color: var(--el-color-primary-light-9) !important;
    color: var(--el-color-primary) !important;
  }
}
</style>
