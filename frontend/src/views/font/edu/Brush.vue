<template>
  <t-loading :loading="loading">
    <div class="container">
    <div class="brush-info">
      <span>累计答题天数： {{ brushConfig.monthCount }} /
        {{ brushConfig.monthLimit === 0 ? '∞' : brushConfig.monthLimit }}
      </span>
      <t-divider layout="vertical"></t-divider>
      <span>今日答题数： {{ brushConfig.dayCount }} /
        {{ brushConfig.dayLimit === 0 ? '∞' : brushConfig.dayLimit }}</span>
      <t-divider layout="vertical"></t-divider>
      <span>答题计时：{{ answerTime === 0 ? '∞' : answerTime }} (S)</span>
      <t-divider layout="vertical"></t-divider>
      <t-space>所属分组：
      <t-select style="width: 120px" size="small" v-model="groupSelected" placeholder="请选择">
        <t-option label="未参与分组" :value="-1" disabled></t-option>
        <t-option :label="brushConfig.groupName" :value="brushConfig.groupId">
        </t-option>
      </t-select></t-space>
      <t-divider></t-divider>
    </div>
    <div v-if="currentShow === 'cover'">
      <t-row>
        <t-col :span="7">
          <div class="notice-item">
            <h2>注意事项：</h2>
            <ul>
              <li>
                每日刷题上限<text class="focus-font">{{
                  brushConfig.dayLimit === 0 ? '∞' : brushConfig.dayLimit
                }}</text>题，月度累计刷题天数上限<text class="focus-font">{{
                    brushConfig.monthLimit === 0 ? '∞' : brushConfig.monthLimit
                  }}</text>天
              </li>
              <li>
                若刷题过程中终止答题或关闭窗口则当前题目不会提交，会默认答题错误！
              </li>
              <li>
                可在任意时间开启答题，不限制打卡次数，但需符合答题量条件！
              </li>
              <li>
                每题答题时长上线为<text class="focus-font">{{
                  brushConfig.answerTime === 0 ? '∞' : brushConfig.answerTime
                  }} </text>秒，超时将自动提交！
              </li>
            </ul>
          </div>
        </t-col>
        <t-col :span="5">
          <div class="slogan">
            <div>
              <t-button shape="round" style="margin: 40px 30px 50px 30px" class="mybutton" @click="startBrushBtn()" :disabled="(brushConfig.monthLimit !== 0 &&
                  brushConfig.monthCount >= brushConfig.monthLimit) ||
                  (brushConfig.dayLimit !== 0 &&
                    brushConfig.dayCount >= brushConfig.dayLimit)
                  ">开 始 答 题</t-button>
            </div>
            <div style="font-size: 20px; color: #fff">
              千万种烦恼，答一题就好！
            </div>
            <img class="brush-icon" src="../../../assets/img/edu/brush-icon.png" />
          </div>
        </t-col>
      </t-row>
    </div>
    <div v-if="currentShow === 'question'">
      <div class="main-container">
        <div class="ques-title">
          [{{
            question.quesType === 2
              ? '多选题'
              : question.quesType === 3
                ? '判断题'
                : '单选题'
          }}]{{ question.quesStem }}
        </div>
        <div class="file-view" v-if="question.fileType === 'jpg' || question.fileType === 'png'">
          <t-image style="width: 100px; height: 100px" :src="fileUrl" :preview-src-list="srcList"></t-image>
        </div>
        <div v-else-if="question.fileType === 'mp4'">
          <video :src="fileUrl" controls height="400px"></video>
        </div>
        <div v-else-if="question.fileType === 'mp3'">
          <audio :src="fileUrl" controls width="300px"></audio>
        </div>
        <div class="ques-options" v-if="question.quesType === 2">
          <t-checkbox-group v-model="checkbox">
            <t-checkbox v-for="item in options" :key="item" class="ques-option" :value="item"
              border>{{ item }}</t-checkbox>
          </t-checkbox-group>
        </div>
        <div class="ques-options" v-else>
          <t-radio-group v-model="radio">
            <t-radio v-for="item in options" :key="item" class="ques-option" :value="item" border>{{ item
            }}</t-radio>
          </t-radio-group>
        </div>
        <div class="answer-area" v-if="resultFlag">
          <div class="result-icon">
            <img v-if="result.passFlag" src="../../../assets/img/edu/answer-right.png" width="100px" />
            <img v-else src="../../../assets/img/edu/answer-wrong.png" width="100px" />
          </div>
          <div class="result-info">
            <h2 v-if="result.passFlag">回答正确</h2>
            <h2 style="color: red" v-else>回答错误</h2>
            <div style="display: inline-block">
              正确答案：{{ question.answer }}<br />答案解析：{{
                question.quesExplain
              }}<br />知识库：{{ question.knowledge }}
            </div>
          </div>
        </div>
      </div>
      <div class="ques-footer">
        <t-button shape="round" :disabled="!submitAndNextFlag" @click="validateSubmit()">提交答案</t-button>
        <t-button shape="round" :disabled="submitAndNextFlag" @click="nextQuestion()"
          v-if="brushConfig.dayCount !== brushConfig.dayLimit">下一题</t-button>
        <div style="display: inline-block; float: right">
          <t-space>
            <t-button @click="collect(question.quesCode)"><template #icon><DynamicIcon name="star" /></template></t-button>
            <t-button theme="danger" style="float: right" @click="disputeBtn()"><template #icon><DynamicIcon name="error-circle" /></template></t-button>
          </t-space>
        </div>
      </div>
    </div>
    </div>
  </t-loading>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { brushApi } from '@/api/edu/brush'
import { questionBankApi } from '@/api/edu/questionBank'
import { questionCollectApi } from '@/api/edu/questionCollect'
import { questionDisputeApi } from '@/api/edu/questionDispute'
import { MessagePlugin } from 'tdesign-vue-next'
import { NotificationIcon, StarIcon, ErrorCircleIcon } from 'tdesign-icons-vue-next'
import { usePrompt } from '@/hooks/usePrompt'

const fsURL = import.meta.env.VITE_FILE_BASE_URL

const brushConfig = reactive({
  monthLimit: 0,
  dayLimit: 0,
  answerTime: 0,
  dayCount: 0,
  monthCount: 0,
  groupId: -1,
  groupName: '暂未分组'
})

const question = reactive({
  quesStem: '',
  quesType: 1,
  fileCode: null,
  fileType: null,
  fileDuration: null,
  options: '',
  answer: '',
  quesExplain: '',
  quesStat: 1,
  category: '',
  knowledge: ''
})

const currentShow = ref('cover')
const groupSelected = ref(-1)
const submitAndNextFlag = ref(true)
const answerCode = ref('')
const radio = ref('')
const checkbox = ref([])
const options = ref([])
const loading = ref(true)
const fileUrl = ref('')
const srcList = ref([])
const result = ref({})
const resultFlag = ref(false)
const answerTime = ref(0)
let timer = null

const getBrushConfig = async () => {
  const res = await brushApi.getBrushConfig()
  if (res.code !== 200) {
    MessagePlugin.error(res.msg)
    return
  }
  Object.assign(brushConfig, res.data)
  answerTime.value = res.data.answerTime
  loading.value = false
  groupSelected.value = res.data.groupName
}

const getQuestion = async () => {
  if (
    brushConfig.dayLimit !== 0 &&
    brushConfig.dayCount >= brushConfig.dayLimit
  ) {
    MessagePlugin.error('您的刷题已达上限！')
    return
  }
  loading.value = true
  const res = await questionBankApi.extractQuestions(brushConfig)
  if (res.code !== 200) {
    MessagePlugin.error(res.msg)
    loading.value = false
    return
  }
  Object.assign(question, res.data.question)
  answerCode.value = res.data.journo
  options.value = question.options.split(',')

  if (question.fileType != null) {
    srcList.value = []
    fileUrl.value =
      fsURL +
      'upload/getFile/edu/' +
      question.fileCode +
      '.' +
      question.fileType
    srcList.value.push(
      fsURL +
      'upload/getFile/edu/' +
      question.fileCode +
      '.' +
      question.fileType
    )
  }

  if (brushConfig.optionRand === 1 && question.quesType === 2) {
    options.value.sort(function () {
      return 0.5 - Math.random()
    })
  }

  brushConfig.dayCount++
  loading.value = false

  if (brushConfig.answerTime !== 0) {
    if (question.fileDuration !== null) {
      answerTime.value = brushConfig.answerTime + question.fileDuration
    } else {
      answerTime.value = brushConfig.answerTime
    }
    timerFunc(answerTime.value)
  }
}

const startBrushBtn = () => {
  getQuestion()
  currentShow.value = 'question'
}

const validateSubmit = () => {
  const type = question.quesType
  if (type === 1) {
    if (radio.value === '') {
      MessagePlugin.warning('请先选择答案！')
      return
    }
  } else if (type === 2) {
    if (checkbox.value.length < 2) {
      MessagePlugin.warning('多选题请至少选择一个选项！')
      return
    }
  } else {
    if (radio.value === '') {
      MessagePlugin.warning('请先选择答案！')
      return
    }
  }
  submitAnswer()
}

const submitAnswer = async () => {
  if (timer) {
    clearInterval(timer)
    timer = null
  }
  const brushScoreForm = {
    journo: answerCode.value,
    quesCode: question.quesCode,
    answer: radio.value + checkbox.value.join(',')
  }
  const res = await brushApi.submitAnswer(brushScoreForm)
  if (res.code !== 200) {
    MessagePlugin.error(res.msg)
    return
  }
  result.value = res.data
  Object.assign(question, res.data.question)
  submitAndNextFlag.value = false
  resultFlag.value = true
}

const nextQuestion = () => {
  radio.value = ''
  checkbox.value = []
  resultFlag.value = false
  submitAndNextFlag.value = true
  getQuestion()
}

const timerFunc = (time) => {
  timer = setInterval(() => {
    answerTime.value = time--
    if (answerTime.value <= 0) {
      submitAnswer()
    }
  }, 1000)
}

const collect = async (quesCode) => {
  const res = await questionCollectApi.collectQuestion(quesCode)
  if (res.code !== 200) {
    MessagePlugin.error(res.msg)
    return
  }
  MessagePlugin.success(res.msg)
}

const submitDispute = async (record) => {
  const res = await questionDisputeApi.createDisputeByExam(record)
  if (res.code !== 200) {
    MessagePlugin.error(res.msg)
    return
  }
  MessagePlugin.success(res.msg)
}

const disputeBtn = async () => {
  const { prompt } = usePrompt()
  const value = await prompt({
    title: '题目问题反馈',
    placeholder: '请描述该题目问题的内容：',
    pattern: /^.{2,250}$/,
    errorMessage: '复议理由不可为空，最长不超过250个字'
  })
  if (value === null) return
  submitDispute({
    quesCode: question.quesCode,
    userAnswer: radio.value + checkbox.value.join(','),
    disputeMemo: value
  })
}

onMounted(() => {
  getBrushConfig()
})
</script>

<style lang="less" scoped>
.container {
  height: 100%;
}

.brush-info {
  width: 100%;
  text-align: right;
  font-weight: 700;
  position: sticky;
  top: 0;
  z-index: 10;
  color: #fff;
  background: rgba(12, 113, 125, 0.85);
  backdrop-filter: blur(6px);
  padding: 10px 16px;
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.12);
}

.main-container {
  height: calc(100vh - 270px);
  overflow: auto;

  .ques-title {
    font-size: 20px;
    color: #fff;
    font-weight: 500;
    line-height: 1.7;
    background: rgba(255, 255, 255, 0.08);
    border-left: 4px solid #31b97f;
    border-radius: 10px;
    padding: 16px 20px;
    word-break: break-word;
  }

  .ques-options {
    margin-top: 18px;

    /* TDesign group 默认 inline-flex + wrap + fit-content，选项会并排且宽度随内容；
       覆盖为纵向 flex 让每个选项独占一行、宽度一致 */
    :deep(.t-radio-group),
    :deep(.t-checkbox-group) {
      width: 100%;
      display: flex;
      flex-direction: column;
      flex-wrap: nowrap;
      gap: 12px;
      align-items: stretch;

      .t-radio,
      .t-checkbox {
        margin: 0;
        padding: 14px 18px;
        background: rgba(255, 255, 255, 0.08);
        border: 1px solid rgba(255, 255, 255, 0.16);
        border-radius: 12px;
        display: flex;
        align-items: flex-start;
        transition:
          background 0.25s ease,
          border-color 0.25s ease,
          transform 0.25s ease,
          box-shadow 0.25s ease;
        cursor: pointer;

        &:hover {
          background: rgba(255, 255, 255, 0.14);
          border-color: rgba(255, 255, 255, 0.32);
          transform: translateY(-1px);
          box-shadow: 0 6px 18px rgba(0, 0, 0, 0.18);
        }

        :deep(.t-radio__label),
        :deep(.t-checkbox__label) {
          color: rgba(255, 255, 255, 0.92);
          font-size: 15px;
          line-height: 1.7;
          word-break: break-word;
          white-space: normal;
        }
      }

      .t-radio.t-is-checked,
      .t-checkbox.t-is-checked {
        background: rgba(49, 185, 127, 0.18);
        border-color: #31b97f;
        box-shadow: 0 0 0 3px rgba(49, 185, 127, 0.18);

        :deep(.t-radio__label),
        :deep(.t-checkbox__label) {
          color: #ffffff;
        }
      }
    }
  }
}

.ques-footer {
  margin-top: 20px;
  width: 100%;
  text-align: center;

  :deep(.t-button) {
    margin: 0 4px;
  }
}

.slogan {
  width: 50%;
  max-width: 420px;
  height: 100%;
  position: absolute;
  right: 30px;
  text-align: center;
}

.brush-icon {
  max-height: 330px;
  max-width: 420px;
  height: 50%;
  bottom: 10%;
  position: fixed;
  right: 50px;
}

.answer-area {
  margin-top: 20px;
  padding: 24px;
  border-radius: 14px;
  background: rgba(255, 255, 255, 0.08);
  border: 1px solid rgba(255, 255, 255, 0.14);
  display: flex;
  align-items: center;
  gap: 24px;

  .result-icon {
    flex: 0 0 auto;
    text-align: center;
    padding-top: 0;

    img {
      display: block;
    }
  }

  .result-info {
    flex: 1;
    min-width: 0;
    font-size: 15px;
    line-height: 1.9;
    color: rgba(255, 255, 255, 0.92);

    h2 {
      margin: 0 0 10px 0;
    }
  }
}

.notice-item {
  display: block;
  padding: auto;
  line-height: 2;
  margin-left: 15%;
  font-size: 18px;
  color: #fff;
  width: 70%;

  .focus-font {
    color: rgb(255, 141, 47);
    font-weight: 600;
  }
}

.mybutton {
  color: #31b97f;
background-color: var(--td-bg-color-container);
  border-color: var(--td-bg-color-container);
  font-size: 20px;
}
</style>
