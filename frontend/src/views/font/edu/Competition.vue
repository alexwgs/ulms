<template>
  <div class="container" v-loading="loading" element-loading-background="rgba(46, 174, 119, 0.5)">
      <el-row>
        <el-col :span="18">
          占位
        </el-col>
        <el-col :span="6">
          <div class="slogan">
            <div></div>
            <img class="brush-icon" src="../../../assets/img/edu/my-questions-logo.png">
          </div>
        </el-col>
      </el-row>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const loading = ref(false)
</script>

<style lang="less" scoped>
  .container {
    height: 100%;
  }
  .brush-icon {
    max-height: 330px;
    max-width: 420px;
    height: 50%;
    bottom: 10%;
    position: fixed;
    right: 50px;
  }
</style>
