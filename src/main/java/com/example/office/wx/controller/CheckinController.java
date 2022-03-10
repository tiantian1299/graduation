package com.example.office.wx.controller;

import cn.hutool.core.date.DateUtil;
import com.example.office.wx.common.util.R;
import com.example.office.wx.config.shiro.JwtUtil;
import com.example.office.wx.controller.form.CheckinForm;
import com.example.office.wx.service.CheckinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        return R.ok("可以考勤");
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
}

