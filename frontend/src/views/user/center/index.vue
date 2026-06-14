<template>
  <div class="user-center">
    <!-- 用户信息头部卡片 -->
    <t-card class="profile-header" :bordered="false">
      <div class="profile-header-content">
        <div class="avatar-section">
          <!-- 头像上传：整个头像区域作为上传触发器 -->
          <t-upload
            ref="avatarUploadRef"
            :action="uploadAction"
            :headers="uploadHeaders"
            accept="image/*"
            :before-upload="beforeAvatarUpload"
            :request-method="uploadAvatarFile"
            :show-upload-list="false"
            @success="onAvatarUploadSuccess"
            @fail="onAvatarUploadFail"
            class="avatar-upload"
          >
            <div class="avatar-wrapper">
              <t-avatar size="120px" :image="avatarFullUrl" class="user-avatar" />
              <div class="avatar-overlay">
                <DynamicIcon name="image" size="24px" />
                <span class="overlay-text">更换头像</span>
              </div>
            </div>
          </t-upload>
          <div class="avatar-name">{{ userStore.ploName }}</div>
          <div class="avatar-role">
            <t-tag theme="primary" variant="light" size="small">{{ userStore.jobStatus || '员工' }}</t-tag>
          </div>
        </div>
        <t-divider layout="vertical" class="header-divider" />
        <div class="info-summary">
          <div class="summary-item">
            <span class="summary-label">
              <DynamicIcon name="user" class="summary-icon" />
              工号
            </span>
            <span class="summary-value">{{ userStore.ploNum }}</span>
          </div>
          <div class="summary-item">
            <span class="summary-label">
              <DynamicIcon name="building" class="summary-icon" />
              科室
            </span>
            <span class="summary-value">{{ userStore.deptName }}</span>
          </div>
          <div class="summary-item">
            <span class="summary-label">
              <DynamicIcon name="view-module" class="summary-icon" />
              组别
            </span>
            <span class="summary-value">{{ userStore.groupName }}</span>
          </div>
          <div class="summary-item">
            <span class="summary-label">
              <DynamicIcon name="calendar" class="summary-icon" />
              入职日期
            </span>
            <span class="summary-value">{{ userStore.inDate }}</span>
          </div>
          <div class="summary-item">
            <span class="summary-label">
              <DynamicIcon name="call" class="summary-icon" />
              手机
            </span>
            <span class="summary-value">{{ userStore.ploMobil || '未填写' }}</span>
          </div>
        </div>
      </div>
    </t-card>

    <!-- 主体内容区 -->
    <div class="main-content">
      <t-row :gutter="16">
        <!-- 左侧：详细信息 -->
        <t-col :span="8">
          <t-card title="基本信息" :bordered="false">
            <template #actions>
              <t-button theme="primary" variant="text" size="small" @click="refreshUserInfo">
                <template #icon>
                  <DynamicIcon name="refresh" />
                </template>
                刷新
              </t-button>
            </template>
            <t-descriptions :column="2" bordered>
              <t-descriptions-item label="姓名">{{ userStore.ploName }}</t-descriptions-item>
              <t-descriptions-item label="工号">{{ userStore.ploNum }}</t-descriptions-item>
              <t-descriptions-item label="科室">{{ userStore.deptName }}</t-descriptions-item>
              <t-descriptions-item label="组别">{{ userStore.groupName }}</t-descriptions-item>
              <t-descriptions-item label="批次">{{ userStore.batchGroup || '--' }}</t-descriptions-item>
              <t-descriptions-item label="职级">{{ userStore.jobLevel || '--' }}</t-descriptions-item>
              <t-descriptions-item label="岗位状态">{{ userStore.jobStatus || '--' }}</t-descriptions-item>
              <t-descriptions-item label="员工状态">
                <t-tag :theme="userStore.ploStatus === '00' ? 'success' : 'warning'" variant="light" size="small">
                  {{ userStore.ploStatus === '00' ? '在职' : userStore.ploStatus }}
                </t-tag>
              </t-descriptions-item>
              <t-descriptions-item label="入职日期">{{ userStore.inDate || '--' }}</t-descriptions-item>
              <t-descriptions-item label="手机号码">{{ userStore.ploMobil || '--' }}</t-descriptions-item>
              <t-descriptions-item label="科室编号">{{ userStore.deptNum || '--' }}</t-descriptions-item>
              <t-descriptions-item label="组别编号">{{ userStore.deptGroup || '--' }}</t-descriptions-item>
              <t-descriptions-item label="描述" :span="2">{{ userStore.ploDesc || '暂无描述' }}</t-descriptions-item>
            </t-descriptions>
          </t-card>
        </t-col>

        <!-- 右侧：安全设置 -->
        <t-col :span="4">
          <t-card title="安全设置" :bordered="false" class="security-card">
            <div class="security-item">
              <div class="security-item-header">
                <DynamicIcon name="lock-on" class="security-icon" />
                <span>账户密码</span>
              </div>
              <div class="security-item-desc">
                定期更换密码可以保护账户安全
              </div>
              <t-button theme="primary" variant="outline" size="small" @click="showPasswordDialog = true">
                修改密码
              </t-button>
            </div>
            <t-divider />
            <div class="security-item">
              <div class="security-item-header">
                <DynamicIcon name="secured" class="security-icon" />
                <span>登录信息</span>
              </div>
              <div class="security-item-desc">
                当前角色：{{ userStore.role }}
              </div>
            </div>
          </t-card>
        </t-col>
      </t-row>
    </div>

    <!-- 修改密码对话框 -->
    <t-dialog
      v-model:visible="showPasswordDialog"
      header="修改密码"
      width="480px"
      :confirm-btn="null"
      :cancel-btn="null"
      :close-on-overlay-click="false"
    >
      <t-form
        ref="passwordFormRef"
        :data="passwordForm"
        :rules="passwordRules"
        label-width="100px"
        @submit="handlePasswordSubmit"
      >
        <t-form-item label="原密码" name="oldPassword">
          <t-input
            v-model="passwordForm.oldPassword"
            type="password"
            placeholder="请输入原密码"
            clearable
          />
        </t-form-item>
        <t-form-item label="新密码" name="newPassword">
          <t-input
            v-model="passwordForm.newPassword"
            type="password"
            placeholder="请输入新密码（至少6位）"
            clearable
          />
        </t-form-item>
        <t-form-item label="确认密码" name="confirmPassword">
          <t-input
            v-model="passwordForm.confirmPassword"
            type="password"
            placeholder="请再次输入新密码"
            clearable
          />
        </t-form-item>
        <t-form-item>
          <t-space>
            <t-button theme="primary" type="submit" :loading="passwordSubmitting">
              确认修改
            </t-button>
            <t-button theme="default" variant="outline" @click="handlePasswordCancel">
              取消
            </t-button>
          </t-space>
        </t-form-item>
      </t-form>
    </t-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useUserStore } from '@/stores'
import { MessagePlugin } from 'tdesign-vue-next'
import { httpInstance } from '@/utils/request'
import { modifyPassword } from '@/api/auth'
import { encryptData } from '@/utils/cryptoService'
import { getToken } from '@/utils/auth'

const userStore = useUserStore()

// 文件服务基础URL（用于展示已上传的头像）
const fsBaseURL = import.meta.env.VITE_FILE_BASE_URL
// 上传接口URL（使用API基础URL）
const apiBaseURL = import.meta.env.VITE_API_BASE_URL

// 头像完整URL
const avatarFullUrl = computed(() => {
  if (!userStore.avatar) return ''
  // 如果头像已经是完整URL（http开头），直接使用；否则拼接文件服务URL
  if (userStore.avatar.startsWith('http')) {
    return userStore.avatar
  }
  return fsBaseURL + userStore.avatar
})

// 头像上传相关
const avatarUploadRef = ref(null)

// 上传目标URL
const uploadAction = computed(() => apiBaseURL + '/upload/avatar')

// 上传时携带认证头
const uploadHeaders = computed(() => ({
  Authorization: getToken() || ''
}))

// 上传前校验
const beforeAvatarUpload = (file) => {
  const isImage = file.raw.type.startsWith('image/')
  if (!isImage) {
    MessagePlugin.error('请选择图片文件')
    return false
  }
  const isLt2M = file.raw.size / 1024 / 1024 < 2
  if (!isLt2M) {
    MessagePlugin.error('头像文件大小不能超过 2MB')
    return false
  }
  return true
}

// 自定义上传方法（使用 httpInstance 以获得拦截器自动处理的认证和错误处理）
const uploadAvatarFile = (file) => {
  const formData = new FormData()
  formData.append('file', file.raw)
  // 使用相对路径，httpInstance 会自动拼接 baseURL
  return httpInstance
    .post('/upload/avatar', formData)
    .then((res) => {
      if (res.code === 200) {
        return {
          status: 'success',
          response: { path: res.path }
        }
      }
      return { status: 'fail', error: res.msg || '上传失败' }
    })
    .catch((err) => {
      return { status: 'fail', error: err.message || '上传失败' }
    })
}

// 头像上传成功 → 调用员工头像更新接口
const onAvatarUploadSuccess = async (context) => {
  const avatarPath = context.response?.path
  if (!avatarPath) {
    MessagePlugin.error('获取上传路径失败')
    return
  }

  try {
    const res = await httpInstance.post('/employee/avatar', {
      avatar: avatarPath
    })
    if (res.code === 200) {
      // 同步更新本地 store
      userStore.setInfo({ avatar: avatarPath })
      MessagePlugin.success('头像更新成功')
    } else {
      MessagePlugin.error(res.msg || '头像更新失败')
    }
  } catch (err) {
    MessagePlugin.error('头像保存失败，请重试')
  }
}

// 头像上传失败
const onAvatarUploadFail = () => {
  MessagePlugin.error('头像上传失败，请重试')
}

// 刷新用户信息
const refreshUserInfo = async () => {
  try {
    await userStore.info()
    MessagePlugin.success('用户信息刷新成功')
  } catch (err) {
    MessagePlugin.error('刷新失败，请稍后重试')
  }
}

// 密码修改
const showPasswordDialog = ref(false)
const passwordSubmitting = ref(false)
const passwordFormRef = ref(null)
const passwordForm = ref({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

// 确认密码验证
const validateConfirmPassword = (val) => {
  if (!val) return { result: false, message: '请确认新密码', type: 'error' }
  if (val !== passwordForm.value.newPassword) {
    return { result: false, message: '两次输入的密码不一致', type: 'error' }
  }
  return { result: true }
}

const passwordRules = {
  oldPassword: [
    { required: true, message: '请输入原密码', type: 'error' }
  ],
  newPassword: [
    { required: true, message: '请输入新密码', type: 'error' },
    { min: 6, message: '密码长度不能少于6位', type: 'error' }
  ],
  confirmPassword: [
    { required: true, message: '请确认新密码', type: 'error' },
    { validator: validateConfirmPassword }
  ]
}

// 提交密码修改
const handlePasswordSubmit = async ({ validateResult }) => {
  if (validateResult !== true) return

  passwordSubmitting.value = true
  try {
    const res = await modifyPassword({
      czyCode: userStore.ploNum,
      oPassword: encryptData(passwordForm.value.oldPassword),
      nPassword: encryptData(passwordForm.value.newPassword),
      cPassword: encryptData(passwordForm.value.confirmPassword)
    })
    if (res.code === 200) {
      MessagePlugin.success('密码修改成功，请重新登录')
      showPasswordDialog.value = false
      // 清除密码表单
      passwordForm.value = { oldPassword: '', newPassword: '', confirmPassword: '' }
      // 退出登录
      setTimeout(async () => {
        await userStore.logout()
        window.location.href = '/login'
      }, 1500)
    } else {
      MessagePlugin.error(res.msg || '密码修改失败')
    }
  } catch (err) {
    MessagePlugin.error('密码修改失败，请稍后重试')
  } finally {
    passwordSubmitting.value = false
  }
}

// 取消密码修改
const handlePasswordCancel = () => {
  showPasswordDialog.value = false
  passwordForm.value = { oldPassword: '', newPassword: '', confirmPassword: '' }
  passwordFormRef.value?.reset()
}

onMounted(() => {
  // 确保用户信息是最新的
  if (!userStore.ploNum) {
    userStore.info()
  }
})
</script>

<style lang="less" scoped>
.user-center {
  padding: 16px;

  .profile-header {
    margin-bottom: 16px;

    .profile-header-content {
      display: flex;
      align-items: center;
    }

    .avatar-section {
      display: flex;
      flex-direction: column;
      align-items: center;
      min-width: 180px;
      padding: 16px 0;

      // 上传组件包裹层 — 去除默认样式
      .avatar-upload {
        :deep(.t-upload) {
          display: flex;
        }
        :deep(.t-upload__trigger) {
          border: none;
          background: none;
          padding: 0;
        }
      }

      .avatar-wrapper {
        position: relative;
        cursor: pointer;

        .user-avatar {
          border: 3px solid var(--td-brand-color-light);
          transition: opacity 0.3s;
        }

        // CSS hover 控制遮罩显示（无需 JS）
        &:hover .avatar-overlay {
          opacity: 1;
        }

        &:hover .user-avatar {
          opacity: 0.6;
        }

        .avatar-overlay {
          position: absolute;
          top: 0;
          left: 0;
          width: 120px;
          height: 120px;
          border-radius: 50%;
          background-color: rgba(0, 0, 0, 0.5);
          color: #fff;
          display: flex;
          flex-direction: column;
          align-items: center;
          justify-content: center;
          gap: 4px;
          opacity: 0;
          transition: opacity 0.3s;
          pointer-events: none; // 遮罩不拦截点击，点击穿透到 t-upload

          .overlay-text {
            color: #fff;
            font-size: 12px;
          }
        }
      }

      .avatar-name {
        margin-top: 12px;
        font-size: 18px;
        font-weight: 600;
        color: var(--td-text-color-primary);
      }

      .avatar-role {
        margin-top: 6px;
      }
    }

    .header-divider {
      height: 120px;
      margin: 0 32px;
    }

    .info-summary {
      display: flex;
      flex-wrap: wrap;
      gap: 16px;
      flex: 1;

      .summary-item {
        width: calc(33.33% - 16px);
        min-width: 160px;

        .summary-label {
          display: flex;
          align-items: center;
          font-size: 13px;
          color: var(--td-text-color-secondary);
          margin-bottom: 4px;

          .summary-icon {
            margin-right: 6px;
            font-size: 14px;
          }
        }

        .summary-value {
          font-size: 15px;
          color: var(--td-text-color-primary);
          font-weight: 500;
          padding-left: 20px;
        }
      }
    }
  }

  .main-content {
    .security-card {
      .security-item {
        padding: 12px 0;

        .security-item-header {
          display: flex;
          align-items: center;
          font-size: 15px;
          font-weight: 500;
          color: var(--td-text-color-primary);
          margin-bottom: 8px;

          .security-icon {
            margin-right: 8px;
            color: var(--td-brand-color);
          }
        }

        .security-item-desc {
          font-size: 13px;
          color: var(--td-text-color-secondary);
          margin-bottom: 12px;
        }
      }
    }
  }
}

// 头像上传：去除 t-upload 默认触发器的边框和背景
.avatar-upload {
  :deep(.t-upload__trigger) {
    border: none !important;
    background: none !important;
    padding: 0 !important;
  }
}
</style>
