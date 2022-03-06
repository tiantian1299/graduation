package com.example.office.wx.controller;

import cn.hutool.core.date.DateUtil;
import com.example.office.wx.common.util.R;
import com.example.office.wx.config.shiro.JwtUtil;
import com.example.office.wx.service.CheckinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @param token  在请求头中拿到token  @RequestHeader("token")
     * @return
     */
    @GetMapping("/validateCheckin")
    @ApiOperation("查看用户今天是否可以签到")
    public R validateCheckin(@RequestHeader("token") String token){
        int userId = jwtUtil.getUserId(token);
        String result = checkinService.validCancheckin(userId, DateUtil.today());
        return R.ok(result);
    }
}
