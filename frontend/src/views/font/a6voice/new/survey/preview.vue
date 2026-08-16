<template>
  <t-card style="max-width: 900px; margin: 30px auto">
    <div class="preview-header">
      <t-button theme="default" variant="text" @click="router.back()">返回编辑</t-button>
      <span class="preview-title">调研预览</span>
    </div>
    <h2 class="survey-title">{{ questionnaire.title || '（未填写标题）' }}</h2>
    <p class="survey-category">
      <span v-if="questionnaire.category">
        分类：{{ categoryName }}
      </span>
      <span v-if="questionnaire.compDate">截止：{{ questionnaire.compDate }}</span>
    </p>
    <div class="survey-content" v-if="questionnaire.content">
      <SafeHtml :html="questionnaire.content" />
    </div>
    <t-divider></t-divider>
    <div class="QN-questions">
      <div v-for="(question, i) in questionnaire.questions" :key="i" class="QN-question">
        <p class="question-title">
          {{ question.sort }}. {{ question.content || '（未填写题目）' }}
        </p>
        <div v-if="question.questionType === 'radio'">
          <t-radio-group>
            <t-radio v-for="(option, j) in question.options" :key="j" :value="option">
              {{ option === '' ? '单选选项-' + (j + 1) : option }}
            </t-radio>
          </t-radio-group>
        </div>
        <div v-else-if="question.questionType === 'checkbox'">
          <t-checkbox-group>
            <t-checkbox v-for="(option, j) in question.options" :key="j" :value="option">
              {{ option === '' ? '多选选项-' + (j + 1) : option }}
            </t-checkbox>
          </t-checkbox-group>
        </div>
        <div v-else-if="question.questionType === 'textarea'">
          <t-textarea disabled placeholder="填空题，答题时填写" />
        </div>
      </div>
    </div>
    <div v-if="questionnaire.questions.length < 1" style="text-align: center; color: #999">
      尚未添加题目
    </div>
  </t-card>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useDictStore } from '@/stores'

const router = useRouter()
const dictStore = useDictStore()

const questionnaire = ref(
  JSON.parse(window.localStorage.getItem('SET_QUESTIONNAIRE')) || {
    title: '',
    category: '',
    compDate: '',
    content: '',
    questions: []
  }
)

const categoryName = computed(() => {
  if (!questionnaire.value.category) return ''
  const item = dictStore
    .getDictByNames('cyt_artical_category', 1)
    .find((it) => parseInt(it.code) === parseInt(questionnaire.value.category))
  return item ? item.codeval : ''
})
</script>

<style scoped>
.preview-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;
}

.preview-title {
  font-size: 16px;
  font-weight: 600;
}

.survey-title {
  text-align: center;
  margin: 10px 0;
}

.survey-category {
  text-align: center;
  color: var(--td-text-color-placeholder);
  display: flex;
  justify-content: center;
  gap: 16px;
}

.survey-content {
  margin: 16px 0;
}

.QN-question {
  margin: 14px 0;
  padding: 12px 16px;
  border: 1px solid var(--td-component-stroke);
  border-radius: 8px;
}

.question-title {
  font-weight: 500;
  margin: 0 0 10px 0;
}
</style>
