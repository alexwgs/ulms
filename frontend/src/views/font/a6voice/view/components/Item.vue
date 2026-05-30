<template>
  <div class="view-container">
    <div class="mian-container">
      <el-main>
        <div class="artical-content">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">青年说</el-breadcrumb-item>
            <el-breadcrumb-item>项目</el-breadcrumb-item>
            <el-breadcrumb-item>{{ categoryName }}</el-breadcrumb-item>
            <el-breadcrumb-item>{{ artical.title }}</el-breadcrumb-item>
          </el-breadcrumb>
          <h3>{{ artical.title }}</h3>
          <div>
            标签：
            <span v-for="item in labelItems" :key="item.label">
              <el-tag v-if="artical[item.field] == item.val" :type="item.type" effect="dark" size="small">
                {{ item.label }}
              </el-tag>
            </span>
            <div class="artical-icon">
              <span style="color: #909399; font-size: 14px; padding-right: 10px">发布人：{{
                artical.user ? artical.user.ploName : '匿名'
              }}
                &emsp;|&emsp;发布时间：{{ artical.pubDate }}</span>
              <i class="iconfont iconfaxian">
                <font>{{ artical.viewNum + 1 }}</font>
              </i>
              <i class="iconfont iconshoucang1">
                <font>{{ artical.collectNum }}</font>
              </i>
              <i class="iconfont iconzan1">
                <font>{{ artical.likeNum }}</font>
              </i>
              <i class="iconfont iconxiaoxi">
                <font>{{ artical.replyNum }}</font>
              </i>
            </div>
          </div>
          <el-divider></el-divider>
          <div v-if="artical.compType !== 6">
            <div class="author" v-for="member in members" :key="member.id">
              <el-avatar shape="square" size="large" :src="fsURL + member.user.avatar"></el-avatar>
              <div class="author-info">
                <span>{{ getMemberRole(member.role) }}：{{
                  member.user.ploName
                }}/{{ member.user.ploNum }}</span>
                <p>{{ member.describe }}</p>
              </div>
            </div>
          </div>
          <div v-else>
            <el-button type="primary" :disabled="itemTakeFlag" style="width: 100%" @click="takeItem" plain>{{
              itemTakeFlag ? '我已经报名这个项目' : '我要报名这个项目'
            }}</el-button>
          </div>
          <div class="artical-text" v-html="artical.content"></div>
          <el-divider></el-divider>
          <p v-if="artical.hasOwnProperty('files') && artical.files">
            附件下载：<el-button v-for="(file, index) in parseFiles(artical.files)" :key="index" size="small"
              @click="downloadFile(file.path)" round>{{ file.name }}</el-button>
          </p>
          <div class="artical-operations">
            <el-button size="small" :type="isLike == 0 ? '' : 'primary'" :disabled="isLike == 1 ? true : false"
              icon="iconfont iconzan1" @click="setLike(1, artical.id)" round>&emsp;点 赞&emsp;{{ artical.likeNum
              }}</el-button>
            <el-button size="small" :type="isCollect == 0 ? '' : 'primary'" icon="iconfont iconshoucang1"
              @click="setCollect" round>&emsp;{{ artical.isCollect == 0 ? '' : '已' }} 收 藏&emsp;{{
                artical.collectNum
              }}</el-button>
          </div>
          <div class="block" v-if="progresses.length">
            <el-divider content-position="left">项目进度</el-divider>
            <el-progress style="margin-bottom: 15px" :text-inside="true" :stroke-width="26"
              :percentage="progresses[0].rate" :color="colors"></el-progress>
            <el-timeline>
              <el-timeline-item v-for="progress in progresses" :key="progress.id" :timestamp="progress.dateTime"
                placement="top">
                <el-card>
                  <h4>{{ getProgressType(progress.type) }}</h4>
                  <p v-text="progress.content"></p>
                  <p v-if="progress.hasOwnProperty('files') && progress.files">
                    附件下载：<el-button v-for="(file, index) in parseFiles(progress.files)" :key="index" size="small"
                      @click="downloadFile(file.path)" round>{{ file.name }}</el-button>
                  </p>
                </el-card>
              </el-timeline-item>
            </el-timeline>
          </div>
        </div>
        <el-card class="comment" v-if="flags.commentFlag">
          <el-divider>
            <el-pagination v-model:current-page="currentPage" @current-change="handleCurrentChange"
              :page-size="commnetQueryInfo.pageSize" layout="total, prev, pager, next" :total="commentTotal">
            </el-pagination>
          </el-divider>
          <div v-for="(comment, index) in comments" :key="comment.id" class="text item">
            <section v-if="comment.user != undefined">
              <div class="comment-avatar">
                <div class="avartar-box-small">
                  <img :src="comment.anonFlag
                    ? fsURL + 'upload/getFile/avatar/avatar.png'
                    : fsURL + comment.user.avatar
                    " />
                </div>
              </div>
              <div class="comment-info">
                <span class="comment-header" v-if="comment.anonFlag">匿名</span>
                <span class="comment-header" v-else>{{ comment.user.ploName }}/{{ comment.user.ploNum }}</span>
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
                <el-button size="small" icon="iconfont iconxiaoxi" @click="reply(comment.id)">
                  回复</el-button>
                <el-button v-if="
                  comment.likes.filter((item) => item.userId == user.ploNum)
                    .length == 0
                " size="small" icon="iconfont iconzan1" @click="setLike(2, comment.id, index)">
                  * {{ comment.likeNum }}</el-button>
                <el-button v-else type="danger" disabled size="small" icon="iconfont iconzan1">* {{ comment.likeNum
                  }}</el-button>
              </div>
              <div v-if="replyId == comment.id">
                <WangEditor v-model="replyForm.content" height="300"></WangEditor>
                <div class="comment-btn">
                  <el-checkbox v-model="replyAnonFlag" v-if="flags.anonFlag">匿名</el-checkbox>
                  <el-button size="small" type="primary" :disabled="replyForm.content.length < 1 ? true : false"
                    @click="submitReply(comment.id, comment.userid)">提交回复</el-button>
                </div>
              </div>
              <div v-for="reply in comment.replys" :key="reply.id" class="reply">
                <section>
                  <div class="comment-info">
                    <span class="comment-header" v-if="reply.anonFlag">匿名 回复</span>
                    <span class="comment-header" v-else>{{ reply.user.ploName }}/{{
                      reply.user.ploNum
                    }}
                      回复</span>
                    <div class="comment-time">{{ reply.dateTime }}</div>
                  </div>
                  <div class="comment-content" v-html="reply.content"></div>
                </section>
              </div>
            </section>
            <el-divider></el-divider>
          </div>
          <div>
            <el-divider content-position="center" size="small" style="margin-bottom: 30px">
              评 论
            </el-divider>
            <WangEditor v-model="commentForm.content" height="300"></WangEditor>
            <div class="comment-btn">
              <el-checkbox v-if="flags.anonFlag" v-model="commentAnonFlag">匿名</el-checkbox>
              &emsp;<el-button type="primary" :disabled="commentForm.content.length < 1 ? true : false"
                @click="submitComment(artical.pubUser)">提交评论</el-button>
            </div>
          </div>
        </el-card>
        <el-card v-else>
          <center style="color: #888">已关闭评论区</center>
        </el-card>
      </el-main>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import WangEditor from '@/components/WangEditor.vue'

const route = useRoute()
const fsURL =
  import.meta.env.VITE_FILE_MANAGE_BASE

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
  compType: 0,
  files: null
})
const isLike = ref(0)
const isCollect = ref(0)
const commentAnonFlag = ref(false)
const replyAnonFlag = ref(false)

const labelItems = [
  { type: '', label: '置顶', field: 'topFlag', val: 1 },
  { type: 'danger', label: '精华', field: 'eliteFlag', val: 1 },
  { type: 'success', label: '已结案', field: 'compType', val: 1 },
  { type: 'warning', label: '待结案', field: 'compType', val: 2 },
  { type: 'info', label: '待认领', field: 'compType', val: 6 }
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
const currentPage = ref(1)
const commentBtnFlag = ref(false)
const replyBtnFlag = ref(false)
const members = ref([])
const progresses = ref([])
const colors = [
  { color: '#f56c6c', percentage: 20 },
  { color: '#e6a23c', percentage: 40 },
  { color: '#5cb87a', percentage: 60 },
  { color: '#1989fa', percentage: 80 },
  { color: '#6f7ad3', percentage: 100 }
]
const itemTakeFlag = ref(false)
const categorys = ref([])
const dict = ref({})

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

const getMemberRole = (role) => {
  const roleItems = dict.value.cyt_item_member || []
  const item = roleItems.find((item) => item.code == role)
  return item ? item.codeval : ''
}

const getProgressType = (type) => {
  const typeItems = dict.value.cyt_item_type || []
  const item = typeItems.find((item) => item.code == type)
  return item ? item.codeval : ''
}

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

const getItemMember = async () => {
  try {
    const res = await httpInstance.get(`cyt/member/${id.value}`)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    members.value = res.data
    if (members.value.length > 0) {
      getProgress()
    }
    for (const member of members.value) {
      if (member.userId === user.value.ploNum) {
        itemTakeFlag.value = true
      }
    }
  } catch (error) {
    ElMessage.error(error.message || '获取成员失败')
  }
}

const getProgress = async () => {
  try {
    const res = await httpInstance.get(`cyt/progress/${id.value}`)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    progresses.value = res.data
  } catch (error) {
    ElMessage.error(error.message || '获取进度失败')
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

const submitComment = async (toUser) => {
  commentForm.toUser = toUser
  commentForm.anonFlag = commentAnonFlag.value ? 1 : 0
  if (commentForm.content.length >= 1000) {
    ElMessage.error('文本字数过多，最多可输入1000个字符！')
    return
  }
  commentForm.articalId = id.value
  try {
    const res = await httpInstance.post('cyt/comment/', commentForm)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    ElMessage.success(res.msg)
    getComment()
    commentForm.content = ''
    artical.replyNum++
  } catch (error) {
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
  try {
    const res = await httpInstance.post('cyt/reply/', replyForm)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    ElMessage.success(res.msg)
    getComment()
    replyForm.content = ''
    replyId.value = -1
    artical.replyNum++
  } catch (error) {
    ElMessage.error(error.message || '提交失败')
  }
}

const handleCurrentChange = (page) => {
  commnetQueryInfo.pageNum = page
  getComment()
}

const takeItem = () => {
  ElMessageBox.confirm('认领后不可撤销，是否继续认领项目?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'error'
  })
    .then(async () => {
      try {
        const res = await httpInstance.put(`cyt/artical/take/${id.value}`)
        if (res.code !== 200) {
          ElMessage.error(res.msg)
          return
        }
        ElMessage.success(res.msg)
        itemTakeFlag.value = true
        getArtical()
      } catch (error) {
        ElMessage.error(error.message || '认领失败')
      }
    })
    .catch(() => {
      ElMessage.error('取消认领项目！')
    })
}

onMounted(() => {
  user.value = JSON.parse(window.localStorage.getItem('user') || '{}')
  dict.value = JSON.parse(window.localStorage.getItem('dict') || '{}')
  categorys.value = dict.value.cyt_artical_category || []
  getArtical()
  getComment()
  getItemMember()
})
</script>

<style scoped>
.view-container {
  width: 100%;
  background-color: #eaedf1;
  min-height: 100%;

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
</style>
