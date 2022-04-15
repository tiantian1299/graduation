package com.example.office.wx.service;

import com.example.office.wx.db.pojo.TbMeeting;

import java.util.ArrayList;
import java.util.HashMap;

public interface MeetingService {

    /**
     * 插入会议记录
     * @param entity
     */
    void insertMeeting(TbMeeting entity);

    /**
     * 更新会议信息
     * @param entity
     */
    void updateMeetingInfo(TbMeeting entity);

    /**
     * 分页查询会议列表
     * @param param
     * @return
     */
    ArrayList<HashMap> searchMyMeetingListByPage(HashMap param);

    /**
     * 根据会议id查询会议详情
     * @param id
     * @return
     */
    HashMap searchMeetingById(int id);

    void deleteMeetingById(int id);

    HashMap searchMeetbyInstanceId(String instanceId, String id);
}