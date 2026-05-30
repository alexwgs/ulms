<template>
  <div class="login_container">
    <el-alert v-if="!authStore.serverStatus" show-icon :title="authStore.disableLoginTitle" type="error"
      :closable="false" center />

    <el-carousel class="carousel-custom" indicator-position="outside" height="500px" :interval="5000">
      <el-carousel-item v-for="item in authStore.indexImgs" :key="item.id" style="text-align: center">
        <img class="carousel-img" :src="fsURL + item.url" height="500px" width="100%" alt="轮播图" />
      </el-carousel-item>
    </el-carousel>

    <div class="copy-right">
      <p>武汉营运中心 业务管理室 CopyRight© 2020-2025, All Right Reserved</p>
    </div>

    <div class="login_box">
      <div class="avatar_box">
        <img src="@/assets/logo.png" alt="Logo" />
      </div>

      <el-form ref="loginFormRef" :model="loginForm" :rules="loginFormRules" class="login_form" label-width="0">
        <el-form-item prop="czyCode">
          <el-input v-model="loginForm.czyCode" prefix-icon="user" placeholder="员工编号" maxlength="7" />
        </el-form-item>

        <el-form-item prop="password">
          <el-input v-model="loginForm.password" type="password" maxlength="6" prefix-icon="lock" @keyup.enter="login"
            placeholder="同人员成长平台、业务辅助系统密码" />
        </el-form-item>

        <el-form-item class="btns">
          <el-button type="primary" @click="login">登录</el-button>
          <el-button type="info" @click="resetPasswordDialogVisible = true">
            修改密码
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <el-dialog title="修改密码" v-model="resetPasswordDialogVisible" width="40%">
      <el-form ref="resetFormRef" :model="resetForm" :rules="resetFormRules" label-width="80px">
        <el-form-item label="员工编号" prop="czyCode">
          <el-input v-model="resetForm.czyCode" maxlength="7" />
        </el-form-item>

        <el-form-item label="旧密码" prop="oPassword">
          <el-input type="password" v-model="resetForm.oPassword" maxlength="6" />
        </el-form-item>

        <el-form-item label="新密码" prop="nPassword">
          <el-input type="password" v-model="resetForm.nPassword" maxlength="6" />
        </el-form-item>

        <el-form-item label="确认密码" prop="cPassword">
          <el-input type="password" v-model="resetForm.cPassword" maxlength="6" />
        </el-form-item>
      </el-form>

      <template #footer>
        <el-button @click="resetPasswordDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="resetPasswordSubmit">确 定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { useUserStore } from '@/stores'
import { ElMessage } from 'element-plus'

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
  try {
    await loginFormRef.value.validate()
    const msg = await userStore.login(loginForm.value)
    router.push('/home')
  } catch (error) {
    // API 错误消息已在 request.js 响应拦截器中统一展示，此处仅需清空密码
    loginForm.value.password = ''
  }
}

const resetPasswordSubmit = async () => {
  try {
    await resetFormRef.value.validate()
    const msg = await authStore.resetPassword(resetForm.value)
    ElMessage.success(msg)
    resetPasswordDialogVisible.value = false
    resetForm.value = {
      czyCode: '',
      oPassword: '',
      nPassword: '',
      cPassword: ''
    }
  } catch (error) {
    if (error.message) {
      ElMessage.error(error.message)
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
  background-color: #fff;
  height: 100vh;
  position: relative;
  overflow: hidden;
}

.login_box {
  width: 400px;
  height: 300px;
  z-index: 999;
  background-color: rgba(255, 255, 255, 0.4);
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
    background-color: #fff;

    img {
      height: 100%;
      width: 100%;
      border-radius: 50%;
      background-color: #eee;
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

.el-alert {
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
