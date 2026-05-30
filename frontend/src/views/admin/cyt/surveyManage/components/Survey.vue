<template>
  <el-container class="view-container">
    <el-container>
      <el-main v-if="artical.category != undefined">
        <div class="artical-content">
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
          <el-divider></el-divider>
          <div class="artical-text" v-html="artical.content"></div>
          <el-divider content-position="center"> 调 研 </el-divider>
          <!-- -------------------------------问卷部分------------------------------------ -->
          <div class="QN-questions">
            <div
              class="QN-question"
              v-for="(question, i) of questions"
              :key="i"
            >
              <!-- 单选 -->
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
              <!-- 多选 -->
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
              <!-- 填空 -->
              <div v-else-if="question.questionType === 'textarea'">
                <p>
                  {{ question.sort }}:{{ question.content }}（输入不超过500字）
                </p>
                <el-input
                  :disabled="answerFlag"
                  type="textarea"
                  v-model="question.answer"
                  :placeholder="question.value"
                  >textarea</el-input
                >
              </div>
            </div>
          </div>
          <!-- -------------------------------问卷部分------------------------------------ -->
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
          <div v-if="comments.length > 0">
            <section v-for="(comment, index) in comments" :key="comment.id">
              <div class="comment-avatar">
                <el-avatar
                  size="small"
                  :src="
                    comment.anonFlag
                      ? fsURL + 'upload/getFile/avatar/avatar.png'
                      : fsURL + comment.user.avatar
                  "
                ></el-avatar>
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
            </section>
            <el-divider></el-divider>
          </div>
        </el-card>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getComments } from '../../../../../api/cyt/index.js'

const route = useRoute()

// 从环境变量获取文件服务URL
const fsURL = import.meta.env.VITE_FILE_BASE_URL

const id = ref(route.params.id) // 获取传递的参数
const replyId = ref(-1)
const artical = ref({})
const isLike = ref(0)
const isCollect = ref(0)
const comments = ref([])
const commentTotal = ref(0)
const currentPage = ref(1)
const members = ref([])
const progresses = ref([])
const answerFlag = ref(false)

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

const questions = ref([])

const checkchange = (index) => {
  // 处理多选变化事件
}

const handleCurrentChange = (page) => {
  commnetQueryInfo.pageNum = page
  currentPage.value = page
  getComment()
}

const getComment = async () => {
  try {
    const res = await getComments(id.value, commnetQueryInfo)
    if (res.code !== 200) {
      console.error('获取评论失败:', res.msg)
      return
    }
    comments.value = res.data.list
    commentTotal.value = res.data.total
  } catch (error) {
    console.error('获取评论失败:', error)
  }
}

onMounted(() => {
  // 获取文章详情和问题列表的逻辑需要根据实际API实现
  // 这里假设已经在父组件中获取了artical和questions数据
  getComment()
})
</script>

<style lang="less" scoped>
.view-container {
  height: 100%;
  background-color: #eaedf1;
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
  // 文章正文的样式
  padding-left: 10px;
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
