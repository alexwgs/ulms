<template>
  <t-card class="tool-card">
    <!-- 筛选条件区域 -->
    <!-- 操作说明 -->
    <PageTips
      title="操作说明"
      theme="info"
      :closable="false"
      message="请正确使用RPA工具配置：1.新建服务时务必完整填写相关信息。2.无需参数的可不配置参数模版，如有请务必准确配置。3.权限，如不配置则全员可查看"
      class="alert-message"
    />
    <div class="filter-bar">
      <t-form
        :data="queryInfo"
        label-width="80px"
        colon
        layout="inline"
        class="filter-form"
      >
        <t-form-item label="关键字" name="query">
          <t-input-adornment style="width: 320px">
            <template #prepend>
              <t-select
                v-model="queryInfo.queryType"
                size="small"
                placeholder="查询类型"
                style="width: 90px"
                clearable
              >
                <t-option label="工具名称" value="name" />
                <t-option label="工具ID" value="id" />
              </t-select>
            </template>
            <template #append>
              <t-button variant="outline" theme="primary" size="small" @click="getList">搜索</t-button>
            </template>
            <t-input placeholder="请输入工具名称" size="small" v-model="queryInfo.query" clearable @clear="getList"></t-input>
          </t-input-adornment>
        </t-form-item>
        <t-form-item label="类别" name="category">
          <t-select
            v-model="queryInfo.category"
            size="small"
            placeholder="全部"
            style="width: 150px"
            @change="getList"
            clearable
          >
            <t-option label="全部类别" value="" />
            <t-option
              v-for="item in (dictStore.dictList?.rpa_tool_list_category || [])"
              :key="item.code"
              :label="item.codeval"
              :value="item.code"
            />
          </t-select>
        </t-form-item>
        <t-form-item label="状态" name="status">
          <t-select
            v-model="queryInfo.status"
            size="small"
            placeholder="全部"
            style="width: 140px"
            @change="getList"
            clearable
          >
            <t-option label="全部" value="" />
            <t-option label="有效" :value="1" />
            <t-option label="无效" :value="0" />
          </t-select>
        </t-form-item>
      </t-form>
      <div class="operation-container">
        <t-button variant="outline" theme="primary" size="small" @click="openToolDialog('add')">新建工具</t-button>
      </div>
    </div>

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
           variant="light">
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
          <t-button variant="outline"
            theme="default"
            size="small" @click="openToolDialog('update', row)"
           >编辑</t-button>
          <t-button variant="outline"
            theme="danger"
            size="small" @click="removeTool(row)"
           >删除</t-button>
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
import { MessagePlugin } from 'tdesign-vue-next'
import { useDictStore } from '@/stores'
import ToolDialog from './components/ToolDialog.vue'
import TemplateDialog from './components/TemplateDialog.vue'
import RoleDialog from './components/RoleDialog.vue'
import { useConfirm } from '@/hooks/useConfirm'
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
  const nextStatus = row.status === 1 ? 0 : 1
  const { confirm: confirmDialog } = useConfirm()
  const ok = await confirmDialog(
    `此操作将${row.status ? '禁用' : '启用'}该工具, 是否继续?`,
    { title: '提示' }
  )
  if (!ok) return
  try {
    await updateRpaToolStatus({ id: row.id, status: nextStatus })
    MessagePlugin.success('状态更新成功')
    getList()
  } catch (error) {
    MessagePlugin.error(error.message || '状态更新失败')
  }
}

// 删除工具
const removeTool = async (row) => {
  const { confirm: confirmDialog } = useConfirm()
  const ok = await confirmDialog('此操作将永久删除该工具, 是否继续?', {
    title: '提示'
  })
  if (!ok) return
  try {
    await deleteRpaTool(row.id)
    MessagePlugin.success('删除成功')
    getList()
  } catch (error) {
    MessagePlugin.error(error.message || '删除失败')
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
  margin-bottom: var(--td-comp-margin-xxl);

  .text-right {
    display: flex;
    justify-content: flex-end;
  }
}

.filter-bar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
  margin-bottom: 16px;
}

.filter-bar .filter-form {
  flex: 1;
  display: flex;
  align-items: center;
  gap: 16px;
  min-width: 0;
  margin-bottom: 0;
}

.filter-bar .operation-container {
  display: flex;
  align-items: center;
  gap: 8px;
  flex-shrink: 0;
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
  margin-top: var(--td-comp-margin-xxl);
  justify-content: flex-end;
}
</style>
