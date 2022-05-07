package com.example.office.wx.controller;

import com.example.office.wx.common.util.R;
import com.example.office.wx.config.shiro.JwtUtil;
import com.example.office.wx.controller.form.ApprovalFrom;
import com.example.office.wx.service.ApprovalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @ApiOperation("完成审批工作")
    public R completeTask(@Valid @RequestBody ApprovalFrom form) {
        approvalService.completeTask(form.getProcessInstanceId(), form.getResult(), form.getType());
        return R.ok().put("result", "success");
    }

    @GetMapping("/getApprovaledList")
    @ApiOperation("查询已审批列表")
    public R getApprovaledList(@RequestHeader("token") String token) {
        Integer userId = jwtUtil.getUserId(token);
        HashMap approvalList = approvalService.getApprovaledList(userId.toString());
        return R.ok().put("result", approvalList);
    }

    @GetMapping("/getUserAssignList")
    @ApiOperation("查询用户申请列表")
    public R getUserAssignList(@RequestHeader("token") String token) {
        Integer userId = jwtUtil.getUserId(token);
        HashMap assignList =  approvalService.getUserAssignList(userId);
        return R.ok().put("result", assignList);
    }



}
