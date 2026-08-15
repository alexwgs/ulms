<template>
  <div>
    <t-card class="main-container">
      <div class="table-toolbar">
        <div class="toolbar-left">
          <t-button
            variant="outline"
            theme="primary"
            size="small"
            @click="manageDailyConfig(null)"
            >新增配置</t-button
          >
        </div>
      </div>
      <CustomTable rowKey="id"
        :data="tableData"
        size="small"
        stripe
        style="width: 100%"
        height="calc(100vh - 300px)"
        @sort-change="tableSort">
        <TableColumn
          label="答题日期"
          prop="quesDate"
          sortable="custom"
          width="140"></TableColumn>
        <TableColumn colKey="groupName" label="分组名称">
          <template #default="scope">
            <t-select
              multiple
              v-model="scope.row.groupId"
              :disabled="true"
              size="small"
            >
              <t-option label="不限制分组" value=""></t-option>
              <t-option
                v-for="item in groupList"
                :key="item.groupId"
                :label="item.groupName"
                :value="item.groupId"
              ></t-option>
            </t-select>
          </template>
        </TableColumn>
        <TableColumn
          prop="optionRand"
          label="选项随机"
          sortable="custom"
          width="140">
          <template #default="scope">{{
            dictStore.getDictLabel('yes_or_not', scope.row.optionRand)
          }}</template>
        </TableColumn>
        <TableColumn
          label="题目编号"
          prop="quesCode"
          sortable="custom"
          width="140">
          <template #default="scope">
            <text>{{
              scope.row.quesCode ? scope.row.quesCode : '未配置'
            }}</text>
          </template>
        </TableColumn>
        <TableColumn colKey="articleId" label="公布栏配置">
          <template #default="scope">
            <t-button
              :theme="scope.row.articleId ? 'success' : 'danger'"
              size="small"
              @click="manageArticleBtn(scope.row)"
              link
              >{{
                scope.row.articleId ? '已配置[修改]' : '未配置[新增]'
              }}</t-button
            >
          </template>
        </TableColumn>
        <TableColumn colKey="handleDate" label="操作" width="100">
          <template #default="scope">
            <t-button
              variant="outline"
              theme="primary" size="small"
              @click="manageDailyConfig(scope.row)"><template #icon><DynamicIcon name="edit" /></template></t-button>
            <t-button variant="outline"
              theme="danger" size="small"
              @click="handleDeleteDailyConfig(scope.row.id)">删除</t-button>
          </template>
        </TableColumn>
      </CustomTable>
      <t-pagination
        @page-size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current="currentPage"
        :page-size-options="pageSizes"
        :page-size="queryInfo.pageSize"

        :total="total"
      ></t-pagination>
    </t-card>

    <!-- 配置维护对话框 -->
    <t-dialog
      header="配置维护"
      v-model:visible="dailyConfigVisible"
      width="60%"
      :close-on-overlay-click="false"
    >
      <t-form
        :data="form"
        :rules="rules"
        size="small"
        ref="formRef"
        label-width="100px"
        class="demo-ruleForm"
      >
        <t-form-item label="分组ID" name="groupId">
          <t-select
            v-model="form.groupId"
            multiple
            collapse-tags
            style="width: 100%"
            placeholder="若选[不限制分组]，禁止多选"
          >
            <t-option label="不限制分组" value=""></t-option>
            <t-option
              v-for="item in groupList"
              :key="item.groupId"
              :label="item.groupName"
              :value="item.groupId"
            ></t-option>
          </t-select>
        </t-form-item>
        <t-form-item label="答题日期" name="quesDate">
          <t-date-picker
            v-model="form.quesDate"
           
            style="width: 100%"
            mode="date"
            placeholder="选择日期"
          ></t-date-picker>
        </t-form-item>
        <t-form-item label="试题" name="quesCode">
          <t-input-adornment>
            <template #append>
              <t-button variant="outline" theme="primary" @click="openQuesSelectDialog"
              >搜索</t-button>
            </template>
            <t-input v-model="form.quesCode" readonly></t-input>
          </t-input-adornment>
        </t-form-item>
        <t-form-item label="选项随机" name="optionRand">
          <t-select v-model="form.optionRand" placeholder="请选择">
            <t-option
              v-for="item in (dictStore.dictList?.yes_or_not || [])"
              :key="item.code"
              :label="item.codeval"
              :value="parseInt(item.code)"
            ></t-option>
          </t-select>
        </t-form-item>
        <t-form-item label="公布栏配置" name="quesCode" v-if="form.articleId">
          <t-input-adornment>
            <template #append>
              <t-button variant="outline" theme="danger" @click="() => (form.articleId = null)">清除</t-button>
            </template>
            <t-input v-model="form.articleId" readonly></t-input>
          </t-input-adornment>
        </t-form-item>
      </t-form>
      <template #footer>
        <t-space>
          <t-button variant="outline" size="small" @click="dailyConfigVisible = false"
            >关 闭</t-button
          >
          <t-button variant="outline" theme="primary" size="small" @click="submit"
            >提 交</t-button
          >
        </t-space>
      </template>
    </t-dialog>

    <!-- 题目选择对话框 -->
    <QuesSelectDialog
      v-model:visible="quesSelectDialogVisible"
      @select="handleQuesSelect"
    />

    <!-- 公布栏文章配置对话框 -->
    <ArticleForm
      v-model:visible="articleFormVisible"
      :articleData="articleForm"
      @success="handleArticleSuccess"
    />
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import QuesSelectDialog from './components/QuesSelectDialog.vue'
import ArticleForm from './components/ArticleForm.vue'
import { dailyConfigApi } from '@/api/edu/dailyConfig'
import { useDictStore } from '@/stores'
import { useCrudPage } from '@/hooks/useCrudPage'
const dictStore = useDictStore()

// 列表 + 分页（useCrudPage 样板，groupId 逗号拆分后处理）
const {
  list: tableData,
  total,
  query: queryInfo,
  currentPage,
  pageSizes,
  handleCurrentChange,
  handleSizeChange,
  load: listDailyConfig
} = useCrudPage({
  fetchList: (q) => dailyConfigApi.getDailyConfigList(q),
  defaultQuery: {
    groupId: '',
    querytype: '',
    query: '',
    orderType: ' desc',
    order: ' quesDate ',
    pageSize: 20,
    pageNum: 1
  },
  transformList: (list) => list.map((item) => {
    item.groupId = item.groupId ? item.groupId.split(',') : ['']
    return item
  }),
  pageSizes: [20, 100, 500]
})

const groupList = ref([])
const dailyConfigVisible = ref(false)
const quesSelectDialogVisible = ref(false)
const articleFormVisible = ref(false)

const formRef = ref(null)
const form = reactive({
  groupId: '',
  articleId: null,
  quesDate: '',
  quesCode: '',
  optionRand: ''
})

const rules = reactive({
  quesDate: [{ required: true, message: '答题日期不可为空！', trigger: 'blur' }],
  optionRand: [{ required: true, message: '选项是否随机！', trigger: 'blur' }]
})

const curentOperateType = ref('')

const articleForm = reactive({
  id: null,
  articleType: 9,
  category: 2,
  title: '',
  content: '',
  status: 1
})

// Lifecycle
onMounted(() => {
  listBrushConfig()
  listDailyConfig()
})

// Methods
const listBrushConfig = async () => {
  const res = await dailyConfigApi.getBrushConfigList()
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  groupList.value = res.data.list
}

const tableSort = (data) => {
  if (!data.descending) queryInfo.value.orderType = ' asc '
  else if (data.descending) queryInfo.value.orderType = ' desc '
  queryInfo.value.order = data.sortBy
  listDailyConfig()
}

const manageDailyConfig = (row) => {
  if (row == null) {
    Object.assign(form, {
      groupId: '',
      articleId: null,
      quesDate: '',
      quesCode: '',
      optionRand: ''
    })
    curentOperateType.value = 'add'
  } else {
    curentOperateType.value = 'edit'
    Object.assign(form, row)
  }
  dailyConfigVisible.value = true
}

const submit = async () => {
  form.groupId = form.groupId.join(',')
  const valid = await formRef.value.validate()
  if (valid !== true) return

  try {
    let res
    if (curentOperateType.value === 'add') {
      res = await dailyConfigApi.addDailyConfig(form)
    } else if (curentOperateType.value === 'edit') {
      res = await dailyConfigApi.updateDailyConfig(form)
    }

    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    dailyConfigVisible.value = false
    listDailyConfig()
  } catch (error) {
    MessagePlugin.error('操作失败，请重试')
  }
}

const openQuesSelectDialog = () => {
  quesSelectDialogVisible.value = true
}

const handleQuesSelect = (row) => {
  form.quesCode = row.quesCode
  quesSelectDialogVisible.value = false
}

const handleDeleteDailyConfig = async (id) => {
  const res = await dailyConfigApi.deleteDailyConfig(id)
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  MessagePlugin.success(res.msg)
  listDailyConfig()
}

const manageArticleBtn = async (row) => {
  if (row.articleId) {
    const res = await dailyConfigApi.getArticleDetail(row.articleId)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    Object.assign(articleForm, res.data)
  } else {
    Object.assign(articleForm, {
      id: null,
      articleType: 9,
      category: 2,
      title: '',
      content: '',
      status: 1
    })
    Object.assign(form, row)
  }
  articleFormVisible.value = true
}

const handleArticleSuccess = async (articleId) => {
  if (!form.id) return
  form.articleId = articleId
  const res = await dailyConfigApi.updateDailyConfig(form)
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  MessagePlugin.success(res.msg)
  listDailyConfig()
}
</script>

<style lang="less" scoped>
.main-container {
  height: calc(100vh - 185px);
}
.tree-box {
  height: 450px;
  overflow: auto;
}
.editor-content {
  height: 400px;
}
</style>
