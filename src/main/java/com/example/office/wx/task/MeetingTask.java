package com.example.office.wx.task;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.example.office.wx.db.mapper.TbMeetingMapper;
import com.example.office.wx.db.pojo.TbMeeting;
import com.example.office.wx.exception.OfficeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Configuration
@EnableScheduling
@Slf4j
public class MeetingTask {

    @Autowired
    TbMeetingMapper tbMeetingMapper;

    @Autowired
    EmailTask emailTask;

    @Autowired
    RedisTemplate redisTemplate;

    @Scheduled(cron = "0 */1 * * * ?")
    private void configureTasks() {
        log.info("执行发送会议通知定时任务");
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

            if(result.equals("同意")){
                for (int i = 0; i < split.length; i++) {
                    msg.setTo(split[i]);
                    msg.setSubject("蒙商公司参会通知");
                    msg.setText("您有一个会议需要参加，会议标题： " + title + "  日期： " + date + "， 开始时间： " + start + "， 结束时间： " + end + "， 时长： " + minute + "分钟， 会议类型：" + meetingType + "， 请前往小程序端查看详情");
                    emailTask.sendMessage(msg);
                }
            }

            tbMeetingMapper.updateSend(Integer.parseInt(id));
        }
        log.info("完成发送会议通知定时任务");
    }


    /**
     * 修改会议状态
     */
    @Scheduled(cron = "0 */2 * * * ?")
    public void updateStatus(){
        log.info("执行更新会议状态任务");
        // 查询正在进行的会议
        List<TbMeeting> tbMeetings = tbMeetingMapper.searchOpening();
        //查询结束的会议
        List<TbMeeting> tbMeetingsClose = tbMeetingMapper.searchClose();
        if (tbMeetings!=null && tbMeetings.size()!=0){
            //更新会议状态
            for (TbMeeting meeting : tbMeetings) {
                String date = meeting.getDate();
                String start = meeting.getStart();
                String end = meeting.getEnd();
                DateTime startDate = DateUtil.parse(date + " " + start, "yyyy-MM-dd HH:mm");
                DateTime endDate = DateUtil.parse(date + " " + end, "yyyy-MM-dd HH:mm");
                long minute = DateUtil.between(endDate, startDate, DateUnit.MINUTE, true);
                //生成房间号
                for (;;){
                    int roomId = RandomUtil.randomInt(1000);
                    if (redisTemplate.hasKey(roomId)){
                        continue;
                    }else{
                        redisTemplate.opsForValue().set(roomId+"",meeting.getId()+"",minute, TimeUnit.MINUTES);
                        redisTemplate.opsForValue().set(meeting.getId()+"",roomId+"",minute, TimeUnit.MINUTES);
                        log.info("会议号："+roomId);
                        break;
                    }
                }
                int i = tbMeetingMapper.updateStatus(meeting.getInstanceId(), 4);
                if (i!=1){
                    throw new OfficeException("更新失败");
                }
            }
        }

        if (tbMeetingsClose!=null && tbMeetingsClose.size()!=0){
            for(TbMeeting meeting : tbMeetingsClose){
                //更新会议状态
                int i = tbMeetingMapper.updateStatus(meeting.getInstanceId(), 5);
                if (i!=1){
                    throw new OfficeException("更新失败");
                }
            }
        }
        log.info("完成执行更新会议状态任务");
    }

}
