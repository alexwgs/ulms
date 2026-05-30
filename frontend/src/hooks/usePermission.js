import { useUserStore } from '@/stores'

/**
 * 权限检查钩子
 */
export function usePermission() {
  const userStore = useUserStore()

  /**
   * 检查用户是否拥有指定权限
   * @param {string} permission - 权限标识
   * @returns {boolean} 是否拥有权限
   */
  const hasPermission = (permission) => {
    // 当前项目权限系统较为简单，仅通过角色判断
    // 如果用户是管理员，则拥有所有权限
    const role = userStore.role || 'admin'
    return role === 'admin'
  }

  return {
    hasPermission
  }
}
