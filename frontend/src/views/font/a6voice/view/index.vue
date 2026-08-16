<template>
  <!-- 详情页分发壳：讨论/调研/课题分别渲染完整组件（含正文、问卷、评论、点赞收藏） -->
  <ViewDetail v-if="articleType === 'view'" />
  <SurveyDetail v-else-if="articleType === 'survey'" />
  <ItemDetail v-else-if="articleType === 'item'" />
</template>

<script setup>
import { ref } from 'vue'
import { useRoute } from 'vue-router'
import ViewDetail from './components/view.vue'
import SurveyDetail from './components/Survey.vue'
import ItemDetail from './components/Item.vue'

const route = useRoute()
const articleType = ref(route.params.type || 'view')
</script>
