<template>
  <div>
    <t-loading :loading="loadingFlag">
      <t-row :gutter="15">
      <t-col :span="mainSpan ? 9 : 12">
        <div class="study-title">
          {{ course.courseName
          }}<t-tag v-if="course.ifEval" theme="success" variant="light">需评价</t-tag>
          <t-tag v-if="course.ifExam" theme="danger" variant="light">需考试</t-tag>
          <div style="float: right">
<t-switch v-model="mainSpan" :label="['显示学习列表', '隐藏学习列表']"></t-switch>
          </div>
        </div>
        <t-divider></t-divider>
        <div v-show="videoFlag == null">
          <t-empty :image-size="200" description="请点击右侧【在线学习列表】开始学习"></t-empty>
        </div>
        <video v-show="videoFlag" type="video/mp4" style="max-height: 70vh; width: 100%;" ref="videoRef"
          controls="controls" preload :src="videoUrl"></video>
        <div v-if="videoFlag == false" style="width: 100%;">
          <!-- 翻页控件 -->
          <div v-if="pdfTotalPage > 0"
            style="margin-top: 10px; display: flex; justify-content: center; align-items: center; gap: 10px;">
            <t-button :disabled="pdfCurrentPage <= 1" @click="pdfCurrentPage--" size="small">上一页</t-button>
            <span>
              <t-input-number v-model="pdfCurrentPage" :min="1" :max="pdfTotalPage" size="small"
                controls-position="right" style="width: 100px;"></t-input-number>
              / {{ pdfTotalPage }}
            </span>
            <t-button :disabled="pdfCurrentPage >= pdfTotalPage" @click="pdfCurrentPage++" size="small">下一页</t-button>
          </div>
          <t-loading :loading="pdfLoading">
            <div style="width: 100%;">
            <!-- 设置VuePDF宽度100%，高度自适应 -->
            <VuePDF ref="pdfRef" :pdf="pdf" :page="pdfCurrentPage" text-layer annotation-layer class="pdf-viewer" />
          </div>
          </t-loading>
        </div>
        <t-row :gutter="15">
          <t-col :span="6"><t-button theme="primary" v-if="course.ifEval" style="display: block; width: 100%"
              @click="completeStudy('eval')">去评价</t-button></t-col>
          <t-col :span="6"><t-button theme="primary" v-if="course.ifExam" style="display: block; width: 100%"
              @click="completeStudy('exam')">去考试</t-button></t-col>
        </t-row>
      </t-col>
      <t-col :span="mainSpan ? 3 : 0">
        <t-card class="card academy-card">
          <template #header>
            <div class="clearfix">
              <span>在线学习列表</span>
              <t-tooltip class="item" content="进度条显示为视频进度，全部为绿色则为学习完成！" placement="left">
                <t-link size="small"><template #icon><DynamicIcon name="help-circle-filled" /></template></t-link>
              </t-tooltip>
            </div>
          </template>
          <div style="height: 300px; overflow: auto">
            <ul class="ul-list">
              <li v-for="(item, index) in video" :key="item.fileId" @click="playVideo(item, index)">
                <div class="text-trim file-title">
                  {{ index + 1 }}.{{ item.fileName }}
                </div>
                <div class="text-trim file-info">
                  {{ dayjs.duration(item.duration * 1000).format('HH:mm:ss') }}
                </div>
                <t-progress :percentage="parseFloat(
                  ((item.currTime * 100) / item.duration).toFixed(1)
                )
                  " :text-inside="true" :stroke-width="18" :color="progressColors"></t-progress>
              </li>
            </ul>
          </div>
        </t-card>
        <t-card class="card" style="margin-top: 10px">
          <template #header>
            <div class="clearfix">
              <span>学习资料</span>
            </div>
          </template>
          <div style="
              height: calc(100vh - 560px);
              overflow: auto;
              min-height: 80px;
            ">
            <ul class="ul-list">
              <li v-for="(item, index) in file" :key="item.fileId" @click="
                $global.downloadFile(
                  fsURL +
                  'upload/getFile/college-document/' +
                  item.fileId +
                  '.' +
                  item.extenName
                )
                ">
                <div class="text-trim file-title">
                  {{ index + 1 }}.{{ item.fileName }}
                </div>
                <div class="text-trim file-info">
                  {{ item.extenName }}
                </div>
                <div style="font-size: 12px">点击下载文件</div>
              </li>
            </ul>
          </div>
        </t-card>
      </t-col>
    </t-row>
    </t-loading>
    <Evaluate ref="evaluateRef"></Evaluate>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, onBeforeUnmount, nextTick, watch } from 'vue'
import { useRouter } from 'vue-router'
import { MessagePlugin } from 'tdesign-vue-next'
import { ArrowLeftIcon, ArrowRightIcon, HelpCircleFilledIcon } from 'tdesign-icons-vue-next'
import Evaluate from '@/views/font/college/evaluate/index.vue'
import { httpInstance } from '@/utils/request'
import dayjs from 'dayjs'
import duration from 'dayjs/plugin/duration'
dayjs.extend(duration)
import { VuePDF, usePDF } from '@tato30/vue-pdf'
import '@tato30/vue-pdf/style.css'

const router = useRouter()
// 展示类文件统一走 HTTPS 文件管理地址，避免混合内容被浏览器拦截
const fsURL = import.meta.env.VITE_FILE_BASE_URL

const mainSpan = ref(true)
const course = ref(JSON.parse(window.localStorage.getItem('course')) || {})
const video = ref([])
const file = ref([])
const progress = ref([])
const videoUrl = ref('')
const loadingFlag = ref(true)
const currentVideo = reactive({})
const currentTime = ref(0)
const videoFlag = ref(null)
const pdfCurrentPage = ref(1)
const pdfTotalPage = ref(1)
const timer = ref(null)

const videoRef = ref(null)
const pdfRef = ref(null)
const evaluateRef = ref(null)

// PDF 相关
const pdfSrc = ref('')
const { pdf, pages } = usePDF(pdfSrc)
const pdfLoading = ref(false)

// 监听 pdf 加载完成
watch(pdf, (newPdf) => {
  if (newPdf) {
    pdfLoading.value = false
  }
})

// 监听页数变化，更新总页数
watch(pages, (newPages) => {
  pdfTotalPage.value = newPages || 1
})

const progressColors = (percentage) => {
  return percentage >= 95 ? '#67c23a' : '#e6a23c'
}

const init = () => {
  return new Promise((resolve, reject) => {
    httpInstance
      .get(`college/course/file/progress/my/${course.value.courseId}`)
      .then((res) => {
        if (res.code !== 200) {
          MessagePlugin.error(res.msg)
          return
        }
        progress.value = res.data
        resolve()
      })
      .catch((error) => {
        reject(error)
      })
  })
}

const playVideo = (item, index) => {
  clearInterval(timer.value)
  videoUrl.value =
    fsURL + 'upload/getFile/college-media/' + item.fileId + '.' + item.extenName
  if (item.extenName === 'mp3' || item.extenName === 'mp4') {
    videoFlag.value = true
    nextTick(() => {
      if (videoRef.value) {
        videoRef.value.autoplay = true
        videoRef.value.currentTime = item.currTime
      }
    })
    Object.assign(currentVideo, item)
    currentVideo.totalTime = item.duration
    currentVideo.index = index
  } else {
    videoFlag.value = false
    // 重置页码和加载状态
    pdfCurrentPage.value = 1
    pdfLoading.value = true
    pdfSrc.value = videoUrl.value
    Object.assign(currentVideo, item)
    currentVideo.totalTime = item.duration
    currentVideo.index = index
    setTimer()
  }
}

const setItemProgress = (data) => {
  const rate = data.currTime / data.duration
  if (rate > 0.98) {
    return 100
  } else {
    return parseInt((data.currTime / data.duration).toFixed(2) * 100)
  }
}

const getProgress = () => {
  video.value = course.value.courseFile?.filter((e) => e.fileType === 1) || []
  video.value.forEach((item) => {
    item.currTime = 0
  })
  file.value = course.value.courseFile?.filter((e) => e.fileType === 2) || []
  init().then(() => {
    if (progress.value.length < 1) {
      video.value.forEach((item) => {
        item.currTime = 0
      })
    } else {
      video.value.forEach((item) => {
        const item2 = progress.value.filter((e) => e.fileId === item.fileId)
        if (item2.length > 0) {
          item.currTime = item2[0].currTime
          item.journo = item2[0].journo
        } else {
          item.currTime = 0
        }
      })
    }
    loadingFlag.value = false
  })
}

const updateProgress = async (record) => {
  const res = await httpInstance.put('college/course/file/progress/my', record)
  if (res.code !== 200) {
    MessagePlugin.error(res.msg)
    return
  }
  if (res.data != null) record.journo = res.data.journo
}

const completeStudy = async (type) => {
  const unStudy = video.value.filter((e) => e.currTime / e.duration <= 0.95)
  if (unStudy.length > 0) {
    MessagePlugin.warning(
      '您尚未完成所有课程的学习，所有课程进度条均为绿色方可完成学习！'
    )
    return
  }
  const res = await httpInstance.put(
    `college/study/file/complete/${course.value.courseId}`
  )
  if (res.code !== 200) {
    MessagePlugin.error(res.msg)
    return
  }
  MessagePlugin.success('已完成在线课程的学习！')
  if (type === 'eval') {
    evaluateRef.value?.show(course.value)
  } else if (type === 'exam') {
    sessionStorage.setItem('course', JSON.stringify(course.value))
    const routeUrl = router.resolve({
      path: '/college/course/exam'
    })
    window.open(routeUrl.href, 'newWindow')
    sessionStorage.removeItem('course')
  }
}

const setTimer = () => {
  timer.value = setInterval(function () {
    if (currentTime.value < currentVideo.currTime) {
      currentTime.value = currentVideo.currTime
    } else {
      if (currentVideo.duration <= currentTime.value + 5) {
        currentVideo.currTime = currentVideo.duration
        updateProgress(currentVideo)
        video.value.splice(currentVideo.index, 1, { ...currentVideo })
        return clearInterval(timer.value)
      }
      currentVideo.currTime = currentTime.value + 5
    }
    video.value.splice(currentVideo.index, 1, { ...currentVideo })
    updateProgress(currentVideo)
  }, 5000)
}

onMounted(() => {
  if (!course.value || !course.value.courseId) {
    MessagePlugin.warning('请从课程详情进入学习')
    loadingFlag.value = false
    return
  }
  let updateCont = 0

  if (videoRef.value) {
    videoRef.value.addEventListener('timeupdate', function () {
      const timeDIff = this.currentTime - currentVideo.currTime
      if (timeDIff < 0) {
      } else if (timeDIff < 2) {
        updateCont++
        if (updateCont >= 40) {
          updateProgress(currentVideo)
          updateCont = 0
        }
        if (updateCont % 5 === 0) {
          video.value.splice(currentVideo.index, 1, { ...currentVideo })
        }
        currentVideo.currTime = this.currentTime.toFixed(0)
      } else {
        MessagePlugin.error('该课程为首次学习，禁止快进！')
      }
      return false
    })

    videoRef.value.addEventListener('pause', function () {
      updateProgress(currentVideo)
      video.value.splice(currentVideo.index, 1, { ...currentVideo })
    })
  }

  window.addEventListener('beforeunload', (event) => {
    completeStudy(null)
    event.preventDefault()
  })

  getProgress()
})

onBeforeUnmount(() => {
  completeStudy(null)
  clearInterval(timer.value)
})
</script>

<style lang="less" scoped>
.text-trim {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

:deep(.pdf-viewer) {
  width: 100% !important;
}

:deep(.pdf-viewer canvas) {
  width: 100% !important;
  height: auto !important;
}

.card {
  :deep(.t-card__body) {
    padding: 0 10px 5px 10px;
    overflow: auto;
  }

  .ul-list {
    list-style-type: none;
    padding-left: 0;
    font-size: 14px;
    display: block;

    li {
      cursor: pointer;
      padding: 10px;
      margin-bottom: 5px;

      .file-title {
        width: 80%;
        display: inline-block;
      }

      .file-info {
        width: 20%;
        display: inline-block;
        color: var(--td-text-color-secondary);
        font-size: 12px;
      }
    }

    li:hover {
      box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.2);
    }
  }
}

.study-title {
  font-size: 20px;
  font-weight: 600;
  color: var(--academy-ink);
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 12px;
}

.card {
  border-radius: var(--academy-radius);
}

.card :deep(.t-card__header) {
  font-weight: 600;
  color: var(--academy-ink);
}
</style>
