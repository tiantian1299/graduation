package com.example.office.wx.service;

import java.util.ArrayList;
import java.util.HashMap;

public interface PermissionService {

    /**
     * 根据角色id查询对应的权限信息
     * @param id
     * @return
     */
    ArrayList<HashMap> queryPermissionByRoleId(int id);

    /**
     * 查询所有权限类型
     * @return
     */
    ArrayList<HashMap> queryAllPermission();
}
