<template>
  <t-card class="management-card">
    <div>
      <t-row :gutter="10">
        <t-col :span="3">
          <t-select
            size="small"
            v-model="curcategory"
            @change="getList"
            placeholder="请选择分类状态"
          >
            <t-option label="讨论" value="cyt_artical_category"></t-option>
            <t-option label="项目" value="cyt_item_category"></t-option>
            <t-option label="系统" value="cyt_system_category"></t-option>
          </t-select>
        </t-col>
        <t-col :span="7">
          <t-button
            size="small"
            variant="outline"
            theme="primary" @click="addCategory"><template #icon><DynamicIcon name="add" /></template>新增板块</t-button
          >
        </t-col>
      </t-row>

      <CustomTable rowKey="id"
        :data="data"
        size="small"
        height="calc(100vh - 325px)"
        :loading="loading">
        <TableColumn colKey="code" label="ID" width="80"> </TableColumn>
        <TableColumn colKey="codeval" label="板块名称" width="260">
        </TableColumn>
        <TableColumn label="审核" width="120">
          <template #default="scope">
            <t-tag
              size="small"
              :theme="JSON.parse(scope.row.description).examine?'success':'danger'"
              variant="light"
              >{{JSON.parse(scope.row.description).examine?'是':'否'}}</t-tag
            >
          </template>
        </TableColumn>
        <TableColumn label="发帖" width="120">
          <template #default="scope">
            <t-tag
              size="small"
              :theme="JSON.parse(scope.row.description).postFlag?'success':'danger'"
              variant="light"
              >{{JSON.parse(scope.row.description).postFlag?'是':'否'}}</t-tag
            >
          </template>
        </TableColumn>
        <TableColumn label="评论" width="120">
          <template #default="scope">
            <t-tag
              size="small"
              :theme="JSON.parse(scope.row.description).commentFlag?'success':'danger'"
              variant="light"
              >{{JSON.parse(scope.row.description).commentFlag?'是':'否'}}</t-tag
            >
          </template>
        </TableColumn>
        <TableColumn label="匿名" width="120">
          <template #default="scope">
            <t-tag
              size="small"
              :theme="JSON.parse(scope.row.description).anonFlag?'success':'danger'"
              variant="light"
              >{{JSON.parse(scope.row.description).anonFlag?'是':'否'}}</t-tag
            >
          </template>
        </TableColumn>
        <TableColumn colKey="status" label="状态" width="120">
          <template #default="scope">
            <t-tag
              size="small"
              :theme="scope.row.status === 1? 'success': 'danger'"
              variant="light"
              >{{scope.row.status === 1? '生效': '失效'}}</t-tag
            >
          </template>
        </TableColumn>
        <TableColumn label="操作">
          <template #default="scope">
            <t-button size="small" variant="outline" @click="updateDialog(scope.row)"
              >编辑</t-button
            >
          </template>
        </TableColumn>
      </CustomTable>
    </div>

    <t-dialog
      header="类别配置"
      v-model:visible="descriptionDialogVisible"
      width="50%"
      :before-close="addDialogClose"
    >
      <t-form
        :data="category"
        ref="formRef"
        :rules="rules"
        label-width="80px"
      >
        <t-form-item label="name">
          <t-input
            size="small"
            v-model="category.name"
            type="number"
            readonly
          ></t-input>
        </t-form-item>
        <t-form-item label="编码" name="code">
          <t-input size="small" v-model="category.code" readonly></t-input>
        </t-form-item>
        <t-form-item label="类别名称" name="codeval">
          <t-input size="small" v-model="category.codeval"></t-input>
        </t-form-item>
        <t-form-item label="板块配置">
<t-switch v-model="description.examine" :label="['审核', '']">
</t-switch>
<t-switch v-model="description.postFlag" :label="['发帖', '']">
</t-switch>
<t-switch v-model="description.commentFlag" :label="['评论', '']">
</t-switch>
<t-switch v-model="description.anonFlag" :label="['匿名', '']">
</t-switch>
        </t-form-item>
        <t-form-item label="状态">
          <t-select
            size="small"
            v-model="category.status"
            placeholder="请选择分类状态"
          >
            <t-option label="有效" :value="1"></t-option>
            <t-option label="失效" :value="0"></t-option>
          </t-select>
        </t-form-item>
      </t-form>
      <template #footer>
        <t-space>
          <t-button variant="outline" size="small" @click="descriptionDialogVisible = false"
            >取 消</t-button
          >
          <t-button variant="outline" size="small" theme="primary" @click="categorySubmit"
            >确 定</t-button
          >
        </t-space>
      </template>
    </t-dialog>

    <t-dialog
      header="新增分类"
      v-model:visible="addDialogVisible"
      width="50%"
      :before-close="addDialogClose"
    >
      <t-form
        :data="category"
        ref="addFormRef"
        :rules="rules"
        label-width="80px"
      >
        <t-form-item label="name">
          <t-input size="small" v-model="category.name" readonly></t-input>
        </t-form-item>
        <t-form-item label="编码" name="code">
          <t-input
            size="small"
            v-model="category.code"
            type="number"
            placeholder="不可与现有ID重复"
          ></t-input>
        </t-form-item>
        <t-form-item label="类别名称" name="codeval">
          <t-input size="small" v-model="category.codeval"></t-input>
        </t-form-item>
        <t-form-item label="板块配置">
<t-switch v-model="description.examine" :label="['审核', '']">
</t-switch>
<t-switch v-model="description.postFlag" :label="['发帖', '']">
</t-switch>
<t-switch v-model="description.commentFlag" :label="['评论', '']">
</t-switch>
<t-switch v-model="description.anonFlag" :label="['匿名', '']">
</t-switch>
        </t-form-item>
        <t-form-item label="状态" required>
          <t-select
            size="small"
            v-model="category.status"
            placeholder="请选择分类状态"
          >
            <t-option label="有效" :value="1"></t-option>
            <t-option label="失效" :value="0"></t-option>
          </t-select>
        </t-form-item>
      </t-form>
      <template #footer>
        <t-space>
          <t-button variant="outline" size="small" @click="addDialogVisible = false"
            >取 消</t-button
          >
          <t-button variant="outline" size="small" theme="primary" @click="categoryAddSubmit"
            >确 定</t-button
          >
        </t-space>
      </template>
    </t-dialog>
  </t-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { MessagePlugin, LoadingPlugin } from 'tdesign-vue-next'
import {
  getDictionaryList,
  updateDictionary,
  addDictionary
} from '@/api/cyt/index.js'

const data = ref([])
const loading = ref(false)

const description = reactive({
  examine: false,
  postFlag: true,
  commentFlag: true,
  anonFlag: false
})

const category = reactive({
  id: 0,
  name: '',
  code: 0,
  codeval: '',
  status: 1,
  description: ''
})

const curcategory = ref('cyt_artical_category')
const descriptionDialogVisible = ref(false)
const addDialogVisible = ref(false)
const formRef = ref(null)
const addFormRef = ref(null)

const rules = {
  code: [
    { required: true, message: '请输入与其他分类不同到ID', trigger: 'blur' }
  ],
  codeval: [
    { required: true, message: '请输入分类名称', trigger: 'blur' },
    { min: 2, max: 7, message: '长度在 2 到 7 个字符', trigger: 'blur' }
  ]
}

const getList = async () => {
  try {
    loading.value = true
    const res = await getDictionaryList(curcategory.value)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    data.value = res.data
  } catch (error) {
    console.error('获取字典列表失败:', error)
    MessagePlugin.error('获取字典列表失败')
  } finally {
    loading.value = false
  }
}

const addCategory = () => {
  category.name = curcategory.value
  addDialogVisible.value = true
}

const updateDialog = (row) => {
  descriptionDialogVisible.value = true
  category.id = row.id
  category.name = row.name
  category.code = row.code
  category.codeval = row.codeval
  category.status = row.status
  const desc = JSON.parse(row.description)
  description.examine = desc.examine
  description.postFlag = desc.postFlag
  description.commentFlag = desc.commentFlag
  description.anonFlag = desc.anonFlag
}

const categorySubmit = async () => {
  if (!formRef.value) return

  const valid = await formRef.value.validate()
  if (valid === true) {
    try {
      category.description = JSON.stringify(description)
      const res = await updateDictionary(category)
      if (res.code !== 200) {
        MessagePlugin.error(res.msg)
        return
      }
      MessagePlugin.success(res.msg)
      getList()
      descriptionDialogVisible.value = false
    } catch (error) {
      console.error('更新字典失败:', error)
      MessagePlugin.error('更新字典失败')
    }
  } else {
    MessagePlugin.error('表单校验失败！请检查表单！')
  }
}

const categoryAddSubmit = async () => {
  if (!addFormRef.value) return

  const valid = await addFormRef.value.validate()
  if (valid === true) {
    try {
      category.name = curcategory.value
      category.description = JSON.stringify(description)
      const res = await addDictionary(category)
      if (res.code !== 200) {
        MessagePlugin.error(res.msg)
        return
      }
      MessagePlugin.success(res.msg)
      getList()
      addDialogVisible.value = false
    } catch (error) {
      console.error('添加字典失败:', error)
      MessagePlugin.error('添加字典失败')
    }
  } else {
    MessagePlugin.error('表单校验失败！请检查表单！')
  }
}

const addDialogClose = () => {
  if (addFormRef.value) {
    addFormRef.value.resetFields()
  }
  addDialogVisible.value = false
  descriptionDialogVisible.value = false
}

onMounted(() => {
  getList()
})
</script>

<style lang="less" scoped>
.management-card {
  height: calc(100vh - 190px);
  overflow: auto;
}

.t-switch {
  padding-left: 25px;
}
</style>
