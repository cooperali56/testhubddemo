import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src'),
      'components': resolve(__dirname, 'src/components'),
      'views': resolve(__dirname, 'src/views'),
      'utils': resolve(__dirname, 'src/utils'),
      'api': resolve(__dirname, 'src/api'),
      'assets': resolve(__dirname, 'src/assets'),
      'router': resolve(__dirname, 'src/router'),
      'store': resolve(__dirname, 'src/store'),
      'types': resolve(__dirname, 'src/types'),
      'hooks': resolve(__dirname, 'src/hooks'),
    },
  },
  css: {
    preprocessorOptions: {
      less: {
        javascriptEnabled: true,
        // additionalData: '@import "./src/assets/styles/variables.less";',
      }
    }
  },
  server: {
    port: 3000,
    strictPort: true,
    host: '0.0.0.0',
    open: true,
    cors: true,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false
      },
      '/auth': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false
      },
      '/test': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false
      },
      '/v3/api-docs': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false
      }
    }
  },
  build: {
    outDir: 'dist',
    assetsDir: 'assets',
    sourcemap: false,
    chunkSizeWarningLimit: 1000,
  }
}) 