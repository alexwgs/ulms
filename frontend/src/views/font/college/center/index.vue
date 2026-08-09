<template>
  <div>
    <t-layout class="center-layout">
      <t-aside width="200px" class="center-aside">
        <t-menu :value="activeMenu" class="center-menu">
          <t-menu-item value="/college/my" to="/college/my">
            <MenuIcon />
            <span>学习中心</span>
          </t-menu-item>
          <t-menu-item value="plan" disabled>
            <CalendarIcon />
            <span>学习计划</span>
          </t-menu-item>
          <t-menu-item value="/college/my/study-record" to="/college/my/study-record">
            <FileIcon />
            <span>学习记录</span>
          </t-menu-item>
          <t-menu-item value="/college/my/point-log" to="/college/my/point-log">
            <TicketIcon />
            <span>金币日志</span>
          </t-menu-item>
          <t-menu-item value="/college/my/hour-log" to="/college/my/hour-log">
            <TimeIcon />
            <span>学时日志</span>
          </t-menu-item>
          <t-menu-item value="/college/my/study-report" to="/college/my/study-report">
            <ChartLineIcon />
            <span>学习报表</span>
          </t-menu-item>
        </t-menu>
      </t-aside>
      <t-content class="center-content">
        <router-view></router-view>
      </t-content>
    </t-layout>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { MenuIcon, CalendarIcon, FileIcon, TicketIcon, TimeIcon, ChartLineIcon } from 'tdesign-icons-vue-next'

const route = useRoute()

// 侧栏高亮跟随当前路由
const activeMenu = computed(() => {
  const p = route.path
  if (p === '/college/my' || p === '/college/my/') return '/college/my'
  if (p.startsWith('/college/my/')) return p
  return '/college/my'
})
</script>

<style lang="less" scoped>
.center-layout {
  gap: 16px;
}

.center-aside {
  background: transparent;
}

.center-menu {
  width: 100% !important;
  min-width: 0 !important;
  box-sizing: border-box;
  background: var(--academy-surface);
  border: 1px solid var(--academy-line);
  border-radius: var(--academy-radius);
  padding: 8px;
  box-shadow: var(--academy-shadow-sm);
  min-height: calc(100vh - 240px);

  :deep(.t-menu__item) {
    border-radius: 8px;
    margin-bottom: 4px;
  }

  :deep(.t-menu__item.t-is-active) {
    color: var(--academy-navy);
    background: rgba(20, 50, 78, 0.08);
  }
}

.center-content {
  padding: 0;
  min-width: 0;
}
</style>
