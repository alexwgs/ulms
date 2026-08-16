<template>
  <div>
    <div class="background-class"></div>
    <div class="table-toolbar a6voice-toolbar">
        <div class="toolbar-left">
          <t-radio-group v-model="queryInfo.category" size="small" @change="getArticleListData">
            <t-radio-button label="" value="">全部</t-radio-button>
            <t-radio-button v-for="item in articleCategoryList" :key="'2' + item.code" :label="item.codeval"
              :value="'2' + item.code"></t-radio-button>
            <t-radio-button v-for="item in itemCategoryList" :key="'1' + item.code" :label="item.codeval"
              :value="'1' + item.code"></t-radio-button>
          </t-radio-group>
          <t-radio-group v-model="queryInfo.sortType" size="small" @change="changeSort">
            <t-radio-button label="最新" value="time"></t-radio-button>
            <t-radio-button label="最热" value="hot"></t-radio-button>
            <t-radio-button label="讨论最多" value="comment"></t-radio-button>
          </t-radio-group>
        </div>
        <div class="toolbar-right">
          <t-input size="small" placeholder="搜索帖子" v-model="queryInfo.query" @change="getArticleListData"
            style="width: 180px" :clearable="true"></t-input>
          <t-button size="small" @click="getArticleListData">刷新</t-button>
          <t-button
            v-if="hasPermission('cyt:artical:add')"
            theme="primary"
            size="small"
            @click="goto('article/new')"
            >发讨论帖</t-button
          >
          <t-button
            v-if="hasPermission('cyt:item:add')"
            theme="primary"
            size="small"
            @click="goto('item/new')"
            >课题发布</t-button
          >
          <t-button
            v-if="hasPermission('cyt:survey:add')"
            theme="primary"
            size="small"
            @click="goto('survey/new')"
            >调研发布</t-button
          >
        </div>
    </div>
    <t-row :gutter="20">
      <t-col :span="8">
        <div class="infinite-list">
          <div class="article-list-tab">
            <t-empty v-if="articleList.length == 0" description="没有符合条件的文章"></t-empty>
            <div class="article-box" v-for="article in articleList" :key="article.id">
              <ArticleView :articleitem="article"></ArticleView>
            </div>
          </div>
        </div>
        <t-pagination style="justify-content: center; margin-top: var(--td-comp-margin-xxl)" @current-change="handleCurrentChange"
          v-model="currentPage" :page-size="queryInfo.pageSize"
          :total="total">
        </t-pagination>
      </t-col>
      <t-col :span="4">
        <div class="my-handle">
          <t-row>
            <t-col style="text-align: center" :span="3"><t-button theme="success" size="small" @click="goto('my-article')" variant="outline"><template #icon><DynamicIcon name="folder-opened" /></template></t-button>
              <div class="my-menu">帖子管理</div>
            </t-col>
            <t-col style="text-align: center" :span="3"><t-button theme="success" size="small" @click="goto('collect')" variant="outline"><template #icon><DynamicIcon name="star-on" /></template></t-button>
              <div class="my-menu">我的收藏</div>
            </t-col>
            <t-col style="text-align: center" :span="3">
              <t-badge :value="unreadCount === 0 ? '' : unreadCount" class="item">
                <t-button theme="success" size="small" @click="goto('atMe')" variant="outline"><template #icon><DynamicIcon name="chat-round" /></template></t-button>
              </t-badge>
              <div class="my-menu">我的消息</div>
            </t-col>
            <t-col v-if="hasPermission('cyt:item:manager')" style="text-align: center" :span="3"><t-button
                theme="success" size="small" @click="goto('my-item')" variant="outline"><template #icon><DynamicIcon name="folder-opened" /></template></t-button>
              <div class="my-menu">项目管理</div>
            </t-col>
          </t-row>
        </div>
        <t-card class="management-card">
          <template #header>
            <span>《A6有声公约》</span>
          </template>
          <div style="height: 55px">
            <Convention></Convention>
          </div>
        </t-card>
        <t-card class="rank-list">
          <template #header>
            <span>本周热榜</span>
          </template>
          <div style="height: calc(100vh - 510px); overflow: auto">
            <div class="hot-box" v-for="article in weeklyHotList" :key="article.id">
              <t-row>
                <t-col :span="10">
                  <div class="article-title">
                    <span>
                      <t-link :href="getArticleLink(article)" target="_blank">{{ article.title }}</t-link>
                    </span>
                  </div>
                </t-col>
                <t-col :span="2" style="text-align: right">
                  <span>{{
                    article.viewNum +
                    article.replyNum * 2 +
                    article.isLike * 2 +
                    article.collectNum * 3
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
import { useDictStore } from '@/stores'
import { usePermission } from '@/hooks/usePermission'
import {
  getWeeklyHot,
  getArticleList,
  getUnreadCount
} from '@/api/a6voice/index.js'
import Convention from './components/Convention.vue'
import ArticleView from './components/ArticleView.vue'

const router = useRouter()

const fsURL = import.meta.env.VITE_FILE_BASE_URL || ''
const weeklyHotList = ref([])
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
const articleList = ref([])
const unreadCount = ref('')
const screenWidth = ref(document.documentElement.clientWidth)

const dictStore = useDictStore()

const articleCategoryList = computed(() => {
  if (!dictStore.dictList?.cyt_artical_category) return []
  return dictStore.dictList.cyt_artical_category.filter(
    (item) => item.status == 1
  )
})

const itemCategoryList = computed(() => {
  if (!dictStore.dictList?.cyt_item_category) return []
  return dictStore.dictList.cyt_item_category.filter((item) => item.status == 1)
})

// 审计修复（F-M7）：接入真实权限码判断（cyt:* 权限码需在菜单资源配置）
const { hasPermission } = usePermission()

onMounted(() => {
  dictStore.getDictList()
  getWeeklyHotData()
  getArticleListData()
  getUnreadCountData()
})

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

const getArticleListData = async (event) => {
  try {
    const res = await getArticleList(queryInfo.value)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    total.value = res.data.total
    articleList.value = res.data.list
  } catch (error) {
    console.error(error)
  }
}

const handleCurrentChange = (page) => {
  queryInfo.value.pageNum = page
  getArticleListData()
}

const changeSort = () => {
  getArticleListData()
}

const getArticleLink = (article) => {
  const typeMap = { 1: 'item', 2: 'view', 3: 'survey' }
  return `${import.meta.env.BASE_URL}article/${typeMap[article.articleType]}/${article.id}`
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

defineExpose({
  getArticleList: getArticleListData
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
  background-color: var(--td-bg-color-container);
  border-radius: 5px;
  padding: 10px;
  margin: 0 10px 10px 10px;
}

.my-handle {
  height: 55px;
  background-color: var(--td-bg-color-container);
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

.article-list-tab {
  height: 100%;
}

.article-box {
  position: relative;
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

  .article-title {
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
  overflow-y: auto;
  padding: 0;
  margin: 0;
  list-style: none;
}
</style>
