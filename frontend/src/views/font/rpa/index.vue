<template>
  <t-card class="management-card" style="height: calc(100vh - 130px)">
    <t-row :gutter="20">
      <t-col :span="8">
        <t-card class="management-card">
          <t-input
            v-model="searchStr"
            placeholder="按工具名称过滤"
            size="small"
            clearable
            style="margin-bottom: 10px"
          />
          <div
            style="
              height: calc(100vh - 230px);
              overflow-x: hidden;
              overflow-y: auto;
              padding-right: 10px;
            "
          >
            <div
              v-for="item in (dictStore.dictList?.rpa_tool_list_category || [])"
              :key="item.code"
              v-show="filteredList(item.code).length > 0"
            >
              <t-divider content-position="left">{{
                item.codeval
              }}</t-divider>
              <t-row :gutter="10">
                <t-col
                  v-for="tool in filteredList(item.code)"
                  :key="tool.id"
                  :span="4"
                >
                  <t-card style="margin-bottom: 10px">
                    <div
                      style="cursor: pointer"
                      @click="toolDetailRef.initData(tool)"
                    >
                      <t-tooltip placement="bottom">
                        <template #content>
                          <SafeHtml :html="tool.memo" />
                        </template>
                        <span>{{ tool.name }}</span>
                      </t-tooltip>
                      <div class="bottom clearfix">
                        <time class="time">联系人：{{ tool.contacts }}</time>
                      </div>
                    </div>
                  </t-card>
                </t-col>
              </t-row>
            </div>
          </div>
        </t-card>
      </t-col>
      <t-col :span="4">
        <ToolDetailVue ref="toolDetailRef"></ToolDetailVue>
      </t-col>
    </t-row>
  </t-card>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import ToolDetailVue from '@/views/font/rpa/components/toolDetail.vue'
import { getRpaToolList } from '@/api/rpa/rpa.js'
import { useDictStore } from '@/stores'

const dictStore = useDictStore()
// 工具列表
const list = ref([])

// 搜索字符串
const searchStr = ref('')

// 工具详情组件引用
const toolDetailRef = ref(null)

// 查询参数
const queryInfo = ref({
  orderType: ' asc',
  order: 'sortId',
  queryType: 'id',
  status: '',
  query: '',
  pageSize: 20,
  pageNum: 1
})

// 筛选工具列表（按分类 + 工具名称）
const filteredList = (category) => {
  const keyword = searchStr.value.trim()
  return list.value.filter(
    (tool) =>
      tool.category === category &&
      (!keyword || (tool.name || '').includes(keyword))
  )
}

// 获取工具列表
const getList = async () => {
  try {
    const res = await getRpaToolList(queryInfo.value)
    if (res.code === 200) {
      list.value = res.data
    } else {
      MessagePlugin.error(res.msg)
    }
  } catch (error) {
    console.error('获取RPA工具列表失败:', error)
    MessagePlugin.error('获取RPA工具列表失败')
  }
}

// 用户信息
const user = ref(null)

// 生命周期钩子
onMounted(() => {
  user.value = JSON.parse(window.localStorage.getItem('user') || '{}')
  getList()
})
</script>

<style lang="less" scoped>
.time {
  font-size: 13px;
  color: var(--td-text-color-secondary);
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.clearfix {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.image {
  width: 100%;
  display: block;
}

.nowarp {
  text-indent: 2em;
  height: 33px;
  font-size: 12px;
  color: var(--td-text-color-secondary);
  overflow: hidden;
  word-break: normal;
  text-overflow: ellipsis;
}
</style>
