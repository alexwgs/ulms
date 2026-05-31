<template>
  <div class="login_container">
    <t-alert v-if="!authStore.serverStatus" :message="authStore.disableLoginTitle" theme="error"
      :close="false" />

    <t-swiper class="carousel-custom" :height="500" :interval="5000" :navigation="{ showSlideBtn: 'never' }">
      <t-swiper-item v-for="item in authStore.indexImgs" :key="item.id">
        <img class="carousel-img" :src="fsURL + item.url" height="500px" width="100%" alt="轮播图" />
      </t-swiper-item>
    </t-swiper>

    <div class="copy-right">
      <p>武汉营运中心 业务管理室 CopyRight© 2020-2025, All Right Reserved</p>
    </div>

    <div class="login_box">
      <div class="avatar_box">
        <img src="@/assets/logo.png" alt="Logo" />
      </div>

      <t-form ref="loginFormRef" :data="loginForm" :rules="loginFormRules" class="login_form" label-width="0">
        <t-form-item name="czyCode">
          <t-input v-model="loginForm.czyCode" placeholder="员工编号" maxlength="10">
            <template #prefix-icon>
              <DynamicIcon name="user" />
            </template>
          </t-input>
        </t-form-item>

        <t-form-item name="password">
          <t-input v-model="loginForm.password" type="password" maxlength="10" @keyup.enter="login"
            placeholder="同人员成长平台、业务辅助系统密码">
            <template #prefix-icon>
              <DynamicIcon name="lock-on" />
            </template>
          </t-input>
        </t-form-item>

        <t-form-item class="btns">
          <t-button theme="primary" @click="login">登录</t-button>
          <t-button theme="default" @click="resetPasswordDialogVisible = true">
            修改密码
          </t-button>
        </t-form-item>
      </t-form>
    </div>

    <t-dialog header="修改密码" v-model:visible="resetPasswordDialogVisible" width="40%">
      <t-form ref="resetFormRef" :data="resetForm" :rules="resetFormRules" label-width="80px">
        <t-form-item label="员工编号" name="czyCode">
          <t-input v-model="resetForm.czyCode" maxlength="10" />
        </t-form-item>

        <t-form-item label="旧密码" name="oPassword">
          <t-input type="password" v-model="resetForm.oPassword" maxlength="10" />
        </t-form-item>

        <t-form-item label="新密码" name="nPassword">
          <t-input type="password" v-model="resetForm.nPassword" maxlength="10" />
        </t-form-item>

        <t-form-item label="确认密码" name="cPassword">
          <t-input type="password" v-model="resetForm.cPassword" maxlength="10" />
        </t-form-item>
      </t-form>

      <template #footer>
        <t-button theme="default" @click="resetPasswordDialogVisible = false">取 消</t-button>
        <t-button theme="primary" @click="resetPasswordSubmit">确 定</t-button>
      </template>
    </t-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { useUserStore } from '@/stores'
import { MessagePlugin } from 'tdesign-vue-next'

const router = useRouter()
const authStore = useAuthStore()
const userStore = useUserStore()

const fsURL = import.meta.env.VITE_FILE_MANAGE_BASE

const loginForm = ref({
  czyCode: '',
  password: '',
  rememberMe: false
})

const loginFormRules = {
  czyCode: [
    { required: true, message: '请输入用户名称', trigger: 'blur' },
    { max: 7, min: 6, message: '请正确输入6位或7位工号' }
  ],
  password: [
    {
      required: true,
      message: '请输入登录密码,同人员成长平台、业务辅助系统密码',
      trigger: 'blur'
    }
  ]
}

const resetPasswordDialogVisible = ref(false)
const resetForm = ref({
  czyCode: '',
  oPassword: '',
  nPassword: '',
  cPassword: ''
})

const resetFormRules = {
  czyCode: [
    { required: true, message: '请输入用户名称', trigger: 'blur' },
    { max: 7, min: 6, message: '请正确输入6位或7位工号' }
  ],
  oPassword: [
    { required: true, message: '请输入旧的登录密码', trigger: 'blur' }
  ],
  nPassword: [
    { required: true, message: '请输入新的登录密码', trigger: 'blur' }
  ],
  cPassword: [
    { required: true, message: '请输入再次输入新密码', trigger: 'blur' }
  ]
}

const loginFormRef = ref(null)
const resetFormRef = ref(null)

const login = async () => {
  // 防止 @keyup.enter 触发时 ref 尚未挂载
  if (!loginFormRef.value) return

  try {
    const valid = await loginFormRef.value.validate()
    if (valid !== true) return

    await userStore.login(loginForm.value)
    router.push('/home')
  } catch (error) {
    // API 错误消息已在 request.js 响应拦截器中统一展示，此处仅需清空密码
    loginForm.value.password = ''
  }
}

const resetPasswordSubmit = async () => {
  if (!resetFormRef.value) return

  try {
    const valid = await resetFormRef.value.validate()
    if (valid !== true) return

    const msg = await authStore.resetPassword(resetForm.value)
    MessagePlugin.success(msg)
    resetPasswordDialogVisible.value = false
    resetForm.value = {
      czyCode: '',
      oPassword: '',
      nPassword: '',
      cPassword: ''
    }
  } catch (error) {
    if (error.message) {
      MessagePlugin.error(error.message)
    }
  }
}

const ifIEdisable = () => {
  // 检测IE浏览器的现代方法
  const isIE = /* 你的IE检测逻辑 */ false
  if (isIE) {
    authStore.disableLoginTitle =
      '使用的IE浏览器会导致部分功能无法使用如桌面推送等……请切换使用谷歌浏览器（Chrome）或EDGE。'
    authStore.serverStatus = false
  }
}

onMounted(() => {
  ifIEdisable()
  authStore.getIndexImgData()
})
</script>

<style lang="less" scoped>
.login_container {
-color: #fff;
  height: 100vh;
  position: relative;
  overflow: hidden;
}

.login_box {
  width: 400px;
  height: 300px;
  z-index: 999;
-color: rgba(255, 255, 255, 0.4);
  border-radius: 3px;
  position: absolute;
  left: 78%;
  top: 50%;
  transform: translate(-50%, -50%);

  .avatar_box {
    height: 130px;
    width: 130px;
    border: 1px solid #eee;
    border-radius: 50%;
    padding: 10px;
    box-shadow: 0 0 10px #ddd;
    position: absolute;
    left: 50%;
    transform: translate(-50%, -50%);
-color: #fff;

    img {
      height: 100%;
      width: 100%;
      border-radius: 50%;
-color: #eee;
    }
  }
}

.login_form {
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}

.btns {
  display: flex;
  justify-content: flex-end;
}

.t-alert {
  border-radius: 0;
  margin-top: 0;
  position: relative;
  z-index: 1000;
}

.carousel-custom {
  margin-top: calc(50vh - 300px);
  position: absolute;
  width: 100%;
  min-width: 900px;
}

.copy-right {
  margin-top: -45px;
  position: absolute;
  width: 100%;
  top: 50%;
  transform: translateY(270px);

  p {
    position: absolute;
    bottom: 0;
    padding: 0;
    margin: 0;
    color: darkgray;
    font-size: 12px;
    right: 6%;
    height: 12px;
  }
}

div::-webkit-scrollbar {
  width: 0;
}
</style>
