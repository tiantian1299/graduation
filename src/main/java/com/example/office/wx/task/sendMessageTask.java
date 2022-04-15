package com.example.office.wx.task;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class sendMessageTask implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        String result = delegateExecution.getVariable("result", String.class);
        System.out.println("审批完成" + result);
    }
}
