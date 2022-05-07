package com.example.office.wx.task;

import com.example.office.wx.common.util.SpringContextUtils;
import com.example.office.wx.db.mapper.TbMeetingMapper;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class sendMessageTask implements JavaDelegate {


    @Override
    public void execute(DelegateExecution delegateExecution) {
        String result = delegateExecution.getVariable("result", String.class);
        String processInstanceId = delegateExecution.getProcessInstanceId();
        String isManager = delegateExecution.getVariable("isManager", String.class);

        TbMeetingMapper tbMeetingMapper = (TbMeetingMapper) SpringContextUtils.getContext().getBean("tbMeetingMapper");

        // 更新会议状态
        if (isManager.equals("是")) {
            return;
        }
        if (result.equals("同意")){
            tbMeetingMapper.updateStatus(processInstanceId,3);
        }else{
            tbMeetingMapper.updateStatus(processInstanceId,2);
        }
    }
}
