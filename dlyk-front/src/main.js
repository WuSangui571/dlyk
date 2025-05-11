import { createApp } from 'vue'

// 引入element-plus
import ElementPlus from 'element-plus'
// 引入element-plus的CSS样式
import 'element-plus/dist/index.css'

import LoginView from './view/LoginView.vue'
createApp(LoginView).use(ElementPlus).mount('#app')
