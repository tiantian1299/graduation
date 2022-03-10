package com.example.office.wx.db.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;


@Mapper
public interface TbWorkdayMapper {

    /**
     * 判断当天是否是特使的工作日（周六周日）
     * @return
     */
    Integer searchTodayIsWorkday();

    /**
     * 查询某个时间段是否存在特殊的工作日
     * @param parms
     * @return
     */
    ArrayList<String> searchWorkday(HashMap parms);
}