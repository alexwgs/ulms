import { DialogPlugin } from 'tdesign-vue-next'

/**
 * 统一「删除 / 危险操作」确认弹窗
 * 用户点击确定返回 true，点击取消 / 关闭返回 false
 * @param {String} message 提示文案
 * @param {Object} [options] 可选：{ title, confirmButtonText, cancelButtonText }
 * @returns {Promise<Boolean>}
 */
export function useConfirm() {
  const confirm = async (message, options = {}) => {
    try {
      await DialogPlugin.confirm(message, options.title || '提示', {
        confirmButtonText: options.confirmButtonText || '确定',
        cancelButtonText: options.cancelButtonText || '取消',
        type: options.type || 'warning'
      })
      return true
    } catch (error) {
      // TDesign 取消/关闭弹窗时 reject，视为用户放弃操作
      return false
    }
  }

  return { confirm }
}

export default useConfirm
