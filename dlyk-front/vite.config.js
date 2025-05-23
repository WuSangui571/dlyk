import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  server:{
    host: '0.0.0.0', // ip地址
    port: 8080, // 设置服务启动端口
    open: true, // 设置服务启动时是否自动打开浏览器
  }
})
