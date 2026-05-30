<template>
  <div class="container">
    <el-row :gutter="15" style="height: 300px">
      <el-col :span="10">
        <img :src="fsURL + 'upload/getFile/college-cover/' + course.coverImg" width="100%" height="300px;" />
      </el-col>
      <el-col :span="14">
        <el-card style="height: 300px">
          <el-descriptions :title="course.courseName" :column="2" border>
            <el-descriptions-item label="课程类型">{{
              course.teachMethod == 2 ? '任务课程' : '常规课程'
              }}</el-descriptions-item>
            <el-descriptions-item label="教学方式">{{
              course.courseType == 1 ? '线上学习' : '线下授课'
              }}</el-descriptions-item>
            <el-descriptions-item label="授课时数">
              <el-icon>
                <Clock />
              </el-icon> {{ course.hours }}
            </el-descriptions-item>
            <el-descriptions-item label="课程积分">
              <el-icon>
                <Ticket />
              </el-icon> {{ course.coin }}
            </el-descriptions-item>
            <el-descriptions-item label="授课对象">{{
              course.teachObject
              }}</el-descriptions-item>
            <el-descriptions-item label="课程评分">
              <el-rate v-if="course.courseScore > 0" style="display: inline-block" v-model="course.courseScore" disabled
                text-color="#ff9900" show-score></el-rate>
              <span v-else>暂无评分</span>
            </el-descriptions-item>
            <el-descriptions-item label="操作" :span="2">
              <el-button v-if="course.teachMethod != 2" @click="signCourse()" :disabled="sign == null ? false : true">{{
                sign == null ? '报名' : sign.handleDate }}</el-button>&emsp;
              <el-button v-show="sign !== null" @click="goStudy()">在线学习</el-button>&emsp;
              <el-button v-if="studyLog && course.ifEval == 1" :disabled="studyLog.evalComp == 1"
                @click="evaluateRef.show(course)">{{ studyLog.evalComp == 1 ? '已评价' : '需评价' }}</el-button>&emsp;
              <el-button v-if="studyLog && course.ifExam == 1" :disabled="!(
                studyLog.ifEval == 0 ||
                (studyLog.evalComp && studyLog.ifEval)
              )
                " @click="goExam(course)">{{
                  studyLog.ifEval == 0 || (studyLog.evalComp && studyLog.ifEval)
                    ? '考试'
                    : '请先评价'
                }}</el-button>
            </el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="15" style="margin-top: 15px">
      <el-col :span="18">
        <el-card class="main-card">
          <el-tabs v-model="tabActive" @tab-click="tabChange">
            <el-tab-pane label="介绍" name="intro">
              <el-row :gutter="10">
                <el-col :span="8"><el-tag :type="course.ifExam === 1 ? 'danger' : 'danger'" effect="plain">{{
                  course.ifExam === 1 ? '需要考试' : '无需考试' }}</el-tag></el-col>
                <el-col :span="8"><el-tag :type="course.ifEval === 1 ? 'danger' : 'danger'" effect="plain">{{
                  course.ifEval === 1 ? '需要评价' : '无需评价' }}</el-tag></el-col>
                <el-col :span="8"><el-tag v-if="course.evalDate != 0">{{ course.evalDate }}天内完成</el-tag></el-col>
              </el-row>
              <div style="margin-top: 10px" v-html="course.courseDes"></div>
              <div class="menu">
                <div class="menu-title">课程目录</div>
                <el-timeline>
                  <el-timeline-item v-for="(file, index) in course.courseFile" :key="file.fileId" :timestamp="'【' + file.extenName + '】' + ' NO.' + (index + 1)
                    " placement="top">
                    <el-card v-if="file.fileType === 1">
                      <h4>{{ file.fileName }}</h4>
                      <p>课程学习时长{{ formatDuration(file.duration) }}</p>
                    </el-card>
                    <p v-else>学习附件 - {{ file.fileName }}</p>
                  </el-timeline-item>
                </el-timeline>
              </div>
            </el-tab-pane>
            <el-tab-pane label="学习情况" name="courseStudy">
              <ViewCourseStudy ref="viewCourseStudyRef" height="600px" width="700px"></ViewCourseStudy>
            </el-tab-pane>
            <el-tab-pane label="评论" name="comment">
              <div class="comment" v-for="(item, index) in evaluate" :key="index">
                <div class="comment-avatar">
                  <div class="avartar-box-small">
                    <img :src="item.ifAnon
                      ? fsURL + 'upload/getFile/avatar/avatar.png'
                      : fsURL + item.user?.avatar
                      " />
                  </div>
                </div>
                <div class="comment-info">
                  <span class="comment-header">{{ item.ploNum }}</span>
                  <div class="comment-time">{{ item.handleDate }}</div>
                </div>
                <div class="comment-content" v-for="(evalItem, evalIndex) in item.evaluate" :key="evalIndex">
                  <b>{{ evalItem.quesCont }}:</b>
                  <el-rate v-if="evalItem.quesType === 1" v-model="evalItem.cont" disabled show-score
                    text-color="#ff9900" score-template="{value}"></el-rate>
                  <div v-else>{{ evalItem.cont }}</div>
                </div>
              </div>
              <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="queryInfo.pageNum" :page-sizes="[20, 40, 100, 200]" :page-size="queryInfo.pageSize"
                layout="total, sizes, prev, pager, next, jumper" :total="total"></el-pagination>
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="teacher-card">
          <div v-if="teacher.avatar == null" style="
              height: 200px;
              width: 100%;
              text-align: center;
              line-height: 200px;
            ">
            暂无照片
          </div>
          <img v-else :src="fsURL + 'upload/getFile/college-avatar/' + teacher.avatar" class="image" width="100%" />
          <div style="padding: 14px">
            <el-tag effect="plain">{{ teacher.ploName }}</el-tag>
            <el-tag v-if="teacher.skillType">{{ skillType[teacher.skillType]?.label
            }}{{ skillName[teacher.skillName]?.label }}</el-tag>
            <div>
              <el-rate v-if="teacher.score > 0" style="display: inline-block; padding-top: 10px" v-model="teacher.score"
                disabled text-color="#ff9900" score-template="{value}" show-score></el-rate><span v-else>暂无评分</span>
            </div>
            <div style="
                font-size: 12px;
                color: #999;
                height: 50px;
                margin-top: 5px;
                overflow: hidden;
              ">
              简介：{{ teacher.introduce }}
            </div>
            <div class="bottom clearfix">
              <el-link underline="never" @click="router.push('/college/teacher')">查看更多...</el-link>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <Evaluate ref="evaluateRef"></Evaluate>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Clock, Ticket } from '@element-plus/icons-vue'
import Evaluate from '@/views/font/college/evaluate/index.vue'
import ViewCourseStudy from '@/views/font/college/view-course-study/index.vue'
import { httpInstance } from '@/utils/request'

const route = useRoute()
const router = useRouter()
const courseId = route.params.courseId // 注意是 params，不是 query
const fsURL = import.meta.env.VITE_FILE_BASE_URL

const course = ref({})
const teacher = ref({})
const tabActive = ref('intro')
const sign = ref(null)
const studyLog = ref(null)
const evaluate = ref([])
const total = ref(0)

const queryInfo = reactive({
  orderType: ' desc',
  order: ' handleDate',
  courseId: '',
  pageSize: 20,
  pageNum: 1
})

const skillType = {
  0: { value: 0, label: '无身份', status: true },
  1: { value: 1, label: '特约', status: true },
  2: { value: 2, label: '预留', status: false },
  3: { value: 3, label: '初级', status: true },
  4: { value: 4, label: '中级', status: true },
  5: { value: 5, label: '高级', status: true }
}

const skillName = {
  1: { value: 1, label: '讲师', status: true },
  2: { value: 2, label: '辅导老师', status: false }
}

const evaluateRef = ref(null)
const viewCourseStudyRef = ref(null)

const formatDuration = (duration) => {
  if (!duration) return '00:00:00'
  const hours = Math.floor(duration / 3600)
  const minutes = Math.floor((duration % 3600) / 60)
  const seconds = duration % 60
  return `${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`
}

const getCourse = async () => {
  const res = await httpInstance.get(`college/course/${courseId}`)
  if (res.code !== 200) return ElMessage.error(res.msg)
  course.value = res.course
  teacher.value = res.teacher
}

const getLog = async () => {
  const res = await httpInstance.get(`college/study/${courseId}`)
  if (res.code !== 200) return ElMessage.error(res.msg)
  sign.value = res.sign
  studyLog.value = res.studyLog
}

const signCourse = async () => {
  const res = await httpInstance.post('college/study/sign', course.value)
  if (res.code !== 200) return ElMessage.error(res.msg)
  ElMessage.success(res.msg)
  getLog()
}

const getEvaluate = async () => {
  queryInfo.courseId = courseId
  const res = await httpInstance.get('college/evaluate/list', {
    params: queryInfo
  })
  if (res.code !== 200) return ElMessage.error(res.msg)
  evaluate.value = res.data.list
  total.value = res.data.total
}

const tabChange = (tab, e) => {
  if (tab.props.name === 'comment') {
    getEvaluate()
  } else if (tab.props.name === 'courseStudy') {
    viewCourseStudyRef.value?.init(courseId)
  }
}

const goStudy = () => {
  window.localStorage.setItem('course', JSON.stringify(course.value))
  router.push('/college/course/study')
}

const handleSizeChange = (pageSize) => {
  queryInfo.pageSize = pageSize
  getEvaluate()
}

const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  getEvaluate()
}

const goExam = (course) => {
  localStorage.setItem('course', JSON.stringify(course))
  const routeUrl = router.resolve({
    name: 'college-exam',
    params: { courseId: courseId }
  })
  window.open(routeUrl.href, 'newWindow')
}

onMounted(() => {
  getCourse()
  getLog()
})
</script>

<style lang="less" scoped>
.container {
  padding-left: calc((100vw - 1240px) / 2);
  max-width: 1200px;
}

.main-card {
  min-height: 500px;

  .menu {
    margin-top: 15px;

    .menu-title {
      margin-bottom: 15px;
      line-height: 35px;
      padding-left: 10px;
      background-color: #f5f5f5;
      border-left: 10px solid #9b2b23;
    }
  }
}

.teacher-card {
  .image {
    width: 100%;
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
    margin-left: 30px;
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
    margin-left: 30px;
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
