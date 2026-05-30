<template>
  <div>
    <el-card class="box-card">
      <el-row style="padding-bottom: 10px">
        <el-col :span="10">
          <el-radio size="small" v-model="status" :label="-1" border
            >数据库管理</el-radio
          >
          <el-radio size="small" v-model="status" :label="1" border
            >数据库列表</el-radio
          >
        </el-col>
        <el-col :span="14"> </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="6">
          <el-input
            size="small"
            placeholder="输入关键字进行过滤"
            v-model="filterText"
          ></el-input>
          <el-tree
            :data="treeData"
            :filter-node-method="filterNode"
            :props="treePorps"
            ref="tree"
            node-key="id"
            @node-click="newTablePanel"
          ></el-tree>
        </el-col>
        <el-col :span="18">
          <el-tabs
            v-model="tableTabsValue"
            type="card"
            closable
            @tab-remove="removeTab"
          >
            <el-tab-pane
              v-for="item in tabs"
              :key="item.name"
              :label="item.title"
              :name="item.name"
            >
              <TableDetail :tableId="item.content"></TableDetail>
            </el-tab-pane>
          </el-tabs>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted, watch } from 'vue'
import { ElMessage } from 'element-plus'
import { getTreeData } from '@/api/db/index.js'
import TableDetail from './components/TableDetail.vue'

const treeData = ref([])
const status = ref(1)
const filterText = ref('')
const tableTabsValue = ref('2')
const tabs = ref([])
const tabIndex = ref(2)
const tree = ref(null)

const treePorps = {
  label: 'name'
}

const getTreeDataHandler = async () => {
  const res = await getTreeData(status.value)
  if (res.code !== 200) return ElMessage.error(res.msg)
  treeData.value = res.data
}

const filterNode = (value, data) => {
  if (!value) return true
  return data.name.indexOf(value) !== -1
}

const newTablePanel = (obj, node, self) => {
  if (obj.treeLevel !== 3) return
  const existingTab = tabs.value.find((item) => item.content === obj.id)
  if (existingTab) {
    tableTabsValue.value = existingTab.name
    return
  }
  const newTab = {
    title: obj.name,
    name: String(obj.id),
    content: obj.id
  }
  tabs.value.push(newTab)
  tableTabsValue.value = newTab.name
}

const removeTab = (targetName) => {
  const index = tabs.value.findIndex((item) => item.name === targetName)
  if (index !== -1) {
    tabs.value.splice(index, 1)
  }
}

watch(filterText, (value) => {
  if (tree.value) {
    tree.value.filter(value)
  }
})

onMounted(() => {
  getTreeDataHandler()
})
</script>
<style lang="less" scoped>
.box-card {
  height: calc(100vh - 190px);
  overflow: auto;
}
</style>
