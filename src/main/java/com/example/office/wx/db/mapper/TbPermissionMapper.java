package com.example.office.wx.db.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;


@Mapper
public interface TbPermissionMapper {
    /**
     * 根据角色id查询对应的权限信息
     *
     * @param id
     * @return
     */
    ArrayList<HashMap> queryPermissionByRoleId(int id);

    /**
     * 查询所有的权限
     *
     * @return
     */
    ArrayList<HashMap> queryAllPermission();
}