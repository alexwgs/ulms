<template>
  <el-card class="box-card">
    <el-page-header @back="() => router.back()" content="我的收藏">
    </el-page-header>
    <el-divider></el-divider>
    <div class="text item">
      <el-table :data="collects" size="small" stripe style="width: 100%" height="calc(100vh - 280px)">
        <el-table-column prop="artical.articalType" label="类型" width="100">
          <template #default="scope">
            <el-tag v-if="scope.row.artical && 'articalType' in scope.row.artical" size="small"
              :type="scope.row.artical.articalType == 1 ? 'danger' : 'info'" effect="plain">{{
                dictStore.getDictName('cyt_artical_type', scope.row.artical.articalType) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="artical.title" label="标题"></el-table-column>
        <el-table-column prop="artical.pubDate" label="发布日期" width="180"></el-table-column>
        <el-table-column prop="dateTime" label="收藏日期" width="180"></el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button type="primary" size="small" @click="viewArtical(scope.$index, scope.row)">查看</el-button>
            <el-button type="danger" size="small" @click="cancelCollect(scope.$index, scope.row)">取消收藏</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination @current-change="handleCurrentChange" v-model:current-page="currentPage"
        :page-size="queryInfo.pageSize" layout="total, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>
  </el-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { httpInstance } from '@/utils/request'
import { useDictStore } from '@/stores'

const router = useRouter()
const dictStore = useDictStore()

const collects = ref([])
const currentPage = ref(1)
const total = ref(0)

const queryInfo = reactive({
  sortType: 'hot',
  querytype: '',
  query: '',
  pageSize: 20,
  pageNum: 1
})

const getDictLabel = (dictName, value) => {
  const dictList = dictStore.getDictByNames(dictName, 1)
  const item = dictList.find(d => d.code == value)
  return item ? item.codeval : ''
}

onMounted(() => {
  getCollectList()
})

const getCollectList = async () => {
  try {
    const res = await httpInstance.get('cyt/collect/list', {
      params: queryInfo
    })
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    total.value = res.data.total
    collects.value = res.data.list
  } catch (error) {
    ElMessage.error('获取收藏列表失败')
    console.error(error)
  }
}

const viewArtical = (index, row) => {
  if (row.artical.articalType === 1) {
    const url = router.resolve({
      path: '/artical/item/' + row.artical.id
    })
    window.open(url.href, '_blank')
  } else {
    const url = router.resolve({
      path: '/artical/view/' + row.artical.id
    })
    window.open(url.href, '_blank')
  }
}

const cancelCollect = async (index, row) => {
  try {
    const res = await httpInstance.post(`cyt/collect/${row.artical.id}/1`)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    ElMessage.success(res.msg)
    getCollectList()
  } catch (error) {
    ElMessage.error('取消收藏失败')
    console.error(error)
  }
}

const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
}
</script>

<style lang="less" scoped>
.box-card {
  height: calc(100vh - 130px);
}
</style>
