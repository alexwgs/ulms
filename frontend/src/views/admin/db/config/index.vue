<template>
  <div>
    <t-card class="management-card">
      <div class="table-toolbar">
        <div class="toolbar-left">
          <t-button variant="outline" theme="primary" size="small" @click="
              treeNodeManage(
                { name: '', pid: 0, treeLevel: 0, sort: 1, status: 1 },
                'add'
              )
            "
            >新增一级节点</t-button
          >
          <t-tag :theme="currentNode == null ? 'danger' : 'success'" variant="light">{{
            currentNode == null ? '选择目录' : '表加入:' + currentNode.name
          }}</t-tag>
          <t-popconfirm
            content="确定要讲表移入此分类么？"
            width="250"
            @confirm="moveInTree()"
          >
            <t-button variant="outline" theme="primary" size="small"><template #icon><DynamicIcon name="arrow-left" /></template>移入</t-button>
          </t-popconfirm>
        </div>
        <div class="toolbar-right">
          <t-select
            v-model="tableQueryInfo.flag"
            size="small"
            placeholder="请选择过滤规则"
            style="width: 130px"
            @change="getTableList"
          >
            <t-option label="已分类" :value="1"></t-option>
            <t-option label="未分类" :value="0"></t-option>
          </t-select>
          <t-input-adornment>
            <template #append>
              <t-button variant="outline" theme="primary" @click="getTableList" size="small">搜索</t-button>
            </template>
            <t-input
              placeholder="查询"
              size="small"
              v-model="tableQueryInfo.keyWord"
              class="input-with-select"
              style="width: 220px"
              @enter="getTableList"
            />
          </t-input-adornment>
          <t-button variant="outline" theme="primary" size="small" @click="syncTableInfo()"
            >同步</t-button
          >
        </div>
      </div>
      <t-row :gutter="20">
        <t-col :span="4">
          <t-tree
            :data="treeData"
            ref="tree"
            :keys="{ value: 'id', label: 'name', children: 'children' }"
            @click="nodeclick"
          >
            <template #label="{ node }">
              <span class="custom-tree-node">
                <span>{{ node.data.name }}</span>
                <span
                  ><div v-if="node.data.treeLevel !== 3">
                    <t-button variant="outline"
                      link
                      theme="primary"
                      size="small"
                      @click="treeNodeManage(node.data, 'add')"
                      >新增</t-button
                    >
                    <t-button variant="outline"
                      link
                      theme="primary"
                      size="small"
                      @click="treeNodeManage(node.data, 'edit')"
                      >修改</t-button
                    >
                  </div>
                  <t-popconfirm
                    v-else
                    content="确定要讲表移出此分类么？"
                    width="250"
                    @confirm="removeTreeNood(node)"
                    >
                    <t-button variant="outline"
                      theme="primary"
                      link
                      size="small"
                      >删除</t-button
                    >
                  </t-popconfirm>
                </span>
              </span>
            </template>
          </t-tree>
        </t-col>
        <t-col :span="8">
          <CustomTable rowKey="id"
            :data="tableData"
            size="small"
            stripe
            style="width: 100%"
            ref="selectTableRef"
            height="calc(100vh - 350px)"
            @select-change="handleSelectionChange">
            <TableColumn type="multiple" width="55"></TableColumn>
            <TableColumn
              prop="name"
              label="表名"
              width="180"></TableColumn>
            <TableColumn
              prop="tableName"
              label="中文名"
              width="180"></TableColumn>
            <TableColumn
              prop="memo"
              label="描述"
              ellipsis></TableColumn>
            <TableColumn label="操作" width="60">
              <template #default="scope">
                <t-button variant="outline"
                  size="small" theme="primary"
                  @click="updateColumn(scope.row)"
                 
                >编辑</t-button>
              </template>
            </TableColumn>
          </CustomTable>
        </t-col>
      </t-row>
    </t-card>

    <t-dialog
      :header="tableTreeDialogTitle"
      v-model:visible="tableTreeDialogFormVisible"
    >
      <t-form
        :data="treeForm"
        size="small"
        ref="treeForm"
        :rules="treeFormRules"
      >
        <t-form-item
          label="分类名称"
          name="name"
          :label-width="formLabelWidth"
        >
          <t-input v-model="treeForm.name" autocomplete="off"></t-input>
        </t-form-item>
        <t-form-item label="状态" :label-width="formLabelWidth">
          <t-select v-model="treeForm.status" placeholder="请选择该菜单状态">
            <t-option label="显示" :value="1"></t-option>
            <t-option label="隐示" :value="0"></t-option>
          </t-select>
        </t-form-item>
        <t-form-item label="排序" name="sort" :label-width="formLabelWidth">
          <t-input
            type="number"
            v-model="treeForm.sort"
            maxlength="6"
            placeholder="由小到大"
            autocomplete="off"
          ></t-input>
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
    <Column ref="columnRef"></Column>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import {
  getTreeData,
  addTreeNode,
  updateTreeNode,
  deleteTreeNode,
  batchMoveTables,
  getTableList as fetchTableList,
  syncTableInfo as syncTableData
} from '../../../../api/db/index.js'
import Column from './components/Column.vue'

const treeData = ref([])
const treeForm = reactive({
  name: '',
  pid: 0,
  treeLevel: 0,
  sort: 1,
  status: 1
})
const tableTreeDialogFormVisible = ref(false)
const tableTreeDialogTitle = ref('')
const formLabelWidth = '120px'
const treeFormRules = reactive({
  name: [
    { required: true, message: '请输入分类名称', trigger: 'blur' },
    { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
  ],
  sort: [{ required: true, message: '请输入排序', trigger: 'blur' }]
})
const tableData = ref([])
const currentNode = ref(null)
const selectTableNames = ref([])
const selectTableIds = ref([])
const tableQueryInfo = reactive({ flag: 0, keyWord: null })
const columnRef = ref(null)
const treeFormRef = ref(null)
const selectTableRef = ref(null)

const getTreeDataHandler = async () => {
  const res = await getTreeData(-1)
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  treeData.value = res.data
}

const nodeclick = (context) => {
      const { node: treeNode } = context;
      const obj = treeNode.data;
  if (obj.treeLevel !== 2) {
    currentNode.value = null
    return
  }
  currentNode.value = obj
}

const treeNodeManage = (data, type) => {
  if (type === 'add') {
    tableTreeDialogTitle.value = '节点新增'
    treeForm.name = ''
    treeForm.pid = data.id
    treeForm.treeLevel = data.treeLevel + 1
    treeForm.sort = 1
    treeForm.status = 1
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
        res = await addTreeNode(treeForm)
      } else if (tableTreeDialogTitle.value === '节点修改') {
        res = await updateTreeNode(treeForm)
      }
      if (res.code !== 200) return MessagePlugin.error(res.msg)
      MessagePlugin.success(res.msg)
      tableTreeDialogFormVisible.value = false
      getTreeDataHandler()
    } catch (error) {
      console.error('操作失败:', error)
      MessagePlugin.error('操作失败')
    }
  } else {
    MessagePlugin.error('表单校验失败！')
  }
}

const removeTreeNood = async (node) => {
  const res = await deleteTreeNode(node.data.id)
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  MessagePlugin.success(res.msg)
  getTreeDataHandler()
}

const getTableList = async () => {
  const res = await fetchTableList(tableQueryInfo)
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  tableData.value = res.data
}

const handleSelectionChange = (_keys, options) => {
  const rows = options?.selectedRowData || []
  selectTableNames.value = rows.map((list) => list.name)
  selectTableIds.value = rows.map((list) => list.id)
}

const moveInTree = async () => {
  if (currentNode.value == null)
    return MessagePlugin.error('没有选中需移入的分类！')
  if (selectTableNames.value.length === 0)
    return MessagePlugin.error('至少请选择一张数据表！')
  const tableTreeRecords = []
  for (let i = 0; i < selectTableNames.value.length; i++) {
    tableTreeRecords.push({
      id: selectTableIds.value[i],
      name: selectTableNames.value[i],
      pid: currentNode.value.id,
      treeLevel: 3,
      sort: i + 1,
      status: 1
    })
  }
  const res = await batchMoveTables(tableTreeRecords)
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  MessagePlugin.success(res.msg)
  if (selectTableRef.value) {
    selectTableRef.value.clearSelection()
  }
  getTreeDataHandler()
  getTableList()
}

const syncTableInfo = async () => {
  const res = await syncTableData()
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  MessagePlugin.success(res.msg)
}

const updateColumn = (row) => {
  columnRef.value.show(row)
}

onMounted(() => {
  getTreeDataHandler()
  getTableList()
})
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
