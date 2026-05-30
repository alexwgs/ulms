<template>
  <el-card class="box-card">
    <el-row :gutter="20">
      <el-col :span="24">
        <el-button
          type="primary"
          size="small"
          @click="roleAddVisible = !roleAddVisible"
          >添加角色</el-button
        >
      </el-col>
    </el-row>
    <el-alert
      style="margin: 10px 0"
      title="操作说明"
      size=""
      type="info"
      description="请正确使用角色配置：1.建议角色名称简介且清晰。2.请注意各个角色的菜单操作权限配置！"
      :closable="false"
    >
    </el-alert>
    <el-table
      :data="roleList"
      size="small"
      @sort-change="tableSort"
      height="calc(100vh - 440px)"
    >
      <el-table-column type="expand">
        <template #default="{ row: scope }">
          <el-row
            :class="['bdbottom', i1 === 0 ? 'bdtop' : '', 'vcenter']"
            v-for="(item1, i1) in scope.children"
            :key="item1.id"
          >
            <el-col :span="5">
              <el-tag :key="item1.id">{{ item1.name }}</el-tag>
              <i class="el-icon-caret-right"></i>
            </el-col>
            <el-col :span="19">
              <el-row
                :class="[i2 === 0 ? '' : 'bdtop', 'vcenter']"
                v-for="(item2, i2) in item1.children"
                :key="item2.id"
              >
                <el-col :span="6">
                  <el-tag :key="item2.id" type="success">{{
                    item2.name
                  }}</el-tag>
                  <i class="el-icon-caret-right"></i>
                </el-col>
                <el-col :span="18">
                  <el-tag
                    v-for="item3 in item2.children"
                    :key="item3.id"
                    type="warning"
                    closable
                    >{{ item3.name }}</el-tag
                  >
                </el-col>
              </el-row>
            </el-col>
          </el-row>
        </template>
      </el-table-column>
      <el-table-column
        label="角色ID"
        prop="id"
        sortable="custom"
      ></el-table-column>
      <el-table-column
        label="角色名称"
        prop="roleName"
        sortable="custom"
      ></el-table-column>
      <el-table-column label="操作" fixed="right" width="300px">
        <template #default="{ row: scope }">
          <el-button
            type="warning"
            size="small"
            icon="edit"
            @click="updateRole(scope)"
            circle
          ></el-button>
          <el-button
            type="danger"
            size="small"
            icon="delete"
            @click="removeRoleById(scope)"
            circle
          ></el-button>
          <el-button size="small" @click="dispatchPermission(scope)"
            >分配权限</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="queryInfo.pageNum"
      :page-sizes="pageSizes"
      :page-size="queryInfo.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>

    <el-dialog
      title="分配权限"
      height="400px"
      :close-on-click-modal="false"
      v-model="permissionTreeVisible"
      @close="closePermissionTreeDialog"
    >
      <div class="dialog-body">
        <div class="buttons">
          <el-radio v-model="systemRadio" @change="getMenuTree" label=""
            >全部</el-radio
          >
          <el-radio v-model="systemRadio" @change="getMenuTree" label="a6squre"
            >A6广场</el-radio
          >
          <el-radio v-model="systemRadio" @change="getMenuTree" label="bpms"
            >绩效平台</el-radio
          >
          <el-button size="small" @click="setAllExpand(true)"
            >展开所有节点</el-button
          >
          <el-button size="small" @click="setAllExpand(false)"
            >收起所有节点</el-button
          >
        </div>
        <el-tree
          :data="permissionsTree"
          show-checkbox
          node-key="id"
          :check-strictly="true"
          :default-expand-all="false"
          ref="permissionTreeRef"
        >
          <template #default="{ data }">
            <span v-if="data.menuType == 0">
              <i :class="data.icon"></i>
              [{{ data.useage === 0 ? '后台' : '前端' }}]{{ data.name }}
            </span>
            <span v-else>
              <i :class="data.icon"></i>
              <el-tag :key="data.id" type="warning" size="small" effect="dark">
                {{ data.name }} [{{ data.resourse }}]
              </el-tag>
            </span>
          </template>
        </el-tree>
      </div>
      <template #footer>
        <el-button size="small" @click="permissionTreeVisible = false"
          >取 消</el-button
        >
        <el-button size="small" type="primary" @click="submitPermission"
          >确 定</el-button
        >
      </template>
    </el-dialog>

    <el-dialog
      title="修改角色信息"
      :close-on-click-modal="false"
      v-model="roleUpdateVisible"
    >
      <el-form :model="roleForm" ref="roleFormRef" :rules="roleFormRules">
        <el-form-item label="角色ID" :label-width="formLabelWidth" prop="id">
          <el-input
            v-model="roleForm.id"
            autocomplete="off"
            :disabled="roleIdDisabled"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="角色名称"
          :label-width="formLabelWidth"
          prop="roleName"
        >
          <el-input v-model="roleForm.roleName" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button size="small" @click="roleUpdateVisible = false"
          >取 消</el-button
        >
        <el-button size="small" type="primary" @click="submitUpdateRole"
          >确 定</el-button
        >
      </template>
    </el-dialog>

    <el-dialog
      title="新增角色信息"
      :close-on-click-modal="false"
      v-model="roleAddVisible"
      @close="closeRoleAddDialog"
    >
      <el-form :model="addRoleForm" ref="addRoleFormRef" :rules="roleFormRules">
        <el-form-item
          label="角色名称"
          :label-width="formLabelWidth"
          prop="roleName"
        >
          <el-input
            size="small"
            v-model="addRoleForm.roleName"
            autocomplete="off"
          ></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button size="small" @click="roleAddVisible = false"
          >取 消</el-button
        >
        <el-button size="small" type="primary" @click="submitAddRole"
          >确 定</el-button
        >
      </template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, onMounted, nextTick } from 'vue'
import { roleApi } from '@/api/system/role'
import { menuApi } from '@/api/system/menu'

// 数据
const roleList = ref([])
const total = ref(0)
const permissionsTree = ref([])
const permissionTreeVisible = ref(false)
const roleUpdateVisible = ref(false)
const roleAddVisible = ref(false)
const systemRadio = ref('')
const updateRoleId = ref(0)
const formLabelWidth = '120px'
const roleIdDisabled = ref(true)
const permissionTreeRef = ref(null)
const roleFormRef = ref(null)
const addRoleFormRef = ref(null)

const roleForm = ref({
  id: 0,
  roleName: ''
})

const addRoleForm = ref({
  roleName: ''
})

const roleFormRules = ref({
  roleName: [
    { required: true, message: '请输入角色名称', trigger: 'blur' },
    { max: 10, min: 2, message: '请输入2-10个字符的角色名称' }
  ]
})

const pageSizes = [20, 100, 500]

// 查询参数
const queryInfo = ref({
  orderType: ' asc',
  order: ' id ',
  querytype: '',
  query: '',
  pageSize: 20,
  pageNum: 1
})

// 生命周期
onMounted(() => {
  getRoleList()
})

// 方法
const getRoleList = async () => {
  try {
    const res = await roleApi.listRole()
    roleList.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    ElMessage.error(error.message)
  }
}

const getMenuTree = async () => {
  try {
    const res = await menuApi.treeMenu({ system: systemRadio.value })
    permissionsTree.value = res.data
  } catch (error) {
    ElMessage.error(error.message)
  }
}

const dispatchPermission = (rowData) => {
  updateRoleId.value = rowData.id
  getMenuTree()
  permissionTreeVisible.value = true
  nextTick(() => {
    permissionTreeRef.value.setCheckedKeys(rowData.permissions.split(','))
  })
}

const removeRoleById = (data) => {
  ElMessageBox.confirm('此操作将永久删除该记录, 是否继续?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(async () => {
      try {
        const res = await roleApi.deleteRole(data.id)
        ElMessage.success(res.msg)
        getRoleList()
      } catch (error) {
        ElMessage.error(error.message)
      }
    })
    .catch(() => {
      ElMessage.info('取消删除')
    })
}

const submitPermission = async () => {
  const halfCheckedKeys = permissionTreeRef.value.getHalfCheckedKeys()
  const checkedKeys = permissionTreeRef.value.getCheckedKeys()
  const idStr = [...halfCheckedKeys, ...checkedKeys].join(',')

  try {
    const res = await roleApi.updateRoleById(updateRoleId.value, {
      permissionIds: idStr
    })
    ElMessage.success(res.msg)
    permissionTreeVisible.value = false
    getRoleList()
  } catch (error) {
    ElMessage.error(error.message)
  }
}

const updateRole = (data) => {
  roleForm.value = {
    id: data.id,
    roleName: data.roleName
  }
  roleUpdateVisible.value = true
}

const submitUpdateRole = async () => {
  try {
    const res = await roleApi.updateRole(roleForm.value)
    ElMessage.success(res.msg)
    roleUpdateVisible.value = false
    getRoleList()
  } catch (error) {
    ElMessage.error(error.message)
  }
}

const submitAddRole = async () => {
  try {
    const res = await roleApi.addRole(addRoleForm.value)
    ElMessage.success(res.msg)
    roleAddVisible.value = false
    getRoleList()
  } catch (error) {
    if (error.message) {
      ElMessage.error(error.message)
    }
  }
}

const closeRoleAddDialog = () => {
  addRoleForm.value.roleName = ''
}

const closePermissionTreeDialog = () => {
  // 清理逻辑
}

const handleSizeChange = (pageSize) => {
  queryInfo.value.pageSize = pageSize
  getRoleList()
}

const handleCurrentChange = (page) => {
  queryInfo.value.pageNum = page
  getRoleList()
}

const tableSort = (data) => {
  queryInfo.value.orderType = data.order === 'ascending' ? ' asc ' : ' desc '
  queryInfo.value.order = data.prop
  getRoleList()
}

const setAllExpand = (state) => {
  const nodes = permissionTreeRef.value.store.nodesMap
  for (const key in nodes) {
    nodes[key].expanded = state
  }
}
</script>

<style lang="less" scoped>
.el-tag {
  margin: 7px;
}
.bdtop {
  border-top: 1px solid #eee;
}
.bdbottom {
  border-bottom: 1px solid #eee;
}
.vcenter {
  display: flex;
  align-items: center;
}
.dialog-body {
  height: 450px;
  overflow: auto;
}
.box-card {
  height: calc(100vh - 240px);
  overflow: auto;
}
</style>
