package com.example.office.wx.db.mapper;

import com.example.office.wx.db.pojo.TbDept;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Mapper
public interface TbDeptMapper {

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
    int deleteDeptById(int id);

    /**
     * 根据部门id更新部门
     *
     * @param entity
     * @return
     */
    int updateDeptById(TbDept entity);

    /**
     * 查询该部门下的员工人数
     * @param keyword
     * @return
     */
    ArrayList<HashMap> searchDeptMembers(String keyword);

    /**
     * 根据部门名查询部门iid
     * @param deptName
     * @return
     */
    int searchDeptIdByDeptName(String deptName);
}