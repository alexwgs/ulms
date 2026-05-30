<template>
  <div class="container">
    <el-row :gutter="20">
      <el-col :span="16">
        <div class="examInfo">
          {{ examInfo.examName }}
        </div>
        <el-divider></el-divider>
        <el-card class="box-card">
          <template #header>
            <div class="clearfix">
              <span>答题区</span>
              <span style="float: right; padding: 3px 0"
                >考试剩余时间：{{ formatUtcTime(examTime) }}</span
              >
            </div>
          </template>
          <div class="answer-main" v-show="mustReadFlag">
            考试须知：
            <el-divider></el-divider>
            <ul>
              <li>
                <p>
                  请认准预览考试说明中的考试配置情况，若标记红色部分尤为重要！
                </p>
              </li>
              <li>
                答案提交需点击下一题完成提交，若考试为可题目跳转，那么每次切换题目也可提交答案！
              </li>
              <li>
                若考试需预约时间，则一定在预约的结束时间前留足答题时间，否则将自动交卷！
                <p>
                  例如：预约时间16:00-16:30,考试时间10分钟。但人员16:28分点开考试，虽然考试时间为10分钟，但距离预约的到期时间仅剩2分钟，则实际可答题时间为2分钟！
                </p>
              </li>
              <li>
                考试页面上的时间仅作为前端用户展示，实际以服务器时间为准！
              </li>
            </ul>
            <el-alert
              v-if="areaFlag"
              :title="'请前往指定区域【' + examInfo.areaName + '】考试'"
              type="error"
              show-icon
              :closable="false"
            ></el-alert>
            <el-alert
              v-if="timeErroInfo != ''"
              :title="timeErroInfo"
              type="error"
              show-icon
              :closable="false"
            ></el-alert>
          </div>
          <Question
            ref="questionRef"
            @getUserAnswer="getUserAnswer"
            style="height: 500px"
          ></Question>
          <el-row style="padding-top: 20px">
            <el-col :span="24"
              ><el-button
                type="primary"
                style="width: 100%"
                size="small"
                @click="nextQuestion()"
                :disabled="startBtnFlag"
              >
                {{ curentQuesNum < 0 ? '开始答题' : '下一题' }}
              </el-button></el-col
            >
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-alert
          :title="
            '请确认答题人信息，当前答题人信息为：【' +
            user.ploName +
            '/' +
            user.ploNum +
            '】'
          "
          :type="userId == user.ploNum ? 'success' : 'error'"
          :closable="false"
          effect="dark"
        >
        </el-alert>
        <el-card class="answer-controller">
          <template #header>
            <div class="clearfix">
              <span>考试说明</span>
            </div>
          </template>
          <el-row :gutter="20" style="text-align: center">
            <el-col :span="8"
              ><el-tag effect="plain"
                >题目数量：{{ examInfo.quesNum }}</el-tag
              ></el-col
            >
            <el-col :span="8"
              ><el-tag effect="plain"
                >考试时长：{{
                  examInfo.examTime === 0 ? '不限' : examInfo.examTime
                }}(分)</el-tag
              ></el-col
            >
            <el-col :span="8"
              ><el-tag effect="plain"
                >考试总分：{{
                  examInfo.scoreMethod === 0
                    ? '100'
                    : examInfo.unitScore * quesNum
                }}</el-tag
              ></el-col
            >
          </el-row>
          <el-row :gutter="20" style="text-align: center; margin-top: 10px">
            <el-col :span="8"
              ><el-tag effect="plain"
                >单题时长：{{
                  examInfo.quesTime == null ? '不限' : examInfo.quesTime
                }}(秒)</el-tag
              ></el-col
            >
            <el-col :span="8"
              ><el-tag
                :type="examInfo.skipQues === 0 ? 'danger' : ''"
                effect="plain"
                >{{
                  examInfo.skipQues === 0 ? '题目不可跳转' : '题目可跳转'
                }}</el-tag
              ></el-col
            >
            <el-col v-if="examInfo.ifBreak === 0" :span="8"
              ><el-tag type="danger" effect="plain"
                >考试不可中断</el-tag
              ></el-col
            >
            <el-col v-if="examInfo.wrongBreak === 0" :span="8"
              ><el-tag type="danger" effect="plain"
                >答错自动交卷</el-tag
              ></el-col
            >
            <el-col v-if="examInfo.optionRandom === 1" :span="8"
              ><el-tag type="danger" effect="plain">选项随机</el-tag></el-col
            >
          </el-row>
        </el-card>
        <el-card class="answer-controller">
          <template #header>
            <div class="clearfix">
              <span>答题控制区</span>
            </div>
          </template>
          <el-row v-if="quesTest.length > 0">
            <el-col
              v-for="(item, index) of quesTest"
              :key="index"
              :span="6"
              style="padding-top: 5px; text-align: center"
              ><el-button
                :type="item.userAnswer === null ? '' : 'success'"
                style="width: 70px"
                @click="changeQues(index)"
                size="small"
                :disabled="examInfo.skipQues === 0"
              >
                <font :class="curentQuesNum == index ? 'currentBtn' : ''"
                  >第{{ index + 1 }}题</font
                >
              </el-button></el-col
            >
          </el-row>
          <el-row style="padding-top: 20px">
            <el-col :span="24">
              <el-popconfirm
                title="注意！！！点击确认将提交本场考试！"
                confirm-button-text="确认交卷"
                @confirm="handOverTest()"
              >
                <template #reference>
                  <el-button
                    type="primary"
                    style="width: 100%"
                    size="small"
                    :disabled="mustReadFlag"
                  >
                    交 卷
                  </el-button>
                </template>
              </el-popconfirm>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog
      v-if="examResult"
      title="考试成绩"
      v-model="dialogVisible"
      width="50%"
      :show-close="false"
      :close-on-click-modal="false"
    >
      <el-result
        :icon="
          examResult.userScore == null || examResult.userScore == undefined
            ? 'error'
            : 'success'
        "
        :title="
          examResult.userScore == null || examResult.userScore == undefined
            ? '交卷失败！'
            : '交卷成功！'
        "
        :subTitle="'您得了【' + examResult.userScore + '】分！'"
      >
      </el-result>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="closeWindow">关 闭</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { examTestApi } from '@/api/edu/examTest'
import { ElMessage } from 'element-plus'
import Question from './component/Question.vue'

// 日期处理辅助函数
const formatDateTime = (date) => {
  if (!date) return ''
  const d = new Date(date)
  return d.toLocaleString('zh-CN')
}

const formatToUnix = (date) => {
  return Math.floor(new Date(date).getTime() / 1000)
}

const formatUtcTime = (seconds) => {
  if (seconds < 0) return 'N/A'
  const hours = Math.floor(seconds / 3600)
  const mins = Math.floor((seconds % 3600) / 60)
  const secs = seconds % 60
  return `${String(hours).padStart(2, '0')}:${String(mins).padStart(2, '0')}:${String(secs).padStart(2, '0')}`
}

// 响应式数据
const examInfo = ref({})
const quesTest = ref([])
const curentQuesNum = ref(-1)
const mustReadFlag = ref(true)
const startBtnFlag = ref(true)
const examScore = ref({})
const areaFlag = ref(false)
const timeErroInfo = ref('')
const examTime = ref(-1)
const timer = ref(null)
const dialogVisible = ref(false)
const examResult = ref(false)
const user = ref({})
const userId = ref('')
const questionRef = ref(null)

// 获取当前 Unix 时间戳
const getCurrentUnixTime = () => {
  return Math.floor(Date.now() / 1000)
}

// 考试预检查
const precheck = async () => {
  try {
    const res = await examTestApi.precheckExam(examInfo.value)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    examScore.value = res.examScore
    user.value = res.user
    areaFlag.value = !res.areaFlag
    examRule()
  } catch (error) {
    console.error('预检查失败:', error)
  }
}

// 考试规则检查
const examRule = () => {
  let testFlag = false
  if (examScore.value.compStat !== 0) {
    testFlag = true
    ElMessage.error('考试已完成或试卷已被阅卷！')
    return
  }
  const curUnixTime = getCurrentUnixTime()
  // 是否需要预约考试
  const examBegUnixTime = formatToUnix(examInfo.value.begDate)
  const examEndUnixTime = formatToUnix(examInfo.value.endDate)
  if (examInfo.value.bookExam === 0) {
    if (examBegUnixTime > curUnixTime || examEndUnixTime < curUnixTime) {
      timeErroInfo.value = '当前不在考试时间内！'
      testFlag = true
    }
  } else {
    if (examInfo.value.bookTime == null || examInfo.value.bookTime === '') {
      timeErroInfo.value = '该考试需预约考试时间，您尚未预约！'
      testFlag = true
    }
    const bookTimes = examInfo.value.bookTime.split('~')
    const bookBegUnixTime = formatToUnix(bookTimes[0])
    const bookEndUnixTime = formatToUnix(
      bookTimes[0].substring(0, 11) + bookTimes[1]
    )
    if (bookBegUnixTime > curUnixTime || bookEndUnixTime < curUnixTime) {
      timeErroInfo.value =
        '请在预约时间【' + examInfo.value.bookTime + '】内考试！'
      testFlag = true
    } else {
      // 判断当前考试剩余时间..直接设置一个定时器
      if (examScore.value.begDate == null) {
        examTime.value =
          examInfo.value.examTime * 60 + curUnixTime > bookEndUnixTime
            ? bookEndUnixTime - curUnixTime
            : examInfo.value.examTime * 60
        timerFunc(examTime.value)
      } else {
        const stratTime = formatToUnix(examScore.value.begDate)
        const leftSecond =
          stratTime + examInfo.value.examTime * 60 > examEndUnixTime
            ? examEndUnixTime - curUnixTime
            : examInfo.value.examTime - (curUnixTime - stratTime)
        timerFunc(leftSecond)
      }
    }
  }
  if (examInfo.value.bookExam === 0) {
    if (examScore.value.begDate == null) {
      examTime.value = examInfo.value.examTime * 60
    } else {
      const stratTime = formatToUnix(examScore.value.begDate)
      const leftSecond =
        examInfo.value.examTime * 60 - (curUnixTime - stratTime)
      examTime.value = leftSecond
      timerFunc(leftSecond)
    }
  }
  startBtnFlag.value = testFlag
}

// 初始化考试
const initTest = () => {
  return new Promise((resolve, reject) => {
    startBtnFlag.value = true
    examTestApi
      .initExam(examInfo.value)
      .then((res) => {
        if (curentQuesNum.value >= 0) return resolve()
        if (res.code !== 200) {
          ElMessage.error(res.msg)
          return reject()
        }
        quesTest.value = res.data
        startBtnFlag.value = false
        resolve()
      })
      .catch((error) => {
        reject(error)
      })
  })
}

// 切换题目
const changeQues = (o) => {
  submitAnswer()
  curentQuesNum.value = o
  questionRef.value.show(quesTest.value[o], examInfo.value.optionRand)
}

// 下一题
const nextQuestion = () => {
  if (curentQuesNum.value < 0) {
    initTest().then(() => {
      // 还需判断是否不可跳过答题，若是，则需确认当前是否有已完成的答题
      if (examInfo.value.skipQues === 0) {
        curentQuesNum.value =
          examInfo.value.quesNum -
          quesTest.value.filter((e) => e.handleDate == null).length -
          1
      }
      if (timer.value == null) timerFunc(examTime.value)
      if (curentQuesNum.value + 1 >= examInfo.value.quesNum) {
        mustReadFlag.value = false
        ElMessage.error('因为您已完成所有题目的提交，请点击右边的交卷！')
        return
      }
      curentQuesNum.value++
      mustReadFlag.value = false
      questionRef.value.show(
        quesTest.value[curentQuesNum.value],
        examInfo.value.optionRand
      )
    })
  } else {
    submitAnswer()
    if (curentQuesNum.value + 1 >= examInfo.value.quesNum) {
      ElMessage.error('已经没有下一题了！')
      return
    }
    curentQuesNum.value++
    changeQues(curentQuesNum.value)
  }
}

// 获取用户答案
const getUserAnswer = (userAnswer) => {
  quesTest.value[curentQuesNum.value].userAnswer = userAnswer
}

// 提交答案
const submitAnswer = async () => {
  if (
    curentQuesNum.value < 0 ||
    curentQuesNum.value >= examInfo.value.quesNum
  ) {
    ElMessage.error('没有题目可以提交！' + curentQuesNum.value)
    return
  }
  try {
    const res = await examTestApi.submitAnswer(
      examInfo.value.wrongBreak,
      quesTest.value[curentQuesNum.value]
    )
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    if (res.msg === 'break') {
      ElMessage.warning('答错自动交卷！')
      handOverTest()
    }
  } catch (error) {
    console.error('提交答案失败:', error)
  }
}

// 交卷
const handOverTest = () => {
  submitAnswer()
  examTestApi
    .handOverTest(examScore.value)
    .then((res) => {
      if (res.code !== 200) {
        ElMessage.error(res.msg)
        return
      }
      examResult.value = res.data
      dialogVisible.value = true
    })
    .then(() => {
      if (window.opener) {
        window.opener.location.reload()
      }
    })
}

// 计时器函数
const timerFunc = (time) => {
  timer.value = setInterval(() => {
    time--
    if (time < examTime.value) {
      examTime.value = time
    }
    if (examTime.value === 0) {
      clearInterval(timer.value)
      timer.value = null
      handOverTest()
    }
  }, 1000)
}

// 关闭窗口
const closeWindow = () => {
  window.location.href = 'about:blank'
  window.close()
}

// 页面卸载处理
const handleUnload = () => {
  if (!examResult.value) submitAnswer()
}

// 生命周期钩子
onMounted(() => {
  userId.value = JSON.parse(window.localStorage.getItem('user')).ploNum
  examInfo.value = JSON.parse(sessionStorage.getItem('examInfo'))
  precheck()
  window.addEventListener('unload', handleUnload)
})

onUnmounted(() => {
  if (timer.value) {
    clearInterval(timer.value)
    timer.value = null
  }
  window.removeEventListener('unload', handleUnload)
})
</script>

<style lang="less" scoped>
.container {
  padding: 20px;
  .box-card {
    height: calc(100vh - 120px);
    :deep(.el-card__body) {
      height: calc(100vh - 220px);
      overflow-y: scroll;
    }
  }
}
.answer-controller {
  margin-bottom: 20px;
}
.currentBtn {
  color: red;
  font-weight: 700;
}
</style>
