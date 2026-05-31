<template>
  <t-card class="box-card">
    <t-row :gutter="15">
      <t-col :span="3">
        <t-input size="small" placeholder="通过工号查找" v-model="queryInfo.ploNum" @change="getUserRoleList" clearable>
        </t-input>
      </t-col>
      <t-col :span="3">
        <t-select size="small" v-model="queryInfo.roleType" @change="getUserRoleList" placeholder="角色类型">
          <t-option v-for="item in dictStore.dictList.oht_role_type" :key="item.code" :label="item.codeval"
            :value="item.code"></t-option>
        </t-select>
      </t-col>
      <t-col :span="3">
        <t-select size="small" v-model="queryInfo.roleStat" @change="getUserRoleList" placeholder="角色状态">
          <t-option v-for="item in dictStore.dictList.oht_role_status" :key="item.code" :label="item.codeval"
            :value="item.code"></t-option>
        </t-select>
      </t-col>
      <t-col :span="3" class="text-right">
        <t-button theme="primary" size="small" @click="addRole">添加名单</t-button>
      </t-col>
    </t-row>
    <CustomTable rowKey="id" :data="ohtRoleTableList" size="small" @sort-change="tableSort" height="calc(100vh - 325px)" stripe
      :loading="loading">
      <TableColumn colKey="ploNum" label="工号" sortable="custom"></TableColumn>
      <TableColumn colKey="roleType" label="角色类型" sortable="custom">
        <template #default="{ row }">
          <span v-for="item in dictStore.dictList.oht_role_type" :key="item.code"
            :style="row.roleType == item.code ? '' : 'display:none'">{{ item.codeval }}</span>
        </template>
      </TableColumn>
      <TableColumn colKey="roleStat" label="角色状态" sortable="custom">
        <template #default="{ row }">
          <t-tag v-for="item in dictStore.dictList.oht_role_status" :key="item.code" :label="item.codeval" size="small"
            :theme="item.code == 0 ? 'danger' : 'success'" :style="row.roleStat == item.code ? '' : 'display:none'"
            effect="dark">
            {{ item.codeval }}
          </t-tag>
        </template>
      </TableColumn>
      <TableColumn label="操作" fixed="right" width="120px">
        <template #default="{ row }">
          <t-button theme="warning" size="small" @click="updateRole(row)" shape="circle"><template #icon><DynamicIcon name="edit" /></template></t-button>
          <t-button theme="danger" size="small" @click="removeRole(row.ploNum)" shape="circle"><template #icon><DynamicIcon name="delete" /></template></t-button>
        </template>
      </TableColumn>
    </CustomTable>
    <t-pagination @page-size-change="handleSizeChange" @current-change="handleCurrentChange"
      :current="queryInfo.pageNum" :page-size-options="pageSizes" :page-size="queryInfo.pageSize"
 :total="total"></t-pagination>
  </t-card>

  <!-- 角色名单对话框 -->
  <UserRoleDialog ref="userRoleDialogRef" v-model:visible="dialogVisible" :dialog-type="dialogType"
    :form-data="currentFormData" @success="getUserRoleList" />
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { MessagePlugin, DialogPlugin } from 'tdesign-vue-next'
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
  getUserRoleList()
}

// 当前页码变化
const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  getUserRoleList()
}

// 表格排序
const tableSort = ({ sortBy, descending }) => {
  queryInfo.order = sortBy
  queryInfo.orderType = !descending ? 'asc' : 'desc'
  getUserRoleList()
}

// 删除角色
const removeRole = async (userId) => {
  try {
    await DialogPlugin.confirm('此操作将永久删除该记录, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(async () => {
      const res = await userRoleApi.deleteUserRole(userId)
      if (res.code !== 200) {
        MessagePlugin.error(res.msg)
        return
      }
      MessagePlugin.success(res.msg)
      getUserRoleList()
    })
  } catch (error) {
    if (error !== 'cancel') {
      MessagePlugin.error('删除失败')
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
