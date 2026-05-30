<template>
  <div class="view-container">
    <div class="mian-container">
      <el-main v-if="artical.category != undefined">
        <div class="artical-content">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">A6有声</el-breadcrumb-item>
            <el-breadcrumb-item>调研</el-breadcrumb-item>
            <el-breadcrumb-item>{{ categoryName }}</el-breadcrumb-item>
            <el-breadcrumb-item>{{ artical.title }}</el-breadcrumb-item>
          </el-breadcrumb>
          <h3>{{ artical.title }}</h3>
          <div>
            标签：
            <span v-for="item in labelItems" :key="item.label">
              <el-tag
                v-if="artical[item.field] == item.val"
                :type="item.type"
                effect="dark"
                size="small"
              >
                {{ item.label }}
              </el-tag>
            </span>
            <div class="artical-icon">
              <span style="color: #909399; font-size: 14px; padding-right: 10px"
                >发布人：{{
                  artical.user ? artical.user.ploName : '匿名'
                }}
                &emsp;|&emsp;发布时间：{{ artical.pubDate }}</span
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
          <el-divider></el-divider>
          <div v-if="comments.length">已完成调研问卷</div>
          <div class="artical-text" v-html="artical.content"></div>
          <el-divider content-position="center"> 调 研 </el-divider>
          调研截止时间：{{ artical.compDate }}
          <div class="QN-questions">
            <div
              class="QN-question"
              v-for="(question, i) of questions"
              :key="i"
            >
              <div v-if="question.questionType === 'radio'">
                <p>{{ question.sort }}:{{ question.content }}</p>
                <el-radio-group v-model="question.answer">
                  <el-radio
                    v-for="option in question.options"
                    :key="i * 10 + option.id"
                    :label="option.content"
                    :disabled="answerFlag"
                    >{{ option.content }}</el-radio
                  >
                </el-radio-group>
              </div>
              <div v-else-if="question.questionType === 'checkbox'">
                <p>{{ question.sort }}:{{ question.content }}</p>
                <el-checkbox-group
                  v-model="question.checkboxs"
                  @change="checkchange(i)"
                >
                  <el-checkbox
                    v-for="option of question.options"
                    :key="i * 10 + option.id"
                    :label="option.content"
                    :disabled="answerFlag"
                  ></el-checkbox>
                </el-checkbox-group>
              </div>
              <div v-else-if="question.questionType === 'textarea'">
                <p>
                  {{ question.sort }}:{{ question.content }}（输入不超过500字）
                </p>
                <el-input
                  :disabled="answerFlag"
                  type="textarea"
                  v-model="question.answer"
                  :placeholder="question.value"
                ></el-input>
              </div>
            </div>
            <div style="width: 100%; text-align: center; padding-bottom: 20px">
              <el-button
                size="small"
                type="primary"
                icon="s-promotion"
                :disabled="answerFlag"
                @click="submitSurvey()"
                >&emsp;{{ answerFlag ? '已提交' : '提交' }}&emsp;</el-button
              >
            </div>
          </div>
          <div class="artical-operations">
            <el-button
              size="small"
              :type="isLike == 0 ? '' : 'primary'"
              :disabled="isLike == 1 ? true : false"
              icon="iconfont iconzan1"
              @click="setLike(1, artical.id)"
              round
              >&emsp;点 赞&emsp;{{ artical.likeNum }}</el-button
            >
            <el-button
              size="small"
              :type="isCollect == 0 ? '' : 'primary'"
              icon="iconfont iconshoucang1"
              @click="setCollect"
              round
              >&emsp;{{ artical.isCollect == 0 ? '' : '已' }} 收 藏&emsp;{{
                artical.collectNum
              }}</el-button
            >
          </div>
        </div>
        <el-card class="comment">
          <el-divider>
            <el-pagination
              v-model:current-page="currentPage"
              @current-change="handleCurrentChange"
              :page-size="commnetQueryInfo.pageSize"
              layout="total, prev, pager, next"
              :total="commentTotal"
            >
            </el-pagination>
          </el-divider>
          <div
            v-for="(comment, index) in comments"
            :key="comment.id"
            class="text item"
          >
            <section>
              <div class="comment-avatar">
                <div class="avartar-box-small">
                  <img
                    :src="
                      !comment.user
                        ? fsURL + 'upload/getFile/avatar/avatar.png'
                        : fsURL + comment.user.avatar
                    "
                  />
                </div>
              </div>
              <div class="comment-info">
                <span class="comment-header" v-if="comment.user"
                  >{{ comment.user.ploName }}/{{ comment.user.ploNum }}</span
                >
                <span class="comment-header" v-else>匿名</span>
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
                <el-button
                  v-if="flags.commentFlag"
                  size="small"
                  icon="iconfont iconxiaoxi"
                  @click="reply(comment.id)"
                >
                  回复</el-button
                >
                <el-button
                  v-if="
                    comment.likes.filter((item) => item.userId == user.ploNum)
                      .length == 0
                  "
                  size="small"
                  icon="iconfont iconzan1"
                  @click="setLike(2, comment.id, index)"
                >
                  * {{ comment.likeNum }}</el-button
                >
                <el-button
                  v-else
                  type="danger"
                  disabled
                  size="small"
                  icon="iconfont iconzan1"
                  >* {{ comment.likeNum }}</el-button
                >
              </div>
              <div v-if="replyId == comment.id">
                <WangEditor v-model="replyForm.content" height="300"></WangEditor>
                <div class="comment-btn">
                  <el-checkbox v-if="flags.anonFlag" v-model="replyAnonFlag"
                    >匿名</el-checkbox
                  >
                  <el-button
                    size="small"
                    type="primary"
                    :disabled="
                      replyForm.content.length < 1
                        ? true
                        : false || replyBtnFlag
                    "
                    @click="submitReply(comment.id, comment.userid)"
                    >提交回复</el-button
                  >
                </div>
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
                    <div class="comment-time">{{ reply.dateTime }}</div>
                  </div>
                  <div class="comment-content" v-html="reply.content"></div>
                </section>
              </div>
            </section>
            <el-divider></el-divider>
          </div>
          <center>
            <el-pagination
              v-model:current-page="currentPage"
              @current-change="handleCurrentChange"
              :page-size="commnetQueryInfo.pageSize"
              layout="total, prev, pager, next"
              :total="commentTotal"
            >
            </el-pagination>
          </center>

          <div v-if="flags.commentFlag">
            <el-divider content-position="center" style="margin-bottom: 30px">
              评 论
            </el-divider>
            <WangEditor v-model="commentForm.content" height="300"></WangEditor>
            <div class="comment-btn">
              <el-checkbox v-if="flags.anonFlag" v-model="commentAnonFlag"
                >匿名</el-checkbox
              >
              &emsp;
              <el-button
                type="primary"
                :disabled="
                  commentForm.content.length < 1 || commentBtnFlag
                    ? true
                    : false
                "
                @click="submitComment(artical.pubUser)"
                >提交评论</el-button
              >
            </div>
          </div>
        </el-card>
      </el-main>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import WangEditor from '@/components/WangEditor.vue'
import { httpInstance } from '@/utils/request'

const route = useRoute()
const fsURL = import.meta.env.VITE_FILE_MANAGE_BASE || import.meta.env.VITE_FILE_BASE_URL

const user = ref({})
const replyId = ref(-1)
const artical = reactive({
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
  compDate: '',
  files: null
})
const questions = ref([])
const isLike = ref(0)
const isCollect = ref(0)
const commentAnonFlag = ref(false)
const replyAnonFlag = ref(false)

const labelItems = [
  { type: '', label: '置顶', field: 'topFlag', val: 1 },
  { type: 'danger', label: '精华', field: 'eliteFlag', val: 1 },
  { type: 'success', label: '已结案', field: 'compType', val: 1 },
  { type: 'warning', label: '待结案', field: 'compType', val: 2 },
  { type: 'info', label: '待认领', field: 'compType', val: 3 }
]

const commentForm = reactive({
  articalId: 0,
  toUser: '',
  content: '',
  anonFlag: false
})

const replyForm = reactive({
  commentId: 0,
  articalId: 0,
  toUser: '',
  content: '',
  anonFlag: false
})

const comments = ref([])
const commentTotal = ref(0)
const commnetQueryInfo = reactive({
  querytype: '',
  query: '',
  pageSize: 10,
  pageNum: 1
})
const answerFlag = ref(false)
const currentPage = ref(1)
const commentBtnFlag = ref(false)
const replyBtnFlag = ref(false)
const categorys = ref([])

const id = computed(() => route.params.id)

const categoryName = computed(() => {
  if (categorys.value.length > 0 && artical.category) {
    const item = categorys.value.find(
      (item) => parseInt(item.code) === parseInt(artical.category)
    )
    return item ? item.codeval : ''
  }
  return ''
})

const flags = computed(() => {
  if (categorys.value.length > 0 && artical.category) {
    const item = categorys.value.find(
      (item) => parseInt(item.code) === parseInt(artical.category)
    )
    if (item && item.description) {
      return JSON.parse(item.description)
    }
  }
  return { commentFlag: true, anonFlag: false }
})

const getArtical = async () => {
  try {
    const res = await httpInstance.get(`cyt/artical/${id.value}`)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    Object.assign(artical, res.data)
    isCollect.value = res.data.isCollect
    isLike.value = res.data.isLike
    document.title = '[A6有声]' + artical.title
  } catch (error) {
    ElMessage.error(error.message || '获取文章失败')
  }
}

const setCollect = async () => {
  try {
    const res = await httpInstance.post(
      `cyt/collect/${id.value}/${isCollect.value}`
    )
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    if (isCollect.value === 0) {
      isCollect.value = 1
      artical.collectNum++
    } else {
      isCollect.value = 0
      artical.collectNum--
    }
    ElMessage.success(res.msg)
  } catch (error) {
    ElMessage.error(error.message || '操作失败')
  }
}

const getQuestions = async () => {
  try {
    const res = await httpInstance.get(`cyt/questions/${id.value}`)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    questions.value = res.data
    if (
      questions.value[0]?.answer != null &&
      questions.value[0]?.answer !== ''
    ) {
      answerFlag.value = true
    }
  } catch (error) {
    ElMessage.error(error.message || '获取问卷失败')
  }
}

const setLike = async (type, targetId, index) => {
  try {
    const res = await httpInstance.post(`cyt/like/${type}/${targetId}`)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    if (type === 1) {
      isLike.value = 1
      artical.likeNum++
    } else if (type === 2 && comments.value[index]) {
      comments.value[index].likeNum++
      comments.value[index].likes.push({
        id: '',
        likeType: 2,
        articalId: id.value,
        userId: user.value.ploNum,
        dateTime: '',
        status: 1
      })
    }
    ElMessage.success(res.msg)
  } catch (error) {
    ElMessage.error(error.message || '操作失败')
  }
}

const getComment = async () => {
  try {
    const res = await httpInstance.get(`cyt/comment/list/${id.value}/`, {
      params: commnetQueryInfo
    })
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    comments.value = res.data.list
    commentTotal.value = res.data.total
  } catch (error) {
    ElMessage.error(error.message || '获取评论失败')
  }
}

const reply = (rid) => {
  if (replyId.value === rid) replyId.value = -1
  else replyId.value = rid
}

const submitSurvey = async () => {
  const answers = []
  for (const question of questions.value) {
    const answer = { questionId: '', articalId: '', answer: '' }
    answer.questionId = question.id
    answer.articalId = question.articalId
    if (question.questionType === 'checkbox') {
      if (!question.checkboxs || question.checkboxs.length < 2) {
        ElMessage.error('[多选题] 不可单选或不选！')
        return
      }
      answer.answer = question.checkboxs.join('|')
    } else {
      if (!question.answer || question.answer.length < 1) {
        ElMessage.error('回答内容不可为空！')
        return
      }
      answer.answer = question.answer
    }
    answers.push(answer)
  }
  answerFlag.value = true
  try {
    const res = await httpInstance.post('cyt/answer', answers)
    if (res.code !== 200) {
      answerFlag.value = false
      ElMessage.error(res.msg)
      return
    }
    ElMessage.success(res.msg)
    getQuestions()
  } catch (error) {
    answerFlag.value = false
    ElMessage.error(error.message || '提交失败')
  }
}

const handleCurrentChange = (page) => {
  commnetQueryInfo.pageNum = page
  getComment()
}

const checkchange = (i) => {
  // Handle checkbox change
}

const submitComment = async (toUser) => {
  commentForm.toUser = toUser
  commentForm.anonFlag = commentAnonFlag.value ? 1 : 0
  if (commentForm.content.length >= 1000) {
    ElMessage.error('文本字数过多，最多可输入1000个字符！')
    return
  }
  commentForm.articalId = id.value
  commentBtnFlag.value = true
  try {
    const res = await httpInstance.post('cyt/comment/', commentForm)
    if (res.code !== 200) {
      commentBtnFlag.value = false
      ElMessage.error(res.msg)
      return
    }
    commentBtnFlag.value = false
    ElMessage.success(res.msg)
    getComment()
    commentForm.content = ''
    artical.replyNum++
  } catch (error) {
    commentBtnFlag.value = false
    ElMessage.error(error.message || '提交失败')
  }
}

const submitReply = async (commentId, toUser) => {
  replyForm.anonFlag = replyAnonFlag.value ? 1 : 0
  if (replyForm.content.length >= 1000) {
    ElMessage.error('文本字数过多，最多可输入1000个字符！')
    return
  }
  replyForm.articalId = id.value
  replyForm.commentId = commentId
  replyForm.toUser = toUser
  replyBtnFlag.value = true
  try {
    const res = await httpInstance.post('cyt/reply/', replyForm)
    if (res.code !== 200) {
      replyBtnFlag.value = false
      ElMessage.error(res.msg)
      return
    }
    replyBtnFlag.value = false
    ElMessage.success(res.msg)
    getComment()
    replyForm.content = ''
    replyId.value = -1
    artical.replyNum++
  } catch (error) {
    replyBtnFlag.value = false
    ElMessage.error(error.message || '提交失败')
  }
}

onMounted(() => {
  user.value = JSON.parse(window.localStorage.getItem('user') || '{}')
  const dict = JSON.parse(window.localStorage.getItem('dict') || '{}')
  categorys.value = dict.cyt_artical_category || []
  getArtical()
  getComment()
  getQuestions()
})
</script>

<style scoped>
.view-container {
  width: 100%;
  background-color: #eaedf1;
  .mian-container {
    margin: 0 auto;
    max-width: 1400px;
  }
}
.artical-header {
  height: 80px !important;
  padding-left: 40px;
}
.artical-content {
  padding: 20px;
  background-color: #fff;
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
  padding-left: 10px;
  min-height: 300px;
  max-width: 1000px;
  word-wrap: break-word;
  :deep(img) {
    max-width: 900px;
  }
}
.artical-operations {
  margin-top: 20px;
  text-align: center;
}
.el-tag {
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
  background-color: #f4f4f5;
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
    max-width: 1000px;
    :deep(img) {
      max-width: 900px;
    }
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
    background-color: #fff;
  }
}
.QN-questions {
  padding: 20px;
  .QN-question {
    margin-bottom: 20px;
    p {
      font-size: 14px;
      font-weight: bold;
      margin-bottom: 10px;
    }
  }
}
</style>
