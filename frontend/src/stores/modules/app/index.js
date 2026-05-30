import { ref } from 'vue'
import { defineStore } from 'pinia'

import defaultSettings from '@/utils/settings.json';

const useAppStore = defineStore('app', {

  state: () => ({
    ...defaultSettings,
    activeFirstMenuId: null, // 当前选中的一级菜单ID
    activeFirstMenuComponent: null, // 当前选中的一级菜单组件类型
    showSubMenu: true // 是否显示子菜单
  }),
  getters: {
    appCurrentSetting(state) {
      return { ...state };
    },
    appDevice(state) {
      return state.device;
    },
    appAsyncMenus(state) {
      return state.serverMenu;
    },
    // 当前选中的一级菜单ID
    currentFirstMenuId(state) {
      return state.activeFirstMenuId;
    },
    // 当前选中的一级菜单组件类型
    currentFirstMenuComponent(state) {
      return state.activeFirstMenuComponent;
    },
    // 是否显示子菜单
    isShowSubMenu(state) {
      return state.showSubMenu;
    },
    // 是否显示sidebar（仅当一级菜单组件为LAYOUT时显示）
    shouldShowSidebar(state) {
      return state.activeFirstMenuComponent === 'LAYOUT';
    }
  },

  actions: {
    // Update app settings
    updateSettings(partial) {
      // @ts-ignore-next-line
      this.$patch(partial);
    },

    // Change theme color
    toggleTheme(dark) {
      if (dark) {
        this.theme = 'dark';
        document.body.setAttribute('color-scheme', 'dark');
      } else {
        this.theme = 'light';
        document.body.removeAttribute('color-scheme');
      }
    },
    toggleDevice(device) {
      this.device = device;
    },
    toggleMenu(value) {
      this.hideMenu = value;
    },
    // 设置当前选中的一级菜单
    setActiveFirstMenu(menuId, hasChildren, component) {
      this.activeFirstMenuId = menuId;
      this.activeFirstMenuComponent = component;
      this.showSubMenu = !!hasChildren;
    },
    // 切换子菜单显示状态
    toggleSubMenu() {
      this.showSubMenu = !this.showSubMenu;
    }
  },
});

export default useAppStore;
