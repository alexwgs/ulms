<template>
  <div class="main-container">
    <el-card class="box-card">
      <template #header>
        <div class="clearfix">
          <span>{{ exam.examInfo.examName || '暂无考试名称' }}</span>
        </div>
      </template>
      <div class="question-container">
        <el-row :gutter="15">
          <el-col :span="6">得分情况：{{ exam.userScore }}</el-col>
          <el-col :span="6">开始时间：{{ exam.begDate }}</el-col>
          <el-col :span="6">结束时间：{{ exam.endDate }}</el-col>
          <el-col :span="6"
            >截止复议时间：{{
              exam.examInfo.disputeDate == null
                ? '不可复议'
                : exam.examInfo.disputeDate
            }}</el-col
          >
        </el-row>
        <div v-for="item in myTest" :key="item.journo">
          <el-divider></el-divider>
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
              <el-image
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
              ></el-image>
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
            <el-checkbox
              v-for="option in item.question.options.split(',')"
              :key="option"
              :model-value="item.question.answer.split(',')"
              class="ques-option"
              :label="option"
              border
              >{{ option }}</el-checkbox
            >
          </div>
          <div v-else class="ques-options">
            <el-radio
              v-for="option in item.question.options.split(',')"
              :key="option"
              :model-value="item.question.answer"
              class="ques-option"
              :label="option"
              border
              >{{ option }}</el-radio
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
              <el-checkbox
                v-for="option in item.question.options.split(',')"
                :key="option"
                :model-value="item.userAnswer.split(',')"
                class="ques-option"
                :label="option"
                border
                >{{ option }}</el-checkbox
              >
            </div>
            <div v-else class="ques-options">
              <el-radio
                v-for="option in item.question.options.split(',')"
                :key="option"
                :model-value="item.userAnswer"
                class="ques-option"
                :label="option"
                border
                >{{ option }}</el-radio
              >
            </div>
            <br />
            解析：{{ item.question.quesExplain }}<br />知识库：{{
              item.question.knowledge
            }}
            <el-button
              type="danger"
              style="float: right"
              @click="disputeBtn(item)"
              v-if="
                exam.examInfo.disputeDate != null &&
                formatToUnix(exam.examInfo.disputeDate) > Date.now() / 1000 &&
                disputeList.filter((e) => e.quesCode === item.question.quesCode)
                  .length < 1
              "
              size="small"
              >申请复议</el-button
            >
            <div
              v-else
              v-for="item1 in disputeList.filter(
                (e) => e.quesCode === item.question.quesCode
              )"
              :key="item1.journo"
            >
              复议理由：{{ item1.disputeMemo }}
              <el-alert
                :title="
                  item1.disputeResult === 2 ? '待回复' : item1.disputeResult
                "
                :type="item1.disputeResult == 1 ? 'success' : 'warning'"
                :description="
                  item1.replyAnswer == null
                    ? ''
                    : '复议结果：' + item1.replyAnswer
                "
                :closable="false"
              ></el-alert>
            </div>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { examTestApi } from '@/api/edu/examTest'
import { questionDisputeApi } from '@/api/edu/questionDispute'

const fsURL = import.meta.env.VITE_FILE_MANAGE_BASE
const route = useRoute()

const examCode = ref('')
const myTest = ref([])
const disputeList = ref([])
const exam = ref({
  journo: '1910290000837',
  examCode: '1910290000834',
  ploNum: '655012',
  begDate: '2019-10-31 14:21:52',
  endDate: '2019-10-31 14:31:07',
  userScore: 0,
  compStat: 1,
  handleIp: '192.168.31.81',
  deptNum: '3006',
  deptGroup: '3006',
  user: {
    ploNum: '655012',
    ploName: '魏根生',
    deptName: '业务管理室',
    groupName: '-',
    deptNum: '3006',
    deptGroup: null,
    batchGroup: '35期',
    jobLevel: '101',
    ploStatus: '00',
    inDate: '2014-05-19',
    outDate: null,
    ploDesc: null,
    organ: null,
    flag: null,
    engName: null,
    ploAddr: null,
    ploMobil: null,
    jobStatus: '金普卡主任',
    officeCmb: null,
    turnType: null,
    pzjc: null,
    avatar: 'upload/getFile/avatar/16468174621306.jpg',
    agentNum: null,
    roles: null
  },
  examInfo: {
    examCode: '1910290000834',
    examName: '个人风采-题目一111',
    begDate: '2019-10-29 00:00:00',
    endDate: '2019-11-01 23:59:59',
    examTime: 30,
    quesTime: null,
    ifBreak: 1,
    ifAgain: 1,
    audioAgain: 1,
    videoAgain: 1,
    scoreMethod: 0,
    unitScore: null,
    quesRandom: 0,
    optionRandom: 0,
    optionOrder: 0,
    wrongBreak: 1,
    examUse: 2,
    examStat: 1,
    handlePlo: '655012',
    handleDate: '2021-07-27 11:31:21',
    examCondition: 1,
    perfDate: '2021-06',
    quesNum: 1,
    score: 100,
    ifRead: 1,
    disputeDate: '2021-06-30',
    areaCode: '1906200002928',
    skipQues: 0,
    bookExam: 1,
    quesTemplate: null,
    bookTime: null,
    areaInfo: '11F培训教室[靠业管]'
  }
})

const formatToUnix = (date) => {
  return Math.floor(new Date(date).getTime() / 1000)
}

const getTest = async () => {
  const res = await examTestApi.getMyTest(examCode.value)
  if (res.code !== 200) return ElMessage.error(res.msg)
  myTest.value = res.data
  // 判断考试是否存在复议，若存在，获取试卷复议情况
  if (exam.value.examInfo.disputeDate != null) getDisputeList()
}

const getDisputeList = async () => {
  const res = await questionDisputeApi.getUserDispute(examCode.value)
  if (res.code !== 200) return ElMessage.error(res.msg)
  disputeList.value = res.data
}

const submitDispute = async (record) => {
  const res = await questionDisputeApi.createDisputeByExam(record)
  if (res.code !== 200) return ElMessage.error(res.msg)
  ElMessage.success(res.msg)
  getDisputeList()
}

const disputeBtn = (item) => {
  ElMessageBox.prompt('请输入复议理由', '复议申请', {
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
      // ElMessage.info('取消输入')
    })
}

onMounted(() => {
  examCode.value = route.params.examCode

  exam.value = JSON.parse(sessionStorage.getItem('examInfo'))
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
    .el-radio,
    .el-checkbox {
      color: #000;
    }
    :deep(.el-checkbox__label),
    :deep(.el-radio__label) {
      display: inline;
      // font-size: 15px;
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
.wrong-answer {
  background-color: #fde2e2;
  border: 1px #f56c6c dotted;
  padding: 8px;
  margin-top: 5px;
  font-size: 14px;
  box-sizing: border-box;
  border-radius: 4px;
}
</style>
