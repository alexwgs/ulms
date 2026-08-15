<template>
  <t-card class="management-card">
    <div class="text item">
      <t-form :data="queryInfo" label-width="80px" colon class="filter-form">
        <t-row :gutter="[24, 24]">
          <t-col :span="2">
            <t-form-item label="年份" name="dateYear">
              <t-date-picker
                size="small"
                @change="getArticleList"
                v-model="yearPicker"
                mode="year"
                placeholder="选择年"
              ></t-date-picker>
            </t-form-item>
          </t-col>
          <t-col :span="2">
            <t-form-item label="板块" name="category">
              <t-select
                size="small"
                v-model="queryInfo.category"
                @change="getArticleList"
                placeholder="全部"
              >
                <t-option :value="-1" label="全部"></t-option>
                <t-option
                  v-for="item in (dictStore.dictList?.cyt_artical_category || [])"
                  :key="item.code"
                  :label="item.codeval"
                  :value="parseInt(item.code)"
                >
                </t-option>
              </t-select>
            </t-form-item>
          </t-col>
          <t-col :span="2">
            <t-form-item label="状态" name="status">
              <t-select
                size="small"
                v-model="queryInfo.status"
                @change="getArticleList"
                placeholder="全部"
              >
                <t-option label="全部" value=""></t-option>
                <t-option
                  v-for="item in (dictStore.dictList?.cyt_artical_status || [])"
                  :key="item.code"
                  :label="item.codeval"
                  :value="item.code"
                >
                </t-option>
              </t-select>
            </t-form-item>
          </t-col>
          <t-col :span="2">
            <t-form-item label="置顶" name="topFlag">
              <t-select
                size="small"
                v-model="queryInfo.topFlag"
                @change="getArticleList"
                placeholder="全部"
              >
                <t-option label="全部" value=""></t-option>
                <t-option label="未置顶" :value="0"></t-option>
                <t-option label="已置顶" :value="1"></t-option>
              </t-select>
            </t-form-item>
          </t-col>
          <t-col :span="2">
            <t-form-item label="精华" name="eliteFlag">
              <t-select
                size="small"
                v-model="queryInfo.eliteFlag"
                @change="getArticleList"
                placeholder="全部"
              >
                <t-option label="全部" value=""></t-option>
                <t-option label="未精华" :value="0"></t-option>
                <t-option label="已精华" :value="1"></t-option>
              </t-select>
            </t-form-item>
          </t-col>
          <t-col :span="2">
            <t-form-item label="广场" name="onStage">
              <t-select
                size="small"
                v-model="queryInfo.onStage"
                @change="getArticleList"
                placeholder="全部"
              >
                <t-option label="全部" value=""></t-option>
                <t-option label="未上广场" :value="0"></t-option>
                <t-option label="上广场" :value="1"></t-option>
              </t-select>
            </t-form-item>
          </t-col>
        </t-row>
      </t-form>
      <CustomTable rowKey="id"
        :data="articles"
        size="small"
        stripe
        height="calc(100vh - 360px)"
        :loading="loading">
        <TableColumn colKey="category" label="板块" width="100">
          <template #default="scope">
            <t-tag
              v-for="item in (dictStore.dictList?.cyt_artical_category || [])"
              size="small"
              :key="item.code"
              :theme="scope.row.category == 1 ? 'danger' : 'default'"
              :style="scope.row.category == item.code ? '' : 'display:none'"
              variant="light"
              >{{ item.codeval }}</t-tag
            >
          </template>
        </TableColumn>
        <TableColumn colKey="title" label="标题">
          <template #default="scope">
            <t-link theme="primary" @click="viewArticle(scope.row)">{{
              scope.row.title
            }}</t-link>
          </template>
        </TableColumn>
        <TableColumn label="状态(置顶、精华、广场)" width="180">
          <template #default="scope">
            <t-tag
              style="cursor: pointer; margin-right: 3px"
              size="small"
              @click="setTopAndSoOn('top', scope.row.id, scope.row.topFlag)"
              :theme="scope.row.topFlag ? 'success' : 'default'"
              variant="light"
              >置顶</t-tag
            >
            <t-tag
              style="cursor: pointer; margin-right: 3px"
              size="small"
              @click="setTopAndSoOn('elite', scope.row.id, scope.row.eliteFlag)"
              :theme="scope.row.eliteFlag ? 'danger' : 'default'"
              variant="light"
              >精华</t-tag
            >
            <t-tag
              style="cursor: pointer; margin-right: 3px"
              size="small"
              @click="setTopAndSoOn('stage', scope.row.id, scope.row.onStage)"
              :theme="scope.row.onStage ? 'warning' : 'default'"
              variant="light"
              >广场</t-tag>
            >
          </template>
        </TableColumn>
        <TableColumn label="审核状态" width="100">
          <template #default="scope">
            <t-tag
              @click="examineItem(scope.row)"
              v-for="item in (dictStore.dictList?.cyt_artical_status || [])"
              size="small"
              :key="item.code"
              :theme="scope.row.status == 1 ? 'success' : 'warning'"
              :style="scope.row.status == item.code ? '' : 'display:none'"
              variant="light"
              >{{ item.codeval }}</t-tag
            >
          </template>
        </TableColumn>
        <TableColumn colKey="pubDate" label="发布日期" width="160">
        </TableColumn>
        <TableColumn label="操作" width="150">
          <template #default="scope">
            <t-space>
            <t-button
              variant="outline"
              theme="primary"
              size="small"
              @click="examineItem(scope.row)"
              >审核</t-button
            >
            <t-button
              variant="outline"
              theme="warning"
              size="small"
              @click="updateCategory(scope.row)"
              >移版</t-button
            >
            </t-space>
          </template>
        </TableColumn>
      </CustomTable>
      <t-pagination
        @current-change="handleCurrentChange"
        @page-size-change="handleSizeChange"
        v-model="currentPage"
        :page-size="queryInfo.pageSize"
        :page-size-options="pageSizes"
        :total="total"
      >
      </t-pagination>
    </div>

    <t-dialog
      header="审核"
      v-model:visible="examineDialogVisible"
      width="30%"
      :before-close="examineHandleClose"
    >
      <div>
        审核结果：
        <t-select size="small" v-model="examineForm.status">
          <t-option
            v-for="item in (dictStore.dictList?.cyt_artical_status || [])"
            :disabled="item.code == 2"
            :key="item.code"
            :label="item.codeval"
            :value="parseInt(item.code)"
          ></t-option>
        </t-select>
      </div>
      <template #footer>
        <t-space>
          <t-button variant="outline" size="small" @click="examineDialogVisible = false"
            >取 消</t-button
          >
          <t-button variant="outline" size="small" theme="primary" @click="examineSubmit"
            >确 定</t-button
          >
        </t-space>
      </template>
    </t-dialog>

    <t-dialog header="移动分类" v-model:visible="categoryDialogVisible" width="30%">
      <div>
        移动到分类：
        <t-select size="small" v-model="categoryForm.category">
          <t-option
            v-for="item in (dictStore.dictList?.cyt_artical_category || []).filter(
              (item) => item.status === 1
            )"
            :key="item.code"
            :label="item.codeval"
            :value="parseInt(item.code)"
          ></t-option>
        </t-select>
      </div>
      <template #footer>
        <t-space>
          <t-button variant="outline" size="small" @click="categoryDialogVisible = false"
            >取 消</t-button
          >
          <t-button variant="outline" theme="primary" size="small" @click="categorySubmit"
            >确 定</t-button
          >
        </t-space>
      </template>
    </t-dialog>
  </t-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { MessagePlugin, LoadingPlugin } from 'tdesign-vue-next'
import { useRouter } from 'vue-router'
import { usePagination } from '@/hooks/usePagination'
import {
  getArticleList as fetchArticleList,
  setArticleStatus,
  examineArticle,
  updateArticleCategory
} from '@/api/cyt/index.js'
import { useDictStore } from '@/stores'
const dictStore = useDictStore()
const router = useRouter()

const articles = ref([])
const loading = ref(false)
const total = ref(0)
const yearPicker = ref(new Date())

const queryInfo = reactive({
  dateYear: '', // 查询年份
  category: -1,
  topFlag: '',
  eliteFlag: '',
  onStage: '',
  sortType: 'time',
  status: '',
  querytype: '',
  query: '',
  pageSize: 20,
  pageNum: 1
})

// 审核窗口
const examineDialogVisible = ref(false)
const examineForm = reactive({
  id: '',
  status: 0
})

const categoryDialogVisible = ref(false)
const categoryForm = reactive({
  id: '',
  category: ''
})

const getArticleList = async () => {
  try {
    loading.value = true
    if (yearPicker.value != null && yearPicker.value !== '') {
      queryInfo.dateYear = yearPicker.value.getFullYear()
    }
    const res = await fetchArticleList(queryInfo.category, queryInfo)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    articles.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    console.error('获取文章列表失败:', error)
    MessagePlugin.error('获取文章列表失败')
  } finally {
    loading.value = false
  }
}

const viewArticle = (row) => {
  const url = router.resolve({ path: '/cyt/item/' + row.id })
  window.open(url.href, '_blank')
}

const setTopAndSoOn = async (type, id, val) => {
  try {
    let newVal = val === 1 ? 0 : 1
    const res = await setArticleStatus(type, id, newVal)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    MessagePlugin.success(res.msg)
    getArticleList()
  } catch (error) {
    console.error('设置文章状态失败:', error)
    MessagePlugin.error('设置文章状态失败')
  }
}

const { currentPage, pageSizes, handleCurrentChange, handleSizeChange } = usePagination({
  query: queryInfo,
  fetch: getArticleList,
  pageSizes: [20, 100, 500]
})

const examineItem = (row) => {
  examineForm.id = row.id
  examineForm.status = row.status
  examineDialogVisible.value = true
}

const examineSubmit = async () => {
  try {
    const res = await examineArticle(examineForm)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    MessagePlugin.success(res.msg)
    examineDialogVisible.value = false
    getArticleList()
  } catch (error) {
    console.error('审核文章失败:', error)
    MessagePlugin.error('审核文章失败')
  }
}

const examineHandleClose = () => {
  examineForm.id = ''
  examineForm.status = 0
  examineDialogVisible.value = false
}

const updateCategory = (row) => {
  categoryDialogVisible.value = true
  categoryForm.id = row.id
  categoryForm.category = row.category
}

const categorySubmit = async () => {
  try {
    const res = await updateArticleCategory(categoryForm)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    MessagePlugin.success(res.msg)
    categoryDialogVisible.value = false
    getArticleList()
  } catch (error) {
    console.error('移动文章分类失败:', error)
    MessagePlugin.error('移动文章分类失败')
  }
}

onMounted(() => {
  getArticleList()
})
</script>

<style lang="less" scoped>
.management-card {
  height: calc(100vh - 190px);
  overflow: auto;
}

.t-link {
  font-size: 12px;
}
</style>
