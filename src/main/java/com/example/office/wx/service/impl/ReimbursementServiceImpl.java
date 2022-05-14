package com.example.office.wx.service.impl;

import com.example.office.wx.db.mapper.TbReimbursementMapper;
import com.example.office.wx.db.mapper.TbUserMapper;
import com.example.office.wx.db.pojo.TbReimbursement;
import com.example.office.wx.exception.OfficeException;
import com.example.office.wx.service.ReimbursementService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
public class ReimbursementServiceImpl implements ReimbursementService {

    @Autowired
    private TbReimbursementMapper tbReimbursementMapper;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TbUserMapper tbUserMapper;

    /**
     * 费用报销申请
     *
     * @param entity
     */
    @Override
    public void insertReimbursement(TbReimbursement entity) {
        HashMap map = new HashMap();
        int deptManager = tbUserMapper.searchDeptManger(Math.toIntExact(entity.getCreateId()));
        map.put("deptManager", deptManager);
        //开启工作流审批
        String instanceId = runtimeService.startProcessInstanceByKey("ReimbursementProcess", map).getProcessInstanceId();
        if (StringUtils.isNotEmpty(instanceId)) {
            entity.setApprovalId(deptManager);
            entity.setInstanceId(instanceId);
            entity.setStatus(1);
            int result = tbReimbursementMapper.insertSelective(entity);
            if (result != 1) {
                throw new OfficeException("费用报销申请失败");
            }
        } else {
            throw new OfficeException("工作流启动失败");
        }
    }

    /**
     * 查询费用报销待审批列表
     *
     * @param instanceId
     * @param id
     * @return
     */
    @Override
    public HashMap searchReimbursementByInstanceId(String instanceId, String id) {
        HashMap map = new HashMap<>();
        map.put("instanceId", instanceId);
        map.put("id", Integer.parseInt(id));
        HashMap result = tbReimbursementMapper.searchReimbursementByInstanceId(map);
        return result;
    }
}
