package com.example.onlineoffice;

import com.example.office.wx.service.PermissionService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class testPermission {

    @Autowired
    private PermissionService permissionService;

    @Test
    public void  testPermission(){
        System.out.println(permissionService.queryPermissionByRoleId(1));
    }
}
