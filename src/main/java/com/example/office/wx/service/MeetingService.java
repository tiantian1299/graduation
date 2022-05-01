package com.example.office.wx.service;

import com.example.office.wx.db.pojo.TbMeeting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    HashMap searchMeetByInstanceId(String instanceId, String id);

    /**
     * 查询用户一个月的会议信息
     * @param param
     * @return
     */
    List<String> searchUserMeetingInMonth(HashMap param);

    /**
     * 根据会议id 查询会议室房间号
     * @param id
     * @return
     */
    int searchRoomIdById(int id);



}
