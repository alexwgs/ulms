<template>
  <div class="comment-container">
    <t-pagination
      v-if="commentTotal > 0"
      v-model="currentPage"
      @current-change="handlePageChange"
      :page-size="queryInfo.pageSize"
      :total="commentTotal"
      style="margin-bottom: 15px"
    />
    <t-list :split="true">
      <t-list-item  v-for="(comment, index) in comments" :key="comment.id">

    <t-comment
      :author="getUserName(comment.user)"
      :datetime="getFloorNum(index) + '楼 · ' + comment.dateTime"
      class="comment-reply"
    >
      <template #avatar>
        <t-avatar shape="round" size="40px">
          <img :src="getAvatar(comment.user)" class="avatar-img" />
        </t-avatar>
      </template>

      <template #content>
        <SafeHtml :html="comment.content" />
      </template>

      <template #actions>
        <t-space key="thumbUp" size="6px" class="action-item" @click="handleLike(2, comment.id, index)">
          <t-icon name="thumb-up" :color="isLikedComment(comment) ? 'red' : 'default'" />
          <span>{{ comment.likeNum }}</span>
        </t-space>
        <t-space key="chat" size="6px" class="action-item" @click="handleReply(comment.id)">
          <t-icon name="chat" />
          <span>回复TA</span>
        </t-space>
      </template>

      <template #reply>
        <div v-if="comment.replys && comment.replys.length">
          <t-comment
            v-for="reply in comment.replys"
            :key="reply.id"
            :author="reply.anonFlag ? '匿名' : getUserName(reply.user) + ' 回复'"
            :datetime="reply.dateTime"
          >
            <template #avatar>
              <t-avatar shape="round" size="32px">
                <img :src="getAvatar(reply.user)" class="avatar-img" />
              </t-avatar>
            </template>
            <template #content>
              <SafeHtml :html="reply.content" />
            </template>
            <template #actions>
              <t-space key="thumbUp" size="6px" class="action-item" @click="handleLike(3, reply.id, index)">
                <t-icon name="thumb-up" :color="isLikedComment(reply) ? 'blue' : 'default'" />
                <span>{{ reply.likeNum }}</span>
              </t-space>
              <t-space key="chat" size="6px" class="action-item" @click="handleReply(reply.id)">
                <t-icon name="chat" />
                <span>回复TA</span>
              </t-space>
            </template>
          </t-comment>
        </div>
        <div v-if="replyId === comment.id" class="reply-form">
          <WangEditor v-model="replyForm.content" height="200" />
          <div class="comment-btn">
            <t-checkbox v-if="showAnonOption" v-model="replyAnonFlag">匿名</t-checkbox>
            <t-button
              theme="primary"
              size="small"
              :disabled="!replyForm.content || replyBtnFlag"
              @click="submitReply(comment.id, comment.userid)"
            >
              提交回复
            </t-button>
          </div>
        </div>
      </template>
    </t-comment>
      </t-list-item>
    </t-list>


    <!-- Sticky bottom comment bar -->
    <div v-if="showCommentForm" ref="stickyBarRef" class="comment-sticky-bar" :class="{ 'is-expanded': commentExpanded }">
      <div class="sticky-bar-trigger" @click="expandComment">
        <t-icon name="edit" />
        <span v-if="!commentExpanded">发表评论...</span>
        <span v-else @click.stop="commentExpanded = false" class="collapse-btn">
          <t-icon name="chevron-down" /> 收起
        </span>
      </div>
      <div v-show="commentExpanded" class="sticky-bar-body" ref="stickyBarBodyRef">
        <WangEditor v-model="commentForm.content" height="200" />
        <div class="comment-btn">
          <t-checkbox v-if="showAnonOption" v-model="commentAnonFlag">匿名</t-checkbox>
          <t-button
            theme="primary"
            :disabled="!commentForm.content || commentBtnFlag"
            @click="handleSubmitComment"
          >
            提交评论
          </t-button>
        </div>
      </div>
    </div>
    <div v-else class="comment-closed">
      <span>已关闭评论区</span>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import WangEditor from '@/components/WangEditor.vue'
import defaultAvatarImg from '@/assets/img/default_avatar.png'
import {
  toggleLike,
  addComment,
  addReply,
  getCommentList
} from '@/api/a6voice/index.js'

const props = defineProps({
  articleId: {
    type: [Number, String],
    required: true
  },
  pubUser: {
    type: String,
    default: ''
  },
  replyUserId: {
    type: String,
    default: ''
  },
  showAnonOption: {
    type: Boolean,
    default: false
  },
  showCommentForm: {
    type: Boolean,
    default: true
  }
})

const emit = defineEmits(['comment-submitted'])

const fsURL = import.meta.env.VITE_FILE_BASE_URL || ''

const currentPage = ref(1)
const comments = ref([])
const commentTotal = ref(0)
const replyId = ref(-1)
const commentBtnFlag = ref(false)
const replyBtnFlag = ref(false)
const commentAnonFlag = ref(false)
const replyAnonFlag = ref(false)
const commentExpanded = ref(false)

const queryInfo = ref({
  pageSize: 10,
  pageNum: 1
})

const commentForm = ref({
  articleId: props.articleId,
  toUser: props.pubUser,
  content: '',
  anonFlag: 0
})

const replyForm = ref({
  commentId: 0,
  articleId: props.articleId,
  toUser: '',
  content: '',
  anonFlag: 0
})

const user = ref({})

const getAvatar = (userInfo) => {
  if (!userInfo?.avatar) return defaultAvatarImg
  return fsURL + userInfo.avatar
}

const getUserName = (userInfo) => {
  if (!userInfo) return '匿名'
  return `${userInfo.ploName}/${userInfo.ploNum}`
}

const getFloorNum = (index) => {
  return (
    commentTotal.value -
    (currentPage.value - 1) * queryInfo.value.pageSize -
    index
  )
}

const isLikedComment = (comment) => {
  if (!user.value?.ploNum || !comment.likes) return false
  return comment.likes.some((item) => item.userId === user.value.ploNum)
}

const fetchComments = async () => {
  try {
    const res = await getCommentList(props.articleId, queryInfo.value)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    comments.value = res.data.list
    commentTotal.value = res.data.total
  } catch (error) {
    console.error('获取评论失败', error)
  }
}

const handlePageChange = (page) => {
  queryInfo.value.pageNum = page
  fetchComments()
}

const handleReply = (commentId) => {
  replyId.value = replyId.value === commentId ? -1 : commentId
  replyForm.value.commentId = commentId
  replyForm.value.content = ''
}

const handleLike = async (type, id, index) => {
  try {
    const res = await toggleLike(type, id)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    if (type === 2) {
      comments.value[index].likeNum++
      comments.value[index].likes.push({
        id: '',
        likeType: 2,
        articleId: props.articleId,
        userId: user.value.ploNum,
        dateTime: '',
        status: 1
      })
    }
    MessagePlugin.success(res.msg)
  } catch (error) {
    console.error('点赞失败', error)
  }
}

const stickyBarRef = ref(null)
const stickyBarBodyRef = ref(null)

const expandComment = () => {
  commentExpanded.value = true
}

const handleClickOutside = (e) => {
  if (!commentExpanded.value) return
  const bar = stickyBarRef.value
  if (bar && !bar.contains(e.target)) {
    commentExpanded.value = false
  }
}

const handleSubmitComment = async () => {
  if (commentForm.value.content.length > 1000) {
    MessagePlugin.error('文本字数过多，最多可输入1000个字符！')
    return
  }
  commentBtnFlag.value = true
  commentForm.value.anonFlag = commentAnonFlag.value ? 1 : 0
  try {
    const res = await addComment(commentForm.value)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    MessagePlugin.success(res.msg)
    commentForm.value.content = ''
    commentAnonFlag.value = false
    commentExpanded.value = false
    await fetchComments()
    emit('comment-submitted')
  } catch (error) {
    console.error('提交评论失败', error)
  } finally {
    commentBtnFlag.value = false
  }
}

const submitReply = async (commentId, toUser) => {
  if (replyForm.value.content.length > 1000) {
    MessagePlugin.error('文本字数过多，最多可输入1000个字符！')
    return
  }
  replyBtnFlag.value = true
  replyForm.value.anonFlag = replyAnonFlag.value ? 1 : 0
  replyForm.value.toUser = toUser
  try {
    const res = await addReply(replyForm.value)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    MessagePlugin.success(res.msg)
    replyForm.value.content = ''
    replyAnonFlag.value = false
    replyId.value = -1
    await fetchComments()
    emit('comment-submitted')
  } catch (error) {
    console.error('提交回复失败', error)
  } finally {
    replyBtnFlag.value = false
  }
}

onMounted(() => {
  const userInfo = window.localStorage.getItem('user')
  if (userInfo) {
    user.value = JSON.parse(userInfo)
  }
  fetchComments()
  document.addEventListener('mousedown', handleClickOutside)
})

onBeforeUnmount(() => {
  document.removeEventListener('mousedown', handleClickOutside)
})

defineExpose({
  fetchComments
})
</script>

<style lang="less" scoped>
.comment-container {

  margin-bottom: 50px;
  .comment-reply {
    > .t-comment__inner {
      > .t-comment__content {
        > .t-comment__actions {
          margin-right: 24px;
        }
      }
    }
  }
  .reply-form {
    margin-top: 12px;
    padding: 12px;
    background: #f9f9f9;
    border-radius: 8px;
  }

  .comment-btn {
    margin-top: 10px;
    display: flex;
    justify-content: flex-end;
    align-items: center;
    gap: 10px;
  }

  .comment-closed {
    text-align: center;
    padding: 30px;
    color: #888;
  }

  :deep(.t-comment__content) {
    word-break: break-all;
    img {
      max-width: 100%;
    }
  }

  .comment-sticky-bar {
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    z-index: 100;
    background: #fff;
    box-shadow: 0 -2px 12px rgba(0, 0, 0, 0.1);
    transition: height 0.3s ease;
    height: 50px;

    &.is-expanded {
      height: auto;
      min-height: 320px;
    }

    .sticky-bar-trigger {
      display: flex;
      align-items: center;
      gap: 8px;
      padding: 12px 20px;
      cursor: pointer;
      color: var(--td-text-color-secondary);
      border-bottom: 1px solid var(--td-component-stroke);
      font-size: 14px;

      &:hover {
        color: #666;
      }

      .collapse-btn {
        margin-left: auto;
        color: #0052d9;
        font-size: 13px;
        cursor: pointer;

        &:hover {
          color: #0034a5;
        }
      }
    }

    .sticky-bar-body {
      padding: 12px 20px;
    }
  }
}
</style>
