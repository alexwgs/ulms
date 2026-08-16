<template>
  <t-card>
    <div class="sub-page-header">
      <t-button theme="default" variant="text" @click="router.back()">
        
      返回</t-button>
      <span class="sub-page-title">创建新调研</span>
    </div>
    <div class="QN-header">
      <t-form ref="formRef" :rules="rules" :data="questionnaire" label-width="80px">
        <t-form-item label="调研标题" name="title">
          <t-input v-model="questionnaire.title" size="small" placeholder="请输入调研标题 2-50 字内"></t-input>
        </t-form-item>
        <t-row>
          <t-col :span="3">
            <t-form-item label="调研分类" name="category">
              <t-select v-model="questionnaire.category" size="small" placeholder="请选择">
                <t-option v-for="item in dictStore.getDictByNames('cyt_artical_category', 1).filter(
                  (item) => item.status == 1
                )" :key="item.code" :label="item.codeval" :value="parseInt(item.code)"></t-option>
              </t-select>
            </t-form-item>
          </t-col>
          <t-col :span="3">
            <t-form-item label="截止时间" name="compDate">
              <t-date-picker size="small" style="width: 180px" v-model="questionnaire.compDate" mode="date" enable-time-picker
                placeholder="选择日期时间">
              </t-date-picker>
            </t-form-item>
          </t-col>
          <t-col :span="6">
            <t-form-item>
              <t-dropdown @click="(dropdownItem) => addQuestion(dropdownItem.value)">
                <t-button theme="primary" size="small">
                  添加题目
                  <AddIcon />
                </t-button>
                <template #dropdown>
                  <t-dropdown-menu>
                    <t-dropdown-item v-for="item in dictStore.getDictByNames('cyt_survey_question_type', 1)"
                      :key="item.id" :value="item.code">{{ item.codeval }}</t-dropdown-item>
                  </t-dropdown-menu>
                </template>
              </t-dropdown>
              <t-space>
                <t-button class="release-button" theme="primary" size="small" @click="preview">预览</t-button>
                <t-button class="release-button" theme="primary" size="small" :disabled="releaseBtnFlag"
                  @click="release">发布</t-button>
              </t-space>
            </t-form-item>
          </t-col>
        </t-row>
        <t-form-item label="调研正文" name="content">
          <WangEditor v-model="questionnaire.content"></WangEditor>
        </t-form-item>
      </t-form>
    </div>
    <div class="QN-questions">
      <div class="QN-question" v-for="(question, i) in questionnaire.questions" :key="i" @mouseover="
        () => {
          hoverQuestion = true
          activeQuestionIndex = i
        }
      " @mouseout="hoverQuestion = false">
        <t-input :ref="`title${i}`" v-if="questionTitleEditing && currentQuestionTitleIndex === `${i}`"
          :placeholder="question.content" v-model="question.content" @blur="questionTitleEditing = false"
          @enter="questionTitleEditing = false"></t-input>
        <p v-else @click="handleQuestionTitleClick(`${i}`, `title${i}`)">
          题号：{{ question.sort }} |
          {{ question.content === '' ? '请输入调研题目！' : question.content }}
        </p>
        <div v-if="question.questionType === 'radio'">
          <t-radio-group v-model="question.radio">
            <div v-for="(label, j) in question.options" :key="j" class="lable-wrapper"
              @mouseenter="handleMouseEnter(i, j)" @mouseleave="hoverLabel = false">
              <t-input :ref="`DOM${i}${j}`" v-if="radioEditing && currentRadioIndex === `${i}${j}`"
                :placeholder="label" v-model="question.options[j]" @blur="radioEditing = false"
                @enter="radioEditing = false"></t-input>
              <t-radio v-else :value="label === '' ? '单选选项-' + (j + 1) : label"
                @click="handleRadioClick(`${i}${j}`, `DOM${i}${j}`)">{{ label === '' ? '单选选项-' + (j + 1) : label
                }}</t-radio>
              <span v-show="hoverLabel && activeLableIndex === `${i}${j}`" class="label-operation">
                <t-icon class="remove-label-icon" @click="removeLable(i, j)"><RemoveIcon /></t-icon>
                <t-icon class="add-label-icon" @click="addLable(i, j)"><AddIcon /></t-icon>
              </span>
            </div>
          </t-radio-group>
        </div>
        <div v-else-if="question.questionType === 'checkbox'">
          <t-checkbox-group v-model="question.checkList">
            <div v-for="(label, j) in question.options" :key="j" class="lable-wrapper"
              @mouseenter="handleMouseEnter(i, j)" @mouseleave="hoverLabel = false">
              <t-input :ref="`DOM${i}${j}`" v-if="checkboxEditing && currentCheckboxIndex === `${i}${j}`"
                :placeholder="label" v-model="question.options[j]" @blur="checkboxEditing = false"
                @enter="checkboxEditing = false"></t-input>
              <!-- 审计修复：多选选项补文字显示（原只渲染空复选框，看不到选项内容） -->
              <t-checkbox v-else :value="label === '' ? '多选选项-' + (j + 1) : label"
                @click="handleCheckboxClick(`${i}${j}`, `DOM${i}${j}`)">{{ label === '' ? '多选选项-' + (j + 1) : label }}</t-checkbox>
              <span v-show="hoverLabel && activeLableIndex === `${i}${j}`" class="label-operation">
                <t-icon class="remove-label-icon" @click="removeLable(i, j)"><RemoveIcon /></t-icon>
                <t-icon class="add-label-icon" @click="addLable(i, j)"><AddIcon /></t-icon>
              </span>
            </div>
          </t-checkbox-group>
        </div>
        <div v-else-if="question.questionType === 'textarea'">
          <p style="color: var(--td-text-color-placeholder)">（填空题，答题时填写）</p>
        </div>
        <div style="text-align: right; padding-bottom: 10px">
          <t-link size="small" theme="primary" :underline="false" :disabled="question.sort === 1 || questionnaire.questions.length === 1
            " @click="orderChange('up', i)">上移</t-link>&nbsp;&nbsp;&nbsp;
          <t-link size="small" theme="primary" :underline="false" :disabled="question.sort === questionnaire.questions.length ||
            questionnaire.questions.length === 1
            " @click="orderChange('down', i)">下移</t-link>&nbsp;&nbsp;&nbsp;
          <t-link size="small" theme="danger" :underline="false" @click="deleteQuestion(i)">删除</t-link>
        </div>
      </div>
    </div>
  </t-card>
</template>

<script setup>
import { ref, reactive, computed, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import { MessagePlugin } from 'tdesign-vue-next'
import { AddIcon, RemoveIcon } from 'tdesign-icons-vue-next'
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
    return MessagePlugin.warning('请注意！仅剩一个选项，不可删除！')
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
      return MessagePlugin.warning('至少需要一个调研问题！')
    for (const item of list.questions) {
      if (item.content === '')
        return MessagePlugin.warning('调研问题的标题不可为空，请检查表单！')
      if (item.questionType !== 'textarea' && item.options.length < 2)
        return MessagePlugin.warning('选项问题不可小于两个！')
      if (item.questionType !== 'textarea' && item.options.length >= 2) {
        for (const option of item.options) {
          if (option === '')
            return MessagePlugin.warning('选项内容不可为空！')
        }
      }
    }
    const valid = await formRef.value.validate()
    if (valid !== true) {
      MessagePlugin.warning('请完整填写表单！')
      releaseBtnFlag.value = false
      return
    }
    releaseBtnFlag.value = true
    const res = await httpInstance.post('cyt/survey', list)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      releaseBtnFlag.value = false
      return
    }
    MessagePlugin.success(res.msg)
    releaseBtnFlag.value = false
    router.back()
  } catch (e) {
    MessagePlugin.warning('请完整填写表单！')
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
  border-bottom: var(--td-component-stroke);
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
  border-bottom: var(--td-component-stroke);
}

.QN-question {
  p {
    margin-bottom: 1em;
  }
}

.QN-questions {
  margin-bottom: 3rem;
}

.t-radio-group {
  display: block;
  position: relative;
}

.t-radio,
.t-checkbox {
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
