package com.example.office.wx.service;

import java.util.HashMap;

public interface ApprovalService {

    /**
     * 查询执行人的审批列表
     *
     * @param operator
     * @return
     */
    HashMap getApprovalList(String operator);

    /**
     * 完成审批工作
     *
     * @param processInstanceId
     */
    void completeTask(String processInstanceId, String result,String type);

    /**
     * 查询某人的已审批列表
     * @param operator
     * @return
     */
    HashMap getApprovaledList(String operator);

    /**
     * 获取员工的申请列表
     * @param id
     * @return
     */
    HashMap getUserAssignList(int id);

}
