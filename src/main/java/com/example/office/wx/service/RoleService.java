package com.example.office.wx.service;

import com.example.office.wx.db.pojo.TbRole;

import java.util.List;

public interface RoleService {

    /**
     * 添加角色
     * @param tbRole
     * @return
     */
    void addRole(TbRole tbRole);

    /**
     * 更新角色的权限
     * @param tbRole
     * @return
     */
    void updateRolePermissions(TbRole tbRole);

    /**
     * 查询所有角色
     * @return
     */
    public List<TbRole> searchAllRole();

    /**
     * 根据角色id删除角色信息
     * @param id
     */
    public void deleteRoleById(int id);
}
