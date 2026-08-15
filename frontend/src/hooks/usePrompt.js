import { h, ref } from 'vue'
import { DialogPlugin, Input as TInput, MessagePlugin } from 'tdesign-vue-next'

/**
 * 输入弹窗（TDesign DialogPlugin 没有 prompt 快捷方法，这里自行实现）
 * 确定返回输入值（string），取消 / 关闭返回 null
 * @param {Object} [options]
 * @param {String} [options.title='提示']
 * @param {String} [options.placeholder]
 * @param {String} [options.defaultValue='']
 * @param {RegExp} [options.pattern] 输入校验正则，不通过时提示 errorMessage 且不关闭弹窗
 * @param {String} [options.errorMessage='输入内容不合法']
 * @param {String} [options.confirmText='确定']
 * @param {String|null} [options.cancelText='取消'] 传 null 则不显示取消按钮
 * @returns {Promise<String|null>}
 */
export function usePrompt() {
  const prompt = (options = {}) => {
    const {
      title = '提示',
      placeholder = '',
      defaultValue = '',
      pattern = null,
      errorMessage = '输入内容不合法',
      confirmText = '确定',
      cancelText = '取消'
    } = options
    return new Promise((resolve) => {
      const inputValue = ref(String(defaultValue))
      let settled = false
      const done = (val) => {
        if (!settled) {
          settled = true
          resolve(val)
        }
      }
      const dialog = DialogPlugin.confirm({
        header: title,
        body: () =>
          h('div', { style: 'padding: 6px 0 2px;' }, [
            h(TInput, {
              modelValue: inputValue.value,
              'onUpdate:modelValue': (v) => {
                inputValue.value = v
              },
              placeholder,
              autofocus: true,
              style: 'width: 100%;'
            })
          ]),
        confirmBtn: confirmText,
        cancelBtn: cancelText,
        onConfirm: () => {
          const value = inputValue.value
          if (pattern && !pattern.test(value)) {
            MessagePlugin.error(errorMessage)
            return
          }
          done(value)
          dialog.destroy()
        },
        onCancel: () => {
          done(null)
          dialog.destroy()
        },
        onClose: () => {
          done(null)
          dialog.destroy()
        }
      })
    })
  }

  return { prompt }
}

export default usePrompt
