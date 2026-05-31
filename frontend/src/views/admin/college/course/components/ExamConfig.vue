<template>
  <div>
    <t-dialog
      header="试题配置"
      width="70%"
      v-model:visible="dialogFormVisible"
      :close-on-overlay-click="false"
      @close="onClose"
    >
      <t-row :gutter="10">
        <t-col :span="4">
          <t-card class="box-card">
            <t-input
              size="small"
              placeholder="输入关键字进行过滤"
              v-model="filterText"
            ></t-input>
            <t-tree
              :data="treeData"
              ref="tree"
              :keys="{ value: 'libCode', label: 'libName', children: 'children' }"
              activable
              @click="nodeclick"
              :filter="filterNode"
            >
              <template #default="{ node }">
                <span class="custom-tree-node">
                  <span
                    >{{ node.data.libName }}
                    <t-tag v-if="node.data.libLevel === 2" size="small">{{
                      node.data.quesNum
                    }}</t-tag></span
                  >
                </span>
              </template>
            </t-tree>
          </t-card>
        </t-col>
        <t-col :span="8">
          <t-row style="margin-bottom: 5px">
            <t-col :span="12"
              ><t-tag>选择：{{ templateFormData.libName }}</t-tag></t-col
            >
          </t-row>
          <t-form
            v-if="templateFormData.libName"
            ref="templateForm"
            :data="templateFormData"
            :rules="rules"
            size="small"
            label-width="120px"
          >
            <t-form-item label="抽取题数" name="passNum">
              <t-input-number
                v-model="templateFormData.quesNum"
                size="small"
                :step="1"
                :max="templateFormRule.quesLimit"
                label="题目数"
                :style="{ width: '100%' }"
              ></t-input-number>
            </t-form-item>
            <t-form-item label="通过题数" name="passNum">
              <t-input-number
                v-model="templateFormData.passNum"
                placeholder="通过题数"
                :step="1"
                :min="1"
                :max="templateFormData.quesNum"
                :style="{ width: '100%' }"
              >
              </t-input-number>
            </t-form-item>
            <t-form-item label="考试时间(分)" name="examTime">
              <t-input
                type="number"
                v-model="templateFormData.examTime"
                placeholder="请输入考试时间(分)"
                :style="{ width: '100%' }"
              >
              </t-input>
            </t-form-item>
            <t-form-item label="重复考试" name="repeatFlag">
              <t-select
                v-model="templateFormData.repeatFlag"
                placeholder="请选择重复考试"
                :style="{ width: '100%' }"
              >
                <t-option
                  v-for="(item, index) in repeatFlagOptions"
                  :key="index"
                  :label="item.label"
                  :value="item.value"
                ></t-option>
              </t-select>
            </t-form-item>
            <t-form-item label="选项随机" name="optionRandom">
              <t-select
                v-model="templateFormData.optionRandom"
                placeholder="请选择选项随机"
                :style="{ width: '100%' }"
              >
                <t-option
                  v-for="(item, index) in optionRandomOptions"
                  :key="index"
                  :label="item.label"
                  :value="item.value"
                ></t-option>
              </t-select>
            </t-form-item>
          </t-form>
        </t-col>
      </t-row>
      <template #footer>
        <div class="dialog-footer">
          <t-button size="small" @click="dialogFormVisible = false"
            >取 消</t-button
          >
          <t-button size="small" theme="danger" @click="cancelExam()"
            >取消考试</t-button
          >
          <t-button size="small" theme="primary" @click="submit()"
            >提 交</t-button
          >
        </div>
      </template>
    </t-dialog>
  </div>
</template>
<script setup>
import { ref, reactive, watch, nextTick } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { questionLibApi } from '@/api/edu/questionLib'
import { courseApi } from '@/api/college/course'
import { examApi } from '@/api/college/exam'

const dialogFormVisible = ref(false)
const filterText = ref('')
const courseId = ref('')
const treeData = ref([])
const tree = ref(null)
const templateForm = ref(null)
const opType = ref('add')
const emit = defineEmits(['refresh'])
const templateFormData = reactive({
  courseId: '',
  libCode: '',
  passNum: 0,
  examTime: 10,
  repeatFlag: 1,
  optionRandom: 1,
  quesLimit: 1,
  libName: ''
})

const templateFormRule = reactive({ quesLimit: 1, quesNum: 1 })

const rules = reactive({
  passNum: [{ required: true, message: '通过题数', trigger: 'blur' }],
  examTime: [],
  repeatFlag: [
    { required: true, message: '请选择重复考试', trigger: 'change' }
  ],
  optionRandom: [
    { required: true, message: '请选择选项随机', trigger: 'change' }
  ]
})

const repeatFlagOptions = ref([
  { label: '可重复考试', value: 1 },
  { label: '不可重复考试', value: 0 }
])
const optionRandomOptions = ref([
  { label: '是', value: 1 },
  { label: '否', value: 0 }
])

const show = (courseIdData) => {
  courseId.value = courseIdData
  getTreeData().then(() => getTemplate())
  dialogFormVisible.value = true
}

const getTemplate = () => {
  examApi.getCourseExamTemplate(courseId.value).then((res) => {
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    if (res.data != null) {
      opType.value = 'edit'
      Object.assign(templateFormData, res.data)

      templateFormData.quesLimit = res.data.quesNum
      nextTick(() => {
        const record = tree.value.getItem(templateFormData.libCode)
        if (!record || !record.parent || !record.parent.parent) {
          templateFormData.quesLimit = 0
          templateFormData.libName =
            '题库取路径错误或已被移除,请重新选择试题抽取路径！'
          MessagePlugin.error(
            '获取配置的试题抽取路径错误或已被移除！请重新选择试题抽取路径。'
          )
        } else {
          templateFormData.libName =
            record.parent.parent.data.libName +
            '->' +
            record.parent.data.libName +
            '->' +
            record.data.libName
          templateFormData.quesLimit = record.data.quesNum
        }
      })
    } else {
      opType.value = 'add'
      templateFormData.courseId = courseId.value
    }
  })
}

const getTreeData = async () => {
  const res = await questionLibApi.getTreeData()
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  treeData.value = res.data
}

const filterNode = (node) => {
  if (!filterText.value) return true
  return node.data.libName.indexOf(filterText.value) !== -1
}

const nodeclick = (context) => {
      const { node: treeNode } = context;
      const obj = treeNode.data;
  if (obj.libLevel === 2) {
    templateFormData.libName =
      treeNode.parent.parent.data.libName +
      '->' +
      treeNode.parent.data.libName +
      '->' +
      obj.libName
    templateFormData.libCode = obj.libCode
    templateFormData.quesNum = 1
    templateFormData.passNum = 1
    templateFormRule.quesLimit = obj.quesNum
  }
}

const submit = async () => {
  if (templateFormData.passNum > templateFormData.quesNum) {
    return MessagePlugin.error('通过考试的题目数量不可以超过总题数！')
  }

  if (templateForm.value) {
    const valid = await templateForm.value.validate()
    if (valid !== true) return

    try {
      let res
      if (opType.value === 'add') {
        res = await examApi.addCourseExamTemplate(templateFormData)
      } else if (opType.value === 'edit') {
        res = await examApi.updateCourseExamTemplate(templateFormData)
      }

      if (res.code !== 200) return MessagePlugin.error(res.msg)
      emit('refresh')
      MessagePlugin.success('操作成功！')
      dialogFormVisible.value = false
    } catch (error) {
      MessagePlugin.error('操作失败，请稍后重试')
    }
  }
}

const cancelExam = async () => {
  try {
    const res = await examApi.cancelCourseExam(courseId.value)
    if (res.code !== 200) return MessagePlugin.error(res.msg)

    MessagePlugin.success(res.msg)
    dialogFormVisible.value = false
  } catch (error) {
    MessagePlugin.error('取消考试失败，请稍后重试')
  }
}

const onClose = () => {
  Object.assign(templateFormData, {
    courseId: '',
    libCode: '',
    passNum: 1,
    examTime: 10,
    repeatFlag: 1,
    optionRandom: 1,
    quesLimit: '',
    libName: ''
  })
}

watch(filterText, (val) => {
  if (tree.value) {
    tree.value.refresh()
  }
})

defineExpose({
  show
})
</script>
<style lang="less" scoped>
.box-card {
  height: 450px;
  overflow: auto;
}
</style>
