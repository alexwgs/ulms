<template>
  <div>
    <el-card class="main-container">
      <el-row style="padding-bottom: 10px" :gutter="15">
        <el-col :span="14">
          <el-input
            placeholder="输入要搜索的内容"
            size="small"
            v-model="queryInfo.query"
          >
            <template #prepend>
              <el-select
                v-model="queryInfo.querytype"
                size="small"
                style="width: 100px"
                placeholder="请选择"
              >
                <el-option label="分组名称" value="groupName"></el-option>
              </el-select>
            </template>
            <template #append>
              <el-button icon="Search" size="small"></el-button>
            </template>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button
            type="primary"
            size="small"
            @click="manageBrushConfig(null)"
            >新增配置</el-button
          >
        </el-col>
        <el-col :span="4">
          <el-button
            type="primary"
            size="small"
            @click="
              $refs.uploadExcelRef.show({ url: 'edu/excel/in/daily/group' })
            "
            >名单批量管理</el-button
          >
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
        <el-table-column prop="groupId" label="ID" width="60">
          <template #default="scope">{{ scope.row.groupId }}</template>
        </el-table-column>
        <el-table-column
          prop="groupName"
          label="分组名称"
          show-overflow-tooltip
        >
        </el-table-column>
        <el-table-column label="答题上线(日/月)" sortable width="140">
          <template #default="scope">{{
            scope.row.dayLimit + ' / ' + scope.row.monthLimit
          }}</template>
        </el-table-column>
        <el-table-column
          prop="optionRand"
          label="选项随机"
          sortable
          width="140"
        >
          <template #default="scope">{{
            dictStore.getDictLabel('yes_or_not', scope.row.optionRand)
          }}</template>
        </el-table-column>
        <el-table-column
          prop="mistakeFirst"
          label="错题优先"
          sortable
          width="140"
        >
          <template #default="scope">{{
            dictStore.getDictLabel('yes_or_not', scope.row.mistakeFirst)
          }}</template>
        </el-table-column>
        <el-table-column
          prop="answerTime"
          label="答题时长"
          sortable
          width="140"
        ></el-table-column>
        <el-table-column prop="libCode" label="题库" sortable width="140">
          <template #default="scope"
            ><el-button
              type="primary"
              size="small"
              @click="manageLibCode(scope.row)"
              link
              >管理题库</el-button
            ></template
          >
        </el-table-column>
        <el-table-column label="错题集" sortable width="140">
          <template #default="scope"
            >{{ scope.row.wrongDay === 0 ? '不限' : scope.row.wrongDay }} /
            {{ scope.row.wrongLimit }}</template
          >
        </el-table-column>
        <el-table-column label="收藏夹" sortable width="140">
          <template #default="scope"
            >{{ scope.row.collectDay === 0 ? '不限' : scope.row.collectDay }} /
            {{
              scope.row.collectLimit === 0 ? '不限' : scope.row.collectLimit
            }}</template
          >
        </el-table-column>
        <el-table-column prop="handleDate" label="操作" width="50">
          <template #default="scope">
            <el-button
              type="primary"
              icon="Edit"
              size="small"
              @click="manageBrushConfig(scope.row)"
              circle
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="pageSizes"
        :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </el-card>
    <!-- 以下内容用着题库管理  -->
    <el-dialog
      title="题库管理"
      v-model="quesLibVisible"
      width="60%"
      :close-on-click-modal="false"
    >
      <el-card class="tree-box">
        <el-tree
          :data="treeData"
          ref="treeRef"
          node-key="libCode"
          show-checkbox
        >
          <template #default="{ node, data }">
            <span class="custom-tree-node">
              <span
                >{{ data.libName }}
                <el-tag v-if="data.libLevel === 2" size="small">{{
                  data.quesNum
                }}</el-tag></span
              >
            </span>
          </template>
        </el-tree>
      </el-card>
      <template #footer>
        <span class="dialog-footer">
          <el-button size="small" @click="quesLibVisible = false"
            >关 闭</el-button
          >
          <el-button type="primary" size="small" @click="libCodeUpdate()"
            >提交修改</el-button
          >
        </span>
      </template>
    </el-dialog>
    <el-dialog
      title="配置维护"
      v-model="brushConfigVisible"
      width="60%"
      :close-on-click-modal="false"
    >
      <el-form
        :model="brushForm"
        :rules="rules"
        size="small"
        ref="brushConfigForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="组ID" prop="groupId">
          <el-input v-model="brushForm.groupId" type="number"></el-input>
        </el-form-item>
        <el-form-item label="分组名称" prop="groupName">
          <el-input v-model="brushForm.groupName"></el-input>
        </el-form-item>
        <el-form-item label="月答题上线" prop="monthLimit">
          <el-input v-model="brushForm.monthLimit" type="number"></el-input>
        </el-form-item>
        <el-form-item label="日答题上线" prop="dayLimit">
          <el-input v-model="brushForm.dayLimit" type="number"></el-input>
        </el-form-item>
        <el-form-item label="答题时长" prop="answerTime">
          <el-input v-model="brushForm.answerTime" type="number"></el-input>
        </el-form-item>
        <el-form-item label="选项随机" prop="optionRand">
          <el-select v-model="brushForm.optionRand" placeholder="请选择">
            <el-option
              v-for="item in dictStore.dict.yes_or_not"
              :key="item.code"
              :label="item.codeval"
              :value="parseInt(item.code)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="错题优先" prop="mistakeFirst">
          <el-select v-model="brushForm.mistakeFirst" placeholder="暂时停用">
            <el-option
              v-for="item in dictStore.dict.yes_or_not"
              :key="item.code"
              :label="item.codeval"
              :value="parseInt(item.code)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="错题天数" prop="wrongDay">
          <el-input v-model="brushForm.wrongDay" type="number"></el-input>
        </el-form-item>
        <el-form-item label="错题题数" prop="wrongLimit">
          <el-input v-model="brushForm.wrongLimit" type="number"></el-input>
        </el-form-item>
        <el-form-item label="收藏天数" prop="collectDay">
          <el-input v-model="brushForm.collectDay" type="number"></el-input>
        </el-form-item>
        <el-form-item label="收藏题数" prop="collectLimit">
          <el-input v-model="brushForm.collectLimit" type="number"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button size="small" @click="brushConfigVisible = false"
            >关 闭</el-button
          >
          <el-button type="primary" size="small" @click="configUpdate()"
            >提交修改</el-button
          >
        </span>
      </template>
    </el-dialog>

    <UploadExcel
      ref="uploadExcelRef"
      title="名单导入"
      info="导入【分组名单】导入必须有“员编”、“分组”列。 "
    ></UploadExcel>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted, nextTick } from 'vue'
import { ElMessage } from 'element-plus'
import UploadExcel from '@/components/UploadExcel.vue'
import { brushApi } from '@/api/edu/brush'
import { useDictStore } from '@/stores'
const dictStore = useDictStore()

// 响应式数据
const queryInfo = reactive({
  groupId: '1',
  querytype: 'groupName',
  query: '',
  orderType: ' desc',
  order: ' groupId ',
  pageSize: 20,
  pageNum: 1
})

const tableData = ref([])
const treeData = ref([])
const currentPage = ref(1)
const pageSizes = [20, 100, 500]
const total = ref(0)
const quesLibVisible = ref(false)
const brushForm = reactive({ brushCode: '' })
const brushConfigVisible = ref(false)
const curentOperateType = ref('')

// 表单验证规则
const rules = {
  groupId: [{ required: true, message: 'ID不可重复！', trigger: 'blur' }],
  groupName: [
    {
      required: true,
      min: 3,
      max: 50,
      message: '长度在 3 到 50 个字符',
      trigger: 'blur'
    }
  ],
  monthLimit: [
    { required: true, message: '每月最多刷题天数，0为不限制', trigger: 'blur' }
  ],
  dayLimit: [
    { required: true, message: '每天最多刷题数量，0为不限制', trigger: 'blur' }
  ],
  answerTime: [
    { required: true, message: '答题时长，0为不限制', trigger: 'blur' }
  ],
  wrongDay: [
    { required: true, message: '错题查看的天数，0为不限制', trigger: 'blur' }
  ],
  wrongLimit: [
    { required: true, message: '错题查看的总数，0为不限制', trigger: 'blur' }
  ],
  collectDay: [
    { required: true, message: '几天内收藏的题目，0为不限制', trigger: 'blur' }
  ],
  collectLimit: [
    { required: true, message: '总收藏的题目，0为不限制', trigger: 'blur' }
  ]
}

// 引用
const treeRef = ref(null)
const brushConfigForm = ref(null)

// 生命周期
onMounted(() => {
  listBrushConfigData()
  getTreeDataList()
})

// 方法
async function listBrushConfigData() {
  const res = await brushApi.listBrushConfig(queryInfo)
  if (res.code !== 200) return ElMessage.error(res.msg)
  total.value = res.data.total
  tableData.value = res.data.list
}

async function getTreeDataList() {
  const res = await brushApi.getTreeData()
  if (res.code !== 200) return ElMessage.error(res.msg)
  treeData.value = res.data
}

async function updateBrushConfigData() {
  const res = await brushApi.updateBrushConfig(brushForm)
  if (res.code !== 200) return ElMessage.error(res.msg)
  ElMessage.success(res.msg)
  listBrushConfigData()
}

async function addBrushConfigData() {
  const res = await brushApi.addBrushConfig(brushForm)
  if (res.code !== 200) return ElMessage.error(res.msg)
  ElMessage.success(res.msg)
  listBrushConfigData()
}

function manageLibCode(row) {
  const libCodes = row.libCode
  brushForm.brushCode = row.brushCode
  brushForm.libCode = ''
  quesLibVisible.value = true
  nextTick(() => {
    if (libCodes != null && libCodes !== '') {
      treeRef.value.setCheckedKeys(libCodes.split(','))
    }
  })
}

function libCodeUpdate() {
  const checked = treeRef.value.getCheckedKeys(true, false)
  brushForm.libCode = checked.join(',')
  updateBrushConfigData()
  quesLibVisible.value = false
}

function manageBrushConfig(row) {
  if (row == null) {
    curentOperateType.value = 'add'
  } else {
    curentOperateType.value = 'edit'
    Object.assign(brushForm, row)
  }
  brushConfigVisible.value = true
}

function configUpdate() {
  brushConfigForm.value.validate(async (valid) => {
    if (valid) {
      if (curentOperateType.value === 'edit') {
        await brushApi.updateBrushConfigData()
      } else {
        await brushApi.addBrushConfigData()
      }
      brushConfigVisible.value = false
    } else {
      return false
    }
  })
}

function getTableList() {
  listBrushConfigData()
}

function handleSizeChange(pageSize) {
  queryInfo.pageSize = pageSize
  listBrushConfigData()
}

function handleCurrentChange(page) {
  queryInfo.pageNum = page
  listBrushConfigData()
}

function tableSort(data) {
  if (data.order === 'ascending') queryInfo.orderType = ' asc '
  else if (data.order === 'descending') queryInfo.orderType = ' desc '
  queryInfo.order = data.prop
  listBrushConfigData()
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
</style>
