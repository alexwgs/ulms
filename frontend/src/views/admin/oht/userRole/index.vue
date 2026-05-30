<template>
  <el-card class="box-card">
    <el-row :gutter="15">
      <el-col :span="6">
        <el-input size="small" placeholder="通过工号查找" v-model="queryInfo.ploNum" @change="getUserRoleList" clearable>
        </el-input>
      </el-col>
      <el-col :span="6">
        <el-select size="small" v-model="queryInfo.roleType" @change="getUserRoleList" placeholder="角色类型">
          <el-option v-for="item in dictStore.dictList.oht_role_type" :key="item.code" :label="item.codeval"
            :value="item.code"></el-option>
        </el-select>
      </el-col>
      <el-col :span="6">
        <el-select size="small" v-model="queryInfo.roleStat" @change="getUserRoleList" placeholder="角色状态">
          <el-option v-for="item in dictStore.dictList.oht_role_status" :key="item.code" :label="item.codeval"
            :value="item.code"></el-option>
        </el-select>
      </el-col>
      <el-col :span="6" class="text-right">
        <el-button type="primary" size="small" @click="addRole">添加名单</el-button>
      </el-col>
    </el-row>
    <el-table :data="ohtRoleTableList" size="small" @sort-change="tableSort" height="calc(100vh - 325px)" stripe
      v-loading="loading">
      <el-table-column prop="ploNum" label="工号" sortable="custom"></el-table-column>
      <el-table-column prop="roleType" label="角色类型" sortable="custom">
        <template #default="{ row }">
          <span v-for="item in dictStore.dictList.oht_role_type" :key="item.code"
            :style="row.roleType == item.code ? '' : 'display:none'">{{ item.codeval }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="roleStat" label="角色状态" sortable="custom">
        <template #default="{ row }">
          <el-tag v-for="item in dictStore.dictList.oht_role_status" :key="item.code" :label="item.codeval" size="small"
            :type="item.code == 0 ? 'danger' : 'success'" :style="row.roleStat == item.code ? '' : 'display:none'"
            effect="dark">
            {{ item.codeval }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" width="120px">
        <template #default="{ row }">
          <el-button type="warning" size="small" icon="Edit" @click="updateRole(row)" circle></el-button>
          <el-button type="danger" size="small" icon="Delete" @click="removeRole(row.ploNum)" circle></el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
      :current-page="queryInfo.pageNum" :page-sizes="pageSizes" :page-size="queryInfo.pageSize"
      layout="total, sizes, prev, pager, next, jumper" :total="total"></el-pagination>
  </el-card>

  <!-- 角色名单对话框 -->
  <UserRoleDialog ref="userRoleDialogRef" v-model:visible="dialogVisible" :dialog-type="dialogType"
    :form-data="currentFormData" @success="getUserRoleList" />
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useDictStore } from '@/stores'
import UserRoleDialog from './components/UserRoleDialog.vue'
import {
  userRoleApi
} from '@/api/oht/userRole'

const dictStore = useDictStore()

// 响应式数据
const loading = ref(false)
const userRoleDialogRef = ref(null)
const ohtRoleTableList = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const dialogType = ref('add')
const currentFormData = ref({})

// 查询参数
const queryInfo = reactive({
  orderType: 'asc',
  order: 'ploNum',
  roleType: '',
  roleStat: '',
  userId: '',
  ploNum: '',
  pageSize: 20,
  pageNum: 1
})

const pageSizes = [20, 100, 500]

// 获取用户角色列表
const getUserRoleList = async () => {
  try {
    loading.value = true
    const res = await userRoleApi.getUserRoleList(queryInfo)
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
  getUserRoleList()
}

// 当前页码变化
const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  getUserRoleList()
}

// 表格排序
const tableSort = ({ prop, order }) => {
  queryInfo.order = prop
  queryInfo.orderType = order === 'ascending' ? 'asc' : 'desc'
  getUserRoleList()
}

// 删除角色
const removeRole = async (userId) => {
  try {
    await ElMessageBox.confirm('此操作将永久删除该记录, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(async () => {
      const res = await userRoleApi.deleteUserRole(userId)
      if (res.code !== 200) {
        ElMessage.error(res.msg)
        return
      }
      ElMessage.success(res.msg)
      getUserRoleList()
    })
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

// 添加角色
const addRole = () => {
  dialogType.value = 'add'
  currentFormData.value = {
    ploNum: null,
    roleType: null,
    roleCode: 0,
    roleStat: 1
  }
  dialogVisible.value = true
}

// 更新角色
const updateRole = (row) => {
  dialogType.value = 'update'
  currentFormData.value = { ...row }
  dialogVisible.value = true
}

// 初始化
onMounted(() => {
  getUserRoleList()
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
}
</style>
