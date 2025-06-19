<template>
  <div class="common-layout">
    <el-container>
      <!--左-->
      <el-aside>
        <img src="../assets/background.jpg" alt="背景">
        <p class="imgTitle">
          欢迎来到三桂管理系统
        </p>
      </el-aside>

      <!--右-->
      <el-main>
        <div class="loginTitle">欢迎登录</div>
        <!--登录表单-->
        <el-form ref="loginRefForm" :rules="loginRules" :model="user" label-width="auto" style="max-width: 600px">
          <!--登录账号文本框-->
          <el-form-item prop="loginAct">
            <el-input placeholder="请输入您的账号" v-model="user.loginAct"/>
          </el-form-item>
          <!--登录密码文本框-->
          <el-form-item prop="loginPwd">
            <el-input type="password" show-password placeholder="请输入您的密码" v-model="user.loginPwd"/>
          </el-form-item>
          <!--登录按钮-->
          <el-form-item>
            <el-button type="primary" round @click="login">登 录</el-button>
          </el-form-item>
          <!--记住我选项框-->
          <el-form-item>
            <el-checkbox label="记住我" v-model="user.rememberMe"/>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
  </div>
</template>

<script>
// import 区域
import {doPost} from "../http/HttpRequest.js";
import {ElMessage} from "element-plus";
import {getTokenName, messageTip, removeToken} from "../util/util.js";

export default {
  // 定义页面使用到的变量
  data() {
    return {
      // 对象变量定义使用 : {}
      user: {},
      // 字符串变量定义使用 : ''
      loginAct: '',
      loginPwd: '',
      rememberMe: false,
      // 定义规则（List集合）使用 : {[],[],[]......}
      loginRules: {
        // 这里是定义账号的验证规则的数组（因为规则可以多个）
        loginAct: [
          {required: true, message: '账号不能为空', trigger: 'blur'},
        ],
        // 这里是定义密码的验证规则
        loginPwd: [
          {required: true, message: '密码不能为空', trigger: 'blur'}
        ],
      },
    }
  },
  methods: {
    // 登录函数
    login() {
      // 提交前保证输入框的合法性
      this.$refs.loginRefForm.validate((isValid) => {
        // isValid是验证后的结果，如果是true表示验证通过，否则未通过
        if (isValid) {
          let formData = new FormData();
          formData.append("loginAct", this.user.loginAct);
          formData.append("loginPwd", this.user.loginPwd);
          formData.append("rememberMe", this.user.rememberMe);

          doPost("/api/login", formData).then((resp) => {
            if (resp.data.code === 200) {
              // 登陆成功
              messageTip("登录成功，欢迎回来！", "success");
              // 删除一下历史localStorage 和 sessionStorage 存储的 token
              removeToken();

              // 前端存储 jwt
              if (this.user.rememberMe === true){
                window.localStorage.setItem(getTokenName(),resp.data.data)
              }else {
                window.sessionStorage.setItem(getTokenName(), resp.data.data)
              }
              // 跳转到系统的主页面
              window.location.href = "/dashboard";
            } else {
              // 登录失败
              messageTip("登录失败，账号或密码错误！", "error");
            }
          });
        }
      })
    }

  }
}
</script>

<style scoped>
.el-button {
  width: 100%;
}

.loginTitle {
  width: 83%;
  margin: 190px auto auto;
  font-weight: bold;
}

.el-form {
  margin-top: 20px;
  width: 25%;
}

.el-aside {
  background: #f9f9f9;
  width: 43.832237%;
  text-align: center;
}

.el-main {
  background: #f9f9f9;
  height: calc(100vh);
}

img {
  margin-top: 150px;
  height: 400px;
}

.imgTitle {
  color: #1a1a1a;
  font-size: 28px;
}
</style>