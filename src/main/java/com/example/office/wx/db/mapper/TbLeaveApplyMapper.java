package com.example.office.wx.db.mapper;

import com.example.office.wx.db.pojo.TbLeaveApply;

public interface TbLeaveApplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbLeaveApply record);

    int insertSelective(TbLeaveApply record);

    TbLeaveApply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbLeaveApply record);

    int updateByPrimaryKey(TbLeaveApply record);
}