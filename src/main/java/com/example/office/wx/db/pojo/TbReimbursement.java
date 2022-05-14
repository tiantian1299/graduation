package com.example.office.wx.db.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * tb_reimbursement
 * @author 
 */
@Data
public class TbReimbursement implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 报销金额
     */
    private Double money;

    /**
     * 报销类别
     */
    private Integer type;

    /**
     * 费用明细
     */
    private String moneyDetail;

    /**
     * 附件
     */
    private String attachment;

    /**
     * 审批人ID
     */
    private Integer approvalId;

    /**
     * 状态（1.待财务审批;3.审批通过;4.审批不通过;5.撤销）
     */
    private Integer status;

    /**
     * 工作流实例ID
     */
    private String instanceId;

    /**
     * 申请人
     */
    private Integer createId;

    /**
     * 创建时间
     */
    private Date craeteTime;

    private static final long serialVersionUID = 1L;
}