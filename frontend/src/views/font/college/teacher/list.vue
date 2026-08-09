<template>
  <div>
    <t-card class="academy-card">
      <div class="teacher-head">
        <h3 class="academy-section-title">讲师风采</h3>
        <div class="teacher-search">
            <t-input-adornment class="input-with-select">
              <template #append>
                <t-button @click="getTeacherList">
                  <t-icon><SearchIcon /></t-icon>
                </t-button>
              </template>
              <t-input
                placeholder="输入讲师姓名或工号查找"
                size="small"
                v-model="queryInfo.query"
              ></t-input>
            </t-input-adornment>
        </div>
      </div>
      <t-form
        ref="queryInfoForm"
        :data="queryInfo"
        layout="inline"
        class="teacher-filter"
      >
        <t-form-item label="讲师类型">
          <t-radio-group
            v-model="queryInfo.skillType"
            size="small"
            @change="getTeacherList"
          >
            <t-radio-button value="">全部</t-radio-button>
            <t-radio-button
              v-for="item in skillType"
              :key="item.value"
              :value="item.value"
              v-show="item.status"
              >{{ item.label }}</t-radio-button
            >
          </t-radio-group>
        </t-form-item>
        <t-form-item label="身份">
          <t-radio-group
            v-model="queryInfo.skillName"
            size="small"
            @change="getTeacherList"
          >
            <t-radio-button value="">全部</t-radio-button>
            <t-radio-button
              v-for="item in skillName"
              :key="item.value"
              :value="item.value"
              v-show="item.status"
              >{{ item.label }}</t-radio-button
            >
          </t-radio-group>
        </t-form-item>
        <t-form-item label="排序">
          <t-link style="" :underline="false" @click="sortTeacher('dataDate')"
            >身份时间<t-icon>
              <ArrowDownIcon
                v-if="
                  queryInfo.order === 'dataDate' &&
                  queryInfo.orderType === 'desc'
                "
              />
              <ArrowUpIcon
                v-else-if="
                  queryInfo.order === 'dataDate' &&
                  queryInfo.orderType === 'asc'
                "
              />
              <CaretDownIcon v-else />
              </t-icon
          ></t-link>
          <t-link
            style="padding-left: 20px"
            :underline="false"
            @click="sortTeacher('score')"
            >讲师评分<t-icon>
              <ArrowDownIcon
                v-if="
                  queryInfo.order === 'score' && queryInfo.orderType === 'desc'
                "
              />
              <ArrowUpIcon
                v-else-if="
                  queryInfo.order === 'score' && queryInfo.orderType === 'asc'
                "
              />
              <CaretDownIcon v-else />
              </t-icon
          ></t-link>
          <t-link
            style="padding-left: 20px"
            :underline="false"
            @click="sortTeacher('courseNum')"
            >授课数量<t-icon>
              <ArrowDownIcon
                v-if="
                  queryInfo.order === 'courseNum' &&
                  queryInfo.orderType === 'desc'
                "
              />
              <ArrowUpIcon
                v-else-if="
                  queryInfo.order === 'courseNum' &&
                  queryInfo.orderType === 'asc'
                "
              />
              <CaretDownIcon v-else />
              </t-icon
          ></t-link>
          <t-link
            style="padding-left: 20px"
            :underline="false"
            @click="sortTeacher('courseHour')"
            >授课时数<t-icon>
              <ArrowDownIcon
                v-if="
                  queryInfo.order === 'courseHour' &&
                  queryInfo.orderType === 'desc'
                "
              />
              <ArrowUpIcon
                v-else-if="
                  queryInfo.order === 'courseHour' &&
                  queryInfo.orderType === 'asc'
                "
              />
              <CaretDownIcon v-else />
              </t-icon
          ></t-link>
        </t-form-item>
      </t-form>
    </t-card>
    <t-row :gutter="20">
      <t-col :span="6" v-for="item in teachers" :key="item">
        <div class="teacher-card" @click="teacherView(item)">
            <div class="teacher-photo">
              <div v-if="item.avatar == null" class="teacher-photo-empty">暂无照片</div>
              <img
                v-else
                :src="fsURL + 'upload/getFile/college-avatar/' + item.avatar"
                alt=""
              />
            </div>
            <div class="teacher-body">
              <div class="teacher-name-line">
                <span class="teacher-name">{{ item.ploName }}</span>
                <div class="teacher-honors" v-if="item.honor">
                <t-tag
                  style="margin-right: 5px"
                  v-for="(honor, index) in item.honor.split('、')"
                  :key="index"
                  size="small"
                  theme="danger"
                  variant="light">{{ honor }}</t-tag>
                </div>
              </div>
              <div class="teacher-intro">简介：{{ cleanDisplayText(item.introduce) || '暂无简介' }}</div>
              <div class="teacher-stats">
                <span>培训课程 {{ item.courseNum }}</span>
                <span>授课课时 {{ item.courseHour }}</span>
                <span
                  ><t-rate
                    v-if="item.score > 0"
                    v-model="item.score"
                    disabled
                    text-color="#ff9900"
                    score-template="{value}"
                  ></t-rate
                  ><span v-else>暂无评分</span></span
                >
              </div>
            </div>
        </div>
      </t-col>
    </t-row>
    <t-pagination
      class="academy-pagination"
      @page-size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current="queryInfo.pageNum"
      :page-size-options="[20, 40, 100, 200]"
      :page-size="queryInfo.pageSize"

      :total="total"
    ></t-pagination>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { MessagePlugin } from 'tdesign-vue-next'
import { httpInstance } from '@/utils/request'
import { SearchIcon, ArrowDownIcon, ArrowUpIcon, CaretDownIcon } from 'tdesign-icons-vue-next'
import { cleanDisplayText } from '@/utils/sanitize'

const router = useRouter()

const fsURL = import.meta.env.VITE_FILE_BASE_URL

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
    MessagePlugin.error(res.msg)
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
  router.push({ name: 'college-teacher-detail', params: { id: teacher.ploNum } })
}

onMounted(() => {
  getTeacherList()
})
</script>

<style lang="less" scoped>
.teacher-head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
  margin-bottom: 8px;
}

.teacher-search {
  width: 380px;
}

.teacher-filter {
  padding: 12px 14px;
  background: var(--academy-bg);
  border-radius: 8px;
  margin-bottom: 18px;

  :deep(.t-form__item) {
    margin-right: 24px;
  }
}

.teacher-card {
  display: flex;
  gap: 14px;
  padding: 16px;
  margin-bottom: 16px;
  border: 1px solid var(--academy-line);
  border-radius: var(--academy-radius);
  background: var(--academy-surface);
  cursor: pointer;
  transition: transform 0.2s ease, box-shadow 0.2s ease;

  &:hover {
    transform: translateY(-3px);
    box-shadow: var(--academy-shadow);
  }
}

.teacher-photo {
  flex: none;
  width: 120px;
  height: 120px;
  border-radius: 10px;
  overflow: hidden;
  background: var(--academy-bg);

  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    display: block;
  }
}

.teacher-photo-empty {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--academy-muted);
  font-size: 13px;
}

.teacher-body {
  flex: 1;
  min-width: 0;
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.teacher-name-line {
  display: flex;
  align-items: center;
  gap: 8px;
  flex-wrap: wrap;
}

.teacher-name {
  font-size: 16px;
  font-weight: 600;
  color: var(--academy-ink);
}

.teacher-honors {
  display: inline-flex;
  gap: 4px;
  flex-wrap: wrap;
}

.teacher-intro {
  font-size: 12px;
  color: var(--academy-muted);
  line-height: 1.6;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.teacher-stats {
  display: flex;
  gap: 18px;
  margin-top: auto;
  font-size: 12px;
  color: var(--academy-muted);

  span {
    display: inline-flex;
    align-items: center;
  }
}
</style>
