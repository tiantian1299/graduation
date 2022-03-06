package com.example.office.wx.db.pojo;

import java.util.Date;

/**
    * 会议表
    */
public class TbMeeting {
    /**
    * 主键
    */
    private Long id;

    /**
    * UUID
    */
    private String uuid;

    /**
    * 会议题目
    */
    private String title;

    /**
    * 创建人ID
    */
    private Long creatorId;

    /**
    * 日期
    */
    private Date date;

    /**
    * 开会地点
    */
    private String place;

    /**
    * 开始时间
    */
    private Date start;

    /**
    * 结束时间
    */
    private Date end;

    /**
    * 会议类型（1在线会议，2线下会议）
    */
    private Short type;

    /**
    * 参与者
    */
    private String members;

    /**
    * 会议内容
    */
    private String desc;

    /**
    * 工作流实例ID
    */
    private String instanceId;

    /**
    * 状态（1未开始，2进行中，3已结束）
    */
    private Short status;

    /**
    * 创建时间
    */
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}