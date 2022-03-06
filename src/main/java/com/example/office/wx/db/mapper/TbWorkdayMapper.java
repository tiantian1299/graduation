package com.example.office.wx.db.mapper;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface TbWorkdayMapper {

    /**
     * 判断当天是否是特使的工作日（周六周日）
     * @return
     */
    Integer searchTodayIsWorkday();
}