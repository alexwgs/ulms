<template>
  <div>
    <t-row :gutter="20">
      <t-col :span="3">
        <t-card class="academy-card lib-cat-card">
          <h3 class="academy-section-title">全部课程分类</h3>
          <div class="cat-filter">
            <t-input placeholder="输入关键字进行过滤" size="small" v-model="filterText" :clearable="true"></t-input>
          </div>
          <div class="cat-tree">
            <t-tree style="margin-top: 5px" :data="tree" @click="courseType" :keys="{ value: 'id', label: 'name', children: 'children' }" expand-level="1" activable
              :filter="filterText ? filterNode : undefined"
              v-model:actived="activeValue" ref="treeRef"></t-tree>
          </div>
        </t-card>
      </t-col>
      <t-col :span="9">
        <t-card class="academy-card">
          <div class="lib-head">
            <h3 class="academy-section-title">课程库</h3>
            <div class="lib-search">
                <t-input-adornment>
                  <template #append>
                    <t-button @click="getCourse">
                      <t-icon>
                        <SearchIcon />
                      </t-icon>
                    </t-button>
                  </template>
                  <t-input placeholder="当前目录下搜索课程" size="small" v-model="queryInfo.query"></t-input>
                </t-input-adornment>
            </div>
          </div>
          <div class="filter">
            <div class="left" style="line-height: 40px">
              当前筛选：<t-tag v-if="currentNode == null" variant="light">全部</t-tag><t-tag v-else closable theme="danger" variant="light"
                @close="() => courseType(null)">{{ currentNode.name }}</t-tag>
            </div>
            <div class="right">
              <t-link class="filter-link" :underline="false" @click="sortCourse('courseScore')">评分<t-icon
                  v-if="queryInfo.order === 'courseScore'">
                  <ArrowDownIcon v-if="queryInfo.orderType === 'desc'" />
                  <ArrowUpIcon v-else />
                </t-icon><t-icon v-else>
                  <MoveIcon />
                </t-icon></t-link>
              <t-link class="filter-link" :underline="false" @click="sortCourse('handleDate')">更新时间<t-icon
                  v-if="queryInfo.order === 'handleDate'">
                  <ArrowDownIcon v-if="queryInfo.orderType === 'desc'" />
                  <ArrowUpIcon v-else />
                </t-icon><t-icon v-else>
                  <MoveIcon />
                </t-icon></t-link>
              <t-link class="filter-link" :underline="false" @click="sortCourse('studyNum')">学习人数<t-icon
                  v-if="queryInfo.order === 'studyNum'">
                  <ArrowDownIcon v-if="queryInfo.orderType === 'desc'" />
                  <ArrowUpIcon v-else />
                </t-icon><t-icon v-else>
                  <MoveIcon />
                </t-icon></t-link>
              <t-link class="filter-link" :underline="false" @click="sortCourse('courseName')">课程名称<t-icon
                  v-if="queryInfo.order === 'courseName'">
                  <ArrowDownIcon v-if="queryInfo.orderType === 'desc'" />
                  <ArrowUpIcon v-else />
                </t-icon><t-icon v-else>
                  <MoveIcon />
                </t-icon></t-link>
              <t-link class="filter-link" :underline="false" @click="sortCourse('lecturer')">授课讲师<t-icon
                  v-if="queryInfo.order === 'lecturer'">
                  <ArrowDownIcon v-if="queryInfo.orderType === 'desc'" />
                  <ArrowUpIcon v-else />
                </t-icon><t-icon v-else>
                  <MoveIcon />
                </t-icon></t-link>
            </div>
          </div>
          <t-empty
            v-if="courses.length === 0"
            class="academy-empty"
            description="当前分类下暂无课程，换个分类看看吧"
            :image-size="120"
          ></t-empty>
          <t-row v-else :gutter="15" style="height: calc(100vh - 300px); overflow: auto">
            <t-col :span="4" v-for="item in courses" :key="item.courseId" style="margin-top: 10px">
              <div @click="gotoCourseView(item.courseId)" class="academy-course-card">
                <div class="cover-wrap">
                  <img
                    :src="fsURL + 'upload/getFile/college-cover/' + item.coverImg"
                    width="100%"
                    @error="hideBrokenImage"
                  />
                  <t-tag :theme="item.studyType == 2 ? 'danger' : 'primary'" size="small" variant="light"
                    class="cover-tag">{{ item.studyType == 2 ? '任务课程' : '常规课程' }}</t-tag>
                </div>
                <div class="info">
                  <div class="course-name" :title="item.courseName">{{ item.courseName }}</div>
                  <div class="lib-course-info">
                    <div class="line">
                      <span class="left">{{ item.lecturer }}</span>
                      <span class="right">
                        {{ item.handleDate.substring(0, 10) }}
                      </span>
                    </div>
                    <div class="line">
                      <span class="left">
                        {{ item.teachMethod ? '线上授课' : '线下授课' }}
                      </span>
                      <span class="right">
                        授课时长：{{ item.hours }}h
                      </span>
                    </div>
                    <div class="line">
                      <span class="left">
                        <t-rate v-if="item.courseScore > 0" v-model="item.courseScore" disabled text-color="#ff9900"
                          score-template="{value}"></t-rate><span v-else>暂无评分</span>
                      </span>
                      <span class="right">
                        {{ item.studyNum }}人学习
                      </span>
                    </div>
                  </div>
                </div>
              </div>
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
import { SearchIcon, ArrowDownIcon, ArrowUpIcon, SwapIcon, MoveIcon } from 'tdesign-icons-vue-next'

const route = useRoute()
const router = useRouter()

const fsURL = import.meta.env.VITE_FILE_BASE_URL

// 封面缺失时隐藏破图图标，避免影响卡片布局
const hideBrokenImage = (e) => {
  e.target.style.display = 'none'
}

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
.lib-cat-card {
  height: 100%;
}

.cat-filter {
  margin-bottom: 12px;
}

.cat-tree {
  height: calc(100vh - 360px);
  min-height: 300px;
  overflow: auto;

  :deep(.t-tree__item) {
    padding: 5px 4px;
  }

  :deep(.t-tree__label) {
    font-size: 13px;
    color: var(--academy-ink);
  }

  :deep(.t-tree__item.t-is-active > .t-tree__label) {
    color: var(--academy-navy);
    font-weight: 600;
  }
}

.lib-head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
}

.lib-search {
  width: 380px;
}

.filter {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 14px;
  margin: 4px 0 12px;
  background: var(--academy-bg);
  border-radius: 8px;
  font-size: 13px;

  .left {
    color: var(--academy-muted);
  }

  .filter-link {
    color: var(--academy-ink);
    padding-left: 18px;

    &:hover {
      color: var(--academy-navy);
    }
  }
}

.lib-course-info {
  .line {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 12px;
    color: var(--academy-muted);
    line-height: 22px;
    overflow: hidden;
  }

  .left {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    margin-right: 8px;
  }

  .right {
    flex: none;
  }
}
</style>
