package com.example.office.wx.service.impl;

import com.example.office.wx.db.mapper.TbMeetingMapper;
import com.example.office.wx.db.mapper.TbUserMapper;
import com.example.office.wx.db.pojo.TbLeaveApply;
import com.example.office.wx.db.pojo.TbReimbursement;
import com.example.office.wx.service.ApprovalService;
import com.example.office.wx.service.LeaveApplyService;
import com.example.office.wx.service.MeetingService;
import com.example.office.wx.service.ReimbursementService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class ApprovalServiceImpl implements ApprovalService {

    @Autowired(required = false)
    TaskService taskService;

    @Autowired(required = false)
    MeetingService meetingService;

    @Autowired(required = false)
    ReimbursementService reimbursementService;

    @Autowired(required = false)
    LeaveApplyService leaveApplyService;

    @Autowired(required = false)
    HistoryService historyService;

    @Autowired(required = false)
    TbMeetingMapper tbMeetingMapper;

    @Autowired(required = false)
    TbUserMapper tbUserMapper;

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
        List<HashMap> reimbursement = Lists.newArrayList();
        List<HashMap> leaveApply = Lists.newArrayList();
        for (Task task : tasks) {
            String instanceId = task.getProcessInstanceId();
            //会议审批
            if (task.getProcessDefinitionId().contains("MeetingProcess")) {
                //查询会议审批信息
                HashMap meetingMap = meetingService.searchMeetByInstanceId(instanceId, operator);
                meeting.add(meetingMap);
            } else if (task.getProcessDefinitionId().contains("ReimbursementProcess")) {
                HashMap reimbursementMap = reimbursementService.searchReimbursementByInstanceId(instanceId, operator);
                reimbursement.add(reimbursementMap);
            } else if (task.getProcessDefinitionId().contains("LeaveApplyProcess")) {
                HashMap leaveApplyMap = leaveApplyService.searchLeaveApplyByInstanceId(instanceId, operator);
                leaveApply.add(leaveApplyMap);
            }

        }
        result.put("meeting", meeting);
        result.put("reimbursement", reimbursement);
        result.put("leaveApply", leaveApply);
        return result;
    }

    /**
     * 完成审批工作
     *
     * @param processInstanceId
     */
    @Override
    @Transactional
    public void completeTask(String processInstanceId, String result, String type) {
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        HashMap map = new HashMap();
        if (type.equals("会议")) {
            map.put("result", result);
            map.put("processInstanceId", processInstanceId);
        } else if ("费用报销".equals(type)) {
            map.put("isManager","是");
            TbReimbursement reimbursement = reimbursementService.searchReimbursementByInstanceId(processInstanceId);
            if (reimbursement != null) {
                map.put("result", result);
                map.put("processInstanceId", processInstanceId);
                if ("同意".equals(result)) {
                    if (reimbursement.getStatus() == 1) {
                        List<Integer> userIds = tbUserMapper.searchUserByRole(5);
                        map.put("financeManager", userIds.get(0));
                        map.put("identity", "1");
                        reimbursement.setApprovalId(userIds.get(0));
                        reimbursement.setStatus(2);
                    } else if (reimbursement.getStatus() == 2) {
                        map.put("identity", "1");
                        if (reimbursement.getMoney() >= 30000) {
                            List<Integer> userIds = tbUserMapper.searchUserByRole(4);
                            map.put("generalManager", userIds.get(0));
                            map.put("identity2", "1");
                            reimbursement.setApprovalId(userIds.get(0));
                            reimbursement.setStatus(3);
                        } else {
                            map.put("identity2", "0");
                            reimbursement.setStatus(4);
                        }
                    } else if (reimbursement.getStatus() == 3) {
                        reimbursement.setStatus(4);
                    }
                } else {
                    map.put("identity", "0");
                    reimbursement.setStatus(4);
                }
                reimbursementService.updateReimbursement(reimbursement);
            }
        } else if ("请假申请".equals(type)) {
            map.put("isManager","是");
            TbLeaveApply leaveApply = leaveApplyService.searchLeaveApplyByInstanceId(processInstanceId);
            if (leaveApply != null) {
                map.put("result", result);
                map.put("processInstanceId", processInstanceId);
                if ("同意".equals(result)) {
                    if (leaveApply.getStatus() == 1) {
                        List<Integer> userIds = tbUserMapper.searchUserByRole(6);
                        map.put("hrManager", userIds.get(0));
                        map.put("identity", "1");
                        leaveApply.setApprovalId(userIds.get(0));
                        leaveApply.setStatus(2);
                    } else if (leaveApply.getStatus() == 2) {
                        map.put("identity", "1");
                        if (Integer.valueOf(leaveApply.getLeaveDuration())>=7){
                            List<Integer> userIds = tbUserMapper.searchUserByRole(4);
                            map.put("generalManager", userIds.get(0));
                            map.put("identity2", "1");
                            leaveApply.setApprovalId(userIds.get(0));
                            leaveApply.setStatus(3);
                        }else {
                            map.put("identity2", "0");
                            leaveApply.setStatus(4);
                        }
                    } else if (leaveApply.getStatus() == 3) {
                        leaveApply.setStatus(4);
                    }
                } else {
                    map.put("identity", "0");
                    leaveApply.setStatus(4);
                }
                leaveApplyService.updateLeaveApply(leaveApply);
            }
        }
        taskService.complete(task.getId(), map);
    }

    /**
     * 查询某人的已审批列表
     *
     * @param operator
     * @return
     */
    @Override
    public HashMap getApprovaledList(String operator) {
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().processFinished().taskAssignee(operator).list();
        HashMap result = new HashMap();
        ArrayList<HashMap> meeting = new ArrayList<>();
        for (HistoricTaskInstance taskInstance : list) {
            String instanceId = taskInstance.getProcessInstanceId();
            String definitionId = taskInstance.getProcessDefinitionId();
            if (definitionId.contains("MeetingProcess")) {
                //查询会议审批信息
                HashMap map = meetingService.searchMeetByInstanceId(instanceId, operator);
                if (map.isEmpty()) {
                    continue;
                }
                Integer status = (Integer) map.get("status");
                if (status.equals(2)) {
                    map.put("result", "不同意");
                    map.put("processStatus", "已结束");
                } else if (status.equals(3)) {
                    map.put("result", "同意");
                    map.put("processStatus", "已结束");
                }
                meeting.add(map);
            }
        }
        result.put("meeting", meeting);
        return result;
    }

    /**
     * 获取员工的申请列表
     *
     * @param id
     * @return
     */
    @Override
    public HashMap getUserAssignList(int id) {
        HashMap result = new HashMap();
        ArrayList<HashMap> meeting = tbMeetingMapper.searchMeetByUserId(id);
        for (HashMap meet : meeting) {
            Integer status = (Integer) meet.get("status");
            if (status.equals(1)) {
                meet.put("status", "审批中");
                meet.put("processStatus", "未结束");
            }
            if (status.equals(2)) {
                meet.put("result", "不同意");
                meet.put("processStatus", "已结束");
            } else if (status.equals(3)) {
                meet.put("result", "同意");
                meet.put("processStatus", "已结束");
            }
        }
        result.put("meeting", meeting);
        return result;
    }
}
