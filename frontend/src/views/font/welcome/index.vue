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
          <t-card class="box-card" style="width: 100%">
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
          <t-card class="box-card" style="width: 100%">
            <template #header>
              <div class="clearfix">
                <span>快捷功能</span>
              </div>
            </template>
            <div class="scroll" style="height: calc(100vh - 515px)">
              <t-row :gutter="20" v-if="userId.substring(0, 1) != '6' || userId == '655012'">
                <t-col class="quick-menu-col" :span="4">
                  <t-button theme="primary" @click="punchCardInDialogVisible = true" plain
                    shape="circle"><template #icon><DynamicIcon name="upload" /></template></t-button>
                  <div class="quick-menu-font">上班打卡</div>
                </t-col>
                <t-col class="quick-menu-col" :span="4">
                  <t-button theme="danger" @click="punchCardOutDialogVisible = true" plain
                    shape="circle"><template #icon><DynamicIcon name="download" /></template></t-button>
                  <div class="quick-menu-font">下班打卡</div>
                </t-col>
                <t-col class="quick-menu-col" :span="4">
                  <t-button theme="success" @click="myPunchJourRef.show()" plain shape="circle"><template #icon><DynamicIcon name="ticket" /></template></t-button>
                  <div class="quick-menu-font">考勤记录</div>
                </t-col>
              </t-row>
              <t-row :gutter="20">
                <t-col class="quick-menu-col" :span="4" v-if="hasPermission('main:directorStatus:view')">
                  <t-button theme="primary" @click="directorStatusFlag = true" plain shape="circle"><template #icon><DynamicIcon name="upload" /></template></t-button>
                  <div class="quick-menu-font">主任状态RT</div>
                </t-col>
                <t-col class="quick-menu-col" v-if="hasPermission('main:quickLink:cmbstudy')" :span="4">
                  <t-button theme="primary" @click="location('edu')" plain shape="circle"><template #icon><DynamicIcon name="education" /></template></t-button>
                  <div class="quick-menu-font">小招学霸2.0</div>
                </t-col>
                <t-col class="quick-menu-col" v-if="hasPermission('main:quickLink:a6college')" :span="4">
                  <t-button theme="primary" @click="location('college')" plain shape="circle"><template #icon><DynamicIcon name="education" /></template></t-button>
                  <div class="quick-menu-font">A6魔法学院</div>
                </t-col>
                <t-col class="quick-menu-col" v-if="hasPermission('main:quickLink:bpms')" :span="4">
                  <t-button theme="primary" @click="location('bpms')" plain shape="circle"><template #icon><DynamicIcon name="cloudy-day" /></template></t-button>
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
      <iframe v-if="hasPermission('main:punchcard:old')" :src="'http://10.47.24.45/auxs/hr_manager_sys/mood_api/punch_card_in.jsp?czy_code=' +
        userId
        " width="100%" height="470px" frameborder="0" scrolling="auto"></iframe>
      <iframe v-else :src="'http://10.47.24.45/auxs/hr_manager_sys/mood_api/punch_card_in.jsp?czy_code=' +
        userId
        " width="100%" height="470px" frameborder="0" scrolling="auto"></iframe>
    </t-dialog>

    <t-dialog header="下班打卡" v-model:visible="punchCardOutDialogVisible" width="70%">
      <iframe v-if="hasPermission('main:punchcard:old')" :src="'http://10.47.24.45/auxs/hr_manager_sys/mood_api/punch_card_out.jsp?czy_code=' +
        userId
        " width="100%" height="470px" frameborder="0" scrolling="auto"></iframe>
      <iframe v-else :src="'http://10.47.24.45/auxs/hr_manager_sys/mood_api/punch_card_out.jsp?czy_code=' +
        userId
        " width="100%" height="470px" frameborder="0" scrolling="auto"></iframe>
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
const punchCardInDialogVisible = ref(false)
const punchCardOutDialogVisible = ref(false)
const directorStatusFlag = ref(false)
const noticeList = ref([])
const myPunchJourRef = ref(null)
const todolistRef = ref(null)

const global = ref(window.__POWERED_BY_QIANKUN__ ? window.$global : null)

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

const hasPermission = (permission) => {
  try {
    if (global.value && global.value.hasPermission) {
      return global.value.hasPermission(permission)
    }
    return true
  } catch (error) {
    return true
  }
}

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
.t-row {
  margin-bottom: 15px;
}

.boardcastBadge {
  margin-top: 5px;
}

.box-card {
  height: 100%;
}

.quick-menu-col {
  text-align: center;
  padding-top: 15px;
}

.quick-menu-font {
  font-size: 14px;
  color: var(--td-font-color);
  padding-top: 5px;

  &:hover {
    color: #67c23a;
  }
}

.quick-login-btn {
-color: var(--td-font-color);
  cursor: pointer;

  &:hover {
-color: #67c23a;
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
