package com.sangui.web;


import com.github.pagehelper.PageInfo;
import com.sangui.model.TUser;
import com.sangui.query.UserQuery;
import com.sangui.result.R;
import com.sangui.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: sangui
 * @CreateTime: 2025-06-19
 * @Description: 用户 User 的 Controller
 * @Version: 1.0
 */
@RestController
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 获取登录人信息
     * @param authentication 认证
     * @return 封装好的 R 返回信息
     */
    @GetMapping("/api/login/info")
    public R loginInfo(Authentication authentication){
        TUser tUser = (TUser) authentication.getPrincipal();
        return R.ok(tUser);
    }

    @GetMapping("/api/login/free")
    public R freeLogin(){
        // TokenVerifyFilter 会自动验证，这里不需要验证
        return  R.ok();
    }

    @GetMapping("/api/users")
    public R userPage(@RequestParam(value = "current",required = false) Integer current){
        if (current == null){
            current = 1;
        }
        PageInfo<TUser> pageInfo = userService.getUserByPage(current);
        return R.ok(pageInfo);
    }

    @GetMapping("/api/user/{id}")
    public R getUserDetail(@PathVariable(value = "id")  Integer id){
        // TUser tUser = userService.getUserById(id);
        TUser tUser = userService.getUserDetailById(id);
        return R.ok(tUser);
    }

    @PostMapping("/api/user")
    public R addUser(UserQuery userQuery, @RequestHeader(value = "Authorization")String token){
        userQuery.setToken(token);
        int count = userService.saveUser(userQuery);
        return count >= 1 ? R.ok() : R.fail();
    }

    @PutMapping("/api/user")
    public R updateUser(UserQuery userQuery, @RequestHeader(value = "Authorization")String token){
        userQuery.setToken(token);
        int count = userService.updateUser(userQuery);
        return count >= 1 ? R.ok() : R.fail();
    }

    @DeleteMapping("/api/user/{id}")
    public R deleteUser(@PathVariable(value = "id")  Integer id){
        int count = userService.deleteById(id);
        return count >= 1 ? R.ok() : R.fail();
    }

    @DeleteMapping("/api/user")
    public R deleteUser(@RequestParam(value = "ids") String ids){
        int count = userService.batchDeleteByIds(ids);
        return count >= 1 ? R.ok() : R.fail();
    }
}
