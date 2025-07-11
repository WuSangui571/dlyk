// 从 vue-router 中，导入 createRouter, createWebHistory 函数
import {createRouter, createWebHistory} from "vue-router";

// 定义变量
let router = createRouter({
    // 路由历史
    history: createWebHistory(),

    // 配置路由，是一个数组，里面可以配置多个路由
    routes: [
        {
            // 路由路径
            path: "/",
            // 路由路径所对应的页面
            component: () => import("../view/LoginView.vue"),
        },
        {
            // 路由路径
            path: "/dashboard",
            // 路由路径所对应的页面
            component: () => import("../view/DashboardView.vue"),
            // 配置子路由，
            children : [
                // 每一个子路由由一对 {}, 分割
                {
                    // 子路由路径，不能以斜杠开头，其实是 /dashboard/user
                    path: "user",
                    // 路由路径所对应的页面
                    component: () => import("../view/UserView.vue"),
                },

            ]
        },
    ],
})
// 导出创建的路由对象
export default router;