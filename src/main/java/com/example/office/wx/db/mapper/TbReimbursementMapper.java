package com.example.office.wx.db.mapper;

import com.example.office.wx.db.pojo.TbReimbursement;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbReimbursementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbReimbursement record);

    int insertSelective(TbReimbursement record);

    TbReimbursement selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbReimbursement record);

    int updateByPrimaryKey(TbReimbursement record);
}