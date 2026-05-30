<template>
  <div class="comment-container">
    <el-pagination v-if="commentTotal > 0" v-model:current-page="currentPage" @current-change="handlePageChange"
      :page-size="queryInfo.pageSize" layout="total, prev, pager, next" :total="commentTotal"
      style="margin-bottom: 15px">
    </el-pagination>

    <div v-for="(comment, index) in comments" :key="comment.id" class="comment-item">
      <div class="comment-avatar">
        <div class="avartar-box-mini">
          <img :src="getAvatar(comment.user)" />
        </div>
      </div>
      <div class="comment-info">
        <span class="comment-header">{{ getUserName(comment.user) }}</span>
        <div class="comment-time">
          {{ getFloorNum(index) }}楼 · {{ comment.dateTime }}
        </div>
      </div>
      <div class="comment-content" v-html="comment.content"></div>
      <div class="comment-operations">
        <el-button size="small" @click="handleReply(comment.id)">
          回复
        </el-button>
        <el-button size="small" :type="isLikedComment(comment) ? 'primary' : ''"
          @click="handleLike(2, comment.id, index)">
          赞 * {{ comment.likeNum }}
        </el-button>
      </div>

      <div v-if="replyId === comment.id" class="reply-form">
        <WangEditor v-model="replyForm.content" height="300"></WangEditor>
        <div class="comment-btn">
          <el-checkbox v-if="showAnonOption" v-model="replyAnonFlag">匿名</el-checkbox>
          <el-button type="primary" size="small" :disabled="!replyForm.content || replyBtnFlag"
            @click="submitReply(comment.id, comment.userid)">
            提交回复
          </el-button>
        </div>
      </div>

      <div v-for="reply in comment.replys" :key="reply.id" class="reply-item">
        <div class="comment-info">
          <span class="comment-header">
            {{ reply.anonFlag ? '匿名' : getUserName(reply.user) }} 回复
          </span>
          <div class="comment-time">{{ reply.dateTime }}</div>
        </div>
        <div class="comment-content" v-html="reply.content"></div>
      </div>
    </div>

    <el-pagination v-if="commentTotal > 0" v-model:current-page="currentPage" @current-change="handlePageChange"
      :page-size="queryInfo.pageSize" layout="total, prev, pager, next" :total="commentTotal" style="margin-top: 15px">
    </el-pagination>

    <div v-if="showCommentForm" class="comment-form">
      <el-divider content-position="center">评 论</el-divider>
      <WangEditor v-model="commentForm.content" height="300"></WangEditor>
      <div class="comment-btn">
        <el-checkbox v-if="showAnonOption" v-model="commentAnonFlag">匿名</el-checkbox>
        <el-button type="primary" :disabled="!commentForm.content || commentBtnFlag" @click="handleSubmitComment">
          提交评论
        </el-button>
      </div>
    </div>
    <div v-else class="comment-closed">
      <span>已关闭评论区</span>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import WangEditor from '@/components/WangEditor.vue'
import {
  toggleLike,
  addComment,
  addReply,
  getCommentList
} from '@/api/a6voice/index.js'

const props = defineProps({
  articalId: {
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

const fsURL = import.meta.env.VITE_FILE_MANAGE_BASE || ''
const defaultAvatar = `${fsURL}upload/getFile/avatar/avatar.png`

const currentPage = ref(1)
const comments = ref([])
const commentTotal = ref(0)
const replyId = ref(-1)
const commentBtnFlag = ref(false)
const replyBtnFlag = ref(false)
const commentAnonFlag = ref(false)
const replyAnonFlag = ref(false)

const queryInfo = ref({
  pageSize: 10,
  pageNum: 1
})

const commentForm = ref({
  articalId: props.articalId,
  toUser: props.pubUser,
  content: '',
  anonFlag: 0
})

const replyForm = ref({
  commentId: 0,
  articalId: props.articalId,
  toUser: '',
  content: '',
  anonFlag: 0
})

const user = ref({})

const getAvatar = (userInfo) => {
  if (!userInfo?.avatar) return defaultAvatar
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
    const res = await getCommentList(props.articalId, queryInfo.value)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
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
      ElMessage.error(res.msg)
      return
    }
    if (type === 2) {
      comments.value[index].likeNum++
      comments.value[index].likes.push({
        id: '',
        likeType: 2,
        articalId: props.articalId,
        userId: user.value.ploNum,
        dateTime: '',
        status: 1
      })
    }
    ElMessage.success(res.msg)
  } catch (error) {
    console.error('点赞失败', error)
  }
}

const handleSubmitComment = async () => {
  if (commentForm.value.content.length > 1000) {
    ElMessage.error('文本字数过多，最多可输入1000个字符！')
    return
  }
  commentBtnFlag.value = true
  commentForm.value.anonFlag = commentAnonFlag.value ? 1 : 0
  try {
    const res = await addComment(commentForm.value)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    ElMessage.success(res.msg)
    commentForm.value.content = ''
    commentAnonFlag.value = false
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
    ElMessage.error('文本字数过多，最多可输入1000个字符！')
    return
  }
  replyBtnFlag.value = true
  replyForm.value.anonFlag = replyAnonFlag.value ? 1 : 0
  replyForm.value.toUser = toUser
  try {
    const res = await addReply(replyForm.value)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    ElMessage.success(res.msg)
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
})

defineExpose({
  fetchComments
})
</script>

<style lang="less" scoped>
.comment-container {
  .comment-item {
    padding: 15px 0;
    border-bottom: 1px solid #eee;

    .comment-avatar {
      display: inline-block;
      vertical-align: top;

      .avartar-box-mini {
        width: 40px;
        height: 40px;
        border-radius: 4px;
        overflow: hidden;

        img {
          width: 100%;
          height: 100%;
          object-fit: cover;
        }
      }
    }

    .comment-info {
      display: inline-block;
      margin-left: 10px;
      vertical-align: top;

      .comment-header {
        font-weight: bold;
        color: #333;
      }

      .comment-time {
        font-size: 12px;
        color: #999;
        margin-top: 4px;
      }
    }

    .comment-content {
      margin-top: 10px;
      padding-left: 50px;
      word-break: break-all;
    }

    .comment-operations {
      margin-top: 10px;
      padding-left: 50px;
    }

    .reply-form {
      margin-top: 15px;
      padding-left: 50px;
    }

    .reply-item {
      margin-left: 50px;
      margin-top: 15px;
      padding: 10px;
      background: #f9f9f9;
      border-radius: 4px;
    }
  }

  .comment-form {
    margin-top: 20px;

    .comment-btn {
      margin-top: 10px;
      display: flex;
      justify-content: flex-end;
      align-items: center;
      gap: 10px;
    }
  }

  .comment-closed {
    text-align: center;
    padding: 30px;
    color: #888;
  }
}
</style>
