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
          library: 'vue-next',
          // 审计修复：自动按需导入 tdesign-icons 图标组件（模板直接使用 <View/> 等）
          resolveIcons: true
        })
      ]
    }),
    Components({
      resolvers: [
        // 自动导入 TDesign 组件
        TDesignResolver({
          library: 'vue-next',
          resolveIcons: true
        })
      ]
    }),
    {
      // 离线/内网优化：TDesign 图标组件（t-icon name=）默认从 tdesign.gtimg.com CDN
      // 动态注入 SVG sprite（index.js），内网环境会加载失败导致图标缺失。
      // 将 CDN 地址替换为本地打包的 public/tdesign-icons.js（内容与 CDN 一致）。
      name: 'tdesign-icons-local',
      enforce: 'pre',
      transform(code, id) {
        if (id.includes('tdesign-icons-vue-next') && id.includes('svg-sprite')) {
          return code.replace(
            'https://tdesign.gtimg.com/icon/0.4.2/fonts/index.js',
            '/ulms/tdesign-icons.js'
          )
        }
        return null
      }
    },
  ],
  base: '/ulms/', //引入路径相当于webpack中的 baseUrl 或 publicPath
  optimizeDeps: {
    // 排除图标库的依赖预构建：esbuild 预构建会绕过下方 transform 插件（CDN URL 替换），
    // 排除后按源码加载，确保离线图标本地化生效
    exclude: ['tdesign-icons-vue-next']
  },
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
