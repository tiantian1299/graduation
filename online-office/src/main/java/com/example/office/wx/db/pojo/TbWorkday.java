package com.example.office.wx.db.pojo;

import java.util.Date;

public class TbWorkday {
    /**
    * 主键
    */
    private Integer id;

    /**
    * 日期
    */
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}