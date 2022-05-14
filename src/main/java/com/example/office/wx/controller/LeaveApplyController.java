package com.example.office.wx.controller;

import com.example.office.wx.common.util.R;
import com.example.office.wx.config.shiro.JwtUtil;
import com.example.office.wx.controller.form.InsertLeaveApplyForm;
import com.example.office.wx.db.pojo.TbLeaveApply;
import com.example.office.wx.service.LeaveApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/leaveApply")
@Api("请假申请模块网络接口")
public class LeaveApplyController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private LeaveApplyService leaveApplyService;

    @PostMapping("/insertLeaveApply")
    @ApiOperation("请假申请")
    @RequiresPermissions(value = {"ROOT"}, logical = Logical.OR)
    public R insertLeaveApply(@Valid @RequestBody InsertLeaveApplyForm form, @RequestHeader("token") String token) {

        TbLeaveApply entity = new TbLeaveApply();
        entity.setLeaveType(form.getLeaveType());
        entity.setStartTime(form.getStartTime());
        entity.setEndTime(form.getEndTime());
        entity.setLeaveDuration(form.getLeaveDuration());
        entity.setReason(form.getReason());
        entity.setAttachment(form.getFilePathsList());
        entity.setCreatorId(jwtUtil.getUserId(token));
        entity.setCreateTime(new Date());
        leaveApplyService.insertLeaveApply(entity);
        return R.ok().put("result", "success");
    }

}
