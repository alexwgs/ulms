<template>
  <div style="padding-left: calc((100vw - 1240px) / 2); max-width: 1200px">
    <t-layout>
      <t-aside width="200px">
        <t-menu
          default-active="2"
          @open="handleOpen"
          @close="handleClose"
          style="min-height: calc(100vh - 110px)"
        >
          <t-menu-item to="/college/my">
            <MenuIcon />
            <span>学习中心</span>
          </t-menu-item>
          <t-menu-item value="2" disabled>
            <MenuIcon />
            <span>学习计划</span>
          </t-menu-item>
          <t-menu-item to="/college/my/study-record">
            <FileIcon />
            <span>学习记录</span>
          </t-menu-item>
          <t-menu-item to="/college/my/point-log">
            <TicketIcon />
            <span>金币日志</span>
          </t-menu-item>
          <t-menu-item to="/college/my/hour-log">
            <TimeIcon />
            <span>学时日志</span>
          </t-menu-item>
          <t-menu-item to="/college/my/study-report">
            <ChartLineIcon />
            <span>学习报表</span>
          </t-menu-item>
        </t-menu>
      </t-aside>
      <t-content style="padding: 0 0 0 10px">
        <router-view></router-view>
      </t-content>
    </t-layout>
  </div>
</template>

<script setup>
import { MenuIcon, FileIcon, TicketIcon, TimeIcon, ChartLineIcon } from 'tdesign-icons-vue-next'

const handleOpen = (key, keyPath) => {
}

const handleClose = (key, keyPath) => {
}
</script>

<style lang="less" scoped>
//   .box-card {
//     min-height: calc(100vh - 120px);
//   }
</style>
