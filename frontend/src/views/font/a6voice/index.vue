<template>
  <div>
    <div class="background-class"></div>
    <el-row :gutter="20">
      <div class="main-handle">
        <div style="display: inline; width: 300px; margin-left: 5px">
          <el-radio-group v-model="queryInfo.category" size="small" @change="getArticalListData">
            <el-radio-button label="" value="">全部</el-radio-button>
            <el-radio-button v-for="item in articalCategoryList" :key="'2' + item.code" :label="item.codeval"
              :value="'2' + item.code"></el-radio-button>
            <el-radio-button v-for="item in itemCategoryList" :key="'1' + item.code" :label="item.codeval"
              :value="'1' + item.code"></el-radio-button>
          </el-radio-group>
        </div>
        <div style="display: inline; margin-left: 5px">
          <el-radio-group v-model="queryInfo.sortType" size="small" @change="changeSort">
            <el-radio-button label="最新" value="time"></el-radio-button>
            <el-radio-button label="最热" value="hot"></el-radio-button>
            <el-radio-button label="讨论最多" value="comment"></el-radio-button>
          </el-radio-group>
        </div>
        <div :class="screenWidth >= 1400 ? '' : 'pull-right'" style="display: inline-block; margin-left: 10px">
          <el-input size="small" placeholder="搜索帖子" v-model="queryInfo.query" @change="getArticalListData"
            style="width: 180px" :clearable="true"></el-input>
          <el-button style="margin-left: 10px" size="small" @click="getArticalListData"><i
              class="el-icon-refresh-left"></i>刷新</el-button>
          <el-dropdown style="margin-left: 10px" @command="goto" type="primary" size="small">
            <el-button type="primary" size="small">
              发帖<i class="el-icon-arrow-down el-icon--right"></i>
            </el-button>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item v-if="hasPermission('cyt:artical:add')" command="artical/new">讨论帖</el-dropdown-item>
                <el-dropdown-item v-if="hasPermission('cyt:item:add')" command="item/new">课题发布</el-dropdown-item>
                <el-dropdown-item v-if="hasPermission('cyt:survey:add')" command="survey/new">调研发布</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="16">
        <el-scrollbar class="infinite-list" @end-reached="load">
          <div class="artical-list-tab">
            <el-empty v-if="articalList.length == 0" description="没有符合条件的文章"></el-empty>
            <div class="artical-box" v-for="artical in articalList" :key="artical.id">
              <ArticalView :articalitem="artical"></ArticalView>
            </div>
          </div>
          <div></div>
        </el-scrollbar>
        <el-pagination style="text-align: center; margin-top: 3px" @current-change="handleCurrentChange"
          v-model:current-page="currentPage" :page-size="queryInfo.pageSize" background layout="prev, pager, next"
          :total="total">
        </el-pagination>
      </el-col>
      <el-col :span="8">
        <div class="my-handle">
          <el-row>
            <el-col style="text-align: center" :span="6"><el-button type="success" size="small" icon="folder-opened"
                @click="goto('my-artical')" plain circle></el-button>
              <div class="my-menu">帖子管理</div>
            </el-col>
            <el-col style="text-align: center" :span="6"><el-button type="success" size="small" icon="star-on"
                @click="goto('collect')" plain circle></el-button>
              <div class="my-menu">我的收藏</div>
            </el-col>
            <el-col style="text-align: center" :span="6">
              <el-badge :value="unreadCount === 0 ? '' : unreadCount" class="item">
                <el-button type="success" icon="chat-round" size="small" @click="goto('atMe')" plain circle></el-button>
              </el-badge>
              <div class="my-menu">我的消息</div>
            </el-col>
            <el-col v-if="hasPermission('cyt:item:manager')" style="text-align: center" :span="6"><el-button
                type="success" size="small" icon="folder-opened" @click="goto('my-item')" plain circle></el-button>
              <div class="my-menu">项目管理</div>
            </el-col>
          </el-row>
        </div>
        <el-card class="box-card">
          <template #header>
            <span>《A6有声公约》</span>
          </template>
          <div style="height: 55px">
            <Convention></Convention>
          </div>
        </el-card>
        <el-card class="rank-list" v-if="false">
          <template #header>
            <span>回帖榜</span>
          </template>
          <el-col :span="6" v-for="item in commentRanks" :key="item.userid">
            <div style="display: inline-block">
              <div class="avartar-box-big">
                <img :src="fsURL + item.user.avatar" />
              </div>
              <div class="rank-avatar-info">发帖量:{{ item.count }}</div>
              <div class="rank-avatar-user">{{ item.user.ploName }}</div>
            </div>
          </el-col>
        </el-card>
        <el-card class="rank-list">
          <template #header>
            <span>本周热榜</span>
          </template>
          <div style="height: calc(100vh - 510px); overflow: auto">
            <div class="hot-box" v-for="artical in weeklyHotList" :key="artical.id">
              <el-row>
                <el-col :span="20">
                  <div class="artical-title">
                    <span>
                      <router-link class="hot-link" v-if="artical.articalType == 2" tag="a" target="_blank"
                        :to="{ path: '/artical/view/' + artical.id }" rel="opener">{{ artical.title }}</router-link>
                      <router-link class="hot-link" v-else-if="artical.articalType == 1" tag="a" target="_blank"
                        :to="{ path: '/artical/item/' + artical.id }" rel="opener">{{ artical.title }}</router-link>
                      <router-link class="hot-link" v-else-if="artical.articalType == 3" tag="a" target="_blank"
                        :to="{ path: '/artical/survey/' + artical.id }" rel="opener">{{ artical.title }}</router-link>
                    </span>
                  </div>
                </el-col>
                <el-col :span="4" style="text-align: right">
                  <i class="el-icon-discover">{{
                    artical.viewNum +
                    artical.replyNum * 2 +
                    artical.isLike * 2 +
                    artical.collectNum * 3
                  }}</i>
                </el-col>
              </el-row>
            </div>
            <div class="cyt-banner"></div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import {
  getWeeklyHot,
  getArticalList,
  getUnreadCount
} from '@/api/a6voice/index.js'
import Convention from './components/Convention.vue'
import ArticalView from './components/ArticalView.vue'

const router = useRouter()

const fsURL = import.meta.env.VITE_FILE_MANAGE_BASE || ''
const weeklyHotList = ref([])
const commentRanks = ref({})
const currentPage = ref(1)
const total = ref(0)
const queryInfo = ref({
  sortType: 'time',
  status: 1,
  queryType: 'title',
  query: '',
  category: '',
  pageSize: 10,
  pageNum: 1
})
const articalList = ref([])
const unreadCount = ref('')
const screenWidth = ref(document.documentElement.clientWidth)

const global = ref(window.__POWERED_BY_QIANKUN__ ? window.$global : null)

const articalCategoryList = computed(() => {
  if (!global.value?.dict?.cyt_artical_category) return []
  return global.value.dict.cyt_artical_category.filter(
    (item) => item.status == 1
  )
})

const itemCategoryList = computed(() => {
  if (!global.value?.dict?.cyt_item_category) return []
  return global.value.dict.cyt_item_category.filter((item) => item.status == 1)
})

const hasPermission = (permission) => {
  if (!global.value?.hasPermission) return true
  return global.value.hasPermission(permission)
}

onMounted(() => {
  getWeeklyHotData()
  getArticalListData()
  getUnreadCountData()
})

const getCommentRank = async () => {
  try {
    const res = await getCommentRank()
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    commentRanks.value = res.data
  } catch (error) {
    console.error(error)
  }
}

const getWeeklyHotData = async () => {
  try {
    const res = await getWeeklyHot()
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    weeklyHotList.value = res.data
  } catch (error) {
    console.error(error)
  }
}

const getArticalListData = async (event) => {
  try {
    const res = await getArticalList(queryInfo.value)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    total.value = res.data.total
    articalList.value = res.data.list
  } catch (error) {
    console.error(error)
  }
}

const handleCurrentChange = (page) => {
  queryInfo.value.pageNum = page
  getArticalListData()
}

const changeSort = () => {
  getArticalListData()
}

const goto = (path) => {
  router.push('/font/a6voice/' + path)
}

const getUnreadCountData = async () => {
  try {
    const res = await getUnreadCount()
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    if (res.data.count === 0) unreadCount.value = 0
    else unreadCount.value = res.count
  } catch (error) {
    console.error(error)
  }
}

const load = () => {
  queryInfo.value.pageSize =
    (queryInfo.value.pageNum + 1) * queryInfo.value.pageSize
  getArticalListData()
}

defineExpose({
  getArticalList: getArticalListData
})
</script>

<style lang="less" scoped>
.background-class {
  position: absolute;
  width: 100%;
  height: calc(100vh - 60px);
  z-index: -999;
  margin: -20px -20px 0 -20px;
}

.main-handle {
  height: 25px;
  background-color: var(--el-bg-color);
  border-radius: 5px;
  padding: 10px;
  margin: 0 10px 10px 10px;
}

.my-handle {
  height: 55px;
  background-color: var(--el-bg-color);
  border-radius: 5px;
  padding: 10px;
  margin-bottom: 10px;
}

.el-card {
  border: none;
}

.rank-list {
  height: calc(100vh - 430px);
  margin-top: 10px;

  .rank-avatar-info {
    height: 20px;
    font-size: 12px;
    position: relative;
    bottom: 20px;
    width: 100%;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    background-color: rgba(0, 0, 0, 0.2);
    color: #fff;
    text-align: center;
  }

  .rank-avatar-user {
    height: 20px;
    font-size: 12px;
    position: relative;
    bottom: 18px;
    width: 100%;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    color: #000;
    text-align: center;
  }
}

.artical-list-tab {
  height: calc(100vh - 225px);
}

.artical-box {
  position: relative;
  border-bottom: #dddddd solid 1px;
}

.my-menu {
  font-size: 14px;
  color: #498872;
  padding-top: 5px;

  :hover {
    color: chartreuse;
  }
}

.hot-box {
  background-color: rgba(256, 256, 256, 0.4);
  padding: 5px;
  box-shadow:
    0 2px 4px rgba(0, 0, 0, 0.12),
    0 0 6px rgba(0, 0, 0, 0.04);
  height: 25px;
  margin-bottom: 10px;
  border-radius: 5px;
  position: relative;

  .artical-title {
    padding-left: 10px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;

    .hot-link {
      font-size: 14px;
      text-decoration: none;
      color: #000;
    }
  }
}

.cyt-banner {
  position: absolute;
  height: 183px;
  width: 175px;
  bottom: 0;
  right: 0;
  background-image: url(../../assets/img/cyt_banner.png);
  background-size: 100% 100%;
}

.pull-right {
  float: right;
}

.infinite-list {
  height: calc(100vh - 310px);
  padding: 0;
  margin: 0;
  list-style: none;
}
</style>
