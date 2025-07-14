package com.sangui.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sangui.mapper.TUserMapper;
import com.sangui.model.TUser;
import com.sangui.model.TUserRole;
import com.sangui.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sangui.constant.Constants.PAGE_SIZE;

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
    public PageInfo<TUser> getUserByPage(Integer current) {
        PageHelper.startPage(current,PAGE_SIZE);
        List<TUser> list = tUserMapper.selectUserByPage();
        return new PageInfo<>(list);
    }

    @Override
    public TUser getUserById(Integer id) {
        return tUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public TUser getUserDetailById(Integer id) {
        return  tUserMapper.selectUserDetailById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询对应 username 的 tUser 实体
        TUser tUser = tUserMapper.selectByLoginAct(username);
        // 验证此 tUser 实体
        if (tUser == null) {
            // 若找不到 tUser，则抛出异常
            throw new UsernameNotFoundException("登录账号不存在！");
        }
        // 返回正确的 tUser
        return tUser;
    }
}
