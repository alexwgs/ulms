<template>
  <div>
    <t-row>
      <t-col :span="8">
        <t-row>
          <Calendar @open-todolist="handleOpenTodolist"></Calendar>
        </t-row>
      </t-col>

      <t-col :span="4">
        <t-row style="height: 250px">
          <t-card class="management-card" style="width: 100%">
            <template #header>
              <div class="clearfix">
                <span>系统公告</span>
              </div>
            </template>
            <CustomTable rowKey="id" :data="noticeList" width="100%" size="small" :stripe="true" :show-header="false">
              <TableColumn colKey="title" label="标题" :ellipsis="true">
                <template #default="scope">
                  <div v-if="scope.row.topFlag === 1">
                    <router-link class="link" style="color: red" tag="a" target="_blank"
                      :to="{ path: '/artical/view/' + scope.row.id }" rel="opener">{{ scope.row.title }}</router-link>
                  </div>
                  <div v-else>
                    <router-link class="link" tag="a" target="_blank" :to="{ path: '/system/view/' + scope.row.id }"
                      rel="opener">{{ scope.row.title }}</router-link>
                  </div>
                </template>
              </TableColumn>
            </CustomTable>
          </t-card>
        </t-row>
        <t-row>
          <t-card class="management-card" style="width: 100%">
            <template #header>
              <div class="clearfix">
                <span>快捷功能</span>
              </div>
            </template>
            <div class="scroll" style="height: calc(100vh - 515px)">
              <t-row :gutter="20" v-if="userId.substring(0, 1) != '6' || userId == '655012'">
                <t-col class="quick-menu-col" :span="4">
                  <t-button theme="primary" @click="punchCardInDialogVisible = true" variant="outline"
                   >上传</t-button>
                  <div class="quick-menu-font">上班打卡</div>
                </t-col>
                <t-col class="quick-menu-col" :span="4">
                  <t-button theme="default" @click="punchCardOutDialogVisible = true" variant="outline"
                   >下载</t-button>
                  <div class="quick-menu-font">下班打卡</div>
                </t-col>
                <t-col class="quick-menu-col" :span="4">
                  <t-button theme="success" @click="myPunchJourRef.show()" variant="outline"><template #icon><DynamicIcon name="ticket" /></template></t-button>
                  <div class="quick-menu-font">考勤记录</div>
                </t-col>
              </t-row>
              <t-row :gutter="20">
                <t-col class="quick-menu-col" :span="4" v-if="hasPermission('main:directorStatus:view')">
                  <t-button theme="primary" @click="directorStatusFlag = true" variant="outline">上传</t-button>
                  <div class="quick-menu-font">主任状态RT</div>
                </t-col>
                <t-col class="quick-menu-col" v-if="hasPermission('main:quickLink:cmbstudy')" :span="4">
                  <t-button theme="primary" @click="location('edu')" variant="outline"><template #icon><DynamicIcon name="education" /></template></t-button>
                  <div class="quick-menu-font">小招学霸2.0</div>
                </t-col>
                <t-col class="quick-menu-col" v-if="hasPermission('main:quickLink:a6college')" :span="4">
                  <t-button theme="primary" @click="location('college')" variant="outline"><template #icon><DynamicIcon name="education" /></template></t-button>
                  <div class="quick-menu-font">A6魔法学院</div>
                </t-col>
                <t-col class="quick-menu-col" v-if="hasPermission('main:quickLink:bpms')" :span="4">
                  <t-button theme="primary" @click="location('bpms')" variant="outline"><template #icon><DynamicIcon name="cloudy-day" /></template></t-button>
                  <div class="quick-menu-font">绩效平台</div>
                </t-col>
              </t-row>
              <QuickUrlTemplete area="main" :span="4"></QuickUrlTemplete>
            </div>
          </t-card>
        </t-row>
      </t-col>
    </t-row>

    <t-dialog header="上班打卡" v-model:visible="punchCardInDialogVisible" width="70%">
      <iframe v-if="!isSecurePage" :src="punchCardUrl('punch_card_in')" width="100%" height="470px"
        frameborder="0" scrolling="auto"></iframe>
      <div v-else class="punch-card-fallback">
        <p>内网打卡系统为 HTTP 地址，当前 HTTPS 安全页面无法直接嵌入，请在新窗口打开打卡。</p>
        <t-button theme="primary" variant="outline" @click="openPunchCard('punch_card_in')">在新窗口打开上班打卡</t-button>
      </div>
    </t-dialog>

    <t-dialog header="下班打卡" v-model:visible="punchCardOutDialogVisible" width="70%">
      <iframe v-if="!isSecurePage" :src="punchCardUrl('punch_card_out')" width="100%" height="470px"
        frameborder="0" scrolling="auto"></iframe>
      <div v-else class="punch-card-fallback">
        <p>内网打卡系统为 HTTP 地址，当前 HTTPS 安全页面无法直接嵌入，请在新窗口打开打卡。</p>
        <t-button theme="primary" variant="outline" @click="openPunchCard('punch_card_out')">在新窗口打开下班打卡</t-button>
      </div>
    </t-dialog>

    <MyPunchJour ref="myPunchJourRef"></MyPunchJour>
    <DirectorStatusRT :visiable="directorStatusFlag"></DirectorStatusRT>
    <Todolist ref="todolistRef"></Todolist>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { MessagePlugin } from 'tdesign-vue-next'
import { UploadIcon, DownloadIcon, TicketIcon, EducationIcon, CloudyDayIcon } from 'tdesign-icons-vue-next'
import { getSystemNotice, addLog } from '@/api/welcome/index.js'
import Calendar from './components/Calendar.vue'
import QuickUrlTemplete from './components/QuickUrlTemplete.vue'
import MyPunchJour from './components/MyPunchJour.vue'
import DirectorStatusRT from './components/DirectorStatusRT.vue'
import Todolist from './components/todolist.vue'

const router = useRouter()

const userId = ref('')

// 打卡系统为内网 HTTP 地址，HTTPS 页面下浏览器会拦截混合内容 iframe，改为提示并提供新窗口打开
const isSecurePage = window.location.protocol === 'https:'
const punchCardUrl = (type) =>
  `http://10.47.24.45/auxs/hr_manager_sys/mood_api/${type}.jsp?czy_code=${userId.value || ''}`
const openPunchCard = (type) => {
  window.open(punchCardUrl(type), '_blank')
}
const punchCardInDialogVisible = ref(false)
const punchCardOutDialogVisible = ref(false)
const directorStatusFlag = ref(false)
const noticeList = ref([])
const myPunchJourRef = ref(null)
const todolistRef = ref(null)

const handleOpenTodolist = (day) => {
  if (todolistRef.value) {
    todolistRef.value.calendarDialogVisible = true
    todolistRef.value.selectDay = day
    todolistRef.value.getTodoList()
  }
}

onMounted(async () => {
  const user = JSON.parse(window.localStorage.getItem('user'))
  if (user) {
    userId.value = user.ploNum
  }
  await getSystemNoticeList()
})

const getSystemNoticeList = async () => {
  try {
    const res = await getSystemNotice(9, 0)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    noticeList.value = res.data.list
  } catch (error) {
    console.error('获取系统公告失败', error)
  }
}

// 前端权限体系尚未建立，暂时保持放行（待接入后端权限码后收紧）
const hasPermission = () => true

const location = (system) => {
  if (system === 'ifms') {
    window.open('http://10.47.81.182/ifms', '_blank')
  } else if (system === 'ccms') {
    window.open('http://hrportal.cs/check.aspx', '_blank')
  } else if (system === 'smartDuty') {
    window.open('http://hrattendancebiz.paas.cmbchina.cn', '_blank')
  } else if (system === 'edu') {
    const routeUrl = router.resolve({
      path: '/edu'
    })
    window.open(routeUrl.href, '_blank')
  } else if (system === 'college') {
    const routeUrl = router.resolve({
      path: '/college'
    })
    window.open(routeUrl.href, '_blank')
  } else if (system === 'bpms') {
    window.open('http://10.47.81.182/bpms', '_blank')
  }
  addLog({ module: system, operate: 'quickLink' })
}
</script>

<style lang="less" scoped>
.punch-card-fallback {
  height: 470px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: var(--td-comp-margin-l);
  color: var(--td-text-color-secondary);
  background-color: var(--td-bg-color-secondarycontainer);
  border-radius: var(--td-radius-default);
}

.t-row {
  margin-bottom: 15px;
}

.boardcastBadge {
  margin-top: 5px;
}

.management-card {
  height: 100%;
}

.quick-menu-col {
  text-align: center;
  padding-top: 15px;
}

.quick-menu-font {
  font-size: 14px;
  color: var(--td-text-color-primary);
  padding-top: 5px;

  &:hover {
    color: var(--td-success-color);
  }
}

.quick-login-btn {
  background-color: var(--td-text-color-primary);
  cursor: pointer;

  &:hover {
background-color: var(--td-success-color);
  }
}

.link {
  text-decoration: none;
}

.scroll {
  overflow-y: auto;
  overflow-x: hidden;
  height: 100%;
}
</style>
