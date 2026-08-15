<template>
  <t-card class="management-card">
    <div class="sub-page-header">
      <t-button theme="default" variant="text" @click="router.back()">
        
      返回</t-button>
      <span class="sub-page-title">我的发布</span>
    </div>
    <t-divider></t-divider>
    <div class="text item">
      <t-form :data="queryInfo" label-width="80px" colon class="filter-form">
        <t-row :gutter="[24, 24]">
          <t-col :span="4">
            <t-form-item label="状态" name="status">
              <t-select size="small" v-model="queryInfo.status" @change="getArticleList"
                placeholder="全部">
                <t-option label="全部" value=""></t-option>
                <t-option v-for="item in dictStore.getDictByNames('cyt_artical_status', 1)" :key="item.id"
                  :label="item.codeval" :value="item.code">
                </t-option>
              </t-select>
            </t-form-item>
          </t-col>
          <t-col :span="4">
            <t-form-item label="项目年份" name="dateYear">
              <t-date-picker size="small" @change="getArticleList" v-model="yearPicker" mode="year" placeholder="选择年">
              </t-date-picker>
            </t-form-item>
          </t-col>
        </t-row>
      </t-form>
      <CustomTable rowKey="id" :data="articles" size="small" stripe style="width: 100%" height="calc(100vh - 350px)">
        <TableColumn colKey="articleType" label="类型" width="100">
          <template #default="scope">
            <t-tag size="small" v-for="item in dictStore.getDictByNames('cyt_artical_type', 1)" :key="item.code"
              :theme="scope.row.articleType == 1 ? 'danger' : 'default'"
              :style="scope.row.articleType == item.code ? '' : 'display:none'" variant="light">{{ item.codeval
              }}</t-tag>
          </template>
        </TableColumn>
        <TableColumn colKey="title" label="标题" ellipsis>
        </TableColumn>
        <TableColumn colKey="viewNum" label="浏览量" width="90">
        </TableColumn>
        <TableColumn colKey="likeNum" label="点赞" width="60">
        </TableColumn>
        <TableColumn colKey="replyNum" label="评论" width="60">
        </TableColumn>
        <TableColumn colKey="status" label="状态" width="100">
          <template #default="scope">
            <t-tag size="small" v-for="item in dictStore.getDictByNames('cyt_artical_status', 1)" :key="item.code"
              :theme="scope.row.status == 1 ? 'success' : 'warning'"
              :style="scope.row.status == item.code ? '' : 'display:none'" variant="light">{{ item.codeval }}</t-tag>
          </template>
        </TableColumn>
        <TableColumn colKey="pubDate" label="发布日期" width="160">
        </TableColumn>
        <TableColumn label="操作" width="230">
          <template #default="scope">
            <t-button theme="primary" size="small" @click="viewArticle(scope.$index, scope.row)">查看</t-button>
            <t-button size="small" :disabled="scope.row.status == 2 || scope.row.status == 4 ? false : true"
              @click="updateArticle(scope.$index, scope.row)">编辑</t-button>
            <t-button theme="danger" size="small" @click="articleDelete(scope.$index, scope.row)">删除</t-button>
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
import { useConfirm } from '@/hooks/useConfirm'

const router = useRouter()
const dictStore = useDictStore()

const articles = ref([])
const currentPage = ref(1)
const total = ref(0)
const yearPicker = ref(new Date())

const queryInfo = reactive({
  dateYear: '',
  status: '',
  querytype: '',
  query: '',
  pageSize: 20,
  pageNum: 1
})

onMounted(() => {
  getArticleList()
})

const getArticleList = async () => {
  if (yearPicker.value != null && yearPicker.value !== '') {
    queryInfo.dateYear = new Date(yearPicker.value).getFullYear()
  }
  try {
    const res = await httpInstance.get('cyt/myArticle', { params: queryInfo })
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    articles.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    MessagePlugin.error('获取文章列表失败')
    console.error(error)
  }
}

const viewArticle = (index, row) => {
  if (row.articleType === 1) {
    const url = router.resolve({ path: '/article/item/' + row.id })
    window.open(url.href, '_blank')
  } else {
    const url = router.resolve({ path: '/article/view/' + row.id })
    window.open(url.href, '_blank')
  }
}

const updateArticle = (index, row) => {
  if (row.articleType === 1) {
    router.push({ path: 'item-pub/' + row.id })
  } else if (row.articleType === 3) {
    router.push({ path: 'survey/' + row.id })
  } else {
    router.push({ path: 'article/' + row.id })
  }
}

const articleDelete = async (index, row) => {
  const { confirm: confirmDialog } = useConfirm()
  const ok = await confirmDialog('删除后不可恢复，请确认是否要删除?', {
    title: '提示',
    type: 'error'
  })
  if (!ok) {
    MessagePlugin.info('取消文章删除！')
    return
  }
  try {
    const params = { id: row.id, status: 0 }
    const res = await httpInstance.put('cyt/article/delete', params)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    MessagePlugin.success(res.msg)
    getArticleList()
  } catch (error) {
    MessagePlugin.error('删除失败')
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

.table-filter {
  padding: 0;

  span {
    font-size: 14px;
    margin-left: 20px;
  }
}
</style>
