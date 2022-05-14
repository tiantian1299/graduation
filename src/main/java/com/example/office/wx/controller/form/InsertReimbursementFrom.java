package com.example.office.wx.controller.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel
@Data
public class InsertReimbursementFrom {

    private Double money;

    private Integer type;

    private String moneyDetail;

    private String filePathsList;

    private String desc;
}
