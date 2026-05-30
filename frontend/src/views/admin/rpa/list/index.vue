<template>
  <el-card class="tool-card">
    <!-- 筛选条件区域 -->
    <el-row :gutter="15" class="filter-row">
      <el-col :span="8">
        <el-input
          placeholder="请输入工具名称"
          size="small"
          v-model="queryInfo.query"
          clearable
          @clear="getList"
        >
          <template #prepend>
            <el-select
              v-model="queryInfo.queryType"
              size="small"
              style="width: 100px"
              placeholder="查询类型"
              clearable
            >
              <el-option label="工具名称" value="name" />
              <el-option label="工具ID" value="id" />
            </el-select>
          </template>
          <template #append>
            <el-button size="small" icon="Search" @click="getList" />
          </template>
        </el-input>
      </el-col>
      <el-col :span="5">
        <el-select
          v-model="queryInfo.category"
          size="small"
          placeholder="请选择工具类别,默认全部"
          @change="getList"
          clearable
        >
          <el-option label="全部类别" value="" />
          <el-option
            v-for="item in dictStore.dictList.rpa_tool_list_category"
            :key="item.code"
            :label="item.codeval"
            :value="item.code"
          />
        </el-select>
      </el-col>
      <el-col :span="5">
        <el-select
          v-model="queryInfo.status"
          size="small"
          placeholder="请选择工具状态,默认全部"
          @change="getList"
          clearable
        >
          <el-option label="全部" value="" />
          <el-option label="有效" :value="1" />
          <el-option label="无效" :value="0" />
        </el-select>
      </el-col>
      <el-col :span="6" class="text-right">
        <el-button type="primary" size="small" @click="openToolDialog('add')">
          新建工具
        </el-button>
      </el-col>
    </el-row>

    <!-- 操作说明 -->
    <el-alert
      title="操作说明"
      type="info"
      :closable="false"
      description="请正确使用RPA工具配置：1.新建服务时务必完整填写相关信息。2.无需参数的可不配置参数模版，如有请务必准确配置。3.权限，如不配置则全员可查看"
      class="alert-message"
    />

    <!-- 数据表格 -->
    <el-table
      :data="list"
      size="small"
      height="calc(100vh - 460px)"
      stripe
      @sort-change="tableSort"
      class="tool-table"
      v-loading="loading"
    >
      <el-table-column
        prop="name"
        label="服务名称"
        sortable="custom"
        show-overflow-tooltip
        min-width="150"
      />
      <el-table-column
        prop="category"
        label="服务分类"
        sortable="custom"
        width="120px"
        show-overflow-tooltip
      >
        <template #default="{ row }">
          {{ dictStore.getDictLabel('rpa_tool_list_category', row.category) }}
        </template>
      </el-table-column>
      <el-table-column
        prop="runTimes"
        label="运行时长(秒)"
        sortable="custom"
        width="140px"
        align="right"
      />
      <el-table-column
        prop="templeteId"
        label="模版"
        sortable="custom"
        width="90px"
        show-overflow-tooltip
      >
        <template #default="{ row }">
          <el-link
            size="small"
            :type="row.templeteId ? 'success' : 'danger'"
            @click="openTemplateDialog(row)"
          >
            {{ row.templeteId ? '已配置' : '未配置' }}
          </el-link>
        </template>
      </el-table-column>
      <el-table-column
        prop="roles"
        label="权限"
        width="90px"
        sortable="custom"
        show-overflow-tooltip
      >
        <template #default="{ row }">
          <el-link
            size="small"
            :type="row.roles ? 'danger' : 'success'"
            @click="openRoleDialog(row)"
          >
            {{ row.roles ? '限制' : '不设限' }}
          </el-link>
        </template>
      </el-table-column>
      <el-table-column
        prop="sortId"
        label="排序"
        sortable="custom"
        width="80px"
        align="right"
      />
      <el-table-column
        prop="contacts"
        label="联系人"
        sortable="custom"
        width="120px"
        show-overflow-tooltip
      />
      <el-table-column
        prop="status"
        label="状态"
        sortable="custom"
        width="90px"
      >
        <template #default="{ row }">
          <el-tag
            :type="row.status ? 'success' : 'danger'"
            size="small"
            @click="changeStatus(row)"
            class="status-tag"
          >
            {{ row.status ? '有效' : '无效' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="创建时间"
        sortable="custom"
        width="160px"
      />
      <el-table-column
        prop="createUser"
        label="创建人"
        sortable="custom"
        width="120px"
      />
      <el-table-column label="操作" width="120px" fixed="right">
        <template #default="{ row }">
          <el-button
            type="warning"
            size="small"
            icon="Edit"
            @click="openToolDialog('update', row)"
            circle
          />
          <el-button
            type="danger"
            size="small"
            icon="Delete"
            @click="removeTool(row)"
            circle
          />
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="queryInfo.pageNum"
      :page-sizes="[20, 40, 100, 200]"
      :page-size="queryInfo.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      class="pagination"
    />
  </el-card>

  <!-- 工具对话框 -->
  <ToolDialog ref="toolDialogRef" @refresh="getList" />

  <!-- 模板对话框 -->
  <TemplateDialog ref="templateDialogRef" @refresh="getList" />

  <!-- 角色权限对话框 -->
  <RoleDialog ref="roleDialogRef" @refresh="getList" />
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useDictStore } from '@/stores'
import ToolDialog from './components/ToolDialog.vue'
import TemplateDialog from './components/TemplateDialog.vue'
import RoleDialog from './components/RoleDialog.vue'
import {
  getAllRpaTool,
  updateRpaToolStatus,
  deleteRpaTool
} from '@/api/rpa/rpa.js'

const dictStore = useDictStore()

// 响应式数据
const loading = ref(false)
const toolDialogRef = ref(null)
const templateDialogRef = ref(null)
const roleDialogRef = ref(null)
const list = ref([])
const total = ref(0)

// 查询参数
const queryInfo = ref({
  orderType: 'desc',
  order: 'sortId',
  queryType: 'name',
  status: '',
  category: '',
  query: '',
  pageSize: 20,
  pageNum: 1
})

// 获取工具列表
const getList = async () => {
  try {
    loading.value = true
    const res = await getAllRpaTool(queryInfo.value)
    list.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    ElMessage.error(error.message || '获取工具列表失败')
  } finally {
    loading.value = false
  }
}

// 分页大小变化
const handleSizeChange = (pageSize) => {
  queryInfo.value.pageSize = pageSize
  getList()
}

// 当前页码变化
const handleCurrentChange = (page) => {
  queryInfo.value.pageNum = page
  getList()
}

// 表格排序
const tableSort = ({ prop, order }) => {
  queryInfo.value.order = prop
  queryInfo.value.orderType = order === 'ascending' ? 'asc' : 'desc'
  getList()
}

// 打开工具对话框
const openToolDialog = (type, row = {}) => {
  toolDialogRef.value.openDialog(type, row)
}

// 打开模板对话框
const openTemplateDialog = (row) => {
  templateDialogRef.value.openDialog(row)
}

// 打开角色权限对话框
const openRoleDialog = (row) => {
  roleDialogRef.value.openDialog(row)
}

// 更改工具状态
const changeStatus = async (row) => {
  try {
    await ElMessageBox.confirm(
      `此操作将${row.status ? '禁用' : '启用'}该工具, 是否继续?`,
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    await updateRpaToolStatus({ id: row.id, status: row.status === 1 ? 0 : 1 })
    ElMessage.success('状态更新成功')
    getList()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error(error.message || '状态更新失败')
    }
  }
}

// 删除工具
const removeTool = async (row) => {
  try {
    await ElMessageBox.alert('此操作将永久删除该工具, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })

    await deleteRpaTool(row.id)
    ElMessage.success('删除成功')
    getList()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error(error.message || '删除失败')
    }
  }
}

// 初始化
onMounted(() => {
  getList()
})
</script>

<style lang="less" scoped>
.rpa-tool-container {
  height: 100%;
  padding: 16px;
}

.tool-card {
  margin-top: 16px;
  height: calc(100% - 50px);
}

.filter-row {
  margin-bottom: 16px;

  .text-right {
    display: flex;
    justify-content: flex-end;
  }
}

.alert-message {
  margin-bottom: 16px;
}

.tool-table {
  margin-top: 16px;

  .status-tag {
    cursor: pointer;
  }
}

.pagination {
  margin-top: 16px;
  justify-content: flex-end;
}
</style>
