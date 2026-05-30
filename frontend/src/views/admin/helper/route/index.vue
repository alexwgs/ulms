<template>
  <div style="height: 100%">
    <el-card class="box-card">
      <el-row :gutter="15">
        <el-col :span="8">
          <el-button
            type="primary"
            size="small"
            icon="plus"
            @click="addTreeNode"
          ></el-button>
          <!-- <el-input placeholder="输入关键字进行过滤" size="small" v-model="filterText"></el-input> -->
        </el-col>
        <el-col :span="16"> </el-col>
      </el-row>
      <el-row :gutter="15">
        <el-col :span="8">
          <div style="height: calc(100vh - 250px); overflow: auto">
            <el-tree
              style="margin-top: 10px"
              :data="tree"
              node-key="id"
              :props="{ children: 'children', label: 'name' }"
              :filter-node-method="filterNode"
              @node-drag-start="handleDragStart"
              @node-drag-end="handleDragEnd"
              draggable
              ref="treeRef"
            >
              <template #default="{ node, data }">
                <span class="custom-tree-node">
                  <span>{{ node.label }}</span>
                  <span>
                    <div>
                      <el-text size="small">序号：{{ data.sort }}</el-text>
                      <el-link
                        type="primary"
                        icon="PlusCircle"
                        size="small"
                        underline="hover"
                        @click="() => addChildTreeNode(data)"
                      ></el-link
                      >&ensp;
                      <el-link
                        type="primary"
                        icon="Edit"
                        size="small"
                        underline="hover"
                        @click="() => editTreeNode(data)"
                      ></el-link
                      >&ensp;
                      <el-link
                        type="danger"
                        icon="Delete"
                        size="small"
                        underline="hover"
                        @click="() => deleteTree(data)"
                      ></el-link>
                    </div>
                  </span>
                </span>
              </template>
            </el-tree>
          </div>
        </el-col>
      </el-row>
    </el-card>
    <!--路径设置对话框-->
    <el-dialog
      title="知识路径设置"
      v-model="treeDialogVisible"
      width="50%"
      :before-close="handleClose"
    >
      <el-form
        ref="courseFormRef"
        :model="treeFormData"
        :rules="rules"
        size="small"
        label-width="100px"
      >
        <el-col :span="24">
          <el-form-item label="名称" prop="name">
            <el-input
              v-model="treeFormData.name"
              placeholder="请输入名称"
              :maxlength="100"
              clearable
              :style="{ width: '100%' }"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="排序" prop="sort">
            <el-input-number
              v-model="treeFormData.sort"
              placeholder="由小到大"
              :step="1"
            ></el-input-number>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态" prop="status">
            <el-switch
              v-model="treeFormData.status"
              active-color="#13ce66"
              :active-value="1"
              :inactive-value="0"
            >
            </el-switch>
          </el-form-item>
        </el-col>
      </el-form>
      <template #footer>
        <el-button size="small" @click="treeDialogVisible = false"
          >取 消</el-button
        >
        <el-button size="small" type="primary" @click="treeSubmit"
          >确 定</el-button
        >
      </template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { treeApi } from '@/api/helper/tree'

// 响应式数据
const tree = ref([])
const filterText = ref('')
const handleNode = ref(null)
const treeDialogVisible = ref(false)
const treeFormData = reactive({
  name: '',
  sort: 1,
  pid: 0,
  grade: 1,
  status: 1
})
const rules = reactive({
  name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
  sort: [{ required: true, message: '由小到大', trigger: 'blur' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }]
})

// 引用
const treeRef = ref(null)
const courseFormRef = ref(null)

// 生命周期钩子
onMounted(() => {
  getTree()
})

// 获取路径树数据
const getTree = async () => {
  try {
    const res = await treeApi.getTree()
    if (res.code !== 200) return ElMessage.error(res.msg)
    tree.value = res.data
  } catch (error) {
    ElMessage.error('获取路径树失败')
  }
}

// 删除路径节点
const deleteTree = (record) => {
  ElMessageBox.confirm(
    '此操作将永久删除该节点及以下所有节点, 是否继续?',
    '提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  )
    .then(async () => {
      try {
        const res = await treeApi.deleteTreeWithChildren(record.id)
        if (res.code !== 200) return ElMessage.error(res.msg)
        ElMessage.success(res.msg)
        getTree()
      } catch (error) {
        ElMessage.error('删除失败')
      }
    })
    .catch(() => {
      ElMessage.info('已取消删除')
    })
}

// 更新路径节点
const updateTree = async (record) => {
  try {
    const res = await treeApi.updateTree(record)
    if (res.code !== 200) return ElMessage.error(res.msg)
    ElMessage.success(res.msg)
    getTree()
  } catch (error) {
    ElMessage.error('更新失败')
  }
}

// 添加路径节点
const addTree = async (record) => {
  try {
    const res = await treeApi.addTree(record)
    if (res.code !== 200) return ElMessage.error(res.msg)
    ElMessage.success(res.msg)
    getTree()
  } catch (error) {
    ElMessage.error('添加失败')
  }
}

// 过滤节点
const filterNode = (value, data) => {
  if (!value) return true
  return data.name.indexOf(value) !== -1
}

// 拖拽开始
const handleDragStart = (node, ev) => {
  handleNode.value = node.data
}

// 拖拽结束
const handleDragEnd = (draggingNode, dropNode, dropType, ev) => {
  if (!handleNode.value) return

  if (dropType === 'inner') {
    handleNode.value.pid = dropNode.data.id
    handleNode.value.grade = dropNode.data.grade + 1
  } else if (dropType === 'after') {
    handleNode.value.sort = dropNode.data.sort + 1
    handleNode.value.grade = dropNode.data.grade
    handleNode.value.pid = dropNode.data.pid
  } else if (dropType === 'before') {
    handleNode.value.sort = dropNode.data.sort - 1
    handleNode.value.grade = dropNode.data.grade
    handleNode.value.pid = dropNode.data.pid
  } else {
    return
  }

  updateTree(handleNode.value)
  handleNode.value = null
}

// 提交表单
const treeSubmit = () => {
  courseFormRef.value.validate((valid) => {
    if (valid) {
      if (treeFormData.id === undefined) {
        addTree(treeFormData)
      } else {
        updateTree(treeFormData)
      }
      treeDialogVisible.value = false
    }
  })
}

// 添加根节点
const addTreeNode = () => {
  Object.assign(treeFormData, {
    name: '',
    sort: 1,
    pid: 0,
    grade: 1,
    status: 1,
    id: undefined
  })
  treeDialogVisible.value = true
}

// 添加子节点
const addChildTreeNode = (data) => {
  Object.assign(treeFormData, {
    name: '',
    sort: 1,
    grade: data.grade + 1,
    pid: data.id,
    status: 1,
    id: undefined
  })
  treeDialogVisible.value = true
}

// 编辑节点
const editTreeNode = (data) => {
  Object.assign(treeFormData, { ...data })
  treeDialogVisible.value = true
}

// 关闭对话框
const handleClose = () => {
  treeDialogVisible.value = false
}
</script>
<style lang="less" scoped>
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
</style>
