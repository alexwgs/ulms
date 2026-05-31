<template>
  <t-layout class="home-container">
    <t-header>
      <div class="logo">
        <img src="@/assets/img/edu/studyLogo.png" />
      </div>
      <div class="nav">
        <t-head-menu style="width: 800px" mode="horizontal"-color="#0c717d"
          active-text-color="#fff" text-color="#fff" router>
          <!-- 为 el-sub-menu 添加唯一的 index -->
          <t-submenu index="1" style="font-size: 18px">
            <template #title>学霸榜 ></template>
            <!-- 移除 router 属性，使用 index 指定路由路径 -->
            <t-menu-item index="/edu/rankBoard">个人榜</t-menu-item>
            <t-menu-item index="/edu/RankBoard2">团队榜</t-menu-item>
          </t-submenu>

          <!-- 其他菜单项同理 -->
          <t-menu-item index="/edu/brush">刷题入口 ></t-menu-item>
          <t-menu-item index="/edu/myexam">我的考试 ></t-menu-item>
          <t-menu-item index="/edu/myquestion">我的题库 ></t-menu-item>
          <t-menu-item index="/edu/competition">知识竞赛 ></t-menu-item>
        </t-head-menu>
      </div>
      <div class="left-sticks"></div>
      <div class="right-sticks"></div>
    </t-header>
    <t-content class="main">
      <router-view></router-view>
    </t-content>
    <div class="bottomBg"></div>
  </t-layout>
</template>

<script setup></script>

<style lang="less" scoped>
.home-container {
-color: #0c717d;
  color: rgb(255, 255, 255);
  height: 100vh;
  min-height: 600px;
  margin: 0;
  z-index: -999;
}

.logo {
  padding: 10px;
  width: 260px;
  display: inline-block;
}

.nav {
  padding-top: 10px;
  width: 610px;
  display: inline-block;
  position: fixed;
  right: 100px;
}

.left-sticks {
  position: absolute;
-image: url(../../../assets/img/edu/left-sticks.png);
-size: 100% 100%;
  left: 10%;
  top: 40px;
  width: 200px;
  height: 100px;
}

.right-sticks {
  position: absolute;
-image: url(../../../assets/img/edu/right-sticks.png);
-size: 100% 100%;
  right: 0;
  top: 30px;
  width: 250px;
  height: 180px;
}

.bottomBg {
  position: absolute;
  width: 100%;
  bottom: 0px;
-image: url(../../../assets/img/edu/bottom.png);
-size: 100% 100%;
  height: 200px;
}

.main {
  margin: 20px 20px 30px 20px;
  padding: 30px 40px 30px 40px;
  border-radius: 12px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  z-index: 1;
}

.t-menu.t-menu--horizontal {
  border-bottom: solid 0;
}

.t-menu--horizontal>.t-menu-item {
  font-size: 18px;
}

:deep(.t-sub-menu > .t-sub-menu__title) {
  font-size: 18px;
}
</style>
