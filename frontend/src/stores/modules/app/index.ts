import { defineStore } from 'pinia'

import defaultSettings from '@/utils/settings.json'

const useAppStore = defineStore('app', {
  state: () => ({
    ...defaultSettings,
    activeFirstMenuId: null as number | string | null, // 当前选中的一级菜单ID
    activeFirstMenuComponent: null as string | null, // 当前选中的一级菜单组件类型
    showSubMenu: true // 是否显示子菜单
  }),
  getters: {
    appCurrentSetting(state: any) {
      return { ...state }
    },
    appDevice(state: any) {
      return state.device
    },
    appAsyncMenus(state: any) {
      return state.serverMenu
    },
    // 当前选中的一级菜单ID
    currentFirstMenuId(state: any) {
      return state.activeFirstMenuId
    },
    // 当前选中的一级菜单组件类型
    currentFirstMenuComponent(state: any) {
      return state.activeFirstMenuComponent
    },
    // 是否显示子菜单
    isShowSubMenu(state: any) {
      return state.showSubMenu
    },
    // 是否显示sidebar（仅当一级菜单组件为LAYOUT时显示）
    shouldShowSidebar(state: any) {
      return state.activeFirstMenuComponent === 'LAYOUT'
    }
  },

  actions: {
    // Update app settings
    updateSettings(partial: Record<string, any>) {
      // @ts-ignore
      this.$patch(partial)
    },

    // Change theme color
    toggleTheme(dark: boolean) {
      if (dark) {
        // @ts-ignore
        this.theme = 'dark'
        document.body.setAttribute('color-scheme', 'dark')
      } else {
        // @ts-ignore
        this.theme = 'light'
        document.body.removeAttribute('color-scheme')
      }
    },
    toggleDevice(device: string) {
      // @ts-ignore
      this.device = device
    },
    toggleMenu(value: boolean) {
      // @ts-ignore
      this.hideMenu = value
    },
    // 设置当前选中的一级菜单
    setActiveFirstMenu(menuId: number | string, hasChildren: boolean, component: string) {
      this.activeFirstMenuId = menuId
      this.activeFirstMenuComponent = component
      this.showSubMenu = !!hasChildren
    },
    // 切换子菜单显示状态
    toggleSubMenu() {
      this.showSubMenu = !this.showSubMenu
    }
  }
})

export default useAppStore
