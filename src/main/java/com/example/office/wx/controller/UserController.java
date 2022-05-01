package com.example.office.wx.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.office.wx.common.util.R;
import com.example.office.wx.config.shiro.JwtUtil;
import com.example.office.wx.config.tencent.TLSSigAPIv2;
import com.example.office.wx.controller.form.*;
import com.example.office.wx.exception.OfficeException;
import com.example.office.wx.service.DeptService;
import com.example.office.wx.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Api("用户模块Web接口")
public class UserController {

    @Autowired
    DeptService deptService;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JwtUtil jwtUtil;
    @Value("${office.jwt.cache-expire}")
    private int cacheExpire;

    @Value("${trtc.appId}")
    private Integer appId;

    @Value("${trtc.secretKey}")
    private String key;

    @Value("${trtc.expire}")
    private Integer expire;

    @PostMapping("/register")
    @ApiOperation("注册用户")
    public R register(@Valid @RequestBody RegisterForm form) {
        // 注册用户
        int id = userService.registerUser(form.getRegisterCode(), form.getCode(), form.getNickname(), form.getPhoto());
        // 生成token
        String token = jwtUtil.createToken(id);
        // 查询用户所对应的权限
        Set<String> permissions = userService.searchUserPermissions(id);
        //缓存令牌
        saveCacheToken(token, id);
        return R.ok("用户注册成功").put("token", token).put("permission", permissions);
    }

    @PostMapping("/login")
    @ApiOperation("登录系统")
    public R login(@Valid @RequestBody LoginForm form) {
        int id = userService.login(form.getCode());
        //生成token字符串
        String token = jwtUtil.createToken(id);
        //将token写入缓存中
        saveCacheToken(token, id);
        //查询用户的权限列表
        Set<String> permissions = userService.searchUserPermissions(id);
        return R.ok().put("token", token).put("permission", permissions);
    }

    /**
     * 将token保存到redis中
     *
     * @param token
     * @param userId
     */
    private void saveCacheToken(String token, int userId) {
        redisTemplate.opsForValue().set(token, userId + "", cacheExpire, TimeUnit.DAYS);
    }

    @PostMapping("/searchUserGroupByDept")
    @ApiOperation("查询员工列表，按照部门分组排列")
    public R searchUserGroupByDept(@Valid @RequestBody SearchUserGroupByDeptForm form) {
        ArrayList<HashMap> list = userService.searchUserGroupByDept(form.getKeyword());
        return R.ok().put("result", list);
    }


    @PostMapping("/insertUser")
    @ApiOperation("添加员工数据")
    @RequiresPermissions(value = {"ROOT", "EMPLOYEE:INSERT"}, logical = Logical.OR)
    public R insertUser(@RequestBody InsertUserForm form) {
        userService.insertUser(form);
        return R.ok().put("result", "success");
    }

    @GetMapping("/searchUserSummary")
    @ApiOperation("查询用户摘要信息")
    public R searchUserSummary(@RequestHeader("token") String token) {
        int userId = jwtUtil.getUserId(token);
        HashMap map = userService.searchUserSummary(userId);
        return R.ok().put("result", map);
    }

    @PostMapping("/searchUserInfo")
    @ApiOperation("查询员工数据")
    public R searchUserInfo(@Valid @RequestBody SearchUserInfoForm form) {
        HashMap map = userService.searchUserInfo(form.getUserId());
        return R.ok().put("result", map);
    }

    @GetMapping("/searchUserSelfInfo")
    @ApiOperation("查询用户自己的信息")
    public R searchUserSelfInfo(@RequestHeader("token") String token) {
        int userId = jwtUtil.getUserId(token);
        HashMap map = userService.searchUserInfo(userId);
        return R.ok().put("result", map);
    }

    @PostMapping("/updateUserInfo")
    @ApiOperation("更新用户数据")
    @RequiresPermissions(value = {"ROOT", "EMPLOYEE:UPDATE"}, logical = Logical.OR)
    public R updateUserInfo(@Valid @RequestBody UpdateUserInfoForm form) {
        int rows = userService.updateUserInfo(form);
        return R.ok().put("result", rows);
    }

    @PostMapping("/deleteUserById")
    @ApiOperation("删除员工记录")
    @RequiresPermissions(value = {"ROOT", "EMPLOYEE:DELETE"}, logical = Logical.OR)
    public R deleteUserById(@Valid @RequestBody DeleteUserByIdForm form) {
        userService.deleteUserById(form.getId());
        return R.ok().put("result", "success");
    }

    @GetMapping("/searchUserContactList")
    @ApiOperation("查询通讯录列表")
    public R searchUserContactList() {
        JSONObject json = userService.searchUserContactList();
        return R.ok().put("result", json);
    }

    @PostMapping("/searchMembers")
    @ApiOperation("查询成员")
    @RequiresPermissions(value = {"ROOT", "MEETING:INSERT", "MEETING:UPDATE"}, logical = Logical.OR)
    public R searchMembers(@Valid @RequestBody SearchMembersForm form) {
        if (!JSONUtil.isJsonArray(form.getMembers())) {
            throw new OfficeException("members不是JSON数组");
        }
        List param = JSONUtil.parseArray(form.getMembers()).toList(Integer.class);
        ArrayList list = userService.searchMembersInfo(param);
        return R.ok().put("result", list);
    }

    @GetMapping("/genUserSig")
    @ApiOperation("生成用户签名")
    public R genUserSig(@RequestHeader("token") String token) throws JSONException {
        int id = jwtUtil.getUserId(token);
        TLSSigAPIv2 api = new TLSSigAPIv2(appId, key);
        String userSig = api.genUserSig(String.valueOf(id), expire);
        return R.ok().put("userSig", userSig).put("id", id);
    }

}
