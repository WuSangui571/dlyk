package com.sangui.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sangui.mapper.TUserMapper;
import com.sangui.model.TUser;
import com.sangui.model.TUserRole;
import com.sangui.query.UserQuery;
import com.sangui.service.UserService;
import com.sangui.util.JwtUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Date;
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
    public PasswordEncoder passwordEncoder;

    @Resource
    TUserMapper tUserMapper;

    @Override
    public PageInfo<TUser> getUserByPage(Integer current) {
        PageHelper.startPage(current,PAGE_SIZE);
        List<TUser> list = tUserMapper.selectUserByPage();
        return new PageInfo<>(list);
    }

    @Override
    public int saveUser(UserQuery userQuery) {
        TUser tUser = new TUser();

        // 把 userQuery 里边的属性数据复制到上面的 tUser 对象里边去
        // 使用 Spring 的一个工具类 BeanUtils.copy 就可以实现（要求两个对象的属性名要相同，属性类要相同）
        BeanUtils.copyProperties(userQuery,tUser);
        // 进行密码加密
        tUser.setLoginPwd(passwordEncoder.encode(userQuery.getLoginPwd()));
        // 添加创建时间
        tUser.setCreateTime(new Date());
        // 添加创建人 id（即当前登录人的 id）
        Integer loginUserId = JwtUtils.parseUserFromJWT(userQuery.getToken()).getId();
        tUser.setCreateBy(loginUserId);

        return tUserMapper.insertSelective(tUser);
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

    @Override
    public int updateUser(UserQuery userQuery) {
        TUser tUser = new TUser();
        BeanUtils.copyProperties(userQuery,tUser);
        // 若账号被修改（即账号不为空）
        if (userQuery.getLoginPwd() != null) {
            tUser.setLoginPwd(passwordEncoder.encode(userQuery.getLoginPwd()));
        }
        // 加入修改人信息
        Integer loginUserId = JwtUtils.parseUserFromJWT(userQuery.getToken()).getId();
        tUser.setEditBy(loginUserId);
        // 加入修改时间
        tUser.setEditTime(new Date());
        // 在数据库中修改
        return tUserMapper.updateByPrimaryKeySelective(tUser);
    }
}
