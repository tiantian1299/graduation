package com.example.office.wx.controller;

import cn.hutool.json.JSONUtil;
import com.example.office.wx.common.util.R;
import com.example.office.wx.controller.form.AddRoleForm;
import com.example.office.wx.controller.form.DeleteForm;
import com.example.office.wx.controller.form.UpdateRolePermissionsForm;
import com.example.office.wx.db.pojo.TbRole;
import com.example.office.wx.exception.OfficeException;
import com.example.office.wx.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/role")
@RestController
@Api(tags = "角色模块web接口")
@Slf4j
public class RoleController {

    @Autowired
    RoleService roleService;

    @PostMapping("/addRole")
    @ApiOperation("添加角色信息")
    @RequiresPermissions(value = {"ROOT", "ROLE:INSERT"}, logical = Logical.OR)
    public R addRole(@Valid @RequestBody AddRoleForm role) {
        if (!JSONUtil.isJsonArray(role.getPermissions())) {
            throw new OfficeException("权限不是数组格式");
        }
        TbRole entity = new TbRole();
        entity.setRoleName(role.getRoleName());
        entity.setPermissions(role.getPermissions());
        roleService.addRole(entity);
        return R.ok().put("result", "success");
    }

    @PostMapping("/updateRolePermissions")
    @ApiOperation("更新角色权限信息")
    @RequiresPermissions(value = {"ROOT", "ROLE:INSERT"}, logical = Logical.OR)
    public R updateRolePermissions(@Valid @RequestBody UpdateRolePermissionsForm form) {
        if (!JSONUtil.isJsonArray(form.getPermissions())) {
            throw new OfficeException("权限不是数组格式");
        }
        TbRole entity = new TbRole();
        entity.setId(form.getId());
        entity.setPermissions(form.getPermissions());
        roleService.updateRolePermissions(entity);
        return R.ok().put("result", "success");
    }

    @GetMapping("/searchAllRole")
    @ApiOperation("查询所有角色数据")
    public R searchAllRole() {
        List<TbRole> list = roleService.searchAllRole();
        return R.ok().put("result", list);
    }

    @PostMapping("/deleteRoleById")
    @ApiOperation("根据ID删除角色")
    @RequiresPermissions(value = {"ROOT", "ROLE:DELETE"}, logical = Logical.OR)
    public R deleteRoleById(@Valid @RequestBody DeleteForm form) {
        roleService.deleteRoleById(form.getId());
        return R.ok().put("result", "success");
    }

}
