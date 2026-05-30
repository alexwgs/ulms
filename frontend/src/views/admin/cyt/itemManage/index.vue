<template>
  <el-card class="box-card">
    <div class="text item">
      <div class="table-filter">
        <el-row>
          <el-col :span="6">
            <span class="demonstration"
              >年份
              <el-date-picker
                size="small"
                style="width: 70%"
                @change="getItemList"
                v-model="yearPicker"
                type="year"
                placeholder="选择年"
              ></el-date-picker>
            </span>
          </el-col>
          <el-col :span="6">
            <span
              >板块
              <el-select
                size="small"
                v-model="queryInfo.category"
                @change="getItemList"
                placeholder="请选择,默认不限制"
              >
                <el-option :value="-1" label="全部"></el-option>
                <el-option
                  v-for="item in dictStore.dictList.cyt_item_category"
                  :key="item.code"
                  :label="item.codeval"
                  :value="parseInt(item.code)"
                >
                </el-option>
              </el-select>
            </span>
          </el-col>
          <el-col :span="6">
            <span
              >状态
              <el-select
                size="small"
                v-model="queryInfo.status"
                @change="getItemList"
                placeholder="请选择,默认不限制"
              >
                <el-option label="全部" value=""></el-option>
                <el-option
                  v-for="item in dictStore.dictList.cyt_artical_status"
                  :key="item.code"
                  :label="item.codeval"
                  :value="item.code"
                >
                </el-option>
              </el-select>
            </span>
          </el-col>
          <el-col :span="6">
            <span
              >进度
              <el-select
                size="small"
                v-model="queryInfo.compType"
                @change="getItemList"
                placeholder="请选择,默认不限制"
              >
                <el-option label="全部" value=""></el-option>
                <el-option
                  v-for="item in dictStore.dictList.cyt_artical_comp_type"
                  :key="item.code"
                  :label="item.codeval"
                  :value="item.code"
                >
                </el-option>
              </el-select>
            </span>
          </el-col>
        </el-row>
        <el-row style="padding-top: 5px">
          <el-col :span="6">
            <span class="demonstration"
              >置顶
              <el-select
                size="small"
                v-model="queryInfo.topFlag"
                @change="getItemList"
                placeholder="请选择,默认不限制"
              >
                <el-option label="全部" value=""></el-option>
                <el-option label="未置顶" :value="0"></el-option>
                <el-option label="已置顶" :value="1"></el-option>
              </el-select>
            </span>
          </el-col>
          <el-col :span="6">
            <span class="demonstration"
              >精华
              <el-select
                size="small"
                v-model="queryInfo.eliteFlag"
                @change="getItemList"
                placeholder="请选择,默认不限制"
              >
                <el-option label="全部" value=""></el-option>
                <el-option label="未精华" :value="0"></el-option>
                <el-option label="已精华" :value="1"></el-option>
              </el-select>
            </span>
          </el-col>
          <el-col :span="6">
            <span class="demonstration"
              >广场
              <el-select
                size="small"
                v-model="queryInfo.onStage"
                @change="getItemList"
                placeholder="请选择,默认不限制"
              >
                <el-option label="全部" value=""></el-option>
                <el-option label="未上广场" :value="0"></el-option>
                <el-option label="上广场" :value="1"></el-option>
              </el-select>
            </span>
          </el-col>
          <el-col :span="6">
            <!-- 预留按钮位置 -->
          </el-col>
        </el-row>
      </div>
      <el-table
        :data="articals"
        size="small"
        stripe
        height="calc(100vh - 360px)"
        v-loading="loading"
      >
        <el-table-column prop="category" label="板块" width="100">
          <template #default="scope">
            <el-tag
              v-for="item in dictStore.dictList.cyt_item_category"
              size="small"
              :key="item.code"
              :type="scope.row.category == 1 ? 'danger' : 'info'"
              :style="scope.row.category == item.code ? '' : 'display:none'"
              effect="plain"
              >{{ item.codeval }}</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column prop="title" label="标题">
          <template #default="scope">
            <el-link type="info" size="small" @click="viewArtical(scope.row)">{{
              scope.row.title
            }}</el-link>
          </template>
        </el-table-column>
        <el-table-column label="状态(置顶、精华、广场)" width="180">
          <template #default="scope">
            <el-tag
              style="cursor: pointer; margin-right: 3px"
              size="small"
              @click="setTopAndSoOn('top', scope.row.id, scope.row.topFlag)"
              :type="scope.row.topFlag ? 'success' : 'info'"
              :effect="scope.row.topFlag ? 'dark' : 'plain'"
              >置顶</el-tag
            >
            <el-tag
              style="cursor: pointer; margin-right: 3px"
              size="small"
              @click="setTopAndSoOn('elite', scope.row.id, scope.row.eliteFlag)"
              :type="scope.row.eliteFlag ? 'danger' : 'info'"
              :effect="scope.row.eliteFlag ? 'dark' : 'plain'"
              >精华</el-tag
            >
            <el-tag
              style="cursor: pointer; margin-right: 3px"
              size="small"
              @click="setTopAndSoOn('stage', scope.row.id, scope.row.onStage)"
              :type="scope.row.onStage ? 'warning' : 'info'"
              :effect="scope.row.onStage ? 'dark' : 'plain'"
              >广场</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column label="审核状态" width="100">
          <template #default="scope">
            <el-tag
              @click="examineItem(scope.row)"
              v-for="item in dictStore.dictList.cyt_artical_status"
              size="small"
              :key="item.code"
              :type="scope.row.status == 1 ? 'success' : 'warning'"
              :style="scope.row.status == item.code ? '' : 'display:none'"
              effect="plain"
              >{{ item.codeval }}</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column label="结案类型" width="100">
          <template #default="scope">
            <el-tag
              v-for="item in dictStore.dictList.cyt_artical_comp_type"
              size="small"
              :key="item.code"
              :type="scope.row.compType == 1 ? 'success' : 'warning'"
              :style="scope.row.compType == item.code ? '' : 'display:none'"
              effect="plain"
              >{{ item.codeval }}</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column prop="pubDate" label="发布日期" width="160">
        </el-table-column>
        <el-table-column label="操作" width="220">
          <template #default="scope">
            <el-button
              type="primary"
              size="small"
              :disabled="scope.row.compType <= 2"
              @click="compItem(scope.row)"
              >结案</el-button
            >
            <el-button
              type="warning"
              size="small"
              @click="updateCategory(scope.row)"
              >移版</el-button
            >
            <el-button
              type="success"
              size="small"
              @click="getSignList(scope.row)"
              >名单</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @current-change="handleCurrentChange"
        v-model:current-page="currentPage"
        :page-size="queryInfo.pageSize"
        layout="total, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </div>

    <el-dialog
      title="审核"
      v-model="examineDialogVisible"
      width="30%"
      :before-close="examineHandleClose"
    >
      <div>
        审核结果：
        <el-select size="small" v-model="examineForm.status">
          <el-option
            v-for="item in dictStore.dictList.cyt_artical_status"
            :key="item.code"
            :label="item.codeval"
            :value="parseInt(item.code)"
          ></el-option>
        </el-select>
      </div>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="examineDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="examineSubmit">确 定</el-button>
        </div>
      </template>
    </el-dialog>

    <el-dialog
      title="结案"
      v-model="compDialogVisible"
      width="30%"
      :before-close="compHandleClose"
    >
      <div>
        结案类型：
        <el-select
          size="small"
          v-model="compForm.compType"
          @change="
            (e) => {
              if (e !== 1) compForm.compGrade = ''
            }
          "
        >
          <el-option
            v-for="item in dictStore.dictList.cyt_artical_comp_type"
            :disabled="parseInt(item.code) > 2"
            :key="item.code"
            :label="item.codeval"
            :value="parseInt(item.code)"
          ></el-option>
        </el-select>
      </div>
      <div style="padding-top: 10px">
        结案等级：
        <el-select
          size="small"
          v-model="compForm.compGrade"
          :disabled="compForm.compType != 1"
        >
          <el-option label="" value=""></el-option>
          <el-option
            v-for="item in dictStore.dictList.cyt_artical_comp_grade"
            :key="item.code"
            :label="item.codeval + '[' + item.code + '分]'"
            :value="parseInt(item.code)"
          ></el-option>
        </el-select>
      </div>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="compDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="compSubmit">确 定</el-button>
        </div>
      </template>
    </el-dialog>

    <el-dialog title="移动分类" v-model="categoryDialogVisible" width="30%">
      <div>
        移动到分类：
        <el-select size="small" v-model="categoryForm.category">
          <el-option
            v-for="item in dictStore.dictList.cyt_item_category.filter(
              (item) => item.status === 1
            )"
            :key="item.code"
            :label="item.codeval"
            :value="parseInt(item.code)"
          ></el-option>
        </el-select>
      </div>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="categoryDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="categorySubmit">确 定</el-button>
        </div>
      </template>
    </el-dialog>

    <el-dialog title="报名列表" v-model="memberDialogVisible" width="60%">
      <div>
        <el-table :data="members" size="small" style="width: 100%">
          <el-table-column prop="user.deptName" label="科室"> </el-table-column>
          <el-table-column prop="user.groupName" label="组别">
          </el-table-column>
          <el-table-column prop="user.ploNum" label="工号" width="180">
          </el-table-column>
          <el-table-column prop="user.ploName" label="姓名" width="180">
          </el-table-column>
        </el-table>
      </div>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="memberDialogVisible = false">关 闭</el-button>
        </div>
      </template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElLoading } from 'element-plus'
import { useDictStore } from '@/stores'
import { useRouter } from 'vue-router'
import {
  getItemList as fetchItemList,
  completeItem,
  examineArtical,
  setArticalStatus,
  updateArticalCategory,
  getItemMembers
} from '@/api/cyt/index.js'

const dictStore = useDictStore()
const router = useRouter()

const articals = ref([])
const loading = ref(false)
const currentPage = ref(1)
const total = ref(0)
const yearPicker = ref(new Date())

const queryInfo = reactive({
  dateYear: '', // 查询年份
  category: -1,
  topFlag: '',
  eliteFlag: '',
  onStage: '',
  compType: '',
  sortType: 'time',
  status: '',
  querytype: '',
  query: '',
  pageSize: 20,
  pageNum: 1
})

// 审核窗口
const examineDialogVisible = ref(false)
const examineForm = reactive({
  id: '',
  status: 0
})

// 结案窗口
const compDialogVisible = ref(false)
const compForm = reactive({
  id: '',
  compType: '',
  compGrade: ''
})

const categoryDialogVisible = ref(false)
const categoryForm = reactive({
  id: '',
  category: ''
})

const memberDialogVisible = ref(false)
const members = ref([])

const getItemList = async () => {
  try {
    loading.value = true
    if (yearPicker.value != null && yearPicker.value !== '') {
      queryInfo.dateYear = yearPicker.value.getFullYear()
    }
    const res = await fetchItemList(queryInfo.category, queryInfo)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    articals.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    console.error('获取项目列表失败:', error)
    ElMessage.error('获取项目列表失败')
  } finally {
    loading.value = false
  }
}

const viewArtical = (row) => {
  const url = router.resolve({ path: '/cyt/item/' + row.id })
  window.open(url.href, '_blank')
}

const compItem = (row) => {
  compForm.id = row.id
  compDialogVisible.value = true
}

const compSubmit = async () => {
  try {
    if (compForm.compType.length < 1 || compForm.compGrade < 1) {
      ElMessage.error('请选择结案类型及得分！')
      return
    }
    const res = await completeItem(compForm)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    ElMessage.success(res.msg)
    compDialogVisible.value = false
    getItemList()
  } catch (error) {
    console.error('项目结案失败:', error)
    ElMessage.error('项目结案失败')
  }
}

const compHandleClose = () => {
  compForm.id = ''
  compForm.compType = ''
  compForm.compGrade = ''
  compDialogVisible.value = false
}

const examineItem = (row) => {
  examineForm.id = row.id
  examineForm.status = row.status
  examineDialogVisible.value = true
}

const examineSubmit = async () => {
  try {
    const res = await examineArtical(examineForm)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    ElMessage.success(res.msg)
    examineDialogVisible.value = false
    getItemList()
  } catch (error) {
    console.error('审核项目失败:', error)
    ElMessage.error('审核项目失败')
  }
}

const examineHandleClose = () => {
  examineForm.id = ''
  examineForm.status = 0
  examineDialogVisible.value = false
}

const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  currentPage.value = page
  getItemList()
}

const setTopAndSoOn = async (type, id, val) => {
  try {
    let newVal = val === 1 ? 0 : 1
    const res = await setArticalStatus(type, id, newVal)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    ElMessage.success(res.msg)
    getItemList()
  } catch (error) {
    console.error('设置项目状态失败:', error)
    ElMessage.error('设置项目状态失败')
  }
}

const updateCategory = (row) => {
  categoryDialogVisible.value = true
  categoryForm.id = row.id
  categoryForm.category = row.category
}

const categorySubmit = async () => {
  try {
    const res = await updateArticalCategory(categoryForm)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    ElMessage.success(res.msg)
    categoryDialogVisible.value = false
    getItemList()
  } catch (error) {
    console.error('移动项目分类失败:', error)
    ElMessage.error('移动项目分类失败')
  }
}

const getSignList = async (row) => {
  try {
    const res = await getItemMembers(row.id)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    members.value = res.data
    memberDialogVisible.value = true
  } catch (error) {
    console.error('获取项目成员失败:', error)
    ElMessage.error('获取项目成员失败')
  }
}

onMounted(() => {
  getItemList()
})
</script>

<style lang="less" scoped>
.box-card {
  height: calc(100vh - 190px);
  overflow: auto;
}

.table-filter {
  padding: 10px;

  span {
    font-size: 12px;
    margin-left: 20px;
  }

  .el-select {
    width: 70%;
  }
}

.el-link {
  font-size: 12px;
}
</style>
