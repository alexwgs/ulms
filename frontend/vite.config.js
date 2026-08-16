import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { TDesignResolver } from '@tdesign-vue-next/auto-import-resolver'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    AutoImport({
      // 自动导入 Vue 相关函数，如：ref, reactive, toRef 等
      imports: ['vue'],
      resolvers: [
        TDesignResolver({
          library: 'vue-next'
        })
      ]
    }),
    Components({
      resolvers: [
        // 自动导入 TDesign 组件
        TDesignResolver({
          library: 'vue-next'
        })
      ]
    }),
  ],
  base: '/ulms/', //引入路径相当于webpack中的 baseUrl 或 publicPath
  server: {
    historyApiFallback: true
  },
  build: {
    sourcemap: false,
    outDir: 'ulms',
    assetsDir: 'static/img/',
    rollupOptions: {
      output: {
        // 审计优化（F-M2）：移除 tdesign/echarts 的强制整包——TDesignResolver 已按需
        // 引入组件，强制 manualChunks 会把整个 7MB 库打进单 chunk 抵消 tree-shaking；
        // 仅保留 vue 生态 vendor 分组，其余交给 Rollup 自动分包
        manualChunks: {
          vendor: ['vue', 'vue-router', 'pinia', 'axios']
        },
        chunkFileNames: 'static/js/[name]-[hash].js',
        entryFileNames: 'static/js/[name]-[hash].js',
        assetFileNames: 'static/[ext]/[name]-[hash].[ext]'
      }
    }
  },
  esbuild: {
    drop: ['console', 'debugger']
  },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  }
})
