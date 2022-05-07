package com.example.office.wx.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateRange;
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
    public void checkin(HashMap params, int userId) {
        //当前时间
        Date now = DateUtil.date();
        //上班时间
        Date start = DateUtil.parse(DateUtil.today() + " " + constants.workTime);
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
     *
     * @param userId
     * @return
     */
    @Override
    public HashMap queryTodayCheckin(int userId) {
        return tbCheckinMapper.queryTodayCheckin(userId);
    }

    /**
     * 查询用户的累计考勤天数
     *
     * @param userId
     * @return
     */
    @Override
    public Long queryCheckinDays(int userId) {
        return tbCheckinMapper.queryCheckinDays(userId);
    }

    /**
     * 查询一周的考勤记录
     *
     * @param params
     * @return
     */
    @Override
    public ArrayList<HashMap> queryWeekCheckin(HashMap params) {
        //查询一周中特殊的工作日
        ArrayList<String> workday = workdayMapper.searchWorkday(params);
        //查询一周中特殊的节假日
        ArrayList<String> holiday = holidaysMapper.searchHoliday(params);
        //查询一周中用户的考勤情况
        ArrayList<HashMap> weekCheckin = tbCheckinMapper.queryWeekCheckin(params);

        //一周开始时间
        DateTime startTime = DateUtil.parseDate(params.get("startTime").toString());
        //一周结束时间
        DateTime endTime = DateUtil.parseDate(params.get("endTime").toString());
        //生成 n 天的日期对象  参数：（开始时间，结束时间，以天为单位）
        DateRange range = DateUtil.range(startTime, endTime, DateField.DAY_OF_MONTH);
        ArrayList<HashMap> list = new ArrayList();
        for (DateTime date : range) {
            String temp = date.toString("yyyy-MM-dd");
            String type = "工作日";
            if (date.isWeekend()) {
                type = "节假日";
            }
            //判断是否是特殊的节假日或工作日
            if (workday != null && workday.contains(temp)) {
                type = "工作日";
            }
            if (holiday != null && holiday.contains(temp)) {
                type = "节假日";
            }

            //判断考勤状态
            String status = "";
            if (type == "工作日" && DateUtil.compare(date, DateUtil.date()) <= 0) {
                //如果是工作日，而且时间是是在当前日期之前
                status = "缺勤";
                //查询周考勤记录中是否有当天考勤
                boolean flag = false;
                for (HashMap map : weekCheckin) {
                    if (map.containsValue(temp)) {
                        status = (String) map.get("status");
                        flag = true;
                        break;
                    }
                }
                //工作日，但是考勤时间还没有结束  （DateUtil.today() ==> yyyy-mm-dd）
                DateTime workEndTime = DateUtil.parse(DateUtil.today() + " " + constants.workEndTime);
                String today = DateUtil.today();
                //当前日期恰好是今天， 而且还没有到打卡日期
                if (today.equals(temp) && DateUtil.compare(DateUtil.date(), workEndTime) <= 0 && flag == false) {
                    status = "";
                }
            }

            //封装返回对象
            HashMap map = new HashMap();
            map.put("date", temp);
            map.put("type", type);
            map.put("status", status);
            map.put("day", date.dayOfWeekEnum().toChinese("周"));
            list.add(map);
        }
        return list;
    }

    /**
     * 查询用户一个月的签到情况
     *
     * @param params
     * @return
     */
    @Override
    public ArrayList<HashMap> queryMonthCheckin(HashMap params) {
        return queryWeekCheckin(params);
    }

}
