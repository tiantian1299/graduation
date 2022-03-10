package com.example.office.wx.db.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;


@Mapper
public interface TbHolidaysMapper {
    /**
     * 判断当天是否是特殊的节假日（周一到周五）
     * @return
     */
    Integer searchTodayIsHolidays();

    /**
     * 查询某个时间段是否有特殊节假日
     * @param params
     * @return
     */
    ArrayList<String> searchHoliday(HashMap params);
}
