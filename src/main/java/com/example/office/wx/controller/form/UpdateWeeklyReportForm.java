package com.example.office.wx.controller.form;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class UpdateWeeklyReportForm {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空")
    private Integer id;

    @NotBlank(message = "周报标题不能为空")
    private String  title;

    /**
     * 本周工作内容
     */
    @NotBlank(message = "本周工作内容不能为空")
    private String thisWeekContent;

    /**
     * 下周工作计划
     */
    @NotBlank(message = "下周工作计划不能为空")
    private String nextWeekPlan;

    /**
     * 抄送人ID
     */
    @NotBlank(message = "抄送人id不能为空")
    private String ccUserIds;

}
