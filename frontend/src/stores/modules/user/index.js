import { defineStore } from 'pinia'
import {
  getUserInfo,
  login as userLogin,
  logout as userLogout
} from '@/api/auth.js'
import { encryptData } from '@/utils/cryptoService.js'
import { setToken, clearToken } from '@/utils/auth'

const useUserStore = defineStore('user', {
  state: () => ({
    ploNum: undefined,
    ploName: undefined,
    deptName: undefined,
    batchGroup: undefined,
    deptNum: undefined,
    deptGroup: undefined,
    groupName: undefined,
    jobLevel: undefined,
    ploStatus: undefined,
    avatar: undefined,
    inDate: undefined,
    jobStatus: undefined,
    ploDesc: undefined,
    ploMobil: undefined,
    role: 'admin'
  }),

  getters: {
    userInfo(state) {
      return { ...state }
    }
  },

  actions: {
    switchRoles() {
      return new Promise((resolve) => {
        this.role = this.role === 'user' ? 'admin' : 'user'
        resolve(this.role)
      })
    },
    // Set user's information
    setInfo(partial) {
      this.$patch(partial)
    },

    // Reset user's information
    resetInfo() {
      this.$reset()
    },

    // Get user's information
    async info() {
      const res = await getUserInfo()
      this.setInfo(res.user)
    },

    // Login
    async login(loginForm) {
      try {
        loginForm.password = encryptData(loginForm.password)
        const res = await userLogin(loginForm)
        if (res.code !== 200) {
          throw new Error(res.msg || '登录失败')
        }
        setToken(res.token)
        localStorage.setItem('userId', res.user.ploNum)
        localStorage.setItem('user', JSON.stringify(res.user))
        this.setInfo(res.user)
        return res
      } catch (err) {
        clearToken()
        throw err
      }
    },
    // Logout
    async logout() {
      await userLogout()
      this.resetInfo()
      clearToken()
    }
  }
})

export default useUserStore
