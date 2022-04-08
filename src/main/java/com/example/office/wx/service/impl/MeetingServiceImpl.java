package com.example.office.wx.service.impl;

import cn.hutool.json.JSONArray;
import com.example.office.wx.db.mapper.TbMeetingMapper;
import com.example.office.wx.db.pojo.TbMeeting;
import com.example.office.wx.exception.OfficeException;
import com.example.office.wx.service.MeetingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
@Slf4j
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    private TbMeetingMapper tbMeetingMapper;

    @Override
    public void insertMeeting(TbMeeting entity) {
        //保存数据
        int row = tbMeetingMapper.insertMeeting(entity);
        if (row != 1) {
            throw new OfficeException("会议添加失败");
        }
        //TODO 开启审批工作流
    }

    /**
     * 分页查询会议信息
     *
     * @param param
     * @return
     */
    @Override
    public ArrayList<HashMap> searchMyMeetingListByPage(HashMap param) {
        ArrayList<HashMap> meetingList = tbMeetingMapper.searchMyMeetingListByPage(param);
        String date = null;
        ArrayList resultList = new ArrayList();
        HashMap resultMap = null;
        JSONArray array = null;
        for (HashMap meeting : meetingList) {
            String temp = meeting.get("date").toString();
            if (!temp.equals(date)) {
                date = temp;
                resultMap = new HashMap();
                array = new JSONArray();
                resultMap.put("date", date);
                resultMap.put("list", array);
                resultList.add(resultMap);
            }
            array.put(meeting);
        }
        return resultList;
    }


    /**
     * 根据会议id查询会议详情
     *
     * @param id
     * @return
     */
    @Override
    public HashMap searchMeetingById(int id) {
        HashMap map = tbMeetingMapper.searchMeetingById(id);
        ArrayList<HashMap> hashMaps = tbMeetingMapper.searchMeetingMembers(id);
        map.put("members", hashMaps);
        return map;
    }
}
