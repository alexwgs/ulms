<template>
  <t-card class="user-list-card">
    <template #header>
      <div class="card-header">
        <UserCircleIcon />
        <span>在线用户</span>
        <t-tag size="small" round effect="dark" theme="success">
          {{ ohtStore.ohtUserList.length }}
        </t-tag>
      </div>
    </template>

    <!-- ==================== 接单组 ==================== -->
    <div class="section" v-if="responders.length">
      <div class="section-header">
        <UserIcon />
        <span class="section-title">接单组</span>
        <t-tag size="small" round effect="plain">{{ responders.length }}</t-tag>
      </div>
      <div class="user-cards">
        <div
          v-for="user in responders"
          :key="user.userId"
          class="user-card"
          :class="responderCardClass(user)"
        >
          <div class="card-left">
            <t-avatar
              shape="square"
              :size="42"
              :src="(user.user?.avatar ? fsURL + user.user.avatar : defaultAvatar)"
              @error="e => e.target.src = defaultAvatar"
            />
            <div class="user-detail">
              <div class="user-name-line">
                <span class="user-name">{{ user.user?.ploName || '--' }}</span>
                <span class="user-num">{{ user.user?.ploNum || '--' }}</span>
              </div>
              <div class="user-dept">
                {{ user.user?.deptName || '' }}<template v-if="user.user?.groupName"> / {{ user.user.groupName }}</template>
              </div>
              <div class="user-ext" v-if="user.station">
                EXT:{{ user.station.extnNum }} {{ user.station.floorNum }}楼
              </div>
            </div>
          </div>
          <div class="card-right">
            <t-tag
              :theme="responderStatusType(user)"
              size="small"
              effect="dark"
              round
            >
              {{ responderStatusText(user) }}
            </t-tag>
            <span class="status-sub" v-if="!user.unfinishCase">{{ user.statusName || '' }}</span>
          </div>
        </div>
      </div>
    </div>

    <!-- ==================== 求助组 ==================== -->
    <div class="section" v-if="seekers.length">
      <div class="section-header seeker-header">
        <ErrorCircleFilledIcon />
        <span class="section-title">求助组</span>
        <t-tag size="small" round effect="plain" theme="warning">{{ seekers.length }}</t-tag>
      </div>
      <div class="user-cards">
        <div
          v-for="user in seekers"
          :key="user.userId"
          class="user-card"
          :class="seekerCardClass(user)"
        >
          <div class="card-left">
            <t-avatar
              shape="square"
              :size="42"
              :src="(user.user?.avatar ? fsURL + user.user.avatar : defaultAvatar)"
              @error="e => e.target.src = defaultAvatar"
            />
            <div class="user-detail">
              <div class="user-name-line">
                <span class="user-name">{{ user.user?.ploName || '--' }}</span>
                <span class="user-num">{{ user.user?.ploNum || '--' }}</span>
              </div>
              <div class="user-dept">
                {{ user.user?.deptName || '' }}<template v-if="user.user?.groupName"> / {{ user.user.groupName }}</template>
              </div>
              <div class="user-ext" v-if="user.station">
                EXT:{{ user.station.extnNum }} {{ user.station.floorNum }}楼
              </div>
            </div>
          </div>
          <div class="card-right" v-if="user.unfinishCase">
            <t-tag
              :theme="seekerStatusType(user)"
              size="small"
              effect="dark"
              round
            >
              {{ seekerStatusText(user) }}
            </t-tag>
            <span class="wait-time" v-if="hasWaitTime(user)">
              {{ caseTypeName(user.unfinishCase.caseType) }} {{ formatWaitTime(user.unfinishCase.waitTime) }}
            </span>
          </div>
        </div>
      </div>
    </div>

    <t-empty v-if="!ohtStore.ohtUserList.length" description="暂无在线用户" :image-size="60" />
  </t-card>
</template>

<script setup>
import { computed, ref, onMounted, onUnmounted } from 'vue'
import { UserCircleIcon, UserIcon, ErrorCircleFilledIcon } from 'tdesign-icons-vue-next'
import { useOhtStore, useDictStore } from '@/stores'

const ohtStore = useOhtStore()
const dictStore = useDictStore()

const fsURL = import.meta.env.VITE_FILE_MANAGE_BASE
const defaultAvatar = new URL('@/assets/img/default_avatar.png', import.meta.url).href

const now = ref(Date.now())
let timer = null

onMounted(() => {
  timer = setInterval(() => { now.value = Date.now() }, 30000)
})
onUnmounted(() => clearInterval(timer))

// ---- helpers ----

/** 获取用户角色类型：0=求助组 1=接单组，优先从 ohtRole 对象读取 */
function getRoleType(u) {
  return u.ohtRole?.roleType ?? u.roleType
}

/** 案件是否处于等待接单状态 */
function isWaiting(ucase) {
  return ucase && ucase.caseStatus === 0
}

/** 案件是否处于处理中状态 */
function isProcessing(ucase) {
  return ucase && ucase.caseStatus === 2
}

// ---- computed lists ----

const sortedUsers = computed(() => {
  const list = [...ohtStore.ohtUserList]
  const responders = []
  const seekers = []

  for (const u of list) {
    if (getRoleType(u) === 0) {
      seekers.push(u)
    } else {
      responders.push(u)
    }
  }

  // 接单组排序：可接单 → 处理中 → 不可接单
  responders.sort((a, b) => {
    const rank = (u) => {
      if (u.unfinishCase) return 2          // 处理中
      if (u.ohtStatus === 1) return 1       // 可接单
      return 3                               // 不可接单
    }
    return rank(a) - rank(b)
  })

  // 求助组排序：有活跃案件优先，按等待时间降序；无案件排后面
  seekers.sort((a, b) => {
    const aActive = a.unfinishCase ? 1 : 0
    const bActive = b.unfinishCase ? 1 : 0
    if (aActive !== bActive) return bActive - aActive
    if (a.unfinishCase && b.unfinishCase) {
      return (b.unfinishCase.waitTime || 0) - (a.unfinishCase.waitTime || 0)
    }
    return 0
  })

  return { responders, seekers }
})

const responders = computed(() => sortedUsers.value.responders)
const seekers = computed(() => sortedUsers.value.seekers)

// ---- 接单组 display ----

function responderStatusText(user) {
  if (user.unfinishCase) return '求助处理中'
  return user.ohtStatus === 1 ? '可接单' : '不可接单'
}

function responderStatusType(user) {
  if (user.unfinishCase) return 'danger'
  return user.ohtStatus === 1 ? 'success' : 'info'
}

function responderCardClass(user) {
  if (user.unfinishCase) return 'busy'
  return user.ohtStatus === 1 ? 'available' : 'unavailable'
}

// ---- 求助组 display ----

function seekerStatusText(user) {
  if (!user.unfinishCase) return ''
  if (isProcessing(user.unfinishCase)) return '求助处理中'
  return '求助中'
}

function seekerStatusType(user) {
  if (isProcessing(user.unfinishCase)) return 'danger'
  return 'warning'
}

function seekerCardClass(user) {
  if (user.unfinishCase) return 'seeker-active'
  return 'seeker-idle'
}

function hasWaitTime(user) {
  return isWaiting(user.unfinishCase)
}

function caseTypeName(caseType) {
  if (caseType == null) return ''
  return dictStore.getDictName('oht_case_type', caseType)
}

function formatWaitTime(waitTime) {
  if (!waitTime) return ''
  const seconds = Math.floor(now.value / 1000) - waitTime
  if (seconds < 0) return ''
  if (seconds < 60) return '刚刚求助'
  const mins = Math.floor(seconds / 60)
  if (mins < 60) return `等待 ${mins} 分钟`
  const hours = Math.floor(mins / 60)
  if (hours < 24) return `等待 ${hours} 小时 ${mins % 60} 分`
  const days = Math.floor(hours / 24)
  return `等待 ${days} 天+`
}
</script>

<style lang="less" scoped>
// ========== 浅色主题变量 ==========
// 接单组
@bg-available:   #f2faf0;
@border-available: #b3e19d;
@bg-busy:        #fef3f3;
@border-busy:    #fab6b6;
@bg-unavailable: #f7f8fa;
@border-unavailable: #dcdfe6;

// 求助组
@bg-seeker-active: #fef7ed;
@border-seeker-active: #f0c060;
@bg-seeker-idle:  #faf9f7;
@border-seeker-idle: #e8e0d5;

// ========== 卡片容器 ==========
.user-list-card {
  flex: 1;
  border-radius: 12px;
  display: flex;
  flex-direction: column;
  overflow: hidden;

  :deep(.t-card__header) {
    padding: 12px 16px;
    border-bottom: 1px solid var(--td-border-color-lighter);
  }
  :deep(.t-card__body) {
    flex: 1;
    overflow-y: auto;
    padding: 0;
  }
}

.card-header {
  display: flex;
  align-items: center;
  gap: 8px;
  font-weight: 600;
  font-size: 15px;
  color: var(--td-text-color-primary);
}

// ========== 分组区域 ==========
.section {
  &:not(:last-child) {
    border-bottom: 1px solid var(--td-border-color-lighter);
  }
}

.section-header {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 10px 16px 6px;
  font-size: 13px;
  font-weight: 600;
  color: var(--td-text-color-secondary);
  position: sticky;
  top: 0;
background: var(--td-bg-color);
  z-index: 1;
}

.user-cards {
  padding: 4px 12px 8px;
}

// ========== 卡片基础 ==========
.user-card {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 8px 10px;
  margin-bottom: 4px;
  border-radius: 10px;
  border-left: 3px solid transparent;
background: var(--td-bg-color);
  transition: 0.2s, transform 0.15s;

  &:hover {
    transform: translateX(2px);
  }

  // ---- 接单组 ----
  &.available {
    background: @bg-available;
    border-left-color: @border-available;
    &:hover { background: #eaf7e5; }
  }
  &.busy {
    background: @bg-busy;
    border-left-color: @border-busy;
    &:hover { background: #fde8e8; }
  }
  &.unavailable {
    background: @bg-unavailable;
    border-left-color: @border-unavailable;
    &:hover { background: #eff1f4; }
  }

  // ---- 求助组 ----
  &.seeker-active {
    background: @bg-seeker-active;
    border-left-color: @border-seeker-active;
    &:hover { background: #fef1dd; }
  }
  &.seeker-idle {
    background: @bg-seeker-idle;
    border-left-color: @border-seeker-idle;
    &:hover { background: #f5f2ed; }
  }
}

// ========== 卡片内部布局 ==========
.card-left {
  display: flex;
  align-items: center;
  gap: 10px;
  min-width: 0;
  flex: 1;
}

.user-detail {
  min-width: 0;
  line-height: 1.4;
}

.user-name-line {
  display: flex;
  align-items: baseline;
  gap: 6px;
  margin-bottom: 1px;
}

.user-name {
  font-size: 13px;
  font-weight: 600;
  color: var(--td-text-color-primary);
  white-space: nowrap;
}

.user-num {
  font-size: 11px;
  color: var(--td-text-color-secondary);
  font-family: 'Courier New', monospace;
}

.user-dept {
  font-size: 11px;
  color: var(--td-text-color-secondary);
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.user-ext {
  font-size: 11px;
  color: var(--td-text-color-placeholder);
}

// ========== 右侧状态区 ==========
.card-right {
  flex-shrink: 0;
  margin-left: 8px;
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 2px;
}

.status-sub {
  font-size: 10px;
  color: var(--td-text-color-secondary);
  white-space: nowrap;
  max-width: 72px;
  overflow: hidden;
  text-overflow: ellipsis;
}

.wait-time {
  font-size: 10px;
  color: #e6a23c;
  white-space: nowrap;
}

// ========== 深色主题 ==========
html.dark {
  .user-card {
    &.available {
background: rgba(103, 194, 58, 0.08);
      border-left-color: rgba(103, 194, 58, 0.45);
      &:hover { background: rgba(103, 194, 58, 0.12); }
    }
    &.busy {
      background: rgba(245, 108, 108, 0.08);
      border-left-color: rgba(245, 108, 108, 0.45);
      &:hover { background: rgba(245, 108, 108, 0.12); }
    }
    &.unavailable {
      background: rgba(144, 147, 153, 0.06);
      border-left-color: rgba(144, 147, 153, 0.3);
      &:hover { background: rgba(144, 147, 153, 0.1); }
    }
    &.seeker-active {
      background: rgba(230, 162, 60, 0.08);
      border-left-color: rgba(230, 162, 60, 0.45);
      &:hover { background: rgba(230, 162, 60, 0.12); }
    }
    &.seeker-idle {
      background: rgba(180, 170, 155, 0.05);
      border-left-color: rgba(180, 170, 155, 0.25);
      &:hover { background: rgba(180, 170, 155, 0.09); }
    }
  }

  .wait-time {
    color: #f0b860;
  }
}
</style>
