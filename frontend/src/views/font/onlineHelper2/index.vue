<template>
  <t-card class="box-card" style="height: calc(100vh - 130px)">
    <t-row :gutter="15">
      <t-col :span="3">
        <t-card shadow="hover">
          <template #header>
            <span>知识路径</span>
          </template>
          <div style="margin-bottom: 10px">
            <t-input
              placeholder="输入关键字进行过滤"
              size="small"
              v-model="filterText"
            ></t-input>
          </div>
          <div style="margin-bottom: 10px">
            <t-button size="small" @click="expanded(true)">展开</t-button>
            <t-button size="small" @click="expanded(false)">收起</t-button>
          </div>
          <div style="height: calc(100vh - 320px); overflow: auto">
            <t-tree
              style="margin-top: 5px"
              :data="tree"
              @click="routeArtical"
              :keys="{ value: 'id', label: 'name', children: 'children' }"
              expand-all
              activable
              v-model:actived="activeValue"
              v-model:expanded="expandedKeys"
              :filter="filterNode"
              ref="treeRef"
            ></t-tree>
          </div>
        </t-card>
      </t-col>
      <t-col :span="9">
        <div style="width: 100%">
          <t-input
            placeholder="请输入关键字,可按下回车查询"
            v-model="queryInfo.query"
            size="small"
            clearable
            @change="getArticalListByQuery('keyword')"
          >
            <template #append>
              <t-button @click="getArticalListByQuery('content')"><template #icon><DynamicIcon name="search" /></template>全文搜索</t-button
              >
            </template>
          </t-input>
          <div style="padding-top: 10px; font-size: 14px">
            <div class="left" style="line-height: 40px">
              当前筛选：<t-tag v-if="currentNode == null">全部</t-tag
              ><t-tag
                v-else
                closable
                theme="danger"
                effect="plain"
                @close="routeArtical(null)"
                >{{ currentNode.name }}</t-tag
              >
            </div>
          </div>
        </div>
        <CustomTable rowKey="id"
          :data="articals"
          size="small"
          height="calc(100vh - 250px)"
          style="width: 100%; padding-top: 10px"
          :show-header="false"
          stripe>
          <TableColumn
            prop="title"
            label="标题"
            sortable="custom"
            ellipsis>
            <template #default="scope">
              <t-link
                style="font-size: 12px"
                underline="hover"
                @click="currentView(scope.row.journo)"
                >{{ scope.row.title }}</t-link
              >
            </template>
          </TableColumn>
          <TableColumn label="新窗口打卡" width="140">
            <template #default="scope">
              <a
                class="link"
                target="_blank"
                :href="'/helper/artical/' + scope.row.journo"
                rel="opener"
                >新窗口打卡</a
              >
            </template>
          </TableColumn>
        </CustomTable>
        <t-pagination
          @page-size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          v-model:current="queryInfo.pageNum"
          :page-size-options="[20, 40, 100, 200]"
          v-model:page-size="queryInfo.pageSize"

          :total="total"
        ></t-pagination>
      </t-col>
    </t-row>
    <ViewDialog ref="viewDialogRef"></ViewDialog>
  </t-card>
</template>

<script setup>
import { ref, reactive, onMounted, watch } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { treeApi } from '@/api/helper/tree'
import { articalApi } from '@/api/helper/artical'
import ViewDialog from './components/ViewDialog.vue'

const queryInfo = reactive({
  orderType: ' desc',
  order: ' clicks',
  routeId: null,
  queryType: null,
  status: 1,
  query: null,
  pageSize: 20,
  pageNum: 1
})

const filterText = ref('')
const articals = ref([])
const tree = ref([])
const currentNode = ref(null)
const total = ref(0)
const treeRef = ref(null)
const activeValue = ref([])
const expandedKeys = ref([])
const viewDialogRef = ref(null)

onMounted(() => {
  getTree()
})

const getTree = async () => {
  try {
    const res = await treeApi.getTree()
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    tree.value = res.data
    getArticalList()
  } catch (error) {
    MessagePlugin.error(error.message || '获取树形数据失败')
  }
}

const filterNode = (node) => {
  if (!filterText.value) return true
  return node.data.name.indexOf(filterText.value) !== -1
}

const getArticalList = async () => {
  try {
    const res = await articalApi.getArticals(queryInfo)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    articals.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    MessagePlugin.error(error.message || '获取文章列表失败')
  }
}

const getArticalListByQuery = (queryType) => {
  queryInfo.queryType = queryType
  getArticalList()
}

const routeArtical = (context) => {
  // Extract data from TDesign @click context, or use raw value from programmatic calls
  let data
  if (context && context.node) {
    data = context.node.data
  } else {
    data = context
  }

  if (data != null && data !== undefined) {
    currentNode.value = data
    queryInfo.routeId = data.id
  } else if (data === null) {
    currentNode.value = null
    queryInfo.routeId = null
    activeValue.value = []
  } else if (data === undefined) {
    if (queryInfo.routeId !== null) {
      activeValue.value = [queryInfo.routeId]
    }
  }
  getArticalList()
}

const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  getArticalList()
}

const handleSizeChange = (pageSize) => {
  queryInfo.pageSize = pageSize
  getArticalList()
}

const currentView = (journo) => {
  if (viewDialogRef.value) {
    viewDialogRef.value.init(journo, queryInfo.query)
  }
}

const expanded = (expandFlag) => {
  if (expandFlag) {
    const collectIds = (nodes) => {
      let ids = []
      for (const node of nodes) {
        ids.push(String(node.id))
        if (node.children && node.children.length > 0) {
          ids = ids.concat(collectIds(node.children))
        }
      }
      return ids
    }
    expandedKeys.value = collectIds(tree.value)
  } else {
    expandedKeys.value = []
  }
}

watch(filterText, (val) => {
  if (treeRef.value) {
    treeRef.value.refresh()
  }
})
</script>

<style scoped>
.link {
  text-decoration: none;
  color: chocolate;
}
</style>
