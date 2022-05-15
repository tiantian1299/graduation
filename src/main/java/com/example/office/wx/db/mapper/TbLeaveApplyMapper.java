package com.example.office.wx.db.mapper;

import com.example.office.wx.db.pojo.TbLeaveApply;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface TbLeaveApplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbLeaveApply record);

    int insertSelective(TbLeaveApply record);

    TbLeaveApply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbLeaveApply record);

    int updateByPrimaryKey(TbLeaveApply record);

    /**
     * 根据请假申请流程id查询请假申请信息
     * @param params
     * @return
     */
    HashMap searchLeaveApplyByInstanceId(HashMap params);

    /**
     * 根据流程ID查询请假申请详情
     * @param params
     * @return
     */
    TbLeaveApply queryLeaveApplyByInstanceId(HashMap params);
}