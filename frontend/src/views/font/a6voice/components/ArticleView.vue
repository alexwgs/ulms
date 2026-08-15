<template>
  <div class="article-card">
    <div class="article-header">
      <div class="article-tags">
        <t-tag size="small" :theme="articleTypeTag" variant="light">{{
          articleTypeText
        }}</t-tag>
        <t-tag
          v-if="articleitem.articleType === 1"
          size="small"
          theme="warning"
          variant="light"
          >{{ itemCategoryText }}</t-tag
        >
        <t-tag
          v-else-if="articleitem.articleType !== 1"
          size="small"
          theme="primary"
          variant="light"
          >{{ articleCategoryText }}</t-tag
        >
      </div>
      <div class="article-badges">
        <t-tag
          v-if="articleitem.topFlag === 1"
          size="small"
          theme="success"
          variant="light"
          >置顶</t-tag
        >
        <t-tag
          v-if="articleitem.eliteFlag === 1"
          size="small"
          theme="danger"
          variant="light"
          >精华</t-tag
        >
      </div>
    </div>
    <div class="article-title">
      <router-link class="link" target="_blank" :to="articleRoute">{{
        articleitem.title
      }}</router-link>
    </div>
    <div class="article-meta">
      <span class="author">{{
        articleitem.user
          ? articleitem.user.ploName + ' / ' + articleitem.user.ploNum
          : '匿名'
      }}</span>
      <span class="dot">·</span>
      <span class="time">{{ timeAgo }}</span>
      <span class="stats">
        <span class="stat"
          ><DynamicIcon name="view-list" /> {{ articleitem.viewNum }}
          浏览</span
        >
        <span class="stat"
          ><DynamicIcon name="chat-round" /> {{ articleitem.replyNum }}
          评论</span
        >
        <span class="stat"
          ><DynamicIcon name="thumb-up" /> {{ articleitem.likeNum }}</span
        >
        <span class="stat"
          ><DynamicIcon name="star-on" /> {{ articleitem.collectNum }}
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
  articleitem: {
    type: Object,
    default: null
  }
})

const articleRoute = computed(() => {
  const typeMap = {
    1: 'item',
    2: 'view',
    3: 'survey'
  }
  const type = typeMap[props.articleitem?.articleType] || 'view'
  return `/article/${type}/${props.articleitem?.id}`
})

const itemCategoryText = computed(() => {
  return dictStore.getDictName('cyt_item_category', props.articleitem?.category)
})

const articleCategoryText = computed(() => {
  return dictStore.getDictName(
    'cyt_artical_category',
    props.articleitem?.category
  )
})

const articleTypeTag = computed(() => {
  if (props.articleitem?.articleType === 1) return 'warning'
  if (props.articleitem?.articleType === 3) return 'danger'
  return 'primary'
})

const articleTypeText = computed(() => {
  return dictStore.getDictName(
    'cyt_artical_type',
    props.articleitem?.articleType
  )
})

const timeAgo = computed(() => {
  if (!props.articleitem?.pubDate) return ''
  return dayjs(props.articleitem.pubDate).fromNow()
})
</script>

<style lang="less" scoped>
.article-card {
  background: var(--td-bg-color-container);
  border: 1px solid var(--td-component-stroke-color);
  border-radius: 8px;
  padding: 14px 18px;
  margin-bottom: 12px;
  transition: box-shadow 0.2s ease;
}

.article-card:hover {
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
}

.article-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 8px;
}

.article-tags {
  display: flex;
  align-items: center;
  gap: 6px;
}

.article-title {
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

.article-meta {
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
