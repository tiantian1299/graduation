package com.example.office.wx.service;

import com.example.office.wx.controller.form.InsertUserForm;
import com.example.office.wx.controller.form.UpdateUserInfoForm;
import com.example.office.wx.db.pojo.TbUser;

import java.util.ArrayList;
import java.util.HashMap;
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
     *
     * @param userId
     * @return
     */
    Set<String> searchUserPermissions(int userId);

    /**
     * 用户登录
     *
     * @param code 临时授权字符串
     * @return
     */
    Integer login(String code);

    /**
     * 根据用户id 来查询用户信息
     *
     * @param userId
     * @return
     */
    TbUser searchById(int userId);

    /**
     * 查询用户的入职日期
     *
     * @param userId
     * @return
     */
    String queryUserHiredate(int userId);

    /**
     * 按照部门分组查询用户信息
     *
     * @param keyword
     * @return
     */
    ArrayList<HashMap> searchUserGroupByDept(String keyword);

    /**
     * 新增员工
     *
     * @param form
     */
    void insertUser(InsertUserForm form);

    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    HashMap searchUserSummary(int userId);


    /**
     * 查询用户的基本信息
     * @param userId
     * @return
     */
    HashMap searchUserInfo(int userId);

    /**
     * 更新员工的基本信息
     * @param tbUser
     * @return
     */
    int updateUserInfo(UpdateUserInfoForm form);
}
