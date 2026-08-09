<template>
  <div>
    <t-card class="academy-card profile-card">
      <div class="profile-avatar">
        <t-avatar
          shape="round"
          size="96px"
          fit="fill"
          :src="fsURL + userStore.avatar"
        ></t-avatar>
      </div>
      <div class="profile-main">
        <div class="profile-name">{{ userStore.ploName }} · {{ userStore.ploNum }}</div>
        <div class="profile-dept">科室：{{ userStore.deptName }}　组别：{{ userStore.groupName }}</div>
        <div class="profile-stats" v-if="myPoint">
          <div class="profile-stat">
            <span class="stat-num">{{ myPoint.coin }}</span>
            <span class="stat-label">金币</span>
          </div>
          <div class="profile-stat">
            <span class="stat-num">{{ myPoint.signNum }}</span>
            <span class="stat-label">培训课程</span>
          </div>
          <div class="profile-stat">
            <span class="stat-num">{{ myPoint.hours }}</span>
            <span class="stat-label">授课课时</span>
          </div>
          <div class="profile-stat">
            <span class="stat-num">{{ myPoint.point }}</span>
            <span class="stat-label">积分</span>
          </div>
        </div>
      </div>
    </t-card>
    <t-row :gutter="10" style="margin-top: 10px; width: 100%">
      <t-col :span="8">
        <t-card class="academy-card" style="height: calc(100vh - 320px)">
          <h3 class="academy-section-title">进行中的学习</h3>
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
                      variant="light"
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
                      variant="light"
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
                      variant="light"
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
        <t-card class="academy-card" style="height: calc(100vh - 320px)">
          <h3 class="academy-section-title">学习计划</h3>
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

// 展示类文件统一走 HTTPS 文件管理地址，避免混合内容被浏览器拦截
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
  const routeData = router.resolve({ name: 'college-course-view', params: { courseId: courseId } })
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

<style lang="less" scoped>
.profile-card {
  display: flex;
  align-items: center;
  gap: 24px;
  padding: 20px 24px;
  background: linear-gradient(120deg, #10273e 0%, var(--academy-navy) 75%);
  border: none;
}

.profile-avatar {
  flex: none;
}

.profile-main {
  flex: 1;
  min-width: 0;
}

.profile-name {
  font-size: 20px;
  font-weight: 600;
  color: #fff;
}

.profile-dept {
  font-size: 13px;
  color: rgba(255, 255, 255, 0.68);
  margin: 6px 0 14px;
}

.profile-stats {
  display: flex;
  gap: 36px;
}

.profile-stat {
  display: flex;
  flex-direction: column;
  gap: 2px;

  .stat-num {
    font-size: 22px;
    font-weight: 700;
    color: var(--academy-gold-2);
  }

  .stat-label {
    font-size: 12px;
    color: rgba(255, 255, 255, 0.68);
  }
}
</style>
