import { defineStore } from 'pinia'
import { ref } from 'vue'
import { encryptData } from '@/utils/cryptoService'
import {
  getDictionary,
  modifyPassword,
  getIndexImg
} from '@/api/auth'

export const useAuthStore = defineStore('auth', () => {
  const serverStatus = ref(true)
  const disableLoginTitle = ref('数据服务器连接失败，请联系管理员！')
  const indexImgs = ref<any[]>([])

  const resetPassword = async (resetForm: Record<string, any>) => {
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
    const res = await getIndexImg()
    if (res.code !== 200) throw new Error(res.msg)
    indexImgs.value = res.data.data
  }

  return {
    serverStatus,
    disableLoginTitle,
    indexImgs,
    getDictionary,
    resetPassword,
    getIndexImgData
  }
})
