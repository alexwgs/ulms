<template>
  <PageTips
    title="操作说明"
    theme="info"
    :closable="false"
    message="请正确使用用户信息：1.用户列表取自人员成长平台。2.需要注意本系统角色与成长平台不一致，如有新入职员工，需搜索后手动分配角色。3.该页面数据每个整点更新。"
  />
  <t-card class="management-card">
    <!-- 搜索区域（参考 TDesign Starter 筛选列表页） -->
    <t-form :data="queryInfo" label-width="80px" colon class="filter-form">
      <t-row :gutter="[24, 24]">
        <t-col :span="6">
          <t-form-item label="查询条件" name="query">
            <t-input-adornment class="search-input">
              <template #prepend>
                <t-select
                  size="small"
                  v-model="queryInfo.queryType"
                  placeholder="请选择"
                >
                  <t-option label="姓名" value="ploName" />
                  <t-option label="工号" value="ploNum" />
                  <t-option label="科室" value="deptNum" />
                  <t-option label="组别" value="DeptGroup" />
                  <t-option label="AgentId" value="agentNum" />
                </t-select>
              </template>
              <template #append>
                <t-button variant="outline" theme="primary" size="small" @click="getEmployeeList">搜索</t-button>
              </template>
              <t-input size="small" placeholder="请输入内容" v-model="queryInfo.query"></t-input>
            </t-input-adornment>
          </t-form-item>
        </t-col>

        <t-col :span="3">
          <t-form-item label="在职状态" name="employmentStatus">
            <t-select
              size="small"
              v-model="employmentStatus"
              placeholder="全部"
              @change="getEmployeeList"
            >
              <t-option label="全部" value="all" />
              <t-option label="在职" value="00" />
              <t-option label="离职" value="01" />
            </t-select>
          </t-form-item>
        </t-col>

        <t-col :span="3" class="operation-container">
          <t-button size="small" theme="primary" :disabled="true">
            添加新用户
          </t-button>
        </t-col>
      </t-row>
    </t-form>
    <!-- 员工表格 -->
    <CustomTable rowKey="id"
      size="small"
      :data="list"
      height="calc(100vh - 400px)"
      :loading="loading">
      <TableColumn label="科室" width="100" colKey="deptName" />
      <TableColumn label="组别" width="100" colKey="groupName" />
      <TableColumn label="工号" width="90" colKey="ploNum" />
      <TableColumn label="姓名" width="110">
        <template #default="{ row }">
          <t-popup trigger="hover" placement="top" :overlay-style="{ width: '280px' }">
            <span class="user-name-cell">{{ row.ploName }}</span>
            <template #content>
              <div class="user-info-tip">
                <div class="tip-name">{{ row.ploName }}</div>
                <div class="tip-row"><span>工号</span><b>{{ row.ploNum || '--' }}</b></div>
                <div class="tip-row"><span>科室</span><b>{{ row.deptName || '--' }}</b></div>
                <div class="tip-row"><span>组别</span><b>{{ row.groupName || '--' }}</b></div>
                <div class="tip-row"><span>岗位</span><b>{{ getJobName(row.jobLevel) }}</b></div>
                <div class="tip-row"><span>电话</span><b>{{ row.ploMobil || '--' }}</b></div>
                <div class="tip-row"><span>住址</span><b>{{ row.ploAddr || '--' }}</b></div>
                <div class="tip-row"><span>入职日期</span><b>{{ row.inDate || '--' }}</b></div>
                <div class="tip-row"><span>离职日期</span><b>{{ row.outDate || '--' }}</b></div>
              </div>
            </template>
          </t-popup>
        </template>
      </TableColumn>
      <TableColumn label="AgentId" width="90" colKey="agentNum" />
      <TableColumn label="期数" width="90" colKey="batchGroup" />
      <TableColumn label="岗位" width="100" colKey="jobLevel">
        <template #default="{ row }">
          {{ getJobName(row.jobLevel) }}
        </template>
      </TableColumn>
      <TableColumn label="入职日期" width="120" colKey="inDate" />
      <TableColumn label="离职日期" width="120" colKey="outDate" />
      <TableColumn label="操作" fixed="right">
        <template #default="{ row }">
          <t-button variant="outline"
            theme="default"
            size="small" @click="handleEdit(row)"
            :disabled="true"
           >编辑</t-button>
          <t-button size="small" @click="openRoleDialog(row)">
            角色分配
          </t-button>
        </template>
      </TableColumn>
    </CustomTable>

    <!-- 分页组件 -->
    <t-pagination
      @page-size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current="currentPage"
      :page-size-options="pageSizes"
      :page-size="queryInfo.pageSize"

      :total="total"
      class="pagination"
    />
  </t-card>

  <!-- 角色分配对话框 -->
  <RoleDialog ref="roleDialogRef" @refresh="getEmployeeList" />
</template>

<script setup>
import { ref, onMounted } from 'vue'
import RoleDialog from './components/RoleDialog.vue'
import { employeeApi } from '@/api/system/employee'
import { jobinfoApi } from '@/api/system/jobinfo'
import { useCrudPage } from '@/hooks/useCrudPage'
const roleDialogRef = ref(null)

const employmentStatus = ref('00')
const jobLevelList = ref([])

// 列表 + 分页（useCrudPage 样板，无删除操作）
const {
  list,
  total,
  loading,
  query: queryInfo,
  currentPage,
  pageSizes,
  handleCurrentChange,
  handleSizeChange,
  load: getEmployeeList
} = useCrudPage({
  fetchList: (q) => employeeApi.listEmployeeByStatus(employmentStatus.value, q),
  defaultQuery: {
    orderType: 'desc',
    order: '',
    queryType: 'ploNum',
    query: '',
    pageSize: 20,
    pageNum: 1
  },
  pageSizes: [20, 100, 500]
})
const getJobLevelList = async () => {
  try {
    const res = await jobinfoApi.listAllJobLevel()
    if (res.code != 200) return
    jobLevelList.value = res.data
  } catch (error) {
    console.error('获取员工列表失败:', error)
  } finally {
    // 无清理操作
  }
}

// 根据岗位编码取岗位名称
const getJobName = (level) => {
  const list = jobLevelList.value
  if (Array.isArray(list)) {
    const hit = list.find((item) => String(item.jobLevel) === String(level))
    if (hit) return hit.jobName
  }
  return level == null || level === '' ? '--' : String(level)
}
// 编辑员工
const handleEdit = (row) => {
}

// 打开角色分配对话框
const openRoleDialog = (employee) => {
  roleDialogRef.value.openDialog(employee)
}

// 初始化数据
onMounted(() => {
  getJobLevelList()
  getEmployeeList()
})
</script>

<style lang="less" scoped>
.employee-management {
  padding: 16px;
}

.management-card {
  // margin-top: 16px;
  height: calc(100vh - 240px);
}

.search-row {
  margin-bottom: var(--td-comp-margin-xxl);

  .search-input {
    width: 100%;
  }

  .text-right {
    display: flex;
    justify-content: flex-end;
  }
}

.pagination {
  margin-top: var(--td-comp-margin-xxl);
  justify-content: flex-end;
}

.user-name-cell {
  color: var(--td-brand-color);
  border-bottom: 1px dashed var(--td-brand-color);
  cursor: pointer;
  padding-bottom: 1px;
}

.user-info-tip {
  font-size: 13px;
}

.user-info-tip .tip-name {
  font-size: 15px;
  font-weight: 600;
  margin-bottom: 8px;
  color: var(--td-text-color-primary);
}

.user-info-tip .tip-row {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  line-height: 24px;
  color: var(--td-text-color-secondary);
}

.user-info-tip .tip-row b {
  color: var(--td-text-color-primary);
  font-weight: 500;
  text-align: right;
}
</style>
