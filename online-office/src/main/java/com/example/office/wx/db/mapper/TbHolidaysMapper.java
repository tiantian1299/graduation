package com.example.office.wx.db.mapper;

import com.example.office.wx.db.pojo.TbHolidays;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface TbHolidaysMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbHolidays record);

    int insertSelective(TbHolidays record);

    TbHolidays selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbHolidays record);

    int updateByPrimaryKey(TbHolidays record);
}