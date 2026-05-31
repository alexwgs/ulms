<template>
  <t-card class="tool-card">
    <!-- 筛选条件区域 -->
    <t-row :gutter="15" class="filter-row">
      <t-col :span="4">
        <t-input
          placeholder="请输入工具名称"
          size="small"
          v-model="queryInfo.query"
          clearable
          @clear="getList"
        >
          <template #prepend>
            <t-select
              v-model="queryInfo.queryType"
              size="small"
              style="width: 100px"
              placeholder="查询类型"
              clearable
            >
              <t-option label="工具名称" value="name" />
              <t-option label="工具ID" value="id" />
            </t-select>
          </template>
          <template #append>
            <t-button size="small" @click="getList"><template #icon><DynamicIcon name="search" /></template></t-button>
          </template>
        </t-input>
      </t-col>
      <t-col :span="3">
        <t-select
          v-model="queryInfo.category"
          size="small"
          placeholder="请选择工具类别,默认全部"
          @change="getList"
          clearable
        >
          <t-option label="全部类别" value="" />
          <t-option
            v-for="item in dictStore.dictList.rpa_tool_list_category"
            :key="item.code"
            :label="item.codeval"
            :value="item.code"
          />
        </t-select>
      </t-col>
      <t-col :span="3">
        <t-select
          v-model="queryInfo.status"
          size="small"
          placeholder="请选择工具状态,默认全部"
          @change="getList"
          clearable
        >
          <t-option label="全部" value="" />
          <t-option label="有效" :value="1" />
          <t-option label="无效" :value="0" />
        </t-select>
      </t-col>
      <t-col :span="3" class="text-right">
        <t-button theme="primary" size="small" @click="openToolDialog('add')">
          新建工具
        </t-button>
      </t-col>
    </t-row>

    <!-- 操作说明 -->
    <t-alert
      title="操作说明"
      theme="info"
      :closable="false"
      message="请正确使用RPA工具配置：1.新建服务时务必完整填写相关信息。2.无需参数的可不配置参数模版，如有请务必准确配置。3.权限，如不配置则全员可查看"
      class="alert-message"
    />

    <!-- 数据表格 -->
    <CustomTable rowKey="id"
      :data="list"
      size="small"
      height="calc(100vh - 460px)"
      stripe
      @sort-change="tableSort"
      class="tool-table"
      :loading="loading">
      <TableColumn
        prop="name"
        label="服务名称"
        sortable="custom"
        ellipsis
        min-width="150"
      />
      <TableColumn
        prop="category"
        label="服务分类"
        sortable="custom"
        width="120px"
        ellipsis>
        <template #default="{ row }">
          {{ dictStore.getDictLabel('rpa_tool_list_category', row.category) }}
        </template>
      </TableColumn>
      <TableColumn
        prop="runTimes"
        label="运行时长(秒)"
        sortable="custom"
        width="140px"
        align="right"
      />
      <TableColumn
        prop="templeteId"
        label="模版"
        sortable="custom"
        width="90px"
        ellipsis>
        <template #default="{ row }">
          <t-link
            size="small"
            :type="row.templeteId ? 'success' : 'danger'"
            @click="openTemplateDialog(row)"
          >
            {{ row.templeteId ? '已配置' : '未配置' }}
          </t-link>
        </template>
      </TableColumn>
      <TableColumn
        prop="roles"
        label="权限"
        width="90px"
        sortable="custom"
        ellipsis>
        <template #default="{ row }">
          <t-link
            size="small"
            :type="row.roles ? 'danger' : 'success'"
            @click="openRoleDialog(row)"
          >
            {{ row.roles ? '限制' : '不设限' }}
          </t-link>
        </template>
      </TableColumn>
      <TableColumn
        prop="sortId"
        label="排序"
        sortable="custom"
        width="80px"
        align="right"
      />
      <TableColumn
        prop="contacts"
        label="联系人"
        sortable="custom"
        width="120px"
        ellipsis
      />
      <TableColumn
        prop="status"
        label="状态"
        sortable="custom"
        width="90px">
        <template #default="{ row }">
          <t-tag
            :theme="row.status ? 'success' : 'danger'"
            size="small"
            @click="changeStatus(row)"
            class="status-tag"
          >
            {{ row.status ? '有效' : '无效' }}
          </t-tag>
        </template>
      </TableColumn>
      <TableColumn
        prop="createTime"
        label="创建时间"
        sortable="custom"
        width="160px"
      />
      <TableColumn
        prop="createUser"
        label="创建人"
        sortable="custom"
        width="120px"
      />
      <TableColumn label="操作" width="120px" fixed="right">
        <template #default="{ row }">
          <t-button
            theme="warning"
            size="small" @click="openToolDialog('update', row)"
            shape="circle"><template #icon><DynamicIcon name="edit" /></template></t-button>
          <t-button
            theme="danger"
            size="small" @click="removeTool(row)"
            shape="circle"><template #icon><DynamicIcon name="delete" /></template></t-button>
        </template>
      </TableColumn>
    </CustomTable>

    <!-- 分页组件 -->
    <t-pagination
      @page-size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current="queryInfo.pageNum"
      :page-size-options="[20, 40, 100, 200]"
      :page-size="queryInfo.pageSize"

      :total="total"
      class="pagination"
    />
  </t-card>

  <!-- 工具对话框 -->
  <ToolDialog ref="toolDialogRef" @refresh="getList" />

  <!-- 模板对话框 -->
  <TemplateDialog ref="templateDialogRef" @refresh="getList" />

  <!-- 角色权限对话框 -->
  <RoleDialog ref="roleDialogRef" @refresh="getList" />
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { MessagePlugin, DialogPlugin } from 'tdesign-vue-next'
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
    MessagePlugin.error(error.message || '获取工具列表失败')
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
const tableSort = ({ sortBy, descending }) => {
  queryInfo.value.order = sortBy
  queryInfo.value.orderType = !descending ? 'asc' : 'desc'
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
    await DialogPlugin.confirm(
      `此操作将${row.status ? '禁用' : '启用'}该工具, 是否继续?`,
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    await updateRpaToolStatus({ id: row.id, status: row.status === 1 ? 0 : 1 })
    MessagePlugin.success('状态更新成功')
    getList()
  } catch (error) {
    if (error !== 'cancel') {
      MessagePlugin.error(error.message || '状态更新失败')
    }
  }
}

// 删除工具
const removeTool = async (row) => {
  try {
    await DialogPlugin.alert('此操作将永久删除该工具, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })

    await deleteRpaTool(row.id)
    MessagePlugin.success('删除成功')
    getList()
  } catch (error) {
    if (error !== 'cancel') {
      MessagePlugin.error(error.message || '删除失败')
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
