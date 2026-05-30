<template>
  <div>
    <el-card class="main-container">
      <el-row style="padding-bottom: 10px" :gutter="15">
        <el-col :span="4">
          <!-- <el-select v-model="queryInfo.groupId" size="small" placeholder="请选择过滤规则" @change="listDailyConfig">
            <el-option label="生效" value="1"></el-option>
            <el-option label="失效" value="0"></el-option>
          </el-select> -->
        </el-col>
        <el-col :span="10">
          <!-- <el-input placeholder="输入要搜索的内容" size="small" v-model="queryInfo.query">
            <el-select v-model="queryInfo.querytype" slot="prepend" style="width:100px" placeholder="请选择">
            <el-option label="题目" value="quesStem"></el-option>
            <el-option label="业务类型" value="category"></el-option>
            <el-option label="题目编号" value="quesCode"></el-option>
            </el-select>
            <el-button slot="append" icon="Search"></el-button>
          </el-input> -->
        </el-col>
        <el-col :span="4">
          <el-button
            type="primary"
            size="small"
            @click="manageDailyConfig(null)"
            >新增配置</el-button
          >
        </el-col>
        <el-col :span="4">
          <!-- <el-button type="primary" size="small" @click="userListBatchBtn()">名单批量管理</el-button> -->
        </el-col>
      </el-row>
      <el-table
        :data="tableData"
        size="small"
        stripe
        style="width: 100%"
        height="calc(100vh - 300px)"
        @sort-change="tableSort"
      >
        <el-table-column
          label="答题日期"
          prop="quesDate"
          sortable="custom"
          width="140"
        ></el-table-column>
        <el-table-column prop="groupName" label="分组名称">
          <template #default="scope">
            <el-select
              multiple
              v-model="scope.row.groupId"
              :disabled="true"
              size="small"
            >
              <el-option label="不限制分组" value=""></el-option>
              <el-option
                v-for="item in groupList"
                :key="item.groupId"
                :label="item.groupName"
                :value="item.groupId"
              ></el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column
          prop="optionRand"
          label="选项随机"
          sortable="custom"
          width="140"
        >
          <template #default="scope">{{
            dictStore.getDictLabel('yes_or_not', scope.row.optionRand)
          }}</template>
        </el-table-column>
        <el-table-column
          label="题目编号"
          prop="quesCode"
          sortable="custom"
          width="140"
        >
          <template #default="scope">
            <text>{{
              scope.row.quesCode ? scope.row.quesCode : '未配置'
            }}</text>
          </template>
        </el-table-column>
        <el-table-column prop="articalId" label="公布栏配置">
          <template #default="scope">
            <el-button
              :type="scope.row.articalId ? 'success' : 'danger'"
              size="small"
              @click="manageArticalBtn(scope.row)"
              link
              >{{
                scope.row.articalId ? '已配置[修改]' : '未配置[新增]'
              }}</el-button
            >
          </template>
        </el-table-column>
        <el-table-column prop="handleDate" label="操作" width="100">
          <template #default="scope">
            <el-button
              type="primary"
              icon="Edit"
              size="small"
              @click="manageDailyConfig(scope.row)"
            ></el-button>
            <el-button
              type="danger"
              icon="Delete"
              size="small"
              @click="handleDeleteDailyConfig(scope.row.id)"
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pageNum"
        :page-sizes="pageSizes"
        :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </el-card>

    <!-- 配置维护对话框 -->
    <el-dialog
      title="配置维护"
      v-model="dailyConfigVisible"
      width="60%"
      :close-on-click-modal="false"
    >
      <el-form
        :model="form"
        :rules="rules"
        size="small"
        ref="formRef"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="分组ID" prop="groupId">
          <el-select
            v-model="form.groupId"
            multiple
            collapse-tags
            style="width: 100%"
            placeholder="若选[不限制分组]，禁止多选"
          >
            <el-option label="不限制分组" value=""></el-option>
            <el-option
              v-for="item in groupList"
              :key="item.groupId"
              :label="item.groupName"
              :value="item.groupId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="答题日期" prop="quesDate">
          <el-date-picker
            v-model="form.quesDate"
            value-format="YYYY-MM-DD"
            style="width: 100%"
            type="date"
            placeholder="选择日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="试题" prop="quesCode">
          <el-input v-model="form.quesCode" readonly>
            <template #append>
              <el-button
                icon="search"
                @click="openQuesSelectDialog"
              ></el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="选项随机" prop="optionRand">
          <el-select v-model="form.optionRand" placeholder="请选择">
            <el-option
              v-for="item in dictStore.dictList.yes_or_not"
              :key="item.code"
              :label="item.codeval"
              :value="parseInt(item.code)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="公布栏配置" prop="quesCode" v-if="form.articalId">
          <el-input v-model="form.articalId" readonly>
            <el-button
              slot="append"
              icon="Delete"
              @click="() => (form.articalId = null)"
            ></el-button>
          </el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button size="small" @click="dailyConfigVisible = false"
            >关 闭</el-button
          >
          <el-button type="primary" size="small" @click="submit"
            >提 交</el-button
          >
        </span>
      </template>
    </el-dialog>

    <!-- 题目选择对话框 -->
    <QuesSelectDialog
      v-model:visible="quesSelectDialogVisible"
      @select="handleQuesSelect"
    />

    <!-- 公布栏文章配置对话框 -->
    <ArticalForm
      v-model:visible="articalFormVisible"
      :articalData="articalForm"
      @success="handleArticalSuccess"
    />
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage } from 'element-plus'
import QuesSelectDialog from './components/QuesSelectDialog.vue'
import ArticalForm from './components/ArticalForm.vue'
import { dailyConfigApi } from '@/api/edu/dailyConfig'
import { useDictStore } from '@/stores'
const dictStore = useDictStore()
// Reactive data
const queryInfo = reactive({
  groupId: '',
  querytype: '',
  query: '',
  orderType: ' desc',
  order: ' quesDate ',
  pageSize: 20,
  pageNum: 1
})

const tableData = ref([])
const groupList = ref([])
const pageSizes = [20, 100, 500]
const total = ref(0)
const dailyConfigVisible = ref(false)
const quesSelectDialogVisible = ref(false)
const articalFormVisible = ref(false)

const formRef = ref(null)
const form = reactive({
  groupId: '',
  articalId: null,
  quesDate: '',
  quesCode: '',
  optionRand: ''
})

const rules = reactive({
  quesDate: { required: true, message: '答题日期不可为空！', trigger: 'blur' },
  optionRand: { required: true, message: '选项是否随机！', trigger: 'blur' }
})

const curentOperateType = ref('')

const articalForm = reactive({
  id: null,
  articalType: 9,
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
const listDailyConfig = async () => {
  const res = await dailyConfigApi.getDailyConfigList(queryInfo)
  if (res.code !== 200) return ElMessage.error(res.msg)
  total.value = res.data.total
  tableData.value = res.data.list
  tableData.value.forEach((item) => {
    item.groupId
      ? (item.groupId = item.groupId.split(','))
      : (item.groupId = [''])
  })
}

const listBrushConfig = async () => {
  const res = await dailyConfigApi.getBrushConfigList()
  if (res.code !== 200) return ElMessage.error(res.msg)
  groupList.value = res.data.list
}

const handleSizeChange = (pageSize) => {
  queryInfo.pageSize = pageSize
  listDailyConfig()
}

const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  listDailyConfig()
}

const tableSort = (data) => {
  if (data.order === 'ascending') queryInfo.orderType = ' asc '
  else if (data.order === 'descending') queryInfo.orderType = ' desc '
  queryInfo.order = data.prop
  listDailyConfig()
}

const manageDailyConfig = (row) => {
  if (row == null) {
    Object.assign(form, {
      groupId: '',
      articalId: null,
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
  if (!valid) return

  try {
    let res
    if (curentOperateType.value === 'add') {
      res = await dailyConfigApi.addDailyConfig(form)
    } else if (curentOperateType.value === 'edit') {
      res = await dailyConfigApi.updateDailyConfig(form)
    }

    if (res.code !== 200) return ElMessage.error(res.msg)
    ElMessage.success(res.msg)
    dailyConfigVisible.value = false
    listDailyConfig()
  } catch (error) {
    ElMessage.error('操作失败，请重试')
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
  if (res.code !== 200) return ElMessage.error(res.msg)
  ElMessage.success(res.msg)
  listDailyConfig()
}

const manageArticalBtn = async (row) => {
  if (row.articalId) {
    const res = await dailyConfigApi.getArticalDetail(row.articalId)
    if (res.code !== 200) return ElMessage.error(res.msg)
    Object.assign(articalForm, res.data)
  } else {
    Object.assign(articalForm, {
      id: null,
      articalType: 9,
      category: 2,
      title: '',
      content: '',
      status: 1
    })
    Object.assign(form, row)
  }
  articalFormVisible.value = true
}

const handleArticalSuccess = async (articalId) => {
  if (!form.id) return
  form.articalId = articalId
  const res = await dailyConfigApi.updateDailyConfig(form)
  if (res.code !== 200) return ElMessage.error(res.msg)
  ElMessage.success(res.msg)
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
