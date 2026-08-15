<template>
  <div style="height: 100%">
    <t-card class="management-card">
      <t-row :gutter="15">
        <t-col :span="4">
          <t-button variant="outline"
            theme="primary"
            size="small" @click="addTreeNode"
          >新增</t-button>
          <!-- <t-input placeholder="输入关键字进行过滤" size="small" v-model="filterText"></t-input> -->
        </t-col>
        <t-col :span="8"> </t-col>
      </t-row>
      <t-row :gutter="15">
        <t-col :span="4">
          <div style="height: calc(100vh - 250px); overflow: auto">
            <t-tree
              style="margin-top: 10px"
              :data="tree"
              :keys="{ value: 'id', label: 'name', children: 'children' }"
              :filter="filterNode"
              @drag-start="handleDragStart"
              @drop="handleDrop"
              draggable
              ref="treeRef"
            >
              <template #label="{ node }">
                <span class="custom-tree-node">
                  <span>{{ node.label }}</span>
                  <span>
                    <div>
                      <span size="small">序号：{{ node.data.sort }}</span>
                      <t-link
                        theme="primary"
                        size="small"
                        :underline="false"
                        @click="() => addChildTreeNode(node.data)"
                        ><template #prefixIcon><DynamicIcon name="add" /></template></t-link
                      >&ensp;
                      <t-link
                        theme="primary"
                        size="small"
                        :underline="false"
                        @click="() => editTreeNode(node.data)"
                        ><template #prefixIcon><DynamicIcon name="edit" /></template></t-link
                      >&ensp;
                      <t-link
                        theme="danger"
                        size="small"
                        :underline="false"
                        @click="() => deleteTree(node.data)"
                        ><template #prefixIcon><DynamicIcon name="delete" /></template></t-link>
                    </div>
                  </span>
                </span>
              </template>
            </t-tree>
          </div>
        </t-col>
      </t-row>
    </t-card>
    <!--路径设置对话框-->
    <t-dialog
      header="知识路径设置"
      v-model:visible="treeDialogVisible"
      width="50%"
      :before-close="handleClose"
    >
      <t-form
        ref="courseFormRef"
        :data="treeFormData"
        :rules="rules"
        size="small"
        label-width="100px"
      >
        <t-row :gutter="15">
        <t-col :span="12">
          <t-form-item label="名称" name="name">
            <t-input
              v-model="treeFormData.name"
              placeholder="请输入名称"
              :maxlength="100"
              clearable
              :style="{ width: '100%' }"
            ></t-input>
          </t-form-item>
        </t-col>
        <t-col :span="6">
          <t-form-item label="排序" name="sort">
            <t-input-number
              v-model="treeFormData.sort"
              placeholder="由小到大"
              :step="1"
            ></t-input-number>
          </t-form-item>
        </t-col>
        <t-col :span="6">
          <t-form-item label="状态" name="status">
            <t-switch
              v-model="treeFormData.status"
              :custom-value="[1, 0]"
            >
            </t-switch>
          </t-form-item>
        </t-col>
        </t-row>
      </t-form>
      <template #footer>
        <t-button size="small" @click="treeDialogVisible = false"
          >取 消</t-button
        >
        <t-button size="small" theme="primary" @click="treeSubmit"
          >确 定</t-button
        >
      </template>
    </t-dialog>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { treeApi } from '@/api/helper/tree'
import { useConfirm } from '@/hooks/useConfirm'

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
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    tree.value = res.data
  } catch (error) {
    MessagePlugin.error('获取路径树失败')
  }
}

// 删除路径节点
const deleteTree = async (record) => {
  const { confirm: confirmDialog } = useConfirm()
  const ok = await confirmDialog(
    '此操作将永久删除该节点及以下所有节点, 是否继续?',
    { title: '提示' }
  )
  if (!ok) {
    MessagePlugin.info('已取消删除')
    return
  }
  try {
    const res = await treeApi.deleteTreeWithChildren(record.id)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    getTree()
  } catch (error) {
    MessagePlugin.error('删除失败')
  }
}

// 更新路径节点
const updateTree = async (record) => {
  try {
    const res = await treeApi.updateTree(record)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    getTree()
  } catch (error) {
    MessagePlugin.error('更新失败')
  }
}

// 添加路径节点
const addTree = async (record) => {
  try {
    const res = await treeApi.addTree(record)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    getTree()
  } catch (error) {
    MessagePlugin.error('添加失败')
  }
}

// 过滤节点
const filterNode = (node) => {
  if (!filterText.value) return true
  return node.data.name.indexOf(filterText.value) !== -1
}

// 拖拽开始
const handleDragStart = (context) => {
  handleNode.value = context.node.data
}

// 拖拽放置（TDesign 在 drop 事件才提供 dropNode/dragNode/dropPosition）
const handleDrop = (context) => {
  const { dragNode, dropNode, dropPosition } = context
  if (!dragNode || !dropNode) return
  const dropType =
    dropPosition === 0 ? 'inner' : dropPosition === -1 ? 'before' : 'after'
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
const treeSubmit = async () => {
  const valid = await courseFormRef.value.validate()
  if (valid === true) {
    if (treeFormData.id === undefined) {
      addTree(treeFormData)
    } else {
      updateTree(treeFormData)
    }
    treeDialogVisible.value = false
  }
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
