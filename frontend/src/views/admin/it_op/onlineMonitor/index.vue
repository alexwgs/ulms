<template>
  <t-card class="monitor-card">
    <!-- ==================== 工具栏 ==================== -->
    <div class="toolbar">
      <t-form :data="{ searchKey, roleFilter, statusFilter }" label-width="80px" colon class="filter-form toolbar-filter-form">
        <t-row :gutter="[24, 24]">
          <t-col :span="6">
            <t-form-item label="关键字" name="searchKey">
              <t-input
                v-model="searchKey"
                placeholder="搜索姓名 / 工号 / 部门"
                clearable
                @input="handleSearch"
              >
                <template #prefix><t-icon><SearchIcon /></t-icon></template>
              </t-input>
            </t-form-item>
          </t-col>
          <t-col :span="3">
            <t-form-item label="角色" name="roleFilter">
              <t-select v-model="roleFilter" placeholder="全部角色" @change="handleSearch">
                <t-option label="全部角色" value="" />
                <t-option label="接单组" value="1" />
                <t-option label="求助组" value="0" />
              </t-select>
            </t-form-item>
          </t-col>
          <t-col :span="3">
            <t-form-item label="状态" name="statusFilter">
              <t-select v-model="statusFilter" placeholder="全部状态" @change="handleSearch">
                <t-option label="全部状态" value="" />
                <t-option label="有案件处理中" value="busy" />
                <t-option label="在线空闲" value="idle" />
              </t-select>
            </t-form-item>
          </t-col>
        </t-row>
      </t-form>
      <div class="toolbar-right">
        <span class="selected-info" v-if="selectedUsers.length">
          已选 <strong>{{ selectedUsers.length }}</strong> 人
        </span>
        <t-button variant="outline" @click="toggleSelectAll">
          {{ isAllSelected ? '取消全选' : '全选当前页' }}
        </t-button>
        <t-button variant="outline" theme="primary" @click="dialogVisible = true" :disabled="!filteredUsers.length">
          <NotificationIcon />
          推送消息
        </t-button>
        <t-button variant="outline" theme="default" @click="fetchOnlineUsers" :loading="loading">刷新</t-button>
      </div>
    </div>

    <!-- ==================== 用户表格 ==================== -->
    <CustomTable rowKey="id"
      ref="tableRef"
      :data="filteredUsers"
      :loading="loading"
      stripe
      @select-change="handleSelectionChange"
      :default-sort="{ sortBy: 'statusTime', descending: true }"
      height="calc(100vh - 290px)">
      <TableColumn type="multiple" width="45" :selectable="() => true" />
      <TableColumn label="用户" min-width="180" sortable colKey="user.ploName">
        <template #default="{ row }">
          <div class="user-cell">
            <t-avatar shape="round" size="36px" :src="row.user?.avatar ? fsURL + row.user.avatar : defaultAvatar" />
            <div class="user-info">
              <span class="uname">{{ row.user?.ploName || '--' }}</span>
              <span class="unum">{{ row.user?.ploNum || row.userId }}</span>
            </div>
          </div>
        </template>
      </TableColumn>
      <TableColumn label="部门" min-width="160" ellipsis>
        <template #default="{ row }">
          {{ row.user?.deptName || '' }}{{ row.user?.groupName ? ' / ' + row.user.groupName : '' }}
        </template>
      </TableColumn>
      <TableColumn label="角色" width="100" align="center">
        <template #default="{ row }">
          <t-tag v-if="row.ohtRole?.roleType === 1" theme="success" size="small" variant="light" round>接单组</t-tag>
          <t-tag v-else-if="row.ohtRole?.roleType === 0" theme="warning" size="small" variant="light" round>求助组</t-tag>
          <t-tag v-else theme="default" size="small" variant="light" round>未知</t-tag>
        </template>
      </TableColumn>
      <TableColumn label="状态" width="130" align="center">
        <template #default="{ row }">
          <template v-if="row.unfinishCase">
            <t-tag :theme="row.unfinishCase.caseStatus === 0 ? 'warning' : 'danger'" size="small" variant="light" round>
              {{ row.unfinishCase.caseStatus === 0 ? '求助中' : '处理中' }}
            </t-tag>
          </template>
          <template v-else>
            <span class="status-name">{{ row.statusName || '在线' }}</span>
          </template>
        </template>
      </TableColumn>
      <TableColumn label="IP 地址" width="150" ellipsis>
        <template #default="{ row }">
          {{ row.station?.pcIp || row.station?.extnIp || '--' }}
        </template>
      </TableColumn>
      <TableColumn label="分机号" width="100" align="center">
        <template #default="{ row }">
          {{ row.station?.extnNum || '--' }}
        </template>
      </TableColumn>
      <TableColumn label="楼层" width="80" align="center" colKey="station.floorNum">
        <template #default="{ row }">
          {{ row.station?.floorNum ? row.station.floorNum + '楼' : '--' }}
        </template>
      </TableColumn>
    </CustomTable>

    <!-- ==================== 底部统计 ==================== -->
    <div class="footer-bar">
      <span>
        在线共 <strong>{{ onlineUsers.length }}</strong> 人，
        接单组 <strong>{{ onlineUsers.filter(u => u.ohtRole?.roleType === 1).length }}</strong> 人，
        求助组 <strong>{{ onlineUsers.filter(u => u.ohtRole?.roleType === 0).length }}</strong> 人
      </span>
      <span class="update-time" v-if="lastUpdateTime">最近更新: {{ lastUpdateTime }}</span>
    </div>
  </t-card>

  <!-- ==================== 推送消息对话框 ==================== -->
  <t-dialog v-model:visible="dialogVisible" header="推送消息" width="560px" destroy-on-close>
    <t-form :data="pushForm" label-width="80px" :rules="pushRules" ref="pushFormRef">
      <t-form-item label="推送目标">
        <t-radio-group v-model="pushTarget">
          <t-radio value="selected" :disabled="!selectedUsers.length">
            选中用户（{{ selectedUsers.length }}人）
          </t-radio>
          <t-radio value="all">全部在线用户（{{ onlineUsers.length }}人）</t-radio>
        </t-radio-group>
      </t-form-item>
      <t-form-item label="模块" name="module">
        <t-select v-model="pushForm.module" filterable allow-create placeholder="选择或输入模块名">
          <t-option label="sys (系统)" value="sys" />
          <t-option label="flow (流程)" value="flow" />
          <t-option label="oht (求助)" value="oht" />
          <t-option label="edu (教育)" value="edu" />
          <t-option label="hr (人事)" value="hr" />
          <t-option label="cyt (论坛)" value="cyt" />
        </t-select>
      </t-form-item>
      <t-form-item label="类型" name="type">
        <t-select v-model="pushForm.type" filterable allow-create placeholder="选择或输入消息类型">
          <t-option label="notice (通知)" value="notice" />
          <t-option label="warning (警告)" value="warning" />
          <t-option label="info (信息)" value="info" />
          <t-option label="error (错误)" value="error" />
        </t-select>
      </t-form-item>
      <t-form-item label="标题" name="title">
        <t-input v-model="pushForm.title" placeholder="消息标题" maxlength="100" show-limit-number />
      </t-form-item>
      <t-form-item label="内容" name="content">
        <t-textarea v-model="pushForm.content"
          :rows="5"
          placeholder="消息内容（可选，支持纯文本）"
          maxlength="500"
          show-limit-number />
      </t-form-item>
    </t-form>
    <template #footer>
      <t-button variant="outline" @click="dialogVisible = false">取消</t-button>
      <t-button variant="outline" theme="primary" @click="submitPush" :loading="pushing">
        <NotificationIcon />
        发送推送
      </t-button>
    </template>
  </t-dialog>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { SearchIcon, RefreshIcon, NotificationIcon } from 'tdesign-icons-vue-next'
import { MessagePlugin } from 'tdesign-vue-next'
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

const fsURL = import.meta.env.VITE_FILE_BASE_URL
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

function handleSelectionChange(_keys, options) {
  selectedUsers.value = options?.selectedRowData || []
}

function toggleSelectAll() {
  if (isAllSelected.value) {
    tableRef.value?.clearSelection()
  } else {
    filteredUsers.value.forEach(row => tableRef.value?.toggleRowSelection(row, true))
  }
}

async function submitPush() {
  const valid = await pushFormRef.value.validate()
  if (valid !== true) return

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
      MessagePlugin.success('推送成功')
      dialogVisible.value = false
      pushForm.value.title = ''
      pushForm.value.content = ''
      pushTarget.value = 'selected'
    } else {
      MessagePlugin.error(res.msg || '推送失败')
    }
  } catch (e) {
    MessagePlugin.error('推送失败，请检查网络连接')
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

  :deep(.t-card__body) {
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
  color: var(--td-text-color-secondary);
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
  color: var(--td-text-color-primary);
}

.unum {
  font-size: 12px;
  color: var(--td-text-color-secondary);
  font-family: 'Courier New', monospace;
}

.status-name {
  font-size: 12px;
  color: var(--td-text-color-secondary);
}

.footer-bar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-top: 12px;
  padding-top: 10px;
  border-top: 1px solid var(--td-component-stroke);
  font-size: 13px;
  color: var(--td-text-color-secondary);
}

.update-time {
  font-size: 12px;
  color: var(--td-text-color-placeholder);
}
</style>
