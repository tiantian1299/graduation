package com.example.office.wx.task;

import com.example.office.wx.db.mapper.TbMeetingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.HashMap;


@Configuration
@EnableScheduling
@Slf4j
public class MeetingTask {

    @Autowired
    TbMeetingMapper tbMeetingMapper;

    @Autowired
    EmailTask emailTask;

    @Scheduled(cron = "0 */2 * * * ?")
    private void configureTasks() {
        log.info("执行定时任务");
        ArrayList<HashMap> hashMaps = tbMeetingMapper.searchForEmail();
        for (HashMap emailDetail : hashMaps) {
            //发送邮件
            SimpleMailMessage msg = new SimpleMailMessage();
            String emailCreator = (String) emailDetail.get("email");
            String emailMembers = (String) emailDetail.get("membersEmail");
            String start = (String) emailDetail.get("start");
            String end = (String) emailDetail.get("end");
            String date = (String) emailDetail.get("date");
            Long minute = (Long) emailDetail.get("minute");
            String title = (String) emailDetail.get("title");
            String meetingType = (String) emailDetail.get("type");
            String id = (String) emailDetail.get("id");
            Integer status = (Integer) emailDetail.get("status");
            String[] split = emailMembers.split(",");
            String result = "";
            if (status.equals(2)) {
                result = "不同意";
            } else if(status.equals(3)) {
                result = "同意";
            }
            //通知申请人
            msg.setTo(emailCreator);
            msg.setSubject("蒙商公司会议审批结果通知");
            msg.setText("您申请的 （" + title + "）审批结果为：" + result + " 请前往小程序查看");
            emailTask.sendMessage(msg);

            for (int i = 0; i < split.length; i++) {
                msg.setTo(split[i]);
                msg.setSubject("蒙商公司参会通知");
                msg.setText("您有一个会议需要参加，会议标题： " + title + "  日期： " + date + "， 开始时间： " + start + "， 结束时间： " + end + "， 时长： " + minute + "分钟， 会议类型：" + meetingType + "， 请前往小程序端查看详情");
                emailTask.sendMessage(msg);
            }
            tbMeetingMapper.updateSend(Integer.parseInt(id));
        }
        log.info("完成定时任务");
    }
}
