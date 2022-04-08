package com.example.office.wx.service.impl;

import cn.hutool.extra.pinyin.PinyinUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.office.wx.controller.form.InsertUserForm;
import com.example.office.wx.controller.form.UpdateUserInfoForm;
import com.example.office.wx.db.mapper.TbDeptMapper;
import com.example.office.wx.db.mapper.TbUserMapper;
import com.example.office.wx.db.pojo.TbUser;
import com.example.office.wx.exception.OfficeException;
import com.example.office.wx.service.DeptService;
import com.example.office.wx.service.UserService;
import com.example.office.wx.task.ActiveCodeTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@Scope("prototype")
public class UserServiceImpl implements UserService {
    @Value("${wx.app-id}")
    private String appId;

    @Value("${wx.app-secret}")
    private String appSecret;

    @Autowired
    private TbUserMapper tbUserMapper;

    @Autowired
    private TbDeptMapper tbDeptMapper;

    @Autowired
    private ActiveCodeTask activeCodeTask;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    DeptService deptService;

    /**
     * 注册新用户
     *
     * @param registerCode 激活码
     * @param code         临时授权登录字符串
     * @param nickname     网名
     * @param photo        头像
     * @return
     */
    @Override
    public int registerUser(String registerCode, String code, String nickname, String photo) {
        // 超级管理员注册
        if (registerCode.equals("000000")) {
            //如果是超级管理员的激活码 ，先判断是否已经存在超级管理员了
            Boolean flag = tbUserMapper.haveRootUser();
            if (!flag) {
                String openId = getOpenId(code);
                HashMap params = new HashMap();
                params.put("openId", openId);
                params.put("nickname", "天天小可爱");
                params.put("photo",photo);
                //角色多个，json格式
                params.put("role", "[0]");
                params.put("status", 1);
                params.put("createTime", new Date());
                params.put("root", true);
                tbUserMapper.insert(params);
                Integer id = tbUserMapper.searchIdByOpenId(openId);
                return id;
            } else {
                throw new OfficeException("已经存在超级管理员了");
            }
        } else if (!redisTemplate.hasKey(registerCode)){
            //判断验证码是否有效
            throw new OfficeException("无效的验证码");
        }else{
            // 根据key值获取value值
            int userId = Integer.parseInt(redisTemplate.opsForValue().get(registerCode).toString());
            String openId = getOpenId(code);
            HashMap param = new HashMap();
            param.put("openId", openId);
            param.put("nickname", nickname);
            param.put("photo", photo);
            param.put("userId", userId);
            int row = tbUserMapper.activeUserAccount(param);
            if (row != 1) {
                throw new OfficeException("账号激活失败");
            }
            // 删除缓存中的激活码
            redisTemplate.delete(registerCode);
            return userId;
        }
    }

    /**
     * 请求微信接口获取openId
     *
     * @param code 用户登录的临时授权字符串
     * @return
     */
    private String getOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        HashMap map = new HashMap();
        map.put("appid", appId);
        map.put("secret", appSecret);
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        //发送请求 ，返回是一个字符串的响应
        String response = HttpUtil.post(url, map);
        //将响应转为json格式
        JSONObject json = JSONUtil.parseObj(response);
        //在json中获取 openId
        String openId = json.getStr("openid");
        if (openId == null || openId.length() == 0) {
            throw new RuntimeException("临时登陆凭证错误");
        }
        return openId;
    }

    /**
     * 根据用户Id查询用户对应的权限
     *
     * @param userId
     * @return
     */
    @Override
    public Set<String> searchUserPermissions(int userId) {
        Set<String> permissions = tbUserMapper.searchUserPermissions(userId);
        return permissions;
    }

    /**
     * 用户登录
     *
     * @param code 临时授权字符串
     * @return
     */
    @Override
    public Integer login(String code) {
        String openId = getOpenId(code);
        Integer id = tbUserMapper.searchIdByOpenId(openId);
        if (id == null) {
            throw new OfficeException("员工不存在");
        }
        return id;
    }

    /**
     * 根据用户id 来查询用户信息
     * @param userId
     * @return
     */
    @Override
    public TbUser searchById(int userId){
        TbUser user = tbUserMapper.searchById(userId);
        return user;
    }

    /**
     * 查询用户的入职日期
     * @param userId
     * @return
     */
    @Override
    public String queryUserHiredate(int userId) {
        return tbUserMapper.queryUserHiredate(userId);
    }

    /**
     * 按照部门分组查询用户信息
     * @param keyword
     * @return
     */
    @Override
    public ArrayList<HashMap> searchUserGroupByDept(String keyword) {
        ArrayList<HashMap> deptMembers = tbDeptMapper.searchDeptMembers(keyword);
        ArrayList<HashMap> userGroupByDept = tbUserMapper.searchUserGroupByDept(keyword);
        //处理返回结果
        for (HashMap map1 : deptMembers) {
            long deptId = (Long) map1.get("id");
            ArrayList members = new ArrayList();
            for (HashMap map2 : userGroupByDept) {
                long id = (Long) map2.get("deptId");
                if (deptId == id) {
                    members.add(map2);
                }
            }
            map1.put("members", members);
        }
        return deptMembers;
    }


    /**
     * 新增员工
     * @param form
     */
    @Override
    public void insertUser(InsertUserForm form){

        if (!JSONUtil.isJsonArray(form.getRole())) {
            throw new OfficeException("角色不是数组格式");
        }

        JSONArray array = JSONUtil.parseArray(form.getRole());
        int id = deptService.searchDeptIdByDeptName(form.getDeptName());
        TbUser user = new TbUser();
        BeanUtils.copyProperties(form, user);
        user.setDeptId(id);
        user.setCreateTime(new Date());
        if (array.contains(0)) {
            user.setRoot(true);
        } else {
            user.setRoot(false);
        }
        int i = tbUserMapper.insertUser(user);
        if (i == 1){
            //生成激活码 发送邮件
            activeCodeTask.sendActiveCodeAsync(user.getId(),user.getEmail());
        }else{
            throw new OfficeException("添加员工失败");
        }
    }

    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    @Override
    public HashMap searchUserSummary(int userId) {
        HashMap map = tbUserMapper.searchUserSummary(userId);
        return map;
    }

    /**
     * 查询用户的个人资料
     * @param userId
     * @return
     */
    @Override
    public HashMap searchUserInfo(int userId) {
        HashMap map = tbUserMapper.searchUserInfo(userId);
        return map;
    }

    /**
     * 更新用户的个人信息
     * @param form
     * @return
     */
    @Override
    public int updateUserInfo(UpdateUserInfoForm form) {
        boolean root = false;
        if (!JSONUtil.isJsonArray(form.getRole())) {
            throw new OfficeException("role不是有效的JSON数组");
        } else {
            // 判断是否有超级管理员权限
            JSONArray role = JSONUtil.parseArray(form.getRole());
            root = role.contains(0) ? true : false;
        }
        int id = deptService.searchDeptIdByDeptName(form.getDeptName());
        TbUser user = new TbUser();
        BeanUtils.copyProperties(form, user);
        user.setDeptId(id);
        user.setId(form.getUserId());
        user.setStatus((byte) form.getStatus());
        user.setRoot(root);
        //更新员工记录
        int rows = tbUserMapper.updateUserInfo(user);
        return rows;
    }

    /**
     * 删除员工
     * @param userId
     */
    @Override
    public void deleteUserById(int userId){
        int i = tbUserMapper.deleteUserById(userId);
        if(i!=1){
            throw new OfficeException("删除员工失败");
        }
    }

    /**
     * 查询用户通讯录
     * @return
     */
    @Override
    public JSONObject searchUserContactList() {
        ArrayList<HashMap> list = tbUserMapper.searchUserContactList();
        String letter = null;
        JSONObject json = new JSONObject(true);
        JSONArray array = null;
        for (HashMap<String, String> map : list) {
            String name = map.get("name");
            //拿到第一个字母
            String firstLetter = PinyinUtil.getPinyin(name).charAt(0) + "";
            firstLetter = firstLetter.toUpperCase();
            if (letter == null || !letter.equals(firstLetter)) {
                letter = firstLetter;
                array = new JSONArray();
                json.set(letter, array);
            }
            array.put(map);
        }
        return json;
    }

    /**
     * 查询参会用户信息
     * @param params
     * @return
     */
    @Override
    public ArrayList<HashMap> searchMembersInfo(List params){
        return tbUserMapper.searchMembersInfo(params);
    }
}
