<template>
  <t-card class="management-card">
    <div class="sub-page-header">
      <t-button theme="default" variant="text" @click="router.back()">
        
      返回</t-button>
      <span class="sub-page-title">我的收藏</span>
    </div>
    <t-divider></t-divider>
    <div class="text item">
      <CustomTable rowKey="id" :data="collects" size="small" stripe style="width: 100%" height="calc(100vh - 280px)">
        <TableColumn colKey="artical.articalType" label="类型" width="100">
          <template #default="scope">
            <t-tag v-if="scope.row.artical && 'articalType' in scope.row.artical" size="small"
              :theme="scope.row.artical.articalType == 1 ? 'danger' : 'default'" variant="light">{{
                dictStore.getDictName('cyt_artical_type', scope.row.artical.articalType) }}</t-tag>
          </template>
        </TableColumn>
        <TableColumn colKey="artical.title" label="标题"></TableColumn>
        <TableColumn colKey="artical.pubDate" label="发布日期" width="180"></TableColumn>
        <TableColumn colKey="dateTime" label="收藏日期" width="180"></TableColumn>
        <TableColumn label="操作" width="200">
          <template #default="scope">
            <t-button theme="primary" size="small" @click="viewArtical(scope.$index, scope.row)">查看</t-button>
            <t-button theme="danger" size="small" @click="cancelCollect(scope.$index, scope.row)">取消收藏</t-button>
          </template>
        </TableColumn>
      </CustomTable>
      <t-pagination @current-change="handleCurrentChange" v-model="currentPage"
        :page-size="queryInfo.pageSize" :total="total">
      </t-pagination>
    </div>
  </t-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { MessagePlugin } from 'tdesign-vue-next'
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
      MessagePlugin.error(res.msg)
      return
    }
    total.value = res.data.total
    collects.value = res.data.list
  } catch (error) {
    MessagePlugin.error('获取收藏列表失败')
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
      MessagePlugin.error(res.msg)
      return
    }
    MessagePlugin.success(res.msg)
    getCollectList()
  } catch (error) {
    MessagePlugin.error('取消收藏失败')
    console.error(error)
  }
}

const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
}
</script>

<style lang="less" scoped>
.management-card {
  height: calc(100vh - 130px);
}
</style>
