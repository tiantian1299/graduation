package com.example.office.wx.service.impl;

import com.example.office.wx.db.mapper.TbDeptMapper;
import com.example.office.wx.db.mapper.TbUserMapper;
import com.example.office.wx.db.pojo.TbDept;
import com.example.office.wx.exception.OfficeException;
import com.example.office.wx.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService{

    @Autowired
    private TbDeptMapper tbDeptMapper;

    @Autowired
    private TbUserMapper tbUserMapper;

    /**
     * 查询所有部门
     * @return
     */
    @Override
    public List<TbDept> searchAllDept() {
        List<TbDept> list = tbDeptMapper.searchAllDept();
        return list;
    }

    /**
     * 插入部门信息
     * @param deptName
     * @return
     */
    @Override
    public int insertDept(String deptName) {
        int row = tbDeptMapper.insertDept(deptName);
        if (row != 1) {
            throw new OfficeException("部门添加失败");
        }
        return row;
    }

    /**
     * 根据id删除部门
     * @param id
     */
    @Override
    public void deleteDeptById(int id) {
        //查询部门下是否还有员工
        long count = tbUserMapper.searchUserCountInDept(id);
        if (count > 0) {
            throw new OfficeException("部门中有员工，无法删除部门");
        } else {
            int row = tbDeptMapper.deleteDeptById(id);
            if (row != 1) {
                throw new OfficeException("部门删除失败");
            }
        }
    }

    @Override
    public void updateDeptById(TbDept entity){
        int row=tbDeptMapper.updateDeptById(entity);
        if (row != 1) {
            throw new OfficeException("部门删除失败");
        }
    }

    /**
     * 根据部门名称查询部门id
     * @param deptName
     * @return
     */
    @Override
    public int searchDeptIdByDeptName(String deptName){
       return tbDeptMapper.searchDeptIdByDeptName(deptName);
    }
}
