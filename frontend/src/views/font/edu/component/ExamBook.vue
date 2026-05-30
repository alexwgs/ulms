<template>
  <div>
    <el-dialog
      title="考试预约"
      v-model="dialogFormVisible"
      width="60%"
      custom-class="bgcolor"
      :modal="false"
      :close-on-click-modal="false"
    >
      <el-form :model="form" :inline="true" size="small">
        <el-form-item label="预约日期" :label-width="formLabelWidth">
          <el-select
            v-model="currentInfoCode"
            placeholder="请选择活动区域"
            @change="listBookedDetail()"
          >
            <el-option
              v-for="item in bookInfo"
              :key="item.infoCode"
              :label="item.bookDate"
              :value="item.infoCode"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="已预约次数" :label-width="formLabelWidth">
          {{ myBooked.length }}
        </el-form-item>
        <el-button
          type="primary"
          size="small"
          :loading="refreshLoading"
          @click="listBookedDetail()"
          >刷新</el-button
        >
      </el-form>
      <el-table :data="data" size="small" height="300px">
        <el-table-column
          property="begTime"
          label="开始时间"
          width="100"
        ></el-table-column>
        <el-table-column
          property="endTime"
          label="结束时间"
          width="100"
        ></el-table-column>
        <el-table-column label="预约名单" show-overflow-tooltip>
          <template #default="scope">
            <el-avatar
              v-for="user in scope.row.users"
              :key="user.ploNum"
              size="small"
              :src="fsURL + user.avatar"
            ></el-avatar>
          </template>
        </el-table-column>
        <el-table-column property="bookLimit" label="预约余位" width="80">
          <template #default="scope">
            {{ scope.row.bookLimit - scope.row.users.length }}
          </template>
        </el-table-column>
        <el-table-column label="我要预约" width="120">
          <template #default="scope">
            <div v-if="timeSelectValid(scope.row.begTime)">【过期】</div>
            <div v-else-if="scope.row.bookLimit - scope.row.users.length <= 0">
              【满员】
            </div>
            <div
              v-else-if="
                myBooked.length > 0 &&
                myBooked.filter(
                  (e) =>
                    e.listStat === 1 &&
                    scope.row.journo === e.timeCode &&
                    e.infoCode === currentInfoCode
                ).length > 0
              "
            >
              <el-tag type="success" size="small" effect="dark"
                >【当前】</el-tag
              >
            </div>
            <el-button
              v-else-if="myBooked.length < 2"
              type="primary"
              icon="star-off"
              size="small"
              @click="bookExam(scope.row)"
              circle
            ></el-button>
            <div v-else>【预约达上限】</div>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { bookInfoApi } from '@/api/edu/bookInfo'
import { ElMessage } from 'element-plus'

const fsURL = import.meta.env.VITE_BASE_API
const dialogFormVisible = ref(false)
const formLabelWidth = '120px'
const form = reactive({
  timeCode: '',
  listStat: 1,
  bookCode: '',
  examCode: '',
  infoCode: ''
})
const currentInfoCode = ref('')
const currentBookInfo = ref(null)
const currentDate = ref('')
const data = ref([])
const bookInfo = ref(null)
const myBooked = ref([])
const refreshLoading = ref(false)

const show = (examCode) => {
  form.examCode = examCode
  listBookInfo()
  dialogFormVisible.value = true
}

const listBookInfo = () => {
  bookInfoApi
    .getExamBookTimeInfo(form.examCode)
    .then((res) => {
      if (res.code !== 200) return ElMessage.error(res.msg)
      bookInfo.value = res.data
    })
    .then(() => {
      if (bookInfo.value != null && bookInfo.value.length > 0) {
        currentInfoCode.value = bookInfo.value[0].infoCode
        listBookedDetail()
      }
    })
}

const listBookedDetail = () => {
  refreshLoading.value = true
  const list = bookInfo.value.filter(
    (e) => e.infoCode === currentInfoCode.value
  )
  if (list != null && list < 1) {
    refreshLoading.value = false
    return ElMessage.warning('暂无预约时点配置！')
  }
  currentBookInfo.value = list[0]
  bookInfoApi
    .getExamBookDetail({
      bookCode: currentBookInfo.value.bookCode,
      examCode: currentBookInfo.value.examCode,
      infoCode: currentBookInfo.value.infoCode
    })
    .then((res) => {
      if (res.code !== 200) return ElMessage.error(res.msg)
      data.value = res.data
      myBooked.value = res.myBooked
    })
    .then(() => {
      refreshLoading.value = false
    })
}

const returnBookName = (bookCode) => {
  const list = timeConfig.value.filter((e) => e.bookCode === bookCode)
  if (list.length < 1) return bookCode
  return list[0].bookName
}

const bookExam = (row) => {
  form.bookCode = currentBookInfo.value.bookCode
  form.examCode = currentBookInfo.value.examCode
  form.infoCode = currentBookInfo.value.infoCode
  form.listStat = 1
  form.timeCode = row.journo

  bookInfoApi
    .createExamBookList(form)
    .then((res) => {
      if (res.code !== 200) return ElMessage.error(res.msg)
      ElMessage.success(res.msg)
    })
    .then(() => {
      listBookedDetail()
      // 调用父组件方法
      if (typeof getCurrentExam === 'function') {
        getCurrentExam()
      }
    })
}

const timeSelectValid = (time) => {
  return (
    moment(currentBookInfo.value.bookDate + ' ' + time).format('X') -
      moment(nowDate).format('X') <=
    0
  )
}

defineExpose({
  show
})
</script>

<style lang="less" scoped>
.bgcolor {
  background-color: #fdfffeb0;
}
</style>
