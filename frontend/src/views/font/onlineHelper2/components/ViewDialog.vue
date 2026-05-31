<template>
  <div>
    <t-dialog
      v-model:visible="articalDialogVisible"
      width="80%"
      @before-close="handleClose"
      top="8vh"
      :close-on-overlay-click="false"
    >
      <div v-if="artical" class="main-content">
        <div class="title">{{ artical.title }}</div>
        <div style="padding-top: 10px">
          标签：
          <span v-for="item in keyword" :key="item" style="padding-left: 5px">
            <t-tag effect="dark" size="small">
              {{ item }}
            </t-tag>
          </span>
          <div class="artical-icon">
            <span style="color: #909399; font-size: 14px; padding-right: 10px"
              >发布人：{{ artical.insertPlo }} &emsp;|&emsp;发布时间：{{
                artical.insertDate
              }}</span
            >
          </div>
        </div>
        <div class="artical-text" v-html="content"></div>
        <p
          v-if="artical.hasOwnProperty('files') && artical.files != null"
          style="margin-bottom: 0"
        >
          附件下载：<t-button
            size="small"
            v-for="(file, index) in JSON.parse(artical.files)"
            :key="index"
            @click="downloadFile(file.path)"
            theme="primary"
            plain
            >{{ file.name }}</t-button
          >
        </p>
      </div>
      <template #footer>
        <div class="dialog-footer">
          <t-input
            v-model.trim="searchWord"
            size="small"
            style="width: 50%; float: left"
            placeholder="快速搜索"
            :clearable="true"
          >
            <template #prepend>关键词高亮</template>
          </t-input>
          <t-button size="small" @click="articalDialogVisible = false"
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
import { articalApi } from '@/api/helper/artical'

const fsURL = import.meta.env.VITE_FILE_MANAGE_BASE || 'http://localhost:8080'
const global = window.__POWERED_BY_QIANKUN__ ? window.$global : null

const articalDialogVisible = ref(false)
const keyword = ref([])
const artical = ref(null)
const searchWord = ref(null)
const content = ref('')

const init = async (journo, searchWordParam) => {
  try {
    const res = await articalApi.getArticalById(journo)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    artical.value = res.data
    keyword.value = res.data.keyWord.split('|')
    content.value = artical.value.content
    searchWord.value = searchWordParam
    articalDialogVisible.value = true
  } catch (error) {
    MessagePlugin.error(error.message || '获取文章失败')
  }
}

const handleClose = (done) => {
  done()
}

const downloadFile = (path) => {
  if (global && global.downloadFile) {
    global.downloadFile(fsURL + path)
  } else {
    window.open(fsURL + path)
  }
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
  if (artical.value && artical.value.content) {
    if (newSearchWord && newSearchWord.length > 0) {
      const escaped = escapeHtml(newSearchWord)
      const Reg = new RegExp(escapeRegExp(escaped), 'g')
      content.value = artical.value.content.replace(
        Reg,
        `<span style="color: #ff5134;background-color: #e0e400;font-weight: 600;">${escaped}</span>`
      )
    } else {
      content.value = artical.value.content
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
  .artical-text {
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
.artical-icon {
  float: right;
  i {
    margin-right: 20px;
    font-size: 16px;
    font {
      color: #797979;
    }
  }
}
.dialog-footer {
  margin-top: -20px;
}
</style>
