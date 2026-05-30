<template>
  <div>
    <el-dialog
      title="试题配置"
      width="70%"
      v-model="dialogFormVisible"
      :close-on-click-modal="false"
      @close="onClose"
    >
      <el-row :gutter="10">
        <el-col :span="8">
          <el-card class="box-card">
            <el-input
              size="small"
              placeholder="输入关键字进行过滤"
              v-model="filterText"
            ></el-input>
            <el-tree
              :data="treeData"
              ref="tree"
              node-key="libCode"
              highlight-current
              @node-click="nodeclick"
              :filter-node-method="filterNode"
            >
              <template #default="{ node, data }">
                <span class="custom-tree-node">
                  <span
                    >{{ data.libName }}
                    <el-tag v-if="data.libLevel === 2" size="small">{{
                      data.quesNum
                    }}</el-tag></span
                  >
                </span>
              </template>
            </el-tree>
          </el-card>
        </el-col>
        <el-col :span="16">
          <el-row style="margin-bottom: 5px">
            <el-col :span="24"
              ><el-tag>选择：{{ templateFormData.libName }}</el-tag></el-col
            >
          </el-row>
          <el-form
            v-if="templateFormData.libName"
            ref="templateForm"
            :model="templateFormData"
            :rules="rules"
            size="small"
            label-width="120px"
          >
            <el-form-item label="抽取题数" prop="passNum">
              <el-input-number
                v-model="templateFormData.quesNum"
                size="small"
                :step="1"
                :max="templateFormRule.quesLimit"
                label="题目数"
                :style="{ width: '100%' }"
              ></el-input-number>
            </el-form-item>
            <el-form-item label="通过题数" prop="passNum">
              <el-input-number
                v-model="templateFormData.passNum"
                placeholder="通过题数"
                :step="1"
                :min="1"
                :max="templateFormData.quesNum"
                :style="{ width: '100%' }"
              >
              </el-input-number>
            </el-form-item>
            <el-form-item label="考试时间(分)" prop="examTime">
              <el-input
                type="number"
                v-model="templateFormData.examTime"
                placeholder="请输入考试时间(分)"
                :style="{ width: '100%' }"
              >
              </el-input>
            </el-form-item>
            <el-form-item label="重复考试" prop="repeatFlag">
              <el-select
                v-model="templateFormData.repeatFlag"
                placeholder="请选择重复考试"
                :style="{ width: '100%' }"
              >
                <el-option
                  v-for="(item, index) in repeatFlagOptions"
                  :key="index"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="选项随机" prop="optionRandom">
              <el-select
                v-model="templateFormData.optionRandom"
                placeholder="请选择选项随机"
                :style="{ width: '100%' }"
              >
                <el-option
                  v-for="(item, index) in optionRandomOptions"
                  :key="index"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <template #footer>
        <div class="dialog-footer">
          <el-button size="small" @click="dialogFormVisible = false"
            >取 消</el-button
          >
          <el-button size="small" type="danger" @click="cancelExam()"
            >取消考试</el-button
          >
          <el-button size="small" type="primary" @click="submit()"
            >提 交</el-button
          >
        </div>
      </template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, reactive, watch, nextTick } from 'vue'
import { ElMessage } from 'element-plus'
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
    if (res.code !== 200) return ElMessage.error(res.msg)
    if (res.data != null) {
      opType.value = 'edit'
      Object.assign(templateFormData, res.data)

      templateFormData.quesLimit = res.data.quesNum
      nextTick(() => {
        const record = tree.value.getNode(templateFormData.libCode)
        if (!record || !record.parent || !record.parent.parent) {
          templateFormData.quesLimit = 0
          templateFormData.libName =
            '题库取路径错误或已被移除,请重新选择试题抽取路径！'
          ElMessage.error(
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
  if (res.code !== 200) return ElMessage.error(res.msg)
  treeData.value = res.data
}

const filterNode = (value, data) => {
  if (!value) return true
  return data.libName.indexOf(value) !== -1
}

const nodeclick = (obj, node) => {
  if (obj.libLevel === 2) {
    templateFormData.libName =
      node.parent.parent.data.libName +
      '->' +
      node.parent.data.libName +
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
    return ElMessage.error('通过考试的题目数量不可以超过总题数！')
  }

  if (templateForm.value) {
    await templateForm.value.validate(async (valid) => {
      if (!valid) return

      try {
        let res
        if (opType.value === 'add') {
          res = await examApi.addCourseExamTemplate(templateFormData)
        } else if (opType.value === 'edit') {
          res = await examApi.updateCourseExamTemplate(templateFormData)
        }

        if (res.code !== 200) return ElMessage.error(res.msg)
        emit('refresh')
        ElMessage.success('操作成功！')
        dialogFormVisible.value = false
      } catch (error) {
        ElMessage.error('操作失败，请稍后重试')
      }
    })
  }
}

const cancelExam = async () => {
  try {
    const res = await examApi.cancelCourseExam(courseId.value)
    if (res.code !== 200) return ElMessage.error(res.msg)

    ElMessage.success(res.msg)
    dialogFormVisible.value = false
  } catch (error) {
    ElMessage.error('取消考试失败，请稍后重试')
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
    tree.value.filter(val)
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
