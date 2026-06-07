<template>
  <t-menu
    v-if="currentSubMenus.length > 0"
    :value="activeMenuId"
    v-model:expanded="expandedIds"
    :collapsed="collapsed"
    :expand-mutex="true"
    theme="light"
    class="menu-vertical"
    @expand="handleExpand"
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
import { ref, computed, watch } from 'vue'
import { useRoute } from 'vue-router'
import { useMenuStore, useAppStore } from '@/stores'
import router from '@/router'
import { mapIcon } from '@/utils/iconMap'

const appStore = useAppStore()
const menuStore = useMenuStore()
const route = useRoute()

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

// 获取当前选中的一级菜单ID
const activeFirstMenuId = computed(() => {
  return appStore.activeFirstMenuId
})

// 获取当前选中一级菜单的子菜单
const currentSubMenus = computed(() => {
  return menuStore.currentSubMenus(activeFirstMenuId.value)
})

// 规范化菜单路径
const normalizePath = (p) => {
  if (!p) return ''
  return p.startsWith('/') ? p : '/' + p
}

// 递归查找匹配当前路由的菜单项ID
const findActiveMenuId = (menus, currentPath) => {
  if (!menus?.length || !currentPath) return ''
  for (const menu of menus) {
    if (normalizePath(menu.path) === currentPath) return menu.id.toString()
    if (menu.children?.length) {
      const found = findActiveMenuId(menu.children, currentPath)
      if (found) return found
    }
  }
  return ''
}

// 收集从根到目标路径的所有祖先菜单 ID（用于展开父级）
const collectExpandedIds = (menus, currentPath) => {
  if (!menus?.length || !currentPath) return []
  for (const menu of menus) {
    if (normalizePath(menu.path) === currentPath) return []
    if (menu.children?.length) {
      // 检查目标是否在后代中
      const found = findActiveMenuId(menu.children, currentPath)
      if (found) return [menu.id.toString(), ...collectExpandedIds(menu.children, currentPath)]
    }
  }
  return []
}

// 当前路由匹配的菜单项ID
const activeMenuId = computed(() => {
  return findActiveMenuId(currentSubMenus.value, route.path) || ''
})

// 展开的父级菜单ID列表：使用本地 ref，路由变化时自动展开祖先，用户手动点击也生效
const expandedIds = ref([])

// 路由变化时，自动展开当前路径的所有祖先菜单
watch(() => route.path, (newPath) => {
  const ids = collectExpandedIds(currentSubMenus.value, newPath)
  // 将新路径的祖先合并到已展开列表（保留用户手动展开的其他菜单）
  const merged = new Set([...expandedIds.value, ...ids])
  expandedIds.value = [...merged]
}, { immediate: true })

// 用户手动点击展开/收起子菜单
const handleExpand = (value) => {
  expandedIds.value = value
}

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
  background-color: var(--td-bg-color-container);

  // 覆盖 TDesign t-menu--scroll 强制设置的 overflow-y: auto
  // 菜单内容未溢出容器，不需要滚动条
  :deep(.t-menu--scroll) {
    overflow-y: hidden !important;
  }

  &:not(.t-is-collapsed) {
    width: 100%;
  }

  .t-icon {
    font-size: 18px;
    margin-right: 5px;
  }

  .t-menu__item:hover,
  .t-submenu__title:hover {
    background-color: var(--td-bg-color-container-hover) !important;
  }

  .t-is-active {
    background-color: var(--td-brand-color-light) !important;
    color: var(--td-brand-color) !important;
  }
}
</style>
