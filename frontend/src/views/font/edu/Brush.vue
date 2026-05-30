<template>
  <div class="container" v-loading="loading" element-loading-background="rgba(18, 139, 154, 0.5)">
    <div class="brush-info">
      <span>累计答题天数： {{ brushConfig.monthCount }} /
        {{ brushConfig.monthLimit === 0 ? '∞' : brushConfig.monthLimit }}
      </span>
      <el-divider direction="vertical"></el-divider>
      <span>今日答题数： {{ brushConfig.dayCount }} /
        {{ brushConfig.dayLimit === 0 ? '∞' : brushConfig.dayLimit }}</span>
      <el-divider direction="vertical"></el-divider>
      <span>答题计时：{{ answerTime === 0 ? '∞' : answerTime }} (S)</span>
      <el-divider direction="vertical"></el-divider>
      所属分组：
      <el-select style="width: 120px" size="small" v-model="groupSelected" placeholder="请选择">
        <el-option label="未参与分组" :value="-1" disabled></el-option>
        <el-option :label="brushConfig.groupName" :value="brushConfig.groupId">
        </el-option>
      </el-select>
      <el-divider></el-divider>
    </div>
    <div v-if="currentShow === 'cover'">
      <el-row>
        <el-col :span="14">
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
        </el-col>
        <el-col :span="10">
          <div class="slogan">
            <div>
              <el-button round style="margin: 40px 30px 50px 30px" class="mybutton" :icon="Promotion"
                @click="startBrushBtn()" :disabled="(brushConfig.monthLimit !== 0 &&
                  brushConfig.monthCount >= brushConfig.monthLimit) ||
                  (brushConfig.dayLimit !== 0 &&
                    brushConfig.dayCount >= brushConfig.dayLimit)
                  ">开 始 答 题</el-button>
            </div>
            <div style="font-size: 20px; color: #fff">
              千万种烦恼，答一题就好！
            </div>
            <img class="brush-icon" src="../../../assets/img/edu/brush-icon.png" />
          </div>
        </el-col>
      </el-row>
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
          <el-image style="width: 100px; height: 100px" :src="fileUrl" :preview-src-list="srcList"></el-image>
        </div>
        <div v-else-if="question.fileType === 'mp4'">
          <video :src="fileUrl" controls height="400px"></video>
        </div>
        <div v-else-if="question.fileType === 'mp3'">
          <audio :src="fileUrl" controls width="300px"></audio>
        </div>
        <div class="ques-options" v-if="question.quesType === 2">
          <el-checkbox v-for="item in options" :key="item" v-model="checkbox" class="ques-option" :label="item"
            border>{{ item }}</el-checkbox>
        </div>
        <div class="ques-options" v-else>
          <el-radio v-for="item in options" :key="item" v-model="radio" class="ques-option" :label="item" border>{{ item
          }}</el-radio>
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
        <el-button round :disabled="!submitAndNextFlag" @click="validateSubmit()">提交答案</el-button>
        <el-button round :disabled="submitAndNextFlag" @click="nextQuestion()"
          v-if="brushConfig.dayCount !== brushConfig.dayLimit">下一题</el-button>
        <div style="display: inline-block; float: right">
          <el-button-group>
            <el-button :icon="Star" @click="collect(question.quesCode)"></el-button>
            <el-button :icon="Warning" type="danger" style="float: right" @click="disputeBtn()"></el-button>
          </el-button-group>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { brushApi } from '@/api/edu/brush'
import { questionBankApi } from '@/api/edu/questionBank'
import { questionCollectApi } from '@/api/edu/questionCollect'
import { questionDisputeApi } from '@/api/edu/questionDispute'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Promotion, Star, Warning } from '@element-plus/icons-vue'

const fsURL = import.meta.env.VITE_FILE_MANAGE_BASE

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
    ElMessage.error(res.msg)
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
    ElMessage.error('您的刷题已达上限！')
    return
  }
  loading.value = true
  const res = await questionBankApi.extractQuestions(brushConfig)
  if (res.code !== 200) {
    ElMessage.error(res.msg)
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
      ElMessage.warning('请先选择答案！')
      return
    }
  } else if (type === 2) {
    if (checkbox.value.length < 2) {
      ElMessage.warning('多选题请至少选择一个选项！')
      return
    }
  } else {
    if (radio.value === '') {
      ElMessage.warning('请先选择答案！')
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
    ElMessage.error(res.msg)
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
    ElMessage.error(res.msg)
    return
  }
  ElMessage.success(res.msg)
}

const submitDispute = async (record) => {
  const res = await questionDisputeApi.createDisputeByExam(record)
  if (res.code !== 200) {
    ElMessage.error(res.msg)
    return
  }
  ElMessage.success(res.msg)
}

const disputeBtn = () => {
  ElMessageBox.prompt('请描述该题目问题的内容：', '题目问题反馈', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    inputPattern: /^.{2,250}$/,
    inputErrorMessage: '复议理由不可为空，最长不超过250个字',
    closeOnClickModal: false
  })
    .then(({ value }) => {
      submitDispute({
        quesCode: question.quesCode,
        userAnswer: radio.value + checkbox.value.join(','),
        disputeMemo: value
      })
    })
    .catch(() => {
      // 取消输入
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
  color: #fff;
}

.main-container {
  height: calc(100vh - 270px);
  overflow: auto;

  .ques-title {
    font-size: 20px;
    color: #fff;
    font-weight: 500;
    line-height: 1.7;
  }

  .ques-options {
    .ques-option {
      margin: 10px 0 0 0;
      text-overflow: ellipsis;
      white-space: normal;
      line-height: 1.6;
      display: block;
      font-size: 15px;
    }

    .el-radio.is-bordered.is-checked,
    .el-checkbox.is-bordered.is-checked {
      border: 1px solid #409eff;

      :deep(.el-radio__label) {
        color: #fff;
      }
    }

    .el-radio,
    .el-checkbox {
      color: #000;
    }

    :deep(.el-checkbox__label),
    :deep(.el-radio__label) {
      display: inline;
      font-size: 15px;
      color: #fff;
    }

    .el-radio.is-bordered,
    .el-checkbox.is-bordered {
      padding: 8px 20px 8px 10px;
      border-radius: 4px;
      border: 1px solid #dcdfe6;
      box-sizing: border-box;
      height: 100%;
    }

    :deep(.el-checkbox__input.is-checked + .el-checkbox__label) {
      color: #fff;
    }
  }
}

.ques-footer {
  height: 30px;
  width: 100%;
  text-align: center;
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
  padding-top: 20px;
  width: 100%;
  height: 200px;

  .result-icon {
    height: 200px;
    padding-top: 20px;
    float: left;
  }

  .result-info {
    width: calc(100% - 120px);
    padding-left: 20px;
    height: 200px;
    display: inline-block;
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
  background-color: #ffffff;
  border-color: #ffffff;
  font-size: 20px;
}
</style>
