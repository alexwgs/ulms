import { defineStore } from 'pinia'

const STORAGE_KEY = 'ulms-ui-settings'
const CONTROL_SIZES = ['small', 'medium', 'large']

/**
 * 全局界面设置（个人偏好，持久化到 localStorage）
 * - controlSize: 统一控件尺寸（按钮/输入框/选择器等），默认 small
 * 通过 html[data-control-size] 驱动 global.css 中的 CSS 变量重映射，
 * 使 TDesign 组件大小统一跟随该设置。
 */
const useUiStore = defineStore('ui', {
  state: () => ({
    controlSize: 'small' as string
  }),

  actions: {
    init() {
      try {
        const saved = JSON.parse(localStorage.getItem(STORAGE_KEY) || '{}')
        if (CONTROL_SIZES.includes(saved.controlSize)) {
          this.controlSize = saved.controlSize
        }
      } catch (error) {
        // 本地配置损坏时回退默认值
      }
      this.applyControlSize()
    },

    setControlSize(size: string) {
      if (!CONTROL_SIZES.includes(size)) return
      this.controlSize = size
      this.applyControlSize()
      localStorage.setItem(STORAGE_KEY, JSON.stringify({ controlSize: size }))
    },

    applyControlSize() {
      document.documentElement.dataset.controlSize = this.controlSize
    }
  }
})

export default useUiStore
