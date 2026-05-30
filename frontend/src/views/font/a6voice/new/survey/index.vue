<template>
  <el-card>
    <el-page-header @back="() => router.back()" content="创建新调研" />
    <div class="QN-header">
      <el-form ref="formRef" :rules="rules" :model="questionnaire" label-width="80px">
        <el-form-item label="调研标题" prop="title">
          <el-input v-model="questionnaire.title" size="small" placeholder="请输入调研标题 2-50 字内"></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="6">
            <el-form-item label="调研分类" prop="category">
              <el-select v-model="questionnaire.category" size="small" placeholder="请选择">
                <el-option v-for="item in dictStore.getDictByNames('cyt_artical_category', 1).filter(
                  (item) => item.status == 1
                )" :key="item.code" :label="item.codeval" :value="parseInt(item.code)"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="截止时间" prop="compDate">
              <el-date-picker size="small" style="width: 180px" v-model="questionnaire.compDate" type="datetime"
                placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item>
              <el-dropdown @command="addQuestion">
                <el-button type="primary" size="small">
                  添加题目
                  <el-icon><Plus /></el-icon>
                </el-button>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item v-for="item in dictStore.getDictByNames('cyt_survey_question_type', 1)"
                      :key="item.id" :command="item.code">{{ item.codeval }}</el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
              <el-button-group>
                <el-button class="release-button" type="primary" size="small" @click="preview">预览</el-button>
                <el-button class="release-button" type="primary" size="small" :disabled="releaseBtnFlag"
                  @click="release">发布</el-button>
              </el-button-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="调研正文" prop="content">
          <WangEditor v-model="questionnaire.content"></WangEditor>
        </el-form-item>
      </el-form>
    </div>
    <div class="QN-questions">
      <div class="QN-question" v-for="(question, i) in questionnaire.questions" :key="i" @mouseover="
        () => {
          hoverQuestion = true
          activeQuestionIndex = i
        }
      " @mouseout="hoverQuestion = false">
        <el-input :ref="`title${i}`" v-if="questionTitleEditing && currentQuestionTitleIndex === `${i}`"
          :placeholder="question.content" v-model="question.content" @blur="questionTitleEditing = false"
          @keyup.enter="questionTitleEditing = false"></el-input>
        <p v-else @click="handleQuestionTitleClick(`${i}`, `title${i}`)">
          题号：{{ question.sort }} |
          {{ question.content === '' ? '请输入调研题目！' : question.content }}
        </p>
        <div v-if="question.questionType === 'radio'">
          <el-radio-group class="el-radio-group" v-model="question.radio">
            <div v-for="(label, j) in question.options" :key="j" class="lable-wrapper"
              @mouseenter="handleMouseEnter(i, j)" @mouseleave="hoverLabel = false">
              <el-input :ref="`DOM${i}${j}`" v-if="radioEditing && currentRadioIndex === `${i}${j}`"
                :placeholder="label" v-model="question.options[j]" @blur="radioEditing = false"
                @keyup.enter="radioEditing = false"></el-input>
              <el-radio v-else class="el-radio" :label="label === '' ? '单选选项-' + (j + 1) : label"
                @click="handleRadioClick(`${i}${j}`, `DOM${i}${j}`)">{{ label === '' ? '单选选项-' + (j + 1) : label
                }}</el-radio>
              <span v-show="hoverLabel && activeLableIndex === `${i}${j}`" class="label-operation">
                <el-icon class="remove-label-icon" @click="removeLable(i, j)"><Minus /></el-icon>
                <el-icon class="add-label-icon" @click="addLable(i, j)"><Plus /></el-icon>
              </span>
            </div>
          </el-radio-group>
        </div>
        <div v-else-if="question.questionType === 'checkbox'">
          <el-checkbox-group v-model="question.checkList">
            <div v-for="(label, j) in question.options" :key="j" class="lable-wrapper"
              @mouseenter="handleMouseEnter(i, j)" @mouseleave="hoverLabel = false">
              <el-input :ref="`DOM${i}${j}`" v-if="checkboxEditing && currentCheckboxIndex === `${i}${j}`"
                :placeholder="label" v-model="question.options[j]" @blur="checkboxEditing = false"
                @keyup.enter="checkboxEditing = false"></el-input>
              <el-checkbox v-else class="el-checkbox" :label="label === '' ? '多选选项-' + (j + 1) : label"
                @click="handleCheckboxClick(`${i}${j}`, `DOM${i}${j}`)"></el-checkbox>
              <span v-show="hoverLabel && activeLableIndex === `${i}${j}`" class="label-operation">
                <el-icon class="remove-label-icon" @click="removeLable(i, j)"><Minus /></el-icon>
                <el-icon class="add-label-icon" @click="addLable(i, j)"><Plus /></el-icon>
              </span>
            </div>
          </el-checkbox-group>
        </div>
        <div v-else-if="question.questionType === 'textarea'">
          <el-input type="textarea" placeholder="请输入内容" v-model="question.value"></el-input>
        </div>
        <div style="text-align: right; padding-bottom: 10px">
          <el-link size="small" type="primary" :underline="false" :disabled="question.sort === 1 || questionnaire.questions.length === 1
            " @click="orderChange('up', i)">上移</el-link>&nbsp;&nbsp;&nbsp;
          <el-link size="small" type="primary" :underline="false" :disabled="question.sort === questionnaire.questions.length ||
            questionnaire.questions.length === 1
            " @click="orderChange('down', i)">下移</el-link>&nbsp;&nbsp;&nbsp;
          <el-link size="small" type="danger" :underline="false" @click="deleteQuestion(i)">删除</el-link>
        </div>
      </div>
    </div>
  </el-card>
</template>

<script setup>
import { ref, reactive, computed, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Plus, Minus } from '@element-plus/icons-vue'
import WangEditor from '@/components/WangEditor.vue'
import { httpInstance } from '@/utils/request'
import { useDictStore } from '@/stores'

const router = useRouter()
const dictStore = useDictStore()
const formRef = ref(null)

const getTemplate = (questionType) => {
  const template = {
    radio: {
      sort: 1,
      questionType: 'radio',
      content: '',
      radio: '',
      options: ['', '']
    },
    checkbox: {
      sort: 1,
      questionType: 'checkbox',
      content: '',
      checkList: [],
      options: ['', '', '', '']
    },
    textarea: {
      sort: 1,
      questionType: 'textarea',
      content: '',
      value: '',
      options: []
    }
  }
  return template[questionType]
}

const titleEditing = ref(false)
const subtitleEditing = ref(false)
const radioEditing = ref(false)
const checkboxEditing = ref(false)
const questionTitleEditing = ref(false)
const hoverQuestion = ref(false)
const hoverLabel = ref(false)
const currentRadioIndex = ref('')
const currentCheckboxIndex = ref('')
const currentTitleIndex = ref('')
const currentQuestionTitleIndex = ref('')
const activeQuestionIndex = ref(0)
const activeLableIndex = ref('')

const questionnaire = reactive(
  JSON.parse(window.localStorage.getItem('SET_QUESTIONNAIRE')) || {
    title: '',
    compDate: '',
    content: '',
    category: '',
    questions: [getTemplate('radio')]
  }
)

const rules = {
  title: [
    { required: true, message: '调研标题', trigger: 'blur' },
    { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
  ],
  category: [
    { required: true, message: '请选择调研板块', trigger: 'change' }
  ],
  compDate: [
    { required: true, message: '请选择调研截止日期', trigger: 'change' }
  ],
  content: [
    { required: true, message: '请输入调研正文内容！', trigger: 'blur' },
    { min: 10, message: '大于 10 个字符', trigger: 'blur' }
  ]
}

const releaseBtnFlag = ref(false)

const handleMouseEnter = (i, j) => {
  hoverLabel.value = true
  activeLableIndex.value = `${i}${j}`
}

const handleRadioClick = (index, ref) => {
  radioEditing.value = true
  currentRadioIndex.value = index
  nextTick(() => {
    if (formRef.value && formRef.value[ref] && formRef.value[ref][0]) {
      formRef.value[ref][0].focus()
    }
  })
}

const handleCheckboxClick = (index, ref) => {
  checkboxEditing.value = true
  currentCheckboxIndex.value = index
  nextTick(() => {
    if (formRef.value && formRef.value[ref] && formRef.value[ref][0]) {
      formRef.value[ref][0].focus()
    }
  })
}

const handleQuestionTitleClick = (index, ref) => {
  questionTitleEditing.value = true
  currentQuestionTitleIndex.value = index
  nextTick(() => {
    if (formRef.value && formRef.value[ref] && formRef.value[ref][0]) {
      formRef.value[ref][0].focus()
    }
  })
}

const deleteQuestion = (index) => {
  questionnaire.questions.splice(index, 1)
  for (let i = 0; i < questionnaire.questions.length; i++) {
    questionnaire.questions[i].sort = i + 1
  }
}

const addLable = (i, j) => {
  questionnaire.questions[i].options.splice(j + 1, 0, '')
}

const removeLable = (i, j) => {
  if (questionnaire.questions[i].options.length <= 1)
    return ElMessage.warning('请注意！仅剩一个选项，不可删除！')
  questionnaire.questions[i].options.splice(j, 1)
}

const addQuestion = (command) => {
  const question = getTemplate(command)
  question.sort = questionnaire.questions.length + 1
  questionnaire.questions.push(question)
}

const release = async () => {
  try {
    const list = reverseQuestionList.value
    if (list.questions.length < 1)
      return ElMessage.warning('至少需要一个调研问题！')
    for (const item of list.questions) {
      if (item.content === '')
        return ElMessage.warning('调研问题的标题不可为空，请检查表单！')
      if (item.questionType !== 'textarea' && item.options.length < 2)
        return ElMessage.warning('选项问题不可小于两个！')
      if (item.questionType !== 'textarea' && item.options.length >= 2) {
        for (const option of item.options) {
          if (option === '')
            return ElMessage.warning('选项内容不可为空！')
        }
      }
    }
    await formRef.value.validate()
    releaseBtnFlag.value = true
    const res = await httpInstance.post('cyt/survey', list)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      releaseBtnFlag.value = false
      return
    }
    ElMessage.success(res.msg)
    releaseBtnFlag.value = false
    router.back()
  } catch (e) {
    ElMessage.warning('请完整填写表单！')
    releaseBtnFlag.value = false
  }
}

const preview = () => {
  window.localStorage.setItem(
    'SET_QUESTIONNAIRE',
    JSON.stringify(questionnaire)
  )
  router.push('/youngTalk/question/preview')
}

const orderChange = (type, i) => {
  if (type === 'down') {
    questionnaire.questions[i] = questionnaire.questions.splice(
      i + 1,
      1,
      questionnaire.questions[i]
    )[0]
  } else if (type === 'up') {
    questionnaire.questions[i] = questionnaire.questions.splice(
      i - 1,
      1,
      questionnaire.questions[i]
    )[0]
  }
  for (let i = 0; i < questionnaire.questions.length; i++) {
    questionnaire.questions[i].sort = i + 1
  }
}

const reverseQuestionList = computed(() => {
  const list = JSON.parse(JSON.stringify(questionnaire))
  return list
})
</script>

<style scoped lang="less">
.edit {
  padding-bottom: 5rem;
}

.header {
  padding: 0 2rem;
  border-bottom: solid 1px #e6e6e6;
  margin-bottom: 1rem;
}

.add-label-icon,
.remove-label-icon {
  font-size: 1rem;
  position: absolute;
  top: 0rem;
  right: 0rem;
  cursor: pointer;
}

.remove-label-icon {
  right: 1.5rem;
}

.delete-icon {
  position: absolute;
  top: 1rem;
  right: 1rem;
}

.lable-wrapper {
  position: relative;
}

.QN-title {
  h1 {
    text-align: center;
    margin-bottom: 0.8em;
  }
}

.QN-description {
  p {
    text-align: center;
  }
}

.QN-header,
.QN-question {
  position: relative;
  margin: 1rem 3rem;
  border-bottom: solid 1px #e6e6e6;
}

.QN-question {
  p {
    margin-bottom: 1em;
  }
}

.QN-questions {
  margin-bottom: 3rem;
}

.el-radio-group {
  display: block;
  position: relative;
}

.el-radio,
.el-checkbox {
  display: block;
  padding: 0.5em;
}

.operation {
  margin: 1rem 3rem;
  display: flex;
  justify-content: center;
}

.release-button {
  width: 6rem;
  margin-left: 2rem;
}

.qr-wrapper {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
</style>
