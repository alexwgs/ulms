<template>
  <div>
    <div class="background-class"></div>
    <t-row :gutter="20">
        <div>
          <t-space>
          <t-radio-group v-model="queryInfo.category" size="small" @change="getArticalListData">
            <t-radio-button label="" value="">全部</t-radio-button>
            <t-radio-button v-for="item in articalCategoryList" :key="'2' + item.code" :label="item.codeval"
              :value="'2' + item.code"></t-radio-button>
            <t-radio-button v-for="item in itemCategoryList" :key="'1' + item.code" :label="item.codeval"
              :value="'1' + item.code"></t-radio-button>
          </t-radio-group>
          </t-space>
            <t-space>
          <t-radio-group v-model="queryInfo.sortType" size="small" @change="changeSort">
            <t-radio-button label="最新" value="time"></t-radio-button>
            <t-radio-button label="最热" value="hot"></t-radio-button>
            <t-radio-button label="讨论最多" value="comment"></t-radio-button>
          </t-radio-group>
            </t-space>
          <t-space>
          <t-input size="small" placeholder="搜索帖子" v-model="queryInfo.query" @change="getArticalListData"
            style="width: 180px" :clearable="true"></t-input>
          </t-space>
          <t-space><t-button style="margin-left: 10px" size="small" @click="getArticalListData">刷新</t-button></t-space>
          <t-space><t-dropdown style="margin-left: 10px" @click="goto" type="primary" size="small">
            <t-button theme="primary" size="small">
              发帖
            </t-button>
            <template #dropdown>
              <t-dropdown-menu>
                <t-dropdown-item v-if="hasPermission('cyt:artical:add')" command="artical/new">讨论帖</t-dropdown-item>
                <t-dropdown-item v-if="hasPermission('cyt:item:add')" command="item/new">课题发布</t-dropdown-item>
                <t-dropdown-item v-if="hasPermission('cyt:survey:add')" command="survey/new">调研发布</t-dropdown-item>
              </t-dropdown-menu>
            </template>
          </t-dropdown>
          </t-space>
        </div>
    </t-row>
    <t-row :gutter="20">
      <t-col :span="8">
        <div class="infinite-list" @end-reached="load">
          <div class="artical-list-tab">
            <t-empty v-if="articalList.length == 0" description="没有符合条件的文章"></t-empty>
            <div class="artical-box" v-for="artical in articalList" :key="artical.id">
              <ArticalView :articalitem="artical"></ArticalView>
            </div>
          </div>
          <div></div>
        </div>
        <t-pagination style="text-align: center; margin-top: 3px" @current-change="handleCurrentChange"
          v-model:current="currentPage" :page-size="queryInfo.pageSize"
          :total="total">
        </t-pagination>
      </t-col>
      <t-col :span="4">
        <div class="my-handle">
          <t-row>
            <t-col style="text-align: center" :span="3"><t-button theme="success" size="small" @click="goto('my-artical')" plain shape="circle"><template #icon><DynamicIcon name="folder-opened" /></template></t-button>
              <div class="my-menu">帖子管理</div>
            </t-col>
            <t-col style="text-align: center" :span="3"><t-button theme="success" size="small" @click="goto('collect')" plain shape="circle"><template #icon><DynamicIcon name="star-on" /></template></t-button>
              <div class="my-menu">我的收藏</div>
            </t-col>
            <t-col style="text-align: center" :span="3">
              <t-badge :value="unreadCount === 0 ? '' : unreadCount" class="item">
                <t-button theme="success" size="small" @click="goto('atMe')" plain shape="circle"><template #icon><DynamicIcon name="chat-round" /></template></t-button>
              </t-badge>
              <div class="my-menu">我的消息</div>
            </t-col>
            <t-col v-if="hasPermission('cyt:item:manager')" style="text-align: center" :span="3"><t-button
                theme="success" size="small" @click="goto('my-item')" plain shape="circle"><template #icon><DynamicIcon name="folder-opened" /></template></t-button>
              <div class="my-menu">项目管理</div>
            </t-col>
          </t-row>
        </div>
        <t-card class="box-card">
          <template #header>
            <span>《A6有声公约》</span>
          </template>
          <div style="height: 55px">
            <Convention></Convention>
          </div>
        </t-card>
        <t-card class="rank-list" v-if="false">
          <template #header>
            <span>回帖榜</span>
          </template>
          <t-col :span="3" v-for="item in commentRanks" :key="item.userid">
            <div style="display: inline-block">
              <div class="avartar-box-big">
                <img :src="fsURL + item.user.avatar" />
              </div>
              <div class="rank-avatar-info">发帖量:{{ item.count }}</div>
              <div class="rank-avatar-user">{{ item.user.ploName }}</div>
            </div>
          </t-col>
        </t-card>
        <t-card class="rank-list">
          <template #header>
            <span>本周热榜</span>
          </template>
          <div style="height: calc(100vh - 510px); overflow: auto">
            <div class="hot-box" v-for="artical in weeklyHotList" :key="artical.id">
              <t-row>
                <t-col :span="10">
                  <div class="artical-title">
                    <span>
                      <t-link :href="getArticalLink(artical)" target="_blank">{{ artical.title }}</t-link>
                    </span>
                  </div>
                </t-col>
                <t-col :span="2" style="text-align: right">
                  <span>{{
                    artical.viewNum +
                    artical.replyNum * 2 +
                    artical.isLike * 2 +
                    artical.collectNum * 3
                  }}</span>
                </t-col>
              </t-row>
            </div>
            <div class="cyt-banner"></div>
          </div>
        </t-card>
      </t-col>
    </t-row>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { MessagePlugin } from 'tdesign-vue-next'
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
      MessagePlugin.error(res.msg)
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
      MessagePlugin.error(res.msg)
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
      MessagePlugin.error(res.msg)
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

const getArticalLink = (artical) => {
  const typeMap = { 1: 'item', 2: 'view', 3: 'survey' }
  return `${import.meta.env.BASE_URL}artical/${typeMap[artical.articalType]}/${artical.id}`
}

const goto = (path) => {
  router.push('/font/a6voice/' + path)
}

const getUnreadCountData = async () => {
  try {
    const res = await getUnreadCount()
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
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
  -color: var(--td-bg-color);
  border-radius: 5px;
  padding: 10px;
  margin: 0 10px 10px 10px;
}

.my-handle {
  height: 55px;
-color: var(--td-bg-color);
  border-radius: 5px;
  padding: 10px;
  margin-bottom: 10px;
}

.t-card {
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
//-color: rgba(0, 0, 0, 0.2);
    //color: #fff;
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
    //color: #000;
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
  padding-top: 5px;
}

.hot-box {
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
    }
  }
}

.cyt-banner {
  position: absolute;
  height: 183px;
  width: 175px;
  bottom: 0;
  right: 0;
  -image: url(@/assets/img/cyt_banner.png);
  -size: 100% 100%;
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
