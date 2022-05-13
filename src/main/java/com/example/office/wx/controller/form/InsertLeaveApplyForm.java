package com.example.office.wx.controller.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel
@Data
public class InsertLeaveApplyForm {

    private Integer leaveType;

    @NotNull
    private Date startTime;

    @NotNull
    private Date endTime;

    @NotBlank
    private String leaveDuration;

    @NotBlank
    private String reason;

    private String filePathsList;
}
