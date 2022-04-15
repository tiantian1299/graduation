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
}
