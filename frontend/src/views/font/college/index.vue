<template>
  <t-layout class="home-container">
    <t-header style="height: 70px">
      <div class="nav">
        <t-head-menu
          style="width: 1000px; display: inline-block; font-size: 18px"
          mode="horizontal"
          active-text-color="#d23333"
          text-color="#000"
        >
          <t-menu-item to="/college">首页</t-menu-item>
          <t-menu-item to="/college/library">课程库</t-menu-item>
          <t-menu-item to="/college/teacher">讲师风采</t-menu-item>
          <t-menu-item to="/college/my">学习中心</t-menu-item>
        </t-head-menu>
      </div>
    </t-header>
    <t-content style="background-color: #f7f8fa">
      <router-view></router-view>
    </t-content>
  </t-layout>
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
  :deep(.t-menu.t-menu--horizontal) {
    border-bottom: none;
  }
}
:deep(.t-card__header) {
  border-bottom: 1px solid #992929;
  border-left: 10px solid #992929;
}
</style>
