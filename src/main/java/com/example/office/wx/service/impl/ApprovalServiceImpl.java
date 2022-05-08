package com.example.office.wx.service.impl;

import com.example.office.wx.db.mapper.TbMeetingMapper;
import com.example.office.wx.service.ApprovalService;
import com.example.office.wx.service.MeetingService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class ApprovalServiceImpl implements ApprovalService {

    @Autowired
    TaskService taskService;

    @Autowired
    MeetingService meetingService;

    @Autowired
    HistoryService historyService;

    @Autowired
    TbMeetingMapper tbMeetingMapper;

    /**
     * 查询执行人的审批列表
     *
     * @param operator
     * @return
     */
    @Override
    public HashMap getApprovalList(String operator) {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(operator).list();
        HashMap result = new HashMap();
        List<HashMap> meeting = Lists.newArrayList();
        for (Task task : tasks) {
            String instanceId = task.getProcessInstanceId();
            //会议审批
            if (task.getProcessDefinitionId().contains("MeetingProcess")) {
                //查询会议审批信息
                HashMap map = meetingService.searchMeetbyInstanceId(instanceId, operator);
                meeting.add(map);
            }

        }
        result.put("meeting", meeting);
        return result;
    }

    /**
     * 完成审批工作
     *
     * @param processInstanceId
     */
    @Override
    public void completeTask(String processInstanceId, String result,String type) {
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        HashMap map = new HashMap();
        if (type.equals("会议")){
            map.put("result", result);
            map.put("processInstanceId",processInstanceId);
        }
        taskService.complete(task.getId(), map);
    }

    /**
     * 查询某人的已审批列表
     * @param operator
     * @return
     */
    @Override
    public HashMap getApprovaledList(String operator){
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().processFinished().taskAssignee(operator).list();
        HashMap result = new HashMap();
        ArrayList<HashMap> meeting = new ArrayList<>();
        for (HistoricTaskInstance taskInstance : list){
            String instanceId = taskInstance.getProcessInstanceId();
            String definitionId = taskInstance.getProcessDefinitionId();
            if (definitionId.contains("MeetingProcess")){
                //查询会议审批信息
                HashMap map = meetingService.searchMeetbyInstanceId(instanceId, operator);
                if (map.isEmpty()){
                    continue;
                }
                Integer status = (Integer) map.get("status");
                if (status.equals(2)){
                    map.put("result","不同意");
                    map.put("processStatus","已结束");
                }else if (status.equals(3)){
                    map.put("result","同意");
                    map.put("processStatus","已结束");
                }
                meeting.add(map);
            }
        }
        result.put("meeting", meeting);
        return result;
    }

    /**
     * 获取员工的申请列表
     * @param id
     * @return
     */
    @Override
    public HashMap getUserAssignList(int id) {
        HashMap result = new HashMap();
        ArrayList<HashMap> meeting =  tbMeetingMapper.searchMeetByUserId(id);
        for (HashMap meet : meeting){
            Integer status = (Integer) meet.get("status");
            if (status.equals(1)){
                meet.put("status","审批中");
                meet.put("processStatus","未结束");
            }
            if (status.equals(2)){
                meet.put("result","不同意");
                meet.put("processStatus","已结束");
            }else if (status.equals(3)){
                meet.put("result","同意");
                meet.put("processStatus","已结束");
            }
        }
        result.put("meeting", meeting);
        return result;
    }
}
