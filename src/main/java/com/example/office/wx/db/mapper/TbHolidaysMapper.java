package com.example.office.wx.db.mapper;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface TbHolidaysMapper {
    /**
     * 判断当天是否是特殊的节假日（周一到周五）
     * @return
     */
    Integer searchTodayIsHolidays();
}
