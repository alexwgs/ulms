<template>
  <div>
    <h3>
      {{
        table.tableName == null ? '未命名' : table.tableName + '/' + table.name
      }}
    </h3>
    <t-row :gutter="10">
      <t-col :span="2"
        ><h5>行数：{{ table.rowNum }}</h5></t-col
      >
      <t-col :span="5"
        ><h5>更新：{{ table.lastAnalyzed }}</h5></t-col
      >
      <t-col :span="4"
        ><h5>索引：{{ table.tableIndex }}</h5></t-col
      >
      <t-col :span="2"
        ><h5>热度：{{ table.clickNum }}</h5></t-col
      >
    </t-row>
    <t-row :gutter="10">
      <t-col :span="12"
        ><h5>说明：{{ table.memo }}</h5></t-col
      >
    </t-row>
    <CustomTable rowKey="id" :data="columnTable" stripe size="small" style="width: 100%">
      <TableColumn colKey="col" label="字段" width="120"></TableColumn>
      <TableColumn
        prop="colName"
        label="名称"
        width="120"></TableColumn>
      <TableColumn
        prop="formatType"
        label="格式"
        width="120"></TableColumn>
      <TableColumn
        prop="formatSize"
        label="大小"
        width="100"></TableColumn>
      <TableColumn
        prop="primaryKey"
        label="主键"
        width="80"></TableColumn>
      <TableColumn colKey="notNull" label="非空" width="80">
      </TableColumn>
      <TableColumn
        prop="describe"
        label="描述"
        ellipsis></TableColumn>
    </CustomTable>
  </div>
</template>
<script setup>
import { ref, onMounted, watch } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
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
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  table.value = res.data
  getColumnInfoHandler()
}

const getColumnInfoHandler = async () => {
  const res = await getColumnInfo(props.tableId)
  if (res.code !== 200) return MessagePlugin.error(res.msg)
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
