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
     * @return
     */
    TbLeaveApply searchLeaveApplyByInstanceId(String instanceId);

    /**
     * 修改请假申请
     * @param entity
     */
    void updateLeaveApply(TbLeaveApply entity);

    /**
     * 查询请假申请待办列表
     *
     * @param instanceId
     * @param id
     * @return
     */
    HashMap searchLeaveApplyByInstanceId(String instanceId, String id);

    /**
     * 根据请假申请id查询请假申请详情
     * @param id
     * @return
     */
    TbLeaveApply searchLeaveApplyById(int id);
}
