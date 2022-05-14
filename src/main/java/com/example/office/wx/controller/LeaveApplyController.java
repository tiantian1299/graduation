package com.example.office.wx.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.example.office.wx.common.util.R;
import com.example.office.wx.config.shiro.JwtUtil;
import com.example.office.wx.controller.form.InsertLeaveApplyForm;
import com.example.office.wx.db.pojo.TbLeaveApply;
import com.example.office.wx.exception.OfficeException;
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
    public R insertLeaveApply(@Valid @RequestBody InsertLeaveApplyForm form, @RequestHeader("token") String token) {

        DateTime d1 = DateUtil.parse(form.getStartTime() + ":00");
        DateTime d2 = DateUtil.parse(form.getEndTime() + ":00");
        if (d2.isBeforeOrEquals(d1)) {
            throw new OfficeException("结束时间必须大于开始时间");
        }
        if (!JSONUtil.isJsonArray(form.getFilePathsList())) {
            throw new OfficeException("filePathsList不是JSON数组");
        }

        TbLeaveApply entity = new TbLeaveApply();
        entity.setLeaveType(form.getLeaveType());
        entity.setStartTime(form.getStartTime()+":00");
        entity.setEndTime(form.getEndTime()+":00");
        entity.setLeaveDuration(form.getLeaveDuration());
        entity.setReason(form.getReason());
        entity.setAttachment(form.getFilePathsList().replaceAll("&quot;","\""));
        entity.setCreatorId(jwtUtil.getUserId(token));
        entity.setCreateTime(new Date());
        leaveApplyService.insertLeaveApply(entity);
        return R.ok().put("result", "success");
    }

}
