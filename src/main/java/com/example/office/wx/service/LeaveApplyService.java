package com.example.office.wx.service;

import com.example.office.wx.db.pojo.TbLeaveApply;

import java.util.HashMap;

public interface LeaveApplyService {

    /**
     * 请假申请
     *
     * @param entity
     */
    void insertLeaveApply(TbLeaveApply entity);

    /**
     * 查询请假申请待办列表
     *
     * @param instanceId
     * @param id
     * @return
     */
    HashMap searchLeaveApplyByInstanceId(String instanceId, String id);
}
