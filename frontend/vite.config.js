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
        manualChunks: {
          echarts: ['echarts'],
          'tdesign-vue-next': ['tdesign-vue-next'],
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
