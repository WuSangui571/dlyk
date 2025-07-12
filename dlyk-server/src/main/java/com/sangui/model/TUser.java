package com.sangui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

/**
 * 用户表
 * t_user
 * @author sangui
 */
@Data
public class TUser implements UserDetails, Serializable {
    /**
     * 主键，自动增长，用户ID
     */
    private Integer id;

    /**
     * 登录账号
     */
    private String loginAct;

    /**
     * 登录密码
     */
    private String loginPwd;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户手机
     */
    private String phone;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 账户是否没有过期，0已过期 1正常
     */
    private Integer accountNoExpired;

    /**
     * 密码是否没有过期，0已过期 1正常
     */
    private Integer credentialsNoExpired;

    /**
     * 账号是否没有锁定，0已锁定 1正常
     */
    private Integer accountNoLocked;

    /**
     * 账号是否启用，0禁用 1启用
     */
    private Integer accountEnabled;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Integer createBy;

    /**
     * 编辑时间
     */
    private Date editTime;

    /**
     * 编辑人
     */
    private Integer editBy;

    /**
     * 最近登录时间
     */
    private Date lastLoginTime;

    private static final long serialVersionUID = 1L;

    // 角色 List
    private List<String> roleList;
    // 权限标识符 List
    private List<String> permissionList;

    /**
     * 一对一映射
     */
    private TUser createByDo;

    /**
     * 
     */
    private TUser editByDo;

    // 实现 UserDetails 的七个方法
    // 使用 @JsonIgnore 注解，使在生成的对象转化为 json 时忽略这个字段（若不写，json 中会自动生成 authorities 字段），下面七个方法同理
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 定义返回的集合
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

        // 检测 roleList 是否为空
        if (!ObjectUtils.isEmpty(this.getRoleList())){
            // 遍历 roleList 里的角色，放到返回的集合里
            this.getRoleList().forEach(role -> {
                list.add(new SimpleGrantedAuthority(role));
            });
        }

        // 检测 permissionList 是否为空
        if (!ObjectUtils.isEmpty(this.getPermissionList())){
            // 遍历 permissionList 里的权限标识符，放到返回的集合里
            this.getPermissionList().forEach(permission -> {
                list.add(new SimpleGrantedAuthority(permission));
            });
        }

        return list;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        // 返回自己的密码即可
        return this.getLoginPwd();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        // 返回自己的用户名即可
        return this.getLoginAct();
    }


    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        // 返回自己的 accountNoExpired 是否正常，即是否为 1
        return this.getAccountNoExpired() == 1;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        // 返回自己的 accountNonLocked 是否正常，即是否为 1
        return this.getAccountNoLocked() == 1;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        // 返回自己的 credentialsNoExpired 是否正常，即是否为 1
        return this.getCredentialsNoExpired() == 1;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        // 返回自己的 accountEnabled 是否正常，即是否为 1
        return this.getAccountEnabled() == 1;
    }
}