<template>
  <el-card class="box-card">
    <el-page-header @back="() => router.back()" content="我的发布">
    </el-page-header>
    <el-divider></el-divider>
    <div class="text item">
      <div class="table-filter">
        <span>状态
          <el-select style="width: 200px;" size="small" v-model="queryInfo.status" @change="getArticalList"
            placeholder="请选择,默认不限制">
            <el-option label="全部" value=""></el-option>
            <el-option v-for="item in dictStore.getDictByNames('cyt_artical_status', 1)" :key="item.id"
              :label="item.codeval" :value="item.code">
            </el-option>
          </el-select>
        </span>
        <span class="demonstration">项目年份
          <el-date-picker size="small" @change="getArticalList" v-model="yearPicker" type="year" placeholder="选择年">
          </el-date-picker>
        </span>
      </div>
      <el-table :data="articals" size="small" stripe style="width: 100%" height="calc(100vh - 350px)">
        <el-table-column prop="articalType" label="类型" width="100">
          <template #default="scope">
            <el-tag size="small" v-for="item in dictStore.getDictByNames('cyt_artical_type', 1)" :key="item.code"
              :type="scope.row.articalType == 1 ? 'danger' : 'info'"
              :style="scope.row.articalType == item.code ? '' : 'display:none'" effect="plain">{{ item.codeval
              }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="title" label="标题" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="viewNum" label="浏览量" width="90">
        </el-table-column>
        <el-table-column prop="likeNum" label="点赞" width="60">
        </el-table-column>
        <el-table-column prop="replyNum" label="评论" width="60">
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag size="small" v-for="item in dictStore.getDictByNames('cyt_artical_status', 1)" :key="item.code"
              :type="scope.row.status == 1 ? 'success' : 'warning'"
              :style="scope.row.status == item.code ? '' : 'display:none'" effect="plain">{{ item.codeval }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="pubDate" label="发布日期" width="160">
        </el-table-column>
        <el-table-column label="操作" width="230">
          <template #default="scope">
            <el-button type="primary" size="small" @click="viewArtical(scope.$index, scope.row)">查看</el-button>
            <el-button size="small" :disabled="scope.row.status == 2 || scope.row.status == 4 ? false : true"
              @click="updateArtical(scope.$index, scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="articalDelete(scope.$index, scope.row)">删除</el-button>
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
import { ElMessage, ElMessageBox } from 'element-plus'
import { httpInstance } from '@/utils/request'
import { useDictStore } from '@/stores'

const router = useRouter()
const dictStore = useDictStore()

const articals = ref([])
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
  getArticalList()
})

const getArticalList = async () => {
  if (yearPicker.value != null && yearPicker.value !== '') {
    queryInfo.dateYear = new Date(yearPicker.value).getFullYear()
  }
  try {
    const res = await httpInstance.get('cyt/myArtical', { params: queryInfo })
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    articals.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    ElMessage.error('获取文章列表失败')
    console.error(error)
  }
}

const viewArtical = (index, row) => {
  if (row.articalType === 1) {
    const url = router.resolve({ path: '/artical/item/' + row.id })
    window.open(url.href, '_blank')
  } else {
    const url = router.resolve({ path: '/artical/view/' + row.id })
    window.open(url.href, '_blank')
  }
}

const updateArtical = (index, row) => {
  if (row.articalType === 1) {
    router.push({ path: 'item-pub/' + row.id })
  } else if (row.articalType === 3) {
    router.push({ path: 'survey/' + row.id })
  } else {
    router.push({ path: 'artical/' + row.id })
  }
}

const articalDelete = (index, row) => {
  ElMessageBox.confirm('删除后不可恢复，请确认是否要删除?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'error'
  }).then(async () => {
    try {
      const params = { id: row.id, status: 0 }
      const res = await httpInstance.put('cyt/artical/delete', params)
      if (res.code !== 200) {
        ElMessage.error(res.msg)
        return
      }
      ElMessage.success(res.msg)
      getArticalList()
    } catch (error) {
      ElMessage.error('删除失败')
      console.error(error)
    }
  }).catch(() => {
    ElMessage.info('取消文章删除！')
  })
}

const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
}
</script>

<style lang="less" scoped>
.box-card {
  height: calc(100vh - 130px);
}

.table-filter {
  padding: 10px;

  span {
    font-size: 14px;
    margin-left: 20px;
  }
}
</style>
