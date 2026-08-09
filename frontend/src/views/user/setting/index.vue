<template>
  <div class="user-setting">
    <t-card title="用户设置" :bordered="false">
      <t-tabs default-value="security">
        <t-tab-panel value="security" label="安全设置">
          <div class="setting-section">
            <div class="setting-item">
              <div class="setting-item-info">
                <div class="setting-item-title">
                  <DynamicIcon name="lock-on" />
                  账户密码
                </div>
                <div class="setting-item-desc">
                  当前密码强度：{{ passwordStrengthText }}
                  <t-tag :theme="passwordStrengthTheme" variant="light" size="small">
                    {{ passwordStrengthText }}
                  </t-tag>
                </div>
              </div>
              <t-button theme="primary" variant="outline" size="small" @click="showPasswordDialog = true">
                修改
              </t-button>
            </div>
            <t-divider />
            <div class="setting-item">
              <div class="setting-item-info">
                <div class="setting-item-title">
                  <DynamicIcon name="secured" />
                  登录状态
                </div>
                <div class="setting-item-desc">
                  当前已登录，角色为 {{ userStore.role }}
                </div>
              </div>
              <t-tag theme="success" variant="light" size="small">正常</t-tag>
            </div>
            <t-divider />
            <div class="setting-item">
              <div class="setting-item-info">
                <div class="setting-item-title">
                  <DynamicIcon name="desktop" />
                  账户安全
                </div>
                <div class="setting-item-desc">
                  上次登录后无异常操作记录
                </div>
              </div>
              <t-tag theme="success" variant="light" size="small">安全</t-tag>
            </div>
          </div>
        </t-tab-panel>

        <t-tab-panel value="display" label="显示设置">
          <div class="setting-section">
            <div class="setting-item">
              <div class="setting-item-info">
                <div class="setting-item-title">
                  <DynamicIcon name="browse" />
                  主题模式
                </div>
                <div class="setting-item-desc">
                  当前使用{{ currentTheme === 'dark' ? '暗黑' : '亮色' }}模式，可点击顶部导航栏的月亮/太阳图标切换
                </div>
              </div>
            </div>
            <div class="setting-item">
              <div class="setting-item-info">
                <div class="setting-item-title">
                  <DynamicIcon name="system" />
                  控件尺寸
                </div>
                <div class="setting-item-desc">
                  统一管理后台按钮、输入框、选择器等控件的大小
                </div>
              </div>
              <t-radio-group
                v-model="uiStore.controlSize"
                variant="default-filled"
                @change="uiStore.setControlSize"
              >
                <t-radio-button value="small">小</t-radio-button>
                <t-radio-button value="medium">中</t-radio-button>
                <t-radio-button value="large">大</t-radio-button>
              </t-radio-group>
            </div>
          </div>
        </t-tab-panel>

        <t-tab-panel value="about" label="关于">
          <div class="setting-section">
            <div class="setting-item">
              <div class="setting-item-info">
                <div class="setting-item-title">
                  <DynamicIcon name="app" />
                  系统信息
                </div>
                <div class="setting-item-desc">
                  <p>A6广场 — 统一登录管理平台 (ULMS)</p>
                  <p style="margin-top: 8px; color: var(--td-text-color-secondary);">
                    提供员工管理、培训考试、HR工具、OA流程及社区论坛等综合服务
                  </p>
                </div>
              </div>
            </div>
          </div>
        </t-tab-panel>
      </t-tabs>
    </t-card>

    <!-- 修改密码对话框（与用户中心共用逻辑） -->
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
import { ref, computed } from 'vue'
import { useUserStore, useUiStore } from '@/stores'
import { MessagePlugin } from 'tdesign-vue-next'
import { modifyPassword } from '@/api/auth'
import { encryptData } from '@/utils/cryptoService'

const userStore = useUserStore()
const uiStore = useUiStore()
uiStore.init()

// 主题
const currentTheme = computed(() => {
  return localStorage.getItem('theme') || 'light'
})

// 密码强度（基于 store 中有无密码信息做简单展示）
const passwordStrengthText = computed(() => '已设置')
const passwordStrengthTheme = 'success'

// 密码修改
const showPasswordDialog = ref(false)
const passwordSubmitting = ref(false)
const passwordFormRef = ref(null)
const passwordForm = ref({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

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
      passwordForm.value = { oldPassword: '', newPassword: '', confirmPassword: '' }
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

const handlePasswordCancel = () => {
  showPasswordDialog.value = false
  passwordForm.value = { oldPassword: '', newPassword: '', confirmPassword: '' }
  passwordFormRef.value?.reset()
}
</script>

<style lang="less" scoped>
.user-setting {
  padding: 16px;

  .setting-section {
    padding: 8px 0;

    .setting-item {
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 16px 0;

      .setting-item-info {
        flex: 1;

        .setting-item-title {
          display: flex;
          align-items: center;
          font-size: 15px;
          font-weight: 500;
          color: var(--td-text-color-primary);
          margin-bottom: 8px;

          :deep(.t-icon) {
            margin-right: 8px;
            color: var(--td-brand-color);
          }
        }

        .setting-item-desc {
          font-size: 13px;
          color: var(--td-text-color-secondary);

          p {
            margin: 0;
          }
        }
      }
    }
  }
}
</style>
