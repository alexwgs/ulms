<template>
  <el-card class="box-card">
    <el-row>
      <el-col :span="24" class="text-right">
        <el-button type="primary" size="small" @click="addRole">添加角色</el-button>
      </el-col>
    </el-row>
    <el-table :data="ohtRoleTableList" size="small" @sort-change="tableSort" height="calc(100vh - 325px)" stripe
      v-loading="loading">
      <el-table-column prop="roleCode" label="CODE" sortable="custom" width="180"></el-table-column>
      <el-table-column prop="roleName" label="角色名称" sortable="custom"></el-table-column>
      <el-table-column prop="roleType" label="角色类型" sortable="custom">
        <template #default="{ row }">
          <span v-for="item in dictStore.dictList.oht_role_type" :key="item.code" v-show="row.roleType == item.code">{{
            item.codeval }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="roleStat" label="角色状态" sortable="custom">
        <template #default="{ row }">
          <el-tag v-for="item in dictStore.dictList.oht_role_status" :key="item.code" :label="item.codeval" size="small"
            :type="item.code == 0 ? 'danger' : 'success'" v-show="row.roleStat == item.code" effect="dark">
            {{ item.codeval }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="roleRelat" label="转接关系"></el-table-column>
      <el-table-column label="操作" fixed="right" width="180px">
        <template #default="{ row }">
          <el-button type="warning" size="small" icon="Edit" @click="ohtRoleEdit(row)" circle></el-button>
          <el-button type="danger" size="small" icon="Delete" @click="removeRoleByRoleCode(row)" circle></el-button>
          <el-button size="small" @click="dispatchRule">分配规则</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
      :current-page="queryInfo.pageNum" :page-sizes="pageSizes" :page-size="queryInfo.pageSize"
      layout="total, sizes, prev, pager, next, jumper" :total="total"></el-pagination>
  </el-card>

  <!-- 角色对话框 -->
  <RoleDialog ref="roleDialogRef" v-model:visible="roleAddVisible" :dialog-type="dialogType"
    :form-data="currentFormData" @success="getOhtRoleList" />

  <!-- 转接关系对话框 -->
  <RoleDispatchDialog v-model:visible="dispatchVisible" :oht-role-table-list="ohtRoleTableList" />
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
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
      ElMessage.error(res.msg)
      return
    }
    ohtRoleTableList.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    console.error('获取角色列表失败:', error)
    ElMessage.error('获取角色列表失败')
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
const tableSort = ({ prop, order }) => {
  queryInfo.order = prop
  queryInfo.orderType = order === 'ascending' ? 'asc' : 'desc'
  getOhtRoleList()
}

// 删除角色
const removeRoleByRoleCode = async (row) => {
  try {
    await ElMessageBox.confirm(
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
        ElMessage.error(res.msg)
        return
      }
      ElMessage.success(res.msg)
      getOhtRoleList()
    })
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('取消删除')
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
.box-card {
  height: calc(100vh - 190px);
  overflow: auto;
}

.text-right {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 16px;
}
</style>
