package com.example.office.wx.db.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * tb_leave_apply
 * @author 
 */
@Data
public class TbLeaveApply implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 请假类型：1.事假;2.调休;3.病假;4.年假;5.婚假;6.产假;7.丧假
     */
    private Integer leaveType;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 时长（天）
     */
    private String leaveDuration;

    /**
     * 请假事由
     */
    private String reason;

    /**
     * 创建人id
     */
    private Integer creatorId;

    /**
     * 工作流实例ID
     */
    private String instanceId;

    /**
     * 状态（1.待上级审批;2.待人力审批;3.审批通过;4.审批不通过;5.撤销）
     */
    private Integer status;

    /**
     * 附件
     */
    private Object attachment;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}