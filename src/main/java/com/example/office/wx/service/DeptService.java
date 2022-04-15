package com.example.office.wx.service;


import com.example.office.wx.db.pojo.TbDept;

import java.util.List;

public interface DeptService {

    /**
     * 查询所有部门
     *
     * @return
     */
    List<TbDept> searchAllDept();

    /**
     * 插入部门信息
     *
     * @param deptName
     * @return
     */
    int insertDept(String deptName);

    /**
     * 根据部门id查询部门信息
     *
     * @param id
     * @return
     */
    void deleteDeptById(int id);

    /**
     * 根据部门id更新部门
     *
     * @param entity
     * @return
     */
    void updateDeptById(TbDept entity);

    /**
     * 根据部门名查询部门iid
     * @param deptName
     * @return
     */
    int searchDeptIdByDeptName(String deptName);
}
