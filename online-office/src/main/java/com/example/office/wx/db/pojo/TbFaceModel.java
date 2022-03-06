package com.example.office.wx.db.pojo;

public class TbFaceModel {
    /**
    * 主键值
    */
    private Integer id;

    /**
    * 用户ID
    */
    private Integer userId;

    /**
    * 用户人脸模型
    */
    private String faceModel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFaceModel() {
        return faceModel;
    }

    public void setFaceModel(String faceModel) {
        this.faceModel = faceModel;
    }
}