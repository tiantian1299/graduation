package com.example.office.wx.db.mapper;

import com.example.office.wx.db.pojo.TbCheckin;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface TbCheckinMapper {

    /**
     * 判断是否存在签到记录
     * @param param
     * @return
     */
    Integer haveCheckin(HashMap param);

    /**
     * 插入一条签到记录
     * @param tbCheckin
     */
    void insert(TbCheckin tbCheckin);

    /**
     * 查询用户当天的签到情况
     * @param userId
     * @return
     */
    HashMap queryTodayCheckin(int userId);

    /**
     * 查询用户的累计签到天数
     * @param userId
     * @return
     */
    Long queryCheckinDays(int userId);

    /**
     * 查询用户一周的签到情况
     * @param parms
     * @return
     */
    ArrayList<HashMap> queryWeekCheckin(HashMap parms);

}