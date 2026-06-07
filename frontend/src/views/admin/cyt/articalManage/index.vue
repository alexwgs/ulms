<template>
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
              ></t-date-picker>
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
                  v-for="item in dictStore.dictList.cyt_artical_category"
                  :key="item.code"
                  :label="item.codeval"
                  :value="parseInt(item.code)"
                >
                </t-option>
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
                  v-for="item in dictStore.dictList.cyt_artical_status"
                  :key="item.code"
                  :label="item.codeval"
                  :value="item.code"
                >
                </t-option>
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
            <!-- 预留按钮位置 -->
          </t-col>
        </t-row>
      </div>
      <CustomTable rowKey="id"
        :data="articals"
        size="small"
        stripe
        height="calc(100vh - 360px)"
        :loading="loading">
        <TableColumn colKey="category" label="板块" width="100">
          <template #default="scope">
            <t-tag
              v-for="item in dictStore.dictList.cyt_artical_category"
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
              @click="setTopAndSoOn('elite', scope.row.id, scope.row.eliteFlag)"
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
              v-for="item in dictStore.dictList.cyt_artical_status"
              size="small"
              :key="item.code"
              :theme="scope.row.status == 1 ? 'success' : 'warning'"
              :style="scope.row.status == item.code ? '' : 'display:none'"
              effect="plain"
              >{{ item.codeval }}</t-tag
            >
          </template>
        </TableColumn>
        <TableColumn colKey="pubDate" label="发布日期" width="160">
        </TableColumn>
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
              @click="updateCategory(scope.row)"
              >移版</t-button
            >
          </template>
        </TableColumn>
      </CustomTable>
      <t-pagination
        @current-change="handleCurrentChange"
        v-model:current="currentPage"
        :page-size="queryInfo.pageSize"

        :total="total"
      >
      </t-pagination>
    </div>

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
            v-for="item in dictStore.dictList.cyt_artical_status"
            :disabled="item.code == 2"
            :key="item.code"
            :label="item.codeval"
            :value="parseInt(item.code)"
          ></t-option>
        </t-select>
      </div>
      <template #footer>
        <div class="dialog-footer">
          <t-button size="small" @click="examineDialogVisible = false"
            >取 消</t-button
          >
          <t-button size="small" theme="primary" @click="examineSubmit"
            >确 定</t-button
          >
        </div>
      </template>
    </t-dialog>

    <t-dialog header="移动分类" v-model:visible="categoryDialogVisible" width="30%">
      <div>
        移动到分类：
        <t-select size="small" v-model="categoryForm.category">
          <t-option
            v-for="item in dictStore.dictList.cyt_artical_category.filter(
              (item) => item.status === 1
            )"
            :key="item.code"
            :label="item.codeval"
            :value="parseInt(item.code)"
          ></t-option>
        </t-select>
      </div>
      <template #footer>
        <div class="dialog-footer">
          <t-button size="small" @click="categoryDialogVisible = false"
            >取 消</t-button
          >
          <t-button theme="primary" size="small" @click="categorySubmit"
            >确 定</t-button
          >
        </div>
      </template>
    </t-dialog>
  </t-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { MessagePlugin, LoadingPlugin } from 'tdesign-vue-next'
import { useRouter } from 'vue-router'
import {
  getArticalList as fetchArticalList,
  setArticalStatus,
  examineArtical,
  updateArticalCategory
} from '@/api/cyt/index.js'
import { useDictStore } from '@/stores'
const dictStore = useDictStore()
const router = useRouter()

const articals = ref([])
const loading = ref(false)
const currentPage = ref(1)
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

const getArticalList = async () => {
  try {
    loading.value = true
    if (yearPicker.value != null && yearPicker.value !== '') {
      queryInfo.dateYear = yearPicker.value.getFullYear()
    }
    const res = await fetchArticalList(queryInfo.category, queryInfo)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    articals.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    console.error('获取文章列表失败:', error)
    MessagePlugin.error('获取文章列表失败')
  } finally {
    loading.value = false
  }
}

const viewArtical = (row) => {
  const url = router.resolve({ path: '/cyt/item/' + row.id })
  window.open(url.href, '_blank')
}

const setTopAndSoOn = async (type, id, val) => {
  try {
    let newVal = val === 1 ? 0 : 1
    const res = await setArticalStatus(type, id, newVal)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    MessagePlugin.success(res.msg)
    getArticalList()
  } catch (error) {
    console.error('设置文章状态失败:', error)
    MessagePlugin.error('设置文章状态失败')
  }
}

const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  currentPage.value = page
  getArticalList()
}

const examineItem = (row) => {
  examineForm.id = row.id
  examineForm.status = row.status
  examineDialogVisible.value = true
}

const examineSubmit = async () => {
  try {
    const res = await examineArtical(examineForm)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    MessagePlugin.success(res.msg)
    examineDialogVisible.value = false
    getArticalList()
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
    const res = await updateArticalCategory(categoryForm)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    MessagePlugin.success(res.msg)
    categoryDialogVisible.value = false
    getArticalList()
  } catch (error) {
    console.error('移动文章分类失败:', error)
    MessagePlugin.error('移动文章分类失败')
  }
}

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
