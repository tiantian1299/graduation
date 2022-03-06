package com.example.office.wx.db.mapper;

import com.example.office.wx.db.pojo.TbDept;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface TbDeptMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbDept record);

    int insertSelective(TbDept record);

    TbDept selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbDept record);

    int updateByPrimaryKey(TbDept record);
}