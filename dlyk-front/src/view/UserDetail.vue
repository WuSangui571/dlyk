<template>
  <el-button type="primary" @click="back">返回</el-button>
  <!--展示信息的表单-->
  <el-form ref="loginRefForm" :model="userDetail" label-width="120px">
    <!--id-->
    <el-form-item label="ID">
      {{ userDetail.id }}
    </el-form-item>
    <!--账号-->
    <el-form-item label="账号">
      {{ userDetail.loginAct }}
    </el-form-item>
    <!--账号-->
    <el-form-item label="密码">
      {{ userDetail.loginPwd }}
    </el-form-item>
    <!--账号-->
    <el-form-item label="姓名">
      {{ userDetail.name }}
    </el-form-item>
    <!--账号-->
    <el-form-item label="电话">
      {{ userDetail.phone }}
    </el-form-item>
    <!--账号-->
    <el-form-item label="邮箱">
      {{ userDetail.email }}
    </el-form-item>
    <!--账号-->
    <el-form-item label="账号未过期">
      {{ userDetail.accountNoExpired === 1 ? "是" : "否" }}
    </el-form-item>
    <!--账号-->
    <el-form-item label="密码未过期">
      {{ userDetail.credentialsNoExpired === 1 ? "是" : "否" }}
    </el-form-item>
    <!--账号-->
    <el-form-item label="账号未锁定">
      {{ userDetail.accountNoLocked === 1 ? "是" : "否" }}
    </el-form-item>
    <!--账号-->
    <el-form-item label="账号是否启用">
      {{ userDetail.accountEnabled === 1 ? "是" : "否" }}
    </el-form-item>
    <!--账号-->
    <el-form-item label="创建时间">
      {{ userDetail.createTime }}
    </el-form-item>
    <!--账号-->
    <el-form-item label="创建人">
      <!--{{ userDetail.createBy }}-->
      {{ userDetail.createByDo.name }}
    </el-form-item>
    <!--账号-->
    <el-form-item label="编辑时间">
      {{ userDetail.editTime }}
    </el-form-item>
    <!--账号-->
    <el-form-item label="编辑人">
      {{ userDetail.editByDo.name }}
    </el-form-item>
    <!--账号-->
    <el-form-item label="最近登录时间">
      {{ userDetail.lastLoginTime }}
    </el-form-item>

  </el-form>
</template>

<script lang="ts">
import  {doGet} from "../http/HttpRequest.js";
export default{
  data(){
    return{
      // 用户详情是个对象
      userDetail : {
        id : 0,
        loginAct : "",
        loginPwd : "",
        name : "",
        phone : "",
        email : "",
        accountNoExpired : 1,
        credentialsNoExpired : 1,
        accountNoLocked : 1,
        accountEnabled : 1,
        createTime : "",
        createByDo : {
          name : "",
        },
        editTime : "",
        editByDo : {
          name : "",
        },
        lastLoginTime : "",
      },
    }
  },
  // 加载页面后使用以下的方法
  mounted() {
    this.loadUserDetail()
  },
  methods : {
    back(){
      this.$router.go(-1);
    },
    // 加载用户详情方法
    loadUserDetail(){
      // 通过 $route 查找参数 id，这里写的 id，要和动态路由里配置的动态路径相同
      let id = this.$route.params.id;
      //console.log("/api/user/" + id)
      doGet("/api/user/" + id,{}).then(resp =>{
        //console.log(resp.data.code)
        if (resp.data.code === 200){
          //console.log("my resp:" + resp)
          this.userDetail = resp.data.data;
        }
      })
    }
  }
}

</script>

<style scoped>

</style>