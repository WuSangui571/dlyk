package com.sangui.service;


import com.github.pagehelper.PageInfo;
import com.sangui.model.TUser;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Author: sangui
 * @CreateTime: 2025-05-11
 * @Description: UserService
 * @Version: 1.0
 */
// 继承 SpringSecurity 框架的 UserDetailsService 接口
public interface UserService extends UserDetailsService {
    /**
     * 根据页码，查询 users
     * @param current 页码
     */
    PageInfo<TUser> getUserByPage(Integer current);
}
