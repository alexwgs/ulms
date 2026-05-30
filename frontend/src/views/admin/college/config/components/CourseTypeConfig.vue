<template>
  <div>
    <el-button
      type="primary"
      size="small"
      icon="plus"
      @click="handleAddRoot"
    ></el-button>
    <div style="height: calc(100vh - 250px); overflow: auto; margin-top: 10px">
      <el-tree
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
              <el-text size="small">序号：{{ data.sort }}</el-text>
              <el-button
                link
                type="primary"
                size="small"
                @click="handleAddChild(data)"
              >
                新增
              </el-button>
              <el-button
                link
                type="warning"
                size="small"
                @click="handleEdit(data)"
              >
                修改
              </el-button>
            </span>
          </span>
        </template>
      </el-tree>
    </div>

    <!-- 课程架构配置弹窗 -->
    <el-dialog
      title="课程架构配置"
      v-model="dialogVisible"
      width="50%"
      :before-close="handleClose"
    >
      <el-form
        ref="formRef"
        :model="formData"
        :rules="rules"
        size="small"
        label-width="100px"
      >
        <el-col :span="24">
          <el-form-item label="名称" prop="name">
            <el-input
              v-model="formData.name"
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
              v-model="formData.sort"
              placeholder="由小到大"
              :step="1"
            ></el-input-number>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态" prop="status">
            <el-select
              v-model="formData.status"
              placeholder="请选择状态"
              clearable
              :style="{ width: '100%' }"
            >
              <el-option label="有效" :value="1"></el-option>
              <el-option label="无效" :value="0"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button size="small" @click="dialogVisible = false"
            >取 消</el-button
          >
          <el-button size="small" type="primary" @click="handleSubmit"
            >确 定</el-button
          >
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { courseTypeApi } from '@/api/college/courseType'

const emit = defineEmits(['refresh'])

const tree = ref([])
const treeRef = ref(null)
const dialogVisible = ref(false)
const formRef = ref(null)
const handleNode = ref(null)
const isEdit = ref(false)

const formData = reactive({
  id: undefined,
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

// 获取课程类型树
const getTree = async () => {
  try {
    const res = await courseTypeApi.getCourseTypeTree()
    if (res.code !== 200) return ElMessage.error(res.msg)
    tree.value = res.data
  } catch (error) {
    ElMessage.error('获取课程类型树失败')
  }
}

// 更新课程类型
const update = async (record) => {
  try {
    const res = await courseTypeApi.updateCourseType(record)
    if (res.code !== 200) return ElMessage.error(res.msg)
    ElMessage.success(res.msg)
    getTree()
    emit('refresh')
  } catch (error) {
    ElMessage.error('更新课程类型失败')
  }
}

// 添加课程类型
const add = async (record) => {
  try {
    const res = await courseTypeApi.addCourseType(record)
    if (res.code !== 200) return ElMessage.error(res.msg)
    ElMessage.success(res.msg)
    getTree()
    emit('refresh')
  } catch (error) {
    ElMessage.error('添加课程类型失败')
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
  update(handleNode.value)
}

// 添加根节点
const handleAddRoot = () => {
  isEdit.value = false
  Object.assign(formData, {
    id: undefined,
    name: '',
    sort: 1,
    pid: 0,
    grade: 1,
    status: 1
  })
  dialogVisible.value = true
}

// 添加子节点
const handleAddChild = (data) => {
  isEdit.value = false
  Object.assign(formData, {
    id: undefined,
    name: '',
    sort: 1,
    grade: data.grade + 1,
    pid: data.id,
    status: 1
  })
  dialogVisible.value = true
}

// 编辑节点
const handleEdit = (data) => {
  isEdit.value = true
  Object.assign(formData, JSON.parse(JSON.stringify(data)))
  dialogVisible.value = true
}

// 提交表单
const handleSubmit = () => {
  if (!formRef.value) return

  formRef.value.validate((valid) => {
    if (valid) {
      if (isEdit.value) {
        update(formData)
      } else {
        add(formData)
      }
      dialogVisible.value = false
    }
  })
}

// 关闭弹窗
const handleClose = () => {
  dialogVisible.value = false
}

onMounted(() => {
  getTree()
})

defineExpose({
  getTree
})
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
