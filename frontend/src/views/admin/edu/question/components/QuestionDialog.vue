<template>
  <div>
    <t-dialog
      :header="dialogTitle"
      v-model:visible="dialogFormVisible"
      :close-on-overlay-click="false"
      width="800px"
    >
      <t-form :data="form" size="small" ref="questionFormRef" :rules="rules">
        <t-form-item label="业务类型" :label-width="formLabelWidth">
          <t-input v-model="form.category" autocomplete="off" />
        </t-form-item>
        <t-form-item
          label="题目类型"
          :label-width="formLabelWidth"
          name="quesType"
        >
          <t-select
            v-model="form.quesType"
            placeholder="选择题目类型"
            @change="typeChange"
          >
            <t-option label="单选题" :value="1" />
            <t-option label="多选题" :value="2" />
            <t-option label="判断题" :value="3" />
          </t-select>
        </t-form-item>
        <t-form-item
          label="题干"
          :label-width="formLabelWidth"
          name="quesStem"
        >
          <t-textarea v-model="form.quesStem"
            :autosize="{ minRows: 2, maxRows: 4 }"
            autocomplete="off"
            maxlength="500"
            show-limit-number />
        </t-form-item>
        <t-form-item label="附件" :label-width="formLabelWidth">
          <t-upload
            :action="fsURL + 'upload/file/edu'"
            @success="handleSuccess"
            :before-upload="getFileDuration"
            @remove="handleRemove"
            :multiple="false"
            :limit="1"
            accept="image/png, image/jpeg, video/mp4, audio/mpeg"
            :file-list="urlList"
          >
            <t-button size="small" theme="primary">点击上传</t-button>
            <template #tip>
              <div>
                只能上传jpg/png/mp3/mp4文件，且不超过500MB
              </div>
            </template>
          </t-upload>
        </t-form-item>
        <t-form-item
          label="附件时长"
          v-show="form.fileDuration !== undefined && form.fileDuration !== ''"
          :label-width="formLabelWidth"
        >
          <t-input
            type="number"
            v-model="form.fileDuration"
            autocomplete="off"
            maxlength="5"
          />
        </t-form-item>
        <t-form-item label="选项" :label-width="formLabelWidth" name="options">
          <div style="width: 100%">
            <t-button
              theme="primary"
              :disabled="form.quesType === 3"
              @click="addOption"
            >
              新增选项
            </t-button>
            <t-divider content-position="left">选项</t-divider>
            <t-radio-group
              v-if="form.quesType === 1"
              v-model="answer"
              class="option-group"
            >
              <t-radio
                v-for="(item, index) in options"
                :key="index"
                :value="item"
                class="option-class"
              >
                <t-input-adornment>
                  <template #append>
                    <t-button variant="outline" theme="default" @click="deleteOption(index)">关闭</t-button>
                  </template>
                  <t-input
                    v-model="options[index]"
                    autocomplete="off"
                    @keyup="inputKeyUp"
                    maxlength="500"
                  ></t-input>
                </t-input-adornment>
              </t-radio>
            </t-radio-group>
            <t-checkbox-group
              v-if="form.quesType === 2"
              v-model="answer"
              class="option-group"
            >
              <t-checkbox
                v-for="(item, index) in options"
                :key="index"
                :value="options[index]"
                class="option-class"
              >
                <t-input-adornment>
                  <template #append>
                    <t-button variant="outline" theme="default" @click="deleteOption(index)">关闭</t-button>
                  </template>
                  <t-input
                    v-model="options[index]"
                    autocomplete="off"
                    @keyup="inputKeyUp"
                    maxlength="500"
                  ></t-input>
                </t-input-adornment>
              </t-checkbox>
            </t-checkbox-group>
            <t-radio-group
              v-if="form.quesType === 3"
              v-model="answer"
              class="option-group"
            >
              <t-radio
                v-for="(item, index) in options"
                :key="index"
                :value="item"
                class="option-class"
              >
                {{ item }}
              </t-radio>
            </t-radio-group>
          </div>
        </t-form-item>
        <t-form-item label="知识库名" :label-width="formLabelWidth">
          <t-input v-model="form.knowledge" autocomplete="off" />
        </t-form-item>
        <t-form-item label="题目解析" :label-width="formLabelWidth">
          <t-textarea v-model="form.quesExplain"
            :autosize="{ minRows: 2, maxRows: 4 }"
            autocomplete="off"
            maxlength="500"
            show-limit-number />
        </t-form-item>
        <t-form-item
          label="题目状态"
          :label-width="formLabelWidth"
          name="quesStat"
        >
          <t-select v-model="form.quesStat" placeholder="选择题目状态">
            <t-option label="生效" :value="1" />
            <t-option label="失效" :value="0" />
          </t-select>
        </t-form-item>
      </t-form>
      <template #footer>
        <span class="dialog-footer">
          <t-button size="small" @click="dialogFormVisible = false"
            >取 消</t-button
          >
          <t-button size="small" theme="primary" @click="submit"
            >提 交</t-button
          >
        </span>
      </template>
    </t-dialog>
  </div>
</template>
<script setup>
import { ref, reactive } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { questionBankApi } from '@/api/edu/questionBank.js'

const props = defineProps(['question'])
const emit = defineEmits(['update:tree', 'update:table'])

const dialogFormVisible = ref(false)
const questionFormRef = ref(null)
const managerType = ref('new')
const fsURL = import.meta.env.VITE_FILE_BASE_URL
// 展示类文件统一走 HTTPS 文件管理地址，避免混合内容被浏览器拦截
const displayURL = import.meta.env.VITE_FILE_BASE_URL || fsURL
const formLabelWidth = ref('120px')
const dialogTitle = ref('题目修改')
const form = reactive({
  libCode: '',
  category: '',
  quesType: null,
  knowledge: '',
  quesStem: '',
  options: '',
  answer: '',
  quesExplain: '',
  quesStat: 1,
  fileCode: null,
  fileType: null,
  fileDuration: null
})
const options = ref([])
const urlList = ref([])
const answer = ref([])
const fileDuration = ref('')

const rules = reactive({
  quesStem: [
    {
      required: true,
      min: 3,
      max: 500,
      message: '长度在 3 到 500 个字符',
      trigger: 'blur'
    }
  ],
  quesType: [{ required: true, message: '选择题目类型', trigger: 'blur' }],
  options: [{ required: true, message: '请配置选项', trigger: 'blur' }],
  quesStat: [{ required: true, message: '选择题目状态', trigger: 'blur' }]
})

const show = (obj) => {
  urlList.value = []
  if (obj.quesCode !== undefined) {
    Object.assign(form, JSON.parse(JSON.stringify(obj)))
    managerType.value = 'edit'
    dialogTitle.value = '题目修改'
    dialogFormVisible.value = true
    options.value = obj.options.split(',')
    if (obj.quesType === 2) {
      answer.value = obj.answer.split(',')
    } else {
      answer.value = obj.answer
    }
    // 判断有没有附件
    if (obj.fileCode !== null && obj.fileCode !== '') {
      urlList.value = [
        {
          name: obj.fileCode + '.' + obj.fileType,
          url:
            displayURL + '/upload/getFile/edu/' + obj.fileCode + '.' + obj.fileType
        }
      ]
    }
  } else if (obj.upCode !== undefined) {
    managerType.value = 'new'
    dialogTitle.value = '题目新增至[' + obj.libName + ']'
    dialogFormVisible.value = true
    answer.value = []
    Object.assign(form, {
      libCode: obj.libCode,
      category: '',
      quesType: null,
      knowledge: '',
      quesStem: '',
      options: '',
      answer: '',
      quesExplain: '',
      quesStat: 1,
      fileCode: null,
      fileType: null,
      fileDuration: null
    })
  }
  dialogFormVisible.value = true
}

const typeChange = () => {
  if (form.quesType === 3) {
    options.value = ['正确', '错误']
    answer.value = ''
  } else if (form.quesType === 2) {
    options.value = ['', '', '', '']
    answer.value = []
  } else if (form.quesType === 1) {
    options.value = ['', '', '', '']
    answer.value = ''
  }
}

const addOption = () => {
  options.value.push('选项' + options.value.length + 1)
}

const handleSuccess = (response) => {
  if (response.code !== 200) return MessagePlugin.error('文件上传出错！')
  const path = response.data.file.path
  const fileType = path.substring(path.indexOf('.') + 1).toLowerCase()
  const fileCode = path.substring(path.indexOf('edu/') + 4, path.indexOf('.'))
  form.fileType = fileType
  form.fileCode = fileCode
}

const getFileDuration = (file) => {
  const fileName = file.name || ''
  const ext = fileName.split('.')[fileName.split('.').length - 1].toLowerCase()
  if (ext === 'jpg' || ext === 'png') {
    form.fileDuration = 5
  } else if (ext === 'mp4' || ext === 'mp3') {
    const url = URL.createObjectURL(file)
    const audioElement = new Audio(url)
    audioElement.addEventListener('loadedmetadata', function (_event) {
      form.fileDuration = audioElement.duration.toFixed(0) // 时长为秒，小数，182.36
    })
  } else {
    MessagePlugin.error(
      '您所提交的文件格式不支持！目前仅支持【jpg、png、mp3、mp4】'
    )
    return false
  }
}

const handleRemove = () => {
  urlList.value = []
  form.fileDuration = null
  form.fileType = null
  form.fileCode = null
}

const deleteOption = (index) => {
  options.value.splice(index, 1)
}

const inputKeyUp = (e) => {
  // 避免英文符号的逗号导致无法分开选项！
  e.target.value = e.target.value.replace(/,/g, '，').trim()
}

const submit = async () => {
  options.value.forEach((item, index, arr) => {
    arr[index] = item.replace(/,/g, '，').trim()
  })
  form.options = options.value.join(',')

  if (form.quesType === 2) {
    if (answer.value.length < 2)
      return MessagePlugin.error('多选题必须有一个以上的答案！')
    answer.value.forEach((item) => {
      let flag = 0
      options.value.forEach((option) => {
        if (item === option) flag = 1
      })
      if (flag === 0) {
        answer.value = []
        return MessagePlugin.error('由于修改了选项，请重新选择答案！')
      }
    })
    form.answer = answer.value.join(',')
  } else {
    if (answer.value == null || answer.value === '')
      return MessagePlugin.error('请选择答案！')
    let flag = 0
    options.value.forEach((option) => {
      if (answer.value === option) flag = 1
    })
    if (flag === 0) {
      answer.value = ''
      return MessagePlugin.error('由于修改了选项，请重新选择答案！')
    }
    form.answer = answer.value
  }

  if (!questionFormRef.value) return

  const valid = await questionFormRef.value.validate()
  if (valid === true) {
    try {
      let res
      if (managerType.value === 'new') {
        res = await questionBankApi.addQuestion(form)
      } else if (managerType.value === 'edit') {
        res = await questionBankApi.updateQuestion(form)
      }

      if (res.code !== 200) return MessagePlugin.error(res.msg)
      MessagePlugin.success(res.msg)

      dialogFormVisible.value = false
      emit('update:tree')
      emit('update:table')
    } catch (error) {
      MessagePlugin.error('操作失败，请重试')
    }
  } else {
    MessagePlugin.error('表单校验失败！')
    return false
  }
}

defineExpose({
  show
})
</script>
<style scoped>
.option-group {
  width: 100%;
  .option-class {
    display: block;
    width: 90%;
    padding-top: 5px;
  }
}
</style>
