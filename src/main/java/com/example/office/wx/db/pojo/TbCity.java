package com.example.office.wx.db.pojo;

/**
    * 疫情城市列表
    */
public class TbCity {
    /**
    * 主键
    */
    private Integer id;

    /**
    * 城市名称
    */
    private String city;

    /**
    * 拼音简称
    */
    private String code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}