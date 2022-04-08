package com.example.onlineoffice;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONArray;
import com.example.office.wx.OnlineOfficeApplication;
import com.example.office.wx.db.mapper.TbUserMapper;
import com.example.office.wx.db.pojo.TbMeeting;
import com.example.office.wx.db.pojo.TbUser;
import com.example.office.wx.service.MeetingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnlineOfficeApplication.class)
public class testPermission {

    @Autowired(required = true)
    private MeetingService meetingService;

    @Autowired
    private TbUserMapper tbUserMapper;


    @Test
  public  void createMeetingData(){
        for(int i=1;i<=20;i++){
            TbMeeting meeting=new TbMeeting();
            meeting.setId((long)i);
            meeting.setUuid(IdUtil.simpleUUID());
            meeting.setTitle("测试会议"+i);
            meeting.setCreatorId(7L); //ROOT用户ID
            meeting.setDate(DateUtil.today());
            meeting.setPlace("线上会议室");
            meeting.setStart("08:30");
            meeting.setEnd("10:30");
            meeting.setType((short) 1);
            meeting.setMembers("[60,66]");
            meeting.setDesc("天天测试会议");
            meeting.setInstanceId(IdUtil.simpleUUID());
            meeting.setStatus((short)3);
            meeting.setCreateTime(new Date());
            meetingService.insertMeeting(meeting);
        }
        for(int i=21;i<=30;i++){
            TbMeeting meeting=new TbMeeting();
            meeting.setId((long)i);
            meeting.setUuid(IdUtil.simpleUUID());
            meeting.setTitle("测试会议"+i);
            meeting.setCreatorId(48L); //ROOT用户ID
            meeting.setDate(DateUtil.today());
            meeting.setPlace("314");
            meeting.setStart("08:30");
            meeting.setEnd("10:30");
            meeting.setType((short) 2);
            meeting.setMembers("[57,58]");
            meeting.setDesc("天天测试会议");
            meeting.setInstanceId(IdUtil.simpleUUID());
            meeting.setStatus((short)3);
            meeting.setCreateTime(new Date());
            meetingService.insertMeeting(meeting);
        }
        for(int i=31;i<=40;i++){
            TbMeeting meeting=new TbMeeting();
            meeting.setId((long)i);
            meeting.setUuid(IdUtil.simpleUUID());
            meeting.setTitle("测试会议"+i);
            meeting.setCreatorId(48L); //ROOT用户ID
            meeting.setDate(DateUtil.today());
            meeting.setPlace("315");
            meeting.setStart("08:30");
            meeting.setEnd("10:30");
            meeting.setType((short) 2);
            meeting.setMembers("[48,57]");
            meeting.setDesc("天天测试会议");
            meeting.setInstanceId(IdUtil.simpleUUID());
            meeting.setStatus((short)3);
            meeting.setCreateTime(new Date());
            meetingService.insertMeeting(meeting);
        }
    }

    @Test
    public void insertUser(){
        for (int i = 0; i < 20; i++) {
            TbUser user = new TbUser();
            user.setNickname("小天天"+i);
            user.setName("小天天"+i);
            JSONArray array = new JSONArray();
            array.put(i%4);
            user.setRole(array.toString());
            user.setStatus((byte) 1);
            user.setCreateTime(new Date());
            user.setEmail("1299769094@qq.com");
            user.setRoot(false);
            user.setHiredate(DateUtil.today());
            user.setPhoto("https://thirdwx.qlogo.cn/mmopen/vi_32/swfssd4Agxaib1wZUXT30JKC1w2mJdOHchreuicLrjM9icYd0t9RPtGdnteKs1aJ3MMksmFtVF7LzaZyqGX6dvmgg/132");
            user.setSex("女");
            user.setDeptId(i%5);
            user.setTel("12312432345");
            tbUserMapper.insertUser(user);
        }
    }
}
