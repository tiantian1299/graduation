package com.example.office.wx.db.mapper;

import com.example.office.wx.db.pojo.TbMeeting;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;


@Mapper
public interface TbMeetingMapper {

    /**
     * 添加一条会议记录
     * @param entity
     * @return
     */
    int insertMeeting(TbMeeting entity);

    /**
     * 分页查询会议列表
     * @param params
     * @return
     */
    ArrayList<HashMap> searchMyMeetingListByPage(HashMap params);

    /**
     * 根据会议id查询会议详情
     * @param id
     * @return
     */
    HashMap searchMeetingById (int id);

    /**
     * 根据会议id会议成员信息
     * @param id
     * @return
     */
    ArrayList<HashMap> searchMeetingMembers (int id);

    /**
     * 更新会议信息
     * @param entity
     * @return
     */
    int updateMeetingInfo(TbMeeting entity);

    /**
     * 删除会议
     * @param id
     * @return
     */
    int deleteMeetingById(int id);

    /**
     * 根据会议流程id查询会议信息
     * @param params
     * @return
     */
   HashMap searchMeetbyInstanceId (HashMap params);
}