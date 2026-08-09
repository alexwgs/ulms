<template>
  <div class="main-container">
    <t-card class="management-card">
      <template #header>
        <div class="clearfix">
          <span>{{ exam.examInfo.examName || '暂无考试名称' }}</span>
        </div>
      </template>
      <div class="question-container">
        <t-row :gutter="15">
          <t-col :span="3">得分情况：{{ exam.userScore }}</t-col>
          <t-col :span="3">开始时间：{{ exam.begDate }}</t-col>
          <t-col :span="3">结束时间：{{ exam.endDate }}</t-col>
          <t-col :span="3"
            >截止复议时间：{{
              exam.examInfo.disputeDate == null
                ? '不可复议'
                : exam.examInfo.disputeDate
            }}</t-col
          >
        </t-row>
        <div v-for="item in myTest" :key="item.journo">
          <t-divider></t-divider>
          第{{ item.quesOrder }}题:{{
            item.question.quesType === 2
              ? '【多选题】'
              : item.question.quesType === 1
                ? '【单选题】'
                : '【判断题】'
          }}（{{ item.score }}分）{{ item.question.quesStem }}
          <div v-if="item.question.fileType">
            <div
              class="file-view"
              v-if="
                item.question.fileType === 'jpg' ||
                item.question.fileType === 'png'
              "
            >
              <t-image
                style="width: 100px; height: 100px"
                :src="
                  fsURL +
                  'upload/getFile/edu/' +
                  item.question.fileCode +
                  '.' +
                  item.question.fileType
                "
                :preview-src-list="[
                  fsURL +
                    'upload/getFile/edu/' +
                    item.question.fileCode +
                    '.' +
                    item.question.fileType
                ]"
              ></t-image>
            </div>
            <div v-else-if="item.question.fileType === 'mp4'">
              <video
                :src="
                  fsURL +
                  'upload/getFile/edu/' +
                  item.question.fileCode +
                  '.' +
                  item.question.fileType
                "
                controls
                height="400px"
              ></video>
            </div>
            <div v-else-if="item.question.fileType === 'mp3'">
              <audio
                :src="
                  fsURL +
                  'upload/getFile/edu/' +
                  item.question.fileCode +
                  '.' +
                  item.question.fileType
                "
                controls
                width="300px"
              ></audio>
            </div>
          </div>
          <div v-if="item.question.quesType === 2" class="ques-options">
            <t-checkbox
              v-for="option in item.question.options.split(',')"
              :key="option"
              :model-value="item.question.answer.split(',')"
              class="ques-option"
              :label="option"
              border
              >{{ option }}</t-checkbox
            >
          </div>
          <div v-else class="ques-options">
            <t-radio
              v-for="option in item.question.options.split(',')"
              :key="option"
              :model-value="item.question.answer"
              class="ques-option"
              :label="option"
              border
              >{{ option }}</t-radio
            >
          </div>
          <div
            v-if="
              item.ifCorrect === 0 ||
              disputeList.filter((e) => e.quesCode === item.question.quesCode)
                .length > 0
            "
            class="wrong-answer"
          >
            <font style="color: red">回答错误！你的答案：</font>
            <div v-if="item.userAnswer == null || item.userAnswer.length < 1">
              未答题！
            </div>
            <div v-else-if="item.question.quesType === 2" class="ques-options">
              <t-checkbox
                v-for="option in item.question.options.split(',')"
                :key="option"
                :model-value="item.userAnswer.split(',')"
                class="ques-option"
                :label="option"
                border
                >{{ option }}</t-checkbox
              >
            </div>
            <div v-else class="ques-options">
              <t-radio
                v-for="option in item.question.options.split(',')"
                :key="option"
                :model-value="item.userAnswer"
                class="ques-option"
                :label="option"
                border
                >{{ option }}</t-radio
              >
            </div>
            <br />
            解析：{{ item.question.quesExplain }}<br />知识库：{{
              item.question.knowledge
            }}
            <t-button
              theme="danger"
              style="float: right"
              @click="disputeBtn(item)"
              v-if="
                exam.examInfo.disputeDate != null &&
                formatToUnix(exam.examInfo.disputeDate) > Date.now() / 1000 &&
                disputeList.filter((e) => e.quesCode === item.question.quesCode)
                  .length < 1
              "
              size="small"
              >申请复议</t-button
            >
            <div
              v-else
              v-for="item1 in disputeList.filter(
                (e) => e.quesCode === item.question.quesCode
              )"
              :key="item1.journo"
            >
              复议理由：{{ item1.disputeMemo }}
              <t-alert
                :title="
                  item1.disputeResult === 2 ? '待回复' : item1.disputeResult
                "
                :type="item1.disputeResult == 1 ? 'success' : 'warning'"
                :message="
                  item1.replyAnswer == null
                    ? ''
                    : '复议结果：' + item1.replyAnswer
                "
                :closable="false"
              ></t-alert>
            </div>
          </div>
        </div>
      </div>
    </t-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { MessagePlugin, DialogPlugin } from 'tdesign-vue-next'
import { examTestApi } from '@/api/edu/examTest'
import { questionDisputeApi } from '@/api/edu/questionDispute'

const fsURL = import.meta.env.VITE_FILE_BASE_URL
const route = useRoute()

const examCode = ref('')
const myTest = ref([])
const disputeList = ref([])
const exam = ref({
  journo: '',
  examCode: '',
  ploNum: '',
  begDate: '',
  endDate: '',
  userScore: 0,
  compStat: 1,
  handleIp: '',
  deptNum: '',
  deptGroup: null,
  user: {},
  examInfo: {}
})

const formatToUnix = (date) => {
  return Math.floor(new Date(date).getTime() / 1000)
}

const getTest = async () => {
  const res = await examTestApi.getMyTest(examCode.value)
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  myTest.value = res.data
  // 判断考试是否存在复议，若存在，获取试卷复议情况
  if (exam.value.examInfo.disputeDate != null) getDisputeList()
}

const getDisputeList = async () => {
  const res = await questionDisputeApi.getUserDispute(examCode.value)
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  disputeList.value = res.data
}

const submitDispute = async (record) => {
  const res = await questionDisputeApi.createDisputeByExam(record)
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  MessagePlugin.success(res.msg)
  getDisputeList()
}

const disputeBtn = (item) => {
  DialogPlugin.prompt('请输入复议理由', '复议申请', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    inputPattern: /^.{2,250}$/,
    inputErrorMessage: '复议理由不可为空，最长不超过250个字',
    closeOnClickModal: false
  })
    .then(({ value }) => {
      submitDispute({
        examCode: item.examCode,
        quesCode: item.question.quesCode,
        userAnswer: item.userAnswer,
        disputeMemo: value
      })
    })
    .catch(() => {
      // MessagePlugin.info('取消输入')
    })
}

onMounted(() => {
  examCode.value = route.params.examCode

  // sessionStorage 无考试信息时 JSON.parse 返回 null，这里兜底为空对象避免白屏
  let storedExam = null
  try {
    storedExam = JSON.parse(sessionStorage.getItem('examInfo'))
  } catch (e) {
    storedExam = null
  }
  exam.value = storedExam || { examInfo: {} }
  getTest()
})
</script>

<style lang="less" scoped>
.main-container {
  overflow: auto;
  height: 100%;
}
.question-container {
  // height: calc(100vh - 270px);
  font-size: 14px;
  line-height: 1.7;
  height: 100%;
  .ques-title {
    //   font-size: 20px;
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
.wrong-answer {;
  border: 1px var(--td-error-color) dotted;
  padding: 8px;
  margin-top: 5px;
  font-size: 14px;
  box-sizing: border-box;
  border-radius: 4px;
}
</style>
