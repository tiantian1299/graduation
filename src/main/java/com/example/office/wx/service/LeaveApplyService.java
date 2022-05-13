package com.example.office.wx.service;

import com.example.office.wx.db.pojo.TbLeaveApply;

public interface LeaveApplyService {

    /**
     * 请假申请
     * @param entity
     */
    void insertLeaveApply(TbLeaveApply entity);
}
