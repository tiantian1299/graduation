package com.example.office.wx.controller;


import cn.hutool.json.JSONUtil;
import com.example.office.wx.common.util.R;
import com.example.office.wx.config.shiro.JwtUtil;
import com.example.office.wx.controller.form.AddWeeklyReportForm;
import com.example.office.wx.controller.form.DeleteForm;
import com.example.office.wx.controller.form.SearchPageForm;
import com.example.office.wx.controller.form.UpdateWeeklyReportForm;
import com.example.office.wx.db.pojo.TbWeeklyReport;
import com.example.office.wx.exception.OfficeException;
import com.example.office.wx.service.WeeklyReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/weeklyReport")
@Api("用户模块Web接口")
public class WeeklyReportController {

    @Autowired
    WeeklyReportService weeklyReportService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 查询本周是否有周报
     *
     * @param token 在请求头中拿到token  @RequestHeader("token")
     * @return
     */
    @GetMapping("/haveWeeklyReport")
    @ApiOperation("查询本周是否有周报")
    public R haveWeeklyReport(@RequestHeader("token") String token) {
        int userId = jwtUtil.getUserId(token);
        TbWeeklyReport result = weeklyReportService.haveWeeklyReport(userId);
        return R.ok().put("result", result);
    }

    @PostMapping("/addWeeklyReport")
    @ApiOperation("添加周报记录")
    public R addRole(@Valid @RequestBody AddWeeklyReportForm reportForm, @RequestHeader("token") String token) {
        if (!JSONUtil.isJsonArray(reportForm.getCcUserIds())) {
            throw new OfficeException("抄送人不是数组格式");
        }
        int userId = jwtUtil.getUserId(token);
        TbWeeklyReport report = new TbWeeklyReport();
        report.setCreateUserId(userId);
        BeanUtils.copyProperties(reportForm, report);
        weeklyReportService.addWeeklyReport(report);
        return R.ok().put("result", "success");
    }

    @PostMapping("/editWeeklyReport")
    @ApiOperation("编辑周报")
    public R editWeeklyReport(@Valid @RequestBody UpdateWeeklyReportForm reportForm) {
        if (!JSONUtil.isJsonArray(reportForm.getCcUserIds())) {
            throw new OfficeException("抄送人不是数组格式");
        }
        TbWeeklyReport report = new TbWeeklyReport();
        BeanUtils.copyProperties(reportForm, report);
        weeklyReportService.editWeeklyReport(report);
        return R.ok().put("result", "success");
    }

    @PostMapping("/getWeeklyReportListByPage")
    @ApiOperation("分页查询周报信息")
    public R getWeeklyReportListByPage(@Valid @RequestBody SearchPageForm form, @RequestHeader("token") String token) {
        int userId = jwtUtil.getUserId(token);
        int page = form.getPage();
        int length = form.getLength();
        long start = (page - 1) * length;
        HashMap map = new HashMap();
        map.put("userId", userId);
        map.put("start", start);
        map.put("length", length);
        ArrayList list = weeklyReportService.getWeeklyReportListByPage(map);
        return R.ok().put("result", list);
    }

    @PostMapping("/deleteWeeklyReport")
    @ApiOperation("删除周报")
    public R deleteWeeklyReport(@Valid @RequestBody DeleteForm form) {
        weeklyReportService.deleteWeeklyReport(form.getId());
        return R.ok().put("result", "success");
    }


    @PostMapping("/getWeeklyReportCollectList")
    @ApiOperation("收集周报")
    public R getWeeklyReportCollectList(@Valid @RequestBody SearchPageForm form, @RequestHeader("token") String token) {
        int userId = jwtUtil.getUserId(token);
        int page = form.getPage();
        int length = form.getLength();
        long start = (page - 1) * length;
        HashMap map = new HashMap();
        map.put("ccUserId", userId);
        map.put("start", start);
        map.put("length", length);
        ArrayList list = weeklyReportService.getWeeklyReportListByPage(map);
        return R.ok().put("result", list);
    }


    @GetMapping("/searchReportById")
    @ApiOperation("根据ID周报信息")
    public R searchMeetingById(@RequestParam Integer id) {
        HashMap map = weeklyReportService.searchReportById(id);
        return R.ok().put("result", map);
    }



}
