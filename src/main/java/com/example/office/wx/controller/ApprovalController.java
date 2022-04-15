package com.example.office.wx.controller;

import com.example.office.wx.common.util.R;
import com.example.office.wx.config.shiro.JwtUtil;
import com.example.office.wx.service.ApprovalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/approval")
@Api("审批模块网络接口")
public class ApprovalController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ApprovalService approvalService;

    @GetMapping("/getApprovalList")
    @ApiOperation("查询审批列表")
    public R getApprovalList(@RequestHeader("token") String token) {
        Integer userId = jwtUtil.getUserId(token);
        HashMap approvalList = approvalService.getApprovalList(userId.toString());
        return R.ok().put("result", approvalList);
    }

    @PostMapping("/completeTask")
    @ApiOperation("查询审批列表")
    public R completeTask(@RequestParam String processInstanceId,String result) {
        approvalService.completeTask(processInstanceId,result);
        return R.ok().put("result", "success");
    }
}
