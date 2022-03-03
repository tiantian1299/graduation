package com.example.office.wx.db.mapper;

import com.example.office.wx.db.pojo.SysConfig;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface SysConfigMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SysConfig record);

    int insertSelective(SysConfig record);

    SysConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysConfig record);

    int updateByPrimaryKey(SysConfig record);
}