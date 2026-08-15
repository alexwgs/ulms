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
      <t-form :data="form" layout="inline" size="small">
        <t-form-item label="预约日期" :label-width="formLabelWidth">
          <t-select
            v-model="currentInfoCode"
            placeholder="请选择活动区域"
            @change="listBookedDetail()"
          >
            <t-option
              v-for="item in bookInfo"
              :key="item.infoCode"
              :label="item.bookDate"
              :value="item.infoCode"
            ></t-option>
          </t-select>
        </t-form-item>
        <t-form-item label="已预约次数" :label-width="formLabelWidth">
          {{ myBooked.length }}
        </t-form-item>
        <t-button
          theme="primary"
          size="small"
          :loading="refreshLoading"
          @click="listBookedDetail()"
          >刷新</t-button
        >
      </t-form>
      <CustomTable rowKey="id" :data="data" size="small" height="300px">
        <TableColumn
          property="begTime"
          label="开始时间"
          width="100"></TableColumn>
        <TableColumn
          property="endTime"
          label="结束时间"
          width="100"></TableColumn>
        <TableColumn label="预约名单" ellipsis>
          <template #default="scope">
            <t-avatar
              v-for="user in scope.row.users"
              :key="user.ploNum"
              size="small"
              :src="fsURL + user.avatar"
            ></t-avatar>
          </template>
        </TableColumn>
        <TableColumn property="bookLimit" label="预约余位" width="80">
          <template #default="scope">
            {{ scope.row.bookLimit - scope.row.users.length }}
          </template>
        </TableColumn>
        <TableColumn label="我要预约" width="120">
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
              <t-tag theme="success" size="small" variant="light"
                >【当前】</t-tag
              >
            </div>
            <t-button
              v-else-if="myBooked.length < 2"
              theme="primary" size="small"
              @click="bookExam(scope.row)"
             
            ><template #icon><DynamicIcon name="star-off" /></template></t-button>
            <div v-else>【预约达上限】</div>
          </template>
        </TableColumn>
      </CustomTable>
    </t-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { bookInfoApi } from '@/api/edu/bookInfo'
import { MessagePlugin } from 'tdesign-vue-next'
import dayjs from 'dayjs'

const emit = defineEmits(['refresh'])

const fsURL = import.meta.env.VITE_FILE_BASE_URL
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
      if (res.code !== 200) return MessagePlugin.error(res.msg)
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
    return MessagePlugin.warning('暂无预约时点配置！')
  }
  currentBookInfo.value = list[0]
  bookInfoApi
    .getExamBookDetail({
      bookCode: currentBookInfo.value.bookCode,
      examCode: currentBookInfo.value.examCode,
      infoCode: currentBookInfo.value.infoCode
    })
    .then((res) => {
      if (res.code !== 200) return MessagePlugin.error(res.msg)
      data.value = res.data
      myBooked.value = res.myBooked
    })
    .then(() => {
      refreshLoading.value = false
    })
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
      if (res.code !== 200) return MessagePlugin.error(res.msg)
      MessagePlugin.success(res.msg)
    })
    .then(() => {
      listBookedDetail()
      // 通知父组件刷新考试列表
      emit('refresh')
    })
}

const timeSelectValid = (time) => {
  return (
    dayjs(currentBookInfo.value.bookDate + ' ' + time).format('X') -
      dayjs().format('X') <=
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
