<template>
  <div class="container">
    <t-row :gutter="20">
      <t-col :span="3">
        <t-card class="top-card" style="background-color: #283138">
          <div
            v-for="item in tree"
            :key="item.id"
            style="border-top: 1px dotted #fff"
          >
            <div
              class="nvl"
              @mouseenter="menuHover(true, item)"
              @mouseleave="nvlDetailShowFlag = false"
            >
              <div class="nvl-title" @click="goCourseLib(item.id)">
                {{ item.name }}
              </div>
              <div class="nvl-label text-trim" v-if="item.children != null">
                <span
                  class="text-trim"
                  v-for="level2 in item.children"
                  :key="level2.id"
                  @click="goCourseLib(level2.id)"
                  >{{ level2.name }}</span
                >
              </div>
              <div class="nvl-label text-trim" v-else>
                <span class="text-trim" @click="goCourseLib(item.id)"
                  >暂无下级分类</span
                >
              </div>
            </div>
          </div>
          <div style="float: right; font-size: 10px; line-height: 20px">
            <t-link
              style="color: #fff"
              :underline="false"
              @click="goCourseLib(null)"
              >查看更多<ArrowRightIcon />
            ></t-link>
          </div>
          <div
            class="nvl-detail"
            v-show="nvlDetailShowFlag"
            @mouseenter="nvlDetailShowFlag = true"
            @mouseleave="nvlDetailShowFlag = !nvlDetailShowFlag"
            v-if="curTreeDetail != null"
          >
            <h3>
              <div class="nvl-title-color"></div>
              {{ curTreeDetail.name }}
            </h3>
            <t-row
              class="nvl-level2"
              v-for="level2 in curTreeDetail.children"
              :key="level2.id"
            >
              <t-col :span="2"
                ><span
                  class="title text-trim"
                  @click="goCourseLib(level2.id)"
                  >{{ level2.name }}</span
                ></t-col
              >
              <t-col :span="10">
                <div class="level3">
                  <span
                    style="display: inline-block"
                    v-for="level3 in level2.children"
                    :key="level3.id"
                    @click="goCourseLib(level3.id)"
                    ><t-divider direction="vertical"></t-divider
                    >{{ level3.name }}</span
                  >
                </div>
              </t-col>
            </t-row>
          </div>
        </t-card>
      </t-col>
      <t-col :span="7">
        <t-swiper :height="340">
          <t-swiper-item v-for="item in adCourse" :key="item.courseId">
            <div style="cursor: pointer" @click="gotoCourseView(item.courseId)">
              <img
                :src="fsURL + 'upload/getFile/college-cover/' + item.coverImg"
                width="100%"
              />
            </div>
          </t-swiper-item>
        </t-swiper>
      </t-col>
      <t-col :span="3">
        <t-card class="top-card personal">
          <div class="user">
            <div class="user-info">
              <t-avatar
                size="40px"
                fit="fill"
                :src="fsURL + userStore.avatar"
              ></t-avatar>
              <div class="user-name">
                <span>{{ userStore.ploName }} | {{ userStore.ploNum }}</span>
                <hr />
                <span
                  >{{ userStore.deptName }} | {{ userStore.groupName }}</span
                >
              </div>
            </div>
          </div>
          <div class="panel-title">个人学习面板</div>
          <div class="personal-panel">
            <ul v-if="myPoint">
              <li @click="router.push({ path: '/college/my' })">
                <CalendarIcon />
                <div class="panel-info">
                  <span>{{ myPoint.studyNum }}</span>
                  <hr />
                  <span>学习计划</span>
                </div>
              </li>
              <li @click="router.push({ path: '/college/my/study-record' })">
                <ChartLineIcon />
                <div class="panel-info">
                  <span>{{ myPoint.signNum }}</span>
                  <hr />
                  <span>学习记录</span>
                </div>
              </li>
              <li @click="router.push({ path: '/college/my/point-log' })">
                <TicketIcon />
                <div class="panel-info">
                  <span>{{ myPoint.coin }}</span>
                  <hr />
                  <span>学习金币</span>
                </div>
              </li>
              <li @click="router.push({ path: '/college/my/hour-log' })">
                <TimeIcon />
                <div class="panel-info">
                  <span>{{ myPoint.hours }}</span>
                  <hr />
                  <span>学习时长</span>
                </div>
              </li>
            </ul>
          </div>
        </t-card>
      </t-col>
    </t-row>
    <CourseCommand></CourseCommand>
    <TeacherList></TeacherList>
    <UserList :userList="userList"></UserList>
    <div
      style="
        display: flex;
        justify-content: center;
        color: #999;
        font-size: 14px;
        width: 100%;
        text-align: center;
        margin-top: 20px;
      "
    >
      本网站由武汉营运中心业务管理室维护<br />CopyRight© 2020-{{
        new Date().getFullYear()
      }}, All Right Reserved
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { MessagePlugin } from 'tdesign-vue-next'
import { ArrowRightIcon, CalendarIcon, ChartLineIcon, TicketIcon, TimeIcon } from 'tdesign-icons-vue-next'
import { httpInstance } from '@/utils/request'
import { useUserStore } from '@/stores'
import TeacherList from '../teacher/index.vue'
import UserList from '../user/index.vue'
import CourseCommand from '../course/command.vue'

const router = useRouter()
const userStore = useUserStore()

const fsURL = import.meta.env.VITE_FILE_BASE_URL
const nvlDetailShowFlag = ref(false)
const tree = ref([])
const curTreeDetail = ref({})
const adCourse = ref([])
const userList = ref([])
const myPoint = ref(null)

const getTree = async () => {
  const res = await httpInstance.get('college/course/type/tree')
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  tree.value = res.data.slice(0, 4)
}

const getRecommend = async () => {
  const res = await httpInstance.get('college/course/recommend')
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  adCourse.value = res.data.filter((e) => e.topFlag !== 0)
}

const goCourseLib = (id) => {
  router.push({ path: '/college/library', query: { id: id } })
}

const menuHover = (flag, item) => {
  nvlDetailShowFlag.value = flag
  curTreeDetail.value = item
}

const getUserPoint = async () => {
  const res = await httpInstance.get('/college/point')
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  userList.value = res.rank
  myPoint.value = res.point
  window.localStorage.setItem('point', JSON.stringify(res.point))
}

const gotoCourseView = (courseId) => {
  const routeData = router.resolve({
    name: 'college-course-view',
    params: { courseId: courseId }
  })
  window.open(routeData.href, '_blank')
}

onMounted(() => {
  getTree()
  getRecommend()
  getUserPoint()
})
</script>

<style lang="less" scoped>
.container {
  padding-left: calc((100vw - 1240px) / 2);
  max-width: 1200px;
}
.top-card {
  height: 340px;
}
.personal {
  .user {
    height: 110px;
    width: 100%;
background-color: #283138;
    vertical-align: top;
    text-align: center;
    .user-info {
      font-size: 12px;
      color: #fff;
      line-height: 120px;
      .user-name {
        display: inline-block;
        text-align: left;
        margin-left: 10px;
        line-height: 1.3;
      }
    }
  }
  .panel-title {
    width: 150px;
    height: 40px;
background-color: #9b2b23;
    border-radius: 8px 8px 0px 0px;
    line-height: 40px;
    color: #fff;
    font-weight: blod;
    margin: -20px auto;
    text-align: center;
  }
  .personal-panel {
    margin-top: 40px;
    width: 100%;
    text-align: center;
    ul {
      width: 100%;
      list-style: none;
      padding-left: 0;
      li:nth-child(1) {
background-color: #e9f8db;
        .t-icon {
          color: #79ce2e;
        }
      }
      li:nth-child(2) {
background-color: #d9ecff;
        .t-icon {
          color: #409eff;
        }
      }
      li:nth-child(3) {
background-color: #e0fff7;
        .t-icon {
          color: #60debc;
        }
      }
      li:nth-child(4) {
background-color: #fef2d3;
        .t-icon {
          color: #ff8402;
        }
      }
      li {
        cursor: pointer;
        height: 45px;
        width: 100px;
        margin: 0 0 15px 10px;
        float: left;
        .t-icon {
          font-size: 25px;
          float: left;
          margin: 8px 10px 0 5px;
        }
        .panel-info {
          padding-top: 3px;
          font-size: 10px;
          line-height: 1;
          float: left;
          color: #666;
        }
      }
    }
  }
}
.text-trim {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.nvl-detail {
  height: 340px;
  width: 720px;
background-color: #fff;
  overflow: auto;
  color: #000;
  position: absolute;
  left: 242px;
  top: 0;
  z-index: 10;
  .nvl-title-color {
    width: 5px;
    border: 10px solid rgb(154 27 27);
    display: inline;
    margin: 0 10px 0 10px;
  }
  .nvl-level2 {
    text-align: left;
    margin-left: 30px;
    margin-top: 10px;
    cursor: pointer;
    .title {
      width: 90px;
      display: inline-block;
    }
    .level3 {
      margin-left: 20px;
      display: inline-block;
      font-size: 14px;
      color: #999;
    }
  }
}
.nvl {
  color: #fff;
  height: 60px;
  margin-bottom: 10px;
  cursor: pointer;
  padding: 7px 10px 0 10px;
  .nvl-title {
    padding-top: 10px;
  }
  .nvl-label {
    font-size: 10px;
    width: 100%;
    margin-top: 10px;
    span {
      margin-right: 10px;
      color: #ccc;
      display: inline-block;
    }
  }
}
:deep(.t-card__body) {
  padding: 0;
}
</style>
