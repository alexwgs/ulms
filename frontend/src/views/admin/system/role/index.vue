<template>
  <t-card class="box-card">
    <t-row :gutter="20">
      <t-col :span="12">
        <t-button
          theme="primary"
          size="small"
          @click="roleAddVisible = !roleAddVisible"
          >添加角色</t-button
        >
      </t-col>
    </t-row>
    <t-alert
      style="margin: 10px 0"
      title="操作说明"
      size=""
      theme="info"
      message="请正确使用角色配置：1.建议角色名称简介且清晰。2.请注意各个角色的菜单操作权限配置！"
      :closable="false"
    >
    </t-alert>
    <CustomTable rowKey="id"
      :data="roleList"
      size="small"
      @sort-change="tableSort"
      height="calc(100vh - 440px)">
      <TableColumn type="expand">
        <template #default="{ row: scope }">
          <t-row
            :class="['bdbottom', i1 === 0 ? 'bdtop' : '', 'vcenter']"
            v-for="(item1, i1) in scope.children"
            :key="item1.id"
          >
            <t-col :span="3">
              <t-tag :key="item1.id">{{ item1.name }}</t-tag>
            </t-col>
            <t-col :span="10">
              <t-row
                :class="[i2 === 0 ? '' : 'bdtop', 'vcenter']"
                v-for="(item2, i2) in item1.children"
                :key="item2.id"
              >
                <t-col :span="3">
                  <t-tag :key="item2.id" theme="success">{{
                    item2.name
                  }}</t-tag>
                </t-col>
                <t-col :span="9">
                  <t-tag
                    v-for="item3 in item2.children"
                    :key="item3.id"
                    theme="warning"
                    closable
                    >{{ item3.name }}</t-tag
                  >
                </t-col>
              </t-row>
            </t-col>
          </t-row>
        </template>
      </TableColumn>
      <TableColumn
        label="角色ID"
        prop="id"
        sortable="custom"></TableColumn>
      <TableColumn
        label="角色名称"
        prop="roleName"
        sortable="custom"></TableColumn>
      <TableColumn label="操作" fixed="right" width="300px">
        <template #default="{ row: scope }">
          <t-button
            theme="warning"
            size="small" @click="updateRole(scope)"
            circle
          ><template #icon><DynamicIcon name="edit" /></template></t-button>
          <t-button
            theme="danger"
            size="small" @click="removeRoleById(scope)"
            circle
          ><template #icon><DynamicIcon name="delete" /></template></t-button>
          <t-button size="small" @click="dispatchPermission(scope)"
            >分配权限</t-button
          >
        </template>
      </TableColumn>
    </CustomTable>
    <t-pagination
      @page-size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current="queryInfo.pageNum"
      :page-size-options="pageSizes"
      :page-size="queryInfo.pageSize"

      :total="total"
    >
    </t-pagination>

    <t-dialog
      header="分配权限"
      height="400px"
      :close-on-overlay-click="false"
      v-model:visible="permissionTreeVisible"
      @close="closePermissionTreeDialog"
    >
      <div class="dialog-body">
        <div class="buttons">
          <t-radio-group v-model="systemRadio" @change="getMenuTree">
            <t-radio value=""
            >全部</t-radio
          >
          <t-radio value="a6squre"
            >A6广场</t-radio
          >
          <t-radio value="bpms"
            >绩效平台</t-radio
          >
          </t-radio-group>
          <t-button size="small" @click="setAllExpand(true)"
            >展开所有节点</t-button
          >
          <t-button size="small" @click="setAllExpand(false)"
            >收起所有节点</t-button
          >
        </div>
        <t-tree
          :data="permissionsTree"
          checkable
          :keys="{ value: 'id', label: 'name', children: 'children' }"
          :check-strictly="true"
          v-model="checkedKeys"
          v-model:expanded="expandedKeys"
          @change="onPermissionTreeChange"
          ref="permissionTreeRef"
        >
          <template #default="{ node }">
            <span v-if="node.data.menuType == 0">
              <i :class="node.data.icon"></i>
              [{{ node.data.useage === 0 ? '后台' : '前端' }}]{{ node.data.name }}
            </span>
            <span v-else>
              <i :class="node.data.icon"></i>
              <t-tag :key="node.data.id" theme="warning" size="small" effect="dark">
                {{ node.data.name }} [{{ node.data.resourse }}]
              </t-tag>
            </span>
          </template>
        </t-tree>
      </div>
      <template #footer>
        <t-button size="small" @click="permissionTreeVisible = false"
          >取 消</t-button
        >
        <t-button size="small" theme="primary" @click="submitPermission"
          >确 定</t-button
        >
      </template>
    </t-dialog>

    <t-dialog
      header="修改角色信息"
      :close-on-overlay-click="false"
      v-model:visible="roleUpdateVisible"
    >
      <t-form :data="roleForm" ref="roleFormRef" :rules="roleFormRules">
        <t-form-item label="角色ID" :label-width="formLabelWidth" name="id">
          <t-input
            v-model="roleForm.id"
            autocomplete="off"
            :disabled="roleIdDisabled"
          ></t-input>
        </t-form-item>
        <t-form-item
          label="角色名称"
          :label-width="formLabelWidth"
          prop="roleName"
        >
          <t-input v-model="roleForm.roleName" autocomplete="off"></t-input>
        </t-form-item>
      </t-form>
      <template #footer>
        <t-button size="small" @click="roleUpdateVisible = false"
          >取 消</t-button
        >
        <t-button size="small" theme="primary" @click="submitUpdateRole"
          >确 定</t-button
        >
      </template>
    </t-dialog>

    <t-dialog
      header="新增角色信息"
      :close-on-overlay-click="false"
      v-model:visible="roleAddVisible"
      @close="closeRoleAddDialog"
    >
      <t-form :data="addRoleForm" ref="addRoleFormRef" :rules="roleFormRules">
        <t-form-item
          label="角色名称"
          :label-width="formLabelWidth"
          prop="roleName"
        >
          <t-input
            size="small"
            v-model="addRoleForm.roleName"
            autocomplete="off"
          ></t-input>
        </t-form-item>
      </t-form>
      <template #footer>
        <t-button size="small" @click="roleAddVisible = false"
          >取 消</t-button
        >
        <t-button size="small" theme="primary" @click="submitAddRole"
          >确 定</t-button
        >
      </template>
    </t-dialog>
  </t-card>
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
const checkedKeys = ref([])
const halfCheckedKeys = ref([])
const expandedKeys = ref([])
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
    MessagePlugin.error(error.message)
  }
}

const getMenuTree = async () => {
  try {
    const res = await menuApi.treeMenu({ system: systemRadio.value })
    permissionsTree.value = res.data
  } catch (error) {
    MessagePlugin.error(error.message)
  }
}

const dispatchPermission = (rowData) => {
  updateRoleId.value = rowData.id
  getMenuTree()
  permissionTreeVisible.value = true
  nextTick(() => {
    checkedKeys.value = rowData.permissions ? rowData.permissions.split(',') : []
  })
}

const removeRoleById = (data) => {
  DialogPlugin.confirm('此操作将永久删除该记录, 是否继续?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(async () => {
      try {
        const res = await roleApi.deleteRole(data.id)
        MessagePlugin.success(res.msg)
        getRoleList()
      } catch (error) {
        MessagePlugin.error(error.message)
      }
    })
    .catch(() => {
      MessagePlugin.info('取消删除')
    })
}

const submitPermission = async () => {
  const idStr = [...halfCheckedKeys.value, ...checkedKeys.value].join(',')

  try {
    const res = await roleApi.updateRoleById(updateRoleId.value, {
      permissionIds: idStr
    })
    MessagePlugin.success(res.msg)
    permissionTreeVisible.value = false
    getRoleList()
  } catch (error) {
    MessagePlugin.error(error.message)
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
    MessagePlugin.success(res.msg)
    roleUpdateVisible.value = false
    getRoleList()
  } catch (error) {
    MessagePlugin.error(error.message)
  }
}

const submitAddRole = async () => {
  try {
    const res = await roleApi.addRole(addRoleForm.value)
    MessagePlugin.success(res.msg)
    roleAddVisible.value = false
    getRoleList()
  } catch (error) {
    if (error.message) {
      MessagePlugin.error(error.message)
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
  queryInfo.value.orderType = data.descending ? ' desc ' : ' asc '
  queryInfo.value.order = data.sortBy
  getRoleList()
}

const setAllExpand = (state) => {
  if (state) {
    const collectIds = (nodes) => {
      let ids = []
      for (const node of nodes) {
        ids.push(String(node.id))
        if (node.children && node.children.length > 0) {
          ids = ids.concat(collectIds(node.children))
        }
      }
      return ids
    }
    expandedKeys.value = collectIds(permissionsTree.value)
  } else {
    expandedKeys.value = []
  }
}

const onPermissionTreeChange = (value, context) => {
  halfCheckedKeys.value = context.halfCheckedKeys || []
}
</script>

<style lang="less" scoped>
.t-tag {
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
