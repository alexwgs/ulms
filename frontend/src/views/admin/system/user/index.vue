<template>
  <el-alert
    title="操作说明"
    type="info"
    :closable="false"
    description="请正确使用用户信息：1.用户列表取自人员成长平台。2.需要注意本系统角色与成长平台不一致，如有新入职员工，需搜索后手动分配角色。3.该页面数据每个整点更新。"
  />
  <el-card class="management-card">
    <!-- 搜索区域 -->
    <el-row :gutter="20" class="search-row">
      <el-col :span="10">
        <el-input
          size="small"
          placeholder="请输入内容"
          v-model="queryInfo.query"
          class="search-input"
        >
          <template #prepend>
            <el-select
              size="small"
              v-model="queryInfo.queryType"
              placeholder="请选择"
              style="width: 100px"
            >
              <el-option label="姓名" value="ploName" />
              <el-option label="工号" value="ploNum" />
              <el-option label="科室" value="deptNum" />
              <el-option label="组别" value="DeptGroup" />
              <el-option label="AgentId" value="agentNum" />
            </el-select>
          </template>
          <template #append>
            <el-button size="small" icon="Search" @click="getEmployeeList" />
          </template>
        </el-input>
      </el-col>

      <el-col :span="8">
        <el-select
          size="small"
          v-model="employmentStatus"
          placeholder="请选择是否在职名单"
          @change="getEmployeeList"
        >
          <el-option label="全部" value="all" />
          <el-option label="在职" value="00" />
          <el-option label="离职" value="01" />
        </el-select>
      </el-col>

      <el-col :span="6" class="text-right">
        <el-button size="small" type="primary" :disabled="true">
          添加新用户
        </el-button>
      </el-col>
    </el-row>
    <!-- 员工表格 -->
    <el-table
      size="small"
      :data="list"
      height="calc(100vh - 400px)"
      v-loading="loading"
    >
      <el-table-column label="科室" width="100" prop="deptName" />
      <el-table-column label="组别" width="100" prop="groupName" />
      <el-table-column label="工号" width="90" prop="ploNum" />
      <el-table-column label="姓名" width="90">
        <template #default="{ row }">
          <el-popover trigger="hover" placement="top" width="200">
            <template #default>
              <p>姓名: {{ row.ploName }}</p>
              <p>住址: {{ row.ploAddr }}</p>
              <p>电话: {{ row.ploMobil }}</p>
            </template>
            <template #reference>
              <el-tag size="small">{{ row.ploName }}</el-tag>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="AgentId" width="90" prop="agentNum" />
      <el-table-column label="期数" width="90" prop="batchGroup" />
      <el-table-column label="岗位" width="100" prop="jobLevel">
        <template #default="{ row }">
          <!-- jobLevelList 是一个数组，包含所有岗位信息 -->
          {{
            Array.isArray(jobLevelList.value)
              ? jobLevelList.value.find(
                  (item) => String(item.jobLevel) === String(row.jobLevel)
                )?.jobName || ''
              : String(row.jobLevel)
          }}
        </template>
      </el-table-column>
      <el-table-column label="入职日期" width="120" prop="inDate" />
      <el-table-column label="离职日期" width="120" prop="outDate" />
      <el-table-column label="操作" fixed="right">
        <template #default="{ row }">
          <el-button
            type="warning"
            size="small"
            icon="Edit"
            @click="handleEdit(row)"
            :disabled="true"
            circle
          />
          <el-button size="small" @click="openRoleDialog(row)">
            角色分配
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="queryInfo.pageNum"
      :page-sizes="[20, 100, 500]"
      :page-size="queryInfo.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      class="pagination"
    />
  </el-card>

  <!-- 角色分配对话框 -->
  <RoleDialog ref="roleDialogRef" @refresh="getEmployeeList" />
</template>

<script setup>
import { ref, onMounted } from 'vue'
import RoleDialog from './components/RoleDialog.vue'
import { employeeApi } from '@/api/system/employee'
import { jobinfoApi } from '@/api/system/jobinfo'
const roleDialogRef = ref(null)

// 查询参数
const queryInfo = ref({
  orderType: 'desc',
  order: '',
  queryType: 'ploNum',
  query: '',
  pageSize: 20,
  pageNum: 1
})

const employmentStatus = ref('00')
const loading = ref(false)
const list = ref([])
const total = ref(0)
const jobLevelList = ref([])

// 获取员工列表
const getEmployeeList = async () => {
  try {
    loading.value = true
    const res = await employeeApi.listEmployeeByStatus(
      employmentStatus.value,
      queryInfo.value
    )
    if (res.code != 200) return
    list.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    console.error('获取员工列表失败:', error)
  } finally {
    loading.value = false
  }
}
const getJobLevelList = async () => {
  try {
    const res = await jobinfoApi.listAllJobLevel()
    if (res.code != 200) return
    jobLevelList.value = res.data
  } catch (error) {
    console.error('获取员工列表失败:', error)
  } finally {
  }
}
// 分页大小变化
const handleSizeChange = (pageSize) => {
  queryInfo.value.pageSize = pageSize
  getEmployeeList()
}

// 当前页码变化
const handleCurrentChange = (page) => {
  queryInfo.value.pageNum = page
  getEmployeeList()
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
  margin-bottom: 16px;

  .search-input {
    width: 100%;
  }

  .text-right {
    display: flex;
    justify-content: flex-end;
  }
}

.pagination {
  margin-top: 16px;
  justify-content: flex-end;
}
</style>
