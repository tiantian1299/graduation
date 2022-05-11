package com.example.office.wx.controller;

import com.example.office.wx.config.shiro.JwtUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reimbursement")
@Api("费用报销模块网络接口")
public class ReimbursementController {

    @Autowired
    private JwtUtil jwtUtil;
}
