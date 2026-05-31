<template>
  <t-loading :loading="loading">
    <div class="container">
      <t-row>
        <t-col :span="9">
          占位
        </t-col>
        <t-col :span="3">
          <div class="slogan">
            <div></div>
            <img class="brush-icon" src="../../../assets/img/edu/my-questions-logo.png">
          </div>
        </t-col>
      </t-row>
    </div>
  </t-loading>
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
