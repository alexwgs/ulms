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
      let settled = false
      const done = (val) => {
        if (!settled) {
          settled = true
          resolve(val)
        }
      }
      const dialog = DialogPlugin.confirm({
        header: options.title || '提示',
        body: message,
        theme: options.type || 'warning',
        confirmBtn: options.confirmButtonText || '确定',
        cancelBtn: options.cancelButtonText || '取消',
        onConfirm: () => {
          done(true)
          dialog.destroy()
        },
        onCancel: () => {
          done(false)
          dialog.destroy()
        },
        // onClose 会被 TDesign 用作实际关闭逻辑，必须在这里显式销毁弹窗
        onClose: () => {
          done(false)
          dialog.destroy()
        }
      })
    })
  }

  return { confirm }
}

export default useConfirm
