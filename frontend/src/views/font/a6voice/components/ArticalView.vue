<template>
  <t-card shadow="hover" :body-style="{ padding: '10px' }">
    <t-row>
      <t-col :span="9">
        <div class="artical-title">
          <span style="font-weight: 550; font-size: 14px" v-if="articalitem.articalType === 1">[{{ itemCategoryText
            }}]</span>
          <span style="font-weight: 550; font-size: 14px" v-else>[{{ articalCategoryText }}]</span>
          <router-link class="link" target="_blank" :to="articalRoute">{{
            articalitem.title
            }}</router-link>
        </div>
      </t-col>
      <t-col :span="2">
        <div class="elite-badage">
          <t-tag v-if="articalitem.topFlag === 1" size="small" theme="success" effect="dark">置顶</t-tag>
          <t-tag v-if="articalitem.eliteFlag === 1" size="small" theme="danger" effect="dark">精华</t-tag>
        </div>
      </t-col>
    </t-row>
    <t-row>
      <t-col :span="12" style="max-height: 30px; font-size: 12px; padding: 5px">
      </t-col>
    </t-row>
    <div class="artical-list-info">
      <t-tag size="small" :theme="articalTypeTag" effect="plain">{{
        articalTypeText
        }}</t-tag>
      <span v-if="articalitem.user">
        {{ articalitem.user.ploName }}/{{ articalitem.user.ploNum }}
      </span>
      <span v-else>匿名</span>
      <span>{{ timeAgo }}</span>
      <span class="icon-list"><i class="iconfont iconfaxian">{{ articalitem.viewNum }} 次查看</i>&nbsp;
        <i class="iconfont iconxiaoxi">{{ articalitem.replyNum }} 条评论</i>&nbsp;
        <i >{{ articalitem.likeNum + articalitem.collectNum }} 个点赞收藏</i>
      </span>
    </div>
  </t-card>
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
.link {
  text-decoration: none;
  color: var(--td-font-color);
}

.artical-title {
  padding-left: 10px;
  font-size: 16px;
  padding-top: 5px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;

  .link {
    display: inline-block;
    min-width: 430px;
  }
}

.artical-list-info {
  font-size: 12px;
  color: #666;
  padding-left: 10px;
  padding-right: 10px;
  position: relative;

  i {
    font-size: 12px;
    padding-left: 5px;
  }

  span {
    padding-left: 5px;
  }

  .icon-list {
    position: absolute;
    right: 0;
    top: 0;
  }
}

.elite-badage {
  position: absolute;
  right: 15px;
  top: 5px;
  font-size: 0;
}
</style>
