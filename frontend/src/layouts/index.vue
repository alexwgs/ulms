
<template>
  <t-layout class="layout">
    <div v-if="navbar" class="layout-navbar">
      <AppHead />
    </div>
    <t-layout>
      <t-aside
        v-if="shouldShowSidebar"
        v-show="!hideMenu"
        class="layout-sider"
        :class="{ 'is-collapsed': appStore.menuCollapse }"
        :width="menuWidth"
        :style="{ paddingTop: navbar ? '' : '60px' }"
      >
        <div class="menu-wrapper">
          <AppSider @collapse="setCollapsed" />
        </div>
        <!-- 侧边栏折叠按钮 -->
        <div class="sidebar-collapse-btn" @click="toggleCollapse">
          <t-icon :name="appStore.menuCollapse ? 'chevron-right' : 'chevron-left'" size="16px" />
        </div>
      </t-aside>
      <t-layout class="layout-content" :style="paddingStyle">
        <t-content>
          <AppMain />
        </t-content>
      </t-layout>
    </t-layout>
  </t-layout>
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
const navbarHeight = `59px`;

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

const toggleCollapse = () => {
  appStore.updateSettings({ menuCollapse: !appStore.menuCollapse });
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
  background-color: var(--td-bg-color-container);
  border-bottom: 1px solid var(--td-border-level-1-color);
}

.layout-sider {
  position: fixed;
  top: 59px;
  left: 0;
  z-index: 99;
  height: calc(100vh - 59px);
  transition: all 0.2s cubic-bezier(0.34, 0.69, 0.1, 1);
  border-right: 1px solid var(--td-border-level-1-color);
  // 收起状态：宽度由 t-aside 的 :width 属性控制
}

// 收起按钮
.sidebar-collapse-btn {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  border-top: 1px solid var(--td-border-level-1-color);
  transition: all 0.2s cubic-bezier(0.34, 0.69, 0.1, 1);
  color: var(--td-text-color-secondary);
  z-index: 10;

  &:hover {
    color: var(--td-brand-color);
  }

  .layout-sider.is-collapsed & {
    // 收起时按钮内容自动居中
  }
}

.menu-wrapper {
  height: calc(100% - 40px); // 为底部折叠按钮留出空间
  overflow: hidden;
}

.layout-content {
  height: 100vh;
  width: 100%;
  background-color: var(--td-bg-color-page);
  transition: padding 0.2s cubic-bezier(0.34, 0.69, 0.1, 1);
}

.t-content {
  height: 100%;
  box-sizing: border-box;
  padding: 20px;
}
</style>
