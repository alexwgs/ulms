<template>
  <div class="container">
    <t-alert v-if="userId != serverUserId" title="注意！！因系统存在缓存，该信息非你的试卷，请按下CTRL+SHIFT+DEL 清除缓存后重新登录" theme="error">
    </t-alert>
    <div class="title">未完成的考试</div>
    <CustomTable rowKey="id" :data="currentExam" size="small" style="width: 100%; margin-top: 15px" height="20vh"
      row-class-name="table">
      <TableColumn colKey="examName" label="考试名称" ellipsis></TableColumn>
      <TableColumn colKey="name" label="考试时间" width="250" ellipsis>
        <template #default="scope">
          {{ scope.row.begDate }} ~ {{ scope.row.endDate }}
        </template>
      </TableColumn>
      <TableColumn colKey="examTime" label="考试时长" width="100"></TableColumn>
      <TableColumn label="考试地点" width="100" ellipsis>
        <template #default="scope">
          {{ scope.row.areaInfo == null ? '不限' : scope.row.areaInfo }}
        </template>
      </TableColumn>
      <TableColumn label="单题时长" width="100">
        <template #default="scope">
          {{ scope.row.quesTime == null ? '不限' : scope.row.quesTime }}
        </template>
      </TableColumn>
      <TableColumn label="考试预约" width="220">
        <template #default="scope">
          <t-button v-if="scope.row.bookExam === 1" size="small" theme="primary"
            @click="() => examBookRef.show(scope.row.examCode)">{{
              scope.row.bookTime === '' ? '预约考试' : scope.row.bookTime
            }}</t-button>
          <div v-else>无需预约</div>
        </template>
      </TableColumn>
      <TableColumn label="操作" width="100">
        <template #default="scope">
          <t-button size="small" theme="primary" :disabled="userId != serverUserId"
            @click="examStart(scope.row)">开始考试</t-button>
        </template>
      </TableColumn>
    </CustomTable>

    <div class="title">已完成的考试{{ refushFlag }}</div>
    <CustomTable rowKey="id" :data="historyExam" size="small" style="width: 100%; margin-top: 15px" height="40vh">
      <TableColumn colKey="examInfo.examName" label="考试名称" ellipsis></TableColumn>
      <TableColumn label="考试类型" width="120">
        <template #default="scope">
          {{
            dictStore.getDictLabel('trm_exam_use', scope.row.examInfo.examUse)
          }}
        </template>
      </TableColumn>
      <TableColumn label="阅卷状态" width="100">
        <template #default="scope">
          {{ dictStore.getDictLabel('yes_or_not', scope.row.examInfo.ifRead) }}
        </template>
      </TableColumn>
      <TableColumn colKey="userScore" label="得分/总分" width="100"></TableColumn>
      <TableColumn label="答题时间" width="210">
        <template #default="scope">
          {{ scope.row.begDate == null ? '未考' : scope.row.begDate }} ~
          {{ scope.row.endDate == null ? '自动交卷' : scope.row.endDate }}
        </template>
      </TableColumn>
      <TableColumn label="操作" width="120px">
        <template #default="scope">
          <t-button theme="primary" size="small" @click="gotoMyTest(scope.row)" :disabled="scope.row.examInfo.ifRead === 0 ||
            scope.row.compStat === 0 ||
            scope.row.compStat === 3
            ">查看试卷</t-button>
        </template>
      </TableColumn>
    </CustomTable>
    <t-pagination @current-change="handleCurrentChange" v-model="currentPage"
      :page-size="queryInfo.pageSize" :total="total">
    </t-pagination>
    <ExamBook ref="examBookRef" @refresh="getCurrentExam"></ExamBook>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { examTestApi } from '@/api/edu/examTest'
import { examConfigApi } from '@/api/edu/examConfig'
import useDictStore from '@/stores/modules/dict'
import { MessagePlugin } from 'tdesign-vue-next'
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
    MessagePlugin.error(res.msg)
    return
  }
  historyExam.value = res.data.list
  total.value = res.data.total
}

const getCurrentExam = async () => {
  const res = await examConfigApi.getCurrentExamList()
  if (res.code !== 200) {
    MessagePlugin.error(res.msg)
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

  .t-table {
    border-radius: 10px;
  }

  /*最外层透明*/
  :deep(.t-table) {
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
:deep(.t-pager li) {
background: rgba(224, 224, 224, 0.25);
}

:deep(.t-pagination button) {
background: rgba(224, 224, 224, 0.25);
}

.table {
  color: #fff;
}
</style>
