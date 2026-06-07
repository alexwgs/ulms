<template>
  <div>
    <t-card class="box-card">
      <div class="text item">
        <div class="table-filter">
          <t-row>
            <t-col :span="3">
              <span class="demonstration"
                >年份
                <t-date-picker
                  size="small"
                  style="width: 70%"
                  @change="getArticalList"
                  v-model="yearPicker"
                  mode="year"
                  placeholder="选择年"
                />
              </span>
            </t-col>
            <t-col :span="3">
              <span
                >板块
                <t-select
                  size="small"
                  v-model="queryInfo.category"
                  @change="getArticalList"
                  placeholder="请选择,默认不限制"
                >
                  <t-option :value="-1" label="全部"></t-option>
                  <t-option
                    v-for="item in dictStore.dict.cyt_system_category"
                    :key="item.id"
                    :label="item.codeval"
                    :value="parseInt(item.code)"
                  ></t-option>
                </t-select>
              </span>
            </t-col>
            <t-col :span="3">
              <span
                >状态
                <t-select
                  size="small"
                  v-model="queryInfo.status"
                  @change="getArticalList"
                  placeholder="请选择,默认不限制"
                >
                  <t-option label="全部" value=""></t-option>
                  <t-option
                    v-for="item in dictStore.dict.cyt_artical_status"
                    :key="item.id"
                    :label="item.codeval"
                    :value="item.code"
                  ></t-option>
                </t-select>
              </span>
            </t-col>
          </t-row>

          <t-row style="padding-top: 5px">
            <t-col :span="3">
              <span class="demonstration"
                >置顶
                <t-select
                  size="small"
                  v-model="queryInfo.topFlag"
                  @change="getArticalList"
                  placeholder="请选择,默认不限制"
                >
                  <t-option label="全部" value=""></t-option>
                  <t-option label="未置顶" :value="0"></t-option>
                  <t-option label="已置顶" :value="1"></t-option>
                </t-select>
              </span>
            </t-col>
            <t-col :span="3">
              <span class="demonstration"
                >精华
                <t-select
                  size="small"
                  v-model="queryInfo.eliteFlag"
                  @change="getArticalList"
                  placeholder="请选择,默认不限制"
                >
                  <t-option label="全部" value=""></t-option>
                  <t-option label="未精华" :value="0"></t-option>
                  <t-option label="已精华" :value="1"></t-option>
                </t-select>
              </span>
            </t-col>
            <t-col :span="3">
              <span class="demonstration"
                >广场
                <t-select
                  size="small"
                  v-model="queryInfo.onStage"
                  @change="getArticalList"
                  placeholder="请选择,默认不限制"
                >
                  <t-option label="全部" value=""></t-option>
                  <t-option label="未上广场" :value="0"></t-option>
                  <t-option label="上广场" :value="1"></t-option>
                </t-select>
              </span>
            </t-col>
            <t-col :span="3">
              <t-button
                size="small"
                theme="primary"
                style="width: 100%"@click="openNoticeEdit('new')"
                ><template #icon><DynamicIcon name="add" /></template>发起公告</t-button
              >
            </t-col>
          </t-row>
        </div>

        <CustomTable rowKey="id"
          :data="articals"
          size="small"
          stripe
          height="calc(100vh - 360px)">
          <TableColumn colKey="category" label="板块" width="100">
            <template #default="scope">
              <t-tag
                v-for="item in dictStore.dict.cyt_system_category"
                size="small"
                :key="item.code"
                :theme="scope.row.category == 1 ? 'danger' : 'info'"
                :style="scope.row.category == item.code ? '' : 'display:none'"
                effect="plain"
                >{{ item.codeval }}</t-tag
              >
            </template>
          </TableColumn>

          <TableColumn colKey="title" label="标题">
            <template #default="scope">
              <t-link theme="primary" @click="viewArtical(scope.row)">{{
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
                :theme="scope.row.topFlag ? 'success' : 'info'"
                :effect="scope.row.topFlag ? 'dark' : 'plain'"
                >置顶</t-tag
              >
              <t-tag
                style="cursor: pointer; margin-right: 3px"
                size="small"
                @click="
                  setTopAndSoOn('elite', scope.row.id, scope.row.eliteFlag)
                "
                :theme="scope.row.eliteFlag ? 'danger' : 'info'"
                :effect="scope.row.eliteFlag ? 'dark' : 'plain'"
                >精华</t-tag
              >
              <t-tag
                style="cursor: pointer; margin-right: 3px"
                size="small"
                @click="setTopAndSoOn('stage', scope.row.id, scope.row.onStage)"
                :theme="scope.row.onStage ? 'warning' : 'info'"
                :effect="scope.row.onStage ? 'dark' : 'plain'"
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
                effect="plain"
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
              <t-button
                theme="primary"
                size="small"
                @click="examineItem(scope.row)"
                >审核</t-button
              >
              <t-button
                theme="warning"
                size="small"
                @click="openNoticeEdit(scope.row.id)"
                >编辑</t-button
              >
            </template>
          </TableColumn>
        </CustomTable>

        <t-pagination
          @current-change="handleCurrentChange"
          v-model:current="currentPage"
          :page-size="queryInfo.pageSize"

          :total="total"
        ></t-pagination>
      </div>

      <!-- 审核对话框 -->
      <t-dialog
        header="审核"
        v-model:visible="examineDialogVisible"
        width="30%"
        @before-close="examineHandleClose"
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
          <span class="dialog-footer">
            <t-button @click="examineDialogVisible = false">取 消</t-button>
            <t-button theme="primary" @click="examineSubmit">确 定</t-button>
          </span>
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
          <span class="dialog-footer">
            <t-button @click="categoryDialogVisible = false">取 消</t-button>
            <t-button theme="primary" @click="categorySubmit">确 定</t-button>
          </span>
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
const articals = ref([])
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
const getArticalList = async () => {
  try {
    if (yearPicker.value != null && yearPicker.value !== '') {
      queryInfo.dateYear = yearPicker.value.getFullYear()
    }

    // 使用聚合后的API
    const res = await manageNoticeApi.getArticalList(
      `cyt/articalList/9/${queryInfo.category}`,
      queryInfo
    )
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }

    articals.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    MessagePlugin.error('获取文章列表失败')
    console.error(error)
  }
}

// 查看文章
const viewArtical = (row) => {
  const url = router.resolve({ path: '/cyt/item/' + row.id })
  window.open(url.href, '_blank')
}

// 设置置顶、精华、广场状态
const setTopAndSoOn = async (type, id, val) => {
  try {
    const newValue = val === 1 ? 0 : 1
    // 使用聚合后的API
    const res = await manageNoticeApi.setTopAndSoOn(
      `cyt/artical/on/${type}/${id}/${newValue}`
    )
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }

    MessagePlugin.success(res.msg)
    getArticalList()
  } catch (error) {
    MessagePlugin.error('操作失败')
    console.error(error)
  }
}

// 分页变化
const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  getArticalList()
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
    const res = await manageNoticeApi.examineArtical(
      'cyt/artical/on/examine',
      examineForm
    )
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }

    MessagePlugin.success(res.msg)
    examineDialogVisible.value = false
    getArticalList()
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
      'cyt/artical/category',
      categoryForm
    )
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }

    MessagePlugin.success(res.msg)
    categoryDialogVisible.value = false
    getArticalList()
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
  getArticalList()
})
</script>

<style lang="less" scoped>
.box-card {
  height: calc(100vh - 190px);
  overflow: auto;
}

.table-filter {
  padding: 10px;

  span {
    font-size: 12px;
    margin-left: 20px;
  }

  .t-select {
    width: 70%;
  }
}

.t-link {
  font-size: 12px;
}
</style>
