package com.example.office.wx.db.mapper;

import com.example.office.wx.db.pojo.TbUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


@Mapper
public interface TbUserMapper {
    /**
     * 判断是否是超级管理员
     *
     * @return
     */
    boolean haveRootUser();

    /**
     * 往员工表中插入一条记录
     *
     * @param param
     * @return
     */
    int insert(HashMap param);

    /**
     * 根据OpenId查询员工id（主键）
     *
     * @param openId
     * @return
     */
    Integer searchIdByOpenId(String openId);

    /**
     * 查询用户的权限列表
     * @param userId
     * @return
     */
    Set<String> searchUserPermissions(int userId);

    /**
     * 根据用户id 来查询用户信息
     * @param userId
     * @return
     */
    TbUser searchById(int userId);

    /**
     * 查询用户的入职日期
     * @param userId
     * @return
     */
    String queryUserHiredate(int userId);

    /**
     * 根据部门id查询该部门下的用户总人数
     * @param deptId
     * @return
     */
    long searchUserCountInDept(int deptId);

    /**
     * 按照部门查询用户信息
     * @param keyword
     * @return
     */
    ArrayList<HashMap> searchUserGroupByDept(String keyword);

    /**
     * 往员工表中插入一条记录
     *
     * @param entity
     * @return
     */
    int insertUser(TbUser entity);

    /**
     * 用户注册绑定openId，头像、和网名
     * @param param
     * @return
     */
    int activeUserAccount(HashMap param);

    /**
     * 查询用户的基本信息
     * @param userId
     * @return
     */
    HashMap searchUserSummary(int userId);

    /**
     * 更新用户信息
     * @param tbUser
     * @return
     */
    int updateUserInfo(TbUser tbUser);

    /**
     * 查询员工的基本信息
     * @param userId
     * @return
     */
    HashMap searchUserInfo(int userId);

    /**
     * 删除员工
     * @param userId
     * @return
     */
    int deleteUserById (int userId);

    /**
     * 查询员工通讯录
     * @return
     */
    ArrayList<HashMap> searchUserContactList();

    /**
     * 查询参会成员信息
     * @param param 参会人员 id
     * @return
     */
    ArrayList<HashMap> searchMembersInfo(List param);

    /**
     * 查询用户角色
     * @param id
     * @return
     */
    ArrayList<String> seacherUserRole(int id);

    /**
     * 查询员工的部门经理
     * @param id
     * @return
     */
    int searchDeptManger(int id);

    /**
     * 通过角色查询用户信息
     * @param roleId
     * @return
     */
    List<Integer> searchUserByRole(int roleId);

    /**
     * 根据用户id查询用户邮箱
     * @param id
     * @return
     */
    String searchMemberEmail(int id);
}