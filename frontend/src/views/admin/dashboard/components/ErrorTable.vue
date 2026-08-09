<template>
  <t-card class="error-table-card" :bordered="true">
    <template #header>
      <span class="card-title">今日异常记录</span>
    </template>
    <CustomTable
      rowKey="id"
      :data="errorList"
      height="350px"
      style="width: 100%"
      :defaultEmptyText="'今日暂无异常'"
    >
      <TableColumn prop="operTime" label="操作时间" width="180"  />
      <TableColumn prop="title" label="模块" width="200" ellipsis />
      <TableColumn prop="content" label="操作类型" width="120" ellipsis />
      <TableColumn prop="errorMsg" label="错误说明" >
        <template #default="scope">
          <t-tooltip
              class="placement top left"
              :content="scope.row.errorMsg"
              placement="left"
              :overlay-style="{ width: '500px' }"
              show-arrow
          >
            <t-button variant="outline"> 详情 </t-button>
          </t-tooltip>
        </template>
      </TableColumn>
    </CustomTable>
  </t-card>
</template>

<script setup>
defineProps({
  errorList: {
    type: Array,
    default: () => []
  }
})
</script>

<style lang="less" scoped>
.error-table-card {
  height: 440px;
}
</style>
