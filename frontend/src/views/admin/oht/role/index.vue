<template>
  <t-card class="management-card">
    <div class="table-toolbar">
      <div class="toolbar-right">
        <t-button variant="outline" theme="primary" size="small" @click="addRole">添加角色</t-button>
      </div>
    </div>
    <CustomTable rowKey="id" :data="ohtRoleTableList" size="small" @sort-change="tableSort" height="calc(100vh - 325px)" stripe
      :loading="loading">
      <TableColumn colKey="roleCode" label="CODE" sortable="custom" width="180"></TableColumn>
      <TableColumn colKey="roleName" label="角色名称" sortable="custom"></TableColumn>
      <TableColumn colKey="roleType" label="角色类型" sortable="custom">
        <template #default="{ row }">
          <span v-for="item in (dictStore.dictList?.oht_role_type || [])" :key="item.code" v-show="row.roleType == item.code">{{
            item.codeval }}</span>
        </template>
      </TableColumn>
      <TableColumn colKey="roleStat" label="角色状态" sortable="custom">
        <template #default="{ row }">
          <t-tag v-for="item in (dictStore.dictList?.oht_role_status || [])" :key="item.code" :label="item.codeval" size="small"
            :theme="item.code == 0 ? 'danger' : 'success'" v-show="row.roleStat == item.code" variant="light">
            {{ item.codeval }}
          </t-tag>
        </template>
      </TableColumn>
      <TableColumn colKey="roleRelat" label="转接关系"></TableColumn>
      <TableColumn label="操作" fixed="right" width="180px">
        <template #default="{ row }">
          <t-button variant="outline" theme="default" size="small" @click="ohtRoleEdit(row)">编辑</t-button>
          <t-button variant="outline" theme="danger" size="small" @click="removeRoleByRoleCode(row)">删除</t-button>
          <t-button variant="outline" size="small" @click="dispatchRule">分配规则</t-button>
        </template>
      </TableColumn>
    </CustomTable>
    <t-pagination @page-size-change="handleSizeChange" @current-change="handleCurrentChange"
      :current="queryInfo.pageNum" :page-size-options="pageSizes" :page-size="queryInfo.pageSize"
 :total="total"></t-pagination>
  </t-card>

  <!-- 角色对话框 -->
  <RoleDialog ref="roleDialogRef" v-model:visible="roleAddVisible" :dialog-type="dialogType"
    :form-data="currentFormData" @success="getOhtRoleList" />

  <!-- 转接关系对话框 -->
  <RoleDispatchDialog v-model:visible="dispatchVisible" :oht-role-table-list="ohtRoleTableList" />
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { MessagePlugin, DialogPlugin } from 'tdesign-vue-next'
import { useDictStore } from '@/stores'
import RoleDialog from './components/RoleDialog.vue'
import RoleDispatchDialog from './components/RoleDispatchDialog.vue'
import { roleApi } from '@/api/oht/role.js'

const dictStore = useDictStore()

// 响应式数据
const loading = ref(false)
const roleDialogRef = ref(null)
const ohtRoleTableList = ref([])
const total = ref(0)
const roleAddVisible = ref(false)
const dispatchVisible = ref(false)
const dialogType = ref('add')
const currentFormData = ref({})

// 查询参数
const queryInfo = reactive({
  orderType: 'asc',
  order: 'roleCode',
  querytype: '',
  query: '',
  pageSize: 20,
  pageNum: 1
})

const pageSizes = [20, 100, 500]

// 获取角色列表
const getOhtRoleList = async () => {
  try {
    loading.value = true
    const res = await roleApi.getRoleList(queryInfo)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    ohtRoleTableList.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    console.error('获取角色列表失败:', error)
    MessagePlugin.error('获取角色列表失败')
  } finally {
    loading.value = false
  }
}

// 分页大小变化
const handleSizeChange = (pageSize) => {
  queryInfo.pageSize = pageSize
  getOhtRoleList()
}

// 当前页码变化
const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  getOhtRoleList()
}

// 表格排序
const tableSort = ({ sortBy, descending }) => {
  queryInfo.order = sortBy
  queryInfo.orderType = !descending ? 'asc' : 'desc'
  getOhtRoleList()
}

// 删除角色
const removeRoleByRoleCode = async (row) => {
  try {
    await DialogPlugin.confirm(
      '此操作将永久删除该记录, 是否继续?',
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    ).then(async () => {
      const res = await roleApi.deleteRole(row.roleCode)
      if (res.code !== 200) {
        MessagePlugin.error(res.msg)
        return
      }
      MessagePlugin.success(res.msg)
      getOhtRoleList()
    })
  } catch (error) {
    if (error !== 'cancel') {
      MessagePlugin.error('取消删除')
    }
  }
}

// 添加角色
const addRole = () => {
  dialogType.value = 'add'
  currentFormData.value = { roleCode: '', roleName: '', roleType: 0, roleStat: 1 }
  roleAddVisible.value = true
}

// 编辑角色
const ohtRoleEdit = (row) => {
  dialogType.value = 'update'
  currentFormData.value = { ...row }
  roleAddVisible.value = true
}

// 分配规则
const dispatchRule = () => {
  dispatchVisible.value = true
}

// 初始化
onMounted(() => {
  getOhtRoleList()
})
</script>

<style lang="less" scoped>
.management-card {
  height: calc(100vh - 190px);
  overflow: auto;
}

.text-right {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 16px;
}
</style>
