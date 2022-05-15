package com.example.office.wx.controller.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel
@Data
public class InsertReimbursementFrom {

    @NotNull(message = "金额不能为空")
    private Double money;

    @NotNull(message = "报销类别不能为空")
    private Integer type;

    @NotBlank(message = "费用不能为空")
    private String moneyDetail;

    @NotBlank(message = "报销单据不能为空")
    private String filePathsList;
}
