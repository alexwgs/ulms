import { useMenuStore } from '@/stores'

/**
 * 权限检查钩子（审计修复 F-M7）
 * 权限码集合来自后端菜单接口装配的 permissions（getWithMenuList 按角色装配），
 * 管理员角色在菜单资源配置了全部权限码资源，因此无需额外角色兜底。
 */
export function usePermission() {
  const menuStore = useMenuStore()

  /**
   * 检查用户是否拥有指定权限
   * @param {string} permission - 权限标识（如 edu:brush:report）
   * @returns {boolean} 是否拥有权限
   */
  const hasPermission = (permission) => {
    const perms = menuStore.permissions || []
    return perms.includes(permission)
  }

  return {
    hasPermission
  }
}
