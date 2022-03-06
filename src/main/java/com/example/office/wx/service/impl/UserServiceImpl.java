package com.example.office.wx.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.office.wx.db.mapper.TbUserMapper;
import com.example.office.wx.db.pojo.TbUser;
import com.example.office.wx.exception.OfficeException;
import com.example.office.wx.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

@Service
@Slf4j
@Scope("prototype")
public class UserServiceImpl implements UserService {
    @Value("${wx.app-id}")
    private String appId;

    @Value("${wx.app-secret}")
    private String appSecret;

    @Autowired
    private TbUserMapper tbUserMapper;

    /**
     * 注册新用户
     *
     * @param registerCode 激活码
     * @param code         临时授权登录字符串
     * @param nickname     网名
     * @param photo        头像
     * @return
     */
    @Override
    public int registerUser(String registerCode, String code, String nickname, String photo) {
        // 超级管理员注册
        if (registerCode.equals("000000")) {
            //如果是超级管理员的激活码 ，先判断是否已经存在超级管理员了
            Boolean flag = tbUserMapper.haveRootUser();
            if (!flag) {
                String openId = getOpenId(code);
                HashMap params = new HashMap();
                params.put("openId", openId);
                params.put("nickname", nickname);
                params.put("photo",photo);
                //角色多个，json格式
                params.put("role", "[0]");
                params.put("status", 1);
                params.put("createTime", new Date());
                params.put("root", true);
                tbUserMapper.insert(params);
                Integer id = tbUserMapper.searchIdByOpenId(openId);
                return id;
            } else {
                throw new OfficeException("已经存在超级管理员了");
            }
        } else {
            // 普通用户注册
        }
        return 0;
    }

    /**
     * 请求微信接口获取openId
     *
     * @param code 用户登录的临时授权字符串
     * @return
     */
    private String getOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        HashMap map = new HashMap();
        map.put("appid", appId);
        map.put("secret", appSecret);
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        //发送请求 ，返回是一个字符串的响应
        String response = HttpUtil.post(url, map);
        //将响应转为json格式
        JSONObject json = JSONUtil.parseObj(response);
        //在json中获取 openId
        String openId = json.getStr("openid");
        if (openId == null || openId.length() == 0) {
            throw new RuntimeException("临时登陆凭证错误");
        }
        return openId;
    }

    /**
     * 根据用户Id查询用户对应的权限
     *
     * @param userId
     * @return
     */
    @Override
    public Set<String> searchUserPermissions(int userId) {
        Set<String> permissions = tbUserMapper.searchUserPermissions(userId);
        return permissions;
    }

    /**
     * 用户登录
     *
     * @param code 临时授权字符串
     * @return
     */
    @Override
    public Integer login(String code) {
        String openId = getOpenId(code);
        Integer id = tbUserMapper.searchIdByOpenId(openId);
        if (id == null) {
            throw new OfficeException("员工不存在");
        }
        return id;
    }

    /**
     * 根据用户id 来查询用户信息
     * @param userId
     * @return
     */
    @Override
    public TbUser searchById(int userId){
        TbUser user = tbUserMapper.searchById(userId);
        return user;
    }
}
