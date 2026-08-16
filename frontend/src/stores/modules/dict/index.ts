import { defineStore } from 'pinia'
import { getDictionary } from '@/api/auth.js'
import { isLogin } from '@/utils/auth'

const useDictStore = defineStore('dict', {
  state: () => ({
    dictList: {} as Record<string, any>
  }),

  getters: {
    dict(state: any) {
      return { ...state.dictList }
    }
  },
  actions: {
    setDict(partial: Record<string, any>) {
      this.dictList = partial || {}
    },
    async getDictList() {
      // 防止并发重复请求
      if (this._dictLoading) return
      this._dictLoading = true
      try {
        // 缓存读取逻辑
        const cache = localStorage.getItem('dictCache')
        if (cache) {
          try {
            this.dictList = JSON.parse(cache) || {}
            return
          } catch (e) {
            console.error('字典缓存解析失败', e)
          }
        }

        // 原始请求逻辑
        const res = await getDictionary()
        this.dictList = (res && res.data) || {}

        // 缓存更新
        localStorage.setItem('dictCache', JSON.stringify(this.dictList))
      } catch (e) {
        console.error('获取字典失败', e)
        if (!this.dictList) this.dictList = {}
      } finally {
        this._dictLoading = false
      }
    },
    getDictByNames(dictName: string, status: number | null) {
      // 判断当前dict存不存在，若不存在从服务器获取（免登录阅读：未登录不发起依赖登录的字典请求）
      if (!this.dictList || !Object.keys(this.dictList).length) {
        if (isLogin()) this.getDictList()
      }
      if (dictName == null || dictName == undefined || dictName == '') return []
      const dicts = this.dictList?.[dictName]
      if (!Array.isArray(dicts)) return []
      if (status) return dicts.filter((item: any) => item.status == 1)
      return dicts
    },
    getDictName(dictName: string, val: any) {
      if (!this.dictList || !Object.keys(this.dictList).length) {
        if (isLogin()) this.getDictList()
        // 未加载（免登录降级）：返回空串而非"未定义"，避免界面显示无意义标签
        return ''
      }
      const dicts = this.dictList?.[dictName]
      const value = dicts?.find((element: any) => element.code == val)
      return value ? value.codeval : '未定义'
    },
    getDictLabel(dictName: string, val: any) {
      if (!this.dictList || !Object.keys(this.dictList).length) {
        if (isLogin()) this.getDictList()
        return ''
      }
      const dicts = this.dictList?.[dictName]
      const value = dicts?.find((element: any) => element.code == val)
      return value ? value.codeval : '未定义'
    }
  }
})

export default useDictStore
