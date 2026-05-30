<template>
  <div>
    <el-card class="box-card">
      <div class="text item">
        <div class="table-filter">
          <el-row>
            <el-col :span="6">
              <span class="demonstration"
                >年份
                <el-date-picker
                  size="small"
                  style="width: 70%"
                  @change="getArticalList"
                  v-model="yearPicker"
                  type="year"
                  placeholder="选择年"
                />
              </span>
            </el-col>
            <el-col :span="6">
              <span
                >板块
                <el-select
                  size="small"
                  v-model="queryInfo.category"
                  @change="getArticalList"
                  placeholder="请选择,默认不限制"
                >
                  <el-option :value="-1" label="全部"></el-option>
                  <el-option
                    v-for="item in dictStore.dict.cyt_system_category"
                    :key="item.id"
                    :label="item.codeval"
                    :value="parseInt(item.code)"
                  ></el-option>
                </el-select>
              </span>
            </el-col>
            <el-col :span="6">
              <span
                >状态
                <el-select
                  size="small"
                  v-model="queryInfo.status"
                  @change="getArticalList"
                  placeholder="请选择,默认不限制"
                >
                  <el-option label="全部" value=""></el-option>
                  <el-option
                    v-for="item in dictStore.dict.cyt_artical_status"
                    :key="item.id"
                    :label="item.codeval"
                    :value="item.code"
                  ></el-option>
                </el-select>
              </span>
            </el-col>
          </el-row>

          <el-row style="padding-top: 5px">
            <el-col :span="6">
              <span class="demonstration"
                >置顶
                <el-select
                  size="small"
                  v-model="queryInfo.topFlag"
                  @change="getArticalList"
                  placeholder="请选择,默认不限制"
                >
                  <el-option label="全部" value=""></el-option>
                  <el-option label="未置顶" :value="0"></el-option>
                  <el-option label="已置顶" :value="1"></el-option>
                </el-select>
              </span>
            </el-col>
            <el-col :span="6">
              <span class="demonstration"
                >精华
                <el-select
                  size="small"
                  v-model="queryInfo.eliteFlag"
                  @change="getArticalList"
                  placeholder="请选择,默认不限制"
                >
                  <el-option label="全部" value=""></el-option>
                  <el-option label="未精华" :value="0"></el-option>
                  <el-option label="已精华" :value="1"></el-option>
                </el-select>
              </span>
            </el-col>
            <el-col :span="6">
              <span class="demonstration"
                >广场
                <el-select
                  size="small"
                  v-model="queryInfo.onStage"
                  @change="getArticalList"
                  placeholder="请选择,默认不限制"
                >
                  <el-option label="全部" value=""></el-option>
                  <el-option label="未上广场" :value="0"></el-option>
                  <el-option label="上广场" :value="1"></el-option>
                </el-select>
              </span>
            </el-col>
            <el-col :span="6">
              <el-button
                size="small"
                type="primary"
                style="width: 100%"
                :icon="Plus"
                @click="openNoticeEdit('new')"
                >发起公告</el-button
              >
            </el-col>
          </el-row>
        </div>

        <el-table
          :data="articals"
          size="small"
          stripe
          height="calc(100vh - 360px)"
        >
          <el-table-column prop="category" label="板块" width="100">
            <template #default="scope">
              <el-tag
                v-for="item in dictStore.dict.cyt_system_category"
                size="small"
                :key="item.code"
                :type="scope.row.category == 1 ? 'danger' : 'info'"
                :style="scope.row.category == item.code ? '' : 'display:none'"
                effect="plain"
                >{{ item.codeval }}</el-tag
              >
            </template>
          </el-table-column>

          <el-table-column prop="title" label="标题">
            <template #default="scope">
              <el-link type="info" @click="viewArtical(scope.row)">{{
                scope.row.title
              }}</el-link>
            </template>
          </el-table-column>

          <el-table-column label="状态(置顶、精华、广场)" width="180">
            <template #default="scope">
              <el-tag
                style="cursor: pointer; margin-right: 3px"
                size="small"
                @click="setTopAndSoOn('top', scope.row.id, scope.row.topFlag)"
                :type="scope.row.topFlag ? 'success' : 'info'"
                :effect="scope.row.topFlag ? 'dark' : 'plain'"
                >置顶</el-tag
              >
              <el-tag
                style="cursor: pointer; margin-right: 3px"
                size="small"
                @click="
                  setTopAndSoOn('elite', scope.row.id, scope.row.eliteFlag)
                "
                :type="scope.row.eliteFlag ? 'danger' : 'info'"
                :effect="scope.row.eliteFlag ? 'dark' : 'plain'"
                >精华</el-tag
              >
              <el-tag
                style="cursor: pointer; margin-right: 3px"
                size="small"
                @click="setTopAndSoOn('stage', scope.row.id, scope.row.onStage)"
                :type="scope.row.onStage ? 'warning' : 'info'"
                :effect="scope.row.onStage ? 'dark' : 'plain'"
                >广场</el-tag
              >
            </template>
          </el-table-column>

          <el-table-column label="审核状态" width="100">
            <template #default="scope">
              <el-tag
                @click="examineItem(scope.row)"
                v-for="item in dictStore.dict.cyt_artical_status"
                size="small"
                :key="item.code"
                :type="scope.row.status == 1 ? 'success' : 'warning'"
                :style="scope.row.status == item.code ? '' : 'display:none'"
                effect="plain"
                >{{ item.codeval }}</el-tag
              >
            </template>
          </el-table-column>

          <el-table-column
            prop="pubDate"
            label="发布日期"
            width="160"
          ></el-table-column>

          <el-table-column label="操作" width="150">
            <template #default="scope">
              <el-button
                type="primary"
                size="small"
                @click="examineItem(scope.row)"
                >审核</el-button
              >
              <el-button
                type="warning"
                size="small"
                @click="openNoticeEdit(scope.row.id)"
                >编辑</el-button
              >
            </template>
          </el-table-column>
        </el-table>

        <el-pagination
          @current-change="handleCurrentChange"
          v-model:current-page="currentPage"
          :page-size="queryInfo.pageSize"
          layout="total, prev, pager, next, jumper"
          :total="total"
        ></el-pagination>
      </div>

      <!-- 审核对话框 -->
      <el-dialog
        title="审核"
        v-model="examineDialogVisible"
        width="30%"
        :before-close="examineHandleClose"
      >
        <div>
          审核结果：
          <el-select size="small" v-model="examineForm.status">
            <el-option
              v-for="item in dictStore.dict.cyt_artical_status"
              :disabled="item.code == 2"
              :key="item.id"
              :label="item.codeval"
              :value="parseInt(item.code)"
            ></el-option>
          </el-select>
        </div>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="examineDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="examineSubmit">确 定</el-button>
          </span>
        </template>
      </el-dialog>

      <!-- 移动分类对话框 -->
      <el-dialog title="移动分类" v-model="categoryDialogVisible" width="30%">
        <div>
          移动到分类：
          <el-select size="small" v-model="categoryForm.category">
            <el-option
              v-for="item in dictStore.dict.cyt_artical_category"
              :key="item.id"
              :label="item.codeval"
              :value="parseInt(item.code)"
            ></el-option>
          </el-select>
        </div>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="categoryDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="categorySubmit">确 定</el-button>
          </span>
        </template>
      </el-dialog>
    </el-card>

    <!-- 新的公告 -->
    <el-dialog title="创建新公告" v-model="newNoticeDialog" :fullscreen="true">
      <NewNotice :id="editId"></NewNotice>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
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
      ElMessage.error(res.msg)
      return
    }

    ElMessage.success(res.msg)
    getArticalList()
  } catch (error) {
    ElMessage.error('操作失败')
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
      ElMessage.error(res.msg)
      return
    }

    ElMessage.success(res.msg)
    examineDialogVisible.value = false
    getArticalList()
  } catch (error) {
    ElMessage.error('审核提交失败')
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
      ElMessage.error(res.msg)
      return
    }

    ElMessage.success(res.msg)
    categoryDialogVisible.value = false
    getArticalList()
  } catch (error) {
    ElMessage.error('分类更新失败')
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

  .el-select {
    width: 70%;
  }
}

.el-link {
  font-size: 12px;
}
</style>
