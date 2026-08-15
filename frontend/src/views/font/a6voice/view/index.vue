<template>
  <div class="artical-view-container">
    <div class="main-container">
      <t-row :gutter="20">
        <t-col :span="8">
          <div class="artical-content">
            <t-breadcrumb separator="/">
              <t-breadcrumb-item :to="{ path: '/a6voice' }">A6有声</t-breadcrumb-item>
              <t-breadcrumb-item>{{ typeName }}</t-breadcrumb-item>
              <t-breadcrumb-item>{{ artical.title }}</t-breadcrumb-item>
            </t-breadcrumb>

            <h2 class="artical-title">{{ artical.title }}</h2>

            <div class="artical-meta" style="
                display: flex;
                align-items: center;
                gap: 12px;
                font-size: 14px;
              ">
              <div style="display: flex; gap: 4px; align-items: center">
                <t-tag v-for="tag in activeTags" :key="tag.label" :theme="tag.type" variant="light" size="small">
                  {{ tag.label }}
                </t-tag>
              </div>
              <span style="color: var(--td-text-color-placeholder)">发布人：{{
                artical.user ? artical.user.ploName : '匿名'
              }}</span>
              <span style="color: var(--td-text-color-placeholder)">发布时间：{{ artical.pubDate }}</span>
              <div style="
                  margin-left: auto;
                  display: flex;
                  gap: 12px;
                  color: var(--td-text-color-secondary);
                ">
                <span><t-icon>
                    <View />
                  </t-icon>
                  {{ artical.viewNum + 1 }} 次</span>
                <span><t-icon>
                    <StarFilled />
                  </t-icon>
                  {{ artical.collectNum }}</span>
                <span><t-icon>
                    <Pointer />
                  </t-icon> {{ artical.likeNum }}</span>
                <span><t-icon>
                    <ChatSquare />
                  </t-icon>
                  {{ artical.replyNum }}</span>
              </div>
            </div>

            <t-divider></t-divider>

            <div v-if="articalType === 'item'" class="item-members">
              <div v-for="member in members" :key="member.id" class="member-card">
                <t-avatar :src="fsURL + member.user.avatar" size="50px"></t-avatar>
                <div class="member-info">
                  <span class="name">{{ member.user.ploName }}</span>
                  <span class="role">{{ member.roleName }}</span>
                </div>
              </div>
            </div>

            <div class="artical-body" v-html="artical.content"></div>

            <div v-if="artical.files" class="attachments">
              <div class="attach-title">附件下载：</div>
              <t-button v-for="(file, index) in parseFiles(artical.files)" :key="index" size="small" theme="primary"
                variant="outline" @click="downloadFile(file)">
                {{ file.name }}
              </t-button>
            </div>

            <div class="artical-actions">
              <t-button :theme="isLike ? 'primary' : 'default'" :disabled="isLike === 1" @click="handleLike">
                <i class="iconfont iconzan1"></i> 点赞 {{ artical.likeNum }}
              </t-button>
              <t-button :theme="isCollect ? 'primary' : 'default'" @click="handleCollect">
                <i class="iconfont iconshoucang1"></i>
                {{ isCollect ? '已' : '' }}收藏 {{ artical.collectNum }}
              </t-button>
            </div>
          </div>

          <t-card v-if="showComment" class="comment-section">
            <Comment ref="commentRef" :artical-id="articalId" :pub-user="artical.pubUser"
              :show-anon-option="flags.anonFlag" :show-comment-form="flags.commentFlag"
              @comment-submitted="onCommentSubmitted" />
          </t-card>
          <t-card v-else class="comment-closed">
            <span>已关闭评论区</span>
          </t-card>
        </t-col>

        <t-col :span="4">
          <t-card v-if="articalType === 'item'" class="progress-card">
            <template #header>
              <span>项目进度</span>
            </template>
            <t-progress :percentage="progress" :status="progressStatus"></t-progress>
            <div class="progress-info">
              <span>当前阶段：{{ currentStage }}</span>
            </div>
          </t-card>

          <t-card v-if="articalType === 'survey'" class="survey-card">
            <template #header>
              <span>调研信息</span>
            </template>
            <div class="survey-info">
              <div class="info-item">
                <span class="label">参与人数：</span>
                <span class="value">{{ surveyStats.answerCount || 0 }}</span>
              </div>
              <div class="info-item">
                <span class="label">有效问卷：</span>
                <span class="value">{{ surveyStats.validCount || 0 }}</span>
              </div>
            </div>
            <t-button v-if="!hasAnswered" theme="primary" size="small" @click="handleAnswerSurvey">
              参与调研
            </t-button>
            <t-button v-else theme="success" size="small" disabled>
              已参与
            </t-button>
          </t-card>

          <t-card class="related-card">
            <template #header>
              <span>相关推荐</span>
            </template>
            <div class="related-list">
              <div v-for="item in relatedList" :key="item.id" class="related-item"
                @click="goToartical(item.id, item.articalType)">
                <span class="related-title">{{ item.title }}</span>
                <span class="related-num">{{ item.replyNum }} 条评论</span>
              </div>
            </div>
          </t-card>
        </t-col>
      </t-row>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { MessagePlugin } from 'tdesign-vue-next'
import Comment from './components/Comment.vue'
import useDictStore from '@/stores/modules/dict'
import { sanitizeHtml } from '@/utils/tools'
import {
  getArticalDetail,
  toggleLike,
  toggleCollect,
  getItemMembers,
  getItemProgress,
  getSurveyDetail,
  getArticalList
} from '@/api/a6voice/index.js'
const dictStore = useDictStore()
const route = useRoute()
const router = useRouter()

const fsURL = import.meta.env.VITE_FILE_BASE_URL || ''

const articalId = ref(route.params.id)
const articalType = ref(route.params.type || 'view')

const artical = ref({})
const isLike = ref(0)
const isCollect = ref(0)
const members = ref([])
const progress = ref(0)
const currentStage = ref('')
const surveyStats = ref({})
const hasAnswered = ref(false)
const relatedList = ref([])
const commentRef = ref(null)

const typeName = computed(() => {
  const typeMap = {
    view: '讨论',
    item: '课题',
    survey: '调研'
  }
  return typeMap[articalType.value] || '讨论'
})

const articalTypeNum = computed(() => {
  const typeMap = {
    view: 2,
    item: 1,
    survey: 3
  }
  return typeMap[articalType.value] || 2
})

const activeTags = computed(() => {
  const tags = []
  if (artical.value.topFlag === 1) tags.push({ label: '置顶', type: 'success' })
  if (artical.value.eliteFlag === 1)
    tags.push({ label: '精华', type: 'danger' })
  if (artical.value.compType) {
    const compTypeMap = {
      1: { label: '已结案', type: 'success' },
      2: { label: '待结案', type: 'warning' },
      3: { label: '待认领', type: 'info' },
      6: { label: '进行中', type: '' }
    }
    if (compTypeMap[artical.value.compType]) {
      tags.push(compTypeMap[artical.value.compType])
    }
  }
  return tags
})

const progressStatus = computed(() => {
  if (progress.value >= 100) return 'success'
  if (progress.value >= 50) return ''
  return 'exception'
})

const showComment = computed(() => {
  if (!artical.value.category) return true
  const categoryDesc = getCategoryDescription(artical.value.category)
  return categoryDesc?.commentFlag !== false
})

const flags = computed(() => {
  if (!artical.value.category) return {}
  return getCategoryDescription(artical.value.category)
})

const getCategoryDescription = (category) => {
  if (!dictStore.getDictByNames('cyt_artical_category')) return {}
  const item = dictStore.getDictName('cyt_artical_category', category)
  if (item?.description) {
    try {
      return JSON.parse(item.description)
    } catch (e) {
      return {}
    }
  }
  return {}
}

const parseFiles = (filesStr) => {
  try {
    return JSON.parse(filesStr)
  } catch {
    return []
  }
}

const downloadFile = (file) => {
  window.open(fsURL + file.path)
}

const fetchartical = async () => {
  try {
    const res = await getArticalDetail(articalId.value)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    artical.value = res.data
    artical.value.content = sanitizeHtml(res.data.content)
    isLike.value = res.data.isLike || 0
    isCollect.value = res.data.isCollect || 0
    document.title = `[A6有声]${artical.value.title}`

    if (articalType.value === 'item') {
      await fetchItemMembers()
      await fetchItemProgress()
    } else if (articalType.value === 'survey') {
      await fetchSurveyStats()
    }
  } catch (error) {
    console.error('获取文章详情失败', error)
  }
}

const fetchItemMembers = async () => {
  try {
    const res = await getItemMembers(articalId.value)
    if (res.code === 200) {
      members.value = res.data || []
    }
  } catch (error) {
    console.error('获取成员失败', error)
  }
}

const fetchItemProgress = async () => {
  try {
    const res = await getItemProgress(articalId.value)
    if (res.code === 200) {
      progress.value = res.data?.progress || 0
      currentStage.value = res.data?.stageName || ''
    }
  } catch (error) {
    console.error('获取进度失败', error)
  }
}

const fetchSurveyStats = async () => {
  try {
    const res = await getSurveyDetail(articalId.value)
    if (res.code === 200) {
      surveyStats.value = res.data || {}
      hasAnswered.value = res.data?.hasAnswer || false
    }
  } catch (error) {
    console.error('获取调研统计失败', error)
  }
}

const fetchRelatedList = async () => {
  try {
    const res = await getArticalList({
      category: artical.value.category,
      pageSize: 5,
      pageNum: 1
    })
    if (res.code === 200) {
      relatedList.value = (res.data.list || [])
        .filter((item) => item.id !== articalId.value)
        .slice(0, 5)
    }
  } catch (error) {
    console.error('获取推荐列表失败', error)
  }
}

const handleLike = async () => {
  try {
    const res = await toggleLike(1, articalId.value)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    isLike.value = 1
    artical.value.likeNum++
    MessagePlugin.success(res.msg)
  } catch (error) {
    console.error('点赞失败', error)
  }
}

const handleCollect = async () => {
  try {
    const res = await toggleCollect(articalId.value, isCollect.value)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    isCollect.value = isCollect.value === 1 ? 0 : 1
    if (isCollect.value === 1) {
      artical.value.collectNum++
    } else {
      artical.value.collectNum--
    }
    MessagePlugin.success(res.msg)
  } catch (error) {
    console.error('收藏失败', error)
  }
}

const onCommentSubmitted = () => {
  artical.value.replyNum++
}

const handleAnswerSurvey = () => {
  const url = router.resolve({
    path: `/artical/survey/${articalId.value}`,
    query: { answer: 1 }
  })
  window.open(url.href, '_blank')
}

const goToartical = (id, type) => {
  const typeMap = {
    1: 'item',
    2: 'view',
    3: 'survey'
  }
  const routeType = typeMap[type] || 'view'
  const url = router.resolve(`/artical/${routeType}/${id}`)
  window.open(url.href, '_blank')
}

onMounted(() => {
  fetchartical()
  fetchRelatedList()
})
</script>

<style scoped>
.artical-view-container {
  width: 100%;
  min-height: 100vh;
-colorbackground: #fff;
  padding: 20px 0;
}

.main-container {
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 20px;
}

.artical-content {
background: #fff;
  padding: 20px;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.artical-meta {
  .artical-info {
    color: var(--td-text-color-placeholder);
    font-size: 14px;
    margin-bottom: 10px;

    span {
      margin-right: 20px;
    }
  }
}

.artical-body {
  min-height: 300px;
  padding: 20px 0;
  word-wrap: break-word;

  :deep(img) {
    max-width: 100%;
  }
}

.item-members {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  margin-bottom: 20px;

  .member-card {
    display: flex;
    align-items: center;
    gap: 10px;
    padding: 10px;
background: #f5f7fa;
    border-radius: 4px;

    .member-info {
      display: flex;
      flex-direction: column;

      .name {
        font-weight: bold;
      }

      .role {
        font-size: 12px;
        color: var(--td-text-color-placeholder);
      }
    }
  }
}

.attachments {
  margin: 20px 0;

  .attach-title {
    margin-bottom: 10px;
    font-weight: bold;
  }

  .t-button {
    margin-right: 10px;
  }
}

.artical-actions {
  margin-top: 30px;
  text-align: center;

  .t-button {
    margin: 0 10px;
  }
}

.comment-section {
  margin-top: 20px;
}

.comment-closed {
  margin-top: 20px;
  text-align: center;
  color: #888;
  padding: 30px;
}

.progress-card,
.survey-card,
.related-card {
  margin-bottom: 20px;
}

.progress-info {
  margin-top: 15px;
  color: #606266;
}

.survey-info {
  margin-bottom: 15px;

  .info-item {
    display: flex;
    justify-content: space-between;
    padding: 8px 0;
    border-bottom: 1px solid var(--td-component-stroke);

    .label {
      color: var(--td-text-color-placeholder);
    }

    .value {
      font-weight: bold;
    }
  }
}

.related-list {
  .related-item {
    padding: 10px;
    cursor: pointer;
    border-bottom: 1px solid var(--td-component-stroke);
    display: flex;
    justify-content: space-between;
    align-items: center;

    &:hover {
background: #f5f7fa;
    }

    .related-title {
      flex: 1;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }

    .related-num {
      font-size: 12px;
      color: var(--td-text-color-placeholder);
      margin-left: 10px;
    }
  }
}
</style>
