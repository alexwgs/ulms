<template>
  <el-card class="box-card">
    <div>
      <el-row :gutter="10">
        <el-col :span="6">
          <el-select
            size="small"
            v-model="curcategory"
            @change="getList"
            placeholder="请选择分类状态"
          >
            <el-option label="讨论" value="cyt_artical_category"></el-option>
            <el-option label="项目" value="cyt_item_category"></el-option>
            <el-option label="系统" value="cyt_system_category"></el-option>
          </el-select>
        </el-col>
        <el-col :span="14">
          <el-button
            size="small"
            type="primary"
            icon="Plus"
            @click="addCategory"
            >新增板块</el-button
          >
        </el-col>
      </el-row>

      <el-table
        :data="data"
        size="small"
        height="calc(100vh - 325px)"
        v-loading="loading"
      >
        <el-table-column prop="code" label="ID" width="80"> </el-table-column>
        <el-table-column prop="codeval" label="板块名称" width="260">
        </el-table-column>
        <el-table-column label="审核" width="120">
          <template #default="scope">
            <el-tag
              size="small"
              :type="JSON.parse(scope.row.description).examine?'success':'danger'"
              effect="dark"
              >{{JSON.parse(scope.row.description).examine?'是':'否'}}</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column label="发帖" width="120">
          <template #default="scope">
            <el-tag
              size="small"
              :type="JSON.parse(scope.row.description).postFlag?'success':'danger'"
              effect="dark"
              >{{JSON.parse(scope.row.description).postFlag?'是':'否'}}</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column label="评论" width="120">
          <template #default="scope">
            <el-tag
              size="small"
              :type="JSON.parse(scope.row.description).commentFlag?'success':'danger'"
              effect="dark"
              >{{JSON.parse(scope.row.description).commentFlag?'是':'否'}}</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column label="匿名" width="120">
          <template #default="scope">
            <el-tag
              size="small"
              :type="JSON.parse(scope.row.description).anonFlag?'success':'danger'"
              effect="dark"
              >{{JSON.parse(scope.row.description).anonFlag?'是':'否'}}</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="120">
          <template #default="scope">
            <el-tag
              size="small"
              :type="scope.row.status === 1? 'success': 'danger'"
              effect="dark"
              >{{scope.row.status === 1? '生效': '失效'}}</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button size="small" @click="updateDialog(scope.row)"
              >编辑</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog
      title="类别配置"
      v-model="descriptionDialogVisible"
      width="50%"
      :before-close="addDialogClose"
    >
      <el-form
        :model="category"
        ref="formRef"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="name">
          <el-input
            size="small"
            v-model="category.name"
            type="number"
            readonly
          ></el-input>
        </el-form-item>
        <el-form-item label="编码" prop="code">
          <el-input size="small" v-model="category.code" readonly></el-input>
        </el-form-item>
        <el-form-item label="类别名称" prop="codeval">
          <el-input size="small" v-model="category.codeval"></el-input>
        </el-form-item>
        <el-form-item label="板块配置">
          <el-switch v-model="description.examine" active-text="审核">
          </el-switch>
          <el-switch v-model="description.postFlag" active-text="发帖">
          </el-switch>
          <el-switch v-model="description.commentFlag" active-text="评论">
          </el-switch>
          <el-switch v-model="description.anonFlag" active-text="匿名">
          </el-switch>
        </el-form-item>
        <el-form-item label="状态">
          <el-select
            size="small"
            v-model="category.status"
            placeholder="请选择分类状态"
          >
            <el-option label="有效" :value="1"></el-option>
            <el-option label="失效" :value="0"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button size="small" @click="descriptionDialogVisible = false"
            >取 消</el-button
          >
          <el-button size="small" type="primary" @click="categorySubmit"
            >确 定</el-button
          >
        </div>
      </template>
    </el-dialog>

    <el-dialog
      title="新增分类"
      v-model="addDialogVisible"
      width="50%"
      :before-close="addDialogClose"
    >
      <el-form
        :model="category"
        ref="addFormRef"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="name">
          <el-input size="small" v-model="category.name" readonly></el-input>
        </el-form-item>
        <el-form-item label="编码" prop="code">
          <el-input
            size="small"
            v-model="category.code"
            type="number"
            placeholder="不可与现有ID重复"
          ></el-input>
        </el-form-item>
        <el-form-item label="类别名称" prop="codeval">
          <el-input size="small" v-model="category.codeval"></el-input>
        </el-form-item>
        <el-form-item label="板块配置">
          <el-switch v-model="description.examine" active-text="审核">
          </el-switch>
          <el-switch v-model="description.postFlag" active-text="发帖">
          </el-switch>
          <el-switch v-model="description.commentFlag" active-text="评论">
          </el-switch>
          <el-switch v-model="description.anonFlag" active-text="匿名">
          </el-switch>
        </el-form-item>
        <el-form-item label="状态" required>
          <el-select
            size="small"
            v-model="category.status"
            placeholder="请选择分类状态"
          >
            <el-option label="有效" :value="1"></el-option>
            <el-option label="失效" :value="0"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button size="small" @click="addDialogVisible = false"
            >取 消</el-button
          >
          <el-button size="small" type="primary" @click="categoryAddSubmit"
            >确 定</el-button
          >
        </div>
      </template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElLoading } from 'element-plus'
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
      ElMessage.error(res.msg)
      return
    }
    data.value = res.data
  } catch (error) {
    console.error('获取字典列表失败:', error)
    ElMessage.error('获取字典列表失败')
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

  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        category.description = JSON.stringify(description)
        const res = await updateDictionary(category)
        if (res.code !== 200) {
          ElMessage.error(res.msg)
          return
        }
        ElMessage.success(res.msg)
        getList()
        descriptionDialogVisible.value = false
      } catch (error) {
        console.error('更新字典失败:', error)
        ElMessage.error('更新字典失败')
      }
    } else {
      ElMessage.error('表单校验失败！请检查表单！')
    }
  })
}

const categoryAddSubmit = async () => {
  if (!addFormRef.value) return

  await addFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        category.name = curcategory.value
        category.description = JSON.stringify(description)
        const res = await addDictionary(category)
        if (res.code !== 200) {
          ElMessage.error(res.msg)
          return
        }
        ElMessage.success(res.msg)
        getList()
        addDialogVisible.value = false
      } catch (error) {
        console.error('添加字典失败:', error)
        ElMessage.error('添加字典失败')
      }
    } else {
      ElMessage.error('表单校验失败！请检查表单！')
    }
  })
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
.box-card {
  height: calc(100vh - 190px);
  overflow: auto;
}

.el-switch {
  padding-left: 25px;
}
</style>
