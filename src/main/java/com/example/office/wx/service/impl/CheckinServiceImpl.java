package com.example.office.wx.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.office.wx.config.SystemConstants;
import com.example.office.wx.db.mapper.TbCheckinMapper;
import com.example.office.wx.db.mapper.TbHolidaysMapper;
import com.example.office.wx.db.mapper.TbWorkdayMapper;
import com.example.office.wx.service.CheckinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
public class CheckinServiceImpl implements CheckinService {

    @Autowired
    private TbCheckinMapper checkinMapper;

    @Autowired
    private TbWorkdayMapper workdayMapper;

    @Autowired
    private TbHolidaysMapper holidaysMapper;

    @Autowired
    private SystemConstants constants;

    @Override
    public String validCancheckin(int userId, String date) {
        boolean isworkday = workdayMapper.searchTodayIsWorkday() != null ? true : false;
        boolean isHoliday = holidaysMapper.searchTodayIsHolidays() != null ? true : false;
        String type = "工作日";
        if (DateUtil.date().isWeekend()){
            type = "节假日";
        }
        if (isworkday){
            type = "工作日";
        }
        if (isHoliday){
            type = "节假日";
        }

        if(type.equals("节假日")){
            return "节假日不需要考勤";
        }else{
            DateTime now = DateUtil.date();
            String start = DateUtil.today()+""+constants.getWorkStartTime();
            String end = DateUtil.today()+""+constants.getWorkEndTime();
            DateTime workStartTime = DateUtil.parse(start);
            DateTime workEndTime = DateUtil.parse(end);
            if (now.isBefore(workStartTime)){
                return "没有到上班考勤开始时间";
            }else if(now.isAfter(workEndTime)){
                return "超过了上班考勤结束时间";
            }else{
                HashMap map = new HashMap();
                map.put("userId",userId);
                map.put("date",date);
                map.put("start",start);
                map.put("end",end);
                boolean isCheckin =  checkinMapper.haveCheckin(map)!=null ? true : false;
                return  isCheckin? "今日已经考勤，不用重复考勤" : "可以考勤";
            }
        }

    }
}
