package com.example.office.wx.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.example.office.wx.config.SystemConstants;
import com.example.office.wx.db.mapper.TbCheckinMapper;
import com.example.office.wx.db.mapper.TbCityMapper;
import com.example.office.wx.db.mapper.TbHolidaysMapper;
import com.example.office.wx.db.mapper.TbWorkdayMapper;
import com.example.office.wx.db.pojo.TbCheckin;
import com.example.office.wx.exception.OfficeException;
import com.example.office.wx.service.CheckinService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private TbCityMapper tbCityMapper;

    @Autowired
    private TbCheckinMapper tbCheckinMapper;

    /**
     * 验证是否可以签到
     *
     * @param userId
     * @param date
     * @return
     */
    @Override
    public String validCancheckin(int userId, String date) {
        boolean isworkday = workdayMapper.searchTodayIsWorkday() != null ? true : false;
        boolean isHoliday = holidaysMapper.searchTodayIsHolidays() != null ? true : false;
        String type = "工作日";
        if (DateUtil.date().isWeekend()) {
            type = "节假日";
        }
        if (isworkday) {
            type = "工作日";
        }
        if (isHoliday) {
            type = "节假日";
        }

        if (type.equals("节假日")) {
            return "节假日不需要考勤";
        } else {
            DateTime now = DateUtil.date();
            String start = DateUtil.today() + " " + constants.getWorkStartTime();
            String end = DateUtil.today() + " " + constants.getWorkEndTime();
            DateTime workStartTime = DateUtil.parse(start);
            DateTime workEndTime = DateUtil.parse(end);
            if (now.isBefore(workStartTime)) {
                return "没有到上班考勤开始时间";
            } else if (now.isAfter(workEndTime)) {
                return "超过了上班考勤结束时间";
            } else {
                HashMap map = new HashMap();
                map.put("userId", userId);
                map.put("date", date);
                map.put("start", start);
                map.put("end", end);
                boolean isCheckin = checkinMapper.haveCheckin(map) != null ? true : false;
                return isCheckin ? "今日已经考勤，不用重复考勤" : "可以考勤";
            }
        }
    }


    /**
     * 在线签到
     *
     * @param params
     */
    @Override
    public void checkin(HashMap params,int userId) {
        //当前时间
        Date now = DateUtil.date();
        //上班时间
        Date start = DateUtil.parse(DateUtil.today() + " " + constants.workStartTime);
        //签到结束时间
        Date end = DateUtil.parse(DateUtil.today() + " " + constants.workEndTime);
        int status = 1;
        if (now.compareTo(start) <= 0) {
            //员工正常签到，签到时间晚于上班开始时间
            status = 1;
        } else if (now.compareTo(start) > 0 && now.compareTo(end) < 0) {
            //迟到
            status = 2;
        }

        //查询风险等级
        int risk = 1;
        String city = (String) params.get("city");
        String district = (String) params.get("district");
        if (!StrUtil.isBlank(city) && !StrUtil.isBlank(district)) {
            String code = tbCityMapper.searchCode(city);
            try {
                //封装本地宝 url
                String url = "http://m." + code + ".bendibao.com/news/yqdengji/?qu=" + district;
                //发送请求 返回 html
                Document document = Jsoup.connect(url).get();
                //<div class="list-content">
                //<p style='float:left;position: relative'>西城区 </p>
                //<p style="float:right;color:#FFAA85;min-width:1rem;">低风险</p>
                //</div>
                Elements elements = document.getElementsByClass("list-content");
                if (elements.size() > 0) {
                    Element element = elements.get(0);
                    // 拿到div中的 最后一个 p标签
                    String result = element.select("p:last-child").text();
                    if (result.equals("高风险")) {
                        risk = 3;
                    } else if (result.equals("中风险")) {
                        risk = 2;
                    }
                }
            } catch (Exception e) {
                log.error("执行异常", e);
                throw new OfficeException("获取风险等级时报");
            }
            // 保存签到记录
            String address = (String) params.get("address");
            String country = (String) params.get("country");
            String province = (String) params.get("province");
            TbCheckin checkin = new TbCheckin();
            checkin.setUserId(userId);
            checkin.setAddress(address);
            checkin.setCountry(country);
            checkin.setProvince(province);
            checkin.setCity(city);
            checkin.setDistrict(district);
            checkin.setStatus((byte) status);
            checkin.setRisk(risk);
            checkin.setDate(DateUtil.today());
            checkin.setCreateTime(now);
            tbCheckinMapper.insert(checkin);
        }
    }

    /**
     * 查询用户当天的考勤记录
     * @param userId
     * @return
     */
    @Override
    public HashMap queryTodayCheckin(int userId) {
        return null;
    }

    /**
     * 查询用户的累计考勤天数
     * @param userId
     * @return
     */
    @Override
    public Long queryCheckinDays(int userId) {
        return null;
    }

    /**
     * 查询一周的考勤记录
     * @param parms
     * @return
     */
    @Override
    public ArrayList<HashMap> queryWeekCheckin(HashMap parms) {
        return null;
    }
}
