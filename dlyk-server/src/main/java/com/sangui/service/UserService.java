package com.sangui.service;


import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Author: sangui
 * @CreateTime: 2025-05-11
 * @Description: UserService
 * @Version: 1.0
 */
// 继承 SpringSecurity 框架的 UserDetailsService 接口
public interface UserService extends UserDetailsService {
    // 接口里不能写具体的类方法，重写的方法不写在这里，在实现类里重写
}
