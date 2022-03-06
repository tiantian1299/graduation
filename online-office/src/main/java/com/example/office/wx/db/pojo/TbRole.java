package com.example.office.wx.db.pojo;

/**
    * 角色表
    */
public class TbRole {
    /**
    * 主键
    */
    private Integer id;

    /**
    * 角色名称
    */
    private String roleName;

    /**
    * 权限集合
    */
    private String permissions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}