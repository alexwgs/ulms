<template>
  <el-container class="home-container">
    <el-header style="height: 70px">
      <div class="nav">
        <el-menu
          style="width: 1000px; display: inline-block; font-size: 18px"
          class="el-menu-demo"
          mode="horizontal"
          active-text-color="#d23333"
          text-color="#000"
          router
        >
          <el-menu-item index="/college">首页</el-menu-item>
          <el-menu-item index="/college/library">课程库</el-menu-item>
          <el-menu-item index="/college/teacher">讲师风采</el-menu-item>
          <el-menu-item index="/college/my">学习中心</el-menu-item>
        </el-menu>
      </div>
    </el-header>
    <el-main style="background-color: #f7f8fa">
      <router-view></router-view>
    </el-main>
  </el-container>
</template>

<script setup>
// Composition API - 此组件为布局组件，无需额外逻辑
</script>

<style lang="less" scoped>
.home-container {
  height: 100vh;
  min-height: 600px;
  min-width: 1200px;
  margin: 0;
}
.nav {
  width: 100%;
  text-align: center;
  border-bottom: 1px solid #9b2b23;
  :deep(.el-menu.el-menu--horizontal) {
    border-bottom: none;
  }
}
:deep(.el-card__header) {
  border-bottom: 1px solid #992929;
  border-left: 10px solid #992929;
}
</style>
