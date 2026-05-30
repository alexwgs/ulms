<template>
  <div style="padding-left: calc((100vw - 1240px) / 2); max-width: 1200px">
    <el-container>
      <el-aside width="200px">
        <el-menu
          default-active="2"
          class="el-menu-vertical-demo"
          router
          @open="handleOpen"
          @close="handleClose"
          style="min-height: calc(100vh - 110px)"
        >
          <el-menu-item index="/college/my">
            <el-icon><Menu /></el-icon>
            <span>学习中心</span>
          </el-menu-item>
          <el-menu-item index="2" disabled>
            <el-icon><Menu /></el-icon>
            <span>学习计划</span>
          </el-menu-item>
          <el-menu-item index="/college/my/study-record">
            <el-icon><Document /></el-icon>
            <span>学习记录</span>
          </el-menu-item>
          <el-menu-item index="/college/my/point-log">
            <el-icon><Ticket /></el-icon>
            <span>金币日志</span>
          </el-menu-item>
          <el-menu-item index="/college/my/hour-log">
            <el-icon><Clock /></el-icon>
            <span>学时日志</span>
          </el-menu-item>
          <el-menu-item index="/college/my/study-report">
            <el-icon><TrendCharts /></el-icon>
            <span>学习报表</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main style="padding: 0 0 0 10px">
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import {
  Menu,
  Document,
  Ticket,
  Clock,
  TrendCharts
} from '@element-plus/icons-vue'

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
