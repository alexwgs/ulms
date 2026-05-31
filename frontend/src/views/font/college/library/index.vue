<template>
  <div style="padding-left: calc((100vw - 1240px) / 2); max-width: 1200px">
    <t-row :gutter="20">
      <t-col :span="3">
        <t-card class="box-card">
          <template #header>
            <div class="clearfix">
              <span>全部课程分类</span>
            </div>
          </template>
          <div style="margin-bottom: 10px">
            <t-input placeholder="输入关键字进行过滤" size="small" v-model="filterText"></t-input>
          </div>
          <div style="height: calc(100vh - 255px); overflow: auto">
            <t-tree style="margin-top: 5px" :data="tree" @click="courseType" :keys="{ value: 'id', label: 'name', children: 'children' }" expand-all activable
              v-model:actived="activeValue" :filter="filterNode" ref="treeRef"></t-tree>
          </div>
        </t-card>
      </t-col>
      <t-col :span="9">
        <t-card class="box-card">
          <template #header>
            <div class="clearfix">
              <span>课程库</span>
              <div style="float: right; width: 400px">
                <t-input placeholder="当前目录下搜索课程" size="small" v-model="queryInfo.query">
                  <template #append>
                    <t-button @click="getCourse">
                      <t-icon>
                        <Search />
                      </t-icon>
                    </t-button>
                  </template>
                </t-input>
              </div>
            </div>
          </template>
          <div class="filter">
            <div class="left" style="line-height: 40px">
              当前筛选：<t-tag v-if="currentNode == null">全部</t-tag><t-tag v-else closable theme="danger" effect="plain"
                @close="() => courseType(null)">{{ currentNode.name }}</t-tag>
            </div>
            <div class="right">
              <t-link class="filter-link" :underline="false" @click="sortCourse('courseScore')">评分<t-icon
                  v-if="queryInfo.order === 'courseScore'">
                  <ArrowDown v-if="queryInfo.orderType === 'desc'" />
                  <ArrowUp v-else />
                </t-icon><t-icon v-else>
                  <Sort />
                </t-icon></t-link>
              <t-link class="filter-link" :underline="false" @click="sortCourse('handleDate')">更新时间<t-icon
                  v-if="queryInfo.order === 'handleDate'">
                  <ArrowDown v-if="queryInfo.orderType === 'desc'" />
                  <ArrowUp v-else />
                </t-icon><t-icon v-else>
                  <Sort />
                </t-icon></t-link>
              <t-link class="filter-link" :underline="false" @click="sortCourse('studyNum')">学习人数<t-icon
                  v-if="queryInfo.order === 'studyNum'">
                  <ArrowDown v-if="queryInfo.orderType === 'desc'" />
                  <ArrowUp v-else />
                </t-icon><t-icon v-else>
                  <Sort />
                </t-icon></t-link>
              <t-link class="filter-link" :underline="false" @click="sortCourse('courseName')">课程名称<t-icon
                  v-if="queryInfo.order === 'courseName'">
                  <ArrowDown v-if="queryInfo.orderType === 'desc'" />
                  <ArrowUp v-else />
                </t-icon><t-icon v-else>
                  <Sort />
                </t-icon></t-link>
              <t-link class="filter-link" :underline="false" @click="sortCourse('lecturer')">授课讲师<t-icon
                  v-if="queryInfo.order === 'lecturer'">
                  <ArrowDown v-if="queryInfo.orderType === 'desc'" />
                  <ArrowUp v-else />
                </t-icon><t-icon v-else>
                  <Sort />
                </t-icon></t-link>
            </div>
          </div>
          <t-row :gutter="15" style="height: calc(100vh - 300px); overflow: auto">
            <t-col :span="4" v-for="item in courses" :key="item.courseId" style="margin-top: 10px">
              <t-card @click="gotoCourseView(item.courseId)" :body-style="{ padding: '0px' }" :shadow="true"
                class="course-panel" :title="item.courseName">
                <img :src="fsURL + 'upload/getFile/college-cover/' + item.coverImg" width="100%" />
                <div class="info">
                  <div style="
                      position: absolute;
                      margin-top: -181px;
                      margin-left: -20px;
                    ">
                    <t-tag :theme="item.studyType == 2 ? 'danger' : 'primary'" size="small" effect="dark">{{
                      item.studyType == 2 ? '任务课程' : '常规课程'
                      }}</t-tag>
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
                        <t-rate v-if="item.courseScore > 0" v-model="item.courseScore" disabled text-color="#ff9900"
                          score-template="{value}"></t-rate><span v-else>暂无评分</span>
                      </div>
                      <div class="right line-info">
                        {{ item.studyNum }}人学习
                      </div>
                    </div>
                  </div>
                </div>
              </t-card>
            </t-col>
          </t-row>
          <t-pagination @page-size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current="queryInfo.pageNum" :page-size-options="[15, 30, 60, 120]" :page-size="queryInfo.pageSize"
 :total="total">
          </t-pagination>
        </t-card>
      </t-col>
    </t-row>
  </div>
</template>

<script setup>
import { ref, reactive, watch, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { MessagePlugin } from 'tdesign-vue-next'
import { httpInstance } from '@/utils/request'
import { SearchIcon, ArrowDownIcon, ArrowUpIcon, SwapIcon } from 'tdesign-icons-vue-next'

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
const activeValue = ref([])

const getTree = () => {
  return new Promise((resolve, reject) => {
    httpInstance
      .get('college/course/type/tree')
      .then((res) => {
        if (res.code !== 200) {
          MessagePlugin.error(res.msg)
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

const filterNode = (node) => {
  if (!filterText.value) return true
  return node.data.name.indexOf(filterText.value) !== -1
}

const getCourse = async () => {
  const res = await httpInstance.get('college/course', {
    params: queryInfo
  })
  if (res.code !== 200) {
    MessagePlugin.error(res.msg)
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

const courseType = (context) => {
  // Extract data from TDesign @click context, or use raw value from programmatic calls
  let data
  if (context && context.node) {
    data = context.node.data
  } else {
    data = context
  }

  if (data != null && data !== undefined) {
    currentNode.value = data
    queryInfo.courseType = data.id
  } else if (data === null) {
    currentNode.value = null
    queryInfo.courseType = null
    activeValue.value = []
  } else if (data === undefined) {
    if (queryInfo.courseType !== null) {
      activeValue.value = [queryInfo.courseType]
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
  treeRef.value?.refresh()
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
