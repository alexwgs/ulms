<template>
  <el-card class="monitor-card">
    <!-- ==================== 工具栏 ==================== -->
    <div class="toolbar">
      <div class="toolbar-left">
        <el-input
          v-model="searchKey"
          placeholder="搜索姓名 / 工号 / 部门"
          clearable
          style="width: 260px"
          @input="handleSearch"
        >
          <template #prefix><el-icon><Search /></el-icon></template>
        </el-input>
        <el-select v-model="roleFilter" placeholder="角色筛选" style="width: 130px" @change="handleSearch">
          <el-option label="全部角色" value="" />
          <el-option label="接单组" value="1" />
          <el-option label="求助组" value="0" />
        </el-select>
        <el-select v-model="statusFilter" placeholder="状态筛选" style="width: 130px" @change="handleSearch">
          <el-option label="全部状态" value="" />
          <el-option label="有案件处理中" value="busy" />
          <el-option label="在线空闲" value="idle" />
        </el-select>
      </div>
      <div class="toolbar-right">
        <span class="selected-info" v-if="selectedUsers.length">
          已选 <strong>{{ selectedUsers.length }}</strong> 人
        </span>
        <el-button @click="toggleSelectAll">
          {{ isAllSelected ? '取消全选' : '全选当前页' }}
        </el-button>
        <el-button type="primary" @click="dialogVisible = true" :disabled="!filteredUsers.length">
          <el-icon><Promotion /></el-icon>
          推送消息
        </el-button>
        <el-button :icon="Refresh" circle @click="fetchOnlineUsers" :loading="loading" />
      </div>
    </div>

    <!-- ==================== 用户表格 ==================== -->
    <el-table
      ref="tableRef"
      :data="filteredUsers"
      v-loading="loading"
      stripe
      @selection-change="handleSelectionChange"
      :default-sort="{ prop: 'statusTime', order: 'descending' }"
      height="calc(100vh - 290px)"
    >
      <el-table-column type="selection" width="45" :selectable="() => true" />
      <el-table-column label="用户" min-width="180" sortable prop="user.ploName">
        <template #default="{ row }">
          <div class="user-cell">
            <el-avatar shape="square" :size="36" :src="row.user?.avatar ? fsURL + row.user.avatar : defaultAvatar" />
            <div class="user-info">
              <span class="uname">{{ row.user?.ploName || '--' }}</span>
              <span class="unum">{{ row.user?.ploNum || row.userId }}</span>
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="部门" min-width="160" show-overflow-tooltip>
        <template #default="{ row }">
          {{ row.user?.deptName || '' }}{{ row.user?.groupName ? ' / ' + row.user.groupName : '' }}
        </template>
      </el-table-column>
      <el-table-column label="角色" width="100" align="center">
        <template #default="{ row }">
          <el-tag v-if="row.ohtRole?.roleType === 1" type="success" size="small" effect="dark" round>接单组</el-tag>
          <el-tag v-else-if="row.ohtRole?.roleType === 0" type="warning" size="small" effect="dark" round>求助组</el-tag>
          <el-tag v-else type="info" size="small" effect="plain" round>未知</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="130" align="center">
        <template #default="{ row }">
          <template v-if="row.unfinishCase">
            <el-tag :type="row.unfinishCase.caseStatus === 0 ? 'warning' : 'danger'" size="small" effect="dark" round>
              {{ row.unfinishCase.caseStatus === 0 ? '求助中' : '处理中' }}
            </el-tag>
          </template>
          <template v-else>
            <span class="status-name">{{ row.statusName || '在线' }}</span>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="IP 地址" width="150" show-overflow-tooltip>
        <template #default="{ row }">
          {{ row.station?.pcIp || row.station?.extnIp || '--' }}
        </template>
      </el-table-column>
      <el-table-column label="分机号" width="100" align="center">
        <template #default="{ row }">
          {{ row.station?.extnNum || '--' }}
        </template>
      </el-table-column>
      <el-table-column label="楼层" width="80" align="center" prop="station.floorNum">
        <template #default="{ row }">
          {{ row.station?.floorNum ? row.station.floorNum + '楼' : '--' }}
        </template>
      </el-table-column>
    </el-table>

    <!-- ==================== 底部统计 ==================== -->
    <div class="footer-bar">
      <span>
        在线共 <strong>{{ onlineUsers.length }}</strong> 人，
        接单组 <strong>{{ onlineUsers.filter(u => u.ohtRole?.roleType === 1).length }}</strong> 人，
        求助组 <strong>{{ onlineUsers.filter(u => u.ohtRole?.roleType === 0).length }}</strong> 人
      </span>
      <span class="update-time" v-if="lastUpdateTime">最近更新: {{ lastUpdateTime }}</span>
    </div>
  </el-card>

  <!-- ==================== 推送消息对话框 ==================== -->
  <el-dialog v-model="dialogVisible" title="推送消息" width="560px" destroy-on-close>
    <el-form :model="pushForm" label-width="80px" :rules="pushRules" ref="pushFormRef">
      <el-form-item label="推送目标">
        <el-radio-group v-model="pushTarget">
          <el-radio value="selected" :disabled="!selectedUsers.length">
            选中用户（{{ selectedUsers.length }}人）
          </el-radio>
          <el-radio value="all">全部在线用户（{{ onlineUsers.length }}人）</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="模块" prop="module">
        <el-select v-model="pushForm.module" filterable allow-create placeholder="选择或输入模块名">
          <el-option label="sys (系统)" value="sys" />
          <el-option label="flow (流程)" value="flow" />
          <el-option label="oht (求助)" value="oht" />
          <el-option label="edu (教育)" value="edu" />
          <el-option label="hr (人事)" value="hr" />
          <el-option label="cyt (论坛)" value="cyt" />
        </el-select>
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-select v-model="pushForm.type" filterable allow-create placeholder="选择或输入消息类型">
          <el-option label="notice (通知)" value="notice" />
          <el-option label="warning (警告)" value="warning" />
          <el-option label="info (信息)" value="info" />
          <el-option label="error (错误)" value="error" />
        </el-select>
      </el-form-item>
      <el-form-item label="标题" prop="title">
        <el-input v-model="pushForm.title" placeholder="消息标题" maxlength="100" show-word-limit />
      </el-form-item>
      <el-form-item label="内容" prop="content">
        <el-input
          v-model="pushForm.content"
          type="textarea"
          :rows="5"
          placeholder="消息内容（可选，支持纯文本）"
          maxlength="500"
          show-word-limit
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitPush" :loading="pushing">
        <el-icon><Promotion /></el-icon>
        发送推送
      </el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { Search, Refresh, Promotion } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { onlineMonitorApi } from '@/API/admin/onlineMonitor'

// ==================== 状态 ====================
const loading = ref(false)
const pushing = ref(false)
const onlineUsers = ref([])
const selectedUsers = ref([])
const searchKey = ref('')
const roleFilter = ref('')
const statusFilter = ref('')
const dialogVisible = ref(false)
const pushTarget = ref('selected')
const lastUpdateTime = ref('')
const tableRef = ref(null)
const pushFormRef = ref(null)

const fsURL = import.meta.env.VITE_FILE_MANAGE_BASE
const defaultAvatar = new URL('@/assets/img/default_avatar.png', import.meta.url).href

const pushForm = ref({
  module: 'sys',
  type: 'notice',
  title: '',
  content: ''
})

const pushRules = {
  module: [{ required: true, message: '请选择模块', trigger: 'change' }],
  type: [{ required: true, message: '请选择或输入类型', trigger: 'change' }],
  title: [{ required: true, message: '请输入消息标题', trigger: 'blur' }]
}

let pollTimer = null

// ==================== 计算属性 ====================
const filteredUsers = computed(() => {
  let list = onlineUsers.value
  const kw = searchKey.value?.toLowerCase() || ''

  if (kw) {
    list = list.filter(u => {
      const name = (u.user?.ploName || '').toLowerCase()
      const num = (u.user?.ploNum || u.userId || '').toLowerCase()
      const dept = (u.user?.deptName || '').toLowerCase()
      const group = (u.user?.groupName || '').toLowerCase()
      return name.includes(kw) || num.includes(kw) || dept.includes(kw) || group.includes(kw)
    })
  }

  if (roleFilter.value !== '') {
    list = list.filter(u => u.ohtRole?.roleType === Number(roleFilter.value))
  }

  if (statusFilter.value === 'busy') {
    list = list.filter(u => u.unfinishCase)
  } else if (statusFilter.value === 'idle') {
    list = list.filter(u => !u.unfinishCase)
  }

  return list
})

const isAllSelected = computed(() => {
  if (!filteredUsers.value.length) return false
  const selectedIds = new Set(selectedUsers.value.map(u => u.userId))
  return filteredUsers.value.every(u => selectedIds.has(u.userId))
})

// ==================== 方法 ====================
async function fetchOnlineUsers() {
  try {
    const res = await onlineMonitorApi.getOnlineUsers()
    if (res.code === 200) {
      onlineUsers.value = res.data || []
      lastUpdateTime.value = new Date().toLocaleTimeString()
    }
  } catch (e) {
    console.error('获取在线用户失败:', e)
  } finally {
    loading.value = false
  }
}

function handleSearch() {
  // computed 自动响应，无需额外处理
}

function handleSelectionChange(rows) {
  selectedUsers.value = rows
}

function toggleSelectAll() {
  if (isAllSelected.value) {
    tableRef.value?.clearSelection()
  } else {
    filteredUsers.value.forEach(row => tableRef.value?.toggleRowSelection(row, true))
  }
}

async function submitPush() {
  const valid = await pushFormRef.value.validate().catch(() => false)
  if (!valid) return

  pushing.value = true
  try {
    const userIds = pushTarget.value === 'selected'
      ? selectedUsers.value.map(u => u.userId)
      : []

    const res = await onlineMonitorApi.pushMessage({
      userIds,
      module: pushForm.value.module,
      type: pushForm.value.type,
      title: pushForm.value.title,
      content: pushForm.value.content || null
    })

    if (res.code === 200) {
      ElMessage.success('推送成功')
      dialogVisible.value = false
      pushForm.value.title = ''
      pushForm.value.content = ''
      pushTarget.value = 'selected'
    } else {
      ElMessage.error(res.msg || '推送失败')
    }
  } catch (e) {
    ElMessage.error('推送失败，请检查网络连接')
  } finally {
    pushing.value = false
  }
}

// ==================== 生命周期 ====================
onMounted(() => {
  loading.value = true
  fetchOnlineUsers()
  pollTimer = setInterval(fetchOnlineUsers, 30000)
})

onUnmounted(() => {
  clearInterval(pollTimer)
})
</script>

<style lang="less" scoped>
.monitor-card {
  height: calc(100vh - 190px);
  display: flex;
  flex-direction: column;

  :deep(.el-card__body) {
    flex: 1;
    display: flex;
    flex-direction: column;
    padding: 16px;
  }
}

.toolbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex-wrap: wrap;
  gap: 12px;
  margin-bottom: 14px;
}

.toolbar-left {
  display: flex;
  align-items: center;
  gap: 10px;
}

.toolbar-right {
  display: flex;
  align-items: center;
  gap: 10px;
}

.selected-info {
  font-size: 13px;
  color: var(--el-text-color-secondary);
}

.user-cell {
  display: flex;
  align-items: center;
  gap: 10px;
}

.user-info {
  display: flex;
  flex-direction: column;
  line-height: 1.3;
}

.uname {
  font-size: 14px;
  font-weight: 500;
  color: var(--el-text-color-primary);
}

.unum {
  font-size: 12px;
  color: var(--el-text-color-secondary);
  font-family: 'Courier New', monospace;
}

.status-name {
  font-size: 12px;
  color: var(--el-text-color-secondary);
}

.footer-bar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-top: 12px;
  padding-top: 10px;
  border-top: 1px solid var(--el-border-color-lighter);
  font-size: 13px;
  color: var(--el-text-color-secondary);
}

.update-time {
  font-size: 12px;
  color: var(--el-text-color-placeholder);
}
</style>
