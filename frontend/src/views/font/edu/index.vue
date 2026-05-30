<template>
  <el-container class="home-container">
    <el-header>
      <div class="logo">
        <img src="@/assets/img/edu/studyLogo.png" />
      </div>
      <div class="nav">
        <el-menu style="width: 800px" class="el-menu-demo" mode="horizontal" background-color="#0c717d"
          active-text-color="#fff" text-color="#fff" router>
          <!-- 为 el-sub-menu 添加唯一的 index -->
          <el-sub-menu index="1" style="font-size: 18px">
            <template #title>学霸榜 ></template>
            <!-- 移除 router 属性，使用 index 指定路由路径 -->
            <el-menu-item index="/edu/rankBoard">个人榜</el-menu-item>
            <el-menu-item index="/edu/RankBoard2">团队榜</el-menu-item>
          </el-sub-menu>

          <!-- 其他菜单项同理 -->
          <el-menu-item index="/edu/brush">刷题入口 ></el-menu-item>
          <el-menu-item index="/edu/myexam">我的考试 ></el-menu-item>
          <el-menu-item index="/edu/myquestion">我的题库 ></el-menu-item>
          <el-menu-item index="/edu/competition">知识竞赛 ></el-menu-item>
        </el-menu>
      </div>
      <div class="left-sticks"></div>
      <div class="right-sticks"></div>
    </el-header>
    <el-main class="main">
      <router-view></router-view>
    </el-main>
    <div class="bottomBg"></div>
  </el-container>
</template>

<script setup></script>

<style lang="less" scoped>
.home-container {
  background-color: #0c717d;
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
  background-image: url(../../../assets/img/edu/left-sticks.png);
  background-size: 100% 100%;
  left: 10%;
  top: 40px;
  width: 200px;
  height: 100px;
}

.right-sticks {
  position: absolute;
  background-image: url(../../../assets/img/edu/right-sticks.png);
  background-size: 100% 100%;
  right: 0;
  top: 30px;
  width: 250px;
  height: 180px;
}

.bottomBg {
  position: absolute;
  width: 100%;
  bottom: 0px;
  background-image: url(../../../assets/img/edu/bottom.png);
  background-size: 100% 100%;
  height: 200px;
}

.main {
  margin: 20px 20px 30px 20px;
  padding: 30px 40px 30px 40px;
  border-radius: 12px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  z-index: 1;
}

.el-menu.el-menu--horizontal {
  border-bottom: solid 0;
}

.el-menu--horizontal>.el-menu-item {
  font-size: 18px;
}

:deep(.el-sub-menu > .el-sub-menu__title) {
  font-size: 18px;
}
</style>
