<template>
  <div class="view-container">
    <div class="mian-container">
      <t-row>
        <div class="article-content">
          <t-breadcrumb separator="/">
            <t-breadcrumb-item :to="{ path: '/' }">A6有声</t-breadcrumb-item>
            <t-breadcrumb-item>讨论</t-breadcrumb-item>
            <t-breadcrumb-item>{{ categoryName }}</t-breadcrumb-item>
            <t-breadcrumb-item>{{ article.title }}</t-breadcrumb-item>
          </t-breadcrumb>
          <h3>{{ article.title }}</h3>
          <div>
            标签：
            <span v-for="item in labelItems" :key="item.label">
              <t-tag
                v-if="article[item.field] == item.val"
                :theme="item.type"
                variant="light"
                size="small"
              >
                {{ item.label }}
              </t-tag>
            </span>
            <div class="article-icon">
              <span style="color: var(--td-text-color-placeholder); font-size: 14px; padding-right: 10px"
                >发布人：{{
                  article.user ? article.user.ploName : '匿名'
                }}
                &emsp;|&emsp;发布时间：{{ article.pubDate }}</span
              >
              <i class="iconfont iconfaxian"
                ><font>{{ article.viewNum + 1 }}</font></i
              >
              <i class="iconfont iconshoucang1"
                ><font>{{ article.collectNum }}</font></i
              >
              <i class="iconfont iconzan1"
                ><font>{{ article.likeNum }}</font></i
              >
              <i class="iconfont iconxiaoxi"
                ><font>{{ article.replyNum }}</font></i
              >
            </div>
          </div>
          <t-divider></t-divider>
          <SafeHtml class="article-text" :html="article.content" />
          <t-divider></t-divider>
          <p v-if="article.hasOwnProperty('files') && article.files">
            附件下载：<t-button
              v-for="(file, index) in parseFiles(article.files)"
              :key="index"
              size="small"
              @click="downloadFile(file.path)"
              theme="primary"
              variant="outline"
              >{{ file.name }}</t-button
            >
          </p>
          <div class="article-operations">
            <t-button
              size="small"
              :theme="isLike == 0 ? 'default' : 'primary'"
              :disabled="isLike == 1 ? true : false"
              @click="setLike(1, article.id)"
              shape="round"
            >
              <template #icon><i class="iconfont iconzan1"></i></template>
              &emsp;点 赞&emsp;{{ article.likeNum }}
            </t-button>
            <t-button
              size="small"
              :theme="isCollect == 0 ? 'default' : 'primary'"
              @click="setCollect"
              shape="round"
            >
              <template #icon><i class="iconfont iconshoucang1"></i></template>
              &emsp;{{ article.isCollect == 0 ? '' : '已' }} 收 藏&emsp;{{
                article.collectNum
              }}
            </t-button>
          </div>
        </div>
      </t-row>
      <t-row>
        <t-card class="comment" v-if="flags.commentFlag">
          <Comment
            ref="commentRef"
            :article-id="id"
            :pub-user="article.pubUser"
            :show-anon-option="flags.anonFlag"
            :show-comment-form="true"
            @comment-submitted="onCommentSubmitted"
          />
        </t-card>
        <t-card v-else>
          <center style="color: #888">已关闭评论区</center>
        </t-card>
      </t-row>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { MessagePlugin } from 'tdesign-vue-next'
import Comment from './Comment.vue'
import { httpInstance } from '@/utils/request'
import useDictStore from '@/stores/modules/dict'
const route = useRoute()
const fsURL = import.meta.env.VITE_FILE_BASE_URL
const dictStore = useDictStore()
const commentRef = ref(null)
const article = reactive({
  title: '',
  category: '',
  content: '',
  user: null,
  pubDate: '',
  viewNum: 0,
  collectNum: 0,
  likeNum: 0,
  replyNum: 0,
  isCollect: 0,
  files: null
})
const isLike = ref(0)
const isCollect = ref(0)

const labelItems = [
  { type: '', label: '置顶', field: 'topFlag', val: 1 },
  { type: 'danger', label: '精华', field: 'eliteFlag', val: 1 },
  { type: 'success', label: '已结案', field: 'compType', val: 1 },
  { type: 'warning', label: '待结案', field: 'compType', val: 2 },
  { type: 'info', label: '待认领', field: 'compType', val: 3 }
]

const categorys = ref([])

const id = computed(() => route.params.id)

const categoryName = computed(() => {
  if (categorys.value.length > 0 && article.category) {
    const item = categorys.value.find(
      (item) => parseInt(item.code) === parseInt(article.category)
    )
    return item ? item.codeval : ''
  }
  return ''
})

const flags = computed(() => {
  if (categorys.value.length > 0 && article.category) {
    const item = categorys.value.find(
      (item) => parseInt(item.code) === parseInt(article.category)
    )
    if (item && item.description) {
      return JSON.parse(item.description)
    }
  }
  return { commentFlag: true, anonFlag: false }
})

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

const getArticle = async () => {
  try {
    const res = await httpInstance.get(`cyt/article/${id.value}`)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    Object.assign(article, res.data)
    isCollect.value = res.data.isCollect
    isLike.value = res.data.isLike
    document.title = '[A6有声]' + article.title
  } catch (error) {
    MessagePlugin.error(error.message || '获取文章失败')
  }
}

const setCollect = async () => {
  try {
    const res = await httpInstance.post(
      `cyt/collect/${id.value}/${isCollect.value}`
    )
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    if (isCollect.value === 0) {
      isCollect.value = 1
      article.collectNum++
    } else {
      isCollect.value = 0
      article.collectNum--
    }
    MessagePlugin.success(res.msg)
  } catch (error) {
    MessagePlugin.error(error.message || '操作失败')
  }
}

const setLike = async (type, targetId, index) => {
  try {
    const res = await httpInstance.post(`cyt/like/${type}/${targetId}`)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    if (type === 1) {
      isLike.value = 1
      article.likeNum++
    }
    MessagePlugin.success(res.msg)
  } catch (error) {
    MessagePlugin.error(error.message || '操作失败')
  }
}

const onCommentSubmitted = () => {
  article.replyNum++
}

onMounted(() => {
  const dict = JSON.parse(window.localStorage.getItem('dictCache') || '{}')
  categorys.value = dictStore.getDict('cyt_artical_category') || []
  getArticle()
})
</script>

<style lang="less" scoped>
.view-container {
  width: 100%;
background-color: var(--td-bg-color-page);
  min-height: 100%;
  .mian-container {
    margin: 0 auto;
    max-width: 1400px;
  }
}
.article-header {
  height: 80px !important;
  padding-left: 40px;
}
.article-content {
  width: 100%;
  padding: 20px;
  background-color: #fff;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
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
.article-text {
  padding-left: 10px;
  min-height: 300px;
  width: 100%;
  word-wrap: break-word;
  :deep(img) {
    max-width: 900px;
  }
}
.article-operations {
  margin-top: 20px;
  text-align: center;
}
.t-tag {
  margin-right: 5px;
}
.author {
  width: 100%;
  padding: 8px 16px;
  margin: 0;
  box-sizing: border-box;
  border-radius: 4px;
  position: relative;
background-color: #fff;
  overflow: hidden;
  opacity: 1;
  display: flex;
  align-items: center;
  transition: opacity 0.2s;
background-color: var(--td-bg-color-secondarycontainer);
  color: var(--td-text-color-placeholder);
  margin-top: 15px;
  margin-bottom: 15px;
  .author-info {
    display: table-cell;
    padding: 0 8px;
    float: right;
    width: 95%;
    span {
      font-size: 13px;
      line-height: 18px;
    }
    p {
      font-size: 12px;
      margin: 5px 0 0;
    }
  }
}
.comment {
  margin-top: 10px;
}
</style>
