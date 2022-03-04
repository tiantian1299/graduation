package com.example.office.wx.db.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * sys_config
 * @author 
 */
@Data
public class SysConfig implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 参数名
     */
    private String paramKey;

    /**
     * 参数值
     */
    private String paramValue;

    private Boolean isDelete;

    /**
     * 备注
     */
    private String remark;

    private static final long serialVersionUID = 1L;
}