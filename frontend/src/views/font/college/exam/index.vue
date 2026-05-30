<template>
  <div class="container">
    <el-row :gutter="20">
      <el-col :span="16">
        <div class="examInfo">
          {{ course?.courseName }}
        </div>
        <el-divider></el-divider>
        <el-card class="box-card">
          <template #header>
            <div class="clearfix">
              <span>答题区</span>
              <span style="float: right; padding: 3px 0">
                考试剩余时间：{{ examTime < 0 ? 'N/A' : formattedExamTime }} </span>
            </div>
          </template>
          <div class="answer-main" v-show="mustReadFlag">
            考试须知：
            <el-divider></el-divider>
            <ul>
              <li>
                <p>请认准预览考试说明中的考试配置情况，若标记红色部分尤为重要！</p>
              </li>
              <li>答案提交需点击下一题完成提交，若考试为可题目跳转，那么每次切换题目也可提交答案！</li>
            </ul>
            历史答题记录
            <el-table :data="examList" style="width: 100%" height="200px">
              <el-table-column prop="begDate" label="开始时间" width="180"></el-table-column>
              <el-table-column prop="endDate" label="交卷时间" width="180"></el-table-column>
              <el-table-column label="答题情况" width="180">
                <template #default="scope">{{ scope.row.rightNum }}/{{ scope.row.quesNum }}</template>
              </el-table-column>
              <el-table-column prop="passFlag" label="是否通过" width="180">
                <template #default="scope">
                  <el-tag :type="scope.row.passFlag == 1 ? 'success' : 'danger'">
                    {{ scope.row.passFlag == 1 ? '通过' : '不通过' }}
                  </el-tag>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <Question ref="questionRef" @getUserAnswer="getUserAnswer" style="height: 500px"></Question>
          <el-row style="padding-top: 20px">
            <el-col :span="24">
              <el-button type="primary" style="width: 100%" size="small" @click="nextQuestion()"
                :disabled="startBtnFlag">
                {{ curentQuesNum < 0 ? '开始答题' : '下一题' }} </el-button>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="answer-controller">
          <template #header>
            <div class="clearfix">
              <span>考试说明</span>
            </div>
          </template>
          <el-row :gutter="20" style="text-align: center">
            <el-col :span="8">
              <el-tag effect="plain">题目数量：{{ setting.quesNum }}</el-tag>
            </el-col>
            <el-col :span="8">
              <el-tag effect="plain">
                考试时长：{{ setting.examTime === 0 ? '不限' : setting.examTime }}(分)
              </el-tag>
            </el-col>
            <el-col :span="8">
              <el-tag effect="plain">考试通过：{{ setting.passNum }}/{{ setting.quesNum }}</el-tag>
            </el-col>
          </el-row>
          <el-row :gutter="20" style="text-align: center; margin-top: 10px">
            <el-col v-if="setting.optionRandom" :span="8">
              <el-tag type="danger" effect="plain">选项随机</el-tag>
            </el-col>
            <el-col v-if="setting.repeatFlag" :span="8">
              <el-tag type="danger" effect="plain">可重复考试</el-tag>
            </el-col>
          </el-row>
        </el-card>
        <el-card class="answer-controller">
          <template #header>
            <div class="clearfix">
              <span>答题控制区</span>
            </div>
          </template>
          <el-row v-if="quesTest.length > 0">
            <el-col v-for="(item, index) of quesTest" :key="index" :span="6"
              style="padding-top: 5px; text-align: center">
              <el-button :type="item.userAnswer === null ? '' : 'success'" style="width: 70px"
                @click="changeQues(index)" size="small">
                <span :class="curentQuesNum == index ? 'currentBtn' : ''">第{{ index + 1 }}题</span>
              </el-button>
            </el-col>
          </el-row>
          <el-row style="padding-top: 20px">
            <el-col :span="24">
              <el-popconfirm width="300" title="注意！！！点击确认将提交本场考试！" confirm-button-text="确认交卷" @confirm="handOverTest()">
                <template #reference>
                  <el-button type="primary" style="width: 100%" size="small" :disabled="mustReadFlag">
                    交 卷
                  </el-button>
                </template>
              </el-popconfirm>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog v-if="examResult" title="考试成绩" v-model="dialogVisible" width="50%" :show-close="false"
      :close-on-click-modal="false">
      <el-result :icon="examResult.passFlag == 1 ? 'success' : 'error'"
        :title="examResult.passFlag == 1 ? '恭喜！考试通过！' : '很遗憾！考试未通过！'"
        :sub-title="'您一共答对了' + examResult.rightNum + '题！'"></el-result>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="router.go(0)">再考一次</el-button>
          <el-button type="primary" @click="closeWindow">关 闭</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
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
        ElMessage.error(res.msg)
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
          ElMessage.error(res.msg)
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
        ElMessage.error('因为您已完成所有题目的提交，请点击右边的交卷！')
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
    ElMessage.error('已经没有下一题了！')
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
    ElMessage.error('没有题目可以提交！' + curentQuesNum.value)
    return
  }
  const res = await httpInstance.post('college/course/exam/check', quesTest.value[curentQuesNum.value])
  if (res.code !== 200) {
    ElMessage.error(res.msg)
  }
}

const handOverTest = () => {
  httpInstance.put(`college/course/exam/submit/${quesTest.value[0].examCode}`).then((res) => {
    ElMessage.success(res.msg)
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
