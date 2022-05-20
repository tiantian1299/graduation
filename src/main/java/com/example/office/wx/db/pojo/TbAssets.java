package com.example.office.wx.db.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * tb_assets
 * @author 
 */
@Data
public class TbAssets implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 资产名称
     */
    private String assetsName;

    /**
     * 库存量
     */
    private Integer inventory;

    /**
     * 创建人id
     */
    private Integer creatorId;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}