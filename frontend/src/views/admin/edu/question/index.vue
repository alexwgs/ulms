<template>
  <div>
    <el-card class="box-card">
      <el-row style="padding-bottom: 10px" :gutter="15">
        <el-col :span="3">
          <el-button
            type="primary"
            size="small"
            @click="
              treeNodeManage(
                {
                  libName: '',
                  upCode: '',
                  libLevel: -1,
                  quesNum: 0,
                  libStat: 1
                },
                'add'
              )
            "
            >新增节点</el-button
          >
        </el-col>
        <el-col :span="4">
          <el-tag :type="currentNode == null ? 'danger' : 'primary'">{{
            currentNode == null ? '选择目录' : '位置:' + currentNode.libName
          }}</el-tag>
        </el-col>
        <el-col :span="6">
          <el-button-group>
            <el-button
              type="primary"
              size="small"
              :disabled="currentNode == null || currentNode.libLevel != 2"
              @click="
                () => {
                  questionDialogRef.show(currentNode)
                }
              "
              icon="edit"
              >新建</el-button
            >
            <el-button
              type="primary"
              size="small"
              icon="position"
              :disabled="transInfo.checkedQuestions.length <= 0"
              @click="treeDialogFormVisible = !treeDialogFormVisible"
              >移动</el-button
            >
            <el-button
              type="primary"
              size="small"
              icon="download"
              @click="questionBankApi.downloadQuestionReport(queryInfo)"
              >下载</el-button
            >
          </el-button-group>
        </el-col>
        <el-col :span="3">
          <el-select
            v-model="queryInfo.quesStat"
            size="small"
            placeholder="请选择过滤规则"
            style="width: 100%"
            @change="getTableList"
          >
            <el-option label="生效" value="1" />
            <el-option label="失效" value="0" />
          </el-select>
        </el-col>
        <el-col :span="8">
          <el-input
            placeholder="输入要搜索的内容"
            size="small"
            v-model="queryInfo.query"
          >
            <el-select
              v-model="queryInfo.querytype"
              slot="prepend"
              style="width: 100px"
              placeholder="请选择"
            >
              <el-option label="题目" value="quesStem" />
              <el-option label="业务类型" value="category" />
              <el-option label="题目编号" value="quesCode" />
            </el-select>
            <el-button slot="append" icon="search" @click="getTableList" />
          </el-input>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="6">
          <div style="height: calc(100vh - 280px); overflow: auto">
            <el-tree
              :data="treeData"
              ref="treeRef"
              node-key="libCode"
              @node-click="nodeclick"
              @node-drag-start="handleDragStart"
              @node-drag-end="handleDragEnd"
              draggable
            >
              <template #default="{ node, data }">
                <span class="custom-tree-node">
                  <span
                    >{{ data.libName }}
                    <el-tag
                      v-if="data.libLevel === 2"
                      size="small"
                      effect="plain"
                      >{{ data.quesNum }}</el-tag
                    ></span
                  >
                  <span>
                    <el-button
                      :text="true"
                      v-show="data.libLevel !== 2"
                      size="small"
                      @click="treeNodeManage(data, 'add')"
                      >新增</el-button
                    >
                    <el-button
                      :text="true"
                      size="small"
                      @click="treeNodeManage(data, 'edit')"
                      >修改</el-button
                    >
                  </span>
                </span>
              </template>
            </el-tree>
          </div>
        </el-col>
        <el-col :span="18">
          <el-table
            :data="tableData"
            border
            size="small"
            stripe
            style="width: 100%"
            height="calc(100vh - 350px)"
            @sort-change="tableSort"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="55" />
            <el-table-column
              prop="quesType"
              sortable="custom"
              label="题型"
              width="80"
            >
              <template #default="scope">
                <el-tag
                  :type="scope.row.quesType == 2 ? 'success' : 'primary'"
                  size="small"
                  effect="plain"
                >
                  {{
                    scope.row.quesType === 2
                      ? '多选题'
                      : scope.row.quesType === 3
                        ? '判断题'
                        : '单选题'
                  }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column
              label="题库"
              sortable="custom"
              width="80"
              show-overflow-tooltip
            >
              <template #default="scope">
                {{ getQuesLibName(scope.row.libCode) }}
              </template>
            </el-table-column>
            <el-table-column
              prop="quesStem"
              label="题目"
              sortable="custom"
              show-overflow-tooltip
            />
            <el-table-column
              prop="handlePlo"
              label="操作人"
              sortable="custom"
              width="90"
            />
            <el-table-column
              prop="handleDate"
              label="操作时间"
              sortable="custom"
              width="140"
            />
            <el-table-column label="操作" width="90">
              <template #default="scope">
                <el-button
                  icon="view"
                  size="small"
                  @click="questionViewRef.show(scope.row)"
                  circle
                />
                <el-button
                  type="primary"
                  icon="edit"
                  size="small"
                  @click="transferObjToChildren(scope.row)"
                  circle
                />
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
          />
        </el-col>
      </el-row>
    </el-card>

    <!-- 题目位置调整 -->
    <el-dialog title="题库选择" v-model="treeDialogFormVisible">
      <div style="height: 400px; overflow: auto">
        当前选中【{{ transInfo.checkedQuestions.length }}】题,添加至【{{
          transInfo.transToName
        }}】
        <el-tree
          :data="treeData"
          ref="transTreeRef"
          node-key="libCode"
          highlight-current
          @node-click="tansNodeclick"
        >
          <template #default="{ node, data }">
            <span class="custom-tree-node">
              <span
                >{{ data.libName }}
                <el-tag
                  v-if="data.libLevel === 2"
                  size="small"
                  effect="plain"
                  >{{ data.quesNum }}</el-tag
                ></span
              >
            </span>
          </template>
        </el-tree>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button size="small" @click="treeDialogFormVisible = false"
            >取 消</el-button
          >
          <el-button size="small" type="primary" @click="transSubmit"
            >确 定</el-button
          >
        </span>
      </template>
    </el-dialog>

    <el-dialog
      :title="tableTreeDialogTitle"
      v-model="tableTreeDialogFormVisible"
    >
      <el-alert
        v-show="treeForm.libStat === 0"
        title="注意:失效会将所有下级题库及题目置为失效"
        type="warning"
        show-icon
        center
        :closable="false"
      />
      <el-form
        :model="treeForm"
        size="small"
        ref="treeFormRef"
        :rules="treeFormRules"
      >
        <el-form-item
          label="分类名称"
          prop="libName"
          :label-width="formLabelWidth"
        >
          <el-input v-model="treeForm.libName" autocomplete="off" />
        </el-form-item>
        <el-form-item label="状态" :label-width="formLabelWidth">
          <el-select v-model="treeForm.libStat" placeholder="请选择状态">
            <el-option label="有效" :value="1" />
            <el-option label="失效" :value="0" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button size="small" @click="tableTreeDialogFormVisible = false"
            >取 消</el-button
          >
          <el-button size="small" type="primary" @click="tableTreeFormSubmit"
            >确 定</el-button
          >
        </span>
      </template>
    </el-dialog>

    <!-- 题目编辑dialog -->
    <QuestionDialog
      ref="questionDialogRef"
      :question="questionObj"
      @update:tree="updateTree"
      @update:table="getTableList"
    />
    <QuestionView ref="questionViewRef" :question="questionObj" />
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import QuestionDialog from './components/QuestionDialog.vue'
import QuestionView from './components/QuestionView.vue'
import { questionLibApi } from '@/api/edu/questionLib'
import { questionBankApi } from '@/api/edu/questionBank'

const treeRef = ref(null)
const transTreeRef = ref(null)
const questionDialogRef = ref(null)
const questionViewRef = ref(null)
const treeFormRef = ref(null)

const treeData = ref([])
const tableData = ref([])
const currentNode = ref(null)
const currentPage = ref(1)
const pageSizes = ref([20, 100, 500])
const total = ref(0)
const questionObj = ref(null)
const treeDialogFormVisible = ref(false)
const tableTreeDialogFormVisible = ref(false)
const tableTreeDialogTitle = ref('')
const formLabelWidth = ref('120px')
const handleNode = ref('')

const treeForm = reactive({
  libName: '',
  upCode: 0,
  libLevel: 0,
  quesNum: 0,
  libStat: 1
})

const treeFormRules = reactive({
  libName: [
    { required: true, message: '请输入分类名称', trigger: 'blur' },
    { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
  ]
})

const queryInfo = reactive({
  libCode: '',
  quesStat: '1',
  querytype: 'quesStem',
  query: '',
  orderType: ' desc',
  order: ' libCode ',
  pageSize: 20,
  pageNum: 1
})

const transInfo = reactive({
  checkedQuestions: [],
  transToName: '',
  libCode: ''
})

onMounted(() => {
  loadTreeData()
})

const loadTreeData = async () => {
  try {
    const res = await questionLibApi.getTreeData()
    if (res.code !== 200) return ElMessage.error(res.msg)
    treeData.value = res.data
  } catch (error) {
    ElMessage.error('获取树数据失败')
  }
}

const nodeclick = (obj, node, self) => {
  currentNode.value = obj
  if (obj.libLevel === 2) {
    queryInfo.libCode = obj.libCode
    getTableList()
  }
}

const treeNodeManage = (data, type) => {
  if (type === 'add') {
    tableTreeDialogTitle.value = '节点新增'
    treeForm.libName = ''
    treeForm.upCode = data.libCode
    treeForm.libLevel = data.libLevel + 1
    treeForm.quesNum = 0
    treeForm.libStat = 1
  } else if (type === 'edit') {
    tableTreeDialogTitle.value = '节点修改'
    Object.assign(treeForm, data)
  }
  tableTreeDialogFormVisible.value = true
}

const tableTreeFormSubmit = async () => {
  if (!treeFormRef.value) return

  const valid = await treeFormRef.value.validate()
  if (valid) {
    try {
      let res
      if (tableTreeDialogTitle.value === '节点新增') {
        res = await questionLibApi.addQuestionLib(treeForm)
      } else if (tableTreeDialogTitle.value === '节点修改') {
        res = await questionLibApi.updateQuestionLib(treeForm)
      }

      if (res.code !== 200) return ElMessage.error(res.msg)
      ElMessage.success(res.msg)
      tableTreeDialogFormVisible.value = false
      loadTreeData()
    } catch (error) {
      ElMessage.error('操作失败，请重试')
    }
  }
}

const getTableList = async () => {
  try {
    const res = await questionBankApi.getQuestionList(queryInfo)
    if (res.code !== 200) return ElMessage.error(res.msg)
    tableData.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    ElMessage.error('获取题目列表失败')
  }
}

const tableSort = (data) => {
  if (data.order === 'ascending') queryInfo.orderType = ' asc '
  else if (data.order === 'descending') queryInfo.orderType = ' desc '
  queryInfo.order = data.prop
  getTableList()
}

const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  getTableList()
}

const handleSizeChange = (pageSize) => {
  queryInfo.pageSize = pageSize
  getTableList()
}

const transferObjToChildren = (row) => {
  // 深拷贝
  questionObj.value = JSON.parse(JSON.stringify(row))
  questionDialogRef.value.show(questionObj.value)
}

const handleSelectionChange = (val) => {
  transInfo.checkedQuestions = val.map((e) => e.quesCode)
}

const tansNodeclick = (obj, node, self) => {
  if (obj.libLevel === 2) {
    transInfo.transToName = obj.libName
    transInfo.libCode = obj.libCode
  }
}

const transSubmit = async () => {
  try {
    const res = await questionBankApi.transferQuestions(
      transInfo.libCode,
      transInfo.checkedQuestions
    )
    if (res.code !== 200) return ElMessage.error(res.msg)
    ElMessage.success(res.msg)
    treeDialogFormVisible.value = false
    loadTreeData()
  } catch (error) {
    ElMessage.error('移动失败，请重试')
  }
}

const getQuesLibName = (libCode) => {
  const tree = treeRef.value
  if (!tree) return '[级别异常]' + libCode

  const record = tree.getNode(libCode)
  let libName = ''
  try {
    libName =
      record.parent.parent.data.libName +
      '->' +
      record.parent.data.libName +
      '->' +
      record.data.libName
  } catch (error) {
    libName = '[级别异常]' + libCode
  }
  return libName
}

const updateTree = () => {
  loadTreeData()
}

const handleDragStart = (node, ev) => {
  handleNode.value = node.data
}

const handleDragEnd = (draggingNode, dropNode, dropType, ev) => {
  if (dropType === 'inner') {
    handleNode.value.upCode = dropNode.data.libCode
    handleNode.value.libLevel = dropNode.data.libLevel + 1
  } else if (dropType === 'after') {
    handleNode.value.sort = dropNode.data.sort + 1
    handleNode.value.libLevel = dropNode.data.libLevel
    handleNode.value.upCode = dropNode.data.upCode
  } else if (dropType === 'before') {
    handleNode.value.sort = dropNode.data.sort - 1
    handleNode.value.libLevel = dropNode.data.libLevel
    handleNode.value.upCode = dropNode.data.upCode
  } else {
    return
  }

  questionLibApi
    .updateQuestionLib(handleNode.value)
    .then((res) => {
      if (res.code !== 200) return ElMessage.error(res.msg)
      ElMessage.success(res.msg)
      loadTreeData()
    })
    .catch((error) => {
      ElMessage.error('操作失败，请重试')
    })
}
</script>
<style lang="less" scoped>
.box-card {
  height: calc(100vh - 190px);
  overflow: auto;
}

.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
</style>
