// 从 vue 框架，导入 createApp 函数
import { createApp } from 'vue'

// 从 element-plus 中导入 ElementPlus 组件
import ElementPlus from 'element-plus'
// 导入 element-plus 的 CSS 样式，不需要 from 子句
import 'element-plus/dist/index.css'

// 从 router.js 中导入 router 组件
import router from "./router/router.js";

// 从 ./App.vue 页面导入 App 组件（一般文件名叫什么，组件名也叫什么）
import App from './App.vue'
let app = createApp(App);

// 导入 icons
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

// 利用上面所导入的 createApp 函数，创建一个 vue 应用，mount 是挂载到 #app 地方
app.use(ElementPlus).use(router).mount('#app')
