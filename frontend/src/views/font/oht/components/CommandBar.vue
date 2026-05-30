<template>
  <div>
    <el-alert v-if="ohtStore.ohtStatusInfo === 'default0'" style="margin-top: 0" title="操作BAR" type="info"
      :closable="false" show-icon>
      当前连接正常，如需求助请点击
      <el-button v-if="ohtStore.ohtRole.roleType === 0" type="success" size="small"
        @click="helpTypeDialogVisible = true">我要求助</el-button>
    </el-alert>

    <el-alert v-if="ohtStore.ohtStatusInfo === 'default1'" style="margin-top: 0" title="操作BAR" type="info"
      :closable="false" show-icon>
      当前连接正常，当前暂无举手订单！
    </el-alert>

    <el-alert v-if="ohtStore.ohtStatusInfo === 'helpWait'" style="margin-top: 0" title="正在发起求助" type="success"
      :closable="false" show-icon>
      当前求助订单已开始分派,当前等待 [
      <b style="color: red"> {{ ohtStore.ohtWaitSeconds }} </b> ] 秒！
      <el-button type="danger" size="small" @click="cancelDialogVisible = true" round>[取消求助]</el-button>
    </el-alert>

    <el-alert v-if="ohtStore.ohtStatusInfo === 'reconnectWait'" style="margin-top: 0" title="正在发起求助" type="success"
      :closable="false" show-icon>
      您在等待过程中出现断线重连,当前等待 [
      <b style="color: red"> {{ ohtStore.ohtWaitSeconds }} </b> ] 秒！
      <el-button type="danger" size="small" @click="cancelDialogVisible = true" round>[取消求助]</el-button>
    </el-alert>

    <el-alert v-if="ohtStore.ohtStatusInfo === 'unfinish'" style="margin-top: 0" title="请尽快结案" type="warning"
      :closable="false" show-icon>
      当前有尚未结案的订单，请先操作结案！
      <el-button type="primary" v-if="ohtStore.ohtRole.roleType === 0" size="small"
        @click="bcaseCompDialogVisible = true" round>[完成结案]</el-button>
      <el-button type="primary" v-if="ohtStore.ohtRole.roleType === 1" size="small"
        @click="pcaseCompDialogVisible = true" round>[完成结案]</el-button>
    </el-alert>

    <el-alert v-if="ohtStore.ohtStatusInfo === 'task'" style="margin-top: 0" title="请尽快接单" type="success"
      :closable="false" show-icon>
      当前有新的求助订单，当前已等待 [
      <b style="color: red"> {{ ohtStore.ohtWaitSeconds }} </b> ]
      秒，请尽快接单！
      <el-button v-if="ohtStore.ohtRole.roleType === 1" type="primary" size="small" @click="takeOrder()"
        round>[接单]</el-button>
      <el-button v-if="ohtStore.ohtRole.roleType === 1" type="danger" size="small" @click="refuseOrder()"
        round>[拒绝]</el-button>
    </el-alert>

    <el-alert v-if="
      ohtStore.ohtStatusInfo === 'linked0' ||
      ohtStore.ohtStatusInfo === 'otherComplete0'
    " style="margin-top: 0" title="建立对话" type="success" :closable="false" show-icon>
      当前已与[<b style="color: red">{{ ohtStore.currentCase.pickUser.ploNum }}/{{
        ohtStore.currentCase.pickUser.ploName
      }}</b>
      ]建立连接,分机号码：<b style="color: red">{{
        ohtStore.currentCase.pickExtn
      }}</b>，可以通过对话框开始对话！
      <b v-if="ohtStore.ohtStatusInfo === 'otherComplete0'"
        style="color: red">请注意：对方已结案，退出此对话！此时发送消息对方无法接收！</b><el-button type="primary" size="small"
        @click="bcaseCompDialogVisible = true" round>[完成结案]</el-button>
    </el-alert>

    <el-alert v-if="
      ohtStore.ohtStatusInfo === 'linked1' ||
      ohtStore.ohtStatusInfo === 'otherComplete1'
    " style="margin-top: 0" title="建立对话" type="success" :closable="false" show-icon>
      当前已与[<b style="color: red">{{ ohtStore.currentCase.buildUser.ploNum }}/{{
        ohtStore.currentCase.buildUser.ploName
      }}</b>
      ]建立连接,分机号码：<b style="color: red">{{
        ohtStore.currentCase.buildExtn
      }}</b>，可以通过对话框开始对话！
      <b v-if="ohtStore.ohtStatusInfo === 'otherComplete1'"
        style="color: red">请注意：对方已结案，退出此对话！此时发送消息对方无法接收！</b><el-button type="primary" size="small"
        @click="pcaseCompDialogVisible = true" round>[完成结案]</el-button>
    </el-alert>

    <el-alert v-if="ohtStore.ohtStatusInfo === 'disconnect'" style="margin-top: 0" title="断线重连" type="warning"
      :closable="false" show-icon>
      当前已与[<b style="color: red">{{ ohtStore.currentCase.pickUser.ploNum }}/{{
        ohtStore.currentCase.pickUser.ploName
      }}</b>
      ]断开连接,分机号码：<b style="color: red">{{
        ohtStore.currentCase.pickExtn
      }}</b>，您当前发送消息对方无法接收！
      <el-button v-if="ohtStore.ohtRole.roleType === 0" type="primary" size="small"
        @click="bcaseCompDialogVisible = true" round>[完成结案]</el-button>
      <el-button v-else-if="ohtStore.ohtRole.roleType === 1" type="primary" size="small"
        @click="pcaseCompDialogVisible = true" round>[完成结案]</el-button>
    </el-alert>

    <el-alert v-if="ohtStore.ohtStatusInfo === 'reconnect0'" style="margin-top: 0" title="断线重连" type="success"
      :closable="false" show-icon>
      当前已与[<b style="color: red">{{ ohtStore.currentCase.pickUser.ploNum }}/{{
        ohtStore.currentCase.pickUser.ploName
      }}</b>
      ]恢复连接,分机号码：<b style="color: red">{{
        ohtStore.currentCase.pickExtn
      }}</b>，可以通过对话框开始对话！
      <el-button type="primary" size="small" @click="bcaseCompDialogVisible = true" round>[完成结案]</el-button>
    </el-alert>

    <el-alert v-if="ohtStore.ohtStatusInfo === 'reconnect1'" style="margin-top: 0" title="断线重连" type="success"
      :closable="false" show-icon>
      当前已与[<b style="color: red">
        {{ ohtStore.currentCase.buildUser.ploNum }}/{{
          ohtStore.currentCase.buildUser.ploName
        }}</b>
      ]恢复连接,分机号码：<b style="color: red">{{
        ohtStore.currentCase.buildExtn
      }}</b>，可以通过对话框开始对话！
      <el-button type="primary" size="small" @click="pcaseCompDialogVisible = true" round>[完成结案]</el-button>
    </el-alert>
    <el-alert v-if="ohtStore.ohtStatusInfo === 'error'" style="margin-top: 0" title="求助系统发生问题" type="error"
      :closable="false" show-icon>
      您当前到角色或您的相关配置不正确，不可使用举手系统！
      <!-- <el-button type="primary" size="small" round>[反馈]</el-button> -->
    </el-alert>
    <!--------------------------------------------下面事操作对话框------------------------------------------------------>
    <el-dialog title="重要提示" v-model="helpTypeDialogVisible" width="60%" center>
      <!-- <span>请注意选择需要求助的类型，以下是各类型受理的业务范围：</span> -->
      <div>
        <div class="case-type1-info">
          <b style="color: #66b1ff; font-size: 14px">业务放行:</b>
          <ol>
            <li>仅限处理线上与客户通话中问题的情景</li>
            <li>
              业务主任权限的业务内容：查询卡号、敏感信息解锁、核身平台解锁 等
            </li>
          </ol>
        </div>
        <div class="case-type2-info">
          <b style="color: #f78989; font-size: 14px">紧急投诉:</b>
          <ol>
            <li>仅限处理线上与客户通话中问题的情景</li>
            <li>无法断线的抱怨问题</li>
            <li>客户坚持要求立即转接领导</li>
          </ol>
        </div>
      </div>
      <template #footer>
        <el-button v-for="item in dictStore.dict.oht_case_type" :key="item.id" size="small"
          :type="item.code == 1 ? 'primary' : 'danger'" @click="newCase(parseInt(item.code))">{{ item.codeval
          }}</el-button>
        <el-button size="small" @click="helpTypeDialogVisible = false">取 消</el-button>
      </template>
    </el-dialog>

    <el-dialog title="取消求助" v-model="cancelDialogVisible" width="60%">
      <div>
        <el-form :model="cancelForm" :rules="cancelFormRuleForm" ref="cancelFormRef" label-width="100px"
          class="demo-ruleForm">
          <el-form-item label="操作类型" prop="caseStatus">
            <el-select size="small" v-model="cancelForm.caseStatus" readonly>
              <el-option label="取消求助" :value="1"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="操作原因" prop="bcompType">
            <el-select size="small" v-model="cancelForm.bcompType">
              <el-option v-for="item in dictStore.getDictByNames('oht_case_bcompType').filter(
                (item) => item.code >= 20
              )" :key="item.id" :label="item.codeval" :value="parseInt(item.code)"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="操作备注" prop="bcompMemo">
            <el-input size="small" type="textarea" maxlength="250" v-model="cancelForm.bcompMemo"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <template #footer="scope">
        <el-button size="small" type="primary" @click="cancelSubmit">提 交</el-button>
        <el-button size="small" @click="cancelDialogVisible = false">取 消</el-button>
      </template>
    </el-dialog>

    <el-dialog title="案件结案" v-if="ohtStore.currentCase !== null" v-model="bcaseCompDialogVisible" width="60%">
      <el-divider content-position="left">案件信息</el-divider>
      <el-row :gutter="15">
        <el-col :span="12">案件类型：{{
          dictStore.getDictLabel('oht_case_type', ohtStore.currentCase.caseType)
        }}</el-col>
        <el-col :span="12">发起时间：{{ ohtStore.currentCase.dataTime }}</el-col>
        <el-col :span="12" v-if="ohtStore.currentCase.pickUser">接单人：{{ ohtStore.currentCase.pickUser.ploNum }}/{{
          ohtStore.currentCase.pickUser.ploName
        }}</el-col>
        <el-col :span="12">接单人分机：{{ ohtStore.currentCase.pickExtn }}</el-col>
        <el-col :span="12" v-if="ohtStore.currentCase.pickUser">科室组别：{{ ohtStore.currentCase.pickUser.deptName }}/{{
          ohtStore.currentCase.pickUser.groupName
        }}</el-col>
        <el-col :span="12"></el-col>
      </el-row>
      <el-divider content-position="left">结案表单</el-divider>
      <div>
        <el-form :model="bcompForm" :rules="bcompFormRuleForm" ref="bcompFormRef" label-width="100px"
          class="demo-ruleForm">
          <el-form-item label="结案类型" prop="bcompType">
            <el-select size="small" v-model="bcompForm.bcompType">
              <el-option v-for="item in dictStore.getDictByNames('oht_case_bcompType').filter(
                (item) => item.code < 20
              )" :key="item.id" :label="item.codeval" :value="parseInt(item.code)"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="案件备注" prop="bcompMemo">
            <el-input size="small" type="textarea" maxlength="250" v-model="bcompForm.bcompMemo"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <template #footer="scope">
        <el-button size="small" type="primary" @click="bcaseCompSubmit">提 交</el-button>
        <el-button size="small" @click="bcaseCompDialogVisible = false">取 消</el-button>
      </template>
    </el-dialog>
    <el-dialog title="案件结案" v-if="ohtStore.currentCase !== null" v-model="pcaseCompDialogVisible" width="60%">
      <el-divider content-position="left">案件信息</el-divider>
      <el-row :gutter="15">
        <el-col :span="12">案件类型：{{
          dictStore.getDictLabel('oht_case_type', ohtStore.currentCase.caseType)
        }}</el-col>
        <el-col :span="12">发起时间：{{ ohtStore.currentCase.dataTime }}</el-col>
        <el-col :span="12" v-if="ohtStore.currentCase.buildUser">建案人：{{ ohtStore.currentCase.buildUser.ploNum }}/{{
          ohtStore.currentCase.buildUser.ploName
        }}</el-col>
        <el-col :span="12">建案人分机：{{ ohtStore.currentCase.buildExtn }}</el-col>
        <el-col :span="12" v-if="ohtStore.currentCase.buildUser">科室组别：{{ ohtStore.currentCase.buildUser.deptName }}/{{
          ohtStore.currentCase.buildUser.groupName
        }}</el-col>
        <el-col :span="12"></el-col>
      </el-row>
      <el-divider content-position="left">结案表单</el-divider>
      <div>
        <el-form :model="pcompForm" :rules="pcompFormRuleForm" ref="pcompFormRef" label-width="100px"
          class="demo-ruleForm">
          <el-form-item label="客户信息" prop="custId">
            <el-input size="small" maxlength="20" v-model="pcompForm.custId" placeholder="ID\证件号\手机号"></el-input>
          </el-form-item>
          <el-form-item label="结案类型" prop="pcompType">
            <el-select size="small" v-model="pcompForm.pcompType">
              <el-option v-for="item in dictStore.dict.oht_case_pcompType" :key="item.id" :label="item.codeval"
                :value="parseInt(item.code)"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="案件备注" prop="pcompMemo">
            <el-input size="small" type="textarea" maxlength="250" v-model="pcompForm.pcompMemo"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <template #footer="scope">
        <el-button size="small" type="primary" @click="pcaseCompSubmit">提 交</el-button>
        <el-button size="small" @click="pcaseCompDialogVisible = false">取 消</el-button>
      </template>
    </el-dialog>
    <el-dialog title="拒绝求助" v-model="refuseDialogVisible" width="60%">
      <div>
        <el-form :model="refuseForm" :rules="refuseFormRuleForm" ref="refuseFormRef" label-width="100px"
          class="demo-ruleForm">
          <el-form-item label="操作类型" prop="taskStatus">
            <el-select size="small" v-model="refuseForm.taskStatus" readonly>
              <el-option label="拒绝求助" :value="2"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="拒绝原因" prop="memo">
            <el-input size="small" type="textarea" maxlength="250" v-model="refuseForm.memo"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <template #footer="scope">
        <el-button size="small" type="primary" @click="refuseSubmit">提 交</el-button>
        <el-button size="small" @click="refuseDialogVisible = false">取 消</el-button>
      </template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, reactive, watch, onMounted, onBeforeUnmount } from 'vue'
import { ElMessage } from 'element-plus'
import { useOhtStore, useDictStore, useWsStore } from '@/stores'
// import webSocketService from './websocket'

// 导入 store
const ohtStore = useOhtStore()
const dictStore = useDictStore()
const wsStore = useWsStore()
// 响应式数据
const helpTypeDialogVisible = ref(false)
const bcaseCompDialogVisible = ref(false)
const pcaseCompDialogVisible = ref(false)
const cancelDialogVisible = ref(false)
const refuseDialogVisible = ref(false)

// 表单数据
const bcompForm = reactive({
  action: 'bcompCase',
  caseId: null,
  bcompType: 1,
  bcompMemo: ''
})
const pcompForm = reactive({
  action: 'pcompCase',
  caseId: null,
  custId: '',
  pcompType: 1,
  pcompMemo: ''
})
const cancelForm = reactive({
  action: 'cancel',
  caseId: null,
  caseStatus: 1,
  bcompType: null,
  bcompMemo: ''
})
const refuseForm = reactive({
  action: 'refuse',
  caseId: null,
  taskStatus: 2,
  memo: ''
})

// 表单验证规则
const bcompFormRuleForm = reactive({
  bcompType: [{ required: true, message: '请选择结案类型', trigger: 'change' }],
  bcompMemo: [{ required: true, message: '请填写案件备注', trigger: 'blur' }]
})

const pcompFormRuleForm = reactive({
  custId: [
    {
      required: true,
      message: '请输入客户ID或身份证或手机号',
      maxlength: 20,
      trigger: 'blur'
    }
  ],
  pcompType: [{ required: true, message: '请选择结案类型', trigger: 'change' }],
  pcompMemo: [{ required: true, message: '请填写案件备注', trigger: 'blur' }]
})

const cancelFormRuleForm = reactive({
  caseStatus: [
    { required: true, message: '请选择案件类型', trigger: 'change' }
  ],
  bcompType: [{ required: true, message: '请选择结案类型', trigger: 'change' }]
})

const refuseFormRuleForm = reactive({
  taskStatus: [
    { required: true, message: '请选择案件类型', trigger: 'change' }
  ],
  memo: [{ required: true, message: '请输入拒绝原因！', trigger: 'blur' }]
})

// 表单引用
const bcompFormRef = ref(null)
const pcompFormRef = ref(null)
const cancelFormRef = ref(null)
const refuseFormRef = ref(null)

// 新的求助案件
const newCase = (caseType) => {
  if (wsStore.connectionStatus !== 'connected') {
    ElMessage.error('WebSocket 未连接，无法发起求助')
    return
  }
  helpTypeDialogVisible.value = false
  wsStore.sendMessage({
    modal: 'oht', type: 'command',
    content: { action: 'build', caseType }
  })
}

// 提交表单（async，修复 validate 回调异步导致 dialog 不关闭的 bug）
const submitForm = async (formRef, cmd) => {
  try {
    await formRef.value.validate()
    if (wsStore.connectionStatus === 'connected') {
      wsStore.sendMessage(cmd)
      return true
    }
    return false
  } catch {
    return false
  }
}

// 提交结案表单（建案人）
const bcaseCompSubmit = async () => {
  bcompForm.caseId = ohtStore.currentCase.caseId
  const cmd = { modal: 'oht', type: 'command', content: { ...bcompForm } }
  const ok = await submitForm(bcompFormRef, cmd)
  if (ok) {
    bcaseCompDialogVisible.value = false
    Object.assign(bcompForm, {
      action: 'bcompCase', caseId: null, bcompType: 1, bcompMemo: ''
    })
  }
}

// 提交结案表单（接单人）
const pcaseCompSubmit = async () => {
  pcompForm.caseId = ohtStore.currentCase.caseId
  const cmd = { modal: 'oht', type: 'command', content: { ...pcompForm } }
  const ok = await submitForm(pcompFormRef, cmd)
  if (ok) {
    pcaseCompDialogVisible.value = false
    Object.assign(pcompForm, {
      action: 'pcompCase', caseId: null, custId: '', pcompType: 1, pcompMemo: ''
    })
  }
}

// 提交取消求助表单
const cancelSubmit = async () => {
  cancelForm.caseId = ohtStore.currentCase.caseId
  const cmd = { modal: 'oht', type: 'command', content: { ...cancelForm } }
  const ok = await submitForm(cancelFormRef, cmd)
  if (ok) {
    cancelDialogVisible.value = false
    Object.assign(cancelForm, {
      action: 'cancel', caseId: null, caseStatus: 1, bcompType: null, bcompMemo: ''
    })
  }
}

// 提交拒绝求助表单
const refuseSubmit = async () => {
  if (!ohtStore.waittingCase[0]) {
    ElMessage.error('订单已不存在!')
    return
  }
  refuseForm.caseId = ohtStore.waittingCase[0].caseId
  const cmd = { modal: 'oht', type: 'command', content: { ...refuseForm } }
  const ok = await submitForm(refuseFormRef, cmd)
  if (ok) {
    refuseDialogVisible.value = false
    Object.assign(refuseForm, {
      action: 'refuse', caseId: null, taskStatus: 2, memo: ''
    })
  }
}

// 接单
const takeOrder = () => {
  if (!ohtStore.waittingCase[0]) return ElMessage.error('订单已不存在!')
  wsStore.sendMessage({
    modal: 'oht', type: 'command',
    content: {
      action: 'takeOrder',
      caseId: ohtStore.waittingCase[0].caseId
    }
  })
}

// 拒绝订单
const refuseOrder = () => {
  refuseDialogVisible.value = true
}

// 监听 ohtStatusInfo 变化
watch(
  () => ohtStore.ohtStatusInfo,
  (newType) => {
    if (newType === 'reconnectWait' && ohtStore.currentCase) {
      ohtStore.ohtTimeCountting(ohtStore.currentCase.buildTime)
    } else if (newType === 'helpWait' && ohtStore.currentCase) {
      ohtStore.ohtTimeCountting(ohtStore.currentCase.buildTime)
    } else if (newType === 'task' && ohtStore.waittingCase[0]) {
      ohtStore.ohtTimeCountting(ohtStore.waittingCase[0].buildTime)
    }
  }
)
</script>
<style lang="less" scoped>
.case-type1-info {
  box-shadow: 0 2px 12px 0 rgba(64, 158, 255, 0.3);
  border-radius: 4px;
  // height: 150px;
  padding: 10px;
  text-align: left;
}

.case-type2-info {
  box-shadow: 0 2px 12px 0 rgba(245, 108, 108, 0.3);
  border-radius: 4px;
  // height: 150px;
  margin-top: 10px;
  padding: 10px;
  text-align: left;
}

.el-alert__title {
  font-size: 16px;
}

.el-alert__description {
  font-size: 14px;
}
</style>
