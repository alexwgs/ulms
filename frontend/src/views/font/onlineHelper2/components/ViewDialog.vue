<template>
  <div>
    <t-dialog
      v-model:visible="articleDialogVisible"
      width="80%"
      :before-close="handleClose"
      top="8vh"
      :close-on-overlay-click="false"
    >
      <div v-if="article" class="main-content">
        <div class="title">{{ article.title }}</div>
        <div style="padding-top: 10px">
          标签：
          <span v-for="item in keyword" :key="item" style="padding-left: 5px">
            <t-tag variant="light" size="small">
              {{ item }}
            </t-tag>
          </span>
          <div class="article-icon">
            <span style="color: var(--td-text-color-placeholder); font-size: 14px; padding-right: 10px"
              >发布人：{{ article.insertPlo }} &emsp;|&emsp;发布时间：{{
                article.insertDate
              }}</span
            >
          </div>
        </div>
        <div class="article-text" v-html="content"></div>
        <p
          v-if="article.hasOwnProperty('files') && article.files != null"
          style="margin-bottom: 0"
        >
          附件下载：<t-button
            size="small"
            v-for="(file, index) in JSON.parse(article.files)"
            :key="index"
            @click="downloadFile(file.path)"
            theme="primary"
            variant="outline"
            >{{ file.name }}</t-button
          >
        </p>
      </div>
      <template #footer>
        <div class="dialog-footer">
          <t-input-adornment style="width: 50%; float: left">
            <template #prepend>关键词高亮</template>
            <t-input v-model.trim="searchWord" size="small" placeholder="快速搜索" :clearable="true"></t-input>
          </t-input-adornment>
          <t-button size="small" @click="articleDialogVisible = false"
            >关闭</t-button
          >
        </div>
      </template>
    </t-dialog>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { articleApi } from '@/api/helper/article'

const fsURL = import.meta.env.VITE_FILE_BASE_URL || 'http://localhost:8080'

const articleDialogVisible = ref(false)
const keyword = ref([])
const article = ref(null)
const searchWord = ref(null)
const content = ref('')

const init = async (journo, searchWordParam) => {
  try {
    const res = await articleApi.getArticleById(journo)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    article.value = res.data
    keyword.value = res.data.keyWord.split('|')
    content.value = article.value.content
    searchWord.value = searchWordParam
    articleDialogVisible.value = true
  } catch (error) {
    MessagePlugin.error(error.message || '获取文章失败')
  }
}

const handleClose = () => {
  // TDesign 的 @before-close 不提供 done 回调，直接修改 v-model:visible
  articleDialogVisible.value = false
}

const downloadFile = (path) => {
  window.open(fsURL + path)
}

function escapeHtml(str) {
  const div = document.createElement('div')
  div.appendChild(document.createTextNode(str))
  return div.innerHTML
}

function escapeRegExp(str) {
  return str.replace(/[.*+?^${}()|[\]\\]/g, '\\$&')
}

watch(searchWord, (newSearchWord) => {
  if (article.value && article.value.content) {
    if (newSearchWord && newSearchWord.length > 0) {
      const escaped = escapeHtml(newSearchWord)
      const Reg = new RegExp(escapeRegExp(escaped), 'g')
      content.value = article.value.content.replace(
        Reg,
        `<span style="color: #ff5134;background-color: #e0e400;font-weight: 600;">${escaped}</span>`
      )
    } else {
      content.value = article.value.content
    }
  }
})

defineExpose({
  init
})
</script>

<style lang="less" scoped>
:deep(.t-dialog__body) {
  padding: 10px 20px;
}
.main-content {
  .title {
    font-size: 18px;
    text-align: left;
    margin-top: -20px;
    width: 100%;
  }
  .article-text {
    height: calc(100vh - 400px);
    width: 100%;
    margin-top: 10px;
    float: center;
    overflow-y: auto;
    overflow-x: hidden;
  }
}
.link {
  text-decoration: none;
  color: chocolate;
}
.article-icon {
  float: right;
  i {
    margin-right: 20px;
    font-size: 16px;
    font {
      color: var(--td-text-color-secondary);
    }
  }
}
.dialog-footer {
  margin-top: -20px;
}
</style>
