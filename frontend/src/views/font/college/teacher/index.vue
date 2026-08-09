<template>
  <t-row style="margin-top: 10px">
    <t-col :span="12">
      <t-card class="academy-card">
        <div class="teacher-head">
          <h3 class="academy-section-title">明星讲师</h3>
          <t-link theme="primary" :underline="false" @click="router.push('/college/teacher')">更多</t-link>
        </div>
        <t-row :gutter="15">
          <t-col
            :span="2"
            v-for="(item, index) in teachers"
            :key="index"
            style="margin-top: 10px"
          >
            <div class="teacher-card" @click="teacherView(item)">
              <div class="teacher-photo">
                <div v-if="item.avatar == null" class="teacher-photo-empty">暂无照片</div>
                <img
                  v-else
                  :src="fsURL + 'upload/getFile/college-avatar/' + item.avatar"
                  alt=""
                />
              </div>
              <div class="teacher-info">
                <div class="teacher-name-line">
                  <span class="teacher-name">{{ item.ploName }}</span>
                  <span v-if="item.honor" class="teacher-honors">
                      <t-tag
                        v-for="(honor, hIndex) in item.honor.split('、')"
                        :key="hIndex"
                        size="small"
                        theme="danger"
                         variant="light">{{ honor }}</t-tag
                      >
                  </span>
                </div>
                <div class="teacher-intro">{{ cleanDisplayText(item.introduce) || '暂无简介' }}</div>
                </div>
            </div>
          </t-col>
        </t-row>
      </t-card>
    </t-col>
  </t-row>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { httpInstance } from '@/utils/request'
import { MessagePlugin } from 'tdesign-vue-next'
import { cleanDisplayText } from '@/utils/sanitize'

const router = useRouter()
// 展示类文件统一走 HTTPS 文件管理地址，避免混合内容被浏览器拦截
const fsURL = ref(import.meta.env.VITE_FILE_BASE_URL)
const teachers = ref([])

const getTopTeacher = async () => {
  try {
    const res = await httpInstance.get('college/teacher/top')
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    teachers.value = res.data
  } catch (error) {
    MessagePlugin.error('获取讲师列表失败')
  }
}

const teacherView = (teacher) => {
  window.localStorage.setItem('teacher', JSON.stringify(teacher))
  router.push({ name: 'college-teacher-detail', params: { id: teacher.ploNum } })
}

onMounted(() => {
  getTopTeacher()
})
</script>

<style lang="less" scoped>
.teacher-head {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.teacher-card {
  cursor: pointer;
  border: 1px solid var(--academy-line);
  border-radius: var(--academy-radius);
  overflow: hidden;
  background: var(--academy-surface);
  transition: transform 0.2s ease, box-shadow 0.2s ease;

  &:hover {
    transform: translateY(-3px);
    box-shadow: var(--academy-shadow);
  }
}

.teacher-photo {
  height: 160px;
  overflow: hidden;

  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    display: block;
  }
}

.teacher-photo-empty {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--academy-muted);
  background: var(--academy-bg);
  font-size: 13px;
}

.teacher-info {
  padding: 10px 12px 12px;
}

.teacher-name-line {
  display: flex;
  align-items: center;
  gap: 6px;
  overflow: hidden;
  white-space: nowrap;
}

.teacher-name {
  font-size: 15px;
  font-weight: 600;
  color: var(--academy-ink);
}

.teacher-honors {
  display: inline-flex;
  gap: 4px;
  overflow: hidden;
}

.teacher-intro {
  margin-top: 6px;
  font-size: 12px;
  color: var(--academy-muted);
  height: 34px;
  line-height: 17px;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}
</style>
