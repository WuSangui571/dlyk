// 从 vue 框架，导入 createApp 函数
import { createApp } from 'vue'

// 引入 element-plus
import ElementPlus from 'element-plus'
// 引入 element-plus 的 CSS 样式
import 'element-plus/dist/index.css'

// 从 ./view/LoginView.vue 页面导入 LoginView
import LoginView from './view/LoginView.vue'
// 利用上面所导入的 createApp 函数，创建一个 vue 应用，mount 是挂载到 #app 地方
createApp(LoginView).use(ElementPlus).mount('#app')
