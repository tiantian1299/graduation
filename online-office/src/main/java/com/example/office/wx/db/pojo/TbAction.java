package com.example.office.wx.db.pojo;

/**
    * 行为表
    */
public class TbAction {
    /**
    * 主键
    */
    private Integer id;

    /**
    * 行为编号
    */
    private String actionCode;

    /**
    * 行为名称
    */
    private String actionName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
}