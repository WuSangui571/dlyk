<template>
  <el-button type="primary">新增用户</el-button>
  <el-button type="danger">批量删除</el-button>
  <el-table
      :data="userList"
      row-key="id"
      style="width: 100%"
      @selection-change="handleSelectionChange">
    <el-table-column type="selection"  width="55" />
    <el-table-column type="index" label="序号" width="55"/>
    <el-table-column prop="loginAct" label="账号" width="150"/>
    <el-table-column prop="name" label="姓名" show-overflow-tooltip width="120"/>
    <el-table-column prop="phone" label="手机" show-overflow-tooltip width="150"/>
    <el-table-column prop="email" label="邮箱" show-overflow-tooltip width="220"/>
    <el-table-column prop="createTime" label="创建时间" show-overflow-tooltip width="270"/>
    <el-table-column label="操作" >
      <!--template 标签的作用是，可在里面的按钮里添加作用域插槽，可以获取这一行别的列的数据，比如这一行的 id-->
      <template #default="scope">
        <!--这里点击函数里的参数就传了这一行的 id-->
        <el-button type="primary" @click="view(scope.row.id)" >详情</el-button>
        <el-button type="success" @click="edit(scope.row.id)" >编辑</el-button>
        <el-button type="danger" @click="del(scope.row.id)" >删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <!--这是分页按钮-->
  <el-pagination
      background
      layout = "prev, pager, next"
      :total = "total"
      :page-size = "pageSize"
      @prev-click = "getData"
      @next-click = "getData"
      @current-change = "getData"/>
  <!--注意上面三个 getData 方法，方法里的 current 参数是 ele-plus 自动传过来要跳转的页码-->

</template>
<script lang="ts">
import {defineComponent} from 'vue'
import  {doGet} from "../http/HttpRequest.js";

export default {
  data(){
    return {
      // userList 是个数组，里面是对象
      userList : [{}],
      total : 0,
      pageSize : 0,
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
            //console.log(resp.data.data);
            this.total = resp.data.data.total;
            this.pageSize = resp.data.data.pageSize
        }
      })
    },
    // 查看指定 id 的用户的详细信息
    view(id){
      // console.log("/dashboard/user/" + id)
      // 跳转路由，push 方法是用来跳转的
      this.$router.push("/dashboard/user/" + id)
    },
    // 编辑指定 id 的用户的信息
    edit(id){

    },
    // 删除指定 id 的用户
    del(id){

    },

  }
}
</script>



<style scoped>
.el-pagination{
  /*设置上边距*/
  margin-top: 12px;
}
.el-table{
  /*设置上边距*/
  margin-top: 12px;
}
</style>