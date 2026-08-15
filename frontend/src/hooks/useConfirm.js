import { DialogPlugin } from 'tdesign-vue-next'

/**
 * 统一「删除 / 危险操作」确认弹窗
 * 用户点击确定返回 true，点击取消 / 关闭返回 false
 * @param {String} message 提示文案
 * @param {Object} [options] 可选：{ title, confirmButtonText, cancelButtonText, type }
 * @returns {Promise<Boolean>}
 */
export function useConfirm() {
  const confirm = (message, options = {}) => {
    return new Promise((resolve) => {
      const dialog = DialogPlugin.confirm({
        header: options.title || '提示',
        body: message,
        theme: options.type || 'warning',
        confirmBtn: options.confirmButtonText || '确定',
        cancelBtn: options.cancelButtonText || '取消',
        onConfirm: () => {
          // 确认按钮不会自动关闭弹窗，需手动销毁
          dialog.destroy()
          resolve(true)
        },
        onCancel: () => resolve(false),
        onClose: () => resolve(false)
      })
    })
  }

  return { confirm }
}

export default useConfirm
