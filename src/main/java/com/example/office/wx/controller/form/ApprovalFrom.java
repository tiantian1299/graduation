package com.example.office.wx.controller.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class ApprovalFrom {

    @NotBlank
    private String processInstanceId;

    @NotBlank
    private String result;

    @NotBlank
    private String type;
}
