<template>
  <div>
    <el-card class="box-card">
      <el-row style="padding-bottom: 10px" :gutter="10">
        <el-col :span="4"
          ><el-button
            type="primary"
            size="small"
            @click="
              treeNodeManage(
                { name: '', pid: 0, treeLevel: 0, sort: 1, status: 1 },
                'add'
              )
            "
            >新增一级节点</el-button
          ></el-col
        >
        <el-col :span="4"
          ><el-tag :type="currentNode == null ? 'danger' : 'success'">{{
            currentNode == null ? '选择目录' : '表加入:' + currentNode.name
          }}</el-tag></el-col
        >
        <el-col :span="3">
          <el-popconfirm
            title="确定要讲表移入此分类么？"
            width="250"
            @confirm="moveInTree()"
          >
            <template #reference
              ><el-button
                slot="reference"
                type="primary"
                size="small"
                icon="arrow-left"
                >移入</el-button
              ></template
            >
          </el-popconfirm>
        </el-col>
        <el-col :span="4">
          <el-select
            v-model="tableQueryInfo.flag"
            size="small"
            placeholder="请选择过滤规则"
            @change="getTableList"
          >
            <el-option label="已分类" :value="1"></el-option>
            <el-option label="未分类" :value="0"></el-option>
          </el-select>
        </el-col>
        <el-col :span="7">
          <el-input
            placeholder="查询"
            size="small"
            v-model="tableQueryInfo.keyWord"
            class="input-with-select"
          >
            <el-button
              slot="append"
              icon="search"
              @click="getTableList"
              size="small"
            ></el-button>
          </el-input>
        </el-col>
        <el-col :span="2">
          <el-button type="danger" size="small" @click="syncTableInfo()"
            >同步</el-button
          >
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-tree
            :data="treeData"
            ref="tree"
            node-key="id"
            @node-click="nodeclick"
          >
            <template #default="{ node, data }">
              <span class="custom-tree-node">
                <span>{{ data.name }}</span>
                <span
                  ><div v-if="data.treeLevel !== 3">
                    <el-button
                      link
                      type="primary"
                      size="small"
                      @click="treeNodeManage(data, 'add')"
                      >新增</el-button
                    >
                    <el-button
                      link
                      type="warning"
                      size="small"
                      @click="treeNodeManage(data, 'edit')"
                      >修改</el-button
                    >
                  </div>
                  <el-popconfirm
                    v-else
                    title="确定要讲表移出此分类么？"
                    width="250"
                    @confirm="removeTreeNood(node, data)"
                    ><template #reference>
                      <el-button
                        slot="reference"
                        type="danger"
                        link
                        size="small"
                        >删除</el-button
                      >
                    </template></el-popconfirm
                  >
                </span>
              </span>
            </template>
          </el-tree>
        </el-col>
        <el-col :span="16">
          <el-table
            :data="tableData"
            size="small"
            stripe
            style="width: 100%"
            ref="selectTable"
            height="calc(100vh - 350px)"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column
              prop="name"
              label="表名"
              width="180"
            ></el-table-column>
            <el-table-column
              prop="tableName"
              label="中文名"
              width="180"
            ></el-table-column>
            <el-table-column
              prop="memo"
              label="描述"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column label="操作" width="60">
              <template #default="scope">
                <el-button
                  size="small"
                  icon="edit"
                  type="warning"
                  @click="updateColumn(scope.row)"
                  circle
                ></el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
    </el-card>

    <el-dialog
      :title="tableTreeDialogTitle"
      v-model="tableTreeDialogFormVisible"
    >
      <el-form
        :model="treeForm"
        size="small"
        ref="treeForm"
        :rules="treeFormRules"
      >
        <el-form-item
          label="分类名称"
          prop="name"
          :label-width="formLabelWidth"
        >
          <el-input v-model="treeForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="状态" :label-width="formLabelWidth">
          <el-select v-model="treeForm.status" placeholder="请选择该菜单状态">
            <el-option label="显示" :value="1"></el-option>
            <el-option label="隐示" :value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="排序" prop="sort" :label-width="formLabelWidth">
          <el-input
            type="number"
            v-model="treeForm.sort"
            maxlength="6"
            placeholder="由小到大"
            autocomplete="off"
          ></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button size="small" @click="tableTreeDialogFormVisible = false"
            >取 消</el-button
          >
          <el-button size="small" type="primary" @click="tableTreeFormSubmit"
            >确 定</el-button
          >
        </div>
      </template>
    </el-dialog>
    <Column ref="columnRef"></Column>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
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
  if (res.code !== 200) return ElMessage.error(res.msg)
  treeData.value = res.data
}

const nodeclick = (obj, node, self) => {
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

  await treeFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        let res
        if (tableTreeDialogTitle.value === '节点新增') {
          res = await addTreeNode(treeForm)
        } else if (tableTreeDialogTitle.value === '节点修改') {
          res = await updateTreeNode(treeForm)
        }
        if (res.code !== 200) return ElMessage.error(res.msg)
        ElMessage.success(res.msg)
        tableTreeDialogFormVisible.value = false
        getTreeDataHandler()
      } catch (error) {
        console.error('操作失败:', error)
        ElMessage.error('操作失败')
      }
    } else {
      ElMessage.error('表单校验失败！')
      return false
    }
  })
}

const removeTreeNood = async (node, data) => {
  const res = await deleteTreeNode(data.id)
  if (res.code !== 200) return ElMessage.error(res.msg)
  ElMessage.success(res.msg)
  getTreeDataHandler()
}

const getTableList = async () => {
  const res = await fetchTableList(tableQueryInfo)
  if (res.code !== 200) return ElMessage.error(res.msg)
  tableData.value = res.data
}

const handleSelectionChange = (val) => {
  selectTableNames.value = val.map((list) => list.name)
  selectTableIds.value = val.map((list) => list.id)
}

const moveInTree = async () => {
  if (currentNode.value == null)
    return ElMessage.error('没有选中需移入的分类！')
  if (selectTableNames.value.length === 0)
    return ElMessage.error('至少请选择一张数据表！')
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
  if (res.code !== 200) return ElMessage.error(res.msg)
  ElMessage.success(res.msg)
  if (selectTableRef.value) {
    selectTableRef.value.clearSelection()
  }
  getTreeDataHandler()
  getTableList()
}

const syncTableInfo = async () => {
  const res = await syncTableData()
  if (res.code !== 200) return ElMessage.error(res.msg)
  ElMessage.success(res.msg)
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
