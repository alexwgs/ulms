<template>
  <div class="container">
    <el-alert v-if="userId != serverUserId" title="注意！！因系统存在缓存，该信息非你的试卷，请按下CTRL+SHIFT+DEL 清除缓存后重新登录" type="error"
      effect="dark">
    </el-alert>
    <div class="title">未完成的考试</div>
    <el-table :data="currentExam" size="small" style="width: 100%; margin-top: 15px" height="20vh"
      row-class-name="table">
      <el-table-column prop="examName" label="考试名称" show-overflow-tooltip></el-table-column>
      <el-table-column prop="name" label="考试时间" width="250" show-overflow-tooltip>
        <template #default="scope">
          {{ scope.row.begDate }} ~ {{ scope.row.endDate }}
        </template>
      </el-table-column>
      <el-table-column prop="examTime" label="考试时长" width="100"></el-table-column>
      <el-table-column label="考试地点" width="100" show-overflow-tooltip>
        <template #default="scope">
          {{ scope.row.areaInfo == null ? '不限' : scope.row.areaInfo }}
        </template>
      </el-table-column>
      <el-table-column label="单题时长" width="100">
        <template #default="scope">
          {{ scope.row.quesTime == null ? '不限' : scope.row.quesTime }}
        </template>
      </el-table-column>
      <el-table-column label="考试预约" width="220">
        <template #default="scope">
          <el-button v-if="scope.row.bookExam === 1" size="small" type="primary"
            @click="() => examBookRef.show(scope.row.examCode)">{{
              scope.row.bookTime === '' ? '预约考试' : scope.row.bookTime
            }}</el-button>
          <div v-else>无需预约</div>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="scope">
          <el-button size="small" type="primary" :disabled="userId != serverUserId"
            @click="examStart(scope.row)">开始考试</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="title">已完成的考试{{ refushFlag }}</div>
    <el-table :data="historyExam" size="small" style="width: 100%; margin-top: 15px" height="40vh">
      <el-table-column prop="examInfo.examName" label="考试名称" show-overflow-tooltip></el-table-column>
      <el-table-column label="考试类型" width="120">
        <template #default="scope">
          {{
            dictStore.getDictLabel('trm_exam_use', scope.row.examInfo.examUse)
          }}
        </template>
      </el-table-column>
      <el-table-column label="阅卷状态" width="100">
        <template #default="scope">
          {{ dictStore.getDictLabel('yes_or_not', scope.row.examInfo.ifRead) }}
        </template>
      </el-table-column>
      <el-table-column prop="userScore" label="得分/总分" width="100"></el-table-column>
      <el-table-column label="答题时间" width="210">
        <template #default="scope">
          {{ scope.row.begDate == null ? '未考' : scope.row.begDate }} ~
          {{ scope.row.endDate == null ? '自动交卷' : scope.row.endDate }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="120px">
        <template #default="scope">
          <el-button type="primary" size="small" @click="gotoMyTest(scope.row)" :disabled="scope.row.examInfo.ifRead === 0 ||
            scope.row.compStat === 0 ||
            scope.row.compStat === 3
            ">查看试卷</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @current-change="handleCurrentChange" :current-page.sync="currentPage"
      :page-size="queryInfo.pageSize" layout="total, prev, pager, next" :total="total">
    </el-pagination>
    <ExamBook ref="examBookRef"></ExamBook>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { examTestApi } from '@/api/edu/examTest'
import { examConfigApi } from '@/api/edu/examConfig'
import useDictStore from '@/stores/modules/dict'
import { ElMessage } from 'element-plus'
import ExamBook from './component/ExamBook.vue'

const router = useRouter()
const dictStore = useDictStore()
const examBookRef = ref(null)

const historyExam = ref([])
const currentExam = ref([])
const total = ref(0)
const currentPage = ref(1)
const queryInfo = reactive({
  pageSize: 10,
  pageNum: 1
})
const serverUserId = ref('')
const userId = ref('')
const refushFlag = ref('')

const getHistoryExam = async () => {
  const res = await examTestApi.getHistoryExam(queryInfo)
  if (res.code !== 200) {
    ElMessage.error(res.msg)
    return
  }
  historyExam.value = res.data.list
  total.value = res.data.total
}

const getCurrentExam = async () => {
  const res = await examConfigApi.getCurrentExamList()
  if (res.code !== 200) {
    ElMessage.error(res.msg)
    return
  }
  currentExam.value = res.data
  serverUserId.value = res.userId
}

const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  getHistoryExam()
}

const gotoMyTest = (examInfo) => {
  sessionStorage.setItem('examInfo', JSON.stringify(examInfo))
  const routeUrl = router.resolve({
    path: '/edu/exam/test/view/' + examInfo.examCode
  })
  window.open(routeUrl.href, 'examView')
  sessionStorage.removeItem('examInfo')
}

const examStart = (examInfo) => {
  sessionStorage.setItem('examInfo', JSON.stringify(examInfo))
  const routeUrl = router.resolve({
    path: '/edu/exam/test'
  })
  window.open(routeUrl.href, 'newWindow')
  sessionStorage.removeItem('examInfo')
}

const refreshList = () => {
  getHistoryExam()
  getCurrentExam()
}

onMounted(() => {
  userId.value = JSON.parse(window.localStorage.getItem('user')).ploNum
  refreshList()
})
</script>

<style lang="less" scoped>
.container {
  height: 100%;

  .title {
    font-size: 22px;
    font-weight: 500;
    color: #fff;
    line-height: 15px;
    border-bottom: #fff 1px dotted;
    padding-top: 15px;
    padding-bottom: 15px;
  }

  .el-table {
    border-radius: 10px;
  }

  /*最外层透明*/
  :deep(.el-table) {
    background-color: rgba(224, 224, 224, 0.25);

    &__expanded-cell {
      background-color: rgba(224, 224, 224, 0.25);
    }

    /* 表格内背景颜色 */
    th,
    tr,
    td {
      background-color: rgba(224, 224, 224, 0.25);
      color: #000;
    }
  }
}

.brush-info {
  width: 100%;
  text-align: right;
  font-weight: 800;
}

// 设置分页背景透明
:deep(.el-pager li) {
  background: rgba(224, 224, 224, 0.25);
}

:deep(.el-pagination button) {
  background: rgba(224, 224, 224, 0.25);
}

.table {
  color: #fff;
}
</style>
