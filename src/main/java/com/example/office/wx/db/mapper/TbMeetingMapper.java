package com.example.office.wx.db.mapper;

import com.example.office.wx.db.pojo.TbMeeting;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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

    /**
     * 更新会议状态
     * @param instanceId
     * @return
     */
    int updateStatus(String instanceId,int status);

    /**
     * 查询邮件字段
     * @return
     */
    ArrayList<HashMap> searchForEmail();

    /**
     * 更新会议id
     * @param id
     * @return
     */
    int updateSend(Integer id);

    /**
     * 查询员工的会议申请列表
     * @param id
     * @return
     */
    ArrayList<HashMap> searchMeetByUserId(int id);

    /**
     * 查询用户一个月的会议 日期
     * @param param
     * @return
     */
    List<String> searchUserMeetingInMonth(HashMap param);
}