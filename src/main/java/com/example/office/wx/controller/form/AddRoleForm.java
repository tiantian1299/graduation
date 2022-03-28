package com.example.office.wx.controller.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class AddRoleForm {
    @NotBlank
    private String roleName;
    @NotBlank
    private String permissions;
}
