package com.example.office.wx.service;

import java.util.Set;

public interface UserService {

    /**
     * 注册新用户
     *
     * @param registerCode 激活码
     * @param code         临时授权登录字符串
     * @param nickname     网名
     * @param photo        头像
     * @return
     */
    int registerUser(String registerCode, String code, String nickname, String photo);

    /**
     * 根据用户Id查询用户对应的权限
     * @param userId
     * @return
     */
    Set<String> searchUserPermissions(int userId);

    /**
     * 用户登录
     * @param code 临时授权字符串
     * @return
     */
    Integer login(String code);
}
