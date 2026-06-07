<template>
  <t-layout class="view-container">
    <t-layout>
      <t-content>
        <div class="artical-content">
          <h3>{{ artical.title }}</h3>
          <div>
            标签：
            <span v-for="item in labelItems" :key="item.label">
              <t-tag
                v-if="artical[item.field] == item.val"
                :theme="item.type"
                effect="dark"
                size="small"
              >
                {{ item.label }}
              </t-tag>
            </span>
            <div class="artical-icon" v-if="artical.user != undefined">
              <span style="color: #909399; font-size: 14px; padding-right: 10px"
                >发布人：{{ artical.user.ploName }} &emsp;|&emsp;发布时间：{{
                  artical.pubDate
                }}</span
              >
              <i class="iconfont iconfaxian"
                ><font>{{ artical.viewNum + 1 }}</font></i
              >
              <i class="iconfont iconshoucang1"
                ><font>{{ artical.collectNum }}</font></i
              >
              <i class="iconfont iconzan1"
                ><font>{{ artical.likeNum }}</font></i
              >
              <i class="iconfont iconxiaoxi"
                ><font>{{ artical.replyNum }}</font></i
              >
            </div>
          </div>
          <t-divider></t-divider>
          <div v-if="artical.compType !== 6">
            <div class="author" v-for="member in members" :key="member.id">
              <t-avatar
                shape="round"
                size="large"
                :src="fsURL + member.user.avatar"
              ></t-avatar>
              <div class="author-info">
                <span
                  >{{
                    dictStore.dictList.cyt_item_member.filter(
                      (item) => item.code == member.role
                    )[0].codeval
                  }}：{{ member.user.ploName }}/{{ member.user.ploNum }}</span
                >
                <p>{{ member.describe }}</p>
              </div>
            </div>
          </div>
          <div v-else>
            <t-button theme="primary" style="width: 100%" plain
              >待认领</t-button
            >
          </div>
          <div class="artical-text" v-html="artical.content"></div>
          <t-divider></t-divider>
          <p v-if="artical.hasOwnProperty('files') && artical.files != null">
            附件下载：<t-button
              v-for="(file, index) in JSON.parse(artical.files)"
              :key="index"
              size="medium"
              @click="downloadFile(fsURL + file.path)"
              round
              >{{ file.name }}</t-button
            >
          </p>
          <div class="artical-operations">
            <t-button theme="primary" disabled icon="iconfont iconzan1" round
              >&emsp;点 赞&emsp;{{ artical.likeNum }}</t-button
            >
            <t-button
              theme="primary"
              disabled
              icon="iconfont iconshoucang1"
              round
              >&emsp; 收 藏&emsp;{{ artical.collectNum }}</t-button
            >
          </div>
          <div class="block" v-if="progresses.length">
            <t-divider content-position="left">项目进度</t-divider>
            <t-progress
              style="margin-bottom: 15px"
              :text-inside="true"
              :stroke-width="26"
              :percentage="progresses[0].rate"
              :color="colors"
            ></t-progress>
            <t-timeline>
              <t-timeline-item
                v-for="progress in progresses"
                :key="progress.id"
                :timestamp="progress.dateTime"
                placement="top"
              >
                <t-card>
                  <h4>
                    {{
                      dictStore.dictList.cyt_item_type.filter(
                        (item) => item.code == progress.type
                      )[0].codeval
                    }}
                  </h4>
                  <p>{{ progress.content }}</p>
                  <p
                    v-if="
                      progress.hasOwnProperty('files') && progress.files != null
                    "
                  >
                    附件下载：<t-button
                      v-for="(file, index) in JSON.parse(progress.files)"
                      :key="index"
                      size="small"
                      @click="downloadFile(fsURL + file.path)"
                      round
                      >{{ file.name }}</t-button
                    >
                  </p>
                </t-card>
              </t-timeline-item>
            </t-timeline>
          </div>
        </div>
        <t-card class="comment">
          <t-divider content-position="center"> 评 论 </t-divider>
          <t-divider>
            <t-pagination
              v-model:current="currentPage"
              @current-change="handleCurrentChange"
              :page-size="commnetQueryInfo.pageSize"

              :total="commentTotal"
            >
            </t-pagination>
          </t-divider>
          <div
            v-for="(comment, index) in comments"
            :key="comment.id"
            class="text item"
          >
            <section v-if="comment.user != undefined">
              <div class="comment-avatar">
                <t-avatar
                  size="small"
                  :src="
                    comment.anonFlag
                      ? fsURL + 'upload/getFile/avatar/avatar.png'
                      : fsURL + comment.user.avatar
                  "
                ></t-avatar>
              </div>
              <div class="comment-info">
                <span class="comment-header" v-if="comment.anonFlag">匿名</span>
                <span class="comment-header" v-else
                  >{{ comment.user.ploName }}/{{ comment.user.ploNum }}</span
                >
                <div class="comment-time">
                  {{
                    commentTotal -
                    (currentPage - 1) * commnetQueryInfo.pageSize -
                    index
                  }}楼&emsp; {{ comment.dateTime }}
                </div>
              </div>
              <div class="comment-content" v-html="comment.content"></div>
              <div class="comment-operations">
                <t-button
                  size="small"
                  theme="danger"
                  icon="iconfont iconxiaoxi"
                  @click="deleteCommentHandler(comment.id)"
                >
                  删除评论</t-button
                >
                <t-button disabled size="small" icon="iconfont iconzan1"
                  >* {{ comment.likeNum }}</t-button
                >
              </div>

              <div
                v-for="reply in comment.replys"
                :key="reply.id"
                class="reply"
              >
                <section>
                  <div class="comment-info">
                    <span class="comment-header" v-if="reply.anonFlag"
                      >匿名 回复</span
                    >
                    <span class="comment-header" v-else
                      >{{ reply.user.ploName }}/{{
                        reply.user.ploNum
                      }}
                      回复</span
                    >
                    <div class="comment-time">{{ reply.dataTime }}</div>
                  </div>
                  <div class="comment-content" v-html="reply.content"></div>
                  <div class="comment-btn">
                    <t-button
                      size="small"
                      theme="danger"
                      @click="deleteReplyHandler(reply.id)"
                      >删除回复</t-button
                    >
                  </div>
                </section>
              </div>
            </section>
            <t-divider></t-divider>
          </div>
        </t-card>
      </t-content>
    </t-layout>
  </t-layout>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { useDictStore } from '@/stores'
import { useRoute } from 'vue-router'
import {
  getItemDetail,
  getItemMembers,
  getItemProgress,
  getComments,
  deleteComment,
  deleteReply
} from '../../../../../api/cyt/index.js'

const dictStore = useDictStore()
const route = useRoute()

// 从环境变量获取文件服务URL
const fsURL = import.meta.env.VITE_FILE_BASE_URL

const id = ref(route.params.id) // 获取传递的参数
const replyId = ref(-1)
const artical = ref({})
const isLike = ref(0)
const isCollect = ref(0)
const comments = ref({})
const commentTotal = ref(0)
const currentPage = ref(1)
const members = ref([])
const progresses = ref([])

const commnetQueryInfo = reactive({
  querytype: '',
  query: '',
  pageSize: 10,
  pageNum: 1
})

const labelItems = ref([
  { type: '', label: '置顶', field: 'topFlag', val: 1 },
  { type: 'danger', label: '精华', field: 'eliteFlag', val: 1 },
  { type: 'success', label: '已结案', field: 'compType', val: 1 },
  { type: 'warning', label: '待结案', field: 'compType', val: 2 },
  { type: 'info', label: '待认领', field: 'compType', val: 6 }
])

const colors = [
  { color: '#f56c6c', percentage: 20 },
  { color: '#e6a23c', percentage: 40 },
  { color: '#5cb87a', percentage: 60 },
  { color: '#1989fa', percentage: 80 },
  { color: '#6f7ad3', percentage: 100 }
]

// 下载文件
const downloadFile = (url) => {
  window.open(url, '_blank')
}

const getArtical = async () => {
  try {
    const res = await getItemDetail(id.value)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    artical.value = res.data
    isCollect.value = res.data.isCollect
    isLike.value = res.data.isLike
  } catch (error) {
    console.error('获取项目详情失败:', error)
    MessagePlugin.error('获取项目详情失败')
  }
}

const getItemMember = async () => {
  try {
    const res = await getItemMembers(id.value)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    members.value = res.data
    if (members.value.length > 0) {
      getProgress()
    }
  } catch (error) {
    console.error('获取项目成员失败:', error)
    MessagePlugin.error('获取项目成员失败')
  }
}

const getProgress = async () => {
  try {
    const res = await getItemProgress(id.value)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    progresses.value = res.data
  } catch (error) {
    console.error('获取项目进度失败:', error)
    MessagePlugin.error('获取项目进度失败')
  }
}

const getComment = async () => {
  try {
    const res = await getComments(id.value, commnetQueryInfo)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    comments.value = res.data.list
    commentTotal.value = res.data.total
  } catch (error) {
    console.error('获取评论失败:', error)
    MessagePlugin.error('获取评论失败')
  }
}

const deleteReplyHandler = async (id) => {
  try {
    const res = await deleteReply(id)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    MessagePlugin.success(res.msg)
    getComment()
  } catch (error) {
    console.error('删除回复失败:', error)
    MessagePlugin.error('删除回复失败')
  }
}

const deleteCommentHandler = async (id) => {
  try {
    const res = await deleteComment(id)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    MessagePlugin.success(res.msg)
    getComment()
  } catch (error) {
    console.error('删除评论失败:', error)
    MessagePlugin.error('删除评论失败')
  }
}

const handleCurrentChange = (page) => {
  commnetQueryInfo.pageNum = page
  currentPage.value = page
  getComment()
}

onMounted(() => {
  getArtical()
  getComment()
  getItemMember()
})
</script>

<style lang="less" scoped>
.view-container {
  height: 100%;
-color: #eaedf1;
}

.artical-content {
  padding: 20px;
-color: #fff;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
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

.artical-text {
  // 文章正文的样式
  padding-left: 10px;
}

.artical-operations {
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
-color: #fff;
  overflow: hidden;
  opacity: 1;
  display: flex;
  align-items: center;
  transition: opacity 0.2s;
-color: #f4f4f5;
  color: #909399;
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
  display: block;
  margin-top: 10px;
  padding: 20px 0 20px 34px;
  border-bottom: 1px solid #eee;
  position: relative;

  .comment-btn {
    margin-top: 10px;
    height: 40px;
    text-align: right;
  }

  .comment-avatar {
    position: absolute;
    left: 0;
    margin-bottom: 20px;
    display: table;
    table-layout: fixed;
    width: 100%;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    padding-left: 15px;
  }

  .comment-info {
    position: relative;

    .comment-header {
      margin-bottom: 12px;
      position: relative;
      font-size: 14px;
      line-height: 24px;
    }

    .comment-time {
      position: absolute;
      right: 0;
      top: 0;
      font-size: 12px;
      line-height: 24px;
      color: #999;
    }
  }

  .comment-content {
    font-size: 14px;
    margin-top: 0;
    line-height: 24px;
    word-wrap: break-word;
    color: #333;
  }

  .comment-operations {
    margin-top: 8px;
    margin-bottom: 15px;
    position: relative;
    font-size: 14px;
    color: #999;
  }

  .reply {
    padding: 20px;
    box-sizing: border-box;
    border: 1px solid #ebebeb;
    border-radius: 3px;
    font-size: 14px;
    line-height: 22px;
    color: #666;
    word-break: break-word;
    margin: 10px;
-color: #fff;
  }
}
</style>
