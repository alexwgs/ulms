<template>
  <div class="wangeditor-container">
    <Toolbar
      :editor="editorRef"
      :defaultConfig="toolbarConfig"
      :mode="mode"
      class="wangeditor-toolbar"
    />
    <Editor
      v-model="valueHtml"
      :defaultConfig="editorConfig"
      :mode="mode"
      @onCreated="handleCreated"
      @onChange="handleChange"
      class="wangeditor-editor"
      :style="{ height: editorHeight + 'px' }"
    />
  </div>
</template>

<script setup>
import { ref, computed, watch, onBeforeUnmount, shallowRef } from 'vue'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import { MessagePlugin } from 'tdesign-vue-next'

const props = defineProps({
  modelValue: {
    type: String,
    default: ''
  },
  disabled: {
    type: Boolean,
    default: false
  },
  height: {
    type: [Number, String],
    default: 500
  },
  placeholder: {
    type: String,
    default: '请输入内容...'
  },
  mode: {
    type: String,
    default: 'default'
  }
})

const emit = defineEmits(['update:modelValue'])

// 编辑器实例，必须用 shallowRef
const editorRef = shallowRef()

// 内容 HTML
const valueHtml = ref(props.modelValue)

// 文件上传基础URL
const fileBaseUrl = import.meta.env.VITE_FILE_BASE_URL || ''

// 计算高度，最小 300px
const editorHeight = computed(() => {
  const height = parseInt(props.height)
  const minHeight = 300
  if (isNaN(height)) return 500
  return height < minHeight ? minHeight : height
})

// 监听外部值变化
watch(() => props.modelValue, (newValue) => {
  if (newValue !== valueHtml.value) {
    valueHtml.value = newValue
  }
})

// 监听内部值变化，触发 v-model
watch(valueHtml, (newValue) => {
  emit('update:modelValue', newValue)
})

// 工具栏配置
const toolbarConfig = {
  excludeKeys: [
    'insertVideo'
  ]
}

// 编辑器配置
const editorConfig = {
  placeholder: props.placeholder,
  readOnly: props.disabled,
  MENU_CONF: {
    uploadImage: {
      server: fileBaseUrl + 'upload/file/cytFile',
      fieldName: 'file',
      maxFileSize: 5 * 1024 * 1024, // 5MB
      maxNumberOfFiles: 10,
      allowedFileTypes: ['image/*'],
      metaWithUrl: true,
      // 自定义插入图片
      customInsert(res, insertFn) {
        // res 即服务端的返回结果
        if (res.code !== 200) {
          MessagePlugin.error(res.msg || '上传失败')
          return
        }
        // 从响应中获取图片 URL
        const path = res.data?.file?.path || res.data?.path
        if (!path) {
          MessagePlugin.error('图片上传失败：未获取到文件地址')
          return
        }
        const url = fileBaseUrl + path
        const alt = res.data?.file?.name || res.data?.name || '图片'
        const href = url
        // 插入图片
        insertFn(url, alt, href)
      },
      // 上传错误处理
      onError(file, err, res) {
        console.error('上传图片错误:', err, res)
        MessagePlugin.error('图片上传失败，请重试')
      },
      // 上传超时
      timeout: 30 * 1000 // 30秒
    },
    uploadVideo: {
      server: fileBaseUrl + 'upload/file/cytFile',
      fieldName: 'file',
      maxFileSize: 100 * 1024 * 1024, // 100MB
      maxNumberOfFiles: 5,
      allowedFileTypes: ['video/*'],
      timeout: 120 * 1000, // 120秒
      customInsert(res, insertFn) {
        if (res.code !== 200) {
          MessagePlugin.error(res.msg || '上传失败')
          return
        }
        const path = res.data?.file?.path || res.data?.path
        if (!path) {
          MessagePlugin.error('视频上传失败：未获取到文件地址')
          return
        }
        insertFn(fileBaseUrl + path, '')
      },
      onError(file, err, res) {
        console.error('上传视频错误:', err, res)
        MessagePlugin.error('视频上传失败，请重试')
      }
    }
  }
}

// 编辑器创建完成
const handleCreated = (editor) => {
  // 一定要用 Object.seal()，否则会报错
  editorRef.value = Object.seal(editor)

  // 如果设置了禁用，则禁用编辑器
  if (props.disabled) {
    editor.disable()
  }
}

// 编辑器内容变化
const handleChange = (editor) => {
  emit('update:modelValue', editor.getHtml())
}

// 组件销毁前销毁编辑器
onBeforeUnmount(() => {
  const editor = editorRef.value
  if (editor == null) return
  editor.destroy()
})
</script>

<style src="@wangeditor/editor/dist/css/style.css"></style>

<style scoped>
.wangeditor-container {
  border: 1px solid #ccc;
  z-index: 100;
}

.wangeditor-toolbar {
  border-bottom: 1px solid #ccc;
}

.wangeditor-editor {
  overflow-y: hidden;
}
</style>
