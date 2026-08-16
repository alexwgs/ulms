<template>
  <t-card>
    <div class="sub-page-header">
      <t-button theme="default" variant="text" @click="router.back()">返回</t-button>
      <span class="sub-page-title">创建新调研</span>
    </div>

    <div class="QN-header">
      <t-form ref="formRef" :rules="rules" :data="questionnaire" label-width="80px">
        <t-form-item label="调研标题" name="title">
          <t-input v-model="questionnaire.title" size="small" placeholder="请输入调研标题（2-50 字）"></t-input>
        </t-form-item>
        <t-row :gutter="16">
          <t-col :span="6">
            <t-form-item label="调研分类" name="category">
              <t-select v-model="questionnaire.category" size="small" placeholder="请选择">
                <t-option v-for="item in dictStore.getDictByNames('cyt_artical_category', 1).filter(
                  (item) => item.status == 1
                )" :key="item.code" :label="item.codeval" :value="parseInt(item.code)"></t-option>
              </t-select>
            </t-form-item>
          </t-col>
          <t-col :span="6">
            <t-form-item label="截止时间" name="compDate">
              <t-date-picker size="small" style="width: 200px" v-model="questionnaire.compDate" mode="date"
                enable-time-picker placeholder="选择日期时间"></t-date-picker>
            </t-form-item>
          </t-col>
          <t-col :span="12">
            <t-form-item>
              <t-space>
                <t-button theme="primary" size="small" variant="outline" @click="addQuestion('radio')">
                  <template #icon><DynamicIcon name="add" /></template>单选
                </t-button>
                <t-button theme="primary" size="small" variant="outline" @click="addQuestion('checkbox')">
                  <template #icon><DynamicIcon name="add" /></template>多选
                </t-button>
                <t-button theme="primary" size="small" variant="outline" @click="addQuestion('textarea')">
                  <template #icon><DynamicIcon name="add" /></template>填空
                </t-button>
                <t-button theme="primary" size="small" @click="preview">预览</t-button>
                <t-button theme="success" size="small" :disabled="releaseBtnFlag" @click="release">发布</t-button>
              </t-space>
            </t-form-item>
          </t-col>
        </t-row>
        <t-form-item label="调研正文" name="content">
          <WangEditor v-model="questionnaire.content"></WangEditor>
        </t-form-item>
      </t-form>
    </div>

    <!-- 题目列表（卡片式，选项直接输入、增删按钮常显） -->
    <div class="QN-questions">
      <div class="QN-question" v-for="(question, i) in questionnaire.questions" :key="i">
        <div class="question-head">
          <span class="question-no">第 {{ question.sort }} 题</span>
          <span class="question-type-tag">{{ typeName(question.questionType) }}</span>
          <div class="question-ops">
            <t-link size="small" theme="primary" :underline="false" :disabled="question.sort === 1"
              @click="orderChange('up', i)">上移</t-link>
            <t-link size="small" theme="primary" :underline="false"
              :disabled="question.sort === questionnaire.questions.length" @click="orderChange('down', i)">下移</t-link>
            <t-link size="small" theme="danger" :underline="false" @click="deleteQuestion(i)">删除</t-link>
          </div>
        </div>
        <t-input v-model="question.content" size="small" placeholder="请输入题目内容" class="question-input" />
        <div v-if="question.questionType !== 'textarea'" class="options-area">
          <div v-for="(label, j) in question.options" :key="j" class="option-row">
            <div class="option-input-wrap">
              <t-radio v-if="question.questionType === 'radio'" :value="label"
                :checked="false" class="option-icon" />
              <t-checkbox v-else :value="label" :checked="false" class="option-icon" />
              <t-input v-model="question.options[j]" size="small" class="option-input"
                :placeholder="'选项 ' + (j + 1)" />
            </div>
            <div class="option-ops">
              <t-button variant="text" theme="danger" size="small" @click="removeLable(i, j)">
                <template #icon><DynamicIcon name="delete" /></template>
              </t-button>
              <t-button variant="text" theme="primary" size="small" @click="addLable(i, j)">
                <template #icon><DynamicIcon name="add" /></template>
              </t-button>
            </div>
          </div>
          <t-button variant="outline" theme="primary" size="small" @click="addLable(i, question.options.length - 1)">
            <template #icon><DynamicIcon name="add" /></template>添加选项
          </t-button>
        </div>
        <div v-else class="textarea-tip">填空题：答题时由参与者填写</div>
      </div>

      <div v-if="questionnaire.questions.length === 0" class="empty-tip">
        尚未添加题目，点击上方「单选 / 多选 / 填空」添加
      </div>
    </div>
  </t-card>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { useRouter } from 'vue-router'
import { MessagePlugin } from 'tdesign-vue-next'
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
      options: ['', '']
    },
    checkbox: {
      sort: 1,
      questionType: 'checkbox',
      content: '',
      checkList: [],
      options: ['', '']
    },
    textarea: {
      sort: 1,
      questionType: 'textarea',
      content: '',
      options: []
    }
  }
  return template[questionType]
}

const typeName = (type) => {
  const map = { radio: '单选题', checkbox: '多选题', textarea: '填空题' }
  return map[type] || type
}

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
  category: [{ required: true, message: '请选择调研板块', trigger: 'change' }],
  compDate: [{ required: true, message: '请选择调研截止日期', trigger: 'change' }],
  content: [
    { required: true, message: '请输入调研正文内容！', trigger: 'blur' },
    { min: 10, message: '大于 10 个字符', trigger: 'blur' }
  ]
}

const releaseBtnFlag = ref(false)

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
    const list = JSON.parse(JSON.stringify(questionnaire))
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
    window.localStorage.removeItem('SET_QUESTIONNAIRE')
    releaseBtnFlag.value = false
    router.back()
  } catch (e) {
    MessagePlugin.warning('请完整填写表单！')
    releaseBtnFlag.value = false
  }
}

const preview = () => {
  window.localStorage.setItem('SET_QUESTIONNAIRE', JSON.stringify(questionnaire))
  router.push('/youngTalk/question/preview')
}

const orderChange = (type, i) => {
  if (type === 'down') {
    questionnaire.questions[i] = questionnaire.questions.splice(i + 1, 1, questionnaire.questions[i])[0]
  } else if (type === 'up') {
    questionnaire.questions[i] = questionnaire.questions.splice(i - 1, 1, questionnaire.questions[i])[0]
  }
  for (let i = 0; i < questionnaire.questions.length; i++) {
    questionnaire.questions[i].sort = i + 1
  }
}
</script>

<style scoped lang="less">
.sub-page-header {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 12px;

  .sub-page-title {
    font-size: 18px;
    font-weight: 600;
  }
}

.QN-header {
  margin-bottom: 20px;
}

.QN-questions {
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.QN-question {
  border: 1px solid var(--td-component-stroke);
  border-radius: 10px;
  padding: 14px 16px;
  background: var(--td-bg-color-container);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
}

.question-head {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 10px;

  .question-no {
    font-weight: 600;
    color: var(--td-brand-color);
  }

  .question-type-tag {
    font-size: 12px;
    color: var(--td-text-color-placeholder);
    background: var(--td-brand-color-light);
    border-radius: 4px;
    padding: 2px 8px;
  }

  .question-ops {
    margin-left: auto;
    display: flex;
    gap: 8px;
  }
}

.question-input {
  margin-bottom: 10px;
}

.options-area {
  display: flex;
  flex-direction: column;
  gap: 8px;

  .option-row {
    display: flex;
    align-items: center;
    gap: 4px;

    .option-input-wrap {
      flex: 1;
      display: flex;
      align-items: center;
      gap: 6px;

      .option-icon {
        flex: 0 0 auto;
      }

      .option-input {
        flex: 1;
      }
    }

    .option-ops {
      flex: 0 0 auto;
      display: flex;
      gap: 0;
    }
  }
}

.textarea-tip {
  color: var(--td-text-color-placeholder);
  font-size: 13px;
  padding: 4px 0;
}

.empty-tip {
  text-align: center;
  color: var(--td-text-color-placeholder);
  padding: 30px 0;
}
</style>
