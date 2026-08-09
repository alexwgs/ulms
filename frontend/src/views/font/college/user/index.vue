<template>
  <t-row style="margin-top: 10px">
    <t-col :span="12">
      <t-card class="academy-card">
        <h3 class="academy-section-title">月度学霸排行榜</h3>
        <div class="rank-list">
          <div class="rank-item" v-for="(item, index) in userList" :key="item.ploNum">
            <span class="rank-no" :class="{ top: index < 3 }">{{ index + 1 }}</span>
            <t-avatar
              size="36px"
              shape="round"
              :image="item.user.avatar ? fsURL + item.user.avatar : ''"
            >
              {{ item.user.ploName ? item.user.ploName[0] : '' }}
            </t-avatar>
            <div class="rank-info">
              <span class="rank-name">{{ item.user.ploName }}</span>
              <span class="rank-dept">部门：{{ item.user.deptName }}</span>
            </div>
            <span class="rank-point">学分 {{ item.point }}</span>
          </div>
        </div>
      </t-card>
    </t-col>
  </t-row>
</template>

<script setup>
const props = defineProps({
  userList: {
    type: Array,
    default: () => []
  }
})

// 展示类文件统一走文件服务地址
const fsURL = import.meta.env.VITE_FILE_BASE_URL
</script>

<style lang="less" scoped>
.rank-list {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 10px 24px;
}

.rank-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 10px 14px;
  border: 1px solid var(--academy-line);
  border-radius: 10px;
  transition: box-shadow 0.2s ease;

  &:hover {
    box-shadow: var(--academy-shadow-sm);
  }
}

.rank-no {
  flex: none;
  width: 26px;
  height: 26px;
  border-radius: 50%;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  font-size: 13px;
  font-weight: 600;
  color: var(--academy-muted);
  background: var(--academy-bg);

  &.top {
    color: #fff;
    background: linear-gradient(135deg, var(--academy-gold-2), var(--academy-gold));
  }
}

.rank-info {
  flex: 1;
  min-width: 0;
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.rank-name {
  font-size: 14px;
  font-weight: 600;
  color: var(--academy-ink);
}

.rank-dept {
  font-size: 12px;
  color: var(--academy-muted);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.rank-point {
  flex: none;
  font-size: 14px;
  font-weight: 700;
  color: var(--academy-gold);
}
</style>
