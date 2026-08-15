<template>
  <div>
    <t-card class="management-card">
      <div class="text item">
        <t-form :data="queryInfo" label-width="80px" colon class="filter-form">
          <t-row :gutter="[24, 24]">
            <t-col :span="3">
              <t-form-item label="年份" name="dateYear">
                <t-date-picker
                  size="small"
                  @change="getArticleList"
                  v-model="yearPicker"
                  mode="year"
                  placeholder="选择年"
                />
              </t-form-item>
            </t-col>
            <t-col :span="3">
              <t-form-item label="板块" name="category">
                <t-select
                  size="small"
                  v-model="queryInfo.category"
                  @change="getArticleList"
                  placeholder="全部"
                >
                  <t-option :value="-1" label="全部"></t-option>
                  <t-option
                    v-for="item in dictStore.dict.cyt_system_category"
                    :key="item.id"
                    :label="item.codeval"
                    :value="parseInt(item.code)"
                  ></t-option>
                </t-select>
              </t-form-item>
            </t-col>
            <t-col :span="3">
              <t-form-item label="状态" name="status">
                <t-select
                  size="small"
                  v-model="queryInfo.status"
                  @change="getArticleList"
                  placeholder="全部"
                >
                  <t-option label="全部" value=""></t-option>
                  <t-option
                    v-for="item in dictStore.dict.cyt_artical_status"
                    :key="item.id"
                    :label="item.codeval"
                    :value="item.code"
                  ></t-option>
                </t-select>
              </t-form-item>
            </t-col>
            <t-col :span="3">
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
            <t-col :span="3">
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
            <t-col :span="3">
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
            <t-col :span="3" class="operation-container">
              <t-button variant="outline" size="small" theme="primary" @click="openNoticeEdit('new')"><template #icon><DynamicIcon name="add" /></template>发起公告</t-button>
            </t-col>
          </t-row>
        </t-form>

        <CustomTable rowKey="id"
          :data="articles"
          size="small"
          stripe
          height="calc(100vh - 360px)">
          <TableColumn colKey="category" label="板块" width="100">
            <template #default="scope">
              <t-tag
                v-for="item in dictStore.dict.cyt_system_category"
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
                @click="
                  setTopAndSoOn('elite', scope.row.id, scope.row.eliteFlag)
                "
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
                >广场</t-tag
              >
            </template>
          </TableColumn>

          <TableColumn label="审核状态" width="100">
            <template #default="scope">
              <t-tag
                @click="examineItem(scope.row)"
                v-for="item in dictStore.dict.cyt_artical_status"
                size="small"
                :key="item.code"
                :theme="scope.row.status == 1 ? 'success' : 'warning'"
                :style="scope.row.status == item.code ? '' : 'display:none'"
                variant="light"
                >{{ item.codeval }}</t-tag
              >
            </template>
          </TableColumn>

          <TableColumn
            prop="pubDate"
            label="发布日期"
            width="160"></TableColumn>

          <TableColumn label="操作" width="150">
            <template #default="scope">
              <t-button variant="outline" theme="primary" size="small" @click="examineItem(scope.row)">审核</t-button>
              <t-button variant="outline" theme="warning" size="small" @click="openNoticeEdit(scope.row.id)">编辑</t-button>
            </template>
          </TableColumn>
        </CustomTable>

        <t-pagination
          @current-change="handleCurrentChange"
          v-model="currentPage"
          :page-size="queryInfo.pageSize"

          :total="total"
        ></t-pagination>
      </div>

      <!-- 审核对话框 -->
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
              v-for="item in dictStore.dict.cyt_artical_status"
              :disabled="item.code == 2"
              :key="item.id"
              :label="item.codeval"
              :value="parseInt(item.code)"
            ></t-option>
          </t-select>
        </div>
        <template #footer>
          <t-space>
            <t-button variant="outline" @click="examineDialogVisible = false">取消</t-button>
            <t-button variant="outline" theme="primary" @click="examineSubmit">确定</t-button>
          </t-space>
        </template>
      </t-dialog>

      <!-- 移动分类对话框 -->
      <t-dialog header="移动分类" v-model:visible="categoryDialogVisible" width="30%">
        <div>
          移动到分类：
          <t-select size="small" v-model="categoryForm.category">
            <t-option
              v-for="item in dictStore.dict.cyt_artical_category"
              :key="item.id"
              :label="item.codeval"
              :value="parseInt(item.code)"
            ></t-option>
          </t-select>
        </div>
        <template #footer>
          <t-space>
            <t-button variant="outline" @click="categoryDialogVisible = false">取消</t-button>
            <t-button variant="outline" theme="primary" @click="categorySubmit">确定</t-button>
          </t-space>
        </template>
      </t-dialog>
    </t-card>

    <!-- 新的公告 -->
    <t-dialog header="创建新公告" v-model:visible="newNoticeDialog" mode="full-screen">
      <NewNotice :id="editId"></NewNotice>
    </t-dialog>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { MessagePlugin } from 'tdesign-vue-next'
import { AddIcon } from 'tdesign-icons-vue-next'
import NewNotice from './components/NewNotice.vue'
import { manageNoticeApi } from '@/api/admin/manageNotice' // 导入聚合后的API
import { useDictStore } from '@/stores'

const dictStore = useDictStore()
// 路由
const router = useRouter()

// 数据
const articles = ref([])
const currentPage = ref(1)
const total = ref(0)
const yearPicker = ref(new Date())

// 查询参数
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

// 分类窗口
const categoryDialogVisible = ref(false)
const categoryForm = reactive({
  id: '',
  category: ''
})

// 新公告窗口
const newNoticeDialog = ref(false)
const editId = ref('')

// 获取文章列表
const getArticleList = async () => {
  try {
    if (yearPicker.value != null && yearPicker.value !== '') {
      queryInfo.dateYear = yearPicker.value.getFullYear()
    }

    // 使用聚合后的API
    const res = await manageNoticeApi.getArticleList(
      `cyt/articleList/9/${queryInfo.category}`,
      queryInfo
    )
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

// 查看文章
const viewArticle = (row) => {
  const url = router.resolve({ path: '/cyt/item/' + row.id })
  window.open(url.href, '_blank')
}

// 设置置顶、精华、广场状态
const setTopAndSoOn = async (type, id, val) => {
  try {
    const newValue = val === 1 ? 0 : 1
    // 使用聚合后的API
    const res = await manageNoticeApi.setTopAndSoOn(
      `cyt/article/on/${type}/${id}/${newValue}`
    )
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }

    MessagePlugin.success(res.msg)
    getArticleList()
  } catch (error) {
    MessagePlugin.error('操作失败')
    console.error(error)
  }
}

// 分页变化
const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  getArticleList()
}

// 审核项目
const examineItem = (row) => {
  examineForm.id = row.id
  examineForm.status = row.status
  examineDialogVisible.value = true
}

// 提交审核
const examineSubmit = async () => {
  try {
    // 使用聚合后的API
    const res = await manageNoticeApi.examineArticle(
      'cyt/article/on/examine',
      examineForm
    )
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }

    MessagePlugin.success(res.msg)
    examineDialogVisible.value = false
    getArticleList()
  } catch (error) {
    MessagePlugin.error('审核提交失败')
    console.error(error)
  }
}

// 关闭审核窗口
const examineHandleClose = () => {
  examineForm.id = ''
  examineForm.status = 0
  examineDialogVisible.value = false
}

// 更新分类
const updateCategory = (index, row) => {
  categoryDialogVisible.value = true
  categoryForm.id = row.id
  categoryForm.category = row.category
}

// 提交分类更新
const categorySubmit = async () => {
  try {
    // 使用聚合后的API
    const res = await manageNoticeApi.updateCategory(
      'cyt/article/category',
      categoryForm
    )
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }

    MessagePlugin.success(res.msg)
    categoryDialogVisible.value = false
    getArticleList()
  } catch (error) {
    MessagePlugin.error('分类更新失败')
    console.error(error)
  }
}

// 打开公告编辑
const openNoticeEdit = (id) => {
  editId.value = id
  newNoticeDialog.value = true
}

// 组件挂载时获取数据
onMounted(() => {
  getArticleList()
})
</script>

<style lang="less" scoped>
.management-card {
  height: calc(100vh - 190px);
  overflow: auto;
}

.filter-actions {
  display: flex;
  justify-content: flex-end;
}

.t-link {
  font-size: 12px;
}
</style>
