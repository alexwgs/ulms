<template>
  <div :loading="loadingFlag">
    <el-steps
      ref="taskStepsRef"
      :active="currentStep"
      simple
      finish-status="success"
    >
      <el-step title="心情打卡" :icon="Star" v-if="tabFlag.mood"></el-step>
      <el-step title="今日身份" :icon="User" v-if="tabFlag.identity"></el-step>
      <el-step
        title="公布栏学习"
        :icon="Reading"
        v-if="tabFlag.artical"
      ></el-step>
      <el-step
        title="每日一招"
        :icon="Calendar"
        v-if="tabFlag.question"
      ></el-step>
    </el-steps>
    <div class="main-content">
      <div v-if="moodPic" v-show="currentItem === '心情打卡'">
        <el-row align="middle">
          <div>
            <el-alert
              title="点击选择你今天的心情哦，点击直接提交！"
              type="success"
              center
              :closable="false"
            ></el-alert>
          </div>
          <el-col
            v-for="mood in moodPic"
            :span="8"
            :key="mood.id"
            style="height: 120px"
          >
            <div class="moon-class" @click="submitMood(mood.moodType)">
              <el-image
                style="width: 100px; height: 100px"
                :src="mood.moodPic"
                fit="fill"
                :alt="mood.moodInfo"
              ></el-image>
            </div>
          </el-col>
        </el-row>
      </div>
      <div v-show="currentItem === '今日身份'">
        <div style="width: 100%; text-align: center; padding-top: 15px">
          <h4>请选择今日身份</h4>
        </div>
        <el-row>
          <el-col :span="24">
            <el-checkbox-group v-model="identityArry" size="small" :max="2">
              <el-checkbox
                style="margin-top: 10px"
                label=""
                @change="handleNoIdentityChange"
                border
                >无身份</el-checkbox
              >
              <el-checkbox
                style="margin-top: 10px"
                v-for="item in identityList"
                :key="item.id"
                :disabled="disabledFlag"
                :label="item.statusName"
                border
              ></el-checkbox>
            </el-checkbox-group>
          </el-col>
        </el-row>
      </div>
      <div v-show="currentItem === '公布栏学习'">
        <h4>{{ artical.title }}</h4>
        <div class="main-container">
          <div v-html="artical.content"></div>
        </div>
        <div style="width: 100%; text-align: center; padding-top: 15px">
          <el-button
            type="primary"
            size="small"
            v-if="dailyScore && dailyScore.valid === 0"
            @click="submitArticalStudy()"
            >学完了</el-button
          >
        </div>
      </div>
      <div v-if="question" v-show="currentItem === '每日一招'">
        <div class="main-container" v-if="question.quesType">
          <div class="ques-title">
            [{{
              question.quesType === 2
                ? '多选题'
                : question.quesType === 3
                  ? '判断题'
                  : '单选题'
            }}]{{ question.quesStem }}
          </div>
          <div
            class="file-view"
            v-if="question.fileType === 'jpg' || question.fileType === 'png'"
          >
            <el-image
              style="width: 100px; height: 100px"
              :src="fileUrl"
              :preview-src-list="srcList"
            ></el-image>
          </div>
          <div v-else-if="question.fileType === 'mp4'">
            <video :src="fileUrl" controls height="400px"></video>
          </div>
          <div v-else-if="question.fileType === 'mp3'">
            <audio :src="fileUrl" controls width="300px"></audio>
          </div>
          <div class="ques-options" v-if="question.quesType === 2">
            <el-checkbox
              v-for="(item, index) in options"
              :key="index"
              v-model="answer.checkbox"
              class="ques-option"
              @change="submitAnswer"
              :label="item"
              border
              >{{ item }}</el-checkbox
            >
          </div>
          <div class="ques-options" v-else>
            <el-radio
              v-for="(item, index) in options"
              :key="index"
              v-model="answer.radio"
              class="ques-option"
              @change="submitAnswer"
              :label="item"
              border
              >{{ item }}</el-radio
            >
          </div>
          <div v-if="quesExplainFlag">答案解析:{{ question.quesExplain }}</div>
        </div>
        <div style="width: 100%; text-align: center; padding-top: 15px">
          <el-button
            type="primary"
            v-if="dailyScore && dailyScore.valid === 0"
            @click="submitQuestion()"
            >确 定</el-button
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed, nextTick } from 'vue'
import { ElMessage, ElNotification } from 'element-plus'
import { Star, User, Reading, Calendar } from '@element-plus/icons-vue'
import {
  getDailyQuestionInit,
  getQuestionBank,
  getMoodPic as apiGetMoodPic,
  getArticalDetail,
  getIdentityList,
  submitMood as apiSubmitMood,
  submitArticalStudy as apiSubmitArticalStudy,
  submitQuestion as apiSubmitQuestion
} from '@/api/welcome/index.js'

const fsURL = import.meta.env.VITE_FILE_MANAGE_BASE || ''

const taskStepsRef = ref(null)
const loadingFlag = ref(false)
const dailyConfig = ref({})
const dailyScore = ref({})
const tabFlag = reactive({
  mood: false,
  identity: false,
  artical: false,
  question: false
})
const artical = ref({})
const currentStep = ref(1)
const currentItem = ref('')
const question = ref({})
const fileUrl = ref('')
const srcList = ref([])
const options = ref([])
const answer = reactive({ radio: '', checkbox: [] })
const quesExplainFlag = ref(false)
const identityList = ref([])
const identityArry = ref([])
const moodPic = ref({})
const studyDate = reactive({ studyBeg: null, studyEnd: null })
const disabledFlag = ref(false)

const init = (quesDate) => {
  loadingFlag.value = true
  quesExplainFlag.value = false
  Object.assign(tabFlag, {
    mood: false,
    identity: false,
    artical: false,
    question: false
  })
  currentStep.value = 1

  getDailyQuestionInit(quesDate)
    .then((res) => {
      if (res.code !== 200) {
        ElMessage.error(res.msg)
        return
      }
      if (res.flag) {
        dailyConfig.value = res.dailyConfig
        dailyScore.value = res.dailyScore
      } else {
        dailyConfig.value = null
        dailyScore.value = null
        Object.assign(tabFlag, {
          mood: false,
          identity: false,
          artical: false,
          question: false
        })
        ElMessage.warning(res.msg)
      }
    })
    .then(() => {
      if (dailyScore.value.valid === 0) {
        if (quesDate) {
          if (dailyConfig.value.articalId) tabFlag.artical = true
          tabFlag.question = true
        } else {
          tabFlag.question = true
          tabFlag.identity =
            hasPermission('main:directorStatus:manager') &&
            getStoreIdentity() === '0'
          if (dailyScore.value.moodType) {
            tabFlag.mood = false
          } else {
            tabFlag.mood = true
          }
          if (dailyConfig.value.articalId) tabFlag.artical = true
          else tabFlag.artical = false
        }
      } else {
        if (dailyConfig.value.articalId) tabFlag.artical = true
      }
      dataLoading()
    })
    .then(() => {
      nextTick(() => {
        setTimeout(() => {
          if (
            taskStepsRef.value &&
            taskStepsRef.value.$children &&
            taskStepsRef.value.$children[currentStep.value - 1]
          ) {
            currentItem.value =
              taskStepsRef.value.$children[currentStep.value - 1].title
          }
          studyDate.studyBeg = formatDateTime()
        }, 300)
      })
    })
    .catch((err) => {
      console.error(err)
    })
    .finally(() => {
      loadingFlag.value = false
    })
}

const dataLoading = () => {
  if (tabFlag.mood) getMoodPic()
  if (tabFlag.identity) getIdentity()
  if (tabFlag.question) getQuestion()
  if (tabFlag.artical) getArtical()
}

const getQuestion = () => {
  getQuestionBank(dailyScore.value.quesCode).then((res) => {
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    question.value = res.data
    options.value = res.data.options.split(',')
    if (question.value.fileType != null) {
      srcList.value = []
      fileUrl.value =
        fsURL +
        'upload/getFile/edu/' +
        question.value.fileCode +
        '.' +
        question.value.fileType
      srcList.value.push(
        fsURL +
          'upload/getFile/edu/' +
          question.value.fileCode +
          '.' +
          question.value.fileType
      )
    }
    if (question.value.optionRand === 1) {
      options.value.sort(() => 0.5 - Math.random())
    }
    answer.radio = ''
    answer.checkbox = []
  })
}

const getIdentity = () => {
  getIdentityList().then((res) => {
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    identityList.value = res.data
  })
}

const getMoodPic = () => {
  apiGetMoodPic().then((res) => {
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    moodPic.value = res.data
  })
}

const getArtical = () => {
  getArticalDetail(dailyConfig.value.articalId).then((res) => {
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    artical.value = res.data
  })
}

const submitMood = async (moodType) => {
  if (moodType == null || moodType === '') {
    ElMessage.error('请选择心情！')
    return
  }
  dailyScore.value.moodType = moodType
  try {
    const res = await apiSubmitMood(dailyScore.value)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    nextStep()
  } catch (error) {
    ElMessage.error('提交失败')
  }
}

const sendIdentityChangeMsg = () => {
  if (identityArry.value.length < 1) {
    ElMessage.error('请至少选择一个身份！')
    return
  }
  const identityChange = {
    modal: 'oht',
    type: 'identity',
    content: '',
    userStatus: null
  }
  identityChange.userStatus = getStoreUserStatus()
  identityChange.content = identityArry.value.join(',')
  sendWebSocketMessage(identityChange)
  setStoreIdentity(identityArry.value.join(','))
  nextStep()
}

const submitArticalStudy = async () => {
  studyDate.studyEnd = formatDateTime()
  if (dailyScore.value.studyBeg && dailyScore.value.studyBeg) {
    nextStep()
  } else {
    dailyScore.value.studyBeg = studyDate.studyBeg
    dailyScore.value.studyEnd = studyDate.studyEnd
    dailyScore.value.articalStatus = 1
    try {
      const res = await apiSubmitArticalStudy(dailyScore.value)
      if (res.code !== 200) {
        ElMessage.error(res.msg)
        return
      }
    } catch (error) {
      ElMessage.error('提交失败')
      return
    }
  }
  nextStep()
}

const submitQuestion = async () => {
  const ans = answer.checkbox.join(',') + answer.radio
  if (ans == null || ans.length < 1) {
    ElMessage.error('请先选择答题的选项！')
    return
  }
  question.value.category = ans
  try {
    const res = await apiSubmitQuestion({
      question: JSON.stringify(question.value),
      dailyScore: JSON.stringify(dailyScore.value)
    })
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    dailyScore.value = res.data
    if (res.data.valid === 0) {
      ElNotification.error({
        title: '答题错误',
        message: '正确答案：[' + question.value.answer + ']'
      })
      quesExplainFlag.value = true
    } else {
      ElNotification.success({ title: '答题正确', message: '任务完成！' })
    }
  } catch (error) {
    ElMessage.error('提交失败')
  }
}

const nextStep = () => {
  currentStep.value++
  if (taskStepsRef.value && taskStepsRef.value.$children) {
    if (taskStepsRef.value.$children.length < currentStep.value - 1) {
      ElMessage.error('任务数量和当前任务出错！')
      return
    }
    if (taskStepsRef.value.$children[currentStep.value - 1]) {
      currentItem.value =
        taskStepsRef.value.$children[currentStep.value - 1].title
    }
  }
}

const handleNoIdentityChange = (event) => {
  disabledFlag.value = event
  if (event) identityArry.value = ['']
}

const hasPermission = (permission) => {
  try {
    const global = window.__POWERED_BY_QIANKUN__ ? window.$global : null
    if (global && global.hasPermission) {
      return global.hasPermission(permission)
    }
    return true
  } catch (error) {
    return true
  }
}

const getStoreIdentity = () => {
  try {
    const store = window.__POWERED_BY_QIANKUN__ ? window.$store : null
    return store?.state?.identity || '0'
  } catch {
    return '0'
  }
}

const setStoreIdentity = (value) => {
  try {
    const store = window.__POWERED_BY_QIANKUN__ ? window.$store : null
    if (store) {
      store.commit('setIdentity', value)
    }
  } catch (error) {
    console.error('设置身份失败', error)
  }
}

const getStoreUserStatus = () => {
  try {
    const store = window.__POWERED_BY_QIANKUN__ ? window.$store : null
    return store?.state?.userStatus || 0
  } catch {
    return 0
  }
}

const sendWebSocketMessage = (data) => {
  try {
    const ws = window.__POWERED_BY_QIANKUN__ ? window.$ws?.ws : null
    if (ws) {
      ws.send(JSON.stringify(data))
    }
  } catch (error) {
    console.error('发送WebSocket消息失败', error)
  }
}

const formatDateTime = () => {
  const now = new Date()
  const year = now.getFullYear()
  const month = String(now.getMonth() + 1).padStart(2, '0')
  const day = String(now.getDate()).padStart(2, '0')
  const hours = String(now.getHours()).padStart(2, '0')
  const minutes = String(now.getMinutes()).padStart(2, '0')
  const seconds = String(now.getSeconds()).padStart(2, '0')
  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
}

defineExpose({
  init
})
</script>

<style lang="less" scoped>
.main-content {
  height: 450px;
  overflow-y: auto;
  overflow-x: hidden;
}
.main-container {
  font-size: 14px;
  line-height: 1.7;
  .ques-title {
    font-weight: 500;
    line-height: 1.7;
    font-size: 18px;
  }
  .ques-options {
    .ques-option {
      margin: 10px 0 0 0;
      text-overflow: ellipsis;
      white-space: normal;
      line-height: 1.6;
      display: block;
    }
    :deep(.el-checkbox__label),
    :deep(.el-radio__label) {
      display: inline;
    }
    .el-radio.is-bordered,
    .el-checkbox.is-bordered {
      padding: 8px 20px 8px 10px;
      border-radius: 4px;
      box-sizing: border-box;
      height: 100%;
    }
  }
}
:deep(.el-drawer) {
  padding: 15px;
  margin: 20px 20px 0 20px;
  border-radius: 20px;
  width: unset;
}
.is-simple.el-step {
  min-width: 250px;
}
.submit-container {
  margin-top: 20px;
}
.moon-class {
  cursor: pointer;
  width: 100px;
  padding: 5px;
  box-shadow:
    0 2px 4px rgba(0, 0, 0, 0.12),
    0 0 6px rgba(0, 0, 0, 0.04);
  margin-left: 30%;
}
.moon-class:hover {
  border: 3px rgba(196, 32, 32, 0.6) solid;
}
</style>
