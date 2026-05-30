<template>
  <el-card class="box-card" style="height: calc(100vh - 130px)">
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card class="box-card">
          <template #header>
            <div class="clearfix">
              <span>RPA轻工具列表</span>
            </div>
          </template>
          <div
            style="
              height: calc(100vh - 230px);
              overflow-x: hidden;
              overflow-y: auto;
              padding-right: 10px;
            "
          >
            <div
              v-for="item in dictStore.dictList.rpa_tool_list_category"
              :key="item.code"
            >
              <el-divider content-position="left">{{
                item.codeval
              }}</el-divider>
              <el-row :gutter="10">
                <el-col
                  v-for="tool in filteredList(item.code)"
                  :key="tool.id"
                  :span="8"
                >
                  <el-card style="margin-bottom: 10px">
                    <div
                      style="cursor: pointer"
                      @click="toolDetailRef.initData(tool)"
                    >
                      <el-tooltip placement="bottom" effect="light">
                        <template #content>
                          <div v-html="tool.memo"></div>
                        </template>
                        <span>{{ tool.name }}</span>
                      </el-tooltip>
                      <div class="bottom clearfix">
                        <time class="time">联系人：{{ tool.contacts }}</time>
                      </div>
                    </div>
                  </el-card>
                </el-col>
              </el-row>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <ToolDetailVue ref="toolDetailRef"></ToolDetailVue>
      </el-col>
    </el-row>
  </el-card>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
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

// 筛选工具列表
const filteredList = (category) => {
  return list.value.filter((tool) => tool.category === category)
}

// 获取工具列表
const getList = async () => {
  try {
    const res = await getRpaToolList(queryInfo.value)
    if (res.code === 200) {
      list.value = res.data
    } else {
      ElMessage.error(res.msg)
    }
  } catch (error) {
    console.error('获取RPA工具列表失败:', error)
    ElMessage.error('获取RPA工具列表失败')
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
  color: var(--el-text-color-secondary);
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
  color: var(--el-text-color-secondary);
  overflow: hidden;
  word-break: normal;
  text-overflow: ellipsis;
}
</style>
