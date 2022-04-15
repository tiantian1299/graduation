package com.example.office.wx.service.impl;

import cn.hutool.json.JSONArray;
import com.example.office.wx.db.mapper.TbMeetingMapper;
import com.example.office.wx.db.mapper.TbUserMapper;
import com.example.office.wx.db.pojo.TbMeeting;
import com.example.office.wx.exception.OfficeException;
import com.example.office.wx.service.MeetingService;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
@Slf4j
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    private TbMeetingMapper tbMeetingMapper;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TbUserMapper tbUserMapper;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private TaskService taskService;

    @Override
    public void insertMeeting(TbMeeting entity) {
        //判断是否是总经理
        String identity = isLeader(Math.toIntExact(entity.getCreatorId()));
        HashMap map = new HashMap();
        map.put("identity", identity);

        if (identity.equals("其他")) {
            //查询该部门的部门经理id
            int deptManager = tbUserMapper.searchDeptManger(Math.toIntExact(entity.getCreatorId()));

            map.put("deptManager", deptManager);
        } else {
            map.put("result", "同意");
        }

        //开启工作流审批
        String instanceId = runtimeService.startProcessInstanceByKey("MeetingProcess", map).getProcessInstanceId();
        if (StringUtils.isNotEmpty(instanceId)) {
            //保存数据
            entity.setInstanceId(instanceId);
            int row = tbMeetingMapper.insertMeeting(entity);
            if (row != 1) {
                throw new OfficeException("会议添加失败");
            }
        } else {
            throw new OfficeException("工作流启动失败");
        }
    }


    /**
     * 更新会议
     *
     * @param entity
     */
    @Override
    public void updateMeetingInfo(TbMeeting entity) {
        //判断是否是总经理
        String identity = isLeader(Math.toIntExact(entity.getCreatorId()));
        HashMap map = new HashMap();
        map.put("identity", identity);

        if (identity.equals("其他")) {
            //查询该部门的部门经理id
            int deptManager = tbUserMapper.searchDeptManger(Math.toIntExact(entity.getCreatorId()));
            map.put("deptManager", deptManager);
        } else {
            map.put("result", "同意");
        }

        //删除原有的工作流实例
        runtimeService.deleteProcessInstance(entity.getInstanceId(), "编辑会议");
        //开启新的工作流审批
        String instanceId = runtimeService.startProcessInstanceByKey("MeetingProcess", map).getProcessInstanceId();
        if (StringUtils.isNotEmpty(instanceId)) {
            //保存数据
            entity.setInstanceId(instanceId);
            int row = tbMeetingMapper.updateMeetingInfo(entity);
            if (row != 1) {
                throw new OfficeException("更新会议失败");
            }
        } else {
            throw new OfficeException("工作流启动失败");
        }
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

    /**
     * 根据id取消会议
     *
     * @param id
     * @return
     */
    @Override
    public void deleteMeetingById(int id) {
        HashMap map = tbMeetingMapper.searchMeetingById(id);
        String instanceId = (String) map.get("instanceId");
        //删除工作流信息
        try {
            runtimeService.deleteProcessInstance(instanceId, "取消会议");
        } catch (Exception e) {
            throw new OfficeException("删除工作流失败");
        }
        int i = tbMeetingMapper.deleteMeetingById(id);
        if (i != 1) {
            throw new OfficeException("取消会议");
        }
    }


    /**
     * 判断用户身份
     *
     * @param id
     * @return
     */
    private String isLeader(int id) {
        ArrayList<String> roles = tbUserMapper.seacherUserRole(id);
        Boolean flag = (roles.contains("部门经理") || roles.contains("总经理"));
        if (flag) {
            return "领导";
        } else {
            return "其他";
        }
    }

    /**
     * 根据流程实例id查询会议信息
     *
     * @param instanceId
     * @param id
     * @return
     */
    @Override
    public HashMap searchMeetbyInstanceId(String instanceId, String id) {
        HashMap map = new HashMap<>();
        map.put("instanceId", instanceId);
        map.put("id", Integer.parseInt(id));
        HashMap result = tbMeetingMapper.searchMeetbyInstanceId(map);
        return result;
    }
}
