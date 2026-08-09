<template>
  <div>
    <t-card class="academy-card teacher-detail">
      <div class="teacher-photo">
        <div v-if="teacher.avatar == null" class="teacher-photo-empty">暂无照片</div>
        <img
          v-else
          :src="fsURL + 'upload/getFile/college-avatar/' + teacher.avatar"
          alt=""
        />
      </div>
      <div class="teacher-main">
        <h2 class="teacher-title">{{ teacher.ploName }}</h2>
        <div v-if="teacher.honor">
          <t-tag
            v-for="(honor, index) in teacher.honor.split('、')"
            :key="index"
            size="small"
            theme="danger"
            style="margin-right: 5px"
           variant="light">
            {{ honor }}
          </t-tag>
        </div>
        <h4 class="teacher-dept" v-if="teacher.user">{{ teacher.user.deptName }}/{{ teacher.user.groupName }}</h4>
        <div class="teacher-tags">
          <t-tag variant="light">{{ skillType[teacher.skillType]?.label }}</t-tag>
          <t-tag style="margin-left: 10px" variant="light">{{ skillName[teacher.skillName]?.label }}</t-tag>
        </div>
        <div class="teacher-stats">
            <div class="stat-item" v-if="teacher.dataDate">
              <CalendarIcon />
              <div class="stat-info">
                <span class="stat-num">{{ teacher.dataDate }}</span>
                <span class="stat-label">身份时间</span>
              </div>
            </div>
            <div class="stat-item">
              <BookmarkIcon />
              <div class="stat-info">
                <span class="stat-num">{{ teacher.courseNum == 0 ? '暂无' : teacher.courseNum }}</span>
                <span class="stat-label">开课数量</span>
              </div>
            </div>
            <div class="stat-item">
              <TimeIcon />
              <div class="stat-info">
                <span class="stat-num">{{ teacher.courseHour == 0 ? '暂无' : teacher.courseHour }}</span>
                <span class="stat-label">授课时数</span>
              </div>
            </div>
            <div class="stat-item">
              <StarIcon />
              <div class="stat-info">
                <span class="stat-num">{{ teacher.score == 0 ? '暂无' : teacher.score }}</span>
                <span class="stat-label">评价得分</span>
              </div>
            </div>
        </div>
      </div>
    </t-card>
    <t-tabs theme="card" class="academy-tabs" style="margin-top: 16px; min-height: 500px">
      <t-tab-panel value="intro" label="个人简介">
        <div class="intro-text">{{ cleanDisplayText(teacher.introduce) || '暂无简介' }}</div>
      </t-tab-panel>
      <t-tab-panel value="courses" label="授课课程">
        <t-timeline>
          <t-timeline-item
            v-for="(item, index) in courses"
            :key="index"
            :timestamp="item.handleDate"
            placement="top"
          >
            <t-card class="academy-card">
              <t-row :gutter="20">
                <t-col :span="4">
                  <img
                    :src="fsURL + 'upload/getFile/college-cover/' + item.coverImg"
                    width="100%"
                  />
                </t-col>
                <t-col :span="8">
                  <h3>{{ item.courseName }}</h3>
                  <div>授课对象：{{ item.teachObject }}</div>
                  <div v-if="item.evalScore == 0" style="margin-top: 10px">暂无评分</div>
                  <t-rate
                    v-else
                    v-model="item.evalScore"
                    disabled
                    show-score
                    text-color="#ff9900"
                    :score-template="'{value}'"
                    style="margin-top: 10px"
                  />
                  <div style="margin-top: 10px">{{ item.studyNum }}人学习</div>
                  <t-button style="margin-top: 10px" theme="primary" @click="gotoCourseView(item.courseId)">
                    去看看
                  </t-button>
                </t-col>
              </t-row>
            </t-card>
          </t-timeline-item>
        </t-timeline>
        <t-pagination
          class="academy-pagination"
          v-model="queryInfo.pageNum"
          v-model:page-size="queryInfo.pageSize"
          :page-size-options="[15, 30, 60, 120]"

          :total="total"
          @page-size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </t-tab-panel>
      <t-tab-panel value="knowledge" label="知识">
        <t-empty :image-size="200" />
      </t-tab-panel>
    </t-tabs>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { MessagePlugin } from 'tdesign-vue-next'
import { CalendarIcon, BookmarkIcon, TimeIcon, StarIcon } from 'tdesign-icons-vue-next'
import { $get } from '@/utils/request'
import { cleanDisplayText } from '@/utils/sanitize'

const router = useRouter()

const fsURL = import.meta.env.VITE_FILE_BASE_URL

const teacher = ref({})

const skillType = {
  0: { value: 0, label: '无身份', status: true },
  1: { value: 1, label: '特约', status: true },
  2: { value: 2, label: '预留', status: false },
  3: { value: 3, label: '初级', status: true },
  4: { value: 4, label: '中级', status: true },
  5: { value: 5, label: '高级', status: true }
}

const skillName = {
  1: { value: 1, label: '讲师', status: true },
  2: { value: 2, label: '辅导老师', status: false }
}

const queryInfo = reactive({
  orderType: ' desc',
  order: 'handleDate',
  queryType: 'lecturer',
  query: '',
  pageSize: 15,
  pageNum: 1
})

const courses = ref([])
const total = ref(0)

const getTeacherCourse = async () => {
  queryInfo.query = teacher.value.ploNum
  const res = await $get('college/course', queryInfo)
  if (res.code !== 200) {
    MessagePlugin.error(res.msg)
    return
  }
  courses.value = res.data.list
  total.value = res.data.total
}

const handleSizeChange = (pageSize) => {
  queryInfo.pageSize = pageSize
  getTeacherCourse()
}

const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  getTeacherCourse()
}

const gotoCourseView = (courseId) => {
  router.push({ name: 'college-course-view', params: { courseId: courseId } })
}

onMounted(() => {
  const teacherData = window.localStorage.getItem('teacher')
  if (teacherData) {
    teacher.value = JSON.parse(teacherData)
  }
  getTeacherCourse()
})
</script>

<style lang="less" scoped>
.teacher-detail {
  display: flex;
  gap: 28px;
  padding: 24px;
}

.teacher-photo {
  flex: none;
  width: 280px;
  height: 300px;
  border-radius: 12px;
  overflow: hidden;
  background: var(--academy-bg);

  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    display: block;
  }
}

.teacher-photo-empty {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--academy-muted);
  font-size: 14px;
}

.teacher-main {
  flex: 1;
  min-width: 0;
}

.teacher-title {
  font-size: 26px;
  font-weight: 700;
  color: var(--academy-ink);
  margin: 4px 0 8px;
}

.teacher-dept {
  font-size: 14px;
  font-weight: 500;
  color: var(--academy-muted);
  margin: 0 0 8px;
}

.teacher-tags {
  margin-bottom: 20px;
}

.teacher-stats {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 12px;
}

.stat-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 12px;
  border: 1px solid var(--academy-line);
  border-radius: 10px;
  background: var(--academy-bg);

  .t-icon {
    font-size: 24px;
    color: var(--academy-navy);
  }
}

.stat-item:nth-child(1) .t-icon { color: #4caf50; }
.stat-item:nth-child(2) .t-icon { color: var(--academy-navy-2); }
.stat-item:nth-child(3) .t-icon { color: #2bb3a3; }
.stat-item:nth-child(4) .t-icon { color: #e8923a; }

.stat-info {
  display: flex;
  flex-direction: column;
  gap: 2px;
  min-width: 0;
}

.stat-num {
  font-size: 15px;
  font-weight: 700;
  color: var(--academy-ink);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.stat-label {
  font-size: 12px;
  color: var(--academy-muted);
}

.intro-text {
  text-indent: 2em;
  line-height: 30px;
  color: var(--academy-ink);
  padding: 8px 4px;
}

.academy-tabs :deep(.t-tabs__nav-item.t-is-active) {
  color: var(--academy-navy);
}

.academy-tabs :deep(.t-tabs__bar) {
  background: var(--academy-gold);
}
</style>
