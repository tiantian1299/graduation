package com.example.office.wx.controller;


import com.example.office.wx.common.util.R;
import com.example.office.wx.controller.form.TestSayHelloForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/test")
@Api(tags = "测试Web接口")
public class TestController {



    @PostMapping("/sayHello")
    @ApiOperation("你好，世界测试接口")
    public R sayHello(@Valid @RequestBody TestSayHelloForm form) {
        return R.ok().put("message", "Hello," + form.getName());
    }

    @PostMapping("/addUser")
    @ApiOperation("添加用户")
    @RequiresPermissions(value = {"A","B"},logical = Logical.OR)
    /**
     * value 为权限列表  logical = Logical.OR 表明这权限关系是或关系
     */
    public R addUser(){
        return R.ok("用户添加成功");
    }


}
