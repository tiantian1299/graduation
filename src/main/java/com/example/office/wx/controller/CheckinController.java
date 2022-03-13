package com.example.office.wx.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.office.wx.common.util.R;
import com.example.office.wx.config.SystemConstants;
import com.example.office.wx.config.shiro.JwtUtil;
import com.example.office.wx.controller.form.CheckinForm;
import com.example.office.wx.controller.form.QueryMonthCheckinForm;
import com.example.office.wx.exception.OfficeException;
import com.example.office.wx.service.CheckinService;
import com.example.office.wx.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

@RequestMapping("/checkin")
@RestController
@Api(tags = "签到模块web接口")
@Slf4j
public class CheckinController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CheckinService checkinService;

    @Autowired
    private UserService userService;

    @Autowired
    private SystemConstants constants;

    public static void main(String[] args) {
        DateTime startTime = DateUtil.beginOfWeek(DateUtil.date());
        DateTime endTime = DateUtil.endOfWeek(DateUtil.date());
        System.out.println(startTime + startTime.dayOfWeekEnum().toChinese());
        System.out.println(endTime + endTime.dayOfWeekEnum().toChinese());
        System.out.println(DateUtil.date().toString());
    }

    /**
     * 判断用户是否可以签到
     *
     * @param token 在请求头中拿到token  @RequestHeader("token")
     * @return
     */
    @GetMapping("/validateCheckin")
    @ApiOperation("查看用户今天是否可以签到")
    public R validateCheckin(@RequestHeader("token") String token) {
        int userId = jwtUtil.getUserId(token);
        String result = checkinService.validCancheckin(userId, DateUtil.today());
        return R.ok(result);
    }

    @PostMapping("/checkin")
    @ApiOperation("签到")
    public R checkin(@Valid @RequestBody CheckinForm form, @RequestHeader("token") String token) {
        int userId = jwtUtil.getUserId(token);
        HashMap param = new HashMap();
        param.put("city", form.getCity());
        param.put("district", form.getDistrict());
        param.put("address", form.getAddress());
        param.put("country", form.getCountry());
        param.put("province", form.getProvince());
        checkinService.checkin(param, userId);
        return R.ok("签到成功");
    }

    @GetMapping("/queryCheckinData")
    @ApiOperation("用户当天签到数据")
    public R queryCheckinData(@RequestHeader("token") String token) {
        int userId = jwtUtil.getUserId(token);

        //查询用户当天的考勤情况
        HashMap resultMap = checkinService.queryTodayCheckin(userId);
        resultMap.put("workStartTime", constants.workStartTime);
        resultMap.put("workEndTime", constants.workEndTime);
        //查询用户累计签到天数
        Long checkinDays = checkinService.queryCheckinDays(userId);
        resultMap.put("checkinDays", checkinDays);

        //查询用户的入职日期
        String hiredate = userService.queryUserHiredate(userId);
        DateTime startTime = DateUtil.beginOfWeek(DateUtil.date());
        DateTime endTime = DateUtil.endOfWeek(DateUtil.date());
        if (startTime.isBefore(DateUtil.parseDate(hiredate))) {
            startTime = DateUtil.parseDate(hiredate);
        }

        HashMap params = new HashMap();
        params.put("userId", userId);
        params.put("startTime", startTime.toString());
        params.put("endTime", endTime.toString());
        ArrayList<HashMap> weekCheckin = checkinService.queryWeekCheckin(params);
        resultMap.put("weekCheckin", weekCheckin);
        return R.ok().put("result", resultMap);
    }

    @PostMapping("/queryMonthCheckinData")
    @ApiOperation("用户当月的签到数据")
    public R queryMonthCheckinData(@Valid @RequestBody QueryMonthCheckinForm form, @RequestHeader("token") String token) {
        int userId = jwtUtil.getUserId(token);
        //查询入职日期
        DateTime hiredate = DateUtil.parse(userService.queryUserHiredate(userId));
        //把月份处理成双数字
        String month = form.getMonth() < 10 ? "0" + form.getMonth() : "" + form.getMonth();
        //某年某月的起始日期
        DateTime startTime = DateUtil.parse(form.getYear() + "-" + month + "-01");
        //如果查询的月份早于员工入职日期的月份
        if (startTime.isBefore(DateUtil.beginOfMonth(hiredate))) {
            throw new OfficeException("该员工之前未入职，暂无考勤数据");
        }
        //如果查询月份和入职日期恰好是同月的，那么开始日期就是入职日期
        if (startTime.isBefore(hiredate)) {
            startTime = hiredate;
        }
        //某年某月的截止日期
        DateTime endTime = DateUtil.endOfMonth(startTime);

        HashMap param = new HashMap();
        param.put("startTime", startTime.toString());
        param.put("endTime",endTime.toString());
        param.put("userId", userId);

        ArrayList<HashMap> list = checkinService.queryMonthCheckin(param);
        //统计月考勤数据  正常，迟到，缺勤
        int normal = 0, late = 0, lock = 0;
        for(HashMap<String,String> map : list){
            String type = map.get("type");
            String status = map.get("status");
            if ("工作日".equals(type) && status!=""){
                //计算考勤状态
                if ("正常".equals(status)){
                    normal++;
                }else if ("迟到".equals(status)){
                    late++;
                }else if ("缺勤".equals(status)){
                    lock++;
                }
            }
        }
        return R.ok().put("list", list).put("normal",normal).put("late",late).put("lock",lock);
    }
}

