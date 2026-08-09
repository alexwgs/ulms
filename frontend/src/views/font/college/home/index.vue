<template>
  <div class="academy-home">
    <t-row :gutter="20">
      <t-col :span="3">
        <t-card class="academy-panel cat-panel" :body-style="{ padding: '14px 12px' }">
          <div
            v-for="item in tree"
            :key="item.id"
            class="cat-item"
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
          <div class="cat-more">
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
              <span class="nvl-title-color"></span>
              {{ curTreeDetail.name }}
            </h3>
            <div
              class="nvl-level2"
              v-for="level2 in curTreeDetail.children"
              :key="level2.id"
            >
              <span class="title text-trim" @click="goCourseLib(level2.id)">{{
                level2.name
              }}</span>
              <div class="level3">
                <span
                  v-for="level3 in level2.children"
                  :key="level3.id"
                  @click="goCourseLib(level3.id)"
                  >{{ level3.name }}</span
                >
              </div>
            </div>
          </div>
        </t-card>
      </t-col>
      <t-col :span="6">
        <t-swiper :height="340" class="academy-swiper">
          <t-swiper-item v-for="item in adCourse" :key="item.courseId">
            <div
              style="cursor: pointer; height: 340px"
              @click="gotoCourseView(item.courseId)"
            >
              <img
                :src="fsURL + 'upload/getFile/college-cover/' + item.coverImg"
                style="width: 100%; height: 100%; object-fit: cover; display: block"
                @error="hideBrokenImage"
              />
            </div>
          </t-swiper-item>
        </t-swiper>
      </t-col>
      <t-col :span="3">
        <t-card class="academy-card growth-card" :body-style="{ padding: '0' }">
          <div class="growth-user">
            <t-avatar
              size="52px"
              shape="round"
              fit="fill"
              :src="fsURL + userStore.avatar"
            ></t-avatar>
            <div class="growth-user-info">
              <span class="growth-name">{{ userStore.ploName }} · {{ userStore.ploNum }}</span>
              <span class="growth-dept">{{ userStore.deptName }} | {{ userStore.groupName }}</span>
            </div>
          </div>
          <div class="growth-title">我的成长</div>
          <div class="growth-stats">
            <div v-if="myPoint" class="growth-grid">
              <div class="growth-item" @click="router.push({ path: '/college/my' })">
                <CalendarIcon class="growth-icon" />
                <div class="growth-num">{{ myPoint.studyNum }}</div>
                <div class="growth-label">学习计划</div>
              </div>
              <div class="growth-item" @click="router.push({ path: '/college/my/study-record' })">
                <ChartLineIcon class="growth-icon" />
                <div class="growth-num">{{ myPoint.signNum }}</div>
                <div class="growth-label">学习记录</div>
              </div>
              <div class="growth-item" @click="router.push({ path: '/college/my/point-log' })">
                <TicketIcon class="growth-icon" />
                <div class="growth-num">{{ myPoint.coin }}</div>
                <div class="growth-label">学习金币</div>
              </div>
              <div class="growth-item" @click="router.push({ path: '/college/my/hour-log' })">
                <TimeIcon class="growth-icon" />
                <div class="growth-num">{{ myPoint.hours }}</div>
                <div class="growth-label">学习时长</div>
              </div>
            </div>
            <t-empty v-else description="暂无学习数据" :image-size="40" />
          </div>
        </t-card>
      </t-col>
    </t-row>
    <CourseCommand></CourseCommand>
    <TeacherList></TeacherList>
    <UserList :userList="userList"></UserList>
    <div class="academy-footer">
      本网站由武汉营运中心业务管理室维护
      <br />CopyRight© 2020-{{ new Date().getFullYear() }}, All Right Reserved
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

// 展示类文件统一走 HTTPS 文件管理地址，避免混合内容被浏览器拦截
const fsURL = import.meta.env.VITE_FILE_BASE_URL

// 封面缺失时隐藏破图图标，避免影响轮播区域
const hideBrokenImage = (e) => {
  e.target.style.display = 'none'
}
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
.academy-home {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

/* 分类面板 */
.cat-panel {
  height: 340px;
  overflow: hidden;
  position: relative;
}

.cat-item {
  border-top: 1px dashed rgba(255, 255, 255, 0.18);
  padding-top: 4px;
}

.nvl {
  color: #fff;
  cursor: pointer;
  padding: 8px 6px 4px;
  border-radius: 6px;
  transition: background 0.2s ease;

  &:hover {
    background: rgba(255, 255, 255, 0.08);
  }

  .nvl-title {
    font-size: 14px;
    font-weight: 600;
    letter-spacing: 1px;
  }

  .nvl-label {
    font-size: 11px;
    margin-top: 6px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;

    span {
      margin-right: 8px;
      color: rgba(255, 255, 255, 0.66);
      display: inline-block;
      cursor: pointer;

      &:hover {
        color: var(--academy-gold-2);
      }
    }
  }
}

.cat-more {
  position: absolute;
  right: 12px;
  bottom: 10px;
  font-size: 12px;
  line-height: 20px;
}

/* 轮播 */
.academy-swiper {
  border-radius: var(--academy-radius);
  overflow: hidden;
  box-shadow: var(--academy-shadow-sm);
}

/* 成长卡片 */
.growth-card {
  height: 340px;
  overflow: hidden;
}

.growth-user {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 20px 16px 16px;
  background: linear-gradient(120deg, #10273e, var(--academy-navy) 70%);
}

.growth-user-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
  min-width: 0;

  .growth-name {
    color: #fff;
    font-size: 15px;
    font-weight: 600;
  }

  .growth-dept {
    color: rgba(255, 255, 255, 0.66);
    font-size: 12px;
  }
}

.growth-title {
  display: inline-block;
  margin: -12px auto 0 16px;
  padding: 5px 18px;
  border-radius: 0 0 10px 10px;
  background: linear-gradient(90deg, var(--academy-gold), var(--academy-gold-2));
  color: #fff;
  font-size: 13px;
  font-weight: 600;
  letter-spacing: 2px;
}

.growth-stats {
  padding: 18px 16px;
}

.growth-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 12px;
}

.growth-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2px;
  padding: 10px 4px;
  border: 1px solid var(--academy-line);
  border-radius: 10px;
  cursor: pointer;
  transition: border-color 0.2s ease, box-shadow 0.2s ease;

  &:hover {
    border-color: var(--academy-gold);
    box-shadow: var(--academy-shadow-sm);
  }
}

.growth-item:nth-child(1) .growth-icon {
  color: #4caf50;
}
.growth-item:nth-child(2) .growth-icon {
  color: var(--academy-navy-2);
}
.growth-item:nth-child(3) .growth-icon {
  color: #2bb3a3;
}
.growth-item:nth-child(4) .growth-icon {
  color: #e8923a;
}

.growth-icon {
  font-size: 22px;
}

.growth-num {
  font-size: 18px;
  font-weight: 700;
  color: var(--academy-ink);
  line-height: 1.2;
}

.growth-label {
  font-size: 12px;
  color: var(--academy-muted);
}

/* 分类悬停弹层 */
.nvl-detail {
  height: 340px;
  width: 700px;
  background-color: #fff;
  overflow: auto;
  color: #000;
  position: absolute;
  left: 284px;
  top: 0;
  z-index: 20;
  box-sizing: border-box;
  padding: 16px 20px;
  border: 1px solid var(--td-component-stroke);
  border-radius: 0 var(--academy-radius) var(--academy-radius) 0;
  box-shadow: var(--academy-shadow);

  h3 {
    display: flex;
    align-items: center;
    gap: 8px;
    font-size: 16px;
    font-weight: 600;
    margin: 0 0 12px;
    padding-bottom: 10px;
    border-bottom: 1px solid var(--td-component-stroke);
  }

  .nvl-title-color {
    width: 4px;
    height: 16px;
    background: linear-gradient(180deg, var(--academy-gold-2), var(--academy-gold));
    border-radius: 2px;
    display: inline-block;
  }

  .nvl-level2 {
    display: flex;
    align-items: flex-start;
    gap: 12px;
    margin: 0 0 10px;
    cursor: pointer;

    .title {
      flex: none;
      width: 80px;
      font-weight: 500;
      color: var(--td-text-color-primary);
      &:hover {
        color: var(--td-brand-color);
      }
    }

    .level3 {
      flex: 1;
      display: flex;
      flex-wrap: wrap;
      gap: 6px 14px;
      font-size: 13px;
      color: var(--td-text-color-secondary);
      span {
        cursor: pointer;
        &:hover {
          color: var(--td-brand-color);
        }
      }
    }
  }
}

/* 页脚 */
.academy-footer {
  display: flex;
  justify-content: center;
  text-align: center;
  color: var(--academy-muted);
  font-size: 13px;
  line-height: 1.8;
  padding: 8px 0 4px;
}
</style>
