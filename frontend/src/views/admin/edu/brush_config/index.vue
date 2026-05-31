<template>
  <div>
    <t-card class="main-container">
      <t-row style="padding-bottom: 10px" :gutter="15">
        <t-col :span="7">
          <t-input
            placeholder="输入要搜索的内容"
            size="small"
            v-model="queryInfo.query"
          >
            <template #prepend>
              <t-select
                v-model="queryInfo.querytype"
                size="small"
                style="width: 100px"
                placeholder="请选择"
              >
                <t-option label="分组名称" value="groupName"></t-option>
              </t-select>
            </template>
            <template #append>
              <t-button size="small"><template #icon><DynamicIcon name="search" /></template></t-button>
            </template>
          </t-input>
        </t-col>
        <t-col :span="2">
          <t-button
            theme="primary"
            size="small"
            @click="manageBrushConfig(null)"
            >新增配置</t-button
          >
        </t-col>
        <t-col :span="2">
          <t-button
            theme="primary"
            size="small"
            @click="
              $refs.uploadExcelRef.show({ url: 'edu/excel/in/daily/group' })
            "
            >名单批量管理</t-button
          >
        </t-col>
      </t-row>
      <CustomTable rowKey="id"
        :data="tableData"
        size="small"
        stripe
        style="width: 100%"
        height="calc(100vh - 300px)"
        @sort-change="tableSort">
        <TableColumn colKey="groupId" label="ID" width="60">
          <template #default="scope">{{ scope.row.groupId }}</template>
        </TableColumn>
        <TableColumn
          prop="groupName"
          label="分组名称"
          ellipsis>
        </TableColumn>
        <TableColumn label="答题上线(日/月)" sortable width="140">
          <template #default="scope">{{
            scope.row.dayLimit + ' / ' + scope.row.monthLimit
          }}</template>
        </TableColumn>
        <TableColumn
          prop="optionRand"
          label="选项随机"
          sortable
          width="140">
          <template #default="scope">{{
            dictStore.getDictLabel('yes_or_not', scope.row.optionRand)
          }}</template>
        </TableColumn>
        <TableColumn
          prop="mistakeFirst"
          label="错题优先"
          sortable
          width="140">
          <template #default="scope">{{
            dictStore.getDictLabel('yes_or_not', scope.row.mistakeFirst)
          }}</template>
        </TableColumn>
        <TableColumn
          prop="answerTime"
          label="答题时长"
          sortable
          width="140"></TableColumn>
        <TableColumn colKey="libCode" label="题库" sortable width="140">
          <template #default="scope"
            ><t-button
              theme="primary"
              size="small"
              @click="manageLibCode(scope.row)"
              link
              >管理题库</t-button
            ></template
          >
        </TableColumn>
        <TableColumn label="错题集" sortable width="140">
          <template #default="scope"
            >{{ scope.row.wrongDay === 0 ? '不限' : scope.row.wrongDay }} /
            {{ scope.row.wrongLimit }}</template
          >
        </TableColumn>
        <TableColumn label="收藏夹" sortable width="140">
          <template #default="scope"
            >{{ scope.row.collectDay === 0 ? '不限' : scope.row.collectDay }} /
            {{
              scope.row.collectLimit === 0 ? '不限' : scope.row.collectLimit
            }}</template
          >
        </TableColumn>
        <TableColumn colKey="handleDate" label="操作" width="50">
          <template #default="scope">
            <t-button
              theme="primary" size="small"
              @click="manageBrushConfig(scope.row)"
              shape="circle"><template #icon><DynamicIcon name="edit" /></template></t-button>
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
    <!-- 以下内容用着题库管理  -->
    <t-dialog
      header="题库管理"
      v-model:visible="quesLibVisible"
      width="60%"
      :close-on-overlay-click="false"
    >
      <t-card class="tree-box">
        <t-tree
          :data="treeData"
          ref="treeRef"
          :keys="{ value: 'libCode', label: 'libName', children: 'children' }"
          checkable
          v-model="checkedKeys"
        >
          <template #default="{ node }">
            <span class="custom-tree-node">
              <span
                >{{ node.data.libName }}
                <t-tag v-if="node.data.libLevel === 2" size="small">{{
                  node.data.quesNum
                }}</t-tag></span
              >
            </span>
          </template>
        </t-tree>
      </t-card>
      <template #footer>
        <span class="dialog-footer">
          <t-button size="small" @click="quesLibVisible = false"
            >关 闭</t-button
          >
          <t-button theme="primary" size="small" @click="libCodeUpdate()"
            >提交修改</t-button
          >
        </span>
      </template>
    </t-dialog>
    <t-dialog
      header="配置维护"
      v-model:visible="brushConfigVisible"
      width="60%"
      :close-on-overlay-click="false"
    >
      <t-form
        :data="brushForm"
        :rules="rules"
        size="small"
        ref="brushConfigForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <t-form-item label="组ID" name="groupId">
          <t-input v-model="brushForm.groupId" type="number"></t-input>
        </t-form-item>
        <t-form-item label="分组名称" name="groupName">
          <t-input v-model="brushForm.groupName"></t-input>
        </t-form-item>
        <t-form-item label="月答题上线" name="monthLimit">
          <t-input v-model="brushForm.monthLimit" type="number"></t-input>
        </t-form-item>
        <t-form-item label="日答题上线" name="dayLimit">
          <t-input v-model="brushForm.dayLimit" type="number"></t-input>
        </t-form-item>
        <t-form-item label="答题时长" name="answerTime">
          <t-input v-model="brushForm.answerTime" type="number"></t-input>
        </t-form-item>
        <t-form-item label="选项随机" name="optionRand">
          <t-select v-model="brushForm.optionRand" placeholder="请选择">
            <t-option
              v-for="item in dictStore.dict.yes_or_not"
              :key="item.code"
              :label="item.codeval"
              :value="parseInt(item.code)"
            ></t-option>
          </t-select>
        </t-form-item>
        <t-form-item label="错题优先" name="mistakeFirst">
          <t-select v-model="brushForm.mistakeFirst" placeholder="暂时停用">
            <t-option
              v-for="item in dictStore.dict.yes_or_not"
              :key="item.code"
              :label="item.codeval"
              :value="parseInt(item.code)"
            ></t-option>
          </t-select>
        </t-form-item>
        <t-form-item label="错题天数" name="wrongDay">
          <t-input v-model="brushForm.wrongDay" type="number"></t-input>
        </t-form-item>
        <t-form-item label="错题题数" name="wrongLimit">
          <t-input v-model="brushForm.wrongLimit" type="number"></t-input>
        </t-form-item>
        <t-form-item label="收藏天数" name="collectDay">
          <t-input v-model="brushForm.collectDay" type="number"></t-input>
        </t-form-item>
        <t-form-item label="收藏题数" name="collectLimit">
          <t-input v-model="brushForm.collectLimit" type="number"></t-input>
        </t-form-item>
      </t-form>
      <template #footer>
        <span class="dialog-footer">
          <t-button size="small" @click="brushConfigVisible = false"
            >关 闭</t-button
          >
          <t-button theme="primary" size="small" @click="configUpdate()"
            >提交修改</t-button
          >
        </span>
      </template>
    </t-dialog>

    <UploadExcel
      ref="uploadExcelRef"
      title="名单导入"
      info="导入【分组名单】导入必须有“员编”、“分组”列。 "
    ></UploadExcel>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted, nextTick } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
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
const checkedKeys = ref([])
const brushConfigForm = ref(null)

// 生命周期
onMounted(() => {
  listBrushConfigData()
  getTreeDataList()
})

// 方法
async function listBrushConfigData() {
  const res = await brushApi.listBrushConfig(queryInfo)
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  total.value = res.data.total
  tableData.value = res.data.list
}

async function getTreeDataList() {
  const res = await brushApi.getTreeData()
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  treeData.value = res.data
}

async function updateBrushConfigData() {
  const res = await brushApi.updateBrushConfig(brushForm)
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  MessagePlugin.success(res.msg)
  listBrushConfigData()
}

async function addBrushConfigData() {
  const res = await brushApi.addBrushConfig(brushForm)
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  MessagePlugin.success(res.msg)
  listBrushConfigData()
}

function manageLibCode(row) {
  const libCodes = row.libCode
  brushForm.brushCode = row.brushCode
  brushForm.libCode = ''
  quesLibVisible.value = true
  nextTick(() => {
    if (libCodes != null && libCodes !== '') {
      checkedKeys.value = libCodes.split(',')
    } else {
      checkedKeys.value = []
    }
  })
}

function libCodeUpdate() {
  brushForm.libCode = checkedKeys.value.join(',')
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

async function configUpdate() {
  const valid = await brushConfigForm.value.validate()
  if (valid === true) {
    if (curentOperateType.value === 'edit') {
      await brushApi.updateBrushConfigData()
    } else {
      await brushApi.addBrushConfigData()
    }
    brushConfigVisible.value = false
  }
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
  if (!data.descending) queryInfo.orderType = ' asc '
  else if (data.descending) queryInfo.orderType = ' desc '
  queryInfo.order = data.sortBy
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
