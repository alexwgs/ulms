<template>
  <div>
    <t-card class="box-card" style="height: 200px">
      <div style="float: left">
        <t-avatar
          shape="round"
          size="160px"
          fit="fill"
          :src="fsURL + userStore.avatar"
        ></t-avatar>
      </div>
      <div
        style="
          float: left;
          padding-left: 15px;
          min-width: 720px;
          padding-bottom: 20px;
        "
      >
        <t-tag effect="plain"
          >{{ userStore.ploName }} / {{ userStore.ploNum }}</t-tag
        >
        <t-row :gutter="10" style="font-size: 12px; padding-top: 15px">
          <t-col :span="6">科室：{{ userStore.deptName }}</t-col>
          <t-col :span="6">组别：{{ userStore.groupName }}</t-col>
        </t-row>
        <t-divider style="margin: 15px 0 15px 0"></t-divider>
        <t-row
          v-if="myPoint"
          :gutter="10"
          style="font-size: 12px; padding-top: 5px"
        >
          <t-col :span="3"
            >金币：<t-tag theme="default">{{ myPoint.coin }}</t-tag></t-col
          >
          <t-col :span="3"
            >培训课程:
            <t-tag theme="default">{{ myPoint.signNum }}</t-tag></t-col
          >
          <t-col :span="3"
            >授课课时: <t-tag theme="default">{{ myPoint.hours }}</t-tag></t-col
          >
          <t-col :span="3"
            >积分: <t-tag theme="default">{{ myPoint.point }}</t-tag></t-col
          >
        </t-row>
      </div>
    </t-card>
    <t-row :gutter="10" style="margin-top: 10px; width: 100%">
      <t-col :span="8">
        <t-card class="box-card" style="height: calc(100vh - 330px)">
          <template #header>
            <div class="clearfix">
              <span>进行中的学习</span>
            </div>
          </template>
          <div>
            <t-empty
              v-if="total == 0"
              :image-size="200"
              description="暂无数据"
            ></t-empty>
            <div v-else>
              <CustomTable rowKey="id"
                :data="records"
                size="small"
                height="calc(100vh - 330px)"
                stripe
                style="width: 100%"
                @sort-change="tableSort($event)">
                <TableColumn
                  prop="course.courseName"
                  label="课程名称"
                  ellipsis></TableColumn>
                <TableColumn
                  prop="studyComp"
                  label="课程进度"
                  sortable="custom"
                  width="100px"
                  ellipsis>
                  <template #default="scope">
                    <t-tag
                      effect="plain"
                      size="small"
                      :theme="scope.row.studyComp == 1 ? 'success' : 'danger'"
                    >
                      {{
                        scope.row.studyComp == 1 ? '已完成' : '未学完'
                      }}</t-tag
                    >
                  </template>
                </TableColumn>
                <TableColumn
                  prop="evalDate"
                  label="评价"
                  sortable="custom"
                  width="100px"
                  ellipsis>
                  <template #default="scope">
                    <t-tag
                      effect="plain"
                      size="small"
                      :theme="
                        scope.row.ifEval == 0
                          ? 'success'
                          : scope.row.evalComp == 1
                            ? 'success'
                            : 'danger'
                      "
                    >
                      {{
                        scope.row.ifEval == 0
                          ? '无需评价'
                          : scope.row.evalComp == 1
                            ? '已评价'
                            : '未评价'
                      }}</t-tag
                    >
                  </template>
                </TableColumn>
                <TableColumn
                  prop="examComp"
                  label="考试"
                  sortable="custom"
                  width="100px"
                  ellipsis>
                  <template #default="scope">
                    <t-tag
                      effect="plain"
                      size="small"
                      :theme="
                        scope.row.ifExam == 0
                          ? 'success'
                          : scope.row.examComp == 1
                            ? 'success'
                            : 'danger'
                      "
                    >
                      {{
                        scope.row.ifExam == 0
                          ? '免考'
                          : scope.row.examComp == 1
                            ? '已完成'
                            : '未完成'
                      }}</t-tag
                    >
                  </template>
                </TableColumn>
                <TableColumn label="查看" width="80px">
                  <template #default="scope">
                    <t-button
                      size="small"
                      theme="primary"
                      @click="gotoCourseView(scope.row.courseId)"
                      >查看</t-button
                    >
                  </template>
                </TableColumn>
              </CustomTable>
            </div>
          </div>
        </t-card>
      </t-col>
      <t-col :span="4">
        <t-card class="box-card" style="height: calc(100vh - 330px)">
          <template #header>
            <div class="clearfix">
              <span>学习计划</span>
            </div>
          </template>
          <t-empty :image-size="200" description="暂未启用"></t-empty>
        </t-card>
      </t-col>
    </t-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { MessagePlugin } from 'tdesign-vue-next'
import { httpInstance } from '@/utils/request'
import { useUserStore } from '@/stores'

const router = useRouter()
const userStore = useUserStore()

const fsURL = import.meta.env.VITE_FILE_BASE_URL
const myPoint = ref(null)
const queryInfo = ref({
  orderType: ' desc',
  order: '',
  coursePass: 2,
  pageSize: 100,
  pageNum: 1
})
const records = ref([])
const total = ref(0)

const getStudyLog = async () => {
  const res = await httpInstance({
    url: 'college/study/my/record',
    method: 'get',
    params: queryInfo.value
  })
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  records.value = res.data.list
  total.value = res.data.total
}

const gotoCourseView = (courseId) => {
  const routeData = router.resolve({
    path: '/college/course/view',
    query: { courseId: courseId }
  })
  window.open(routeData.href, '_blank')
}

const tableSort = (data) => {
  if (!data.descending) queryInfo.value.orderType = ' asc '
  else if (data.descending) queryInfo.value.orderType = ' desc '
  queryInfo.value.order = data.sortBy
  getStudyLog()
}

onMounted(() => {
  myPoint.value = JSON.parse(window.localStorage.getItem('point'))
  getStudyLog()
})
</script>

<style lang="less" scoped></style>
