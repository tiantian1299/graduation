package com.example.office.wx.service.impl;

import com.example.office.wx.db.mapper.TbLeaveApplyMapper;
import com.example.office.wx.db.mapper.TbReimbursementMapper;
import com.example.office.wx.db.mapper.TbUserMapper;
import com.example.office.wx.db.pojo.TbLeaveApply;
import com.example.office.wx.exception.OfficeException;
import com.example.office.wx.service.LeaveApplyService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
public class LeaveApplyServiceImpl implements LeaveApplyService {

    @Autowired(required = false)
    private TbLeaveApplyMapper tbLeaveApplyMapper;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired(required = false)
    private TbUserMapper tbUserMapper;

    /**
     * 请假申请
     *
     * @param entity
     */
    @Override
    public void insertLeaveApply(TbLeaveApply entity) {
        HashMap map = new HashMap();
        int deptManager = tbUserMapper.searchDeptManger(Math.toIntExact(entity.getCreatorId()));
        map.put("deptManager", deptManager);
        //开启工作流审批
        String instanceId = runtimeService.startProcessInstanceByKey("LeaveApplyProcess", map).getProcessInstanceId();
        if (StringUtils.isNotEmpty(instanceId)) {
            entity.setInstanceId(instanceId);
            entity.setApprovalId(deptManager);
            entity.setStatus(1);
            int result = tbLeaveApplyMapper.insertSelective(entity);
            if (result != 1) {
                throw new OfficeException("费用报销申请失败");
            }
        } else {
            throw new OfficeException("工作流启动失败");
        }
    }

    /**
     * 查询请假申请待办列表
     *
     * @param instanceId
     * @param id
     * @return
     */
    @Override
    public HashMap searchLeaveApplyByInstanceId(String instanceId, String id) {
        HashMap map = new HashMap<>();
        map.put("instanceId", instanceId);
        map.put("id", Integer.parseInt(id));
        HashMap result = tbLeaveApplyMapper.searchLeaveApplyByInstanceId(map);
        return result;
    }
}
