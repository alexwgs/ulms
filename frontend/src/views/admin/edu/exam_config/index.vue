<template>
  <div style="height: 100%">
    <el-card class="box-card">
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input
            placeholder="模糊搜索"
            size="small"
            v-model="queryInfo.query"
          >
            <el-button slot="append" icon="search" @click="getExamInfoList()" />
          </el-input>
        </el-col>
        <el-col :span="6">
          <el-select
            v-model="queryInfo.status"
            size="small"
            placeholder="请选择状态"
            @change="getExamInfoList()"
          >
            <el-option label="有效" :value="1" />
            <el-option label="无效" :value="0" />
          </el-select>
        </el-col>
        <el-col :span="8">
          <el-button type="primary" size="small" @click="addNewExamInfoBtn()"
            >新建考试</el-button
          >
        </el-col>
      </el-row>
      <el-alert
        title="操作说明"
        type="info"
        description="请正确使用字典配置：1.点击名单可以查看或配置考试名单。2.试题请配置。3.点击复议可以查看该考试的复议情况！4.红色的时钟表示该场考试需要设置考试预约时间"
        :closable="false"
      >
      </el-alert>
      <el-table
        :data="examTableList"
        size="small"
        height="calc(100vh - 400px)"
        stripe
        @sort-change="tableSort"
        style="width: 100%"
      >
        <el-table-column
          prop="examName"
          label="考试名称"
          sortable="custom"
          show-overflow-tooltip
        />
        <el-table-column
          prop="begDate"
          label="开始时间"
          width="100px"
          show-overflow-tooltip
          sortable="custom"
        />
        <el-table-column
          prop="endDate"
          label="结束时间"
          width="100px"
          show-overflow-tooltip
          sortable="custom"
        />
        <el-table-column label="名单" width="60px">
          <template #default="scope">
            <el-link
              size="small"
              type="primary"
              @click="() => examScoreRef.show(scope.row.examCode)"
              >管理</el-link
            >
          </template>
        </el-table-column>
        <el-table-column label="试题" width="70px">
          <template #default="scope">
            <el-link
              size="small"
              :type="scope.row.quesNum == 0 ? 'warning' : 'success'"
              @click="() => examQuestionConfigRef.show(scope.row.examCode)"
              >{{
                scope.row.quesNum == 0
                  ? '未配置'
                  : '共' + scope.row.quesNum + '题'
              }}</el-link
            >
          </template>
        </el-table-column>
        <el-table-column
          prop="bookExam"
          label="预约"
          width="100px"
          show-overflow-tooltip
          sortable="custom"
        >
          <template #default="scope">
            <el-link
              v-if="scope.row.bookExam === 1"
              size="small"
              type="primary"
              @click="() => examTimeConfigRef.show(scope.row.examCode)"
              >配置</el-link
            >
            <el-text v-else size="small">无需</el-text>
          </template>
        </el-table-column>

        <el-table-column
          prop="disputeDate"
          label="复议"
          show-overflow-tooltip
          sortable="custom"
          width="100px"
        >
          <template #default="scope">
            <div v-if="scope.row.disputeDate == null">不可复议</div>
            <el-link
              v-else
              size="small"
              type="success"
              @click="disputeManageRef.show(scope.row.examCode)"
              >{{ scope.row.disputeDate }}</el-link
            >
          </template>
        </el-table-column>
        <el-table-column label="阅卷" width="70px">
          <template #default="scope">
            <el-popconfirm
              v-if="scope.row.ifRead === 0"
              width="320"
              title="阅卷后试卷不可修改，不可恢复，将结算所有考试人员得分！"
              @confirm="readTestAction(scope.row.examCode)"
            >
              <template #reference>
                <el-link
                  size="small"
                  type="primary"
                  underline="hover"
                  slot="reference"
                  >阅卷</el-link
                >
              </template>
            </el-popconfirm>
            <span v-else-if="scope.row.ifRead === 1">{{
              dictStore.getDictLabel('yes_or_not', scope.row.ifRead)
            }}</span>
            <el-text v-else size="small">无需</el-text>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="160px">
          <template #default="scope">
            <el-button
              type="primary"
              icon="edit"
              size="small"
              @click="examInfoEditBtn(scope.row)"
              circle
            />
            <el-button
              v-if="scope.row.bookExam === 1"
              icon="timer"
              size="small"
              @click="() => bookInfoRef.show(scope.row.examCode)"
              circle
            />
            <el-popover placement="right" width="400" trigger="click">
              <template #reference>
                <el-button
                  style="margin-left: 10px"
                  icon="document"
                  size="small"
                  circle
                ></el-button>
              </template>
              <div style="text-align: right; margin: 0">
                <el-button
                  type="primary"
                  size="small"
                  @click="
                    examConfigApi.downloadExamScoreReport(scope.row.examCode)
                  "
                  >成绩报表（个人）</el-button
                >
                <el-button type="primary" size="small"
                  >成绩报表（小组）</el-button
                >
                <el-button
                  type="primary"
                  size="small"
                  @click="
                    examConfigApi.downloadExamTestReport(scope.row.examCode)
                  "
                  >试卷明细</el-button
                >
              </div>
            </el-popover>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="pageSizes"
        :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </el-card>

    <!-- 弹出窗口 -->
    <el-dialog
      :title="examFormTitle"
      :close-on-click-modal="false"
      width="80%"
      :fullscreen="true"
      v-model="examFormVisible"
    >
      <el-form
        :model="examForm"
        ref="examFormRef"
        size="small"
        :rules="examFormRules"
      >
        <el-divider content-position="left">基础配置</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item
              label="考试名称"
              :label-width="formLabelWidth"
              prop="examName"
            >
              <el-input
                size="small"
                v-model="examForm.examName"
                autocomplete="off"
              />
            </el-form-item>
            <el-form-item
              label="考试周期"
              :label-width="formLabelWidth"
              prop="cycleDate"
            >
              <el-date-picker
                v-model="datetimeRange"
                size="small"
                style="width: 100%"
                type="datetimerange"
                value-format="YYYY-MM-DD HH:mm:ss"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
              />
            </el-form-item>
            <el-form-item
              label="考场区域"
              :label-width="formLabelWidth"
              prop="areaCode"
            >
              <el-select
                size="small"
                v-model="examForm.areaCode"
                placeholder="是否指定考场"
              >
                <el-option label="不限制考场" value="" />
                <el-option
                  v-for="item in areaConfig"
                  :key="item.areaCode"
                  :label="item.areaName"
                  :value="item.areaCode"
                />
              </el-select>
            </el-form-item>
            <el-form-item
              label="考试时长(分)"
              :label-width="formLabelWidth"
              prop="examTime"
            >
              <el-input
                size="small"
                v-model="examForm.examTime"
                type="number"
                autocomplete="off"
              />
            </el-form-item>
            <el-form-item
              label="考试中断"
              :label-width="formLabelWidth"
              prop="ifBreak"
            >
              <el-select
                size="small"
                v-model="examForm.ifBreak"
                placeholder="考试是否可中途退出"
              >
                <el-option
                  v-for="item in dictStore.dictList.yes_or_not"
                  :key="item.code"
                  :label="item.codeval"
                  :value="parseInt(item.code)"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="试卷用途"
              :label-width="formLabelWidth"
              prop="examUse"
            >
              <el-select
                v-model="examForm.examUse"
                size="small"
                style="width: 120px"
                placeholder="请选择"
              >
                <el-option label="常规试卷" :value="0" />
                <el-option label="互动答题" :value="1" />
                <el-option label="月考加速度" :value="2" />
              </el-select>
              <el-date-picker
                v-model="examForm.perfDate"
                size="small"
                style="width: 120px"
                value-format="yyyy-MM"
                type="month"
                :disabled="examForm.examUse !== 2"
              />
            </el-form-item>
            <el-form-item label="计分方式" :label-width="formLabelWidth">
              <el-input
                placeholder="请输入单位分值"
                size="small"
                v-model="examForm.unitScore"
                :disabled="examForm.scoreMethod === 0"
              >
                <el-select
                  v-model="examForm.scoreMethod"
                  size="small"
                  slot="prepend"
                  placeholder="请选择"
                  style="width: 130px"
                >
                  <el-option label="百分制" :value="0" />
                  <el-option label="累分制" :value="1" />
                </el-select>
              </el-input>
            </el-form-item>
            <el-form-item
              label="预约考试"
              :label-width="formLabelWidth"
              prop="bookExam"
            >
              <el-select
                size="small"
                v-model="examForm.bookExam"
                placeholder="是否需要预约才可考试"
              >
                <el-option
                  v-for="item in dictStore.dictList.yes_or_not"
                  :key="item.code"
                  :label="item.codeval"
                  :value="parseInt(item.code)"
                />
              </el-select>
            </el-form-item>
            <el-form-item
              label="单题时长(秒)"
              :label-width="formLabelWidth"
              prop="quesTime"
            >
              <el-input
                size="small"
                type="number"
                v-model="examForm.quesTime"
                autocomplete="off"
              />
            </el-form-item>
            <el-form-item
              label="重新考试"
              :label-width="formLabelWidth"
              prop="ifAgain"
            >
              <el-select
                size="small"
                v-model="examForm.ifAgain"
                placeholder="是否可重复考试"
              >
                <el-option
                  v-for="item in dictStore.dictList.yes_or_not"
                  :key="item.code"
                  :label="item.codeval"
                  :value="parseInt(item.code)"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">高级配置</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item
              label="音频重播(*"
              :label-width="formLabelWidth"
              prop="audioAgain"
            >
              <el-select
                size="small"
                v-model="examForm.audioAgain"
                placeholder="声音文件是否可以重播"
              >
                <el-option
                  v-for="item in dictStore.dictList.yes_or_not"
                  :key="item.code"
                  :label="item.codeval"
                  :value="parseInt(item.code)"
                />
              </el-select>
            </el-form-item>
            <el-form-item
              label="题号随机"
              :label-width="formLabelWidth"
              prop="quesRandom"
            >
              <el-select
                size="small"
                v-model="examForm.quesRandom"
                placeholder="题号是否随机"
              >
                <el-option
                  v-for="item in dictStore.dictList.yes_or_not"
                  :key="item.code"
                  :label="item.codeval"
                  :value="parseInt(item.code)"
                />
              </el-select>
            </el-form-item>
            <el-form-item
              label="题目跳转"
              :label-width="formLabelWidth"
              prop="skipQues"
            >
              <el-select
                size="small"
                v-model="examForm.skipQues"
                placeholder="是否可以选择题号跳转"
              >
                <el-option
                  v-for="item in dictStore.dictList.yes_or_not"
                  :key="item.code"
                  :label="item.codeval"
                  :value="parseInt(item.code)"
                />
              </el-select>
            </el-form-item>
            <el-form-item
              label="选项展示(*"
              :label-width="formLabelWidth"
              prop="optionOrder"
            >
              <el-select
                size="small"
                v-model="examForm.optionOrder"
                placeholder="免登陆菜单状态"
              >
                <el-option label="音视频播放完毕显示" :value="1" />
                <el-option label="同时显示" :value="0" />
              </el-select>
            </el-form-item>
            <el-form-item
              label="复议时间"
              :label-width="formLabelWidth"
              prop="disputeDate"
            >
              <el-date-picker
                type="datetime"
                v-model="examForm.disputeDate"
                size="small"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="如空则不允许复议"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="视频重播(*"
              :label-width="formLabelWidth"
              prop="videoAgain"
            >
              <el-select
                size="small"
                v-model="examForm.videoAgain"
                placeholder="视频文件是否可以重播"
              >
                <el-option
                  v-for="item in dictStore.dictList.yes_or_not"
                  :key="item.code"
                  :label="item.codeval"
                  :value="parseInt(item.code)"
                />
              </el-select>
            </el-form-item>
            <el-form-item
              label="选项随机"
              :label-width="formLabelWidth"
              prop="optionRandom"
            >
              <el-select
                size="small"
                v-model="examForm.optionRandom"
                placeholder="选项乱序"
              >
                <el-option
                  v-for="item in dictStore.dictList.yes_or_not"
                  :key="item.code"
                  :label="item.codeval"
                  :value="parseInt(item.code)"
                />
              </el-select>
            </el-form-item>
            <el-form-item
              label="答错继续"
              :label-width="formLabelWidth"
              prop="wrongBreak"
            >
              <el-select
                size="small"
                v-model="examForm.wrongBreak"
                placeholder="答错是否自动提交试卷"
              >
                <el-option
                  v-for="item in dictStore.dictList.yes_or_not"
                  :key="item.code"
                  :label="item.codeval"
                  :value="parseInt(item.code)"
                />
              </el-select>
            </el-form-item>
            <el-form-item
              label="试卷状态"
              :label-width="formLabelWidth"
              prop="examStat"
            >
              <el-select
                size="small"
                v-model="examForm.examStat"
                placeholder="试卷状态"
              >
                <el-option label="生效" :value="1" />
                <el-option label="失效" :value="0" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button size="small" @click="examFormVisible = false"
            >取 消</el-button
          >
          <el-button
            size="small"
            type="primary"
            @click="examFormSubmit"
            :disabled="examForm.ifRead === 1"
            >确 定</el-button
          >
        </span>
      </template>
    </el-dialog>

    <!-- 组件引用 -->
    <ExamScore ref="examScoreRef" />
    <ExamTimeConfig ref="examTimeConfigRef" />
    <ExamQuestionConfig
      ref="examQuestionConfigRef"
      @refresh="getExamInfoList"
    />
    <DisputeManage ref="disputeManageRef" />
    <BookInfo ref="bookInfoRef" />
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useDictStore } from '@/stores'
import { examConfigApi } from '@/api/edu/examConfig'
import { bookInfoApi } from '@/api/edu/bookInfo'
// 组件导入
import ExamScore from './components/ExamScore.vue'
import ExamQuestionConfig from './components/ExamQuestionConfig.vue'
import ExamTimeConfig from './components/ExamTimeConfig.vue'
import DisputeManage from './components/DisputeManage.vue'
import BookInfo from './components/BookInfo.vue'

// 组件引用
const examScoreRef = ref(null)
const examQuestionConfigRef = ref(null)
const examTimeConfigRef = ref(null)
const disputeManageRef = ref(null)
const bookInfoRef = ref(null)
const examFormRef = ref(null)

// 响应式数据
const examTableList = ref([])
const currentPage = ref(1)
const pageSizes = ref([20, 100, 500])
const total = ref(0)
const examFormVisible = ref(false)
const examFormTitle = ref('')
const formLabelWidth = ref('120px')
const datetimeRange = ref(['', ''])
const areaConfig = ref([])
const dictStore = useDictStore()

// 查询信息
const queryInfo = reactive({
  orderType: ' desc',
  order: ' examCode ',
  querytype: '',
  query: '',
  status: 1,
  pageSize: 20,
  pageNum: 1
})

// 考试表单
const examForm = reactive({
  examName: '',
  begDate: '',
  endDate: '',
  examTime: null,
  quesTime: null,
  ifBreak: 1,
  ifAgain: 0,
  audioAgain: 1,
  videoAgain: 1,
  scoreMethod: 0,
  quesRandom: 0,
  optionRandom: 0,
  optionOrder: 0,
  wrongBreak: 1,
  examUse: 0,
  examStat: 1,
  examCondition: 1,
  score: 100,
  ifRead: 0,
  disputeDate: '',
  areaCode: '',
  skipQues: 0,
  bookExam: 0,
  perfDate: ''
})

// 表单验证规则
const examFormRules = reactive({
  examName: [
    {
      required: true,
      min: 3,
      max: 50,
      message: '长度在 3 到 50 个字符',
      trigger: 'blur'
    }
  ],
  examTime: [
    { required: true, message: '请输入考试时间（分钟）', trigger: 'blur' }
  ]
})

// 生命周期
onMounted(() => {
  getExamInfoList()
  getAreaConfig()
})

// 获取考试列表
const getExamInfoList = async () => {
  try {
    const res = await examConfigApi.getExamList(queryInfo)
    if (res.code !== 200) return ElMessage.error(res.msg)
    examTableList.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    ElMessage.error('获取考试列表失败')
  }
}

// 新增考试
const addNewExamInfoBtn = () => {
  examFormTitle.value = '新建考试'
  datetimeRange.value = ['', '']
  Object.assign(examForm, {
    examName: '',
    begDate: '',
    endDate: '',
    examTime: null,
    quesTime: null,
    ifBreak: 1,
    ifAgain: 0,
    audioAgain: 1,
    videoAgain: 1,
    scoreMethod: 0,
    quesRandom: 0,
    optionRandom: 0,
    optionOrder: 0,
    wrongBreak: 1,
    examUse: 0,
    examStat: 1,
    examCondition: 1,
    score: 100,
    ifRead: 0,
    disputeDate: '',
    areaCode: '',
    skipQues: 0,
    bookExam: 0,
    perfDate: ''
  })
  examFormVisible.value = true
}

// 编辑考试
const examInfoEditBtn = (row) => {
  examFormTitle.value = '预览/修改考试'
  Object.assign(examForm, row)
  datetimeRange.value = [row.begDate, row.endDate]
  examFormVisible.value = true
}

// 提交考试表单
const examFormSubmit = async () => {
  examForm.begDate = datetimeRange.value[0]
  examForm.endDate = datetimeRange.value[1]

  if (examForm.quesTime != null && examForm.skipQues === 1) {
    return ElMessage.error('单题时长和题目不可跳转必须同时设置！')
  }

  if (!examFormRef.value) return

  const valid = await examFormRef.value.validate()
  if (valid) {
    try {
      let res
      if (examFormTitle.value === '新建考试') {
        res = await examConfigApi.addExamConfig(examForm)
      } else if (examFormTitle.value === '预览/修改考试') {
        res = await examConfigApi.updateExamConfig(examForm)
      }

      if (res.code !== 200) return ElMessage.error(res.msg)
      ElMessage.success(res.msg)
      examFormVisible.value = false
      getExamInfoList()
    } catch (error) {
      ElMessage.error('操作失败，请重试')
    }
  }
}

// 获取考场地点配置
const getAreaConfig = async () => {
  try {
    const res = await examConfigApi.getAreaConfigList({ status: 1 })
    if (res.code !== 200) return ElMessage.error(res.msg)
    areaConfig.value = res.data
  } catch (error) {
    ElMessage.error('获取考场地点配置失败')
  }
}

// 阅卷操作
const readTestAction = async (examCode) => {
  try {
    const res = await examConfigApi.readTest(examCode)
    if (res.code !== 200) return ElMessage.error(res.msg)
    ElMessage.success(res.msg)
    getExamInfoList()
  } catch (error) {
    ElMessage.error('阅卷操作失败')
  }
}

// 分页处理
const handleSizeChange = (pageSize) => {
  queryInfo.pageSize = pageSize
  getExamInfoList()
}

const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  getExamInfoList()
}

// 排序处理
const tableSort = (data) => {
  if (data.order === 'ascending') queryInfo.orderType = ' asc '
  else if (data.order === 'descending') queryInfo.orderType = ' desc '
  queryInfo.order = data.prop
  getExamInfoList()
}

// 获取是/否文本
const getYesOrNoText = (code) => {
  const item = yesOrNoOptions.value.find((e) => parseInt(e.code) === code)
  return item ? item.codeval : code
}
</script>
<style lang="less" scoped>
.box-card {
  height: calc(100vh - 190px);
  overflow: auto;
}

.el-link {
  font-size: 12px;
}
</style>
