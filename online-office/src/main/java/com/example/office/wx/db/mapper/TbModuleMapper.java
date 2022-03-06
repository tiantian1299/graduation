package com.example.office.wx.db.mapper;

import com.example.office.wx.db.pojo.TbModule;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface TbModuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbModule record);

    int insertSelective(TbModule record);

    TbModule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbModule record);

    int updateByPrimaryKey(TbModule record);
}