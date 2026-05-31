<template>
  <t-popup placement="top" width="600" trigger="click">
    <CustomTable rowKey="id" :data="disputeData" size="small">
      <TableColumn property="userAnswer" label="用户答案">
        <template #default="scope">
          <p
            v-if="scope.row.userAnswer"
            v-for="(userSelection, index) in scope.row.userAnswer.split(',')"
            :key="index"
          >{{ index + 1 }}. {{ userSelection }}</p>
        </template>
      </TableColumn>
      <TableColumn
        property="disputeMemo"
        label="复议理由"
        width="150"
        ellipsis></TableColumn>
      <TableColumn
        width="80"
        property="ploNum"
        label="复议工号"></TableColumn>
      <TableColumn
        width="100"
        property="dataDate"
        label="复议时间"
        ellipsis></TableColumn>
    </CustomTable>
    <template #reference>
      <t-button size="small">复议{{ disputeCount }}人次</t-button>
    </template>
  </t-popup>
</template>

<script setup>
import { defineProps } from 'vue'

// Props
const props = defineProps({
  disputeData: {
    type: Array,
    default: () => []
  },
  disputeCount: {
    type: String,
    default: '0'
  }
})
</script>

<style scoped></style>
