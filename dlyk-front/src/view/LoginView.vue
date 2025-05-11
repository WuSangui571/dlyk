<template>
  <div class="common-layout">
    <el-container>
      <!--左-->
      <el-aside>
        <img src="../assets/background.jpg"  alt="背景">
        <p class="imgTitle">
          欢迎来到三桂管理系统
        </p>
      </el-aside>

      <!--右-->
      <el-main>
        <div class="loginTitle">欢迎登录</div>
        <!--登录表单-->
        <el-form ref="loginRefForm" :rules="loginRules" :model="user" label-width="auto" style="max-width: 600px">
          <!--账号-->
          <el-form-item  prop="loginAct">
            <el-input placeholder="请输入您的账号" v-model="user.loginAct"/>
          </el-form-item>
          <!--密码-->
          <el-form-item prop="loginPwd">
            <el-input type="password" show-password placeholder="请输入您的密码" v-model="user.loginPwd" />
          </el-form-item>
          <!--登录-->
          <el-form-item>
            <el-button type="primary" round @click="login">登 录</el-button>
          </el-form-item>
          <!--记住我-->
          <el-form-item>
            <el-checkbox label="记住我" v-model="rememberMe"/>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import {doPost} from "../http/HttpRequest.js";

export default {
  data() {
    return {
      user: {},
      loginAct: '',
      loginPwd: '',
      rememberMe: false,
      loginRules: {
        // 定义账号的验证规则
        loginAct: [
          { required: true, message: '账号不能为空', trigger: 'blur' },
        ],
        // 定义密码的验证规则
        loginPwd: [
          { required: true, message: '密码不能为空', trigger: 'blur' }
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
          doPost("/api/login", formData).then((resp) => {
            console.log(resp);
          });
        }
      })

    }
  }
}
</script>

<style scoped>
.el-button{
  width: 100%;
}
.loginTitle{
  width: 83%;
  margin: 190px auto auto;
  font-weight: bold;
}
.el-form{
  margin-top: 20px;
  width: 25%;
}
.el-aside{
  background: #f9f9f9;
  width: 43.832237%;
  text-align : center;
}
.el-main{
  background: #f9f9f9;
  height: calc(100vh);
}
img{
  margin-top: 150px;
  height: 400px;
}
.imgTitle{
  color: #1a1a1a;
  font-size: 28px;
}
</style>