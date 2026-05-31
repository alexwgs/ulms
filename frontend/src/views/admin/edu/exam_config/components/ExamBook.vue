<template>
  <div>
    <t-dialog
      header="考试预约"
      v-model:visible="dialogFormVisible"
      width="60%"
      custom-class="bgcolor"
      :modal="false"
      :close-on-overlay-click="false"
    >
      <t-form :data="form" layout="inline" size="small" class="inline-form">
        <t-form-item label="预约日期" :label-width="formLabelWidth">
          <t-select
            v-model="currentInfoCode"
            placeholder="请选择活动区域"
            @change="listBookedDetail"
          >
            <t-option
              v-for="item in bookInfo"
              :key="item.infoCode"
              :label="item.bookDate"
              :value="item.infoCode"
            />
          </t-select>
        </t-form-item>
        <t-form-item label="已预约次数" :label-width="formLabelWidth">
          {{ myBooked.length }}
        </t-form-item>
        <t-form-item>
          <t-button
            theme="primary"
            size="small"
            :loading="refreshLoading"
            @click="listBookedDetail"
            >刷新</t-button
          >
        </t-form-item>
      </t-form>
      <CustomTable rowKey="id" :data="data" size="small" height="300px">
        <TableColumn property="begTime" label="开始时间" width="100" />
        <TableColumn property="endTime" label="结束时间" width="100" />
        <TableColumn label="预约名单" ellipsis>
          <template #default="scope">
            <t-avatar
              v-for="user in scope.row.users"
              :key="user.ploNum"
              size="small"
              :src="fsURL + user.avatar"
            />
          </template>
        </TableColumn>
        <TableColumn property="bookLimit" label="预约余位" width="80">
          <template #default="scope">
            {{ scope.row.bookLimit - scope.row.users.length }}
          </template>
        </TableColumn>
        <TableColumn label="我要预约" width="120">
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
              <t-tag theme="success" size="small" effect="dark"
                >【当前】</t-tag
              >
            </div>
            <t-button
              v-else
              theme="primary" size="small"
              @click="bookExam(scope.row)"
              circle
            ><template #icon><DynamicIcon name="star-off" /></template></t-button>
          </template>
        </TableColumn>
      </CustomTable>
    </t-dialog>
  </div>
</template>
<script setup>
import { ref, reactive } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
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
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    bookInfo.value = res.data
    if (bookInfo.value != null && bookInfo.value.length > 0) {
      currentInfoCode.value = bookInfo.value[0].infoCode
      listBookedDetail()
    }
  } catch (error) {
    MessagePlugin.error('获取预约信息失败')
  }
}

const listBookedDetail = async () => {
  refreshLoading.value = true
  try {
    const list = bookInfo.value.filter(
      (e) => e.infoCode === currentInfoCode.value
    )
    if (list == null || list.length < 1)
      return MessagePlugin.warning('暂无预约时点配置！')
    currentBookInfo.value = list[0]

    const res = await bookInfoApi.getExamBookDetail({
      bookCode: currentBookInfo.value.bookCode,
      examCode: currentBookInfo.value.examCode,
      infoCode: currentBookInfo.value.infoCode,
      userId: userId.value
    })
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    data.value = res.data
    myBooked.value = res.myBooked
  } catch (error) {
    MessagePlugin.error('获取预约详情失败')
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
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    listBookedDetail()
  } catch (error) {
    MessagePlugin.error('预约失败')
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
-color: #fdfffeb0;
}
.inline-form .t-input {
  --td-input-width: 150px;
}

.inline-form .t-select {
  --td-select-width: 150px;
}
</style>
