package com.sangui.service.impl;


import com.sangui.mapper.TUserMapper;
import com.sangui.model.TUser;
import com.sangui.model.TUserRole;
import com.sangui.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author: sangui
 * @CreateTime: 2025-05-11
 * @Description: UserServiceImpl
 * @Version: 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    TUserMapper tUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TUser tUser = tUserMapper.selectByLoginAct(username);
        if (tUser == null) {
            throw new UsernameNotFoundException("登录账号不存在！");
        }
        return tUser;
    }
}
