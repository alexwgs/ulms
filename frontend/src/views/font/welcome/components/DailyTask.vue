<template>
  <div :loading="loadingFlag">
    <t-steps
      ref="taskStepsRef"
      :current="currentStep"
      simple
      finish-status="success"
    >
      <t-step-item title="心情打卡" v-if="tabFlag.mood"><template #icon><DynamicIcon name="star" /></template></t-step-item>
      <t-step-item title="今日身份" v-if="tabFlag.identity"><template #icon><DynamicIcon name="user" /></template></t-step-item>
      <t-step-item
        title="公布栏学习" v-if="tabFlag.article"
      ><template #icon><DynamicIcon name="book-open" /></template></t-step-item>
      <t-step-item
        title="每日一招" v-if="tabFlag.question"
      ><template #icon><DynamicIcon name="calendar" /></template></t-step-item>
    </t-steps>
    <div class="main-content">
      <div v-if="moodPic" v-show="currentItem === '心情打卡'">
        <div>
          <t-alert
            title="点击选择你今天的心情哦，点击直接提交！"
            theme="success"
            center
            :closable="false"
          ></t-alert>
        </div>
        <t-row align="middle">
          <t-col
            v-for="mood in moodPic"
            :span="4"
            :key="mood.id"
            style="height: 120px"
          >
            <div class="moon-class" @click="submitMood(mood.moodType)">
              <t-image
                style="width: 100px; height: 100px"
                :src="mood.moodPic"
                fit="fill"
                :alt="mood.moodInfo"
              ></t-image>
            </div>
          </t-col>
        </t-row>
      </div>
      <div v-show="currentItem === '今日身份'">
        <div style="width: 100%; text-align: center; padding-top: 15px">
          <h4>请选择今日身份</h4>
        </div>
        <t-row>
          <t-col :span="12">
            <t-checkbox-group v-model="identityArry" size="small" :max="2">
              <t-checkbox
                style="margin-top: 10px"
                value=""
                @change="handleNoIdentityChange"
                border
                >无身份</t-checkbox
              >
              <t-checkbox
                style="margin-top: 10px"
                v-for="item in identityList"
                :key="item.id"
                :disabled="disabledFlag"
                :value="item.statusName"
                border
              ></t-checkbox>
            </t-checkbox-group>
          </t-col>
        </t-row>
      </div>
      <div v-show="currentItem === '公布栏学习'">
        <h4>{{ article.title }}</h4>
        <div class="main-container">
          <div v-html="article.content"></div>
        </div>
        <div style="width: 100%; text-align: center; padding-top: 15px">
          <t-button
            theme="primary"
            size="small"
            v-if="dailyScore && dailyScore.valid === 0"
            @click="submitArticleStudy()"
            >学完了</t-button
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
            <t-image
              style="width: 100px; height: 100px"
              :src="fileUrl"
              :preview-src-list="srcList"
            ></t-image>
          </div>
          <div v-else-if="question.fileType === 'mp4'">
            <video :src="fileUrl" controls height="400px"></video>
          </div>
          <div v-else-if="question.fileType === 'mp3'">
            <audio :src="fileUrl" controls width="300px"></audio>
          </div>
          <div class="ques-options" v-if="question.quesType === 2">
            <t-checkbox
              v-for="(item, index) in options"
              :key="index"
              v-model="answer.checkbox"
              class="ques-option"
              @change="submitAnswer"
              :value="item"
              border
              >{{ item }}</t-checkbox
            >
          </div>
          <div class="ques-options" v-else>
            <t-radio
              v-for="(item, index) in options"
              :key="index"
              v-model="answer.radio"
              class="ques-option"
              @change="submitAnswer"
              :value="item"
              border
              >{{ item }}</t-radio
            >
          </div>
          <div v-if="quesExplainFlag">答案解析:{{ question.quesExplain }}</div>
        </div>
        <div style="width: 100%; text-align: center; padding-top: 15px">
          <t-button
            theme="primary"
            v-if="dailyScore && dailyScore.valid === 0"
            @click="submitQuestion()"
            >确 定</t-button
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed, nextTick } from 'vue'
import { MessagePlugin, NotifyPlugin } from 'tdesign-vue-next'
import { useOhtStore, useWsStore } from '@/stores'
import { StarIcon, UserIcon, BookOpenIcon, CalendarIcon } from 'tdesign-icons-vue-next'
import {
  getDailyQuestionInit,
  getQuestionBank,
  getMoodPic as apiGetMoodPic,
  getArticleDetail,
  getIdentityList,
  submitMood as apiSubmitMood,
  submitArticleStudy as apiSubmitArticleStudy,
  submitQuestion as apiSubmitQuestion
} from '@/api/welcome/index.js'

const fsURL = import.meta.env.VITE_FILE_BASE_URL || ''

const taskStepsRef = ref(null)
const loadingFlag = ref(false)
const dailyConfig = ref({})
const dailyScore = ref({})
const tabFlag = reactive({
  mood: false,
  identity: false,
  article: false,
  question: false
})
const article = ref({})
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
    article: false,
    question: false
  })
  currentStep.value = 1

  getDailyQuestionInit(quesDate)
    .then((res) => {
      if (res.code !== 200) {
        MessagePlugin.error(res.msg)
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
          article: false,
          question: false
        })
        MessagePlugin.warning(res.msg)
      }
    })
    .then(() => {
      if (dailyScore.value.valid === 0) {
        if (quesDate) {
          if (dailyConfig.value.articleId) tabFlag.article = true
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
          if (dailyConfig.value.articleId) tabFlag.article = true
          else tabFlag.article = false
        }
      } else {
        if (dailyConfig.value.articleId) tabFlag.article = true
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
  if (tabFlag.article) getArticle()
}

const getQuestion = () => {
  getQuestionBank(dailyScore.value.quesCode).then((res) => {
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
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
      MessagePlugin.error(res.msg)
      return
    }
    identityList.value = res.data
  })
}

const getMoodPic = () => {
  apiGetMoodPic().then((res) => {
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    moodPic.value = res.data
  })
}

const getArticle = () => {
  getArticleDetail(dailyConfig.value.articleId).then((res) => {
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    article.value = res.data
  })
}

const submitMood = async (moodType) => {
  if (moodType == null || moodType === '') {
    MessagePlugin.error('请选择心情！')
    return
  }
  dailyScore.value.moodType = moodType
  try {
    const res = await apiSubmitMood(dailyScore.value)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    nextStep()
  } catch (error) {
    MessagePlugin.error('提交失败')
  }
}

const sendIdentityChangeMsg = () => {
  if (identityArry.value.length < 1) {
    MessagePlugin.error('请至少选择一个身份！')
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

const submitArticleStudy = async () => {
  studyDate.studyEnd = formatDateTime()
  if (dailyScore.value.studyBeg && dailyScore.value.studyBeg) {
    nextStep()
  } else {
    dailyScore.value.studyBeg = studyDate.studyBeg
    dailyScore.value.studyEnd = studyDate.studyEnd
    dailyScore.value.articleStatus = 1
    try {
      const res = await apiSubmitArticleStudy(dailyScore.value)
      if (res.code !== 200) {
        MessagePlugin.error(res.msg)
        return
      }
    } catch (error) {
      MessagePlugin.error('提交失败')
      return
    }
  }
  nextStep()
}

const submitQuestion = async () => {
  const ans = answer.checkbox.join(',') + answer.radio
  if (ans == null || ans.length < 1) {
    MessagePlugin.error('请先选择答题的选项！')
    return
  }
  question.value.category = ans
  try {
    const res = await apiSubmitQuestion({
      question: JSON.stringify(question.value),
      dailyScore: JSON.stringify(dailyScore.value)
    })
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    dailyScore.value = res.data
    if (res.data.valid === 0) {
      NotifyPlugin.error({
        title: '答题错误',
        message: '正确答案：[' + question.value.answer + ']'
      })
      quesExplainFlag.value = true
    } else {
      NotifyPlugin.success({ title: '答题正确', message: '任务完成！' })
    }
  } catch (error) {
    MessagePlugin.error('提交失败')
  }
}

const nextStep = () => {
  currentStep.value++
  if (taskStepsRef.value && taskStepsRef.value.$children) {
    if (taskStepsRef.value.$children.length < currentStep.value - 1) {
      MessagePlugin.error('任务数量和当前任务出错！')
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

// 前端权限体系尚未建立，暂时保持放行
const hasPermission = () => true

const ohtStore = useOhtStore()
const wsStore = useWsStore()

const getStoreIdentity = () => ohtStore.identity || '0'

const setStoreIdentity = (value) => {
  ohtStore.setIdentity(value)
}

const getStoreUserStatus = () => ohtStore.userStatus || 0

const sendWebSocketMessage = (data) => {
  wsStore.sendMessage(data)
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
    :deep(.t-checkbox__label),
    :deep(.t-radio__label) {
      display: inline;
    }
    .t-radio.is-bordered,
    .t-checkbox.is-bordered {
      padding: 8px 20px 8px 10px;
      border-radius: 4px;
      box-sizing: border-box;
      height: 100%;
    }
  }
}
:deep(.t-drawer) {
  padding: 15px;
  margin: 20px 20px 0 20px;
  border-radius: 20px;
  width: unset;
}
.is-simple.t-step-item {
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
