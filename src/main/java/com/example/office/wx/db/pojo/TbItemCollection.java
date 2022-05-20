package com.example.office.wx.db.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * tb_item_collection
 * @author 
 */
@Data
public class TbItemCollection implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 物品名称
     */
    private Integer itemName;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 用途
     */
    private String use;

    /**
     * 领用人
     */
    private Object receivers;

    /**
     * 受理人
     */
    private Integer handleId;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}