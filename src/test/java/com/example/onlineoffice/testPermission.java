package com.example.onlineoffice;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONException;
import com.example.office.wx.OnlineOfficeApplication;
import com.example.office.wx.db.mapper.TbUserMapper;
import com.example.office.wx.db.pojo.TbMeeting;
import com.example.office.wx.db.pojo.TbUser;
import com.example.office.wx.service.MeetingService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnlineOfficeApplication.class)
public class testPermission {

    @Autowired
    TaskService taskService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    RepositoryService repositoryService;
    @Autowired(required = true)
    private MeetingService meetingService;
    @Autowired
    private TbUserMapper tbUserMapper;

    @Test
    public void createMeetingData() {
        for (int i = 1; i <= 20; i++) {
            TbMeeting meeting = new TbMeeting();
            meeting.setId((long) i);
            meeting.setUuid(IdUtil.simpleUUID());
            meeting.setTitle("测试会议" + i);
            meeting.setCreatorId(7L); //ROOT用户ID
            meeting.setDate(DateUtil.today());
            meeting.setPlace("线上会议室");
            meeting.setStart("08:30");
            meeting.setEnd("10:30");
            meeting.setType((short) 1);
            meeting.setMembers("[60,66]");
            meeting.setDesc("天天测试会议");
            meeting.setInstanceId(IdUtil.simpleUUID());
            meeting.setStatus((short) 3);
            meeting.setCreateTime(new Date());
            meetingService.insertMeeting(meeting);
        }
        for (int i = 21; i <= 30; i++) {
            TbMeeting meeting = new TbMeeting();
            meeting.setId((long) i);
            meeting.setUuid(IdUtil.simpleUUID());
            meeting.setTitle("测试会议" + i);
            meeting.setCreatorId(48L); //ROOT用户ID
            meeting.setDate(DateUtil.today());
            meeting.setPlace("314");
            meeting.setStart("08:30");
            meeting.setEnd("10:30");
            meeting.setType((short) 2);
            meeting.setMembers("[57,58]");
            meeting.setDesc("天天测试会议");
            meeting.setInstanceId(IdUtil.simpleUUID());
            meeting.setStatus((short) 3);
            meeting.setCreateTime(new Date());
            meetingService.insertMeeting(meeting);
        }
        for (int i = 31; i <= 40; i++) {
            TbMeeting meeting = new TbMeeting();
            meeting.setId((long) i);
            meeting.setUuid(IdUtil.simpleUUID());
            meeting.setTitle("测试会议" + i);
            meeting.setCreatorId(48L); //ROOT用户ID
            meeting.setDate(DateUtil.today());
            meeting.setPlace("315");
            meeting.setStart("08:30");
            meeting.setEnd("10:30");
            meeting.setType((short) 2);
            meeting.setMembers("[48,57]");
            meeting.setDesc("天天测试会议");
            meeting.setInstanceId(IdUtil.simpleUUID());
            meeting.setStatus((short) 3);
            meeting.setCreateTime(new Date());
            meetingService.insertMeeting(meeting);
        }
    }

    @Test
    public void insertUser() {
        for (int i = 0; i < 20; i++) {
            TbUser user = new TbUser();
            user.setNickname("小天天" + i);
            user.setName("小天天" + i);
            JSONArray array = new JSONArray();
            array.put(i % 4);
            user.setRole(array.toString());
            user.setStatus((byte) 1);
            user.setCreateTime(new Date());
            user.setEmail("1299769094@qq.com");
            user.setRoot(false);
            user.setHiredate(DateUtil.today());
            user.setPhoto("https://thirdwx.qlogo.cn/mmopen/vi_32/swfssd4Agxaib1wZUXT30JKC1w2mJdOHchreuicLrjM9icYd0t9RPtGdnteKs1aJ3MMksmFtVF7LzaZyqGX6dvmgg/132");
            user.setSex("女");
            user.setDeptId(i % 5);
            user.setTel("12312432345");
            tbUserMapper.insertUser(user);
        }
    }

    @Test
    public void print() {
        JSONObject zhangsan = new JSONObject();
        try {
            //添加
            zhangsan.put("name", "张三");
            zhangsan.put("age", 18.4);
            zhangsan.put("birthday", "1900-20-03");
            zhangsan.put("majar", new String[]{"哈哈", "嘿嘿"});
            zhangsan.put("null", null);
            zhangsan.put("house", false);
            System.out.println(zhangsan.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (org.springframework.boot.configurationprocessor.json.JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询任务代办
     */
    @Test
    public void getPersonTaskList2() {
        List<Task> tiantian = taskService.createTaskQuery().taskAssignee("10").list();
        for (Task task : tiantian) {
            System.out.println("流程实例id：" + task.getProcessInstanceId());
            System.out.println("流程定义id" + task.getProcessDefinitionId());
            System.out.println("任务id：" + task.getId());
            System.out.println("任务负责人：" + task.getAssignee());
            System.out.println("任务名称：" + task.getName());
        }
    }


}
