<template>
  <t-dialog header="试题预览" v-model:visible="dialogVisible" width="50%">
    <div class="main-container" v-if="question !== null">
      <div class="ques-title">
        [{{
          question.quesType === 2
            ? '多选题'
            : question.quesType === 3
              ? '判断题'
              : '单选题'
        }}]{{ question.quesStem }}
      </div>
      <div
        class="file-view"
        v-if="
          question.fileType !== null &&
          (question.fileType === 'jpg' || question.fileType === 'png')
        "
      >
        <t-image
          style="width: 100px; height: 100px"
          :src="fileUrl"
          :preview-src-list="srcList"
        />
      </div>
      <div v-else-if="question.fileType === 'mp4'">
        <video :src="fileUrl" controls height="400px"></video>
      </div>
      <div v-else-if="question.fileType === 'mp3'">
        <audio :src="fileUrl" controls width="300px"></audio>
      </div>
      <div class="ques-options" v-if="question.quesType === 2">
        <t-checkbox-group v-model="answer.checkbox">
          <t-checkbox
            v-for="(item, index) of options"
            :key="index"
            class="ques-option"
            :label="item"
            :value="item"
            border
            disabled
          ></t-checkbox>
        </t-checkbox-group>
      </div>
      <div class="ques-options" v-else>
        <t-radio-group v-model="answer.radio">
          <t-radio
            v-for="(item, index) of options"
            :key="index"
            class="ques-option"
            :value="item"
            border
            disabled
            >{{ item }}</t-radio
          >
        </t-radio-group>
      </div>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <t-button size="small" @click="dialogVisible = false">关 闭</t-button>
      </span>
    </template>
  </t-dialog>
</template>
<script setup>
import { ref, reactive } from 'vue'

const dialogVisible = ref(false)
// 展示类文件统一走 HTTPS 文件管理地址，避免混合内容被浏览器拦截
const fsURL = import.meta.env.VITE_FILE_BASE_URL
const question = ref(null)
const fileUrl = ref('')
const srcList = ref([])
const options = ref([])
const answer = reactive({ radio: '', checkbox: [] })
const optionRand = ref(0)

const show = (q) => {
  question.value = q
  getQuestion()
  dialogVisible.value = true
}

const getQuestion = () => {
  if (!question.value) return

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
    // 判断是否选项随机，不随机判断题
    options.value.sort(function () {
      return 0.5 - Math.random()
    })
  }
  const standAnswer = question.value.answer
  if (question.value.quesType === 2) {
    answer.checkbox = standAnswer.split(',')
  } else {
    answer.radio = standAnswer
  }
}

defineExpose({
  show
})
</script>
<style lang="less" scoped>
.main-container {
  overflow: auto;
  height: 100%;
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
    }

    .t-radio,
    .t-checkbox {
      color: #000;
    }

    :deep(.t-checkbox__label),
    :deep(.t-radio__label) {
      display: inline;
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
