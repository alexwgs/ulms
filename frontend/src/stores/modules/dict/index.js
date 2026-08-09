import { defineStore } from 'pinia'
import { getDictionary } from '@/api/auth.js'

const useDictStore = defineStore('dict', {
  state: () => ({
    dictList: {}
  }),

  getters: {
    dict(state) {
      return { ...state.dictList }
    }
  },
  actions: {
    setDict(partial) {
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
    /**
     * 获取指定字典的列表
     * @param {*} dictName 获取字典名称
     * @param { 0| 1| null} status 0- 失效 1- 有效 空值-全部
     * @returns {Array}
     */
    getDictByNames(dictName, status) {
      // 0- 失效 1- 有效 空值-全部
      // 判断当前dict存不存在，若不存在从服务器获取
      if (!this.dictList || !Object.keys(this.dictList).length) {
        this.getDictList()
      }
      if (dictName == null || dictName == undefined || dictName == '') return []
      const dicts = this.dictList?.[dictName]
      if (!Array.isArray(dicts)) return []
      if (status) return dicts.filter((item) => item.status == 1)
      return dicts
    },
    /**
     * 通过指定的字典名称和值匹配中文名称
     * @param {String} dictName 字典名称
     * @param {*} val 字典值
     * @returns {String}
     */
    getDictName(dictName, val) {
      // 判断当前dict存不存在，若不存在从服务器获取
      if (!this.dictList || !Object.keys(this.dictList).length) {
        this.getDictList()
      }
      const dicts = this.dictList?.[dictName]
      let value = dicts?.find((element) => element.code == val)
      return value ? value.codeval : '未定义'
    },
    getDictLabel(dictName, val) {
      // 判断当前dict存不存在，若不存在从服务器获取
      if (!this.dictList || !Object.keys(this.dictList).length) {
        this.getDictList()
      }
      const dicts = this.dictList?.[dictName]
      let value = dicts?.find((element) => element.code == val)
      return value ? value.codeval : '未定义'
    }
  }
})

export default useDictStore
