<template>
  <el-popover placement="top" width="600" trigger="click">
    <el-table :data="disputeData" size="small">
      <el-table-column property="userAnswer" label="用户答案">
        <template #default="scope">
          <el-text
            v-if="scope.row.userAnswer"
            v-for="(userSelection, index) in scope.row.userAnswer.split(',')"
            :key="index"
            tag="p"
            >{{ index + 1 }}. {{ userSelection }}</el-text
          >
        </template>
      </el-table-column>
      <el-table-column
        property="disputeMemo"
        label="复议理由"
        width="150"
        show-overflow-tooltip
      ></el-table-column>
      <el-table-column
        width="80"
        property="ploNum"
        label="复议工号"
      ></el-table-column>
      <el-table-column
        width="100"
        property="dataDate"
        label="复议时间"
        show-overflow-tooltip
      ></el-table-column>
    </el-table>
    <template #reference>
      <el-button size="small">复议{{ disputeCount }}人次</el-button>
    </template>
  </el-popover>
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
