<template>
  <el-card class="box-card" style="height: calc(100vh - 130px)">
    <el-row :gutter="15">
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>
            <span>知识路径</span>
          </template>
          <div style="margin-bottom: 10px">
            <el-input
              placeholder="输入关键字进行过滤"
              size="small"
              v-model="filterText"
            ></el-input>
          </div>
          <div style="margin-bottom: 10px">
            <el-button size="small" @click="expanded(true)">展开</el-button>
            <el-button size="small" @click="expanded(false)">收起</el-button>
          </div>
          <div style="height: calc(100vh - 320px); overflow: auto">
            <el-tree
              style="margin-top: 5px"
              :data="tree"
              @node-click="routeArtical"
              node-key="id"
              :props="{ children: 'children', label: 'name' }"
              default-expand-all
              highlight-current
              :filter-node-method="filterNode"
              ref="treeRef"
            ></el-tree>
          </div>
        </el-card>
      </el-col>
      <el-col :span="18">
        <div style="width: 100%">
          <el-input
            placeholder="请输入关键字,可按下回车查询"
            v-model="queryInfo.query"
            size="small"
            clearable
            @change="getArticalListByQuery('keyword')"
          >
            <template #append>
              <el-button icon="Search" @click="getArticalListByQuery('content')"
                >全文搜索</el-button
              >
            </template>
          </el-input>
          <div style="padding-top: 10px; font-size: 14px">
            <div class="left" style="line-height: 40px">
              当前筛选：<el-tag v-if="currentNode == null">全部</el-tag
              ><el-tag
                v-else
                closable
                type="danger"
                effect="plain"
                @close="routeArtical(null)"
                >{{ currentNode.name }}</el-tag
              >
            </div>
          </div>
        </div>
        <el-table
          :data="articals"
          size="small"
          height="calc(100vh - 250px)"
          style="width: 100%; padding-top: 10px"
          :show-header="false"
          stripe
        >
          <el-table-column
            prop="title"
            label="标题"
            sortable="custom"
            show-overflow-tooltip
          >
            <template #default="scope">
              <el-link
                style="font-size: 12px"
                underline="hover"
                @click="currentView(scope.row.journo)"
                >{{ scope.row.title }}</el-link
              >
            </template>
          </el-table-column>
          <el-table-column label="新窗口打卡" width="140">
            <template #default="scope">
              <a
                class="link"
                target="_blank"
                :href="'/helper/artical/' + scope.row.journo"
                rel="opener"
                >新窗口打卡</a
              >
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          v-model:current-page="queryInfo.pageNum"
          :page-sizes="[20, 40, 100, 200]"
          v-model:page-size="queryInfo.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        ></el-pagination>
      </el-col>
    </el-row>
    <ViewDialog ref="viewDialogRef"></ViewDialog>
  </el-card>
</template>

<script setup>
import { ref, reactive, onMounted, watch } from 'vue'
import { ElMessage } from 'element-plus'
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
const viewDialogRef = ref(null)

onMounted(() => {
  getTree()
})

const getTree = async () => {
  try {
    const res = await treeApi.getTree()
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    tree.value = res.data
    getArticalList()
  } catch (error) {
    ElMessage.error(error.message || '获取树形数据失败')
  }
}

const filterNode = (value, data) => {
  if (!value) return true
  return data.name.indexOf(value) !== -1
}

const getArticalList = async () => {
  try {
    const res = await articalApi.getArticals(queryInfo)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    articals.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    ElMessage.error(error.message || '获取文章列表失败')
  }
}

const getArticalListByQuery = (queryType) => {
  queryInfo.queryType = queryType
  getArticalList()
}

const routeArtical = (data) => {
  if (data != null && data !== undefined) {
    currentNode.value = data
    queryInfo.routeId = data.id
  } else if (data === null) {
    currentNode.value = null
    queryInfo.routeId = null
    if (treeRef.value) {
      treeRef.value.setCurrentKey(queryInfo.routeId)
    }
  } else if (data === undefined) {
    if (queryInfo.routeId !== null) {
      if (treeRef.value) {
        treeRef.value.setCurrentKey(queryInfo.routeId)
        currentNode.value = treeRef.value.getCurrentNode()
      }
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
  if (treeRef.value && treeRef.value.$children) {
    treeRef.value.$children.forEach(function (item, index, arr) {
      item.expanded = expandFlag
    })
  }
}

watch(filterText, (val) => {
  if (treeRef.value) {
    treeRef.value.filter(val)
  }
})
</script>

<style scoped>
.link {
  text-decoration: none;
  color: chocolate;
}
</style>
