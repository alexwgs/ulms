<template>
  <div style="padding-left: calc((100vw - 1240px) / 2); max-width: 1200px">
    <el-card class="box-card">
      <template #header>
        <div class="clearfix">
          <span>讲师风采</span>
          <div style="float: right; width: 400px">
            <el-input
              placeholder="输入讲师姓名或工号查找"
              size="small"
              v-model="queryInfo.query"
              class="input-with-select"
            >
              <template #append>
                <el-button @click="getTeacherList">
                  <el-icon><Search /></el-icon>
                </el-button>
              </template>
            </el-input>
          </div>
        </div>
      </template>
      <el-form
        ref="queryInfoForm"
        :model="queryInfo"
        label-position="left"
        label-width="100px"
      >
        <el-form-item label="讲师类型">
          <el-radio-group
            v-model="queryInfo.skillType"
            size="small"
            @change="getTeacherList"
          >
            <el-radio-button label="">全部</el-radio-button>
            <el-radio-button
              v-for="item in skillType"
              :key="item.value"
              :label="item.value"
              v-show="item.status"
              >{{ item.label }}</el-radio-button
            >
          </el-radio-group>
        </el-form-item>
        <el-form-item label="身份">
          <el-radio-group
            v-model="queryInfo.skillName"
            size="small"
            @change="getTeacherList"
          >
            <el-radio-button label="">全部</el-radio-button>
            <el-radio-button
              v-for="item in skillName"
              :key="item.value"
              :label="item.value"
              v-show="item.status"
              >{{ item.label }}</el-radio-button
            >
          </el-radio-group>
        </el-form-item>
        <el-form-item label="排序">
          <el-link style="" underline="never" @click="sortTeacher('dataDate')"
            >身份时间<el-icon class="el-icon--right">
              <ArrowDown
                v-if="
                  queryInfo.order === 'dataDate' &&
                  queryInfo.orderType === 'desc'
                "
              />
              <ArrowUp
                v-else-if="
                  queryInfo.order === 'dataDate' &&
                  queryInfo.orderType === 'asc'
                "
              />
              <DCaret v-else /> </el-icon
          ></el-link>
          <el-link
            style="padding-left: 20px"
            underline="never"
            @click="sortTeacher('score')"
            >讲师评分<el-icon class="el-icon--right">
              <ArrowDown
                v-if="
                  queryInfo.order === 'score' && queryInfo.orderType === 'desc'
                "
              />
              <ArrowUp
                v-else-if="
                  queryInfo.order === 'score' && queryInfo.orderType === 'asc'
                "
              />
              <DCaret v-else /> </el-icon
          ></el-link>
          <el-link
            style="padding-left: 20px"
            underline="never"
            @click="sortTeacher('courseNum')"
            >授课数量<el-icon class="el-icon--right">
              <ArrowDown
                v-if="
                  queryInfo.order === 'courseNum' &&
                  queryInfo.orderType === 'desc'
                "
              />
              <ArrowUp
                v-else-if="
                  queryInfo.order === 'courseNum' &&
                  queryInfo.orderType === 'asc'
                "
              />
              <DCaret v-else /> </el-icon
          ></el-link>
          <el-link
            style="padding-left: 20px"
            underline="never"
            @click="sortTeacher('courseHour')"
            >授课时数<el-icon class="el-icon--right">
              <ArrowDown
                v-if="
                  queryInfo.order === 'courseHour' &&
                  queryInfo.orderType === 'desc'
                "
              />
              <ArrowUp
                v-else-if="
                  queryInfo.order === 'courseHour' &&
                  queryInfo.orderType === 'asc'
                "
              />
              <DCaret v-else /> </el-icon
          ></el-link>
        </el-form-item>
      </el-form>
    </el-card>
    <el-row :gutter="20">
      <div v-for="item in teachers" :key="item">
        <el-col :span="12">
          <el-card class="box-card">
            <div style="float: left">
              <div
                v-if="item.avatar == null"
                style="
                  height: 120px;
                  width: 120px;
                  text-align: center;
                  line-height: 120px;
                  border-radius: 5px;
                "
              >
                暂无照片
              </div>
              <img
                v-else
                width="120px"
                style="min-height: 120px; max-height: 120px; border-radius: 5px"
                :src="fsURL + 'upload/getFile/college-avatar/' + item.avatar"
              />
            </div>
            <div
              style="
                float: left;
                margin-left: 10px;
                width: 390px;
                cursor: pointer;
              "
              @click="teacherView(item)"
            >
              <el-tag effect="plain">{{ item.ploName }}</el-tag>
              <div style="margin-left: 10px; display: inline" v-if="item.honor">
                <el-tag
                  style="margin-right: 5px"
                  v-for="(honor, index) in item.honor.split('、')"
                  :key="index"
                  size="small"
                  type="danger"
                  >{{ honor }}</el-tag
                >
              </div>
              <div
                style="
                  font-size: 12px;
                  color: #999;
                  height: 50px;
                  margin-top: 5px;
                  overflow: hidden;
                "
              >
                简介：{{ item.introduce }}
              </div>
              <el-row :gutter="10" style="font-size: 12px; margin-top: 5px">
                <el-col :span="8">培训课程:{{ item.courseNum }}</el-col>
                <el-col :span="8">授课课时:{{ item.courseHour }}</el-col>
                <el-col :span="8"
                  ><el-rate
                    v-if="item.score > 0"
                    style="display: inline-block"
                    v-model="item.score"
                    disabled
                    text-color="#ff9900"
                    score-template="{value}"
                  ></el-rate
                  ><span v-else>暂无评分</span></el-col
                >
              </el-row>
            </div>
            <div style="width: 100%; margin-top: 130px; height: 130px">
              <el-empty
                :image-size="50"
                description="暂时无任何知识"
              ></el-empty>
            </div>
          </el-card>
        </el-col>
      </div>
    </el-row>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="queryInfo.pageNum"
      :page-sizes="[20, 40, 100, 200]"
      :page-size="queryInfo.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    ></el-pagination>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { httpInstance } from '@/utils/request'
import { Search, ArrowDown, ArrowUp, DCaret } from '@element-plus/icons-vue'

const router = useRouter()

const fsURL = import.meta.env.VITE_FILE_MANAGE_BASE

const queryInfo = reactive({
  orderType: 'desc',
  order: 'ploNum',
  queryType: 'search',
  query: '',
  skillType: '',
  skillName: '',
  status: 1,
  pageSize: 20,
  pageNum: 1
})

const teachers = ref([])
const total = ref(0)

const skillType = {
  0: { value: 0, label: '无身份', status: true },
  1: { value: 1, label: '特约', status: true },
  2: { value: 2, label: '预留', status: false },
  3: { value: 3, label: '初级', status: true },
  4: { value: 4, label: '中级', status: true },
  5: { value: 5, label: '高级', status: true }
}

const skillName = {
  1: { value: 1, label: '讲师', status: true },
  2: { value: 2, label: '辅导老师', status: false }
}

const getTeacherList = async () => {
  const res = await httpInstance.get('college/teacher/list', {
    params: queryInfo
  })
  if (res.code !== 200) {
    ElMessage.error(res.msg)
    return
  }
  teachers.value = res.data.list
  total.value = res.data.total
}

const sortTeacher = (order) => {
  queryInfo.order = order
  if (queryInfo.orderType === 'asc') queryInfo.orderType = 'desc'
  else queryInfo.orderType = 'asc'
  getTeacherList()
}

const handleSizeChange = (pageSize) => {
  queryInfo.pageSize = pageSize
  getTeacherList()
}

const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  getTeacherList()
}

const teacherView = (teacher) => {
  window.localStorage.setItem('teacher', JSON.stringify(teacher))
  router.push({ path: '/college/teacher/view' })
}

onMounted(() => {
  getTeacherList()
})
</script>

<style lang="less" scoped>
.text-trim {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.box-card {
  margin-bottom: 10px;
}
</style>
