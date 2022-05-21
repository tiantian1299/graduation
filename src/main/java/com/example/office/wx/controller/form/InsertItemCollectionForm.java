package com.example.office.wx.controller.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ApiModel
@Data
public class InsertItemCollectionForm {

    @NotNull
    private Integer itemName;

    @NotNull
    private Integer count;

    @NotNull
    private String use;

    @NotBlank
    private String members;

}

