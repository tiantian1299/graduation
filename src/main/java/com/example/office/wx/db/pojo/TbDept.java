package com.example.office.wx.db.pojo;

public class TbDept {
    /**
    * 主键
    */
    private Integer id;

    /**
    * 部门名称
    */
    private String deptName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}