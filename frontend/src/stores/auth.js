import { defineStore } from 'pinia'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { encryptData } from '@/utils/cryptoService'
import {
  getDictionary,
  login,
  modifyPassword,
  getIndexImg,
  setClientInfo
} from '@/api/auth'
export const useAuthStore = defineStore('auth', () => {
  const router = useRouter()

  const serverStatus = ref(true)
  const disableLoginTitle = ref('数据服务器连接失败，请联系管理员！')
  const indexImgs = ref([])

  function GetClientInfo() {
    const clientInfo = {
      os: 'unknown',
      osbit: '32',
      ie: '',
      chrome: '',
      clientInfo: ''
    }
    const sUserAgent = navigator.userAgent.toLocaleLowerCase()
    const isWin =
      navigator.platform === 'Win32' || navigator.platform === 'Windows'
    const isMac =
      navigator.platform === 'mac68k' ||
      navigator.platform === 'macppc' ||
      navigator.platform === 'macintosh' ||
      navigator.platform === 'macintel'
    clientInfo.clientInfo = navigator.appVersion
    if (isMac) clientInfo.os = 'Mac'
    var isUnix = navigator.platform === 'x11' && !isWin && !isMac
    if (isUnix) clientInfo.os = 'Unix'
    var isLinux = String(navigator.platform).indexOf('linux') > -1
    if (isLinux) clientInfo.os = 'Linux'
    if (isWin) {
      var isWinXP =
        sUserAgent.indexOf('windows nt 5.1') > -1 ||
        sUserAgent.indexOf('windows xp') > -1
      if (isWinXP) clientInfo.os = 'Windows XP'
      var isWin2003 =
        sUserAgent.indexOf('windows nt 5.2') > -1 ||
        sUserAgent.indexOf('windows 2003') > -1
      if (isWin2003) clientInfo.os = 'Windows 2003'
      var isWin7 =
        sUserAgent.indexOf('windows nt 6.1') > -1 ||
        sUserAgent.indexOf('windows 7') > -1
      if (isWin7) clientInfo.os = 'Windows 7'
      var isWin10 =
        sUserAgent.indexOf('windows nt 10') > -1 ||
        sUserAgent.indexOf('windows 10') > -1
      if (isWin10) clientInfo.os = 'Windows 10'
    }

    if (sUserAgent.indexOf('win64') > -1 || sUserAgent.indexOf('wow64') > -1) {
      clientInfo.osbit = '64位'
    } else {
      clientInfo.osbit = '32位'
    }

    if (
      sUserAgent.match(/msie/) != null ||
      sUserAgent.match(/trident/) != null
    ) {
      clientInfo.ie = 'IE'
    } else if (sUserAgent.match(/chrome/) != null) {
      clientInfo.chrome = 'chrome'
    } else if (sUserAgent.match(/safari/) != null) {
      clientInfo.chrome = 'Safari'
    }
    return clientInfo
  }

  const setLogin = async (loginForm) => {
    try {
      const encryptedForm = {
        ...loginForm,
        password: encryptData(loginForm.password)
      }
      const res = await login(encryptedForm)

      if (res.code !== 200) return
      localStorage.setItem('token', res.token)
      localStorage.setItem('userId', res.user.ploNum)
      localStorage.setItem('user', JSON.stringify(res.user))
      // 提交客户端信息
      await setClientInfo(GetClientInfo())

      router.push('/')
      return res.msg
    } catch (error) {
      throw error
    }
  }

  const resetPassword = async (resetForm) => {
    if (resetForm.nPassword !== resetForm.cPassword) {
      throw new Error('两次输入的密码不一致！')
    }

    const encryptedForm = {
      ...resetForm,
      oPassword: encryptData(resetForm.oPassword),
      nPassword: encryptData(resetForm.nPassword),
      cPassword: encryptData(resetForm.cPassword)
    }

    const res = await modifyPassword(encryptedForm)
    if (res.code !== 200) throw new Error(res.msg)

    return res.msg
  }

  const getIndexImgData = async () => {
    try {
      const res = await getIndexImg()
      if (res.code !== 200) throw new Error(res.msg)
      indexImgs.value = res.data.data
    } catch (error) {
      throw error
    }
  }

  return {
    serverStatus,
    disableLoginTitle,
    indexImgs,
    getDictionary,
    setLogin,
    resetPassword,
    getIndexImgData
  }
})
