package com.example.office.wx.controller;

import com.example.office.wx.common.util.R;
import com.example.office.wx.config.shiro.JwtUtil;
import com.example.office.wx.controller.form.InsertReimbursementFrom;
import com.example.office.wx.db.pojo.TbReimbursement;
import com.example.office.wx.service.ReimbursementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/reimbursement")
@Api("费用报销模块网络接口")
public class ReimbursementController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ReimbursementService reimbursementService;

    @PostMapping("/insertReimbursement")
    @ApiOperation("申请费用报销")
    @RequiresPermissions(value = {"ROOT"}, logical = Logical.OR)
    public R insertReimbursement(@Valid @RequestBody InsertReimbursementFrom form, @RequestHeader("token") String token) {
        TbReimbursement entity = new TbReimbursement();
        entity.setMoney(form.getMoney());
        entity.setType(form.getType());
        entity.setMoneyDetail(form.getMoneyDetail());
        entity.setAttachment(form.getFilePathsList());
        entity.setCreateId(jwtUtil.getUserId(token));
        entity.setCraeteTime(new Date());
        reimbursementService.insertReimbursement(entity);
        return R.ok().put("result", "success");
    }
}
