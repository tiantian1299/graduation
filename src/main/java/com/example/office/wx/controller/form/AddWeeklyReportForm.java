package com.example.office.wx.controller.form;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class AddWeeklyReportForm {

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
