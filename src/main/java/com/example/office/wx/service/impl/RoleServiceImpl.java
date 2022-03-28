package com.example.office.wx.service.impl;

import com.example.office.wx.db.mapper.TbRoleMapper;
import com.example.office.wx.db.pojo.TbRole;
import com.example.office.wx.exception.OfficeException;
import com.example.office.wx.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    TbRoleMapper tbRoleMapper;

    /**
     * 新增角色
     *
     * @param tbRole
     */
    @Override
    public void addRole(TbRole tbRole) {
        int i = tbRoleMapper.addRole(tbRole);
        if (i != 1) {
            throw new OfficeException("添加角色失败");
        }
    }

    /**
     * 更新角色权限
     *
     * @param tbRole
     */
    @Override
    public void updateRolePermissions(TbRole tbRole) {
        int i = tbRoleMapper.updateRolePermissions(tbRole);
        if (i != 1) {
            throw new OfficeException("更新角色权限失败");
        }
    }

    /**
     * 查询所有角色
     *
     * @return
     */
    @Override
    public List<TbRole> searchAllRole() {
        List<TbRole> list = tbRoleMapper.searchAllRole();
        return list;
    }

    /**
     * 根据角色id删除角色相关信息
     *
     * @param id
     */
    @Override
    public void deleteRoleById(int id) {
        // 判断该角色是否关联用户
        long count = tbRoleMapper.searchRoleUsersCount(id);
        if (count > 0) {
            throw new OfficeException("该角色关联着用户，所以无法删除");
        }
        int row = tbRoleMapper.deleteRoleById(id);
        if (row != 1) {
            throw new OfficeException("角色删除失败");
        }
    }
}
