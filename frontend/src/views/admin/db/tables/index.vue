<template>
  <div>
    <t-card class="box-card">
      <t-row style="padding-bottom: 10px">
        <t-col :span="5">
          <t-radio-group v-model="status" size="small">
            <t-radio :value="-1" border
            >数据库管理</t-radio
          >
          <t-radio :value="1" border
            >数据库列表</t-radio
          >
          </t-radio-group>
        </t-col>
        <t-col :span="7"> </t-col>
      </t-row>
      <t-row :gutter="20">
        <t-col :span="3">
          <t-input
            size="small"
            placeholder="输入关键字进行过滤"
            v-model="filterText"
          ></t-input>
          <t-tree
            :data="treeData"
            :filter="filterNode"
            :keys="{ value: 'id', label: 'name', children: 'children' }"
            ref="tree"
            @click="newTablePanel"
          ></t-tree>
        </t-col>
        <t-col :span="9">
          <t-tabs
            v-model="tableTabsValue"
            type="card"
            closable
            @tab-remove="removeTab"
          >
            <t-tab-panel
              v-for="item in tabs"
              :key="item.name"
              :label="item.title"
              :name="item.name"
            >
              <TableDetail :tableId="item.content"></TableDetail>
            </t-tab-panel>
          </t-tabs>
        </t-col>
      </t-row>
    </t-card>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted, watch } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
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
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  treeData.value = res.data
}

const filterNode = (node) => {
  if (!filterText.value) return true
  return node.data.name.indexOf(filterText.value) !== -1
}

const newTablePanel = (context) => {
      const { node: treeNode } = context;
      const obj = treeNode.data;
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
    tree.value.refresh()
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
