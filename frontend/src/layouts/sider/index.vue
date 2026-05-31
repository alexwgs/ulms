<template>
  <t-menu
    v-if="currentSubMenus.length > 0"
    :value="activeMenuId"
    :collapsed="collapsed"
    :expand-mutex="true"
    theme="light"
    class="menu-vertical"
  >
    <template v-for="item in currentSubMenus" :key="item.id">
      <t-submenu v-if="item.children" :value="item.id.toString()">
        <template #title>
          <DynamicIcon :name="mapIcon(item.icon)" />
          <span>{{ item.name }}</span>
        </template>
        <!-- 第二级菜单 -->
        <template v-for="i in item.children" :key="i.id">
          <t-submenu v-if="i.children" :value="i.id.toString()">
            <template #title>
              <DynamicIcon :name="mapIcon(i.icon)" />
              <span>{{ i.name }}</span>
            </template>
            <!-- 第三级菜单 -->
            <template v-for="o in i.children" :key="o.id">
              <t-submenu v-if="o.children" :value="o.id.toString()">
                <template #title>
                  <span>{{ o.name }}</span>
                </template>
              </t-submenu>
              <t-menu-item
                v-else
                :value="o.id.toString()"
                @click="goto(o.path)"
              >
                <DynamicIcon :name="mapIcon(o.icon)" />
                <span>{{ o.name }}</span>
              </t-menu-item>
            </template>
          </t-submenu>
          <t-menu-item v-else :value="i.id.toString()" @click="goto(i.path)">
            <DynamicIcon :name="mapIcon(i.icon)" />
            <span>{{ i.name }}</span>
          </t-menu-item>
        </template>
      </t-submenu>
      <t-menu-item v-else :value="item.id.toString()" @click="goto(item.path)">
        <DynamicIcon :name="mapIcon(item.icon)" />
        <span>{{ item.name }}</span>
      </t-menu-item>
    </template>
  </t-menu>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useMenuStore, useAppStore } from '@/stores'
import router from '@/router'
import { mapIcon } from '@/utils/iconMap'

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
.menu-vertical {
  height: 100%;
  width: 100%;
  border-right: none;
  overflow: hidden;
-color: var(--td-bg-color-container);

  &:not(.t-is-collapsed) {
    width: 100%;
  }

  .t-icon {
    font-size: 18px;
    margin-right: 5px;
  }

  .t-menu__item:hover,
  .t-submenu__title:hover {
-color: var(--td-bg-color-container-hover) !important;
  }

  .t-is-active {
-color: var(--td-brand-color-light) !important;
    color: var(--td-brand-color) !important;
  }
}
</style>
