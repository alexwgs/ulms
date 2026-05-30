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
      <el-form :model="form" :inline="true" size="small" class="inline-form">
        <el-form-item label="预约日期" :label-width="formLabelWidth">
          <el-select
            v-model="currentInfoCode"
            placeholder="请选择活动区域"
            @change="listBookedDetail"
          >
            <el-option
              v-for="item in bookInfo"
              :key="item.infoCode"
              :label="item.bookDate"
              :value="item.infoCode"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="已预约次数" :label-width="formLabelWidth">
          {{ myBooked.length }}
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            size="small"
            :loading="refreshLoading"
            @click="listBookedDetail"
            >刷新</el-button
          >
        </el-form-item>
      </el-form>
      <el-table :data="data" size="small" height="300px">
        <el-table-column property="begTime" label="开始时间" width="100" />
        <el-table-column property="endTime" label="结束时间" width="100" />
        <el-table-column label="预约名单" show-overflow-tooltip>
          <template #default="scope">
            <el-avatar
              v-for="user in scope.row.users"
              :key="user.ploNum"
              size="small"
              :src="fsURL + user.avatar"
            />
          </template>
        </el-table-column>
        <el-table-column property="bookLimit" label="预约余位" width="80">
          <template #default="scope">
            {{ scope.row.bookLimit - scope.row.users.length }}
          </template>
        </el-table-column>
        <el-table-column label="我要预约" width="120">
          <template #default="scope">
            <div v-if="timeSelectValid(scope.row.endTime)">【过期】</div>
            <!-- <div v-else-if="scope.row.bookLimit - scope.row.users.length <= 0">【满员】</div> -->
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
              v-else
              type="primary"
              icon="star-off"
              size="small"
              @click="bookExam(scope.row)"
              circle
            />
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { bookInfoApi } from '@/api/edu/bookInfo'

const dialogFormVisible = ref(false)
const formLabelWidth = ref('120px')
const currentInfoCode = ref('')
const currentBookInfo = ref(null)
const data = ref([])
const bookInfo = ref([])
const myBooked = ref([])
const refreshLoading = ref(false)
const fsURL = ref(import.meta.env.VITE_FILE_BASE_URL)

const form = reactive({
  timeCode: '',
  listStat: 1,
  bookCode: '',
  examCode: '',
  infoCode: ''
})
const userId = ref('')

const show = (examCode, user) => {
  form.examCode = examCode
  userId.value = user
  listBookInfo()
  dialogFormVisible.value = true
}

const listBookInfo = async () => {
  try {
    const res = await bookInfoApi.getExamBookTimeInfo(form.examCode)
    if (res.code !== 200) return ElMessage.error(res.msg)
    bookInfo.value = res.data
    if (bookInfo.value != null && bookInfo.value.length > 0) {
      currentInfoCode.value = bookInfo.value[0].infoCode
      listBookedDetail()
    }
  } catch (error) {
    ElMessage.error('获取预约信息失败')
  }
}

const listBookedDetail = async () => {
  refreshLoading.value = true
  try {
    const list = bookInfo.value.filter(
      (e) => e.infoCode === currentInfoCode.value
    )
    if (list == null || list.length < 1)
      return ElMessage.warning('暂无预约时点配置！')
    currentBookInfo.value = list[0]

    const res = await bookInfoApi.getExamBookDetail({
      bookCode: currentBookInfo.value.bookCode,
      examCode: currentBookInfo.value.examCode,
      infoCode: currentBookInfo.value.infoCode,
      userId: userId.value
    })
    if (res.code !== 200) return ElMessage.error(res.msg)
    data.value = res.data
    myBooked.value = res.myBooked
  } catch (error) {
    ElMessage.error('获取预约详情失败')
  } finally {
    refreshLoading.value = false
  }
}

const bookExam = async (row) => {
  form.bookCode = currentBookInfo.value.bookCode
  form.examCode = currentBookInfo.value.examCode
  form.infoCode = currentBookInfo.value.infoCode
  form.listStat = 1
  form.timeCode = row.journo
  form.ploNum = userId.value

  try {
    const res = await bookInfoApi.forceExamBookList(form)
    if (res.code !== 200) return ElMessage.error(res.msg)
    ElMessage.success(res.msg)
    listBookedDetail()
  } catch (error) {
    ElMessage.error('预约失败')
  }
}

const timeSelectValid = (time) => {
  if (!currentBookInfo.value) return false
  const now = new Date()
  const bookDateTime = new Date(`${currentBookInfo.value.bookDate} ${time}`)
  return bookDateTime.getTime() - now.getTime() <= 0
}

defineExpose({
  show
})
</script>
<style lang="less" scoped>
.bgcolor {
  background-color: #fdfffeb0;
}
.inline-form .el-input {
  --el-input-width: 150px;
}

.inline-form .el-select {
  --el-select-width: 150px;
}
</style>
