<template>
  <div style="height: 100%">
    <t-card class="management-card">
      <PageTips
        title="操作说明"
        theme="info"
        :closable="false"
        message="请正确使用字典配置：1.点击名单可以查看或配置考试名单。2.试题请配置。3.点击复议可以查看该考试的复议情况！4.红色的时钟表示该场考试需要设置考试预约时间"
      >
      </PageTips>
      <div class="filter-bar">
        <t-form
          :data="queryInfo"
          label-width="80px"
          colon
          layout="inline"
          class="filter-form"
        >
          <t-form-item label="关键字" name="query">
            <t-input-adornment style="width: 260px">
              <template #append>
                <t-button variant="outline" theme="primary" @click="getCourseTable()">搜索</t-button>
              </template>
              <t-input placeholder="模糊搜索" size="small" v-model="queryInfo.query"></t-input>
            </t-input-adornment>
          </t-form-item>
          <t-form-item label="状态" name="status">
            <t-select
              v-model="queryInfo.status"
              size="small"
              placeholder="全部"
              style="width: 150px"
              @change="getCourseTable()"
            >
              <t-option label="全部" value=""></t-option>
              <t-option label="已发布" :value="1"></t-option>
              <t-option label="未发布" :value="0"></t-option>
            </t-select>
          </t-form-item>
        </t-form>
        <div class="operation-container">
          <t-button variant="outline" theme="primary" size="small" @click="courseManager(null)">新建课程</t-button>
          <t-button
            variant="outline"
            theme="default"
            size="small"
            @click="
              downloadExcel(
                'college/report/course',
                null,
                '课程详情明细.xlsx'
              )
            "
            >下载课程列表</t-button
          >
        </div>
      </div>
      <CustomTable rowKey="id"
        :data="courses"
        size="small"
        height="calc(100vh - 400px)"
        stripe
        @sort-change="tableSort"
        style="width: 100%">
        <TableColumn
          prop="courseName"
          label="课程名称"
          sortable="custom"
          ellipsis></TableColumn>
        <TableColumn
          prop="endDate"
          label="有效时间"
          sortable="custom"
          ellipsis>
          <template #default="scope">
            {{ scope.row.begDate }} ~ {{ scope.row.endDate }}</template
          >
        </TableColumn>
        <TableColumn
          prop="teachMethod"
          label="类型"
          sortable="custom"
          width="70px"
          ellipsis>
          <template #default="scope">{{
            scope.row.teachMethod == 1 ? '常规' : '任务'
          }}</template>
        </TableColumn>
        <TableColumn
          prop="studyType"
          label="形式"
          sortable="custom"
          width="70px"
          ellipsis>
          <template #default="scope">{{
            scope.row.studyType == 1 ? '线上' : '线下'
          }}</template>
        </TableColumn>
        <TableColumn
          prop="teachObject"
          label="授课对象"
          sortable="custom"
          width="100px"
          ellipsis></TableColumn>
        <TableColumn
          prop="coverImg"
          label="封面"
          sortable="custom"
          width="70px"
          ellipsis>
          <template #default="scope"
            ><t-link
              @click="coverRef.show(scope.row.coverImg, scope.row.courseId)"
              :type="scope.row.coverImg == null ? 'danger' : 'primary'"
            >
              {{ scope.row.coverImg == null ? '否' : '已配置' }}
            </t-link></template
          >
        </TableColumn>
        <TableColumn
          prop="recommendFlag"
          label="滚屏/分类"
          sortable="custom"
          width="140px"
          ellipsis>
          <template #default="scope"
            ><t-button @click="showRecommend(scope.row)" size="small" link
              >{{ scope.row.topFlag < 1 ? '否' : '是' }}[{{
                scope.row.topFlag
              }}]/ {{ scope.row.recommend ? scope.row.recommend : '否' }}[{{
                scope.row.recommendFlag
              }}]
            </t-button></template
          >
        </TableColumn>
        <TableColumn
          colKey="courseFile"
          label="附件"
          sortable="custom"
          width="70px">
          <template #default="scope">
            <t-link
              @click="
                courseFileRef.show(scope.row.courseFile, scope.row.courseId)
              "
              :type="scope.row.courseFile.length > 0 ? 'primary' : 'danger'"
            >
              {{ scope.row.courseFile.length > 0 ? '已配置' : '否' }}
            </t-link></template
          >
        </TableColumn>
        <TableColumn
          prop="ifExam"
          label="考试"
          sortable="custom"
          width="70px">
          <template #default="scope">
            <t-link
              @click="examConfigRef.show(scope.row.courseId)"
              :type="scope.row.ifExam ? 'primary' : 'danger'"
            >
              {{
                scope.row.ifExam
                  ? scope.row.examId == null
                    ? '未配置'
                    : '已配置'
                  : '否'
              }}
            </t-link></template
          >
        </TableColumn>
        <TableColumn
          prop="ifEval"
          label="评价"
          sortable="custom"
          width="70px">
          <template #default="scope">
            <t-link
              @click="satisfyRef.show(scope.row)"
              :type="scope.row.ifEval ? 'primary' : 'danger'"
            >
              {{
                scope.row.ifEval
                  ? scope.row.tempId == null
                    ? '未配置'
                    : '已配置'
                  : '否'
              }}
            </t-link></template
          >
        </TableColumn>
        <TableColumn
          prop="status"
          label="状态"
          sortable="custom"
          width="75px">
          <template #default="scope"
            ><t-tag
              @click="updateStatus(scope.row)"
              :type="scope.row.status ? 'success' : 'danger'"
              size="small"
             variant="light">
              {{ scope.row.status ? '已发布' : '未发布' }}
            </t-tag></template
          >
        </TableColumn>
        <TableColumn
          prop="handleDate"
          label="操作时间"
          sortable="custom"
          ellipsis></TableColumn>
        <TableColumn label="操作" fixed="right" width="150px">
          <template #default="scope">
            <t-button variant="outline"
              theme="default" size="small"
              @click="courseManager(scope.row)"
             >编辑</t-button>
            <t-button variant="outline"
              v-if="scope.row.bookExam === 1"
              theme="danger" size="small"
              @click="() => bookInfoRef.show(scope.row.examCode)"
             ><template #icon><DynamicIcon name="time" /></template>预约</t-button>
            <t-dropdown trigger="click">
              <t-button variant="outline" theme="default" size="small">
                <template #icon><DynamicIcon name="file" /></template>报表下载
              </t-button>
              <template #dropdown>
                <t-dropdown-menu>
                  <t-dropdown-item value="hum" @click="() => downloadReport('hum', scope.row)">学习报表</t-dropdown-item>
                  <t-dropdown-item value="exam" @click="() => downloadReport('exam', scope.row)">考试报表</t-dropdown-item>
                  <t-dropdown-item value="eval" @click="() => downloadReport('eval', scope.row)">满意度报表</t-dropdown-item>
                </t-dropdown-menu>
              </template>
            </t-dropdown>
            <t-button variant="outline" theme="default" size="small" @click="() => taskListRef.show(scope.row.courseId)">任务</t-button>
          </template>
        </TableColumn>
      </CustomTable>
      <t-pagination
        @page-size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current="queryInfo.pageNum"
        :page-size-options="[20, 40, 100, 200]"
        :page-size="queryInfo.pageSize"

        :total="total"
      ></t-pagination>
    </t-card>

    <!-- 弹出窗口 -->
    <t-dialog
      :header="courseFormTitle"
      :close-on-overlay-click="false"
      width="80%"
      @close="onClose"
      v-model:visible="courseFormVisible"
    >
      <t-form
        ref="courseForm"
        :data="courseFormData"
        :rules="rules"
        size="small"
        label-width="100px"
      >
        <t-row :gutter="15">
          <t-col :span="12">
            <t-form-item label="课程名称" name="courseName">
              <t-input
                v-model="courseFormData.courseName"
                placeholder="请输入名称课程课程名称"
                :maxlength="50"
                show-limit-number
                clearable
                :style="{ width: '100%' }"
              ></t-input>
            </t-form-item>
          </t-col>
          <t-col :span="6">
            <t-form-item label="讲师" name="lecturer">
              <t-select
                v-model="courseFormData.lecturer"
                placeholder="请选择讲师"
                filterable
                clearable
                style="height: 20px; width: 100%"
              >
                <t-option
                  v-for="(item, index) in teachers"
                  :key="index"
                  :label="
                    item.ploName +
                    '/' +
                    item.ploNum +
                    (item.status === 0 ? '[无效]' : '1')
                  "
                  :value="item.ploNum"
                ></t-option>
              </t-select>
            </t-form-item>
          </t-col>
          <t-col :span="6">
            <t-form-item label="日期范围" name="dateRange">
              <t-date-range-picker v-model="courseFormData.dateRange" format="YYYY-MM-DD" style="width: 100%" :placeholder="['开始日期', '结束日期']" clearable ></t-date-range-picker>
            </t-form-item>
          </t-col>
          <t-col :span="6">
            <t-form-item label="课程积分" name="coin">
              <t-input-number
                v-model="courseFormData.coin"
                placeholder="课程积分"
                :step="0.5"
              ></t-input-number>
            </t-form-item>
          </t-col>
          <t-col :span="6">
            <t-form-item label="课程时数" name="hours">
              <t-input-number
                v-model="courseFormData.hours"
                placeholder="课程时数"
                :step="0.5"
              ></t-input-number>
            </t-form-item>
          </t-col>
          <t-col :span="6">
            <t-form-item label="所属分类" name="courseType">
              <t-cascader
                v-model="courseFormData.courseType"
                :options="courseTypeTree"
                @change="
                  courseFormData.courseType = courseFormData.courseType.pop()
                "
                :props="{ checkStrictly: true, value: 'id', label: 'name' }"
                :style="{ width: '100%' }"
              ></t-cascader>
            </t-form-item>
          </t-col>
          <t-col :span="6">
            <t-form-item label="授课对象" name="teachObject">
              <t-select
                v-model="courseFormData.teachObject"
                placeholder="请选择选项随机"
                :style="{ width: '100%' }"
              >
                <t-option label="全体员工" value="全体员工"></t-option>
                <t-option
                  v-for="(item, index) in teachObject"
                  :key="index"
                  :label="item.groupName + '[' + item.ploNum + '人]'"
                  :value="item.groupName"
                ></t-option>
              </t-select>
            </t-form-item>
          </t-col>
          <t-col :span="6">
            <t-form-item label="授课方式" name="teachMethod">
              <t-radio-group v-model="courseFormData.teachMethod" size="small">
                <t-radio-button
                  v-for="(item, index) in dictStore.getDictByNames(
                    'trm_course_teach_method',
                    1
                  )"
                  :key="index"
                  :value="Number(item.code)"
                  :label="item.codeval"
                ></t-radio-button>
              </t-radio-group>
            </t-form-item>
          </t-col>
          <t-col :span="6">
            <t-form-item label="学习方式" name="studyType">
              <t-radio-group v-model="courseFormData.studyType" size="small">
                <t-radio-button
                  v-for="(item, index) in dictStore.getDictByNames(
                    'trm_course_study_type',
                    1
                  )"
                  :key="index"
                  :value="Number(item.code)"
                  :label="item.codeval"
                ></t-radio-button>
              </t-radio-group>
            </t-form-item>
          </t-col>
          <t-col :span="12">
            <t-form-item label="课程描述" name="courseDes">
              <t-textarea v-model="courseFormData.courseDes"
                placeholder="请输入课程描述"
                :maxlength="2000"
                show-limit-number
                :autosize="{ minRows: 4, maxRows: 8 }"
                :style="{ width: '100%' }" />
            </t-form-item>
          </t-col>
        </t-row>
      </t-form>

      <template #footer>
        <t-space>
          <t-button size="small" variant="outline" @click="courseFormVisible = false">取消</t-button>
          <t-button size="small" variant="outline" theme="primary" @click="submitForm()">确定</t-button>
        </t-space>
      </template>
    </t-dialog>

    <t-dialog
      header="课程推荐"
      :close-on-overlay-click="false"
      width="60%"
      @close="onClose"
      v-model:visible="recommendFormVisible"
    >
      <PageTips
        title="操作说明"
        theme="info"
        :closable="false"
        message="滚动广告输入0则为不展示。输入数字大于1则有大到小滚动。推荐分类，输入相同分类名称则自动归类在首页推荐栏位展示"
      ></PageTips>
      <t-form
        ref="recommendFormRef"
        layout="inline"
        :data="recommendForm"
        :rules="rules"
        size="small"
        class="form-inline"
      >
        <t-form-item label="滚动广告" name="topFlag">
          <t-input
            v-model="recommendForm.topFlag"
            type="number"
            placeholder="输入数字从大至小"
            :maxlength="2"
          ></t-input>
        </t-form-item>
        <t-form-item label="推荐分类" name="recommend">
          <t-input
            v-model="recommendForm.recommend"
            placeholder="请输入推荐类别"
            :maxlength="20"
            show-limit-number
            clearable
            :style="{ width: '100%' }"
          ></t-input>
        </t-form-item>
        <t-form-item label="分类顺序" name="recommendFlag">
          <t-input
            v-model="recommendForm.recommendFlag"
            type="number"
            placeholder="输入数字从大至小"
            :maxlength="2"
          ></t-input>
        </t-form-item>
      </t-form>
      <template #footer>
        <t-space>
          <t-button size="small" variant="outline" @click="recommendFormVisible = false">取消</t-button>
          <t-button size="small" variant="outline" theme="primary" @click="submitRecommendForm()">确定</t-button>
        </t-space>
      </template>
    </t-dialog>
    <CourseFile ref="courseFileRef" @refresh="getCourseTable"></CourseFile>
    <Satisfy ref="satisfyRef" @refresh="getCourseTable"></Satisfy>
    <Cover ref="coverRef" @refresh="getCourseTable"></Cover>
    <ExamConfig ref="examConfigRef" @refresh="getCourseTable"></ExamConfig>
    <TaskList ref="taskListRef"></TaskList>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import CourseFile from './components/CourseFile.vue'
import Satisfy from './components/Satisfy.vue'
import Cover from './components/Cover.vue'
import ExamConfig from './components/ExamConfig.vue'
import TaskList from './components/TaskList.vue'
import { courseApi } from '@/api/college/course'
import { courseTypeApi } from '@/api/college/courseType'
import { teacherApi } from '@/api/college/teacher'
import { teachGroupApi } from '@/api/college/teachGroup'
import { useDictStore } from '@/stores'
import { downloadExcel } from '@/utils/request'

const dictStore = useDictStore()
const courseFileRef = ref(null)
const satisfyRef = ref(null)
const coverRef = ref(null)
const examConfigRef = ref(null)
const taskListRef = ref(null)
const bookInfoRef = ref(null)
const courseForm = ref(null)
const recommendFormRef = ref(null)

const queryInfo = reactive({
  orderType: ' desc',
  order: 'handleDate',
  queryType: ' courseName ',
  status: '',
  query: '',
  pageSize: 20,
  pageNum: 1
})

const total = ref(0)
const courses = ref([])
const teachObject = ref([])
// 表单参数
const courseFormTitle = ref('')
const courseFormVisible = ref(false)
const courseTypeTree = ref({})
const recommendFormVisible = ref(false)

const courseFormData = reactive({
  courseId: '',
  courseName: undefined,
  lecturer: undefined,
  dateRange: [''],
  begDate: '',
  endDate: '',
  coin: 0,
  hours: 0,
  teachObject: undefined,
  teachMethod: undefined,
  courseType: '',
  studyType: undefined,
  courseDes: null
})

const rules = reactive({
  courseName: [
    { required: true, message: '请输入名称课程课程名称', trigger: 'blur' }
  ],
  lecturer: [{ required: true, message: '请选择讲师', trigger: 'change' }],
  dateRange: [
    {
      required: true,
      type: 'array',
      message: '请至少选择一个日期范围',
      trigger: 'change'
    }
  ],
  coin: [{ required: true, message: '课程积分', trigger: 'blur' }],
  hour: [{ required: true, message: '课程时数', trigger: 'blur' }],
  teachObject: [
    { required: true, message: '请选择授课方式', trigger: 'change' }
  ],
  courseType: [
    { required: true, message: '请选择课程类型', trigger: 'change' }
  ],
  studyType: [{ required: true, message: '请选择学习方式', trigger: 'change' }]
})

const teachers = ref([])
// 以下为课程推荐
const recommendForm = reactive({
  courseId: '',
  topFlag: '',
  recommend: '',
  recommendFlag: 0
})

const getCourseTable = async () => {
  try {
    const res = await courseApi.getAdminCourseList(queryInfo)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    courses.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    MessagePlugin.error('获取课程列表失败')
  }
}

const handleSizeChange = (pageSize) => {
  queryInfo.pageSize = pageSize
  getCourseTable()
}

const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  getCourseTable()
}

// 报表下载：点击“报表下载”弹出 学习/考试/满意度 报表
const downloadReport = (type, row) => {
  const configs = {
    hum: {
      url: 'college/report/hum/' + row.courseId,
      name: '课程学习记录明细BY人员.xlsx'
    },
    exam: {
      url: 'college/report/exam/' + row.courseId,
      name: '课程考试记录BY人员.xlsx'
    },
    eval: {
      url: 'college/report/eval/' + row.courseId,
      name: '满意度评价明细By课程.xlsx'
    }
  }
  const cfg = configs[type]
  if (!cfg) return
  downloadExcel(cfg.url, null, cfg.name)
}

const tableSort = (data) => {
  if (!data.descending) queryInfo.orderType = ' asc '
  else if (data.descending) queryInfo.orderType = ' desc '
  queryInfo.order = data.sortBy
  getCourseTable()
}

const onClose = () => {
  if (courseForm.value) {
    courseForm.value.resetFields()
  }
  if (recommendFormRef.value) {
    recommendFormRef.value.resetFields()
  }
}

// 获取讲师名单
const courseManager = (course) => {
  if (course !== null) {
    courseFormTitle.value = '课程更新'
    courseFormData.courseId = course.courseId
    courseFormData.courseName = course.courseName
    courseFormData.lecturer = course.lecturer
    courseFormData.dateRange = [course.begDate, course.endDate]
    courseFormData.coin = course.coin
    courseFormData.hours = course.hours
    courseFormData.teachObject = course.teachObject
    courseFormData.teachMethod = course.teachMethod
    courseFormData.courseType = course.courseType
    courseFormData.studyType = course.studyType
    courseFormData.courseDes = course.courseDes
  } else {
    courseFormTitle.value = '课程新增'
    courseFormData.courseId = ''
    courseFormData.courseName = undefined
    courseFormData.lecturer = undefined
    courseFormData.dateRange = [
      new Date().toISOString().split('T')[0],
      '2099-12-31'
    ]
    courseFormData.coin = 0
    courseFormData.hours = 0
    courseFormData.teachObject = undefined
    courseFormData.teachMethod = undefined
    courseFormData.courseType = ''
    courseFormData.studyType = undefined
    courseFormData.courseDes = null
  }
  courseFormVisible.value = true
}

const submitForm = async () => {
  if (!courseForm.value) return

  courseFormData.begDate = courseFormData.dateRange[0]
  courseFormData.endDate = courseFormData.dateRange[1]
  // courseFormData.courseType = courseFormData.courseType.pop()

  const valid = await courseForm.value.validate()
  if (valid === true) {
    try {
      let res
      if (courseFormTitle.value === '课程新增') {
        res = await courseApi.saveCourse(courseFormData)
      } else {
        res = await courseApi.updateCourse(courseFormData)
      }
      if (res.code !== 200) return MessagePlugin.error(res.msg)
      MessagePlugin.success(res.msg)
      getCourseTable()
      courseFormVisible.value = false
    } catch (error) {
      MessagePlugin.error('保存课程失败')
    }
  } else {
    MessagePlugin.error('表单校验失败！请检查表单！')
  }
}

// 以下为推荐首页的方法
const showRecommend = (row) => {
  recommendForm.courseId = row.courseId
  recommendForm.topFlag = row.topFlag
  recommendForm.recommend = row.recommend
  recommendForm.recommendFlag = row.recommendFlag
  recommendFormVisible.value = true
}

const submitRecommendForm = async () => {
  if (recommendForm.topFlag == null) recommendForm.topFlag = 0
  if (recommendForm.recommend == null) recommendForm.recommend = ' '
  try {
    const res = await courseApi.updateCourse(recommendForm)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    getCourseTable()
    recommendFormVisible.value = false
  } catch (error) {
    MessagePlugin.error('保存推荐设置失败')
  }
}

const updateStatus = async (row) => {
  try {
    const res = await courseApi.updateCourseStatus(
      row.courseId,
      row.status ? 0 : 1
    )
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    getCourseTable()
  } catch (error) {
    MessagePlugin.error('更新课程状态失败')
  }
}

const loadInitialData = async () => {
  try {
    const [courseTypeRes, teachersRes, teachGroupRes] = await Promise.all([
      courseTypeApi.getCourseTypeTree(),
      teacherApi.getAllTeachers(),
      teachGroupApi.getTeachGroupList()
    ])

    if (courseTypeRes.code === 200) {
      courseTypeTree.value = courseTypeRes.data
    } else {
      MessagePlugin.error(courseTypeRes.msg)
    }

    if (teachersRes.code === 200) {
      teachers.value = teachersRes.data
    } else {
      MessagePlugin.error(teachersRes.msg)
    }

    if (teachGroupRes.code === 200) {
      teachObject.value = teachGroupRes.data
    } else {
      MessagePlugin.error(teachGroupRes.msg)
    }

    await getCourseTable()
  } catch (error) {
    MessagePlugin.error('加载初始数据失败')
  }
}

onMounted(() => {
  loadInitialData()
})
</script>

<style lang="less" scoped>
.filter-bar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
  margin-bottom: 16px;
}

.filter-bar .filter-form {
  flex: 1;
  display: flex;
  align-items: center;
  gap: 16px;
  min-width: 0;
  margin-bottom: 0;
}

.filter-bar .operation-container {
  display: flex;
  align-items: center;
  gap: 8px;
  flex-shrink: 0;
}
</style>
