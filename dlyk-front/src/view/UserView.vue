<template>
  <el-button type="primary">新增用户</el-button>
  <el-button type="danger">批量删除</el-button>
  <el-table
      :data="userList"
      row-key="id"
      style="width: 100%"
      @selection-change="handleSelectionChange">
    <el-table-column type="selection" :selectable="selectable" width="55" />
    <el-table-column type="index" label="序号" width="55"/>
    <el-table-column prop="loginAct" label="账号" width="120"/>
    <el-table-column prop="name" label="姓名" show-overflow-tooltip width="80"/>
    <el-table-column prop="phone" label="手机" show-overflow-tooltip width="120"/>
    <el-table-column prop="email" label="邮箱" show-overflow-tooltip width="180"/>
    <el-table-column prop="createTime" label="创建时间" show-overflow-tooltip width="250"/>
    <el-table-column label="操作" >
      <el-button type="primary">详情</el-button>
      <el-button type="success">编辑</el-button>
      <el-button type="danger">删除</el-button>
    </el-table-column>
  </el-table>

</template>
<script lang="ts">
import {defineComponent} from 'vue'
import  {doGet} from "../http/HttpRequest.js";

export default {
  data(){
    return {
      // userList 是个数组，里面是对象
      userList : [{}],
    }
  },
  mounted() {
    this.getData();
  },
  methods:{
    // 勾选或取消勾选触发此事件
    handleSelectionChange(){

    },
    // 获取表格 Data 的函数
    getData(current){
      // 这里要做分页查询，所以需传入后端 当前页 和 每页数量 的数据
      doGet("/api/users",{
        current : current
      }).then(resp => {
        if (resp.data.code === 200){
          this.userList = resp.data.data.list;
          // console.log(resp.data.data)
        }
      })
    },
  }
}
</script>



<style scoped>

</style>