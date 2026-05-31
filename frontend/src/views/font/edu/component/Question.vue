<template>
  <div v-if="showFlag" style="height: 500px">
    <div style="display: block; height: 24px; width: 100%" v-if="quesTime != -1">
      <span style="float: right; padding: 3px 0">此题剩余时间：{{ quesTime < 0 ? 'N/A' : $moment.utc(quesTime *
        1000).format("HH:mm:ss")}}</span>
    </div>
    <t-loading :loading="loading">
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
        <t-checkbox-group v-model="answer.checkbox" @change="submitAnswer">
          <t-checkbox v-for="(item, index) of options" :key="index" class="ques-option"
            :value="item" border>{{ item }}</t-checkbox>
        </t-checkbox-group>
      </div>
      <div class="ques-options" v-else>
        <t-radio-group v-model="answer.radio" @change="submitAnswer">
          <t-radio v-for="(item, index) of options" :key="index" class="ques-option"
            :value="item" border>{{ item }}</t-radio>
        </t-radio-group>
      </div>
    </div>
    </t-loading>
  </div>
</template>
<script setup>
import { ref, reactive, computed } from 'vue'

const fsURL = import.meta.env.VITE_FILE_MANAGE_BASE

const loading = ref(false)
const showFlag = ref(false)
const optionRand = ref(0)
const quesTest = ref(null)
const question = ref({})
const fileUrl = ref('')
const srcList = ref([])
const timer = ref(null)
const quesTime = ref(-1)
const answer = reactive({ radio: '', checkbox: [] })
const options = ref([])

const emit = defineEmits(['getUserAnswer'])

const show = (testObj, opRand) => {
  quesTest.value = testObj
  optionRand.value = opRand
  getQuestion()
  showFlag.value = true
  quesTimeInit()
}

const quesTimeInit = () => {
  timer.value = clearInterval(timer.value)
  const examInfo = window.localStorage.getItem('examInfo')
  if (examInfo) {
    const parsedExamInfo = JSON.parse(examInfo)
    if (parsedExamInfo.quesTime && parsedExamInfo.quesTime > 0) {
      quesTime.value = parsedExamInfo.quesTime
      timerFunc()
    }
  }
}

const getQuestion = async () => {
  loading.value = true
  question.value = quesTest.value.question
  options.value = question.value.options.split(',')

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

  if (optionRand.value === 1 && question.value.quesType === 2) {
    options.value.sort(function () {
      return 0.5 - Math.random()
    })
  }

  const userAnswer = quesTest.value.userAnswer
  answer.radio = ''
  answer.checkbox = []

  if (userAnswer == null || userAnswer === '') {
    answer.radio = ''
    answer.checkbox = []
  } else {
    if (question.value.quesType === 2) {
      answer.checkbox = userAnswer.split(',')
    } else {
      answer.radio = userAnswer
    }
  }

  loading.value = false
}

const submitAnswer = () => {
  const userAnswer = answer.radio + answer.checkbox.join(',')
  emit('getUserAnswer', userAnswer)
}

const timerFunc = () => {
  timer.value = setInterval(() => {
    quesTime.value--
    if (quesTime.value === 0) {
      timer.value = clearInterval(timer.value)
      submitAnswer()
    }
  }, 1000)
}

defineExpose({
  show
})
</script>
<style lang="less" scoped>
.main-container {
  overflow: auto;
  height: 100%;
}

.main-container {
  font-size: 14px;
  line-height: 1.7;
  height: 100%;

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
      // font-size: 15px;
    }

    .t-radio,
    .t-checkbox {
      color: #000;
    }

    :deep(.t-checkbox__label),
    :deep(.t-radio__label) {
      display: inline;
      // font-size: 15px;
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
</style>
