<template>
  <div class="helper-article-container">
    <div class="main-container">
      <t-card v-if="article" class="article-card">
        <div class="article-head">
          <div class="article-title">{{ article.title }}</div>
          <div class="article-meta">
            <span>
              <t-tag
                v-for="item in keyword"
                :key="item"
                variant="light"
                size="small"
                style="margin-right: 6px"
              >
                {{ item }}
              </t-tag>
            </span>
            <span class="publisher">发布人：{{ article.insertPlo }} &emsp;|&emsp;发布时间：{{ article.insertDate }}</span>
          </div>
        </div>
        <t-divider />
        <SafeHtml class="article-text" :html="article.content || ''" />
        <div
          v-if="article.hasOwnProperty('files') && article.files != null"
          class="attachments"
        >
          <span class="attach-title">附件下载：</span>
          <t-button
            size="small"
            theme="primary"
            variant="outline"
            v-for="(file, index) in parseFiles(article.files)"
            :key="index"
            @click="downloadFile(file.path)"
            style="margin-right: 8px"
          >
            {{ file.name }}
          </t-button>
        </div>
      </t-card>
      <t-card v-else>
        <t-empty description="文章不存在或已删除"></t-empty>
      </t-card>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { MessagePlugin } from 'tdesign-vue-next'
import { articleApi } from '@/api/helper/article'

const route = useRoute()
const article = ref(null)
const keyword = ref([])

const fsURL = import.meta.env.VITE_FILE_BASE_URL || 'http://localhost:8080'

const parseFiles = (filesStr) => {
  try {
    return JSON.parse(filesStr)
  } catch {
    return []
  }
}

const downloadFile = (path) => {
  window.open(fsURL + path)
}

const fetchArticle = async () => {
  try {
    const res = await articleApi.getArticleById(route.params.journo)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    article.value = res.data
    keyword.value = res.data.keyWord ? res.data.keyWord.split('|') : []
    document.title = `[值机助手]${res.data.title || ''}`
  } catch (error) {
    console.error('获取文章失败', error)
    MessagePlugin.error('获取文章失败')
  }
}

onMounted(() => {
  fetchArticle()
})
</script>

<style scoped>
.helper-article-container {
  width: 100%;
  min-height: 100vh;
  background: #fff;
  padding: 20px 0;
}

.main-container {
  max-width: 1100px;
  margin: 0 auto;
  padding: 0 20px;
}

.article-card {
  border-radius: 4px;
}

.article-head {
  .article-title {
    font-size: 22px;
    font-weight: 600;
    color: var(--td-text-color-primary);
    line-height: 1.5;
  }

  .article-meta {
    margin-top: 12px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    flex-wrap: wrap;
    gap: 8px;

    .publisher {
      font-size: 13px;
      color: var(--td-text-color-placeholder);
    }
  }
}

.article-text {
  min-height: 300px;
  padding: 10px 0;
  word-wrap: break-word;

  :deep(img) {
    max-width: 100%;
  }
}

.attachments {
  margin-top: 20px;
  padding-top: 15px;
  border-top: 1px solid var(--td-component-stroke);

  .attach-title {
    font-weight: bold;
    margin-right: 8px;
  }
}
</style>
