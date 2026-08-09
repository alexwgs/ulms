<template>
  <div style="height:100%">
    <t-card class="management-card">
      <PageTips title="操作说明" theme="info" :closable="false"
        message="请正确使用文章管理配置：1.地区/条线 新版本暂未启用。2.前端文章顺序优先按照序号从小到大，再由点击量倒序。3.状态为已发布"></PageTips>
      <div class="filter-bar">
        <t-form
          :data="queryInfo"
          label-width="80px"
          colon
          layout="inline"
          class="filter-form"
        >
          <t-form-item label="关键字" name="query">
            <t-input-adornment style="width: 260px">
              <template #append>
                <t-button variant="outline" theme="primary" @click="getArticals">搜索</t-button>
              </template>
              <t-input placeholder="模糊搜索" size="small" v-model="queryInfo.query"></t-input>
            </t-input-adornment>
          </t-form-item>
          <t-form-item label="路径" name="routeId">
            <t-cascader
              v-model="queryInfo.routeId"
              size="small"
              :options="tree"
              :keys="{ value: 'id', label: 'name' }"
              placeholder="选择路径筛选"
              style="width: 180px"
              @change="routerChange"
              clearable
            ></t-cascader>
          </t-form-item>
          <t-form-item label="状态" name="status">
            <t-select
              v-model="queryInfo.status"
              size="small"
              placeholder="全部"
              style="width: 140px"
              @change="getArticals"
            >
              <t-option label="全部" value=""></t-option>
              <t-option label="有效" :value="1"></t-option>
              <t-option label="无效" :value="0"></t-option>
            </t-select>
          </t-form-item>
        </t-form>
        <div class="operation-container">
          <t-button variant="outline" theme="primary" size="small" @click="articalManager('add', null)">新建文章</t-button>
        </div>
      </div>
      <CustomTable rowKey="id" :data="articals" size="small" height="calc(100vh - 400px)" stripe @sort-change="tableSort"
        style="width: 100%">
        <!-- <TableColumn colKey="routeId" label="路径" sortable="custom" ellipsis></TableColumn> -->
        <TableColumn colKey="title" label="标题" sortable="custom" ellipsis></TableColumn>
        <TableColumn colKey="owner" label="条线" sortable="custom" width="70px">
          <template #default="scope">
            {{ownerlist.find(item => item.value === scope.row.owner)?.label || ''}}
          </template>
        </TableColumn>
        <TableColumn colKey="search" label="搜索" sortable="custom" width="70px" ellipsis>
          <template #default="scope">{{ scope.row.search == 1 ? '是' : '否' }}</template>
        </TableColumn>
        <TableColumn colKey="clicks" label="点击" sortable="custom" width="70px"
          ellipsis></TableColumn>
        <TableColumn colKey="sorting" label="序号" sortable="custom" width="70px"
          ellipsis></TableColumn>
        <TableColumn colKey="status" label="状态" sortable="custom" width="75px">
          <template #default="scope"><t-tag :theme="scope.row.status ? 'success' : 'danger'" size="small" variant="light">{{
            scope.row.status ? '已发布' : '未发布' }}</t-tag></template>
        </TableColumn>
        <TableColumn colKey="updateDate" label="操作时间" sortable="custom" width="140px"></TableColumn>
        <TableColumn label="操作" width="100px">
          <template #default="scope">
            <t-button variant="outline" size="small" theme="default" @click="articalManager('update', scope.row.journo)"
             >编辑</t-button>
            <t-button variant="outline" size="small" theme="danger" @click="deleteArtical(scope.row.journo)"
             >删除</t-button>
          </template>
        </TableColumn>
      </CustomTable>
      <t-pagination @page-size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current="queryInfo.pageNum" :page-size-options="[20, 40, 100, 200]" :page-size="queryInfo.pageSize"
 :total="total"></t-pagination>
    </t-card>
    <HelperArtical ref="helperArticalRef" @refresh-list="getArticals"></HelperArtical>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import HelperArtical from './components/artical.vue'
import { articalApi } from '@/api/helper/artical'
import { treeApi } from '@/api/helper/tree'

// 响应式数据
const total = ref(0)
const queryInfo = reactive({
  orderType: ' desc',
  order: ' journo',
  queryType: '',
  status: 1,
  keyword: '',
  routeId: '',
  query: '',
  pageSize: 20,
  pageNum: 1
})
const articals = ref([])
const arealist = ref([
  { label: '全部', value: '0' },
  { label: '上海', value: '1' },
  { label: '成都', value: '2' },
  { label: '武汉', value: '3' }
])
const ownerlist = ref([
  { label: '全部', value: '0' },
  { label: '客服条线', value: '1' },
  { label: '风管条线', value: '2' }
])
const tree = ref([])

// 引用
const helperArticalRef = ref(null)

// 生命周期钩子
onMounted(() => {
  getTree()
  getArticals()
})

// 获取路径树数据
const getTree = async () => {
  try {
    const res = await treeApi.getTree()
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    tree.value = res.data
  } catch (error) {
    MessagePlugin.error('获取路径树失败')
  }
}

// 获取文章列表
const getArticals = async () => {
  try {
    const res = await articalApi.getArticals(queryInfo)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    articals.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    MessagePlugin.error('获取文章列表失败')
  }
}

// 删除文章
const deleteArtical = async (journo) => {
  try {
    const res = await articalApi.deleteArtical(journo)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    getArticals()
  } catch (error) {
    MessagePlugin.error('删除文章失败')
  }
}

// 页面大小变化
const handleSizeChange = (pageSize) => {
  queryInfo.pageSize = pageSize
  getArticals()
}

// 当前页变化
const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  getArticals()
}

// 表格排序
const tableSort = (data) => {
  if (!data.descending) queryInfo.orderType = ' asc '
  else if (data.descending) queryInfo.orderType = ' desc '
  queryInfo.order = data.sortBy
  getArticals()
}

// 文章管理
const articalManager = (opType, journo) => {
  helperArticalRef.value?.getArtical(opType, journo)
}

// 路径变化处理
const routerChange = () => {
  if (queryInfo.routeId && queryInfo.routeId.length >= 2) {
    queryInfo.routeId = queryInfo.routeId[queryInfo.routeId.length - 1]
  } else {
    queryInfo.routeId = ''
  }
  getArticals()
}
</script>
<style lang="less" scoped>
.filter-bar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
  margin-bottom: 16px;
}

.filter-bar .filter-form {
  flex: 1;
  display: flex;
  align-items: center;
  gap: 16px;
  min-width: 0;
  margin-bottom: 0;
}

.filter-bar .operation-container {
  display: flex;
  align-items: center;
  gap: 8px;
  flex-shrink: 0;
}
</style>
