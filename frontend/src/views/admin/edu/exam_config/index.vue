<template>
  <div style="height: 100%">
    <t-card class="box-card">
      <t-row :gutter="20">
        <t-col :span="4">
          <t-input
            placeholder="模糊搜索"
            size="small"
            v-model="queryInfo.query"
          >
            <t-button slot="append" @click="getExamInfoList()"><template #icon><DynamicIcon name="search" /></template></t-button>
          </t-input>
        </t-col>
        <t-col :span="3">
          <t-select
            v-model="queryInfo.status"
            size="small"
            placeholder="请选择状态"
            @change="getExamInfoList()"
          >
            <t-option label="有效" :value="1" />
            <t-option label="无效" :value="0" />
          </t-select>
        </t-col>
        <t-col :span="4">
          <t-button theme="primary" size="small" @click="addNewExamInfoBtn()"
            >新建考试</t-button
          >
        </t-col>
      </t-row>
      <t-alert
        title="操作说明"
        theme="info"
        message="请正确使用字典配置：1.点击名单可以查看或配置考试名单。2.试题请配置。3.点击复议可以查看该考试的复议情况！4.红色的时钟表示该场考试需要设置考试预约时间"
        :closable="false"
      >
      </t-alert>
      <CustomTable rowKey="id"
        :data="examTableList"
        size="small"
        height="calc(100vh - 400px)"
        stripe
        @sort-change="tableSort"
        style="width: 100%">
        <TableColumn
          prop="examName"
          label="考试名称"
          sortable="custom"
          ellipsis
        />
        <TableColumn
          prop="begDate"
          label="开始时间"
          width="100px"
          ellipsis
          sortable="custom"
        />
        <TableColumn
          prop="endDate"
          label="结束时间"
          width="100px"
          ellipsis
          sortable="custom"
        />
        <TableColumn label="名单" width="60px">
          <template #default="scope">
            <t-link
              size="small"
              theme="primary"
              @click="() => examScoreRef.show(scope.row.examCode)"
              >管理</t-link
            >
          </template>
        </TableColumn>
        <TableColumn label="试题" width="70px">
          <template #default="scope">
            <t-link
              size="small"
              :type="scope.row.quesNum == 0 ? 'warning' : 'success'"
              @click="() => examQuestionConfigRef.show(scope.row.examCode)"
              >{{
                scope.row.quesNum == 0
                  ? '未配置'
                  : '共' + scope.row.quesNum + '题'
              }}</t-link
            >
          </template>
        </TableColumn>
        <TableColumn
          prop="bookExam"
          label="预约"
          width="100px"
          ellipsis
          sortable="custom">
          <template #default="scope">
            <t-link
              v-if="scope.row.bookExam === 1"
              size="small"
              theme="primary"
              @click="() => examTimeConfigRef.show(scope.row.examCode)"
              >配置</t-link
            >
            <span v-else size="small">无需</span>
          </template>
        </TableColumn>

        <TableColumn
          prop="disputeDate"
          label="复议"
          ellipsis
          sortable="custom"
          width="100px">
          <template #default="scope">
            <div v-if="scope.row.disputeDate == null">不可复议</div>
            <t-link
              v-else
              size="small"
              theme="success"
              @click="disputeManageRef.show(scope.row.examCode)"
              >{{ scope.row.disputeDate }}</t-link
            >
          </template>
        </TableColumn>
        <TableColumn label="阅卷" width="70px">
          <template #default="scope">
            <t-popconfirm
              v-if="scope.row.ifRead === 0"
              width="320"
              content="阅卷后试卷不可修改，不可恢复，将结算所有考试人员得分！"
              @confirm="readTestAction(scope.row.examCode)"
            >
              <template #reference>
                <t-link
                  size="small"
                  theme="primary"
                  underline="hover"
                  slot="reference"
                  >阅卷</t-link
                >
              </template>
            </t-popconfirm>
            <span v-else-if="scope.row.ifRead === 1">{{
              dictStore.getDictLabel('yes_or_not', scope.row.ifRead)
            }}</span>
            <span v-else size="small">无需</span>
          </template>
        </TableColumn>
        <TableColumn label="操作" fixed="right" width="160px">
          <template #default="scope">
            <t-button
              theme="primary" size="small"
              @click="examInfoEditBtn(scope.row)"
              circle
            ><template #icon><DynamicIcon name="edit" /></template></t-button>
            <t-button
              v-if="scope.row.bookExam === 1" size="small"
              @click="() => bookInfoRef.show(scope.row.examCode)"
              circle
            ><template #icon><DynamicIcon name="timer" /></template></t-button>
            <t-popup placement="right" width="400" trigger="click">
              <template #reference>
                <t-button
                  style="margin-left: 10px" size="small"
                  circle
                ><template #icon><DynamicIcon name="document" /></template></t-button>
              </template>
              <div style="text-align: right; margin: 0">
                <t-button
                  theme="primary"
                  size="small"
                  @click="
                    examConfigApi.downloadExamScoreReport(scope.row.examCode)
                  "
                  >成绩报表（个人）</t-button
                >
                <t-button theme="primary" size="small"
                  >成绩报表（小组）</t-button
                >
                <t-button
                  theme="primary"
                  size="small"
                  @click="
                    examConfigApi.downloadExamTestReport(scope.row.examCode)
                  "
                  >试卷明细</t-button
                >
              </div>
            </t-popup>
          </template>
        </TableColumn>
      </CustomTable>
      <t-pagination
        @page-size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current="currentPage"
        :page-size-options="pageSizes"
        :page-size="queryInfo.pageSize"

        :total="total"
      >
      </t-pagination>
    </t-card>

    <!-- 弹出窗口 -->
    <t-dialog
      :header="examFormTitle"
      :close-on-overlay-click="false"
      width="80%"
      mode="full-screen"
      v-model:visible="examFormVisible"
    >
      <t-form
        :data="examForm"
        ref="examFormRef"
        size="small"
        :rules="examFormRules"
      >
        <t-divider content-position="left">基础配置</t-divider>
        <t-row>
          <t-col :span="6">
            <t-form-item
              label="考试名称"
              :label-width="formLabelWidth"
              prop="examName"
            >
              <t-input
                size="small"
                v-model="examForm.examName"
                autocomplete="off"
              />
            </t-form-item>
            <t-form-item
              label="考试周期"
              :label-width="formLabelWidth"
              prop="cycleDate"
            >
              <t-date-range-picker v-model="datetimeRange" size="small" style="width: 100%" enable-time-picker format="YYYY-MM-DD HH:mm:ss" :placeholder="['开始日期', '结束日期']" />
            </t-form-item>
            <t-form-item
              label="考场区域"
              :label-width="formLabelWidth"
              prop="areaCode"
            >
              <t-select
                size="small"
                v-model="examForm.areaCode"
                placeholder="是否指定考场"
              >
                <t-option label="不限制考场" value="" />
                <t-option
                  v-for="item in areaConfig"
                  :key="item.areaCode"
                  :label="item.areaName"
                  :value="item.areaCode"
                />
              </t-select>
            </t-form-item>
            <t-form-item
              label="考试时长(分)"
              :label-width="formLabelWidth"
              prop="examTime"
            >
              <t-input
                size="small"
                v-model="examForm.examTime"
                type="number"
                autocomplete="off"
              />
            </t-form-item>
            <t-form-item
              label="考试中断"
              :label-width="formLabelWidth"
              prop="ifBreak"
            >
              <t-select
                size="small"
                v-model="examForm.ifBreak"
                placeholder="考试是否可中途退出"
              >
                <t-option
                  v-for="item in dictStore.dictList.yes_or_not"
                  :key="item.code"
                  :label="item.codeval"
                  :value="parseInt(item.code)"
                />
              </t-select>
            </t-form-item>
          </t-col>
          <t-col :span="6">
            <t-form-item
              label="试卷用途"
              :label-width="formLabelWidth"
              prop="examUse"
            >
              <t-select
                v-model="examForm.examUse"
                size="small"
                style="width: 120px"
                placeholder="请选择"
              >
                <t-option label="常规试卷" :value="0" />
                <t-option label="互动答题" :value="1" />
                <t-option label="月考加速度" :value="2" />
              </t-select>
              <t-date-picker
                v-model="examForm.perfDate"
                size="small"
                style="width: 120px"
               
                mode="month"
                :disabled="examForm.examUse !== 2"
              />
            </t-form-item>
            <t-form-item label="计分方式" :label-width="formLabelWidth">
              <t-input
                placeholder="请输入单位分值"
                size="small"
                v-model="examForm.unitScore"
                :disabled="examForm.scoreMethod === 0"
              >
                <t-select
                  v-model="examForm.scoreMethod"
                  size="small"
                  slot="prepend"
                  placeholder="请选择"
                  style="width: 130px"
                >
                  <t-option label="百分制" :value="0" />
                  <t-option label="累分制" :value="1" />
                </t-select>
              </t-input>
            </t-form-item>
            <t-form-item
              label="预约考试"
              :label-width="formLabelWidth"
              prop="bookExam"
            >
              <t-select
                size="small"
                v-model="examForm.bookExam"
                placeholder="是否需要预约才可考试"
              >
                <t-option
                  v-for="item in dictStore.dictList.yes_or_not"
                  :key="item.code"
                  :label="item.codeval"
                  :value="parseInt(item.code)"
                />
              </t-select>
            </t-form-item>
            <t-form-item
              label="单题时长(秒)"
              :label-width="formLabelWidth"
              prop="quesTime"
            >
              <t-input
                size="small"
                type="number"
                v-model="examForm.quesTime"
                autocomplete="off"
              />
            </t-form-item>
            <t-form-item
              label="重新考试"
              :label-width="formLabelWidth"
              prop="ifAgain"
            >
              <t-select
                size="small"
                v-model="examForm.ifAgain"
                placeholder="是否可重复考试"
              >
                <t-option
                  v-for="item in dictStore.dictList.yes_or_not"
                  :key="item.code"
                  :label="item.codeval"
                  :value="parseInt(item.code)"
                />
              </t-select>
            </t-form-item>
          </t-col>
        </t-row>
        <t-divider content-position="left">高级配置</t-divider>
        <t-row>
          <t-col :span="6">
            <t-form-item
              label="音频重播(*"
              :label-width="formLabelWidth"
              prop="audioAgain"
            >
              <t-select
                size="small"
                v-model="examForm.audioAgain"
                placeholder="声音文件是否可以重播"
              >
                <t-option
                  v-for="item in dictStore.dictList.yes_or_not"
                  :key="item.code"
                  :label="item.codeval"
                  :value="parseInt(item.code)"
                />
              </t-select>
            </t-form-item>
            <t-form-item
              label="题号随机"
              :label-width="formLabelWidth"
              prop="quesRandom"
            >
              <t-select
                size="small"
                v-model="examForm.quesRandom"
                placeholder="题号是否随机"
              >
                <t-option
                  v-for="item in dictStore.dictList.yes_or_not"
                  :key="item.code"
                  :label="item.codeval"
                  :value="parseInt(item.code)"
                />
              </t-select>
            </t-form-item>
            <t-form-item
              label="题目跳转"
              :label-width="formLabelWidth"
              prop="skipQues"
            >
              <t-select
                size="small"
                v-model="examForm.skipQues"
                placeholder="是否可以选择题号跳转"
              >
                <t-option
                  v-for="item in dictStore.dictList.yes_or_not"
                  :key="item.code"
                  :label="item.codeval"
                  :value="parseInt(item.code)"
                />
              </t-select>
            </t-form-item>
            <t-form-item
              label="选项展示(*"
              :label-width="formLabelWidth"
              prop="optionOrder"
            >
              <t-select
                size="small"
                v-model="examForm.optionOrder"
                placeholder="免登陆菜单状态"
              >
                <t-option label="音视频播放完毕显示" :value="1" />
                <t-option label="同时显示" :value="0" />
              </t-select>
            </t-form-item>
            <t-form-item
              label="复议时间"
              :label-width="formLabelWidth"
              prop="disputeDate"
            >
              <t-date-picker
                mode="date"
                enable-time-picker
                v-model="examForm.disputeDate"
                size="small"
                placeholder="如空则不允许复议"
              />
            </t-form-item>
          </t-col>
          <t-col :span="6">
            <t-form-item
              label="视频重播(*"
              :label-width="formLabelWidth"
              prop="videoAgain"
            >
              <t-select
                size="small"
                v-model="examForm.videoAgain"
                placeholder="视频文件是否可以重播"
              >
                <t-option
                  v-for="item in dictStore.dictList.yes_or_not"
                  :key="item.code"
                  :label="item.codeval"
                  :value="parseInt(item.code)"
                />
              </t-select>
            </t-form-item>
            <t-form-item
              label="选项随机"
              :label-width="formLabelWidth"
              prop="optionRandom"
            >
              <t-select
                size="small"
                v-model="examForm.optionRandom"
                placeholder="选项乱序"
              >
                <t-option
                  v-for="item in dictStore.dictList.yes_or_not"
                  :key="item.code"
                  :label="item.codeval"
                  :value="parseInt(item.code)"
                />
              </t-select>
            </t-form-item>
            <t-form-item
              label="答错继续"
              :label-width="formLabelWidth"
              prop="wrongBreak"
            >
              <t-select
                size="small"
                v-model="examForm.wrongBreak"
                placeholder="答错是否自动提交试卷"
              >
                <t-option
                  v-for="item in dictStore.dictList.yes_or_not"
                  :key="item.code"
                  :label="item.codeval"
                  :value="parseInt(item.code)"
                />
              </t-select>
            </t-form-item>
            <t-form-item
              label="试卷状态"
              :label-width="formLabelWidth"
              prop="examStat"
            >
              <t-select
                size="small"
                v-model="examForm.examStat"
                placeholder="试卷状态"
              >
                <t-option label="生效" :value="1" />
                <t-option label="失效" :value="0" />
              </t-select>
            </t-form-item>
          </t-col>
        </t-row>
      </t-form>
      <template #footer>
        <span class="dialog-footer">
          <t-button size="small" @click="examFormVisible = false"
            >取 消</t-button
          >
          <t-button
            size="small"
            theme="primary"
            @click="examFormSubmit"
            :disabled="examForm.ifRead === 1"
            >确 定</t-button
          >
        </span>
      </template>
    </t-dialog>

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
import { MessagePlugin } from 'tdesign-vue-next'
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
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    examTableList.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    MessagePlugin.error('获取考试列表失败')
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
    return MessagePlugin.error('单题时长和题目不可跳转必须同时设置！')
  }

  if (!examFormRef.value) return

  const valid = await examFormRef.value.validate()
  if (valid === true) {
    try {
      let res
      if (examFormTitle.value === '新建考试') {
        res = await examConfigApi.addExamConfig(examForm)
      } else if (examFormTitle.value === '预览/修改考试') {
        res = await examConfigApi.updateExamConfig(examForm)
      }

      if (res.code !== 200) return MessagePlugin.error(res.msg)
      MessagePlugin.success(res.msg)
      examFormVisible.value = false
      getExamInfoList()
    } catch (error) {
      MessagePlugin.error('操作失败，请重试')
    }
  }
}

// 获取考场地点配置
const getAreaConfig = async () => {
  try {
    const res = await examConfigApi.getAreaConfigList({ status: 1 })
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    areaConfig.value = res.data
  } catch (error) {
    MessagePlugin.error('获取考场地点配置失败')
  }
}

// 阅卷操作
const readTestAction = async (examCode) => {
  try {
    const res = await examConfigApi.readTest(examCode)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    getExamInfoList()
  } catch (error) {
    MessagePlugin.error('阅卷操作失败')
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
  if (!data.descending) queryInfo.orderType = ' asc '
  else if (data.descending) queryInfo.orderType = ' desc '
  queryInfo.order = data.sortBy
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

.t-link {
  font-size: 12px;
}
</style>
