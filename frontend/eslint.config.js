import js from '@eslint/js'
import pluginVue from 'eslint-plugin-vue'
import globals from 'globals'

// 工程规范基线：宽松起步，先把基础设施立起来，后续逐步收紧为 error。
// 说明：
//  - 默认使用 ESLint recommended + Vue recommended；
//  - 现有代码量较大，console/未使用变量等暂降级为 warning；
//  - 格式类规则（属性换行、标签换行等）属风格偏好，暂关闭，避免噪音淹没真正问题；
//  - 后续重构过程中逐步将 warning 收敛为 error 并接入 CI。
export default [
  {
    ignores: ['ulms/**', 'node_modules/**', 'dist/**']
  },
  js.configs.recommended,
  ...pluginVue.configs['flat/recommended'],
  {
    languageOptions: {
      globals: {
        ...globals.browser,
        ...globals.node
      }
    },
    rules: {
      'no-console': 'off',
      'no-unused-vars': 'warn',
      'no-debugger': 'warn',
      'no-useless-assignment': 'warn',
      'vue/multi-word-component-names': 'off',
      'vue/no-v-html': 'warn',
      'vue/require-default-prop': 'off',
      'vue/no-v-text-v-html-on-component': 'off',
      'vue/no-unused-vars': 'warn',
      'vue/no-deprecated-router-link-tag-prop': 'warn',
      // 风格偏好规则暂关闭
      'vue/max-attributes-per-line': 'off',
      'vue/singleline-html-element-content-newline': 'off',
      'vue/multiline-html-element-content-newline': 'off',
      'vue/html-self-closing': 'off',
      'vue/first-attribute-linebreak': 'off',
      'vue/html-closing-bracket-newline': 'off',
      'vue/html-indent': 'off'
    }
  },
  {
    // TableColumn 是 CustomTable 的 stub 组件，模板有意为空（仅读取其 props），跳过根元素校验
    files: ['src/components/TableColumn.vue'],
    rules: {
      'vue/valid-template-root': 'off'
    }
  }
]
