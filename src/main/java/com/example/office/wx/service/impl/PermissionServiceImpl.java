package com.example.office.wx.service.impl;

import cn.hutool.json.JSONObject;
import com.example.office.wx.db.mapper.TbPermissionMapper;
import com.example.office.wx.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private TbPermissionMapper tbPermissionMapper;

    /**
     * 根据角色Id 查询用户对应的权限
     *
     * @param id
     * @return
     */
    @Override
    public ArrayList<HashMap> queryPermissionByRoleId(int id) {
        ArrayList<HashMap> result = tbPermissionMapper.queryPermissionByRoleId(id);
        ArrayList<HashMap> hashMaps = handleResult(result);
        return hashMaps;
    }

    /**
     * 查询所有权限类型
     *
     * @return
     */
    @Override
    public ArrayList<HashMap> queryAllPermission() {
        ArrayList<HashMap> result = tbPermissionMapper.queryAllPermission();
        ArrayList<HashMap> hashMaps = handleResult(result);
        return hashMaps;
    }

    /**
     * 处理返回结果
     * 按照模块分组展示
     *
     * @param list
     * @return
     */
    private ArrayList<HashMap> handleResult(ArrayList<HashMap> list) {
        //权限列表
        ArrayList permsList = new ArrayList();
        //行为列表
        ArrayList actionList = new ArrayList();
        HashSet set = new HashSet();
        HashMap data = new HashMap();

        for (HashMap map : list) {
            Long permissionId = (Long) map.get("id");
            String moduleName = (String) map.get("moduleName");
            String actionName = (String) map.get("actionName");
            String selected = map.get("selected").toString();

            if (set.contains(moduleName)) {
                JSONObject json = new JSONObject();
                json.set("id", permissionId);
                json.set("actionName", actionName);
                json.set("selected", selected.equals("1") ? true : false);
                actionList.add(json);
            } else {
                set.add(moduleName);
                data = new HashMap();
                data.put("moduleName", moduleName);
                actionList = new ArrayList();
                JSONObject json = new JSONObject();
                json.set("id", permissionId);
                json.set("actionName", actionName);
                json.set("selected", selected.equals("1") ? true : false);
                actionList.add(json);
                data.put("action", actionList);
                permsList.add(data);
            }
        }
        return permsList;
    }
}
