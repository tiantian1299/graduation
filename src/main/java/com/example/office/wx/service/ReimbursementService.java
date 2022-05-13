package com.example.office.wx.service;

import com.example.office.wx.db.pojo.TbReimbursement;

public interface ReimbursementService {

    /**
     * 费用报销申请
     * @param entity
     */
    void insertReimbursement(TbReimbursement entity);
}
