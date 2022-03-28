package com.example.office.wx.controller.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel
@Data
public class PermissionForm {
    @NotNull
    @Min(value = 0)
    private Integer id;
}
