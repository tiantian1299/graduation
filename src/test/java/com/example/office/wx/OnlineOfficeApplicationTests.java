package com.example.office.wx;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OnlineOfficeApplicationTests {
    @Autowired
    TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private
    RepositoryService repositoryService;

    /**
     * 流程发布
     */
    @Test
    public void deploy() {
        DeploymentBuilder builder = repositoryService.createDeployment();
        builder.addClasspathResource("processes/MeetingProcess.bpmn");
        Deployment deployment = builder.name("会议审批流程").deploy();
        System.out.println("流程发布成功，流程部署ID：" + deployment.getId());
    }

}
