<template>
  <div style="padding-left: calc((100vw - 1240px) / 2); max-width: 1200px">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card class="box-card">
          <template #header>
            <div class="clearfix">
              <span>全部课程分类</span>
            </div>
          </template>
          <div style="margin-bottom: 10px">
            <el-input placeholder="输入关键字进行过滤" size="small" v-model="filterText"></el-input>
          </div>
          <div style="height: calc(100vh - 255px); overflow: auto">
            <el-tree style="margin-top: 5px" :data="tree" @node-click="courseType" node-key="id"
              :props="{ children: 'children', label: 'name' }" default-expand-all highlight-current
              :filter-node-method="filterNode" ref="treeRef"></el-tree>
          </div>
        </el-card>
      </el-col>
      <el-col :span="18">
        <el-card class="box-card">
          <template #header>
            <div class="clearfix">
              <span>课程库</span>
              <div style="float: right; width: 400px">
                <el-input placeholder="当前目录下搜索课程" size="small" v-model="queryInfo.query">
                  <template #append>
                    <el-button @click="getCourse">
                      <el-icon>
                        <Search />
                      </el-icon>
                    </el-button>
                  </template>
                </el-input>
              </div>
            </div>
          </template>
          <div class="filter">
            <div class="left" style="line-height: 40px">
              当前筛选：<el-tag v-if="currentNode == null">全部</el-tag><el-tag v-else closable type="danger" effect="plain"
                @close="() => courseType(null)">{{ currentNode.name }}</el-tag>
            </div>
            <div class="right">
              <el-link class="filter-link" underline="never" @click="sortCourse('courseScore')">评分<el-icon
                  v-if="queryInfo.order === 'courseScore'" class="el-icon--right">
                  <ArrowDown v-if="queryInfo.orderType === 'desc'" />
                  <ArrowUp v-else />
                </el-icon><el-icon v-else class="el-icon--right">
                  <Sort />
                </el-icon></el-link>
              <el-link class="filter-link" underline="never" @click="sortCourse('handleDate')">更新时间<el-icon
                  v-if="queryInfo.order === 'handleDate'" class="el-icon--right">
                  <ArrowDown v-if="queryInfo.orderType === 'desc'" />
                  <ArrowUp v-else />
                </el-icon><el-icon v-else class="el-icon--right">
                  <Sort />
                </el-icon></el-link>
              <el-link class="filter-link" underline="never" @click="sortCourse('studyNum')">学习人数<el-icon
                  v-if="queryInfo.order === 'studyNum'" class="el-icon--right">
                  <ArrowDown v-if="queryInfo.orderType === 'desc'" />
                  <ArrowUp v-else />
                </el-icon><el-icon v-else class="el-icon--right">
                  <Sort />
                </el-icon></el-link>
              <el-link class="filter-link" underline="never" @click="sortCourse('courseName')">课程名称<el-icon
                  v-if="queryInfo.order === 'courseName'" class="el-icon--right">
                  <ArrowDown v-if="queryInfo.orderType === 'desc'" />
                  <ArrowUp v-else />
                </el-icon><el-icon v-else class="el-icon--right">
                  <Sort />
                </el-icon></el-link>
              <el-link class="filter-link" underline="never" @click="sortCourse('lecturer')">授课讲师<el-icon
                  v-if="queryInfo.order === 'lecturer'" class="el-icon--right">
                  <ArrowDown v-if="queryInfo.orderType === 'desc'" />
                  <ArrowUp v-else />
                </el-icon><el-icon v-else class="el-icon--right">
                  <Sort />
                </el-icon></el-link>
            </div>
          </div>
          <el-row :gutter="15" style="height: calc(100vh - 300px); overflow: auto">
            <el-col :span="8" v-for="item in courses" :key="item.courseId" style="margin-top: 10px">
              <el-card @click="gotoCourseView(item.courseId)" :body-style="{ padding: '0px' }" shadow="always"
                class="course-panel" :title="item.courseName">
                <img :src="fsURL + 'upload/getFile/college-cover/' + item.coverImg" width="100%" />
                <div class="info">
                  <div style="
                      position: absolute;
                      margin-top: -181px;
                      margin-left: -20px;
                    ">
                    <el-tag :type="item.studyType == 2 ? 'danger' : 'primary'" size="small" effect="dark">{{
                      item.studyType == 2 ? '任务课程' : '常规课程'
                      }}</el-tag>
                  </div>
                  <div class="text-trim">
                    {{ item.courseName }}
                  </div>
                  <div class="course-info">
                    <div class="line">
                      <div class="line-info left">{{ item.lecturer }}</div>
                      <div class="right line-info">
                        {{ item.handleDate.substring(0, 10) }}
                      </div>
                    </div>
                    <div class="line">
                      <div class="line-info left">
                        {{ item.teachMethod ? '线上授课' : '线下授课' }}
                      </div>
                      <div class="right line-info">
                        授课时长：{{ item.hours }}h
                      </div>
                    </div>
                    <div class="line">
                      <div class="line-info left">
                        <el-rate v-if="item.courseScore > 0" v-model="item.courseScore" disabled text-color="#ff9900"
                          score-template="{value}"></el-rate><span v-else>暂无评分</span>
                      </div>
                      <div class="right line-info">
                        {{ item.studyNum }}人学习
                      </div>
                    </div>
                  </div>
                </div>
              </el-card>
            </el-col>
          </el-row>
          <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page="queryInfo.pageNum" :page-sizes="[15, 30, 60, 120]" :page-size="queryInfo.pageSize"
            layout="total, sizes, prev, pager, next, jumper" :total="total">
          </el-pagination>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, reactive, watch, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { httpInstance } from '@/utils/request'
import { Search, ArrowDown, ArrowUp, Sort } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()

const fsURL = import.meta.env.VITE_FILE_MANAGE_BASE

const queryInfo = reactive({
  orderType: 'desc',
  order: 'handleDate',
  queryType: 'courseName',
  query: '',
  courseType: null,
  pageSize: 15,
  pageNum: 1
})

const courses = ref([])
const tree = ref([])
const filterText = ref('')
const currentNode = ref(null)
const total = ref(0)
const treeRef = ref(null)

const getTree = () => {
  return new Promise((resolve, reject) => {
    httpInstance
      .get('college/course/type/tree')
      .then((res) => {
        if (res.code !== 200) {
          ElMessage.error(res.msg)
          return reject(res.msg)
        }
        tree.value = res.data
        resolve()
      })
      .catch((error) => {
        reject(error)
      })
  }).then(() => {
    courseType()
  })
}

const filterNode = (value, data) => {
  if (!value) return true
  return data.name.indexOf(value) !== -1
}

const getCourse = async () => {
  const res = await httpInstance.get('college/course', {
    params: queryInfo
  })
  if (res.code !== 200) {
    ElMessage.error(res.msg)
    return
  }
  courses.value = res.data.list
  total.value = res.data.total
}

const sortCourse = (order) => {
  queryInfo.order = order
  if (queryInfo.orderType === 'asc') queryInfo.orderType = 'desc'
  else queryInfo.orderType = 'asc'
  getCourse()
}

const courseType = (data) => {
  if (data != null && data !== undefined) {
    currentNode.value = data
    queryInfo.courseType = data.id
  } else if (data === null) {
    currentNode.value = null
    queryInfo.courseType = null
    treeRef.value?.setCurrentKey(queryInfo.courseType)
  } else if (data === undefined) {
    if (queryInfo.courseType !== null) {
      treeRef.value?.setCurrentKey(queryInfo.courseType)
      currentNode.value = treeRef.value?.getCurrentNode()
    }
  }
  getCourse()
}

const gotoCourseView = (courseId) => {
  const routeData = router.resolve({
    name: 'college-course-view',  // 使用路由名称，避免硬编码路径
    params: { courseId: courseId }
  })
  // 在新窗口打开
  window.open(routeData.href, '_blank')

}

const handleSizeChange = (pageSize) => {
  queryInfo.pageSize = pageSize
  getCourse()
}

const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  getCourse()
}

watch(filterText, (val) => {
  treeRef.value?.filter(val)
})

onMounted(() => {
  queryInfo.courseType = route.query.id
  getTree()
})
</script>

<style lang="less" scoped>
.text-trim {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.filter {
  width: 100%;
  height: 40px;
  background-color: #f5f5f5;
  border: solid 1px #e4e4e4;
  font-size: 14px;

  .filter-link {
    line-height: 40px;
    padding-left: 20px;
  }
}

.course-panel {
  cursor: pointer;

  img {
    height: 160px;
    width: 100%;
  }

  .info {
    padding: 14px;

    .course-info {
      overflow: hidden;

      .line {
        margin-top: 5px;
        height: 18px;
        display: flex;

        .line-info {
          font-size: 14px;
          width: 50%;
          color: #999;
        }
      }
    }
  }
}

.left {
  float: left;
  text-align: left;
}

.right {
  float: right;
  text-align: right;
}
</style>
