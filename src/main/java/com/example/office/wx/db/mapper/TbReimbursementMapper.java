package com.example.office.wx.db.mapper;

import com.example.office.wx.db.pojo.TbReimbursement;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface TbReimbursementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbReimbursement record);

    int insertSelective(TbReimbursement record);

    TbReimbursement selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbReimbursement record);

    int updateByPrimaryKey(TbReimbursement record);

    /**
     * 根据费用申请流程id查询费用申请信息
     *
     * @param params
     * @return
     */
    HashMap searchReimbursementByInstanceId(HashMap params);

    /**
     * 根据流程ID查询报销费用明细
     * @param params
     * @return
     */
    TbReimbursement queryReimbursementByInstanceId(HashMap params);
}