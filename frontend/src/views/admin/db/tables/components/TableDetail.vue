<template>
  <div>
    <h3>
      {{
        table.tableName == null ? '未命名' : table.tableName + '/' + table.name
      }}
    </h3>
    <el-row :gutter="10">
      <el-col :span="3"
        ><h5>行数：{{ table.rowNum }}</h5></el-col
      >
      <el-col :span="10"
        ><h5>更新：{{ table.lastAnalyzed }}</h5></el-col
      >
      <el-col :span="8"
        ><h5>索引：{{ table.tableIndex }}</h5></el-col
      >
      <el-col :span="3"
        ><h5>热度：{{ table.clickNum }}</h5></el-col
      >
    </el-row>
    <el-row :gutter="10">
      <el-col :span="24"
        ><h5>说明：{{ table.memo }}</h5></el-col
      >
    </el-row>
    <el-table :data="columnTable" stripe size="small" style="width: 100%">
      <el-table-column prop="col" label="字段" width="120"></el-table-column>
      <el-table-column
        prop="colName"
        label="名称"
        width="120"
      ></el-table-column>
      <el-table-column
        prop="formatType"
        label="格式"
        width="120"
      ></el-table-column>
      <el-table-column
        prop="formatSize"
        label="大小"
        width="100"
      ></el-table-column>
      <el-table-column
        prop="primaryKey"
        label="主键"
        width="80"
      ></el-table-column>
      <el-table-column prop="notNull" label="非空" width="80">
      </el-table-column>
      <el-table-column
        prop="describe"
        label="描述"
        show-overflow-tooltip
      ></el-table-column>
    </el-table>
  </div>
</template>
<script setup>
import { ref, onMounted, watch } from 'vue'
import { ElMessage } from 'element-plus'
import { getTableDetail, getColumnInfo } from '@/api/db/index.js'

const props = defineProps({
  tableId: {
    type: [String, Number],
    required: true
  }
})

const table = ref({})
const columnTable = ref([])

const getTableInfo = async () => {
  const res = await getTableDetail(props.tableId)
  if (res.code !== 200) return ElMessage.error(res.msg)
  table.value = res.data
  getColumnInfoHandler()
}

const getColumnInfoHandler = async () => {
  const res = await getColumnInfo(props.tableId)
  if (res.code !== 200) return ElMessage.error(res.msg)
  columnTable.value = res.data
}

onMounted(() => {
  getTableInfo()
})

watch(
  () => props.tableId,
  (newVal) => {
    if (newVal) {
      getTableInfo()
    }
  }
)
</script>
<style lang="less" scoped></style>
