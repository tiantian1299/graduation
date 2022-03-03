package com.example.office.wx.db.mapper;

import com.example.office.wx.db.pojo.TbMeeting;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface TbMeetingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TbMeeting record);

    int insertSelective(TbMeeting record);

    TbMeeting selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbMeeting record);

    int updateByPrimaryKey(TbMeeting record);
}