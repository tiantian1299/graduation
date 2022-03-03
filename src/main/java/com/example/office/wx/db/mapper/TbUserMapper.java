package com.example.office.wx.db.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
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

    Set<String> searchUserPermissions(int userId);
}