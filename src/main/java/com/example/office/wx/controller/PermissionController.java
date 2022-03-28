package com.example.office.wx.controller;

import com.example.office.wx.common.util.R;
import com.example.office.wx.controller.form.PermissionForm;
import com.example.office.wx.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

@RequestMapping("/permission")
@RestController
@Api(tags = "权限模块web接口")
@Slf4j
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping("/queryPermissionByRoleId")
    @ApiOperation("根据角色Id查询对应的权限信息")
    @RequiresPermissions(value = {"ROOT", "ROLE:SELECT"}, logical = Logical.OR)
    public R queryPermissionByRoleId(@RequestBody @Valid PermissionForm form) {
        ArrayList<HashMap> hashMaps = permissionService.queryPermissionByRoleId(form.getId());
        return R.ok().put("result", hashMaps);
    }

    @GetMapping("/queryAllPermission")
    @ApiOperation("查询所有权限信息")
    @RequiresPermissions(value = {"ROOT", "ROLE:SELECT"}, logical = Logical.OR)
    public R queryAllPermission() {
        ArrayList<HashMap> hashMaps = permissionService.queryAllPermission();
        return R.ok().put("result", hashMaps);
    }
}
