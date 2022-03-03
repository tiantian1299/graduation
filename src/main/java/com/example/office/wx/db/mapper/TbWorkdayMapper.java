package com.example.office.wx.db.mapper;

import com.example.office.wx.db.pojo.TbWorkday;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface TbWorkdayMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbWorkday record);

    int insertSelective(TbWorkday record);

    TbWorkday selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbWorkday record);

    int updateByPrimaryKey(TbWorkday record);
}