package com.example.office.wx.service;

import java.util.ArrayList;
import java.util.HashMap;

public interface CheckinService {

    /**
     * 判断当天是否可以考勤
     *
     * @param userId
     * @param date
     * @return
     */
    String validCancheckin(int userId, String date);

    /**
     * 在线签到
     *
     * @param params
     */
    void checkin(HashMap params, int userId);

    /**
     * 查询当天的考勤
     *
     * @param userId
     * @return
     */
    HashMap queryTodayCheckin(int userId);

    /**
     * 查询用户的累计签到天数
     *
     * @param userId
     * @return
     */
    Long queryCheckinDays(int userId);

    /**
     * 查询用户一周的签到情况
     *
     * @param params
     * @return
     */
    ArrayList<HashMap> queryWeekCheckin(HashMap params);

    /**
     * 查询用户一个月的签到情况
     *
     * @param params
     * @return
     */
    ArrayList<HashMap> queryMonthCheckin(HashMap params);
}
