<template>
  <el-alert
    title="操作说明"
    type="info"
    :closable="false"
    description="请正确使用部门编号：1.一共需设置4位数字。2.第一位为地区编码3为武汉。3.第二位为科室编号。4.最后两位为组别编号。"
  />
  <el-card class="box-card">
    <el-row :gutter="20">
      <el-col :span="10">
        <el-col :span="6">
          <el-button type="primary" size="small" @click="addDepartment"
            >添加部门</el-button
          >
        </el-col>
      </el-col>
    </el-row>
    <el-table
      :data="deptartmentData"
      size="small"
      style="width: 100%; margin-bottom: 20px"
      height="calc(100vh - 330px)"
      row-key="deptNum"
      stripe
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column prop="deptNum" label="部门编号" sortable width="180">
      </el-table-column>
      <el-table-column prop="deptName" label="部门名称" sortable>
      </el-table-column>
      <el-table-column prop="upDept" label="上级部门"> </el-table-column>
      <el-table-column label="操作" width="200">
        <template #default="scope">
          <el-button
            size="small"
            icon="Edit"
            type="warning"
            @click="update(scope.row)"
            circle
          ></el-button>
          <el-button
            size="small"
            icon="Delete"
            type="danger"
            @click="remove(scope.$index, scope.row)"
            circle
          ></el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>

  <el-dialog
    :title="dialogTitle"
    :close-on-click-modal="false"
    v-model="dialogFormVisible"
  >
    <el-form :model="departmentForm" ref="departmentFormRef" :rules="formRules">
      <el-form-item
        label="部门编号"
        :label-width="formLabelWidth"
        prop="deptNum"
      >
        <el-input
          size="small"
          type="number"
          v-model="departmentForm.deptNum"
          autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item
        label="部门名称"
        :label-width="formLabelWidth"
        prop="deptName"
      >
        <el-input
          size="small"
          v-model="departmentForm.deptName"
          autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item
        label="上级部门"
        :label-width="formLabelWidth"
        prop="upDept"
      >
        <el-select
          size="small"
          v-model="departmentForm.upDept"
          placeholder="请选择上级部门"
          :disabled="upDeptDisabled"
        >
          <el-option label="无上级部门" value="" key=""></el-option>
          <el-option
            v-for="item in deptartmentData"
            :label="item.deptName"
            :value="item.deptNum"
            :key="item.deptNum"
          ></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button size="small" @click="dialogFormVisible = false"
          >取 消</el-button
        >
        <el-button size="small" type="primary" @click="dialogFormSubmit"
          >确 定</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { departmentApi } from '@/api/system/department'

const deptartmentData = ref([])
const dialogTitle = ref('')
const dialogFormVisible = ref(false)
const formLabelWidth = '120px'
const upDeptDisabled = ref(false)
const departmentFormRef = ref(null)

const departmentForm = reactive({
  deptNum: '',
  deptName: '',
  upDept: ''
})

const formRules = reactive({
  deptNum: [
    { required: true, message: '请输入部门编号', trigger: 'blur' },
    {
      min: 4,
      max: 4,
      message: '请输入 4 位以 3 开头的数字',
      trigger: 'blur'
    }
  ],
  deptName: [
    { required: true, message: '请输入部门名称', trigger: 'blur' },
    { min: 2, max: 8, message: '请输入 3 到 8 个字符', trigger: 'blur' }
  ]
})

onMounted(() => {
  getDepartmentData()
})

const getDepartmentData = async () => {
  const res = await departmentApi.treeDepartment()
  if (res.code !== 200) return ElMessage.error(res.msg)
  deptartmentData.value = res.data
}

const addDepartment = () => {
  dialogTitle.value = '新增部门'
  upDeptDisabled.value = false
  dialogFormVisible.value = true
  Object.assign(departmentForm, {
    deptNum: '',
    deptName: '',
    upDept: ''
  })
  if (departmentFormRef.value) {
    departmentFormRef.value.resetFields()
  }
}

const update = (data) => {
  if (data.upDept === undefined || data.upDept === '') {
    upDeptDisabled.value = true
    data.upDept = ''
  } else {
    upDeptDisabled.value = false
  }
  dialogTitle.value = '编辑部门'
  departmentForm.deptNum = data.deptNum
  departmentForm.deptName = data.deptName
  departmentForm.upDept = data.upDept
  dialogFormVisible.value = true
}

const remove = (node, data) => {
  ElMessageBox.confirm(
    '此操作将永久删除该记录,同时会删除子菜单, 是否继续?',
    '提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(async () => {
    const res = await departmentApi.deleteDepartment(data.deptNum)
    if (res.code !== 200) return
    ElMessage.success(res.msg)
    const parent = node.parent
    const children = parent.data.children || parent.data
    const index = children.findIndex((d) => d.id === data.id)
    children.splice(index, 1)
  })
}

const dialogFormSubmit = () => {
  if (dialogTitle.value === '新增部门') {
    departmentFormRef.value.validate(async (valid) => {
      if (!valid) return false
      const res = await departmentApi.addDepartment(departmentForm)
      if (res.code !== 200) return
      ElMessage.success(res.msg)
      dialogFormVisible.value = false
      getDepartmentData()
    })
  } else {
    departmentFormRef.value.validate(async (valid) => {
      if (!valid) return false
      const res = await departmentApi.updateDepartmentById(
        departmentForm.deptNum,
        departmentForm
      )
      if (res.code !== 200) return
      ElMessage.success(res.msg)
      dialogFormVisible.value = false
      getDepartmentData()
    })
  }
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
.box-card {
  height: calc(100vh - 240px);
}
</style>
