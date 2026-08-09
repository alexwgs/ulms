<template>
  <div>
    <t-card class="management-card">
      <div class="table-toolbar">
        <div class="toolbar-left">
          <t-button
            variant="outline"
            theme="primary"
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
            >新增节点</t-button
          >
          <t-tag :theme="currentNode == null ? 'danger' : 'primary'" variant="light">{{
            currentNode == null ? '选择目录' : '位置:' + currentNode.libName
          }}</t-tag>
          <t-space>
            <t-button
              variant="outline"
              theme="primary"
              size="small"
              :disabled="currentNode == null || currentNode.libLevel != 2"
              @click="
                () => {
                  questionDialogRef.show(currentNode)
                }
              "
              ><template #icon><DynamicIcon name="edit" /></template>新建</t-button
            >
            <t-button
              variant="outline"
              theme="primary"
              size="small" :disabled="transInfo.checkedQuestions.length <= 0"
              @click="treeDialogFormVisible = !treeDialogFormVisible"
              ><template #icon><DynamicIcon name="position" /></template>移动</t-button
            >
            <t-button
              variant="outline"
              theme="primary"
              size="small" @click="questionBankApi.downloadQuestionReport(queryInfo)"
              ><template #icon><DynamicIcon name="download" /></template>下载</t-button
            >
          </t-space>
        </div>
        <div class="toolbar-right">
          <t-select
            v-model="queryInfo.quesStat"
            size="small"
            placeholder="请选择过滤规则"
            style="width: 140px"
            @change="getTableList"
          >
            <t-option label="生效" value="1" />
            <t-option label="失效" value="0" />
          </t-select>
          <t-input-adornment>
            <template #prepend>
              <t-select
                v-model="queryInfo.querytype"
                style="width: 100px"
                placeholder="请选择"
              >
                <t-option label="题目" value="quesStem" />
                <t-option label="业务类型" value="category" />
                <t-option label="题目编号" value="quesCode" />
              </t-select>
            </template>
            <template #append>
              <t-button variant="outline" @click="getTableList"><template #icon><DynamicIcon name="search" /></template>搜索</t-button>
            </template>
            <t-input placeholder="输入要搜索的内容" size="small" v-model="queryInfo.query" style="width: 300px"></t-input>
          </t-input-adornment>
        </div>
      </div>
      <t-row :gutter="20">
        <t-col :span="3">
          <div style="height: calc(100vh - 280px); overflow: auto">
            <t-tree
              :data="treeData"
              ref="treeRef"
              :keys="{ value: 'libCode', label: 'libName', children: 'children' }"
              @click="nodeclick"
              @drag-start="handleDragStart"
              @drag-end="handleDragEnd"
              draggable
            >
              <template #label="{ node }">
                <span class="custom-tree-node">
                  <span
                    >{{ node.data.libName }}<template
                      v-if="!node.data.children || node.data.children.length === 0"
                      >[{{ node.data.quesNum ?? 0 }}]</template
                    ></span
                  >
                  <span>
                    <t-button
                      variant="text"
                      theme="primary"
                      v-show="node.data.libLevel !== 2"
                      size="small"
                      @click="treeNodeManage(node.data, 'add')"
                      >新增</t-button
                    >
                    <t-button
                      variant="text"
                      theme="primary"
                      size="small"
                      @click="treeNodeManage(node.data, 'edit')"
                      >修改</t-button
                    >
                  </span>
                </span>
              </template>
            </t-tree>
          </div>
        </t-col>
        <t-col :span="9">
          <CustomTable rowKey="quesCode"
            :data="tableData"
            border
            size="small"
            stripe
            style="width: 100%"
            height="calc(100vh - 350px)"
            @sort-change="tableSort"
            @select-change="handleSelectionChange">
            <TableColumn type="multiple" width="55" />
            <TableColumn
              prop="quesType"
              sortable="custom"
              label="题型"
              width="80">
              <template #default="scope">
                <t-tag
                  :theme="scope.row.quesType == 2 ? 'success' : 'primary'"
                  size="small"
                  variant="light"
                >
                  {{
                    scope.row.quesType === 2
                      ? '多选题'
                      : scope.row.quesType === 3
                        ? '判断题'
                        : '单选题'
                  }}
                </t-tag>
              </template>
            </TableColumn>
            <TableColumn
              label="题库"
              sortable="custom"
              width="80"
              ellipsis>
              <template #default="scope">
                {{ getQuesLibName(scope.row.libCode) }}
              </template>
            </TableColumn>
            <TableColumn
              prop="quesStem"
              label="题目"
              sortable="custom"
              ellipsis
            />
            <TableColumn
              prop="handlePlo"
              label="操作人"
              sortable="custom"
              width="90"
            />
            <TableColumn
              prop="handleDate"
              label="操作时间"
              sortable="custom"
              width="140"
            />
            <TableColumn label="操作" width="90">
              <template #default="scope">
                <t-button variant="outline" size="small"
                  @click="questionViewRef.show(scope.row)"
                 ><template #icon><DynamicIcon name="view-list" /></template>查看</t-button>
                <t-button variant="outline"
                  theme="default" size="small"
                  @click="transferObjToChildren(scope.row)"
                 
                >编辑</t-button>
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
          />
        </t-col>
      </t-row>
    </t-card>

    <!-- 题目位置调整 -->
    <t-dialog header="题库选择" v-model:visible="treeDialogFormVisible">
      <div style="height: 400px; overflow: auto">
        当前选中【{{ transInfo.checkedQuestions.length }}】题,添加至【{{
          transInfo.transToName
        }}】
        <t-tree
          :data="treeData"
          ref="transTreeRef"
          :keys="{ value: 'libCode', label: 'libName', children: 'children' }"
          activable
          @click="tansNodeclick"
        >
          <template #label="{ node }">
            <span class="custom-tree-node">
              <span
                >{{ node.data.libName }}<template
                  v-if="!node.data.children || node.data.children.length === 0"
                  >[{{ node.data.quesNum ?? 0 }}]</template
                ></span
              >
            </span>
          </template>
        </t-tree>
      </div>
      <template #footer>
        <t-space>
          <t-button variant="outline" size="small" @click="treeDialogFormVisible = false"
            >取 消</t-button
          >
          <t-button variant="outline" size="small" theme="primary" @click="transSubmit"
            >确 定</t-button
          >
        </t-space>
      </template>
    </t-dialog>

    <t-dialog
      :header="tableTreeDialogTitle"
      v-model:visible="tableTreeDialogFormVisible"
    >
      <t-alert
        v-show="treeForm.libStat === 0"
        title="注意:失效会将所有下级题库及题目置为失效"
        theme="warning"
        show-icon
        center
        :closable="false"
      />
      <t-form
        :data="treeForm"
        size="small"
        ref="treeFormRef"
        :rules="treeFormRules"
      >
        <t-form-item
          label="分类名称"
          name="libName"
          :label-width="formLabelWidth"
        >
          <t-input v-model="treeForm.libName" autocomplete="off" />
        </t-form-item>
        <t-form-item label="状态" :label-width="formLabelWidth">
          <t-select v-model="treeForm.libStat" placeholder="请选择状态">
            <t-option label="有效" :value="1" />
            <t-option label="失效" :value="0" />
          </t-select>
        </t-form-item>
      </t-form>
      <template #footer>
        <t-space>
          <t-button variant="outline" size="small" @click="tableTreeDialogFormVisible = false"
            >取 消</t-button
          >
          <t-button variant="outline" size="small" theme="primary" @click="tableTreeFormSubmit"
            >确 定</t-button
          >
        </t-space>
      </template>
    </t-dialog>

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
import { MessagePlugin } from 'tdesign-vue-next'
import QuestionDialog from './components/QuestionDialog.vue'
import QuestionView from './components/QuestionView.vue'
import { questionLibApi } from '@/api/edu/questionLib'
import { questionBankApi } from '@/api/edu/questionBank'
import { usePagination } from '@/hooks/usePagination'

const treeRef = ref(null)
const transTreeRef = ref(null)
const questionDialogRef = ref(null)
const questionViewRef = ref(null)
const treeFormRef = ref(null)

const treeData = ref([])
const tableData = ref([])
const currentNode = ref(null)
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
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    treeData.value = res.data
  } catch (error) {
    MessagePlugin.error('获取树数据失败')
  }
}

const nodeclick = (context) => {
      const { node: treeNode } = context;
      const obj = treeNode.data;
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
  if (valid === true) {
    try {
      let res
      if (tableTreeDialogTitle.value === '节点新增') {
        res = await questionLibApi.addQuestionLib(treeForm)
      } else if (tableTreeDialogTitle.value === '节点修改') {
        res = await questionLibApi.updateQuestionLib(treeForm)
      }

      if (res.code !== 200) return MessagePlugin.error(res.msg)
      MessagePlugin.success(res.msg)
      tableTreeDialogFormVisible.value = false
      loadTreeData()
    } catch (error) {
      MessagePlugin.error('操作失败，请重试')
    }
  }
}

const getTableList = async () => {
  try {
    const res = await questionBankApi.getQuestionList(queryInfo)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    tableData.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    MessagePlugin.error('获取题目列表失败')
  }
}

const tableSort = (data) => {
  if (!data.descending) queryInfo.orderType = ' asc '
  else if (data.descending) queryInfo.orderType = ' desc '
  queryInfo.order = data.sortBy
  getTableList()
}





const transferObjToChildren = (row) => {
  // 深拷贝
  questionObj.value = JSON.parse(JSON.stringify(row))
  questionDialogRef.value.show(questionObj.value)
}

const handleSelectionChange = (_keys, options) => {
  transInfo.checkedQuestions = (options?.selectedRowData || []).map((e) => e.quesCode)
}

const tansNodeclick = (context) => {
      const { node: treeNode } = context;
      const obj = treeNode.data;
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
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    treeDialogFormVisible.value = false
    loadTreeData()
  } catch (error) {
    MessagePlugin.error('移动失败，请重试')
  }
}

const getQuesLibName = (libCode) => {
  const tree = treeRef.value
  if (!tree) return '[级别异常]' + libCode

  const record = tree.getItem(libCode)
  try {
    const level1 = record && record.getParent ? record.getParent() : null
    const level0 = level1 && level1.getParent ? level1.getParent() : null
    if (!level0 || !level1) return '[级别异常]' + libCode
    return (
      level0.data.libName +
      '->' +
      level1.data.libName +
      '->' +
      record.data.libName
    )
  } catch (error) {
    return '[级别异常]' + libCode
  }
}

const updateTree = () => {
  loadTreeData()
}

const handleDragStart = (context) => {
      handleNode.value = context.dragNode.data
}

const handleDragEnd = (context) => {
      const { dragNode, dropNode, dropPosition } = context;
      const dropType = dropPosition === 0 ? 'inner' : dropPosition === -1 ? 'before' : 'after';
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
      if (res.code !== 200) return MessagePlugin.error(res.msg)
      MessagePlugin.success(res.msg)
      loadTreeData()
    })
    .catch((error) => {
      MessagePlugin.error('操作失败，请重试')
    })
}
const { currentPage, pageSizes, handleCurrentChange, handleSizeChange } = usePagination({ query: queryInfo, fetch: getTableList, pageSizes: [20, 100, 500] })
</script>
<style lang="less" scoped>
.management-card {
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
