package com.example.office.wx.db.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * tb_weekly_report
 * @author 
 */
@Data
public class TbWeeklyReportDto implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 本周工作内容
     */
    private String thisWeekContent;

    /**
     * 下周工作计划
     */
    private String nextWeekPlan;

    /**
     * 创建人ID
     */
    private Integer createUserId;

    /**
     * 抄送人ID
     */
    private String ccUserIds;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 创建人姓名
     */
    private String name;

    /**
     * 标题
     */
    private String title;

    private static final long serialVersionUID = 1L;
}