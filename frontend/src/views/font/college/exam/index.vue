<template>
  <div class="container">
    <t-row :gutter="20">
      <t-col :span="8">
        <div class="examInfo">
          {{ course?.courseName }}
        </div>
        <t-divider></t-divider>
        <t-card class="box-card">
          <template #header>
            <div class="clearfix">
              <span>答题区</span>
              <span style="float: right; padding: 3px 0">
                考试剩余时间：{{ examTime < 0 ? 'N/A' : formattedExamTime }} </span>
            </div>
          </template>
          <div class="answer-main" v-show="mustReadFlag">
            考试须知：
            <t-divider></t-divider>
            <ul>
              <li>
                <p>请认准预览考试说明中的考试配置情况，若标记红色部分尤为重要！</p>
              </li>
              <li>答案提交需点击下一题完成提交，若考试为可题目跳转，那么每次切换题目也可提交答案！</li>
            </ul>
            历史答题记录
            <CustomTable rowKey="id" :data="examList" style="width: 100%" height="200px">
              <TableColumn colKey="begDate" label="开始时间" width="180"></TableColumn>
              <TableColumn colKey="endDate" label="交卷时间" width="180"></TableColumn>
              <TableColumn label="答题情况" width="180">
                <template #default="scope">{{ scope.row.rightNum }}/{{ scope.row.quesNum }}</template>
              </TableColumn>
              <TableColumn colKey="passFlag" label="是否通过" width="180">
                <template #default="scope">
                  <t-tag :theme="scope.row.passFlag == 1 ? 'success' : 'danger'">
                    {{ scope.row.passFlag == 1 ? '通过' : '不通过' }}
                  </t-tag>
                </template>
              </TableColumn>
            </CustomTable>
          </div>
          <Question ref="questionRef" @getUserAnswer="getUserAnswer" style="height: 500px"></Question>
          <t-row style="padding-top: 20px">
            <t-col :span="12">
              <t-button theme="primary" style="width: 100%" size="small" @click="nextQuestion()"
                :disabled="startBtnFlag">
                {{ curentQuesNum < 0 ? '开始答题' : '下一题' }} </t-button>
            </t-col>
          </t-row>
        </t-card>
      </t-col>
      <t-col :span="4">
        <t-card class="answer-controller">
          <template #header>
            <div class="clearfix">
              <span>考试说明</span>
            </div>
          </template>
          <t-row :gutter="20" style="text-align: center">
            <t-col :span="4">
              <t-tag effect="plain">题目数量：{{ setting.quesNum }}</t-tag>
            </t-col>
            <t-col :span="4">
              <t-tag effect="plain">
                考试时长：{{ setting.examTime === 0 ? '不限' : setting.examTime }}(分)
              </t-tag>
            </t-col>
            <t-col :span="4">
              <t-tag effect="plain">考试通过：{{ setting.passNum }}/{{ setting.quesNum }}</t-tag>
            </t-col>
          </t-row>
          <t-row :gutter="20" style="text-align: center; margin-top: 10px">
            <t-col v-if="setting.optionRandom" :span="4">
              <t-tag theme="danger" effect="plain">选项随机</t-tag>
            </t-col>
            <t-col v-if="setting.repeatFlag" :span="4">
              <t-tag theme="danger" effect="plain">可重复考试</t-tag>
            </t-col>
          </t-row>
        </t-card>
        <t-card class="answer-controller">
          <template #header>
            <div class="clearfix">
              <span>答题控制区</span>
            </div>
          </template>
          <t-row v-if="quesTest.length > 0">
            <t-col v-for="(item, index) of quesTest" :key="index" :span="3"
              style="padding-top: 5px; text-align: center">
              <t-button :type="item.userAnswer === null ? '' : 'success'" style="width: 70px"
                @click="changeQues(index)" size="small">
                <span :class="curentQuesNum == index ? 'currentBtn' : ''">第{{ index + 1 }}题</span>
              </t-button>
            </t-col>
          </t-row>
          <t-row style="padding-top: 20px">
            <t-col :span="12">
              <t-popconfirm width="300" content="注意！！！点击确认将提交本场考试！" confirm-button-text="确认交卷" @confirm="handOverTest()">
                <template #reference>
                  <t-button theme="primary" style="width: 100%" size="small" :disabled="mustReadFlag">
                    交 卷
                  </t-button>
                </template>
              </t-popconfirm>
            </t-col>
          </t-row>
        </t-card>
      </t-col>
    </t-row>
    <t-dialog v-if="examResult" header="考试成绩" v-model:visible="dialogVisible" width="50%" :close-btn="false"
      :close-on-overlay-click="false">
      <t-result :icon="examResult.passFlag == 1 ? 'success' : 'error'"
        :title="examResult.passFlag == 1 ? '恭喜！考试通过！' : '很遗憾！考试未通过！'"
        :sub-title="'您一共答对了' + examResult.rightNum + '题！'"></t-result>
      <template #footer>
        <span class="dialog-footer">
          <t-button @click="router.go(0)">再考一次</t-button>
          <t-button theme="primary" @click="closeWindow">关 闭</t-button>
        </span>
      </template>
    </t-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { MessagePlugin, Result } from 'tdesign-vue-next'
import { httpInstance } from '@/utils/request'
import Question from '@/views/font/edu/component/Question.vue'

const router = useRouter()

// Refs
const questionRef = ref(null)

// Reactive data
const course = ref(null)
const examList = ref([])
const setting = ref({})
const quesTest = ref([])
const curentQuesNum = ref(-1)
const mustReadFlag = ref(true)
const startBtnFlag = ref(true)
const examTime = ref(-1)
const timer = ref(null)
const dialogVisible = ref(false)
const examResult = ref(null)

// Computed
const formattedExamTime = computed(() => {
  if (examTime.value < 0) return 'N/A'
  const hours = Math.floor(examTime.value / 3600)
  const minutes = Math.floor((examTime.value % 3600) / 60)
  const seconds = examTime.value % 60
  return `${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`
})

// Methods
const precheck = () => {
  httpInstance
    .get(`college/course/exam/init/${course.value.courseId}`)
    .then((res) => {
      if (res.code !== 200) {
        MessagePlugin.error(res.msg)
        return
      }
      setting.value = res.setting
      examList.value = res.data
      examTime.value = res.setting.examTime * 60
      startBtnFlag.value = false
    })
}

const initTest = () => {
  return new Promise((resolve, reject) => {
    httpInstance
      .get(`college/course/exam/start/${course.value.courseId}`)
      .then((res) => {
        if (res.code !== 200) {
          MessagePlugin.error(res.msg)
          reject(res.msg)
          return
        }
        quesTest.value = res.data
        resolve()
      })
      .catch((error) => {
        reject(error)
      })
  })
}

const changeQues = (o) => {
  submitAnswer()
  curentQuesNum.value = o
  questionRef.value.show(quesTest.value[o], setting.value.optionRand)
}

const nextQuestion = () => {
  if (curentQuesNum.value < 0) {
    initTest().then(() => {
      if (timer.value == null) timerFunc(examTime.value)
      if (curentQuesNum.value + 1 >= setting.value.quesNum) {
        mustReadFlag.value = false
        MessagePlugin.error('因为您已完成所有题目的提交，请点击右边的交卷！')
        return
      }
      curentQuesNum.value++
      mustReadFlag.value = false
      questionRef.value.show(quesTest.value[curentQuesNum.value], setting.value.optionRand)
    })
    return
  }
  submitAnswer()
  if (curentQuesNum.value + 1 >= setting.value.quesNum) {
    MessagePlugin.error('已经没有下一题了！')
    return
  }
  curentQuesNum.value++
  changeQues(curentQuesNum.value)
}

const getUserAnswer = (userAnswer) => {
  quesTest.value[curentQuesNum.value].userAnswer = userAnswer
}

const submitAnswer = async () => {
  if (curentQuesNum.value < 0 || curentQuesNum.value >= course.value.quesNum) {
    MessagePlugin.error('没有题目可以提交！' + curentQuesNum.value)
    return
  }
  const res = await httpInstance.post('college/course/exam/check', quesTest.value[curentQuesNum.value])
  if (res.code !== 200) {
    MessagePlugin.error(res.msg)
  }
}

const handOverTest = () => {
  httpInstance.put(`college/course/exam/submit/${quesTest.value[0].examCode}`).then((res) => {
    MessagePlugin.success(res.msg)
    examResult.value = res.examResult
    dialogVisible.value = true
  })
}

const closeWindow = () => {
  window.location.href = 'about:blank'
  window.close()
}

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

// Lifecycle hooks
onMounted(() => {
  course.value = JSON.parse(localStorage.getItem('course'))
  precheck()
})

onUnmounted(() => {
  if (timer.value) {
    clearInterval(timer.value)
    timer.value = null
  }
})
</script>

<style lang="less" scoped>
.container {
  padding: 20px;

  .box-card {
    height: calc(100vh - 120px);

    :deep(.t-card__body) {
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
