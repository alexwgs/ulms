
<template>
  <el-container class="layout">
    <div v-if="navbar" class="layout-navbar">
      <AppHead />
    </div>
    <el-container>
      <el-aside 
        v-if="shouldShowSidebar" 
        v-show="!hideMenu" 
        class="layout-sider" 
        :width="menuWidth" 
        :style="{ paddingTop: navbar ? '' : '60px' }"
      >
        <div class="menu-wrapper">
          <AppSider @collapse="setCollapsed" />
        </div>
      </el-aside>
      <el-container class="layout-content" :style="paddingStyle">
        <el-main>
          <AppMain />
        </el-main>
        <!-- <el-footer height="40px">
          <AppFooter />
        </el-footer> -->
      </el-container>
    </el-container>
  </el-container>
</template>

<script setup>
// import AppFooter from '@/layouts/footer/index.vue'
import AppSider from '@/layouts/sider/index.vue'
import AppMain from '@/layouts/page/index.vue'
import AppHead from '@/layouts/head/index.vue'
import { useAppStore } from '@/stores';

import { computed, ref, onMounted } from 'vue'

const isInit = ref(false);
const appStore = useAppStore();
const navbarHeight = `60px`;

const navbar = computed(() => appStore.navbar);
const hideMenu = computed(() => appStore.hideMenu);
const footer = computed(() => appStore.footer);
const shouldShowSidebar = computed(() => appStore.shouldShowSidebar);

const menuWidth = computed(() => {
  return appStore.menuCollapse ? '48px' : `${appStore.menuWidth}px`;
});

const collapsed = computed(() => {
  return appStore.menuCollapse;
});

const paddingStyle = computed(() => {
  const paddingLeft =
    shouldShowSidebar.value && !hideMenu.value
      ? { paddingLeft: menuWidth.value }
      : {};
  const paddingTop = navbar.value ? { paddingTop: navbarHeight } : {};
  return { ...paddingLeft, ...paddingTop };
});

const setCollapsed = (val) => {
  if (!isInit.value) return;
  appStore.updateSettings({ menuCollapse: val });
};

onMounted(() => {
  isInit.value = true;
});
</script>

<style lang="less" scoped>
.layout {
  width: 100%;
  height: 100vh;
}

.layout-navbar {
  position: fixed;
  top: 0;
  left: 0;
  z-index: 100;
  width: 100%;
  height: 59px;
}

.layout-sider {
  position: fixed;
  top: 59px;
  left: 0;
  z-index: 99;
  height: calc(100vh - 59px);
  transition: all 0.2s cubic-bezier(0.34, 0.69, 0.1, 1);
  border-right: 1px solid var(--el-border-color);

  > .el-aside__inner {
    height: 100%;
    overflow-y: hidden;
  }
}

.menu-wrapper {
  height: 100%;
  overflow: auto;
  overflow-x: hidden;

  &::-webkit-scrollbar {
    width: 6px;
  }

  &::-webkit-scrollbar-thumb {
    border-radius: 3px;
    background-color: var(--el-text-color-placeholder);
  }

  &::-webkit-scrollbar-thumb:hover {
    background-color: var(--el-text-color-secondary);
  }
}

.layout-content {
  min-height: 100vh;
  width: 100%;
  overflow-y: auto;
  background-color: var(--el-bg-color-page);
  transition: padding 0.2s cubic-bezier(0.34, 0.69, 0.1, 1);
}

.el-main {
  padding: 20px;
}

.el-footer {
  height: auto;
  padding: 16px 20px;
}
</style>