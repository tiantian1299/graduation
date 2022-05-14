package com.example.office.wx.service;

import com.example.office.wx.db.pojo.TbReimbursement;

import java.util.HashMap;

public interface ReimbursementService {

    /**
     * 费用报销申请
     *
     * @param entity
     */
    void insertReimbursement(TbReimbursement entity);

    /**
     * 查询费用报销待审批列表
     *
     * @param instanceId
     * @param id
     * @return
     */
    HashMap searchReimbursementByInstanceId(String instanceId, String id);
}
