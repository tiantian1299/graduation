package com.example.office.wx.db.mapper;

import com.example.office.wx.db.pojo.TbRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface TbRoleMapper {

    /**
     * 添加角色
     * @param tbRole
     * @return
     */
    int addRole(TbRole tbRole);

    /**
     * 更新角色的权限
     * @param tbRole
     * @return
     */
    int updateRolePermissions(TbRole tbRole);

    /**
     * 查询所有角色
     * @return
     */
    public List<TbRole> searchAllRole();

    /**
     * 查询该角色有多少用户
     * @param id
     * @return
     */
    public long searchRoleUsersCount(int id);

    /**
     * 根据id 删除角色
     * @param id
     * @return
     */
    public int deleteRoleById(int id);
}