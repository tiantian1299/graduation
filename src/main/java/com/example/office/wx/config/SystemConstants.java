package com.example.office.wx.config;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class SystemConstants {
    /**
     * 上班考勤开始时间
     */
    public String workStartTime;

    /**
     * 上班时间
     */
    public String workTime;
    /**
     * 上班考勤截止时间
     */
    public String workEndTime;
    /**
     * 下班考勤开始时间
     */
    public String closeStartTime;
    /**
     * 下班时间
     */
    public String closeTime;
    /**
     * 下班考勤截止时间
     */
    public String closeEndTime;
}
