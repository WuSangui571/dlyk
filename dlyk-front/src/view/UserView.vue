<template>
  <el-button type="primary"  @click="add">新增用户</el-button>
  <!--新增用户的弹窗-->
  <el-dialog v-model="userDialogVisible" :title="user.id > 0 ?'编辑用户':'新增用户'" center width="45%" draggable>
    <!--登录表单-->
      <el-form ref="loginRefForm" :model="user" label-width="110px" :rules="userRules">
        <el-form-item label="账号" prop="loginAct">
          <el-input placeholder="请输入要新增的账号" v-model="user.loginAct"/>
        </el-form-item>
        <el-form-item label="密码" v-if="user.id > 0">
          <el-input type="password" placeholder="密码为空值表示不修改密码" v-model="user.loginPwd"/>
        </el-form-item>
        <el-form-item label="密码" prop="loginPwd" v-else>
          <el-input type="password" placeholder="请输入要新增的密码" v-model="user.loginPwd"/>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input placeholder="请输入要新增的姓名" v-model="user.name"/>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input placeholder="请输入要新增的电话" v-model="user.phone"/>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input placeholder="请输入要新增的邮箱" v-model="user.email"/>
        </el-form-item>
        <el-form-item label="账号未过期" prop="accountNoExpired">
          <el-select v-model="user.accountNoExpired" placeholder="请选择" >
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="密码未过期" prop="credentialsNoExpired">
          <el-select v-model="user.credentialsNoExpired" placeholder="请选择">
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="账号未锁定" prop="accountNoLocked">
          <el-select v-model="user.accountNoLocked" placeholder="请选择">
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="账号是否启用" prop="accountEnabled">
          <el-select v-model="user.accountEnabled" placeholder="请选择">
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
            />
          </el-select>
        </el-form-item>
      </el-form>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="userDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveAdd">提交</el-button>
      </div>
    </template>
  </el-dialog>
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
  <!--注意上面三个 getData 方法，方法里没有参数，但实际是有的，current 参数是 ele-plus 自动传过来要跳转的页码-->

</template>
<script lang="ts">
import {defineComponent} from 'vue'
import {doGet, doPost} from "../http/HttpRequest.js";
import {messageTip} from "../util/util";

export default {
  // 注入父级页面提供的属性
  inject: ['reload'],
  data(){
    return {
      // userList 是个数组，里面是对象
      userList : [{}],
      total : 0,
      pageSize : 0,
      // 新增用户弹窗，true 为弹，false 为不弹
      userDialogVisible : false,
      // 定义用户的属性
      user : {
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
      // 定义用户的规则
      userRules:{
        loginAct : [
          {required: true, message: '账号不能为空', trigger: 'blur'},
        ],
        loginPwd : [
          {required: true, message: '密码不能为空', trigger: 'blur'},
          {min: 3,max :10,message: '密码位数必须在3-10之间', trigger: 'blur'},
        ],
        name : [
          {required: true, message: '姓名不能为空', trigger: 'blur'},
          {pattern : /^[\u4e00-\u9fa5]{0,}$/, message: "姓名必须是中文", trigger: 'blur'}
        ],
        phone : [
          {required: true, message: '电话不能为空', trigger: 'blur'},
          {pattern : /^1[3-9]\d{9}$/, message: "手机格式有误", trigger: 'blur'}
        ],
        email : [
          {required: true, message: '邮箱不能为空', trigger: 'blur'},
          {pattern : /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/, message: "请输入正确的邮箱格式", trigger: 'blur'}
        ],
        accountNoExpired : [
          {required: true, message: '请选择账号是否未过期', trigger: 'blur'},
        ],
        credentialsNoExpired : [
          {required: true, message: '请选择密码是否未过期', trigger: 'blur'},
        ],
        accountNoLocked : [
          {required: true, message: '请选择账号是否未锁定', trigger: 'blur'},
        ],
        accountEnabled : [
          {required: true, message: '请选择账号是否启用', trigger: 'blur'},
        ],
      },
      // 选择器
      options :[
        {
          value: '1',
          label: '是',
        },
        {
          value: '0',
          label: '否',
        },
      ],
    }
  },
  mounted() {
    this.getData();
  },
  methods:{
    // 保存添加，将新增的信息返回给后端
    saveAdd(){
      // 提交之前再进行一次验证
      this.$refs.loginRefForm.validate((isValid) =>{
        // 只有验证成功才能提交
        if (isValid){
          let formData = new FormData();
          for (let item in this.user){
            // 追加(字段名，字段值)
            formData.append(item,this.user[item]);
          }
          doPost("/api/user",formData).then(resp =>{
            console.log(resp)
            if (resp.data.code === 200){
              messageTip("提交成功！","success");
              this.userDialogVisible = false;
              // 重新载入
              this.reload();
            }else{
              messageTip("提交失败！","error");
            }
          })
        }
      })

    },
    // 新增用户方法。
    add(){
      // 重置 user 里的数据
      this.user = {}
      // 新增方法被调用之后，设置新增用户的弹窗为 true
      this.userDialogVisible = true;
    },

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
      this.userDialogVisible = true;
      this.loadUser(id);
    },
    // 加载编辑用户时的信息
    loadUser(id){
      doGet("/api/user/" + id,{}).then((resp) =>{
        //console.log(resp)
        if (resp.data.code === 200){
          this.user = resp.data.data;
          this.user.loginPwd = "";
        }else{

        }
      })
    },
    // 删除指定 id 的用户
    del(id){

    },

  }
}
</script>



<style scoped>

.el-select{
  width: 100%;
}
.el-pagination{
  /*设置上边距*/
  margin-top: 12px;
}
.el-table{
  /*设置上边距*/
  margin-top: 12px;
}
</style>