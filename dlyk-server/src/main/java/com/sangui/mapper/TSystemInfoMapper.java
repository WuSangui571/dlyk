package com.sangui.mapper;

import com.sangui.model.TSystemInfo;

public interface TSystemInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TSystemInfo record);

    int insertSelective(TSystemInfo record);

    TSystemInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TSystemInfo record);

    int updateByPrimaryKey(TSystemInfo record);
}