package com.sangui.mapper;

import com.sangui.model.TUser;

import java.util.List;

/**
 * @author sangui
 */
public interface TUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TUser record);

    int insertSelective(TUser record);

    TUser selectByPrimaryKey(Integer id);

    TUser selectUserDetailById(Integer id);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);

    TUser selectByLoginAct(String username);

    List<TUser> selectUserByPage();

    int batchDelete(String ids);
}