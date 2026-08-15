<template>
  <t-card class="management-card">
    <div class="table-toolbar">
      <div class="toolbar-left">
        <t-button variant="outline"
          theme="primary"
          size="small"
          @click="roleAddVisible = !roleAddVisible"
          >添加角色</t-button
        >
      </div>
    </div>
    <PageTips
      style="margin: 10px 0"
      title="操作说明"
      size=""
      theme="primary"
      message="请正确使用角色配置：1.建议角色名称简介且清晰。2.请注意各个角色的菜单操作权限配置！"
      :closable="false"
    >
    </PageTips>
    <CustomTable rowKey="id"
      :data="roleList"
      size="small"
      @sort-change="tableSort"
      height="calc(100vh - 440px)">
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
          <t-space>
          <t-button variant="outline"
            theme="primary"
            size="small" @click="updateRole(scope)"
           
          >编辑</t-button>
          <t-button variant="outline"
            theme="danger"
            size="small" @click="removeRoleById(scope)"
           
          >删除</t-button>
          <t-button variant="outline" size="small" @click="dispatchPermission(scope)"
            >分配权限</t-button
          >
          </t-space>
        </template>
      </TableColumn>
    </CustomTable>
    <t-pagination
      @page-size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current="currentPage"
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
          <t-button variant="outline" size="small" @click="setAllExpand(true)"
            >展开所有节点</t-button
          >
          <t-button variant="outline" size="small" @click="setAllExpand(false)"
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
          <template #label="{ node }">
            <span v-if="node.data.menuType == 0">
              <i :class="node.data.icon"></i>
              [{{ node.data.useage === 0 ? '后台' : '前端' }}]{{ node.data.name }}
            </span>
            <span v-else>
              <i :class="node.data.icon"></i>
              <t-tag :key="node.data.id" theme="warning" size="small" variant="light">
                {{ node.data.name }} [{{ node.data.resourse }}]
              </t-tag>
            </span>
          </template>
        </t-tree>
      </div>
      <template #footer>
        <t-button variant="outline" size="small" @click="permissionTreeVisible = false"
          >取 消</t-button
        >
        <t-button variant="outline" size="small" theme="primary" @click="submitPermission"
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
          name="roleName"
        >
          <t-input v-model="roleForm.roleName" autocomplete="off"></t-input>
        </t-form-item>
      </t-form>
      <template #footer>
        <t-button variant="outline" size="small" @click="roleUpdateVisible = false"
          >取 消</t-button
        >
        <t-button variant="outline" size="small" theme="primary" @click="submitUpdateRole"
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
          name="roleName"
        >
          <t-input
            size="small"
            v-model="addRoleForm.roleName"
            autocomplete="off"
          ></t-input>
        </t-form-item>
      </t-form>
      <template #footer>
        <t-button variant="outline" size="small" @click="roleAddVisible = false"
          >取 消</t-button
        >
        <t-button variant="outline" size="small" theme="primary" @click="submitAddRole"
          >确 定</t-button
        >
      </template>
    </t-dialog>
  </t-card>
</template>

<script setup>
import { MessagePlugin } from 'tdesign-vue-next'
import { ref, onMounted } from 'vue'
import { roleApi } from '@/api/system/role'
import { menuApi } from '@/api/system/menu'
import { useCrudPage } from '@/hooks/useCrudPage'

// 数据
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

// 列表 + 分页 + 删除（useCrudPage 样板）
const {
  list: roleList,
  total,
  query: queryInfo,
  currentPage,
  pageSizes,
  handleCurrentChange,
  handleSizeChange,
  load: getRoleList,
  remove: removeRoleById
} = useCrudPage({
  fetchList: (q) => roleApi.listRole(q),
  defaultQuery: {
    orderType: ' asc',
    order: ' id ',
    querytype: '',
    query: '',
    pageSize: 20,
    pageNum: 1
  },
  deleteApi: (row) => roleApi.deleteRole(row.id),
  pageSizes: [20, 100, 500]
})

// 生命周期
onMounted(() => {
  getRoleList()
})

const getMenuTree = async () => {
  try {
    const res = await menuApi.treeMenu({ system: systemRadio.value })
    permissionsTree.value = res.data
  } catch (error) {
    MessagePlugin.error(error.message)
  }
}

const dispatchPermission = async (rowData) => {
  updateRoleId.value = rowData.id
  permissionTreeVisible.value = true
  // 先等菜单树加载完成，再回填已勾选权限，避免树数据未就绪导致默认值丢失
  await getMenuTree()
  checkedKeys.value = rowData.permissions
    ? rowData.permissions.split(',').filter(Boolean).map(Number)
    : []
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
  // 关闭时清理勾选状态，避免下次打开残留
  checkedKeys.value = []
  halfCheckedKeys.value = []
  expandedKeys.value = []
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
  const node = context.node
  // 勾选父节点时自动级联勾选所有子孙节点（仅勾选方向级联）；
  // 取消勾选保持父子独立，因此取消所有子节点后父节点仍可单独勾选。
  const isChecked = node && Array.isArray(value) && value.includes(node.value)
  if (isChecked && node && Array.isArray(node.children) && node.children.length > 0) {
    const collectIds = (nodes) => {
      let ids = []
      for (const n of nodes) {
        ids.push(n.value)
        if (Array.isArray(n.children) && n.children.length > 0) {
          ids = ids.concat(collectIds(n.children))
        }
      }
      return ids
    }
    checkedKeys.value = Array.from(new Set([...value, ...collectIds(node.children)]))
  } else {
    checkedKeys.value = value
  }
  halfCheckedKeys.value = context.halfCheckedKeys || []
}
</script>

<style lang="less" scoped>
.t-tag {
  margin: 7px;
}
.bdtop {
  border-top: 1px solid var(--td-component-stroke);
}
.bdbottom {
  border-bottom: 1px solid var(--td-component-stroke);
}
.vcenter {
  display: flex;
  align-items: center;
}
.dialog-body {
  height: 450px;
  overflow: auto;
}
.management-card {
  height: calc(100vh - 240px);
  overflow: auto;
}
</style>
