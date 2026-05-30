<template>
  <div style="height:100%">
    <el-card class="box-card">
      <el-row :gutter="15">
        <el-col :span="6">
          <el-input placeholder="模糊搜索" size="small" v-model="queryInfo.query">
            <template #append>
              <el-button icon="Search" @click="getArticals"></el-button>
            </template>
          </el-input>
        </el-col>
        <el-col :span="6">
          <el-cascader v-model="queryInfo.routeId" size="small" :options="tree" :props="{ value: 'id', label: 'name' }"
            :style="{ width: '100%' }" placeholder="选择路径筛选" @change="routerChange" clearable></el-cascader>
        </el-col>
        <el-col :span="6">
          <el-select v-model="queryInfo.status" size="small" placeholder="请选择发布状态" @change="getArticals">
            <el-option label="全部" value=""></el-option>
            <el-option label="有效" :value="1"></el-option>
            <el-option label="无效" :value="0"></el-option>
          </el-select>
        </el-col>
        <el-col :span="6">
          <el-button type="primary" size="small" @click="articalManager('add', null)">新建文章</el-button>
          <!-- <el-button type="primary" size="small" @click="$global.downloadExcel('college/report/course', null, '课程详情明细.xlsx')">下载文章</el-button> -->
        </el-col>
      </el-row>
      <el-alert title="操作说明" type="info" :closable="false"
        description="请正确使用文章管理配置：1.地区/条线 新版本暂未启用。2.前端文章顺序优先按照序号从小到大，再由点击量倒序。3.状态为已发布"></el-alert>
      <el-table :data="articals" size="small" height="calc(100vh - 400px)" stripe @sort-change="tableSort"
        style="width: 100%">
        <!-- <el-table-column prop="routeId" label="路径" sortable="custom" show-overflow-tooltip></el-table-column> -->
        <el-table-column prop="title" label="标题" sortable="custom" show-overflow-tooltip></el-table-column>
        <!-- <el-table-column prop="area" label="地区" sortable="custom" width="70px">
        <template slot-scope="scope">
          {{arealist.filter(item => item.value === scope.row.area)[0].label}}
        </template>
      </el-table-column> -->
        <el-table-column prop="owner" label="条线" sortable="custom" width="70px">
          <template #default="scope">
            {{ownerlist.find(item => item.value === scope.row.owner)?.label || ''}}
          </template>
        </el-table-column>
        <el-table-column prop="search" label="搜索" sortable="custom" width="70px" show-overflow-tooltip>
          <template #default="scope">{{ scope.row.search == 1 ? '是' : '否' }}</template>
        </el-table-column>
        <el-table-column prop="clicks" label="点击" sortable="custom" width="70px"
          show-overflow-tooltip></el-table-column>
        <el-table-column prop="sorting" label="序号" sortable="custom" width="70px"
          show-overflow-tooltip></el-table-column>
        <el-table-column prop="status" label="状态" sortable="custom" width="75px">
          <template #default="scope"><el-tag :type="scope.row.status ? 'success' : 'danger'" size="small">{{
            scope.row.status ? '已发布' : '未发布' }}</el-tag></template>
        </el-table-column>
        <el-table-column prop="updateDate" label="操作时间" sortable="custom" width="140px"></el-table-column>
        <el-table-column label="操作" width="100px">
          <template #default="scope">
            <el-button size="small" type="primary" icon="Edit" @click="articalManager('update', scope.row.journo)"
              circle></el-button>
            <el-button size="small" type="danger" icon="Delete" @click="deleteArtical(scope.row.journo)"
              circle></el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="queryInfo.pageNum" :page-sizes="[20, 40, 100, 200]" :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="total"></el-pagination>
    </el-card>
    <HelperArtical ref="helperArticalRef" @refresh-list="getArticals"></HelperArtical>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
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
    if (res.code !== 200) return ElMessage.error(res.msg)
    tree.value = res.data
  } catch (error) {
    ElMessage.error('获取路径树失败')
  }
}

// 获取文章列表
const getArticals = async () => {
  try {
    const res = await articalApi.getArticals(queryInfo)
    if (res.code !== 200) return ElMessage.error(res.msg)
    articals.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    ElMessage.error('获取文章列表失败')
  }
}

// 删除文章
const deleteArtical = async (journo) => {
  try {
    const res = await articalApi.deleteArtical(journo)
    if (res.code !== 200) return ElMessage.error(res.msg)
    ElMessage.success(res.msg)
    getArticals()
  } catch (error) {
    ElMessage.error('删除文章失败')
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
  if (data.order === 'ascending') queryInfo.orderType = ' asc '
  else if (data.order === 'descending') queryInfo.orderType = ' desc '
  queryInfo.order = data.prop
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
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
</style>