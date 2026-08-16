<template>
  <t-row :gutter="16" class="stats-card-row">
    <t-col :span="3">
      <t-card :bordered="false" class="stat-card stat-card--online">
        <div class="stat-card__body">
          <div class="stat-card__icon">
            <UserIcon size="32px" />
          </div>
          <div class="stat-card__content">
            <div class="stat-card__value">{{ onlineCount }}</div>
            <div class="stat-card__label">实时在线人数</div>
          </div>
        </div>
      </t-card>
    </t-col>
    <t-col :span="3">
      <t-card :bordered="false" class="stat-card stat-card--uv">
        <div class="stat-card__body">
          <div class="stat-card__icon">
            <UsergroupIcon size="32px" />
          </div>
          <div class="stat-card__content">
            <div class="stat-card__value">{{ todayStats.uv ?? 0 }}</div>
            <div class="stat-card__label">今日UV</div>
          </div>
        </div>
      </t-card>
    </t-col>
    <t-col :span="3">
      <t-card :bordered="false" class="stat-card stat-card--pv">
        <div class="stat-card__body">
          <div class="stat-card__icon">
            <ChartLineIcon size="32px" />
          </div>
          <div class="stat-card__content">
            <div class="stat-card__value">{{ todayStats.pv ?? 0 }}</div>
            <div class="stat-card__label">今日PV</div>
          </div>
        </div>
      </t-card>
    </t-col>
    <t-col :span="3">
      <t-card :bordered="false" class="stat-card" :class="{ 'stat-card--danger': todayStats.exceptionCount > 0 }">
        <div class="stat-card__body">
          <div class="stat-card__icon">
            <ErrorCircleIcon size="32px" />
          </div>
          <div class="stat-card__content">
            <div class="stat-card__value">{{ todayStats.exceptionCount ?? 0 }}</div>
            <div class="stat-card__label">今日异常数</div>
          </div>
        </div>
      </t-card>
    </t-col>
  </t-row>
</template>

<script setup>
import { UserIcon, UsergroupIcon, ChartLineIcon, ErrorCircleIcon } from 'tdesign-icons-vue-next'

defineProps({
  todayStats: {
    type: Object,
    default: () => ({ uv: 0, pv: 0, exceptionCount: 0 })
  },
  onlineCount: {
    type: Number,
    default: 0
  }
})
</script>

<style lang="less" scoped>
.stats-card-row {
  margin-bottom: 16px;
}

.stat-card {
  transition: transform 0.2s ease;

  &:hover {
    transform: translateY(-2px);
  }

  &__body {
    display: flex;
    align-items: center;
    gap: 16px;
  }

  &__icon {
    width: 56px;
    height: 56px;
    border-radius: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-shrink: 0;
  }

  &__content {
    flex: 1;
    min-width: 0;
  }

  &__value {
    font-size: 28px;
    font-weight: 700;
    line-height: 1.2;
  }

  &__label {
    font-size: 13px;
    color: var(--td-text-color-secondary);
    margin-top: 4px;
  }
}

.stat-card--online .stat-card__icon {
  background: rgba(0, 82, 217, 0.1);
  color: #0052d9;
}

.stat-card--uv .stat-card__icon {
  background: rgba(0, 168, 112, 0.1);
  color: #00a870;
}

.stat-card--pv .stat-card__icon {
  background: rgba(237, 123, 47, 0.1);
  color: #ed7b2f;
}

.stat-card--danger .stat-card__icon {
  background: rgba(245, 108, 108, 0.1);
  color: var(--td-error-color);
}

.stat-card--danger .stat-card__value {
  color: var(--td-error-color);
}
</style>
