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
            <el-button
              slot="append"
              icon="Search"
              @click="getCourseTable()"
            ></el-button>
          </el-input>
        </el-col>
        <el-col :span="6">
          <el-select
            v-model="queryInfo.status"
            size="small"
            placeholder="请选择发布状态"
            @change="getCourseTable()"
          >
            <el-option label="全部" value=""></el-option>
            <el-option label="已发布" :value="1"></el-option>
            <el-option label="未发布" :value="0"></el-option>
          </el-select>
        </el-col>
        <el-col :span="8">
          <el-button type="primary" size="small" @click="courseManager(null)"
            >新建课程</el-button
          >
          <el-button
            type="primary"
            size="small"
            @click="
              $global.downloadExcel(
                'college/report/course',
                null,
                '课程详情明细.xlsx'
              )
            "
            >下载课程列表</el-button
          >
        </el-col>
      </el-row>
      <el-alert
        title="操作说明"
        type="info"
        :closable="false"
        description="请正确使用字典配置：1.点击名单可以查看或配置考试名单。2.试题请配置。3.点击复议可以查看该考试的复议情况！4.红色的时钟表示该场考试需要设置考试预约时间"
      >
      </el-alert>
      <el-table
        :data="courses"
        size="small"
        height="calc(100vh - 400px)"
        stripe
        @sort-change="tableSort"
        style="width: 100%"
      >
        <el-table-column
          prop="courseName"
          label="课程名称"
          sortable="custom"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          prop="endDate"
          label="有效时间"
          sortable="custom"
          show-overflow-tooltip
        >
          <template #default="scope">
            {{ scope.row.begDate }} ~ {{ scope.row.endDate }}</template
          >
        </el-table-column>
        <el-table-column
          prop="teachMethod"
          label="类型"
          sortable="custom"
          width="70px"
          show-overflow-tooltip
        >
          <template #default="scope">{{
            scope.row.teachMethod == 1 ? '常规' : '任务'
          }}</template>
        </el-table-column>
        <el-table-column
          prop="studyType"
          label="形式"
          sortable="custom"
          width="70px"
          show-overflow-tooltip
        >
          <template #default="scope">{{
            scope.row.studyType == 1 ? '线上' : '线下'
          }}</template>
        </el-table-column>
        <el-table-column
          prop="teachObject"
          label="授课对象"
          sortable="custom"
          width="100px"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          prop="coverImg"
          label="封面"
          sortable="custom"
          width="70px"
          show-overflow-tooltip
        >
          <template #default="scope"
            ><el-link
              @click="coverRef.show(scope.row.coverImg, scope.row.courseId)"
              :type="scope.row.coverImg == null ? 'danger' : 'primary'"
            >
              {{ scope.row.coverImg == null ? '否' : '已配置' }}
            </el-link></template
          >
        </el-table-column>
        <el-table-column
          prop="recommendFlag"
          label="滚屏/分类"
          sortable="custom"
          width="140px"
          show-overflow-tooltip
        >
          <template #default="scope"
            ><el-button @click="showRecommend(scope.row)" size="small" link
              >{{ scope.row.topFlag < 1 ? '否' : '是' }}[{{
                scope.row.topFlag
              }}]/ {{ scope.row.recommend ? scope.row.recommend : '否' }}[{{
                scope.row.recommendFlag
              }}]
            </el-button></template
          >
        </el-table-column>
        <el-table-column
          prop="ifExam"
          label="附件"
          sortable="custom"
          width="70px"
        >
          <template #default="scope">
            <el-link
              @click="
                courseFileRef.show(scope.row.courseFile, scope.row.courseId)
              "
              :type="scope.row.courseFile.length > 0 ? 'primary' : 'danger'"
            >
              {{ scope.row.courseFile.length > 0 ? '已配置' : '否' }}
            </el-link></template
          >
        </el-table-column>
        <el-table-column
          prop="ifExam"
          label="考试"
          sortable="custom"
          width="70px"
        >
          <template #default="scope">
            <el-link
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
            </el-link></template
          >
        </el-table-column>
        <el-table-column
          prop="ifEval"
          label="评价"
          sortable="custom"
          width="70px"
        >
          <template #default="scope">
            <el-link
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
            </el-link></template
          >
        </el-table-column>
        <el-table-column
          prop="status"
          label="状态"
          sortable="custom"
          width="75px"
        >
          <template #default="scope"
            ><el-tag
              @click="updateStatus(scope.row)"
              :type="scope.row.status ? 'success' : 'danger'"
              size="small"
            >
              {{ scope.row.status ? '已发布' : '未发布' }}
            </el-tag></template
          >
        </el-table-column>
        <el-table-column
          prop="handleDate"
          label="操作时间"
          sortable="custom"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column label="操作" fixed="right" width="150px">
          <template #default="scope">
            <el-button
              type="primary"
              icon="Edit"
              size="small"
              @click="courseManager(scope.row)"
              circle
            ></el-button>
            <el-button
              v-if="scope.row.bookExam === 1"
              type="danger"
              icon="Time"
              size="small"
              @click="() => bookInfoRef.show(scope.row.examCode)"
              circle
            ></el-button>
            <el-popover placement="top" width="400" trigger="click">
              <p>报表下载</p>
              <div style="text-align: right; margin: 0">
                <el-button
                  type="primary"
                  size="small"
                  @click="
                    $global.downloadExcel(
                      'college/report/hum/' + scope.row.courseId,
                      null,
                      '课程学习记录明细BY人员.xlsx'
                    )
                  "
                  >学习报表</el-button
                >
                <el-button
                  type="primary"
                  size="small"
                  @click="
                    $global.downloadExcel(
                      'college/report/exam/' + scope.row.courseId,
                      null,
                      '课程考试记录BY人员.xlsx'
                    )
                  "
                  >考试报表</el-button
                >
                <el-button
                  type="primary"
                  size="small"
                  @click="
                    $global.downloadExcel(
                      'college/report/eval/' + scope.row.courseId,
                      null,
                      '满意度评价明细By课程.xlsx'
                    )
                  "
                  >满意度报表</el-button
                >
              </div>
              <template #reference>
                <el-button
                  style="margin-left: 10px"
                  type="warning"
                  icon="Document"
                  size="small"
                  circle
                ></el-button>
              </template>
            </el-popover>
            <el-button
              size="small"
              round
              @click="() => taskListRef.show(scope.row.courseId)"
              >任务</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pageNum"
        :page-sizes="[20, 40, 100, 200]"
        :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </el-card>

    <!-- 弹出窗口 -->
    <el-dialog
      :title="courseFormTitle"
      :close-on-click-modal="false"
      width="80%"
      @close="onClose"
      v-model="courseFormVisible"
    >
      <el-form
        ref="courseForm"
        :model="courseFormData"
        :rules="rules"
        size="small"
        label-width="100px"
      >
        <el-row :gutter="15">
          <el-col :span="24">
            <el-form-item label="课程名称" prop="courseName">
              <el-input
                v-model="courseFormData.courseName"
                placeholder="请输入名称课程课程名称"
                :maxlength="50"
                show-word-limit
                clearable
                :style="{ width: '100%' }"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="讲师" prop="lecturer">
              <el-select
                v-model="courseFormData.lecturer"
                placeholder="请选择讲师"
                filterable
                clearable
                style="height: 20px; width: 100%"
              >
                <el-option
                  v-for="(item, index) in teachers"
                  :key="index"
                  :label="
                    item.ploName +
                    '/' +
                    item.ploNum +
                    (item.status === 0 ? '[无效]' : '1')
                  "
                  :value="item.ploNum"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="日期范围" prop="dateRange">
              <el-date-picker
                type="daterange"
                v-model="courseFormData.dateRange"
                format="YYYY-MM-DD"
                value-format="YYYY-MM-DD"
                style="width: 100%"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                range-separator="至"
                clearable
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="课程积分" prop="coin">
              <el-input-number
                v-model="courseFormData.coin"
                placeholder="课程积分"
                :step="0.5"
              ></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="课程时数" prop="hours">
              <el-input-number
                v-model="courseFormData.hours"
                placeholder="课程时数"
                :step="0.5"
              ></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属分类" prop="courseType">
              <el-cascader
                v-model="courseFormData.courseType"
                :options="courseTypeTree"
                @change="
                  courseFormData.courseType = courseFormData.courseType.pop()
                "
                :props="{ checkStrictly: true, value: 'id', label: 'name' }"
                :style="{ width: '100%' }"
              ></el-cascader>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="授课对象" prop="teachObject">
              <el-select
                v-model="courseFormData.teachObject"
                placeholder="请选择选项随机"
                :style="{ width: '100%' }"
              >
                <el-option label="全体员工" value="全体员工"></el-option>
                <el-option
                  v-for="(item, index) in teachObject"
                  :key="index"
                  :label="item.groupName + '[' + item.ploNum + '人]'"
                  :value="item.groupName"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="授课方式" prop="teachMethod">
              <el-radio-group v-model="courseFormData.teachMethod" size="small">
                <el-radio-button
                  v-for="(item, index) in dictStore.getDictByNames(
                    'trm_course_teach_method',
                    1
                  )"
                  :key="index"
                  :value="Number(item.code)"
                  :label="item.codeval"
                ></el-radio-button>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="学习方式" prop="studyType">
              <el-radio-group v-model="courseFormData.studyType" size="small">
                <el-radio-button
                  v-for="(item, index) in dictStore.getDictByNames(
                    'trm_course_study_type',
                    1
                  )"
                  :key="index"
                  :value="Number(item.code)"
                  :label="item.codeval"
                ></el-radio-button>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="课程描述" prop="courseDes">
              <el-input
                type="textarea"
                v-model="courseFormData.courseDes"
                placeholder="请输入课程描述"
                :maxlength="2000"
                show-word-limit
                :autosize="{ minRows: 4, maxRows: 8 }"
                :style="{ width: '100%' }"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <template #footer>
        <div class="dialog-footer">
          <el-button size="small" @click="courseFormVisible = false"
            >取消</el-button
          >
          <el-button size="small" type="primary" @click="submitForm()"
            >确定</el-button
          >
        </div>
      </template>
    </el-dialog>

    <el-dialog
      title="课程推荐"
      :close-on-click-modal="false"
      width="60%"
      @close="onClose"
      v-model="recommendFormVisible"
    >
      <el-alert
        title="操作说明"
        type="info"
        :closable="false"
        description="滚动广告输入0则为不展示。输入数字大于1则有大到小滚动。推荐分类，输入相同分类名称则自动归类在首页推荐栏位展示"
      ></el-alert>
      <el-form
        ref="recommendFormRef"
        :inline="true"
        :model="recommendForm"
        :rules="rules"
        size="small"
        class="form-inline"
      >
        <el-form-item label="滚动广告" prop="topFlag">
          <el-input
            v-model="recommendForm.topFlag"
            type="number"
            placeholder="输入数字从大至小"
            :maxlength="2"
          ></el-input>
        </el-form-item>
        <el-form-item label="推荐分类" prop="recommend">
          <el-input
            v-model="recommendForm.recommend"
            placeholder="请输入推荐类别"
            :maxlength="20"
            show-word-limit
            clearable
            :style="{ width: '100%' }"
          ></el-input>
        </el-form-item>
        <el-form-item label="分类顺序" prop="recommendFlag">
          <el-input
            v-model="recommendForm.recommendFlag"
            type="number"
            placeholder="输入数字从大至小"
            :maxlength="2"
          ></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button size="small" @click="recommendFormVisible = false"
            >取消</el-button
          >
          <el-button size="small" type="primary" @click="submitRecommendForm()"
            >确定</el-button
          >
        </div>
      </template>
    </el-dialog>
    <CourseFile ref="courseFileRef" @refresh="getCourseTable"></CourseFile>
    <Satisfy ref="satisfyRef" @refresh="getCourseTable"></Satisfy>
    <Cover ref="coverRef" @refresh="getCourseTable"></Cover>
    <ExamConfig ref="examConfigRef" @refresh="getCourseTable"></ExamConfig>
    <TaskList ref="taskListRef"></TaskList>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
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
    if (res.code !== 200) return ElMessage.error(res.msg)
    courses.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    ElMessage.error('获取课程列表失败')
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

const tableSort = (data) => {
  if (data.order === 'ascending') queryInfo.orderType = ' asc '
  else if (data.order === 'descending') queryInfo.orderType = ' desc '
  queryInfo.order = data.prop
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

const submitForm = () => {
  if (!courseForm.value) return

  courseFormData.begDate = courseFormData.dateRange[0]
  courseFormData.endDate = courseFormData.dateRange[1]
  // courseFormData.courseType = courseFormData.courseType.pop()

  courseForm.value.validate(async (valid) => {
    if (valid) {
      try {
        let res
        if (courseFormTitle.value === '课程新增') {
          res = await courseApi.saveCourse(courseFormData)
        } else {
          res = await courseApi.updateCourse(courseFormData)
        }
        if (res.code !== 200) return ElMessage.error(res.msg)
        ElMessage.success(res.msg)
        getCourseTable()
        courseFormVisible.value = false
      } catch (error) {
        ElMessage.error('保存课程失败')
      }
    } else {
      ElMessage.error('表单校验失败！请检查表单！')
    }
  })
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
    if (res.code !== 200) return ElMessage.error(res.msg)
    ElMessage.success(res.msg)
    getCourseTable()
    recommendFormVisible.value = false
  } catch (error) {
    ElMessage.error('保存推荐设置失败')
  }
}

const updateStatus = async (row) => {
  try {
    const res = await courseApi.updateCourseStatus(
      row.courseId,
      row.status ? 0 : 1
    )
    if (res.code !== 200) return ElMessage.error(res.msg)
    ElMessage.success(res.msg)
    getCourseTable()
  } catch (error) {
    ElMessage.error('更新课程状态失败')
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
      ElMessage.error(courseTypeRes.msg)
    }

    if (teachersRes.code === 200) {
      teachers.value = teachersRes.data
    } else {
      ElMessage.error(teachersRes.msg)
    }

    if (teachGroupRes.code === 200) {
      teachObject.value = teachGroupRes.data
    } else {
      ElMessage.error(teachGroupRes.msg)
    }

    await getCourseTable()
  } catch (error) {
    ElMessage.error('加载初始数据失败')
  }
}

onMounted(() => {
  loadInitialData()
})
</script>

<style lang="less" scoped>
.form-inline .el-input {
  --el-input-width: 150px;
}

.form-inline .el-select {
  --el-select-width: 150px;
}
</style>
