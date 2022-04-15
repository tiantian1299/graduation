package com.example.office.wx.service.impl;

import com.example.office.wx.service.ApprovalService;
import com.example.office.wx.service.MeetingService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ApprovalServiceImpl implements ApprovalService {

    @Autowired
    TaskService taskService;

    @Autowired
    MeetingService meetingService;

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
        ArrayList<HashMap> meeting = new ArrayList<>();
        for (Task task : tasks) {
            String instanceId = task.getProcessInstanceId();
            //会议审批
            if (task.getProcessDefinitionId().equals("MeetingProcess:2:76ae6e8f-bbcf-11ec-89e9-144f8a149005")) {
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
    public void completeTask(String processInstanceId, String result) {
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        HashMap map = new HashMap();
        map.put("result", result);
        taskService.complete(task.getId(), map);
    }


}
