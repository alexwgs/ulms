<template>
  <div class="artical-card">
    <div class="artical-header">
      <div class="artical-tags">
        <t-tag size="small" :theme="articalTypeTag" variant="light">{{
          articalTypeText
        }}</t-tag>
        <t-tag
          v-if="articalitem.articalType === 1"
          size="small"
          theme="warning"
          variant="light"
          >{{ itemCategoryText }}</t-tag
        >
        <t-tag
          v-else-if="articalitem.articalType !== 1"
          size="small"
          theme="primary"
          variant="light"
          >{{ articalCategoryText }}</t-tag
        >
      </div>
      <div class="artical-badges">
        <t-tag
          v-if="articalitem.topFlag === 1"
          size="small"
          theme="success"
          variant="light"
          >置顶</t-tag
        >
        <t-tag
          v-if="articalitem.eliteFlag === 1"
          size="small"
          theme="danger"
          variant="light"
          >精华</t-tag
        >
      </div>
    </div>
    <div class="artical-title">
      <router-link class="link" target="_blank" :to="articalRoute">{{
        articalitem.title
      }}</router-link>
    </div>
    <div class="artical-meta">
      <span class="author">{{
        articalitem.user
          ? articalitem.user.ploName + ' / ' + articalitem.user.ploNum
          : '匿名'
      }}</span>
      <span class="dot">·</span>
      <span class="time">{{ timeAgo }}</span>
      <span class="stats">
        <span class="stat"
          ><DynamicIcon name="view-list" /> {{ articalitem.viewNum }}
          浏览</span
        >
        <span class="stat"
          ><DynamicIcon name="chat-round" /> {{ articalitem.replyNum }}
          评论</span
        >
        <span class="stat"
          ><DynamicIcon name="thumb-up" /> {{ articalitem.likeNum }}</span
        >
        <span class="stat"
          ><DynamicIcon name="star-on" /> {{ articalitem.collectNum }}
          收藏</span
        >
      </span>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import dayjs from 'dayjs'
import relativeTime from 'dayjs/plugin/relativeTime'
import 'dayjs/locale/zh-cn'
import useDictStore from '@/stores/modules/dict'

dayjs.extend(relativeTime)
dayjs.locale('zh-cn')

const dictStore = useDictStore()
const props = defineProps({
  articalitem: {
    type: Object,
    default: null
  }
})

const articalRoute = computed(() => {
  const typeMap = {
    1: 'item',
    2: 'view',
    3: 'survey'
  }
  const type = typeMap[props.articalitem?.articalType] || 'view'
  return `/artical/${type}/${props.articalitem?.id}`
})

const itemCategoryText = computed(() => {
  return dictStore.getDictName('cyt_item_category', props.articalitem?.category)
})

const articalCategoryText = computed(() => {
  return dictStore.getDictName(
    'cyt_artical_category',
    props.articalitem?.category
  )
})

const articalTypeTag = computed(() => {
  if (props.articalitem?.articalType === 1) return 'warning'
  if (props.articalitem?.articalType === 3) return 'danger'
  return 'primary'
})

const articalTypeText = computed(() => {
  return dictStore.getDictName(
    'cyt_artical_type',
    props.articalitem?.articalType
  )
})

const timeAgo = computed(() => {
  if (!props.articalitem?.pubDate) return ''
  return dayjs(props.articalitem.pubDate).fromNow()
})
</script>

<style lang="less" scoped>
.artical-card {
  background: var(--td-bg-color-container);
  border: 1px solid var(--td-component-stroke-color);
  border-radius: 8px;
  padding: 14px 18px;
  margin-bottom: 12px;
  transition: box-shadow 0.2s ease;
}

.artical-card:hover {
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
}

.artical-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 8px;
}

.artical-tags {
  display: flex;
  align-items: center;
  gap: 6px;
}

.artical-title {
  font-size: 17px;
  font-weight: 600;
  line-height: 1.5;

  .link {
    color: var(--td-text-color-primary);
    text-decoration: none;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
  }

  .link:hover {
    color: var(--td-brand-color);
  }
}

.artical-meta {
  display: flex;
  align-items: center;
  gap: 6px;
  margin-top: 10px;
  font-size: 13px;
  color: var(--td-text-color-secondary);

  .dot {
    color: #c2c2c2;
  }

  .author {
    color: var(--td-brand-color);
  }

  .stats {
    margin-left: auto;
    display: flex;
    align-items: center;
    gap: 16px;
  }

  .stat {
    display: inline-flex;
    align-items: center;
    gap: 4px;
  }
}
</style>
