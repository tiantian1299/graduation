package com.example.office.wx.db.mapper;

import com.example.office.wx.db.pojo.TbRole;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface TbRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbRole record);

    int insertSelective(TbRole record);

    TbRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbRole record);

    int updateByPrimaryKey(TbRole record);
}