package com.example.office.wx.service;

public interface CheckinService {

    /**
     * 判断当天是否可以考勤
     * @param userId
     * @param date
     * @return
     */
    String validCancheckin(int userId,String date);
}
