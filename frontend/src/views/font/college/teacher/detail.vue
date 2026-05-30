<template>
  <div style="padding-left: calc((100vw - 1200px) / 2); max-width: 1200px">
    <el-card class="box-card">
      <div style="float: left; width: 320px; margin-bottom: 10px">
        <div
          v-if="teacher.avatar == null"
          style="height: 250px; width: 280px; text-align: center; line-height: 250px"
        >
          暂无照片
        </div>
        <img
          v-else
          width="280px"
          style="min-height: 250px; max-height: 320px; border-radius: 10px"
          :src="fsURL + 'upload/getFile/college-avatar/' + teacher.avatar"
        />
      </div>
      <div>
        <h2>{{ teacher.ploName }}</h2>
        <div v-if="teacher.honor">
          <el-tag
            v-for="(honor, index) in teacher.honor.split('、')"
            :key="index"
            size="small"
            type="danger"
            style="margin-right: 5px"
          >
            {{ honor }}
          </el-tag>
        </div>
        <h4 v-if="teacher.user">{{ teacher.user.deptName }}/{{ teacher.user.groupName }}</h4>
        <div class="teacher-name">
          <el-tag>{{ skillType[teacher.skillType]?.label }}</el-tag>
          <el-tag style="margin-left: 10px">{{ skillName[teacher.skillName]?.label }}</el-tag>
        </div>
        <div class="personal-panel">
          <ul>
            <li v-if="teacher.dataDate">
              <el-icon><Calendar /></el-icon>
              <div class="panel-info">
                <span>{{ teacher.dataDate }}</span>
                <hr />
                <span>身份时间</span>
              </div>
            </li>
            <li>
              <el-icon><Collection /></el-icon>
              <div class="panel-info">
                <span>{{ teacher.courseNum == 0 ? '暂无' : teacher.courseNum }}</span>
                <hr />
                <span>开课数量</span>
              </div>
            </li>
            <li>
              <el-icon><Timer /></el-icon>
              <div class="panel-info">
                <span>{{ teacher.courseHour == 0 ? '暂无' : teacher.courseHour }}</span>
                <hr />
                <span>授课时数</span>
              </div>
            </li>
            <li>
              <el-icon><Star /></el-icon>
              <div class="panel-info">
                <span>{{ teacher.score == 0 ? '暂无' : teacher.score }}</span>
                <hr />
                <span>评价得分</span>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </el-card>
    <el-tabs type="border-card" style="margin-top: 15px; min-height: 500px">
      <el-tab-pane label="个人简介">
        <div style="text-indent: 2em; line-height: 30px" v-text="teacher.introduce"></div>
      </el-tab-pane>
      <el-tab-pane label="授课课程">
        <el-timeline>
          <el-timeline-item
            v-for="(item, index) in courses"
            :key="index"
            :timestamp="item.handleDate"
            placement="top"
          >
            <el-card>
              <el-row :gutter="20">
                <el-col :span="8">
                  <img
                    :src="fsURL + 'upload/getFile/college-cover/' + item.coverImg"
                    width="100%"
                  />
                </el-col>
                <el-col :span="16">
                  <h3>{{ item.courseName }}</h3>
                  <div>授课对象：{{ item.teachObject }}</div>
                  <div v-if="item.evalScore == 0" style="margin-top: 10px">暂无评分</div>
                  <el-rate
                    v-else
                    v-model="item.evalScore"
                    disabled
                    show-score
                    text-color="#ff9900"
                    :score-template="'{value}'"
                    style="margin-top: 10px"
                  />
                  <div style="margin-top: 10px">{{ item.studyNum }}人学习</div>
                  <el-button style="margin-top: 10px" type="primary" @click="gotoCourseView(item.courseId)">
                    去看看
                  </el-button>
                </el-col>
              </el-row>
            </el-card>
          </el-timeline-item>
        </el-timeline>
        <el-pagination
          v-model:current-page="queryInfo.pageNum"
          v-model:page-size="queryInfo.pageSize"
          :page-sizes="[15, 30, 60, 120]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </el-tab-pane>
      <el-tab-pane label="知识">
        <el-empty :image-size="200" />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Calendar, Collection, Timer, Star } from '@element-plus/icons-vue'
import { $get } from '@/utils/request'

const router = useRouter()

const fsURL = import.meta.env.VITE_FILE_MANAGE_BASE_URL

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
    ElMessage.error(res.msg)
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
  router.push({ path: '/college/course/view', query: { courseId: courseId } })
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
.text-trim {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.personal-panel {
  text-align: center;
  ul {
    width: 100%;
    list-style: none;
    padding-left: 0;
    li:nth-child(1) {
      background-color: #e9f8db;
      .el-icon {
        color: #79ce2e;
      }
    }
    li:nth-child(2) {
      background-color: #d9ecff;
      .el-icon {
        color: #409eff;
      }
    }
    li:nth-child(3) {
      background-color: #e0fff7;
      .el-icon {
        color: #60debc;
      }
    }
    li:nth-child(4) {
      background-color: #fef2d3;
      .el-icon {
        color: #ff8402;
      }
    }
    li {
      height: 75px;
      width: 180px;
      border-radius: 10px;
      margin: 0 10px 15px 0;
      float: left;
      .el-icon {
        font-size: 50px;
        float: left;
        margin: 10px 10px 0 5px;
      }
      .panel-info {
        padding-top: 13px;
        font-size: 14px;
        line-height: 1;
        float: left;
        color: #666;
      }
    }
  }
}
</style>
