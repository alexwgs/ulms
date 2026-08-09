<template>
  <div class="academy">
    <t-layout class="home-container">
      <t-header class="academy-header" style="height: 64px">
        <div class="academy-brand">
          <span class="academy-logo">A6</span>
          <span class="academy-title">A6魔法学院</span>
          <span class="academy-sub">ACADEMY</span>
        </div>
        <div class="academy-nav">
          <t-head-menu :value="activeNav">
            <t-menu-item value="/college" to="/college">首页</t-menu-item>
            <t-menu-item value="/college/library" to="/college/library">课程库</t-menu-item>
            <t-menu-item value="/college/teacher" to="/college/teacher">讲师风采</t-menu-item>
            <t-menu-item value="/college/my" to="/college/my">学习中心</t-menu-item>
          </t-head-menu>
        </div>
      </t-header>
      <t-content style="background-color: transparent">
        <div class="academy-main">
          <router-view v-slot="{ Component }">
            <transition name="academy-fade" mode="out-in">
              <component :is="Component" />
            </transition>
          </router-view>
        </div>
      </t-content>
    </t-layout>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import './college-theme.css'

const route = useRoute()

// 根据当前路由高亮顶部导航（课程/评价等子页面归属“首页”）
const activeNav = computed(() => {
  const p = route.path
  if (p === '/college' || p.startsWith('/college/course') || p.startsWith('/college/evaluate')) {
    return '/college'
  }
  if (p.startsWith('/college/library')) return '/college/library'
  if (p.startsWith('/college/teacher')) return '/college/teacher'
  if (
    p.startsWith('/college/my') ||
    p.startsWith('/college/center') ||
    p.startsWith('/college/user') ||
    p.startsWith('/college/view-course-study')
  ) {
    return '/college/my'
  }
  return ''
})
</script>

<style lang="less" scoped>
.home-container {
  min-height: 100vh;
}

.academy-header {
  display: flex;
  align-items: center;
}

.academy-nav {
  flex: 1;
  display: flex;
  justify-content: center;
  padding-right: 200px;
}
</style>
