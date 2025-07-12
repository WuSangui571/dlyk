<template>

  <el-container>
    <!--左：side bar-->
    <el-aside :width="isCollapse ?'64px':'200px'">
        <div class="menuTittle">@三桂管理系统</div>
        <!--其中的 :router="true" 代表 开启路由模式，可在 index 中添加路径-->
        <el-menu
            default-active="2"
            class="el-menu-vertical-demo"
            style="border-right: 0 px solid var(--el-menu-border-color);"
            :unique-opened="true"
            :router="true"
            :collapse="isCollapse"
            :collapse-transition="false">
          <el-sub-menu index="1">
            <template #title>
              <el-icon><OfficeBuilding /></el-icon>
              <span>市场</span>
            </template>

              <el-menu-item index="1-1">
                <el-icon><Notification /></el-icon>
                市场活动
              </el-menu-item>
              <el-menu-item index="1-2">
                <el-icon><DataAnalysis /></el-icon>
                市场统计
              </el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="2">
            <template #title>
              <el-icon><Operation /></el-icon>
              <span>线索</span>
            </template>
            <el-menu-item index="2-1">
              <el-icon><CreditCard /></el-icon>
              线索管理
            </el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="3">
            <template #title>
              <el-icon><User /></el-icon>
              <span>客户</span>
            </template>
            <el-menu-item index="3-1">
              <el-icon><CreditCard /></el-icon>
              客户管理
            </el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="4">
            <template #title>
              <el-icon><Money /></el-icon>
              <span>交易</span>
            </template>
            <el-menu-item index="4-1">
              <el-icon><CreditCard /></el-icon>
              交易管理
            </el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="5">
            <template #title>
              <el-icon><Box /></el-icon>
              <span>产品</span>
            </template>
            <el-menu-item index="5-1">
              <el-icon><CreditCard /></el-icon>
              产品管理
            </el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="6">
            <template #title>
              <el-icon><Reading /></el-icon>
              <span>字典</span>
            </template>
            <el-menu-item index="6-1">
              <el-icon><CreditCard /></el-icon>
              字典管理
            </el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="7">
            <template #title>
              <el-icon><UserFilled /></el-icon>
              <span>用户</span>
            </template>
            <!--<el-menu-item index="7-1">-->
            <el-menu-item index="/dashboard/user">
              <el-icon><CreditCard /></el-icon>
              用户管理
            </el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="8">
            <template #title>
              <el-icon><Setting /></el-icon>
              <span>系统</span>
            </template>
            <el-menu-item index="8-1">
              <el-icon><CreditCard /></el-icon>
              系统管理
            </el-menu-item>
          </el-sub-menu>

        </el-menu>
    </el-aside>
    <!--右：网站主体，包含 上 中 下 -->
    <el-container class="rightContainer">
      <!--上：head-->
      <el-header>
        <el-icon class="switch-button" @click="showMenu"><Switch /></el-icon>
        <el-dropdown :hide-on-click="false">
          <span class="el-dropdown-link">
            {{ user.loginAct }}
            <el-icon class="el-icon--right"><arrow-down/></el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item>个人资料</el-dropdown-item>
              <el-dropdown-item>修改密码</el-dropdown-item>
              <el-dropdown-item divided @click="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>
      <!--中：网站主要信息-->
      <el-main>
        <!-- router-view 标签就是根据路由去显示的-->
        <router-view/>
      </el-main>
      <!--下：版权信息-->
      <el-footer>Copyright © 2025 三桂管理系统 All rights reserved. <a href="https://beian.miit.gov.cn/" target="_blank">浙ICP备2025167176号</a></el-footer>
    </el-container>
  </el-container>

</template>

<script lang="ts">
import {defineComponent} from 'vue'
import {User} from "@element-plus/icons-vue";
import {doGet} from "../http/HttpRequest.js";
import {messageConfirm, messageTip, removeToken} from "../util/util";

export default defineComponent({
  name: "DashboardView",
  data(){
    return {
      // false 菜单不折叠。true 菜单折叠
      isCollapse: false,
      // 登录用户对象
      user:{},
      // name:""
    }
  },
  // vue 的生命周期中的一个函数钩子
  // mounted 函数代表页面渲染后执行 mounted 里的代码
  mounted(){
    // 加载当前登录用户
    this.loadLoginUser();
  },
  methods:{
    showMenu(){
      this.isCollapse = !this.isCollapse
    },
    loadLoginUser(){
      doGet("api/login/info",{}).then((resp) =>{
        this.user = resp.data.data
        // console.log(resp)
      })
    },
    logout(){
      doGet("/api/logout",{}).then(resp =>{
        if (resp.data.code === 200){
          // 退出成功
          removeToken();
          messageTip("退出成功","success")
          window.location.href = "/"
        } else {
          messageConfirm("退出异常，是否强制退出？").then(() =>{
            removeToken();
            window.location.href = "/"
          }).catch(() =>{
            messageTip("取消强制退出","warning")
          })
        }
      })
    }
  },
  components: {User}
})
</script>

<style scoped>
.el-dropdown-item{
  font-size: 12px;
}
.el-dropdown{
  float: right;
  height: 35px;
  line-height: 35px;
  font-size: 12px;
}
.switch-button{
  cursor:pointer;
}
.menuTittle{
  text-align: center;
  height: 35px;
  line-height: 35px;
  background-color: white;
}
.el-aside {
  background-color: #f9f9f9;
}
.el-header {
  background-color: white;
  height: 35px;
  line-height: 35px;
}
.el-main {
  background-color: #f9f9f9;
}
.el-footer {
  background-color: white;
  height: 30px;
  text-align: center;
  line-height: 30px;
}
.rightContainer {
  height: calc(100vh);
}
a {
  text-decoration: none;
  color: black;
}
</style>