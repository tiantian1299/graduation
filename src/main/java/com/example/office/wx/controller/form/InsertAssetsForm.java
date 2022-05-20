package com.example.office.wx.controller.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel
@Data
public class InsertAssetsForm {

    @NotNull
    private String assetsName;

    @NotNull
    private int inventory;

}

