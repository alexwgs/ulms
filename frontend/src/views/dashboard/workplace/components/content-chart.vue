<template>
  <a-spin :loading="loading" style="width: 100%">
    <a-card
      class="general-card"
      :header-style="{ paddingBottom: 0 }"
      :body-style="{
        paddingTop: '20px',
      }"
      title="内容数据图表"
    >
      <template #extra>
        <a-link>图表</a-link>
      </template>
      <!-- <Chart height="289px" :option="chartOption" /> -->
    </a-card>
  </a-spin>
</template>

<script setup>
</script>

<style scoped lang="less"></style>
