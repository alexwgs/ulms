<template>
  <div>
    <t-button
      theme="primary"
      size="small" @click="handleAddRoot"
    ><template #icon><DynamicIcon name="plus" /></template></t-button>
    <div style="height: calc(100vh - 250px); overflow: auto; margin-top: 10px">
      <t-tree
        :data="tree"
        :keys="{ value: 'id', label: 'name', children: 'children' }"
        :filter="filterNode"
        @drag-start="handleDragStart"
        @drag-end="handleDragEnd"
        draggable
        ref="treeRef"
      >
        <template #default="{ node }">
          <span class="custom-tree-node">
            <span>{{ node.label }}</span>
            <span>
              <span size="small">序号：{{ node.data.sort }}</span>
              <t-button
                link
                theme="primary"
                size="small"
                @click="handleAddChild(node.data)"
              >
                新增
              </t-button>
              <t-button
                link
                theme="warning"
                size="small"
                @click="handleEdit(node.data)"
              >
                修改
              </t-button>
            </span>
          </span>
        </template>
      </t-tree>
    </div>

    <!-- 课程架构配置弹窗 -->
    <t-dialog
      header="课程架构配置"
      v-model:visible="dialogVisible"
      width="50%"
      @before-close="handleClose"
    >
      <t-form
        ref="formRef"
        :data="formData"
        :rules="rules"
        size="small"
        label-width="100px"
      >
        <t-col :span="12">
          <t-form-item label="名称" name="name">
            <t-input
              v-model="formData.name"
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
              v-model="formData.sort"
              placeholder="由小到大"
              :step="1"
            ></t-input-number>
          </t-form-item>
        </t-col>
        <t-col :span="6">
          <t-form-item label="状态" name="status">
            <t-select
              v-model="formData.status"
              placeholder="请选择状态"
              clearable
              :style="{ width: '100%' }"
            >
              <t-option label="有效" :value="1"></t-option>
              <t-option label="无效" :value="0"></t-option>
            </t-select>
          </t-form-item>
        </t-col>
      </t-form>
      <template #footer>
        <div class="dialog-footer">
          <t-button size="small" @click="dialogVisible = false"
            >取 消</t-button
          >
          <t-button size="small" theme="primary" @click="handleSubmit"
            >确 定</t-button
          >
        </div>
      </template>
    </t-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { courseTypeApi } from '@/api/college/courseType'

const emit = defineEmits(['refresh'])

const tree = ref([])
const filterText = ref('')
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
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    tree.value = res.data
  } catch (error) {
    MessagePlugin.error('获取课程类型树失败')
  }
}

// 更新课程类型
const update = async (record) => {
  try {
    const res = await courseTypeApi.updateCourseType(record)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    getTree()
    emit('refresh')
  } catch (error) {
    MessagePlugin.error('更新课程类型失败')
  }
}

// 添加课程类型
const add = async (record) => {
  try {
    const res = await courseTypeApi.addCourseType(record)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    getTree()
    emit('refresh')
  } catch (error) {
    MessagePlugin.error('添加课程类型失败')
  }
}

// 过滤节点
const filterNode = (node) => {
  if (!filterText.value) return true
  return node.data.name.indexOf(filterText.value) !== -1
}

// 拖拽开始
const handleDragStart = (context) => {
      handleNode.value = context.dragNode.data
}

// 拖拽结束
const handleDragEnd = (context) => {
      const { dragNode, dropNode, dropPosition } = context;
      const dropType = dropPosition === 0 ? 'inner' : dropPosition === -1 ? 'before' : 'after';
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
const handleSubmit = async () => {
  if (!formRef.value) return

  const valid = await formRef.value.validate()
  if (valid === true) {
    if (isEdit.value) {
      update(formData)
    } else {
      add(formData)
    }
    dialogVisible.value = false
  }
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
